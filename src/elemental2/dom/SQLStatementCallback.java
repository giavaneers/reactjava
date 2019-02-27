package elemental2.dom;

import jsinterop.annotations.JsFunction;

@JsFunction
public interface SQLStatementCallback {
  void onInvoke(SQLTransaction p0, SQLResultSet p1);
}
