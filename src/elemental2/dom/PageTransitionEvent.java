package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PageTransitionEvent extends Event {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface PageTransitionEventEventInitDictType {
    @JsOverlay
    static PageTransitionEvent.PageTransitionEventEventInitDictType create() {
      return Js.uncheckedCast(JsPropertyMap.of());
    }

    @JsProperty
    boolean isPersisted();

    @JsProperty
    void setPersisted(boolean persisted);
  }

  public boolean persisted;

  public PageTransitionEvent(
      String type, PageTransitionEvent.PageTransitionEventEventInitDictType eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public PageTransitionEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public native void initPageTransitionEvent(
      String typeArg, boolean canBubbleArg, boolean cancelableArg, Object persistedArg);
}
