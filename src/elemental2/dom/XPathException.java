package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class XPathException {
  public static double INVALID_EXPRESSION_ERR;
  public static double TYPE_ERR;
  public double code;
}
