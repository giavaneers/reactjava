package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsConstructorFn;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Document extends Node {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface CreateTextNodeDataUnionType {
    @JsOverlay
    static Document.CreateTextNodeDataUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default double asDouble() {
      return Js.asDouble(this);
    }

    @JsOverlay
    default String asString() {
      return Js.asString(this);
    }

    @JsOverlay
    default boolean isDouble() {
      return (Object) this instanceof Double;
    }

    @JsOverlay
    default boolean isString() {
      return (Object) this instanceof String;
    }
  }

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface RegisterElementOptionsType {
    @JsOverlay
    static Document.RegisterElementOptionsType create() {
      return Js.uncheckedCast(JsPropertyMap.of());
    }

    @JsProperty
    String getExtends();

    @JsProperty
    void setExtends(String extends_);
  }

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface RegisterOptionsType {
    @JsOverlay
    static Document.RegisterOptionsType create() {
      return Js.uncheckedCast(JsPropertyMap.of());
    }

    @JsProperty
    String getExtends();

    @JsProperty
    void setExtends(String extends_);
  }

  public double childElementCount;
  public HTMLScriptElement currentScript;
  public DocumentType doctype;
  public Element documentElement;
  public String documentURI;
  public DOMConfiguration domConfig;
  public Element firstElementChild;
  public FontFaceSet fonts;
  public Element fullscreenElement;
  public boolean fullscreenEnabled;
  public HTMLHeadElement head;
  public boolean hidden;
  public DOMImplementation implementation;
  public String inputEncoding;
  public Element lastElementChild;
  public boolean mozFullScreen;
  public Element mozFullScreenElement;
  public boolean mozFullScreenEnabled;
  public boolean mozHidden;
  public String mozVisibilityState;
  public Element msFullscreenElement;
  public boolean msFullscreenEnabled;
  public boolean msHidden;
  public String msVisibilityState;
  public Element scrollingElement;
  public boolean strictErrorChecking;
  public String visibilityState;
  public Element webkitCurrentFullScreenElement;
  public boolean webkitFullScreenKeyboardInputAllowed;
  public Element webkitFullscreenElement;
  public boolean webkitFullscreenEnabled;
  public boolean webkitHidden;
  public boolean webkitIsFullScreen;
  public String webkitVisibilityState;
  public String xmlEncoding;
  public boolean xmlStandalone;
  public String xmlVersion;

  public native Node adoptNode(Node externalNode);

  public native CaretPosition caretPositionFromPoint(int x, int y);

  public native Attr createAttribute(String name);

  public native Attr createAttributeNS(String namespaceURI, String qualifiedName);

  public native CDATASection createCDATASection(String data);

  public native Comment createComment(String data);

  public native DocumentFragment createDocumentFragment();

  public native Element createElement(String tagName, String typeExtension);

  public native Element createElement(String tagName);

  public native Element createElementNS(
      String namespaceURI, String qualifiedName, String typeExtension);

  public native Element createElementNS(String namespaceURI, String qualifiedName);

  public native EntityReference createEntityReference(String name);

  public native ProcessingInstruction createProcessingInstruction(String target, String data);

  public native Text createTextNode(Document.CreateTextNodeDataUnionType data);

  @JsOverlay
  public final Text createTextNode(String data) {
    return createTextNode(Js.<Document.CreateTextNodeDataUnionType>uncheckedCast(data));
  }

  @JsOverlay
  public final Text createTextNode(double data) {
    return createTextNode(Js.<Document.CreateTextNodeDataUnionType>uncheckedCast(data));
  }

  public native Touch createTouch(
      Window view,
      EventTarget target,
      int identifier,
      double pageX,
      double pageY,
      double screenX,
      double screenY);

  public native TouchList createTouchList(Touch[] touches);

  public native Element elementFromPoint(double x, double y);

  public native void exitFullscreen();

  public native Element getElementById(String s);

  public native NodeList<Element> getElementsByTagName(String tagname);

  public native NodeList<Element> getElementsByTagNameNS(String namespace, String name);

  public native Node importNode(Node externalNode, boolean deep);

  public native Object mozCancelFullScreen();

  public native void normalizeDocument();

  public native Object postMessage(String message);

  public native Element querySelector(String selectors);

  public native NodeList<Element> querySelectorAll(String selectors);

  public native Object register(String type, Document.RegisterOptionsType options);

  public native JsConstructorFn<? extends Element> registerElement(
      String type, Document.RegisterElementOptionsType options);

  public native JsConstructorFn<? extends Element> registerElement(String type);

  public native Node renameNode(Node n, String namespaceURI, String qualifiedName);

  public native Object webkitCancelFullScreen();
}
