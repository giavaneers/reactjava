package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface NavigatorUserMediaError {
  @JsProperty
  double getCode();

  @JsProperty
  String getConstraintName();

  @JsProperty
  String getMessage();

  @JsProperty
  String getName();

  @JsProperty(name = "PERMISSION_DENIED")
  double getPERMISSION_DENIED();

  @JsProperty
  void setCode(double code);

  @JsProperty
  void setConstraintName(String constraintName);

  @JsProperty
  void setMessage(String message);

  @JsProperty
  void setName(String name);
}
