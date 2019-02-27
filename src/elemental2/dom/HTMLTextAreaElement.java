package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLTextAreaElement extends HTMLElement {
  public String accessKey;
  public boolean autofocus;
  public int cols;
  public String defaultValue;
  public boolean disabled;
  public HTMLFormElement form;
  public NodeList<HTMLLabelElement> labels;
  public String name;
  public boolean readOnly;
  public int rows;
  public int tabIndex;
  public String type;
  public String validationMessage;
  public ValidityState validity;
  public String value;
  public boolean willValidate;

  public native void blur();

  public native boolean checkValidity();

  public native void focus();

  public native boolean reportValidity();

  public native void select();

  public native void setCustomValidity(String message);
}
