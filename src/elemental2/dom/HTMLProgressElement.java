package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLProgressElement extends HTMLElement {
  public NodeList<Node> labels;
  public double max;
  public double position;
  public double value;
}
