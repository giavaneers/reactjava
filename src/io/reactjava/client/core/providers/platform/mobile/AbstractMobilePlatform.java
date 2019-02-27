/*==============================================================================

name:       AbstractMobilePlatform.java

purpose:    Mobile Platform.

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
package io.reactjava.client.core.providers.platform.mobile;
                                       // imports --------------------------- //
import io.reactjava.client.core.providers.platform.AbstractPlatform;
import io.reactjava.client.core.react.Properties;
import java.util.HashMap;
import java.util.Map;
                                    // AbstractMobilePlatform ================//
public abstract class AbstractMobilePlatform extends AbstractPlatform
{
                                       // class constants --------------------//
public static final String kREACT_NATIVE_BUTTON = "ReactNative.Button";
public static final String kREACT_NATIVE_TEXT   = "ReactNative.Text";
public static final String kREACT_NATIVE_VIEW   = "ReactNative.View";

public static final Map<String,String> kTAG_MAP_DEFAULT =
   new HashMap<String,String>()
   {{
      put("div",    kREACT_NATIVE_VIEW);
      put("h1",     kREACT_NATIVE_TEXT);
      put("button", kREACT_NATIVE_BUTTON);
   }};
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // protected instance variables ------ //

/*------------------------------------------------------------------------------

@name       AbstractMobilePlatform - constructor
                                                                              */
                                                                             /**
            Constructor

@return     An instance of AbstractMobilePlatform iff successful.

@param      method            method
@param      url               target url
@param      bAsync            true iff async

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public AbstractMobilePlatform()
{
   this(null);
}
/*------------------------------------------------------------------------------

@name       AbstractMobilePlatform - constructor
                                                                              */
                                                                             /**
            Constructor

@return     An instance of AbstractMobilePlatform iff successful.

@param      method            method
@param      url               target url
@param      bAsync            true iff async

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public AbstractMobilePlatform(
   Properties props)
{
   super(props);
}
/*------------------------------------------------------------------------------

@name       getTagMapDefault - get default tag map
                                                                              */
                                                                             /**
            Get default map of any replacement tag by standard tag. A tag is
            resolved by first checking whether there is a replacement in any
            custom tag map and if not, checking whether there is a replacement
            in the default tag map.

            For example, in the React configuration, there exists an entry
            for the tag "div" whose entry is
            "node_modules.react-native.Libraries.Components.View".

@return     tag map

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public Map<String,String> getTagMapDefault()
{
   return(kTAG_MAP_DEFAULT);
}
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
public String resolveTag(
   String  tagName,
   boolean bText)
{
   String replacement = getTagMapDefault().get(tagName);
   if (bText && kREACT_NATIVE_VIEW.equals(replacement))
   {
      replacement = kREACT_NATIVE_TEXT;
   }
   if (replacement != null)
   {
      tagName = replacement;
   }

   return(tagName);
}
}//====================================// end PlatformWeb ====================//
