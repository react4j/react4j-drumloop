package react4j.drumloop;

import elemental2.promise.Promise;
import javaemul.internal.annotations.DoNotAutobox;
import javax.annotation.Nonnull;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "React" )
public final class ReactCache
{
  private ReactCache()
  {
  }

  @JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "Object" )
  public interface Resource<InputType, OutputType>
  {
    void preload( @Nonnull InputType input );

    OutputType read( @Nonnull InputType input );
  }

  @FunctionalInterface
  @JsFunction
  public interface CreateResourceFn<InputType, OutputType>
  {
    Promise<OutputType> createResource( @DoNotAutobox @Nonnull InputType input );
  }

  @FunctionalInterface
  @JsFunction
  public interface InputToKeyFn<InputType>
  {
    Object deriveKey( @DoNotAutobox @Nonnull InputType input );
  }

  @JsMethod
  public static native <InputType, OutputType> Resource<InputType, OutputType> unstable_createResource( @Nonnull CreateResourceFn<InputType, OutputType> createResourceFn,
                                                                                                        @Nonnull InputToKeyFn<InputType> inputToKeyFn );

  @JsMethod
  public static native <InputType, OutputType> Resource<InputType, OutputType> unstable_createResource( @Nonnull CreateResourceFn<InputType, OutputType> createResourceFn );
}
