/*==============================================================================

name:       Render.java

purpose:    Tensorflow.js api-vis charting

history:    Thu May 14, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.tensorflow;

                                       // imports --------------------------- //
import elemental2.dom.HTMLElement;
import elemental2.promise.Promise;
import io.reactjava.client.core.react.NativeObject;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;
                                       // Render =============================//
@JsType(isNative = true, namespace = "tfvis.render")
public class Render
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

@name       Render - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu May 14, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private Render()
{
}
/*------------------------------------------------------------------------------

@name       barchart - render a barchart
                                                                              */
                                                                             /**
            Render a barchart.

@return     promise

@param      container      container element
@param      data           data array
@param      opts           options

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native Promise<Void> barchart(
   HTMLElement  container,
   Object[]     data,
   NativeObject opts);

/*------------------------------------------------------------------------------

@name       confusionMatrix - render a confusion matrix
                                                                              */
                                                                             /**
            Render a confusion matrix.

@return     promise

@param      container      container element
@param      data           values and tickLabels
@param      tickLabels     tick labels

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
@JsMethod(namespace = "ReactJava.tfvis.render")
public static native Promise<Void> confusionMatrix(
   NativeObject container,
   NativeObject data,
   String[]     tickLabels);

/*------------------------------------------------------------------------------

@name       heatmap - render a heatmap
                                                                              */
                                                                             /**
            Render a heatmap.

@return     promise

@param      container      container element
@param      data           values and x and y tickLabels
@param      opts           options

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native Promise<Void> heatmap(
   HTMLElement  container,
   NativeObject data,
   NativeObject opts);

/*------------------------------------------------------------------------------

@name       heatmap - render a histogram
                                                                              */
                                                                             /**
            Render a histogram.

@return     promise

@param      container      container element
@param      data           data array
@param      opts           options

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native Promise<Void> histogram(
   HTMLElement  container,
   Object[]     data,
   NativeObject opts);

/*------------------------------------------------------------------------------

@name       linechart - render a linechart
                                                                              */
                                                                             /**
            Render a linechart.

@return     promise

@param      container      container element
@param      data           data values and series
@param      opts           options

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native Promise<Void> linechart(
   HTMLElement  container,
   NativeObject data,
   NativeObject opts);

/*------------------------------------------------------------------------------

@name       scatterplot - render a scatterplot
                                                                              */
                                                                             /**
            Render a scatterplot.

@return     promise

@param      container      container element
@param      data           data values and series
@param      opts           options

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native Promise<Void> scatterplot(
   HTMLElement  container,
   NativeObject data,
   NativeObject opts);

/*------------------------------------------------------------------------------

@name       scatterplot - render a scatterplot
                                                                              */
                                                                             /**
            Render a scatterplot.

@return     promise

@param      container      container element
@param      data           data headers and values
@param      opts           options

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native void table(
   HTMLElement  container,
   NativeObject data,
   NativeObject opts);

}//====================================// end Render -------------------------//
