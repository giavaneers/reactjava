/*==============================================================================

name:       NativeObject.java

purpose:    ReactJava Component NativeObject.

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
import java.util.HashMap;
import java.util.Map;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Any;
import jsinterop.base.Js;
import jsinterop.base.JsForEachCallbackFn;
import jsinterop.base.JsPropertyMap;
                                       // NativeObject =======================//
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class NativeObject extends JsObject
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

@name       NativeObject - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public NativeObject()
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
   if (value instanceof String)
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
public final NativeObject set(
   String propertyName,
   Object value)
{
   if (value != null)
   {
      JsPropertyMap map = Js.asPropertyMap(this);
      if (value instanceof Boolean)
      {
         map.set(propertyName, ((Boolean)value).booleanValue());
      }
      else if (value instanceof Number)
      {
                                          // perhaps there is some way to use the//
                                          // @DoNotAutobox annotation instead... //
         if (value instanceof Integer)
         {
            map.set(propertyName, ((Integer)value).intValue());
         }
         else if (value instanceof Double)
         {
            map.set(propertyName, ((Double)value).doubleValue());
         }
         else if (value instanceof Float)
         {
            map.set(propertyName, ((Float)value).floatValue());
         }
      }
      else
      {
         map.set(propertyName, value);
      }
   }

   return(this);
}
/*------------------------------------------------------------------------------

@name       toMap - create Map from NativeObject instance
                                                                              */
                                                                             /**
            Create Map from NativeObject instance

@return     Map from NativeObject instance

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Map<String,Object> toMap()
{
   Map<String,Object>  map      = new HashMap<>();
   JsForEachCallbackFn callback = new JsForEachCallbackFn()
   {
      public void onKey(String key)
      {
         map.put(key, get(key));
      }
   };
   Js.asPropertyMap(this).forEach(callback);
   return(map);
}
/*------------------------------------------------------------------------------

@name       with - factory method
                                                                              */
                                                                             /**
            Factory method creating a NativeObject instance containing the
            specified properties specified as a sequence iof tuples, where
            each tuple consists of a String property name followed by a
            value object reference.

            For example:

               NativeObject myProperties =
                  NativeObject.with("text", "MyText", "callback", MyCallbackFcn);

             or to create a copy of an instance adding new/overriding properties:

               NativeObject template =
                  NativeObject.with("name", "MyName", "callback", OrigCallbackFcn);

               NativeObject myProperties =
                  NativeObject.with(
                     template, "text", "MyText", "callback", MyCallbackFcn);

             or to create a Native object with properties of a Map adding
             new/overriding properties::

               Map template =
                  new HashMap()
                  {{
                     put("name",    "MyName");
                     put("callback", OrigCallbackFcn);
                  }};

               NativeObject myProperties =
                  NativeObject.with(
                     template, "text", "MyText", "callback", MyCallbackFcn);


@return     An instance of NativeObject if successful.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public static NativeObject with(Object... args)
{
   int i = 0;
   NativeObject o = new NativeObject();

   if (args[0] instanceof Map)
   {
                                       // must be first test since            //
                                       // Map instanceof NativeObject resolves//
                                       // to true                             //
      Map template = (Map)args[0];
      for (Object key : template.keySet())
      {
         o.set(key.toString(), template.get(key));
      }
      i = 1;
   }
   else if (args[0] instanceof NativeObject)
   {
      NativeObject        template = (NativeObject)args[0];
      JsForEachCallbackFn callback = new JsForEachCallbackFn()
      {
         public void onKey(String key)
         {
            o.set(key, template.get(key));
         }
      };
      Js.asPropertyMap((NativeObject)args[0]).forEach(callback);
      i++;
   }
   for (; i < args.length; i++)
   {
      o.set((String)args[i], args[++i]);
   }
   return(o);
}
/*------------------------------------------------------------------------------

@name       without - factory method
                                                                              */
                                                                             /**
            Factory method creating a NativeObject instance containing the
            specified properties template without the properties referenced
            by the specified list of keys.

            For example:

               NativeObject template =
                  NativeObject.with("text", "MyText", "callback", MyCallbackFcn);

               NativeObject templateWithout =
                  NativeObject.without(template, "callback");

             yields the same as:

               NativeObject.with("text", "MyText");

@return     An instance of NativeObject if successful.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public static NativeObject without(NativeObject ref, String... keys)
{
   if (ref == null)
   {
      throw new IllegalArgumentException("Reference may not be null");
   }

   NativeObject        o        = new NativeObject();
   JsForEachCallbackFn callback = new JsForEachCallbackFn()
   {
      public void onKey(String key)
      {
         o.set(key, ref.get(key));
      }
   };
   Js.asPropertyMap(ref).forEach(callback);

   for (int i = 0; i < keys.length; i++)
   {
      o.remove(keys[i]);
   }

   return(o);
}
}//====================================// end NativeObject -------------------//
