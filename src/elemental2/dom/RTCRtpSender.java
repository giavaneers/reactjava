package elemental2.dom;

import elemental2.core.JsObject;
import elemental2.promise.Promise;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface RTCRtpSender {
  @JsProperty
  RTCDTMFSender getDtmf();

  JsObject getParameters();

  @JsProperty
  MediaStreamTrack getTrack();

  Object replaceTrack(MediaStreamTrack track);

  Promise<Void> setParameters(JsObject params);

  @JsOverlay
  default Promise<Void> setParameters(Object params) {
    return setParameters(Js.<JsObject>uncheckedCast(params));
  }
}
