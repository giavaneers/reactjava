package elemental2.dom;

import elemental2.core.JsIterable;
import elemental2.core.JsIterator;
import elemental2.core.JsIteratorIterable;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Headers implements JsIterable<String[]> {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface ConstructorHeadersInitUnionType {
    @JsOverlay
    static Headers.ConstructorHeadersInitUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default Headers asHeaders() {
      return Js.cast(this);
    }

    @JsOverlay
    default JsPropertyMap<String> asJsPropertyMap() {
      return Js.cast(this);
    }

    @JsOverlay
    default String[][] asStringArrayArray() {
      return Js.cast(this);
    }

    @JsOverlay
    default boolean isHeaders() {
      return (Object) this instanceof Headers;
    }

    @JsOverlay
    default boolean isStringArrayArray() {
      return (Object) this instanceof Object[];
    }
  }

  public Headers() {}

  public Headers(Headers.ConstructorHeadersInitUnionType headersInit) {}

  public Headers(Headers headersInit) {}

  public Headers(JsPropertyMap<String> headersInit) {}

  public Headers(String[][] headersInit) {}

  public native void append(String name, String value);

  public native void delete(String name);

  public native JsIteratorIterable<String[]> entries();

  public native String get(String name);

  public native String[] getAll(String name);

  public native boolean has(String name);

  public native JsIterator<String> keys();

  public native void set(String name, String value);

  public native JsIterator<String> values();
}
