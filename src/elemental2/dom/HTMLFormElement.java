package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLFormElement extends HTMLElement {
  public String acceptCharset;
  public String action;
  public HTMLFormControlsCollection<HTMLElement> elements;
  public String enctype;
  public int length;
  public String method;
  public String name;
  public boolean noValidate;
  public String target;

  public native boolean checkValidity();

  public native boolean reportValidity();

  public native void reset();

  public native void submit();
}
