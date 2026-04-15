package react4j.drumloop.views;

import arez.annotations.Action;
import arez.annotations.ComponentDependency;
import java.util.Objects;
import javax.annotation.Nonnull;
import react4j.ReactNode;
import react4j.annotations.Input;
import react4j.annotations.Render;
import react4j.annotations.View;
import react4j.dom.proptypes.html.BtnProps;
import react4j.drumloop.model.StepCell;
import static react4j.dom.DOM.*;

@View( type = View.Type.TRACKING )
public abstract class StepButton
{
  @ComponentDependency
  @Nonnull
  final StepCell _cell;

  StepButton( @Input @Nonnull final StepCell cell )
  {
    _cell = Objects.requireNonNull( cell );
  }

  @Render
  @Nonnull
  ReactNode render()
  {
    final boolean oddBar = ( _cell.beat() / 4 ) % 2 == 1;
    return button( new BtnProps()
                     .className( "step_button",
                                 oddBar ? "step_button_odd" : null,
                                 _cell.on() ? "step_button_on" : null,
                                 _cell.doubled() ? "step_button_doubled" : null )
                     .onClick( e -> toggleCell( e.isShiftKey() ) ) );
  }

  @Action
  void toggleCell( final boolean shiftKey )
  {
    final boolean on = _cell.on();
    if ( on )
    {
      if ( shiftKey && !_cell.doubled() )
      {
        _cell.setDoubled();
      }
      else
      {
        _cell.setOff();
      }
    }
    else
    {
      if ( shiftKey )
      {
        _cell.setDoubled();
      }
      else
      {
        _cell.setOn();
      }
    }
  }
}
