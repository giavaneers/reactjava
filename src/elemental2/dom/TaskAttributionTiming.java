package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class TaskAttributionTiming extends PerformanceEntry {
  public String containerId;
  public String containerName;
  public String containerSrc;
  public String containerType;
}
