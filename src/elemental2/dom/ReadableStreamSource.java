package elemental2.dom;

import elemental2.promise.IThenable;
import elemental2.promise.Promise;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface ReadableStreamSource {
  @JsFunction
  public interface CancelFn {
    Promise<Object> onInvoke(Object p0);
  }

  @JsFunction
  public interface PullFn {
    @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
    public interface P0UnionType {
      @JsOverlay
      static ReadableStreamSource.PullFn.P0UnionType of(Object o) {
        return Js.cast(o);
      }

      @JsOverlay
      default ReadableByteStreamController asReadableByteStreamController() {
        return Js.cast(this);
      }

      @JsOverlay
      default ReadableStreamDefaultController asReadableStreamDefaultController() {
        return Js.cast(this);
      }
    }

    IThenable<Object> onInvoke(ReadableStreamSource.PullFn.P0UnionType p0);

    @JsOverlay
    default IThenable<Object> onInvoke(ReadableByteStreamController p0) {
      return onInvoke(Js.<ReadableStreamSource.PullFn.P0UnionType>uncheckedCast(p0));
    }

    @JsOverlay
    default IThenable<Object> onInvoke(ReadableStreamDefaultController p0) {
      return onInvoke(Js.<ReadableStreamSource.PullFn.P0UnionType>uncheckedCast(p0));
    }
  }

  @JsFunction
  public interface StartFn {
    @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
    public interface P0UnionType {
      @JsOverlay
      static ReadableStreamSource.StartFn.P0UnionType of(Object o) {
        return Js.cast(o);
      }

      @JsOverlay
      default ReadableByteStreamController asReadableByteStreamController() {
        return Js.cast(this);
      }

      @JsOverlay
      default ReadableStreamDefaultController asReadableStreamDefaultController() {
        return Js.cast(this);
      }
    }

    IThenable<Object> onInvoke(ReadableStreamSource.StartFn.P0UnionType p0);

    @JsOverlay
    default IThenable<Object> onInvoke(ReadableByteStreamController p0) {
      return onInvoke(Js.<ReadableStreamSource.StartFn.P0UnionType>uncheckedCast(p0));
    }

    @JsOverlay
    default IThenable<Object> onInvoke(ReadableStreamDefaultController p0) {
      return onInvoke(Js.<ReadableStreamSource.StartFn.P0UnionType>uncheckedCast(p0));
    }
  }

  @JsOverlay
  static ReadableStreamSource create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  int getAutoAllocateChunkSize();

  @JsProperty
  ReadableStreamSource.CancelFn getCancel();

  @JsProperty
  ReadableStreamSource.PullFn getPull();

  @JsProperty
  ReadableStreamSource.StartFn getStart();

  @JsProperty
  String getType();

  @JsProperty
  void setAutoAllocateChunkSize(int autoAllocateChunkSize);

  @JsProperty
  void setCancel(ReadableStreamSource.CancelFn cancel);

  @JsProperty
  void setPull(ReadableStreamSource.PullFn pull);

  @JsProperty
  void setStart(ReadableStreamSource.StartFn start);

  @JsProperty
  void setType(String type);
}
