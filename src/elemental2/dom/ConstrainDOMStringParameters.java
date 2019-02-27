package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface ConstrainDOMStringParameters {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface GetExactUnionType {
    @JsOverlay
    static ConstrainDOMStringParameters.GetExactUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default String asString() {
      return Js.asString(this);
    }

    @JsOverlay
    default String[] asStringArray() {
      return Js.cast(this);
    }

    @JsOverlay
    default boolean isString() {
      return (Object) this instanceof String;
    }

    @JsOverlay
    default boolean isStringArray() {
      return (Object) this instanceof Object[];
    }
  }

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface GetIdealUnionType {
    @JsOverlay
    static ConstrainDOMStringParameters.GetIdealUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default String asString() {
      return Js.asString(this);
    }

    @JsOverlay
    default String[] asStringArray() {
      return Js.cast(this);
    }

    @JsOverlay
    default boolean isString() {
      return (Object) this instanceof String;
    }

    @JsOverlay
    default boolean isStringArray() {
      return (Object) this instanceof Object[];
    }
  }

  @JsOverlay
  static ConstrainDOMStringParameters create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  ConstrainDOMStringParameters.GetExactUnionType getExact();

  @JsProperty
  ConstrainDOMStringParameters.GetIdealUnionType getIdeal();

  @JsProperty
  void setExact(ConstrainDOMStringParameters.GetExactUnionType exact);

  @JsOverlay
  default void setExact(String exact) {
    setExact(Js.<ConstrainDOMStringParameters.GetExactUnionType>uncheckedCast(exact));
  }

  @JsOverlay
  default void setExact(String[] exact) {
    setExact(Js.<ConstrainDOMStringParameters.GetExactUnionType>uncheckedCast(exact));
  }

  @JsProperty
  void setIdeal(ConstrainDOMStringParameters.GetIdealUnionType ideal);

  @JsOverlay
  default void setIdeal(String ideal) {
    setIdeal(Js.<ConstrainDOMStringParameters.GetIdealUnionType>uncheckedCast(ideal));
  }

  @JsOverlay
  default void setIdeal(String[] ideal) {
    setIdeal(Js.<ConstrainDOMStringParameters.GetIdealUnionType>uncheckedCast(ideal));
  }
}
