package react4j.drumloop.views;

import java.util.Objects;
import javax.annotation.Nonnull;
import react4j.ReactNode;
import react4j.annotations.Render;
import react4j.annotations.View;
import react4j.dom.proptypes.html.BtnProps;
import react4j.drumloop.model.DrumMachine;
import static react4j.dom.DOM.*;

@View( type = View.Type.TRACKING )
abstract class PlayButton
{
  @Nonnull
  private final DrumMachine _drumMachine;

  PlayButton( @Nonnull final DrumMachine drumMachine )
  {
    _drumMachine = Objects.requireNonNull( drumMachine );
  }

  @Render
  @Nonnull
  ReactNode render()
  {
    final boolean on = _drumMachine.isRunning();
    return button( new BtnProps()
                     .className( "startButton", on ? null : "startButton_off" )
                     .onClick( e -> _drumMachine.toggleRunning() ),
                   on ? "Stop" : "Play" );
  }
}
