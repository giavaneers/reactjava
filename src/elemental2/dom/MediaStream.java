package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class MediaStream implements EventTarget {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface ConstructorStreamOrTracksUnionType {
    @JsOverlay
    static MediaStream.ConstructorStreamOrTracksUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default MediaStream asMediaStream() {
      return Js.cast(this);
    }

    @JsOverlay
    default MediaStreamTrack[] asMediaStreamTrackArray() {
      return Js.cast(this);
    }

    @JsOverlay
    default boolean isMediaStream() {
      return (Object) this instanceof MediaStream;
    }

    @JsOverlay
    default boolean isMediaStreamTrackArray() {
      return (Object) this instanceof Object[];
    }
  }

  @JsFunction
  public interface OnactiveFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnaddtrackFn {
    Object onInvoke(MediaStreamTrackEvent p0);
  }

  @JsFunction
  public interface OnendedFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OninactiveFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnremovetrackFn {
    Object onInvoke(MediaStreamTrackEvent p0);
  }

  public boolean active;
  public boolean ended;
  public String id;
  public String label;
  public MediaStream.OnactiveFn onactive;
  public MediaStream.OnaddtrackFn onaddtrack;
  public MediaStream.OnendedFn onended;
  public MediaStream.OninactiveFn oninactive;
  public MediaStream.OnremovetrackFn onremovetrack;

  public MediaStream() {}

  public MediaStream(MediaStream.ConstructorStreamOrTracksUnionType streamOrTracks) {}

  public MediaStream(MediaStream streamOrTracks) {}

  public MediaStream(MediaStreamTrack[] streamOrTracks) {}

  public native void addEventListener(
      String type, EventListener listener, EventTarget.AddEventListenerOptionsUnionType useCapture);

  public native void addEventListener(String type, EventListener listener);

  public native void addTrack(MediaStreamTrack track);

  @JsMethod(name = "clone")
  public native MediaStream clone_();

  public native boolean dispatchEvent(Event evt);

  public native MediaStreamTrack[] getAudioTracks();

  public native MediaStreamTrack getTrackById(String trackId);

  public native MediaStreamTrack[] getTracks();

  public native MediaStreamTrack[] getVideoTracks();

  public native void removeEventListener(
      String type,
      EventListener listener,
      EventTarget.RemoveEventListenerOptionsUnionType useCapture);

  public native void removeEventListener(String type, EventListener listener);

  public native void removeTrack(MediaStreamTrack track);

  public native void stop();
}
