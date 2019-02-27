package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface RTCDTMFSender {
  @JsFunction
  public interface OntonechangeFn {
    Object onInvoke(RTCDTMFToneChangeEvent p0);
  }

  @JsProperty
  RTCDTMFSender.OntonechangeFn getOntonechange();

  @JsProperty
  String getToneBuffer();

  Object insertDTMF(String tones, double duration, double interToneGap);

  Object insertDTMF(String tones, double duration);

  Object insertDTMF(String tones);

  @JsProperty
  void setOntonechange(RTCDTMFSender.OntonechangeFn ontonechange);
}
