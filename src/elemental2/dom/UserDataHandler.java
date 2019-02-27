package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class UserDataHandler {
  public double NODE_ADOPTED;
  public double NODE_CLONED;
  public double NODE_DELETED;
  public double NODE_IMPORTED;
  public double NODE_RENAMED;

  public native void handle(double operation, String key, Object data, Node src, Node dst);

  public native void handle(double operation, String key, Object data, Node src);

  public native void handle(double operation, String key, Object data);

  public native void handle(double operation, String key);
}
