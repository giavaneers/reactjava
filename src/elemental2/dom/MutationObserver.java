package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class MutationObserver {
  @JsFunction
  public interface MutationObserverCallbackFn {
    Object onInvoke(MutationRecord[] p0, MutationObserver p1);
  }

  public MutationObserver(MutationObserver.MutationObserverCallbackFn callback) {}

  public native Object disconnect();

  public native void observe(Node target, MutationObserverInit options);

  public native void observe(Node target);

  public native MutationRecord[] takeRecords();
}
