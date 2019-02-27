package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Geolocation {
  @JsFunction
  public interface GetCurrentPositionErrorCallbackFn {
    Object onInvoke(GeolocationPositionError p0);
  }

  @JsFunction
  public interface GetCurrentPositionSuccessCallbackFn {
    Object onInvoke(GeolocationPosition p0);
  }

  @JsFunction
  public interface WatchPositionErrorCallbackFn {
    Object onInvoke(GeolocationPositionError p0);
  }

  @JsFunction
  public interface WatchPositionSuccessCallbackFn {
    Object onInvoke(GeolocationPosition p0);
  }

  public native void clearWatch(int watchId);

  public native void getCurrentPosition(
      Geolocation.GetCurrentPositionSuccessCallbackFn successCallback,
      Geolocation.GetCurrentPositionErrorCallbackFn errorCallback,
      GeolocationPositionOptions options);

  public native void getCurrentPosition(
      Geolocation.GetCurrentPositionSuccessCallbackFn successCallback,
      Geolocation.GetCurrentPositionErrorCallbackFn errorCallback);

  public native void getCurrentPosition(
      Geolocation.GetCurrentPositionSuccessCallbackFn successCallback);

  public native int watchPosition(
      Geolocation.WatchPositionSuccessCallbackFn successCallback,
      Geolocation.WatchPositionErrorCallbackFn errorCallback,
      GeolocationPositionOptions options);

  public native int watchPosition(
      Geolocation.WatchPositionSuccessCallbackFn successCallback,
      Geolocation.WatchPositionErrorCallbackFn errorCallback);

  public native int watchPosition(Geolocation.WatchPositionSuccessCallbackFn successCallback);
}
