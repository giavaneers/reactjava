package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DeviceRotationRate {
  public double alpha;
  public double beta;
  public double gamma;
}
