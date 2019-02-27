package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLSelectElement extends HTMLElement {
  public boolean autofocus;
  public boolean disabled;
  public HTMLFormElement form;
  public NodeList<HTMLLabelElement> labels;
  public int length;
  public boolean multiple;
  public String name;
  public HTMLOptionsCollection options;
  public int selectedIndex;
  public HTMLCollection<HTMLOptionElement> selectedOptions;
  public int size;
  public String type;
  public String validationMessage;
  public ValidityState validity;
  public String value;
  public boolean willValidate;

  public native void add(HTMLElement element, HTMLElement before);

  public native void add(HTMLElement element);

  public native void blur();

  public native boolean checkValidity();

  public native void focus();

  public native void remove();

  public native void remove(int index);

  public native boolean reportValidity();

  public native void setCustomValidity(String message);
}
