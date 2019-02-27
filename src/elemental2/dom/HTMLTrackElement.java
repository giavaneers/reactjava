package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLTrackElement extends HTMLElement {
  @JsProperty(name = "default")
  public boolean default_;

  public String kind;
  public String label;
  public int readyState;
  public String src;
  public String srclang;
  public TextTrack track;
}
