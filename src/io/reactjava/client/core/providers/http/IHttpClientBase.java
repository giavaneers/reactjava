/*==============================================================================

name:       IHttpClientBase - http client provider interface

purpose:    GWT compatible Http client provider interface

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                  This program was created by Giavaneers
        and is the confidential and proprietary product of Giavaneers Inc.
      Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2017 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.providers.http;
                                       // imports --------------------------- //
import com.giavaneers.util.gwt.APIRequestor;
import io.reactjava.client.core.providers.http.HttpClientBase.JsXMLHttpRequest;
import io.reactjava.client.core.providers.http.IHttpResponse.ResponseType;
import io.reactjava.client.core.react.IProvider;

                                       // IHttpClientBase ========================//
public interface IHttpClientBase extends IProvider
{
                                       // constants ------------------------- //
public static final String kDELETE = "DELETE";
public static final String kGET    = "GET";
public static final String kOPTION = "OPTION";
public static final String kPOST   = "POST";
public static final String kPUT    = "PUT";

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // (none)                              //

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
public String getErrorReason();

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
String getAllResponseHeaders();

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
int getReadyState();

/*------------------------------------------------------------------------------

@name       getRequestor - get requestor
                                                                              */
                                                                             /**
            Get requestor.

@return     requestor

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
APIRequestor getRequestor();

/*------------------------------------------------------------------------------

@name       getRequestToken - get request token
                                                                              */
                                                                             /**
            Get request token.

@return     request token

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
Object getRequestToken();

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
Object getResponse();

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
String getResponseHeader(
   String header);

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
String getResponseText();

/*------------------------------------------------------------------------------

@name       getResponseType - get the response type
                                                                              */
                                                                             /**
            Set the response type.

@return     response type

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
ResponseType getResponseType();

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
int getStatus();

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
String getStatusText();

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
boolean getTimeout();

/*------------------------------------------------------------------------------

@name       getURL - get url
                                                                              */
                                                                             /**
            Get url.

@return     url

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
String getURL();

/*------------------------------------------------------------------------------

@name       getXHR - get xhr
                                                                              */
                                                                             /**
            Get xhr.

@return     xhr

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
JsXMLHttpRequest getXHR();

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
IHttpClientBase open();

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
public void send(
   APIRequestor requestor);

/*------------------------------------------------------------------------------

@name       send - send the request
                                                                              */
                                                                             /**
            Send the request.

@return     null

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Object send(
   byte[] bytes);

/*------------------------------------------------------------------------------

@name       sendFormData - sendFormData the request
                                                                              */
                                                                             /**
            Send the request.

@return     void

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Object sendFormData(
   String data)
   throws Exception;

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
IHttpClientBase setErrorReason(
   String errorReason);

/*------------------------------------------------------------------------------

@name       setMethod- set the specified response type
                                                                              */
                                                                             /**
            Set the specified response type.

@return     null

@param      responseType      response type

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
IHttpClientBase setMethod(
   String method);

/*------------------------------------------------------------------------------

@name       setReadyStateChangedListener - set the readyStateChanged listener
                                                                              */
                                                                             /**
            Set the readyStateChanged listener.

@return     void

@param      handler     readyStateChanged listener

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IHttpClientBase setReadyStateChangedListener(
   IReadyStateChangedListener readyStateChangedListener);

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
IHttpClientBase setRequestHeader(
   String headerName,
   String value);

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
IHttpClientBase setRequestor(
   APIRequestor requestor);

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
IHttpClientBase setRequestToken(
   Object requestToken);

/*------------------------------------------------------------------------------

@name       setResponseType - set the specified response type
                                                                              */
                                                                             /**
            Set the specified response type.

@return     void

@param      responseType      response type

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
IHttpClientBase setResponseType(
   ResponseType responseType);

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
IHttpClientBase setStatusText(
   String statusText);

/*------------------------------------------------------------------------------

@name       setTimeout - set that a timeout occurred
                                                                              */
                                                                             /**
            Set that a timeout occurred.

@return     void

@param      timeout    'true'

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IHttpClientBase setTimeout();

/*------------------------------------------------------------------------------

@name       setResponseType - set the specified response type
                                                                              */
                                                                             /**
            Set the specified response type.

@return     void

@param      responseType      response type

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
IHttpClientBase setURL(
   String url);

/*==============================================================================

name:       IReadyStateChangedListener - readyStateChanged listener

purpose:    readyStateChanged listener

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static interface IReadyStateChangedListener
{
                                       // constants ------------------------- //
public static final int kUNSENT  = 0;  // initial state                       //
public static final int kOPENED  = 1;  // open() method successfully invoked  //
                                       // response headers received           //
public static final int kHEADERS_RECEIVED = 2;
public static final int kLOADING = 3;  // response is being received          //
public static final int kDONE    = 4;  // response received                   //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
void onStateChanged();
}//====================================// IReadyStateChangedListener --------- //
}//====================================// IHttpClientBase ====================//
