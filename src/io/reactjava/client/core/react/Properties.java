/*==============================================================================

name:       Properties.java

purpose:    ReactJava Component Properties.

history:    Mon June 4, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                  This program was created by Giavaneers
         and is the confidential and proprietary product of Giavaneers Inc.
       Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2018 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.


==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import elemental2.core.JsObject;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsForEachCallbackFn;
import jsinterop.base.JsPropertyMap;
                                       // Properties =========================//
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class Properties extends JsObject
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

@return     An instance of Properties if successful.

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Properties()
{
   super();
}
/*------------------------------------------------------------------------------

@name       get - get value of specified property
                                                                              */
                                                                             /**
            Get value of specified property

@return     value of specified property, or null if not found.

@param      propertyName      property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Object get(
   String propertyName)
{
   return(Js.asPropertyMap(this).get(propertyName));
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
@JsOverlay
public final boolean getBoolean(
   String propertyName)
{
   boolean booleanValue;

   Object value = get(propertyName);
   if (value instanceof String)
   {
      booleanValue = "true".equals((String)value);
   }
   else
   {
      booleanValue = Utilities.getObjectBooleanValueNative(this, propertyName);
   }
   return(booleanValue);
}
/*------------------------------------------------------------------------------

@name       getChildren - get children property value
                                                                              */
                                                                             /**
            Get children property value

@return     property value, or null if not found.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Element getChildren()
{
   return(Js.uncheckedCast(get("children")));
}
/*------------------------------------------------------------------------------

@name       getComponent - get component of these properties
                                                                              */
                                                                             /**
            Get component of these properties

@return     value of component, or null if not found.

@param      propertyName      property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Component getComponent()
{
   return((Component)get("component"));
}
/*------------------------------------------------------------------------------

@name       getConfiguration - get application configuration
                                                                              */
                                                                             /**
            Get application configuration

@return     application configuration.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final IConfiguration getConfiguration()
{
   return((IConfiguration)get("configuration"));
}
/*------------------------------------------------------------------------------

@name       getEventHandler - get event handler value of specified property
                                                                              */
                                                                             /**
            Get event handler  value of specified property

@return     event handler  value of specified property, or null if not found.

@param      propertyName      property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final INativeEventHandler getEventHandler(
   String propertyName)
{
   return((INativeEventHandler)get(propertyName));
}
/*------------------------------------------------------------------------------

@name       getHistory - get history property value
                                                                              */
                                                                             /**
            Get history property value

@return     history value, or null if not found.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Router getHistory()
{
   return(Js.uncheckedCast(get("history")));
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
@JsOverlay
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
      intValue = Utilities.getObjectIntValueNative(this, propertyName);
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
@JsOverlay
public final String getString(
   String propertyName)
{
   return((String)get(propertyName));
}
/*------------------------------------------------------------------------------

@name       remove - remove specified property
                                                                              */
                                                                             /**
            Remove specified property

@return     value of removed property or null if not found.

@param      propertyName      property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Object remove(
   String propertyName)
{
   JsPropertyMap<Object> map = Js.asPropertyMap(this);
   Object value = map.get(propertyName);
   map.delete(propertyName);
   return(value);
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
@JsOverlay
public final Properties set(
   String propertyName,
   Object value)
{
   if (value instanceof Number)
   {
                                       // perhaps there is some way to use the//
                                       // @DoNotAutobox annotation instead... //
      if (value instanceof Boolean)
      {
         Utilities.setObjectBooleanValueNative(
            this, propertyName, ((Boolean)value).booleanValue());
      }
      else if (value instanceof Integer)
      {
         Utilities.setObjectIntValueNative(
            this, propertyName, ((Integer)value).intValue());
      }
      else if (value instanceof Double)
      {
         Utilities.setObjectDoubleValueNative(
            this, propertyName, ((Double)value).doubleValue());
      }
      else if (value instanceof Float)
      {
         Utilities.setObjectFloatValueNative(
            this, propertyName, ((Float)value).floatValue());
      }
   }
   else
   {
      Js.asPropertyMap(this).set(propertyName, value);
   }

   return(this);
}
/*------------------------------------------------------------------------------

@name       setComponent - set component for these properties
                                                                              */
                                                                             /**
            Set component for these properties

@return     void

@param      component      component for these properties

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Properties setComponent(
   Component value)
{
   set("component", value);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setComponent - set application configuration
                                                                              */
                                                                             /**
            Set application configuration

@return     void

@param      value    application configuration

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
protected final Properties setConfiguration(
   IConfiguration value)
{
   set("configuration", value);
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
@JsOverlay
public final static Properties with(Object... args)
{
   int i = 0;
   Properties props = new Properties();

   if (args[0] instanceof Properties)
   {
      Properties          template = (Properties)args[0];
      JsForEachCallbackFn callback = new JsForEachCallbackFn()
      {
         public void onKey(String key)
         {
            props.set(key, template.get(key));
         }
      };
      Js.asPropertyMap((Properties)args[0]).forEach(callback);
      i++;
   }
   for (; i < args.length; i++)
   {
      props.set((String)args[i], args[++i]);
   }
   return(props);
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
@JsOverlay
public final static Properties without(Properties ref, String... keys)
{
   if (ref == null)
   {
      throw new IllegalArgumentException("Reference may not be null");
   }

   Properties          props    = new Properties();
   JsForEachCallbackFn callback = new JsForEachCallbackFn()
   {
      public void onKey(String key)
      {
         props.set(key, ref.get(key));
      }
   };
   Js.asPropertyMap(ref).forEach(callback);

   for (int i = 0; i < keys.length; i++)
   {
      props.remove(keys[i]);
   }

   return(props);
}
}//====================================// end Properties ---------------------//
