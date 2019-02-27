package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class History {
  public int length;
  public String scrollRestoration;
  public Object state;

  public native void back();

  public native void forward();

  public native void go();

  public native void go(double delta);

  public native void pushState(Object data, String title, String url);

  public native void pushState(Object data, String title);

  public native void replaceState(Object data, String title, String url);

  public native void replaceState(Object data, String title);
}
