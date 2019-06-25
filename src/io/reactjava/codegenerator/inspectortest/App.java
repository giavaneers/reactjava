/*==============================================================================

name:       AppReactJava.java

purpose:    ReactJava website app.

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
import io.reactjava.client.components.generalpage.GeneralPage;
import io.reactjava.client.core.react.SEOInfo;
import io.reactjava.client.core.react.SEOInfo.SEOPageInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
                                       // AppReactJava =======================//
public class App extends AppComponentTemplate
{
                                       // class constants --------------------//
public static final String   kSEO_DEPLOY_PATH = "http://www.reactjava.io";
public static final String   kIMAGE           = "images/ReactJava64px.png";
public static final String   kSUB_HEADER      = "Create your first ReactJava app";
public static final String[] kMANIFESTS       =
{
                                       // if kSRCCFG_BIND_MANIFESTS_INTO_IMAGE//
   //CompileTime.resolve("text://manifests/RjContributorGuide"),
   //CompileTime.resolve("text://manifests/RjGetStarted"),
   //CompileTime.resolve("text://manifests/RjLanding"),
   //CompileTime.resolve("text://manifests/RjUserGuide")

                                       // else                                //
   "manifests/RjContributorGuide",
   "manifests/RjGetStarted",
   "manifests/RjLanding",
   "manifests/RjUserGuide"
};
public static final String kDESCRIPTION_CONTRIBUTOR_GUIDE =
   "The ReactJava Contributor Guide contains all the resources you need to make "
 + "contributions to this project. It includes an architectural overview, and "
 + "detailed explanations of particular design and implementation features. "
 + "It provides a coding convention and simple rules by which contributions "
 + "are submitted and integrated into the project.";

public static final String kDESCRIPTION_GET_STARTED =
   "This section will help you install and build your first "
 + "ReactJava app. If you already have ReactJava installed, you can "
 + "skip ahead to the Tutorial.";

public static final String kDESCRIPTION_LANDING =
   "Use Java to build the same great applications for mobile and the desktop "
 + "as you do with React and React Native. The same powerful features of React "
 + "you expect: lightweight, declarative, performant, component-based "
 + "programming that is simple to write and easy to debug; packaged in a way "
 + "that naturally combines the structure, familiarity, and reach of Java. "
 + "And targeting native mobile environments is often right out of the box. "
 + "In most cases, ReactJava automatically translates your ReactJava "
 + "components to React Native equivalents.";

public static final String kDESCRIPTION_USER_GUIDE =
   "The ReactJava User Guide includes a simple tutorial that is a step by step "
 + "illustration of how ReactJava works and how you can use it to build a "
 + "React app using Java.";

public static final String kPAGE_ID_CONTRIBUTOR_GUIDE  = "contributorGuide";
public static final String kPAGE_ID_DEFAULT            = "";
public static final String kPAGE_ID_GET_STARTED        = "getStarted";
public static final String kPAGE_ID_LANDING            = "landing";
public static final String kPAGE_ID_USER_GUIDE         = "userGuide";

public static final Map<String,String> kDESCRIPTIONS =
   new HashMap<String,String>()
   {{
      put(kPAGE_ID_CONTRIBUTOR_GUIDE, kDESCRIPTION_CONTRIBUTOR_GUIDE);
      put(kPAGE_ID_GET_STARTED,       kDESCRIPTION_GET_STARTED);
      put(kPAGE_ID_DEFAULT,           kDESCRIPTION_LANDING);
      put(kPAGE_ID_LANDING,           kDESCRIPTION_LANDING);
      put(kPAGE_ID_USER_GUIDE,        kDESCRIPTION_USER_GUIDE);
   }};

public static final String kTITLE_CONTRIBUTOR_GUIDE = "ReactJava Contributor Guide";
public static final String kTITLE_GET_STARTED       = "Getting Started with ReactJava";
public static final String kTITLE_LANDING           = "ReactJava";
public static final String kTITLE_USER_GUIDE        = "ReactJava User Guide";

public static final Map<String,String> kTITLES =
   new HashMap<String,String>()
   {{
      put(kPAGE_ID_CONTRIBUTOR_GUIDE, kTITLE_CONTRIBUTOR_GUIDE);
      put(kPAGE_ID_GET_STARTED,       kTITLE_GET_STARTED);
      put(kPAGE_ID_DEFAULT,           kTITLE_LANDING);
      put(kPAGE_ID_LANDING,           kTITLE_LANDING);
      put(kPAGE_ID_USER_GUIDE,        kTITLE_USER_GUIDE);
   }};
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
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
   return(GeneralPage.getImportedNodeModules());
}
/*------------------------------------------------------------------------------

@name       getManifests - get manifests
                                                                              */
                                                                             /**
            Get manifests.

@return     manifests

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected String[] getManifests()
{
   return(kMANIFESTS);
}
/*------------------------------------------------------------------------------

@name       getSEOInfo - get seo information
                                                                              */
                                                                             /**
            Get SEO info. This method is typically invoked at compile time.

            The intention is to provide a title, description, and base url for
            the app deployment in order to create a redirect target for each
            hash, along with an associated sitemap.

@return     SEOInfo string

@history    Sun Jun 16, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected SEOInfo getSEOInfo()
{
   SEOInfo seoInfo =
      new SEOInfo(
         kSEO_DEPLOY_PATH,
         new ArrayList<SEOPageInfo>()
         {{
            add(new SEOPageInfo(
               kPAGE_ID_DEFAULT,
               kTITLE_LANDING,
               kDESCRIPTION_LANDING));
            add(new SEOPageInfo(
               kPAGE_ID_CONTRIBUTOR_GUIDE,
               kTITLE_CONTRIBUTOR_GUIDE,
               kDESCRIPTION_CONTRIBUTOR_GUIDE));
            add(new SEOPageInfo(
               kPAGE_ID_GET_STARTED,
               kTITLE_GET_STARTED,
               kDESCRIPTION_GET_STARTED));
            add(new SEOPageInfo(
               kPAGE_ID_USER_GUIDE,
               kTITLE_USER_GUIDE,
               kDESCRIPTION_USER_GUIDE));
         }});

   return(seoInfo);
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
   super.render();
}
}//====================================// end AppReactJava ===================//
