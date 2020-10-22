/*==============================================================================

name:       ReactCodePackager.java

purpose:    GWT Compiler post processor to build application js bundle..

history:    Mon Aug 28, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.compiler.codegenerator;
                                       // imports --------------------------- //
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.dev.CompilerContext;
import com.google.gwt.dev.cfg.ModuleDef;
import com.google.gwt.dev.jjs.PrecompilationContext;
import io.reactjava.client.providers.platform.IPlatform;
import io.reactjava.client.providers.platform.mobile.android.PlatformAndroid;
import io.reactjava.client.providers.platform.mobile.ios.PlatformIOS;
import io.reactjava.client.providers.platform.web.PlatformWeb;
import io.reactjava.client.core.react.AppComponentTemplate;
import io.reactjava.client.core.react.INativeEventHandler;
import io.reactjava.client.core.react.IUITheme;
import io.reactjava.client.core.react.ReactGeneratedCode;
import io.reactjava.client.core.react.SEOInfo;
import io.reactjava.client.core.react.Utilities;
import io.reactjava.client.core.resources.javascript.IJavascriptResources;
import io.reactjava.client.core.rxjs.observable.Observable;
import io.reactjava.compiler.jsx.IConfiguration;
import io.reactjava.compiler.jsx.IJSXTransform;
import io.reactjava.compiler.jsx.JSXSEOInfo;
import io.reactjava.compiler.jsx.TypeDsc;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.Deflater;
                                       // ReactCodePackager ==================//
public class ReactCodePackager implements IPostprocessor
{
                                       // class constants --------------------//
public static final boolean kSRCCFG_TREE_SHAKE  = false;
public static final boolean kSRCCFG_USE_REQUIRE = true;

public static final String  kGENERATOR_PACKAGE_NAME =
   "io.reactjava.client.generated";

public static final String  kGENERATOR_SIMPLE_CLASSNAME =
   "ReactCodeGeneratorImplementation";

public static final String  kOUT_BUILD_SRC_PATH =
   IConfiguration.toOSPath("out/build/reactnative/src");

public static final String  kRSRC_BASE_PATH =
   "io/reactjava/client/core/resources/";

public static final List<String> kFILTER_ALL_TYPES  =
   new ArrayList<String>()
   {{
      add("com.giavaneers.util.core");
      add("com.google");
      add("io.reactjava.client.core.rxjs");
      add("elemental2");
      add("core");
      add("io.angular4java");
      add("java");
      add("javax");
      add("jsinterop");
      add("org.jsoup");
   }};

public static final List<String> kFILTER_APP_TYPES  =
   new ArrayList<String>()
   {{
      add("io.reactjava.client.core");
      add("io.reactjava.compiler");
   }};

public static final List<String> kFILTER_RX_TYPES  =
   new ArrayList<String>()
   {{
      add("io.reactjava.client.core.resources");
      add("io.reactjava.compiler");
   }};

public static final String kBOOT_JS_FILENAME =
   "Boot.js";

public static final String kDEPLOY_PATH_PROPERTY_NAME =
   "reactjava.deploypath";

public static final String kKEY_ALL_TYPES  = "allTypes";
public static final String kKEY_APP_TYPES  = "appTypes";
public static final String kKEY_COMPONENTS = "components";
public static final String kKEY_PLATFORMS  = "platforms";
public static final String kKEY_PROVIDERS  = "providers";

public static final String kAPP_CLASSNAME_TOKEN = "%appClassnameToken%";

public static final String kBOOT_JS =
   "import {Component} from 'react';\n"
 + "import _ from './reactNativeGWTLibrary.js';\n"
 + "\n"
 + "window.ReactJava.React = window.React;\n"
 + "\n"
 + "export default class Boot extends Component\n"
 + "{\n"
 + "   constructor(props)\n"
 + "   {\n"
 + "      super(props);\n"
 + "   }\n"
 + "   render()\n"
 + "   {\n"
 + "      var element =\n"
 + "         window.io.reactjava.client.core.react.ReactGeneratedCode.boot(\n"
 + "            '" + kAPP_CLASSNAME_TOKEN + "');\n"
 + "\n"
 + "      return(element);\n"
 + "   }\n"
 + "}\n";

public static final String kREDIRECT_HTML =
   "<!DOCTYPE html>\n"
 + "<html lang='en'>\n"
 + "<head>\n"
 + "   <script type='text/javascript'>\n"
 + "                                   // avoids leaving the orig in history  //\n"
 + "      window.location.replace(\n"
 + "         window.location.href.replace(\n"
 + "            '%redirectName%.html',\n"
 + "            '%defaultName%.html#%hash%'));\n"
 + "   </script>\n"
 + "</head>\n"
 + "<body></body>\n"
 + "</html>\n";

public static final String kSITEMAP_HDR =
   "<?xml version='1.0' encoding='UTF-8'?>\n"
 + "<urlset xmlns='http://www.sitemaps.org/schemas/sitemap/0.9'>\n";

public static final String kSITEMAP_ITEM =
   "   <url>\n"
 + "      <loc>%itemPath%</loc>\n"
 + "      <changefreq>daily</changefreq>\n"
 + "   </url>\n";

public static final String kSITEMAP_FTR =
   "</urlset>\n";

public static final String kROBOTS_TXT =
   "User-agent: *\n"
 + "Disallow:\n"
 + "Sitemap: %deployPath%/%moduleName%Sitemap.xml\n";

                                       // class variables ------------------- //
                                       // none                                //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       ReactCodeGenerator - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ReactCodePackager()
{
   super();
}
/*------------------------------------------------------------------------------

@name       copyBundleScriptToArtifact - copy library scripts to artifact
                                                                              */
                                                                             /**
            Copy library scripts to artifact: "reactjavaplatform.js" and
            "reactjavaapp.js".

            Platform and app scripts generated separately to reduce build time
            since platform will not change and app may or may not change

@param      configuration     configuration
@param      context           context
@param      logger            logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void copyBundleScriptToArtifact(
   IConfiguration   configuration,
   GeneratorContext context,
   TreeLogger logger)
   throws           Exception
{
   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager..copyBundleScriptToArtifact(): entered");

   Boolean bCompressForce = null;
   InputStream in;

   if (injectScriptAppDirty(logger))
   {
      String injectScript = generateInjectScript(configuration, logger);
      in = new ByteArrayInputStream(injectScript.getBytes("UTF-8"));
   }
   else
   {
      in = new FileInputStream(
         IConfiguration.getInjectScript(logger));

      bCompressForce = false;
   }

   String path = "javascript/reactjavaapp.js";
   copyStreamToArtifact(
      in, path, configuration, context, logger, bCompressForce);

   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyBundleScriptToArtifact(): exiting");
}
/*------------------------------------------------------------------------------

@name       copyCSSFileToArtifact - copy css file to artifact
                                                                              */
                                                                             /**
            copy css file to artifact.

@param      src               src
@param      configuration     configuration
@param      context           context
@param      logger            logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void copyCSSFileToArtifact(
   File             src,
   IConfiguration   configuration,
   GeneratorContext context,
   TreeLogger       logger)
   throws           Exception
{
   logger.log(
      TreeLogger.Type.INFO,
      "copyCSSFileToArtifact(): " + src.getAbsolutePath());

   copyStreamToArtifact(
      new FileInputStream(src),
      "css/" + src.getName(), configuration, context, logger);

   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyCSSFileToArtifact(): exiting");
}
/*------------------------------------------------------------------------------

@name       copyCSSResourceToArtifact - copy css rsrc to artifact
                                                                              */
                                                                             /**
            copy css rsrc to artifact.

@param      rsrcPath          rsrcPath
@param      configuration     configuration
@param      context           context
@param      logger            logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void copyCSSResourceToArtifact(
   String           rsrcPath,
   IConfiguration   configuration,
   GeneratorContext context,
   TreeLogger       logger)
   throws           Exception
{
   if (!rsrcPath.startsWith("http"))
   {
      copyResourceToArtifact("css/" + rsrcPath, configuration, context, logger);
   }
}
/*------------------------------------------------------------------------------

@name       copyHTMLContentToWAR - copy html content to artifact
                                                                              */
                                                                             /**
            Copy html content to artifact.

@param      filename    filename
@param      content     content
@param      logger      logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void copyHTMLContentToWAR(
   String           filename,
   String           content,
   TreeLogger       logger)
   throws           Exception
{
   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyHTMLContentToWAR(): "+ filename);

   File warDir   = IConfiguration.getWarDir(logger);
   File htmlFile = new File(warDir, filename);

   try (OutputStream out = new FileOutputStream(htmlFile))
   {
      try (InputStream in = new ByteArrayInputStream(content.getBytes("UTF-8")))
      {
         IConfiguration.fastChannelCopy(in, out, content.length());
      }
   }

   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyHTMLContentToWAR(): exiting");
}
/*------------------------------------------------------------------------------

@name       copyImageResourceToArtifact - copy image rsrc to artifact
                                                                              */
                                                                             /**
            copy image rsrc to artifact.

@param      rsrcPath          rsrcPath
@param      configuration     configuration
@param      context           context
@param      logger            logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void copyImageResourceToArtifact(
   String           rsrcPath,
   IConfiguration   configuration,
   GeneratorContext context,
   TreeLogger       logger)
   throws           Exception
{
   if (!rsrcPath.startsWith("http"))
   {
      copyResourceToArtifact(
         "images/" + rsrcPath, configuration, context, logger);
   }
}
/*------------------------------------------------------------------------------

@name       copyPlatformScriptsToArtifact - copy platform scripts to artifact
                                                                              */
                                                                             /**
            Copy platform scripts to artifact.

@param      configuration     configuration
@param      context           context
@param      logger            logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void copyPlatformScriptsToArtifact(
   IConfiguration   configuration,
   GeneratorContext context,
   TreeLogger       logger)
   throws           Exception
{
   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyPlatformScriptsToArtifact(): entered");

   Collection<String> scripts = configuration.getRequiredPlatformScripts();
   if (scripts.size() > 0)
   {
      for (String rsrcScript : scripts)
      {
         copyScriptResourceToArtifact(
            rsrcScript, configuration, context, logger);
      }
   }

   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyPlatformScriptsToArtifact(): exiting");
}
/*------------------------------------------------------------------------------

@name       copyResources - copy appropriate scripts
                                                                              */
                                                                             /**
            Copy appropriate scripts

@param      configuration     configuration
@param      context           context
@param      logger            logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static void copyResources(
   IConfiguration   configuration,
   GeneratorContext context,
   TreeLogger       logger)
   throws           Exception
{
   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyResources(): begin copying resources");

   boolean bLoadLazy = configuration.getScriptsLoadLazy();

   Collection<String> globalImages = configuration.getGlobalImages();
   if (globalImages.size() > 0)
   {
      logger.log(
         TreeLogger.Type.INFO,
         "ReactCodePackager.copyResources(): begin copying images");

      for (String rsrcImage : globalImages)
      {
                                       // copy each image from the associated //
                                       // resource to the distribution...     //
         copyImageResourceToArtifact(
            rsrcImage, configuration, context, logger);
      }
      logger.log(
         TreeLogger.Type.INFO,
         "ReactCodePackager.copyResources(): done copying images");
   }

   Collection<String> globalCSS = configuration.getGlobalCSS();
   for (String importedModule : TypeDsc.getAllImportedNodeModules(logger))
   {
      if (importedModule.endsWith(".css"))
      {
         globalCSS.add(importedModule);
      }
   }
   if (globalCSS.size() > 0)
   {
      logger.log(
         TreeLogger.Type.INFO,
         "ReactCodePackager.copyResources(): begin copying css");

      String imported = "";
      for (String rsrcStylesheet : globalCSS)
      {
                                       // copy each stylesheet from associated//
                                       // resource to the distribution...     //
         if (rsrcStylesheet.startsWith("/"))
         {
                                       // copy an imported stylesheet         //
            File cssFile = new File(rsrcStylesheet);
            imported += imported.length() > 0 ? "," : "" + cssFile.getName();

            copyCSSFileToArtifact(cssFile, configuration, context, logger);
         }
         else
         {
            copyCSSResourceToArtifact(
               rsrcStylesheet, configuration, context, logger);
         }
      }
      if (imported.length() == 0)
      {
         logger.log(
            TreeLogger.Type.INFO,
            "ReactCodePackager.copyResources(): "
          + "making empty imported stylesheets list");

         imported = " ";
      }
                                       // copy the list to the distribution   //
      copyStreamToArtifact(
         new ByteArrayInputStream(imported.getBytes("UTF-8")),
         "css/" + ReactGeneratedCode.kIMPORTED_STYLESHEETS_LIST,
         configuration, context, logger);

      logger.log(
         TreeLogger.Type.INFO,
         "ReactCodePackager.copyResources(): done copying css");
   }
   if (!IJavascriptResources.kSRCCFG_SCRIPTS_AS_RESOURCES && bLoadLazy)
   {
      logger.log(
         TreeLogger.Type.INFO,
         "ReactCodePackager.copyResources(): begin copying scripts");

                                       // copy each platform script from its  //
                                       // associated resource to the          //
                                       // distribution...                     //
      copyPlatformScriptsToArtifact(configuration, context, logger);

                                       // copy the configuration to the       //
                                       // distribution...                     //
      //copyConfigurationToArtifact(configuration, context, logger);

      if (IConfiguration.kSRCCFG_BUNDLE_SCRIPT)
      {
                                       // copy the application bundle to the  //
                                       // distribution...                     //
         copyBundleScriptToArtifact(configuration, context, logger);
      }
      else
      {
                                       // copy each library script from its   //
                                       // associated resource to the          //
                                       // distribution...                     //
                                       // (this approach may be fruitless     //
                                       // since a script may require others...//
                                       // the bundle script approach seems    //
                                       // better)                             //
         //copyLibraryScriptsToArtifact(configuration, context, logger);
      }

      logger.log(
         TreeLogger.Type.INFO,
         "ReactCodePackager.copyResources(): done copying scripts");
   }

   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyResources(): done copying resources");
}
/*------------------------------------------------------------------------------

@name       copyResourceToArtifact - copy script rsrc to artifact
                                                                              */
                                                                             /**
            copy script rsrc to artifact.

@param      rsrcPath          rsrcPath
@param      configuration     configuration
@param      context           context
@param      logger            logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void copyResourceToArtifact(
   String           rsrcPath,
   IConfiguration   configuration,
   GeneratorContext context,
   TreeLogger       logger)
   throws           Exception
{
   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyResourceToArtifact(): " + rsrcPath);

   InputStream in =
      context.getResourcesOracle().getResourceAsStream(
         kRSRC_BASE_PATH + rsrcPath);

   if (in == null)
   {
      throw new FileNotFoundException(kRSRC_BASE_PATH + rsrcPath);
   }

   copyStreamToArtifact(in, rsrcPath, configuration, context, logger);

   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyResourceToArtifact(): exiting");
}
/*------------------------------------------------------------------------------

@name       copyRobotsContentToWAR - copy robot.txt content to artifact
                                                                              */
                                                                             /**
            Copy robots.txt content to artifact.

@param      deployPath   deploy path
@param      moduleName   module name
@param      logger      logger

@history    Sun Jun 16, 2019 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void copyRobotsContentToWAR(
   String     deployPath,
   String     moduleName,
   TreeLogger logger)
   throws     Exception
{
   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyRobotsContentToWAR(): entered");

   File warDir     = IConfiguration.getWarDir(logger);
   File robotsFile = new File(warDir, "robots.txt");
   String content  =
      kROBOTS_TXT
         .replace("%deployPath%", deployPath)
         .replace("%moduleName%", moduleName);

   try (OutputStream out = new FileOutputStream(robotsFile))
   {
      try (InputStream in = new ByteArrayInputStream(content.getBytes("UTF-8")))
      {
         IConfiguration.fastChannelCopy(in, out, content.length());
      }
   }

   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyRobotsContentToWAR(): exiting");
}
/*------------------------------------------------------------------------------

@name       copyScriptResourceToArtifact - copy script rsrc to artifact
                                                                              */
                                                                             /**
            copy script rsrc to artifact.

@param      rsrcPaths      one or more script paths in preferred order separated
                           by commas
@param      configuration  configuration
@param      context        context
@param      logger         logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void copyScriptResourceToArtifact(
   String           rsrcPaths,
   IConfiguration   configuration,
   GeneratorContext context,
   TreeLogger       logger)
   throws           Exception
{
   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyScriptResourceToArtifact(): "
    + "entered with rsrcPaths=" + rsrcPaths);

   if (!rsrcPaths.startsWith("http"))
   {
      String[] choices = rsrcPaths.split(",");
      for (String rsrcPath : choices)
      {
         if (rsrcPath.startsWith(IConfiguration.kNODE_MODULES_DIR))
         {
            int    idx     = IConfiguration.kNODE_MODULES_DIR.length();
            String relPath = rsrcPath.substring(idx);
            File   nodeDir = IConfiguration.getProjectDirectory("node_modules", logger);
            File   script  = new File(nodeDir, relPath);
            if (!script.exists())
            {
               logger.log(
                  TreeLogger.Type.INFO,
                  "ReactCodePackager.copyScriptResourceToArtifact(): "
                + "node_modules script not found: " + script.getAbsolutePath());

               continue;
            }
                                       // copy the node script to the artifact//
            rsrcPath = "javascript/" + choices[choices.length - 1];

            logger.log(
               TreeLogger.Type.INFO,
               "ReactCodePackager.copyScriptResourceToArtifact(): "
             + "copying node_modules script: " + script.getAbsolutePath()
             + " to " + rsrcPath);

            copyStreamToArtifact(
               new FileInputStream(script),
               rsrcPath, configuration, context, logger);

            break;
         }
         else
         {
            rsrcPath = "javascript/" + rsrcPath;

            logger.log(
               TreeLogger.Type.INFO,
               "ReactCodePackager.copyScriptResourceToArtifact(): "
             + "copying resource script: " + rsrcPath);

            copyResourceToArtifact(rsrcPath, configuration, context, logger);
         }
      }
   }
   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyScriptResourceToArtifact(): "
    + "exiting for rsrcPaths=" + rsrcPaths);

}
/*------------------------------------------------------------------------------

@name       copySitemapContentToWAR - copy sitemap content to artifact
                                                                              */
                                                                             /**
            Copy sitemap content to artifact.

@param      content     sitemap content
@param      logger      logger

@history    Sun Jun 16, 2019 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void copySitemapContentToWAR(
   String     moduleName,
   String     content,
   TreeLogger logger)
   throws     Exception
{
   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copySitemapContentToWAR(): entered");

   File warDir      = IConfiguration.getWarDir(logger);
   File sitemapFile = new File(warDir, moduleName + "Sitemap.xml");

   try (OutputStream out = new FileOutputStream(sitemapFile))
   {
      try (InputStream in = new ByteArrayInputStream(content.getBytes("UTF-8")))
      {
         IConfiguration.fastChannelCopy(in, out, content.length());
      }
   }

   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copySitemapContentToWAR(): exiting");
}
/*------------------------------------------------------------------------------

@name       copyStreamToArtifact - copy stream to artifact
                                                                              */
                                                                             /**
            copy stream to artifact.

@param      in                source stream
@param      dstPath           destination path
@param      configuration     configuration
@param      context           context
@param      logger            logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void copyStreamToArtifact(
   InputStream      in,
   String           dstPath,
   IConfiguration   configuration,
   GeneratorContext context,
   TreeLogger       logger)
   throws           Exception
{
   copyStreamToArtifact(in, dstPath, configuration, context, logger, null);
}
/*------------------------------------------------------------------------------

@name       copyStreamToArtifact - copy stream to artifact
                                                                              */
                                                                             /**
            copy stream to artifact.

@param      in                source stream
@param      dstPath           destination path
@param      configuration     configuration
@param      context           context
@param      logger            logger
@param      bCompressForce    iff true, force compression

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void copyStreamToArtifact(
   InputStream      in,
   String           dstPath,
   IConfiguration   configuration,
   GeneratorContext context,
   TreeLogger       logger,
   Boolean          bCompressForce)
   throws           Exception
{
   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyStreamToArtifact(): entered for " + dstPath);

   if (in == null)
   {
      throw new IllegalArgumentException("Input stream may not be null");
   }
                                       // remove any '@' characters in the    //
                                       // script path since at least the GWT  //
                                       // code server throws CORS objectsions //
                                       // with URLs containing a '@' character//
   String dstName =
      Utilities.getArtifactReactJavaDirectoryName()+"/" + dstPath.replace("@","");

   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyStreamToArtifact(): dst=" + dstName);

   OutputStream out = context.tryCreateResource(logger, dstName);
   if (out != null)
   {
      OutputStream outSave = out;

      boolean bCompress =
         bCompressForce != null
            ? bCompressForce
            : configuration.getScriptsCompressed()
                  && dstPath.endsWith(".js")
                  && !dstPath.contains(IJavascriptResources.kSCRIPT_PLAT_PAKO);

      if (bCompress)
      {
         out = new ByteArrayOutputStream();
      }

      IConfiguration.fastChannelCopy(in, out, in.available());

      logger.log(
         TreeLogger.Type.INFO,
         "ReactCodePackager.copyStreamToArtifact(): output"
            + (bCompress ? "" : " not") + " compressed");

      if (bCompress)
      {
         byte[] uncompressed = ((ByteArrayOutputStream)out).toByteArray();
         byte[] compressed   = toDeflaterFiltered(uncompressed);

         in  = new ByteArrayInputStream(compressed);
         out = outSave;
         IConfiguration.fastChannelCopy(in, out, in.available());

         String snippet = new String(uncompressed, "UTF-8").substring(0, 300);
         logger.log(
            TreeLogger.Type.DEBUG,
            "ReactCodePackager.copyStreamToArtifact(): compressing source snippet=\n" + snippet);
      }
      try
      {
         context.commitResource(logger, out);
         logger.log(
            TreeLogger.Type.INFO,
            "ReactCodePackager.copyStreamToArtifact(): resource was committed");
      }
      catch(Exception e)
      {
         logger.log(
            TreeLogger.Type.ERROR,
            "ReactCodePackager.copyStreamToArtifact(): resource was not committed");
         throw e;
      }
   }
   else
   {
      logger.log(
         TreeLogger.Type.INFO,
         "ReactCodePackager.copyStreamToArtifact(): "
       + "resource by that name is already pending or already exists");
   }

   logger.log(
      TreeLogger.Type.INFO,
      "ReactCodePackager.copyStreamToArtifact(): exiting");
}
/*------------------------------------------------------------------------------

@name       generateBootJs - generate bootstrap react component
                                                                              */
                                                                             /**
            Generate bootstrap react component.


@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void generateBootJs(
   TreeLogger logger)
   throws     Exception
{
   logger.log(
      TreeLogger.Type.INFO, "ReactCodePackager.generateBootJs(): entered");

   TypeDsc app = TypeDsc.getTargetApp(logger);
   if (app != null)
   {
      String  appClassname = TypeDsc.getTargetApp(logger).classname;
      String  sBootJs      = kBOOT_JS.replace(kAPP_CLASSNAME_TOKEN, appClassname);

      File    projectDir   = IConfiguration.getProjectDirectory(null, logger);
      File    buildSrcDir  = new File(projectDir, kOUT_BUILD_SRC_PATH);
      buildSrcDir.mkdirs();

      File bootJs = new File(buildSrcDir, kBOOT_JS_FILENAME);
      JavascriptBundler.writeFile(bootJs, sBootJs);
   }

   logger.log(
      TreeLogger.Type.INFO, "ReactCodePackager.generateBootJs(): exiting");
}
/*------------------------------------------------------------------------------

@name       generateInjectScript - generate inject script
                                                                              */
                                                                             /**
            Generate inject script.

@return     inject script file

@param      logger      logger

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
             Example command line:

            "browserify HelloWorldReactJava.nocache.js "
            "HelloWorldReactJava.devmode.js "
            "B198F197F8CB44A1F19CFF81CBFEBB94.cache.js  -o bundle.js "
            "-s ReactJava -r @material-ui/core/Button -r react -r react-dom "
            "-r rxjs/Observable -r rxjs/Subscriber -r rxjs/Subscription"

            For information on how to use browserified modules with
            react-native, see:

            "https://hackernoon.com/using-core-node-js-modules-in-"
            "react-native-apps-64acd4d07140

            Also, from

            "https://stackoverflow.com/questions/40777337/"
            "how-to-use-browserified-libraries-in-react-native",

            The best solution I've come up with is to switch to webpack.
            As alluded to in one of the comments the library needs to be
            processed by something like browserify or web pack because it has
            dependencies on node builtins like 'domain'. The problem is that
            browserify declares a require() method, which does not play nice
            inside of ReactJava Native which also has a require() method. Switching
            to webpack resolved this because they name their require()
            differently, __webpack_require() and this allows the processed
            version to work correctly inside of ReactJava Native.

            Also, from

            "https://webpack.js.org/guides/development/"
            "#adjusting-your-text-editor",

            When using automatic compilation of your code, you could run into
            issues when saving your files. Some editors have a "safe write"
            feature that can potentially interfere with recompilation.
            JetBrains IDEs (e.g. IntelliJ): Uncheck "Use safe write" in
            Preferences > Appearance & Behavior > System Settings.

                                                                              */
