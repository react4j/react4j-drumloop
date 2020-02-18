package react4j.drumloop.ioc;

import javax.annotation.Nonnull;
import react4j.drumloop.model.DrumMachine;
import react4j.drumloop.views.BpmInputFactory;
import react4j.drumloop.views.DrumMachineViewFactory;
import react4j.drumloop.views.IndicatorViewFactory;
import react4j.drumloop.views.PlayButtonFactory;
import sting.Injector;

@SuppressWarnings( "UnusedReturnValue" )
@Injector( includes = { DrumMachine.class,
                        BpmInputFactory.class,
                        DrumMachineViewFactory.class,
                        IndicatorViewFactory.class,
                        PlayButtonFactory.class } )
public interface DrumLoopInjector
{
  @Nonnull
  static DrumLoopInjector create()
  {
    return new Sting_DrumLoopInjector();
  }
}
