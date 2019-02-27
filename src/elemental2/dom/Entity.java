package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Entity extends Node {
  public String inputEncoding;
  public String notationName;
  public String publicId;
  public String systemId;
  public String xmlEncoding;
  public String xmlVersion;
}
