package react4j.drumloop.views;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import react4j.Component;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import react4j.dom.proptypes.html.BtnProps;
import react4j.drumloop.model.DrumMachine;
import static react4j.dom.DOM.*;

@ReactComponent( type = ReactComponent.Type.TRACKING )
abstract class PlayButton
  extends Component
{
  @Nonnull
  private final DrumMachine _drumMachine;

  PlayButton( @Nonnull final DrumMachine drumMachine )
  {
    _drumMachine = drumMachine;
  }

  @Nullable
  @Override
  protected ReactNode render()
  {
    final boolean on = _drumMachine.isRunning();
    return button( new BtnProps()
                     .className( "startButton", on ? null : "startButton_off" )
                     .onClick( e -> _drumMachine.toggleRunning() ),
                   on ? "Stop" : "Play" );
  }
}
