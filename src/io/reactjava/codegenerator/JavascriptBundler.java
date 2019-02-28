/*==============================================================================

name:       JavascriptBundler - ReactJava GWT compiler pst process0r

purpose:    Various utilities to post process for ReactJava native.

            This implementation generates a webpack compatible javascript
            library of the core compiler output and stores it in the project
            war directory.

history:    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.codegenerator;
                                       // imports --------------------------- //
import com.giavaneers.net.cloudstorage.GoogleCloudStorageManagerBase;
import io.reactjava.client.core.providers.platform.IPlatform;
import io.reactjava.jsx.IConfiguration;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static java.util.stream.Collectors.toList;
                                       // JavascriptBundler ==================//
public class JavascriptBundler
{
                                       // class constants --------------------//
public static final boolean kSRCCFG_NODE_MODULES_IN_PROJECT   = true;
public static final boolean kSRCCFG_PROJECT_TEMPLATE_RESOURCE = true;

public static final String kPROJECT_TEMPLATE_NAME =
   "ProjectTemplate";

public static final String kTEMPLATE_PROJECT_FILENAME =
   "ProjectTemplate.iml";

public static final String kSERVICE_ACCOUNT_ID =
   "reactjavagcsserviceaccount@reactjava-218300.iam.gserviceaccount.com";

public static final String kSERVICE_ACCOUNT_PRIVATE_KEY_NAME =
   "ReactJavaServiceAccountCredentials.json";

public static final String kRELEASE_BUCKET_NAME =
   "reactjava.io";

public static final String kREACT_JAVA_RELEASE_DOWNLOAD_BUCKET_URL =
   "https://storage.googleapis.com/" + kRELEASE_BUCKET_NAME + "/";

public static final String kREACT_JAVA_RELEASES_DOWNLOAD_URL =
   kREACT_JAVA_RELEASE_DOWNLOAD_BUCKET_URL + "releases/";

public static final String kREACT_JAVA_RELEASES_LATEST_DOWNLOAD_URL =
   kREACT_JAVA_RELEASES_DOWNLOAD_URL + "latest/";

public static final String kREACT_JAVA_DOWNLOAD_URL_CONTAINER_URL =
   kREACT_JAVA_RELEASES_LATEST_DOWNLOAD_URL + "latestReactJavaRelease.txt";

public static final String kREACT_JAVA_GWT_RELEASE_NAME =
   "core-2.8.2.zip";

public static final String kREACT_JAVA_DOWNLOAD_GWT_RELEASE_URL =
   kREACT_JAVA_RELEASE_DOWNLOAD_BUCKET_URL
      + "releases/core/" + kREACT_JAVA_GWT_RELEASE_NAME;

public static final String kDOWNLOAD_URL_INTELLIJ_HELLOWORLD_PROJECT_TEMPLATE =
   kREACT_JAVA_RELEASE_DOWNLOAD_BUCKET_URL
      + "releases/projectTemplates/HelloWorld/IntelliJ/"
      + kPROJECT_TEMPLATE_NAME + ".zip";

public static final String kAPPLICATION_NAME =
   "reactJavaDeploy";

public static final String kOUT_BUILD_SRC_PATH =
   toOSPath("out/build/reactnative/src");

public static final String kOUT_BUILD_LOGS_PATH =
   toOSPath("out/build/logs");

public static final String kREACT_JAVA_JAR_NAME =
   "reactjava.jar";

public static final List<String> kNO_RELAUNCH =
   new ArrayList<String>()
   {{
      add("buildgwtdevjar");
      add("buildrelease");
      add("creategwtlibrary");
      add("deletetempgwtcache");
      add("updatejarmanifest");
      add("updateprojecttemplateresource");
      //add("jsxtransform");
      add("platformios");
      add("platformweb");
      add("shadowtosrc");
      add("srctoshadow");
   }};

public static final List<String> kOPCODES =
   new ArrayList<String>()
   {{
      addAll(kNO_RELAUNCH);
      add("createproject");
      add("publishgwtsdk");
      add("publishjar");
      add("update");
   }};

public static final Set<String> kREACT_NATIVE_KEEP_FILENAMES =
   new HashSet<String>(Arrays.asList(new String[]
   {
      "android",
      "app.json",
      "ios",
      "node_modules",
   }));

public static final String kGWT_LIBRARY_NAME = "reactNativeGWTLibrary.js";

public static final String kGWT_LIBRARY_DELIM_BEG =
   "var $intern_0";

public static final String kGWT_LIBRARY_DELIM_END =
   "com_google_gwt_lang_ModuleUtils_addInitFunctions__V(com_google_gwt_lang";

public static final String kGWT_LIBRARY_PREFIX =
   "import React from 'react';\n"
 + "import {Text,View,Button} from 'react-native';\n"
 + "\n"
 + "(function (root)\n"
 + "{\n"
 + "   'use strict';\n"
 + "\n"
 + "   function getExports($wnd)\n"
 + "   {\n"
 + "      var $doc = $wnd.document;\n"
 + "      var $core = {};\n"
 + "      var navigator =\n"
 + "      {\n"
 + "         userAgent: 'webkit'\n"
 + "      };\n"
 + "\n"
 + "      function noop(){}\n"
 + "\n"
 + "      var __gwtModuleFunction = noop;\n"
 + "      __gwtModuleFunction.__moduleStartupDone = noop;\n"
 + "      var $sendStats = noop;\n"
 + "      var $moduleName, $moduleBase;\n"
 + "\n// start GWT output---\n";

public static final String kGWT_LIBRARY_SUFFIX =
   "\n// end GWT output---\n"
 + "   }\n"
 + "\n"
 + "   var isBrowser, globalEnv;\n"
 + "   \n"
 + "   if (typeof self !== 'undefined')\n"
 + "   { // Usual Browser Window or Web Worker\n"
 + "      isBrowser = true;\n"
 + "      globalEnv = self;\n"
 + "   }\n"
 + "   else if (typeof global !== 'undefined')\n"
 + "   { // Node.js\n"
 + "      isBrowser = false;\n"
 + "      globalEnv = global;\n"
 + "   }\n"
 + "   else\n"
 + "   { // Other environment (example: CouchDB)\n"
 + "      isBrowser = false;\n"
 + "      globalEnv = root;\n"
 + "   }\n"
 + "\n"
 + "   var document = globalEnv.document || {};\n"
 + "\n"
 + "   if (!document.compatMode)\n"
 + "   {\n"
 + "      document.compatMode = 'CSS1Compat';\n"
 + "   }\n"
 + "\n"
 + "   var fakeWindow;\n"
 + "   if (isBrowser && !false)\n"
 + "   {\n"
 + "      fakeWindow = globalEnv;\n"
 + "   }\n"
 + "   else\n"
 + "   {\n"
 + "      fakeWindow               = {};\n"
 + "      fakeWindow.setTimeout    = globalEnv.setTimeout    ? globalEnv.setTimeout.bind(globalEnv)    : noop;\n"
 + "      fakeWindow.clearTimeout  = globalEnv.clearTimeout  ? globalEnv.clearTimeout.bind(globalEnv)  : noop;\n"
 + "      fakeWindow.setInterval   = globalEnv.setInterval   ? globalEnv.setInterval.bind(globalEnv)   : noop;\n"
 + "      fakeWindow.clearInterval = globalEnv.clearInterval ? globalEnv.clearInterval.bind(globalEnv) : noop;\n"
 + "\n"
 + "      // required since GWT 2.8.0\n"
 + "      fakeWindow.Error         = globalEnv.Error;\n"
 + "      fakeWindow.Math          = globalEnv.Math;\n"
 + "      fakeWindow.RegExp        = globalEnv.RegExp;\n"
 + "      fakeWindow.TypeError     = globalEnv.TypeError;\n"
 + "   }\n"
 + "\n"
 + "   if (!fakeWindow.document)\n"
 + "   {\n"
 + "      fakeWindow.document = document;\n"
 + "   }\n"
 + "\n"
 + "   fakeWindow.React              = React;\n"
 + "   fakeWindow.ReactJava          = {};\n"
 + "   fakeWindow.ReactJava.exports  = {'ReactNative.Button':Button, 'ReactNative.Text':Text, 'ReactNative.View':View};\n"
 + "   fakeWindow.ReactJava.getType  = function(type){return fakeWindow.ReactJava.exports[type]};\n"
 + "   fakeWindow.ReactNative        = {};\n"
 + "   fakeWindow.ReactNative.Button = Button;\n"
 + "   fakeWindow.ReactNative.Text   = Text;\n"
 + "   fakeWindow.ReactNative.View   = View;\n"
 + "\n"
 + "   getExports(fakeWindow);\n"
 + "\n"
 + "})(this, React, Button, Text, View);\n";

public static final String kINDEX_JS =
   //"import Foo from './gwtLibrary.js'\n";

   "var ReactJava  = require('react');\n"
 + "var ReactDOM   = require('react-dom');\n"
 + "var _getType   = function(type){return module.exports[type]};\n"
 + "module.exports = {ReactJava:ReactJava, ReactDOM:ReactDOM, getType:_getType};\n";

public static final String kINDEX_JS_REACT_NATIVE =
   "import Foo from './gwtLibrary.js'\n";

public static final String kWEBPACK_CONFIG_JS =
   "var path         = require('path');\n"
 + "var projectDir   = path.resolve(__dirname,  './');\n"
 + "var buildDir     = path.resolve(projectDir, 'build');\n"
 + "var buildSrcDir  = path.resolve(buildDir,   'src');\n"
 + "var buildDistDir = path.resolve(buildDir,   'javascript');\n"
 + "\n"
 + "module.exports = {\n"
 + "\n"
 + "   mode:  'development',\n"
 + "   entry:\n"
 + "   [\n"
 + "      path.resolve(buildSrcDir, 'index.js')\n"
 + "   ],\n"
 + "   devtool: 'inline-source-map',\n"
 + "   output:\n"
 + "   {\n"
 + "      path:      buildDistDir,\n"
 + "      filename: 'reactjavaapp.js',\n"
 + "   }\n"
 + "}\n";

public static final String kWEBPACK_CONFIG_JS_REACT_NATIVE =
   "var path        = require('path');\n"
 + "var projectDir  = path.resolve(__dirname,  './');\n"
 + "var outDir      = path.resolve(projectDir, 'out');\n"
 + "var buildDir    = path.resolve(outDir,     'build');\n"
 + "var reactDir    = path.resolve(buildDir,   'reactnative');\n"
 + "var reactSrcDir = path.resolve(reactDir,   'src');\n"
 + "var warDir      = path.resolve(projectDir, 'war');\n"
 + "\n"
 + "module.exports = {\n"
 + "\n"
 + "   mode:  'development',\n"
 + "   entry:\n"
 + "   [\n"
 + "      path.resolve(reactSrcDir, 'index.js')\n"
 + "   ],\n"
 + "   devtool: 'inline-source-map',\n"
 + "   output:\n"
 + "   {\n"
 + "      path:      warDir,\n"
 + "      filename: 'reactjavabundle.js',\n"
 + "   }\n"
 + "}\n";

public static final String kDATE_FORMAT_PATTERN_VERSION = "yyMMddHHmm";

public static final String kVERSION =
   "0.1." + new SimpleDateFormat(kDATE_FORMAT_PATTERN_VERSION).format(new Date());

                                       // doesn't change often so hardcoded   //
                                       // and matched with contents of        //
                                       // 'ReactJavaExtensionVersion.txt' in  //
                                       // core distribution                    //
public static final String kVERSION_COMPILER =
   "GWT-2.8.2-X1901101109";

public static final String kCOMPILER_VERSION_FILENAME =
   "ReactJavaExtensionVersion.txt";
                                       // consists of the kVERSION followed by//
                                       // the enhanced compiler version       //
public static final String kVERSION_IMPLEMENTATION =
   kVERSION + ";" + kVERSION_COMPILER;

public static final Manifest kREACT_JAVA_JAR_MANIFEST =
   new Manifest()
   {{
      Attributes atts = getMainAttributes();
      atts.putValue("Manifest-Version",       "1.0");
      atts.putValue("Main-Class", "io.reactjava.codegenerator.JavascriptBundler");
      atts.putValue("Name",                   "io/reactjava");
      atts.putValue("Specification-Title",    "React Java Classes");
      atts.putValue("Specification-Version",  kVERSION);
      atts.putValue("Specification-Vendor",   "iBigDea");
      atts.putValue("Implementation-Title",   "io.reactjava");
      atts.putValue("Implementation-Version", kVERSION_IMPLEMENTATION);
      atts.putValue("Implementation-Vendor",  "Giavaneers, Inc.");
   }};
                                       // class variables ------------------- //
                                       // google cloud storage manager        //
protected static GoogleCloudStorageManagerBase gcsManager;

                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignPlatform - ensure package.json contains specified platform
                                                                              */
                                                                             /**
            Ensure package.json contains specified platform.

@return     void

@params     platform    platform specification

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void assignPlatform(
   String platform)
   throws Exception
{
   platform =
      platform.equals(IPlatform.kPLATFORM_IOS.toLowerCase())
         ? IPlatform.kPLATFORM_IOS
         : platform.equals(IPlatform.kPLATFORM_WEB.toLowerCase())
            ? IPlatform.kPLATFORM_WEB : platform;

                                       // find the package.json file          //
   File projectDir = new File(System.getProperty("user.dir"));

   File packageJson = new File(projectDir, "package.json");
   if (!packageJson.exists())
   {
      throw new IllegalStateException("Cannot find package.json");
   }

   String contents = getFileAsString(packageJson);
   if (contents.contains(platform))
   {
      System.out.println("Already assigned " + platform);
   }
   else
   {
                                       // package.json must be modified       //
      String newContents = null;
      int    idxBeg      = contents.indexOf("platform\"");
      if (idxBeg < 0)
      {
                                       // add the platform element at the end //
         System.out.println("Adding platform " + platform + " at the end");
         newContents = contents;
         if (!newContents.endsWith(","))
         {
            newContents += ",\n";
         }

         newContents += "\"platform\": \"" + platform + "\"\n";
      }
      else
      {
                                       // modify the existing platform element//
         boolean bErr = false;
         while (true)
         {
            idxBeg = contents.indexOf(':', idxBeg);
            if (idxBeg < 0)
            {
               bErr = true;
               break;
            }
            idxBeg = contents.indexOf("\"Platform", idxBeg);
            if (idxBeg < 0)
            {
               bErr = true;
               break;
            }
            int idxEnd = contents.indexOf("\"", idxBeg + 1);
            if (idxEnd < 0)
            {
               bErr = true;
               break;
            }

            String oldPlatform = contents.substring(idxBeg + 1, idxEnd);

            System.out.println(
               "Changing platform from " + oldPlatform + " to " + platform);

            newContents =
               contents.substring(0, idxBeg + 1)
                  + platform + contents.substring(idxEnd);
            break;
         }
         if (bErr)
         {
            throw new IllegalStateException("package.json malformed");
         }
      }
      if (newContents != null)
      {
         writeFile(packageJson, newContents);
         if (IPlatform.kPLATFORM_IOS.equals(platform))
         {
                                       // if IOS specified as the new platform//
                                       // install and init react native if    //
                                       // required                            //
            createReactJavaProjectNativeSupport(projectDir);
         }
                                       // force a clean build                 //
         touchJavaSource();
         clean();
      }
   }
}
/*------------------------------------------------------------------------------

@name       browserifyInitialize - install browserify module
                                                                              */
                                                                             /**
            Install browserify module.

@return     new project folder

@param      projectDir     project directory

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File browserifyInitialize(
   File   projectDir)
{
   String   sHome    = System.getProperty("user.home");
   File     home     = new File(sHome);
   File     wd       = kSRCCFG_NODE_MODULES_IN_PROJECT ? projectDir : home;
   String   nodePath = getLocalNodePath();
   String[] args;

   if (kSRCCFG_NODE_MODULES_IN_PROJECT || getNpmPackageOutOfDate("browserify"))
   {
      System.out.println("Installing/updating browserify package...");
      args = new String[]{"npm install browserify --loglevel=error"};
      if (exec(args, wd, nodePath) != 0)
      {
         throw new IllegalStateException("browserify was not installed");
      }
      System.out.println("Done");
   }

   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       buildGWTCodeServerJar - build core-codeserver.jar
                                                                              */
                                                                             /**
            Generate the enhanced core-codeserver.jar from the existing, adding
            the reactjava.jar contents.

@return     void

@history    Sun Dec 23, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void buildGWTCodeServerJar()
   throws Exception
{
   System.out.println("JavascriptBundler.buildGWTCodeServerJar(): entered");

   File   projectDir = new File(System.getProperty("user.dir"));
   String baseSrc    = "src/";
   String baseCls    = "out/artifacts/ReactJava_war_exploded/WEB-INF/classes/";

   String[] javaEntries =
   {
      "com/google/gwt/dev/codeserver/CodeServer",
   };
   String[] classEntries =
   {
      "com/google/gwt/dev/codeserver/CodeServer",
   };

   List<Path>   targets = new ArrayList<>();
   List<String> entries = new ArrayList<>();

   for (int i = 0; i < javaEntries.length; i++)
   {
      targets.add(
        new File(projectDir, baseSrc + javaEntries[i] + ".java").toPath());
      entries.add(javaEntries[i] + ".java");
   }
   for (int i = 0; i < classEntries.length; i++)
   {
      targets.add(
         new File(projectDir, baseCls + classEntries[i] + ".class").toPath());
      entries.add(classEntries[i] + ".class");
   }

   jarUpdate(
      new File("/Users/brianm/.reactjava/core/core-2.8.2/core-codeserver.jar"),
      targets, entries);

   System.out.println("JavascriptBundler.buildGWTCodeServerJar(): exiting");
}
/*------------------------------------------------------------------------------

@name       buildGWTDevJar - build core-dev.jar
                                                                              */
                                                                             /**
            Generate the enhanced core-dev.jar from the existing, adding the
            reactjava.jar contents.

@return     void

@history    Sun Dec 23, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void buildGWTDevJar()
   throws Exception
{
   System.out.println("JavascriptBundler.buildGWTDevJar(): entered");

   File   projectDir = new File(System.getProperty("user.dir"));
   String baseSrc    = "src/";
   String baseCls    = "out/artifacts/ReactJava_war_exploded/WEB-INF/classes/";

   String[] javaEntries =
   {
      //"com/google/core/dev/Compiler",
      //"com/google/core/dev/codeserver/CodeServer",
      "com/google/gwt/dev/Precompile",
      "com/google/gwt/dev/javac/CompilationStateBuilder",
      "com/google/gwt/dev/javac/CompilationUnitBuilder",
   };
   String[] classEntries =
   {
      //"com/google/core/dev/Compiler",
      //"com/google/core/dev/codeserver/CodeServer",
      "com/google/gwt/dev/Precompile",
      "com/google/gwt/dev/javac/CompilationStateBuilder",
      "com/google/gwt/dev/javac/CompilationStateBuilder$1",
      "com/google/gwt/dev/javac/CompilationStateBuilder$CompileMoreLater",
      "com/google/gwt/dev/javac/CompilationStateBuilder$CompileMoreLater$1",
      "com/google/gwt/dev/javac/CompilationStateBuilder$CompileMoreLater$UnitProcessorImpl",
      "com/google/gwt/dev/javac/CompilationStateBuilder$CompileMoreLater$UnitProcessorImpl$1",
      "com/google/gwt/dev/javac/CompilationUnitBuilder",
      "com/google/gwt/dev/javac/CompilationUnitBuilder$1",
      "com/google/gwt/dev/javac/CompilationUnitBuilder$GeneratedCompilationUnit",
      "com/google/gwt/dev/javac/CompilationUnitBuilder$GeneratedCompilationUnitBuilder",
      "com/google/gwt/dev/javac/CompilationUnitBuilder$ResourceCompilationUnitBuilder",
   };

   List<Path>   targets = new ArrayList<>();
   List<String> entries = new ArrayList<>();

   for (int i = 0; i < javaEntries.length; i++)
   {
      targets.add(
        new File(projectDir, baseSrc + javaEntries[i] + ".java").toPath());
      entries.add(javaEntries[i] + ".java");
   }
   for (int i = 0; i < classEntries.length; i++)
   {
      targets.add(
         new File(projectDir, baseCls + classEntries[i] + ".class").toPath());
      entries.add(classEntries[i] + ".class");
   }

   jarUpdate(
      new File("/Users/brianm/.reactjava/gwt/gwt-2.8.2/gwt-dev.jar"),
      targets, entries);

   System.out.println("JavascriptBundler.buildGWTDevJar(): exiting");
}
/*------------------------------------------------------------------------------

@name       buildReactJavaJar - build reactjava.jar
                                                                              */
                                                                             /**
            Generate reactjava.jar

@return     void

@history    Sun Dec 23, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void buildReactJavaJar()
   throws Exception
{
   System.out.println("JavascriptBundler.buildReactJavaJar(): entered");

   File       projectDir = new File(System.getProperty("user.dir"));
   List<Path> paths      = new ArrayList<Path>();
   List<Path> entries    = new ArrayList<Path>();

   File dstJar =
      new File("/Users/brianm/working/io/reactjava/lib/reactjava.jar");

   String[] bases =
   {
      "src",
      "classes",
      "out/artifacts/ReactJava_war_exploded/WEB-INF/classes",
   };

   for (String base : bases)
   {
      Path path = new File(projectDir, base).toPath();
      List<Path> basePaths =
         Files.walk(path)
           .filter(s -> !s.toString().endsWith(".DS_Store"))
           .collect(toList());

      for (Path basePath : basePaths)
      {
         if (basePath.toFile().isDirectory())
         {
            continue;
         }

         Path entry = path.relativize(basePath);
         if ("META-INF/MANIFEST.MF".equals(entry.toString()))
         {
                                       // manifest is specified explicitly    //
            continue;
         }
         if (!entries.contains(entry))
         {
            paths.add(basePath);
            entries.add(entry);
         }
      }
   }

   jarFiles(dstJar, paths, entries, kREACT_JAVA_JAR_MANIFEST);
   System.out.println("JavascriptBundler.buildReactJavaJar(): exiting");
}
/*------------------------------------------------------------------------------

@name       buildRelease - build release
                                                                              */
                                                                             /**
            Generate reactjava.jar and the enhanced core-dev.jar

@return     void

@history    Sun Dec 23, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void buildRelease()
   throws Exception
{
   buildReactJavaJar();
}
/*------------------------------------------------------------------------------

@name       clean - delete output directory to clean
                                                                              */
                                                                             /**
            Delete output directory to clean.

@return     void

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void clean()
   throws Exception
{
   File projectDir = new File(System.getProperty("user.dir"));
   File outDir     = new File(projectDir, "out");
   if (outDir.exists())
   {
      deleteDirectory(outDir);
   }
}
/*------------------------------------------------------------------------------

@name       copyGWTReleaseViaGCS - copy core realese from cloud storage
                                                                              */
                                                                             /**
            Copy core realese from cloud storage.

@return     downloaded file

@param      dstDir      destination directory

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File copyGWTReleaseViaGCS(
   File   dstDir)
   throws Exception
{
   File  gwtReleaseFile = new File(dstDir, kREACT_JAVA_GWT_RELEASE_NAME);

   copyFile(kREACT_JAVA_DOWNLOAD_GWT_RELEASE_URL, gwtReleaseFile.toPath());

   System.out.println("Downloaded latest reactjava.jar");

   return(gwtReleaseFile);
}
/*------------------------------------------------------------------------------

@name       copyFile - copy project template
                                                                              */
                                                                             /**
            Copy project template.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private static void copyFile(
   String url,
   Path   dst)
   throws IOException
{
   InputStream in  = null;
   try
   {
      in  = (new URL(url)).openStream();
      Files.copy(in, dst);
   }
   finally
   {
      if (in != null)
      {
         in.close();
      }
   }
}
/*------------------------------------------------------------------------------

@name       copyFile - copy project template
                                                                              */
                                                                             /**
            Copy project template.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private static void copyFile(
   Path               src,
   Path               dst,
   boolean            bDeleteExisting,
   Map<String,String> replaceMap)
   throws             IOException
{
   File               srcFile  = src.toFile();
   File               dstFile  = dst.toFile();
   String             type     = getFileType(srcFile);
   boolean            bSymLink = Files.isSymbolicLink(src);

   if (bSymLink || ".jar".equals(type))
   {
      Files.copy(src, dst, LinkOption.NOFOLLOW_LINKS);
   }
   else
   {
      if (replaceMap != null)
      {
         String dstName =
            dstFile.getName().replace(
               kPROJECT_TEMPLATE_NAME, replaceMap.get(kPROJECT_TEMPLATE_NAME));

         dst = new File(dstFile.getParentFile(), dstName).toPath();
      }

      dstFile.getParentFile().mkdirs();
      if (bDeleteExisting)
      {
         dstFile.delete();
      }

      byte[] buf = getFileBytes(srcFile);
      String str = new String(buf, "UTF-8");

      if (replaceMap != null)
      {
         for (String key : replaceMap.keySet())
         {
            str = str.replace(key, replaceMap.get(key));
         }
      }

      ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("UTF-8"));
      Files.copy(in, dst);
   }
}
/*------------------------------------------------------------------------------

@name       copyFiles - copy files
                                                                              */
                                                                             /**
            Copy files from src to dst

@return     void

@param      src      src file or directory
@param      dst      dst file or directory
@param      filter   if non-null, list of file ancestors to be copied

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private static void copyFiles(
   Path               srcPath,
   Path               dstPath,
   List<Path>         filter,
   boolean            bDeleteExisting,
   Map<String,String> replaceMap)
   throws             IOException
{
   if (filter != null)
   {
      if (!srcPath.toFile().isDirectory())
      {
         throw new IOException("src must be a directory");
      }
      for (Path path : filter)
      {
         Path srcPathRel  = srcPath.relativize(path);
         Path dstPathRslv = dstPath.resolve(srcPathRel);
         Path dstPathNrm  = dstPathRslv.normalize();
         copyFile(path, dstPathNrm, bDeleteExisting, replaceMap);
      }
   }
   else if (!srcPath.toFile().isDirectory())
   {
      copyFile(srcPath, dstPath, bDeleteExisting, replaceMap);
   }
   else
   {
      File   dst     = dstPath.toFile();
      String dstName = dst.getName();
      if (kPROJECT_TEMPLATE_NAME.toLowerCase().equals(dstName))
      {
         String projectName = replaceMap.get(kPROJECT_TEMPLATE_NAME);
         dst = new File(dst.getParentFile(), projectName.toLowerCase());
      }
      dst.mkdirs();

      for (File child : srcPath.toFile().listFiles())
      {
         String filename = child.getName();
         if (".DS_Store".equals(filename))
         {
            continue;
         }
         copyFiles(
            child.toPath(), new File(dst, child.getName()).toPath(), filter,
            bDeleteExisting, replaceMap);
      }
   }
}
/*------------------------------------------------------------------------------

@name       copyNativeProjectSupport - copy native project support
                                                                              */
                                                                             /**
            Copy native project support.

@return     project folder

@param      nativeProjectDir     directory of native project support
@param      projectDir           project directory

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File copyNativeProjectSupport(
   File   nativeProjectDir,
   File   projectDir)
   throws Exception
{
   Map<String,String> replaceMap =
      new HashMap<String,String>()
      {{
         put(kPROJECT_TEMPLATE_NAME, projectDir.getName());
         put(kPROJECT_TEMPLATE_NAME.toLowerCase(), projectDir.getName().toLowerCase());
      }};

   copyFiles(nativeProjectDir.toPath(), projectDir.toPath(), null, true, replaceMap);
   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       copyProjectTemplate - copy project template from cloud storage
                                                                              */
                                                                             /**
            Copy project template from cloud storage.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File copyProjectTemplate(
   File    projectDir,
   boolean bUpdate)
   throws  Exception
{
   File tempDir = createLocalTempFile("reactJavaProjectTemplateContainer", true);
   File temp    = createLocalTempFile("reactJavaProjectTemplate", false);

   if (kSRCCFG_PROJECT_TEMPLATE_RESOURCE)
   {
      System.out.println("Extracting project template");
      IConfiguration.fastChannelCopy(
         JavascriptBundler.class.getResourceAsStream(
            "resources/ProjectTemplate.zip"),
         new FileOutputStream(temp));
   }
   else
   {
      System.out.println("Downloading latest project template");
      copyFile(kDOWNLOAD_URL_INTELLIJ_HELLOWORLD_PROJECT_TEMPLATE, temp.toPath());
   }

   zipExtractFiles(temp, tempDir);

   String projectTemplateNameLC = kPROJECT_TEMPLATE_NAME.toLowerCase();

   Map<String,String> replaceMap =
      new HashMap<String,String>()
      {{
         put(kPROJECT_TEMPLATE_NAME, projectDir.getName());
         put(projectTemplateNameLC,  projectDir.getName().toLowerCase());
      }};

   Path srcPath = new File(tempDir, kPROJECT_TEMPLATE_NAME).toPath().normalize();
   Path dstPath = projectDir.toPath().normalize();

   List<Path> filter = null;
   if (bUpdate)
   {
                                       // absolute source paths               //
      String antBuild         = "antbuildreactjava.xml";
      String runConfiguration = ".idea/runConfigurations/MyProject.xml";

      filter = new ArrayList<Path>()
      {{
                                       // update the ant build file           //
         add(new File(srcPath.toFile(), antBuild).toPath().normalize());

                                       // update the core run configuration    //
         add(new File(srcPath.toFile(), runConfiguration).toPath().normalize());
      }};
   }

   copyFiles(srcPath, dstPath, filter, true, replaceMap);

   System.out.println("Done");

   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       copyReactJavaJar - copy react java jar
                                                                              */
                                                                             /**
            Copy react java jar to specified directory.

            Use the contents of

               "https://storage.googleapis.com/react4java.io/releases/latest/"
               "latestReactJavaRelease.txt"

            to get the download url so as to not require reactjava.jar to
            include the classes necessary to make gcs access.

@return     project directory

@param      projectDir     project directory

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File copyReactJavaJar(
   File   dstDir,
   String version)
   throws Exception
{
   String downloadURL;
   String filename;

   if (version == null)
   {
      downloadURL = getURLAsString(kREACT_JAVA_DOWNLOAD_URL_CONTAINER_URL);
      filename    = downloadURL.substring(downloadURL.lastIndexOf('/') + 1);
   }
   else
   {
      filename    = "reactjava@" + version + ".jar";
      downloadURL = kREACT_JAVA_RELEASES_DOWNLOAD_URL + filename;
   }

   System.out.println("Downloading latest reactjava.jar=" + filename);
   File reactJavaJar = new File(dstDir, "reactjava.jar");
   if (reactJavaJar.exists())
   {
      reactJavaJar.delete();
   }

   dstDir.mkdirs();
   copyFile(downloadURL, reactJavaJar.toPath());
   System.out.println("Done");

   return(reactJavaJar);
}
/*------------------------------------------------------------------------------

@name       copyReactJavaJarLatestToProject - copy react java jar
                                                                              */
                                                                             /**
            Copy react java jar to project folder.

            Use the contents of

               "https://storage.googleapis.com/react4java.io/releases/latest/"
               "latestReactJavaRelease.txt"

            to get the download url so as to not require reactjava.jar to
            include the classes necessary to make gcs access.

@return     project directory

@param      projectDir     project directory

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File copyReactJavaJarToProject(
   File   projectDir,
   String version)
   throws Exception
{
   File   warLibDir = new File(projectDir, toOSPath("war/WEB-INF/lib/"));
   return(copyReactJavaJar(warLibDir, version));
}
/*------------------------------------------------------------------------------

@name       copyReactJavaJarLatestViaGCS - copy react java jar
                                                                              */
                                                                             /**
            Copy react java jar to project folder.

@return     project directory

@param      projectDir     project directory

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File copyReactJavaJarLatestViaGCS(
   File   projectDir)
   throws Exception
{
   //StorageObject source = null;
   //for (StorageObject object : gcsManager().listBucket(kRELEASE_BUCKET_NAME))
   //{
   //   if (object.getName().startsWith("releases/latest/reactjava."))
   //   {
   //      source = object;
   //      break;
   //   }
   //}
   //
   //if (source == null)
   //{
   //   throw new IllegalStateException("Release of reactjava.jar not found");
   //}
   //
   //String sourceName = source.getName();
   //       sourceName = sourceName.substring(sourceName.lastIndexOf(toOSPath("/")));
   //
   //File reactJavaJarFile =
   //   new File(projectDir, toOSPath("war/WEB-INF/lib/" + sourceName));
   //
   //gcsManager().downloadObject(source, reactJavaJarFile);
   //
   //System.out.println("Downloaded latest reactjava.jar");
   //
   //return(reactJavaJarFile);
   return(null);
}
/*------------------------------------------------------------------------------

@name       copyReactJavaJarSelf - copy react java jar
                                                                              */
                                                                             /**
            Copy react java jar to project folder.

@return     project directory

@param      projectDir     project directory

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File copyReactJavaJarSelf(
   File   projectDir)
   throws Exception
{
   File jar =
      new File(
         JavascriptBundler.class
            .getProtectionDomain()
            .getCodeSource()
            .getLocation().toURI());

   if (!kREACT_JAVA_JAR_NAME.equals(jar.getName()))
   {
      System.out.println(
         "ERROR: Cannot copy reactjava.jar; "
       + "perhaps this method is not being executed from a copy of the jar.");
   }
   else
   {
      copyFile(
         jar.toPath(),
         new File(projectDir, toOSPath("war/WEB-INF/lib/") + kREACT_JAVA_JAR_NAME).toPath(),
         true,
         null);

      System.out.println("Copied reactjava.jar");
   }

   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       createLocalTempFile - create temporary directory
                                                                              */
                                                                             /**
            Create temporary directory.

@return     temporary directory.

@param      name     directory name.

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File createLocalTempFile(
   String  name,
   boolean bDirectory)
   throws  IOException
{
   File tempDir = getTempDir();
   if (!tempDir.exists())
   {
      tempDir.mkdirs();
   }
   File temp = new File(tempDir, name);
   if (temp.exists())
   {
      if (temp.isDirectory())
      {
         deleteDirectory(temp);
      }
      else
      {
         temp.delete();
      }
   }
   if (bDirectory)
   {
      temp.mkdirs();
   }
   return(temp);
}
/*------------------------------------------------------------------------------

@name       createReactJavaProjectNativeSupport - standard main routine
                                                                              */
                                                                             /**
            Standard main routine

@return     void

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void createReactJavaProjectNativeSupport(
   File projectDir)
{
   try
   {

                                       // initialize react native support     //
      File tempNativeProjectDir = reactNativeProjectInitialize(projectDir);

                                       // trim default react native support   //
      reactNativeTrim(tempNativeProjectDir);

                                       // copy native project support         //
      //copyNativeProjectSupport(tempNativeProjectDir, projectDir);
      moveNativeProjectSupport(tempNativeProjectDir, projectDir);

      System.out.println(
         "Successfully added native support to react java project "
            + projectDir.getName() + "------------------");
   }
   catch(Throwable t)
   {
      t.printStackTrace();
   }
}
/*------------------------------------------------------------------------------

@name       deleteDirectory - copy project template
                                                                              */
                                                                             /**
            Copy project template.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void deleteDirectory(
   File   targetDir)
   throws IOException
{
   if (targetDir.exists())
   {
      System.out.println("Deleting " + targetDir.getAbsolutePath() + "...");

      Path rootPath = Paths.get(targetDir.getAbsolutePath());

      Files.walk(rootPath)
         .sorted(Comparator.reverseOrder())
         .map(Path::toFile)
         .forEach(File::delete);

      System.out.println("Done");
   }
}
/*------------------------------------------------------------------------------

@name       deleteTempGWTCache - delete contents of the GWT cache
                                                                              */
                                                                             /**
            Delete contents of the core and jetty cache files in the java temp
            directory to ensure the js files are rebuilt.

@return     void

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void deleteTempGWTCache()
   throws IOException
{
   File tempDir = new File(System.getProperty("java.io.tmpdir"));

   System.out.println(
       "Deleting contents of the core and jetty cache files "
       + "in the java temp directory to ensure the js files are rebuilt.\n"
       + "Java temp directory=" + tempDir.getAbsolutePath());

   if (!tempDir.exists())
   {
      System.out.println("Specified temp directory does not exist.");
   }
   else
   {
      File[] delDirs =
         tempDir.listFiles(new FilenameFilter()
         {
            public boolean accept(File dir, String name)
            {
               return(name.startsWith("core") || name.startsWith("jetty-"));
            }
         });

      if (delDirs.length == 0)
      {
         System.out.println("No cache files found.");
      }
      else
      {
         for (File delFile : delDirs)
         {
            if (delFile.isDirectory())
            {
               deleteDirectory(delFile);
            }
            else
            {
               System.out.println("Deleting " + delFile.getAbsolutePath() + "...");
               delFile.delete();
            }
         }
      }
   }

   System.out.println("Done");
}
/*------------------------------------------------------------------------------

@name       doOp - do specified operation
                                                                              */
                                                                             /**
            Do specified operation.

@return     void

@param      argsList    args list

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void doOp(
   List<String> argsList)
   throws       Exception
{
   File   currentDir = new File(System.getProperty("user.dir"));
   String opcode     = null;
   String version    = null;
   String path       = null;

   for (int i = 0; i < argsList.size(); i++)
   {
      String arg   = argsList.get(i);
      String argLC = arg.toLowerCase();
      if (argsList.size() == 1)
      {
         if (kOPCODES.contains(argLC))
         {
            opcode = argLC;
         }
         else
         {
            path = arg;
         }
         break;
      }
      if (argsList.size() == i + 1)
      {
         path = arg;
         break;
      }
      if ("update".equals(argLC))
      {
         opcode = argLC;
                                       // if not specified, the path is the   //
                                       // current wd                          //
         path   =
            argsList.size() >= i + 1
               ? argsList.get(++i) : currentDir.getAbsolutePath();
      }
      else if ("-version".equals(arg))
      {
         if (argsList.size() < i + 1)
         {
            throw new IllegalArgumentException("Need to specify version");
         }
         version = argsList.get(++i);
      }
      else if (opcode == null)
      {
         opcode = argLC;
      }
      else
      {
         throw new IllegalArgumentException("Syntax error");
      }
   }

   if (opcode == null)
   {
      opcode = "createproject";
   }

   long start = System.nanoTime();

   System.out.println(new Date());
   System.out.println("nanoTime=" + start);
   System.out.println("opcode  =" + opcode);
   System.out.println(
      "projectDir=" + (path != null ? path : currentDir.getAbsolutePath()));

   if ("buildgwtdevjar".equals(opcode))
   {
      buildGWTDevJar();
   }
   else if ("buildrelease".equals(opcode))
   {
      buildRelease();
   }
   else if ("creategwtlibrary".equals(opcode))
   {
      generateGWTLibrary();
   }
   else if ("deletetempgwtcache".equals(opcode))
   {
      deleteTempGWTCache();
   }
   //else if ("jsxtransform".equals(opcode))
   //{
   //   jsxTransform(path);
   //}
   else if ("shadowtosrc".equals(opcode))
   {
      shadowToSrc();
   }
   else if ("srctoshadow".equals(opcode))
   {
      srcToShadow();
   }
   else if ("updatejarmanifest".equals(opcode))
   {
      updateJarManifest();
   }
   else if ("updateprojecttemplateresource".equals(opcode))
   {
      updateProjectTemplateResource();
   }
   else if ("platformweb".equals(opcode) || "platformios".equals(opcode))
   {
      assignPlatform(opcode);
   }
   else if ("publishgwtsdk".equals(opcode))
   {
      publishGWTSDK();
   }
   else if ("publishjar".equals(opcode))
   {
      publishJar();
   }
   else if ("createproject".equals(opcode) || "update".equals(opcode))
   {
      boolean bCreate = "createproject".equals(opcode);
      File projectDir =
         bCreate
            ? (path == null
               ? new File(currentDir, "MyProject")
               : path.startsWith(File.separator)
                  ? new File(path) : new File(currentDir, path))
            : path == null ? currentDir : new File(path);

      if (!getIsValidJavaIdentifier(projectDir.getName()))
      {
         throw new IllegalArgumentException(
            "Project name must be a valid java identifier "
          + "(see https://docs.oracle.com/cd/E19798-01/821-1841/bnbuk/index.html)");
      }

      updateReactJavaCore(projectDir, version, "update".equals(opcode));
   }
   else
   {
      throw new IllegalArgumentException(opcode);
   }
   System.out.println("nanoTime=" + System.nanoTime());
   System.out.println("duration=" + ((System.nanoTime() - start) / 1000000L));
}
/*------------------------------------------------------------------------------

@name       exec - initialize node and npm if not already
                                                                              */
                                                                             /**
            Initialize node and npm if not already.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static int exec(
   String[] commands,
   File     workingDir)
{
   return(exec(commands, workingDir, null, null));
}
/*------------------------------------------------------------------------------

@name       exec - initialize node and npm if not already
                                                                              */
                                                                             /**
            Initialize node and npm if not already.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static int exec(
   String[] commands,
   File     workingDir,
   String[] container)
{
   return(exec(commands, workingDir, container, null));
}
/*------------------------------------------------------------------------------

@name       exec - initialize node and npm if not already
                                                                              */
                                                                             /**
            Initialize node and npm if not already.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static int exec(
   String[] commands,
   File     workingDir,
   String   newPath)
{
   return(exec(commands, workingDir, null, newPath));
}
/*------------------------------------------------------------------------------

@name       exec - initialize node and npm if not already
                                                                              */
                                                                             /**
            Initialize node and npm if not already.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static int exec(
   String[] args,
   File     workingDir,
   String[] container,
   String   newPath)
{
   int err = 2;
   if (container != null && container.length < 1)
   {
      throw new IllegalArgumentException("Container length must be >= 1");
   }
   try
   {
      String[] first =
         getOSWindows()
            ? new String[]{"cmd.exe", "/C"} : new String[]{"bash","-c"};

      String[] commands = new String[first.length + args.length];

      System.arraycopy(first, 0, commands, 0,            first.length);
      System.arraycopy(args,  0, commands, first.length, args.length);

      ProcessBuilder pb = new ProcessBuilder(commands).directory(workingDir);
      if (newPath != null)
      {
         Map<String, String> env = pb.environment();
         String path = env.get("PATH");
         path += ":" + newPath;
         env.put("PATH", path);
      }

      pb.redirectError(Redirect.INHERIT);
      if (container == null)
      {
         pb.redirectOutput(Redirect.INHERIT);
      }

      Process p = pb.start();
      if (container != null)
      {
         new InputStreamStringBuilder(p.getInputStream(), container);
      }

      err = p.waitFor();
   }
   catch(Exception e)
   {
      System.err.println(e);
      err = err;
   }
   return(err);
}
/*------------------------------------------------------------------------------

@name       findJavaSource - copy project template
                                                                              */
                                                                             /**
            Copy project template.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private static File findJavaSource(
   File   src)
   throws IOException
{
   File sourceFile = null;
   if (!src.isDirectory())
   {
      if ("App.java".equals(src.getName()))
      {
         sourceFile = src;
      }
   }
   else
   {
      for (File child : src.listFiles())
      {
         if (child.isHidden())
         {
            continue;
         }

         sourceFile = findJavaSource(child);
         if (sourceFile != null)
         {
            break;
         }
      }
   }
   return(sourceFile);
}
/*------------------------------------------------------------------------------

@name       gcsManager - get gcs manager
                                                                              */
                                                                             /**
            Get gcs manager.

@return     gcs manager

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static GoogleCloudStorageManagerBase gcsManager()
   throws Exception
{
   if (gcsManager == null)
   {
      File projectDir  = new File(System.getProperty("user.dir"));
      File rsrcKeyDir  = new File(projectDir, toOSPath("resources/keys"));
      File rsrcKey     = new File(rsrcKeyDir, kSERVICE_ACCOUNT_PRIVATE_KEY_NAME);

      InputStream svcAcctPrivateInputStream =
         rsrcKey.exists() ? new FileInputStream(rsrcKey) : null;

      gcsManager =
         new GoogleCloudStorageManagerBase(
            kSERVICE_ACCOUNT_ID, svcAcctPrivateInputStream, kAPPLICATION_NAME);
   }

   return(gcsManager);
}
/*------------------------------------------------------------------------------

@name       generateGWTLibrary - generate library from core compiler output
                                                                              */
                                                                             /**
            Generate library from core compiler output. Only invoked for
            ReactJava Native.

@return     void

@params     artifactDir  artiface directory, for example:
                         "/Users/brianm/working/IdeaProjects/JsInteropExample/"
                         "out/artifacts/JsInteropExample_war_exploded"

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File generateGWTLibrary()
   throws Exception
{
                                       // find the core compiler output        //
   File   projectDir = new File(System.getProperty("user.dir"));
   File   gwtOut     = null;
   File   artifact   = null;
   File[] artifacts = new File(projectDir, toOSPath("out/artifacts")).listFiles();
   System.out.println("numArtifacts=" + artifacts.length);

   for (File candidate : artifacts)
   {
      if (candidate.isHidden() || !candidate.isDirectory())
      {
         continue;
      }
      if (candidate.getName().endsWith("_war_exploded"))
      {
         artifact = candidate;
         break;
      }
   }
   if (artifact == null)
   {
      throw new FileNotFoundException("Cannot find artifact");
   }

   for (File candidate : artifact.listFiles())
   {
      System.out.println("Found " + candidate.getName());
      if (candidate.isHidden() || !candidate.isDirectory())
      {
         continue;
      }
      if ("ReactJava".equals(candidate.getName()))
      {
         continue;
      }
      if ("WEB-INF".equals(candidate.getName()))
      {
         continue;
      }
      for (File chase : candidate.listFiles())
      {
         if (chase.getName().endsWith(".cache.js"))
         {
            System.out.println("Found core output=" + chase.getAbsolutePath());
            gwtOut = chase;
            break;
         }
      }

      if (gwtOut != null)
      {
         break;
      }
   }
   if (gwtOut == null)
   {
      throw new FileNotFoundException("Cannot find core compiler output");
   }

   String gwtJs  = getFileAsString(gwtOut);
   int    idxBeg = gwtJs.indexOf(kGWT_LIBRARY_DELIM_BEG);
   if (idxBeg < 0)
   {
      throw new IllegalStateException("GWT compiler output unexpected");
   }

   int idxEnd = gwtJs.indexOf(kGWT_LIBRARY_DELIM_END);
   if (idxEnd < 0)
   {
      throw new IllegalStateException("GWT compiler output unexpected");
   }

   String gwtSlice = gwtJs.substring(idxBeg, idxEnd);

                                       // comment out next line               //
   gwtSlice += "\n//commented out by reactjava\n//";
   gwtSlice += gwtJs.substring(idxEnd);

   String library  = kGWT_LIBRARY_PREFIX + gwtSlice + kGWT_LIBRARY_SUFFIX;

   File buildDir = new File(projectDir, kOUT_BUILD_SRC_PATH);
   buildDir.mkdirs();

   File gwtLibrary = writeFile(new File(buildDir, kGWT_LIBRARY_NAME), library);

   return(gwtLibrary);
}
/*------------------------------------------------------------------------------

@name       getFileAsString - get file as string
                                                                              */
                                                                             /**
            Get file as string.

@return     file as string.

@param      path     file path.

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getFileAsString(
   File   file)
   throws Exception
{
   return(new String(getFileBytes(file), "UTF-8"));
}
/*------------------------------------------------------------------------------

@name       getFileBytes - get file bytes
                                                                              */
                                                                             /**
            Get file bytes.

@return     file bytes.

@param      path     file path.

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static byte[] getFileBytes(
   File   file)
   throws IOException
{
   return(getInputStreamBytes(new FileInputStream(file)));
}
/*------------------------------------------------------------------------------

@name       getFileBytes - get file bytes
                                                                              */
                                                                             /**
            Get file bytes.

@return     file bytes.

@param      path     file path.

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getFileType(
   File   file)
{
   String type = null;
   String name = file.getName();
   int    idx  = name.lastIndexOf('.');
   if (idx >= 0)
   {
      type = name.substring(idx);
   }
   return(type);
}
/*------------------------------------------------------------------------------

@name       getGWTInstalled - get whether core is installed
                                                                              */
                                                                             /**
            Get whether core is installed.

@return     true iff core is installed

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean getGWTInstalled()
   throws IOException
{
   System.out.println(
      "Checking whether the latest version of core is installed...");

   boolean bInstalled = false;
   String  msg        = "GWT is not installed.";

   File home = new File(System.getProperty("user.home"));
   File gwt  = new File(home, toOSPath(".reactjava/core/core-2.8.2"));
   if (gwt.exists())
   {
      msg = "The GWT installation is out of date.";

                                       // check the version                   //
      File gwtVersion =
         new File(gwt.getParentFile(), "ReactJavaExtensionVersion.txt");

      if (gwtVersion.exists())
      {
         String version =
            new String(
               getInputStreamBytes(new FileInputStream(gwtVersion))).trim();

         if (bInstalled = kVERSION_COMPILER.equals(version))
         {
            msg = "The GWT installation is up to date.";
         }
      }
   }

   System.out.println(msg);

   return(bInstalled);
}
/*------------------------------------------------------------------------------

@name       getInputStreamBytes - get input stream bytes
                                                                              */
                                                                             /**
            Get input stream bytes.

@return     input stream bytes.

@param      in    input stream

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static byte[] getInputStreamBytes(
   URL    url)
   throws IOException
{
  return(getInputStreamBytes(url.openStream(), Long.MAX_VALUE));
}
/*------------------------------------------------------------------------------

@name       getInputStreamBytes - get input stream bytes
                                                                              */
                                                                             /**
            Get input stream as string.

@return     input stream as string.

@param      in    input stream

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static byte[] getInputStreamBytes(
   InputStream in)
   throws IOException
{
   return(getInputStreamBytes(in, in.available()));
}
/*------------------------------------------------------------------------------

@name       getInputStreamBytes - get input stream bytes
                                                                              */
                                                                             /**
            Get input stream as string.

@return     input stream as string.

@param      in    input stream

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static byte[] getInputStreamBytes(
   InputStream in,
   long        length)
   throws IOException
{
   ByteArrayOutputStream out = new ByteArrayOutputStream();
   IConfiguration.fastChannelCopy(in, out, length);

   return(out.toByteArray());
}
/*------------------------------------------------------------------------------

@name       getIsValidJavaIdentifier - if specified string is valid identifier
                                                                              */
                                                                             /**
            Test whether specified string is valid identifier.

@return     true iff specified string is valid identifier

@param      candidate      candidate identifier

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean getIsValidJavaIdentifier(
   String candidate)
{
   int     length = candidate.length();
   boolean bValid =
      length > 0 && Character.isJavaIdentifierStart(candidate.charAt(0));

   if (bValid)
   {
      for (int i = 1; i < length; i++)
      {
         if (!Character.isJavaIdentifierPart(candidate.charAt(i)))
         {
            bValid = false;
            break;
         }
      }
   }

   return(bValid);
}
/*------------------------------------------------------------------------------

@name       getLatestJarAndRelaunch - get lastest jar and relaunch
                                                                              */
                                                                             /**
            Get lastest jar and relaunch.

@return     void

@param      projectPath    project path

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void getLatestJarAndRelaunch(
   List<String> argsList)
   throws       Exception
{
   System.out.println("Getting latest reactjava.jar and relaunching...");

   File   tempDir = createLocalTempFile("latestJarContainer", true);
   File   newJar  = copyReactJavaJar(tempDir, null);
   File   wd      = new File(System.getProperty("user.dir"));
   String cmd     = "java -jar " + newJar.getAbsolutePath() + " -relaunched";

   for (String arg : argsList)
   {
      cmd += " " + arg;
   }
   int retVal = exec(new String[]{cmd}, wd);
}
/*------------------------------------------------------------------------------

@name       getLocalNodePath - update bash files to support node installation
                                                                              */
                                                                             /**
            Update bash files to support local node installation.

@return     local node path or null if not found

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getLocalNodePath()
{
   String  sHome   = System.getProperty("user.home");
   File    home    = new File(sHome);
   File    node    = new File(home, toOSPath("/.nvm/versions/node"));
   String  newPath = null;

   if (node.exists())
   {
      for (File chase : node.listFiles())
      {
         if (chase.isHidden())
         {
            continue;
         }
         newPath = new File(chase, "bin").getAbsolutePath();
         break;
      }
   }

   return(newPath);
}
/*------------------------------------------------------------------------------

@name       getNodeInstalled - get whether node is installed
                                                                              */
                                                                             /**
            Get whether node is installed.

@return     true iff node is installed

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean getNodeInstalled()
{
   System.out.println("Checking whether node is installed...");

   File home = new File(System.getProperty("user.home"));

   boolean bInstalled = exec(new String[]{"node --version"}, home) == 0;

   System.out.println("Node is" + (bInstalled ? "" : " not") + " installed.");

   return(bInstalled);
}
/*------------------------------------------------------------------------------

@name       getNpmInstalled - get whether npm is installed
                                                                              */
                                                                             /**
            Get whether npm is installed.

@return     true iff npm is installed

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean getNpmInstalled()
{
   File home = new File(System.getProperty("user.home"));

   return(exec(new String[]{"npm --version"}, home) == 0);
}
/*------------------------------------------------------------------------------

@name       getNpmPackagesOutOfDate - get if specified package is out of date
                                                                              */
                                                                             /**
            Get whether specified package is out of date.

@return     true iff specified package is not installed or out of date

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean getNpmPackageOutOfDate(
   String  name)
{
   boolean bOutOfDate = true;
   String  installed  = getNpmPackageVersionInstalled(name);
   if (installed != null)
   {
      String[] container = new String[1];
      File     home      = new File(System.getProperty("user.home"));

      exec(new String[]{"npm view " + name + " version"}, home, container);
      if (container[0] != null)
      {
         bOutOfDate = !installed.equals(container[0]);
         System.out.println(
            name + " is " + (bOutOfDate ? "out of date" : "up to date"));
      }
      else
      {
         System.out.println(
            "Checking whether " + name + " is out of date generated error.");
      }
   }
   return(bOutOfDate);
}
/*------------------------------------------------------------------------------

@name       getNpmPackageInstalled - get whether specified package is installed
                                                                              */
                                                                             /**
            Get whether specified package is installed.

@return     true iff specified package is installed

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean getNpmPackageInstalled(
   String name)
{
   System.out.println("Getting whether " + name + " is installed...");

   boolean bInstalled = getNpmPackageVersionInstalled(name) != null;
   System.out.println(
      name + " is" + (bInstalled ? "" : " not") + " installed");

   return(bInstalled);
}
/*------------------------------------------------------------------------------

@name       getNpmPackageVersionInstalled - get specified npm package version
                                                                              */
                                                                             /**
            Get version of any specified npm package installed.

@return     specified npm package version, or null if not installed

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getNpmPackageVersionInstalled(
   String name)
{
   String version = null;

   System.out.println("Getting " + name + " version...");
   try
   {
      File home        = new File(System.getProperty("user.home"));
      File nodeModules = new File(home, "node_modules");
      File moduleDir   = new File(nodeModules, name);

      while(true)
      {
         if (!moduleDir.exists())
         {
            System.out.println(name + " is not installed");
            break;
         }

         File   modulePkg   = new File(moduleDir, "package.json");
         String contents    = getFileAsString(modulePkg);
         int    idx         = contents.indexOf("\"_id\":");
         if (idx < 0)
         {
            throw new IllegalStateException(
               "module package.json does not contain 'id' attribute");
         }

         version = contents.substring(idx, contents.indexOf('\n', idx));
         idx     = version.indexOf('@');
         if (idx < 0)
         {
            throw new IllegalStateException(
               "module package.json 'id' attribute value unexpcted");
         }

         version = version.substring(idx + 1, version.indexOf('\"', idx));
         System.out.println(name + " version installed is " + version);
         break;
      }
   }
   catch(Exception e)
   {
      System.out.println(
         "Getting " + name + " version generated error: " + e);
   }

   return(version);
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
public static boolean getOSWindows()
{
   return(System.getProperty("os.name").toLowerCase().contains("windows"));
}
/*------------------------------------------------------------------------------

@name       getReactJavaDir - get react java dir
                                                                              */
                                                                             /**
            Get react java dir.

@return     react java dir

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File getReactJavaDir()
{
   return(new File(new File(System.getProperty("user.home")), ".reactjava"));
}
/*------------------------------------------------------------------------------

@name       getTempDir - get temporary directory
                                                                              */
                                                                             /**
            Get temporary directory.

@return     temporary directory.

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File getTempDir()
   throws  IOException
{
   return(new File(getReactJavaDir(), "temp"));
}
/*------------------------------------------------------------------------------

@name       getURLAsString - get object with url as string
                                                                              */
                                                                             /**
            Get object with url as string.

@return     object with url as string.

@param      url      target object url

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getURLAsString(
   String url)
   throws Exception
{
   String                content = null;
   InputStream           in  = null;
   ByteArrayOutputStream out = null;
   try
   {
      in  = (new URL(url)).openStream();
      out = new ByteArrayOutputStream();

      IConfiguration.fastChannelCopy(in, out, Integer.MAX_VALUE);
      content = new String(out.toByteArray(), "UTF-8");
   }
   finally
   {
      if (in != null)
      {
         in.close();
      }
      if (out != null)
      {
         out.close();
      }
   }
   return(content);
}
/*------------------------------------------------------------------------------

@name       reactNativeProjectInitialize - copy project template
                                                                              */
                                                                             /**
            Copy project template.

            Could do this with

               "curl -o core-2-8.zip "https://storage.googleapis.com/"
               "core-releases/core-2.8.2.zip"

               but the download server seems very slow...

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File gwtInitialize(
   File   projectDir)
   throws Exception
{
   if (!getGWTInstalled())
   {
      String sHome  = System.getProperty("user.home");
      File   home   = new File(sHome);
      File   gwtDir = new File(home, toOSPath(".reactjava/core"));
      File   gwt    = new File(gwtDir, "core-2.8.2");

      gwt.mkdirs();
                                       // download release from cloud storage //
      System.out.println(
         "\nDownloading core release (this may take a few minutes)...");
      File src = copyGWTReleaseViaGCS(gwtDir);

      System.out.println("\nInstalling core...");
      zipExtractFiles(src, gwtDir);
                                       // delete the release zip file         //
      src.delete();
                                       // create the version file             //
      writeFile(
         new File(gwtDir, kCOMPILER_VERSION_FILENAME), kVERSION_COMPILER);

      System.out.println("Done");
   }

   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       jarFiles - create jar of target files to specified output jar
                                                                              */
                                                                             /**
            Create jar of target files to specified output jar.

@return     void

@param      jarFile     output jar
@param      targets     array of target files
@param      manifest    jar file manifest

@history    Fri Nov 6, 2003 04:35:52 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void jarFiles(
   File       jarFile,
   List<Path> targets,
   List<Path> entries,
   Manifest   manifest)
   throws     Exception
{
   JarOutputStream jarOut =
      new JarOutputStream(
         new BufferedOutputStream(new FileOutputStream(jarFile)), manifest);

   for (int i = 0; i < targets.size(); i++)
   {
      Path target = targets.get(i);
      File file   = target.toFile();
      if (file.isDirectory())
      {
         continue;
      }

      JarEntry entry = new JarEntry(entries.get(i).toString());
      entry.setMethod(ZipEntry.DEFLATED);
      try
      {
         jarOut.putNextEntry(entry);
      }
      catch(Exception e)
      {
         e = e;
      }

      IConfiguration.fastChannelCopy(
         new BufferedInputStream(new FileInputStream(file)), jarOut);
   }
   jarOut.close();
                                       // verify the new jar                  //
   JarFile newJar = new JarFile(jarFile);
   newJar.close();
}
/*------------------------------------------------------------------------------

@name       jarUpdate - create jar of target files to specified output jar
                                                                              */
                                                                             /**
            Create jar of target files to specified output jar.

@return     void

@param      jarFile     output jar
@param      targets     array of target files
@param      manifest    jar file manifest

@history    Fri Nov 6, 2003 04:35:52 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void jarUpdate(
   File         jarFile,
   List<Path>   targets,
   List<String> entries)
   throws       IOException
{
   System.out.println("JavascriptBundler.jarUpdate(): entered");

   boolean         bUpdated = false;
   File            tmpJar   = File.createTempFile("tempJar", ".tmp");
   JarOutputStream jarOut   =
      new JarOutputStream(
         new BufferedOutputStream(new FileOutputStream(tmpJar)));

                                       // write the new entries               //
   for (int i = 0; i < targets.size(); i++)
   {
      Path target = targets.get(i);
      File file   = target.toFile();
      if (file.isDirectory())
      {
         continue;
      }

      JarEntry entry = new JarEntry(entries.get(i));
      entry.setMethod(ZipEntry.DEFLATED);
      jarOut.putNextEntry(entry);

      IConfiguration.fastChannelCopy(
         new BufferedInputStream(new FileInputStream(file)), jarOut);

      System.out.println("JavascriptBundler.jarUpdate(): added " + entry);
   }
                                       // copy original entries               //
   JarFile     origJar    = new JarFile(jarFile);
   Enumeration jarEntries = origJar.entries();
   while (jarEntries.hasMoreElements())
   {
      JarEntry entry = (JarEntry)jarEntries.nextElement();
      if (entries.contains(entry.getName()))
      {
         continue;
      }

      InputStream entryIn = origJar.getInputStream(entry);
      jarOut.putNextEntry(entry);

      IConfiguration.fastChannelCopy(new BufferedInputStream(entryIn), jarOut);
      bUpdated = true;
   }

   jarOut.close();
   origJar.close();

   if (bUpdated)
   {
                                       // verify the new jar                  //
      JarFile newJar = new JarFile(tmpJar);
      newJar.close();
                                       // substitute the new jar              //
      jarFile.delete();
      tmpJar.renameTo(jarFile);
      System.out.println("JavascriptBundler.jarUpdate(): updated");
   }
   System.out.println("JavascriptBundler.jarUpdate(): exiting");
}
/*------------------------------------------------------------------------------

@name       jsxTransform - transform source files
                                                                              */
                                                                             /**
            Transform source files.

@return     project directory

@param      projectDir  project directory

@history    Sat Nov 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
//public static void jsxTransform(
//   String classname)
//   throws IOException
//{
//   srcToShadow();
//// deleteTempGWTCache();
//
//   List<String> classnames = new ArrayList<String>();
//   if (true/*classname != null*/)
//   {
//      classnames.add("io.reactjava.client.examples.helloworld.App");
//   }
//   else
//   {
//      Path srcPath = new File(System.getProperty("user.dir"), "src").toPath();
//      Files.walk(srcPath)
//         .filter(Files::isRegularFile)
//         .forEach((Path path) ->
//         {
//            if (path.toFile().getName().toLowerCase().endsWith(".java"))
//            {
//               classnames.add(
//                 srcPath.relativize(path).toString().replace("/","."));
//            }
//         });
//   }
//   for (String chase : classnames)
//   {
//      TreeLogger logger = new PrintWriterTreeLogger();
//
//      String parsed =
//         new JSXTransform().parse(
//            chase,
//            chase/*"io.reactjava.client.examples.helloworld.generated.App"*/,
//            logger);
//
//      if (parsed != null)
//      {
//                                       // replace src file with parsed        //
//         writeFile(
//            JSXTransform.getComponentSourceFile(chase, logger),
//            parsed);
//      }
//   }
//}
/*------------------------------------------------------------------------------

@name       listDescendantFiles - get descendant files
                                                                              */
                                                                             /**
            Get all descendant files satisfying specified filename filter.

@return     void

@param      parent            parent file
@param      pattern           regular expression pattern as file filter
@param      subDirPattern     regular expression pattern as subdirectory filter
@param      bDirectories      include matching directories in list
@param      bAllDirectories   include all directories in list
@param      descendants       list of matching descendant Files

@history    Fri Aug 15, 2003 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static List<File> listDescendantFiles(
   File       parent,
   Pattern    pattern,
   Pattern    subDirPattern,
   boolean    bDirectories,
   boolean    bAllDirectories,
   List<File> descendants)
{
   File[]     children = parent.listFiles();

   if (descendants == null)
   {
      descendants = new Vector<File>();
   }

   for (int i = 0, iMax = children != null ? children.length : 0; i < iMax; i++)
   {
      File    child     = children[i];
      boolean bDir      = child.isDirectory();
      String  childName = child.getName();

      if (bAllDirectories && bDir)
      {
         descendants.add(child);
      }
      else if ((pattern == null) || pattern.matcher(childName).matches())
      {
         if (!bDir || bDirectories)
         {
            descendants.add(child);
         }
      }
      if (bDir)
      {
         if ((subDirPattern == null)
               || subDirPattern.matcher(childName).matches())
         {
            listDescendantFiles(
               child,
               pattern,
               subDirPattern,
               bDirectories,
               bAllDirectories,
               descendants);
         }
      }
   }
   return(descendants);
}
/*------------------------------------------------------------------------------

@name       localNodeBashUpdate - update bash files for local node installation
                                                                              */
                                                                             /**
            Update bash files to support local node installation.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File localNodeBashUpdate(
   File    projectDir)
   throws  Exception
{
   String newPath = getLocalNodePath();
   if (newPath == null)
   {
      throw new IllegalStateException("Cannot find local node installation");
   }

   String  sHome         = System.getProperty("user.home");
   String  sourceBashRC  = "source " + sHome + toOSPath("/.bashrc");
   File    home          = new File(sHome);
   boolean bAddNodePath  = true;
   boolean bSourceBashRC = true;
   String  sBashProfile  = "";
   File    bashProfile   = new File(home, ".bash_profile");
   boolean bFileExists   = bashProfile.exists();
   if (bFileExists)
   {
                                       // if .bash_profile exists, update it, //
                                       // otherwise create a new one          //
      sBashProfile  = getFileAsString(bashProfile);
      bSourceBashRC = !sBashProfile.contains(sourceBashRC);
      bAddNodePath  = !sBashProfile.contains(newPath);
   }
   else
   {
      System.out.println("Creating new .bash_profile...");
   }

   if (bSourceBashRC || bAddNodePath)
   {
      if (bFileExists)
      {
         System.out.println("Saving .bash_profile to .bash_profile.backup...");
         writeFile(new File(home, ".bash_profile.backup"), sBashProfile);
      }
      if (bSourceBashRC)
      {
         System.out.println("Updating .bash_profile to source .bashrc...");
         String entry =
            "# reactjava sourcing bashrc from here in order to pick up nvm\n"
               + sourceBashRC + "\n\n";

         sBashProfile = localNodeBashUpdateEntry(sBashProfile, entry);
      }
      if (bAddNodePath)
      {
         System.out.println("Updating .bash_profile to add node path...");
         String entry =
            "# reactjava add node path\n"
               + "export PATH=\"" + newPath + ":$PATH\"\n\n";

         sBashProfile = localNodeBashUpdateEntry(sBashProfile, entry);
      }

      writeFile(bashProfile, sBashProfile);
      System.out.println("Done");
   }

   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       localNodeBashUpdate - update bash files to support node installation
                                                                              */
                                                                             /**
            Update bash files to support node installation.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static String localNodeBashUpdateEntry(
   String sBashProfile,
   String entry)
{
   String header = entry.substring(0, entry.indexOf('\n') + 1);
   int idx = sBashProfile.indexOf(header);
   if (idx >= 0)
   {
                                 // remove previous entry               //
      int idxEnd =
         sBashProfile.indexOf( '\n', sBashProfile.indexOf('\n', idx) + 1) + 2;

      if (idxEnd < 0)
      {
         throw new IllegalStateException("Existing entry in .bash_profile");
      }

      sBashProfile =
         sBashProfile.substring(0, idx)
            + entry
            + (idxEnd < sBashProfile.length()
               ? sBashProfile.substring(idxEnd) : "");
   }
   else
   {
      sBashProfile = entry + sBashProfile;
   }

   return(sBashProfile);
}
/*------------------------------------------------------------------------------

@name       main - standard main routine
                                                                              */
                                                                             /**
            Standard main routine.

            Examples:

            1. Create new project with the latest version

               create-reactjava-app MyNewProject

            2. Create new project with version 0.1.1810191229

               create-reactjava-app -version 0.1.1810191229 MyNewProject

            3. Update project with the latest version

               create-reactjava-app update MyNewProject

            4. Update project with version 0.1.1810191229

               create-reactjava-app -version 0.1.1810191229 update MyNewProject


@return     void

@param      args     args[0] - platform specification

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void main(
   String[] args)
{
   List<String> argsList    = new ArrayList<String>(Arrays.asList(args));
   boolean      bRelaunched = argsList.remove("-relaunched");
   String       arg0        = argsList.size() > 0 ? argsList.get(0) : null;
   String       arg0LC      = arg0 != null ? arg0.toLowerCase() : null;
   boolean      bOpRelaunch = arg0 != null && !kNO_RELAUNCH.contains(arg0LC);
   boolean      bRelaunch   = !bRelaunched && bOpRelaunch;
   try
   {
      if (bRelaunch)
      {
         getLatestJarAndRelaunch(argsList);
      }
      else
      {
         doOp(argsList);
      }
   }
   catch(Exception e)
   {
      e.printStackTrace();
   }
}
/*------------------------------------------------------------------------------

@name       moveNativeProjectSupport - move native project support
                                                                              */
                                                                             /**
            Move native project support.

@return     project folder

@param      nativeProjectDir     directory of native project support
@param      projectDir           project directory

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File moveNativeProjectSupport(
   File   nativeProjectDir,
   File   projectDir)
   throws Exception
{
   for (File src : nativeProjectDir.listFiles())
   {
      Files.move(
         src.toPath(),
         new File(projectDir, src.getName()).toPath(),
         LinkOption.NOFOLLOW_LINKS);
   }

   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       nodeInitialize - initialize node and npm if not already
                                                                              */
                                                                             /**
            Initialize node and npm if not already.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File nodeInitialize(
   File     projectDir)
   throws   Exception
{
   File     home = new File(System.getProperty("user.home"));
   int      err  = 0;
   String[] args;

   boolean  bInstallNode = !getNodeInstalled();

   if (bInstallNode && getOSWindows())
   {
      bInstallNode = false;
      System.out.println("\nYou must install node.js first (nodejs.org)...");
   }
   if (bInstallNode)
   {
                                       // we use nvm to install node and npm  //
                                       // so we can do it all without being a //
                                       // superuser...                        //
                                       // first see if nvm already exists     //
      args =
         new String[]
         {
            toOSPath(
               "source ~/.nvm/nvm.sh;"
          +    "source ~/.nvm/bash_completion;"
          +    "nvm --version")};
      err = exec(args, home);
   }
   if (bInstallNode && err != 0)
   {
      System.out.println("\nInstalling nvm...");
                                       // invoke through bash since that is   //
                                       // what interprets the pipe command    //
      args =
         new String[]
         {
            "curl https://raw.githubusercontent.com/"
          + "creationix/nvm/v0.25.0/install.sh | bash"
         };

      if ((err = exec(args, home)) != 0)
      {
         System.out.println("Command returned errCode=" + err);
      }
      System.out.println("Done");
   }
   if (bInstallNode)
   {
      System.out.println("\nInstalling node and npm locally...");
      args =
         new String[]
         {
            toOSPath(
               "source ~/.nvm/nvm.sh;"
          +    "source ~/.nvm/bash_completion;"
          +    "nvm install stable")};

      if ((err = exec(args, home)) != 0)
      {
         throw new IllegalStateException("Command returned errCode=" + err);
      }
      System.out.println("Done");
                                       // update bash files for new install   //
      localNodeBashUpdate(projectDir);
                                       // use bash to see if node and npm     //
                                       // exist since that will use $PATH     //
      if (!getNodeInstalled())
      {
         throw new IllegalStateException("node was not installed");
      }
      if (!getNpmInstalled())
      {
         throw new IllegalStateException("npm was not installed");
      }

      //System.out.println("\nMaking folder for the npm global packages...");
      //new File(home, ".npm-packages").mkdirs();
      //System.out.println("Done");
      //
      //System.out.println("\nTelling npm where to find/store them...");
      //args =
      //   new String[]
      //   {"npm config set prefix" + home + "/.npm-packages"};
      //
      //if ((err = exec(args, home, nodePath)) != 0)
      //{
      //   throw new IllegalStateException("Command returned errCode=" + err);
      //}
      //System.out.println("Done");
   }
   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       publishGWTSDK - publish core sdk
                                                                              */
                                                                             /**
            Publish core sdk with enhanced core-dev.jar.

@return     void

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void publishGWTSDK()
   throws Exception
{
   String path   = "/Users/brianm/.reactjava/gwt/gwt-2.8.2";
   File   sdkDir = new File(path);
   if (!sdkDir.exists())
   {
      throw new IllegalStateException("Cannot find source core sdk");
   }
                                       // zip the sdk                         //
   File tmpZip = File.createTempFile("tmpZip", ".tmp");
   zipDirectory(sdkDir, tmpZip);
                                       // publish to the 'releases' directory //
   String filename   = "gwt-2.8.2.zip";
   String bucketName = kRELEASE_BUCKET_NAME;
   String objectPath = "releases/gwt/" + filename;
   String uploadURL  = "gs://" + bucketName + "/" + objectPath;

   gcsManager().writeFile(tmpZip.getAbsolutePath(), uploadURL, null,
      (Object response, Object requestToken) ->
      {
         System.out.println(
            ((Map<String,Object>)response).get("status").toString());
      });

   tmpZip.delete();
}
/*------------------------------------------------------------------------------

@name       publishJar - publish reactjava jar file
                                                                              */
                                                                             /**
            Publish reactjava jar file.

            This implementation is in three steps:

               1. publish the jar to the "releases" directory.

               2. Write a "latestReactJavaRelease.txt" text file contaning the
                  full name of the jar file (including version) to the
                  "releases/latest" directory.

               3. Publish the jar to the "releases/latest" directory, renaming
                  the jar file to simply "reactjava.jar".

@return     void

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void publishJar()
   throws Exception
{
   String path = "/Users/brianm/working/io/reactjava/lib/reactjava.jar";
   File   file = new File(path);
   if (!file.exists())
   {
      throw new IllegalStateException("Cannot find source reactjava.jar");
   }
                                       // read the version                    //
   JarFile    jar      = new JarFile(file);
   Manifest   manifest = jar.getManifest();
   Attributes atts     = manifest.getMainAttributes();
   String     version  = atts.getValue("Specification-Version");
   jar.close();
                                       // publish to the 'releases' directory //
   String filename   = "reactjava@" + version + ".jar";
   String bucketName = kRELEASE_BUCKET_NAME;
   String objectPath = "releases/" + filename;
   String uploadURL  = "gs://" + bucketName + "/" + objectPath;

   gcsManager().writeFile(path, uploadURL, null,
      (Object response, Object requestToken) ->
      {
         System.out.println(
            ((Map<String,Object>)response).get("status").toString());
      });
                                       // write the download url              //
   byte[] content =
      (kREACT_JAVA_RELEASES_DOWNLOAD_URL + filename).getBytes("UTF-8");

   objectPath = "releases/latest/latestReactJavaRelease.txt";
   uploadURL  = "gs://" + bucketName + "/" + objectPath;

   gcsManager().writeFile(content, uploadURL);

                                       // publish to 'releases/latest' dir    //
   filename   = "reactjava.jar";
   objectPath = "releases/latest/" + filename;
   uploadURL  = "gs://" + bucketName + "/" + objectPath;

   //List<StorageObject> objects = gcsManager().listBucket(kRELEASE_BUCKET_NAME);
   //for (StorageObject object : objects)
   //{
   //   String objectName = object.getName();
   //   if (objectName.startsWith("releases/latest/reactjava@"))
   //   {
   //      System.out.println("Deleting existing published object " + objectName);
   //      String deleteURL = "gs://" + bucketName + "/" + objectName;
   //      gcsManager.deleteFile(deleteURL);
   //   }
   //}

   gcsManager().writeFile(path, uploadURL, null,
      (Object response, Object requestToken) ->
      {
         System.out.println(
            ((Map<String,Object>)response).get("status").toString());
      });
}
/*------------------------------------------------------------------------------

@name       reactAndReactDomInitialize - install react dom module
                                                                              */
                                                                             /**
            Install react dom module.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File reactAndReactDomInitialize(
   File   projectDir)
{
   String   sHome    = System.getProperty("user.home");
   File     home     = new File(sHome);
   File     wd       = kSRCCFG_NODE_MODULES_IN_PROJECT ? projectDir : home;
   String   nodePath = getLocalNodePath();
   String[] args;

   if (kSRCCFG_NODE_MODULES_IN_PROJECT || getNpmPackageOutOfDate("react"))
   {
      System.out.println("Installing/updating react package...");
      args = new String[]{"npm install react --loglevel=error"};
      if (exec(args, wd, nodePath) != 0)
      {
         throw new IllegalStateException("react was not installed");
      }
      System.out.println("Done");
   }

   if (kSRCCFG_NODE_MODULES_IN_PROJECT || getNpmPackageOutOfDate("react-dom"))
   {
      System.out.println("Installing/updating react-dom package...");
      args = new String[]{"npm install react-dom --loglevel=error"};
      if (exec(args, wd, nodePath) != 0)
      {
         throw new IllegalStateException("react-dom was not installed");
      }
      System.out.println("Done");
   }

   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       reactiveXInitialize - install reactiveX module
                                                                              */
                                                                             /**
            Install reactiveX module.

@return     new project folder

@param      projectDir     project directory

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File reactiveXInitialize(
   File   projectDir)
{
   String   sHome    = System.getProperty("user.home");
   File     home     = new File(sHome);
   File     wd       = kSRCCFG_NODE_MODULES_IN_PROJECT ? projectDir : home;
   String   nodePath = getLocalNodePath();
   String[] args;

   if (kSRCCFG_NODE_MODULES_IN_PROJECT || getNpmPackageOutOfDate("rxjs"))
   {
                                       // RxJs is currently restricted        //
                                       // to version 5.5.11 since             //
                                       // the current jsinterop support       //
                                       // com.github.timofeevda.core.rxjs      //
                                       // is at that version; the support is  //
                                       // now available for version 6 so      //
                                       // update both when time isvailable... //
                                       // note that files of the released     //
                                       // support package have been changed   //
                                       // for compatibility with the ReactJava//
                                       // bundle namespace = "Rx"             //
                                       //    -> namespace = "ReactJava.Rx"    //

      System.out.println("Installing/updating rxjs package...");
      args = new String[]{"npm install rxjs@5.5.11 --loglevel=error"};
      if (exec(args, wd, nodePath) != 0)
      {
         throw new IllegalStateException("rxjs was not installed");
      }
      System.out.println("Done");
   }

   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       reactJavaDirInitialize - initialize reactjava lib if not already
                                                                              */
                                                                             /**
            Initialize reactjava lib if not already.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File reactJavaDirInitialize(
   File   projectDir)
{
   String sHome        = System.getProperty("user.home");
   File   home         = new File(sHome);
   File   reactJavaDir = new File(home, ".reactjava");
   if (!reactJavaDir.exists())
   {
      System.out.println("Creating local .reactjava repository...");
      reactJavaDir.mkdirs();
      System.out.println("Done");
   }

   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       reactNativeInitialize - install react native modules
                                                                              */
                                                                             /**
            Install react native modules.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File reactNativeInitialize(
   File   projectDir)
{
                                       // nodePath will be null if node       //
                                       // installation is not local           //
   String   sHome    = System.getProperty("user.home");
   File     home     = new File(sHome);
   File     wd       = kSRCCFG_NODE_MODULES_IN_PROJECT ? projectDir : home;
   String   nodePath = getLocalNodePath();
   String[] args;

   if (kSRCCFG_NODE_MODULES_IN_PROJECT || getNpmPackageOutOfDate("react-native"))
   {
      System.out.println(
         "Installing/updating react-native (this may take a few minutes)...");

                                       // recommendation is to use npm rather //
                                       // than yarn                           //
                                       // see: https://blog.risingstack.com/  //
                                       // yarn-vs-npm-node-js-package-managers//
      args = new String[]{"npm install react-native --loglevel=error"};
      if (exec(args, wd, nodePath) != 0)
      {
         throw new IllegalStateException("react-native was not installed");
      }
      System.out.println("Done");
   }
   if (kSRCCFG_NODE_MODULES_IN_PROJECT || getNpmPackageOutOfDate("react-native-cli"))
   {
      System.out.println("Installing/updating react-native-cli...");
      args = new String[]{"npm install react-native-cli --loglevel=error"};
      if (exec(args, wd, nodePath) != 0)
      {
         throw new IllegalStateException("react-native-cli was not installed");
      }
      System.out.println("Done");
   }

   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       reactNativeProjectInitialize - copy project template
                                                                              */
                                                                             /**
            Copy project template.

@return     temporary react native project dir

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File reactNativeProjectInitialize(
   File     projectDir)
   throws   Exception
{
                                       // nodePath will be null if node       //
                                       // installation is not local           //
   String   nodePath    = getLocalNodePath();
   String   projectName = projectDir.getName();
   File     nativeDir   = null;
   String[] args;

   System.out.println("Checking for existing project react-native support...");
   File iosDir = new File(projectDir, "ios");
   if (iosDir.exists() && iosDir.isDirectory())
   {
      System.out.println("Existing project react-native support found");
   }
   else
   {
                                       // react-native init needs an empty    //
                                       // folder to work correctly, so use a  //
                                       // new temporary directory             //

      File tempProjectDir = createLocalTempFile("reactJavaNativeTempDir", true);

                                       // now initialize react native support //

      System.out.println("Initializing react native project...");
      args =
         new String[]{"react-native init " + projectName};

      if (exec(args, tempProjectDir, nodePath) != 0)
      {
         throw new IllegalStateException(
            "react-native-cli init returned error");
      }
      System.out.println("Done");

      nativeDir = new File(tempProjectDir, projectName);
      reactNativeUpdateLaunchPage(nativeDir);
   }

   return(nativeDir);
}
/*------------------------------------------------------------------------------

@name       reactNativeTrim - copy project template
                                                                              */
                                                                             /**
            Copy project template.

@return     new project folder

@param      projectName    new project name

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File reactNativeTrim(
   File   projectDir)
{
   for (File chase : projectDir.listFiles())
   {
      if (!kREACT_NATIVE_KEEP_FILENAMES.contains(chase.getName()))
      {
         chase.delete();
      }
   }

   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       reactNativeUpdateLaunchPage - update react-native-cli launch page
                                                                              */
                                                                             /**
            Update react-native-cli launch page.

@return     new project folder

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File reactNativeUpdateLaunchPage(
   File   projectDir)
   throws Exception
{
   System.out.println("Updating react-native-cli launch web page...");

   File launchPage =
      new File(
         projectDir,
         toOSPath(
            "node_modules/react-native/local-cli/server/"
          + "util/debugger-ui/index.html"));

   if (!launchPage.exists())
   {
      throw new IllegalStateException(
         "Cannot find " + launchPage.getAbsolutePath());
   }

   String page  = getFileAsString(launchPage);
   String token = "React Native JS code runs as a web worker inside this tab.";
   int    idx   = page.indexOf(token);
   if (idx < 0)
   {
      System.out.println(
         "WARNING: Launch page contents unexpected: not updated.");
   }
   else
   {
      String tokenNew =
         "ReactJava Native JS code runs as a web worker inside this tab.<br><br>\n"
       + "Ensure Remote Debugging is enabled by clicking "
       + "on the emulator and pressing Option-D to bring up the developer "
       + "menu.<br><br>\n"
       + "If this is the first time launched since the project was loaded or any "
       + "native code was changed, the native application will be built "
       + "which can take a number of minutes before the application will be "
       + "started. You can monitor the build progress in the "
       + "'react-native run-ios' tab of the IntelliJ Debug panel.<br><br> "
       + "It is possible the application will not start until Option-Command-I "
       + "is pressed as described below.<br><br>\n";

      page = page.replace(token, tokenNew);

      writeFile(launchPage, page);
   }

   System.out.println("Done");
   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       shadowToSrc - rename shadow directory to src directory
                                                                              */
                                                                             /**
            Rename shadow directory to src directory.

@return     project directory

@param      projectDir  project directory

@history    Sat Nov 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File shadowToSrc()
   throws IOException
{
   long start      = System.currentTimeMillis();
   File projectDir = new File(System.getProperty("user.dir"));
   Path shadowPath = new File(projectDir, "out/build/shadow").toPath();
   Path srcPath    = new File(projectDir, "src").toPath();

   if (shadowPath.toFile().exists())
   {
      List<Path> paths =
         Files.walk(shadowPath)
            .filter(Files::isRegularFile)
            .collect(toList());

      copyFiles(shadowPath, srcPath, paths, true, null);
   }

   System.out.println(
      "shadowToSrc(): exiting, duration=" + (System.currentTimeMillis() - start));

   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       srcToShadow - copy src directory to shadow directory
                                                                              */
                                                                             /**
            Copy src directory to shadow directory.

@return     project directory

@param      projectDir  project directory

@history    Sat Nov 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File srcToShadow()
   throws IOException
{
   long start      = System.currentTimeMillis();
   File projectDir = new File(System.getProperty("user.dir"));
   Path srcPath    = new File(projectDir, "src").toPath();
   Path shadowPath = new File(projectDir, "out/build/shadow").toPath();

   List<Path> filter =
      new ArrayList<Path>(Arrays.asList(
         new Path[]
         {
            new File(
               projectDir,
               "src/io/reactjava/client/examples/helloworld/App.java").toPath(),
         }));

   copyFiles(srcPath, shadowPath, filter, true, null);
   System.out.println(
      "srcToShadow(): exiting, duration=" + (System.currentTimeMillis() - start));

   return(projectDir);
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
public static String toOSPath(
   String raw)
{
   return(getOSWindows() ? raw.replace ("/", File.separator) : raw);
}
/*------------------------------------------------------------------------------

@name       touchJavaSource - touch a source file to force project rebuild
                                                                              */
                                                                             /**
            Touch a source file to force project rebuild.

@return     void

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void touchJavaSource()
   throws Exception
{
   File projectDir = new File(System.getProperty("user.dir"));
   File srscDir    = new File(projectDir, "src");
   File sourceFile = findJavaSource(srscDir);
   System.out.println("Touch " + sourceFile);

   sourceFile.setLastModified(System.currentTimeMillis());
}
/*------------------------------------------------------------------------------

@name       updateJarManifest - update the jar manifest
                                                                              */
                                                                             /**
            Update the jar manifest.

@return     void

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void updateJarManifest()
{
   try
   {
      String timestamp = new SimpleDateFormat("yyMMddHHmm").format(new Date());
      String version   = "0.1." + timestamp;
      File   prjDir    = new File(System.getProperty("user.dir"));
      File   srcDir    = new File(prjDir, "src");
      File   template  = new File(srcDir, toOSPath("META-INF/MANIFEST.TEMPLATE"));
      String content   = getFileAsString(template).replace("%version%",version);

      writeFile(new File(srcDir, toOSPath("META-INF/MANIFEST.MF")), content);
   }
   catch(Throwable t)
   {
      t.printStackTrace();
   }
}
/*------------------------------------------------------------------------------

@name       updateProjectTemplateResource - update the project template resource
                                                                              */
                                                                             /**
            Update the project template resource. The assumption is this is
            executed as part of a build ant post process task so the output
            is written to the out/production directory.

@return     void

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void updateProjectTemplateResource()
{
   File   projectDir  = new File(System.getProperty("user.dir"));
   File   outDir      = new File(projectDir, toOSPath("out/production/ReactJava"));
   String path        = toOSPath("io/reactjava/codegenerator/ProjectTemplate.zip");
   File   templateZip = new File(outDir, path);
   File   templateDir = new File(projectDir, toOSPath("resources/template"));
   try
   {
      zipDirectory(templateDir, templateZip);
   }
   catch(Throwable t)
   {
      t.printStackTrace();
   }
}
/*------------------------------------------------------------------------------

@name       updateReactJavaCore - standard main routine
                                                                              */
                                                                             /**
            Standard main routine

@return     void

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void updateReactJavaCore(
   File    projectDir,
   String  version,
   boolean bUpdate)
   throws  Exception
{
   if (bUpdate)
   {
      if (!projectDir.exists())
      {
         throw new Exception(
            "Existing project not found at " + projectDir.getAbsolutePath());
      }
   }
   else
   {
                                       // delete if already exists            //
      deleteDirectory(projectDir);
   }
                                       // initialize/update reactjava lib     //
   reactJavaDirInitialize(projectDir);
                                       // initialize/update node and npm      //
   nodeInitialize(projectDir);

                                       // copy project template               //
   copyProjectTemplate(projectDir, bUpdate);

                                       // copy reactjava.jar                  //
   copyReactJavaJarToProject(projectDir, version);

                                       // initialize/update react, react-dom  //
   reactAndReactDomInitialize(projectDir);

                                       // initialize/update browserify        //
   browserifyInitialize(projectDir);
                                       // initialize/update reactiveX         //
   reactiveXInitialize(projectDir);
                                       // initialize react native modules now //
                                       // so project run configurations iOS   //
                                       // and android will be able to         //
                                       // reference react-native-cli and not  //
                                       // indicate an error                   //
   reactNativeInitialize(projectDir);
                                       // initialize/update core support       //
   gwtInitialize(projectDir);
                                       // delete any temp directory           //
   deleteDirectory(getTempDir());

   System.out.println(
      "Successfully "+ (bUpdate ? "updated" : "created") +" react java project "
         + projectDir.getName() + "------------------");
}
/*------------------------------------------------------------------------------

@name       writeFile - write specified file with specified content
                                                                              */
                                                                             /**
            Write specified file with specified content

@return     void

@params     dst         destination file
@params     content     content

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File writeFile(
   File   dst,
   String content)
   throws IOException
{
   FileOutputStream out = new FileOutputStream(dst);
   out.write(content.getBytes("UTF-8"));
   out.close();
   return(dst);
}
/*------------------------------------------------------------------------------

@name       zipExtractFile - extract file from zip file
                                                                              */
                                                                             /**
            Extract file from zip file.

@return     void

@param      zipFile        output zip file
@param      entryName      entry name
@param      outputFile     output file

@history    Fri Nov 6, 2003 04:35:52 (Giavaneers - LBM) created.

                                                                              */
