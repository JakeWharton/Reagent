package reagent;

import com.google.common.truth.BooleanSubject;
import com.google.common.truth.IntegerSubject;
import com.google.common.truth.StringSubject;
import com.google.common.truth.Truth;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Rule;
import org.junit.Test;
import reagent.tester.TaskRecorder;
import reagent.tester.RecordingRule;

import static org.junit.Assert.assertTrue;

public final class TaskJavaTest {
  @Rule public final RecordingRule rule = new RecordingRule();

  @Test public void create() {
    TaskRecorder<String, StringSubject> recorder = rule.one(Truth::assertThat);
    Task.<String>createOne(downstream -> downstream.onItem("Hello")).subscribe(recorder);

    recorder.assertItem().isEqualTo("Hello");
  }

  @Test public void createError() {
    RuntimeException exception = new RuntimeException("Oops!");
    TaskRecorder<String, StringSubject> recorder = rule.one(Truth::assertThat);
    Task.<String>createOne(downstream -> downstream.onError(exception)).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }

  @Test public void just() {
    TaskRecorder<String, StringSubject> recorder = rule.one(Truth::assertThat);
    Task.just("Hello").subscribe(recorder);

    recorder.assertItem().isEqualTo("Hello");
  }

  @Test public void error() {
    RuntimeException exception = new RuntimeException("Oops!");
    TaskRecorder<String, StringSubject> recorder = rule.one(Truth::assertThat);
    Task.<String>error(exception).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }

  @Test public void defer() {
    AtomicInteger count = new AtomicInteger();
    Task<Integer> deferred = Task.deferOne(() -> Task.just(count.incrementAndGet()));

    TaskRecorder<Integer, IntegerSubject> recorder1 = rule.one(Truth::assertThat);
    deferred.subscribe(recorder1);
    recorder1.assertItem().isEqualTo(1);

    TaskRecorder<Integer, IntegerSubject> recorder2 = rule.one(Truth::assertThat);
    deferred.subscribe(recorder2);
    recorder2.assertItem().isEqualTo(2);
  }

  @Test public void fromCallable() {
    AtomicBoolean called = new AtomicBoolean();
    TaskRecorder<Boolean, BooleanSubject> recorder = rule.one(Truth::assertThat);
    Task.fromCallable(() -> called.compareAndSet(false, true)).subscribe(recorder);
    assertTrue(called.get());

    recorder.assertItem().isTrue();
  }

  @Test public void fromCallableThrowing() {
    RuntimeException exception = new RuntimeException("Oops!");
    TaskRecorder<String, StringSubject> recorder = rule.one(Truth::assertThat);
    Task.<String>fromCallable(() -> { throw exception; }).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }
}
