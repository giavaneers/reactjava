/*==============================================================================

name:       HttpRequest - core compatible HttpRequest using ReactiveX

purpose:    GWT compatible HttpRequest using ReactiveX

            Simple usage for GET:

               HttpClient.get(url).subscribe(
                  HttpResponse rsp ->
                  {
                     subscriber.next(colorsBytes);
                  },
                  (HttpResponse error) ->
                  {
                     subscriber.error(error);
                  });


            Simple usage for PUT:

               byte[] payload = ...;
               HttpClient.put(url, payload).subscribe(
                  HttpResponse rsp ->
                  {
                     subscriber.next(colorsBytes);
                  },
                  (HttpResponse error) ->
                  {
                     subscriber.error(error);
                  });

history:    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

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
import com.google.gwt.http.client.URL;
import elemental2.core.ArrayBuffer;
import elemental2.core.Uint8Array;
import io.reactjava.client.core.react.Observable;
import io.reactjava.client.core.react.Subscriber;
import io.reactjava.client.providers.http.IHttpResponse.ResponseType;
import jsinterop.base.Js;
                                       // HttpClient =========================//
public class HttpClient extends HttpClientBase implements IHttpClient
{
                                       // constants ------------------------- //
private static final Logger kLOGGER = Logger.newInstance();

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       HttpClient - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public HttpClient()
{
   super();
}
/*------------------------------------------------------------------------------

@name       get - a convenience method to get from the specified url
                                                                              */
                                                                             /**
            Get from the specified url.

@return     An observable

@param      url      target url

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Observable<HttpResponse> get(
   String url)
{
   Observable<HttpResponse> observable =
      new HttpClient()
         .setURL(url)
         .setMethod(kGET)
         .send((byte[])null);

   return(observable);
}
/*------------------------------------------------------------------------------

@name       post - a convenience method to post to the specified url
                                                                              */
                                                                             /**
            Post to the specified url.

@return     An observable

@param      url      target url
@param      data     any payload

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Observable<HttpResponse> post(
   String url,
   byte[] data)
{
   Observable<HttpResponse> observable =
      new HttpClient()
         .setURL(url)
         .setMethod(kPOST)
         .send(data);

   return(observable);
}
/*------------------------------------------------------------------------------

@name       put - a convenience method to put to the specified url
                                                                              */
                                                                             /**
            Put to the specified url.

@return     An observable

@param      url      target url
@param      data     any payload

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Observable<HttpResponse> put(
   String url,
   byte[] data)
{
   Observable<HttpResponse> observable =
      new HttpClient()
         .setURL(url)
         .setMethod(kPUT)
         .send(data);

   return(observable);
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
public Observable<HttpResponse> send()
{
   return(send((byte[])null));
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
public Observable<HttpResponse> send(
   byte[] bytes)
{
   Uint8Array uint8Array = Js.uncheckedCast(bytes);
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
public Observable<HttpResponse> send(
   ArrayBuffer arrayBuffer)
{
   return(send(new Uint8Array(arrayBuffer)));
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
public Observable<HttpResponse> send(
   Uint8Array uint8Array)
{
   Observable<HttpResponse> observable = Observable.create(
      (Subscriber<HttpResponse> subscriber) ->
   {
      if (getReadyStateChangedListener() == null)
      {
         props.set(
            kKEY_RDY_STATE_CHANGED_LISTENER,
            new DefaultReadyStateChangeListener(this, subscriber));

            //kLOGGER.logInfo(
            //   "HttpClient.send(): assigned readyStateChangedListener="
            //  + getReadyStateChangedListener());
      }

      super.send(uint8Array);
      return(subscriber);
   });

   return(observable);
}
/*------------------------------------------------------------------------------

@name       sendFormData - sendFormData the request
                                                                              */
                                                                             /**
            Send the request.

@return     void

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Observable<HttpResponse> sendFormData(
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
public IHttpClient setErrorReason(
   String errorReason)
{
   super.setErrorReason(errorReason);
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
public IHttpClient setMethod(
   String method)
{
   super.setMethod(method);
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
public IHttpClient setReadyStateChangedListener(
   IReadyStateChangedListener readyStateChangedListener)
{
   super.setReadyStateChangedListener(readyStateChangedListener);
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
public IHttpClient setRequestHeader(
   String headerName,
   String value)
{
   super.setRequestHeader(headerName, value);
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
public IHttpClient setRequestor(
   APIRequestor requestor)
{
   super.setRequestor(requestor);
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
public IHttpClient setRequestToken(
   Object requestToken)
{
   super.setRequestToken(requestToken);
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
public IHttpClient setResponseType(
   ResponseType responseType)
{
   super.setResponseType(responseType);
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
public IHttpClient setStatusText(
   String statusText)
{
   super.setStatusText(statusText);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setTimeout - set that a timeout occurred
                                                                              */
                                                                             /**
            Set that a timeout occurred.

@return     http client

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IHttpClient setTimeout()
{
   super.setTimeout();
   return(this);
}
/*------------------------------------------------------------------------------

@name       setResponseType - set the specified response type
                                                                              */
                                                                             /**
            Set the specified response type.

@return     http client

@param      url      url

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
public IHttpClient setURL(
   String url)
{
   super.setURL(url);
   return(this);
}
/*==============================================================================

name:       DefaultReadyStateChangeListener - default readyStateChange listener

purpose:    Default readyStateChange listener

history:    Wed Jun 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class DefaultReadyStateChangeListener
   extends HttpClientBase.DefaultReadyStateChangeListener

{
                                       // class constants ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
protected final Subscriber subscriber;// subscriber                           //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       DefaultReadyStateChangeListener - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Mon Aug 18, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public DefaultReadyStateChangeListener(
   IHttpClient client,
   Subscriber  subscriber)
{
   super(client);
   this.subscriber = subscriber;
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
   HttpResponse response = new HttpResponse(getClient());
   Throwable    error    = response.getError();
   if (error != null)
   {
      kLOGGER.logError(
         "HttpClient.DefaultReadyStateChangeListener.signalCompletion(): "
       + "responding to subscriber with error=" + error);

      subscriber.error(error);
   }
   else
   {
      //kLOGGER.logInfo(
      //   "HttpClient.DefaultReadyStateChangeListener.processSuccess(): "
      // + "responding byte[] to subscriber");

      subscriber.next(response);
      subscriber.complete();
   }
}
}//====================================// DefaultReadyStateChangeListener ====//
}//====================================// HttpClient =========================//
