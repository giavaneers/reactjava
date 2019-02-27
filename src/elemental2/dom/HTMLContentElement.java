package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLContentElement extends HTMLElement {
  public String select;

  public native NodeList<Node> getDistributedNodes();
}
