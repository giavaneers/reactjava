package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class NotificationEvent extends ExtendableEvent {
  public Notification notification;

  public NotificationEvent(String type, ExtendableEventInit eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (ExtendableEventInit) null);
  }

  public NotificationEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (ExtendableEventInit) null);
  }
}
