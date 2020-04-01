/*==============================================================================

name:       Properties.java

purpose:    ReactJava Component Properties.

history:    Mon June 4, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.codegenerator.inspectortest;
                                       // imports --------------------------- //
import java.util.HashMap;
import jsinterop.annotations.JsOverlay;

// Properties =========================//
public class Properties extends HashMap
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Properties - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Properties()
{
}
/*------------------------------------------------------------------------------

@name       getBoolean - get boolean value of specified property
                                                                              */
                                                                             /**
            Get boolean value of specified property

@return     boolean value of specified property, or null if not found.

@param      propertyName      property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final boolean getBoolean(
   String propertyName)
{
   return(getBoolean(propertyName, false));
}
/*------------------------------------------------------------------------------

@name       getBoolean - get boolean value of specified property
                                                                              */
                                                                             /**
            Get boolean value of specified property

@return     boolean value of specified property, or null if not found.

@param      propertyName      property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final boolean getBoolean(
   String  propertyName,
   boolean defaultValue)
{
   boolean booleanValue;

   Object value = get(propertyName);
   if (value == null)
   {
      booleanValue = defaultValue;
   }
   else if (value instanceof String)
   {
      booleanValue = "true".equals((String)value);
   }
   else
   {
      booleanValue = (Boolean)value;
   }
   return(booleanValue);
}
/*------------------------------------------------------------------------------

@name       getInt - get int value of specified property
                                                                              */
                                                                             /**
            Get int value of specified property

@return     int value of specified property, or null if not found.

@param      propertyName      property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes      see set() with invocation of Utilties.setObjectIntValueNative()
                                                                              */
//------------------------------------------------------------------------------
public final int getInt(
   String propertyName)
{
   int intValue;

   Object value = get(propertyName);
   if (value instanceof String)
   {
      intValue = Integer.parseInt((String)value);
   }
   else
   {
      intValue = (Integer)value;
   }

   return(intValue);
}
/*------------------------------------------------------------------------------

@name       getString - get string value of specified property
                                                                              */
                                                                             /**
            Get string value of specified property

@return     string value of specified property, or null if not found.

@param      propertyName      property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final String getString(
   String propertyName)
{
   return((String)get(propertyName));
}
/*------------------------------------------------------------------------------

@name       set - set value of specified property
                                                                              */
                                                                             /**
            Set value of specified property

@return     void

@param      propertyName      property name
@param      value             value of specified property

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final Properties set(
   String propertyName,
   Object value)
{
   put(propertyName, value);
   return(this);
}
/*------------------------------------------------------------------------------

@name       with - factory method
                                                                              */
                                                                             /**
            Factory method creating a Properties instance containing the
            specified properties specified as a sequence iof tuples, where
            each tuple consists of a String property name followed by a
            value object reference.

            For example:

               Properties myProperties =
                  Properties.with("text", "MyText", "callback", MyCallbackFcn);

             or to create a copy of an instance adding new/overriding properties:

               Properties template =
                  Properties.with("name", "MyName", "callback", OrigCallbackFcn);

               Properties myProperties =
                  Properties.with(
                     template, "text", "MyText", "callback", MyCallbackFcn);


@return     An instance of Properties if successful.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final static Properties with(Object... args)
{
   return(new Properties());
}
/*------------------------------------------------------------------------------

@name       without - factory method
                                                                              */
                                                                             /**
            Factory method creating a Properties instance containing the
            specified properties template without the properties referenced
            by the specified list of keys.

            For example:

               Properties template =
                  Properties.with("text", "MyText", "callback", MyCallbackFcn);

               Properties templateWithout =
                  Properties.without(template, "callback");

             yields the same as:

               Properties.with("text", "MyText");

@return     An instance of Properties if successful.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final static Properties without(Properties ref, String... keys)
{
   return(new Properties());
}
}//====================================// end Properties ---------------------//
