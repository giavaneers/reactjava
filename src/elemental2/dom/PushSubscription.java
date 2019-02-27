package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PushSubscription {
  public String endpoint;
  public String subscriptionId;

  public native Promise<Boolean> unsubscribe();
}
