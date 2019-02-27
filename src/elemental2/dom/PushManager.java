package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PushManager {
  public native Promise<PushSubscription> getSubscription();

  public native Promise<PushSubscription> subscribe();

  public native Promise<PushSubscription> subscribe(PushSubscriptionOptions options);
}
