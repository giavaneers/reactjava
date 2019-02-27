package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DeviceOrientationEvent extends Event {
  public boolean absolute;
  public double alpha;
  public double beta;
  public double gamma;
  public double webkitCompassAccuracy;
  public double webkitCompassHeading;

  public DeviceOrientationEvent() {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }
}
