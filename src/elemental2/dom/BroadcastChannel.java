package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class BroadcastChannel implements EventTarget {
  @JsFunction
  public interface OnmessageFn {
    Object onInvoke(MessageEvent<Object> p0);
  }

  public String name;
  public BroadcastChannel.OnmessageFn onmessage;

  public BroadcastChannel(String channelName) {}

  public native void addEventListener(
      String type, EventListener listener, EventTarget.AddEventListenerOptionsUnionType options);

  public native void addEventListener(String type, EventListener listener);

  public native void close();

  public native boolean dispatchEvent(Event evt);

  public native Object postMessage(Object p0);

  public native void removeEventListener(
      String type, EventListener listener, EventTarget.RemoveEventListenerOptionsUnionType options);

  public native void removeEventListener(String type, EventListener listener);
}
