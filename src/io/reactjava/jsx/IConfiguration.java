/*==============================================================================

name:       IConfiguration.java

purpose:    ReactJava Code Generation Configuration Interface.

history:    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.jsx;
                                       // imports --------------------------- //
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.dev.util.log.PrintWriterTreeLogger;
import io.reactjava.client.core.react.Utilities;
import io.reactjava.client.providers.platform.IPlatform;
import io.reactjava.client.providers.platform.mobile.android.PlatformAndroid;
import io.reactjava.client.providers.platform.mobile.ios.PlatformIOS;
import io.reactjava.client.providers.platform.web.PlatformWeb;
import io.reactjava.jsx.IJSXTransform.ILibraryComponent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarFile;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
                                       // IConfiguration =====================//
public interface IConfiguration
   extends io.reactjava.client.core.react.IConfiguration
{
                                       // class constants --------------------//
                                       // "export " at beginning of a line    //
String kREGEX_EXPORT = "(?m)^export\\b";
                                       // "import " at beginning of a line    //
String kREGEX_IMPORT = "(?m)^import\\b";

                                       // class variables ------------------- //
                                       // platform                            //
IPlatform[]        platform = new IPlatform[1];
                                       // script dependency tags              //
List<String>       dependenciesSet = new ArrayList<String>();
                                       // previous script dependency tags     //
Set<String>        dependenciesSetPrevious = new HashSet<String>();
                                       // script dependencies by tag          //
Map<String,String> dependencies = new HashMap<String,String>();
                                       // previous script dependencies by tag //
Map<String,String> dependenciesPrevious = new HashMap<String,String>();

/*------------------------------------------------------------------------------

@name       fastChannelCopy - fast channel copy
                                                                              */
                                                                             /**
            fast channel copy.

@param      in    input stream
@param      out   output stream

@history    Sun Mar 16, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
static void fastChannelCopy(
   InputStream  in,
   OutputStream out)
   throws       IOException
{
   fastChannelCopy(in, out, Integer.MAX_VALUE);
}
/*------------------------------------------------------------------------------

@name       fastChannelCopy - fast channel copy
                                                                              */
                                                                             /**
            fast channel copy.

@param      in       input stream
@param      out      output stream
@param      length   number of bytes

@history    Sun Mar 16, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
static void fastChannelCopy(
   InputStream  in,
   OutputStream out,
   long         length)
   throws       IOException
{
   if (length > 0)
   {
      ReadableByteChannel channelIn  = Channels.newChannel(in);
      WritableByteChannel channelOut = Channels.newChannel(out);

      final int        chunkSize = (int)Math.min(1024 * 1024, length);
      final int        modulo    = (int)(length >= chunkSize ? length % chunkSize : 0);
      final long       lenMain   = length - modulo;
      final ByteBuffer buffMain  = ByteBuffer.allocateDirect(chunkSize);
      final ByteBuffer buffLeft  = modulo > 0 ? ByteBuffer.allocateDirect(modulo) : null;
      long             totalRead = 0;

      for (int mode = 0; totalRead < length && mode < 2; mode++)
      {
         ByteBuffer buffer   = mode == 0 ? buffMain : buffLeft;
         long       lenPart  = mode == 0 ? lenMain  : modulo;
         long       partRead = 0;

         while (partRead < lenPart)
         {
            int bytesRead = channelIn.read(buffer);
            if (bytesRead < 0)
            {
               break;
            }

            totalRead += bytesRead;
            partRead  += bytesRead;
                                       // prepare the buffer to be drained    //
            buffer.flip();
                                       // write to the channel, may block     //
            channelOut.write(buffer);
                                       // if partial transfer, shift remainder//
                                       // down; if buffer is empty, same as   //
                                       // doing clear()                       //
            buffer.compact();
         }
                                       // eof will leave buffer in fill state //
         buffer.flip();
                                       // make sure buffer is fully drained   //
         while (buffer.hasRemaining())
         {
           channelOut.write(buffer);
         }
      }

      channelIn.close();
   }
}
/*------------------------------------------------------------------------------

@name       getArtifactDir - get artifact directory path
                                                                              */
                                                                             /**
            Get artifact directory path.

@return     artifact directory path.

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static File getArtifactDir(
   TreeLogger logger)
   throws FileNotFoundException
{
   File artifactDir  = null;
   File outDir       = IConfiguration.getProjectDirectory("out", logger);
   File artifactsDir = new File(outDir, "artifacts");
   if (artifactsDir.exists())
   {
      for (File candidate : artifactsDir.listFiles())
      {
         if (candidate.getName().endsWith("war_exploded"))
         {
            artifactDir = new File(candidate.getAbsolutePath());
            break;
         }
      }
   }
   else
   {
      throw new FileNotFoundException(
         "Cannot find artifacts directory: " + artifactsDir.getAbsolutePath());
   }
   if (artifactDir == null)
   {
      throw new FileNotFoundException("Cannot find artifact directory");
   }

   logger.log(
      logger.DEBUG,
      "jsx.IConfiguration.getArtifactDir(): artifactDir="
         + artifactDir.getAbsolutePath());

   return(artifactDir);
}
/*------------------------------------------------------------------------------

@name       getArtifactJavascriptDir - get artifact javascript directory path
                                                                              */
                                                                             /**
            Get artifact javascript directory path.

@return     artifact javascript directory path, or null if does not exist.

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static File getArtifactJavascriptDir(
   TreeLogger logger)
   throws     FileNotFoundException
{
   File moduleDir     = getModuleDir(logger);
   File javascriptDir = new File(moduleDir, "reactjava/javascript");

   if (!javascriptDir.exists())
   {
      throw new FileNotFoundException(
         "Cannot find artifact javascript directory");
   }

   logger.log(
      logger.DEBUG,
      "jsx.IConfiguration.getArtifactJavascriptDir(): artifactJavascriptDir="
         + javascriptDir.getAbsolutePath());

   return(javascriptDir);
}
/*------------------------------------------------------------------------------

@name       getDependencies - get map of script dependencies
                                                                              */
                                                                             /**
            Get map of script dependencies.

@return     map of script dependencies

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static Map<String,String> getDependencies()
{
   return(dependencies);
}
/*------------------------------------------------------------------------------

@name       getDependenciesPrevious - get previous map of script dependencies
                                                                              */
                                                                             /**
            Get previous map of script dependencies.

@return     previous map of script dependencies

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static Map<String,String> getDependenciesPrevious()
{
   return(dependenciesPrevious);
}
/*------------------------------------------------------------------------------

@name       getDependenciesSet - get set of dependency tags
                                                                              */
                                                                             /**
            Get set of script dependency tags.

@return     set of script dependency tags

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static List<String> getDependenciesSet()
{
   return(dependenciesSet);
}
/*------------------------------------------------------------------------------

@name       getDependenciesSetPrevious - get previous set of dependency tags
                                                                              */
                                                                             /**
            Get previous set of script dependency tags.

@return     previous set of script dependency tags

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static Set<String> getDependenciesSetPrevious()
{
   return(dependenciesSetPrevious);
}
/*------------------------------------------------------------------------------

@name       getDescendantJavascript - get library component javascript
                                                                              */
                                                                             /**
            Get library component for javascript specified filename or
            "index.js" within descendent directory with filename without ".js".

@return     library component for specified filename

@param      parent      parent
@param      filename    filename
@param      logger      logger

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static File getDescendantJavascript(
   File             parent,
   String           filename,
   TreeLogger       logger)
{
   File             descendant = null;
   String           jsName     = filename + ".js";
   String           parentName = parent.getName();
   File[]           children   = parent.listFiles();
   Map<String,File> candidates = new HashMap<String,File>();

   if (children != null)
   {
      boolean bES6Syntax = false;

      for (File child : children)
      {
         String content   = null;
         String childName = child.getName();
         if (jsName.equals(childName)
               || "index.js".equals(childName) && filename.equals(parentName))
         {
            try
            {
               content = IJSXTransform.getFileAsString(child, logger);
               if (isES6Syntax(content))
               {
                                       // ensure not using ES6                //
                  bES6Syntax = true;
                  break;
               }

               candidates.put(childName, child);
            }
            catch(Exception e)
            {
                                       // break on error reading candidate    //
               break;
            }
         }
      }
      if (!bES6Syntax)
      {
                                       // in preferred order...               //
         descendant = candidates.get(jsName);
         if (descendant == null)
         {
            descendant = candidates.get("index.js");
         }
         if (descendant == null)
         {
            for (File child : children)
            {
               if (child.isDirectory())
               {
                  descendant = getDescendantJavascript(child, filename, logger);
                  if (descendant != null)
                  {
                     break;
                  }
               }
            }
         }
      }
   }

   return(descendant);
}
/*------------------------------------------------------------------------------

@name       getInjectScript - get inject script
                                                                              */
                                                                             /**
            Get inject script.

@return     inject script

@param      logger      any logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
static File getInjectScript(
   TreeLogger logger)
   throws     FileNotFoundException
{
   File jsFile =
      new File(
         IConfiguration.getArtifactJavascriptDir(logger), "reactjavaapp.js");

   if (!jsFile.exists())
   {
      throw new FileNotFoundException("Cannot find app inject script");
   }
   return(jsFile);
}
/*------------------------------------------------------------------------------

@name       getJarFiles - get jar files in project lib directory
                                                                              */
                                                                             /**
            Get jar files in project lib directory.

@return     jar files in project lib directory

@param      logger         logger.

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
static List<JarFile> getJarFiles(
   TreeLogger    logger)
{
   List<JarFile> jarFiles = null;
   File          libDir   = getLibraryDir(logger);
   File[]        files    = libDir != null ? libDir.listFiles() : null;
   if (files != null)
   {
      jarFiles = new ArrayList<JarFile>();
      for (File file : files)
      {
         if (file.getName().toLowerCase().endsWith(".jar"))
         {
            try
            {
               jarFiles.add(new JarFile(file));
            }
            catch(Exception e)
            {
               logger.log(logger.ERROR, "jsx.IConfiguration.getJarFiles(): " + e);
            }
         }
      }
   }
   return(jarFiles);
}
/*------------------------------------------------------------------------------

@name       getLibraryComponent - get library component for specified tag name
                                                                              */
                                                                             /**
            Get library component for specified tag name.

@return     library component for specified tag name

@param      tagName     library component tag name

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static ILibraryComponent getLibraryComponent(
   String     tagName,
   TreeLogger logger)
{
   ILibraryComponent component = () ->
   {
      return(tagName);
   };

   String componentPath =
      IConfiguration.getLibraryComponentJavascript(tagName, logger);

   if (componentPath != null)
   {
      logger.log(
         logger.DEBUG,
         "jsx.IConfiguration.getLibraryComponent(): adding dependency="
            + tagName + ", " + componentPath);

      if (!IConfiguration.getDependenciesSet().contains(tagName))
      {
         IConfiguration.getDependenciesSet().add(tagName);
         IConfiguration.getDependencies().put(tagName, componentPath);
      }
   }

   return(componentPath != null ? component : null);
}
/*------------------------------------------------------------------------------

@name       getLibraryComponentJavascript - get library component javascript
                                                                              */
                                                                             /**
            Get library component for javascript specified tag name.

@return     library component for specified tag name

@param      tagName     library component tag name

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getLibraryComponentJavascript(
   String     tagName,
   TreeLogger logger)
{
   String componentPath = getDependenciesPrevious().get(tagName);
   if (componentPath == null)
   {
      int idx = tagName.indexOf('.');
      if (idx > 0)
      {
         //try
         //{
            componentPath = getNodeModuleJavascript(tagName, logger);
         //}
         //catch(IllegalStateException e)
         //{
         //   throw new IllegalStateException(
         //      "Cannot find component for <" + tagName + ">: "
         //    + "did you forget to install a node module?");
         //}
         //catch(Exception e)
         //{
         //   throw new RuntimeException(e);
         //}
      }
   }
   return(componentPath);
}
/*------------------------------------------------------------------------------

@name       getLibraryDir - get library directory
                                                                              */
                                                                             /**
            Get library directory.

@return     library directory

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static File getLibraryDir(
   TreeLogger logger)
{
   File projectDir = getProjectDirectory(null, logger);
   File libraryDir = new File(projectDir, "lib");
   if (!libraryDir.exists() || !libraryDir.isDirectory())
   {
      libraryDir = getWarLibraryDir(logger);
   }
   return(libraryDir);
}
/*------------------------------------------------------------------------------

@name       getModuleDir - get artifact module directory
                                                                              */
                                                                             /**
            Get artifact module directory.

@return     artifact module directory.

@param      logger      any logger

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static File getModuleDir(
   TreeLogger logger)
   throws     FileNotFoundException
{
   File moduleDir   = null;
   File artifactDir = getArtifactDir(logger);
   for (File candidate : artifactDir.listFiles())
   {
      String candidateName = candidate.getName();
      if (candidate.isHidden())
      {
         continue;
      }
      if (!candidate.isDirectory())
      {
         continue;
      }
      if ("WEB-INF".equals(candidateName))
      {
         continue;
      }
      for (File child : candidate.listFiles())
      {
         if ("reactjava".equals(child.getName()))
         {
            moduleDir = candidate;
            break;
         }
      }
      if (moduleDir != null)
      {
         break;
      }
   }
   if (moduleDir == null)
   {
      throw new FileNotFoundException("Cannot find module directory");
   }

   return(moduleDir);
}
/*------------------------------------------------------------------------------

@name       getNodeModuleCSS - get node module css path
                                                                              */
                                                                             /**
            Get node module css path specified module name.

@return     node module css path specified module name

@param      module            node module css name
@param      configuration     configuration
@param      logger            logger

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static String getNodeModuleCSS(
   String         module,
   IConfiguration configuration,
   TreeLogger     logger)
   throws         Exception
{
   String nodeModulePath = null;

   if (!module.endsWith(".css"))
   {
      throw new IllegalArgumentException("Target must end with '.css'");
   }

   String css = module.substring(0, module.lastIndexOf('.'));
          css = css.replace(".", "/");
          css = css + ".css";

   if (!configuration.getGlobalCSS().contains(css))
   {
      File stylesheet = new File(getNodeModulesDir(logger), css);
      if (!stylesheet.exists())
      {
         throw new IllegalStateException(
            "Cannot find node module css " + module + ": "
          + "did you forget to install it?"
          + " " + stylesheet.getAbsolutePath());
      }
      else
      {
         nodeModulePath = stylesheet.getAbsolutePath();

         logger.log(
            logger.DEBUG,
            "jsx.IConfiguration.getNodeModuleCSS(): module=" + module
          + ", cssPath=" + nodeModulePath);
      }
   }
   return(nodeModulePath);
}
/*------------------------------------------------------------------------------

@name       getNodeModuleJavascript - get node module javascript path
                                                                              */
                                                                             /**
            Get node module javascript path specified module name.

@return     node module javascript path specified module name

@param      module      node module name
@param      logger      any logger

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static String getNodeModuleJavascript(
   String     module,
   TreeLogger logger)
{
   String nodeModulePath = getDependenciesPrevious().get(module);
   //if (nodeModulePath == null) example pdfjs-dist causes problems here
   //{
   //   nodeModulePath = getNodeModuleJavascriptFromWebpack(module, logger);
   //}
   if (nodeModulePath == null)
   {
      nodeModulePath = getNodeModuleJavascriptFromPackageJSON(module, logger);
   }
   if (nodeModulePath == null)
   {
      File   nodeModules = getNodeModulesDir(logger);
      File   moduleDir   = nodeModules;
      String filename    = module;
                                       // chase the module directory          //
      for (int idx = module.indexOf('.');
            idx > 0;
            idx = module.indexOf('.', idx + 1))
      {
         String relPath = module.substring(0, idx).replace(".", "/");
         File   chase   = new File(nodeModules, relPath);
         if (!chase.isDirectory())
         {
            break;
         }
         moduleDir = chase;
         filename  = module.substring(idx + 1);
      }

      logger.log(
         logger.INFO,
         "jsx.IConfiguration.getNodeModuleJavascript(): module=" + module
       + ", moduleDir=" + moduleDir
       + ", filename="  + filename);

      File descendant = getDescendantJavascript(moduleDir, filename, logger);
      //if (descendant == null)
      //{
      //   throw new IllegalStateException(
      //      "Cannot find node module " + module + ": "
      //    + "did you forget to install it?");
      //}
      //else
      if (descendant != null)
      {
         nodeModulePath = descendant.getAbsolutePath();

         logger.log(
            logger.INFO,
            "jsx.IConfiguration.getNodeModuleJavascript(): module=" + module
          + ", componentPath=" + nodeModulePath);
      }
   }
   return(nodeModulePath);
}
/*------------------------------------------------------------------------------

@name       getNodeModuleCSSFromPackageJSON - get node module css item
                                                                              */
                                                                             /**
            Get node module css item.

@return     browserify inject css item

@param      module      corresponding node module name.

@history    Wed Dec 12, 2018 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getNodeModuleCSSFromPackageJSON(
   String     module,
   TreeLogger logger)
   throws     Exception
{
   return(getNodeModuleTargetFromPackageJSON(module, false, logger));
}
/*------------------------------------------------------------------------------

@name       getNodeModuleJavascriptFromPackageJSON - get node module export item
                                                                              */
                                                                             /**
            Get node module export item.

@return     browserify inject script item

@param      module      corresponding node module name.

@history    Wed Dec 12, 2018 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getNodeModuleJavascriptFromPackageJSON(
   String     module,
   TreeLogger logger)
{
   return(getNodeModuleTargetFromPackageJSON(module, true, logger));
}
/*------------------------------------------------------------------------------

@name       getNodeModuleJavascriptFromPackageJSON - get node module export item
                                                                              */
                                                                             /**
            Get node module export item.  The specified module is either just
            the module name, or a relative path to a specific item.

            If the specified module is a relative path to a specific item, and
            if the item is found, that is returned as the target.

            Otherwise the specified module is searched for a 'package.json'
            file, and if found, the 'main' entry is found for a javascript
            file, and the 'style' entry is found for a css file. If that is
            found, it is returned as the target.

@return     browserify inject script item

@param      module      corresponding node module name or relative path.

@history    Wed Dec 12, 2018 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getNodeModuleTargetFromPackageJSON(
   String     module,
   boolean    bJavascript,
   TreeLogger logger)
{
   String  exportItem     = null;
   File    nodeModulesDir = IConfiguration.getNodeModulesDir(logger);
   File    target         = null;
   String  json           = null;
   String  relPath        = module.indexOf('/') > 0 ? module : null;
   if (relPath != null)
   {
                                       // explicit path specification ------- //
      logger.log(
         logger.INFO,
         "IConfiguration.getNodeModuleTargetFromPackageJSON(): "
       + "target relative path specified explicitly=" + module);

      if (bJavascript && !module.endsWith(".js"))
      {
         module += ".js";
      }
      else if (!bJavascript && !module.endsWith(".css"))
      {
         module += ".css";
      }

      target = new File(nodeModulesDir, module);
   }
   else
   {
                                       // module name specification --------- //
      logger.log(
         logger.INFO,
         "IConfiguration.getNodeModuleTargetFromPackageJSON(): "
       + "specified module name=" + module);

      File nodeModuleDir  = new File(nodeModulesDir, module);

                                       // get any package.json 'main' entry   //
      File pkgJson = new File(nodeModuleDir, "package.json");
      if (pkgJson.exists())
      {
         try
         {
            json = IJSXTransform.getFileAsString(pkgJson, logger);
         }
         catch(Exception e)
         {
            throw new IllegalStateException(e);
         }
      }
      if (json != null)
      {
                                       // module name specification (cont)    //
         String key = bJavascript ? "main" : "style";
         try
         {
            relPath = (String)((JSONObject)new JSONParser().parse(json)).get(key);
         }
         catch(Exception e)
         {
                                       // ignore                              //
         }
      }

      if (relPath != null)
      {
         File file = new File(nodeModuleDir, relPath);

         logger.log(
            logger.INFO,
            "IConfiguration.getNodeModuleTargetFromPackageJSON(): "
          + "checking package.json for " + file.getAbsolutePath());

         if (file.exists())
         {
            target = file;

            logger.log(
               logger.INFO,
               "IConfiguration.getNodeModuleTargetFromPackageJSON(): "
                  + target.getAbsolutePath() + " found");
         }
      }

      if (bJavascript && target == null)
      {
         String item = IConfiguration.toReactAttributeName(module, logger);

         String[] filenames = {"index.js", "default.js", item};
         for (int i = 0; i < filenames.length; i++)
         {
            File file = new File(nodeModuleDir, filenames[i]);

            logger.log(
               logger.INFO,
               "IConfiguration.getNodeModuleTargetFromPackageJSON(): "
             + "checking for " + file.getAbsolutePath());

            if (file.exists())
            {
               target = file;
               break;
            }
         }
      }
      if (bJavascript && target == null)
      {
         target = new File(nodeModuleDir + ".js");

         logger.log(
            logger.INFO,
            "IConfiguration.getNodeModuleTargetFromPackageJSON(): checking for "
          + target.getAbsolutePath());
      }
   }

   if (target != null && target.exists())
   {
      logger.log(
         logger.INFO,
         "IConfiguration.getNodeModuleTargetFromPackageJSON(): target="
            + target.getAbsolutePath());

      String suffix = bJavascript ? ".js" : ".css";

      String exportName = target.getName();
      if (exportName.endsWith(suffix))
      {
         exportName = exportName.substring(0, exportName.length() - 3);
      }
      exportItem = target.getAbsolutePath();
   }

   logger.log(
      logger.INFO,
      "IConfiguration.getNodeModuleTargetFromPackageJSON(): for module="
         + module + ", exportItem=" + exportItem);

   return(exportItem);
}
/*------------------------------------------------------------------------------

@name       getNodeModuleJavascriptFromWebpack - get node module export item
                                                                              */
                                                                             /**
            Get node module export item from any module webpack.js file.

@return     browserify inject script item

@param      module      corresponding node module name.

@history    Wed Dec 12, 2018 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getNodeModuleJavascriptFromWebpack(
   String     module,
   TreeLogger logger)
{
   return(getNodeModuleTargetFromWebpack(module, true, logger));
}
/*------------------------------------------------------------------------------

@name       getNodeModuleTargetFromWebpack - get node module export item
                                                                              */
                                                                             /**
            Get node module export item from any module webpack.js.

@return     browserify inject script item

@param      module      corresponding node module name or relative path.

@history    Wed Dec 12, 2018 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getNodeModuleTargetFromWebpack(
   String     module,
   boolean    bJavascript,
   TreeLogger logger)
{
   String  exportItem     = null;
   File    nodeModulesDir = IConfiguration.getNodeModulesDir(logger);

   if (!bJavascript)
   {
      throw new UnsupportedOperationException("Many are called...");
   }
   else
   {
                                       // module name specification --------- //
      logger.log(
         logger.INFO,
         "IConfiguration.getNodeModuleTargetFromWebpack(): "
       + "specified module name=" + module);

      File nodeModuleDir  = new File(nodeModulesDir, module);

                                       // get any webpack.js                  //
      File webpack = new File(nodeModuleDir, "webpack.js");
      if (webpack.exists())
      {
         exportItem = webpack.getAbsolutePath();

         logger.log(
            logger.INFO,
            "IConfiguration.getNodeModuleTargetFromWebpack(): "
               + exportItem + " found");
      }
   }

   logger.log(
      logger.INFO,
      "IConfiguration.getNodeModuleTargetFromWebpack(): for module="
         + module + ", exportItem=" + exportItem);

   return(exportItem);
}
/*------------------------------------------------------------------------------

@name       getNodeModulesDir - get node modules directory
                                                                              */
                                                                             /**
            Get node modules directory.

@return     node modules directory

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static File getNodeModulesDir(
   TreeLogger logger)
{
   File nodeModulesDir = getProjectDirectory("node_modules", logger);

   if (nodeModulesDir == null)
   {
      throw new IllegalStateException(
         "Node modules directory not found. Did you install node "
       + "and use npm to install ReactJava?");
   }
   return(nodeModulesDir);
}
/*------------------------------------------------------------------------------

@name       getOSWindows - get whether os is windows
                                                                              */
                                                                             /**
            Get whether os is windows.

@return     true iff os is windows

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static boolean getOSWindows()
{
   return(System.getProperty("os.name").toLowerCase().contains("windows"));
}
/*------------------------------------------------------------------------------

@name       getPlatform - get platform
                                                                              */
                                                                             /**
            Get platform.

@return     platform.

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IPlatform getPlatform(
   TreeLogger logger)
{
   if (platform[0] == null)
   {
      platform[0] = initPlatform(logger);
   }
   return(platform[0]);
}
/*------------------------------------------------------------------------------

@name       getPlatformIsWeb - get platform is web
                                                                              */
                                                                             /**
            Get platform is web.

@return     true iff platform is null or web.

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static boolean getPlatformIsWeb(
   String     platform,
   TreeLogger logger)
{
   return(
      platform == null
         || IPlatform.kPLATFORM_WEB.equals(getPlatform(logger).getOS()));
}
/*------------------------------------------------------------------------------

@name       getProjectDirectory - get project directory
                                                                              */
                                                                             /**
            Get project directory with specified name.

@return     project directory with specified name.

@param      name           project directory name.
@param      logger         logger.

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
static File getProjectDirectory(
   String     name,
   TreeLogger logger)
{
   return(getProjectDirectory(name, null, logger));
}
/*------------------------------------------------------------------------------

@name       getProjectDirectory - get project directory
                                                                              */
                                                                             /**
            Get project directory with specified name.

@return     project directory with specified name or null if not found.

@param      name           project directory name.
@param      path           path at which to start search.
@param      logger         logger.

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
static File getProjectDirectory(
   String     name,
   String     path,
   TreeLogger logger)
{
   File projectDir = null;
   if (path == null)
   {
                                    // find the project path                  //
      String classpath = System.getProperty("java.class.path");
      int    idx       = classpath.indexOf(toOSPath("/out"));
      if (idx > 0)
      {
         if (logger != null)
         {
            logger.log(
               logger.DEBUG,
               "jsx.IConfiguration.getProjectDirectory(): classpath=" + classpath);
         }

         path =
            classpath.substring(
               classpath.lastIndexOf(getOSWindows() ? ';' : ':', idx) + 1,
               idx + 1);
      }
      else
      {
         if (logger != null)
         {
            logger.log(
               logger.WARN,
               "jsx.IConfiguration.getProjectDirectory(): "
             + "assuming working directory is project directory");
         }

         path = System.getProperty("user.dir");
      }
      if (logger != null)
      {
         logger.log(
            logger.DEBUG,
            "jsx.IConfiguration.getProjectDirectory(): project path=" + path);
      }
   }
   if (name == null)
   {
      projectDir = new File(path);
   }
   else
   {
      File[] children = new File(path).listFiles();
      if (children != null)
      {
         for (File dir : children)
         {
            String candidate = dir.getName();
            if (!dir.isDirectory()
                  || dir.isHidden()
                  || !name.equals(candidate))
            {
               continue;
            }

            projectDir = dir;

            if (logger != null)
            {
               logger.log(
                  logger.DEBUG,
                  "jsx.IConfiguration.getProjectDirectory(): "
                     + name + " directory=" + dir.getAbsolutePath());
            }
            break;
         }
         if (projectDir == null)
         {
            for (File dir : children)
            {
                                          // chase the children                  //
               projectDir =
                  getProjectDirectory(name, dir.getAbsolutePath(), logger);
               if (projectDir != null)
               {
                  break;
               }
            }
         }
      }
   }
   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       getWarDir - get war directory
                                                                              */
                                                                             /**
            Get war brary directory.

@return     war library directory or null if not found

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static File getWarDir(
   TreeLogger logger)
{
   File war        = null;
   File projectDir = getProjectDirectory(null, logger);
   File chase      = new File(projectDir, "war");
   if (!chase.exists())
   {
      chase = new File(projectDir, "web");
   }
   if (chase.exists())
   {
      war = chase;
   }
   return(war);
}
/*------------------------------------------------------------------------------

@name       getWarLibraryDir - get war library directory
                                                                              */
                                                                             /**
            Get war library directory.

@return     war library directory

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static File getWarLibraryDir(
   TreeLogger logger)
{
   File libraryDir = null;
   File war        = getWarDir(logger);
   if (war != null)
   {
      File chase = getProjectDirectory("lib", war.getAbsolutePath(), logger);
      if (chase.exists())
      {
         libraryDir = chase;
      }
   }
   return(libraryDir);
}
/*------------------------------------------------------------------------------

@name       initPlatform - initialize platform
                                                                              */
                                                                             /**
            Initialize platform.

@return     platform.

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IPlatform initPlatform(
   TreeLogger logger)
{
   IPlatform platform = null;
                                       // check for a system property value   //
   String sPlatform = System.getProperty("platform");
   if (sPlatform == null)
   {
                                       // check for a 'platform' entry in any //
                                       // 'package.json' file                 //
      File packageJSONFile =
         new File(
            IConfiguration.getProjectDirectory(null, logger), "package.json");

      if (packageJSONFile.exists())
      {
         try
         {
            String jsonStr =
               IJSXTransform.getFileAsString(packageJSONFile, logger);

         //JsonParser  parser     = new JsonParser();
         //JsonObject  jsonObject = parser.parse(jsonStr).getAsJsonObject();
         //JsonElement jsonElem   = jsonObject.get("platform");
         //if (jsonElem != null)
         //{
         //   sPlatform = jsonElem.getAsString();
         //}
                                       // instead of being dependant upon Gson//
            jsonStr =
               jsonStr.substring(
                  jsonStr.indexOf('{') + 1, jsonStr.lastIndexOf('}'));

            for (String element : jsonStr.split(","))
            {
               String[] tokens = element.trim().split(":");
               if (tokens.length != 2)
               {
                  continue;
               }
               if (!"\"platform\"".equals(tokens[0].trim()))
               {
                  continue;
               }

               sPlatform = tokens[1].replace("\"", "").trim();
            }
         }
         catch(IOException e)
         {
            logger.log(
               logger.WARN,
               "jsx.IConfiguration.initPlatform(): package.json not found");
         }
      }
   }
   if (sPlatform == null || IPlatform.kPLATFORM_WEB.equals(sPlatform))
   {
      platform = new PlatformWeb();
   }
   else if (IPlatform.kPLATFORM_IOS.equals(sPlatform))
   {
      platform = new PlatformIOS();
   }
   else if (IPlatform.kPLATFORM_ANDROID.equals(sPlatform))
   {
      platform = new PlatformAndroid();
   }
   else if (!IPlatform.kPLATFORMS.contains(sPlatform))
   {
      throw new UnsupportedOperationException(
         "Unknown platform=" + sPlatform);
   }
   if (logger != null)
   {
      logger.log(
         logger.DEBUG,
         "jsx.IConfiguration.initPlatform(): platform=" + sPlatform);
   }

   return(platform);
}
/*------------------------------------------------------------------------------

@name       isES6Syntax - test whether specified string contains ES6 syntax
                                                                              */
                                                                             /**
            Test whether specified string contains ES6 syntax.

            This implementation simply looks for the beginning of a line
            starting with the word "import" or "export", neither of which is
            allowed in ES5.

@return     True iff specified string contains ES6 syntax

@param      target      target string

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static boolean isES6Syntax(
   String target)
{
   boolean bES6Syntax =
      target.split(kREGEX_IMPORT, 2).length > 1
         || target.split(kREGEX_EXPORT, 2).length > 1;

   return(bES6Syntax);
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
      System.out.println(
         "Node module path ="
            + getNodeModuleJavascript(args[0], new PrintWriterTreeLogger()));
   }
   catch(Exception e)
   {
      e.printStackTrace();
   }
}
/*------------------------------------------------------------------------------

@name       toOSPath - convert specified file path for current OS
                                                                              */
                                                                             /**
            Convert specified file path for current OS.


@return     specified file path for current OS.

@param      raw      raw file path in unix style

@history    Fri May 20, 2016 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
static String toOSPath(
   String raw)
{
   return(getOSWindows() ? raw.replace ("/", File.separator) : raw);
}
/*------------------------------------------------------------------------------

@name       toReactAttributeName - convert attribute name to react syntax
                                                                              */
                                                                             /**
            Replace all '-x' sequences to 'X' or if does not contain a '-'
            character, replace all '.x' sequences to 'X'

@return     The specified attribute name in react syntax.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes      A regex version of this is needed...
                                                                              */
//------------------------------------------------------------------------------
public static String toReactAttributeName(
   String     attributeName,
   TreeLogger logger)
{
   String reactName = "class".equals(attributeName) ? "className" : attributeName;
   String delim     = reactName.indexOf('-') > 0 ? "-" : "\\.";
   String parts[]   = reactName.split(delim);

   if (parts.length > 1)
   {
      StringBuffer buf = new StringBuffer(parts[0]);
      for (int i = 1; i < parts.length; i++)
      {
         String part = parts[i];
         buf.append(part.substring(0, 1).toUpperCase() + part.substring(1));
      }
      reactName = buf.toString();
   }

   return(reactName);
}
}//====================================// end IConfiguration =================//
