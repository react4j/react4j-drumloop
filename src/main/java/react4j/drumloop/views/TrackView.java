package react4j.drumloop.views;

import java.util.Objects;
import javax.annotation.Nonnull;
import react4j.ReactNode;
import react4j.annotations.Input;
import react4j.annotations.Render;
import react4j.annotations.View;
import react4j.dom.proptypes.html.HtmlProps;
import react4j.drumloop.model.Track;
import static react4j.dom.DOM.*;

@View
public abstract class TrackView
{
  @Nonnull
  final Track _track;

  TrackView( @Input( immutable = true ) @Nonnull final Track track )
  {
    _track = Objects.requireNonNull( track );
  }

  @Render
  @Nonnull
  ReactNode render()
  {
    _track.suspendUntilAudioLoaded();
    return div( new HtmlProps().className( "track" ),
                div( new HtmlProps().className( "track_info" ),
                     h2( new HtmlProps().className( "track_title" ), _track.getName() )
                ),
                div( new HtmlProps().className( "step_row" ),
                     fragment( _track.getStepCells().stream().map( StepButtonBuilder::cell ) )
                )
    );
  }
}
