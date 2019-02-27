package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface EventTarget {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface AddEventListenerOptionsUnionType {
    @JsOverlay
    static EventTarget.AddEventListenerOptionsUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default AddEventListenerOptions asAddEventListenerOptions() {
      return Js.cast(this);
    }

    @JsOverlay
    default boolean asBoolean() {
      return Js.asBoolean(this);
    }

    @JsOverlay
    default boolean isBoolean() {
      return (Object) this instanceof Boolean;
    }
  }

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface RemoveEventListenerOptionsUnionType {
    @JsOverlay
    static EventTarget.RemoveEventListenerOptionsUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default boolean asBoolean() {
      return Js.asBoolean(this);
    }

    @JsOverlay
    default EventListenerOptions asEventListenerOptions() {
      return Js.cast(this);
    }

    @JsOverlay
    default boolean isBoolean() {
      return (Object) this instanceof Boolean;
    }
  }

  @JsOverlay
  default void addEventListener(
      String type, EventListener listener, AddEventListenerOptions options) {
    addEventListener(
        type, listener, Js.<EventTarget.AddEventListenerOptionsUnionType>uncheckedCast(options));
  }

  void addEventListener(
      String type, EventListener listener, EventTarget.AddEventListenerOptionsUnionType options);

  @JsOverlay
  default void addEventListener(String type, EventListener listener, boolean options) {
    addEventListener(
        type, listener, Js.<EventTarget.AddEventListenerOptionsUnionType>uncheckedCast(options));
  }

  void addEventListener(String type, EventListener listener);

  boolean dispatchEvent(Event evt);

  @JsOverlay
  default void removeEventListener(
      String type, EventListener listener, EventListenerOptions options) {
    removeEventListener(
        type, listener, Js.<EventTarget.RemoveEventListenerOptionsUnionType>uncheckedCast(options));
  }

  void removeEventListener(
      String type, EventListener listener, EventTarget.RemoveEventListenerOptionsUnionType options);

  @JsOverlay
  default void removeEventListener(String type, EventListener listener, boolean options) {
    removeEventListener(
        type, listener, Js.<EventTarget.RemoveEventListenerOptionsUnionType>uncheckedCast(options));
  }

  void removeEventListener(String type, EventListener listener);
}
