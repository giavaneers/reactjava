package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DOMImplementationCSS {
  public native CSSStyleSheet createCSSStyleSheet(String title, String media);
}
