package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Attr extends Node {
  public boolean isId;
  public String name;
  public Element ownerElement;
  public TypeInfo schemaTypeInfo;
  public boolean specified;
  public String value;
}
