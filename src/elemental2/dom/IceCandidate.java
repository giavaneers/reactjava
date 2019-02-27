package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class IceCandidate {
  public String label;

  public IceCandidate(String label, String sdp) {}

  public native String toSdp();
}
