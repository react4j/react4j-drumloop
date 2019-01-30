package react4j.drumloop.model;

import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.Observable;
import arez.annotations.Observe;
import arez.component.CollectionsUtil;
import elemental2.dom.DomGlobal;
import elemental2.dom.Response;
import elemental2.media.AudioBuffer;
import elemental2.media.AudioContext;
import elemental2.promise.Promise;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Singleton;
import react4j.drumloop.ReactCache;

@Singleton
@ArezComponent
public abstract class DrumMachine
{
  private static final int INITIAL_BPM = 65;
  private static final int MAX_STEPS = 4 /* bars */ * 4 /* beats */;
  @Nonnull
  private final AudioContext _audioContext;
  @Nonnull
  private final ArrayList<Track> _tracks = new ArrayList<>();
  @Nonnull
  private final ArrayList<SoundEffect> _effects = new ArrayList<>();
  private int _bpm = INITIAL_BPM;
  @Nullable
  private Double _timerId;
  @Nonnull
  private final ReactCache.Resource<String, AudioBuffer> _audioCache;

  DrumMachine()
  {
    _audioContext = new AudioContext();
    _tracks.add( new Track( this, "Kick", "sounds/kick.wav" ) );
    _tracks.add( new Track( this, "Sub1", "sounds/bass.wav" ) );
    _tracks.add( new Track( this, "Sub2", "sounds/sub.wav" ) );
    _tracks.add( new Track( this, "Snare", "sounds/snare.wav" ) );
    _tracks.add( new Track( this, "Clap", "sounds/clap.wav" ) );
    _tracks.add( new Track( this, "HiHat", "sounds/hat2.wav" ) );
    _tracks.add( new Track( this, "OpenHiHat", "sounds/openhihat.wav" ) );
    _effects.add( new SoundEffect( this, "Turn Up (F)", "sounds/loop.wav" ) );
    _effects.add( new SoundEffect( this, "SQUAD (Am)", "sounds/loop130.wav" ) );
    _effects.add( new SoundEffect( this, "Hey", "sounds/hey.wav" ) );
    _effects.add( new SoundEffect( this, "Yeah", "sounds/yeah.wav" ) );
    _audioCache = ReactCache.unstable_createResource( this::loadAudioData );
  }

  @Nonnull
  public final List<Track> getTracks()
  {
    return CollectionsUtil.wrap( _tracks );
  }

  @Nonnull
  public List<SoundEffect> getEffects()
  {
    return CollectionsUtil.wrap( _effects );
  }

  @Nonnull
  public AudioContext getActiveAudioContext()
  {
    if ( "suspended".equals( _audioContext.state ) )
    {
      _audioContext.resume();
    }
    return _audioContext;
  }

  @Nonnull
  final ReactCache.Resource<String, AudioBuffer> getAudioCache()
  {
    return _audioCache;
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

  @Nonnull
  private Promise<AudioBuffer> loadAudioData( @Nonnull final String sound )
  {
    return DomGlobal
      .fetch( sound )
      .then( Response::arrayBuffer )
      .then( _audioContext::decodeAudioData );
  }
}
