package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class MediaList implements JsArrayLike<MediaList> {
  public int length;
  public String mediaText;

  public native String item(int index);
}
