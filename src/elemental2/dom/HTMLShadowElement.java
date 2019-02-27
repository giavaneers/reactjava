package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLShadowElement extends HTMLElement {
  public native NodeList<Node> getDistributedNodes();
}
