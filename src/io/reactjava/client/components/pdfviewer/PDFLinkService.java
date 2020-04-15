/*==============================================================================

name:       PDFLinkService.java

purpose:    Native pdfjs link service api

history:    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.pdfviewer;
                                       // imports --------------------------- //
import io.reactjava.client.core.react.NativeObject;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
                                       // PDFLinkService =====================//
@JsType(isNative  = true, namespace = "pdfjsViewer")
public class PDFLinkService
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // viewer                              //
@JsProperty public PDFViewerNative pdfViewer;
                                       // protected instance variables -------//
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       PDFLinkService - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private PDFLinkService()
{
}
/*------------------------------------------------------------------------------

@name       PDFLinkService - constructor
                                                                              */
                                                                             /**
            Constructor

@param      props    props

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private PDFLinkService(
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
public static PDFLinkService newInstance(
   int externalLinkTarget)
{
   PDFLinkService linkService =
      new PDFLinkService(
         NativeObject.with("externalLinkTarget", externalLinkTarget));

   return(linkService);
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

}//====================================// end PDFLinkService -----------------//
