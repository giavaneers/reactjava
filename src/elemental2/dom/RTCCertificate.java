package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface RTCCertificate {
  @JsOverlay
  static RTCCertificate create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  double getExpires();

  @JsProperty
  void setExpires(double expires);
}
