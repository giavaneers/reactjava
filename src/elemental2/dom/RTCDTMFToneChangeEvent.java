package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class RTCDTMFToneChangeEvent extends Event {
  public String tone;

  public RTCDTMFToneChangeEvent(String type, RTCDTMFToneChangeEventInit eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }
}
