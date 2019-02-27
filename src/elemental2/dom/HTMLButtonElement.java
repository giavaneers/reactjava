package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLButtonElement extends HTMLElement {
  public String accessKey;
  public boolean autofocus;
  public boolean disabled;
  public HTMLFormElement form;
  public String formAction;
  public String formEnctype;
  public String formMethod;
  public String formTarget;
  public NodeList<HTMLLabelElement> labels;
  public String name;
  public int tabIndex;
  public String type;
  public String validationMessage;
  public ValidityState validity;
  public String value;
  public boolean willValidate;

  public native boolean checkValidity();

  public native boolean reportValidity();

  public native void setCustomValidity(String message);
}
