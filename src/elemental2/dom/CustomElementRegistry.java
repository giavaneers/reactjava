package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsConstructorFn;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class CustomElementRegistry {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface DefineOptionsType {
    @JsOverlay
    static CustomElementRegistry.DefineOptionsType create() {
      return Js.uncheckedCast(JsPropertyMap.of());
    }

    @JsProperty
    String getExtends();

    @JsProperty
    void setExtends(String extends_);
  }

  @JsOverlay
  public final void define(
      String tagName,
      Class<? extends HTMLElement> klass,
      CustomElementRegistry.DefineOptionsType options) {
    define(tagName, Js.asConstructorFn(klass), options);
  }

  @JsOverlay
  public final void define(String tagName, Class<? extends HTMLElement> klass) {
    define(tagName, Js.asConstructorFn(klass));
  }

  public native void define(
      String tagName,
      JsConstructorFn<? extends HTMLElement> klass,
      CustomElementRegistry.DefineOptionsType options);

  public native void define(String tagName, JsConstructorFn<? extends HTMLElement> klass);

  public native JsConstructorFn<? extends HTMLElement> get(String tagName);

  public native Promise<JsConstructorFn<? extends HTMLElement>> whenDefined(String tagName);
}
