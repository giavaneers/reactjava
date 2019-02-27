package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface CanvasDrawingStyles {
  @JsProperty
  String getFont();

  @JsProperty
  String getLineCap();

  double[] getLineDash();

  @JsProperty
  String getLineJoin();

  @JsProperty
  double getLineWidth();

  @JsProperty
  double getMiterLimit();

  @JsProperty
  String getTextAlign();

  @JsProperty
  String getTextBaseline();

  @JsProperty
  void setFont(String font);

  @JsProperty
  void setLineCap(String lineCap);

  void setLineDash(double[] p0);

  @JsProperty
  void setLineJoin(String lineJoin);

  @JsProperty
  void setLineWidth(double lineWidth);

  @JsProperty
  void setMiterLimit(double miterLimit);

  @JsProperty
  void setTextAlign(String textAlign);

  @JsProperty
  void setTextBaseline(String textBaseline);
}
