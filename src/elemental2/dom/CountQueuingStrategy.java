package elemental2.dom;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class CountQueuingStrategy {
  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface CountQueuingStrategyConfigType {
    @JsOverlay
    static CountQueuingStrategy.CountQueuingStrategyConfigType create() {
      return Js.uncheckedCast(JsPropertyMap.of());
    }

    @JsProperty
    double getHighWaterMark();

    @JsProperty
    void setHighWaterMark(double highWaterMark);
  }

  public CountQueuingStrategy(CountQueuingStrategy.CountQueuingStrategyConfigType config) {}

  public native int size(Object chunk);
}
