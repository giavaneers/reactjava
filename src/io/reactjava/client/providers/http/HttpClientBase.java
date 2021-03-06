/*==============================================================================

name:       HttpRequest - core compatible HttpRequest using JsXMLHttpRequest

purpose:    GWT compatible HttpRequest using JsXMLHttpRequest

            Simple usage for GET:

               final HttpRequest httpReq = new HttpRequest(url);
               httpReq.send(requestToken, new APIRequestor()
               {
                  public void apiResponse(Object response, Object reqToken)
                  {
                     if (response instanceof Throwable)
                     {
                        ...
                     }
                     else
                     {
                        String responseText = (String)response;
                        ...
                     }
                  }
               });

            Simple usage for PUT:

               byte[] payload = ...;
               final HttpRequest httpReq = new HttpRequest(url, kPUT, payload);
               httpReq.send(requestToken, new APIRequestor()
               {
                  public void apiResponse(Object response, Object reqToken)
                  {
                     if (response instanceof Throwable)
                     {
                        ...
                     }
                     else
                     {
                        String responseText = (String)response;
                        ...
                     }
                  }
               });

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.providers.http;
                                       // imports --------------------------- //
import com.giavaneers.util.gwt.APIRequestor;
import com.giavaneers.util.gwt.Logger;
import elemental2.core.ArrayBuffer;
import elemental2.core.Uint8Array;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import io.reactjava.client.core.react.Provider;
import io.reactjava.client.providers.http.HttpClientBase.JsXMLHttpRequest.IReadyStateChangedHandler;
import io.reactjava.client.providers.http.IHttpResponse.ResponseType;
import io.reactjava.client.core.react.NativeObject;
import io.reactjava.client.core.react.Properties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.base.Js;
                                    // HttpClientBase ========================//
public class HttpClientBase extends Provider implements IHttpClientBase
{
                                       // constants ------------------------- //
private static final Logger kLOGGER = Logger.newInstance();

public static final String kKEY_ERROR_REASON               = "errorReason";
public static final String kKEY_METHOD                     = "method";
public static final String kKEY_RDY_STATE_CHANGED_LISTENER = "readyStateChangedListener";
public static final String kKEY_REQUEST_TOKEN              = "requestToken";
public static final String kKEY_REQUESTOR                  = "requestor";
public static final String kKEY_STATUS_TEXT                = "statusText";
public static final String kKEY_TIMEOUT                    = "timeout";
public static final String kKEY_URL                        = "url";
public static final String kKEY_XHR                        = "xhr";

public static final Map<Integer,String> kSTATUS_CODES_SUCCESS =
   new HashMap<Integer,String>()
   {{
      put(200, "OK");
      put(201, "Created");
      put(206, "Partial Content");
      put(308, "Permanent Redirect");
   }};

public static final List<ResponseType> kRESPONSE_TYPES_SUPPORTED =
   new ArrayList<ResponseType>()
   {{
      add(ResponseType.kARRAYBUFFER);
      add(ResponseType.kDEFAULT);
      add(ResponseType.kJSON);
      add(ResponseType.kTEXT);
   }};
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
protected NativeObject props;            // properties                          //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       HttpClientBase - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public HttpClientBase()
{
   this((Properties)null);
}
/*------------------------------------------------------------------------------

@name       HttpClientBase - constructor
                                                                              */
                                                                             /**
            Constructor

@param      url               target url

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public HttpClientBase(
   String url)
{
   this((Properties)null);
   props = NativeObject.with(props(), kKEY_URL, url);
}
/*------------------------------------------------------------------------------

@name       HttpClientBase - constructor
                                                                              */
                                                                             /**
            Constructor

@param      props            props

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public HttpClientBase(
   Properties props)
{
   this.props = props != null ? props.toNativeObject() : new NativeObject();

   JsXMLHttpRequest xhr = getXHR();
   if (xhr == null)
   {
      xhr = new JsXMLHttpRequest();
      xhr.setOnreadystatechange(new ReadyStateChangeHandler());
      props().set(kKEY_XHR, xhr);
   }

   Object method = props().get(kKEY_METHOD);
   if (method == null)
   {
      props().set(kKEY_METHOD, kGET);
   }
}
/*------------------------------------------------------------------------------

@name       bytesToUint8Array - get Uint8Array from byte array
                                                                              */
                                                                             /**
            Get Uint8Array from byte array.

@return     Uint8Array from byte array

@param      bytes    byte array

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Uint8Array bytesToUint8Array(
   byte[] bytes)
{
   Uint8Array uint8Array = Js.uncheckedCast(bytes);
   return(uint8Array);
}
/*------------------------------------------------------------------------------

@name       getAllResponseHeaders - get all response headers
                                                                              */
                                                                             /**
            Get all response headers.

            Note, for cross-origin requests, other than the six 'simple'
            response headers, response headers must be explicitly exposed by the
            server in the "Access-Control-Expose-Headers" response header from
            the server before the Browser will make them available to javascript.
            For Google Cloud Storage, this is not configurable in the CORS
            configuration json file and the standard set does not include
            'Content-Range" among others.

@return     all response headers

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getAllResponseHeaders()
{
   return(getXHR().getAllResponseHeaders());
}
/*------------------------------------------------------------------------------

@name       getMethod - get method
                                                                              */
                                                                             /**
            Get method.

@return     method

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
public String getMethod()
{
   return(props().getString(kKEY_METHOD));
}
/*------------------------------------------------------------------------------

@name       getReadyState - get response ready state
                                                                              */
                                                                             /**
            Get response ready state.

@return     response ready state

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getReadyState()
{
   return(getXHR().getReadyState());
}
/*------------------------------------------------------------------------------

@name       getReadyStateChangedListener - get ready state changed listener
                                                                              */
                                                                             /**
            Get ready state changed listener.

@return     ready state changed listener

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IReadyStateChangedListener getReadyStateChangedListener()
{
   return(
      (IReadyStateChangedListener) props().get(
         kKEY_RDY_STATE_CHANGED_LISTENER));
}
/*------------------------------------------------------------------------------

@name       getErrorReason - get error reason
                                                                              */
                                                                             /**
            Get error reason.

@return     error reason

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getErrorReason()
{
   return(props().getString(kKEY_ERROR_REASON));
}
/*------------------------------------------------------------------------------

@name       getRequestor - get requestor
                                                                              */
                                                                             /**
            Get requestor.

@return     requestor

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
public APIRequestor getRequestor()
{
   return((APIRequestor) props().get(kKEY_REQUESTOR));
}
/*------------------------------------------------------------------------------

@name       getRequestToken - get request token
                                                                              */
                                                                             /**
            Get request token.

@return     request token

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
public Object getRequestToken()
{
   return(props().get(kKEY_REQUEST_TOKEN));
}
/*------------------------------------------------------------------------------

@name       getResponse - get response array buffer
                                                                              */
                                                                             /**
            Get response array buffer.

@return     response array buffer

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Object getResponse()
{
   return(getXHR().getResponse());
}
/*------------------------------------------------------------------------------

@name       getResponseHeader - get response header
                                                                              */
                                                                             /**
            Get response header.

@return     response header

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getResponseHeader(
   String header)
{
   return(getXHR().getResponseHeader(header));
}
/*------------------------------------------------------------------------------

@name       getResponseText - get response text
                                                                              */
                                                                             /**
            Get response text.

@return     response text

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getResponseText()
{
   return(getXHR().getResponseText());
}
/*------------------------------------------------------------------------------

@name       getResponseType - get the response type
                                                                              */
                                                                             /**
            Set the response type.

@return     response type

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
public ResponseType getResponseType()
{
   return(ResponseType.fromString(getXHR().getResponseType()));
}
/*------------------------------------------------------------------------------

@name       getStatus - get response status
                                                                              */
                                                                             /**
            Get response status.

@return     status value

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getStatus()
{
   return(getXHR().getStatus());
}
/*------------------------------------------------------------------------------

@name       getStatusText - get status text
                                                                              */
                                                                             /**
            Get status text.

@return     status text

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getStatusText()
{
   return(props().getString(kKEY_STATUS_TEXT));
}
/*------------------------------------------------------------------------------

@name       getTimeout - get whether a timeout occurred
                                                                              */
                                                                             /**
            Get whether a timeout occurred.

@return     true iff a timeout occurred

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean getTimeout()
{
   return(props().get(kKEY_TIMEOUT) != null);
}
/*------------------------------------------------------------------------------

@name       getURL - get url
                                                                              */
                                                                             /**
            Get url.

@return     url

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
public String getURL()
{
   return(props().getString(kKEY_URL));
}
/*------------------------------------------------------------------------------

@name       getXHR - get xhr
                                                                              */
                                                                             /**
            Get xhr.

@return     xhr

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
public JsXMLHttpRequest getXHR()
{
   return((JsXMLHttpRequest) props().get(kKEY_XHR));
}
/*------------------------------------------------------------------------------

@name       onStateChanged - onStateChanged handler
                                                                              */
                                                                             /**
            onStateChanged handler

@history    Mon Aug 18, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void onStateChanged()
{
}
/*------------------------------------------------------------------------------

@name       open - open the request
                                                                              */
                                                                             /**
            Open the request.

@return     void

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public HttpClientBase open()
{
   if (getMethod() == null)
   {
      throw new IllegalStateException("Neither method nor url may be null");
   }
   if (getURL() == null)
   {
      throw new IllegalStateException("Url may be null");
   }
   getXHR().open(getMethod(), getURL(), true);

   if (IReadyStateChangedListener.kOPENED != getXHR().getReadyState())
   {
      throw new IllegalStateException(
         "IReadyStateChangedListener.kOPENED != xhr.getReadyState()");
   }

   return(this);
}
/*------------------------------------------------------------------------------

@name       props - get properties
                                                                              */
                                                                             /**
            Get properties.

@return     properties

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public NativeObject props()
{
   if (props == null)
   {
      props = new NativeObject();
   }
   return(props);
}
/*------------------------------------------------------------------------------

@name       send - send the request
                                                                              */
                                                                             /**
            Send the request.

@param      requestor      requestor

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void send(
   APIRequestor requestor)
{
   setRequestor(requestor).send((byte[])null);
}
/*------------------------------------------------------------------------------

@name       send - send the request
                                                                              */
                                                                             /**
            Send the request.

@return     void

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Object send(
   String body)
{
   return(send((Object)body));
}
/*------------------------------------------------------------------------------

@name       send - send the request
                                                                              */
                                                                             /**
            Send the request.

@return     void

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Object send(
   byte[] bytes)
{
   Uint8Array uint8Array = bytes != null ? Js.uncheckedCast(bytes) : null;
   return(send(uint8Array));
}
/*------------------------------------------------------------------------------

@name       send - send the request
                                                                              */
                                                                             /**
            Send the request.

@return     void

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Object send(
   Object body)
{
   if (body != null)
   {
      String method = getMethod();
      if (method != null && !kPOST.equals(method) && !kPUT.equals(method))
      {
         throw new IllegalArgumentException("Body must be null for " + method);
      }
   }

   if (IReadyStateChangedListener.kOPENED != getXHR().getReadyState())
   {
      open();
   }

   if (getReadyStateChangedListener() == null)
   {
      props().set(
         kKEY_RDY_STATE_CHANGED_LISTENER,
         new DefaultReadyStateChangeListener(this));

      //kLOGGER.logInfo(
      //   "HttpClientBase.send(): assigning readyStateChangedListener="
      //  + getReadyStateChangedListener());
   }

   //kLOGGER.logInfo("HttpClientBase.send(): xhr.send() on APIRequestor.");

   getXHR().send(body);

   return(null);
}
/*------------------------------------------------------------------------------

@name       send - send the request
                                                                              */
                                                                             /**
            Send the request.

@return     void

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Object send(
   ArrayBuffer arrayBuffer)
{
   Uint8Array uint8Array =
      arrayBuffer != null ? new Uint8Array(arrayBuffer) : null;

   return(send(uint8Array));
}
/*------------------------------------------------------------------------------

@name       send - send the request
                                                                              */
                                                                             /**
            Send the request.

@return     void

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Object send(
   Uint8Array uint8Array)
{
   return(send((Object)uint8Array));
}
/*------------------------------------------------------------------------------

@name       sendFormData - sendFormData the request
                                                                              */
                                                                             /**
            Send the request.

@return     null

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Object sendFormData(
   String data)
   throws Exception
{
   byte[] bytes = URL.encode(data).replace("%20", "+").getBytes("UTF-8");
   return(send(bytes));
}
/*------------------------------------------------------------------------------

@name       setErrorReason - set the specified error reason
                                                                              */
                                                                             /**
            Set the specified error reason .

@return     void

@param      errorReason    error reason

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IHttpClientBase setErrorReason(
   String errorReason)
{
   props().set(kKEY_ERROR_REASON, errorReason);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setMethod- set the specified response type
                                                                              */
                                                                             /**
            Set the specified response type.

@return     http client

@param      method      method

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
public IHttpClientBase setMethod(
   String method)
{
   if (!IHttpClientBase.isMethod(method))
   {
      throw new IllegalArgumentException("Unknown method: " + method);
   }
   props().set(kKEY_METHOD, method);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setReadyStateChangedListener - set the readyStateChanged listener
                                                                              */
                                                                             /**
            Set the readyStateChanged listener.

@return     http client

@param      readyStateChangedListener     readyStateChanged listener

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IHttpClientBase setReadyStateChangedListener(
   IReadyStateChangedListener readyStateChangedListener)
{
   props().set(
      kKEY_RDY_STATE_CHANGED_LISTENER, readyStateChangedListener);

   return(this);
}
/*------------------------------------------------------------------------------

@name       setRequestHeader - set the specified request header
                                                                              */
                                                                             /**
            Set the specified request header.

@return     void

@param      headerName     request header name
@param      value          request header value

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IHttpClientBase setRequestHeader(
   String headerName,
   String value)
{
   if (IReadyStateChangedListener.kOPENED != getXHR().getReadyState())
   {
      open();
   }

   getXHR().setRequestHeader(headerName, value);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setRequestor - set the specified requestor
                                                                              */
                                                                             /**
            Set the specified requestor.

@return     void

@param      requestor      requestor

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IHttpClientBase setRequestor(
   APIRequestor requestor)
{
   props().set(kKEY_REQUESTOR, requestor);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setRequestToken - set the specified request token
                                                                              */
                                                                             /**
            Set the specified request token.

@return     void

@param      requestToken      request token

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IHttpClientBase setRequestToken(
   Object requestToken)
{
   props().set(kKEY_REQUEST_TOKEN, requestToken);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setResponseType - set the specified response type
                                                                              */
                                                                             /**
            Set the specified response type.

@return     this

@param      responseType      response type

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
public IHttpClientBase setResponseType(
   ResponseType responseType)
{
   getXHR().setResponseType(responseType.value);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setStatusText - set status text
                                                                              */
                                                                             /**
            Set status text.

@return     this

@param      statusText     status text

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IHttpClientBase setStatusText(
   String statusText)
{
   props().set(kKEY_STATUS_TEXT, statusText);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setTimeout - set that a timeout occurred
                                                                              */
                                                                             /**
            Set that a timeout occurred.

@return     this

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IHttpClientBase setTimeout()
{
   props().set(kKEY_TIMEOUT, "true");
   return(this);
}
/*------------------------------------------------------------------------------

@name       setResponseType - set the specified response type
                                                                              */
                                                                             /**
            Set the specified response type.

@return     this

@param      url      url

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
public IHttpClientBase setURL(
   String url)
{
   props().set(kKEY_URL, url);
   return(this);
}
/*------------------------------------------------------------------------------

@name       uint8ArrayToBytes - get byte array from Uint8Array
                                                                              */
                                                                             /**
            Get byte array from Uint8Array.

@return     byte array from Uint8Array

@param      uint8Array      Uint8Array

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static byte[] uint8ArrayToBytes(
   Uint8Array uint8Array)
{
   //byte[] bytes = new byte[0];
   //try
   //{
   //   bytes = new byte[uint8Array != null ? uint8Array.length() : 0];
   //}
   //catch(StringIndexOutOfBoundsException e)
   //{
   //   e = e;
   //   throw e;
   //}
   //
   //for (int i = 0, iMax = bytes.length; i < iMax; i++)
   //{
   //   try
   //   {
   //      bytes[i] = (byte)uint8Array.get(i);
   //   }
   //   catch(StringIndexOutOfBoundsException e)
   //   {
   //      e = e;
   //      throw e;
   //   }
   //}
   byte[] bytes = Js.uncheckedCast(uint8Array);
   return(bytes);
}
/*==============================================================================

name:       DefaultReadyStateChangeListener - default readyStateChange listener

purpose:    Default readyStateChange listener

history:    Wed Jun 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class DefaultReadyStateChangeListener
   implements IReadyStateChangedListener
{
                                       // class constants ------------------- //
private static final Logger kLOGGER = Logger.newInstance();

                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
protected final IHttpClientBase client;// client                              //
                                       // start time                          //
protected long                  startTime;
                                       // response data loading               //
protected boolean               bLoading;
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       DefaultReadyStateChangeListener - default constructor
                                                                              */
                                                                             /**
            Default constructor

@param      client      http client

@history    Mon Aug 18, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public DefaultReadyStateChangeListener(
   IHttpClientBase client)
{
   this.client = client;
}
/*------------------------------------------------------------------------------

@name       getClient - get client
                                                                              */
                                                                             /**
            Get client.

@return     client

@history    Mon Aug 18, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected IHttpClientBase getClient()
{
   return(client);
}
/*------------------------------------------------------------------------------

@name       onStateChanged - onStateChanged handler
                                                                              */
                                                                             /**
            onStateChanged handler

@history    Mon Aug 18, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void onStateChanged()
{
   JsXMLHttpRequest xhr = getClient().getXHR();
   int readyState = xhr.getReadyState();
   if (kLOADING == readyState)
   {
      if (!bLoading)
      {
         bLoading = true;
                                       // refine the start time               //
         startTime = System.currentTimeMillis();

         //kLOGGER.logInfo(
         //   "HttpClientBase.DefaultReadyStateChangeListener."
         // + "onReadyStateChange(): response data loading");
      }
   }
   else if (kDONE == readyState)
   {
      //kLOGGER.logInfo(
      //   "HttpClientBase.DefaultReadyStateChangeListener."
      // + "onReadyStateChange(): response data loaded");

      processCompletion();

      if (kSTATUS_CODES_SUCCESS.get(xhr.getStatus()) != null)
      {
         xhr.clearOnReadyStateChange();
      }
   }
}
/*------------------------------------------------------------------------------

@name       parseJsonErrorResponse - parse json error response
                                                                              */
                                                                             /**
            Parse json error response.

            Handles any of two types:

            1. IETF devised RFC 7807, which creates a generalized error-handling
            schema which is composed of five parts:

            type     a URI identifier that categorizes the error
            title    a brief, human-readable message about the error
            status   the HTTP response code (optional)
            detail   a human-readable explanation of the error
            instance a URI that identifies the specific occurrence of the error

            2. Google Cloud API error syntax which is of the following form:

               {
                  "error": {
                     "code": 401,
                     "message": "Invalid Credentials",
                     "errors": [
                        {
                           "message": "Invalid Credentials",
                           "domain": "global",
                           "reason": "authError",
                           "locationType": "header",
                           "location": "Authorization"
                        }
                     ]
                  }
               }

@return     parsed error response, or null if not json or if one of the
            supported types

@history    Thu Mar 11, 2021 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected String parseJsonErrorResponse(
   String unparsed)
{
   String parsed = null;
   try
   {
      JSONValue  jsonValue  = JSONParser.parseStrict(unparsed);
      JSONObject jsonObject = jsonValue.isObject();

                                       // check for RFC 7807 format           //
      JSONValue rfc7807Title = jsonObject.get("title");
      if (rfc7807Title != null)
      {
         parsed = rfc7807Title.isString().stringValue();

         JSONValue jsonDetail = jsonObject.get("detail");
         if (jsonDetail != null)
         {
            parsed += " - " + jsonDetail.isString().stringValue();
         }
      }
      else
      {
                                       // check for Google Cloud API format   //
         JSONValue jsonError = jsonObject.get("error");
         if (jsonError != null)
         {
            JSONObject error  = jsonError.isObject();
            JSONValue jsonMsg = jsonObject.get("message");
            if (jsonMsg != null)
            {
               parsed = jsonMsg.isString().stringValue();
            }
         }
      }
   }
   catch(Exception e)
   {
                                       // not json or not a supported type    //
   }

   return(parsed);
}
/*------------------------------------------------------------------------------

@name       processCompletion - processCompletion handler
                                                                              */
                                                                             /**
            processCompletion handler

@history    Mon Aug 18, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void processCompletion()
{
   JsXMLHttpRequest xhr = getClient().getXHR();
                                       // capture any statusText              //
   getClient().setStatusText(xhr.getStatusText());

   boolean bErr = false;
                                       // verify response type is supported   //
   ResponseType responseType = ResponseType.fromString(xhr.getResponseType());
   if (!kRESPONSE_TYPES_SUPPORTED.contains(responseType))
   {
      String msg =
         "HttpClient.DefaultReadyStateChangeListener.processCompletion(): "
       + "response type not supported: " + responseType;

      kLOGGER.logError(msg);
      getClient().setErrorReason(msg);
      bErr = true;
   }
   if (!bErr)
   {
      bErr = kSTATUS_CODES_SUCCESS.get(xhr.getStatus()) == null;
   }
   if (bErr)
   {
                                       // process error --------------------- //
      String statusText = null;
      if (responseType != ResponseType.kARRAYBUFFER)
      {
         statusText = getClient().getStatusText();
         if (statusText == null || statusText.length() == 0)
         {
            if (getClient().getTimeout())
            {
               statusText = "Timeout without response";
            }
            else
            {
               statusText = "Request status = " + xhr.getStatus();
            }

            getClient().setStatusText(statusText);
         }
      }
                                       // get any reason available            //
      String reason = getClient().getErrorReason();
      if (reason == null && responseType != ResponseType.kARRAYBUFFER)
      {
         try
         {
            String response = getClient().getXHR().getResponseText();
            if (response != null && response.length() > 0)
            {
               reason = parseJsonErrorResponse(response);
            }
            if (reason == null || reason.length() == 0)
            {
               reason = statusText;
            }
         }
         catch(Exception e)
         {
            reason = e.toString();
         }

         getClient().setErrorReason(reason);
      }
   }

   signalCompletion();
}
/*------------------------------------------------------------------------------

@name       signalCompletion - signal completion
                                                                              */
                                                                             /**
            Signal completion

@history    Mon Aug 18, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void signalCompletion()
{
   APIRequestor requestor = getClient().getRequestor();
   if (requestor != null)
   {
      try
      {
         requestor.apiResponse(
            new HttpResponse(getClient()), getClient().getRequestToken());
      }
      catch(Throwable t)
      {
         kLOGGER.logError(t);
      }
   }
}
}//====================================// DefaultReadyStateChangeListener ----//
/*==============================================================================

name:       JsXMLHttpRequest - jsinterop definition for access to javascript

purpose:    JsInterop definition for access to javascript XMLHttpRequest

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:      Heavily borrowed from
            fr.lteconsulting.client.ajax.interop.JsXMLHttpRequest
            see https://github.com/ltearno/angular2-gwt

==============================================================================*/
@jsinterop.annotations.JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "XMLHttpRequest")
public static class JsXMLHttpRequest
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       clearOnReadyStateChange - clear onReadyStateChange handler
                                                                              */
                                                                             /**
            Clear onReadyStateChange handler.

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final void clearOnReadyStateChange()
{
   setOnreadystatechange(null);
}
/*------------------------------------------------------------------------------

@name       getAllResponseHeaders - get response headers
                                                                              */
                                                                             /**
            Get response headers.

@return     response headers

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes      From fr.lteconsulting.client.ajax.interop.JsXMLHttpRequest
            see https://github.com/ltearno/angular2-gwt
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native String getAllResponseHeaders();

/*------------------------------------------------------------------------------

@name       getOnreadystatechange - get onReadyStateChange handler
                                                                              */
                                                                             /**
            Get onReadyStateChange handler.

@return     any onReadyStateChange handler

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsProperty
public native JsXMLHttpRequest.IReadyStateChangedHandler getOnreadystatechange();

/*------------------------------------------------------------------------------

@name       getReadyState - get response ready state
                                                                              */
                                                                             /**
            Get response ready state.

@return     response ready state

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes      From fr.lteconsulting.client.ajax.interop.JsXMLHttpRequest
            see https://github.com/ltearno/angular2-gwt
                                                                              */
