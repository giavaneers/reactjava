package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class KeyboardEvent extends UIEvent {
  public boolean altKey;

  @JsProperty(name = "char")
  public String char_;

  public String code;
  public boolean ctrlKey;
  public String key;
  public String keyIdentifier;
  public String locale;
  public int location;
  public boolean metaKey;
  public boolean repeat;
  public boolean shiftKey;

  public KeyboardEvent(String type, KeyboardEventInit eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (UIEventInit) null);
  }

  public KeyboardEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (UIEventInit) null);
  }

  public native boolean getModifierState(String keyIdentifierArg);

  public native void initKeyboardEvent(
      String typeArg,
      boolean canBubbleArg,
      boolean cancelableArg,
      Window viewArg,
      String keyIdentifierArg,
      int keyLocationArg,
      String modifiersList);
}
