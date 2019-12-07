/*==============================================================================

name:       FirebaseDatabaseService.java

purpose:    Firebase Database Service.

history:    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

notes:      '-generateJsInteropExports' must be included in Dev Mode parameters
            setting of GWT debug configuration for java methods to be
            exported to javascript

                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.providers.database.firebase;

                                       // imports --------------------------- //
import com.giavaneers.util.gwt.Logger;
import com.google.gwt.core.client.JavaScriptObject;
import elemental2.promise.Promise;
import io.reactjava.client.core.providers.database.IDatabaseService;
import io.reactjava.client.core.providers.database.firebase.FirebaseDatabaseService.Firebase.DataSnapshot;
import io.reactjava.client.core.providers.database.firebase.FirebaseDatabaseService.Firebase.Database;
import io.reactjava.client.core.providers.database.firebase.FirebaseDatabaseService.Firebase.Reference;
import io.reactjava.client.core.react.Configuration;
import io.reactjava.client.core.react.IConfiguration;
import io.reactjava.client.core.react.NativeObject;
import io.reactjava.client.core.react.Properties;
import io.reactjava.client.core.rxjs.observable.Observable;
import io.reactjava.client.core.rxjs.observable.Subscriber;
import io.reactjava.client.core.react.Utilities;
import java.util.HashMap;
import java.util.Map;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Any;
                                       // FirebaseDatabaseService ============//
@JsType                                // export to Javascript                //
public class FirebaseDatabaseService implements IDatabaseService
{
                                       // class constants --------------------//
private static final Logger   kLOGGER = Logger.newInstance();

public static final String    kKEY_APP      = "app";
public static final String    kKEY_DATABASE = "database";
                                       // class variables ------------------- //
protected static final String kINJECT_URL =
   "https://www.gstatic.com/firebasejs/7.5.0/firebase.js";

public static final String kEVENT_TYPE_CHILD_ADDED   = "child_added";
public static final String kEVENT_TYPE_CHILD_CHANGED = "child_changed";
public static final String kEVENT_TYPE_CHILD_MOVED   = "child_moved";
public static final String kEVENT_TYPE_CHILD_REMOVED = "child_removed";
public static final String kEVENT_TYPE_VALUE         = "value";

                                       // public instance variables --------- //
                                       // (none)
                                       // protected instance variables -------//
protected NativeObject props;            // properties                          //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       FirebaseDatabaseService - constructor
                                                                              */
                                                                             /**
            Constructor

@param      props    props

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public FirebaseDatabaseService(
   Properties props)
{
   this.props = props != null ? props.toNativeObject() : new NativeObject();
}
/*------------------------------------------------------------------------------

@name       configure - configuration routine
                                                                              */
                                                                             /**
            Configuration routine.

@return     void

@param      configurationData    configuration data

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Observable configure(
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

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected Observable configure(
   String apiKey,
   String authDomain,
   String databaseURL,
   String projectId,
   String storageBucket,
   String messagingSenderId)
{
   Observable<Database> observable =
      Observable.create((Subscriber<Database> subscriber) ->
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

               Object rsp;
               try
               {
                  props().set(kKEY_APP,      Firebase.initializeApp(config));
                  props().set(kKEY_DATABASE, Firebase.database());
                  rsp  = getDatabase();
               }
               catch(Exception e)
               {
                  kLOGGER.logError(e);
                  rsp = e;
               }

               if (rsp instanceof Throwable)
               {
                  subscriber.error((Throwable)rsp);
               }
               else
               {
                  subscriber.next((Database)rsp);
                  subscriber.complete();
               }
            });

         return(subscriber);
      });
   return(observable);
}
/*------------------------------------------------------------------------------

@name       get  - get data from specified path
                                                                              */
                                                                             /**
            Get data from the specified reference path

@return     Observable

@param      reference      record path

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Observable<Map<String,Object>> get(
   String reference)
{
   Observable<Map<String,Object>> observable =
      Observable.create((Subscriber<Map<String,Object>> subscriber) ->
      {
         Reference ref     = getDatabase().ref(reference);
         Promise   promise = ref.once(kEVENT_TYPE_VALUE);
         promise.then(
            data ->
            {
               subscriber.next(((DataSnapshot)data).toMap());
               subscriber.complete();
               return(promise);
            },
            error ->
            {
               subscriber.error(error);
               return(promise);
            });

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

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public JavaScriptObject getApp()
{
   return((JavaScriptObject) props().get(kKEY_APP));
}
/*------------------------------------------------------------------------------

@name       getDatabase - get database
                                                                              */
                                                                             /**
            Get database.

@return     database

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Database getDatabase()
{
   return((Database)props().get(kKEY_DATABASE));
}
/*------------------------------------------------------------------------------

@name       getConfiguration - get application configuration
                                                                              */
                                                                             /**
            Get application configuration

@return     application configuration.

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@SuppressWarnings("unusable-by-js")
public IConfiguration getConfiguration()
{
   IConfiguration configuration = (IConfiguration)props().get("configuration");
   if (configuration == null)
   {
      configuration = Configuration.sharedInstance();
      props().set("configuration", configuration);
   }
   return(configuration);
}
/*------------------------------------------------------------------------------

@name       initNative - get the Database service for the default app
                                                                              */
                                                                             /**
            Get the Database service for the default app

@param      defaultDatabase    default authentication provider

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final static native void initNative(
   Database defaultDatabase)
   throws   Exception
/*-{
      var email    = 'giavaneersdeveloper@gmail.com';
      var password = '1545 1/2 Pacific Avenue';
      try
      {
         var promise;
         promise = defaultDatabase.createUserWithEmailAndPassword(email, password)
            .then(function(user)
            {
                                       // promise succeeded                   //
               promise = defaultDatabase.signOut();
               return(promise);
            })
            .then(function()
            {
                                       // promise succeeded                   //
               promise = defaultDatabase.signInWithEmailAndPassword(email, password);
               return(promise);
            })
            .then(function(user)
            {
                                       // promise succeeded                   //
               promise = defaultDatabase.signOut();
               return(promise);
            })
            .then(function(user)
            {
                                       // promise succeeded                   //
               console.log("Successful!");
            },
            function(error)
            {
               switch(error.code)
               {
                  case 'auth/email-already-in-use':
                  {
                                       // createUserWithEmailAndPassword()    //
                     $wnd.console.log(error.message);
                     break;
                  }
                  case 'auth/operation-not-allowed':
                  {
                                       // createUserWithEmailAndPassword()    //
                     $wnd.console.log(error.message);
                     break;
                  }
                  case 'auth/weak-password':
                  {
                                       // createUserWithEmailAndPassword()    //
                     $wnd.console.log(error.message);
                     break;
                  }
                  case 'auth/invalid-email':
                  {
                                       // createUserWithEmailAndPassword()    //
                                       // signInWithEmailAndPassword()        //
                     $wnd.console.log(error.message);
                     break;
                  }
                  case 'auth/user-disabled':
                  {
                                       // signInWithEmailAndPassword()        //
                     $wnd.console.log(error.message);
                     break;
                  }
                  case 'auth/user-not-found':
                  {
                                       // signInWithEmailAndPassword()        //
                     $wnd.console.log(error.message);
                     break;
                  }
                  case 'auth/wrong-password':
                  {
                                       // signInWithEmailAndPassword()        //
                     $wnd.console.log(error.message);
                     break;
                  }
                  default:
                  {
                     $wnd.console.log(error.message);
                  }
               }
            });
      }
      catch (err)
      {
         $wnd.console.log("Could not get the Database service for the default app");
         $wnd.console.log(err.message);
      }
}-*/;
/*------------------------------------------------------------------------------

@name       props - get properties
                                                                              */
                                                                             /**
            Get properties.

@return     properties

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

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

@name       put  - put data to specified path
                                                                              */
                                                                             /**
            Put the specified data to the specified reference path

@return     Observable

@param      reference   record path
@param      value       data

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Observable<String> put(
   String             reference,
   Map<String,String> value)
{
   Observable<String> observable =
      Observable.create((Subscriber<String> subscriber) ->
      {
         Reference ref     = getDatabase().ref(reference);
         Promise   promise = ref.set(NativeObject.with(value));
         promise.then(
            result ->
            {
               subscriber.next(result != null ? result.toString() : null);
               subscriber.complete();
               return(promise);
            },
            error ->
            {
               subscriber.error(error);
               return(promise);
            });

         return(subscriber);
      });
   return(observable);
}
/*==============================================================================

name:       Firebase - core compatible Firebase

purpose:    GWT compatible Firebase

history:    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

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
public static native Database database() throws Exception;

/*------------------------------------------------------------------------------

@name       initializeApp - initialize app
                                                                              */
                                                                             /**
            Iinitialize app

@param      config      configuration

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public static native JavaScriptObject initializeApp(Object config);

/*==============================================================================

name:       Database - core compatible Firebase Database service

purpose:    GWT compatible Firebase Database service

history:    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@jsinterop.annotations.JsType(
   isNative = true, namespace = "firebase.database", name = "Database")
public static class Database
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

@name       ref  - get Reference for specified path
                                                                              */
                                                                             /**
            Get Reference for specified path.

@return     firebase.Database

@param      email       email
@param      password    password

@example
            firebase.auth().createUserWithEmailAndPassword(email, password)
                .catch(function(error) {
              // Handle Errors here.
              var errorCode = error.code;
              var errorMessage = error.message;
              if (errorCode == 'auth/weak-password') {
                alert('The password is too weak.');
              } else {
                alert(errorMessage);
              }
              console.log(error);
            });

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native Reference ref(
   String String);

}//====================================// Database ===========================//
/*==============================================================================

name:       DataSnapshot - database data

purpose:    database data

history:    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@jsinterop.annotations.JsType(
   isNative = true, namespace = "firebase.database", name = "DataSnapshot")
public static class DataSnapshot
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
@JsProperty
public String    key;                  // native key                          //
@JsProperty
public Reference ref;                  // native reference                    //
                                       // protected instance variables ------ //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       toMap - snapshot to map
                                                                              */
                                                                             /**
            Get Map representation of snapshot

@return     Map representation of snapshot.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Map<String,Object> toMap()
{
   Map<String,Object> map = null;
   String             key = this.key;
   Any                val = val();

   if (val != null)
   {
      map = new HashMap<>();
      map.put(key, val);
   }
   return(map);
}
/*------------------------------------------------------------------------------

@name       val - get snapshot value
                                                                              */
                                                                             /**
            Get snapshot value.

@return     Promise

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native Any val();

}//====================================// DataSnapshot =======================//
/*==============================================================================

name:       Reference - core compatible Firebase Database service

purpose:    GWT compatible Firebase Database service

history:    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@jsinterop.annotations.JsType(
   isNative = true, namespace = "firebase.database", name = "Reference")
public static class Reference
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       once - read data from specified reference
                                                                              */
                                                                             /**
            Read data from specified reference.

@return     Promise

@param      eventType      event type

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native Promise once(
   String eventType);

/*------------------------------------------------------------------------------

@name       set - write data to specified reference
                                                                              */
                                                                             /**
            Write data to specified reference.

@return     Promise

@param      value       data

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native Promise set(
   NativeObject value);

}//====================================// Reference ==========================//
}//====================================// Firebase ===========================//
}//====================================// end FirebaseDatabaseService ========//
