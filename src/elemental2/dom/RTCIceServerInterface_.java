package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface RTCIceServerInterface_ {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface GetUrlsUnionType {
    @JsOverlay
    static RTCIceServerInterface_.GetUrlsUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default String asString() {
      return Js.asString(this);
    }

    @JsOverlay
    default String[] asStringArray() {
      return Js.cast(this);
    }

    @JsOverlay
    default boolean isString() {
      return (Object) this instanceof String;
    }

    @JsOverlay
    default boolean isStringArray() {
      return (Object) this instanceof Object[];
    }
  }

  @JsProperty
  String getCredential();

  @JsProperty
  RTCIceServerInterface_.GetUrlsUnionType getUrls();

  @JsProperty
  String getUsername();

  @JsProperty
  void setCredential(String credential);

  @JsProperty
  void setUrls(RTCIceServerInterface_.GetUrlsUnionType urls);

  @JsOverlay
  default void setUrls(String urls) {
    setUrls(Js.<RTCIceServerInterface_.GetUrlsUnionType>uncheckedCast(urls));
  }

  @JsOverlay
  default void setUrls(String[] urls) {
    setUrls(Js.<RTCIceServerInterface_.GetUrlsUnionType>uncheckedCast(urls));
  }

  @JsProperty
  void setUsername(String username);
}
