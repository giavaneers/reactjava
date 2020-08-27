/*==============================================================================

name:       AppComponentInspector - ReactJava GWT compiler pre processor

purpose:    Compiles the target application with reactjava component proxy
            classes in order to instantiate and interrogate the app component
            for various properties needed at compile time, such as any
            imported node modules.

history:    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.codegenerator;
                                       // imports --------------------------- //
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.util.log.PrintWriterTreeLogger;
import io.reactjava.client.core.react.AppComponentTemplate;
import io.reactjava.client.core.react.SEOInfo;
import io.reactjava.codegenerator.IPreprocessor.TypeDsc;
import io.reactjava.jsx.IConfiguration;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
                                       // AppComponentInspector ============= //
public class AppComponentInspector
{
                                       // constants ------------------------- //
public static final String   kOUT_INSPECTOR_SRC_PATH =
   IConfiguration.toOSPath("out/build/reactjava/inspector/src/");

public static final String   kPROXY_BASE_PATH =
   "io/reactjava/client/core/react/";

public static final String[] kPROXIES =
{
   "AppComponentTemplate.proxy",
   "Component.proxy",
   "NativeObject.proxy",
   "Properties.proxy"
};
                                       // class variables ------------------- //
protected static String[] parsedSplits;// parsed splits                       //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       compileInspector - get imported node modules
                                                                              */
                                                                             /**
            Get imported node modules.

@param      classpath         classpath
@param      appClassnames     app classname
@param      logger            logger

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void compileInspector(
   String       classpath,
   List<String> appClassnames,
   TreeLogger   logger)
   throws       Exception
{
   File         projectDir = IConfiguration.getProjectDirectory(null, null);
   File         srcDir     = new File(projectDir, "src");
   List<String> srcFiles   = new ArrayList<>();
                                       // add each of the app sources         //
   for (String classname : appClassnames)
   {
      File srcFile = new File(srcDir, classname.replace(".","/") + ".java");
      srcFiles.add(srcFile.getAbsolutePath());
   }

   String inspectorSrcPath =
      new File(projectDir, kOUT_INSPECTOR_SRC_PATH).getAbsolutePath();

                                       // copy the proxy sources              //
   for (String proxy : kPROXIES)
   {
      String path    = kPROXY_BASE_PATH + proxy.replace(".proxy",".java");
      File   dstFile = new File(inspectorSrcPath, path);
             dstFile.delete();
             dstFile.getParentFile().mkdirs();

      IConfiguration.fastChannelCopy(
         AppComponentInspector.class.getResourceAsStream(proxy),
         new FileOutputStream(dstFile));

      srcFiles.add(dstFile.getAbsolutePath());
   }
                                       // compile the app component with the  //
                                       // proxy and reactjava.jar             //
   List<String> commandList = new ArrayList<>();
   commandList.addAll(
      Arrays.asList(
         new String[]{"javac","-cp",classpath, "-d", inspectorSrcPath}));
   commandList.addAll(srcFiles);

   String[] commands = commandList.toArray(new String[commandList.size()]);
   Process  process  = new ProcessBuilder(commands).start();

   ByteArrayOutputStream err = new ByteArrayOutputStream();
   IConfiguration.fastChannelCopy(process.getErrorStream(), err);

   if (process.waitFor() != 0)
   {
      String errMsg = new String(err.toByteArray(), "UTF-8");
      throw new IllegalStateException(
         "Inspector compilation returned error: " + errMsg);
   }
}
/*------------------------------------------------------------------------------

@name       getCompileClasspath - get compile classpath
                                                                              */
                                                                             /**
            Get compile classpath.

@return     compile classpath

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getCompileClasspath()
{
   File projectDir      = IConfiguration.getProjectDirectory(null, null);
   File inspectorSrcDir = new File(projectDir, kOUT_INSPECTOR_SRC_PATH);
   inspectorSrcDir.mkdirs();

   String classpath =
      inspectorSrcDir.getAbsolutePath()
      + File.pathSeparator + System.getProperty("java.class.path");

   return(classpath);
}
/*------------------------------------------------------------------------------

@name       getAppInfoByTypeDsc - get app info
                                                                              */
                                                                             /**
            Get imported node modules, embedded resources and page hashes.
            Typically invoked by the ReactCodeGenerator at GWT compile time.

@return     collection of node module names to be imported by the target app,
            embedded resource names, and page hashes.

@param      app                        app
@param      providersAndComponents     providers and components
@param      logger                     logger

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getAppInfoByTypeDsc(
   TypeDsc                app,
   Map<String,TypeDsc>    providersAndComponents,
   TreeLogger             logger)
   throws                 Exception
{
   String appClassname = IPreprocessor.getClassname(app.type);

   logger.log(
      logger.INFO,
         "AppComponentInspector.getAppInfoByTypeDsc(): "
       + "entered with appClassname=" + appClassname);

   List<String> appClassnames = new ArrayList<>();
   File         projectDir    = IConfiguration.getProjectDirectory("src", logger);

                                       // the app classname is first          //
   appClassnames.add(appClassname);

   for (TypeDsc classType : providersAndComponents.values())
   {
      String classname = IPreprocessor.getClassname(classType.type);
      if (!appClassname.equals(classname))
      {
                                       // verify the source exists and is not //
                                       // an inner class of another source    //
         String pathname = toPathname(classname) + ".java";
         if (new File(projectDir, pathname).exists())
         {
            logger.log(
               logger.INFO,
               "AppComponentInspector.getAppInfoByTypeDsc(): "
             + "adding " + classname);

            appClassnames.add(classname);
         }
      }
   }

   return(getAppInfo(appClassnames, logger));
}
/*------------------------------------------------------------------------------

@name       getAppInfoByJClassType - app info
                                                                              */
                                                                             /**
            Get imported node modules, embedded resources and page hashes.
            Typically invoked by the ReactCodeGenerator at GWT compile time.

@return     collection of node module names to be imported by the target app,
            embedded resource names, and page hashes.

@param      appClassname      app classname
@param      appTypes          app types
@param      logger            logger

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getAppInfoByJClassType(
   String                 appClassname,
   Map<String,JClassType> appTypes,
   TreeLogger             logger)
   throws                 Exception
{
   logger.log(
      logger.INFO,
      "AppComponentInspector.getAppInfoByJClassType(): "
    + "entered with appClassname=" + appClassname);

   List<String> appClassnames = new ArrayList<>();
   File         projectDir    = IConfiguration.getProjectDirectory("src", logger);

                                       // the app classname is first          //
   appClassnames.add(appClassname);

   for (JClassType classType : appTypes.values())
   {
      String classname = classType.getQualifiedSourceName();
      if (!appClassname.equals(classname))
      {
                                       // verify the source exists and is not //
                                       // an inner class of another source    //
         String pathname = toPathname(classname) + ".java";
         if (new File(projectDir, pathname).exists())
         {
            logger.log(
               logger.INFO,
               "AppComponentInspector.getAppInfoByJClassType(): "
             + "adding " + classname);

            appClassnames.add(classname);
         }
      }
   }

   return(getAppInfo(appClassnames, logger));
}
/*------------------------------------------------------------------------------

@name       getAppInfo - get app info
                                                                              */
                                                                             /**
            Get imported node modules, embedded resources and page hashes..

@return     collection of node module names, embedded resource names and page
            hashes

@param      appClassnames     app classnames
@param      logger            logger

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getAppInfo(
   List<String> appClassnames,
   TreeLogger   logger)
   throws       Exception
{
   String msg = "";
   for (String appClassname : appClassnames)
   {
      msg += (msg.length() > 0 ? ", " : "") + appClassname;
   }

   msg =
      "AppComponentInspector.getAppInfo(): entered with appClassnames=" + msg;

   logger.log( logger.INFO, msg);
                                       // clear any parsed splits             //
   parsedSplits = null;
                                       // the app classname is first          //
   String appClassname = appClassnames.get(0);
   String classpath    = getCompileClasspath();

                                       // compile the component sources       //
   compileInspector(classpath, appClassnames, logger);

                                       // execute the proxy using the cp      //
                                       // capturing the result                //

   String   mainClass = AppComponentTemplate.class.getName();
   String[] commands  = {"java", "-cp", classpath, mainClass, appClassname};
   Process  process   = new ProcessBuilder(commands).start();

   ByteArrayOutputStream out = new ByteArrayOutputStream();
   IConfiguration.fastChannelCopy(process.getInputStream(), out);

   process.waitFor();

   String result = new String(out.toByteArray(), "UTF-8").trim();

   logger.log(
      logger.INFO,
      "AppComponentInspector.getAppInfo(): result=" + result);

   if (!result.contains(SEOInfo.kDELIMITER))
   {
      throw new IllegalStateException(
         "AppComponentInspector.getAppInfo(): result indicates error");
   }

   return(result);
}
/*------------------------------------------------------------------------------

@name       getParsedAnalyticsId - get parsed google analytics id
                                                                              */
                                                                             /**
            Get parsed google analytics id.

@return     parsed google analytics id

@param      appInfo     app info

@history    Fri Aug 21, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getParsedAnalyticsId(
   String     appInfo,
   TreeLogger logger)
{
   if (parsedSplits == null)
   {
      parse(appInfo, logger);
   }

   String googleAnalyticsId = parsedSplits[0];
   if (googleAnalyticsId != null && googleAnalyticsId.length() > 0)
   {
      googleAnalyticsId = googleAnalyticsId.split(",")[0].trim();
   }
   return(googleAnalyticsId);
}
/*------------------------------------------------------------------------------

@name       getParsedEmbeddedResources - get parsed embedded resources
                                                                              */
                                                                             /**
            Get parsed embedded resources.

@return     parsed embedded resources

@history    Fri Aug 21, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Collection<String> getParsedEmbeddedResources(
   String     appInfo,
   TreeLogger logger)
{
   if (parsedSplits == null)
   {
      parse(appInfo, logger);
   }

   String splitResources = parsedSplits[2];

   Collection<String> embeddedResources = new ArrayList<>();
   if (splitResources != null && splitResources.length() > 0)
   {
      for (String split : splitResources.split(","))
      {
         split = split.trim();
         if (split.length() > 0)
         {
            embeddedResources.add(split);
         }
      }
   }

   return(embeddedResources);
}
/*------------------------------------------------------------------------------

@name       getParsedImportedNodeModules - get imported node modules
                                                                              */
                                                                             /**
            Get parsed imported node modules.

@return     imported node modules

@history    Fri Aug 21, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Collection<String> getParsedImportedNodeModules(
   String     appInfo,
   TreeLogger logger)
{
   if (parsedSplits == null)
   {
      parse(appInfo, logger);
   }

   String splitModules = parsedSplits[1];

   Collection<String> importedModules = new ArrayList<>();
   if (splitModules != null && splitModules.length() > 0)
   {
      for (String split : splitModules.split(","))
      {
         split = split.trim();
         if (split.length() > 0)
         {
            importedModules.add(split);
         }
      }
   }
   return(importedModules);
}
/*------------------------------------------------------------------------------

@name       getParsedSEOInfo - get parsed seo info
                                                                              */
                                                                             /**
            Get parsed seo info.

@return     seo info

@history    Fri Aug 21, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getParsedSEOInfo(
   String     appInfo,
   TreeLogger logger)
{
   if (parsedSplits == null)
   {
      parse(appInfo, logger);
   }

   return(parsedSplits[3]);
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
      long start = System.currentTimeMillis();

      String importedModules =
         AppComponentInspector.getAppInfo(
            new ArrayList<>(Arrays.asList(args)), new PrintWriterTreeLogger());

      System.out.println("Exiting, duration=" + (System.currentTimeMillis() - start));
      System.out.println("modules=" + importedModules);
   }
   catch(Exception e)
   {
      e.printStackTrace();
   }
}
/*------------------------------------------------------------------------------

@name       parse - parse response string
                                                                              */
                                                                             /**
            Parse response string, returning splits, where

            splits[0]      google analytics id
            splits[1]      imported node modules
            splits[2]      embedded resources
            splits[3]      seo

@param      appInfo     app info response string
@param      logger      logger

@history    Fri Aug 21, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static void parse(
   String     appInfo,
   TreeLogger logger)
{
   logger.log( TreeLogger.INFO, "ReactCodePackager.parse(): appInfo=" + appInfo);

   if (parsedSplits == null)
   {
                                       // remove wrapping brackets            //
      appInfo = appInfo.substring(1);
      appInfo = appInfo.substring(0, appInfo.length() - 1);

      logger.log(
         TreeLogger.INFO, "ReactCodePackager.parse(): unwrapped=" + appInfo);

                                       // separate the google analytics id    //
                                       // from the imported modules           //
                                       // from the embedded resources         //
                                       // from the seo info                   //
      parsedSplits = appInfo.split("<delimiter>");

      logger.log(
         TreeLogger.INFO,
         "ReactCodePackager.parse(): splits.length=" + parsedSplits.length);
   }
}
/*------------------------------------------------------------------------------

@name       toClassname - get classname from pathname
                                                                              */
                                                                             /**
            Get classname from pathname.

@return     classname from pathname

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String toClassname(
   String pathname)
{
   return(pathname.replace("/","."));
}
/*------------------------------------------------------------------------------

@name       toPathname - get pathname from classname
                                                                              */
                                                                             /**
            Get pathname from classname.

@return     pathname from classname

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String toPathname(
   String classname)
{
   return(classname.replace(".","/"));
}
}//====================================// end AppComponentInspector ==========//
