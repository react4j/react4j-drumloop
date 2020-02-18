package react4j.drumloop.model;

import arez.Arez;
import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.Feature;
import arez.annotations.Observable;
import elemental2.dom.DomGlobal;
import elemental2.dom.Response;
import elemental2.media.AudioBuffer;
import elemental2.media.AudioBufferSourceNode;
import elemental2.media.AudioContext;
import elemental2.media.GainNode;
import elemental2.promise.Promise;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import react4j.drumloop.ReactCache;

@ArezComponent( service = Feature.ENABLE )
public abstract class DrumMachine
{
  private static final int INITIAL_BPM = 65;
  private static final int BAR_COUNT = 4;
  private static final int BEATS_PER_BAR = 4;
  private static final int MAX_STEPS = BAR_COUNT * BEATS_PER_BAR;
  @Nonnull
  private final AudioContext _audioContext;
  @Nonnull
  private final ArrayList<Track> _tracks = new ArrayList<>();
  @Nonnull
  private final ArrayList<SoundEffect> _effects = new ArrayList<>();
  private int _bpm = INITIAL_BPM;
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
    return Arez.areCollectionsPropertiesUnmodifiable() ? Collections.unmodifiableList( _tracks ) : _tracks;
  }

  @Nonnull
  public List<SoundEffect> getEffects()
  {
    return Arez.areCollectionsPropertiesUnmodifiable() ? Collections.unmodifiableList( _effects ) : _effects;
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

  @Observable( writeOutsideTransaction = Feature.ENABLE )
  public int getBpm()
  {
    return _bpm;
  }

  public void setBpm( final int bpm )
  {
    _bpm = bpm;
  }

  @Observable( writeOutsideTransaction = Feature.ENABLE )
  public abstract int getCurrentStep();

  abstract void setCurrentStep( int currentStep );

  @Observable
  public abstract boolean isRunning();

  public abstract void setRunning( boolean running );

  @Action
  public void toggleRunning()
  {
    final boolean running = !isRunning();
    setRunning( running );
    setCurrentStep( MAX_STEPS - 1 );
    if ( running )
    {
      playBeat();
    }
  }

  private void playSoundAt( @Nonnull final AudioBuffer buffer )
  {
    final GainNode gain = _audioContext.createGain();
    gain.gain.value = 0.2;
    gain.connect( _audioContext.destination );
    final AudioBufferSourceNode node = _audioContext.createBufferSource();
    node.buffer = buffer;
    node.connect( gain );
    node.start();
  }

  @Action
  void playBeat()
  {
    if ( isRunning() )
    {
      final int nextStep = ( getCurrentStep() + 1 ) % MAX_STEPS;

      for ( final Track track : getTracks() )
      {
        final StepCell cell = track.getStepCells().get( nextStep );
        if ( cell.on() )
        {
          if ( cell.doubled() )
          {
            playSoundAt( track.getAudioBuffer() );
            DomGlobal.setTimeout( v -> playSoundAt( track.getAudioBuffer() ), 100 );
          }
          else
          {
            playSoundAt( track.getAudioBuffer() );
          }
        }
      }

      setCurrentStep( nextStep );
      DomGlobal.setTimeout( v -> playBeat(), 60.0 / _bpm * 1000.0 );
    }
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
