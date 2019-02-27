package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class XPathEvaluator {
  public native void createExpression(String expr, XPathNSResolver resolver);

  public native void createExpression(String expr);

  public native void createNSResolver(Node nodeResolver);

  public native XPathResult evaluate(
      String expr, Node contextNode, XPathNSResolver resolver, int type, Object result);

  public native XPathResult evaluate(
      String expr, Node contextNode, XPathNSResolver resolver, int type);

  public native XPathResult evaluate(String expr, Node contextNode, XPathNSResolver resolver);

  public native XPathResult evaluate(String expr, Node contextNode);
}
