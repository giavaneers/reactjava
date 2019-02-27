package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLOutputElement extends HTMLElement {
  public String defaultValue;
  public HTMLFormElement form;
  public DOMTokenList htmlFor;
  public NodeList<HTMLLabelElement> labels;
  public String name;
  public String type;
  public String validationMessage;
  public ValidityState validity;
  public String value;
  public boolean willValidate;

  public native boolean checkValidity();

  public native boolean reportValidity();

  public native Object setCustomValidity(String message);
}
