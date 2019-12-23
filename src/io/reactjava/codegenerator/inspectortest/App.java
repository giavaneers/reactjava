/*==============================================================================

name:       AppBase.java

purpose:    App base class.

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
package io.reactjava.codegenerator.inspectortest;
                                       // imports --------------------------- //
import com.giavaneers.util.gwt.Logger;
import elemental2.dom.DomGlobal;
import io.reactjava.client.components.generalpage.Descriptors.ButtonDsc;
import io.reactjava.client.components.generalpage.Descriptors.FooterDsc;
import io.reactjava.client.components.generalpage.Descriptors.FooterDsc.FooterCategoryDsc;
import io.reactjava.client.components.generalpage.Descriptors.FooterDsc.FooterCategoryDsc.FooterTopicDsc;
import io.reactjava.client.components.generalpage.Descriptors.FooterDsc.FooterCreditDsc;
import io.reactjava.client.components.generalpage.Descriptors.PageDsc;
import io.reactjava.client.components.generalpage.Descriptors.SectionDsc;
import io.reactjava.client.components.generalpage.GeneralAppBar;
import io.reactjava.client.components.generalpage.GeneralPage;
import io.reactjava.client.core.providers.http.HttpClient;
import io.reactjava.client.core.providers.http.HttpResponse;
import io.reactjava.client.core.react.AppComponentTemplate;
import io.reactjava.client.core.react.Router;
import io.reactjava.client.moduleapis.ReactGA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
                                       // AppBase ============================//
public class App extends AppComponentTemplate
{
                                       // class constants --------------------//
                                       // src configuration                   //
public static final boolean kSRCCFG_BIND_MANIFESTS_INTO_IMAGE  = true;

private static final Logger kLOGGER  = Logger.newInstance();

                                       // other constants                     //
public static final String kKEY_PAGE_ID         = "pageId";
public static final String kSTATE_VALUE_KNOWN   = "known";
public static final String kSTATE_VALUE_UNKNOWN = "unknown";

                                       // page ids                            //
public static final String kPAGE_ID_CONTRIBUTOR_GUIDE  = "contributorGuide";
public static final String kPAGE_ID_DEFAULT            = "";
public static final String kPAGE_ID_GET_STARTED        = "getStarted";
public static final String kPAGE_ID_LANDING            = "landing";
public static final String kPAGE_ID_USER_GUIDE         = "userGuide";

                                       // page indices                        //
public static final int    kPAGE_IDX_CONTRIBUTOR_GUIDE = 0;
public static final int    kPAGE_IDX_GET_STARTED       = 1;
public static final int    kPAGE_IDX_LANDING           = 2;
public static final int    kPAGE_IDX_USER_GUIDE        = 3;

public static final Map<String,Integer> kPAGE_ID_TO_PAGE_IDX =
   new HashMap<String,Integer>()
   {{
      put(Router.kPATH_DEFAULT,       kPAGE_IDX_LANDING);
      put(kPAGE_ID_CONTRIBUTOR_GUIDE, kPAGE_IDX_CONTRIBUTOR_GUIDE);
      put(kPAGE_ID_GET_STARTED,       kPAGE_IDX_GET_STARTED);
      put(kPAGE_ID_LANDING,           kPAGE_IDX_LANDING);
      put(kPAGE_ID_USER_GUIDE,        kPAGE_IDX_USER_GUIDE);
   }};

public static final String kSECTION_CONTRIBUTOR_GUIDE = "Contributor Guide";
public static final String kSECTION_GET_STARTED       = "Get Started";
public static final String kSECTION_USER_GUIDE        = "User Guide";


public static final String kPATH        = "path:";
public static final String kPATH_PARAM  = ":" + kKEY_PAGE_ID;

                                       // navigation routes                   //
public static final Map<String,Class>  kAPP_ROUTES = new HashMap<>();

                                       // router push paths                   //
public static final String kPUSH_CONTRIBUTOR_GUIDE = kPAGE_ID_CONTRIBUTOR_GUIDE;
public static final String kPUSH_GET_STARTED       = kPAGE_ID_GET_STARTED;
public static final String kPUSH_USER_GUIDE        = kPAGE_ID_USER_GUIDE;
public static final String kPUSH_LANDING           = kPAGE_ID_LANDING;

                                       // menu urls                           //
public static final String kMENU_URL_DOCS     = kPATH + kPUSH_GET_STARTED;
public static final String kMENU_URL_TUTORIAL = kPATH + kPUSH_USER_GUIDE;
public static final String kMENU_URL_GITHUB   = "https://github.com/giavaneers";

                                       // app bar button names                //
public static final String kAPP_BAR_BUTTON_TEXT_API      = "API";
public static final String kAPP_BAR_BUTTON_TEXT_DOCS     = "Docs";
public static final String kAPP_BAR_BUTTON_TEXT_GITHUB   = "GitHub";
public static final String kAPP_BAR_BUTTON_TEXT_TUTORIAL = "Tutorial";

                                       // in order of appearance              //
public static final int    kAPP_BAR_BUTTON_IDX_DOCS     = 0;
public static final int    kAPP_BAR_BUTTON_IDX_TUTORIAL = 1;
public static final int    kAPP_BAR_BUTTON_IDX_API      = 2;
public static final int    kAPP_BAR_BUTTON_IDX_GITHUB   = 3;
public static final int    kAPP_BAR_BUTTON_IDX_LOGIN    = 4;

public static final ButtonDsc[] kBUTTON_DSCS =
{
                                       // in order of appearance              //
   new ButtonDsc(kAPP_BAR_BUTTON_TEXT_DOCS,     kMENU_URL_DOCS),
   new ButtonDsc(kAPP_BAR_BUTTON_TEXT_TUTORIAL, kMENU_URL_TUTORIAL),
   new ButtonDsc(kAPP_BAR_BUTTON_TEXT_API,      ""),
   new ButtonDsc(kAPP_BAR_BUTTON_TEXT_GITHUB,   ""),
   new ButtonDsc(GeneralAppBar.kAPP_BAR_BUTTON_TEXT_LOGIN, ""),
};
                                       // page section descriptors            //
public static final SectionDsc[] kSECTION_DSCS =
{
   new SectionDsc(
      kSECTION_GET_STARTED,
      "images/Download.png",
      new String[]
      {
         "Learn by jumping right in",
         "Install everything you need",
         "Template project ready to run",
         "Illustrative examples"
      },
      "Get started",
      "contained"),

   new SectionDsc(
      kSECTION_USER_GUIDE,
      "images/Book.png",
      new String[]
      {
         "Get detailed developer info",
         "Complete API reference",
         "Helpful how-tos",
         "Illustrative examples"
      },
      "User Guide",
      "outlined"),

   new SectionDsc(
      kSECTION_CONTRIBUTOR_GUIDE,
      "images/Edit.png",
      new String[]
      {
         "Contribute to the project",
         "Architectural overview",
         "Implementation details",
         "GIT open source repository"
      },
      "Contributor Guide",
      "outlined")
};

public static final String    kLOCATION = "$Location";
public static final FooterDsc kFOOTER   =
   new FooterDsc(
      new FooterCategoryDsc[]
      {
         new FooterCategoryDsc(
            "Project",
            new FooterTopicDsc[]
            {
               new FooterTopicDsc(
                  "Team", "http://www.giavaneers.com"),
               new FooterTopicDsc(
                  "History", "http://www.giavaneers.com"),
               new FooterTopicDsc(
                  "Contact us", "http://www.giavaneers.com/contact")
            }),
         new FooterCategoryDsc(
            "Features",
            new FooterTopicDsc[]
            {
               new FooterTopicDsc(
                  "Get Started",       kLOCATION + "#getStarted"),
               new FooterTopicDsc(
                  "User Guide",        kLOCATION + "#userGuide"),
               new FooterTopicDsc(
                  "Contributor Guide", kLOCATION + "#contributorGuide"),
            }),
         new FooterCategoryDsc(
            "Resources",
            new FooterTopicDsc[]
            {
               new FooterTopicDsc(
                  "Other Projects",  "http://www.giavaneers.com/platforms"),
               new FooterTopicDsc(
                  "Google Console",  "https://console.cloud.google.com"),
               new FooterTopicDsc(
                  "Google Search",   "https://search.google.com/search-console"),
               new FooterTopicDsc(
                  "Google Analytics","https://analytics.google.com/analytics"),
            }),
         new FooterCategoryDsc(
            "Legal",
            new FooterTopicDsc[]
            {
               new FooterTopicDsc("License",        ""),
               new FooterTopicDsc("Privacy policy", ""),
               new FooterTopicDsc("Terms of use",   "")
            }),
      },
      new FooterCreditDsc(
         "<a href=\"http://www.giavaneers.com\" target=\"_blank\">"
       + "   <img src=\"images/GiavaneersMark.png\" class=\"logo\" />"
       + "</a>",
         "Website created with React and "
       + "<a href=\"http://www.reactjava.io\" target=\"_blank\">ReactJava</a>\"")
    );
                                       // page descriptor                     //
public static final PageDsc kPAGE_DSC_BASE =
   new PageDsc(
      "dummyTitle",
      "dummyImage",
      true,
      kBUTTON_DSCS,
      null,
      kSECTION_DSCS,
      kFOOTER);
                                       // class variables ------------------- //
                                       // map of manifest by pageId           //
protected static Map<String,String> manifestMap;

                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       getGoogleAnalyticsId - get google analytics id
                                                                              */
                                                                             /**
            Get google analytics id. This impementation is to be overridden.

@return     google analytics id.

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected String getGoogleAnalyticsId()
{
   return("UA-142849130-1");
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
protected List<String> getImportedNodeModules()
{
   List<String> modules = new ArrayList(GeneralPage.getImportedNodeModules());
// modules.add("react-ga");

   return(modules);
}
/*------------------------------------------------------------------------------

@name       getManifest - get manifest for specified pageId
                                                                              */
                                                                             /**
            Get manifest for specified pageId.

@param      pageId      specified pageId

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
/
90.
                                                                              */
