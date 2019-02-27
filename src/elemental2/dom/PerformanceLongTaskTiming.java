package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PerformanceLongTaskTiming extends PerformanceEntry {
  public TaskAttributionTiming[] attribution;
}
