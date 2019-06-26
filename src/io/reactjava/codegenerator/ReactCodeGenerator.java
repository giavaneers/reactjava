/*==============================================================================

name:       ReactJavaCodeGenerator.java

purpose:    GWT Compiler post processor to build application js bundle..

history:    Mon Aug 28, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.codegenerator;
                                       // imports --------------------------- //
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dev.CompilerContext;
import com.google.gwt.dev.cfg.ModuleDef;
import com.google.gwt.dev.jjs.PrecompilationContext;
import com.google.gwt.dev.util.log.AbstractTreeLogger;
import com.google.gwt.dev.util.log.PrintWriterTreeLogger;
import io.reactjava.client.core.providers.platform.IPlatform;
import io.reactjava.client.core.providers.platform.mobile.android.PlatformAndroid;
import io.reactjava.client.core.providers.platform.mobile.ios.PlatformIOS;
import io.reactjava.client.core.providers.platform.web.PlatformWeb;
import io.reactjava.client.core.react.AppComponentTemplate;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.INativeEventHandler;
import io.reactjava.client.core.react.IProvider;
import io.reactjava.client.core.react.IReactCodeGenerator;
import io.reactjava.client.core.react.IUITheme;
import io.reactjava.client.core.react.SEOInfo;
import io.reactjava.client.core.react.SEOInfo.SEOPageInfo;
import io.reactjava.client.core.react.Utilities;
import io.reactjava.client.core.resources.javascript.IJavascriptResources;
import io.reactjava.jsx.IConfiguration;
import io.reactjava.jsx.IJSXTransform;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.Deflater;
                                       // ReactCodeGenerator =================//
public class ReactCodeGenerator implements IPostprocessor
{
                                       // class constants --------------------//
public static final boolean kSRCCFG_TREE_SHAKE  = false;
public static final boolean kSRCCFG_USE_REQUIRE = true;

public static final String  kGENERATOR_PACKAGE_NAME =
   "io.reactjava.client.generated";

public static final String  kGENERATOR_SIMPLE_CLASSNAME =
   "ReactCodeGeneratorImplementation";

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
      add("io.reactjava.codegenerator");
      add("io.reactjava.jsx");
   }};

public static final List<String> kFILTER_RX_TYPES  =
   new ArrayList<String>()
   {{
      add("io.reactjava.client.core.resources");
      add("io.reactjava.codegenerator");
      add("io.reactjava.jsx");
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
 + "export default class Boot extends Component\n"
 + "{\n"
 + "   constructor(props)\n"
 + "   {\n"
 + "      super(props);\n"
 + "   }\n"
 + "   render()\n"
 + "   {\n"
 + "      var element =\n"
 + "         new window.io.reactjava.client.generated.ReactCodeGeneratorImplementation().boot(\n"
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
public ReactCodeGenerator()
{
   super();
}
/*------------------------------------------------------------------------------

@name       addImportedNodeModuleJavascript - add imported node module script
                                                                              */
                                                                             /**
            Add imported node module javascript.

@param      module      module
@param      logger      logger

@history    Sun Dec 02, 2018 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void addImportedNodeModuleJavascript(
   String     module,
   TreeLogger logger)
   throws     Exception
{
                                       // add the module to the dependency set//
                                       // generating error information if not //
                                       // found                               //
   String path = IConfiguration.getNodeModuleJavascript(module, logger);
   if (path != null)
   {
      if (!IConfiguration.getDependenciesSet().contains(module))
      {
         IConfiguration.getDependenciesSet().add(module);
         IConfiguration.getDependencies().put(module, path);
      }
   }

   logger.log(TreeLogger.INFO,
      "getImportedNodeModules(): module=" + module
    + ", " + (path != null ? "" : "not ") + "found");
}
/*------------------------------------------------------------------------------

@name       addImportedNodeModuleStylesheet - add imported node module css
                                                                              */
                                                                             /**
            Add imported node module css.

@param      module      module
@param      logger      logger

@history    Sun Dec 02, 2018 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void addImportedNodeModuleStylesheet(
   String         module,
   IConfiguration configuration,
   TreeLogger     logger)
   throws         Exception
{
   String path =
      module.endsWith(".css")
         ? IConfiguration.getNodeModuleCSS(module, configuration, logger)
         : IConfiguration.getNodeModuleCSSFromPackageJSON(module, logger);

   if (path != null)
   {
      configuration.getGlobalCSS().add(path);
   }

   logger.log(TreeLogger.INFO,
      "getImportedNodeModules(): stylesheet=" + path
    + ", " + (path != null ? "" : "not ") + "found");
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
   TreeLogger       logger)
   throws           Exception
{
   logger.log(TreeLogger.Type.INFO, "copyBundleScriptToArtifact(): entered");

   Boolean bCompressForce = null;
   InputStream in;

   if (injectScriptAppDirty(logger))
   {
      String injectScript = generateInjectScript(configuration, logger);
      in = new ByteArrayInputStream(injectScript.getBytes("UTF-8"));
   }
   else
   {
      in = new FileInputStream(io.reactjava.jsx.IConfiguration.getInjectScript(logger));
      bCompressForce = false;
   }

   String path = "javascript/reactjavaapp.js";
   copyStreamToArtifact(in, path, configuration, context, logger, bCompressForce);

   logger.log(TreeLogger.Type.INFO, "copyBundleScriptToArtifact(): exiting");
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

   logger.log(TreeLogger.Type.INFO, "copyCSSFileToArtifact(): exiting");
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
   logger.log(TreeLogger.Type.INFO, "copyHTMLContentToWAR(): "+ filename);

   File warDir   = IConfiguration.getWarDir(logger);
   File htmlFile = new File(warDir, filename);

   try (OutputStream out = new FileOutputStream(htmlFile))
   {
      try (InputStream in = new ByteArrayInputStream(content.getBytes("UTF-8")))
      {
         IConfiguration.fastChannelCopy(in, out, content.length());
      }
   }

   logger.log(TreeLogger.Type.INFO, "copyHTMLContentToWAR(): exiting");
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
   logger.log(TreeLogger.Type.INFO, "copyPlatformScriptsToArtifact(): entered");

   Collection<String> scripts = configuration.getRequiredPlatformScripts();
   if (scripts.size() > 0)
   {
      for (String rsrcScript : scripts)
      {
         copyScriptResourceToArtifact(
            rsrcScript, configuration, context, logger);
      }
   }

   logger.log(TreeLogger.Type.INFO, "copyPlatformScriptsToArtifact(): exiting");
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
   logger.log(TreeLogger.Type.INFO, "copyResources(): begin copying resources");

   boolean bLoadLazy = configuration.getScriptsLoadLazy();

   Collection<String> globalImages = configuration.getGlobalImages();
   if (globalImages.size() > 0)
   {
      logger.log(TreeLogger.Type.INFO, "copyResources(): begin copying images");
      for (String rsrcImage : globalImages)
      {
                                          // copy each image from the associated //
                                          // resource to the distribution...     //
         copyImageResourceToArtifact(
            rsrcImage, configuration, context, logger);
      }
      logger.log(TreeLogger.Type.INFO, "copyResources(): done copying images");
   }
   Collection<String> globalCSS = configuration.getGlobalCSS();
   if (globalCSS.size() > 0)
   {
      logger.log(TreeLogger.Type.INFO, "copyResources(): begin copying css");
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
            "copyResources(): making empty imported stylesheets list");

         imported = " ";
      }
                                       // copy the list to the distribution   //
      copyStreamToArtifact(
         new ByteArrayInputStream(imported.getBytes("UTF-8")),
         "css/" + IReactCodeGenerator.kIMPORTED_STYLESHEETS_LIST,
         configuration, context, logger);

      logger.log(TreeLogger.Type.INFO, "copyResources(): done copying css");
   }
   if (!IJavascriptResources.kSRCCFG_SCRIPTS_AS_RESOURCES && bLoadLazy)
   {
      logger.log(TreeLogger.Type.INFO, "copyResources(): begin copying scripts");

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

      logger.log(TreeLogger.Type.INFO, "copyResources(): done copying scripts");
   }

   logger.log(TreeLogger.Type.INFO, "copyResources(): done copying resources");
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
   logger.log(TreeLogger.Type.INFO, "copyResourceToArtifact(): " + rsrcPath);

   InputStream in =
      context.getResourcesOracle().getResourceAsStream(
         kRSRC_BASE_PATH + rsrcPath);

   if (in == null)
   {
      throw new FileNotFoundException(kRSRC_BASE_PATH + rsrcPath);
   }

   copyStreamToArtifact(in, rsrcPath, configuration, context, logger);

   logger.log(TreeLogger.Type.INFO, "copyResourceToArtifact(): exiting");
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
   logger.log(TreeLogger.Type.INFO, "copyRobotsContentToWAR(): entered");

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

   logger.log(TreeLogger.Type.INFO, "copyRobotsContentToWAR(): exiting");
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
      "copyScriptResourceToArtifact(): entered with rsrcPaths=" + rsrcPaths);

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
                  "copyScriptResourceToArtifact(): "
                + "node_modules script not found: " + script.getAbsolutePath());

               continue;
            }
                                       // copy the node script to the artifact//
            rsrcPath = "javascript/" + choices[choices.length - 1];

            logger.log(
               TreeLogger.Type.INFO,
               "copyScriptResourceToArtifact(): "
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
               "copyScriptResourceToArtifact(): "
             + "copying resource script: " + rsrcPath);

            copyResourceToArtifact(rsrcPath, configuration, context, logger);
         }
      }
   }
   logger.log(
      TreeLogger.Type.INFO,
      "copyScriptResourceToArtifact(): exiting for rsrcPaths=" + rsrcPaths);

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
   logger.log(TreeLogger.Type.INFO, "copySitemapContentToWAR(): entered");

   File warDir      = IConfiguration.getWarDir(logger);
   File sitemapFile = new File(warDir, moduleName + "Sitemap.xml");

   try (OutputStream out = new FileOutputStream(sitemapFile))
   {
      try (InputStream in = new ByteArrayInputStream(content.getBytes("UTF-8")))
      {
         IConfiguration.fastChannelCopy(in, out, content.length());
      }
   }

   logger.log(TreeLogger.Type.INFO, "copySitemapContentToWAR(): exiting");
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
      TreeLogger.Type.INFO, "copyStreamToArtifact(): entered for " + dstPath);

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

   logger.log(TreeLogger.Type.INFO, "copyStreamToArtifact(): dst=" + dstName);

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
            "copyStreamToArtifact(): compressing source snippet=\n" + snippet);
      }
      try
      {
         context.commitResource(logger, out);
         logger.log(TreeLogger.Type.INFO,"copyStreamToArtifact(): resource was committed");
      }
      catch(Exception e)
      {
         logger.log(
            TreeLogger.Type.ERROR,"copyStreamToArtifact(): resource was not committed");
         throw e;
      }
   }
   else
   {
      logger.log(
         TreeLogger.Type.INFO,
         "copyStreamToArtifact(): resource by that name is already pending "
       + "or already exists");
   }

   logger.log(TreeLogger.Type.INFO, "copyStreamToArtifact(): exiting");
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
      logger.log(TreeLogger.Type.INFO, "generateInjectScript(): entered");
   }

   String injectScript = generateInjectScriptBrowserify(configuration, logger);

   if (logger != null)
   {
      logger.log(TreeLogger.Type.INFO, "generateInjectScript(): exiting");
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
   File    projectDir      = IConfiguration.getProjectDirectory(null, logger);

   logger.log(
      logger.INFO,
      "generateInjectScriptBrowserify(): entered, productionMode="
         + bProductionMode);
                                       // RxJs is currently restricted to     //
                                       // version 5.5.11 (npm i rxjs@5.5.11)  //
                                       // since the current jsinterop support //
                                       // (com.github.timofeevda.core.rxjs)   //
                                       // is at that version; the support is  //
                                       // now available for version 6 so      //
                                       // update both when time is available..//
                                       // note that files of the released     //
                                       // support package have been changed   //
                                       // for compatibility with the ReactJava//
                                       // bundle namespace = "Rx"             //
                                       //    -> namespace = "ReactJava"    //
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
      //script += "var Rx       = require('rxjs');\n";
   }
   else
   {
      script += "import React    from 'react';\n";
      script += "import ReactDOM from 'react-dom';\n";
      //script += "import Rx       from 'rxjs';\n";
   }

   script += "var _getType = function(type){return module.exports[type]};\n";

   logger.log(
      logger.INFO,
      "generateInjectScriptBrowserify(): numDependencies="
         + IConfiguration.getDependenciesSet().size());

   for (String module : IConfiguration.getDependenciesSet())
   {
      logger.log(
         logger.INFO,
         "generateInjectScriptBrowserify(): adding module=" + module);

                                       // get the require string              //
                                       // and the export target               //
      String item   = module.substring(module.lastIndexOf('.') + 1);
             item   = IConfiguration.toReactAttributeName(item);
      String source = module.replace(".","/");

      String scriptAdd;
      if (kSRCCFG_USE_REQUIRE)
      {
         scriptAdd = "var " + item + " = require('" + source + "');\n";
      }
      else
      {
         scriptAdd = "import " + item + " from '" + source + "';\n";
      }

      String target    = item + ".default || " + item + "." + item;
      String exportAdd = ",\n   " + item + ":" + target;

      script += scriptAdd;
      export += exportAdd;
   }

   export += "};\n";
   script += export;

   logger.log(
      logger.INFO, "generateInjectScriptBrowserify(): main.js=\n" + script);

                                       // the current working directory is not//
                                       // necessarily the project directory   //
                                       // and it is not possible to change it //
                                       // without a lot of native code and    //
                                       // other fiddling so save it explicitly//
                                       // to the project directory and then   //
                                       // exec with the wd the project dir... //
   File main = new File(projectDir, "main.js");
   ByteArrayInputStream in = new ByteArrayInputStream(script.getBytes("UTF-8"));
   FileOutputStream out = new FileOutputStream(main);
   IConfiguration.fastChannelCopy(in, out, in.available());
   out.flush();
   out.close();
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

   for (int iOp = 0; iOp < 2; iOp++)
   {
      Map<String,String> environment = new HashMap<>();
      List<String>       commands    = new ArrayList<>();
      switch(iOp)
      {
         case 0:
         {
            // ex: node_modules/.bin/browserify main.js -s ReactJava -o appbundle.js

            String envify =
               ".bin/envify" + (IConfiguration.getOSWindows() ? ".cmd" : "");

            File executableFile =
               new File(IConfiguration.getNodeModulesDir(logger), envify);

            if (!executableFile.exists())
            {
               String msg =
                  "Cannot find executable. "
                + "Did you forget to install envify? (npm i envify)";

               logger.log(logger.ERROR, "generateInjectScriptBrowserify(): " + msg);
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

               logger.log(logger.ERROR, "generateInjectScriptBrowserify(): " + msg);
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

            commands.add("main.js");
            commands.add("-s");
            commands.add(namespace);

            if (bProductionMode)
            {
               environment.put("NODE_ENV", "production");

                                       // envify                              //
               commands.add("-t");
               commands.add("envify");
            }

            commands.add("-o");
            commands.add(bundleName);
            break;
         }
         case 1:
         {
            if (!bProductionMode)
            {
               break;
            }
                                       // rename inject file                  //
            injectScriptFile.renameTo(prettyScriptFile);
            injectScriptFile = new File(projectDir, bundleName);

            // ex: node_modules/.bin/uglifyjs "appbundle.pretty.js" -cm -o appbundle.js
            String uglify =
               ".bin/uglifyjs" + (IConfiguration.getOSWindows() ? ".cmd" : "");

            File executableFile =
               new File(IConfiguration.getNodeModulesDir(logger), uglify);

            if (!executableFile.exists())
            {
               String msg =
                  "Cannot find executable. "
                + "Did you forget to install uglifyjs? (npm i uglifyjs)";

               logger.log(logger.ERROR, "generateInjectScriptBrowserify(): " + msg);
               throw new IllegalStateException(msg);
            }

            String executable = executableFile.getAbsolutePath();

            commands.add(executable);
            commands.add(prettyName);
            commands.add("-cm");
            commands.add("-o");
            commands.add(bundleName);
            break;
         }
      }

      if (commands.size() > 0)
      {
         String command = "";
         for (String token : commands)
         {
            command += token + " ";
         }

         logger.log(
            logger.INFO,
            "generateInjectScriptBrowserify(): \nwd=" + projectDir.getAbsolutePath()
          + "\ncommand=" + command + "\n");

         long           start = System.currentTimeMillis();
         ProcessBuilder pb    = new ProcessBuilder(commands).directory(projectDir);
         if (environment.size() > 0)
         {
            pb.environment().putAll(environment);
         }

         Process p  = pb.start();
         int     cc = p.waitFor();

         logger.log(
            logger.INFO,
            "generateInjectScriptBrowserify(): completed in "
          + (System.currentTimeMillis() - start) + " ms.");

         if (cc != 0)
         {
            String msg =
               "Process returned an error. Did you forget to install any of "
             + "react, react-dom, or rxjs@5.5.11? (please note version of rxjs)";

            logger.log(logger.ERROR, "generateInjectScriptBrowserify(): " + msg);
            throw new IllegalStateException(msg);
         }
         if (!injectScriptFile.exists())
         {
            throw new IllegalStateException("Inject file was not generated");
         }
      }
   }

   String injectScript = IJSXTransform.getFileAsString(injectScriptFile, logger);
   logger.log(
      logger.INFO,
      "generateInjectScriptBrowserify(): injectScript length="
         + injectScript.length());
   //main.delete();

   logger.log(
      logger.INFO,
      "generateInjectScriptBrowserify(): exiting after "
    + (System.currentTimeMillis() - entry) + " msec");

   return(injectScript);
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

@name       getAppClassname - get application classname
                                                                              */
                                                                             /**
            Get application classname.

@return     application classname

@param      appType     app type
@param      logger      logger

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getAppClassname(
   JType      appType,
   TreeLogger logger)
{
   String appClassname = appType.getQualifiedSourceName();

   logger.log(
      TreeLogger.Type.INFO,
      "getAppClassname(): appClassname=" + appClassname);

   return(appClassname);
}
/*------------------------------------------------------------------------------

@name       getAppType - get application type
                                                                              */
                                                                             /**
            Get application type.

@return     application type

@param      components     components
@param      logger         logger

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static JType getAppType(
   Map<String,JClassType> components,
   TreeLogger             logger)
{
                                       // find the AppComponentTemplate       //
   String     appTemplateClassname = AppComponentTemplate.class.getName();
   JClassType appTemplateType      = null;
   for (String classname : components.keySet())
   {
      JClassType candidate = components.get(classname);
      if (appTemplateClassname.equals(candidate.getQualifiedSourceName()))
      {
         appTemplateType = candidate;
         break;
      }
   }
   if (appTemplateType == null)
   {
      throw new IllegalStateException("Cannot find AppComponentTemplate type");
   }
                                       // find AppComponentTemplate subTypes  //
   Set<JClassType> appClassTypes = new HashSet<JClassType>();
   for (String classname : components.keySet())
   {
      JClassType candidate = components.get(classname);
      if (candidate.isAssignableTo(appTemplateType))
      {
         appClassTypes.add(candidate);
      }
   }
                                       // find the app type                   //
   JType appType = null;
   for (JClassType candidate : appClassTypes)
   {
      boolean bSubtype = false;
      for (JClassType chase : appClassTypes)
      {
         if (chase.equals(candidate))
         {
            continue;
         }
         if (candidate.isAssignableFrom(chase))
         {
            logger.log(
               logger.INFO,
               "getAppType(): " + candidate.getQualifiedSourceName()
             + " is a superclass of " + chase.getQualifiedSourceName());

            bSubtype = true;
            break;
         }
      }
      if (!bSubtype)
      {
         appType = candidate;
         break;
      }
   }
   if (appType == null)
   {
      throw new IllegalStateException("Cannot find App type");
   }

   logger.log(logger.INFO, "getAppType: appType=" + appType);

   return(appType);
}
/*------------------------------------------------------------------------------

@name       getConfiguration - get configuration
                                                                              */
                                                                             /**
            This implementation simply instantiates a default configuration.

            This implementation should be replaced to generate a configuration
            by examining the specified context.

@return     The configuration.

@param      context     context
@param      logger     logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected IConfiguration getConfiguration(
   GeneratorContext context,
   TreeLogger       logger)
   throws           IOException
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
   File    libDir       = IConfiguration.getWarLibraryDir(logger);
   if (libDir != null)
   {
      File candidate = new File(libDir, "reactjava.jar");
      if (candidate.exists())
      {
         reactJavaJar = new JarFile(candidate);
      }
   }
   return(reactJavaJar);
}
/*------------------------------------------------------------------------------

@name       getSourceForClassname - get source for classname
                                                                              */
                                                                             /**
            Get source as string for classname.

@param      classname      classname
@param      logger         logger

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getSourceForClassname(
   String     classname,
   TreeLogger logger)
   throws     Exception
{
   String  src = null;
   JarFile jar;
                                       // try project source directory        //
   File   srcDir  = IConfiguration.getProjectDirectory("src", null);
   String srcPath = classname.replace(".","/") + ".java";
   File   srcFile = new File(srcDir, srcPath);
   if (srcFile.exists())
   {
      src = IJSXTransform.getFileAsString(srcFile, logger);
   }
   else if ((jar = getReactJavaJar(logger)) != null)
   {
                                       // try source in reactjava.jar         //
      JarEntry entry = jar.getJarEntry(srcPath);
      if (entry != null)
      {
         src =
            IJSXTransform.getInputStreamAsString(
               jar.getInputStream(entry), logger);
      }
   }

   return(src);
}
/*------------------------------------------------------------------------------

@name       handleImportedNodeModules - add imported node modules
                                                                              */
                                                                             /**
            Add imported node modules.

@param      importedModules   imported node modules
@param      configuration     configuration
@param      logger            logger

@history    Sun Dec 02, 2018 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void handleImportedNodeModules(
   Collection<String> importedModules,
   IConfiguration     configuration,
   TreeLogger         logger)
   throws             Exception
{
   if (importedModules.size() == 0)
   {
      logger.log(
         TreeLogger.INFO,
         "handleImportedNodeModules(): no imported node modules specified");
   }
   else
   {
      for (String moduleSpecifier : importedModules)
      {
                                       // find whether looking for javascript //
                                       // or css or both                      //
         String[] splits  = moduleSpecifier.split(":");
         String   module  = splits[0];
         boolean  bScript = module.endsWith(".js");
         boolean  bStyle  = module.endsWith(".css");

         if (!bScript && !bStyle)
         {
            for (int iSplit = 1; iSplit < splits.length; iSplit++)
            {
               String split = splits[iSplit].trim().toLowerCase();
               if ("javascript".equals(split))
               {
                  bScript = true;
               }
               else if ("css".equals(split))
               {
                  bStyle = true;
               }
            }
         }

         bScript |= bStyle == false;
         if (bScript)
         {
            addImportedNodeModuleJavascript(module, logger);
         }
         if (bStyle)
         {
            addImportedNodeModuleStylesheet(module, configuration, logger);
         }
      }
   }
}
/*------------------------------------------------------------------------------

@name       handleImportedNodeModulesAndSEO - get imported node modules
                                                                              */
                                                                             /**
            Get any imported node modules from the application class
            implementation, adding them to the dependencies set, as well as any
            page hashes.

@param      typesMap          map of component type by classname
@param      configuration     configuration
@param      module            module
@param      logger            logger

@history    Sun Dec 02, 2018 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void handleImportedNodeModulesAndSEO(
   Map<String,Map<String,JClassType>> typesMap,
   ModuleDef                          module,
   IConfiguration                     configuration,
   TreeLogger                         logger)
   throws                             Exception
{
   Collection<String>     importedModules = new ArrayList<>();
   Map<String,JClassType> components      = typesMap.get(kKEY_COMPONENTS);

   JType  appType      = getAppType(components, logger);
   String appClassname = getAppClassname(appType, logger);

   if (appClassname != null
         && !appClassname.equals(AppComponentTemplate.class.getName()))
   {
      logger.log(
         TreeLogger.INFO,
         "handleImportedNodeModulesAndSEO(): checking for " + appClassname);

                                       // get the component imported modules  //
      Map<String,JClassType> appTypes = typesMap.get(kKEY_APP_TYPES);

      String importedModulesAndSEO =
         AppComponentInspector.getImportedModulesAndSEO(
            appClassname, appTypes, logger).trim();

      logger.log(
         TreeLogger.INFO,
         "handleImportedNodeModulesAndSEO(): result=" + importedModulesAndSEO);

                                       // remove wrapping brackets            //
      importedModulesAndSEO = importedModulesAndSEO.substring(1);
      importedModulesAndSEO =
         importedModulesAndSEO.substring(0, importedModulesAndSEO.length() - 1);

      logger.log(
         TreeLogger.INFO,
         "handleImportedNodeModulesAndSEO(): unwrapped=" + importedModulesAndSEO);

                                       // separate the imported modules from  //
                                       // the page info                       //
      String  sSEOInfo = "";
      boolean bSEO     = false;
      for (String entry : importedModulesAndSEO.split(","))
      {
         entry = entry.trim();
         if (!bSEO && "$".equals(entry))
         {
            bSEO = true;
            continue;
         }
         else if (!bSEO)
         {
            importedModules.add(entry);
         }
         else
         {
            sSEOInfo += entry;
         }
      }

      handleImportedNodeModules(importedModules, configuration, logger);

      logger.log(
         TreeLogger.INFO,
         "handleImportedNodeModulesAndSEO(): sSEOInfo=" + sSEOInfo);

      handleSEOInfo(new SEOInfo(sSEOInfo), module, logger);
   }
}
/*------------------------------------------------------------------------------

@name       handleSEOInfo - handle page hashes
                                                                              */
                                                                             /**
            Handle collection of app page hashes for SEO support.Creates a
            redirect target for each hash, along with an associated sitemap.

@param      seoInfo        seo info
@param      module         module
@param      logger         logger

@history    Sun Dec 02, 2018 18:00:00 (LBM) created.

@notes      see https://developers.google.com/search/docs/guides/intro-structured-data
                                                                              */
