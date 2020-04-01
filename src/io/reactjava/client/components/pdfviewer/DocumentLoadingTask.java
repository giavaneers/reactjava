package io.reactjava.client.components.pdfviewer;

import elemental2.promise.Promise;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/*==============================================================================

name:       DocumentLoadingTask - native pdfjs api

purpose:    Native pdfjs api

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
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
