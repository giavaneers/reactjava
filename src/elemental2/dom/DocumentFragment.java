package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DocumentFragment extends Node {
  public double childElementCount;
  public Element firstElementChild;
  public Element lastElementChild;
}
