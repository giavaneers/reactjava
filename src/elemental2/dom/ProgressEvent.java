package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class ProgressEvent extends Event {
  public boolean lengthComputable;
  public double loaded;
  public double total;

  public ProgressEvent(String type, ProgressEventInit progressEventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public ProgressEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }
}
