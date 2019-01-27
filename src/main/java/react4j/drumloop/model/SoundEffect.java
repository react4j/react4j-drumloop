package react4j.drumloop.model;

import javax.annotation.Nonnull;

public final class SoundEffect
  extends AbstractSoundSample
{
  SoundEffect( @Nonnull final DrumMachine drumMachine, @Nonnull final String name, @Nonnull final String sound )
  {
    super( drumMachine, name, sound );
  }
}
