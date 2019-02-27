package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLImageElement extends HTMLElement {
  @JsFunction
  public interface OnerrorFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnloadFn {
    Object onInvoke(Event p0);
  }

  public String align;
  public String alt;
  public String border;
  public boolean complete;
  public String crossOrigin;
  public int height;
  public int hspace;
  public boolean isMap;
  public String longDesc;
  public String lowSrc;
  public String name;
  public int naturalHeight;
  public int naturalWidth;
  public HTMLImageElement.OnerrorFn onerror;
  public HTMLImageElement.OnloadFn onload;
  public String sizes;
  public String src;
  public String srcset;
  public String useMap;
  public int vspace;
  public int width;
}
