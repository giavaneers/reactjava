/*==============================================================================

name:       AbstractPlatform.java

purpose:    Abstract Platform.

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
package io.reactjava.client.core.providers.platform;
                                       // imports --------------------------- //
import io.reactjava.client.core.react.Properties;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
                                       // AbstractPlatform ===================//
public abstract class AbstractPlatform implements IPlatform
{
                                       // class constants --------------------//
public static final Set<String> kCSS_NUMERIC_PROPERTIES =
   new HashSet<String>()
   {{
      add("fontSize");
      add("margin");
      add("marginTop");
      add("marginBottom");
      add("marginLeft");
      add("marginRight");
   }};
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
protected Properties props;            // properties                          //

/*------------------------------------------------------------------------------

@name       AbstractPlatform - constructor
                                                                              */
                                                                             /**
            Constructor

@return     An instance of AbstractPlatform iff successful.

@param      method            method
@param      url               target url
@param      bAsync            true iff async

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public AbstractPlatform()
{
   this(null);
}
/*------------------------------------------------------------------------------

@name       AbstractPlatform - constructor
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
public AbstractPlatform(
   Properties props)
{
   this.props = props;
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
   return(null);
}
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
public Object resolveCSSPropertyValue(
   String  property,
   String  value)
{
   Object resolved = value;

   //if (kCSS_NUMERIC_PROPERTIES.contains(property))
   //{
   //   int    i;
   //   char[] chars = value.toCharArray();
   //   for (i = 0; i < chars.length; i++)
   //   {
   //      if (!Character.isDigit(chars[i]))
   //      {
   //         break;
   //      }
   //   }
   //
   //   resolved = Integer.parseInt(value.substring(0, i));
   //}
   return(resolved);
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
   return(null);
}
}//====================================// end AbstractPlatform ===============//
