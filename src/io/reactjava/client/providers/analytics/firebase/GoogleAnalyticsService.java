/*==============================================================================

name:       GoogleAnalyticsService.java

purpose:    Google Analytics Service for Firebase.

history:    Thu Dec 05, 2019 10:30:00 (Giavaneers - LBM) created

notes:      '-generateJsInteropExports' must be included in Dev Mode parameters
            setting of GWT debug configuration for java methods to be
            exported to javascript

                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.providers.analytics.firebase;

                                       // imports --------------------------- //
import io.reactjava.client.providers.analytics.IAnalyticsService;
import io.reactjava.client.providers.auth.firebase.FirebaseCore;
import io.reactjava.client.core.react.NativeObject;
import io.reactjava.client.core.react.Properties;
import io.reactjava.client.moduleapis.ReactGA;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
                                       // GoogleAnalyticsService =============//
public class GoogleAnalyticsService implements IAnalyticsService
{
                                       // class constants --------------------//
                                       // (none)
                                       // class variables ------------------- //
                                       // (none)
                                       // public instance variables --------- //
                                       // (none)
                                       // protected instance variables -------//
                                       // (none)
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       GoogleAnalyticsService - constructor
                                                                              */
                                                                             /**
            Constructor

@param      props    props

@history    Tue Dec 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public GoogleAnalyticsService(
   Properties props)
{
}
/*------------------------------------------------------------------------------

@name       exception - report an exception
                                                                              */
                                                                             /**
            Report an exception.

@param      description    description
@param      fatal          whether is a fatal error

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void exception(
   String  description,
   boolean fatal)
{
   ReactGA.exception(description, fatal);
}
/*------------------------------------------------------------------------------

@name       ga - original ga function
                                                                              */
                                                                             /**
            The original ga function can be accessed via this method.

@param      args     args

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void ga(
   Object... args)
{
   ReactGA.ga(args);
}
/*------------------------------------------------------------------------------

@name       getAnalytics - get analytics
                                                                              */
                                                                             /**
            Get analytics.

@return     analytics

@history    Tue Dec 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Analytics getAnalytics()
{
   if (true)
   {
      throw new UnsupportedOperationException();
   }
   return((Analytics)Js.uncheckedCast(FirebaseCore.getAnalytics()));
}
/*------------------------------------------------------------------------------

@name       logEvent  - send analytics event with any specified event params
                                                                              */
                                                                             /**
            Send analytics event with any specified event params.

@param      eventName      event name

@history    Tue Dec 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void logEvent(
   EventNames eventName)
{
   if (true)
   {
      throw new UnsupportedOperationException();
   }
   getAnalytics().logEvent(eventName.value());
}
/*------------------------------------------------------------------------------

@name       logEvent  - send analytics event with any specified event params
                                                                              */
                                                                             /**
            Send analytics event with any specified event params.

@param      category             a top level category (required)
@param      action               description of the behaviour (required)

@history    Tue Dec 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void logEvent(
   String  category,
   String  action)
{
   logEvent(category, action, null, null, null, null);
}
/*------------------------------------------------------------------------------

@name       logEvent  - send analytics event with any specified event params
                                                                              */
                                                                             /**
            Send analytics event with any specified event params.

@param      category             a top level category (required)
@param      action               description of the behaviour (required)
@param      label                more precise labelling of the action (optional)
@param      value                numerical value (optional)
@param      bNonInteraction      if an event is not triggered by a user
                                 interaction, but instead by the code
                                 (e.g. on page load), it should be flagged as a
                                 nonInteraction event to avoid skewing bounce
                                 rate data  (optional)
@param      transport            transport mechanism with which hits will be
                                 sent, one of 'beacon', 'xhr', or 'image'
                                 (optional)

@history    Tue Dec 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void logEvent(
   String  category,
   String  action,
   String  label,
   Integer value,
   Boolean bNonInteraction,
   String  transport)
{
   NativeObject args = NativeObject.with("category", category, "action",action);
   if (label != null)
   {
      args.set("label", label);
   }
   if (value != null)
   {
      args.set("value", value);
   }
   if (bNonInteraction != null)
   {
      args.set("nonInteraction", bNonInteraction);
   }
   if (transport != null)
   {
      args.set("transport", transport);
   }

   ReactGA.event(args);
}
/*==============================================================================

name:       Analytics - core compatible Firebase Analytics service

purpose:    JsInterop Firebase Analytics service

history:    Tue Dec 31, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public static class Analytics extends NativeObject
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       logEvent  - send analytics event with any specified event params
                                                                              */
                                                                             /**
            Send analytics event with any specified event params.

@param      eventNameValue      event name

@history    Tue Dec 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native void logEvent(
   String eventNameValue);

}//====================================// Analytics ==========================//
}//====================================// end GoogleAnalyticsService =========//
