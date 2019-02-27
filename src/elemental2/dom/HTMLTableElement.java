package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLTableElement extends HTMLElement {
  public String align;
  public String bgColor;
  public String border;
  public HTMLTableCaptionElement caption;
  public String cellPadding;
  public String cellSpacing;
  public String frame;
  public HTMLCollection<HTMLTableRowElement> rows;
  public String rules;
  public String summary;
  public HTMLCollection<HTMLTableSectionElement> tBodies;
  public HTMLTableSectionElement tFoot;
  public HTMLTableSectionElement tHead;
  public String width;

  public native HTMLElement createCaption();

  public native HTMLElement createTFoot();

  public native HTMLElement createTHead();

  public native void deleteCaption();

  public native HTMLElement deleteRow(int index);

  public native void deleteTFoot();

  public native void deleteTHead();

  public native HTMLElement insertRow();

  public native HTMLElement insertRow(int index);
}
