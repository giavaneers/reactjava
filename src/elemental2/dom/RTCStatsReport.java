package elemental2.dom;

import elemental2.core.JsDate;
import elemental2.core.JsIterator;
import elemental2.core.JsObject;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface RTCStatsReport {
  @JsFunction
  public interface ForEachCallbackFn {
    Object onInvoke(JsObject p0);

    @JsOverlay
    default Object onInvoke(Object p0) {
      return onInvoke(Js.<JsObject>uncheckedCast(p0));
    }
  }

  <SCOPE> Object forEach(RTCStatsReport.ForEachCallbackFn callback, SCOPE thisObj);

  Object forEach(RTCStatsReport.ForEachCallbackFn callback);

  JsObject get(String key);

  @JsProperty
  String getId();

  @JsProperty
  RTCStatsReport getLocal();

  @JsProperty
  RTCStatsReport getRemote();

  @JsProperty
  JsDate getTimestamp();

  @JsProperty
  String getType();

  JsIterator<String> keys();

  String[] names();

  String stat(String name);
}
