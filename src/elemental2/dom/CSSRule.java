package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class CSSRule {
  public static double CHARSET_RULE;
  public static double FONT_FACE_RULE;
  public static double IMPORT_RULE;
  public static double MEDIA_RULE;
  public static double PAGE_RULE;
  public static double STYLE_RULE;
  public static double UNKNOWN_RULE;
  public String cssText;
  public CSSRule parentRule;
  public CSSStyleSheet parentStyleSheet;
  public CSSStyleDeclaration style;
  public int type;
}
