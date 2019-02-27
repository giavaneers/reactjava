package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface PhotoSettings {
  @JsProperty
  String getFillLightMode();

  @JsProperty
  double getImageHeight();

  @JsProperty
  double getImageWidth();

  @JsProperty
  boolean isRedEyeReduction();

  @JsProperty
  void setFillLightMode(String fillLightMode);

  @JsProperty
  void setImageHeight(double imageHeight);

  @JsProperty
  void setImageWidth(double imageWidth);

  @JsProperty
  void setRedEyeReduction(boolean redEyeReduction);
}
