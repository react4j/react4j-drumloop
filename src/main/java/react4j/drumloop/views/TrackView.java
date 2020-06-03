package react4j.drumloop.views;

import javax.annotation.Nonnull;
import react4j.ReactNode;
import react4j.annotations.Prop;
import react4j.annotations.ReactComponent;
import react4j.annotations.Render;
import react4j.dom.proptypes.html.HtmlProps;
import react4j.drumloop.model.Track;
import static react4j.dom.DOM.*;

@ReactComponent
public abstract class TrackView
{
  @Prop( immutable = true )
  @Nonnull
  abstract Track track();

  @Render
  @Nonnull
  ReactNode render()
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
