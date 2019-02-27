package elemental2.dom;

import elemental2.core.JsIteratorIterable;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DOMTokenList implements JsArrayLike<String> {
  public int length;

  public native void add(String... var_args);

  public native boolean contains(String token);

  public native String item(int index);

  public native void remove(String... var_args);

  @JsMethod(name = "toString")
  public native String toString_();

  public native boolean toggle(String token, boolean force);

  public native boolean toggle(String token);

  public native JsIteratorIterable<String> values();
}
