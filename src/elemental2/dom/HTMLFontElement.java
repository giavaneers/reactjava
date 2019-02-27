package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLFontElement extends HTMLElement {
  public String color;
  public String face;
  public String size;
}
