package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class CSSPageRule extends CSSRule {
  public String selectorText;
  public CSSStyleDeclaration style;
}
