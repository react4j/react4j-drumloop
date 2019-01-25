package react4j.drumloop.views;

import arez.annotations.Action;
import arez.annotations.Observable;
import arez.annotations.PostConstruct;
import elemental2.dom.HTMLInputElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import react4j.arez.ReactArezComponent;
import react4j.dom.events.FormEvent;
import react4j.dom.proptypes.html.BtnProps;
import react4j.dom.proptypes.html.HtmlProps;
import react4j.dom.proptypes.html.InputProps;
import react4j.dom.proptypes.html.attributeTypes.InputType;
import static react4j.dom.DOM.*;

@ReactComponent
public abstract class DrumLoopView
  extends ReactArezComponent
{
  @PostConstruct
  final void postConstruct()
  {
    setBpm( 65 );
  }

  @Observable( writeOutsideTransaction = true )
  abstract int bpm();

  abstract void setBpm( int bpm );

  private void onBpmChange( @Nonnull final FormEvent e )
  {
    setBpm( Integer.parseInt( ( (HTMLInputElement) e.getTarget() ).value ) );
  }

  @Observable
  abstract boolean on();

  abstract void setOn( boolean on );

  @Action
  void toggleOn()
  {
    setOn( !on() );
  }

  @Nullable
  @Override
  protected ReactNode render()
  {
    final boolean on = on();
    return div( new HtmlProps().className( "container" ),
                div( new HtmlProps().className( "header" ),
                     h1( new HtmlProps().className( "logo" ), "Trap Lord 9000" ),
                     input( new InputProps()
                              .className( "bpmInput" )
                              .type( InputType.number )
                              .value( String.valueOf( bpm() ) )
                              .min( "60" )
                              .max( "180" )
                              .onChange( this::onBpmChange ) ),
                     button( new BtnProps()
                               .className( "startButton", on ? null : "startButton_off" )
                               .onClick( e -> toggleOn() ),
                             on ? "Stop" : "Play"
                     )
                ),
                div( new HtmlProps().className( "buttonContainer" ),
                     FxButtonBuilder.title( "Turn Up (F)" ).sound( "sounds/loop.wav" ),
                     FxButtonBuilder.title( "SQUAD (Am)" ).sound( "sounds/loop130.wav" ),
                     FxButtonBuilder.title( "Hey" ).sound( "sounds/hey.wav" ),
                     FxButtonBuilder.title( "Yeah" ).sound( "sounds/yeah.wav" )
                ) );
  }
}
