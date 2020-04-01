package io.reactjava.client.components.pdfviewer;

import elemental2.promise.Promise;
import io.reactjava.client.core.react.NativeObject;
import io.reactjava.client.core.rxjs.observable.Observable;
import io.reactjava.client.core.rxjs.observable.Subscriber;
import java.util.ArrayList;
import java.util.List;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/*==============================================================================

name:       PDFPageProxy - native pdfjs api

purpose:    Native pdfjs api

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@JsType(isNative  = true, namespace = "pdfjsLib")
public class PDFPageProxy
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
@JsProperty
public int pageIndex;                  // zero-based page index               //
                                       // protected instance variables -------//
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       PDFPageProxy - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private PDFPageProxy()
{
}
/*------------------------------------------------------------------------------

@name       getAnnotations - get annotations
                                                                              */
                                                                             /**
            Get annotations.

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native Promise<NativeObject[]> getAnnotations();

/*------------------------------------------------------------------------------

@name       getExplicitDestinations - get explicit destinations for the page
                                                                              */
                                                                             /**
            Get list of explicit destinations for the page, sorted by position.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
protected final Observable<List<Destination>> getExplicitDestinations()
{
   PDFDocumentProxy pdfDocument =
      PDFViewer.sharedInstance().getPDFViewer().getDocument();

   Observable<List<Destination>> observable = Observable.create(
      (Subscriber<List<Destination>> subscriber) ->
      {
         pdfDocument.getAnnotations().subscribe(
            (NativeObject[][] annotations) ->
            {
               List<Destination> destinations = new ArrayList<>();
               for (NativeObject annotation : annotations[getPageNum() - 1])
               {

               }

               subscriber.next(destinations);
               subscriber.complete();
            },
            error ->
            {
               subscriber.error(error);
            });

         return(subscriber);
      });

   return(observable);
}
/*------------------------------------------------------------------------------

@name       getPageNum - get page number
                                                                              */
                                                                             /**
            Get one-based page number.

@return     one-based page number

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final int getPageNum()
{
   return(pageIndex + 1);
}
/*------------------------------------------------------------------------------

@name       getTextContent - get text content
                                                                              */
                                                                             /**
            Get text content.

@return     text content

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native Promise<TextContent> getTextContent();

/*------------------------------------------------------------------------------

@name       getViewport - get viewport for specified index
                                                                              */
                                                                             /**
            Get viewport for specified index.

@return     viewport for specified index

@param      scale      scale

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final PageViewport getViewport(
   double scale)
{
   return(getViewport(NativeObject.with("scale", scale)));
}
/*------------------------------------------------------------------------------

@name       getViewport - get viewport for specified index
                                                                              */
                                                                             /**
            Get viewport for specified index.

@return     viewport for specified index

@param      scale    scale

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final PageViewport getViewport(
   double  scale,
   double  rotation,
   boolean bDontFlip)
{
   return(
      getViewport(
         NativeObject.with(
            "scale", scale, "rotation", rotation, "dontFlip", bDontFlip)));
}
/*------------------------------------------------------------------------------

@name       getViewport - get viewport for specified index
                                                                              */
                                                                             /**
            Get viewport for specified index.

@return     viewport for specified index

@param      params      params

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native PageViewport getViewport(
   NativeObject params);

/*------------------------------------------------------------------------------

@name       render - get pdf document for specified url
                                                                              */
                                                                             /**
            Get pdf document for specified url.

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native void render(
   NativeObject params);

}//====================================// end PDFPageProxy -------------------//
