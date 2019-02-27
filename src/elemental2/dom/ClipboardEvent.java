package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class ClipboardEvent extends Event {
  public DataTransfer clipboardData;

  public ClipboardEvent(String type, ClipboardEventInit eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public ClipboardEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }
}
