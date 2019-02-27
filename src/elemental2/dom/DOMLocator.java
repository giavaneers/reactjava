package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DOMLocator {
  public double byteOffset;
  public double columnNumber;
  public double lineNumber;
  public Node relatedNode;
  public String uri;
  public double utf16Offset;
}
