package react4j.drumloop;

import akasha.Element;
import akasha.WindowGlobal;
import com.google.gwt.core.client.EntryPoint;
import react4j.ReactElement;
import react4j.dom.ReactDOM;
import react4j.drumloop.ioc.DrumLoopInjector;
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
    DrumLoopInjector.create();
    final Element element = WindowGlobal.document().getElementById( "app" );
    assert null != element;
    ReactDOM
      .createRoot( element )
      .render( ReactElement.createStrictMode( DrumMachineViewBuilder.build() ), null );
  }
}
