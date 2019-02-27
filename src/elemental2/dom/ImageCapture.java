package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class ImageCapture {
  public MediaStreamTrack track;

  public ImageCapture(MediaStreamTrack videoTrack) {}

  public native Promise<PhotoCapabilities> getPhotoCapabilities();

  public native Promise<ImageBitmap> grabFrame();

  public native Promise<Blob> takePhoto();

  public native Promise<Blob> takePhoto(PhotoSettings photoSettings);
}
