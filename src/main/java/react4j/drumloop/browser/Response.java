package react4j.drumloop.browser;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import react4j.drumloop.browser.core.ArrayBuffer;
import react4j.drumloop.browser.promise.Promise;

@JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "Response" )
public final class Response
{
  private Response()
  {
  }

  @Nonnull
  public native Promise<ArrayBuffer> arrayBuffer();
}