//------------------------------------------------------------------------------
protected void handleSEOInfo(
   SEOInfo     seoInfo,
   ModuleDef   module,
   TreeLogger  logger)
   throws      Exception
{
   if (seoInfo == null)
   {
      logger.log(TreeLogger.INFO, "handleSEOInfo(): no SEO requested");
   }
   else if (seoInfo.pageInfos.size() == 0)
   {
      logger.log(TreeLogger.INFO, "handleSEOInfo(): no page infos specified");
   }
   else
   {
      String sitemapContent  = kSITEMAP_HDR;
      String defaultHTMLName = getHTMLFilenameForModule(module.getName(),logger);
             defaultHTMLName =
               defaultHTMLName.substring(0, defaultHTMLName.length() - 5);

      String deployPath = seoInfo.deployPath;

      logger.log(
         TreeLogger.INFO,
         "handleSEOInfo(): "
       + "deployPath=" + deployPath + ", htmlName=" + defaultHTMLName);

      for (SEOPageInfo pageInfo : seoInfo.pageInfos)
      {
         String pageHash = pageInfo.pageHash;

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
      else if ("parseReactiveXInvocations".toLowerCase().equals(opcodeLC))
      {
         new ReactCodeGenerator().parseReactiveXInvocations(
            "io.reactjava.client.core.providers.http.HttpClient",
            newFileLogger(null, 0));
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

@name       newFileLogger - new file logger
                                                                              */
                                                                             /**
            New file logger.

@return     new file logger

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static TreeLogger newFileLogger(
   TreeLogger logger,
   long       nanoTime)
{
   if (logger == null || logger instanceof AbstractTreeLogger)
   {
      try
      {
         Type maxDetail =
            logger == null
               ? TreeLogger.ALL : ((AbstractTreeLogger)logger).getMaxDetail();

         File logFile   =
            new File(
               IConfiguration.getProjectDirectory(null, logger),
               "antlog.codegenerator.txt");

         logFile.delete();

         logger = new PrintWriterTreeLogger(logFile);
         ((PrintWriterTreeLogger)logger).setMaxDetail(maxDetail);
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
   return(logger);
}
/*------------------------------------------------------------------------------

@name       parseClasses - log class information
                                                                              */
                                                                             /**
            Filter all classes visible to GWT client side and return only those
            marked as an Injectable.

@return     map of classname by class type

@param      oracle     oracle
@param      logger     logger

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected Map<String,Map<String,JClassType>> parseClasses(
   TypeOracle     oracle,
   IConfiguration configuration,
   TreeLogger     logger)
   throws         Exception
{
   logger.log(TreeLogger.Type.INFO, "parseClasses(): entered");

   String propertiesClassname =
      io.reactjava.client.core.react.Properties.class.getName().replace("$",".");

   String platformClassname  = IPlatform.class.getName().replace("$",".");
   String providerClassname  = IProvider.class.getName().replace("$",".");
   String componentClassname = Component.class.getName().replace("$",".");

   JClassType[]           types    = oracle.getTypes();
   Map<String,JClassType> allTypes = new HashMap<String,JClassType>();

   JClassType             propertiesType = null;
   Map<String,JClassType> platforms      = new HashMap<String,JClassType>();
   JClassType             platformType   = null;
   Map<String,JClassType> providers      = new HashMap<String,JClassType>();
   JClassType             providerType   = null;
   Map<String,JClassType> components     = new HashMap<String,JClassType>();
   JClassType             componentType  = null;
   Map<String,JClassType> appTypes       = new HashMap<String,JClassType>();

   for (JClassType classType : types)
   {
      String classname = classType.getQualifiedSourceName();

      //logger.log(TreeLogger.Type.INFO, "parseClasses(): parsing " + classname);

      if (propertiesClassname.equals(classname))
      {
         propertiesType = classType;
         logger.log(
            TreeLogger.Type.DEBUG,
            "parseClasses(): found Properties, "
          + classType.getSimpleSourceName()
          + "->" + classType.getQualifiedSourceName());

         continue;
      }
      if (platformClassname.equals(classname))
      {
         platformType = classType;
         logger.log(
            TreeLogger.Type.DEBUG,
            "parseClasses(): found IPlatform, "
          + classType.getSimpleSourceName()
          + "->" + classType.getQualifiedSourceName());

         continue;
      }
      if (providerClassname.equals(classname))
      {
         providerType = classType;
         logger.log(
            TreeLogger.Type.DEBUG,
            "parseClasses(): found IProvider, "
          + classType.getSimpleSourceName()
          + "->" + classType.getQualifiedSourceName());

         continue;
      }
      else if (componentClassname.equals(classname))
      {
         componentType = classType;
         logger.log(
            TreeLogger.Type.DEBUG,
            "parseClasses(): found Component, "
          + classType.getSimpleSourceName()
          + "->" + classType.getQualifiedSourceName());

         continue;
      }

      boolean bFilteredAll = false;
      for (String filter : kFILTER_ALL_TYPES)
      {
         if (classname.startsWith(filter))
         {
            bFilteredAll = true;
            break;
         }
      }
      if (!bFilteredAll)
      {
         allTypes.put(classType.getSimpleSourceName(), classType);

                                       // further filter to check for custom  //
                                       // app types                           //
         boolean bFilteredApp = false;
         for (String filter : kFILTER_APP_TYPES)
         {
            if (classname.startsWith(filter))
            {
               bFilteredApp = true;
               break;
            }
         }
         if (!bFilteredApp)
         {
            appTypes.put(classType.getSimpleSourceName(), classType);
         }
                                       // further filter to check for         //
                                       // reactive-x types                    //
         boolean bFilteredRx = false;
         for (String filter : kFILTER_RX_TYPES)
         {
            if (classname.startsWith(filter))
            {
               bFilteredRx = true;
               break;
            }
         }
         if (!bFilteredRx)
         {
            parseReactiveXInvocations(classname, logger);
         }
      }
   }

   if (propertiesType == null)
   {
      throw new Exception("Can't find Properties type");
   }
   if (platformType == null)
   {
      throw new Exception("Can't find IPlatform type");
   }
   if (providerType == null)
   {
      throw new Exception("Can't find IProvider type");
   }
   if (componentType == null)
   {
      throw new Exception("Can't find Component type");
   }

   for (JClassType allType : allTypes.values())
   {
      logger.log(TreeLogger.Type.INFO, "parseClasses(): presented " + allType.getQualifiedSourceName());
      if (allType.isInterface() != null)
      {
         continue;
      }
      if (allType.equals(platformType)
            || allType.equals(providerType)
            || allType.equals(componentType))
      {
         continue;
      }

      boolean bComponent = allType.isAssignableTo(componentType);
      boolean bPlatform  = allType.isAssignableTo(platformType);
      boolean bProvider  = allType.isAssignableTo(providerType);

      if (!bComponent && !bPlatform && !bProvider)
      {
         continue;
      }

      String classname = allType.getQualifiedSourceName();
      logger.log(TreeLogger.Type.INFO, "parseClasses(): examining " + classname);

                                       // used to require a default, nullary, //
                                       // or constructor taking a Properties  //
                                       // instance as a sole argument (190317)//
      //if (parseConstructors(allType, propertiesType, logger).get("propsParam"))
      //{
         if (bComponent)
         {
            components.put(allType.getSimpleSourceName(), allType);
         }
         if (bPlatform)
         {
            platforms.put(allType.getSimpleSourceName(), allType);
         }
         if (bProvider)
         {
            providers.put(allType.getSimpleSourceName(), allType);
         }
      //}
   }
   for (String classname : providers.keySet())
   {
      logger.log(TreeLogger.Type.INFO,"parseClasses(): provider=" + classname);
   }
   for (String classname : components.keySet())
   {
      logger.log(TreeLogger.Type.INFO,"parseClasses(): component=" + classname);
   }

   Map<String,Map<String,JClassType>> providersAndComponents =
      new HashMap<String,Map<String,JClassType>>()
      {{
         put(kKEY_ALL_TYPES,  allTypes);
         put(kKEY_APP_TYPES,  appTypes);
         put(kKEY_COMPONENTS, components);
         put(kKEY_PLATFORMS,  platforms);
         put(kKEY_PROVIDERS,  providers);
      }};

   return(providersAndComponents);
}
/*------------------------------------------------------------------------------

@name       parseConstructors - parse constructors of specified type
                                                                              */
                                                                             /**
            Parse constructors of specified type, checking for a default,
            nullary, or constructor taking a Properties instance as its sole
            argument.

@return     A descriptor of the existence of a default, nullary, or constructor
            taking a Properties instance as its sole argument.

@param      type     class type

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected Map<String,Boolean> parseConstructors(
   JClassType type,
   JClassType propertiesType,
   TreeLogger logger)
{
   boolean bDefault    = false;
   boolean bProperties = false;

   for (JConstructor constructor : type.getConstructors())
   {
      JType[] paramTypes = constructor.getParameterTypes();
      if (paramTypes == null || paramTypes.length == 0)
      {
         bDefault = true;
         continue;
      }
      if (paramTypes.length > 1)
      {
         continue;
      }

      JClassType paramClassType = paramTypes[0].isClass();
      if (paramClassType == null)
      {
         continue;
      }
      if (paramClassType.isAssignableTo(propertiesType))
      {
         bProperties = true;
      }
   }
   if (!bDefault)
   {
      logger.log(
         TreeLogger.Type.DEBUG,
         "parseConstructors(): resource is not default instantiable: "
            + type.getQualifiedSourceName());
   }

   Map<String,Boolean> result = new HashMap<String,Boolean>();
   result.put("default",    bDefault);
   result.put("propsParam", bProperties);

   return(result);
}
/*------------------------------------------------------------------------------

@name       parseReactiveXInvocations - parse any ReactiveX invocations
                                                                              */
                                                                             /**
            Parse any ReactiveX invocations to determine which Rx scripts need
            to be imported.

@param      classname      classname
@param      logger         logger

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void parseReactiveXInvocations(
   String     classname,
   TreeLogger logger)
   throws     Exception
{
   String src = getSourceForClassname(classname, logger);
   if (src == null)
   {
      logger.log(
         Type.INFO,
         "ReactCodeGenerator.parseReactiveXInvocations(): "
       + "cannot find source for " + classname
       + ". If its an inner class, it may be included in another source file");
   }
   else
   {
      String token = "io.reactjava.client.core.rxjs.";
      char[] delim = {'(','\n','\t',' ',';'};

      for (int iBeg = 0, iEnd = 0, iMax = src.length(); iBeg < iMax; iBeg= iEnd)
      {
         iBeg = src.indexOf(token, iEnd);
         if (iBeg < 0)
         {
            break;
         }
         iBeg += token.length();
         iEnd  = Integer.MAX_VALUE;
         for (char chase : delim)
         {
            int idx = src.indexOf(chase, iBeg);
            if (idx >= 0 && idx < iEnd)
            {
               iEnd = idx;
            }
         }
         if (iEnd ==  Integer.MAX_VALUE)
         {
            throw new IllegalStateException("Invocation target unterminated");
         }

         String target = src.substring(iBeg, iEnd);
                target = target.substring(target.lastIndexOf('.') + 1);
                target = "rxjs." + target;

         addImportedNodeModuleJavascript(target, logger);

         logger.log(
            Type.INFO,
            "ReactCodeGenerator.parseReactiveXInvocations(): found " + target
          + " in " + classname);
      }
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

@history    Sun Mar 16, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void process(
   TreeLogger            logger,
   CompilerContext       compilerContext,
   PrecompilationContext precompilationContext)
   throws                Exception
{
   long start = System.nanoTime();

   logger = newFileLogger(logger, start);
   logger.log(logger.INFO, new Date().toString());
   logger.log(logger.INFO, "nanoTime=" + start);
   logger.log(logger.DEBUG, "process(): entered");

   ModuleDef module = compilerContext.getModule();

   GeneratorContext context =
      precompilationContext.getRebindPermutationOracle().getGeneratorContext();

   IConfiguration configuration = getConfiguration(context, logger);

   Map<String,Map<String,JClassType>> providersAndComponents =
      parseClasses(context.getTypeOracle(), configuration, logger);

                                       // copy all scripts and css to artifact//
   copyResources(configuration, context, logger);

   saveCurrentDependencies(logger);

   handleImportedNodeModulesAndSEO(
      providersAndComponents, module, configuration, logger);

   logger.log(logger.INFO, "nanoTime=" + System.nanoTime());
   logger.log(
      logger.INFO,
      "process(): exiting, total ms=" + (System.nanoTime() - start) / 1000000L);
}
/*------------------------------------------------------------------------------

@name       saveCurrentDependencies - save current dependencies
                                                                              */
                                                                             /**
            Save current dependencies.

@param      logger      logger

@history    Sun Mar 16, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void saveCurrentDependencies(
   TreeLogger logger)
{
   IConfiguration
      .getDependenciesPrevious()
      .clear();

   IConfiguration
      .getDependenciesPrevious()
      .putAll(IConfiguration.getDependencies());

   IConfiguration
      .getDependenciesSetPrevious()
      .clear();

   IConfiguration
      .getDependenciesSetPrevious()
      .addAll(IConfiguration.getDependenciesSet());
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
}//====================================// end ReactJavaCodeGenerator =========//
