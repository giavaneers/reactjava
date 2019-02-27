package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface ServiceWorkerGlobalScope extends WorkerGlobalScope {
  @JsFunction
  public interface OnactivateFn {
    Object onInvoke(ExtendableEvent p0);
  }

  @JsFunction
  public interface OnbeforeevictedFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnevictedFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnfetchFn {
    Object onInvoke(FetchEvent p0);
  }

  @JsFunction
  public interface OninstallFn {
    Object onInvoke(InstallEvent p0);
  }

  @JsFunction
  public interface OnmessageFn {
    Object onInvoke(MessageEvent p0);
  }

  @JsProperty
  CacheStorage getCaches();

  @JsProperty
  ServiceWorkerClients getClients();

  @JsProperty
  Console getConsole();

  @JsProperty
  ServiceWorkerGlobalScope.OnactivateFn getOnactivate();

  @JsProperty
  ServiceWorkerGlobalScope.OnbeforeevictedFn getOnbeforeevicted();

  @JsProperty
  ServiceWorkerGlobalScope.OnevictedFn getOnevicted();

  @JsProperty
  ServiceWorkerGlobalScope.OnfetchFn getOnfetch();

  @JsProperty
  ServiceWorkerGlobalScope.OninstallFn getOninstall();

  @JsProperty
  ServiceWorkerGlobalScope.OnmessageFn getOnmessage();

  @JsProperty
  ServiceWorkerRegistration getRegistration();

  @JsProperty
  String getScope();

  @JsProperty
  Cache getScriptCache();

  @JsProperty
  void setCaches(CacheStorage caches);

  @JsProperty
  void setClients(ServiceWorkerClients clients);

  @JsProperty
  void setConsole(Console console);

  @JsProperty
  void setOnactivate(ServiceWorkerGlobalScope.OnactivateFn onactivate);

  @JsProperty
  void setOnbeforeevicted(ServiceWorkerGlobalScope.OnbeforeevictedFn onbeforeevicted);

  @JsProperty
  void setOnevicted(ServiceWorkerGlobalScope.OnevictedFn onevicted);

  @JsProperty
  void setOnfetch(ServiceWorkerGlobalScope.OnfetchFn onfetch);

  @JsProperty
  void setOninstall(ServiceWorkerGlobalScope.OninstallFn oninstall);

  @JsProperty
  void setOnmessage(ServiceWorkerGlobalScope.OnmessageFn onmessage);

  @JsProperty
  void setRegistration(ServiceWorkerRegistration registration);

  @JsProperty
  void setScope(String scope);

  @JsProperty
  void setScriptCache(Cache scriptCache);

  Promise<Void> skipWaiting();
}
