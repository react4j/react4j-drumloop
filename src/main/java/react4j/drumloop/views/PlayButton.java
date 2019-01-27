package react4j.drumloop.views;

import javax.annotation.Nullable;
import javax.inject.Inject;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import react4j.arez.ReactArezComponent;
import react4j.dom.proptypes.html.BtnProps;
import react4j.drumloop.model.DrumMachine;
import static react4j.dom.DOM.*;

@ReactComponent
abstract class PlayButton
  extends ReactArezComponent
{
  @Inject
  DrumMachine _drumMachine;

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
