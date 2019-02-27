package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PerformanceResourceTiming extends PerformanceEntry {
  public double connectEnd;
  public double connectStart;
  public int decodedBodySize;
  public double domainLookupEnd;
  public double domainLookupStart;
  public int encodedBodySize;
  public double fetchStart;
  public String initiatorType;
  public String nextHopProtocol;
  public double redirectEnd;
  public double redirectStart;
  public double requestStart;
  public double responseEnd;
  public double responseStart;
  public double secureConnectionStart;
  public int transferSize;
  public double workerStart;
}
