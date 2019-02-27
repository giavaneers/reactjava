package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class CSSInterface {
  public native String escape(String ident);

  public native boolean supports(String property, String value);

  public native boolean supports(String property);
}
