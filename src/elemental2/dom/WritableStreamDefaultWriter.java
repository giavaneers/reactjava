package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface WritableStreamDefaultWriter {
  Promise<Void> abort(Object reason);

  Promise<Void> close();

  @JsProperty
  Promise<Void> getClosed();

  @JsProperty
  int getDesiredSize();

  @JsProperty
  Promise<Double> getReady();

  void releaseLock();

  @JsProperty
  void setClosed(Promise<Void> closed);

  @JsProperty
  void setDesiredSize(int desiredSize);

  @JsProperty
  void setReady(Promise<Double> ready);

  Promise<Void> write(Object chunk);
}
