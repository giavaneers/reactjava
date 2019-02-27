package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLMeterElement extends HTMLElement {
  public double high;
  public NodeList<Node> labels;
  public double low;
  public double max;
  public double min;
  public double optimum;
  public double value;
}
