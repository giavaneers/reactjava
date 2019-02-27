package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class VTTRegion {
  public String id;
  public int lines;
  public double regionAnchorX;
  public double regionAnchorY;
  public String scroll;
  public double viewportAnchorX;
  public double viewportAnchorY;
  public double width;
}
