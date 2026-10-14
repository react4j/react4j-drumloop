package react4j.drumloop;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import java.util.function.Function;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;
import react4j.drumloop.browser.promise.AcceptCallback;
import react4j.drumloop.browser.promise.Promise;

public final class ReactCacheTest
  implements EntryPoint
{
  private final StringBuilder _results = new StringBuilder();
  private int _passed;

  @Override
  public void onModuleLoad()
  {
    run( "pending reads throw the original promise and share one load", () -> {
      final Deferred deferred = new Deferred();
      final ReactCache<String, String> resource = new ReactCache<>( deferred );
      check( catchThrown( () -> resource.read( "sound" ) ) == deferred.promise(), "Original promise" );
      check( catchThrown( () -> resource.read( "sound" ) ) == deferred.promise(), "Same pending promise" );
      check( 1 == deferred._loads, "One load" );
      deferred._resolve.onInvoke( "buffer" );
      check( "buffer".equals( resource.read( "sound" ) ), "Resolved value" );
      check( 1 == deferred._loads, "No reload after resolution" );
    } );
    run( "preload starts once without suspending and shares its result with read", () -> {
      final Deferred deferred = new Deferred();
      final ReactCache<String, String> resource = new ReactCache<>( deferred );
      resource.preload( "sound" );
      resource.preload( "sound" );
      check( catchThrown( () -> resource.read( "sound" ) ) == deferred.promise(), "Preloaded promise" );
      check( 1 == deferred._loads, "One preload" );
      deferred._resolve.onInvoke( "buffer" );
      resource.preload( "sound" );
      check( "buffer".equals( resource.read( "sound" ) ), "Preloaded value" );
      check( 1 == deferred._loads, "No reload after preloading" );
    } );
    run( "rejections preserve the original error and do not reload", () -> {
      final Deferred deferred = new Deferred();
      final ReactCache<String, String> resource = new ReactCache<>( deferred );
      final Object error = JsPropertyMap.of();
      resource.preload( "sound" );
      deferred._reject.onInvoke( error );
      check( catchThrown( () -> resource.read( "sound" ) ) == error, "Original error" );
      resource.preload( "sound" );
      check( catchThrown( () -> resource.read( "sound" ) ) == error, "Cached error" );
      check( 1 == deferred._loads, "No retry" );
    } );
    run( "null rejections are distinct from pending entries", () -> {
      final Deferred deferred = new Deferred();
      final ReactCache<String, String> resource = new ReactCache<>( deferred );
      resource.preload( "sound" );
      deferred._reject.onInvoke( null );
      check( null == catchThrown( () -> resource.read( "sound" ) ), "Null rejection" );
    } );
    run( "a thenable can only settle successfully once", () -> {
      final Deferred deferred = new Deferred();
      final ReactCache<String, String> resource = new ReactCache<>( deferred );
      resource.preload( "sound" );
      deferred._resolve.onInvoke( "first" );
      deferred._resolve.onInvoke( "second" );
      deferred._reject.onInvoke( "error" );
      check( "first".equals( resource.read( "sound" ) ), "First resolution wins" );
    } );
    run( "a rejection cannot be overwritten by a later settlement", () -> {
      final Deferred deferred = new Deferred();
      final ReactCache<String, String> resource = new ReactCache<>( deferred );
      resource.preload( "sound" );
      deferred._reject.onInvoke( "first" );
      deferred._resolve.onInvoke( "buffer" );
      deferred._reject.onInvoke( "second" );
      check( "first".equals( catchThrown( () -> resource.read( "sound" ) ) ), "First rejection wins" );
    } );
    run( "synchronously resolved thenables are immediately readable", () -> {
      final Thenable thenable = newThenable();
      thenable.then = ( resolve, reject ) -> resolve.onInvoke( "buffer" );
      final ReactCache<String, String> resource =
        new ReactCache<>( key -> Js.uncheckedCast( thenable ) );
      check( "buffer".equals( resource.read( "sound" ) ), "Synchronous result" );
    } );
    run( "synchronously rejected thenables preserve their error", () -> {
      final Thenable thenable = newThenable();
      thenable.then = ( resolve, reject ) -> reject.onInvoke( "error" );
      final ReactCache<String, String> resource =
        new ReactCache<>( key -> Js.uncheckedCast( thenable ) );
      check( "error".equals( catchThrown( () -> resource.read( "sound" ) ) ), "Synchronous error" );
    } );
    run( "different keys load independently", () -> {
      final Deferred first = new Deferred();
      final Deferred second = new Deferred();
      final ReactCache<String, String> resource =
        new ReactCache<>( key -> "first".equals( key ) ? first.apply( key ) : second.apply( key ) );
      resource.preload( "first" );
      resource.preload( "second" );
      first._resolve.onInvoke( "one" );
      check( "one".equals( resource.read( "first" ) ), "First key" );
      check( catchThrown( () -> resource.read( "second" ) ) == second.promise(), "Second key still pending" );
    } );
    run( "resources do not share entries", () -> {
      final Deferred first = new Deferred();
      final Deferred second = new Deferred();
      new ReactCache<>( first ).preload( "sound" );
      new ReactCache<>( second ).preload( "sound" );
      check( 1 == first._loads && 1 == second._loads, "Independent resources" );
    } );
    final Promise<String> resolved = resolve( "buffer" );
    final Promise<String> rejected = reject( "error" );
    final ReactCache<String, String> success = new ReactCache<>( key -> resolved );
    final ReactCache<String, String> failure = new ReactCache<>( key -> rejected );
    success.preload( "sound" );
    failure.preload( "sound" );
    run( "native promises suspend until their callbacks run", () -> {
      check( catchThrown( () -> success.read( "sound" ) ) == resolved, "Native pending promise" );
      check( catchThrown( () -> failure.read( "sound" ) ) == rejected, "Native rejected promise before callback" );
    } );
    Scheduler.get().scheduleDeferred( () -> {
      run( "native promises resolve asynchronously", () -> {
        check( "buffer".equals( success.read( "sound" ) ), "Native resolved value" );
      } );
      run( "native promise rejections are cached asynchronously", () -> {
        check( "error".equals( catchThrown( () -> failure.read( "sound" ) ) ), "Native rejection" );
      } );
      _results.append( "\nPassed " ).append( _passed ).append( " cache tests\n" );
      showResults();
    } );
  }

  private void run( final String name, final Runnable test )
  {
    try
    {
      test.run();
      _passed++;
      _results.append( "PASS: " ).append( name ).append( '\n' );
      showResults();
    }
    catch ( final Throwable error )
    {
      _results.append( "FAIL: " ).append( name ).append( ": " ).append( error ).append( '\n' );
      showResults();
      throw error;
    }
  }

  private void showResults()
  {
    Document.get().getElementById( "results" ).setInnerText( _results.toString() );
  }

  private static void check( final boolean condition, final String message )
  {
    if ( !condition )
    {
      throw new AssertionError( message );
    }
  }

  private static native Object catchThrown( ReadFn read ) /*-{
    try {
      read();
    } catch (error) {
      return error;
    }
    throw new Error("Expected read to throw");
  }-*/;

  private static native Thenable newThenable() /*-{
    return new $wnd.Promise(function () {});
  }-*/;

  @JsMethod( namespace = "Promise", name = "resolve" )
  private static native Promise<String> resolve( String value );

  @JsMethod( namespace = "Promise", name = "reject" )
  private static native Promise<String> reject( String error );

  @JsFunction
  private interface ReadFn
  {
    Object read();
  }

  @JsFunction
  private interface ThenFn
  {
    void then( AcceptCallback<String> resolve, AcceptCallback<Object> reject );
  }

  @JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "Object" )
  private static final class Thenable
  {
    public ThenFn then;

    private Thenable()
    {
    }
  }

  private static final class Deferred
    implements Function<String, Promise<String>>
  {
    private final Thenable _thenable = newThenable();
    private AcceptCallback<String> _resolve;
    private AcceptCallback<Object> _reject;
    private int _loads;

    private Deferred()
    {
      _thenable.then = ( onFulfilled, onRejected ) -> {
        _resolve = onFulfilled;
        _reject = onRejected;
      };
    }

    @Override
    public Promise<String> apply( final String input )
    {
      _loads++;
      return promise();
    }

    private Promise<String> promise()
    {
      return Js.uncheckedCast( _thenable );
    }
  }
}
