package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class MutationRecord {
  public NodeList<Node> addedNodes;
  public String attributeName;
  public String attributeNamespace;
  public Node nextSibling;
  public String oldValue;
  public Node previousSibling;
  public NodeList<Node> removedNodes;
  public Node target;
  public String type;
}
