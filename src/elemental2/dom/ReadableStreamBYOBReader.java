package elemental2.dom;

import elemental2.core.ArrayBufferView;
import elemental2.promise.Promise;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface ReadableStreamBYOBReader {
  Promise<Object> cancel(Object reason);

  @JsProperty
  Promise<Void> getClosed();

  Promise<IteratorResult> read(ArrayBufferView view);

  void releaseLock();

  @JsProperty
  void setClosed(Promise<Void> closed);
}
