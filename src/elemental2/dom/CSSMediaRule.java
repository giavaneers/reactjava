package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class CSSMediaRule extends CSSRule {
  public CSSRuleList cssRules;
  public MediaList media;

  public native void deleteRule(int index);

  public native double insertRule(String rule, int index);
}
