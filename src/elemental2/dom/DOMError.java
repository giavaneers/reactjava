package elemental2.dom;

import elemental2.core.JsObject;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DOMError {
  public static double SEVERITY_ERROR;
  public static double SEVERITY_FATAL_ERROR;
  public static double SEVERITY_WARNING;
  public DOMLocator location;
  public String message;
  public String name;
  public JsObject relatedData;
  public JsObject relatedException;
  public double severity;
  public String type;
}
