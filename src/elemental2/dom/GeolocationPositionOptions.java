package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface GeolocationPositionOptions {
  @JsOverlay
  static GeolocationPositionOptions create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  double getMaximumAge();

  @JsProperty
  double getTimeout();

  @JsProperty
  boolean isEnableHighAccuracy();

  @JsProperty
  void setEnableHighAccuracy(boolean enableHighAccuracy);

  @JsProperty
  void setMaximumAge(double maximumAge);

  @JsProperty
  void setTimeout(double timeout);
}
