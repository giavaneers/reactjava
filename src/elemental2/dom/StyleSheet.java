package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class StyleSheet {
  public boolean disabled;
  public String href;
  public MediaList media;
  public Node ownerNode;
  public StyleSheet parentStyleSheet;
  public String title;
  public String type;
}
