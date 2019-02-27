package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Selection {
  public Node anchorNode;
  public int anchorOffset;
  public Node focusNode;
  public int focusOffset;
  public boolean isCollapsed;
  public int rangeCount;
  public String type;

  public native void addRange(Range range);

  public native void collapse(Node node, int index);

  public native void collapse(Node node);

  public native void collapseToEnd();

  public native void collapseToStart();

  public native boolean containsNode(Node node, boolean partlyContained);

  public native boolean containsNode(Node node);

  public native void deleteFromDocument();

  public native void empty();

  public native void extend(Node node, int offset);

  public native void extend(Node node);

  public native Range getRangeAt(int index);

  public native void removeAllRanges();

  public native void removeRange(Range range);

  public native void selectAllChildren(Node node);

  public native void setBaseAndExtent(
      Node anchorNode, int anchorOffset, Node focusNode, int focusOffset);

  public native void setPosition(Node node, int offset);

  public native void setPosition(Node node);
}
