/*==============================================================================

name:       GlobalWorkerOptions.java

purpose:    Native pdfjs GlobalWorkerOptions api

history:    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.pdfviewer;
                                       // imports --------------------------- //
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
                                       // GlobalWorkerOptions ================//
@JsType(isNative  = true, namespace = "pdfjsLib")
public class PageViewport
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
@JsProperty
public int      width;                 // width                               //
@JsProperty
public int      height;                // height                              //

                                       // transform converting pdf coordinate //
                                       // system to the normal canvas like    //
                                       // coordinates taking in account scale //
                                       // and rotation  and offset            //
                                       // [0] rotation A                      //
                                       // [1] rotation B                      //
                                       // [2] rotation C                      //
                                       // [3] rotation D                      //
                                       // [4] offset X                        //
                                       // [5] offset Y                        //
@JsProperty
public double[] transform;
                                       // protected instance variables -------//
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       PageViewport - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private PageViewport()
{
}
/*------------------------------------------------------------------------------

@name       convertToViewportPoint - convert to point in the viewport coords
                                                                              */
                                                                             /**
            Convert PDF point to the viewport coordinates. For examples, useful
            for converting PDF location into canvas pixel coordinates.

@return     point in the viewport coords

@param      x     x coordinate
@param      y     y coordinate

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected native double[] convertToViewportPoint(
   double x,
   double y);

}//====================================// end PageViewport -------------------//
