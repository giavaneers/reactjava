package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Path2D implements CanvasPathMethods {
  public native void addPath(Path2D path);

  public native void arc(
      double x,
      double y,
      double radius,
      double startAngle,
      double endAngle,
      boolean optAnticlockwise);

  public native void arc(double x, double y, double radius, double startAngle, double endAngle);

  public native void arcTo(double x1, double y1, double x2, double y2, double radius);

  public native void bezierCurveTo(
      double cp1x, double cp1y, double cp2x, double cp2y, double x, double y);

  public native void closePath();

  public native void lineTo(double x, double y);

  public native void moveTo(double x, double y);

  public native void quadraticCurveTo(double cpx, double cpy, double x, double y);

  public native void rect(double x, double y, double w, double h);
}
