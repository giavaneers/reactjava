package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class UIEvent extends Event {
  public int detail;

  public UIEvent(String type, UIEventInit eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public UIEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public native void initUIEvent(
      String typeArg, boolean canBubbleArg, boolean cancelableArg, Window viewArg, int detailArg);
}
