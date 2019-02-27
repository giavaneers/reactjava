package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class MutationEvent extends Event {
  public int attrChange;
  public String attrName;
  public String newValue;
  public String prevValue;
  public Node relatedNode;

  public MutationEvent() {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public native void initMutationEvent(
      String typeArg,
      boolean canBubbleArg,
      boolean cancelableArg,
      Node relatedNodeArg,
      String prevValueArg,
      String newValueArg,
      String attrNameArg,
      int attrChangeArg);
}
