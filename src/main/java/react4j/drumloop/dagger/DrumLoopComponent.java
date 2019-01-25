package react4j.drumloop.dagger;

import dagger.Component;
import javax.annotation.Nonnull;
import javax.inject.Singleton;

@Singleton
@Component
public interface DrumLoopComponent
{
  @Nonnull
  static DrumLoopComponent create()
  {
    return DaggerDrumLoopComponent.create();
  }
}
