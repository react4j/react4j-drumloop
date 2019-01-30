package react4j.drumloop.model;

import elemental2.media.AudioBuffer;
import elemental2.media.AudioBufferSourceNode;
import elemental2.media.AudioContext;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jsinterop.base.Js;
import react4j.Keyed;

public abstract class AbstractSoundSample
  implements Keyed
{
  @Nonnull
  private final DrumMachine _drumMachine;
  @Nonnull
  private final String _name;
  @Nonnull
  private final String _sound;
  @Nullable
  private AudioBuffer _audioBuffer;
  private AudioBufferSourceNode _node;

  AbstractSoundSample( @Nonnull final DrumMachine drumMachine, @Nonnull final String name, @Nonnull final String sound )
  {
    _drumMachine = Objects.requireNonNull( drumMachine );
    _name = Objects.requireNonNull( name );
    _sound = Objects.requireNonNull( sound );
  }

  @Nonnull
  @Override
  public String getKey()
  {
    return getSound();
  }

  @Nonnull
  public final String getName()
  {
    return _name;
  }

  @Nonnull
  public final String getSound()
  {
    return _sound;
  }

  public final void suspendUntilAudioLoaded()
  {
    if ( null == _audioBuffer )
    {
      _audioBuffer = _drumMachine.getAudioCache().read( _sound );
    }
  }

  @Nonnull
  private AudioBufferSourceNode newAudioNode()
  {
    final AudioContext audioContext = _drumMachine.getActiveAudioContext();
    _node = audioContext.createBufferSource();
    _node.buffer = getAudioBuffer();
    _node.connect( audioContext.destination );
    return _node;
  }

  @Nonnull
  private AudioBuffer getAudioBuffer()
  {
    assert null != _audioBuffer;
    return _audioBuffer;
  }

  public final void play()
  {
    if ( null != _node )
    {
      stop();
    }
    start( false );
  }

  public final void start( final boolean loop )
  {
    final AudioBufferSourceNode node = newAudioNode();
    node.loop = loop;
    node.onended = e -> {
      if ( !node.loop )
      {
        stop();
      }
      //TODO: This should be void return and has been fixed in upstream closure compielr
      return null;
    };
    node.start( 0 );
    _node = node;
  }

  public final void loop()
  {
    if ( null != _node )
    {
      _node.loop = true;
      Js.debugger();
    }
    else
    {
      start( true );
    }
  }

  public final void stop()
  {
    if ( null != _node )
    {
      _node.stop();
      _node.disconnect();
      _node = null;
    }
  }
}
