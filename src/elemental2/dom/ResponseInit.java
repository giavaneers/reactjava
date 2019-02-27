package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface ResponseInit {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface GetHeadersUnionType {
    @JsOverlay
    static ResponseInit.GetHeadersUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default Headers asHeaders() {
      return Js.cast(this);
    }

    @JsOverlay
    default JsPropertyMap<String> asJsPropertyMap() {
      return Js.cast(this);
    }

    @JsOverlay
    default String[][] asStringArrayArray() {
      return Js.cast(this);
    }

    @JsOverlay
    default boolean isHeaders() {
      return (Object) this instanceof Headers;
    }

    @JsOverlay
    default boolean isStringArrayArray() {
      return (Object) this instanceof Object[];
    }
  }

  @JsOverlay
  static ResponseInit create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  ResponseInit.GetHeadersUnionType getHeaders();

  @JsProperty
  int getStatus();

  @JsProperty
  String getStatusText();

  @JsProperty
  void setHeaders(ResponseInit.GetHeadersUnionType headers);

  @JsOverlay
  default void setHeaders(Headers headers) {
    setHeaders(Js.<ResponseInit.GetHeadersUnionType>uncheckedCast(headers));
  }

  @JsOverlay
  default void setHeaders(JsPropertyMap<String> headers) {
    setHeaders(Js.<ResponseInit.GetHeadersUnionType>uncheckedCast(headers));
  }

  @JsOverlay
  default void setHeaders(String[][] headers) {
    setHeaders(Js.<ResponseInit.GetHeadersUnionType>uncheckedCast(headers));
  }

  @JsProperty
  void setStatus(int status);

  @JsProperty
  void setStatusText(String statusText);
}
