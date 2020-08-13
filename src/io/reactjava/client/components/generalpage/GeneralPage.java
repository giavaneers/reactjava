/*==============================================================================

name:       GeneralPage.java

purpose:    ReactJava website general page.

history:    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

notes:

                  This program was created by Giavaneers
        and is the confidential and proprietary product of Giavaneers Inc.
      Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2019 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.


==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.generalpage;
                                       // imports --------------------------- //
import com.giavaneers.util.gwt.Logger;
import elemental2.dom.DomGlobal;
import io.reactjava.client.components.generalpage.ContentPage.ContentDsc;
import io.reactjava.client.components.generalpage.Descriptors.AppBarDsc;
import io.reactjava.client.components.generalpage.Descriptors.FooterDsc;
import io.reactjava.client.components.generalpage.Descriptors.PageDsc;
import io.reactjava.client.components.pdfviewer.PDFViewer;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.IUITheme;
import io.reactjava.client.core.react.NativeObject;
import io.reactjava.client.core.react.Properties;
import io.reactjava.client.core.react.Router;
import io.reactjava.client.core.react.Utilities;
import io.reactjava.client.core.rxjs.observable.Observable;
import io.reactjava.client.providers.http.HttpClient;
import io.reactjava.client.providers.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
                                       // GeneralPage ========================//
public class GeneralPage<P extends Properties> extends Component
{
                                       // class constants ------------------- //
private static final Logger kLOGGER = Logger.newInstance();

                                       // property keys                       //
public static final String  kKEY_PAGE_DSC = "pagedsc";
public static final String  kKEY_MANIFEST = "manifest";

                                       // state variable name                 //
public static final String  kSTATE_ANCHOR           = "anchor";
public static final String  kSTATE_CONTENT          = "content";
public static final String  kSTATE_SIDE_DRAWER_OPEN = "sideDrawerOpen";

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // protected instance variables -------//
protected AppBarDsc appBarDsc;         // app bar descriptor                  //
protected PageDsc   pageDsc;           // page descriptor                     //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       getAppBarDsc - get app bar descriptor
                                                                              */
                                                                             /**
            Get app bar descriptor.

@return     app bar descriptor

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected AppBarDsc getAppBarDsc()
{
   if (appBarDsc == null)
   {
      appBarDsc =
         new AppBarDsc(
            getPageDsc().title,
            getPageDsc().bMenuButton,
            getPageDsc().appBarButtons,
            getStateBoolean(kSTATE_SIDE_DRAWER_OPEN),
            openHandler);
   }
   return(appBarDsc);
}
/*------------------------------------------------------------------------------

@name       getContent - get content
                                                                              */
                                                                             /**
            Get content.

            If props().getString(kKEY_MANIFEST) is a url, returns an empty list
            and sets the kSTATE_CONTENT state value after the raw text has been
            fetched and parsed.

            Otherwise, returns the parsed manifest immediately.

@return     content descriptor list which is empty iff the manifest is a url to
            be fetched.

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected List<ContentDsc> getContent()
{
   List<ContentDsc> content = getContentStateValue();
   if (content == null)
   {
      Object rawText = getManifestRawText();
      if (rawText instanceof Observable)
      {
         ((Observable<HttpResponse>)rawText).subscribe(
            (HttpResponse httpResponse) ->
            {
               setState(
                  kSTATE_CONTENT, ContentDsc.parse(httpResponse.getText()));
            },
            error ->
            {
                                    // ignore                              //
               kLOGGER.logError(error.toString());
            });
      }
      else
      {
         content = ContentDsc.parse((String)rawText);
      }
   }

   return(content);
}
/*------------------------------------------------------------------------------

@name       getContentPDFURL - get any content pdf url
                                                                              */
                                                                             /**
            Get any content pdf url.

@return     Any content pdf url, or null if none.

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected String getContentPDFURL()
{
   String pdfURL =  null;
   List<ContentDsc> content = getContentStateValue();
   if (content != null)
   {
      for (ContentDsc chase : content)
      {
         if (chase.type == ContentDsc.kTYPE_URL)
         {
            pdfURL = chase.text;
            break;
         }
      }
   }

   return(pdfURL);
}
/*------------------------------------------------------------------------------

@name       getContentProperties - get any content properties
                                                                              */
                                                                             /**
            Get any content properties.

@return     any content properties, or null if none

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected Properties getContentProperties()
{
   Properties properties = Properties.newInstance();
   for (ContentDsc dsc : getContent())
   {
      if (dsc.type == ContentDsc.kTYPE_PROPERTIES)
      {
         properties = dsc.properties;
         break;
      }
   }
   return(properties);
}
/*------------------------------------------------------------------------------

@name       getContentStateValue - get content state value
                                                                              */
                                                                             /**
            Get content state value.

@return     content state value.

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected List<ContentDsc> getContentStateValue()
{
   return((List<ContentDsc>)getState("content"));
}
/*------------------------------------------------------------------------------

@name       getCover - get any PDFViewer cover
                                                                              */
                                                                             /**
            Get any PDFViewer cover.

@return     any PDFViewer cover

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected String getCover()
{
   String cover = props().getString(PDFViewer.kPROPERTY_COVER);
   if (cover == null)
   {
      cover = (String)getContentProperties().get("cover");
   }
   return(cover);
}
/*------------------------------------------------------------------------------

@name       getImportedNodeModules - get imported node modules
                                                                              */
                                                                             /**
            Get imported node modules.

@return     list of node module names.

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static List<String> getImportedNodeModules()
{
   return(Arrays.asList(
      "prismjs/components/prism-core",
      "prismjs/components/prism-clike",
      "prismjs/components/prism-java",
      "prismjs/themes/prism-okaidia.css"
   ));
}
/*------------------------------------------------------------------------------

@name       openHandler - open handler
                                                                              */
                                                                             /**
            Open handler.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Consumer<Map<String,Object>> openHandler = (Map<String,Object> args) ->
{
   Boolean bOpenDrawer = (Boolean)args.get("bOpen");
   if (bOpenDrawer != null && bOpenDrawer)
   {
      Component.forClass(SideDrawer.class).subscribe(
         this,
         (Component component) ->
         {
            component.setState("show", true);
         },
         error -> {});
   }
   else
   {
      String url = (String)args.get("url");
      String id  = url != null ? url : (String)args.get("id");
      if (id == null)
      {
         setState(kSTATE_ANCHOR, "");
      }
      else if (id.startsWith("path:"))
      {
         Router.push(id.substring(id.indexOf(':') + 1).trim());
      }
      else if (id.startsWith("bookmark:"))
      {
                                       // ex: bookmark:{157,0,'XYZ',72,720,0} //
         pdfFViewerNavigateToBookmark(id);
      }
      else if (Utilities.isURL(id))
      {
                                       // relative or absolute reference      //
         DomGlobal.window.open(id, "_blank");
      }
      else
      {
         setState(kSTATE_ANCHOR, id);
      }
   }
};
/*------------------------------------------------------------------------------

@name       getManifestRawText - get manifest raw text
                                                                              */
                                                                             /**
            Get manifest raw text.

@return     iff manifest is a url, returns an observable for an http request to
            get the manifest raw text; otherwise, returns the manifest raw text
            directly.

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected Object getManifestRawText()
{
   String manifest = props().getString(kKEY_MANIFEST);
   String url      = Utilities.toAbsoluteURL(manifest);
   Object retVal   = url != null ? HttpClient.get(url) : manifest;

   return(retVal);
}
/*------------------------------------------------------------------------------

@name       getPageDsc - get page descriptor
                                                                              */
                                                                             /**
            Get page descriptor.

@return     page descriptor

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected PageDsc getPageDsc()
{
   if (pageDsc == null)
   {
      pageDsc = (PageDsc)props().get(kKEY_PAGE_DSC);
      if (pageDsc == null)
      {
         pageDsc = PageDsc.kDEFAULT;
      }
   }
   return(pageDsc);
}
/*------------------------------------------------------------------------------

@name       getPDFOptions - get any pdf options                                */
                                                                             /**
            Get any pdf options .

@return     any pdf options

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected NativeObject getPDFOptions()
{
   NativeObject pdfOptions = new NativeObject();
   String       pdfURL     = getContentPDFURL();
   if (pdfURL != null)
   {
      pdfOptions =
         NativeObject.with(
            PDFViewer.kPROPERTY_COVER,         "#bdcff1",
            PDFViewer.kPROPERTY_COVER_TOP_PX, "70px",
            PDFViewer.kPROPERTY_PDF_URL,       pdfURL,
            PDFViewer.kPROPERTY_SCROLL_SRC_ID, "main");
   }
   return(pdfOptions);
}
/*------------------------------------------------------------------------------

@name       pdfFViewerNavigateToBookmark - pdfViewer navigate to bookmark
                                                                              */
                                                                             /**
            PDFViewer navigate to specified bookmark

@param      bookmark    target bookmark, ex: "bookmark:{157,0,'XYZ',72,720,0}"

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void pdfFViewerNavigateToBookmark(
   String bookmark)
{
   Component.forClass(PDFViewer.class).subscribe(
      this,
      (Component pdfViewer) ->
      {
         ((PDFViewer)pdfViewer).navigateTo(bookmark);
      },
      error -> {});
}
/*------------------------------------------------------------------------------

@name       render - render component
                                                                              */
                                                                             /**
            Render component.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final void render()
{
   useState(kSTATE_ANCHOR, "");
   useState(kSTATE_CONTENT, getContent());
   useState(kSTATE_SIDE_DRAWER_OPEN, false);

   List<ContentDsc> content = getContentStateValue();
   if (content != null)
   {
      FooterDsc footerDsc = getPageDsc().footer;
      String    mainClass = willRenderPDF() ? "mainClassPDF" : " layout";
 /*--
      <div class="generalpagecontainer">
                                       <!-- App Bar --------------------------->
         <GeneralAppBar appbardsc={getAppBarDsc()} />
         <main id="main" class={mainClass}>
                                       <!-- Content --------------------------->
            <ContentPage content={content} pdfoptions={getPDFOptions()} />
--*/
      if (footerDsc != null)
      {
         if (willRenderPDF())
         {
 /*--
                                       <!-- Footer ---------------------------->
            <Footer footer={footerDsc} />
--*/
         }
/*--
         </main>
--*/
         if (!willRenderPDF())
         {
/*--
                                       <!-- Footer ---------------------------->
         <Footer footer={footerDsc} />
--*/
         }
      }
      if (getPageDsc().bMenuButton)
      {
 /*--
                                       <!-- Side Drawer ----------------------->
         <SideDrawer
            openhandler={openHandler}
            content={content}
            pdfurl={getContentPDFURL()}
            displayhash={props().get("displayhash")}
         />
--*/
      }
 /*--
      </div>
--*/
      int    appBarHeight = getTheme().getSpacing().getUnit() * 10;
      String anchorId     = getStateString(kSTATE_ANCHOR);
      if (anchorId != null && anchorId.length() > 0)
      {
                                       // scroll to element                   //
         Router.scrollIntoView(anchorId, -appBarHeight);
      }
   }
}
/*------------------------------------------------------------------------------

@name       renderCSS - get component css
                                                                              */
                                                                             /**
            Get component css.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
   String unit         = "" + getTheme().getSpacing().getUnit() + "px";
   String unitX3       = IUITheme.cssLengthScale(unit, 3);
   String unitX6       = IUITheme.cssLengthScale(unit, 6);
   String unitX6Plus   = IUITheme.cssLengthAdd(unitX6, 900);
   String bodyOverflow = willRenderPDF() ? "hidden" : "visible";
/*--
   body
   {
      overflow-y:      {bodyOverflow};
   }
   .mainClassPDF
   {
      position:        absolute;
      overflow:        auto;
      top:             70px;
      bottom:          0px;
      left:            0px;
      width:           100%;
   }
   .layout
   {
      margin-left:     {unitX3};
      margin-right:    {unitX3};
      width:           auto;
   }
   @media (min-width:  {unitX6Plus})
   {
      .layout
      {
         margin-left:  auto;
         margin-right: auto;
         width:        900px;
      }
   }
--*/
}
/*------------------------------------------------------------------------------

@name       willRenderPDF - test whether will render PDF
                                                                              */
                                                                             /**
            test whether will render PDF

@return     true iff will render PDF

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean willRenderPDF()
{
   return(getContentPDFURL() != null);
}
}//====================================// end GeneralPage ====================//
