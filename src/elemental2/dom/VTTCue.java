package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class VTTCue extends TextTrackCue {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface LineUnionType {
    @JsOverlay
    static VTTCue.LineUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default double asDouble() {
      return Js.asDouble(this);
    }

    @JsOverlay
    default String asString() {
      return Js.asString(this);
    }

    @JsOverlay
    default boolean isDouble() {
      return (Object) this instanceof Double;
    }

    @JsOverlay
    default boolean isString() {
      return (Object) this instanceof String;
    }
  }

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface PositionUnionType {
    @JsOverlay
    static VTTCue.PositionUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default double asDouble() {
      return Js.asDouble(this);
    }

    @JsOverlay
    default String asString() {
      return Js.asString(this);
    }

    @JsOverlay
    default boolean isDouble() {
      return (Object) this instanceof Double;
    }

    @JsOverlay
    default boolean isString() {
      return (Object) this instanceof String;
    }
  }

  public String align;
  public VTTCue.LineUnionType line;
  public String lineAlign;
  public VTTCue.PositionUnionType position;
  public String positionAlign;
  public VTTRegion region;
  public int size;
  public boolean snapToLines;
  public String text;
  public String vertical;

  public VTTCue(double startTime, double endTime, String text) {
    // This call is only here for java compilation purpose.
    super(0, 0, (String) null);
  }

  public native DocumentFragment getCueAsHTML();
}
