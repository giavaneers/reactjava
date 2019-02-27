package elemental2.dom;

import elemental2.core.JsObject;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class RTCPeerConnectionIceEvent {
  public RTCIceCandidate candidate;

  public RTCPeerConnectionIceEvent(String type, JsObject eventInitDict) {}

  public RTCPeerConnectionIceEvent(String type, Object eventInitDict) {}
}
