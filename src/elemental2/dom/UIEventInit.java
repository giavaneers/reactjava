package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface UIEventInit extends EventInit {
  @JsOverlay
  static UIEventInit create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  int getDetail();

  @JsProperty
  Window getView();

  @JsProperty
  void setDetail(int detail);

  @JsProperty
  void setView(Window view);
}
