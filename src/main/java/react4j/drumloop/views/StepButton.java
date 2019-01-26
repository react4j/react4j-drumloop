package react4j.drumloop.views;

import arez.annotations.Action;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import react4j.ReactNode;
import react4j.annotations.Prop;
import react4j.annotations.ReactComponent;
import react4j.arez.ReactArezComponent;
import react4j.dom.proptypes.html.BtnProps;
import react4j.drumloop.model.StepCell;
import static react4j.dom.DOM.*;

@ReactComponent
public abstract class StepButton
  extends ReactArezComponent
{
  @Prop
  @Nonnull
  abstract StepCell cell();

  @Nullable
  @Override
  protected ReactNode render()
  {
    final StepCell cell = cell();
    final boolean oddBar = ( cell.beat() / 4 ) % 2 == 1;
    return button( new BtnProps()
                     .className( "step_button",
                                 oddBar ? "step_button_odd" : null,
                                 cell.on() ? "step_button_on" : null,
                                 cell.doubled() ? "step_button_doubled" : null )
                     .onClick( e -> toggleCell( e.isShiftKey() ) ) );
  }

  @Action
  void toggleCell( final boolean shiftKey )
  {
    final StepCell cell = cell();
    final boolean on = cell.on();
    if ( on )
    {
      if ( shiftKey && !cell.doubled() )
      {
        cell.setDoubled();
      }
      else
      {
        cell.setOff();
      }
    }
    else
    {
      if ( shiftKey )
      {
        cell.setDoubled();
      }
      else
      {
        cell.setOn();
      }
    }
  }
}
