package elemental2.dom;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class SQLTransaction {
  @JsFunction
  public interface ExecuteSqlErrorCallbackFn {
    boolean onInvoke(SQLTransaction p0, SQLError p1);
  }

  public native void executeSql(
      String sqlStatement,
      Object[] queryArgs,
      SQLStatementCallback callback,
      SQLTransaction.ExecuteSqlErrorCallbackFn errorCallback);

  public native void executeSql(
      String sqlStatement, Object[] queryArgs, SQLStatementCallback callback);

  public native void executeSql(String sqlStatement, Object[] queryArgs);

  public native void executeSql(String sqlStatement);
}
