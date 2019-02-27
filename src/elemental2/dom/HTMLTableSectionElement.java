package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLTableSectionElement extends HTMLElement {
  public String align;
  public String ch;
  public String chOff;
  public HTMLCollection<HTMLTableRowElement> rows;
  public String vAlign;

  public native HTMLElement deleteRow(int index);

  public native HTMLElement insertRow();

  public native HTMLElement insertRow(int index);
}
