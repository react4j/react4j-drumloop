package react4j.drumloop.model;

import java.util.Objects;
import javax.annotation.Nonnull;

public final class Track
{
  @Nonnull
  private final String _name;
  @Nonnull
  private final String _sound;

  public Track( @Nonnull final String name, @Nonnull final String sound )
  {
    _name = Objects.requireNonNull( name );
    _sound = Objects.requireNonNull( sound );
  }

  @Nonnull
  public String getName()
  {
    return _name;
  }

  @Nonnull
  public String getSound()
  {
    return _sound;
  }
}
