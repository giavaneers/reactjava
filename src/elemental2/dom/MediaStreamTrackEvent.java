package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class MediaStreamTrackEvent extends Event {
  public MediaStreamTrack track;

  public MediaStreamTrackEvent(String type, MediaStreamTrackEventInit eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }
}
