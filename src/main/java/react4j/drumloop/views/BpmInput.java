package react4j.drumloop.views;

import elemental2.dom.HTMLInputElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import react4j.arez.ReactArezComponent;
import react4j.dom.events.FormEvent;
import react4j.dom.proptypes.html.InputProps;
import react4j.dom.proptypes.html.attributeTypes.InputType;
import react4j.drumloop.model.DrumMachine;
import static react4j.dom.DOM.*;

@ReactComponent
public abstract class BpmInput
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
    return input( new InputProps()
                    .className( "bpmInput" )
                    .type( InputType.number )
                    .value( String.valueOf( _drumMachine.getBpm() ) )
                    .min( "60" )
                    .max( "180" )
                    .onChange( this::onBpmChange ) );
  }
}
