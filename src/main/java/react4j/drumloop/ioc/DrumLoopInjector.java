package react4j.drumloop.ioc;

import javax.annotation.Nonnull;
import react4j.drumloop.model.ModelFragment;
import react4j.drumloop.views.ViewsFragment;
import sting.Injector;

@SuppressWarnings( "UnusedReturnValue" )
@Injector( includes = { ModelFragment.class, ViewsFragment.class } )
public interface DrumLoopInjector
{
  @Nonnull
  static DrumLoopInjector create()
  {
    return new Sting_DrumLoopInjector();
  }
}
