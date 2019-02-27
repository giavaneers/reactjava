package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class WritableStream {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface ConstructorQueuingStrategyUnionType {
    @JsOverlay
    static WritableStream.ConstructorQueuingStrategyUnionType of(Object o) {
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

  public boolean locked;

  public WritableStream() {}

  public WritableStream(
      WritableStreamSink underlyingSink, ByteLengthQueuingStrategy queuingStrategy) {}

  public WritableStream(
      WritableStreamSink underlyingSink,
      WritableStream.ConstructorQueuingStrategyUnionType queuingStrategy) {}

  public WritableStream(WritableStreamSink underlyingSink, CountQueuingStrategy queuingStrategy) {}

  public WritableStream(
      WritableStreamSink underlyingSink,
      ReadableStream.ReadableStreamQueuingStrategyType queuingStrategy) {}

  public WritableStream(WritableStreamSink underlyingSink) {}

  public native Promise<Void> abort(Object reason);

  public native WritableStreamDefaultWriter getWriter();
}
