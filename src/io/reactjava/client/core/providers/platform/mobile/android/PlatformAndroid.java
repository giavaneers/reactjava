/*==============================================================================

name:       PlatformAndroid.java

purpose:    Android Platform.

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
package io.reactjava.client.core.providers.platform.mobile.android;
                                       // imports --------------------------- //
import io.reactjava.client.core.providers.platform.mobile.AbstractMobilePlatform;
import io.reactjava.client.core.react.Properties;

                                       // PlatformAndroid ====================//
public class PlatformAndroid extends AbstractMobilePlatform
{
                                       // class constants --------------------//
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       PlatformAndroid - constructor
                                                                              */
                                                                             /**
            Constructor

@return     An instance of PlatformAndroid iff successful.

@param      method            method
@param      url               target url
@param      bAsync            true iff async

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public PlatformAndroid()
{
   this(null);
}
/*------------------------------------------------------------------------------

@name       PlatformAndroid - constructor
                                                                              */
                                                                             /**
            Constructor

@return     An instance of PlatformAndroid iff successful.

@param      method            method
@param      url               target url
@param      bAsync            true iff async

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public PlatformAndroid(
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
   return(kPLATFORM_ANDROID);
}
}//====================================// end PlatformAndroid ================//
