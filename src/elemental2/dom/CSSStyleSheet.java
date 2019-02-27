package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class CSSStyleSheet extends StyleSheet {
  public CSSRuleList cssRules;
  public CSSRule ownerRule;

  public native void deleteRule(int index);

  public native int insertRule(String rule, int index);
}
