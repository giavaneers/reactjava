package elemental2.dom;

import elemental2.core.ArrayBuffer;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PushMessageData {
  public native ArrayBuffer arrayBuffer();

  public native Blob blob();

  public native Object json();

  public native String text();
}
