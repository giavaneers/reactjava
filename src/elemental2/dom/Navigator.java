package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Navigator {
  @JsFunction
  public interface WebkitGetUserMediaErrorCallbackFn {
    Object onInvoke(NavigatorUserMediaError p0);
  }

  @JsFunction
  public interface WebkitGetUserMediaSuccessCallbackFn {
    Object onInvoke(MediaStream p0);
  }

  public String appCodeName;
  public String appName;
  public String appVersion;
  public boolean cookieEnabled;
  public Geolocation geolocation;
  public String language;
  public MediaDevices mediaDevices;
  public MimeTypeArray mimeTypes;
  public boolean onLine;
  public String platform;
  public PluginArray plugins;
  public String product;
  public ServiceWorkerContainer serviceWorker;
  public String userAgent;

  public native Promise<BatteryManager> getBattery();

  public native boolean javaEnabled();

  public native void registerContentHandler(String mimeType, String url, String title);

  public native void registerProtocolHandler(String scheme, String url, String title);

  public native boolean taintEnabled();

  public native void unregisterContentHandler(String mimeType, String url);

  public native void unregisterProtocolHandler(String scheme, String url);

  public native void webkitGetUserMedia(
      MediaStreamConstraints constraints,
      Navigator.WebkitGetUserMediaSuccessCallbackFn successCallback,
      Navigator.WebkitGetUserMediaErrorCallbackFn errorCallback);

  public native void webkitGetUserMedia(
      MediaStreamConstraints constraints,
      Navigator.WebkitGetUserMediaSuccessCallbackFn successCallback);
}
