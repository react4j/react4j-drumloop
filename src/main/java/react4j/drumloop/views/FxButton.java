package react4j.drumloop.views;

import arez.annotations.Action;
import arez.annotations.Observable;
import arez.annotations.PreDispose;
import elemental2.media.AudioBufferSourceNode;
import elemental2.media.AudioContext;
import elemental2.media.GainNode;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import react4j.ReactNode;
import react4j.annotations.Input;
import react4j.annotations.Render;
import react4j.annotations.View;
import react4j.dom.proptypes.html.BtnProps;
import react4j.drumloop.model.SoundEffect;
import static react4j.dom.DOM.*;

@View( type = View.Type.TRACKING )
abstract class FxButton
{
  @Nullable
  private AudioBufferSourceNode _node;

  @Input( immutable = true )
  @Nonnull
  abstract SoundEffect sound();

  @Observable()
  abstract boolean held();

  abstract void setHeld( boolean held );

  @PreDispose
  void preDispose()
  {
    stop();
  }

  @Render
  @Nonnull
  ReactNode render()
  {
    final SoundEffect sound = sound();
    sound.suspendUntilAudioLoaded();
    return button( new BtnProps()
                     .className( "button", held() ? "button_held" : null )
                     .onMouseDown( e -> playSound( e.isShiftKey() ) )
                     .onTouchStart( e -> playSound( e.isShiftKey() ) )
                     .onTouchEnd( e -> stopSound() )
                     .onMouseUp( e -> stopSound() ),
                   sound.getName() );
  }

  @Action
  void playSound( final boolean isShiftKeyDown )
  {
    if ( held() )
    {
      setHeld( false );
      stop();
    }
    else
    {
      if ( isShiftKeyDown )
      {
        loop();
        setHeld( true );
      }
      else
      {
        play();
      }
    }
  }

  @Action
  void stopSound()
  {
    if ( !held() )
    {
      stop();
    }
  }

  @Nonnull
  private AudioBufferSourceNode newAudioNode()
  {
    final SoundEffect sound = sound();
    final AudioContext audioContext = sound.getDrumMachine().getActiveAudioContext();
    final AudioBufferSourceNode node = audioContext.createBufferSource();
    node.buffer = sound.getAudioBuffer();
    final GainNode gain = audioContext.createGain();
    node.connect( gain );
    gain.gain.value = 0.2;
    gain.connect( audioContext.destination );
    return node;
  }

  private void play()
  {
    if ( null != _node )
    {
      stop();
    }
    start( false );
  }

  private void start( final boolean loop )
  {
    final AudioBufferSourceNode node = newAudioNode();
    node.loop = loop;
    node.onended = e -> stopUnlessLooping( node );
    node.start( 0 );
    _node = node;
  }

  private void stopUnlessLooping( @Nonnull final AudioBufferSourceNode node )
  {
    if ( !node.loop )
    {
      stop();
    }
  }

  private void loop()
  {
    if ( null != _node )
    {
      _node.loop = true;
    }
    else
    {
      start( true );
    }
  }

  private void stop()
  {
    if ( null != _node )
    {
      _node.stop();
      _node.disconnect();
      _node = null;
    }
  }
}
