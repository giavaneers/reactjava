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
import elemental2.dom.DomGlobal;
import io.reactjava.client.components.generalpage.ContentPage.ContentDsc;
import io.reactjava.client.components.generalpage.GeneralAppBar.AppBarDsc;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.IUITheme;
import io.reactjava.client.core.react.Properties;
import io.reactjava.client.core.react.Router;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
                                       // GeneralPage ========================//
public class GeneralPage<P extends Properties> extends Component
{
                                       // class constants ------------------- //
public static Properties   manifests;  // manifests map                       //
                                       // property keys                       //
public static final String kKEY_MANIFEST = "manifest";
public static final String kKEY_TITLE    = "title";

                                       // state variable name                 //
public static final String kSTATE_ANCHOR = "anchor";
public static final String kSTATE_OPEN   = "open";

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
protected List<ContentDsc> content;    // array of content descriptors        //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       getContent - get content
                                                                              */
                                                                             /**
            Get content.

@return     content descriptor list

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected List<ContentDsc> getContent()
{
   if (content == null)
   {
      content =
         ContentDsc.parse(
            manifests.getString(props().getString(kKEY_MANIFEST)));
   }
   return(content);
}
/*------------------------------------------------------------------------------

@name       openHandler - initialize
                                                                              */
                                                                             /**
            Initialize.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Consumer<Map<String,Object>> openHandler = (Map<String,Object> args) ->
{
   boolean bOpen = (Boolean)args.get("bOpen");
   setState(kSTATE_OPEN, bOpen);

   String id = (String)args.get("id");
   if (id == null)
   {
      setState(kSTATE_ANCHOR, "");
   }
   else if (id.startsWith("path:"))
   {
      Router.push(id.substring(id.indexOf(':') + 1).trim());
   }
   else if (id.startsWith("http:") || id.startsWith("https:"))
   {
      DomGlobal.window.open(id, "_blank");
   }
   else
   {
      setState(kSTATE_ANCHOR, id);
   }
};
/*------------------------------------------------------------------------------

@name       manifests - get manifests
                                                                              */
                                                                             /**
            Get map of manifest key to manifest value.

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static Properties manifests()
{
   return(manifests);
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
public void render()
{
   useState(kSTATE_ANCHOR, "");
   useState(kSTATE_OPEN,   false);

   boolean          bOpen   = getStateBoolean(kSTATE_OPEN);
   List<ContentDsc> content = getContent();
   String           title   = props().getString(kKEY_TITLE);
   if (title == null)
   {
      title = "";
   }
   AppBarDsc appBarDsc = new AppBarDsc(title, bOpen, openHandler);
/*--
   <div>
                                       <!-- App Bar --------------------------->
      <GeneralAppBar appbardsc={appBarDsc}></GeneralAppBar>
      <main class="layout">
                                       <!-- Content --------------------------->
         <ContentPage content={content}></ContentPage>
      </main>
                                       <!-- Footer ---------------------------->
      <Footer />
                                       <!-- Side Drawer ----------------------->
      <SideDrawer open={bOpen} openhandler={openHandler} content={content}>
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
/*------------------------------------------------------------------------------

@name       setManifests - assign manifests
                                                                              */
                                                                             /**
            Assign map of manifest key to manifest value.

@param      manifestsMap      map of manifest key to manifest value

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void setManifests(
   Properties manifestsMap)
{
   manifests = manifestsMap;
}
}//====================================// end GeneralPage ====================//
