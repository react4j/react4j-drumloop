package react4j.drumloop.views;

import arez.annotations.Action;
import arez.annotations.Observable;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import react4j.Component;
import react4j.ReactNode;
import react4j.annotations.Prop;
import react4j.annotations.ReactComponent;
import react4j.dom.proptypes.html.BtnProps;
import react4j.drumloop.model.SoundEffect;
import static react4j.dom.DOM.*;

@ReactComponent( type = ReactComponent.Type.TRACKING )
abstract class FxButton
  extends Component
{
  @Prop( immutable = true )
  @Nonnull
  abstract SoundEffect sound();

  @Observable()
  abstract boolean held();

  abstract void setHeld( boolean held );

  @Nullable
  @Override
  protected ReactNode render()
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
      sound().stop();
    }
    else
    {
      final SoundEffect sound = sound();
      if ( isShiftKeyDown )
      {
        sound.loop();
        setHeld( true );
      }
      else
      {
        sound.play();
      }
    }
  }

  @Action
  void stopSound()
  {
    if ( !held() )
    {
      sound().stop();
    }
  }
}
