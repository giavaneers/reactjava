package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLLabelElement extends HTMLElement {
  public String accessKey;
  public Element control;
  public HTMLFormElement form;
  public String htmlFor;
}
