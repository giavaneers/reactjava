package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Image extends HTMLImageElement {
  public Image() {}

  public Image(double width, double height) {}

  public Image(double width) {}
}
