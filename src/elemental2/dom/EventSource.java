package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class EventSource implements EventTarget {
  @JsFunction
  public interface CloseFn {
    Object onInvoke();
  }

  @JsFunction
  public interface OnerrorFn {
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

  public double CLOSED;
  public double CONNECTING;
  public double OPEN;
  public EventSource.CloseFn close;
  public EventSource.OnerrorFn onerror;
  public EventSource.OnmessageFn onmessage;
  public EventSource.OnopenFn onopen;
  public int readyState;
  public String url;
  public boolean withCredentials;

  public EventSource(String url, EventSourceInit eventSourceInitDict) {}

  public EventSource(String url) {}

  public native void addEventListener(
      String type, EventListener listener, EventTarget.AddEventListenerOptionsUnionType options);

  public native void addEventListener(String type, EventListener listener);

  public native boolean dispatchEvent(Event evt);

  public native void removeEventListener(
      String type, EventListener listener, EventTarget.RemoveEventListenerOptionsUnionType options);

  public native void removeEventListener(String type, EventListener listener);
}
