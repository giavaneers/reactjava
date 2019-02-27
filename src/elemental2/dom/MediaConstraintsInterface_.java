package elemental2.dom;

import elemental2.core.JsObject;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface MediaConstraintsInterface_ {
  @JsProperty
  JsObject getMandatory();

  @JsProperty
  JsObject[] getOptional();

  @JsProperty
  void setMandatory(JsObject mandatory);

  @JsOverlay
  default void setMandatory(Object mandatory) {
    setMandatory(Js.<JsObject>uncheckedCast(mandatory));
  }

  @JsProperty
  void setOptional(JsObject[] optional);
}
