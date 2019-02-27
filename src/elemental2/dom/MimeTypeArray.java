package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class MimeTypeArray implements JsArrayLike<MimeType>, JsPropertyMap<MimeType> {
  public int length;

  public native MimeType item(int index);

  public native MimeType namedItem(String name);
}
