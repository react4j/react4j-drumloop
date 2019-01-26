package react4j.drumloop.model;

import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.Observable;
import arez.component.CollectionsUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.inject.Singleton;

@Singleton
@ArezComponent
public abstract class DrumMachine
{
  private static final int INITIAL_BPM = 65;
  @Nonnull
  private final ArrayList<Track> _tracks = new ArrayList<>();
  private int _bpm = INITIAL_BPM;

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
  public abstract boolean isRunning();

  public abstract void setRunning( boolean running );

  @Action
  public void toggleRunning()
  {
    setRunning( !isRunning() );
  }
}
