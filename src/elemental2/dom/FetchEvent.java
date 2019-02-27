package elemental2.dom;

import elemental2.promise.IThenable;
import elemental2.promise.Promise;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class FetchEvent extends ExtendableEvent {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface RespondWithRUnionType {
    @JsOverlay
    static FetchEvent.RespondWithRUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default IThenable<Response> asIThenable() {
      return Js.cast(this);
    }

    @JsOverlay
    default Response asResponse() {
      return Js.cast(this);
    }

    @JsOverlay
    default boolean isResponse() {
      return (Object) this instanceof Response;
    }
  }

  public ServiceWorkerClient client;
  public String clientId;
  public boolean isReload;
  public Promise<Response> preloadResponse;
  public Request request;
  public String reservedClientId;
  public String targetClientId;

  public FetchEvent(String type, FetchEventInit eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (ExtendableEventInit) null);
  }

  public FetchEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (ExtendableEventInit) null);
  }

  @JsMethod(name = "default")
  public native Promise<Response> default_();

  public native Promise<Response> forwardTo(String url);

  @JsOverlay
  public final void respondWith(IThenable<Response> r) {
    respondWith(Js.<FetchEvent.RespondWithRUnionType>uncheckedCast(r));
  }

  public native void respondWith(FetchEvent.RespondWithRUnionType r);

  @JsOverlay
  public final void respondWith(Response r) {
    respondWith(Js.<FetchEvent.RespondWithRUnionType>uncheckedCast(r));
  }
}
