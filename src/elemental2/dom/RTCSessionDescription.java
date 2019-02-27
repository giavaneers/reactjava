package elemental2.dom;

import elemental2.core.JsObject;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class RTCSessionDescription {
  public String sdp;
  public String type;

  public RTCSessionDescription() {}

  public RTCSessionDescription(JsObject descriptionInitDict) {}

  public RTCSessionDescription(Object descriptionInitDict) {}
}
