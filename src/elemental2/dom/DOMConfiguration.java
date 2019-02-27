package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DOMConfiguration {
  public DOMStringList parameterNames;

  public native boolean canSetParameter(String name);

  public native Object getParameter(String name);

  public native Object setParameter(String name, Object value);
}
