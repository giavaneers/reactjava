package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class TouchList implements JsArrayLike<Touch> {
  public int length;

  public native Touch identifiedTouch(double identifier);

  public native Touch item(int index);
}
