package io.reactjava.client.components.pdfviewer;

import io.reactjava.client.core.react.NativeObject;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/*==============================================================================

name:       PDFHistory - native pdfjs history api

purpose:    Native pdfjs history api

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@JsType(isNative  = true, namespace = "pdfjsViewer")
public class PDFHistory
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // link service                        //
@JsProperty public PDFLinkService linkService;
                                       // protected instance variables -------//
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       PDFHistory - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private PDFHistory()
{
}
/*------------------------------------------------------------------------------

@name       PDFHistory - constructor
                                                                              */
                                                                             /**
            Constructor

@param      props    props

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private PDFHistory(
   NativeObject args)
{
}
/*------------------------------------------------------------------------------

@name       navigateTo - navigate to specified explicit destination
                                                                              */
                                                                             /**
            Navigate to specified explicit destination.

@params     destination    destination, such as
                           [{"num":157,"gen":0},{"name":"XYZ"},72,720,0]

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native void navigateTo(
   Destination destination);

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
public static PDFHistory newInstance(
   PDFLinkService linkService)
{
   return(new PDFHistory(NativeObject.with("linkService", linkService)));
}
/*------------------------------------------------------------------------------

@name       newInstance - new instance for specified link service and event bus
                                                                              */
                                                                             /**
            New instance for specified link service and event bus.

@params     linkService    link service
@params     eventBus       eventBus

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public static PDFHistory newInstance(
   PDFLinkService linkService,
   EventBus       eventBus)
{
   PDFHistory history =
      new PDFHistory(NativeObject.with(
         "linkService", linkService, "eventBus", eventBus));

   return(history);
}
/*------------------------------------------------------------------------------

@name       setDocument - assign document
                                                                              */
                                                                             /**
            Assign document.

@params     document    target document

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native void setDocument(
   PDFDocumentProxy document,
   String           url);

/*------------------------------------------------------------------------------

@name       setHistory - assign history
                                                                              */
                                                                             /**
            Assign history.

@params     history     target history

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native void setHistory(
   PDFHistory history);

/*------------------------------------------------------------------------------

@name       setViewer - assign viewer
                                                                              */
                                                                             /**
            Assign viewer.

@params     viewer      target viewer

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native void setViewer(
   PDFViewerNative viewer);

}//====================================// end PDFHistory -----------------//
