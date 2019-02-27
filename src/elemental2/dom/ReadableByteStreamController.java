package elemental2.dom;

import elemental2.core.ArrayBufferView;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface ReadableByteStreamController {
  void close();

  void enqueue(ArrayBufferView chunk);

  void error(Object err);

  @JsProperty
  ReadableStreamBYOBRequest getByobRequest();

  @JsProperty
  int getDesiredSize();

  @JsProperty
  void setByobRequest(ReadableStreamBYOBRequest byobRequest);

  @JsProperty
  void setDesiredSize(int desiredSize);
}
