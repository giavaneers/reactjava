/*==============================================================================

name:       IHttpClient - http client provider interface using ReactiveX

purpose:    GWT compatible Http client provider interface using ReactiveX

history:    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.providers.http;
                                       // imports --------------------------- //
import com.giavaneers.util.gwt.APIRequestor;
import io.reactjava.client.providers.http.IHttpResponse.ResponseType;
import io.reactjava.client.core.rxjs.observable.Observable;

                                       // IHttpClient ========================//
public interface IHttpClient extends IHttpClientBase
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // (none)                              //

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
Observable<HttpResponse> send();

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
Observable<HttpResponse> send(
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
Observable<HttpResponse> sendFormData(
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
IHttpClient setErrorReason(
   String errorReason);

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
IHttpClient setMethod(
   String method);

/*------------------------------------------------------------------------------

@name       setReadyStateChangedListener - set the readyStateChanged listener
                                                                              */
                                                                             /**
            Set the readyStateChanged listener.

@return     this

@param      readyStateChangedListener     readyStateChanged listener

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IHttpClient setReadyStateChangedListener(
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
IHttpClient setRequestHeader(
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
IHttpClient setRequestor(
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
IHttpClient setRequestToken(
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
IHttpClient setResponseType(
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
IHttpClient setStatusText(
   String statusText);

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
IHttpClient setTimeout();

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
IHttpClient setURL(
   String url);

}//====================================// IHttpClient ========================//
