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
                                       // IDatabaseService ===================//
public interface IDatabaseService extends IProvider
{
                                       // class constants --------------------//
                                       // (none)                              //
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

}//====================================// end IDatabaseService ===============//
