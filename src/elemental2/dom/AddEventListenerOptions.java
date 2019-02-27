package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface AddEventListenerOptions extends EventListenerOptions {
  @JsOverlay
  static AddEventListenerOptions create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  boolean isOnce();

  @JsProperty
  boolean isPassive();

  @JsProperty
  void setOnce(boolean once);

  @JsProperty
  void setPassive(boolean passive);
}
