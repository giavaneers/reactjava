package elemental2.dom;

import elemental2.core.JsIterable;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLCollection<T> implements JsIterable<T>, JsArrayLike<T>, JsPropertyMap<T> {
  public int length;

  public native T item(int index);

  public native T namedItem(String name);
}
