package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class ValidityState {
  public boolean badInput;
  public boolean customError;
  public boolean patternMismatch;
  public boolean rangeOverflow;
  public boolean rangeUnderflow;
  public boolean stepMismatch;
  public boolean tooLong;
  public boolean tooShort;
  public boolean typeMismatch;
  public boolean valid;
  public boolean valueMissing;
}
