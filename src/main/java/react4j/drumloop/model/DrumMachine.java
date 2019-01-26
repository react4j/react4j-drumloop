package react4j.drumloop.model;

import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.Observable;
import arez.annotations.Observe;
import arez.component.CollectionsUtil;
import elemental2.dom.DomGlobal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Singleton;

@Singleton
@ArezComponent
public abstract class DrumMachine
{
  private static final int INITIAL_BPM = 65;
  private static final int MAX_STEPS = 4 /* bars */ * 4 /* beats */;
  @Nonnull
  private final ArrayList<Track> _tracks = new ArrayList<>();
  private int _bpm = INITIAL_BPM;
  @Nullable
  private Double _timerId;

  DrumMachine()
  {
    _tracks.add( new Track( "Kick", "sounds/kick.wav" ) );
    _tracks.add( new Track( "Sub1", "sounds/bass.wav" ) );
    _tracks.add( new Track( "Sub2", "sounds/sub.wav" ) );
    _tracks.add( new Track( "Snare", "sounds/snare.wav" ) );
    _tracks.add( new Track( "Clap", "sounds/clap.wav" ) );
    _tracks.add( new Track( "HiHat", "sounds/hat2.wav" ) );
    _tracks.add( new Track( "OpenHiHat", "sounds/openhihat.wav" ) );
  }

  @Nonnull
  public final List<Track> getTracks()
  {
    return CollectionsUtil.wrap( _tracks );
  }

  @Observable( writeOutsideTransaction = true )
  public int getBpm()
  {
    return _bpm;
  }

  public void setBpm( final int bpm )
  {
    _bpm = bpm;
  }

  @Observable
  public abstract int getCurrentStep();

  abstract void setCurrentStep( int currentStep );

  @Action
  public void incCurrentStep()
  {
    setCurrentStep( ( getCurrentStep() + 1 ) % MAX_STEPS );
  }

  @Observable
  public abstract boolean isRunning();

  public abstract void setRunning( boolean running );

  @Action
  public void toggleRunning()
  {
    setRunning( !isRunning() );
    setCurrentStep( 0 );
  }

  @Observe
  void manageTimer()
  {
    final boolean running = isRunning();
    if ( running && null == _timerId )
    {
      startTimer();
    }
    else if ( !running && null != _timerId )
    {
      cancelTimer();
    }
  }

  private void startTimer()
  {
    assert null == _timerId;
    final double delay = 60.0 * 1000 / _bpm;
    _timerId = DomGlobal.setInterval( v -> incCurrentStep(), delay );
  }

  private void cancelTimer()
  {
    assert null != _timerId;
    DomGlobal.clearInterval( _timerId );
    _timerId = null;
  }
}
