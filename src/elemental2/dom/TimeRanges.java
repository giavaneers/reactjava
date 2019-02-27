package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class TimeRanges {
  public int length;

  public native double end(int index);

  public native double start(int index);
}
