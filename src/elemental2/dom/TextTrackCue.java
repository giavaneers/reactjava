package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class TextTrackCue {
  public double endTime;
  public String id;
  public double startTime;
  public String text;

  public TextTrackCue(double startTime, double endTime, String text) {}
}
