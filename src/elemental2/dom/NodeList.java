package elemental2.dom;

import elemental2.core.JsIterable;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class NodeList<T> implements JsIterable<T>, JsArrayLike<T> {
  @JsFunction
  public interface ForEachCallbackFn<T> {
    Object onInvoke(T p0, double p1, NodeList<T> p2);
  }

  public int length;

  public native <T, S> void forEach(NodeList.ForEachCallbackFn<T> callback, S thisobj);

  public native <T> void forEach(NodeList.ForEachCallbackFn<T> callback);

  public native T item(int index);
}
