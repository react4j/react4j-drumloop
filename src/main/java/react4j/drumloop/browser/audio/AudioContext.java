package react4j.drumloop.browser.audio;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import react4j.drumloop.browser.core.ArrayBuffer;
import react4j.drumloop.browser.promise.Promise;

@JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "AudioContext" )
public final class AudioContext
{
  public AudioContext()
  {
  }

  @JsProperty( name = "destination" )
  @Nonnull
  public native AudioNode destination();

  @JsProperty( name = "state" )
  @Nonnull
  public native String state();

  @Nonnull
  public native Promise<Void> resume();

  @Nonnull
  public native GainNode createGain();

  @Nonnull
  public native AudioBufferSourceNode createBufferSource();

  @Nonnull
  public native Promise<AudioBuffer> decodeAudioData( @Nonnull ArrayBuffer audioData );
}
