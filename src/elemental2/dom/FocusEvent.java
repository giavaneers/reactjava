package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class FocusEvent extends UIEvent {
  public EventTarget relatedTarget;

  public FocusEvent(String type, FocusEventInit eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (UIEventInit) null);
  }

  public FocusEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (UIEventInit) null);
  }
}
