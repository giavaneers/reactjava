package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLSourceElement extends HTMLElement {
  public String media;
  public String sizes;
  public String src;
  public String srcset;
  public String type;
}
