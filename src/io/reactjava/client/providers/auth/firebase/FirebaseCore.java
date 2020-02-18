/*==============================================================================

name:       FirebaseCore.java

purpose:    Firebase Services Core. All instances share the same configuration

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:      '-generateJsInteropExports' must be included in Dev Mode parameters
            setting of GWT debug configuration for java methods to be
            exported to javascript

                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.providers.auth.firebase;

                                       // imports --------------------------- //
import com.giavaneers.util.gwt.Logger;
import elemental2.core.JsObject;
import elemental2.promise.Promise;
import io.reactjava.client.core.react.Configuration;
import io.reactjava.client.core.react.IConfiguration;
import io.reactjava.client.core.react.IConfiguration.ICloudServices;
import io.reactjava.client.core.react.NativeObject;
import io.reactjava.client.core.rxjs.observable.Observable;
import io.reactjava.client.core.rxjs.observable.Subscriber;
import io.reactjava.client.core.react.Utilities;
import java.util.ArrayList;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
                                       // FirebaseCore =======================//
@JsType                                // export to Javascript                //
public class FirebaseCore
{
                                       // class constants --------------------//
private static final Logger   kLOGGER = Logger.newInstance();

public static final String    kKEY_ANALYTICS    = "analytics";
public static final String    kKEY_APP          = "app";
public static final String    kKEY_AUTH         = "auth";
public static final String    kKEY_CONFIG       = "configuration";
public static final String    kKEY_DATABASE     = "database";

protected static final String kSCRIPT_ANALYTICS = "firebase-analytics.js";
protected static final String kSCRIPT_APP       = "firebase-app.js";
protected static final String kSCRIPT_AUTH      = "firebase-auth.js";
protected static final String kSCRIPT_DATABASE  =  "firebase-database.js";
protected static final String kSCRIPT_VERSION   = "7.6.1";

protected static final String kINJECT_URL_BASE  =
   "https://www.gstatic.com/firebasejs/";

protected static final String kINJECT_URL_VERSION =
   kINJECT_URL_BASE + kSCRIPT_VERSION + "/";

protected static final String kINJECT_URL_ANALYTICS =
   kINJECT_URL_VERSION + kSCRIPT_ANALYTICS;

protected static final String kINJECT_URL_APP =
   kINJECT_URL_VERSION + kSCRIPT_APP;

protected static final String kINJECT_URL_AUTH =
   kINJECT_URL_VERSION + kSCRIPT_AUTH;

protected static final String kINJECT_URL_DATABASE =
   kINJECT_URL_VERSION + kSCRIPT_DATABASE;

                                       // class variables ------------------- //
protected static NativeObject props;   // properties                          //
                                       // public instance variables --------- //
                                       // (none)
                                       // protected instance variables -------//
                                       // (none)
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       configure - configuration routine
                                                                              */
                                                                             /**
            Configuration routine.

@return     Observable

@param      cloudConfig    cloud services config

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Observable configure(
   ICloudServices cloudConfig)
{
   Observable obs  =
      configure(
         cloudConfig.getAPIKey(),
         cloudConfig.getAuthDomain(),
         cloudConfig.getDatabaseURL(),
         cloudConfig.getProjectId(),
         cloudConfig.getStorageBucket(),
         cloudConfig.getMessagingSenderId(),
         cloudConfig.getAppId(),
         cloudConfig.getTrackingId());

   return(obs);
}
/*------------------------------------------------------------------------------

@name       configure - configuration routine
                                                                              */
                                                                             /**
            Configuration routine.

@return     void

@param      apiKey               apiKey
@param      authDomain           authDomain
@param      databaseURL          databaseURL
@param      projectId            projectId
@param      storageBucket        storageBucket
@param      messagingSenderId    messagingSenderId

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static Observable configure(
   String apiKey,
   String authDomain,
   String databaseURL,
   String projectId,
   String storageBucket,
   String messagingSenderId)
{
   return(
      configure(
         apiKey, authDomain, databaseURL, projectId, storageBucket,
         messagingSenderId, "", ""));
}
/*------------------------------------------------------------------------------

@name       configure - configuration routine
                                                                              */
                                                                             /**
            Configuration routine.

@return     void

@param      apiKey               apiKey
@param      authDomain           authDomain
@param      databaseURL          databaseURL
@param      projectId            projectId
@param      storageBucket        storageBucket
@param      messagingSenderId    messagingSenderId
@param      appId                firebase appId
@param      measurementId        analytics trackingId

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static Observable configure(
   String apiKey,
   String authDomain,
   String databaseURL,
   String projectId,
   String storageBucket,
   String messagingSenderId,
   String appId,
   String measurementId)
{
   Observable<NativeObject> observable = Observable.create(
      (Subscriber<NativeObject> subscriber) ->
      {
                                       // inject script, configure only once  //
         if (props().get(kKEY_CONFIG) != null)
         {
            subscriber.next(props());
            subscriber.complete();
         }
         else if (apiKey == null || projectId == null || appId == null)
         {
                                       // ensure minimum config spec          //
            kLOGGER.logInfo(
               "Firebase not initialized "
             + "since apiKey, projectId and appId were not all specified");
            subscriber.next(props());
            subscriber.complete();
         }
         else
         {
            ArrayList<String> scripts = new ArrayList<>();
            scripts.add(kINJECT_URL_APP);

            //if (measurementId != null)
            //{
            //   scripts.add(kINJECT_URL_ANALYTICS);
            //}
            if (authDomain != null)
            {
               scripts.add(kINJECT_URL_AUTH);
            }
            if (databaseURL != null)
            {
               scripts.add(kINJECT_URL_DATABASE);
            }
                                       // inject the required scripts         //
            Utilities.injectScriptsAndCSS(
               getConfiguration(), scripts, null,
               (Object response, Object reqToken) ->
               {
                  NativeObject config =
                     NativeObject.with(
                        "apiKey",            apiKey,
                        "authDomain",        authDomain,
                        "databaseURL",       databaseURL,
                        "projectId",         projectId,
                        "storageBucket",     storageBucket,
                        "messagingSenderId", messagingSenderId,
                        "appId",             appId,
                        "measurementId",     measurementId);
                  try
                  {
                     props().set(kKEY_CONFIG, config);
                     props().set(kKEY_APP,    Firebase.initializeApp(config));

                     //if (measurementId != null)
                     //{
                     //                  // using google analytics exclusively  //
                     //                  // for now                             //
                     //   props().set(kKEY_ANALYTICS, Firebase.analytics());
                     //}
                     if (authDomain != null)
                     {
                        props().set(kKEY_AUTH, Firebase.auth());
                     }
                     if (databaseURL != null)
                     {
                        props().set(kKEY_DATABASE, Firebase.database());
                     }

                     subscriber.next(props());
                     subscriber.complete();
                  }
                  catch(Exception e)
                  {
                     kLOGGER.logError(e);
                     subscriber.error(e);
                  }
               });
         }

         return(subscriber);
      });

   return(observable);
}
/*------------------------------------------------------------------------------

@name       getAnalytics - get analytics
                                                                              */
                                                                             /**
            Get analytics.

@return     analytics

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static JsObject getAnalytics()
{
   return((JsObject)props().get(kKEY_ANALYTICS));
}
/*------------------------------------------------------------------------------

@name       getApp - get app
                                                                              */
                                                                             /**
            Get app.

@return     app

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static JsObject getApp()
{
   return((JsObject)props().get(kKEY_APP));
}
/*------------------------------------------------------------------------------

@name       getAuth - get auth
                                                                              */
                                                                             /**
            Get auth.

@return     auth

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static JsObject getAuth()
{
   return((JsObject)props().get(kKEY_AUTH));
}
/*------------------------------------------------------------------------------

@name       getConfiguration - get application configuration
                                                                              */
                                                                             /**
            Get application configuration

@return     application configuration.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@SuppressWarnings("unusable-by-js")
public static IConfiguration getConfiguration()
{
   IConfiguration configuration = (IConfiguration)props().get(kKEY_CONFIG);
   if (configuration == null)
   {
      configuration = Configuration.sharedInstance();
      props().set(kKEY_CONFIG, configuration);
   }
   return(configuration);
}
/*------------------------------------------------------------------------------

@name       getDatabase - get database
                                                                              */
                                                                             /**
            Get database.

@return     database

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static JsObject getDatabase()
{
   return((JsObject)props().get(kKEY_DATABASE));
}
/*------------------------------------------------------------------------------

@name       getErrorMessage - get error message
                                                                              */
                                                                             /**
            Get error message.

@return     error message

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getErrorMessage(
   Object error)
{
   NativeObject errorObj = Js.uncheckedCast(error);
   String       message  = errorObj.getString("message");
   return(message);
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
public static NativeObject props()
{
   if (props == null)
   {
      props = new NativeObject();
   }
   return(props);
}
/*==============================================================================

name:       Firebase - core compatible Firebase

purpose:    GWT compatible Firebase

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@jsinterop.annotations.JsType(
   isNative = true, namespace= JsPackage.GLOBAL, name = "firebase")
public static class Firebase
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

@name       analytics - get the Analytics service for the default app
                                                                              */
                                                                             /**
            Get the Analytics service for the default app

@return     Analytics service for the default app

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public static native JsObject analytics() throws Exception;

/*------------------------------------------------------------------------------

@name       auth - get the Auth service for the default app
                                                                              */
                                                                             /**
            Get the Auth service for the default app

@return     Auth service for the default app

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public static native JsObject auth() throws Exception;

/*------------------------------------------------------------------------------

@name       database - get the Database service for the default app
                                                                              */
                                                                             /**
            Get the Database service for the default app

@return     Database service for the default app

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public static native JsObject database() throws Exception;

/*------------------------------------------------------------------------------

@name       initializeApp - initialize app
                                                                              */
                                                                             /**
            Iinitialize app

@param      config      configuration

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public static native JsObject initializeApp(Object config);

}//====================================// Firebase ===========================//
}//====================================// end FirebaseCore ===================//
