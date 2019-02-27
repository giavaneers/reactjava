package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class CSSRuleList implements JsArrayLike<CSSRule> {
  public int length;

  public native CSSRule item(int index);
}
