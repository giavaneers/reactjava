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
package io.reactjava.client.core.providers.auth.firebase;

                                       // imports --------------------------- //
import com.giavaneers.util.gwt.Logger;
import elemental2.core.JsObject;
import io.reactjava.client.core.react.Configuration;
import io.reactjava.client.core.react.IConfiguration;
import io.reactjava.client.core.react.NativeObject;
import io.reactjava.client.core.rxjs.observable.Observable;
import io.reactjava.client.core.rxjs.observable.Subscriber;
import io.reactjava.client.core.react.Utilities;
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

public static final String    kKEY_APP      = "app";
public static final String    kKEY_AUTH     = "auth";
public static final String    kKEY_CONFIG   = "configuration";
public static final String    kKEY_DATABASE = "database";

protected static final String kINJECT_URL =
   "https://www.gstatic.com/firebasejs/7.5.0/firebase.js";

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

@return     void

@param      configurationData    configuration data

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Observable configure(
   Object configurationData)
{
   if (!(configurationData instanceof String[])
         || ((String[])configurationData).length != 6)
   {
      throw new IllegalArgumentException(
         "Configuration data must be String[]"
       + "{apiKey, authDomain, databaseURL, projectId, storageBucket, "
       + "messagingSenderId}");
   }

   String[] args = (String[])configurationData;

   return(configure(args[0], args[1], args[2], args[3], args[4], args[5]));
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
   Observable<NativeObject> observable = Observable.create(
      (Subscriber<NativeObject> subscriber) ->
      {
                                       // inject script, configure only once  //
         if (props().get(kKEY_CONFIG) != null)
         {
            subscriber.next(props());
            subscriber.complete();
         }
         else
         {
            Utilities.injectScriptOrCSS(
               getConfiguration(), kINJECT_URL, null,
               (Object response, Object reqToken) ->
               {
                  NativeObject config =
                     NativeObject.with(
                        "apiKey",            apiKey,
                        "authDomain",        authDomain,
                        "databaseURL",       databaseURL,
                        "projectId",         projectId,
                        "storageBucket",     storageBucket,
                        "messagingSenderId", messagingSenderId);
                  try
                  {
                     props().set(kKEY_APP,      Firebase.initializeApp(config));
                     props().set(kKEY_AUTH,     Firebase.auth());
                     props().set(kKEY_CONFIG,   config);
                     props().set(kKEY_DATABASE, Firebase.database());

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

@name       getDatabase - get database
                                                                              */
                                                                             /**
            Get database.

@return     database

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
