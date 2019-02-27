package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLFrameElement extends HTMLElement {
  public Document contentDocument;
  public String frameBorder;
  public String longDesc;
  public String marginHeight;
  public String marginWidth;
  public String name;
  public boolean noResize;
  public String scrolling;
  public String src;
}
