package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DocumentCSS {
  public native CSSStyleDeclaration getOverrideStyle(Element elt, String pseudoElt);
}
