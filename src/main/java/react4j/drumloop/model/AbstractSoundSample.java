package react4j.drumloop.model;

import elemental2.media.AudioBuffer;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
  public final AudioBuffer getAudioBuffer()
  {
    assert null != _audioBuffer;
    return _audioBuffer;
  }
}
