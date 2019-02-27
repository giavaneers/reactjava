package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLDocument extends Document {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface LinksHTMLCollectionTypeParameterUnionType {
    @JsOverlay
    static HTMLDocument.LinksHTMLCollectionTypeParameterUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default HTMLAnchorElement asHTMLAnchorElement() {
      return Js.cast(this);
    }

    @JsOverlay
    default HTMLAreaElement asHTMLAreaElement() {
      return Js.cast(this);
    }

    @JsOverlay
    default boolean isHTMLAnchorElement() {
      return (Object) this instanceof HTMLAnchorElement;
    }

    @JsOverlay
    default boolean isHTMLAreaElement() {
      return (Object) this instanceof HTMLAreaElement;
    }
  }

  public String URL;
  public HTMLCollection<HTMLAnchorElement> anchors;
  public HTMLCollection<HTMLAppletElement> applets;
  public HTMLBodyElement body;
  public String cookie;
  public String domain;
  public HTMLCollection<HTMLFormElement> forms;
  public HTMLCollection<HTMLImageElement> images;
  public HTMLCollection<HTMLDocument.LinksHTMLCollectionTypeParameterUnionType> links;
  public String referrer;
  public String title;

  public native void close();

  public native NodeIterator createNodeIterator(
      Node root, double whatToShow, NodeFilter filter, boolean entityReferenceExpansion);

  public native NodeIterator createNodeIterator(Node root, double whatToShow, NodeFilter filter);

  public native NodeIterator createNodeIterator(Node root, double whatToShow);

  public native NodeIterator createNodeIterator(Node root);

  public native TreeWalker createTreeWalker(
      Node root, double whatToShow, NodeFilter filter, boolean entityReferenceExpansion);

  public native TreeWalker createTreeWalker(Node root, double whatToShow, NodeFilter filter);

  public native TreeWalker createTreeWalker(Node root, double whatToShow);

  public native TreeWalker createTreeWalker(Node root);

  public native NodeList<Element> getElementsByName(String elementName);

  public native void open();

  public native void open(String mimeType, String replace);

  public native void open(String mimeType);

  public native void write(String text);

  public native void writeln(String text);
}
