package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class MimeType {
  public String description;
  public Plugin enabledPlugin;
  public String suffixes;
  public String type;
}
