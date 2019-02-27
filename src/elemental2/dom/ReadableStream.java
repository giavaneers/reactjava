package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class ReadableStream {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface ConstructorQueuingStrategyUnionType {
    @JsOverlay
    static ReadableStream.ConstructorQueuingStrategyUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default ByteLengthQueuingStrategy asByteLengthQueuingStrategy() {
      return Js.cast(this);
    }

    @JsOverlay
    default CountQueuingStrategy asCountQueuingStrategy() {
      return Js.cast(this);
    }

    @JsOverlay
    default ReadableStream.ReadableStreamQueuingStrategyType asReadableStreamQueuingStrategyType() {
      return Js.cast(this);
    }

    @JsOverlay
    default boolean isByteLengthQueuingStrategy() {
      return (Object) this instanceof ByteLengthQueuingStrategy;
    }

    @JsOverlay
    default boolean isCountQueuingStrategy() {
      return (Object) this instanceof CountQueuingStrategy;
    }
  }

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface GetReaderOptionsType {
    @JsOverlay
    static ReadableStream.GetReaderOptionsType create() {
      return Js.uncheckedCast(JsPropertyMap.of());
    }

    @JsProperty
    String getMode();

    @JsProperty
    void setMode(String mode);
  }

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface GetReaderUnionType {
    @JsOverlay
    static ReadableStream.GetReaderUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default ReadableStreamBYOBReader asReadableStreamBYOBReader() {
      return Js.cast(this);
    }

    @JsOverlay
    default ReadableStreamDefaultReader asReadableStreamDefaultReader() {
      return Js.cast(this);
    }
  }

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface ReadableStreamQueuingStrategyType {
    @JsFunction
    public interface SizeFn {
      double onInvoke(Object p0);
    }

    @JsOverlay
    static ReadableStream.ReadableStreamQueuingStrategyType create() {
      return Js.uncheckedCast(JsPropertyMap.of());
    }

    @JsProperty
    double getHighWaterMark();

    @JsProperty
    ReadableStream.ReadableStreamQueuingStrategyType.SizeFn getSize();

    @JsProperty
    void setHighWaterMark(double highWaterMark);

    @JsProperty
    void setSize(ReadableStream.ReadableStreamQueuingStrategyType.SizeFn size);
  }

  public boolean locked;

  public ReadableStream() {}

  public ReadableStream(
      ReadableStreamSource underlyingSource, ByteLengthQueuingStrategy queuingStrategy) {}

  public ReadableStream(
      ReadableStreamSource underlyingSource,
      ReadableStream.ConstructorQueuingStrategyUnionType queuingStrategy) {}

  public ReadableStream(
      ReadableStreamSource underlyingSource, CountQueuingStrategy queuingStrategy) {}

  public ReadableStream(
      ReadableStreamSource underlyingSource,
      ReadableStream.ReadableStreamQueuingStrategyType queuingStrategy) {}

  public ReadableStream(ReadableStreamSource underlyingSource) {}

  public native Promise<Void> cancel(Object reason);

  public native ReadableStream.GetReaderUnionType getReader();

  public native ReadableStream.GetReaderUnionType getReader(
      ReadableStream.GetReaderOptionsType options);

  public native ReadableStream pipeThrough(TransformStream transform, PipeOptions options);

  public native ReadableStream pipeThrough(TransformStream transform);

  public native Promise<Void> pipeTo(WritableStream dest, PipeOptions options);

  public native Promise<Void> pipeTo(WritableStream dest);

  public native ReadableStream[] tee();
}
