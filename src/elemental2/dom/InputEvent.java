package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class InputEvent extends UIEvent {
  public String data;
  public DataTransfer dataTransfer;
  public String inputType;
  public boolean isComposed;

  public InputEvent(String type, InputEventInit eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (UIEventInit) null);
  }

  public InputEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (UIEventInit) null);
  }
}
