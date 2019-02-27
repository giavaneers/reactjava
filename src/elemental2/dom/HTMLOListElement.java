package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLOListElement extends HTMLElement {
  public boolean compact;
  public int start;
  public String type;
}
