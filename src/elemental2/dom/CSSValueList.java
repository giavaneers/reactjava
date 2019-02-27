package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class CSSValueList extends CSSValue implements JsArrayLike<CSSValue> {
  public int length;

  public native CSSValue item(int index);
}
