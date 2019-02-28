/*==============================================================================

name:       APIRequestor

purpose:    General requestor callback.

history:    Sat Jan 12, 2013 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package com.giavaneers.util.gwt;
                                       // imports --------------------------- //
                                       // (none)                              //
                                       // APIRequestor =======================//
public interface APIRequestor
{
                                       // constants ------------------------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       apiResponse - api response notification
                                                                              */
                                                                             /**
            API response notification

@return     void

@param      response    runtime response.

@history    Sat Jan 12, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void apiResponse(
  Object response,
  Object requestToken);

}//====================================// end APIRequestor -------------------//