//------------------------------------------------------------------------------
public static boolean zipExtractFile(
   File                 zipFile,
   String               entryName,
   File                 outputFile)
   throws               Exception
{
   boolean              bSuccess = false;
   byte[]               bytes    = new byte[1024];
   ZipFile              zip      = null;
   ZipEntry             zipEntry = null;
   InputStream          zipIn    = null;
   BufferedInputStream  in       = null;
   FileOutputStream     fileOut  = null;
   BufferedOutputStream out      = null;
   int                  bytesRead;

   zip      = new ZipFile(zipFile);
   zipEntry = zip.getEntry(entryName);

   if (zipEntry != null)
   {
      zipIn   = zip.getInputStream(zipEntry);
      in      = new BufferedInputStream(zipIn);
      fileOut = new FileOutputStream(outputFile);
      out     = new BufferedOutputStream(fileOut);

      while ((bytesRead = in.read(bytes)) >= 0)
      {
         out.write(bytes, 0, bytesRead);
      }
      bSuccess = true;
   }
   if (zip != null)
   {
      zip.close();
   }
   if (out != null)
   {
      out.close();
   }

   return(bSuccess);
}
/*------------------------------------------------------------------------------

@name       zipExtractFiles - extract all files from zip file
                                                                              */
                                                                             /**
            Extract all files from zip file.

@return     true iff successful

@param      zipFile           output zip file
@param      outputDirectory   output directory

@history    Fri Nov 6, 2003 04:35:52 (Giavaneers - LBM) created.

                                                                              */