//------------------------------------------------------------------------------
@JsProperty
public native int getReadyState();

/*------------------------------------------------------------------------------

@name       getResponse - get response array buffer
                                                                              */
                                                                             /**
            Get response array buffer.

@return     response array buffer

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@SuppressWarnings("unusable-by-js")
@JsProperty
public native ArrayBuffer getResponse();

/*------------------------------------------------------------------------------

@name       getResponseHeader - get response header
                                                                              */
                                                                             /**
            Get response header.

@return     response header

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes      From fr.lteconsulting.client.ajax.interop.JsXMLHttpRequest
            see https://github.com/ltearno/angular2-gwt
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native String getResponseHeader(String header);

/*------------------------------------------------------------------------------

@name       getResponseText - get response text
                                                                              */
                                                                             /**
            Get response text.

@return     response text

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes      From fr.lteconsulting.client.ajax.interop.JsXMLHttpRequest
            see https://github.com/ltearno/angular2-gwt
                                                                              */
//------------------------------------------------------------------------------
@JsProperty
public native String getResponseText();

/*------------------------------------------------------------------------------

@name       getResponseType - get the response type
                                                                              */
                                                                             /**
            Get the response type.

@return     response type

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
@JsProperty
public native String getResponseType();

/*------------------------------------------------------------------------------

@name       getStatusText - get response status text
                                                                              */
                                                                             /**
            Get response status text.

@return     status text value

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes      From fr.lteconsulting.client.ajax.interop.JsXMLHttpRequest
            see https://github.com/ltearno/angular2-gwt
                                                                              */