//------------------------------------------------------------------------------
public static String generateInjectScript(
   IConfiguration configuration,
   TreeLogger     logger)
   throws         Exception
{
   if (logger != null)
   {
      logger.log(
         TreeLogger.Type.INFO,
         "ReactCodePackager.generateInjectScript(): entered");
   }

   String injectScript = generateInjectScriptBrowserify(configuration, logger);

   if (logger != null)
   {
      logger.log(
         TreeLogger.Type.INFO,
         "ReactCodePackager.generateInjectScript(): exiting");
   }
   return(injectScript);
}
/*------------------------------------------------------------------------------

@name       generateInjectScriptBrowserify - generate browserify inject script
                                                                              */
                                                                             /**
            Generate browserify inject script.

@return     browserify inject script file

@param      logger      logger

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
            Note: requires the following node modules be installed:

               npm i browserify
               npm i envify
               npm i uglify

            and if kSRCCFG_TREE_SHAKE

               npm i common-shakeify

            Example command line which generates a UMD bundle for the supplied
            export name (ReactJava). This bundle works with other module
            systems and sets the name given as a window global if no module
            system is found.

            "browserify HelloWorldReactJava.nocache.js "
            "HelloWorldReactJava.devmode.js "
            "B198F197F8CB44A1F19CFF81CBFEBB94.cache.js -o bundle.js "
            "-s ReactJava -r @material-ui/core/Button -r react -r react-dom "
            "-r rxjs/Observable -r rxjs/Subscriber -r rxjs/Subscription"

            For information on how to use browserified modules with
            react-native, see:

            "https://hackernoon.com/using-core-node-js-modules-in-"
            "react-native-apps-64acd4d07140
                                                                              */
