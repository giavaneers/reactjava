package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PushEvent extends ExtendableEvent {
  public PushMessageData data;

  public PushEvent(String type, ExtendableEventInit eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (ExtendableEventInit) null);
  }

  public PushEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (ExtendableEventInit) null);
  }
}
