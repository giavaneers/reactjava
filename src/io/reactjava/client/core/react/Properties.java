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
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsForEachCallbackFn;
                                       // Properties =========================//
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class Properties extends NativeObject
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
   IConfiguration configuration = (IConfiguration)get("configuration");
   if (configuration == null)
   {
      configuration = Configuration.sharedInstance();
      setConfiguration(configuration);
   }
   return(configuration);
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
public static Properties with(Object... args)
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
public static Properties without(Properties ref, String... keys)
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
