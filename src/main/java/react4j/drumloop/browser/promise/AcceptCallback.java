package react4j.drumloop.browser.promise;

import jsinterop.annotations.JsFunction;

@FunctionalInterface
@JsFunction
public interface AcceptCallback<T>
{
  void onInvoke( T value );
}