//------------------------------------------------------------------------------
public static void zipExtractFiles(
   ZipInputStream inZip,
   File           outputDirectory)
   throws         Exception
{
   while (true)
   {
      ZipEntry zipEntry = inZip.getNextEntry();
      if (zipEntry == null)
      {
         break;
      }

      File newFile = new File(outputDirectory, zipEntry.getName());
      FileOutputStream out = new FileOutputStream(newFile);

      IConfiguration.fastChannelCopy(inZip, out);

      //for (int numRead = inZip.read(); numRead != -1; numRead = inZip.read())
      //{
      //   out.write(numRead);
      //}

      inZip.closeEntry();
      out.close();
   }
   inZip.close();
}
/*------------------------------------------------------------------------------

@name       zipExtractFiles - extract all files from zip file
                                                                              */
                                                                             /**
            Extract all files from zip file.

@return     true iff successful

@param      zipFile           output zip file
@param      outputDirectory   output directory

@history    Fri Nov 6, 2003 04:35:52 (Giavaneers - LBM) created.

                                                                              */
//------------------------------------------------------------------------------
public static boolean zipExtractFiles(
   File    zipFile,
   File    outputDirectory)
   throws  Exception
{
   boolean bSuccess     = true;
   ZipFile zip          = null;
   int     total        = 0;
   int     numExtracted = 0;

   zip   = new ZipFile(zipFile);
   total = zip.size();

   for(Enumeration entries = zip.entries();
       bSuccess && entries.hasMoreElements();
       numExtracted++)
   {
      //System.out.println("Progress = " + (numExtracted * 100 / total) + "%");

      ZipEntry zipEntry = (ZipEntry)entries.nextElement();
      if (zipEntry.isDirectory())
      {
         continue;
      }
      String entryName = zipEntry.getName();
      File   outFile   = new File(outputDirectory, entryName);
      File   parent    = outFile.getParentFile();

      if (!parent.exists())
      {
                                       // ensure the appropriate directories  //
                                       // exists                              //
         parent.mkdirs();
      }
                                    // extract the entry                   //
      bSuccess = zipExtractFile(zipFile, entryName, outFile);
   }
   if (zip != null)
   {
      zip.close();
   }

   return(bSuccess);
}
/*------------------------------------------------------------------------------

@name       zipDirectory - zip the specified directory
                                                                              */
                                                                             /**
            Extract all files from zip file.

@return     true iff successful

@param      zipFile           output zip file
@param      outputDirectory   output directory

@history    Fri Nov 6, 2003 04:35:52 (Giavaneers - LBM) created.

                                                                              */
