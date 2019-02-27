package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class HTMLSlotElement extends HTMLElement {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface AssignedNodesOptionsType {
    @JsOverlay
    static HTMLSlotElement.AssignedNodesOptionsType create() {
      return Js.uncheckedCast(JsPropertyMap.of());
    }

    @JsProperty
    boolean isFlatten();

    @JsProperty
    void setFlatten(boolean flatten);
  }

  public native Node[] assignedNodes();

  public native Node[] assignedNodes(HTMLSlotElement.AssignedNodesOptionsType options);
}