//------------------------------------------------------------------------------
@JsProperty
public native String getStatusText();

/*------------------------------------------------------------------------------

@name       getStatus - get response status
                                                                              */
                                                                             /**
            Get response status.

@return     status value

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes      From fr.lteconsulting.client.ajax.interop.JsXMLHttpRequest
            see https://github.com/ltearno/angular2-gwt
                                                                              */
//------------------------------------------------------------------------------
@JsProperty
public native int getStatus();

/*------------------------------------------------------------------------------

@name       open - open a connection
                                                                              */
                                                                             /**
            Open a connection to the specified method to the url.

@param      method      method
@param      url         url
@param      isAsync     true iff asynchronous

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes      From fr.lteconsulting.client.ajax.interop.JsXMLHttpRequest
            see https://github.com/ltearno/angular2-gwt
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native void open(String method, String url, boolean isAsync);

/*------------------------------------------------------------------------------

@name       sendFormData - sendFormData the request
                                                                              */
                                                                             /**
            Send the request.

@param      data     data

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes      From fr.lteconsulting.client.ajax.interop.JsXMLHttpRequest
            see https://github.com/ltearno/angular2-gwt
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native void send(Object data);

/*------------------------------------------------------------------------------

@name       setOnLoad - set onLoad handler
                                                                              */
                                                                             /**
            Set onLoad handler.

@param      handler     handler

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsProperty
public native void setOnLoad(JsXMLHttpRequest.IOnLoadHandler handler);

/*------------------------------------------------------------------------------

@name       setOnreadystatechange - set onReadyStateChange handler
                                                                              */
                                                                             /**
            Set onReadyStateChange handler.

@param      handler     handler

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes      From fr.lteconsulting.client.ajax.interop.JsXMLHttpRequest
            see https://github.com/ltearno/angular2-gwt
                                                                              */
