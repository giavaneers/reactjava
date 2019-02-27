package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface TransformStream {
  @JsOverlay
  static TransformStream create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  ReadableStream getReadable();

  @JsProperty
  WritableStream getWritable();

  @JsProperty
  void setReadable(ReadableStream readable);

  @JsProperty
  void setWritable(WritableStream writable);
}
