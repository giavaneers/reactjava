package elemental2.dom;

import elemental2.core.JsIterable;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class NamedNodeMap<T> implements JsIterable<T>, JsArrayLike<T>, JsPropertyMap<T> {
  public int length;

  public native Node getNamedItem(String name);

  public native Node item(int index);

  public native Node removeNamedItem(String name);

  public native Node setNamedItem(Node arg);
}
