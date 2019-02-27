package elemental2.dom;

import elemental2.core.JsDate;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface RTCRtpContributingSource {
  @JsProperty
  double getSource();

  @JsProperty
  JsDate getTimestamp();

  @JsProperty
  void setSource(double source);

  @JsProperty
  void setTimestamp(JsDate timestamp);
}
