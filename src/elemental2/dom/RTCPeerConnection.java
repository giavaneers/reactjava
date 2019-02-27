package elemental2.dom;

import elemental2.core.JsObject;
import elemental2.promise.Promise;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class RTCPeerConnection implements EventTarget {
  @JsFunction
  public interface AddIceCandidateErrorCallbackFn {
    Object onInvoke(DOMException p0);
  }

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface GetStatsUnionType {
    @JsOverlay
    static RTCPeerConnection.GetStatsUnionType of(Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default Promise<RTCStatsReport> asPromise() {
      return Js.cast(this);
    }

    @JsOverlay
    default RTCStatsReport asRTCStatsReport() {
      return Js.cast(this);
    }

    @JsOverlay
    default boolean isPromise() {
      return (Object) this instanceof Promise;
    }
  }

  @JsFunction
  public interface OnaddstreamFn {
    Object onInvoke(MediaStreamEvent p0);
  }

  @JsFunction
  public interface OndatachannelFn {
    Object onInvoke(RTCDataChannelEvent p0);
  }

  @JsFunction
  public interface OnicecandidateFn {
    Object onInvoke(RTCPeerConnectionIceEvent p0);
  }

  @JsFunction
  public interface OniceconnectionstatechangeFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnnegotiationneededFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnremovestreamFn {
    Object onInvoke(MediaStreamEvent p0);
  }

  @JsFunction
  public interface OnsignalingstatechangeFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OntrackFn {
    Object onInvoke(RTCTrackEvent p0);
  }

  public static native Promise<RTCCertificate> generateCertificate(JsObject keygenAlgorithm);

  @JsOverlay
  public static final Promise<RTCCertificate> generateCertificate(Object keygenAlgorithm) {
    return generateCertificate(Js.<JsObject>uncheckedCast(keygenAlgorithm));
  }

  public String iceConnectionState;
  public String iceGatheringState;
  public RTCSessionDescription localDescription;
  public RTCPeerConnection.OnaddstreamFn onaddstream;
  public RTCPeerConnection.OndatachannelFn ondatachannel;
  public RTCPeerConnection.OnicecandidateFn onicecandidate;
  public RTCPeerConnection.OniceconnectionstatechangeFn oniceconnectionstatechange;
  public RTCPeerConnection.OnnegotiationneededFn onnegotiationneeded;
  public RTCPeerConnection.OnremovestreamFn onremovestream;
  public RTCPeerConnection.OnsignalingstatechangeFn onsignalingstatechange;
  public RTCPeerConnection.OntrackFn ontrack;
  public RTCSessionDescription remoteDescription;
  public String signalingState;

  public RTCPeerConnection(RTCConfigurationRecord_ configuration, JsObject constraints) {}

  public RTCPeerConnection(RTCConfigurationRecord_ configuration, Object constraints) {}

  public RTCPeerConnection(RTCConfigurationRecord_ configuration) {}

  public native void addEventListener(
      String type, EventListener listener, EventTarget.AddEventListenerOptionsUnionType useCapture);

  public native void addEventListener(String type, EventListener listener);

  public native Promise addIceCandidate(
      RTCIceCandidate candidate,
      RTCVoidCallback successCallback,
      RTCPeerConnection.AddIceCandidateErrorCallbackFn errorCallback);

  public native Promise addIceCandidate(RTCIceCandidate candidate, RTCVoidCallback successCallback);

  public native Promise addIceCandidate(RTCIceCandidate candidate);

  public native void addStream(MediaStream stream, JsObject constraints);

  @JsOverlay
  public final void addStream(MediaStream stream, Object constraints) {
    addStream(stream, Js.<JsObject>uncheckedCast(constraints));
  }

  public native void addStream(MediaStream stream);

  public native RTCRtpSender addTrack(
      MediaStreamTrack track, MediaStream stream, MediaStream... var_args);

  public native Object close();

  public native Promise<RTCSessionDescription> createAnswer();

  public native Promise<RTCSessionDescription> createAnswer(
      JsObject successCallbackOrConstraints,
      RTCPeerConnectionErrorCallback errorCallback,
      JsObject constraints);

  public native Promise<RTCSessionDescription> createAnswer(
      JsObject successCallbackOrConstraints, RTCPeerConnectionErrorCallback errorCallback);

  public native Promise<RTCSessionDescription> createAnswer(JsObject successCallbackOrConstraints);

  @JsOverlay
  public final Promise<RTCSessionDescription> createAnswer(
      Object successCallbackOrConstraints,
      RTCPeerConnectionErrorCallback errorCallback,
      Object constraints) {
    return createAnswer(
        Js.<JsObject>uncheckedCast(successCallbackOrConstraints),
        errorCallback,
        Js.<JsObject>uncheckedCast(constraints));
  }

  @JsOverlay
  public final Promise<RTCSessionDescription> createAnswer(
      Object successCallbackOrConstraints, RTCPeerConnectionErrorCallback errorCallback) {
    return createAnswer(Js.<JsObject>uncheckedCast(successCallbackOrConstraints), errorCallback);
  }

  @JsOverlay
  public final Promise<RTCSessionDescription> createAnswer(Object successCallbackOrConstraints) {
    return createAnswer(Js.<JsObject>uncheckedCast(successCallbackOrConstraints));
  }

  public native RTCDataChannel createDataChannel(String label, JsObject dataChannelDict);

  @JsOverlay
  public final RTCDataChannel createDataChannel(String label, Object dataChannelDict) {
    return createDataChannel(label, Js.<JsObject>uncheckedCast(dataChannelDict));
  }

  public native RTCDataChannel createDataChannel(String label);

  public native Promise<RTCSessionDescription> createOffer();

  public native Promise<RTCSessionDescription> createOffer(
      JsObject successCallbackOrConstraints,
      RTCPeerConnectionErrorCallback errorCallback,
      JsObject constraints);

  public native Promise<RTCSessionDescription> createOffer(
      JsObject successCallbackOrConstraints, RTCPeerConnectionErrorCallback errorCallback);

  public native Promise<RTCSessionDescription> createOffer(JsObject successCallbackOrConstraints);

  @JsOverlay
  public final Promise<RTCSessionDescription> createOffer(
      Object successCallbackOrConstraints,
      RTCPeerConnectionErrorCallback errorCallback,
      Object constraints) {
    return createOffer(
        Js.<JsObject>uncheckedCast(successCallbackOrConstraints),
        errorCallback,
        Js.<JsObject>uncheckedCast(constraints));
  }

  @JsOverlay
  public final Promise<RTCSessionDescription> createOffer(
      Object successCallbackOrConstraints, RTCPeerConnectionErrorCallback errorCallback) {
    return createOffer(Js.<JsObject>uncheckedCast(successCallbackOrConstraints), errorCallback);
  }

  @JsOverlay
  public final Promise<RTCSessionDescription> createOffer(Object successCallbackOrConstraints) {
    return createOffer(Js.<JsObject>uncheckedCast(successCallbackOrConstraints));
  }

  public native boolean dispatchEvent(Event evt);

  public native MediaStream[] getLocalStreams();

  public native RTCRtpReceiver[] getReceivers();

  public native MediaStream[] getRemoteStreams();

  public native RTCRtpSender[] getSenders();

  public native RTCPeerConnection.GetStatsUnionType getStats();

  public native RTCPeerConnection.GetStatsUnionType getStats(
      RTCStatsCallback successCallback, MediaStreamTrack selector);

  public native RTCPeerConnection.GetStatsUnionType getStats(RTCStatsCallback successCallback);

  public native MediaStream getStreamById(String streamId);

  public native void removeEventListener(
      String type,
      EventListener listener,
      EventTarget.RemoveEventListenerOptionsUnionType useCapture);

  public native void removeEventListener(String type, EventListener listener);

  public native void removeStream(MediaStream stream);

  public native void removeTrack(RTCRtpSender sender);

  public native Promise<RTCSessionDescription> setLocalDescription(
      RTCSessionDescription description,
      RTCVoidCallback successCallback,
      RTCPeerConnectionErrorCallback errorCallback);

  public native Promise<RTCSessionDescription> setLocalDescription(
      RTCSessionDescription description, RTCVoidCallback successCallback);

  public native Promise<RTCSessionDescription> setLocalDescription(
      RTCSessionDescription description);

  public native Promise<RTCSessionDescription> setRemoteDescription(
      RTCSessionDescription description,
      RTCVoidCallback successCallback,
      RTCPeerConnectionErrorCallback errorCallback);

  public native Promise<RTCSessionDescription> setRemoteDescription(
      RTCSessionDescription description, RTCVoidCallback successCallback);

  public native Promise<RTCSessionDescription> setRemoteDescription(
      RTCSessionDescription description);

  public native void updateIce();

  public native void updateIce(RTCConfigurationRecord_ configuration, JsObject constraints);

  @JsOverlay
  public final void updateIce(RTCConfigurationRecord_ configuration, Object constraints) {
    updateIce(configuration, Js.<JsObject>uncheckedCast(constraints));
  }

  public native void updateIce(RTCConfigurationRecord_ configuration);
}
