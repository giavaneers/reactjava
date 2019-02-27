package elemental2.dom;

import elemental2.promise.Promise;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class SyncManager {
  public native Promise<String[]> getTags();

  public native Promise<Void> register(String tag);
}
