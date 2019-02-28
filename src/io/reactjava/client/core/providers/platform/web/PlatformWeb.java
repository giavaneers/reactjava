/*==============================================================================

name:       PlatformWeb.java

purpose:    Web Platform.

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

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
