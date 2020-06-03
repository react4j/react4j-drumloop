package react4j.drumloop.views;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import react4j.Component;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import react4j.dom.proptypes.html.CssProps;
import react4j.dom.proptypes.html.HtmlProps;
import react4j.drumloop.model.DrumMachine;
import static react4j.dom.DOM.*;

@ReactComponent( type = ReactComponent.Type.MAYBE_TRACKING )
public abstract class IndicatorView
  extends Component
{
  @Nonnull
  private final DrumMachine _drumMachine;

  IndicatorView( @Nonnull final DrumMachine drumMachine )
  {
    _drumMachine = Objects.requireNonNull( drumMachine );
  }

  @Nullable
  @Override
  protected ReactNode render()
  {
    final boolean on = _drumMachine.isRunning();
    final int step = _drumMachine.getCurrentStep();

    return div( new HtmlProps().className( "indicatorContainer" ),
                on ?
                div( new HtmlProps().className( "indicator" ).style( new CssProps().left( step * 37.5 + "px" ) ) ) :
                null
    );
  }
}
