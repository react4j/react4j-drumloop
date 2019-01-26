package react4j.drumloop.dagger;

import dagger.Component;
import javax.annotation.Nonnull;
import javax.inject.Singleton;
import react4j.drumloop.views.DrumLoopViewDaggerComponentExtension;

@Singleton
@Component
public interface DrumLoopComponent
  extends DrumLoopViewDaggerComponentExtension
{
  @Nonnull
  static DrumLoopComponent create()
  {
    final DrumLoopComponent component = DaggerDrumLoopComponent.create();
    component.bindDrumLoopView();
    return component;
  }
}