//------------------------------------------------------------------------------
public static void zipDirectory(
   File       srcDir,
   File       dst)
   throws     Exception
{
   byte[]     buffer = new byte[1024];
   int        refIdx = srcDir.getAbsolutePath().length();
   List<File> files  = listDescendantFiles(srcDir,null,null, false, false,null);
   int        length;

   ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(dst));
   for(File file : files)
   {
      String   entryName = file.getAbsolutePath().substring(refIdx);
      ZipEntry zipEntry  = new ZipEntry(entryName);
      zipOut.putNextEntry(zipEntry);

      FileInputStream fileIn = new FileInputStream(file);
      while ((length = fileIn.read(buffer)) > 0)
      {
         zipOut.write(buffer, 0, length);
      }
      zipOut.closeEntry();
      fileIn.close();
   }
   zipOut.close();
}
///*------------------------------------------------------------------------------
//
//@name       generateBundleScript - generate bundle script
//                                                                              */
//                                                                             /**
//            Generate bundle script
//
//@return     void
//
//@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created
//
//@notes
//                                                                              */
////------------------------------------------------------------------------------
//public static File generateBundleScript(
//   String     platform,
//   TreeLogger logger)
//   throws     Exception
//{
//                                       // generate core libararyand index.js   //
//   generateIndexJs(generateGWTLibrary());
//
//                                       // generate webpackconfig.js           //
//   File webpackConfig = generateWebpackConfigJs();
//
//                                       // invoke webpack                      //
//   File bundleScript = invokeWebpack();
//                                       // cleanup the project directory       //
//   webpackConfig.delete();
//
//   return(bundleScript);
//}
/*------------------------------------------------------------------------------

@name       generateIndexJs - generate index js file
                                                                              */
                                                                             /**
            Generate index js file to the "out/build/src" directory

@return     void

@params     reactNativeDir    react native build directory

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
//public static void generateIndexJs(
//   File   gwtLibrary)
//   throws Exception
//{
//   String  indexJs = bWeb ? kINDEX_JS : kINDEX_JS_REACT_NATIVE;
//   File    dstFile = new File(gwtLibrary.getParentFile(), "index.js");
//   ReactCodeGenerator.writeFile(dstFile, indexJs);
//}
///*------------------------------------------------------------------------------
//
//@name       generateWebpackConfigJs - generate webpackConfig js file
//                                                                              */
//                                                                             /**
//            Generate webpackConfig js file
//
//@return     void
//
//@params     reactNativeDir    react native build directory
//
//@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created
//
//@notes
//                                                                              */
////------------------------------------------------------------------------------
//public static File generateWebpackConfigJs()
//   throws Exception
//{
//   File webpackConfig =
//      new File(new File(System.getProperty("user.dir")), "webpack.config.js");
//
//   ReactCodeGenerator.writeFile(webpackConfig, kWEBPACK_CONFIG_JS_REACT_NATIVE);
//   return(webpackConfig);
//}
///*------------------------------------------------------------------------------
//
//@name       invokeWebpack - invoke webpack to build reactnative javascript
//                                                                              */
//                                                                             /**
//            Invoke webpack to build reactnative javascript file
//
//@return     void
//
//@params     artifactDir    artifact directory
//
//@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created
//
//@notes
//                                                                              */
////------------------------------------------------------------------------------
//public static File invokeWebpack()
//   throws Exception
//{
//   File projectDir = new File(System.getProperty("user.dir"));
//   String command  = "webpack";
//   int err = Runtime.getRuntime().exec(command, null, projectDir).waitFor();
//   if (err != 0)
//   {
//      throw new IllegalStateException("webpack returned errCode=" + err);
//   }
//
//   return(new File(projectDir, "out/build/reactnative/dist/reactjavabundle"));
//}
/*==============================================================================

name:       InputStreamStringBuilder - input stream string builder

purpose:    Builds a string from a specified input stream

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class InputStreamStringBuilder implements Runnable
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
protected InputStream in;              // input stream                        //
protected String[]    container;       // result container                    //

/*------------------------------------------------------------------------------

@name       InputStreamStringBuilder - constructor for specified input stream
                                                                              */
                                                                             /**
            Constructor for specified input stream and result container

@return     An instance of InputStreamStringBuilder if successful.

@param      in             input stream
@param      container      result container

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public InputStreamStringBuilder(
   InputStream in,
   String[]    container)
{
   this.in        = in;
   this.container = container;
   if (container.length < 1)
   {
      throw new IllegalArgumentException("Container must have length >= 1");
   }
   new Thread(this).start();
}
/*------------------------------------------------------------------------------

@name       run - standard run method
                                                                              */
                                                                             /**
            Runnable standard run() method

@return     void

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void run()
{
   try
   {
      StringBuffer   buffer = new StringBuffer();
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String         line;

      while ((line = reader.readLine()) != null)
      {
         buffer.append(line);
      }
      container[0] = buffer.toString();
   }
   catch (IOException ioe)
   {
      ioe.printStackTrace();
   }
}
}//====================================// end inner InputStreamStringBuilder =//
}//====================================// end JavascriptBundler ==============//
