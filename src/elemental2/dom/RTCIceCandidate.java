package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class RTCIceCandidate {
  public String candidate;
  public int sdpMLineIndex;
  public String sdpMid;

  public RTCIceCandidate() {}

  public RTCIceCandidate(RTCIceCandidateInit candidateInitDict) {}
}
