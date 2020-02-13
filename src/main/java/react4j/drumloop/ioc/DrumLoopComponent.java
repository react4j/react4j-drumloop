package react4j.drumloop.ioc;

import dagger.Component;
import javax.annotation.Nonnull;
import javax.inject.Singleton;
import react4j.drumloop.model.DrumMachineDaggerModule;
import react4j.drumloop.views.BpmInputFactory;
import react4j.drumloop.views.DrumMachineViewFactory;
import react4j.drumloop.views.IndicatorViewFactory;
import react4j.drumloop.views.PlayButtonFactory;

@SuppressWarnings( "UnusedReturnValue" )
@Singleton
@Component( modules = DrumMachineDaggerModule.class )
public interface DrumLoopComponent
{
  BpmInputFactory bpmInputFactory();

  DrumMachineViewFactory drumMachineViewFactory();

  IndicatorViewFactory indicatorViewFactory();

  PlayButtonFactory playButtonFactory();

  @Nonnull
  static DrumLoopComponent create()
  {
    final DrumLoopComponent component = DaggerDrumLoopComponent.create();
    // These calls make sure the components are created and bound to the react factories
    component.bpmInputFactory();
    component.drumMachineViewFactory();
    component.indicatorViewFactory();
    component.playButtonFactory();
    return component;
  }
}
