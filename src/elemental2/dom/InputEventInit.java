package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface InputEventInit extends UIEventInit {
  @JsOverlay
  static InputEventInit create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  String getData();

  @JsProperty
  DataTransfer getDataTransfer();

  @JsProperty
  String getInputType();

  @JsProperty
  boolean isIsComposing();

  @JsProperty
  void setData(String data);

  @JsProperty
  void setDataTransfer(DataTransfer dataTransfer);

  @JsProperty
  void setInputType(String inputType);

  @JsProperty
  void setIsComposing(boolean isComposing);
}
