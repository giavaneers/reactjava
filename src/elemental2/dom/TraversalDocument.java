package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface TraversalDocument {
  @JsFunction
  public interface CreateNodeIteratorFn {
    NodeIterator onInvoke(Node p0, double p1, NodeFilter p2, boolean p3);
  }

  @JsFunction
  public interface CreateTreeWalkerFn {
    TreeWalker onInvoke(Node p0, double p1, NodeFilter p2, boolean p3);
  }

  @JsOverlay
  static TraversalDocument create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  TraversalDocument.CreateNodeIteratorFn getCreateNodeIterator();

  @JsProperty
  TraversalDocument.CreateTreeWalkerFn getCreateTreeWalker();

  @JsProperty
  void setCreateNodeIterator(TraversalDocument.CreateNodeIteratorFn createNodeIterator);

  @JsProperty
  void setCreateTreeWalker(TraversalDocument.CreateTreeWalkerFn createTreeWalker);
}
