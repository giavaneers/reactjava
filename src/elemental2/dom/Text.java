package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Text extends CharacterData {
  public String wholeText;

  public Text() {}

  public Text(String contents) {}

  public native NodeList<Node> getDestinationInsertionPoints();

  public native Text replaceWholeText(String newText);

  public native Text splitText(int offset);
}
