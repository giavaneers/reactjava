package elemental2.dom;

import elemental2.core.Transferable;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class MessagePort implements Transferable, EventTarget {
  @JsFunction
  public interface OnmessageFn {
    Object onInvoke(MessageEvent<Object> p0);
  }

  public MessagePort.OnmessageFn onmessage;

  public native void addEventListener(
      String type, EventListener listener, EventTarget.AddEventListenerOptionsUnionType options);

  public native void addEventListener(String type, EventListener listener);

  public native void close();

  public native boolean dispatchEvent(Event evt);

  public native void postMessage(Object message, Transferable[] transfer);

  public native void postMessage(Object message);

  public native void removeEventListener(
      String type, EventListener listener, EventTarget.RemoveEventListenerOptionsUnionType options);

  public native void removeEventListener(String type, EventListener listener);

  public native void start();
}
