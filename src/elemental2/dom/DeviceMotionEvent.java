package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DeviceMotionEvent extends Event {
  public DeviceAcceleration acceleration;
  public DeviceAcceleration accelerationIncludingGravity;
  public double interval;
  public DeviceRotationRate rotationRate;

  public DeviceMotionEvent() {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }
}
