package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface Location {
  DOMStringList ancestorOrigins();

  void assign(String url);

  @JsProperty
  String getHash();

  @JsProperty
  String getHost();

  @JsProperty
  String getHostname();

  @JsProperty
  String getHref();

  @JsProperty
  String getOrigin();

  @JsProperty
  String getPathname();

  @JsProperty
  String getPort();

  @JsProperty
  String getProtocol();

  @JsProperty
  String getSearch();

  void reload();

  void reload(boolean forceReload);

  void replace(String url);

  @JsProperty
  void setHash(String hash);

  @JsProperty
  void setHost(String host);

  @JsProperty
  void setHostname(String hostname);

  @JsProperty
  void setHref(String href);

  @JsProperty
  void setPathname(String pathname);

  @JsProperty
  void setPort(String port);

  @JsProperty
  void setProtocol(String protocol);

  @JsProperty
  void setSearch(String search);
}
