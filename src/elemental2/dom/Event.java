package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Event {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface ComposedPathArrayUnionType {
    @JsOverlay
    static Event.ComposedPathArrayUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default Document asDocument() {
      return Js.cast(this);
    }

    @JsOverlay
    default Element asElement() {
      return Js.cast(this);
    }

    @JsOverlay
    default ShadowRoot asShadowRoot() {
      return Js.cast(this);
    }

    @JsOverlay
    default Window asWindow() {
      return Js.cast(this);
    }

    @JsOverlay
    default boolean isDocument() {
      return (Object) this instanceof Document;
    }

    @JsOverlay
    default boolean isElement() {
      return (Object) this instanceof Element;
    }

    @JsOverlay
    default boolean isShadowRoot() {
      return (Object) this instanceof ShadowRoot;
    }

    @JsOverlay
    default boolean isWindow() {
      return (Object) this instanceof Window;
    }
  }

  @JsFunction
  public interface DeepPathFn {
    EventTarget[] onInvoke();
  }

  @JsOverlay public static final double AT_TARGET = Event__Constants.AT_TARGET;
  @JsOverlay public static final double BUBBLING_PHASE = Event__Constants.BUBBLING_PHASE;
  @JsOverlay public static final double CAPTURING_PHASE = Event__Constants.CAPTURING_PHASE;
  public boolean bubbles;
  public boolean cancelable;
  public boolean composed;
  public EventTarget currentTarget;
  public Event.DeepPathFn deepPath;
  public boolean defaultPrevented;
  public int eventPhase;
  public String namespaceURI;
  public Element[] path;
  public EventTarget target;
  public double timeStamp;
  public String type;

  public Event(String type, EventInit eventInitDict) {}

  public Event(String type) {}

  public native Event.ComposedPathArrayUnionType[] composedPath();

  public native void initEvent(String eventTypeArg, boolean canBubbleArg, boolean cancelableArg);

  public native void initEvent(String eventTypeArg, boolean canBubbleArg);

  public native void initEvent(String eventTypeArg);

  public native void preventDefault();

  public native void stopImmediatePropagation();

  public native void stopPropagation();
}
