package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface VideoPlaybackQuality {
  @JsOverlay
  static VideoPlaybackQuality create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  int getCorruptedVideoFrames();

  @JsProperty
  double getCreationTime();

  @JsProperty
  int getDroppedVideoFrames();

  @JsProperty
  double getTotalFrameDelay();

  @JsProperty
  int getTotalVideoFrames();

  @JsProperty
  void setCorruptedVideoFrames(int corruptedVideoFrames);

  @JsProperty
  void setCreationTime(double creationTime);

  @JsProperty
  void setDroppedVideoFrames(int droppedVideoFrames);

  @JsProperty
  void setTotalFrameDelay(double totalFrameDelay);

  @JsProperty
  void setTotalVideoFrames(int totalVideoFrames);
}
