package elemental2.dom;

import elemental2.core.Transferable;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface DedicatedWorkerGlobalScope extends WorkerGlobalScope {
  @JsFunction
  public interface OnmessageFn {
    Object onInvoke(MessageEvent<Object> p0);
  }

  @JsProperty
  DedicatedWorkerGlobalScope.OnmessageFn getOnmessage();

  void postMessage(Object message, Transferable[] transfer);

  void postMessage(Object message);

  @JsProperty
  void setOnmessage(DedicatedWorkerGlobalScope.OnmessageFn onmessage);

  void webkitPostMessage(Object message, Transferable[] transfer);

  void webkitPostMessage(Object message);
}
