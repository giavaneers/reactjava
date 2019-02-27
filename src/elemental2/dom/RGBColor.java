package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class RGBColor {
  public CSSPrimitiveValue blue;
  public CSSPrimitiveValue green;
  public CSSPrimitiveValue red;
}
