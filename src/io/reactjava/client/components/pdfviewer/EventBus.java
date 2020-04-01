package io.reactjava.client.components.pdfviewer;

import io.reactjava.client.core.react.INativeFunction;
import jsinterop.annotations.JsType;

/*==============================================================================

name:       EventBus - native pdfjs EventBus api

purpose:    Native pdfjs EventBus api

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
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
