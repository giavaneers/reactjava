package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface ConstrainLongRange extends LongRange {
  @JsOverlay
  static ConstrainLongRange create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  int getExact();

  @JsProperty
  int getIdeal();

  @JsProperty
  void setExact(int exact);

  @JsProperty
  void setIdeal(int ideal);
}
