package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class TextTrack implements EventTarget {
  public TextTrackCueList activeCues;
  public TextTrackCueList cues;
  public String mode;

  public native void addCue(TextTrackCue cue);

  public native void addEventListener(
      String type, EventListener listener, EventTarget.AddEventListenerOptionsUnionType useCapture);

  public native void addEventListener(String type, EventListener listener);

  public native boolean dispatchEvent(Event evt);

  public native void removeCue(TextTrackCue cue);

  public native void removeEventListener(
      String type, EventListener listener, EventTarget.RemoveEventListenerOptionsUnionType options);

  public native void removeEventListener(String type, EventListener listener);
}
