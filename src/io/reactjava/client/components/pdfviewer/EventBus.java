/*==============================================================================

name:       EventBus.java

purpose:    Native pdfjs EventBus api

history:    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.pdfviewer;
                                       // imports --------------------------- //
import io.reactjava.client.core.react.INativeFunction;
import jsinterop.annotations.JsType;
                                       // EventBus ===========================//
@JsType(isNative  = true, namespace = "pdfjsViewer")
public class EventBus
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

@name       EventBus - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public EventBus()
{
}
/*------------------------------------------------------------------------------

@name       off - unregister event bus handler
                                                                              */
                                                                             /**
            Unregister event bus handler.

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native void off(
   String          eventName,
   INativeFunction callback);

/*------------------------------------------------------------------------------

@name       on - register event bus handler
                                                                              */
                                                                             /**
            Register event bus handler.

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native void on(
   String          eventName,
   INativeFunction callback);

}//====================================// end EventBus -----------------------//
