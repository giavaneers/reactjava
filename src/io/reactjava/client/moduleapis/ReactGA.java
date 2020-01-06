/*==============================================================================

name:       ReactGA - native google analytics api

purpose:    Native google analytics api for node module 'react-ga' included
            for an App with something like the following override:

            protected List<String> getImportedNodeModules()
            {
               return(Arrays.asList("react-ga"));
            }

history:    Wed Jun 10, 2019 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.moduleapis;
                                       // imports --------------------------- //
import io.reactjava.client.core.react.NativeObject;
import jsinterop.annotations.JsType;
                                       // ReactGA ============================//
@JsType(isNative = true, namespace = "ReactJava", name = "reactGa")
public class ReactGA
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

@name       event - report an event
                                                                              */
                                                                             /**
            Report an event.

            Examples:

            ReactGA.event(
               NativeObject.with(
                  "category", "User",
                  "action",   "Created an Account"));

            ReactGA.event(
               NativeObject.with(
                  "category", "Social",
                  "action",   "Rated an App",
                  "value",    3));

            ReactGA.event(
               NativeObject.with(
                  "category", "Editing",
                  "action",   "Deleted Component",
                  "label",    "Game Widget"));

            ReactGA.event(
               NativeObject.with(
                  "category",       "Promotion",
                  "action",         "Displayed Promotional Widget",
                  "label",          "Homepage Thing",
                  "nonInteraction", true,
                  ));


@param      args     event args packaged as a NativeObject

              category        String.  Required. A top level category.

              action          String.  Required. A description of the behaviour.

              label           String.  Optional. More precise labelling of the
                              related action.

              value:          int.     Optional. A numerical value.

              nonInteraction: boolean. Optional. If an event is not triggered by
                              a user interaction, but instead by the code
                              (e.g. on page load), it should be flagged as a
                              nonInteraction event to avoid skewing bounce rate
                              data.

              transport:      String.  Optional. Transport mechanism with which
                              hits will be sent, one of 'beacon', 'xhr', or
                              'image'.

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native static void event(
   NativeObject args);

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
public native static void exception(
   String  description,
   boolean fatal);

/*------------------------------------------------------------------------------

@name       initialize - initialize google analytics api
                                                                              */
                                                                             /**
            Initialize google analytics api.

@param      googleAnalyticsId    id

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native static void initialize(
   String googleAnalyticsId);

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
public native static void ga(
   Object... args);

/*------------------------------------------------------------------------------

@name       modalview - report a modalview
                                                                              */
                                                                             /**
            Report a modalview.

@param      rawPath    path of page

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native static void modalview(
   String rawPath);

/*------------------------------------------------------------------------------

@name       pageview - report a pageview
                                                                              */
                                                                             /**
            Report a pageview.

@param      rawPath    path of page

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native static void pageview(
   String rawPath);

}//====================================// end ReactGA ------------------------//
