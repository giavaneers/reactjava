package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface ReadableStreamDefaultController {
  void close();

  void enqueue(Object chunk);

  void error(Object err);

  @JsProperty
  int getDesiredSize();

  @JsProperty
  void setDesiredSize(int desiredSize);
}
