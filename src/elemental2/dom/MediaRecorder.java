package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class MediaRecorder implements EventTarget {
  @JsFunction
  public interface OndataavailableFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnerrorFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnpauseFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnresumeFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnstartFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnstopFn {
    Object onInvoke(Event p0);
  }

  public static native boolean isTypeSupported(String type);

  public int audioBitsPerSecond;
  public String mimeType;
  public MediaRecorder.OndataavailableFn ondataavailable;
  public MediaRecorder.OnerrorFn onerror;
  public MediaRecorder.OnpauseFn onpause;
  public MediaRecorder.OnresumeFn onresume;
  public MediaRecorder.OnstartFn onstart;
  public MediaRecorder.OnstopFn onstop;
  public String state;
  public MediaStream stream;
  public int videoBitsPerSecond;

  public MediaRecorder(MediaStream stream, MediaRecorderOptions options) {}

  public MediaRecorder(MediaStream stream) {}

  public native void addEventListener(
      String type, EventListener listener, EventTarget.AddEventListenerOptionsUnionType useCapture);

  public native void addEventListener(String type, EventListener listener);

  public native boolean dispatchEvent(Event evt);

  public native void pause();

  public native void removeEventListener(
      String type,
      EventListener listener,
      EventTarget.RemoveEventListenerOptionsUnionType useCapture);

  public native void removeEventListener(String type, EventListener listener);

  public native void requestData();

  public native void resume();

  public native Object start();

  public native Object start(int timeslice);

  public native void stop();
}
