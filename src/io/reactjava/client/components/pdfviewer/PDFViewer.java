/*==============================================================================

name:       PDFViewer.java

purpose:    PDFViewer, supporting the following properties:

history:    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.pdfviewer;
                                       // imports --------------------------- //
import com.giavaneers.util.gwt.Logger;
import com.google.gwt.core.client.Callback;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.EventTarget;
import elemental2.dom.HTMLElement;
import elemental2.promise.Promise;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.INativeEffectHandler;
import io.reactjava.client.core.react.INativeEventHandler;
import io.reactjava.client.core.react.INativeFunction1Arg;
import io.reactjava.client.core.react.NativeObject;
import io.reactjava.client.core.react.Utilities;
import io.reactjava.client.core.rxjs.observable.Observable;
import io.reactjava.client.core.rxjs.observable.Subscriber;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import jsinterop.base.Js;
                                       // PDFViewer ==========================//
public class PDFViewer extends Component
{
                                       // class constants --------------------//
public static final Logger kLOGGER = Logger.newInstance();

                                       // options --------------------------- //
public static final String kPDFJS_CDN_URL =
   "https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.2.228/pdf.js";
   //"pdfs/dist/pdf.js";

public static final String kPDFJS_VIEWER_URL =
   "https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.2.228/pdf_viewer.js";
   //"pdfs/dist/pdf_viewer.js";

public static final String kPDFJS_VIEWER_CSS_URL =
   "https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.3.200/pdf_viewer.css";
   //"pdfs/dist/pdf_viewer.css";

public static final String kCOMPONENT_ID_PDF_VIEWER = "ReactJavaPDFViewer";

public static final String kELEMENT_ID_VIEWER                       =
   "viewer";
public static final String kELEMENT_ID_VIEWER_CONTAINER             =
   "viewerContainer";
public static final String kELEMENT_ID_VIEWER_CONTAINER_PARENT      =
   "viewerContainerParent";
public static final String kELEMENT_ID_VIEWER_CONTAINER_GRANDPARENT =
   "viewerContainerGrandparent";

public static final String kPROPERTY_BOOKMARKS     = "bookmarks";
public static final String kPROPERTY_PDF_URL       = "pdfurl";
public static final String kPROPERTY_SCROLL_SRC_ID = "scrollsrcid";
public static final String kPROPERTY_VIEWERSTYLE   = "viewerstyle";

public static final String kSTATE_PDF_READY = "pdfReady";

                                       // external link target values         //
public static final int kLINK_TARGET_NONE   = 0;
public static final int kLINK_TARGET_SELF   = 1;
public static final int kLINK_TARGET_BLANK  = 2;
public static final int kLINK_TARGET_PARENT = 3;

public static final int kLINK_TARGET_TOP    = 4;
                                       // class variables ------------------- //
                                       // shared instance                     //
public  static PDFViewer  sharedInstance;
                                       // instance subscribers                //
protected static List<Subscriber<PDFViewer>>
                          instanceSubscribers;
                                       // map of native pdf viewer by url     //
protected static Map<String,PDFViewerNative>
                          pdfViewerNativeByURL;

                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // pdfViewer                           //
protected PDFViewerNative pdfViewerNative;
                                       // bookmark subscribers                //
protected List<Subscriber<List<List<Bookmark>>>>
                          bookmarkSubscribers;

                                       // pdfViewer ready subscribers         //
protected List<Subscriber<String>>
                          pdfViewerReadySubscribers;

                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       clickHandler - onClick event handler
                                                                              */
                                                                             /**
            onClick event handler as a public instance variable.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler clickHandler = (Event e) ->
{
   int    current   = getPDFPage().getPageNum();
   String elementId = ((HTMLElement)e.currentTarget).id;
   switch(elementId)
   {
      case "next":
      {
         getPDFPage(Math.min(getPDFViewer().getDocument().getNumPages(), current + 1));
         break;
      }
      case "previous":
      {
         getPDFPage(Math.max(1, current - 1));
         break;
      }
   }
};
/*------------------------------------------------------------------------------

@name       addEventListener - add event listener
                                                                              */
                                                                             /**
            Add event listener with any specified debounce interval and
            and specified value filter.

@param      eventName            event name
@param      debounceInterval     debounce msec
@param      eventTarget          target element; if null -> window
@param      supplier             any function producing filter that must change
@param      listener             event listener

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void addEventListener(
   final String        eventName,
   EventTarget         eventTarget,
   final int           debounceInterval,
   final Supplier      filter,
   final EventListener listener)
{
   if (eventTarget == null)
   {
      eventTarget = DomGlobal.window;
   }
   eventTarget.addEventListener(eventName, new EventListener()
   {
      long   last;
      long   now;
      Object lastValue;
      Object val;

      public void handleEvent(Event e)
      {
         now = System.currentTimeMillis();
         val = filter != null ? filter.get() : null;

         if ((last == 0 || (now - last) > debounceInterval)
               && (lastValue == null || !lastValue.equals(val)))
         {
            lastValue = val;
            last      = now;
            try
            {
               listener.handleEvent(e);
            }
            catch(Throwable t)
            {
               kLOGGER.logError(
                  "PDFViewer.addEventListener(): handleEvent threw " + t);
            }
         }
      }
   });
};
/*------------------------------------------------------------------------------

@name       addViewerContainerElement - get the pdf viewer
                                                                              */
                                                                             /**
            Get the pdf viewer.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void addViewerContainerElement()
{
   PDFViewerNative pdfViewer = getPDFViewerCached();
   if (pdfViewer != null)
   {
                                       // add the archived native viewer      //
      HTMLElement grandparent =
         (HTMLElement)DomGlobal.document.getElementById(
            "viewerContainerGrandparent");

      grandparent.appendChild(pdfViewer.getViewerContainerParentElement());
      this.pdfViewerNative = pdfViewer;
      if (getDocumentURLHash() == null)
      {
         setPDFViewerReady();
      }
   }
   else
   {
                                       // create a new native viewer          //
      createPDFViewerNative();
   }
};
/*------------------------------------------------------------------------------

@name       cachePDFViewerNative - cache native pdf viewer
                                                                              */
                                                                             /**
            Cache native pdf viewer.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void cachePDFViewerNative(
   PDFViewerNative pdfViewer)
{
   getPDFViewerNativeByURL().put(getDocumentURL(), pdfViewer);
};
/*------------------------------------------------------------------------------

@name       cacheViewerContainerParentElement - cache viewer container element
                                                                              */
                                                                             /**
            Cache viewer container parent element.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void cacheViewerContainerParentElement()
{
   Element viewerContainerParent =
      DomGlobal.document.getElementById(kELEMENT_ID_VIEWER_CONTAINER_PARENT);

   if (viewerContainerParent != null)
   {
      PDFViewerNative pdfViewer = getPDFViewerCached();
      if (pdfViewer != null)
      {
         pdfViewer.setViewerContainerParentElement(viewerContainerParent);
      }
   }
};
/*------------------------------------------------------------------------------

@name       createPDFViewerNative - create native pdf viewer
                                                                              */
                                                                             /**
            Create native pdf viewer.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void createPDFViewerNative()
{
                                       // initialize link annotations to open //
                                       // external links in a new tab         //
   PDFLinkService pdfLinkService =
      PDFLinkService.newInstance(kLINK_TARGET_BLANK);

                                       // initialize history                   //
   pdfLinkService.setHistory(PDFHistory.newInstance(pdfLinkService));

                                       // initialize search                   //
   PDFFindController pdfFindController =
      PDFFindController.newInstance(pdfLinkService);

                                       // get a viewer                        //
   PDFViewerNative pdfViewer =
      new PDFViewerNative(ViewerOptions.newInstance(
         DomGlobal.document.getElementById(getScrollEventSrcElementId()),
         DomGlobal.document.getElementById("viewer"),
         pdfLinkService,
         pdfFindController));

   cachePDFViewerNative(pdfViewer);
   pdfLinkService.setViewer(pdfViewer);
                                       // window resize event handler ensures //
                                       // always 'page-width'                 //
   addEventListener("resize", null, 250,
      () -> DomGlobal.document.documentElement.clientWidth,
      (Event e) ->
      {
         pdfViewer.currentScaleValue = "page-width";
         pdfViewer.update();
      });
                                       // load the document                   //
   Promise<PDFDocumentProxy> promise =
      PDFJS.getDocument(getDocumentURLBase()).promise;

   promise.then(
      (PDFDocumentProxy pdfDocument) ->
      {
         pdfDocument.initialize(this, pdfLinkService);
         return(promise);
      },
      error -> null);
};
/*------------------------------------------------------------------------------

@name       defaultElementId - provide a default elementId
                                                                              */
                                                                             /**
            Provide a default elementId.

@returns    a default elementId, or null if none.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected String defaultElementId()
{
   return(kCOMPONENT_ID_PDF_VIEWER);
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
public Observable<List<List<Bookmark>>> getBookmarks()
{
   Observable<List<List<Bookmark>>> observable = Observable.create(
      (Subscriber<List<List<Bookmark>>> subscriber) ->
      {
         PDFViewerNative  pdfViewer   = getPDFViewer();
         PDFDocumentProxy pdfDocument =
            pdfViewer != null ? pdfViewer.getDocument() : null;

         if (!props().getBoolean(kPROPERTY_BOOKMARKS, true))
         {
            List<List<Bookmark>> bookmarks = new ArrayList<>();
            if (pdfDocument != null)
            {
               pdfDocument.bookmarks = bookmarks;
            }
            subscriber.next(bookmarks);
            subscriber.complete();
         }
         else if (pdfDocument != null)
         {
            pdfDocument.getBookmarks().subscribe(
               (List<List<Bookmark>> bookmarks) ->
               {
                  kLOGGER.logInfo(
                     "PDFViewer.getBookmarks(): "
                   + "initialized bookmarks.size()=" + bookmarks.size());

                  subscriber.next(bookmarks);
                  subscriber.complete();
               },
               error ->
               {
                  subscriber.error(error);
               });
         }
         else
         {
            getSubscribersBookmarks().add(subscriber);
         }
         return(subscriber);
      });

   return(observable);
}
/*------------------------------------------------------------------------------

@name       getDocumentURL - get document raw url
                                                                              */
                                                                             /**
            Get document raw url

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getDocumentURL()
{
   return(props().getString(kPROPERTY_PDF_URL));
}
/*------------------------------------------------------------------------------

@name       getDocumentURLBase - get document url without any hash
                                                                              */
                                                                             /**
            Get document url without any hash

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getDocumentURLBase()
{
   String base = getDocumentURL();
   int    idx  = base.indexOf('#');
   if (idx > 0)
   {
      base = base.substring(0, idx);
   }
   return(base);
}
/*------------------------------------------------------------------------------

@name       getDocumentURLRaw - get any document url hash
                                                                              */
                                                                             /**
            Get any document url hash

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getDocumentURLHash()
{
   String hash = null;
   String raw  = getDocumentURL();
   int    idx  = raw.indexOf('#');
   if (idx > 0)
   {
      hash = raw.substring(idx + 1);
   }
   return(hash);
}
/*------------------------------------------------------------------------------

@name       getInstance - get new instance
                                                                              */
                                                                             /**
            Get new instance.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Observable<PDFViewer> getInstance()
{
   Observable<PDFViewer> observable = Observable.create(
      (Subscriber<PDFViewer> subscriber) ->
      {
         PDFViewer pdfViewer = sharedInstance();
         if (pdfViewer != null)
         {
            subscriber.next(pdfViewer);
            subscriber.complete();
         }
         else
         {
            getSubscribersInstance().add(subscriber);
         }
         return(subscriber);
      });

   return(observable);
}
/*------------------------------------------------------------------------------

@name       getPDFPage - get the current pdf page
                                                                              */
                                                                             /**
            Get the current pdf page.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public PDFPageProxy getPDFPage()
{
   PDFPageProxy currentPage = Js.uncheckedCast(getState("pdfPage"));
   return(currentPage);
}
/*------------------------------------------------------------------------------

@name       getPDFPage - get the pdf page
                                                                              */
                                                                             /**
            Get the pdf page.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void getPDFPage(
   int pageNum)
{
   PDFDocumentProxy pdfDocument = Js.uncheckedCast(getState("pdfDocument"));
   Promise promise = pdfDocument.getPage(pageNum);
   promise.then(
      (page) ->
      {
                                       // will generate a render() invocation //
         setState("pdfPage", page);
         return(promise);
      },
      error ->
      {
         return(null);
      });
}
/*------------------------------------------------------------------------------

@name       getPDFViewer - get the pdf viewer
                                                                              */
                                                                             /**
            Get the pdf viewer.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public PDFViewerNative getPDFViewer()
{
   return(pdfViewerNative);
}
/*------------------------------------------------------------------------------

@name       getPDFViewerCached - get any cached native pdf viewer by url
                                                                              */
                                                                             /**
            Get any cached native pdf viewer by url.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected PDFViewerNative getPDFViewerCached()
{
   return(Js.uncheckedCast(getPDFViewerNativeByURL().get(getDocumentURL())));
}
/*------------------------------------------------------------------------------

@name       getPDFViewerNativeByURL - get any native pdf viewer by url
                                                                              */
                                                                             /**
            Get any native pdf viewer by url.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static Map<String,PDFViewerNative> getPDFViewerNativeByURL()
{
   if (pdfViewerNativeByURL == null)
   {
      pdfViewerNativeByURL = new HashMap<>();
   }
   return(pdfViewerNativeByURL);
}
/*------------------------------------------------------------------------------

@name       getPDFViewerReady - get indication that the PDFViewer is ready
                                                                              */
                                                                             /**
            Get indication that the PDFViewer is ready.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Observable<String> getPDFViewerReady()
{
   Observable<String> observable = Observable.create(
      (Subscriber<String> subscriber) ->
      {
         if (getState(kSTATE_PDF_READY) != null)
         {
            pdfViewerReadySubscriberResolve(subscriber);
         }
         else
         {
            getSubscribersPDFViewerReady().add(subscriber);
         }
         return(subscriber);
      });

   return(observable);
}
/*------------------------------------------------------------------------------

@name       handleEffect - handleEffect handler
                                                                              */
                                                                             /**
            handleEffect handler as a public instance variable.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEffectHandler handleEffect = () ->
{
   setSharedInstance(this);
   injectPdfJsScriptsAndCSS();
};
/*------------------------------------------------------------------------------

@name       onDocumentLoadSuccess - onDocumentLoadSuccess event handler
                                                                              */
                                                                             /**
            onDocumentLoadSuccess event handler as a public instance variable.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeFunction1Arg<Integer> onDocumentLoadSuccess = (Integer numPages) ->
{
   setState("numPages", numPages);
};
/*------------------------------------------------------------------------------

@name       getScrollEventSrcElementId - get scroll event src elementId
                                                                              */
                                                                             /**
            Get scroll event src elementId.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getScrollEventSrcElementId()
{
   String id = props().getString(kPROPERTY_SCROLL_SRC_ID);
   if (id == null)
   {
      id = kELEMENT_ID_VIEWER_CONTAINER_PARENT;
   }
   return(id);
}
/*------------------------------------------------------------------------------

@name       getSubscribersBookmarks - get bookmarks subscribers
                                                                              */
                                                                             /**
            Get bookmarks subscribers.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected List<Subscriber<List<List<Bookmark>>>> getSubscribersBookmarks()
{
   if (bookmarkSubscribers == null)
   {
      bookmarkSubscribers = new ArrayList<>();
   }
   return(bookmarkSubscribers);
}
/*------------------------------------------------------------------------------

@name       getSubscribersInstance - get new instance subscribers
                                                                              */
                                                                             /**
            Get new instance subscribers.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static List<Subscriber<PDFViewer>> getSubscribersInstance()
{
   if (instanceSubscribers == null)
   {
      instanceSubscribers = new ArrayList<>();
   }
   return(instanceSubscribers);
}
/*------------------------------------------------------------------------------

@name       getSubscribersPDFViewerReady - get new instance subscribers
                                                                              */
                                                                             /**
            Get new instance subscribers.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected List<Subscriber<String>> getSubscribersPDFViewerReady()
{
   if (pdfViewerReadySubscribers == null)
   {
      pdfViewerReadySubscribers = new ArrayList<>();
   }
   return(pdfViewerReadySubscribers);
}
/*------------------------------------------------------------------------------

@name       getViewerContainerParentCached - get any cached container element
                                                                              */
                                                                             /**
            Get any cached container element.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected Element getViewerContainerParentCached()
{
   Element cached = null;

   PDFViewerNative pdfViewer = getPDFViewerCached();
   if (pdfViewer != null)
   {
      cached = Js.uncheckedCast(pdfViewer.getViewerContainerParentElement());
   }
   return(cached);
}
/*------------------------------------------------------------------------------

@name       injectPdfJsScriptsAndCSS - inject pdfjs scripts and css
                                                                              */
                                                                             /**
            Inject pdfjs scripts and css.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void injectPdfJsScriptsAndCSS()
{
   //Utilities.injectCSS(kPDFJS_VIEWER_CSS_URL);
   Utilities.injectScript(
      kPDFJS_CDN_URL, new Callback<Void,Exception>()
      {
         public void onFailure(Exception reason)
         {

            kLOGGER.logError(reason);
         }
         public void onSuccess(Void result)
         {
            Utilities.injectScript(
               kPDFJS_VIEWER_URL, new Callback<Void,Exception>()
               {
                  public void onFailure(Exception reason)
                  {
                     kLOGGER.logError(reason);
                  }
                  public void onSuccess(Void result)
                  {
                     addViewerContainerElement();
                  }
               });
         }
      });
}
/*------------------------------------------------------------------------------

@name       navigateTo - navigate to specified bookmark
                                                                              */
                                                                             /**
            Navigate to specified bookmark.

@params     bookmark    destination, such as
                           ["{"num":157,"gen":0},{"name":"XYZ"},72,720,0]

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void navigateTo(
   String bookmark)
{
   navigateTo(Destination.newInstance(bookmark));
}
/*------------------------------------------------------------------------------

@name       navigateTo - navigate to specified destination
                                                                              */
                                                                             /**
            Navigate to specified destination.

@params     destination    destination, such as
                           [{"num":157,"gen":0},{"name":"XYZ"},72,720,0]

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void navigateTo(
   Destination destination)
{
   kLOGGER.logInfo(
      "PDFViewer.navigateTo(destination): "
    + "destination=" + destination.toStringOverride());

   getPDFViewer().getLinkService().navigateTo(destination);
}
/*------------------------------------------------------------------------------

@name       navigateToHash - navigate to any hash
                                                                              */
                                                                             /**
            Navigate to any hash. Invoked on completion of getBookmarks()
            invocation in init of PDFDocumentProxy.

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void navigateToHash()
{
   String hash = getDocumentURLHash();
   if (hash != null)
   {
      Bookmark bookmark = Bookmark.bookmarkById.get(hash);
      if (bookmark != null)
      {
         navigateTo(bookmark.dest);
         //DomGlobal.setTimeout((eTimeout) ->
         //{
         //   setPDFViewerReady();
         //}, 5000);
         setPDFViewerReady();
      }
      else
      {
         setPDFViewerReady();
      }
   }
}
/*------------------------------------------------------------------------------

@name       pdfViewerReadySubscriberResolve - resolve the pdf viewer subscriber
                                                                              */
                                                                             /**
            Resolve the pdf viewer subscriber.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void pdfViewerReadySubscriberResolve(
   Subscriber<String> subscriber)
{
   subscriber.next("PDFViewer Ready");
   subscriber.complete();
}
/*------------------------------------------------------------------------------

@name       render - render component
                                                                              */
                                                                             /**
            Render component. This implementation is all markup, with no java
            code included.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final void render()
{
   useState(kSTATE_PDF_READY, null);
                                       // passing an empty set of dependencies//
                                       // causes the effect handler to be     //
                                       // invoked only when mounted and       //
                                       // unmounted, not on update as would   //
                                       // occurr on using the single argument //
                                       // useEffect() method                  //
   useEffect(handleEffect, new Object[0]);

   NativeObject viewerStyle = (NativeObject)props().get(kPROPERTY_VIEWERSTYLE);
   if (viewerStyle == null)
   {
      viewerStyle = new NativeObject();
   }
   if (getState(kSTATE_PDF_READY) == null)
   {
      kLOGGER.logInfo("PDFViewer.render(): moving top off the display");

                                       // wait for pdf ready before displaying//
                                       // (cannot use display:none since needs//
                                       // to attach viewerContainer to parent)//
      viewerStyle = NativeObject.with(viewerStyle, "top", "10000px");
   }
   else
   {
      kLOGGER.logInfo("PDFViewer.render(): not moving top off the display");
   }
/*--
   <div id={kELEMENT_ID_VIEWER_CONTAINER_GRANDPARENT}>
      <div id={kELEMENT_ID_VIEWER_CONTAINER_PARENT} style={viewerStyle}>
--*/
   if (getViewerContainerParentCached() == null)
   {
/*--
         <div id={kELEMENT_ID_VIEWER_CONTAINER}>
            <div id={kELEMENT_ID_VIEWER} class="pdfViewer"></div>
         </div>
--*/
   }
   cacheViewerContainerParentElement();
