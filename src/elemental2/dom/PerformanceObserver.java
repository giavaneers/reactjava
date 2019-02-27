package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PerformanceObserver {
  public PerformanceObserver(PerformanceObserverCallback callback) {}

  public native void disconnect();

  public native Object observe(PerformanceObserverInit options);
}
