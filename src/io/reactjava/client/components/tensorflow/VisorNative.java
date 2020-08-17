/*==============================================================================

name:       VisorNative.java

purpose:    Tensorflow.js History

history:    Thu May 14, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.tensorflow;

                                       // imports --------------------------- //
import io.reactjava.client.core.react.NativeObject;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;
                                       // History ============================//
@JsType(isNative = true, namespace = "ReactJava.tfvis", name = "Visor")
public class VisorNative extends NativeObject
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       VisorNative - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu May 14, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private VisorNative()
{
}
/*------------------------------------------------------------------------------

@name       bindKeys - bind the tilde key to toggle the visor.
                                                                              */
                                                                             /**
            Bind the tilde key to toggle the visor. Called by default when the
            visor is initially created.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native void bindKeys();

/*------------------------------------------------------------------------------

@name       close - close the visor
                                                                              */
                                                                             /**
            Close the visor.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native void close();

/*------------------------------------------------------------------------------

@name       isFullScreen - test whether visor is open
                                                                              */
                                                                             /**
            Test whether visor is open.

@return     true iff the visor is open

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native boolean isFullScreen();

/*------------------------------------------------------------------------------

@name       isOpen - test whether visor is in 'fullscreen' mode
                                                                              */
                                                                             /**
            Test whether visor is in 'fullscreen' mode.

@return     true iff the visor is in 'fullscreen' mode

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native boolean isOpen();

/*------------------------------------------------------------------------------

@name       open - open the visor
                                                                              */
                                                                             /**
            Open the visor.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native void open();

/*------------------------------------------------------------------------------

@name       setActiveTab - c
                                                                              */
                                                                             /**
            Set the active tab for the visor.

@param      tabName     tab name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native void setActiveTab(
   String tabName);

/*------------------------------------------------------------------------------

@name       surface - create a surface on the visor
                                                                              */
                                                                             /**
            Most methods in tfjs-vis that take a surface also take a SurfaceInfo
            so you rarely need to call this method unless you want to make a
            custom plot.

@return     New surface information: container, label and drawArea.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final SurfaceElements surface(
   String name,
   String tab)
{
   return(surface(NativeObject.with("name", name, "tab", tab)));
}
/*------------------------------------------------------------------------------

@name       surface - create a surface on the visor
                                                                              */
                                                                             /**
            Most methods in tfjs-vis that take a surface also take a SurfaceInfo
            so you rarely need to call this method unless you want to make a
            custom plot.

@return     New surface information: container, label and drawArea.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native SurfaceElements surface(
   NativeObject options);

/*------------------------------------------------------------------------------

@name       unbindKeys - unbind the tilde key to toggle the visor.
                                                                              */
                                                                             /**
            Unbind the tilde key to toggle the visor.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native void unbindKeys();

/*------------------------------------------------------------------------------

@name       visor - factory method
                                                                              */
                                                                             /**
            Factory method.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
@JsMethod(namespace = "ReactJava.tfvis")
public static native VisorNative visor();

/*------------------------------------------------------------------------------

@name       toggle - toggle the visor between open and close
                                                                              */
                                                                             /**
            Toggle the visor between open and close.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native void toggle();

/*------------------------------------------------------------------------------

@name       toggleFullScreen - toggle the visor between full screen and not
                                                                              */
                                                                             /**
            Toggle the visor between full screen and not.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native void toggleFullScreen();

}//====================================// end Visor --------------------------//
