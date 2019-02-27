package elemental2.dom;

import elemental2.core.JsObject;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Console {
  @JsMethod(name = "assert")
  public native void assert_(Object condition, Object... var_args);

  public native void clear();

  public native void count();

  public native void count(String label);

  public native void debug(Object... var_args);

  public native void error(Object... var_args);

  public native void group(Object... var_args);

  public native void groupCollapsed(Object... var_args);

  public native void groupEnd();

  public native void info(Object... var_args);

  public native void log(Object... var_args);

  public native void table(JsObject data, Object columns);

  public native void table(JsObject data);

  @JsOverlay
  public final void table(Object data, Object columns) {
    table(Js.<JsObject>uncheckedCast(data), columns);
  }

  @JsOverlay
  public final void table(Object data) {
    table(Js.<JsObject>uncheckedCast(data));
  }

  public native void time(String name);

  public native void timeEnd(String name);

  public native void trace(Object... var_args);

  public native void warn(Object... var_args);
}
