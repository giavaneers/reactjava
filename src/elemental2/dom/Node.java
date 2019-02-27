package elemental2.dom;

import elemental2.core.JsObject;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Node implements EventTarget {
  public static double ATTRIBUTE_NODE;
  public static double CDATA_SECTION_NODE;
  public static double COMMENT_NODE;
  public static double DOCUMENT_FRAGMENT_NODE;
  public static double DOCUMENT_NODE;
  public static double DOCUMENT_POSITION_CONTAINED_BY;
  public static double DOCUMENT_POSITION_CONTAINS;
  public static double DOCUMENT_POSITION_DISCONNECTED;
  public static double DOCUMENT_POSITION_FOLLOWING;
  public static double DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC;
  public static double DOCUMENT_POSITION_PRECEDING;
  public static double DOCUMENT_TYPE_NODE;
  public static double ELEMENT_NODE;
  public static double ENTITY_NODE;
  public static double ENTITY_REFERENCE_NODE;
  public static double NOTATION_NODE;
  public static double PROCESSING_INSTRUCTION_NODE;
  public static double TEXT_NODE;
  public static double XPATH_NAMESPACE_NODE;
  public HTMLSlotElement assignedSlot;
  public NamedNodeMap<Attr> attributes;
  public String baseURI;
  public NodeList<Node> childNodes;
  public Node firstChild;
  public boolean isConnected;
  public Node lastChild;
  public String localName;
  public String namespaceURI;
  public Node nextSibling;
  public String nodeName;
  public int nodeType;
  public String nodeValue;
  public Document ownerDocument;
  public Node parentNode;
  public String prefix;
  public Node previousSibling;
  public String textContent;

  public native void addEventListener(
      String type, EventListener listener, EventTarget.AddEventListenerOptionsUnionType options);

  public native void addEventListener(String type, EventListener listener);

  public native Node appendChild(Node newChild);

  public native Node cloneNode(boolean deep);

  public native int compareDocumentPosition(Node other);

  public native boolean contains(Node n);

  public native boolean dispatchEvent(Event evt);

  public native JsObject getFeature(String feature, String version);

  public native Node getRootNode();

  public native Node getRootNode(GetRootNodeOptions options);

  public native JsObject getUserData(String key);

  public native boolean hasAttributes();

  public native boolean hasChildNodes();

  public native Node insertBefore(Node newChild, Node refChild);

  public native boolean isDefaultNamespace(String namespaceURI);

  public native boolean isEqualNode(Node arg);

  public native boolean isSameNode(Node other);

  public native boolean isSupported(String feature, String version);

  public native String lookupNamespaceURI(String prefix);

  public native String lookupPrefix(String namespaceURI);

  public native void normalize();

  public native Element querySelector(String query);

  public native NodeList<Element> querySelectorAll(String query);

  public native Node removeChild(Node oldChild);

  public native void removeEventListener(
      String type, EventListener listener, EventTarget.RemoveEventListenerOptionsUnionType options);

  public native void removeEventListener(String type, EventListener listener);

  public native Node replaceChild(Node newChild, Node oldChild);

  public native JsObject setUserData(JsObject key, JsObject data, UserDataHandler handler);

  @JsOverlay
  public final JsObject setUserData(Object key, Object data, UserDataHandler handler) {
    return setUserData(Js.<JsObject>uncheckedCast(key), Js.<JsObject>uncheckedCast(data), handler);
  }
}
