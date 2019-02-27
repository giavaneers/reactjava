package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PerformanceNavigation {
  public double TYPE_BACK_FORWARD;
  public double TYPE_NAVIGATE;
  public double TYPE_RELOAD;
  public double TYPE_RESERVED;
  public double redirectCount;
  public double type;
}
