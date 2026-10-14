package react4j.drumloop.browser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "Document" )
public final class Document
{
  private Document()
  {
  }

  @Nullable
  public native Element getElementById( @Nonnull String elementId );
}
