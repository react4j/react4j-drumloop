package react4j.drumloop.browser.audio;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "AudioNode" )
public class AudioNode
{
  protected AudioNode()
  {
  }

  @Nonnull
  public native AudioNode connect( @Nonnull AudioNode destinationNode );

  public native void disconnect();
}
