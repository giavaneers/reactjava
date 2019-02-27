package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class RTCDataChannelEvent extends Event {
  public RTCDataChannel channel;

  private RTCDataChannelEvent() {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }
}
