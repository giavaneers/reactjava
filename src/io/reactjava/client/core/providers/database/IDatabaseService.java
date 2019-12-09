/*==============================================================================

name:       IDatabaseService.java

purpose:    Database Service Interface.

history:    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

note:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.providers.database;
                                       // imports --------------------------- //
import io.reactjava.client.core.rxjs.observable.Observable;
import io.reactjava.client.core.react.IProvider;
import java.util.Map;
import jsinterop.annotations.JsFunction;
                                       // IDatabaseService ===================//
public interface IDatabaseService extends IProvider
{
                                       // class constants --------------------//
public static final String kEVENT_TYPE_CHILD_ADDED   = "child_added";
public static final String kEVENT_TYPE_CHILD_CHANGED = "child_changed";
public static final String kEVENT_TYPE_CHILD_MOVED   = "child_moved";
public static final String kEVENT_TYPE_CHILD_REMOVED = "child_removed";
public static final String kEVENT_TYPE_OFF           = "off";
public static final String kEVENT_TYPE_VALUE         = "value";

                                       // class variables ------------------- //
                                       // (none)                              //
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
   Object configurationData);

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
Observable<Map<String,Object>> get(
   String reference);

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
   IEventCallback callback);

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
   IEventCallback callback);

/*------------------------------------------------------------------------------

@name       put  - put data to specified path
                                                                              */
                                                                             /**
            Put the specified data to the specified reference path

@return     Observable

@param      path        record path
@param      record      data

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Observable<String> put(
   String             path,
   Map<String,String> record);

/*------------------------------------------------------------------------------

@name       remove  - remove record at specified path
                                                                              */
                                                                             /**
            Remove record and all of its decendants at specified reference path

@return     Observable

@param      path        record path

@history    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Observable<String> remove(
   String path);

/*==============================================================================

name:       IEventCallback - marker interface

purpose:    General event callback interface

history:    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@JsFunction
public interface IEventCallback
{
   void handleEvent(Map<String,Object> dataSnapshot, String prevChildKey);
}//====================================// IEventCallback =====================//
}//====================================// end IDatabaseService ===============//