/*--
      </div>
   </div>
--*/
}
/*------------------------------------------------------------------------------

@name       renderCSS - get component css
                                                                              */
                                                                             /**
            Get component css.

@history    Thu Dec 12, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
   boolean bOverflow = false;

   NativeObject viewerstyle = (NativeObject)props().get(kPROPERTY_VIEWERSTYLE);
   if (viewerstyle != null)
   {
      String overflow = viewerstyle.getString("overflow");
      if ("auto".equals(overflow) || "scroll".equals(overflow))
      {
         bOverflow = true;
      }
      if (!bOverflow)
      {
         String overflowY = viewerstyle.getString("overflow-y");
         if ("auto".equals(overflowY) || "scroll".equals(overflowY))
         {
            bOverflow = true;
         }
      }
   }
   if (bOverflow)
   {
      kLOGGER.logInfo("PDFViewer.renderCSS(): hiding body overflow-y");
/*--
      body
      {
         overflow-y: hidden;
      }
--*/
   }
   else
   {
      kLOGGER.logInfo("PDFViewer.renderCSS(): not hiding body overflow-y");
   }
/*--
.textLayer {
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  overflow: hidden;
  opacity: 0.2;
  line-height: 1.0;
}

.textLayer > span {
  color: transparent;
  position: absolute;
  white-space: pre;
  cursor: text;
  -webkit-transform-origin: 0% 0%;
          transform-origin: 0% 0%;
}

.textLayer .highlight {
  margin: -1px;
  padding: 1px;

  background-color: rgb(180, 0, 170);
  border-radius: 4px;
}

.textLayer .highlight.begin {
  border-radius: 4px 0px 0px 4px;
}

.textLayer .highlight.end {
  border-radius: 0px 4px 4px 0px;
}

.textLayer .highlight.middle {
  border-radius: 0px;
}

.textLayer .highlight.selected {
  background-color: rgb(0, 100, 0);
}

.textLayer ::-moz-selection { background: rgb(0,0,255); }

.textLayer ::selection { background: rgb(0,0,255); }

.textLayer .endOfContent {
  display: block;
  position: absolute;
  left: 0px;
  top: 100%;
  right: 0px;
  bottom: 0px;
  z-index: -1;
  cursor: default;
  -webkit-user-select: none;
     -moz-user-select: none;
      -ms-user-select: none;
          user-select: none;
}

.textLayer .endOfContent.active {
  top: 0px;
}


.annotationLayer section {
  position: absolute;
}

.annotationLayer .linkAnnotation > a,
.annotationLayer .buttonWidgetAnnotation.pushButton > a {
  position: absolute;
  font-size: 1em;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.annotationLayer .linkAnnotation > a:hover,
.annotationLayer .buttonWidgetAnnotation.pushButton > a:hover {
  opacity: 0.2;
  background: #ff0;
  box-shadow: 0px 2px 10px #ff0;
}

.annotationLayer .textAnnotation img {
  position: absolute;
  cursor: pointer;
}

.annotationLayer .textWidgetAnnotation input,
.annotationLayer .textWidgetAnnotation textarea,
.annotationLayer .choiceWidgetAnnotation select,
.annotationLayer .buttonWidgetAnnotation.checkBox input,
.annotationLayer .buttonWidgetAnnotation.radioButton input {
  background-color: rgba(0, 54, 255, 0.13);
  border: 1px solid transparent;
  box-sizing: border-box;
  font-size: 9px;
  height: 100%;
  margin: 0;
  padding: 0 3px;
  vertical-align: top;
  width: 100%;
}

.annotationLayer .choiceWidgetAnnotation select option {
  padding: 0;
}

.annotationLayer .buttonWidgetAnnotation.radioButton input {
  border-radius: 50%;
}

.annotationLayer .textWidgetAnnotation textarea {
  font: message-box;
  font-size: 9px;
  resize: none;
}

.annotationLayer .textWidgetAnnotation input[disabled],
.annotationLayer .textWidgetAnnotation textarea[disabled],
.annotationLayer .choiceWidgetAnnotation select[disabled],
.annotationLayer .buttonWidgetAnnotation.checkBox input[disabled],
.annotationLayer .buttonWidgetAnnotation.radioButton input[disabled] {
  background: none;
  border: 1px solid transparent;
  cursor: not-allowed;
}

.annotationLayer .textWidgetAnnotation input:hover,
.annotationLayer .textWidgetAnnotation textarea:hover,
.annotationLayer .choiceWidgetAnnotation select:hover,
.annotationLayer .buttonWidgetAnnotation.checkBox input:hover,
.annotationLayer .buttonWidgetAnnotation.radioButton input:hover {
  border: 1px solid #000;
}

.annotationLayer .textWidgetAnnotation input:focus,
.annotationLayer .textWidgetAnnotation textarea:focus,
.annotationLayer .choiceWidgetAnnotation select:focus {
  background: none;
  border: 1px solid transparent;
}

.annotationLayer .buttonWidgetAnnotation.checkBox input:checked:before,
.annotationLayer .buttonWidgetAnnotation.checkBox input:checked:after,
.annotationLayer .buttonWidgetAnnotation.radioButton input:checked:before {
  background-color: #000;
  content: '';
  display: block;
  position: absolute;
}

.annotationLayer .buttonWidgetAnnotation.checkBox input:checked:before,
.annotationLayer .buttonWidgetAnnotation.checkBox input:checked:after {
  height: 80%;
  left: 45%;
  width: 1px;
}

.annotationLayer .buttonWidgetAnnotation.checkBox input:checked:before {
  -webkit-transform: rotate(45deg);
          transform: rotate(45deg);
}

.annotationLayer .buttonWidgetAnnotation.checkBox input:checked:after {
  -webkit-transform: rotate(-45deg);
          transform: rotate(-45deg);
}

.annotationLayer .buttonWidgetAnnotation.radioButton input:checked:before {
  border-radius: 50%;
  height: 50%;
  left: 30%;
  top: 20%;
  width: 50%;
}

.annotationLayer .textWidgetAnnotation input.comb {
  font-family: monospace;
  padding-left: 2px;
  padding-right: 0;
}

.annotationLayer .textWidgetAnnotation input.comb:focus {
  width: 1150%;
}

.annotationLayer .buttonWidgetAnnotation.checkBox input,
.annotationLayer .buttonWidgetAnnotation.radioButton input {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  padding: 0;
}

.annotationLayer .popupWrapper{
  position: absolute;
  width: 20em;
}

.annotationLayer .popup{
  position: absolute;
  z-index: 200;
  max-width: 20em;
  background-color: #FFFF99;
  box-shadow: 0px 2px 5px #888;
  border-radius: 2px;
  padding: 6px;
  margin-left: 5px;
  cursor: pointer;
  font: message-box;
  font-size: 9px;
  word-wrap: break-word;
}

.annotationLayer .popup > * {
  font-size: 9px;
}

.annotationLayer .popup h1 {
  display: inline-block;
}

.annotationLayer .popup span {
  display: inline-block;
  margin-left: 5px;
}

.annotationLayer .popup p {
  border-top: 1px solid #333;
  margin-top: 2px;
  padding-top: 2px;
}

.annotationLayer .highlightAnnotation,
.annotationLayer .underlineAnnotation,
.annotationLayer .squigglyAnnotation,
.annotationLayer .strikeoutAnnotation,
.annotationLayer .freeTextAnnotation,
.annotationLayer .lineAnnotation svg line,
.annotationLayer .squareAnnotation svg rect,
.annotationLayer .circleAnnotation svg ellipse,
.annotationLayer .polylineAnnotation svg polyline,
.annotationLayer .polygonAnnotation svg polygon,
.annotationLayer .caretAnnotation,
.annotationLayer .inkAnnotation svg polyline,
.annotationLayer .stampAnnotation,
.annotationLayer .fileAttachmentAnnotation {
  cursor: pointer;
}

.pdfViewer .canvasWrapper {
  overflow: hidden;
}

.pdfViewer .page {
  direction: ltr;
  width: 816px;
  height: 1056px;
  margin: 1px auto -8px auto;
  position: relative;
  overflow: visible;
  border: 9px solid transparent;
  background-clip: content-box;
  -webkit-border-image: url(images/shadow.png) 9 9 repeat;
       -o-border-image: url(images/shadow.png) 9 9 repeat;
          border-image: url(images/shadow.png) 9 9 repeat;
  background-color: white;
}

.pdfViewer.removePageBorders .page {
  margin: 0px auto 10px auto;
  border: none;
}

.pdfViewer.singlePageView {
  display: inline-block;
}

.pdfViewer.singlePageView .page {
  margin: 0;
  border: none;
}

.pdfViewer.scrollHorizontal, .pdfViewer.scrollWrapped, .spread {
  margin-left: 3.5px;
  margin-right: 3.5px;
  text-align: center;
}

.pdfViewer.scrollHorizontal, .spread {
  white-space: nowrap;
}

.pdfViewer.removePageBorders,
.pdfViewer.scrollHorizontal .spread,
.pdfViewer.scrollWrapped .spread {
  margin-left: 0;
  margin-right: 0;
}

.spread .page,
.pdfViewer.scrollHorizontal .page,
.pdfViewer.scrollWrapped .page,
.pdfViewer.scrollHorizontal .spread,
.pdfViewer.scrollWrapped .spread {
  display: inline-block;
  vertical-align: middle;
}

.spread .page,
.pdfViewer.scrollHorizontal .page,
.pdfViewer.scrollWrapped .page {
  margin-left: -3.5px;
  margin-right: -3.5px;
}

.pdfViewer.removePageBorders .spread .page,
.pdfViewer.removePageBorders.scrollHorizontal .page,
.pdfViewer.removePageBorders.scrollWrapped .page {
  margin-left: 5px;
  margin-right: 5px;
}

.pdfViewer .page canvas {
  margin: 0;
  display: block;
}

.pdfViewer .page canvas[hidden] {
  display: none;
}

.pdfViewer .page .loadingIcon {
  position: absolute;
  display: block;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  background: url('images/loading-icon.gif') center no-repeat;
}

.pdfPresentationMode .pdfViewer {
  margin-left: 0;
  margin-right: 0;
}

.pdfPresentationMode .pdfViewer .page,
.pdfPresentationMode .pdfViewer .spread {
  display: block;
}

.pdfPresentationMode .pdfViewer .page,
.pdfPresentationMode .pdfViewer.removePageBorders .page {
  margin-left: auto;
  margin-right: auto;
}

.pdfPresentationMode:-ms-fullscreen .pdfViewer .page {
  margin-bottom: 100% !important;
}

.pdfPresentationMode:-webkit-full-screen .pdfViewer .page {
  margin-bottom: 100%;
  border: 0;
}

.pdfPresentationMode:-moz-full-screen .pdfViewer .page {
  margin-bottom: 100%;
  border: 0;
}

.pdfPresentationMode:fullscreen .pdfViewer .page {
  margin-bottom: 100%;
  border: 0;
}

--*/
}
/*------------------------------------------------------------------------------

@name       setPDFViewer - set the native pdf viewer
                                                                              */
                                                                             /**
            Get the native pdf viewer.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void setPDFViewer(
   PDFViewerNative pdfViewerNative)
{
   this.pdfViewerNative = pdfViewerNative;

   if (getDocumentURLHash() == null)
   {
      setPDFViewerReady();
   }
}
/*------------------------------------------------------------------------------

@name       setPDFViewerReady - set the pdf viewer ready
                                                                              */
                                                                             /**
            Set the pdf viewer ready.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void setPDFViewerReady()
{
   setState(kSTATE_PDF_READY, pdfViewerNative);

   List<Subscriber<String>> subscribers = getSubscribersPDFViewerReady();
   while(subscribers.size() > 0)
   {
      Subscriber<String> subscriber = subscribers.remove(0);
      pdfViewerReadySubscriberResolve(subscriber);
   }
}
/*------------------------------------------------------------------------------

@name       setSharedInstance - set shared instance
                                                                              */
                                                                             /**
            Set shared instance.


@param      pdfViewer      shared instance.

@history    Fri Dec 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected static void setSharedInstance(
   PDFViewer pdfViewer)
{
   sharedInstance = pdfViewer;
                                       // notify all subscribers              //
   List<Subscriber<PDFViewer>> subscribers = getSubscribersInstance();
   while(subscribers.size() > 0)
   {
      Subscriber<PDFViewer> subscriber = subscribers.remove(0);
      subscriber.next(pdfViewer);
      subscriber.complete();
   }
}
/*------------------------------------------------------------------------------

@name       sharedInstance - get shared instance
                                                                              */
                                                                             /**
            Get shared instance.


@return     shared instance.

@history    Fri Dec 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static PDFViewer sharedInstance()
{
   return(sharedInstance);
}
}//====================================// end PDFViewer ======================//