//------------------------------------------------------------------------------
public static String generateInjectScriptBrowserify(
   IConfiguration configuration,
   TreeLogger     logger)
   throws         Exception
{
   long    entry           = System.currentTimeMillis();
   boolean bProductionMode = configuration.getProductionMode();

   logger.log(
      logger.INFO,
      "ReactCodePackager.generateInjectScriptBrowserify(): "
    + "entered, productionMode=" + bProductionMode);

                                       // RxJs is currently restricted to     //
                                       // version 5.5.11 (npm i rxjs@5.5.11)  //
                                       // since the current jsinterop support //
                                       // (com.github.timofeevda.core.rxjs)   //
                                       // is at that version; the support is  //
                                       // now available for version 6 so      //
                                       // update both when time is available. //
                                       // note that files of the released     //
                                       // support package have been changed   //
                                       // for compatibility with the ReactJava//
                                       // bundle namespace = "Rx"             //
                                       //    -> namespace = "ReactJava"       //

                                       // generate main.js                    //
   File   projectDir = IConfiguration.getProjectDirectory(null, logger);
   String mainJsName = "main.js";

   generateInjectScriptBrowserifyMainJs(
      configuration, projectDir, mainJsName, logger);

                                       // create app bundle with browserify   //
   String bundleName       = "appbundle.js";
   File   injectScriptFile = new File(projectDir, bundleName);
   String prettyName       = "appbundle.pretty.js";
   File   prettyScriptFile = new File(projectDir, prettyName);

   if (injectScriptFile.exists())
   {
      injectScriptFile.delete();
   }
   if (prettyScriptFile.exists())
   {
      prettyScriptFile.delete();
   }

   Map<String,String> environment = new HashMap<>();
   List<String>       commands    = new ArrayList<>();

                                       // add browserify command              //
   generateInjectScriptBrowserifyMakeBrowserifyCommand(
      environment, commands, bundleName, mainJsName, bProductionMode, logger);

   if (bProductionMode)
   {
                                       // rename inject file                  //
      injectScriptFile.renameTo(prettyScriptFile);
      injectScriptFile = new File(projectDir, bundleName);

                                       // add uglify command                  //
      generateInjectScriptBrowserifyMakeUglifyCommand(
         commands, bundleName, prettyName, logger);
   }

   if (commands.size() > 0)
   {
                                       // run commands                        //
      String browserifyCommand = "";
      for (String command : commands)
      {
         if (browserifyCommand.length() > 0)
         {
            browserifyCommand += " ";
         }
         browserifyCommand += command;
      }

      logger.log(
         logger.INFO,
         "ReactCodePackager.generateInjectScriptBrowserify(): "
        + "browserify command=" + browserifyCommand);

      generateInjectScriptBrowserifyRunCommands(
         environment, commands, projectDir, logger);

      if (!injectScriptFile.exists())
      {
         throw new IllegalStateException("Inject file was not generated");
      }
   }

   String injectScript = IJSXTransform.getFileAsString(injectScriptFile, logger);
   logger.log(
      logger.INFO,
      "ReactCodePackager.generateInjectScriptBrowserify(): injectScript length="
         + injectScript.length());
   //main.delete();

   logger.log(
      logger.INFO,
      "ReactCodePackager.generateInjectScriptBrowserify(): exiting after "
    + (System.currentTimeMillis() - entry) + " msec");

   return(injectScript);
}
/*------------------------------------------------------------------------------

@name       generateInjectScriptBrowserifyMainJs - generate main.js
                                                                              */
                                                                             /**
            Generate browserify inject script.

@return     browserify inject script file

@param      projectDir        project directory
@param      mainJsName        main.js name
@param      logger            logger

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File generateInjectScriptBrowserifyMainJs(
   IConfiguration configuration,
   File           projectDir,
   String         mainJsName,
   TreeLogger     logger)
   throws         Exception
{
   String script = "";
   String export =
      "module.exports = \n{\n"
    + "   React:React,\n"
    + "   ReactComponent:React.Component,\n"
    + "   Fragment:React.Fragment,\n"
    + "   ReactDOM:ReactDOM,\n"
    + "   getType:_getType";

   if (kSRCCFG_USE_REQUIRE)
   {
      script += "var React    = require('react');\n";
      script += "var ReactDOM = require('react-dom');\n";
   }
   else
   {
      script += "import React    from 'react';\n";
      script += "import ReactDOM from 'react-dom';\n";
   }

   script += "var _getType = function(type){return module.exports[type]};\n";

   String nodeModulesDirPath =
      IConfiguration.getNodeModulesDir(logger).getAbsolutePath();

   for (String module : TypeDsc.getAllImportedNodeModules(logger))
   {
      if (module.endsWith(".css"))
      {
         continue;
      }

      logger.log(
         logger.INFO,
         "ReactCodePackager.generateInjectScriptBrowserifyMainJs(): "
       + "adding module=" + module);
                                       // get the require string              //
                                       // and the export target               //
      //String item   = module.substring(module.lastIndexOf('.') + 1);
      //       item   = IConfiguration.toReactAttributeName(item, logger);
      //String source = module.replace(".","/");

                                       // split off any 'export as' spec      //
      String[] splits = module.split(";");
      if (splits.length > 2)
      {
         throw new IllegalArgumentException(
            "Syntax error: too many ';' characters: " + module);
      }
      module = splits[0];
                                       // get the require string              //
                                       // and the export target               //
      int    delim   = '/';
      String itemRaw = module.substring(module.lastIndexOf(delim) + 1);
      if (!itemRaw.endsWith(".js"))
      {
         continue;
      }
      itemRaw = itemRaw.substring(0, itemRaw.length() - 3);

      String item   = IConfiguration.toReactAttributeName(itemRaw, logger);
      String source = module;
      if (source.startsWith(nodeModulesDirPath))
      {
         source = source.substring(nodeModulesDirPath.length() + 1);
      }
                                       // handle any explicit specification of//
                                       // the export item                     //
      String exportAs = splits.length > 1 ? splits[1] : item;

      logger.log(
         logger.INFO,
         "ReactCodePackager.generateInjectScriptBrowserifyMainJs(): "
       + "exportAs=" + exportAs + " (itemRaw="   + itemRaw   + "), "
       + "source="   + source   + ")");

      String scriptAdd;
      if (kSRCCFG_USE_REQUIRE)
      {
         scriptAdd = "var " + exportAs + " = require('" + source + "');\n";
      }
      else
      {
         scriptAdd = "import " + exportAs + " from '" + source + "';\n";
      }

      String target = exportAs + ".default || " + exportAs + "." + exportAs + " || " + exportAs;
      String exportAdd = ",\n   " + exportAs + ":" + target;

      script += scriptAdd;
      export += exportAdd;
   }

   export += "\n};\n";
   script += export;

   logger.log(
      logger.INFO,
      "ReactCodePackager.generateInjectScriptBrowserifyMainJs(): main.js=\n"
         + script);
                                       // the current working directory is not//
                                       // necessarily the project directory   //
                                       // and it is not possible to change it //
                                       // without a lot of native code and    //
                                       // other fiddling so save it explicitly//
                                       // to the project directory and then   //
                                       // exec with the wd the project dir... //
   File main = new File(projectDir, mainJsName);
   ByteArrayInputStream in = new ByteArrayInputStream(script.getBytes("UTF-8"));
   FileOutputStream out = new FileOutputStream(main);
   IConfiguration.fastChannelCopy(in, out, in.available());
   out.flush();
   out.close();

   return(main);
}
/*------------------------------------------------------------------------------

@name       generateInjectScriptBrowserifyMakeBrowserifyCommand
                                                                              */
                                                                             /**
            Generate browserify commands, for example:

            node_modules/.bin/browserify main.js -s ReactJava -o appbundle.js

@param      environment       environment
@param      commands          list of commands
@param      bundleName        bundle name
@param      mainJsName        main.js name
@param      logger            logger

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void generateInjectScriptBrowserifyMakeBrowserifyCommand(
   Map<String,String> environment,
   List<String>       commands,
   String             bundleName,
   String             mainJsName,
   boolean            bProductionMode,
   TreeLogger         logger)
{
   String envify =
      ".bin/envify" + (IConfiguration.getOSWindows() ? ".cmd" : "");

   File executableFile =
      new File(IConfiguration.getNodeModulesDir(logger), envify);

   if (!executableFile.exists())
   {
      String msg =
         "Cannot find executable. "
       + "Did you forget to install envify? (npm i envify)";

      logger.log(
         logger.ERROR,
         "ReactCodePackager"
       + ".generateInjectScriptBrowserifyMakeBrowserifyCommand(): " + msg);

      throw new IllegalStateException(msg);
   }

   String namespace  = "ReactJava";
   String browserify =
      ".bin/browserify" + (IConfiguration.getOSWindows() ? ".cmd" : "");

   executableFile =
      new File(IConfiguration.getNodeModulesDir(logger), browserify);

   if (!executableFile.exists())
   {
      String msg =
         "Cannot find executable. "
       + "Did you forget to install browserify? (npm i browserify)";

      logger.log(
         logger.ERROR,
         "ReactCodePackager"
       + ".generateInjectScriptBrowserifyMakeBrowserifyCommand(): " + msg);

      throw new IllegalStateException(msg);
   }

   String executable = executableFile.getAbsolutePath();
   commands.add(executable);

   if (kSRCCFG_TREE_SHAKE)
   {
                                       // tree shake                          //
      commands.add("-p");
      commands.add("common-shakeify");
   }

   commands.add(mainJsName);
   commands.add("-s");
   commands.add(namespace);
                                       // use brfs plugin
   //commands.add("-t");
   //commands.add("brfs");

   if (bProductionMode)
   {
      environment.put("NODE_ENV", "production");

                                       // envify                              //
      commands.add("-t");
      commands.add("envify");
   }

   commands.add("-o");
   commands.add(bundleName);
}
/*------------------------------------------------------------------------------

@name       generateInjectScriptBrowserifyMakeUglifyCommand
                                                                              */
                                                                             /**
            Generate uglify commands, for example:

            node_modules/.bin/uglifyjs "appbundle.pretty.js" -cm -o appbundle.js

@param      commands          list of commands
@param      bundleName        bundle name
@param      prettyName        pretty name
@param      logger            logger

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void generateInjectScriptBrowserifyMakeUglifyCommand(
   List<String>       commands,
   String             bundleName,
   String             prettyName,
   TreeLogger         logger)
{
   String uglify =
      ".bin/uglifyjs" + (IConfiguration.getOSWindows() ? ".cmd" : "");

   File executableFile =
      new File(IConfiguration.getNodeModulesDir(logger), uglify);

   if (!executableFile.exists())
   {
      String msg =
         "ReactCodePackager.generateInjectScriptBrowserifyMakeUglifyCommand(): "
       + "cannot find executable. "
       + "Did you forget to install uglifyjs? (npm i uglifyjs)";

      logger.log(logger.ERROR, msg);

      throw new IllegalStateException(msg);
   }

   String executable = executableFile.getAbsolutePath();

   commands.add(executable);
   commands.add(prettyName);
   commands.add("-cm");
   commands.add("-o");
   commands.add(bundleName);
}
/*------------------------------------------------------------------------------

@name       generateInjectScriptBrowserifyRunCommands - run commands
                                                                              */
                                                                             /**
            Generate browserify inject script.

@param      environment       environment
@param      commands          list of commands
@param      projectDir        project directory
@param      logger            logger

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void generateInjectScriptBrowserifyRunCommands(
   Map<String,String> environment,
   List<String>       commands,
   File               projectDir,
   TreeLogger         logger)
   throws             Exception
{
   String command = "";
   for (String token : commands)
   {
      command += token + " ";
   }

   logger.log(
      logger.INFO,
      "ReactCodePackager.generateInjectScriptBrowserifyRunCommands(): \nwd="
    + projectDir.getAbsolutePath()
    + "\ncommand=" + command + "\n");

   File errOut = File.createTempFile("err", ".tmp");

   ProcessBuilder pb =
      new ProcessBuilder(commands)
         .directory(projectDir)
         .redirectError(errOut);

   if (environment.size() > 0)
   {
      pb.environment().putAll(environment);
   }

   long    start = System.currentTimeMillis();
   Process p     = pb.start();
   int     cc    = p.waitFor();

   logger.log(
      logger.INFO,
      "ReactCodePackager.generateInjectScriptBrowserifyRunCommands(): "
    + "completed in "
    + (System.currentTimeMillis() - start) + " ms.");

   if (cc != 0)
   {
      String err = JavascriptBundler.getFileAsString(errOut);
      String msg =
         "ReactCodePackager.generateInjectScriptBrowserifyRunCommands(): "
       + "Process returned an error."
       + "\nDid you forget to install any of react, react-dom, "
       + "or rxjs@5.5.11 (please note version of rxjs)?"
       + "\n" + err;

      logger.log(logger.ERROR, msg);

      errOut.delete();

      throw new IllegalStateException(msg);
   }
}
/*------------------------------------------------------------------------------

@name       generateHashRedirect - generate redirect for specified hash
                                                                              */
                                                                             /**
            Generate redirect for specified hash.

@param      defaultName    default page name
@param      pageHash       page hash
@param      logger         logger

@history    Sun Jun 16, 2019 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void generateHashRedirect(
   String     defaultName,
   String     pageHash,
   TreeLogger logger)
   throws     Exception
{
   if (pageHash != null && pageHash.length() > 0)
   {
      String pageHashCap =
         pageHash.substring(0,1).toUpperCase() + pageHash.substring(1);

      String redirectName = defaultName + pageHashCap;

      String content =
         kREDIRECT_HTML
            .replace("%redirectName%", redirectName)
            .replace("%defaultName%",  defaultName)
            .replace("%hash%",         pageHash);

      copyHTMLContentToWAR(redirectName + ".html", content, logger);
   }
}
/*------------------------------------------------------------------------------

@name       generateSitemapItem - generate sitemap
                                                                              */
                                                                             /**
            Generate sitemap.

@param      moduleName     module name
@param      content        sitemap content
@param      deployPath     deploy path
@param      pageHash       page hash
@param      logger         logger

@history    Sun Jun 16, 2019 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected String generateSitemapItem(
   String     moduleName,
   String     content,
   String     deployPath,
   String     pageHash,
   TreeLogger logger)
   throws     Exception
{
   String pageHashCap =
      pageHash.length() > 0
         ? pageHash.substring(0,1).toUpperCase() + pageHash.substring(1)
         : pageHash;

   String itemPath = deployPath + "/" + moduleName + pageHashCap + ".html";

   content += kSITEMAP_ITEM.replace("%itemPath%", itemPath);
   return(content);
}
/*------------------------------------------------------------------------------

@name       getConfiguration - get configuration
                                                                              */
                                                                             /**
            This implementation simply instantiates a default configuration.

            This implementation should be replaced to generate a configuration
            by examining the specified context.

@return     The configuration.

@param      logger     logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected IConfiguration getConfiguration(
   TreeLogger logger)
{
   return(new Configuration(logger));
}
/*------------------------------------------------------------------------------

@name       getDependenciesChanged - get previous map of script dependencies
                                                                              */
                                                                             /**
            Get previous map of script dependencies.

@return     previous map of script dependencies

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean getDependenciesChanged()
{
   boolean bChanged =
      IConfiguration.getDependenciesSet().size()
         != IConfiguration.getDependenciesSetPrevious().size();

   if (!bChanged)
   {
      for (String dependency : IConfiguration.getDependenciesSet())
      {
         if (!IConfiguration.getDependenciesSetPrevious().contains(dependency))
         {
            bChanged = true;
            break;
         }
      }
   }
   return(bChanged);
}
/*------------------------------------------------------------------------------

@name       getHTMLFilenameForModule - get html filename for module
                                                                              */
                                                                             /**
            Get html filename for module.

@return     html filename for module

@param      moduleName     module name
@param      logger         logger

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getHTMLFilenameForModule(
   String     moduleName,
   TreeLogger logger)
   throws     Exception
{
   String htmlFilename = null;
   File[] htmlFiles =
      IConfiguration.getWarDir(logger).listFiles(new FilenameFilter()
      {
         public boolean accept(
            File   dir,
            String name)
         {
            return name.endsWith(".html");
         }
      });

   String token = moduleName + ".nocache.js";
   for (File htmlFile : htmlFiles)
   {
      if (JavascriptBundler.getFileAsString(htmlFile).contains(token))
      {
         htmlFilename = htmlFile.getName();
         break;
      }
   }
   if (htmlFilename == null)
   {
      throw new IllegalStateException(
         "HTML file for module " + moduleName + " not found");
   }

   return(htmlFilename);
}
/*------------------------------------------------------------------------------

@name       getReactJavaJar - get any reactjava jar file
                                                                              */
                                                                             /**
            Get any reactjava jar file

@return     reactjava jar file or null if not found.

@param      logger         logger

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public JarFile getReactJavaJar(
   TreeLogger logger)
   throws     Exception
{
   JarFile reactJavaJar = null;
   File    candidate    = null;

   File    libDir       = IConfiguration.getWarLibraryDir(logger);
   if (libDir != null)
   {
      candidate = new File(libDir, "reactjava.jar");
      if (candidate.exists())
      {
         reactJavaJar = new JarFile(candidate);
      }
   }

   logger.log(
      logger.DEBUG,
      "ReactJavaCodePackager.getReactJavaJar(): reactjava.jar "
         + (reactJavaJar != null
               ? "found at " + candidate.getAbsolutePath()
               : "not found" ));

   return(reactJavaJar);
}
///*------------------------------------------------------------------------------
//
//@name       handleAppInfo - get imported node modules
//                                                                              */
//                                                                             /**
//            Get any imported node modules from the application class
//            implementation, adding them to the dependencies set, as well as any
//            page hashes.
//
//@param      typesMap          map of component type by classname
//@param      configuration     configuration
//@param      module            module
//@param      logger            logger
//
//@history    Sun Dec 02, 2018 18:00:00 (LBM) created.
//
//@notes
//                                                                              */
////------------------------------------------------------------------------------
//protected void handleAppInfo(
//   Map<String,Map<String,JClassType>> typesMap,
//   ModuleDef                          module,
//   IConfiguration                     configuration,
//   TreeLogger                         logger)
//   throws                             Exception
//{
//   Map<String,JClassType> components = typesMap.get(kKEY_COMPONENTS);
//
//   JType  appType      = getAppType(components, logger);
//   String appClassname = getAppClassname(appType, logger);
//
//   if (appClassname != null
//         && !appClassname.equals(AppComponentTemplate.class.getName()))
//   {
//      logger.log(
//         TreeLogger.INFO,
//         "ReactCodePackager.handleAppInfo(): "
//       + "checking for " + appClassname);
//
//                                       // get the component imported modules  //
//      Map<String,JClassType> appTypes = typesMap.get(kKEY_APP_TYPES);
//
////importedNodeModulesStart//
//// AppComponentInspector inspector =
////         AppComponentInspector
////            .newInstance(appClassname, appTypes, logger);
////
////      handleImportedNodeModules(
////         inspector.getParsedImportedNodeModules(logger), configuration, logger);
//
//      //handleEmbeddedResources(
//      //   inspector.getParsedEmbeddedResources(logger), configuration, logger);
//
//      //handleGoogleAnalyticsId(
//      //   inspector.getParsedAnalyticsId(logger), module, logger);
//      //
//      //handleSEOInfo(
//      //   inspector.getParsedSEOInfo(logger), module, logger);
//   }
//}
/*------------------------------------------------------------------------------

@name       handleGoogleAnalyticsId - handle google analytics id
                                                                              */
                                                                             /**
            Handle google analytics id.

@param      analyticsId    parsed analytics id
@param      module         module
@param      logger         logger

@history    Sun Dec 02, 2018 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void handleGoogleAnalyticsId(
   String      analyticsId,
   ModuleDef   module,
   TreeLogger  logger)
   throws      Exception
{
   logger.log(
      TreeLogger.INFO,
      "ReactCodePackager.handleGoogleAnalyticsId(): analyticsId=" + analyticsId);
}
/*------------------------------------------------------------------------------

@name       handleSEOInfo - handle page hashes
                                                                              */
                                                                             /**
            Handle collection of app page hashes for SEO support.Creates a
            redirect target for each hash, along with an associated sitemap.

@param      module            module
@param      logger            logger

@history    Sun Dec 02, 2018 18:00:00 (LBM) created.
            Sun Sep 27, 2020 18:00:00 (LBM) redone.

@notes      see https://developers.google.com/search/docs/guides/intro-structured-data
                                                                              */
