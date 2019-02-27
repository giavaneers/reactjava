package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface SharedWorkerGlobalScope extends WorkerGlobalScope {
  @JsFunction
  public interface OnconnectFn {
    Object onInvoke(Event p0);
  }

  @JsProperty
  String getName();

  @JsProperty
  SharedWorkerGlobalScope.OnconnectFn getOnconnect();

  @JsProperty
  void setName(String name);

  @JsProperty
  void setOnconnect(SharedWorkerGlobalScope.OnconnectFn onconnect);
}
