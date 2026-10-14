package react4j.drumloop.browser.audio;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "GainNode" )
public final class GainNode
  extends AudioNode
{
  private GainNode()
  {
  }

  @JsProperty( name = "gain" )
  @Nonnull
  public native AudioParam gain();
}
