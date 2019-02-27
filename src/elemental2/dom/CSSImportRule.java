package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class CSSImportRule extends CSSRule {
  public String href;
  public MediaList media;
  public CSSStyleSheet styleSheet;
}
