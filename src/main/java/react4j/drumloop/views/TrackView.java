package react4j.drumloop.views;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import react4j.Component;
import react4j.ReactNode;
import react4j.annotations.Prop;
import react4j.annotations.ReactComponent;
import react4j.dom.proptypes.html.HtmlProps;
import react4j.drumloop.model.Track;
import static react4j.dom.DOM.*;

@ReactComponent
public abstract class TrackView
  extends Component
{
  @Prop
  @Nonnull
  abstract Track track();

  @Nullable
  @Override
  protected ReactNode render()
  {
    final Track track = track();
    track.suspendUntilAudioLoaded();
    return div( new HtmlProps().className( "track" ),
                div( new HtmlProps().className( "track_info" ),
                     h2( new HtmlProps().className( "track_title" ), track.getName() ) ),
                div( new HtmlProps().className( "step_row" ),
                     fragment( track.getStepCells().stream().map( StepButtonBuilder::cell ) )
                )
    );
  }
}
