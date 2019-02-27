package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface MouseEventInit extends EventModifierInit {
  @JsOverlay
  static MouseEventInit create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  int getButton();

  @JsProperty
  int getButtons();

  @JsProperty
  double getClientX();

  @JsProperty
  double getClientY();

  @JsProperty
  EventTarget getRelatedTarget();

  @JsProperty
  double getScreenX();

  @JsProperty
  double getScreenY();

  @JsProperty
  void setButton(int button);

  @JsProperty
  void setButtons(int buttons);

  @JsProperty
  void setClientX(double clientX);

  @JsProperty
  void setClientY(double clientY);

  @JsProperty
  void setRelatedTarget(EventTarget relatedTarget);

  @JsProperty
  void setScreenX(double screenX);

  @JsProperty
  void setScreenY(double screenY);
}
