package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HashChangeEvent extends Event {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface HashChangeEventEventInitDictType {
    @JsOverlay
    static HashChangeEvent.HashChangeEventEventInitDictType create() {
      return Js.uncheckedCast(JsPropertyMap.of());
    }

    @JsProperty
    String getNewURL();

    @JsProperty
    String getOldURL();

    @JsProperty
    void setNewURL(String newURL);

    @JsProperty
    void setOldURL(String oldURL);
  }

  public String newURL;
  public String oldURL;

  public HashChangeEvent(
      String type, HashChangeEvent.HashChangeEventEventInitDictType eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public HashChangeEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public native void initHashChangeEvent(
      String typeArg,
      boolean canBubbleArg,
      boolean cancelableArg,
      String oldURLArg,
      String newURLArg);
}
