package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface MediaDevices extends EventTarget {
  Promise<MediaDeviceInfo[]> enumerateDevices();

  MediaTrackSupportedConstraints getSupportedConstraints();

  Promise<MediaStream> getUserMedia(MediaStreamConstraints constraints);
}
