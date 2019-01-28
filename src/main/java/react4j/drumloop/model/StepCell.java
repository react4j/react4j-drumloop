package react4j.drumloop.model;

import arez.annotations.ArezComponent;
import arez.annotations.Observable;
import javax.annotation.Nonnull;
import react4j.Keyed;

@ArezComponent
public abstract class StepCell
  implements Keyed
{
  private static final int STATE_OFF = 0;
  private static final int STATE_ON = 1;
  private static final int STATE_DOUBLED = 2;
  /**
   * The position of cell in row.
   */
  private final int _beat;

  @Nonnull
  public static StepCell create( final int beat )
  {
    return new Arez_StepCell( beat );
  }

  StepCell( final int beat )
  {
    assert beat >= 0;
    _beat = beat;
  }

  public int beat()
  {
    return _beat;
  }

  @Nonnull
  @Override
  public String getKey()
  {
    return String.valueOf( beat() );
  }

  public final boolean on()
  {
    return getState() != STATE_OFF;
  }

  public final boolean doubled()
  {
    return getState() == STATE_DOUBLED;
  }

  public void setOn()
  {
    setState( STATE_ON );
  }

  public void setOff()
  {
    setState( STATE_OFF );
  }

  public void setDoubled()
  {
    setState( STATE_DOUBLED );
  }

  @Observable
  abstract int getState();

  abstract void setState( int state );
}
