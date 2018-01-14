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
import reagent.tester.ObservableRecorder;
import reagent.tester.RecordingRule;

import static org.junit.Assert.assertTrue;

public final class ObservableJavaTest {
  @Rule public final RecordingRule rule = new RecordingRule();

  @Test public void create() {
    ObservableRecorder<String, StringSubject> recorder = rule.observable(Truth::assertThat);
    Observable.<String>createMany(downstream -> {
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
    ObservableRecorder<String, StringSubject> recorder = rule.observable(Truth::assertThat);
    Observable.<String>createMany(downstream -> downstream.onError(exception)).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }

  @Test public void empty() {
    ObservableRecorder<String, StringSubject> recorder = rule.observable(Truth::assertThat);
    Observable.<String>empty().subscribe(recorder);

    recorder.assertComplete();
  }

  @Test public void just() {
    ObservableRecorder<String, StringSubject> recorder = rule.observable(Truth::assertThat);
    Observable.just("Hello").subscribe(recorder);

    recorder.assertItem().isEqualTo("Hello");
    recorder.assertComplete();
  }

  @Test public void error() {
    RuntimeException exception = new RuntimeException("Oops!");
    ObservableRecorder<String, StringSubject> recorder = rule.observable(Truth::assertThat);
    Observable.<String>error(exception).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }

  @Test public void fromArray() {
    ObservableRecorder<String, StringSubject> recorder = rule.observable(Truth::assertThat);
    Observable.fromArray("Hello", "World").subscribe(recorder);

    recorder.assertItem().isEqualTo("Hello");
    recorder.assertItem().isEqualTo("World");
    recorder.assertComplete();
  }

  @Test public void fromIterable() {
    ObservableRecorder<String, StringSubject> recorder = rule.observable(Truth::assertThat);
    Observable.fromIterable(Arrays.asList("Hello", "World")).subscribe(recorder);

    recorder.assertItem().isEqualTo("Hello");
    recorder.assertItem().isEqualTo("World");
    recorder.assertComplete();
  }

  @Test public void defer() {
    AtomicInteger count = new AtomicInteger();
    Observable<Integer> deferred = Observable.deferMany(() -> Observable.just(count.incrementAndGet()));

    ObservableRecorder<Integer, IntegerSubject> recorder1 = rule.observable(Truth::assertThat);
    deferred.subscribe(recorder1);
    recorder1.assertItem().isEqualTo(1);
    recorder1.assertComplete();

    ObservableRecorder<Integer, IntegerSubject> recorder2 = rule.observable(Truth::assertThat);
    deferred.subscribe(recorder2);
    recorder2.assertItem().isEqualTo(2);
    recorder2.assertComplete();
  }

  @Test public void fromCallable() {
    AtomicBoolean called = new AtomicBoolean();
    ObservableRecorder<Boolean, BooleanSubject> recorder = rule.observable(Truth::assertThat);
    Observable.fromCallable(() -> called.compareAndSet(false, true)).subscribe(recorder);
    assertTrue(called.get());

    recorder.assertItem().isTrue();
    recorder.assertComplete();
  }

  @Test public void fromCallableThrowing() {
    RuntimeException exception = new RuntimeException("Oops!");
    ObservableRecorder<String, StringSubject> recorder = rule.observable(Truth::assertThat);
    Observable.<String>fromCallable(() -> { throw exception; }).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }

  @Test public void fromRunnable() {
    AtomicBoolean called = new AtomicBoolean();
    ObservableRecorder<String, StringSubject> recorder = rule.observable(Truth::assertThat);
    Observable.<String>fromRunnable(() -> called.compareAndSet(false, true)).subscribe(recorder);
    assertTrue(called.get());

    recorder.assertComplete();
  }

  @Test public void fromRunnableThrowing() {
    RuntimeException exception = new RuntimeException("Oops!");
    ObservableRecorder<String, StringSubject> recorder = rule.observable(Truth::assertThat);
    Observable.<String>fromRunnable(() -> { throw exception; }).subscribe(recorder);

    recorder.assertError().isSameAs(exception);
  }
}
