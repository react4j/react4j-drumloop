package react4j.drumloop.browser.promise;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;

@FunctionalInterface
@JsFunction
public interface OnFulfilledCallback<T, V>
{
  @Nonnull
  Promise<V> onFulfilled( T value );
}
