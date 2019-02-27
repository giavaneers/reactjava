package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class SyncEvent extends ExtendableEvent {
  public boolean lastChance;
  public String tag;

  public SyncEvent() {
    // This call is only here for java compilation purpose.
    super((String) null, (ExtendableEventInit) null);
  }
}