//------------------------------------------------------------------------------
protected void getManifest(
   String pageId)
{
   String url = getManifests()[kPAGE_ID_TO_PAGE_IDX.get(pageId)];

   HttpClient.get(url).subscribe(
      (HttpResponse rsp) ->
      {
         getManifestMap().put(pageId, rsp.getText());
         setState(pageId, kSTATE_VALUE_KNOWN);
      },
      (Throwable error) ->
      {
         kLOGGER.logError(error.getMessage());
      });
}
/*------------------------------------------------------------------------------

@name       getManifestMap - get manifest map
                                                                              */
                                                                             /**
            Get manifest map.

@return     manifest map

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected Map<String,String> getManifestMap()
{
   if (manifestMap == null)
   {
      manifestMap = new HashMap<>();

      if (kSRCCFG_BIND_MANIFESTS_INTO_IMAGE)
      {
         String[] manifests = getManifests();

         manifestMap.put(
            Router.kPATH_DEFAULT,       manifests[kPAGE_IDX_LANDING]);
         manifestMap.put(
            kPAGE_ID_CONTRIBUTOR_GUIDE, manifests[kPAGE_IDX_CONTRIBUTOR_GUIDE]);
         manifestMap.put(
            kPAGE_ID_GET_STARTED,       manifests[kPAGE_IDX_GET_STARTED]);
         manifestMap.put(
            kPAGE_ID_LANDING,           manifests[kPAGE_IDX_LANDING]);
         manifestMap.put(
            kPAGE_ID_USER_GUIDE,        manifests[kPAGE_IDX_USER_GUIDE]);
      }
   }
   return(manifestMap);
}
/*------------------------------------------------------------------------------

@name       getManifests - get manifests
                                                                              */
                                                                             /**
            Get manifests. This implementation is null.

@return     manifests

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected String[] getManifests()
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       getNavRoutes - get routes for application
                                                                              */
                                                                             /**
            Get map of component classname by route path.

@return     void

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected Map<String,Class> getNavRoutes()
{
   kAPP_ROUTES.put(Router.kPATH_DEFAULT, getClass());
   kAPP_ROUTES.put(kPATH_PARAM,          getClass());
   return(kAPP_ROUTES);
}
/*------------------------------------------------------------------------------

@name       getPageDsc - get page descriptor
                                                                              */
                                                                             /**
            Get page descriptor. This implementation is null.

@return     page descriptor

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected PageDsc getPageDsc()
{
   return(null);
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
protected PageDsc getPageDsc(
   String title,
   String image,
   String subHeader,
   String gitHubURL)
{
   String apiMenuURL =
      "/javadoc/" + title.replace(" ","").toLowerCase() + "/index.html";

   PageDsc pageDsc                                       = kPAGE_DSC_BASE;
   pageDsc.sections[0].subheader                         = subHeader;
   pageDsc.title                                         = title;
   pageDsc.image                                         = image;
   pageDsc.appBarButtons[kAPP_BAR_BUTTON_IDX_API].url    = apiMenuURL;
   pageDsc.appBarButtons[kAPP_BAR_BUTTON_IDX_GITHUB].url = gitHubURL;

   return(pageDsc);
}
/*------------------------------------------------------------------------------

@name       initialize - initialize
                                                                              */
                                                                             /**
            Initialize. This override of the default configures google
            analytics.

@history    Thu Jun 27, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void initialize()
{
   ReactGA.initialize(getGoogleAnalyticsId());
   ReactGA.pageview(
      DomGlobal.location.getPathname() + DomGlobal.location.getSearch());
}
/*------------------------------------------------------------------------------

@name       render - render component

            Render component.

@return     void

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final void render()
{
                                       // either way works...                 //
   String pageId = true ? Router.getPath() : props().getString(kKEY_PAGE_ID);
   useState(pageId, kSTATE_VALUE_UNKNOWN);
   if (kSTATE_VALUE_UNKNOWN.equals(getStateString(pageId)))
   {
                                       // retrieve the manifest for pageId    //
      getManifest(pageId);
   }
   else
   {
      String mf = getManifestMap().get(pageId);
      switch(pageId)
      {
         case kPAGE_ID_CONTRIBUTOR_GUIDE:
         case kPAGE_ID_GET_STARTED:
         case kPAGE_ID_USER_GUIDE:
         {
/*--        <GeneralPage pagedsc={getPageDsc()} manifest={mf}></GeneralPage>
--*/
            break;
         }
         default:
         {
/*--        <LandingPage pagedsc={getPageDsc()} manifest={mf}></LandingPage>--*/
         }
      }
   }
}
}//====================================// end App ============================//
