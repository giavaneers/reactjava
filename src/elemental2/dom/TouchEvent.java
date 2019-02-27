package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class TouchEvent extends UIEvent {
  public boolean altKey;
  public TouchList changedTouches;
  public boolean ctrlKey;
  public boolean metaKey;
  public boolean shiftKey;
  public TouchList targetTouches;
  public TouchList touches;

  public TouchEvent(String type, TouchEventInit eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (UIEventInit) null);
  }

  public TouchEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (UIEventInit) null);
  }
}
