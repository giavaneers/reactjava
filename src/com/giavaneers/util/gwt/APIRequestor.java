/*==============================================================================

name:       APIRequestor

purpose:    General requestor callback.

history:    Sat Jan 12, 2013 10:30:00 (Giavaneers - LBM) created

notes:

                     This program was created by Giavaneers
            and is the confidential and proprietary product of Giavaneers.
         Any unauthorized use, reproduction or transfer is strictly prohibited.

                        COPYRIGHT 2013 BY GIAVANEERS, INC.
         (Subject to limited distribution and restricted disclosure only).
                              All rights reserved.

==============================================================================*/
                                       // package ----------------------------//
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
