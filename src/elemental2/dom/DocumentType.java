package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DocumentType extends Node {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface ReplaceWithNodesUnionType {
    @JsOverlay
    static DocumentType.ReplaceWithNodesUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default Node asNode() {
      return Js.cast(this);
    }

    @JsOverlay
    default String asString() {
      return Js.asString(this);
    }

    @JsOverlay
    default boolean isNode() {
      return (Object) this instanceof Node;
    }

    @JsOverlay
    default boolean isString() {
      return (Object) this instanceof String;
    }
  }

  public NamedNodeMap<Entity> entities;
  public String internalSubset;
  public String name;
  public NamedNodeMap<Notation> notations;
  public String publicId;
  public String systemId;

  public native void remove();

  @JsOverlay
  public final void replaceWith(Node... nodes) {
    replaceWith(Js.<DocumentType.ReplaceWithNodesUnionType>uncheckedCast(nodes));
  }

  public native void replaceWith(DocumentType.ReplaceWithNodesUnionType... nodes);

  @JsOverlay
  public final void replaceWith(String... nodes) {
    replaceWith(Js.<DocumentType.ReplaceWithNodesUnionType>uncheckedCast(nodes));
  }
}
