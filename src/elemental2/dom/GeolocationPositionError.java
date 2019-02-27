package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface GeolocationPositionError {
  @JsOverlay
  static GeolocationPositionError create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  double getCode();

  @JsProperty
  String getMessage();

  @JsProperty(name = "PERMISSION_DENIED")
  double getPERMISSION_DENIED();

  @JsProperty(name = "POSITION_UNAVAILABLE")
  double getPOSITION_UNAVAILABLE();

  @JsProperty(name = "TIMEOUT")
  double getTIMEOUT();

  @JsProperty(name = "UNKNOWN_ERROR")
  double getUNKNOWN_ERROR();

  @JsProperty
  void setCode(double code);

  @JsProperty
  void setMessage(String message);

  @JsProperty(name = "PERMISSION_DENIED")
  void setPERMISSION_DENIED(double PERMISSION_DENIED);

  @JsProperty(name = "POSITION_UNAVAILABLE")
  void setPOSITION_UNAVAILABLE(double POSITION_UNAVAILABLE);

  @JsProperty(name = "TIMEOUT")
  void setTIMEOUT(double TIMEOUT);

  @JsProperty(name = "UNKNOWN_ERROR")
  void setUNKNOWN_ERROR(double UNKNOWN_ERROR);
}
