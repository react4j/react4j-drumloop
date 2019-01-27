package react4j.drumloop.dagger;

import dagger.Component;
import javax.annotation.Nonnull;
import javax.inject.Singleton;
import react4j.drumloop.model.DrumMachineDaggerModule;
import react4j.drumloop.views.DrumLoopViewDaggerComponentExtension;
import react4j.drumloop.views.IndicatorViewDaggerComponentExtension;

@Singleton
@Component( modules = DrumMachineDaggerModule.class )
public interface DrumLoopComponent
  extends DrumLoopViewDaggerComponentExtension,
          IndicatorViewDaggerComponentExtension
{
  @Nonnull
  static DrumLoopComponent create()
  {
    final DrumLoopComponent component = DaggerDrumLoopComponent.create();
    component.bindDrumLoopView();
    component.bindIndicatorView();
    return component;
  }
}
