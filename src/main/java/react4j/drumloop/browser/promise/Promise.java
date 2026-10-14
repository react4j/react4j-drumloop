package react4j.drumloop.browser.promise;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "Promise" )
public final class Promise<T>
{
  private Promise()
  {
  }

  @Nonnull
  public native <V> Promise<V> then( @Nonnull OnFulfilledCallback<? super T, ? extends V> onFulfilled );
}
