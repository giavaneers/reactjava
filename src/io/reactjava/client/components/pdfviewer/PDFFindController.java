package io.reactjava.client.components.pdfviewer;

import io.reactjava.client.core.react.NativeObject;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

/*==============================================================================

name:       PDFFindController - native pdfjs search service api

purpose:    Native pdfjs search service api

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@JsType(isNative  = true, namespace = "pdfjsViewer")
public class PDFFindController
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       PDFFindController - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private PDFFindController()
{
   super();
}
/*------------------------------------------------------------------------------

@name       PDFFindController - constructor
                                                                              */
                                                                             /**
            Constructor

@param      props    props

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private PDFFindController(
   NativeObject args)
{
}
/*------------------------------------------------------------------------------

@name       newInstance - new instance for specified link service
                                                                              */
                                                                             /**
            New instance for specified link service.

@params     linkService    link service

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public static PDFFindController newInstance(
   PDFLinkService linkService)
{
   return(new PDFFindController(NativeObject.with("linkService", linkService)));
}
}//====================================// end PDFFindController --------------//
