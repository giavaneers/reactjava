package elemental2.dom;

import elemental2.core.JsObject;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class MediaStreamEvent {
  public MediaStream stream;

  public MediaStreamEvent(String type, JsObject eventInitDict) {}

  public MediaStreamEvent(String type, Object eventInitDict) {}
}
