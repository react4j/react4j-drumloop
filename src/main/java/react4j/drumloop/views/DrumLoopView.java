package react4j.drumloop.views;

import elemental2.dom.HTMLInputElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import react4j.arez.ReactArezComponent;
import react4j.dom.events.FormEvent;
import react4j.dom.proptypes.html.HtmlProps;
import react4j.dom.proptypes.html.InputProps;
import react4j.dom.proptypes.html.attributeTypes.InputType;
import react4j.drumloop.model.DrumMachine;
import static react4j.dom.DOM.*;

@ReactComponent
public abstract class DrumLoopView
  extends ReactArezComponent
{
  @Inject
  DrumMachine _drumMachine;

  private void onBpmChange( @Nonnull final FormEvent e )
  {
    _drumMachine.setBpm( Integer.parseInt( ( (HTMLInputElement) e.getTarget() ).value ) );
  }

  @Nullable
  @Override
  protected ReactNode render()
  {
    return div( new HtmlProps().className( "container" ),
                renderHeader(),
                suspense( p( "Loading..." ),
                          4000,
                          fragment( div( new HtmlProps().className( "stepSequencer" ),
                                         IndicatorViewBuilder.build(),
                                         fragment( _drumMachine.getTracks()
                                                     .stream()
                                                     .map( t -> TrackViewBuilder.key( t.getName() ).track( t ) ) )
                                    ),
                                    div( new HtmlProps().className( "buttonContainer" ),
                                         fragment( _drumMachine.getEffects()
                                                     .stream()
                                                     .map( s -> FxButtonBuilder.key( s.getName() ).sound( s ) ) )
                                    )
                          )
                )
    );
  }

  @Nonnull
  private ReactNode renderHeader()
  {
    return div( new HtmlProps().className( "header" ),
                h1( new HtmlProps().className( "logo" ), "Trap Lord 9000" ),
                input( new InputProps()
                         .className( "bpmInput" )
                         .type( InputType.number )
                         .value( String.valueOf( _drumMachine.getBpm() ) )
                         .min( "60" )
                         .max( "180" )
                         .onChange( this::onBpmChange ) ),
                PlayButtonBuilder.build()
    );
  }
}
