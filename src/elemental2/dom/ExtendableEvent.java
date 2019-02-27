package elemental2.dom;

import elemental2.promise.IThenable;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class ExtendableEvent extends Event {
  public ServiceWorker activeWorker;

  public ExtendableEvent(String type, ExtendableEventInit eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public ExtendableEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public native void waitUntil(IThenable f);
}
