package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface CacheQueryOptions {
  @JsOverlay
  static CacheQueryOptions create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  String getCacheName();

  @JsProperty
  boolean isIgnoreMethod();

  @JsProperty
  boolean isIgnoreSearch();

  @JsProperty
  boolean isIgnoreVary();

  @JsProperty
  boolean isPrefixMatch();

  @JsProperty
  void setCacheName(String cacheName);

  @JsProperty
  void setIgnoreMethod(boolean ignoreMethod);

  @JsProperty
  void setIgnoreSearch(boolean ignoreSearch);

  @JsProperty
  void setIgnoreVary(boolean ignoreVary);

  @JsProperty
  void setPrefixMatch(boolean prefixMatch);
}
