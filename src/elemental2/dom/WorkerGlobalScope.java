package elemental2.dom;

import elemental2.core.Transferable;
import elemental2.promise.Promise;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface WorkerGlobalScope extends EventTarget {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface FetchInputUnionType {
    @JsOverlay
    static WorkerGlobalScope.FetchInputUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default Request asRequest() {
      return Js.cast(this);
    }

    @JsOverlay
    default String asString() {
      return Js.asString(this);
    }

    @JsOverlay
    default boolean isRequest() {
      return (Object) this instanceof Request;
    }

    @JsOverlay
    default boolean isString() {
      return (Object) this instanceof String;
    }
  }

  @JsFunction
  public interface OnerrorFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnofflineFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnonlineFn {
    Object onInvoke(Event p0);
  }

  void close();

  Promise<Response> fetch(WorkerGlobalScope.FetchInputUnionType input, RequestInit init);

  Promise<Response> fetch(WorkerGlobalScope.FetchInputUnionType input);

  @JsOverlay
  default Promise<Response> fetch(Request input, RequestInit init) {
    return fetch(Js.<WorkerGlobalScope.FetchInputUnionType>uncheckedCast(input), init);
  }

  @JsOverlay
  default Promise<Response> fetch(Request input) {
    return fetch(Js.<WorkerGlobalScope.FetchInputUnionType>uncheckedCast(input));
  }

  @JsOverlay
  default Promise<Response> fetch(String input, RequestInit init) {
    return fetch(Js.<WorkerGlobalScope.FetchInputUnionType>uncheckedCast(input), init);
  }

  @JsOverlay
  default Promise<Response> fetch(String input) {
    return fetch(Js.<WorkerGlobalScope.FetchInputUnionType>uncheckedCast(input));
  }

  @JsProperty
  WorkerLocation getLocation();

  @JsProperty
  WorkerGlobalScope.OnerrorFn getOnerror();

  @JsProperty
  WorkerGlobalScope.OnofflineFn getOnoffline();

  @JsProperty
  WorkerGlobalScope.OnonlineFn getOnonline();

  @JsProperty
  WorkerPerformance getPerformance();

  @JsProperty
  WorkerGlobalScope getSelf();

  void postMessage(Object message, Transferable[] transfer);

  void postMessage(Object message);

  @JsProperty
  void setLocation(WorkerLocation location);

  @JsProperty
  void setOnerror(WorkerGlobalScope.OnerrorFn onerror);

  @JsProperty
  void setOnoffline(WorkerGlobalScope.OnofflineFn onoffline);

  @JsProperty
  void setOnonline(WorkerGlobalScope.OnonlineFn ononline);

  @JsProperty
  void setPerformance(WorkerPerformance performance);

  @JsProperty
  void setSelf(WorkerGlobalScope self);
}
