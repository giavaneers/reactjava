package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DragEvent extends MouseEvent {
  public DataTransfer dataTransfer;

  public DragEvent(String type, DragEventInit eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (MouseEventInit) null);
  }

  public DragEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (MouseEventInit) null);
  }
}
