package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface ServiceWorkerContainer extends EventTarget {
  @JsFunction
  public interface OncontrollerchangeFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnerrorFn {
    Object onInvoke(ErrorEvent p0);
  }

  @JsProperty
  ServiceWorker getController();

  @JsProperty
  ServiceWorkerContainer.OncontrollerchangeFn getOncontrollerchange();

  @JsProperty
  ServiceWorkerContainer.OnerrorFn getOnerror();

  @JsProperty
  Promise<ServiceWorkerRegistration> getReady();

  Promise<ServiceWorkerRegistration> getRegistration();

  Promise<ServiceWorkerRegistration> getRegistration(String documentURL);

  Promise<ServiceWorkerRegistration[]> getRegistrations();

  Promise<ServiceWorkerRegistration> register(String scriptURL, RegistrationOptions options);

  Promise<ServiceWorkerRegistration> register(String scriptURL);

  @JsProperty
  void setController(ServiceWorker controller);

  @JsProperty
  void setOncontrollerchange(ServiceWorkerContainer.OncontrollerchangeFn oncontrollerchange);

  @JsProperty
  void setOnerror(ServiceWorkerContainer.OnerrorFn onerror);

  @JsProperty
  void setReady(Promise<ServiceWorkerRegistration> ready);
}
