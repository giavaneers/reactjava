package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLMetaElement extends HTMLElement {
  public String content;
  public String httpEquiv;
  public String name;
  public String scheme;
}
