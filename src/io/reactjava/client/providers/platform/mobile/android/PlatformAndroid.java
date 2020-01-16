/*==============================================================================

name:       PlatformAndroid.java

purpose:    Android Platform.

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.providers.platform.mobile.android;
                                       // imports --------------------------- //
import io.reactjava.client.providers.platform.mobile.AbstractMobilePlatform;
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

@param      props            props

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
