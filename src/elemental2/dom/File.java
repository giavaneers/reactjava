package elemental2.dom;

import elemental2.core.ArrayBuffer;
import elemental2.core.JsDate;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class File extends Blob {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface ConstructorContentsArrayUnionType {
    @JsOverlay
    static File.ConstructorContentsArrayUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default ArrayBuffer asArrayBuffer() {
      return Js.cast(this);
    }

    @JsOverlay
    default Blob asBlob() {
      return Js.cast(this);
    }

    @JsOverlay
    default String asString() {
      return Js.asString(this);
    }

    @JsOverlay
    default boolean isArrayBuffer() {
      return (Object) this instanceof ArrayBuffer;
    }

    @JsOverlay
    default boolean isBlob() {
      return (Object) this instanceof Blob;
    }

    @JsOverlay
    default boolean isString() {
      return (Object) this instanceof String;
    }
  }

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface FilePropertiesType {
    @JsOverlay
    static File.FilePropertiesType create() {
      return Js.uncheckedCast(JsPropertyMap.of());
    }

    @JsProperty
    double getLastModified();

    @JsProperty
    String getType();

    @JsProperty
    void setLastModified(double lastModified);

    @JsProperty
    void setType(String type);
  }

  public double lastModified;
  public JsDate lastModifiedDate;
  public String name;
  public String webkitRelativePath;

  public File() {}

  public File(
      File.ConstructorContentsArrayUnionType[] contents,
      String name,
      File.FilePropertiesType properties) {}

  public File(File.ConstructorContentsArrayUnionType[] contents, String name) {}

  public File(File.ConstructorContentsArrayUnionType[] contents) {}
}
