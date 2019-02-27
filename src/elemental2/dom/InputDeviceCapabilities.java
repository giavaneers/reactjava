package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class InputDeviceCapabilities {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface InputDeviceCapabilitiesOptionsType {
    @JsOverlay
    static InputDeviceCapabilities.InputDeviceCapabilitiesOptionsType create() {
      return Js.uncheckedCast(JsPropertyMap.of());
    }

    @JsProperty
    String getFiresTouchEvents();

    @JsProperty
    String getPointerMovementScrolls();

    @JsProperty
    void setFiresTouchEvents(String firesTouchEvents);

    @JsProperty
    void setPointerMovementScrolls(String pointerMovementScrolls);
  }

  public boolean firesTouchEvents;
  public boolean pointerMovementScrolls;

  public InputDeviceCapabilities() {}

  public InputDeviceCapabilities(
      InputDeviceCapabilities.InputDeviceCapabilitiesOptionsType options) {}
}
