package elemental2.dom;

import elemental2.core.JsObject;
import elemental2.promise.Promise;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface FontFaceSet extends EventTarget {
  @JsFunction
  public interface ForEachCallbackFn {
    Object onInvoke(FontFace p0, double p1, FontFaceSet p2);
  }

  @JsFunction
  public interface OnloadingFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnloadingdoneFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnloadingerrorFn {
    Object onInvoke(Event p0);
  }

  void add(FontFace value);

  boolean check(String font, String text);

  boolean check(String font);

  void clear();

  void delete(FontFace value);

  void forEach(FontFaceSet.ForEachCallbackFn callback, JsObject selfObj);

  @JsOverlay
  default void forEach(FontFaceSet.ForEachCallbackFn callback, Object selfObj) {
    forEach(callback, Js.<JsObject>uncheckedCast(selfObj));
  }

  void forEach(FontFaceSet.ForEachCallbackFn callback);

  @JsProperty
  FontFaceSet.OnloadingFn getOnloading();

  @JsProperty
  FontFaceSet.OnloadingdoneFn getOnloadingdone();

  @JsProperty
  FontFaceSet.OnloadingerrorFn getOnloadingerror();

  @JsProperty
  Promise<FontFaceSet> getReady();

  @JsProperty
  String getStatus();

  boolean has(FontFace font);

  Promise<FontFace[]> load(String font, String text);

  Promise<FontFace[]> load(String font);

  @JsProperty
  void setOnloading(FontFaceSet.OnloadingFn onloading);

  @JsProperty
  void setOnloadingdone(FontFaceSet.OnloadingdoneFn onloadingdone);

  @JsProperty
  void setOnloadingerror(FontFaceSet.OnloadingerrorFn onloadingerror);

  @JsProperty
  void setReady(Promise<FontFaceSet> ready);

  @JsProperty
  void setStatus(String status);
}
