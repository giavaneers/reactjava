package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class InstallEvent extends ExtendableEvent {
  public InstallEvent(String type, InstallEventInit eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (ExtendableEventInit) null);
  }

  public InstallEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (ExtendableEventInit) null);
  }
}
