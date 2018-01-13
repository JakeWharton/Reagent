package reagent;

import com.google.common.truth.BooleanSubject;
import com.google.common.truth.IntegerSubject;
import com.google.common.truth.StringSubject;
import com.google.common.truth.Truth;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Rule;
import org.junit.Test;
import reagent.tester.OneRecorder;
import reagent.tester.RecordingRule;

import static org.junit.Assert.assertTrue;

public final class OneJavaTest {
  @Rule public final RecordingRule rule = new RecordingRule();

  @Test public void just() {
    OneRecorder<String, StringSubject> recorder = rule.one(Truth::assertThat);
    One.just("Hello").subscribe(recorder);

    recorder.assertItem().isEqualTo("Hello");
  }

  @Test public void error() {
    RuntimeException exception = new RuntimeException("Oops!");
    OneRecorder<String, StringSubject> recorder = rule.one(Truth::assertThat);
    One.<String>error(exception).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }

  @Test public void defer() {
    AtomicInteger count = new AtomicInteger();
    One<Integer> deferred = One.deferOne(() -> One.just(count.incrementAndGet()));

    OneRecorder<Integer, IntegerSubject> recorder1 = rule.one(Truth::assertThat);
    deferred.subscribe(recorder1);
    recorder1.assertItem().isEqualTo(1);

    OneRecorder<Integer, IntegerSubject> recorder2 = rule.one(Truth::assertThat);
    deferred.subscribe(recorder2);
    recorder2.assertItem().isEqualTo(2);
  }

  @Test public void fromCallable() {
    AtomicBoolean called = new AtomicBoolean();
    OneRecorder<Boolean, BooleanSubject> recorder = rule.one(Truth::assertThat);
    One.fromCallable(() -> called.compareAndSet(false, true)).subscribe(recorder);
    assertTrue(called.get());

    recorder.assertItem().isTrue();
  }

  @Test public void fromCallableThrowing() {
    RuntimeException exception = new RuntimeException("Oops!");
    OneRecorder<String, StringSubject> recorder = rule.one(Truth::assertThat);
    One.<String>fromCallable(() -> { throw exception; }).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }
}
