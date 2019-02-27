package elemental2.dom;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface MediaTrackCapabilities {
  @JsProperty
  double getAspectRatio();

  @JsProperty
  MediaSettingsRange getBrightness();

  @JsProperty
  double getChannelCount();

  @JsProperty
  MediaSettingsRange getColorTemperature();

  @JsProperty
  MediaSettingsRange getContrast();

  @JsProperty
  String getDeviceId();

  @JsProperty
  boolean[] getEchoCancellation();

  @JsProperty
  MediaSettingsRange getExposureCompensation();

  @JsProperty
  String[] getExposureMode();

  @JsProperty
  String[] getFacingMode();

  @JsProperty
  String[] getFocusMode();

  @JsProperty
  double getFrameRate();

  @JsProperty
  String getGroupId();

  @JsProperty
  double getHeight();

  @JsProperty
  MediaSettingsRange getIso();

  @JsProperty
  double getLatency();

  @JsProperty
  double getSampleRate();

  @JsProperty
  int getSampleSize();

  @JsProperty
  MediaSettingsRange getSaturation();

  @JsProperty
  MediaSettingsRange getSharpness();

  @JsProperty
  double getVolume();

  @JsProperty
  String[] getWhiteBalanceMode();

  @JsProperty
  double getWidth();

  @JsProperty
  MediaSettingsRange getZoom();

  @JsProperty
  boolean isTorch();

  @JsProperty
  void setAspectRatio(double aspectRatio);

  @JsProperty
  void setBrightness(MediaSettingsRange brightness);

  @JsProperty
  void setChannelCount(double channelCount);

  @JsProperty
  void setColorTemperature(MediaSettingsRange colorTemperature);

  @JsProperty
  void setContrast(MediaSettingsRange contrast);

  @JsProperty
  void setDeviceId(String deviceId);

  @JsProperty
  void setEchoCancellation(boolean[] echoCancellation);

  @JsProperty
  void setExposureCompensation(MediaSettingsRange exposureCompensation);

  @JsProperty
  void setExposureMode(String[] exposureMode);

  @JsProperty
  void setFacingMode(String[] facingMode);

  @JsProperty
  void setFocusMode(String[] focusMode);

  @JsProperty
  void setFrameRate(double frameRate);

  @JsProperty
  void setGroupId(String groupId);

  @JsProperty
  void setHeight(double height);

  @JsProperty
  void setIso(MediaSettingsRange iso);

  @JsProperty
  void setLatency(double latency);

  @JsProperty
  void setSampleRate(double sampleRate);

  @JsProperty
  void setSampleSize(int sampleSize);

  @JsProperty
  void setSaturation(MediaSettingsRange saturation);

  @JsProperty
  void setSharpness(MediaSettingsRange sharpness);

  @JsProperty
  void setTorch(boolean torch);

  @JsProperty
  void setVolume(double volume);

  @JsProperty
  void setWhiteBalanceMode(String[] whiteBalanceMode);

  @JsProperty
  void setWidth(double width);

  @JsProperty
  void setZoom(MediaSettingsRange zoom);
}
