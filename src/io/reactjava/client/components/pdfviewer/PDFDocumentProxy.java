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

name:       PDFDocumentProxy - native pdfjs api

purpose:    Native pdfjs api

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@JsType(isNative  = true, namespace = "pdfjsLib")
public class PDFDocumentProxy
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // pdfInfo                             //
@JsProperty public NativeObject   _pdfInfo;
                                       // protected instance variables -------//
                                       // annotations by page                 //
protected NativeObject[][]        annotations;
                                       // sorted lists of named dst by page   //
protected List<Destination>[]     namedDestinations;
                                       // sorted lists of explicit dst by page//
protected List<List<Destination>> explicitDestinations;
                                       // sorted lists of bookmarks by page   //
protected List<List<Bookmark>>    bookmarks;

                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       PDFDocumentProxy - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private PDFDocumentProxy()
{
}
/*------------------------------------------------------------------------------

@name       getAnnotations - get all annotations
                                                                              */
                                                                             /**
            Get all annotations.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Observable<NativeObject[][]> getAnnotations()
{
   Observable<NativeObject[][]> observable = Observable.create(
      (Subscriber<NativeObject[][]> subscriber) ->
      {
         if (annotations != null)
         {
                                       // previously requested                //
            subscriber.next(annotations);
            subscriber.complete();
         }
         else
         {
                                       // initial request                     //
            annotations = new NativeObject[getNumPages()][];

            final int[] pageCompletedCount = {0};

            for (int pageNum = 1; pageNum <= annotations.length; pageNum ++)
            {
               getAnnotations(annotations, pageNum).subscribe(
                  (Integer pageNumDone) ->
                  {
                     if (++pageCompletedCount[0] == annotations.length)
                     {
                        subscriber.next(annotations);
                        subscriber.complete();
                     }
                  },
                  error ->
                  {
                     subscriber.error(error);
                  });
            }
         }
         return(subscriber);
      });

   return(observable);
};
/*------------------------------------------------------------------------------

@name       getAnnotations - get annotations for specified page
                                                                              */
                                                                             /**
            Get annotations for specified page.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
protected final Observable<Integer> getAnnotations(
   final NativeObject[][] annotations,
   final int              pageNum)
{
   Observable<Integer> observable = Observable.create(
      (Subscriber<Integer> subscriber) ->
      {
         Promise<PDFPageProxy> promisePage = getPage(pageNum);
         promisePage.then((PDFPageProxy pdfPage) ->
         {
            Promise<NativeObject[]> promiseAnnot = pdfPage.getAnnotations();
            promiseAnnot.then((NativeObject[] data) ->
            {
               annotations[pageNum - 1] = data;
               subscriber.next(pageNum);
               subscriber.complete();
               return(promiseAnnot);
            },
            error -> null);

            return(promisePage);
         },
         error -> null);

         return(subscriber);
      });

   return(observable);
}
/*------------------------------------------------------------------------------

@name       getBookmarks - get bookmarks
                                                                              */
                                                                             /**
            Get bookmarks as a list of lists, one list per page, where each list
            is sorted by y offset on the page.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Observable<List<List<Bookmark>>> getBookmarks()
{
   Observable<List<List<Bookmark>>> observable = Observable.create(
      (Subscriber<List<List<Bookmark>>> subscriber) ->
      {
         if (bookmarks != null)
         {
                                       // previously requested                //
            subscriber.next(bookmarks);
            subscriber.complete();
         }
         else
         {
                                       // initial request                     //
            getExplicitDestinations().subscribe(
               (List<List<Destination>> explicitDestinations) ->
               {
                                       // the bookmarks are available when    //
                                       // the explicit destinations are       //
                                       // available                           //
                  subscriber.next(bookmarks);
                  subscriber.complete();
               },
               error ->
               {
                  subscriber.error(error);
               });

         }
         return(subscriber);
      });

   return(observable);
};
/*------------------------------------------------------------------------------

@name       getDestination - get all information of the given named destination
                                                                              */
                                                                             /**
            Get all information of the given named destination.

@return     all information of the given named destination

@param      id    named destination id

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected native Promise getDestination(
   String id);

/*------------------------------------------------------------------------------

@name       getDestinations - get a lookup table for mapping named destinations
                                                                              */
                                                                             /**
            Get a lookup table for mapping named destinations to reference
            numbers.

@return     lookup table for mapping named destinations

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected native Promise getDestinations();

/*------------------------------------------------------------------------------

@name       getExplicitDestinations - get all explicit destinations
                                                                              */
                                                                             /**
            Get all explicit destinations, as a list of destination lists, one
            list per page, where each list is sorted by position in the page.

@return     Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@param      bReturnDestinations     if true, return explicit destinations;
                                    otherwise, return bookmarks

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
protected final Observable<List<List<Destination>>> getExplicitDestinations()
{
   Observable<List<List<Destination>>> observable = Observable.create(
      (Subscriber<List<List<Destination>>> subscriber) ->
      {
         if (explicitDestinations != null)
         {
                                       // previously requested                //
            subscriber.next(explicitDestinations);
            subscriber.complete();
         }
         else
         {
                                       // initial request                     //
            getAnnotations().subscribe(
               (NativeObject[][] documentAnnotations) ->
               {
                  getExplicitDestinationsAnnotationsHandler(
                     subscriber, documentAnnotations);
               },
               error ->
               {
                  subscriber.error(error);
               });

         }
         return(subscriber);
      });

   return(observable);
};
/*------------------------------------------------------------------------------

@name       getExplicitDestinationsAnnotationsHandler - getAnnotations() handler
                                                                              */
                                                                             /**
            getAnnotations() handler for getExplicitDestinations() method.

@return     Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@param      bReturnDestinations     if true, return explicit destinations;
                                    otherwise, return bookmarks

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
protected final void getExplicitDestinationsAnnotationsHandler(
   Subscriber<List<List<Destination>>> subscriber,
   NativeObject[][]                    documentAnnotations)
{
   List<List<Destination>> destinations = new ArrayList<>();
   List<List<Bookmark>>    bookmarksLst = new ArrayList<>();
   for (int i = 0, iMax = getNumPages(); i < iMax; i++)
   {
                                       // a new list for each page            //
      destinations.add(new ArrayList<>());
      bookmarksLst.add(new ArrayList<>());
   }

   final boolean[] bAllRequested = {false};
   final int[]     numRequests   = {0};
   final int[]     numResponses  = {0};

   for (NativeObject[] pageAnnotations : documentAnnotations)
   {
      for (NativeObject annotation : pageAnnotations)
      {
         final String      url  = annotation.getString("url");
         final String      id   = annotation.getString("id");
         final Destination dest = (Destination)annotation.get("dest");
         if (url == null && dest != null)
         {
            ++numRequests[0];

            NativeObject    destRef          = dest.getDestRef();
            Promise<Number> promisePageIndex = getPageIndex(destRef);
            promisePageIndex.then((Number pageIndexNumber) ->
            {
               int pageIndex = pageIndexNumber.intValue();

               destinations.get(pageIndex).add(dest);

                                       // create the associated bookmark      //

               List<Bookmark> pageMarks = bookmarksLst.get(pageIndex);
               Bookmark       bookmark  = new Bookmark(pageIndex, dest);

                                       // standard contains() seems not to    //
                                       // invoke bookmark.equals() ???        //
               boolean bContains = false;
               for (Bookmark test : pageMarks)
               {
                  if (bookmark.equals(test))
                  {
                     bContains = true;
                     break;
                  }
               }
               if (!bContains)
               {
                  pageMarks.add(bookmark);
               }

               if (bAllRequested[0] && ++numResponses[0] == numRequests[0])
               {
                                       // add the text for each bookmark      //
                                       // sort the bookmarks on each page     //
                  getExplicitDestinationsSortBookmarks(
                     subscriber, bookmarksLst, explicitDestinations);
               }
               return(promisePageIndex);
            },
            error -> null);
         }
      }
   }
                                       // indicate all requests have been made//
   bAllRequested[0] = true;
};
/*------------------------------------------------------------------------------

@name       getExplicitDestinationsSetBookmarksText - getAnnotations() handler
                                                                              */
                                                                             /**
            getAnnotations() handler for getExplicitDestinations() method.

@return     Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@param      bReturnDestinations     if true, return explicit destinations;
                                    otherwise, return bookmarks

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
protected final void getExplicitDestinationsSetBookmarksText(
   final Subscriber<List<List<Destination>>> subscriber,
   final List<List<Bookmark>>                bookmarksLst,
   final List<List<Destination>>             destinations,
   final int                                 pageIndex,
   final int[]                               pagesProcessed)
{
                                       // get the page contents to find the   //
                                       // bookmark text                       //
   getPageTextContent(pageIndex).subscribe(
      (TextContent textContent) ->
      {
         for (Bookmark bookmark : bookmarksLst.get(pageIndex))
         {
                                       // assign the bookmark text            //
            bookmark.setTextContent(textContent);
         }

         if (++pagesProcessed[0] == bookmarksLst.size())
         {
                                       // signal all bookmarks and            //
                                       // destinations complete               //
            bookmarks            = bookmarksLst;
            explicitDestinations = destinations;
            subscriber.next(explicitDestinations);
            subscriber.complete();
         }
      },
      error ->
      {
         subscriber.error(error);
      });
};
/*------------------------------------------------------------------------------

@name       getExplicitDestinationsSortBookmarks - getAnnotations() handler
                                                                              */
                                                                             /**
            getAnnotations() handler for getExplicitDestinations() method.

@return     Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@param      bReturnDestinations     if true, return explicit destinations;
                                    otherwise, return bookmarks

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
protected final void getExplicitDestinationsSortBookmarks(
   Subscriber<List<List<Destination>>> subscriber,
   List<List<Bookmark>>                bookmarksLst,
   List<List<Destination>>             destinations)
{
   final int[] pagesProcessed = {0};

   for (int pageIndex = 0; pageIndex < bookmarksLst.size(); pageIndex++)
   {
      List<Bookmark> pageMarks = bookmarksLst.get(pageIndex);
      if (pageMarks.size() == 0)
      {
         ++pagesProcessed[0];
         continue;
      }
                                       // sort the bookmarks on each page     //
      pageMarks.sort(null);

      getExplicitDestinationsSetBookmarksText(
         subscriber,
         bookmarksLst,
         destinations,
         pageIndex,
         pagesProcessed);
   }
};
/*------------------------------------------------------------------------------

@name       getNamedDestinations - get all named destinations
                                                                              */
                                                                             /**
            Get all named destinations, as an array of destination lists, one
            list per page, where each list is sorted by position in the page.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Observable<List<Destination>[]> getNamedDestinations()
{
   Observable<List<Destination>[]> observable = Observable.create(
      (Subscriber<List<Destination>[]> subscriber) ->
      {
         if (namedDestinations != null)
         {
                                       // previously requested                //
            subscriber.next(namedDestinations);
            subscriber.complete();
         }
         else
         {
                                       // initial request                     //
            Promise promise = getDestinations();
            promise.then(table ->
            {
               if (table != null)
               {
                  namedDestinations = new List[0];
               }
               return(promise);
            },
            error -> null);
         }
         return(subscriber);
      });

   return(observable);
};
/*------------------------------------------------------------------------------

@name       getNumPages - get number of pages
                                                                              */
                                                                             /**
            Get number of pages.

@return     number of pages

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final int getNumPages()
{
   return(_pdfInfo.getInt("numPages"));
}
/*------------------------------------------------------------------------------

@name       getPage - get gae for specified page
                                                                              */
                                                                             /**
            Get pdf document for specified url.

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native Promise<PDFPageProxy> getPage(
   int pageNumber);

/*------------------------------------------------------------------------------

@name       getPageIndex - get page index for specified destination reference
                                                                              */
                                                                             /**
            Get page index for specified destination reference. Page index is
            zero based. See Destination for destRef.

@return     a Promise<int> for the specified index

@param      destRef     ex: {num: 183, gen:0}

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native Promise<Number> getPageIndex(
   NativeObject destRef);

/*------------------------------------------------------------------------------

@name       getPageTextContent - get page content
                                                                              */
                                                                             /**
            Get page content for specified page.

@param      pageIndex      page index

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Observable<TextContent> getPageTextContent(
   final int pageIndex)
{
   Observable<TextContent> observable = Observable.create(
      (Subscriber<TextContent> subscriber) ->
      {
         Promise<PDFPageProxy> promisePage = getPage(pageIndex + 1);
         promisePage.then(
            (PDFPageProxy pdfPage) ->
            {
               Promise<TextContent> promiseContent = pdfPage.getTextContent();
               promiseContent.then(
                  (TextContent textContent) ->
                  {
                     subscriber.next(textContent);
                     subscriber.complete();
                     return(promiseContent);
                  },
                  error ->
                  {
                     subscriber.error(new Exception(error.toString()));
                     return(null);
                  });

               return(promisePage);
            },
            error ->
            {
               subscriber.error(new Exception(error.toString()));
               return(null);
            });

         return(subscriber);
      });

   return(observable);
};
/*------------------------------------------------------------------------------

@name       initialize - initialize
                                                                              */
                                                                             /**
            Initialize

@param      number of pages

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final void initialize(
   PDFViewer      pdfViewer,
   PDFLinkService pdfLinkService)
{
   PDFViewerNative pdfViewerNative = pdfLinkService.pdfViewer;

   pdfLinkService.setDocument(this, null);
   pdfViewerNative.setDocument(this);

   if (bookmarks == null)
   {
                                       // initialize bookmarks                //
      getBookmarks().subscribe(
         (List<List<Bookmark>> bookmarks) ->
         {
                                       // notify all subscribers              //
            List<Subscriber<List<List<Bookmark>>>> subscribers =
               pdfViewer.getSubscribersBookmarks();

            while(subscribers.size() > 0)
            {
               Subscriber<List<List<Bookmark>>> subscriber =
                  subscribers.remove(0);

               subscriber.next(bookmarks);
               subscriber.complete();
            }
                                       // navigate to any hash                //
            pdfViewer.navigateToHash();
         },
         error ->{});
   }
   pdfViewer.setPDFViewer(pdfViewerNative);
}
}//====================================// end PDFDocumentProxy ---------------//
