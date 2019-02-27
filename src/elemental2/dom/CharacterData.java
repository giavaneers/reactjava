package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class CharacterData extends Node {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface ReplaceWithNodesUnionType {
    @JsOverlay
    static CharacterData.ReplaceWithNodesUnionType of(Object o) {
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

  public String data;
  public int length;
  public Element nextElementSibling;
  public Element previousElementSibling;

  public native void appendData(String arg);

  public native void deleteData(int offset, int count);

  public native void insertData(int offset, String arg);

  public native void remove();

  public native void replaceData(int offset, int count, String arg);

  @JsOverlay
  public final void replaceWith(Node... nodes) {
    replaceWith(Js.<CharacterData.ReplaceWithNodesUnionType>uncheckedCast(nodes));
  }

  public native void replaceWith(CharacterData.ReplaceWithNodesUnionType... nodes);

  @JsOverlay
  public final void replaceWith(String... nodes) {
    replaceWith(Js.<CharacterData.ReplaceWithNodesUnionType>uncheckedCast(nodes));
  }

  public native String substringData(int offset, int count);
}
