package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class SQLResultSet {
  public int insertId;
  public SQLResultSetRowList rows;
  public int rowsAffected;
}
