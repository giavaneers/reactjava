package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLOptionsCollection
    implements JsArrayLike<HTMLOptionElement>, JsPropertyMap<HTMLOptionElement> {
  public int length;

  public native Node item(int index);
}
