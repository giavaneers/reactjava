package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLVideoElement extends HTMLMediaElement {
  public int height;
  public String poster;
  public int videoHeight;
  public int videoWidth;
  public int webkitDecodedFrameCount;
  public boolean webkitDisplayingFullscreen;
  public int webkitDroppedFrameCount;
  public boolean webkitSupportsFullscreen;
  public int width;

  public native VideoPlaybackQuality getVideoPlaybackQuality();

  public native void webkitEnterFullScreen();

  public native void webkitEnterFullscreen();

  public native void webkitExitFullScreen();

  public native void webkitExitFullscreen();
}
