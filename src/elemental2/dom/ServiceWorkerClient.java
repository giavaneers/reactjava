package elemental2.dom;

import elemental2.core.Transferable;
import elemental2.promise.Promise;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class ServiceWorkerClient {
  public boolean focused;
  public String frameType;
  public boolean hidden;
  public String id;
  public Promise<Void> ready;
  public String url;
  public String visibilityState;

  public native Promise focus();

  public native Promise<ServiceWorkerClient> navigate(String url);

  public native void postMessage(Object message, Transferable[] transfer);

  public native void postMessage(Object message);
}
