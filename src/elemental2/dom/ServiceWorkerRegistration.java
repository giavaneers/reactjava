package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface ServiceWorkerRegistration extends EventTarget {
  @JsFunction
  public interface OnupdatefoundFn {
    Object onInvoke(Event p0);
  }

  @JsProperty
  ServiceWorker getActive();

  @JsProperty
  ServiceWorker getInstalling();

  @JsProperty
  NavigationPreloadManager getNavigationPreload();

  Promise<Notification[]> getNotifications();

  Promise<Notification[]> getNotifications(GetNotificationOptions filter);

  @JsProperty
  ServiceWorkerRegistration.OnupdatefoundFn getOnupdatefound();

  @JsProperty
  PushManager getPushManager();

  @JsProperty
  String getScope();

  @JsProperty
  SyncManager getSync();

  @JsProperty
  ServiceWorker getWaiting();

  @JsProperty
  void setActive(ServiceWorker active);

  @JsProperty
  void setInstalling(ServiceWorker installing);

  @JsProperty
  void setNavigationPreload(NavigationPreloadManager navigationPreload);

  @JsProperty
  void setOnupdatefound(ServiceWorkerRegistration.OnupdatefoundFn onupdatefound);

  @JsProperty
  void setPushManager(PushManager pushManager);

  @JsProperty
  void setScope(String scope);

  @JsProperty
  void setSync(SyncManager sync);

  @JsProperty
  void setWaiting(ServiceWorker waiting);

  Promise<Void> showNotification(String title, NotificationOptions options);

  Promise<Void> showNotification(String title);

  Promise<Boolean> unregister();

  Promise<Void> update();
}
