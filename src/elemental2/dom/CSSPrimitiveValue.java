package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class CSSPrimitiveValue extends CSSValue {
  public static double CSS_ATTR;
  public static double CSS_CM;
  public static double CSS_COUNTER;
  public static double CSS_DEG;
  public static double CSS_DIMENSION;
  public static double CSS_EMS;
  public static double CSS_EXS;
  public static double CSS_GRAD;
  public static double CSS_HZ;
  public static double CSS_IDENT;
  public static double CSS_IN;
  public static double CSS_KHZ;
  public static double CSS_MM;
  public static double CSS_MS;
  public static double CSS_NUMBER;
  public static double CSS_PC;
  public static double CSS_PERCENTAGE;
  public static double CSS_PT;
  public static double CSS_PX;
  public static double CSS_RAD;
  public static double CSS_RECT;
  public static double CSS_RGBCOLOR;
  public static double CSS_S;
  public static double CSS_STRING;
  public static double CSS_UNKNOWN;
  public static double CSS_URI;
  public double primitiveType;

  public native Counter getCounterValue();

  public native double getFloatValue(double unitType);

  public native RGBColor getRGBColorValue();

  public native Rect getRectValue();

  public native String getStringValue();

  public native void setFloatValue(double unitType, double floatValue);

  public native void setStringValue(double stringType, String stringValue);
}
