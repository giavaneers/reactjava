package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PerformanceEntry {
  public double duration;
  public String entryType;
  public String name;
  public double startTime;
}
