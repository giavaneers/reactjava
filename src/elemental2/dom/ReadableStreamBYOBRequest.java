package elemental2.dom;

import elemental2.core.ArrayBufferView;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface ReadableStreamBYOBRequest {
  @JsProperty
  ArrayBufferView getView();

  void respond(double bytesWritten);

  void respondWithNewView(ArrayBufferView view);

  @JsProperty
  void setView(ArrayBufferView view);
}
