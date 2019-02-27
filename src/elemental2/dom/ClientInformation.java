package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class ClientInformation {
  public boolean onLine;

  public native void registerContentHandler(String mimeType, String uri, String title);

  public native void registerProtocolHandler(String protocol, String uri, String title);
}
