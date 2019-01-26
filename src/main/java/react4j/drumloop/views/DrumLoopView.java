package react4j.drumloop.views;

import elemental2.dom.HTMLInputElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import react4j.arez.ReactArezComponent;
import react4j.dom.events.FormEvent;
import react4j.dom.proptypes.html.BtnProps;
import react4j.dom.proptypes.html.CssProps;
import react4j.dom.proptypes.html.HtmlProps;
import react4j.dom.proptypes.html.InputProps;
import react4j.dom.proptypes.html.attributeTypes.InputType;
import react4j.drumloop.model.DrumMachine;
import react4j.drumloop.model.Track;
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
    final boolean on = _drumMachine.isRunning();
    final int step = 0;
    return div( new HtmlProps().className( "container" ),
                renderHeader( on ),
                // <React.Suspense fallback={<p>loading</p>}>
                div( new HtmlProps().className( "stepSequencer" ),
                     renderIndicator( on, step ),
                     fragment( _drumMachine.getTracks().stream().map( this::renderTrack ) )
                ),
                div( new HtmlProps().className( "buttonContainer" ),
                     FxButtonBuilder.title( "Turn Up (F)" ).sound( "sounds/loop.wav" ),
                     FxButtonBuilder.title( "SQUAD (Am)" ).sound( "sounds/loop130.wav" ),
                     FxButtonBuilder.title( "Hey" ).sound( "sounds/hey.wav" ),
                     FxButtonBuilder.title( "Yeah" ).sound( "sounds/yeah.wav" )
                )
                // </React.Suspense>
    );
  }

  @Nonnull
  private ReactNode renderTrack( @Nonnull final Track track )
  {
    return div( new HtmlProps().className( "track" ),
                div( new HtmlProps().className( "track_info" ),
                     h2( new HtmlProps().className( "track_title" ), track.getName() ) ),
                div( new HtmlProps().className( "step_row" ),
                     fragment( track.getStepCells().stream().map( StepButtonBuilder::cell ) )
                )
    );
  }

  @Nonnull
  private ReactNode renderHeader( final boolean on )
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
                button( new BtnProps()
                          .className( "startButton", on ? null : "startButton_off" )
                          .onClick( e -> _drumMachine.toggleRunning() ),
                        on ? "Stop" : "Play"
                )
    );
  }

  @Nonnull
  private ReactNode renderIndicator( final boolean on, final int step )
  {
    return div( new HtmlProps().className( "indicatorContainer" ),
                on ?
                div( new HtmlProps()
                       .className( "indicator" )
                       .style( new CssProps().left( ( step * 37.5 ) + "px" ) ) ) :
                null
    );
  }
}
