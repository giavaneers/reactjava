package io.reactjava.client.components.pdfviewer;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/*==============================================================================

name:       PDFPageView - native pdfjs PDFPageView

purpose:    Native pdfjs PDFPageView

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@JsType(isNative  = true, namespace = "pdfjsLib")
public class PDFPageView
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
@JsProperty
public PageViewport viewport;          // viewport                            //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       PDFPageView - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private PDFPageView()
{
}
}//====================================// end PDFPageView --------------------//
