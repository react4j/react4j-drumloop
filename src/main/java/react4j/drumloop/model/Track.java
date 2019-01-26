package react4j.drumloop.model;

import arez.component.CollectionsUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class Track
{
  private static final int MAX_BEATS = 16;
  @Nonnull
  private final String _name;
  @Nonnull
  private final String _sound;
  @Nonnull
  private final List<StepCell> _stepCells = new ArrayList<>();

  public Track( @Nonnull final String name, @Nonnull final String sound )
  {
    _name = Objects.requireNonNull( name );
    _sound = Objects.requireNonNull( sound );
    for ( int i = 0; i < MAX_BEATS; i++ )
    {
      _stepCells.add( StepCell.create( i ) );
    }
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

  @Nonnull
  public List<StepCell> getStepCells()
  {
    return CollectionsUtil.wrap( _stepCells );
  }
}
