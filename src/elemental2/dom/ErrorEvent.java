package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class ErrorEvent extends Event {
  public int colno;
  public Object error;
  public String filename;
  public int lineno;
  public String message;

  public ErrorEvent(String type, ErrorEventInit eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public ErrorEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }
}
