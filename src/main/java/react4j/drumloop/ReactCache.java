package react4j.drumloop;

import com.google.gwt.core.client.JavaScriptException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import react4j.drumloop.browser.promise.Promise;

public final class ReactCache<InputType, OutputType>
{
  @Nonnull
  private final Function<InputType, Promise<OutputType>> _loader;
  @Nonnull
  private final Map<InputType, Entry<OutputType>> _entries = new HashMap<>();

  public ReactCache( @Nonnull final Function<InputType, Promise<OutputType>> loader )
  {
    _loader = Objects.requireNonNull( loader );
  }

  public void preload( @Nonnull final InputType input )
  {
    getEntry( input );
  }

  public OutputType read( @Nonnull final InputType input )
  {
    final Entry<OutputType> entry = getEntry( input );
    if ( State.PENDING == entry._state )
    {
      // GWT unwraps this exception so React receives the original promise.
      throw new JavaScriptException( entry._promise );
    }
    else if ( State.REJECTED == entry._state )
    {
      throw new JavaScriptException( entry._error );
    }
    else
    {
      return entry._value;
    }
  }

  @Nonnull
  private Entry<OutputType> getEntry( @Nonnull final InputType input )
  {
    Entry<OutputType> entry = _entries.get( input );
    if ( null == entry )
    {
      entry = new Entry<>( _loader.apply( input ) );
      _entries.put( input, entry );
    }
    return entry;
  }

  private enum State
  {
    PENDING, RESOLVED, REJECTED
  }

  private static final class Entry<OutputType>
  {
    @Nonnull
    private final Promise<OutputType> _promise;
    @Nonnull
    private State _state = State.PENDING;
    @Nullable
    private OutputType _value;
    @Nullable
    private Object _error;

    private Entry( @Nonnull final Promise<OutputType> promise )
    {
      _promise = Objects.requireNonNull( promise );
      _promise.thenAccept( value -> {
        if ( State.PENDING == _state )
        {
          _value = value;
          _state = State.RESOLVED;
        }
      }, error -> {
        if ( State.PENDING == _state )
        {
          _error = error;
          _state = State.REJECTED;
        }
      } );
    }
  }
}
