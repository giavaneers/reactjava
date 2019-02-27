package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DOMImplementationSource {
  public native DOMImplementation getDOMImplementation(String features);

  public native DOMImplementationList getDOMImplementationList(String features);
}
