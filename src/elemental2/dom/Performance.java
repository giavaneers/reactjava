package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Performance {
  public PerformanceNavigation navigation;
  public PerformanceTiming timing;

  public native void clearMarks();

  public native void clearMarks(String markName);

  public native void clearMeasures();

  public native void clearMeasures(String measureName);

  public native void clearResourceTimings();

  public native PerformanceEntry[] getEntries();

  public native PerformanceEntry[] getEntriesByName(String name, String entryType);

  public native PerformanceEntry[] getEntriesByName(String name);

  public native PerformanceEntry[] getEntriesByType(String entryType);

  public native void mark(String markName);

  public native void measure(String measureName, String startMark, String endMark);

  public native void measure(String measureName, String startMark);

  public native void measure(String measureName);

  public native double now();

  public native void setResourceTimingBufferSize(int maxSize);

  public native void webkitClearResourceTimings();

  public native double webkitNow();
}
