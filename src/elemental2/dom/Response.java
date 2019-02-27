package elemental2.dom;

import elemental2.core.ArrayBuffer;
import elemental2.core.ArrayBufferView;
import elemental2.promise.Promise;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Response implements Body {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface ConstructorBodyUnionType {
    @JsOverlay
    static Response.ConstructorBodyUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default ArrayBuffer asArrayBuffer() {
      return Js.cast(this);
    }

    @JsOverlay
    default ArrayBufferView asArrayBufferView() {
      return Js.cast(this);
    }

    @JsOverlay
    default Blob asBlob() {
      return Js.cast(this);
    }

    @JsOverlay
    default FormData asFormData() {
      return Js.cast(this);
    }

    @JsOverlay
    default ReadableStream asReadableStream() {
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
    default boolean isArrayBufferView() {
      return (Object) this instanceof ArrayBufferView;
    }

    @JsOverlay
    default boolean isBlob() {
      return (Object) this instanceof Blob;
    }

    @JsOverlay
    default boolean isFormData() {
      return (Object) this instanceof FormData;
    }

    @JsOverlay
    default boolean isReadableStream() {
      return (Object) this instanceof ReadableStream;
    }

    @JsOverlay
    default boolean isString() {
      return (Object) this instanceof String;
    }
  }

  public static native Response error();

  public static native Response redirect(String url, double status);

  public static native Response redirect(String url);

  public ReadableStream body;
  public Object bodyUsed;
  public Headers headers;
  public boolean ok;
  public boolean redirected;
  public int status;
  public String statusText;
  public Promise<Headers> trailer;
  public String type;
  public String url;

  public Response() {}

  public Response(ArrayBuffer body, ResponseInit init) {}

  public Response(ArrayBuffer body) {}

  public Response(ArrayBufferView body, ResponseInit init) {}

  public Response(ArrayBufferView body) {}

  public Response(Blob body, ResponseInit init) {}

  public Response(Blob body) {}

  public Response(Response.ConstructorBodyUnionType body, ResponseInit init) {}

  public Response(Response.ConstructorBodyUnionType body) {}

  public Response(FormData body, ResponseInit init) {}

  public Response(FormData body) {}

  public Response(ReadableStream body, ResponseInit init) {}

  public Response(ReadableStream body) {}

  public Response(String body, ResponseInit init) {}

  public Response(String body) {}

  public native Promise<ArrayBuffer> arrayBuffer();

  public native Promise<Blob> blob();

  @JsMethod(name = "clone")
  public native Response clone_();

  public native Promise<FormData> formData();

  @JsProperty
  public native boolean isBodyUsed();

  public native Promise<Object> json();

  @JsProperty
  public native void setBodyUsed(boolean bodyUsed);

  public native Promise<String> text();
}
