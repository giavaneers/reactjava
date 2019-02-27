package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class XPathExpression {
  public native Object evaluate(Node contextNode, int type, Object result);

  public native Object evaluate(Node contextNode, int type);

  public native Object evaluate(Node contextNode);
}
