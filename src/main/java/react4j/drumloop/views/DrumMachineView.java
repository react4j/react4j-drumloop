package react4j.drumloop.views;

import java.util.Objects;
import javax.annotation.Nonnull;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import react4j.annotations.Render;
import react4j.dom.proptypes.html.HtmlProps;
import react4j.drumloop.model.DrumMachine;
import static react4j.dom.DOM.*;

@ReactComponent
public abstract class DrumMachineView
{
  @Nonnull
  private final DrumMachine _drumMachine;

  DrumMachineView( @Nonnull final DrumMachine drumMachine )
  {
    _drumMachine = Objects.requireNonNull( drumMachine );
  }

  @Render
  @Nonnull
  ReactNode render()
  {
    return div( new HtmlProps().className( "container" ),
                div( new HtmlProps().className( "header" ),
                     h1( new HtmlProps().className( "logo" ), "Trap Lord 9000" ),
                     BpmInputBuilder.build(),
                     PlayButtonBuilder.build()
                ),
                suspense( p( "Loading..." ),
                          4000,
                          fragment( div( new HtmlProps().className( "stepSequencer" ),
                                         IndicatorViewBuilder.build(),
                                         fragment( _drumMachine.getTracks()
                                                     .stream()
                                                     .map( TrackViewBuilder::track ) )
                                    ),
                                    div( new HtmlProps().className( "buttonContainer" ),
                                         fragment( _drumMachine.getEffects()
                                                     .stream()
                                                     .map( FxButtonBuilder::sound ) )
                                    )
                          )
                )
    );
  }
}
