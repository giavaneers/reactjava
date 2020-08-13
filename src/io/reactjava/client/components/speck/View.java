/*==============================================================================

name:       View

purpose:    Native Speck View

history:    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.speck;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
                                       // imports --------------------------- //
                                       // (none)                              //
                                       // View ===============================//
@JsType(isNative  = true, namespace = "ReactJava.ReactJavaComponents.Speck")
public class View
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
@JsProperty
public int   resolution;               // resolution                          //
@JsProperty
public int   aoRes;                    // aoRes                               //
@JsProperty
public float zoom;                     // zoom                                //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       View - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private View()
{
}
/*------------------------------------------------------------------------------

@name       center - center image
                                                                              */
                                                                             /**
            Center image

@param      view     view instance
@param      s        system instance

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void center(View view, System s);

/*------------------------------------------------------------------------------

@name       newInstance - factory method
                                                                              */
                                                                             /**
            Factory method

@return     system instance

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod(namespace = "ReactJava.ReactJavaComponents.Speck", name = "View.new")
public static native View newInstance();

/*------------------------------------------------------------------------------

@name       resolve - resolve
                                                                              */
                                                                             /**
            Resolve

@param      view     view instance

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void resolve(View view);

/*------------------------------------------------------------------------------

@name       rotate - rotate image
                                                                              */
                                                                             /**
            Rotate image

@param      view     view instance
@param      dx       delta x
@param      dy       delta y

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void rotate(View view, double dx, double dy);

/*------------------------------------------------------------------------------

@name       translate - translate image
                                                                              */
                                                                             /**
            Translate image

@param      view     view instance
@param      dx       delta x
@param      dy       delta y

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void translate(View view, double dx, double dy);

}//====================================// end View ---------------------------//
