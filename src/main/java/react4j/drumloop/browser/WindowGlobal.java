package react4j.drumloop.browser;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import react4j.drumloop.browser.promise.Promise;

@JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "window" )
public final class WindowGlobal
{
  private WindowGlobal()
  {
  }

  @JsProperty( name = "document" )
  @Nonnull
  public static native Document document();

  @Nonnull
  public static native Promise<Response> fetch( @Nonnull String input );
}
