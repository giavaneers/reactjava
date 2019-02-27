package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLStyleElement extends HTMLElement implements LinkStyle {
  public boolean disabled;
  public String media;
  public StyleSheet sheet;
  public String type;

  @JsProperty
  public native StyleSheet getSheet();

  @JsProperty
  public native void setSheet(StyleSheet sheet);
}
