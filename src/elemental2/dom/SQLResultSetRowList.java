package elemental2.dom;

import elemental2.core.JsObject;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class SQLResultSetRowList implements JsArrayLike<JsObject> {
  public int length;

  public native JsObject item(int index);
}
