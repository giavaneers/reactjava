package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface NotificationOptions {
  @JsOverlay
  static NotificationOptions create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  NotificationAction[] getActions();

  @JsProperty
  String getBody();

  @JsProperty
  String getDir();

  @JsProperty
  String getIcon();

  @JsProperty
  String getLang();

  @JsProperty
  String getTag();

  @JsProperty
  boolean isRequireInteraction();

  @JsProperty
  void setActions(NotificationAction[] actions);

  @JsProperty
  void setBody(String body);

  @JsProperty
  void setDir(String dir);

  @JsProperty
  void setIcon(String icon);

  @JsProperty
  void setLang(String lang);

  @JsProperty
  void setRequireInteraction(boolean requireInteraction);

  @JsProperty
  void setTag(String tag);
}
