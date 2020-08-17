/*==============================================================================

name:       SurfaceElements.java

purpose:    Tensorflow.js SurfaceElements

history:    Thu May 14, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.tensorflow;

                                       // imports --------------------------- //
import elemental2.dom.HTMLDivElement;
import io.reactjava.client.core.react.NativeObject;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
                                       // SurfaceElements ====================//
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class SurfaceElements extends NativeObject
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
@JsProperty
public HTMLDivElement container;       // surface containing DOM element      //
@JsProperty
public HTMLDivElement drawArea;        // element for rendering visualizations//
                                       // or other content                    //
@JsProperty
public HTMLDivElement label;           // label element                       //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       SurfaceElements - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public SurfaceElements()
{
   super();
}
/*------------------------------------------------------------------------------

@name       accessors - SurfaceElements accessors
                                                                              */
                                                                             /**
            SurfaceElements accessors.

@history    Thu May 14, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsProperty
public native HTMLDivElement getContainer();
@JsProperty
public native HTMLDivElement getDrawArea();
@JsProperty
public native HTMLDivElement getLabel();

}//====================================// end SurfaceElements ----------------//
