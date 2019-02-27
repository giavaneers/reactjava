package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface PhotoCapabilities {
  @JsProperty
  String[] getFillLightMode();

  @JsProperty
  MediaSettingsRange getImageHeight();

  @JsProperty
  MediaSettingsRange getImageWidth();

  @JsProperty
  String getRedEyeReduction();
}
