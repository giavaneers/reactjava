package elemental2.dom;

import elemental2.core.ArrayBuffer;
import elemental2.promise.Promise;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface Body {
  Promise<ArrayBuffer> arrayBuffer();

  Promise<Blob> blob();

  Promise<FormData> formData();

  @JsProperty
  boolean isBodyUsed();

  Promise<Object> json();

  @JsProperty
  void setBodyUsed(boolean bodyUsed);

  Promise<String> text();
}
