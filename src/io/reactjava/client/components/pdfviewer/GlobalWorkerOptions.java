package io.reactjava.client.components.pdfviewer;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/*==============================================================================

name:       GlobalWorkerOptions - native pdfjs GlobalWorkerOptions api

purpose:    Native pdfjs GlobalWorkerOptions api

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
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
