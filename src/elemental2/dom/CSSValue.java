package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class CSSValue {
  public static double CSS_CUSTOM;
  public static double CSS_INHERIT;
  public static double CSS_PRIMITIVE_VALUE;
  public static double CSS_VALUE_LIST;
  public String cssText;
  public double cssValueType;
}
