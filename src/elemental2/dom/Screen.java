package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Screen {
  public int availHeight;
  public int availWidth;
  public int colorDepth;
  public int height;
  public int pixelDepth;
  public int width;
}
