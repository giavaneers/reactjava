package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface MutationObserverInit {
  @JsOverlay
  static MutationObserverInit create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  String[] getAttributeFilter();

  @JsProperty
  boolean isAttributeOldValue();

  @JsProperty
  boolean isAttributes();

  @JsProperty
  boolean isCharacterData();

  @JsProperty
  boolean isCharacterDataOldValue();

  @JsProperty
  boolean isChildList();

  @JsProperty
  boolean isSubtree();

  @JsProperty
  void setAttributeFilter(String[] attributeFilter);

  @JsProperty
  void setAttributeOldValue(boolean attributeOldValue);

  @JsProperty
  void setAttributes(boolean attributes);

  @JsProperty
  void setCharacterData(boolean characterData);

  @JsProperty
  void setCharacterDataOldValue(boolean characterDataOldValue);

  @JsProperty
  void setChildList(boolean childList);

  @JsProperty
  void setSubtree(boolean subtree);
}
