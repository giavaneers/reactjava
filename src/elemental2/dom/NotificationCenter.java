package elemental2.dom;

import elemental2.core.Function;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class NotificationCenter {
  public native double checkPermission();

  public native Notification createHTMLNotification(String url);

  public native Notification createNotification(String iconUrl, String title, String body);

  public native void requestPermission();

  public native void requestPermission(Function callback);
}
