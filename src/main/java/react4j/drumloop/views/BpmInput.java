package react4j.drumloop.views;

import elemental2.dom.HTMLInputElement;
import java.util.Objects;
import javax.annotation.Nonnull;
import react4j.ReactNode;
import react4j.annotations.Render;
import react4j.annotations.View;
import react4j.dom.events.FormEvent;
import react4j.dom.proptypes.html.InputProps;
import react4j.dom.proptypes.html.attributeTypes.InputType;
import react4j.drumloop.model.DrumMachine;
import static react4j.dom.DOM.*;

@View( type = View.Type.TRACKING )
public abstract class BpmInput
{
  @Nonnull
  private final DrumMachine _drumMachine;

  BpmInput( @Nonnull final DrumMachine drumMachine )
  {
    _drumMachine = Objects.requireNonNull( drumMachine );
  }

  private void onBpmChange( @Nonnull final FormEvent e )
  {
    _drumMachine.setBpm( Integer.parseInt( ( (HTMLInputElement) e.getTarget() ).value ) );
  }

  @Render
  @Nonnull
  ReactNode render()
  {
    return input( new InputProps()
                    .className( "bpmInput" )
                    .type( InputType.number )
                    .value( String.valueOf( _drumMachine.getBpm() ) )
                    .min( "60" )
                    .max( "180" )
                    .onChange( this::onBpmChange ) );
  }
}
