package reagent;

import org.junit.Test;
import reagent.Observable;

public final class ObservableJavaTest {
  @Test public void simple() {
    Observable.just("hey")
        .subscribe(System.out::println);
  }
}
