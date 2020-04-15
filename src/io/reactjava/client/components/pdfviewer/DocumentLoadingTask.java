/*==============================================================================

name:       DocumentLoadingTask.java

purpose:    Native pdfjs api

history:    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.pdfviewer;
                                       // imports --------------------------- //
import elemental2.promise.Promise;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
                                       // DocumentLoadingTask ================//
@JsType(isNative  = true, namespace = "pdfjsLib")
public class DocumentLoadingTask
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // public instance variables --------- //
                                       // promise                             //
@JsProperty
public Promise<PDFDocumentProxy> promise;
                                       // protected instance variables -------//
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       PDFDocumentLoadingTask - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private DocumentLoadingTask()
{
}
}//====================================// end DocumentLoadingTask ------------//
