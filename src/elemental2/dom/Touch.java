package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Touch {
  public double clientX;
  public double clientY;
  public double force;
  public int identifier;
  public double pageX;
  public double pageY;
  public double radiusX;
  public double radiusY;
  public double rotationAngle;
  public double screenX;
  public double screenY;
  public EventTarget target;

  public Touch(TouchInitDict touchInitDict) {}
}
