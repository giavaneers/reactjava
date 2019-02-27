package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class SharedWorker implements EventTarget {
  @JsFunction
  public interface OnerrorFn {
    Object onInvoke(Event p0);
  }

  public SharedWorker.OnerrorFn onerror;
  public MessagePort port;

  public SharedWorker(String scriptURL, String name) {}

  public SharedWorker(String scriptURL) {}

  public native void addEventListener(
      String type, EventListener listener, EventTarget.AddEventListenerOptionsUnionType options);

  public native void addEventListener(String type, EventListener listener);

  public native boolean dispatchEvent(Event evt);

  public native void removeEventListener(
      String type, EventListener listener, EventTarget.RemoveEventListenerOptionsUnionType options);

  public native void removeEventListener(String type, EventListener listener);
}
