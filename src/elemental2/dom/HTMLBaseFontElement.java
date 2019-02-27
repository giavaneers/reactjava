package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLBaseFontElement extends HTMLElement {
  public String color;
  public String face;
  public int size;
}
