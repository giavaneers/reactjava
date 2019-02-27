package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class XMLHttpRequestUpload extends XMLHttpRequestEventTarget {
  @JsFunction
  public interface OnprogressFn {
    void onInvoke(ProgressEvent p0);
  }

  public XMLHttpRequestUpload.OnprogressFn onprogress;
}
