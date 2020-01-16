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
package io.reactjava.client.providers.database.firebase;

                                       // imports --------------------------- //
import elemental2.promise.Promise;
import io.reactjava.client.providers.auth.firebase.FirebaseCore;
import io.reactjava.client.providers.database.IDatabaseService;
import io.reactjava.client.providers.database.firebase.FirebaseDatabaseService.Reference.INativeEventCallback;
import io.reactjava.client.core.react.NativeObject;
import io.reactjava.client.core.react.Properties;
import io.reactjava.client.core.rxjs.observable.Observable;
import io.reactjava.client.core.rxjs.observable.Subscriber;
import java.util.HashMap;
import java.util.Map;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Any;
import jsinterop.base.Js;
                                       // FirebaseDatabaseService ============//
@JsType                                // export to Javascript                //
public class FirebaseDatabaseService implements IDatabaseService
{
                                       // class constants --------------------//
                                       // (none)
                                       // class variables ------------------- //
                                       // (none)
                                       // public instance variables --------- //
                                       // (none)
                                       // protected instance variables -------//
                                       // registered callbacks                //
protected Map<IEventCallback,INativeEventCallback>
                            registeredCallbacks;
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       FirebaseDatabaseService - constructor
                                                                              */
                                                                             /**
            Constructor

@param      props    props

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public FirebaseDatabaseService(
   Properties props)
{
}
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
public Observable configure(
   Object configurationData)
{
   return(null);
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
               subscriber.error(FirebaseCore.getErrorMessage(error));
               return(null);
            });

         return(subscriber);
      });
   return(observable);
}
/*------------------------------------------------------------------------------

@name       getStart  - get data from specified path for the specified event
                                                                              */
                                                                             /**
            Get data from the specified reference path for the specified event.

@return     event handler

@param      reference      record path
@param      eventType      kEVENT_TYPE_CHILD_ADDED, kEVENT_TYPE_CHILD_CHANGED,
                           kEVENT_TYPE_CHILD_MOVED, kEVENT_TYPE_CHILD_REMOVED,
                           or kEVENT_TYPE_VALUE.
@param      callback       event handler


@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IEventCallback getStart(
   String         reference,
   String         eventType,
   IEventCallback callback)
{
   try
   {
      INativeEventCallback nativeCallback =
         getDatabase().ref(reference).on(
            eventType,
            (DataSnapshot dataSnapshot, String anyPrevChildKey) ->
            {
               try
               {
                  Map<String,Object> map = dataSnapshot.toMap();
                  String       childName = map.keySet().toArray(new String[0])[0];
                  NativeObject value     = NativeObject.with(map.get(childName));

                  Map<String,Map<String,Object>> valueMap = new HashMap<>();
                  valueMap.put(childName, value.toMap());

                  callback.handleEvent(valueMap, anyPrevChildKey);
               }
               catch(Throwable t)
               {
                  throw t;
               }
            });
                                       // bind the client and native callbacks//
      getRegisteredCallbacks().put(callback, nativeCallback);
   }
   catch(Throwable t)
   {
      throw t;
   }

   return(callback);
}
/*------------------------------------------------------------------------------

@name       getStop - detach a previous get callback
                                                                              */
                                                                             /**
            Detach a previous get callback.

@param      reference      record path
@param      eventType      kEVENT_TYPE_CHILD_ADDED, kEVENT_TYPE_CHILD_CHANGED,
                           kEVENT_TYPE_CHILD_MOVED, kEVENT_TYPE_CHILD_REMOVED,
                           or kEVENT_TYPE_VALUE.
@param      callback       event handler


@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void getStop(
   String         reference,
   String         eventType,
   IEventCallback callback)
{
   INativeEventCallback nativeCallback = getRegisteredCallbacks().remove(callback);
   if (nativeCallback != null)
   {
      getDatabase().ref(reference).off(eventType, nativeCallback);
   }
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
   return((Database)Js.uncheckedCast(FirebaseCore.getDatabase()));
}
/*------------------------------------------------------------------------------

@name       getRegisteredCallbacks - get map of registered callbacks
                                                                              */
                                                                             /**
            Get map of registered callbacks

@return     map of registered callbacks.

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected Map<IEventCallback,INativeEventCallback> getRegisteredCallbacks()
{
   if (registeredCallbacks == null)
   {
      registeredCallbacks = new HashMap<>();
   }
   return(registeredCallbacks);
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
               subscriber.error(FirebaseCore.getErrorMessage(error));
               return(null);
            });

         return(subscriber);
      });
   return(observable);
}
/*------------------------------------------------------------------------------

@name       remove  - remove record at specified path
                                                                              */
                                                                             /**
            Remove record and all of its decendants at specified refernce path

@return     Observable

@param      reference      record path

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Observable<String> remove(
   String reference)
{
   Observable<String> observable =
      Observable.create((Subscriber<String> subscriber) ->
      {
         Reference ref     = getDatabase().ref(reference);
         Promise   promise = ref.remove();
         promise.then(
            result ->
            {
               subscriber.next(result != null ? result.toString() : null);
               subscriber.complete();
               return(promise);
            },
            error ->
            {
               subscriber.error(FirebaseCore.getErrorMessage(error));
               return(null);
            });

         return(subscriber);
      });
   return(observable);
}
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

@param      ref         reference path

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
   String ref);

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

@name       off - detach a previous on callback
                                                                              */
                                                                             /**
            Detach a previous on callback.

@param      eventType      event type
@param      callback       callback

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native void off(
   String               eventType,
   INativeEventCallback callback);

/*------------------------------------------------------------------------------

@name       on - read data from specified reference on specified change
                                                                              */
                                                                             /**
            Read data from specified reference on specified change.

@return     Promise

@param      eventType      event type
@param      callback       callback

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native INativeEventCallback on(
   String               eventType,
   INativeEventCallback callback);

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

@name       remove - remove record at specified reference
                                                                              */
                                                                             /**
            Remove record and all of its decendants at specified reference.

@return     Promise

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native Promise remove();

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

/*==============================================================================

name:       IEventCallback - marker interface

purpose:    General event callback interface

history:    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@JsFunction
public interface INativeEventCallback
{
   void handleEvent(DataSnapshot dataSnapshot, String prevChildKey);
}//====================================// IEventCallback =====================//
}//====================================// Reference ==========================//
}//====================================// end FirebaseDatabaseService ========//
