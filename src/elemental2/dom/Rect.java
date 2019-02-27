package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Rect {
  public CSSPrimitiveValue bottom;
  public CSSPrimitiveValue left;
  public CSSPrimitiveValue right;
  public CSSPrimitiveValue top;
}
