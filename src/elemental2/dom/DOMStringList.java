package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DOMStringList implements JsArrayLike<String> {
  public int length;

  public native boolean contains(String str);

  public native String item(int index);
}
