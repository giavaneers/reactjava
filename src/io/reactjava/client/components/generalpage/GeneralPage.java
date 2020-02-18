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
import io.reactjava.client.components.generalpage.Descriptors.PageDsc;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.IUITheme;
import io.reactjava.client.core.react.Properties;
import io.reactjava.client.core.react.Router;
import io.reactjava.client.core.react.Utilities;
import io.reactjava.client.core.rxjs.observable.Observable;
import io.reactjava.client.providers.http.HttpClient;
import io.reactjava.client.providers.http.HttpResponse;
import java.util.ArrayList;
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
public static final String  KSTATE_SIDE_DRAWER_OPEN = "sideDrawerOpen";

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // protected instance variables -------//
protected AppBarDsc         appBarDsc; // app bar descriptor                  //
protected PageDsc           pageDsc;   // page descriptor                     //
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
            getStateBoolean(KSTATE_SIDE_DRAWER_OPEN),
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
   List<ContentDsc> content = new ArrayList<>();
   Object           rawText = getManifestRawText();
   if (rawText instanceof Observable)
   {
      ((Observable<HttpResponse>)rawText).subscribe(
         (HttpResponse httpResponse) ->
         {
            List<ContentDsc> parsed = ContentDsc.parse(httpResponse.getText());
            setState(kSTATE_CONTENT, parsed);
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

   return(content);
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
      "prismjs.components.prism-core",
      "prismjs.components.prism-clike",
      "prismjs.components.prism-java",
      "prismjs.themes.prism-okaidia.css"
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
   boolean bOpen = (Boolean)args.get("bOpen");
   setState(KSTATE_SIDE_DRAWER_OPEN, bOpen);

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
   else if (Utilities.isURL(id))
   {
                                       // relative or absolute reference      //
      DomGlobal.window.open(id, "_blank");
   }
   else
   {
      setState(kSTATE_ANCHOR, id);
   }
};
/*------------------------------------------------------------------------------

@name       getManifestRawText - get manifest raw text
                                                                              */
                                                                             /**
            Get manifest raw text.

@return     page descriptor

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected Object getManifestRawText()
{
   String manifest = props().getString(kKEY_MANIFEST);
   Object retVal   =
      Utilities.isURL(manifest) ? HttpClient.get(manifest) : manifest;

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
   useState(KSTATE_SIDE_DRAWER_OPEN, false);
 /*--
   <div>
                                       <!-- App Bar --------------------------->
      <GeneralAppBar appbardsc={getAppBarDsc()}></GeneralAppBar>
      <main class="layout">
                                       <!-- Content --------------------------->
         <ContentPage content={getState(kSTATE_CONTENT)}></ContentPage>
      </main>
                                       <!-- Footer ---------------------------->
      <Footer footer={getPageDsc().footer}></Footer>
                                       <!-- Side Drawer ----------------------->
      <SideDrawer
         open={getStateBoolean(KSTATE_SIDE_DRAWER_OPEN)}
         openhandler={openHandler}
         content={getState(kSTATE_CONTENT)}>
      </SideDrawer>
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
/*------------------------------------------------------------------------------

@name       renderCSS - get component css
                                                                              */
                                                                             /**
            Get component css.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
   .toolbar : getTheme().getMixins().toolbar;
                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
   String unit       = "" + getTheme().getSpacing().getUnit() + "px";
   String unitX3     = IUITheme.cssLengthScale(unit, 3);
   String unitX6     = IUITheme.cssLengthScale(unit, 6);
   String unitX6Plus = IUITheme.cssLengthAdd(unitX6, 900);
/*--
   .layout
   {
      margin-left:  {unitX3};
      margin-right: {unitX3};
      width:        auto;
   }

   @media (min-width: {unitX6Plus})
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
}//====================================// end GeneralPage ====================//
