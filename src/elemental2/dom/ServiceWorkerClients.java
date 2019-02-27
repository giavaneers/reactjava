package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface ServiceWorkerClients {
  Promise<Void> claim();

  Promise<ServiceWorkerClient> get(String id);

  Promise<ServiceWorkerClient[]> getAll();

  Promise<ServiceWorkerClient[]> getAll(ServiceWorkerClientQueryOptions options);

  Promise<ServiceWorkerClient[]> matchAll();

  Promise<ServiceWorkerClient[]> matchAll(ServiceWorkerClientQueryOptions options);

  Promise<ServiceWorkerClient> openWindow(String url);
}