//------------------------------------------------------------------------------
protected void handleSEOInfo(
   ModuleDef   module,
   TreeLogger  logger)
   throws      Exception
{
   JSXSEOInfo seoInfo = null;

   TypeDsc app = TypeDsc.getTargetApp(logger);
   if (app != null)
   {
      seoInfo = app.getComponentSEOInfo(logger);
   }
   if (seoInfo == null || seoInfo.getPageInfos() == null)
   {
      logger.log(TreeLogger.INFO, "handleSEOInfo(): no SEO requested");
   }
   else if (seoInfo.getPageInfos().length == 0)
   {
      logger.log(TreeLogger.INFO, "handleSEOInfo(): no page infos specified");
   }
   else
   {
      String sitemapContent  = kSITEMAP_HDR;
      String defaultHTMLName = getHTMLFilenameForModule(module.getName(),logger);
             defaultHTMLName =
               defaultHTMLName.substring(0, defaultHTMLName.length() - 5);

      String deployPath = seoInfo.getDeployPath();

      logger.log(
         TreeLogger.INFO,
         "handleSEOInfo(): "
       + "deployPath=" + deployPath + ", htmlName=" + defaultHTMLName);

      for (String[] pageInfo : seoInfo.getPageInfos())
      {
         String pageHash = pageInfo[SEOInfo.kIDX_PAGEINFO_PAGE_HASH];
         generateHashRedirect(defaultHTMLName, pageHash, logger);

         sitemapContent =
            generateSitemapItem(
               defaultHTMLName, sitemapContent, deployPath, pageHash, logger);
      }

      sitemapContent += kSITEMAP_FTR;
      copySitemapContentToWAR(defaultHTMLName, sitemapContent, logger);
      copyRobotsContentToWAR(deployPath, defaultHTMLName, logger);
   }
}
/*------------------------------------------------------------------------------

@name       injectScriptAppDirty - test if app inject script dirty
                                                                              */
                                                                             /**
            Test whether app inject script does not exist or has changed.

@return     true iff app inject script does not exist or has changed.

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean injectScriptAppDirty(
   TreeLogger logger)
{
   boolean bCreate = true;
   try
   {
      IConfiguration.getInjectScript(logger);
      bCreate = getDependenciesChanged();
   }
   catch(FileNotFoundException e)
   {
                                       // ignore                              //
   }

   logger.log(
      TreeLogger.Type.INFO,
      "injectScriptAppDirty(): "
    + (bCreate ? "new script required" : "app script has not changed"));

   return(bCreate);
}
/*------------------------------------------------------------------------------

@name       main - standard main routine
                                                                              */
                                                                             /**
            Standard main routine.

@param      args     args[0] - platform specification

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void main(
   String[] args)
{
   try
   {
      System.out.println(new Date());
      if (args.length < 1)
      {
         throw new IllegalArgumentException("Num args=" + args.length);
      }

      String opcodeLC = args[0].toLowerCase();

      if ("testcodegenerator".equals(opcodeLC))
      {
         File src = IConfiguration.getProjectDirectory("src", null);
         src = src;
      }
      else if ("generateInjectScriptBrowserify".toLowerCase().equals(opcodeLC))
      {
         TreeLogger logger = IPostprocessor.getFileLogger(null);
         Configuration configuration = new Configuration(logger);
         configuration.setProductionMode(false);

         String sProjectDir =
            "/Users/brianm/working/IdeaProjects/ReactJava/ReactJavaExamples";

         File projectDir = new File(sProjectDir);

         String sNodeModulesDir = sProjectDir + "/node_modules/";

         List<String> dependenciesSet =
            new ArrayList<>(Arrays.asList(
               //"pdfjs-dist",
               //"pdfjs-dist/web/pdf_viewer",
               //"@material-ui.core.Grid",
               "@material-ui.icons.Menu"));

         Map<String,String> dependencies = new HashMap<String,String>()
         {{
            //put(
            //   "pdfjs-dist",
            //   sNodeModulesDir + "pdfjs-dist/build/pdf.js");
            //put(
            //   "pdfjs-dist/web/pdf_viewer",
            //   sNodeModulesDir + "pdfjs-dist/web/pdf_viewer.js");
            //put(
            //   "@material-ui.core.Grid",
            //   sNodeModulesDir + "@material-ui/core/Grid/Grid.js");
            put(
               "@material-ui.icons.Menu",
               sNodeModulesDir + "@material-ui/icons/Menu/Menu.js");
         }};

         generateInjectScriptBrowserify(configuration, logger);
      }
      else if ("handleImportedNodeModulesAndSEO".toLowerCase().equals(opcodeLC))
      {
         TreeLogger logger = IPostprocessor.getFileLogger(null);
         Configuration configuration = new Configuration(logger);
         configuration.setProductionMode(false);

         //String s1 = "import blah blah";
         //String s2 = "export blah blah";
         //
         //String[] splits1a = s1.split(IConfiguration.kREGEX_IMPORT);
         //String[] splits1b = s1.split(IConfiguration.kREGEX_EXPORT);
         //String[] splits2a = s2.split(IConfiguration.kREGEX_IMPORT);
         //String[] splits2b = s2.split(IConfiguration.kREGEX_EXPORT);

         String importedModulesAndSEO =
            "[<delimiter>, "
          //+ "pdfjs-dist, "
          //+ "pdfjs-dist/web/pdf_viewer, "
          //+ "@material-ui.core.Grid, "
          //+ "@material-ui.core.CardContent, "
          + "@material-ui.icons.Menu, "
          + "rxjs.Observable"
          + "<delimiter>, "
          + "<SEOInfo>]";

         //new ReactCodePackager().handleAppInfo(
            //importedModulesAndSEO, configuration, logger);
      }
      else
      {
         throw new IllegalArgumentException(args[0]);
      }
   }
   catch(Exception e)
   {
      e.printStackTrace();
   }
}
/*------------------------------------------------------------------------------

@name       process - process specified source
                                                                              */
                                                                             /**
            Process specified source.

@param      logger                  logger
@param      compilerContext         compiler context
@param      precompilationContext   precompilation context
@param      bUpdate                 iff true, this is an update of an
                                    incremental build

@history    Sun Mar 16, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void process(
   TreeLogger            logger,
   CompilerContext       compilerContext,
   PrecompilationContext precompilationContext,
   boolean               bUpdate)
   throws                Exception
{
   long start = System.nanoTime();
   logger.log(logger.DEBUG, "ReactCodePackager.process(): entered");

   generateBootJs(logger);
                                       // copy all scripts and css to artifact//
   IConfiguration configuration = getConfiguration(logger);

                                       // handle SEO info                     //
   handleSEOInfo(compilerContext.getModule(), logger);

   GeneratorContext context =
      precompilationContext.getRebindPermutationOracle().getGeneratorContext();

   copyResources(configuration, context, logger);

   logger.log(
      logger.INFO,
      "process(): exiting, total ms=" + (System.nanoTime() - start) / 1000000L);
}
/*------------------------------------------------------------------------------

@name       setPlatform - set platform
                                                                              */
                                                                             /**
            Set platform. This method is for development and test purposes.

@param      os    platform os

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void setPlatform(
   String os)
{
   if (!IPlatform.kPLATFORMS.contains(os))
   {
      throw new IllegalArgumentException(os + " is unknown os");
   }

   //platform =
   //   IPlatform.kPLATFORM_WEB.equals(os)
   //      ? new PlatformWeb()
   //      : IPlatform.kPLATFORM_IOS.equals(os)
   //         ? new PlatformIOS()
   //         : new PlatformAndroid();

   throw new UnsupportedOperationException();
}
/*------------------------------------------------------------------------------

@name       toDeflaterFiltered - composite device data record to byte array
                                                                              */
                                                                             /**
            Convert device data record to compressed byte array using filtered
            Deflator.

            This method is not located in Utilities since Utilities must also
            be compilable in GWT.


@return     byte array representation of compressed device data record.

@param      uncompressed     uncompressed bytes

@history    Fri May 20, 2016 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static byte[] toDeflaterFiltered(
   byte[] uncompressed)
{
   byte[] bytes = new byte[0];
   if (uncompressed != null && uncompressed.length > 0)
   {
                                       // avoid using input and output streams//
                                       // to allow GWT compatibility          //
      Deflater deflater = new Deflater();
                                       // must set strategy before input...   //
      deflater.setStrategy(Deflater.FILTERED);
      deflater.setInput(uncompressed);
      deflater.finish();

      int    total      = 0;
      byte[] compressed = new byte[Math.max(uncompressed.length, 1000)];
      byte[] buffer     = new byte[uncompressed.length];

      for (int cnt = 0, off = 0; !deflater.finished(); off += cnt, total += cnt)
      {
         cnt = deflater.deflate(buffer);
         System.arraycopy(buffer, 0, compressed, off, cnt);
      }
      deflater.end();
                                       // compressing can make it bigger but  //
                                       // need to compress it anyway since    //
                                       // otherwise subsequent decompress     //
                                       // will fail                           //
      bytes = new byte[total];
      System.arraycopy(compressed, 0, bytes, 0, total);
   }

   return(bytes);
}
/*==============================================================================

name:       Configuration - configuration

purpose:    JsInterop definition for access to javascript onLoad handler

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Configuration
   extends Properties implements IConfiguration
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Configuration - default constructor
                                                                              */
                                                                             /**
            Default constructor

@param      logger      logger

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Configuration(
   TreeLogger logger)
{
   super();
   putAll(kCONFIGURATION_DEFAULT_SANS_THEME);

   IPlatform platform = IConfiguration.getPlatform(logger);
   if (platform != null)
   {
      Map<String,String> providers = getProviders();
      String             os        = platform.getOS();

      providers.put(
         IPlatform.class.getName(),
         IPlatform.kPLATFORM_WEB.equals(os)
            ? PlatformWeb.class.getName()
            : IPlatform.kPLATFORM_IOS.equals(os)
               ? PlatformIOS.class.getName()
               : PlatformAndroid.class.getName());
   }

   String productionMode = System.getProperty("productionMode");
   if (productionMode != null)
   {
      setProductionMode("true".equals(productionMode.toLowerCase()));
   }
}
/*------------------------------------------------------------------------------

@name       getApp - get app
                                                                              */
                                                                             /**
            Get app.

@return     app

@history    Mon Dec 23, 2019 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public AppComponentTemplate getApp()
{
   return((AppComponentTemplate)get(kKEY_APP));
}
/*------------------------------------------------------------------------------

@name       getGlobalCSS - get global css
                                                                              */
                                                                             /**
            Get global css.

@return     global css

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public Collection<String> getGlobalCSS()
{
   Collection<String> globalCSS = (Collection<String>)get(kKEY_GLOBAL_CSS);
   return(globalCSS);
}
/*------------------------------------------------------------------------------

@name       getGlobalImages - get global images
                                                                              */
                                                                             /**
            Get global images.

@return     global images

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public Collection<String> getGlobalImages()
{
   Collection<String> globalCSS = (Collection<String>)get(kKEY_GLOBAL_IMAGES);
   return(globalCSS);
}
/*------------------------------------------------------------------------------

@name       getBundleScripts - get bundle scripts
                                                                              */
                                                                             /**
            Get bundle scripts.

@return     bundle scripts

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public Collection<String> getBundleScripts()
{
  return((Collection<String>)get(kKEY_BUNDLE_SCRIPTS));
}
/*------------------------------------------------------------------------------

@name       getCloudServicesConfig - get cloud services config
                                                                              */
                                                                             /**
            Get cloud services config.

@return     cloud services config.

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ICloudServices getCloudServicesConfig()
{
   return((ICloudServices)get(kKEY_CLOUD_SERVICES_CONFIG));
}
/*------------------------------------------------------------------------------

@name       getImportedNodeModules - get imported node modules
                                                                              */
                                                                             /**
            Get imported node modules.

@history    Sun Dec 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Collection<String> getImportedNodeModules()
{
  return((Collection<String>)get(kKEY_IMPORTED_NODE_MODULES));
}
/*------------------------------------------------------------------------------

@name       getName - get name
                                                                              */
                                                                             /**
            Get name.

@return     name

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getName()
{
  return(getString(kKEY_CONFIGURATION_NAME));
}
/*------------------------------------------------------------------------------

@name       getNavRoutes - get nav routes
                                                                              */
                                                                             /**
            Get nav routes.

@return     nav routes

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public Map<String,Class> getNavRoutes()
{
  return((Map<String,Class>)get(kKEY_NAV_ROUTES));
}
/*------------------------------------------------------------------------------

@name       getNavRoutesNested - get any nested nav routes
                                                                              */
                                                                             /**
            Get any nested nav routes.

@return     any nested nav routes

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public Map<String,Class> getNavRoutesNested()
{
  return((Map<String,Class>)get(kKEY_NAV_ROUTES_NESTED));
}
/*------------------------------------------------------------------------------

@name       getProductionMode - get production mode
                                                                              */
                                                                             /**
            Get production mode.

@return     production mode

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean getProductionMode()
{
   boolean bProductionMode = getBoolean(kKEY_PRODUCTION_MODE);
   return(bProductionMode);
}
/*------------------------------------------------------------------------------

@name       getProvider - get provider for specified interface
                                                                              */
                                                                             /**
            Get provider for specified interface.

@return     provider for specified interface

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getProvider(
   String interfaceClassname)
{
   return(getProviders().get(interfaceClassname));
}
/*------------------------------------------------------------------------------

@name       getProviders - get map of provider interface to provider impl
                                                                              */
                                                                             /**
            Get map of provider interface to provider implementation.

@return     Map of provider interface to provider implementation

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public Map<String,String> getProviders()
{
   Map<String,String> providers = (Map<String,String>)get(kKEY_PROVIDERS);
   return(providers);
}
/*------------------------------------------------------------------------------

@name       getRequiredPlatformImports - get required platform imports
                                                                              */
                                                                             /**
            Get required platform imports.

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Collection<String> getRequiredPlatformImports()
{
   Collection<String> requiredPlatformImports =
      (Collection<String>)get(kKEY_REQUIRED_PLATFORM_IMPORTS);

   return(requiredPlatformImports);
}
/*------------------------------------------------------------------------------

@name       getRequiredPlatformScripts - get required platform scripts
                                                                              */
                                                                             /**
            Get required platform scripts.

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Collection<String> getRequiredPlatformScripts()
{
   Collection<String> requiredPlatformScripts =
      (Collection<String>)get(kKEY_REQUIRED_PLATFORM_SCRIPTS);

   return(requiredPlatformScripts);
}
/*------------------------------------------------------------------------------

@name       getScriptsCompressed - get whether scripts are compressed
                                                                              */
                                                                             /**
            Get whether scripts are compressed.

@return     true iff scripts are compressed

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public boolean getScriptsCompressed()
{
   boolean bScriptsCompressed = getBoolean(kKEY_SCRIPTS_COMPRESSED);
   return(bScriptsCompressed);
}
/*------------------------------------------------------------------------------

@name       getScriptsLoadLazy - get whether javascripts load lazy
                                                                              */
                                                                             /**
            Get whether javascripts load lazy. Lazily loaded leaves the scripts
            on the server which are downloaded on demand; preloaded are embedded
            within a single compressed package and downloaded all at once.

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public boolean getScriptsLoadLazy()
{
   boolean bScriptsLoadLazy = getBoolean(kKEY_SCRIPTS_LOAD_LAZY);
   return(bScriptsLoadLazy);
}
/*------------------------------------------------------------------------------

@name       getSEOInfo - get seo info
                                                                              */
                                                                             /**
            Get seo info.

@return     seo info

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public SEOInfo getSEOInfo()
{
  return((SEOInfo)get(kKEY_SEO_INFO));
}
/*------------------------------------------------------------------------------

@name       getTagMap - get any custom tag map
                                                                              */
                                                                             /**
            Get any custom map of any replacement tag by standard tag. A tag is
            resolved by first checking whether there is a replacement in any
            custom tag map and if not, checking whether there is a replacement
            in the default tag map.

            For example, in the React configuration, there exists an entry
            for the tag "div" whose entry is
            "node_modules.react-native.Libraries.Components.View".

@return     tag map

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public Map<String,String> getTagMap()
{
  return((Map<String,String>)get(kKEY_TAG_MAP_CUSTOM));
}
/*------------------------------------------------------------------------------

@name       getTagMapDefault - get default tag map
                                                                              */
                                                                             /**
            Get default map of any replacement tag by standard tag. A tag is
            resolved by first checking whether there is a replacement in any
            custom tag map and if not, checking whether there is a replacement
            in the default tag map.

            For example, in the React configuration, there exists an entry
            for the tag "div" whose entry is
            "node_modules.react-native.Libraries.Components.View".

@return     tag map

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public Map<String,String> getTagMapDefault()
{
  return((Map<String,String>)get(kKEY_TAG_MAP_DEFAULT));
}
/*------------------------------------------------------------------------------

@name       getTheme - get default theme
                                                                              */
                                                                             /**
            Get default theme.

@return     default theme

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public IUITheme getTheme()
{
   return((IUITheme)get(kKEY_THEME));
}
/*------------------------------------------------------------------------------

@name       initialize - initialize
                                                                              */
                                                                             /**
            Initialize. This implementation is null.

@history    Mon Dec 23, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Observable<IConfiguration> initialize()
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       setApp - set app
                                                                              */
                                                                             /**
            Set app.

@return     this

@param      app      app

@history    Mon Dec 23, 2019 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public io.reactjava.client.core.react.IConfiguration setApp(
   AppComponentTemplate app)
{
   if (app != null)
   {
      set(kKEY_APP, app);
   }
   return(this);
}
/*------------------------------------------------------------------------------

@name       setBundleScripts - set bundle scripts
                                                                              */
                                                                             /**
            Set bundle scripts.

@return     this

@param      scripts     bundle scripts

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public io.reactjava.client.core.react.IConfiguration setBundleScripts(
   Collection<String> scripts)
{
   if (scripts != null)
   {
      set(kKEY_BUNDLE_SCRIPTS, scripts);
   }
   return(this);
}
/*------------------------------------------------------------------------------

@name       setGlobalCSS - set global css
                                                                              */
                                                                             /**
            Set global css.

@return     this configuration

@param      globalCSS      global css urls

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public io.reactjava.client.core.react.IConfiguration setGlobalCSS(
   Collection<String> globalCSS)
{
   if (globalCSS != null)
   {
      set(kKEY_GLOBAL_CSS, globalCSS);
   }
   return(this);
}
/*------------------------------------------------------------------------------

@name       setGlobalCSS - set global css
                                                                              */
                                                                             /**
            Set global css.

@return     void

@param      globalCSS      global css urls

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public io.reactjava.client.core.react.IConfiguration setGlobalCSS(
   String[] globalCSS)
{
   if (globalCSS != null)
   {
      setGlobalCSS(new ArrayList(Arrays.asList(globalCSS)));
   }
   return(this);
}
/*------------------------------------------------------------------------------

@name       setCloudServicesConfig - set cloud services config
                                                                              */
                                                                             /**
            Set cloud services config.

@return     this configuration

@param      cloudServicesConfig    cloud services config.

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public io.reactjava.client.core.react.IConfiguration setCloudServicesConfig(
   ICloudServices cloudServicesConfig)
{
   if (cloudServicesConfig != null)
   {
      set(kKEY_CLOUD_SERVICES_CONFIG, cloudServicesConfig);
   }
   return(this);
}
/*------------------------------------------------------------------------------

@name       setImportedNodeModules - set imported node modules
                                                                              */
                                                                             /**
            Set imported node modules.

@return     this

@param      modules     imported node modules

@history    Sun Dec 02, 2018 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public io.reactjava.client.core.react.IConfiguration setImportedNodeModules(
   Collection<String> modules)
{
   if (modules != null)
   {
      set(kKEY_IMPORTED_NODE_MODULES, modules);
   }
   return(this);
}
/*------------------------------------------------------------------------------

@name       setName - set name
                                                                              */
                                                                             /**
            Set name.

@return     void

@param      name     name

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public io.reactjava.client.core.react.IConfiguration setName(
   String name)
{
   if (name != null)
   {
      set(kKEY_CONFIGURATION_NAME, name);
   }
   return(this);
}
/*------------------------------------------------------------------------------

@name       setNavRoutes - set nav routes
                                                                              */
                                                                             /**
            Set nav routes.

@return     this

@param      navRoutes      nav routes

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public io.reactjava.client.core.react.IConfiguration setNavRoutes(Map<String,Class> navRoutes)
{
   set(kKEY_NAV_ROUTES, navRoutes);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setNavRoutesNested - set nested nav routes
                                                                              */
                                                                             /**
            Set nested nav routes.

@return     this

@param      navRoutesNested      nested nav routes

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public io.reactjava.client.core.react.IConfiguration setNavRoutesNested(Map<String,Class> navRoutesNested)
{
   set(kKEY_NAV_ROUTES_NESTED, navRoutesNested);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setProductionMode - set production mode
                                                                              */
                                                                             /**
            Set production mode.

@return     void

@param      bProductionMode      iff true, production mode; otherwise debug mode

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public io.reactjava.client.core.react.IConfiguration setProductionMode(
   boolean bProductionMode)
{
   set(kKEY_PRODUCTION_MODE, bProductionMode ? "true" : "false");
   return(this);
}
/*------------------------------------------------------------------------------

@name       setProvider - set provider for specified interface
                                                                              */
                                                                             /**
            Set provider for specified interface

@return     this

@param      interfaceClassname   interface classname
@param      providerClassname    provider classname

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public io.reactjava.client.core.react.IConfiguration setProvider(
   String interfaceClassname,
   String providerClassname)
{
   getProviders().put(interfaceClassname, providerClassname);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setProviders - set get map of provider interface to provider impl
                                                                              */
                                                                             /**
            Set map of provider interface to provider implementation.

@return     this

@param      providers   map of provider interface to provider implementation

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public io.reactjava.client.core.react.IConfiguration setProviders(
   Map<String,String> providers)
{
   set(kKEY_PROVIDERS, providers);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setScriptsLoadLazy - set whether scripts load lazy
                                                                              */
                                                                             /**
            Set whether scripts load lazy.

@return     void

@param      bScriptsLoadLazy     iff true, scripts load lazy

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public io.reactjava.client.core.react.IConfiguration setScriptsLoadLazy(
   boolean bScriptsLoadLazy)
{
   set(kKEY_SCRIPTS_LOAD_LAZY, bScriptsLoadLazy ? "true" : "false");
   return(this);
}
/*------------------------------------------------------------------------------

@name       setSEOInfo - set seo info
                                                                              */
                                                                             /**
            Set seo info.

@param      seoInfo     seo info

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public io.reactjava.client.core.react.IConfiguration setSEOInfo(
   SEOInfo seoInfo)
{
   set(kKEY_SEO_INFO, seoInfo);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setTagMap - assign a custom tag map
                                                                              */
                                                                             /**
            Assign a custom map of any replacement tag by standard tag. A tag is
            resolved by first checking whether there is a replacement in any
            custom tag map and if not, checking whether there is a replacement
            in the default tag map.

            For example, in the React configuration, there exists an entry
            for the tag "div" whose entry is "View".

@param      tagMap      tag map

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void setTagMap(
   Map<String,String> tagMap)
{
   set(kKEY_TAG_MAP_CUSTOM, tagMap);
}
/*------------------------------------------------------------------------------

@name       setTheme - set default theme
                                                                              */
                                                                             /**
            Set default theme.

@return     theme

@param      theme    new theme

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public IUITheme setTheme(
   IUITheme theme)
{
   set(kKEY_THEME, theme);
   return(theme);
}
/*------------------------------------------------------------------------------

@name       toString - override of standard toString method
                                                                              */
                                                                             /**
            Override of standard toString method

@return     string representation

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String toString()
{
   String representation = "";
   for (Object key : keySet())
   {
      representation += key + "=" + get(key) + ";\n";
   }

   return(representation);
}
}//====================================// end Configuration ==================//
/*==============================================================================

name:       Properties - properties

purpose:    JsInterop definition for access to javascript onLoad handler

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Properties extends HashMap
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       Properties - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Properties()
{
   super();
}
/*------------------------------------------------------------------------------

@name       get - get value of specified property
                                                                              */
                                                                             /**
            Get value of specified property

@return     value of specified property, or null if not found.

@param      propertyName      property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Object get(
   String propertyName)
{
   return(super.get(propertyName));
}
/*------------------------------------------------------------------------------

@name       getBoolean - get boolean value of specified property
                                                                              */
                                                                             /**
            Get boolean value of specified property

@return     boolean value of specified property, or null if not found.

@param      propertyName      property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean getBoolean(
   String propertyName)
{
   Object value = get(propertyName);
   if (value instanceof String)
   {
      value = "true".equals((String)value);
   }
   return((Boolean)value);
}
/*------------------------------------------------------------------------------

@name       getString - get string value of specified property
                                                                              */
                                                                             /**
            Get string value of specified property

@return     string value of specified property, or null if not found.

@param      propertyName      property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler getEventHandler(
   String propertyName)
{
   return((INativeEventHandler)get(propertyName));
}
/*------------------------------------------------------------------------------

@name       getInt - get int value of specified property
                                                                              */
                                                                             /**
            Get int value of specified property

@return     int value of specified property, or null if not found.

@param      propertyName      property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Integer getInt(
   String propertyName)
{
   Object value = get(propertyName);
   if (value instanceof String)
   {
      value = Integer.parseInt((String)value);
   }
   return((Integer)value);
}
/*------------------------------------------------------------------------------

@name       getString - get string value of specified property
                                                                              */
                                                                             /**
            Get string value of specified property

@return     string value of specified property, or null if not found.

@param      propertyName      property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getString(
   String propertyName)
{
   return((String)get(propertyName));
}
/*------------------------------------------------------------------------------

@name       set - set value of specified property
                                                                              */
                                                                             /**
            Set value of specified property

@param      propertyName      property name
@param      value             value of specified property

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final void set(
   String propertyName,
   Object value)
{
   super.put(propertyName, value);
}
}//====================================// end Properties =====================//
}//====================================// end ReactCodePackager ==============//
