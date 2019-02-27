package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface MediaDeviceInfo {
  @JsProperty
  String getDeviceId();

  @JsProperty
  String getGroupId();

  @JsProperty
  String getKind();

  @JsProperty
  String getLabel();
}
