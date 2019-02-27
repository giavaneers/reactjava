/*==============================================================================

name:       PlatformWeb.java

purpose:    Web Platform.

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:      '-generateJsInteropExports' must be included in Dev Mode parameters
            setting of GWT debug configuration for java methods to be
            exported to javascript

                  This program was created by Giavaneers
        and is the confidential and proprietary product of Giavaneers Inc.
      Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2017 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.


==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.providers.platform.web;
                                       // imports --------------------------- //
import io.reactjava.client.core.providers.platform.AbstractPlatform;
import io.reactjava.client.core.react.Properties;

                                       // PlatformWeb ========================//
public class PlatformWeb extends AbstractPlatform
{
                                       // class constants --------------------//
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // protected instance variables ------ //

/*------------------------------------------------------------------------------

@name       PlatformWeb - constructor
                                                                              */
                                                                             /**
            Constructor

@return     An instance of PlatformWeb iff successful.

@param      method            method
@param      url               target url
@param      bAsync            true iff async

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public PlatformWeb()
{
   this(null);
}
/*------------------------------------------------------------------------------

@name       PlatformWeb - constructor
                                                                              */
                                                                             /**
            Constructor

@return     An instance of PlatformWeb iff successful.

@param      method            method
@param      url               target url
@param      bAsync            true iff async

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public PlatformWeb(
   Properties props)
{
   super(props);
}
/*------------------------------------------------------------------------------

@name       getOS - get operating system
                                                                              */
                                                                             /**
            Get operating system.

@return     operating system

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getOS()
{
   return(kPLATFORM_WEB);
}
}//====================================// end PlatformWeb ======================//
