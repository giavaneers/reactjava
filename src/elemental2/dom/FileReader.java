package elemental2.dom;

import elemental2.core.ArrayBuffer;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class FileReader implements EventTarget {
  @JsFunction
  public interface OnabortFn {
    Object onInvoke(ProgressEvent p0);
  }

  @JsFunction
  public interface OnerrorFn {
    Object onInvoke(ProgressEvent p0);
  }

  @JsFunction
  public interface OnloadFn {
    Object onInvoke(ProgressEvent p0);
  }

  @JsFunction
  public interface OnloadendFn {
    Object onInvoke(ProgressEvent p0);
  }

  @JsFunction
  public interface OnloadstartFn {
    Object onInvoke(ProgressEvent p0);
  }

  @JsFunction
  public interface OnprogressFn {
    Object onInvoke(ProgressEvent p0);
  }

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface ResultUnionType {
    @JsOverlay
    static FileReader.ResultUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default ArrayBuffer asArrayBuffer() {
      return Js.cast(this);
    }

    @JsOverlay
    default Blob asBlob() {
      return Js.cast(this);
    }

    @JsOverlay
    default String asString() {
      return Js.asString(this);
    }

    @JsOverlay
    default boolean isArrayBuffer() {
      return (Object) this instanceof ArrayBuffer;
    }

    @JsOverlay
    default boolean isBlob() {
      return (Object) this instanceof Blob;
    }

    @JsOverlay
    default boolean isString() {
      return (Object) this instanceof String;
    }
  }

  public static double DONE;
  public static double EMPTY;
  public static double LOADING;
  public DOMError error;
  public FileReader.OnabortFn onabort;
  public FileReader.OnerrorFn onerror;
  public FileReader.OnloadFn onload;
  public FileReader.OnloadendFn onloadend;
  public FileReader.OnloadstartFn onloadstart;
  public FileReader.OnprogressFn onprogress;
  public int readyState;
  public FileReader.ResultUnionType result;

  public native void abort();

  public native void addEventListener(
      String type, EventListener listener, EventTarget.AddEventListenerOptionsUnionType options);

  public native void addEventListener(String type, EventListener listener);

  public native boolean dispatchEvent(Event evt);

  public native void readAsArrayBuffer(Blob blob);

  public native void readAsBinaryString(Blob blob);

  public native void readAsDataURL(Blob blob);

  public native void readAsText(Blob blob, String encoding);

  public native void readAsText(Blob blob);

  public native void removeEventListener(
      String type, EventListener listener, EventTarget.RemoveEventListenerOptionsUnionType options);

  public native void removeEventListener(String type, EventListener listener);
}
