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
@JsType(isNative  = true, namespace = "ReactJava.pdf")
public class GlobalWorkerOptions
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
@JsProperty
public PDFWorker workerPort;           // worker port                         //
@JsProperty
public String    workerSrc;            // pdf.worker.js filepath              //
                                       // protected instance variables -------//
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       GlobalWorkerOptions - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private GlobalWorkerOptions()
{
}
}//====================================// end GlobalWorkerOptions ------------//
