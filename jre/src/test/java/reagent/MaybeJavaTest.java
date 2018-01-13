package reagent;

import com.google.common.truth.BooleanSubject;
import com.google.common.truth.IntegerSubject;
import com.google.common.truth.StringSubject;
import com.google.common.truth.Truth;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Rule;
import org.junit.Test;
import reagent.tester.MaybeRecorder;
import reagent.tester.RecordingRule;

import static org.junit.Assert.assertTrue;

public final class MaybeJavaTest {
  @Rule public final RecordingRule rule = new RecordingRule();

  @Test public void empty() {
    MaybeRecorder<String, StringSubject> recorder = rule.maybe(Truth::assertThat);
    Maybe.<String>empty().subscribe(recorder);

    recorder.assertComplete();
  }

  @Test public void just() {
    MaybeRecorder<String, StringSubject> recorder = rule.maybe(Truth::assertThat);

    Maybe.just("Hello").subscribe(recorder);

    recorder.assertItem().isEqualTo("Hello");
  }

  @Test public void error() {
    RuntimeException exception = new RuntimeException("Oops!");
    MaybeRecorder<String, StringSubject> recorder = rule.maybe(Truth::assertThat);
    Maybe.<String>error(exception).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }

  @Test public void defer() {
    AtomicInteger count = new AtomicInteger();
    Maybe<Integer> deferred = Maybe.deferMaybe(() -> Maybe.just(count.incrementAndGet()));

    MaybeRecorder<Integer, IntegerSubject> recorder1 = rule.maybe(Truth::assertThat);
    deferred.subscribe(recorder1);
    recorder1.assertItem().isEqualTo(1);

    MaybeRecorder<Integer, IntegerSubject> recorder2 = rule.maybe(Truth::assertThat);
    deferred.subscribe(recorder2);
    recorder2.assertItem().isEqualTo(2);
  }

  @Test public void fromCallable() {
    AtomicBoolean called = new AtomicBoolean();
    MaybeRecorder<Boolean, BooleanSubject> recorder = rule.maybe(Truth::assertThat);
    Maybe.fromCallable(() -> called.compareAndSet(false, true)).subscribe(recorder);
    assertTrue(called.get());

    recorder.assertItem().isTrue();
  }

  @Test public void fromCallableThrowing() {
    RuntimeException exception = new RuntimeException("Oops!");
    MaybeRecorder<String, StringSubject> recorder = rule.maybe(Truth::assertThat);
    Maybe.<String>fromCallable(() -> { throw exception; }).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }

  @Test public void fromRunnable() {
    AtomicBoolean called = new AtomicBoolean();
    MaybeRecorder<String, StringSubject> recorder = rule.maybe(Truth::assertThat);
    Maybe.<String>fromRunnable(() -> called.compareAndSet(false, true)).subscribe(recorder);
    assertTrue(called.get());

    recorder.assertComplete();
  }

  @Test public void fromRunnableThrowing() {
    RuntimeException exception = new RuntimeException("Oops!");
    MaybeRecorder<String, StringSubject> recorder = rule.maybe(Truth::assertThat);
    Maybe.<String>fromRunnable(() -> { throw exception; }).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }
}
