package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface CacheStorage {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface MatchRequestUnionType {
    @JsOverlay
    static CacheStorage.MatchRequestUnionType of(Object o) {
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

  Promise<Boolean> delete(String cacheName);

  Promise<Boolean> has(String cacheName);

  Promise<String[]> keys();

  Promise<Response> match(CacheStorage.MatchRequestUnionType request, CacheQueryOptions options);

  Promise<Response> match(CacheStorage.MatchRequestUnionType request);

  @JsOverlay
  default Promise<Response> match(Request request, CacheQueryOptions options) {
    return match(Js.<CacheStorage.MatchRequestUnionType>uncheckedCast(request), options);
  }

  @JsOverlay
  default Promise<Response> match(Request request) {
    return match(Js.<CacheStorage.MatchRequestUnionType>uncheckedCast(request));
  }

  @JsOverlay
  default Promise<Response> match(String request, CacheQueryOptions options) {
    return match(Js.<CacheStorage.MatchRequestUnionType>uncheckedCast(request), options);
  }

  @JsOverlay
  default Promise<Response> match(String request) {
    return match(Js.<CacheStorage.MatchRequestUnionType>uncheckedCast(request));
  }

  Promise<Cache> open(String cacheName);
}
