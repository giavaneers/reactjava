package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class StyleSheetList implements JsArrayLike<StyleSheet> {
  public int length;

  public native StyleSheet item(int index);
}
