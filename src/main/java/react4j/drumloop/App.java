package react4j.drumloop;

import com.google.gwt.core.client.EntryPoint;
import elemental2.dom.DomGlobal;
import react4j.ReactElement;
import react4j.dom.ReactDOM;
import react4j.drumloop.ioc.DrumLoopComponent;
import react4j.drumloop.views.DrumMachineViewBuilder;

public class App
  implements EntryPoint
{
  @Override
  public void onModuleLoad()
  {
    // Uncomment this line for more detailed event logging
    //ReactArezSpyUtil.enableSpyEventLogging();

    // TODO: Having to add the next line feels super bad. Other frameworks (VueJS/Angular2+)
    // combine the next two steps with the instance render and injection setup combined
    DrumLoopComponent.create();
    ReactDOM
      .createRoot( DomGlobal.document.getElementById( "app" ) )
      .render( ReactElement.createStrictMode( DrumMachineViewBuilder.build() ), null );
  }
}
