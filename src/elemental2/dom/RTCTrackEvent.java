package elemental2.dom;

import elemental2.core.JsObject;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class RTCTrackEvent {
  public RTCRtpReceiver receiver;
  public MediaStream[] streams;
  public MediaStreamTrack track;
  public RTCRtpTransceiver transceiver;

  public RTCTrackEvent(String type, JsObject eventInitDict) {}

  public RTCTrackEvent(String type, Object eventInitDict) {}
}
