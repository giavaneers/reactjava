package elemental2.dom;

import elemental2.core.Transferable;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Worker implements EventTarget {
  @JsFunction
  public interface OnerrorFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnmessageFn {
    Object onInvoke(MessageEvent<Object> p0);
  }

  public Worker.OnerrorFn onerror;
  public Worker.OnmessageFn onmessage;

  public Worker(Object arg0) {}

  public native void addEventListener(
      String type, EventListener listener, EventTarget.AddEventListenerOptionsUnionType options);

  public native void addEventListener(String type, EventListener listener);

  public native boolean dispatchEvent(Event evt);

  public native void postMessage(Object message, Transferable[] transfer);

  public native void postMessage(Object message);

  public native void removeEventListener(
      String type, EventListener listener, EventTarget.RemoveEventListenerOptionsUnionType options);

  public native void removeEventListener(String type, EventListener listener);

  public native void terminate();

  public native void webkitPostMessage(Object message, Transferable[] transfer);

  public native void webkitPostMessage(Object message);
}
