package elemental2.dom;

import elemental2.core.JsObject;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLCanvasElement extends HTMLElement {
  @JsFunction
  public interface ToBlobCallbackFn {
    Object onInvoke(Blob p0);
  }

  public int height;
  public int width;

  public native MediaStream captureStream();

  public native MediaStream captureStream(double framerate);

  public native JsObject getContext(String contextId, JsObject args);

  @JsOverlay
  public final JsObject getContext(String contextId, Object args) {
    return getContext(contextId, Js.<JsObject>uncheckedCast(args));
  }

  public native JsObject getContext(String contextId);

  public native Object toBlob(
      HTMLCanvasElement.ToBlobCallbackFn callback, String type, Object... var_args);

  public native String toDataURL(String type, Object... var_args);
}
