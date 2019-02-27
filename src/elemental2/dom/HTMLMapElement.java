package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLMapElement extends HTMLElement {
  public HTMLCollection<HTMLAreaElement> areas;
  public String name;
}
