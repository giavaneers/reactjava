package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface RTCConfigurationRecord_ {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface GetIceServersArrayUnionType {
    @JsOverlay
    static RTCConfigurationRecord_.GetIceServersArrayUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default RTCConfigurationInterface_.IceServersFieldType asIceServersFieldType() {
      return Js.cast(this);
    }

    @JsOverlay
    default RTCIceServerInterface_ asRTCIceServerInterface_() {
      return Js.cast(this);
    }

    @JsOverlay
    default RTCConfigurationInterface_.UrlsFieldType asUrlsFieldType() {
      return Js.cast(this);
    }
  }

  @JsOverlay
  static RTCConfigurationRecord_ create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  RTCConfigurationRecord_.GetIceServersArrayUnionType[] getIceServers();

  @JsProperty
  void setIceServers(RTCConfigurationRecord_.GetIceServersArrayUnionType[] iceServers);
}
