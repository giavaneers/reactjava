package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DOMImplementationList implements JsArrayLike<DOMImplementation> {
  public int length;

  public native DOMImplementation item(int index);
}