//------------------------------------------------------------------------------
@JsProperty
public native void setOnreadystatechange(JsXMLHttpRequest.IReadyStateChangedHandler handler);

/*------------------------------------------------------------------------------

@name       setRequestHeader - set the specified request header
                                                                              */
                                                                             /**
            Set the specified request header.

@param      headerName     request header name
@param      value          request header value

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes      From fr.lteconsulting.client.ajax.interop.JsXMLHttpRequest
            see https://github.com/ltearno/angular2-gwt
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native void setRequestHeader(String headerName, String value);

/*------------------------------------------------------------------------------

@name       setResponseType - set the specified response type
                                                                              */
                                                                             /**
            Set the specified response type.

@param      responseType      response type

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
@JsProperty
public native void setResponseType(String responseType);

/*==============================================================================

name:       IOnLoadHandler - jsinterop definition

purpose:    JsInterop definition for access to javascript onLoad handler

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@JsFunction
@FunctionalInterface
public static interface IOnLoadHandler
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
void onLoad(Object event);
}//====================================// IOnLoadHandler =====================//
/*==============================================================================

name:       IReadyStateChangedHandler - jsinterop definition

purpose:    JsInterop definition for access to javascript onStateChanged handler

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:      From fr.lteconsulting.client.ajax.interop.ReadyStateHandler
            see https://github.com/ltearno/angular2-gwt

==============================================================================*/
@JsFunction
@FunctionalInterface
public static interface IReadyStateChangedHandler
{
                                       // constants ------------------------- //
@JsOverlay
public static final int kUNSENT  = 0;  // initial state                       //
@JsOverlay
public static final int kOPENED  = 1;  // open() method successfully invoked  //
@JsOverlay                             // response headers received           //
public static final int kHEADERS_RECEIVED = 2;
@JsOverlay
public static final int kLOADING = 3;  // response is being received          //
@JsOverlay
public static final int kDONE    = 4;  // response received                   //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
void onStateChanged(Object event);
}//====================================// IReadyStateChangedHandler --------- //
}//====================================// JsXMLHttpRequest ===================//
/*==============================================================================

name:       ReadyStateChangeHandler - readyStateChange handler

purpose:    ReadyStateChange handler

history:    Wed Jun 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public final class ReadyStateChangeHandler implements IReadyStateChangedHandler
{
                                       // class constants ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       onStateChanged - onStateChanged handler
                                                                              */
                                                                             /**
            onStateChanged handler

@param      evt     onStateChanged event

@history    Mon Aug 18, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void onStateChanged(
   Object evt)
{
   if (getReadyStateChangedListener() != null)
   {
      getReadyStateChangedListener().onStateChanged();
   }
}
}//====================================// ReadyStateChangeHandler ------------//
}//====================================// HttpClientBase =====================//
