package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class TextTrackList implements JsArrayLike<TextTrack> {
  public int length;

  public native TextTrack getTrackById(String id);
}
