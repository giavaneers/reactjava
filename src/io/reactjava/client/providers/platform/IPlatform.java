/*==============================================================================

name:       IPlatform.java

purpose:    Platform Interface.

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.providers.platform;
                                       // imports --------------------------- //
import io.reactjava.client.core.react.IProvider;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
                                       // IPlatform ==========================//
public interface IPlatform extends IProvider
{
                                       // class constants --------------------//
public static final String kPLATFORM_ANDROID = "PlatformAndroid";
public static final String kPLATFORM_IOS     = "PlatformIOS";
public static final String kPLATFORM_WEB     = "PlatformWeb";

public static final Set<String> kPLATFORMS =
   new HashSet<String>(
      Arrays.asList(
         new String[]{kPLATFORM_ANDROID, kPLATFORM_IOS, kPLATFORM_WEB}));

                                       // class variables ------------------- //
                                       // (none)                              //
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
String getOS();

/*------------------------------------------------------------------------------

@name       getTagMapDefault - get default tag map
                                                                              */
                                                                             /**
            Get default map of any replacement tag by standard tag. A tag is
            resolved by first checking whether there is a replacement in any
            custom tag map and if not, checking whether there is a replacement
            in the default tag map.

            For example, in the React configuration, there exists an entry
            for the tag "div" whose entry is "View".

@return     tag map

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
Map<String,String> getTagMapDefault();

/*------------------------------------------------------------------------------

@name       resolveCSSPropertyValue - resolve specified css value for platform
                                                                              */
                                                                             /**
            Resolve specified css value for specified css property for platform

@return     The resolved css value

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Object resolveCSSPropertyValue(
   String  property,
   String  value);

/*------------------------------------------------------------------------------

@name       resolveTag - resolve specified tag name for platform
                                                                              */
                                                                             /**
            Resolve specified tag name for platform

@return     The resolved tag name

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String resolveTag(
   String  tagName,
   boolean bText);

}//====================================// end IPlatform ======================//
