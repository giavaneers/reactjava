/*==============================================================================

name:       IHttpResponse - http response interface

purpose:    GWT compatible Http client provider interface

history:    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.providers.http;
                                       // imports --------------------------- //
import java.util.Map;
                                       // IHttpResponse ======================//
public interface IHttpResponse
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       getBytes - get response data
                                                                              */
                                                                             /**
            Get response data.

@return     response data

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
byte[] getBytes();

/*------------------------------------------------------------------------------

@name       getClient - get http client
                                                                              */
                                                                             /**
            Get http client.

@return     http client

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
IHttpClientBase getClient();

/*------------------------------------------------------------------------------

@name       getError - get response error
                                                                              */
                                                                             /**
            Get response error.

@return     response error

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
Throwable getError();

/*------------------------------------------------------------------------------

@name       getHeaders - get all response headers
                                                                              */
                                                                             /**
            Get all response headers.

@return     all response headers

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Map<String,String> getHeaders();

/*------------------------------------------------------------------------------

@name       getStatus - get response status code
                                                                              */
                                                                             /**
            Get response status code

@return     response status code

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
int getStatus();

/*------------------------------------------------------------------------------

@name       getStatusText - get response status text
                                                                              */
                                                                             /**
            Get response status text

@return     response status text

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
String getStatusText();

/*------------------------------------------------------------------------------

@name       getText - get response text
                                                                              */
                                                                             /**
            Get response text.

@return     response text

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
String getText();

/*------------------------------------------------------------------------------

@name       getType - get response type
                                                                              */
                                                                             /**
            Get response type.

@return     response type

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
ResponseType getType();

/*==============================================================================

name:       ResponseType - response type

purpose:    response type

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static enum ResponseType
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
   kARRAYBUFFER ("arraybuffer"),
   kBLOB        ("blob"),
   kDOCUMENT    ("document"),
   kJSON        ("json"),
   kTEXT        ("text"),
   kDEFAULT     ("text");
                                       // protected instance variables ------ //
protected String value;
                                       // constructor                         //
ResponseType(String value)
{
   this.value = value;
}
/*------------------------------------------------------------------------------

@name       getHttpClient - get http client
                                                                              */
                                                                             /**
            Get http client.

@return     http client

@history    Fri Nov 09, 2018 10:30:00 (Giavaneers - LBM) created

                                                                              */
//------------------------------------------------------------------------------
public static ResponseType fromString(
   String type)
{
   ResponseType responseType;
   switch(type)
   {
      case "arraybuffer":
      {
         responseType = kARRAYBUFFER;
         break;
      }
      case "blob":
      {
         responseType = kBLOB;
         break;
      }
      case "document":
      {
         responseType = kDOCUMENT;
         break;
      }
      case "json":
      {
         responseType = kJSON;
         break;
      }
      case "text":
      {
         responseType = kTEXT;
         break;
      }
      default:
      {
         responseType = kDEFAULT;
      }
   }
   return(responseType);
}
}//====================================// ResponseType -----------------------//
}//====================================// IHttpClient ========================//
