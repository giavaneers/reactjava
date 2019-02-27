package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class ServiceWorker extends Worker {
  @JsFunction
  public interface OnstatechangeFn {
    Object onInvoke(Event p0);
  }

  public ServiceWorker.OnstatechangeFn onstatechange;
  public String scriptURL;
  public String state;

  public ServiceWorker() {
    // This call is only here for java compilation purpose.
    super((Object) null);
  }
}
