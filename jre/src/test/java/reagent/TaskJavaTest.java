package reagent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Rule;
import org.junit.Test;
import reagent.tester.RecordingRule;
import reagent.tester.TaskRecorder;

import static org.junit.Assert.assertTrue;

public final class TaskJavaTest {
  @Rule public final RecordingRule rule = new RecordingRule();

  @Test public void empty() {
    TaskRecorder recorder = rule.task();
    Task.empty().subscribe(recorder);

    recorder.assertComplete();
  }

  @Test public void error() {
    RuntimeException exception = new RuntimeException("Oops!");
    TaskRecorder recorder = rule.task();
    Task.error(exception).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }

  @Test public void defer() {
    AtomicInteger count = new AtomicInteger();
    Task deferred = Task.deferTask(() -> Task.fromRunnable(count::incrementAndGet));

    TaskRecorder recorder1 = rule.task();
    deferred.subscribe(recorder1);
    recorder1.assertComplete();

    TaskRecorder recorder2 = rule.task();
    deferred.subscribe(recorder2);
    recorder2.assertComplete();
  }

  @Test public void fromCallable() {
    AtomicBoolean called = new AtomicBoolean();
    TaskRecorder recorder = rule.task();
    Task.fromCallable(() -> called.compareAndSet(false, true)).subscribe(recorder);
    assertTrue(called.get());

    recorder.assertComplete();
  }

  @Test public void fromCallableThrowing() {
    RuntimeException exception = new RuntimeException("Oops!");
    TaskRecorder recorder = rule.task();
    Task.fromCallable(() -> { throw exception; }).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }

  @Test public void fromRunnable() {
    AtomicBoolean called = new AtomicBoolean();
    TaskRecorder recorder = rule.task();
    Task.fromRunnable(() -> called.compareAndSet(false, true)).subscribe(recorder);
    assertTrue(called.get());

    recorder.assertComplete();
  }

  @Test public void fromRunnableThrowing() {
    RuntimeException exception = new RuntimeException("Oops!");
    TaskRecorder recorder = rule.task();
    Task.fromRunnable(() -> { throw exception; }).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }
}
