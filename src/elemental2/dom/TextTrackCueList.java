package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class TextTrackCueList implements JsArrayLike<TextTrackCue> {
  public int length;

  public native TextTrackCue getCueById(String id);
}
