package reagent;

import com.google.common.truth.BooleanSubject;
import com.google.common.truth.IntegerSubject;
import com.google.common.truth.StringSubject;
import com.google.common.truth.Truth;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Rule;
import org.junit.Test;
import reagent.tester.ManyRecorder;
import reagent.tester.RecordingRule;

import static org.junit.Assert.assertTrue;

public final class ManyJavaTest {
  @Rule public final RecordingRule rule = new RecordingRule();

  @Test public void create() {
    ManyRecorder<String, StringSubject> recorder = rule.many(Truth::assertThat);
    Many.<String>createMany(downstream -> {
      downstream.onNext("Hello");
      downstream.onNext("World");
      downstream.onComplete();
    }).subscribe(recorder);

    recorder.assertItem().isEqualTo("Hello");
    recorder.assertItem().isEqualTo("World");
    recorder.assertComplete();
  }

  @Test public void createError() {
    RuntimeException exception = new RuntimeException("Oops!");
    ManyRecorder<String, StringSubject> recorder = rule.many(Truth::assertThat);
    Many.<String>createMany(downstream -> downstream.onError(exception)).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }

  @Test public void empty() {
    ManyRecorder<String, StringSubject> recorder = rule.many(Truth::assertThat);
    Many.<String>empty().subscribe(recorder);

    recorder.assertComplete();
  }

  @Test public void just() {
    ManyRecorder<String, StringSubject> recorder = rule.many(Truth::assertThat);
    Many.just("Hello").subscribe(recorder);

    recorder.assertItem().isEqualTo("Hello");
    recorder.assertComplete();
  }

  @Test public void error() {
    RuntimeException exception = new RuntimeException("Oops!");
    ManyRecorder<String, StringSubject> recorder = rule.many(Truth::assertThat);
    Many.<String>error(exception).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }

  @Test public void fromArray() {
    ManyRecorder<String, StringSubject> recorder = rule.many(Truth::assertThat);
    Many.fromArray("Hello", "World").subscribe(recorder);

    recorder.assertItem().isEqualTo("Hello");
    recorder.assertItem().isEqualTo("World");
    recorder.assertComplete();
  }

  @Test public void fromIterable() {
    ManyRecorder<String, StringSubject> recorder = rule.many(Truth::assertThat);
    Many.fromIterable(Arrays.asList("Hello", "World")).subscribe(recorder);

    recorder.assertItem().isEqualTo("Hello");
    recorder.assertItem().isEqualTo("World");
    recorder.assertComplete();
  }

  @Test public void defer() {
    AtomicInteger count = new AtomicInteger();
    Many<Integer> deferred = Many.deferMany(() -> Many.just(count.incrementAndGet()));

    ManyRecorder<Integer, IntegerSubject> recorder1 = rule.many(Truth::assertThat);
    deferred.subscribe(recorder1);
    recorder1.assertItem().isEqualTo(1);
    recorder1.assertComplete();

    ManyRecorder<Integer, IntegerSubject> recorder2 = rule.many(Truth::assertThat);
    deferred.subscribe(recorder2);
    recorder2.assertItem().isEqualTo(2);
    recorder2.assertComplete();
  }

  @Test public void fromCallable() {
    AtomicBoolean called = new AtomicBoolean();
    ManyRecorder<Boolean, BooleanSubject> recorder = rule.many(Truth::assertThat);
    Many.fromCallable(() -> called.compareAndSet(false, true)).subscribe(recorder);
    assertTrue(called.get());

    recorder.assertItem().isTrue();
    recorder.assertComplete();
  }

  @Test public void fromCallableThrowing() {
    RuntimeException exception = new RuntimeException("Oops!");
    ManyRecorder<String, StringSubject> recorder = rule.many(Truth::assertThat);
    Many.<String>fromCallable(() -> { throw exception; }).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }

  @Test public void fromRunnable() {
    AtomicBoolean called = new AtomicBoolean();
    ManyRecorder<String, StringSubject> recorder = rule.many(Truth::assertThat);
    Many.<String>fromRunnable(() -> called.compareAndSet(false, true)).subscribe(recorder);
    assertTrue(called.get());

    recorder.assertComplete();
  }

  @Test public void fromRunnableThrowing() {
    RuntimeException exception = new RuntimeException("Oops!");
    ManyRecorder<String, StringSubject> recorder = rule.many(Truth::assertThat);
    Many.<String>fromRunnable(() -> { throw exception; }).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }
}
