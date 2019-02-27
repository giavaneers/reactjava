package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class NameList implements JsArrayLike<String> {
  public int length;

  public native boolean contains(String str);

  public native boolean containsNS(String namespaceURI, String name);

  public native String getName(int index);

  public native String getNamespaceURI(int index);
}
