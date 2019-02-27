package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface PerformanceObserverInit {
  @JsOverlay
  static PerformanceObserverInit create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  String[] getEntryTypes();

  @JsProperty
  boolean isBuffered();

  @JsProperty
  void setBuffered(boolean buffered);

  @JsProperty
  void setEntryTypes(String[] entryTypes);
}
