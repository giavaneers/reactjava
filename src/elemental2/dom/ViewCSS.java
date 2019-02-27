package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class ViewCSS {
  public native CSSStyleDeclaration getComputedStyle(Element elt, String pseudoElt);

  public native CSSStyleDeclaration getComputedStyle(Element elt);
}
