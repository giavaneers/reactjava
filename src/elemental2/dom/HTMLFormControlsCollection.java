package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLFormControlsCollection<T> extends HTMLCollection<T> implements JsArrayLike<T> {
  public native T namedItem(String name);
}
