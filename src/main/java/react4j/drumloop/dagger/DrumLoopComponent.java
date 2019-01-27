package react4j.drumloop.dagger;

import dagger.Component;
import javax.annotation.Nonnull;
import javax.inject.Singleton;
import react4j.drumloop.model.DrumMachineDaggerModule;
import react4j.drumloop.views.BpmInputDaggerComponentExtension;
import react4j.drumloop.views.DrumMachineViewDaggerComponentExtension;
import react4j.drumloop.views.IndicatorViewDaggerComponentExtension;
import react4j.drumloop.views.PlayButtonDaggerComponentExtension;

@Singleton
@Component( modules = DrumMachineDaggerModule.class )
public interface DrumLoopComponent
  extends BpmInputDaggerComponentExtension,
          DrumMachineViewDaggerComponentExtension,
          IndicatorViewDaggerComponentExtension,
          PlayButtonDaggerComponentExtension
{
  @Nonnull
  static DrumLoopComponent create()
  {
    final DrumLoopComponent component = DaggerDrumLoopComponent.create();
    component.bindBpmInput();
    component.bindDrumMachineView();
    component.bindIndicatorView();
    component.bindPlayButton();
    return component;
  }
}
