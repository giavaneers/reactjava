package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLOptionElement extends HTMLElement {
  public boolean defaultSelected;
  public boolean disabled;
  public HTMLFormElement form;
  public int index;
  public String label;
  public boolean selected;
  public String text;
  public String value;
}
