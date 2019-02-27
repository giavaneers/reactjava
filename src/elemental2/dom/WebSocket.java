package elemental2.dom;

import elemental2.core.ArrayBuffer;
import elemental2.core.ArrayBufferView;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class WebSocket implements EventTarget {
  @JsFunction
  public interface OncloseFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnmessageFn {
    Object onInvoke(MessageEvent<Object> p0);
  }

  @JsFunction
  public interface OnopenFn {
    Object onInvoke(Event p0);
  }

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface SendDataUnionType {
    @JsOverlay
    static WebSocket.SendDataUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default ArrayBuffer asArrayBuffer() {
      return Js.cast(this);
    }

    @JsOverlay
    default ArrayBufferView asArrayBufferView() {
      return Js.cast(this);
    }

    @JsOverlay
    default String asString() {
      return Js.asString(this);
    }

    @JsOverlay
    default boolean isArrayBuffer() {
      return (Object) this instanceof ArrayBuffer;
    }

    @JsOverlay
    default boolean isArrayBufferView() {
      return (Object) this instanceof ArrayBufferView;
    }

    @JsOverlay
    default boolean isString() {
      return (Object) this instanceof String;
    }
  }

  public static double CLOSED;
  public static double CLOSING;
  public static double CONNECTING;
  public static double OPEN;
  public String binaryType;
  public int bufferedAmount;
  public WebSocket.OncloseFn onclose;
  public WebSocket.OnmessageFn onmessage;
  public WebSocket.OnopenFn onopen;
  public int readyState;
  public String url;

  public WebSocket(String url, String protocol) {}

  public WebSocket(String url) {}

  public native void addEventListener(
      String type, EventListener listener, EventTarget.AddEventListenerOptionsUnionType options);

  public native void addEventListener(String type, EventListener listener);

  public native void close();

  public native void close(int code, String reason);

  public native void close(int code);

  public native boolean dispatchEvent(Event evt);

  public native void removeEventListener(
      String type, EventListener listener, EventTarget.RemoveEventListenerOptionsUnionType options);

  public native void removeEventListener(String type, EventListener listener);

  @JsOverlay
  public final boolean send(ArrayBuffer data) {
    return send(Js.<WebSocket.SendDataUnionType>uncheckedCast(data));
  }

  @JsOverlay
  public final boolean send(ArrayBufferView data) {
    return send(Js.<WebSocket.SendDataUnionType>uncheckedCast(data));
  }

  public native boolean send(WebSocket.SendDataUnionType data);

  @JsOverlay
  public final boolean send(String data) {
    return send(Js.<WebSocket.SendDataUnionType>uncheckedCast(data));
  }
}
