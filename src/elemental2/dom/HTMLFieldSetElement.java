package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLFieldSetElement extends HTMLElement {
  public boolean disabled;
  public HTMLCollection elements;
  public HTMLFormElement form;
  public String name;
  public String type;
  public String validationMessage;
  public ValidityState validity;
  public boolean willValidate;

  public native boolean checkValidity();

  public native void setCustomValidity(String message);
}
