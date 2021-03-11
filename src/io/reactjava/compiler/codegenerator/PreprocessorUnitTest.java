/*==============================================================================

name:       PreprocessorUnitTest.java

purpose:    ReactJava IPreprocessor Unit Test.

history:    Wed Sep 02, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.compiler.codegenerator;
                                       // imports --------------------------- //
import io.reactjava.compiler.jsx.IJSXTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
                                       // PreprocessorUnitTest ===============//
public class PreprocessorUnitTest
{
                                       // class constants --------------------//
public static final Map<String,List<String>> kTEST_CONFIG_BY_TEST_NAME =
   new HashMap<String,List<String>>()
   {{
      put(
         "Chat",
         new ArrayList<>(Arrays.asList(
            "io.reactjava.client.examples.chat.App",
            "io.reactjava.client.examples.chat.Chat",
            "io.reactjava.client.examples.chat.Login"
         )));
      put(
         "DisplayCode",
         new ArrayList<>(Arrays.asList(
            "io.reactjava.client.examples.displaycode.App"
         )));
      put(
         "GeneralPage",
         new ArrayList<>(Arrays.asList(
            "io.reactjava.client.examples.generalpage.App"
         )));
      put(
         "HelloWorld",
         new ArrayList<>(Arrays.asList(
            "io.reactjava.client.examples.helloworld.App"
         )));
      put(
         "MaterialUITheme",
         new ArrayList<>(Arrays.asList(
            "io.reactjava.client.examples.materialui.theme.App"
         )));
      put(
         "PDF",
         new ArrayList<>(Arrays.asList(
            "io.reactjava.client.examples.pdf.App",
            "io.reactjava.client.examples.pdf.Drawer"
         )));
      put(
         "PlatformsWebsite",
         new ArrayList<>(Arrays.asList(
            "com.giavaneers.web.platformswebsite.reactjava.AppReactJava",
            "com.giavaneers.web.platformswebsite.general.AppBase",
            "com.giavaneers.web.platformswebsite.general.LandingPage"
         )));
      put(
         "SEO",
         new ArrayList<>(Arrays.asList(
            "io.reactjava.client.examples.seo.App"
         )));
      put(
         "Speck",
         new ArrayList<>(Arrays.asList(
            "io.reactjava.client.examples.speck.App"
         )));
      put(
         "SplitPanel",
         new ArrayList<>(Arrays.asList(
            "io.reactjava.client.examples.splitpanel.App"
         )));
      put(
         "SplitPanelNoApp",
         new ArrayList<>(Arrays.asList(
            "io.reactjava.client.components.splitpanel.SplitPanel"
         )));
      put(
         "TextField",
         new ArrayList<>(Arrays.asList(
            "io.reactjava.client.examples.textfield.App"
         )));
      put(
         "ThreeByThreeStep05",
         new ArrayList<>(Arrays.asList(
            "io.reactjava.client.examples.threebythree.step05.materialui.App"
         )));
      put(
         "TwoSquaresOneFile",
         new ArrayList<>(Arrays.asList(
            "io.reactjava.client.examples.statevariable.twosquaresonefile.App"
         )));
   }};

public static final List<String> kPROVIDER_AND_COMPONENT_CANDIDATE_CLASSNAMES =
   new ArrayList<>(Arrays.asList(
   "io.reactjava.client.providers.platform.web.PlatformWeb",
   "io.reactjava.client.components.generalpage.ContentCode",
   "io.reactjava.client.providers.platform.mobile.AbstractMobilePlatform",
   "io.reactjava.client.components.pdfviewer.PDFLinkService",
   "io.reactjava.client.components.generalpage.GeneralPage",
   "io.reactjava.client.components.pdfviewer.ViewerCover",
   "io.reactjava.client.components.pdfviewer.EventBus",
   "io.reactjava.client.components.pdfviewer.TextContent",
   "io.reactjava.client.components.generalpage.Descriptors",
   "io.reactjava.client.components.pdfviewer.PDFHistory",
   "io.reactjava.client.providers.platform.AbstractPlatform",
   "io.reactjava.client.providers.auth.firebase.FirebaseAuthenticationService",
   "io.reactjava.client.components.login.Login",
   "io.reactjava.client.components.speck.View",
   "io.reactjava.client.components.compiletime.Constants",
   "io.reactjava.client.components.pdfviewer.ViewerOptions",
   "io.reactjava.client.components.pdfviewer.TextContentItem",
   "io.reactjava.client.providers.http.HttpClient",
   "io.reactjava.client.components.pdfviewer.PDFWorker",
   "io.reactjava.client.components.pdfviewer.Point",
   "io.reactjava.client.components.generalpage.ContentImage",
   "io.reactjava.client.providers.auth.IAuthenticationService",
   "io.reactjava.client.providers.database.IDatabaseService",
   "io.reactjava.client.providers.platform.mobile.ios.PlatformIOS",
   "io.reactjava.client.providers.http.IHttpClientBase",
   "io.reactjava.client.components.speck.Atom",
   "io.reactjava.client.providers.database.firebase.FirebaseDatabaseService",
   "io.reactjava.client.components.pdfviewer.PageViewport",
   "io.reactjava.client.components.generalpage.ContentPage",
   "io.reactjava.client.providers.platform.mobile.android.PlatformAndroid",
   "io.reactjava.client.components.generalpage.ContentTitle",
   "io.reactjava.client.moduleapis.ReactGA",
   "io.reactjava.client.core.react.ReactGeneratedCode",
   "io.reactjava.client.components.pdfviewer.PDFJS",
   "io.reactjava.client.components.pdfviewer.Transform",
   "io.reactjava.client.components.speck.System",
   "io.reactjava.client.components.generalpage.ContentBody",
   "io.reactjava.client.components.generalpage.ContentCaption",
   "io.reactjava.client.components.pdfviewer.Destination",
   "io.reactjava.client.components.pdfviewer.PDFViewerNative",
   "io.reactjava.client.components.pdfviewer.Bookmark",
   "io.reactjava.client.components.generalpage.SideDrawer",
   "io.reactjava.client.components.pdfviewer.DocumentLoadingTask",
   "io.reactjava.client.providers.http.HttpResponse",
   "io.reactjava.client.components.pdfviewer.PDFViewer",
   "io.reactjava.client.providers.auth.firebase.FirebaseCore",
   "io.reactjava.client.components.pdfviewer.PDFPageView",
   "io.reactjava.client.components.pdfviewer.PDFFindController",
   "io.reactjava.client.providers.http.IHttpResponse",
   "io.reactjava.client.components.speck.Speck",
   "io.reactjava.client.moduleapis.GoogleAPIs",
   "io.reactjava.client.providers.analytics.IAnalyticsService",
   "io.reactjava.client.providers.platform.IPlatform",
   "io.reactjava.client.providers.analytics.google.GoogleAnalyticsService",
   "io.reactjava.client.components.pdfviewer.PDFPageProxy",
   "io.reactjava.client.components.generalpage.Footer",
   "io.reactjava.client.components.pdfviewer.GlobalWorkerOptions",
   "io.reactjava.client.providers.http.HttpClientBase",
   "io.reactjava.client.components.generalpage.Prism",
   "io.reactjava.client.components.speck.Renderer",
   "io.reactjava.client.providers.http.IHttpClient",
   "io.reactjava.client.components.pdfviewer.PDFDocumentProxy",
   "io.reactjava.client.components.generalpage.GeneralAppBar"));

protected static final String kBASE_PATH = "/Users/brianm/working/";

                                       // class variables ------------------- //
                                       // none                                //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       main - standard main method
                                                                              */
                                                                             /**
            Standard main method.

@param      args, where

               args[0]     test number

@history    Wed Aug 19, 2020 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
static void main(
   String[] args)
{
   try
   {
      if (args.length != 1)
      {
         throw new IllegalArgumentException("Must specify a test name");
      }
                                       // add the specified app classnames    //
      String appClassname = null;
      for (String classname : kTEST_CONFIG_BY_TEST_NAME.get(args[0]))
      {
         appClassname = classname;
         kPROVIDER_AND_COMPONENT_CANDIDATE_CLASSNAMES.add(classname);
      }
                                       // build up the candidates map         //
      Map<String,String> providerAndComponentCandidates = new HashMap<>();
      for (String classname : kPROVIDER_AND_COMPONENT_CANDIDATE_CLASSNAMES)
      {
         String pathname = kBASE_PATH + classname.replace(".","/") + ".java";
         File   source   = new File(pathname);
         String contents = IJSXTransform.getFileAsString(source, null);
         providerAndComponentCandidates.put(classname, contents);
      }

      String encoding = com.google.gwt.dev.util.Util.DEFAULT_ENCODING;

                                       // initialize the preprocessors as     //
                                       // would be done by the compiler       //
      IPreprocessor.initialize(null);

      for (String classname : kPROVIDER_AND_COMPONENT_CANDIDATE_CLASSNAMES)
      {
         IPreprocessor.allPreprocessors(
            classname,
            providerAndComponentCandidates.get(classname).getBytes(encoding),
            encoding,
            providerAndComponentCandidates,
            null);
      }
      if ("HelloWorld".equals(args[0]))
      {
         String pathname = kBASE_PATH + appClassname.replace(".","/") + ".java";
         File   source   = new File(pathname);

                                       // simulate incremental change during  //
                                       // debug session                       //
         Thread.sleep(5000);
                                       // 'blue" to 'green' or vice versa     //
         String contents = IJSXTransform.getFileAsString(source, null);
         if (contents.contains("blue"))
         {
            contents = contents.replace("blue", "green");
         }
         else if (contents.contains("green"))
         {
            contents = contents.replace("green", "blue");
         }
         else
         {
            throw new IllegalStateException(
               "Source does not contain 'blue' or 'green'");
         }

         IPreprocessor.allPreprocessors(
            appClassname,
            contents.getBytes("UTF-8"),
            encoding,
            providerAndComponentCandidates,
            null);
      }
   }
    catch(Throwable t)
   {
      t.printStackTrace();
   }
}
}//====================================// end PreprocessorUnitTest ===========//
