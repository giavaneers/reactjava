package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DOMApplicationCache implements EventTarget {
  @JsFunction
  public interface OncachedFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OncheckingFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OndownloadingFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnerrorFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnnoupdateFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnprogressFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnupdatereadyFn {
    Object onInvoke(Event p0);
  }

  public double CHECKING;
  public double DOWNLOADING;
  public double IDLE;
  public double OBSOLETE;
  public double UNCACHED;
  public double UPDATEREADY;
  public DOMApplicationCache.OncachedFn oncached;
  public DOMApplicationCache.OncheckingFn onchecking;
  public DOMApplicationCache.OndownloadingFn ondownloading;
  public DOMApplicationCache.OnerrorFn onerror;
  public DOMApplicationCache.OnnoupdateFn onnoupdate;
  public DOMApplicationCache.OnprogressFn onprogress;
  public DOMApplicationCache.OnupdatereadyFn onupdateready;
  public double status;

  public native void addEventListener(
      String type, EventListener listener, EventTarget.AddEventListenerOptionsUnionType options);

  public native void addEventListener(String type, EventListener listener);

  public native boolean dispatchEvent(Event evt);

  public native void removeEventListener(
      String type, EventListener listener, EventTarget.RemoveEventListenerOptionsUnionType options);

  public native void removeEventListener(String type, EventListener listener);

  public native void swapCache();

  public native void update();
}
