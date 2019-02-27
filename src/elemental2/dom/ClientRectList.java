package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class ClientRectList implements JsArrayLike<ClientRect> {
  public int length;

  public native ClientRect item(int index);
}
