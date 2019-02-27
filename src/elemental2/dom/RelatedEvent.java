package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class RelatedEvent extends Event {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface RelatedEventEventInitDictType {
    @JsOverlay
    static RelatedEvent.RelatedEventEventInitDictType create() {
      return Js.uncheckedCast(JsPropertyMap.of());
    }

    @JsProperty
    EventTarget getRelatedTarget();

    @JsProperty
    void setRelatedTarget(EventTarget relatedTarget);
  }

  public EventTarget relatedTarget;

  public RelatedEvent(String type, RelatedEvent.RelatedEventEventInitDictType eventInitDict) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public RelatedEvent(String type) {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }
}
