package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PerformanceNavigationTiming extends PerformanceResourceTiming {
  public double domComplete;
  public double domContentLoadedEventEnd;
  public double domContentLoadedEventStart;
  public double domInteractive;
  public double loadEventEnd;
  public double loadEventStart;
  public double redirectCount;
  public String type;
  public double unloadEventEnd;
  public double unloadEventStart;
}
