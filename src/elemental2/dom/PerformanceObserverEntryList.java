package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PerformanceObserverEntryList {
  public native PerformanceEntry[] getEntries();

  public native PerformanceEntry[] getEntriesByName(String type);

  public native PerformanceEntry[] getEntriesByType(String name, String type);

  public native PerformanceEntry[] getEntriesByType(String name);
}
