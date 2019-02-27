package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLMenuElement extends HTMLElement {
  public boolean compact;
  public String label;
  public String type;
}
