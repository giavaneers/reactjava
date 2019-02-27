package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLLinkElement extends HTMLElement implements LinkStyle {
  public String as;
  public String charset;
  public boolean disabled;
  public String href;
  public String hreflang;

  @JsProperty(name = "import")
  public Document import_;

  public String media;
  public String rel;
  public String rev;
  public StyleSheet sheet;
  public String target;
  public String type;

  @JsProperty
  public native StyleSheet getSheet();

  @JsProperty
  public native void setSheet(StyleSheet sheet);
}
