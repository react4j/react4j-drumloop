package react4j.drumloop.views;

import javax.annotation.Nullable;
import javax.inject.Inject;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import react4j.arez.ReactArezComponent;
import react4j.dom.proptypes.html.CssProps;
import react4j.dom.proptypes.html.HtmlProps;
import react4j.drumloop.model.DrumMachine;
import static react4j.dom.DOM.*;

@ReactComponent
public abstract class IndicatorView
  extends ReactArezComponent
{
  @Inject
  DrumMachine _drumMachine;

  @Nullable
  @Override
  protected ReactNode render()
  {
    final boolean on = _drumMachine.isRunning();
    final int step = _drumMachine.getCurrentStep();

    return div( new HtmlProps().className( "indicatorContainer" ),
                on ?
                div( new HtmlProps()
                       .className( "indicator" )
                       .style( new CssProps().left( ( step * 37.5 ) + "px" ) ) ) :
                null
    );
  }
}
