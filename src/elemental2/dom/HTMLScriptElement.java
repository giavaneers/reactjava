package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLScriptElement extends HTMLElement {
  public String charset;
  public boolean defer;
  public String event;
  public String htmlFor;
  public String src;
  public String text;
  public String type;
}
