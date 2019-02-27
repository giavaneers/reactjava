package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class DOMException {
  public static double DOMSTRING_SIZE_ERR;
  public static double HIERARCHY_REQUEST_ERR;
  public static double INDEX_SIZE_ERR;
  public static double INUSE_ATTRIBUTE_ERR;
  public static double INVALID_ACCESS_ERR;
  public static double INVALID_CHARACTER_ERR;
  public static double INVALID_MODIFICATION_ERR;
  public static double INVALID_STATE_ERR;
  public static double NAMESPACE_ERR;
  public static double NOT_FOUND_ERR;
  public static double NOT_SUPPORTED_ERR;
  public static double NO_DATA_ALLOWED_ERR;
  public static double NO_MODIFICATION_ALLOWED_ERR;
  public static double SYNTAX_ERR;
  public static double TYPE_MISMATCH_ERR;
  public static double VALIDATION_ERR;
  public static double WRONG_DOCUMENT_ERR;
  public int code;
}
