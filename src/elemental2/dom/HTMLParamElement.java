package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLParamElement extends HTMLElement {
  public String name;
  public String type;
  public String value;
  public String valueType;
}
