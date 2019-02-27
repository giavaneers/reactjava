package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PopStateEvent extends Event {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface PopStateEventEventInitDictType {
    @JsOverlay
    static PopStateEvent.PopStateEventEventInitDictType create() {
      return Js.uncheckedCast(JsPropertyMap.of());
    }

    @JsProperty
    Object getState();

    @JsProperty
    void setState(Object state);
  }

  public Object state;

  public PopStateEvent(String type, PopStateEvent.PopStateEventEventInitDictType eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public PopStateEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public native void initPopStateEvent(
      String typeArg, boolean canBubbleArg, boolean cancelableArg, Object stateArg);
}
