package io.reactjava.client.components.pdfviewer;

import elemental2.dom.Element;
import io.reactjava.client.core.react.NativeObject;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/*==============================================================================

name:       PDFViewerNative - native pdfjs viewer api

purpose:    Native pdfjs viewer api

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@JsType(isNative = true, namespace = "pdfjsViewer", name="PDFViewer")
public class PDFViewerNative
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // container                           //
@JsProperty public Element          container;

                                       // document                            //
@JsProperty public PDFDocumentProxy pdfDocument;
                                       // scale default value either a string //
                                       // double value or one of              //
                                       // "page-actual", "page-width",        //
                                       // "page-height", "page-fit", or "auto"//
@JsProperty public String           currentScaleValue;

                                       // scale default value either a string //
                                       // double value or one of              //
                                       // "page-actual", "page-width",        //
                                       // "page-height", "page-fit", or "auto"//
@JsProperty public String           DEFAULT_SCALE_VALUE;

                                       // link service                        //
@JsProperty public PDFLinkService   linkService;

                                       // protected instance variables -------//
                                       // viewer container parent element     //
protected  Element                  viewerContainerParentElement;

                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       PDFViewer - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public PDFViewerNative()
{
   super();
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
public PDFViewerNative(
   NativeObject args)
{
}
/*------------------------------------------------------------------------------

@name       forceRendering - render specified page views
                                                                              */
                                                                             /**
            Render specified page views.

@returns    true iff rendered

@param      viewsSpec      if null, render the current visible pages; otherwise,
                           a NativeObject with three fields:

                           "first" -> the first page view
                           "last"  -> the last page view
                           "views" -> the array of visible pages

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native boolean forceRendering(
   NativeObject viewsSpec);

/*------------------------------------------------------------------------------

@name       getCanvasOffset - get offset in canvas coordinates
                                                                              */
                                                                             /**
            Get offset in canvas coordinates.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Point getCanvasOffset(
   Point point,
   int   pageIndex)
{
   PDFPageView  pageView  = getPageView(pageIndex);
   PageViewport viewport  = pageView.viewport;
   double[]     xformed   = viewport.convertToViewportPoint(point.x, point.y);

   return(new Point(xformed));
};
/*------------------------------------------------------------------------------

@name       getDocument - get document
                                                                              */
                                                                             /**
            Get document.

@returns    document

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final PDFDocumentProxy getDocument()
{
   return(pdfDocument);
}
/*------------------------------------------------------------------------------

@name       getLinkService - get link service
                                                                              */
                                                                             /**
            Get link service.

@returns    link service

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final PDFLinkService getLinkService()
{
   return(linkService);
}
/*------------------------------------------------------------------------------

@name       getPageView - get page view for specified page index
                                                                              */
                                                                             /**
            Get page view for specified page index.

@returns    page view for specified page index

@param      pageIndex      page index

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native PDFPageView getPageView(
   int pageIndex);

/*------------------------------------------------------------------------------

@name       getViewerContainerParentElement - get viewer container element
                                                                              */
                                                                             /**
            Get viewer container parent element.

@returns    viewer container element

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Element getViewerContainerParentElement()
{
   return(viewerContainerParentElement);
}
/*------------------------------------------------------------------------------

@name       _resetView - reset viewer
                                                                              */
                                                                             /**
            Reset viewer.

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native void _resetView();

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
   PDFDocumentProxy document);

/*------------------------------------------------------------------------------

@name       setScale - assign scale
                                                                              */
                                                                             /**
            Assign scale.

@params     scale          new scale value, either a string double value or one
                           of "page-actual", "page-width", "page-height",
                           "page-fit", or "auto"

@params     bNoScroll      whether shoild scroll

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final void setScale(
   String  scale,
   boolean bNoScroll)
{
   currentScaleValue = scale;
}
/*------------------------------------------------------------------------------

@name       setViewerContainerElement - set viewer container element
                                                                              */
                                                                             /**
            St viewer container element.

@oaram      viewerContainerElement     viewer container element

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final void setViewerContainerParentElement(
   Element viewerContainerParentElement)
{
   this.viewerContainerParentElement = viewerContainerParentElement;
}
/*------------------------------------------------------------------------------

@name       update - update
                                                                              */
                                                                             /**
            Update native viewer.

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native void update();

}//====================================// end PDFViewer ----------------------//
