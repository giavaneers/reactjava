package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface NotificationAction {
  @JsOverlay
  static NotificationAction create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  String getAction();

  @JsProperty
  String getIcon();

  @JsProperty
  String getTitle();

  @JsProperty
  void setAction(String action);

  @JsProperty
  void setIcon(String icon);

  @JsProperty
  void setTitle(String title);
}
