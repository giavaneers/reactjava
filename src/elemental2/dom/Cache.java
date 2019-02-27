package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface Cache {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface AddAllRequestsArrayUnionType {
    @JsOverlay
    static Cache.AddAllRequestsArrayUnionType of(Object o) {
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

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface AddRequestUnionType {
    @JsOverlay
    static Cache.AddRequestUnionType of(Object o) {
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

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface DeleteRequestUnionType {
    @JsOverlay
    static Cache.DeleteRequestUnionType of(Object o) {
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

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface KeysRequestUnionType {
    @JsOverlay
    static Cache.KeysRequestUnionType of(Object o) {
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

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface MatchAllRequestUnionType {
    @JsOverlay
    static Cache.MatchAllRequestUnionType of(Object o) {
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

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface MatchRequestUnionType {
    @JsOverlay
    static Cache.MatchRequestUnionType of(Object o) {
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

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface PutRequestUnionType {
    @JsOverlay
    static Cache.PutRequestUnionType of(Object o) {
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

  Promise<Void> add(Cache.AddRequestUnionType request);

  @JsOverlay
  default Promise<Void> add(Request request) {
    return add(Js.<Cache.AddRequestUnionType>uncheckedCast(request));
  }

  @JsOverlay
  default Promise<Void> add(String request) {
    return add(Js.<Cache.AddRequestUnionType>uncheckedCast(request));
  }

  Promise<Void> addAll(Cache.AddAllRequestsArrayUnionType[] requests);

  Promise<Boolean> delete(Cache.DeleteRequestUnionType request, CacheQueryOptions options);

  Promise<Boolean> delete(Cache.DeleteRequestUnionType request);

  @JsOverlay
  default Promise<Boolean> delete(Request request, CacheQueryOptions options) {
    return delete(Js.<Cache.DeleteRequestUnionType>uncheckedCast(request), options);
  }

  @JsOverlay
  default Promise<Boolean> delete(Request request) {
    return delete(Js.<Cache.DeleteRequestUnionType>uncheckedCast(request));
  }

  @JsOverlay
  default Promise<Boolean> delete(String request, CacheQueryOptions options) {
    return delete(Js.<Cache.DeleteRequestUnionType>uncheckedCast(request), options);
  }

  @JsOverlay
  default Promise<Boolean> delete(String request) {
    return delete(Js.<Cache.DeleteRequestUnionType>uncheckedCast(request));
  }

  Promise<Request[]> keys();

  Promise<Request[]> keys(Cache.KeysRequestUnionType request, CacheQueryOptions options);

  Promise<Request[]> keys(Cache.KeysRequestUnionType request);

  @JsOverlay
  default Promise<Request[]> keys(Request request, CacheQueryOptions options) {
    return keys(Js.<Cache.KeysRequestUnionType>uncheckedCast(request), options);
  }

  @JsOverlay
  default Promise<Request[]> keys(Request request) {
    return keys(Js.<Cache.KeysRequestUnionType>uncheckedCast(request));
  }

  @JsOverlay
  default Promise<Request[]> keys(String request, CacheQueryOptions options) {
    return keys(Js.<Cache.KeysRequestUnionType>uncheckedCast(request), options);
  }

  @JsOverlay
  default Promise<Request[]> keys(String request) {
    return keys(Js.<Cache.KeysRequestUnionType>uncheckedCast(request));
  }

  Promise<Response> match(Cache.MatchRequestUnionType request, CacheQueryOptions options);

  Promise<Response> match(Cache.MatchRequestUnionType request);

  @JsOverlay
  default Promise<Response> match(Request request, CacheQueryOptions options) {
    return match(Js.<Cache.MatchRequestUnionType>uncheckedCast(request), options);
  }

  @JsOverlay
  default Promise<Response> match(Request request) {
    return match(Js.<Cache.MatchRequestUnionType>uncheckedCast(request));
  }

  @JsOverlay
  default Promise<Response> match(String request, CacheQueryOptions options) {
    return match(Js.<Cache.MatchRequestUnionType>uncheckedCast(request), options);
  }

  @JsOverlay
  default Promise<Response> match(String request) {
    return match(Js.<Cache.MatchRequestUnionType>uncheckedCast(request));
  }

  Promise<Response[]> matchAll();

  Promise<Response[]> matchAll(Cache.MatchAllRequestUnionType request, CacheQueryOptions options);

  Promise<Response[]> matchAll(Cache.MatchAllRequestUnionType request);

  @JsOverlay
  default Promise<Response[]> matchAll(Request request, CacheQueryOptions options) {
    return matchAll(Js.<Cache.MatchAllRequestUnionType>uncheckedCast(request), options);
  }

  @JsOverlay
  default Promise<Response[]> matchAll(Request request) {
    return matchAll(Js.<Cache.MatchAllRequestUnionType>uncheckedCast(request));
  }

  @JsOverlay
  default Promise<Response[]> matchAll(String request, CacheQueryOptions options) {
    return matchAll(Js.<Cache.MatchAllRequestUnionType>uncheckedCast(request), options);
  }

  @JsOverlay
  default Promise<Response[]> matchAll(String request) {
    return matchAll(Js.<Cache.MatchAllRequestUnionType>uncheckedCast(request));
  }

  Promise<Void> put(Cache.PutRequestUnionType request, Response response);

  @JsOverlay
  default Promise<Void> put(Request request, Response response) {
    return put(Js.<Cache.PutRequestUnionType>uncheckedCast(request), response);
  }

  @JsOverlay
  default Promise<Void> put(String request, Response response) {
    return put(Js.<Cache.PutRequestUnionType>uncheckedCast(request), response);
  }
}
