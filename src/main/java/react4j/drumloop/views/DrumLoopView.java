package react4j.drumloop.views;

import javax.annotation.Nullable;
import react4j.Component;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import static react4j.dom.DOM.*;

@ReactComponent
public abstract class DrumLoopView
  extends Component
{
  @Nullable
  @Override
  protected ReactNode render()
  {
    return div( "Hello World" );
  }
}
