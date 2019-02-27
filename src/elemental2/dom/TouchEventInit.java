package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface TouchEventInit extends UIEventInit {
  @JsOverlay
  static TouchEventInit create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  Touch[] getChangedTouches();

  @JsProperty
  EventTarget getRelatedTarget();

  @JsProperty
  Touch[] getTargetTouches();

  @JsProperty
  Touch[] getTouches();

  @JsProperty
  void setChangedTouches(Touch[] changedTouches);

  @JsProperty
  void setRelatedTarget(EventTarget relatedTarget);

  @JsProperty
  void setTargetTouches(Touch[] targetTouches);

  @JsProperty
  void setTouches(Touch[] touches);
}
