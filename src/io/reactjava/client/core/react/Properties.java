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
            Default constructor, package-private

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Properties()
{
   super();
}
/*------------------------------------------------------------------------------

@name       equivalent - standard equals() method, renamed
                                                                              */
                                                                             /**
            Standard equals() method, renamed since a 'JsOverlay method cannot
            be nor override a JsProperty or a JsMethod'.

@return     value of specified property, or null if not found.

@param      propertyName      property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final boolean equivalent(
   Object obj)
{
   Properties candidate = null;
   boolean    bEquals   = obj instanceof Properties;
   if (bEquals)
   {
      candidate = (Properties)obj;
      bEquals   = size() == candidate.size();
   }
   if (bEquals)
   {
      JsPropertyMap thisMap      = Js.asPropertyMap(this);
      JsPropertyMap candidateMap = Js.asPropertyMap(candidate);
      for (String key : keys(this))
      {
         if (!thisMap.get(key).equals(candidateMap.get(key)))
         {
            bEquals = false;
            break;
         }
      }
   }
   return(bEquals);
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
@JsOverlay
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
public final ReactElement getChildren()
{
   return(Js.uncheckedCast(get("children")));
}
/*------------------------------------------------------------------------------

@name       getComponent - get component of these properties
                                                                              */
                                                                             /**
            Get component of these properties

@return     value of component, or null if not found.

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
   IConfiguration configuration = (IConfiguration)get("configuration");
   if (configuration == null)
   {
      configuration = Configuration.sharedInstance();
      setConfiguration(configuration);
   }
   return(configuration);
}
/*------------------------------------------------------------------------------

@name       getDouble - get double value of specified property
                                                                              */
                                                                             /**
            Get double value of specified property

@return     double value of specified property, or null if not found.

@param      propertyName      property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes      see set() with invocation of Utilties.setObjectIntValueNative()
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final double getDouble(
   String propertyName)
{
   double doubleValue;

   Object value = get(propertyName);
   if (value == null)
   {
      doubleValue = 0;
   }
   else if (value instanceof String)
   {
      doubleValue = Double.parseDouble((String)value);
   }
   else
   {
      doubleValue = Utilities.getObjectDoubleValueNative(this, propertyName);
   }
   return(doubleValue);
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
   if (value == null)
   {
      intValue = 0;
   }
   else if (value instanceof String)
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

@name       isEmpty - test whether has no entrries
                                                                              */
                                                                             /**
            Test whether has no entrries

@return     true iff has no entries

@history    Mon Aug 10, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final boolean isEmpty()
{
   return(JsObject.keys(this).length == 0);
}
/*------------------------------------------------------------------------------

@name       newInstance - factory method
                                                                              */
                                                                             /**
            Factory method

@return     new instance

@history    Mon Aug 10, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public static final Properties newInstance()
{
   return(new Properties());
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
            Set value of specified property. Package-private.

@return     void

@param      propertyName      property name
@param      value             value of specified property

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
final Properties set(
   String propertyName,
   Object value)
{
   if (value != null)
   {
                                       // this structure to allow assignment  //
                                       // of primitive values...              //
      if (value instanceof Boolean)
      {
         boolean val = ((Boolean)value).booleanValue();
         Js.asPropertyMap(this).set(propertyName, val);
      }
      else if (value instanceof Integer)
      {
         int val = ((Integer)value).intValue();
         Js.asPropertyMap(this).set(propertyName, val);
      }
      else if (value instanceof Double)
      {
         double val = ((Double)value).doubleValue();
         Js.asPropertyMap(this).set(propertyName, val);
      }
      else if (value instanceof Float)
      {
         float val = ((Float)value).floatValue();
         Js.asPropertyMap(this).set(propertyName, val);
      }
      else
      {
         Js.asPropertyMap(this).set(propertyName, value);
      }
                                       // checking...                         //
      if (get(propertyName) == null)
      {
         throw new IllegalStateException("value not written");
      }
   }

   return(this);
}
/*------------------------------------------------------------------------------

@name       setComponent - set component for these properties
                                                                              */
                                                                             /**
            Set component for these properties. . Package-private.

@return     void

@param      value      component for these properties

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
final Properties setComponent(
   Component value)
{
   set("component", value);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setConfiguration - set application configuration
                                                                              */
                                                                             /**
            Set application configuration. Package-private.

@return     void

@param      value    application configuration

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
final Properties setConfiguration(
   IConfiguration value)
{
   set("configuration", value);
   return(this);
}
/*------------------------------------------------------------------------------

@name       size - get number of entries
                                                                              */
                                                                             /**
            Get number of entries.

@return     number of entries

@history    Mon Aug 10, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final int size()
{
   return(JsObject.keys(this).length);
}
/*------------------------------------------------------------------------------

@name       toNativeObject - create a representative NativeObject
                                                                              */
                                                                             /**
            Create a representative NativeObject.

@return     a representative NativeObject

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final NativeObject toNativeObject()
{
   NativeObject        nativeObject = new NativeObject();
   JsForEachCallbackFn callback     = new JsForEachCallbackFn()
   {
      public void onKey(String key)
      {
         nativeObject.set(key, get(key));
      }
   };
   Js.asPropertyMap(this).forEach(callback);

   return(nativeObject);
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
public static final Properties with(Object... args)
{
   int i = 0;
   Properties props = new Properties();

   if (args != null && args.length > 0)
   {
      if (args[0] != null && args[0] instanceof Properties)
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
public static final Properties without(Properties ref, String... keys)
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
