/*==============================================================================

name:       JavascriptBundler - ReactJava GWT compiler post processor

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
package io.reactjava.compiler.codegenerator;
                                       // imports --------------------------- //
import com.giavaneers.net.cloudstorage.GoogleCloudStorageManagerBase;
import io.reactjava.client.providers.platform.IPlatform;
import io.reactjava.compiler.jsx.IConfiguration;
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

                                       // react-native support needs debug    //
                                       // (LBM 191012)                        //
public static final boolean kSRCCFG_REACT_NATIVE_ALWAYS       = false;

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

public static final String kGWT_RELEASES_DOWNLOAD_URL =
   kREACT_JAVA_RELEASE_DOWNLOAD_BUCKET_URL + "releases/gwt/";

public static final String kGWT_LATEST_RELEASE_FILENAME =
   "latestRelease.txt";

public static final String kGWT_DOWNLOAD_URL_2_8_2 =
   "https://goo.gl/pZZPXS";

public static final String kGWT_DOWNLOAD_URL_CONTAINER_URL =
   kGWT_RELEASES_DOWNLOAD_URL + kGWT_LATEST_RELEASE_FILENAME;

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
      add("buildreleasejar");
      add("creategwtlibrary");
      add("deletetempgwtcache");
      add("ensurereactjavagwtdev");
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
      "src",
      "war"
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
 + "      var $gwt = {};\n"
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
   "0.2." + new SimpleDateFormat(kDATE_FORMAT_PATTERN_VERSION).format(new Date());

                                       // matched with contents of            //
                                       // 'ReactJavaGWTersion.txt' in gwt     //
                                       // distribution                        //
                                       // Note that moving to 2.9 is known to //
                                       // be problematic, requiring the Jetty //
                                       // embedded implementation to be       //
                                       // abandoned and use of a separate     //
                                       // server instead (since the embedded  //
                                       // version of Jetty is built with a    //
                                       // specific version of GWT, and since  //
                                       // support for J2CL from IntelliJ is   //
                                       // coming soon, which will change the  //
                                       // way things work, stay with 2.8 now  //
//public static final String kGWT =
//   "gwt-2.9.0";

public static final String kGWT =
   "gwt-2.8.2";

public static final String kVERSION_GWT =
   kGWT + ".%version%";

public static final String kGWT_EMBEDDED_FILENAME =
   "ReactJavaGWTEmbedded.txt";
public static final String kGWT_VERSION_FILENAME =
   "ReactJavaGWTVersion.txt";
                                       // consists of the kVERSION followed by//
                                       // the enhanced compiler version       //
public static final String kVERSION_IMPLEMENTATION =
   kVERSION + ";" + kVERSION_GWT;

public static final Manifest kREACT_JAVA_JAR_MANIFEST =
   new Manifest()
   {{
      Attributes atts = getMainAttributes();
      atts.putValue("Manifest-Version",       "1.0");
      atts.putValue("Main-Class", "io.reactjava.compiler.codegenerator.JavascriptBundler");
      atts.putValue("Name",                   "io/reactjava");
      atts.putValue("Specification-Title",    "React Java Classes");
      atts.putValue("Specification-Version",  kVERSION);
      atts.putValue("Specification-Vendor",   "iBigDea");
      atts.putValue("Implementation-Title",   "io.reactjava");
      atts.putValue("Implementation-Version", kVERSION_IMPLEMENTATION);
      atts.putValue("Implementation-Vendor",  "Giavaneers, Inc.");
   }};

public static final List<String> kGWT_DEV_JAR_ENHANCEMENTS =
   Arrays.asList(
      "com/google/gwt/dev/Precompile.java",
      "com/google/gwt/dev/javac/CompilationProblemReporter.java",
      "com/google/gwt/dev/javac/CompilationStateBuilder.java",
      "com/google/gwt/dev/javac/CompilationUnitBuilder.java",

      "com/google/gwt/dev/Precompile.class",
      "com/google/gwt/dev/javac/CompilationProblemReporter.class",
      "com/google/gwt/dev/javac/CompilationStateBuilder.class",
      "com/google/gwt/dev/javac/CompilationStateBuilder$1.class",
      "com/google/gwt/dev/javac/CompilationStateBuilder$CompileMoreLater.class",
      "com/google/gwt/dev/javac/CompilationStateBuilder$CompileMoreLater$1.class",
      "com/google/gwt/dev/javac/CompilationStateBuilder$CompileMoreLater$UnitProcessorImpl.class",
      "com/google/gwt/dev/javac/CompilationStateBuilder$CompileMoreLater$UnitProcessorImpl$1.class",
      "com/google/gwt/dev/javac/CompilationUnitBuilder.class",
      "com/google/gwt/dev/javac/CompilationUnitBuilder$1.class",
      "com/google/gwt/dev/javac/CompilationUnitBuilder$GeneratedCompilationUnit.class",
      "com/google/gwt/dev/javac/CompilationUnitBuilder$GeneratedCompilationUnitBuilder.class",
      "com/google/gwt/dev/javac/CompilationUnitBuilder$ResourceCompilationUnitBuilder.class",
      kGWT_EMBEDDED_FILENAME);
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

@param      platform    platform specification

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
            Install browserify modules.

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

   if (kSRCCFG_NODE_MODULES_IN_PROJECT || getNpmPackageOutOfDate("common-shakeify"))
   {
      System.out.println("Installing/updating common-shakeify package...");
      args = new String[]{"npm install common-shakeify --loglevel=error"};
      if (exec(args, wd, nodePath) != 0)
      {
         throw new IllegalStateException("common-shakeify was not installed");
      }
      System.out.println("Done");
   }
   if (kSRCCFG_NODE_MODULES_IN_PROJECT || getNpmPackageOutOfDate("envify"))
   {
      System.out.println("Installing/updating envify package...");
      args = new String[]{"npm install envify --loglevel=error"};
      if (exec(args, wd, nodePath) != 0)
      {
         throw new IllegalStateException("envify was not installed");
      }
      System.out.println("Done");
   }
   if (kSRCCFG_NODE_MODULES_IN_PROJECT || getNpmPackageOutOfDate("browserify"))
   {
                                       // should have been updated by         //
                                       // common-shakeify above               //
      System.out.println("Installing/updating browserify package...");
      args = new String[]{"npm install browserify --loglevel=error"};
      if (exec(args, wd, nodePath) != 0)
      {
         throw new IllegalStateException("browserify was not installed");
      }
      System.out.println("Done");
   }
   if (kSRCCFG_NODE_MODULES_IN_PROJECT || getNpmPackageOutOfDate("uglify-js"))
   {
                                       // should have been updated by         //
                                       // browserify above                    //
      System.out.println("Installing/updating uglify-js package...");
      args = new String[]{"npm install uglify-js --loglevel=error"};
      if (exec(args, wd, nodePath) != 0)
      {
         throw new IllegalStateException("uglify-js was not installed");
      }
      System.out.println("Done");
   }

   return(projectDir);
}
/*------------------------------------------------------------------------------

@name       buildGWTDevJar - build core-dev.jar
                                                                              */
                                                                             /**
            Generate the enhanced core-dev.jar from the existing, adding the
            reactjava.jar contents.

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
      "com/google/gwt/dev/Precompile",
      "com/google/gwt/dev/javac/CompilationProblemReporter",
      "com/google/gwt/dev/javac/CompilationStateBuilder",
      "com/google/gwt/dev/javac/CompilationUnitBuilder",
   };
   String[] classEntries =
   {
      "com/google/gwt/dev/Precompile",
      "com/google/gwt/dev/javac/CompilationProblemReporter",
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
      new File("/Users/brianm/.reactjava/gwt/" + kGWT + "/gwt-dev.jar"),
      targets, entries);
                                       // update the release version          //
   updateGWTInstalledVersion();

   System.out.println("JavascriptBundler.buildGWTDevJar(): exiting");
}
/*------------------------------------------------------------------------------

@name       buildJar - build jar
                                                                              */
                                                                             /**
            Generate jar

@param      args, where

               args[0]     manifest version
               args[1]     main class
               args[2]     name
               args[3]     specification title
               args[4]     specification version
               args[5]     specification vendor
               args[6]     implementation title
               args[7]     implementation version
               args[8]     implementation vendor
               args[9]     destination jar path
               args[10]    src classes path
               args[...]   resource folder paths, one per arg



@history    Mon Aug 18, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void buildJar(
   String[] args)
   throws   Exception
{
   String timestamp =
      new SimpleDateFormat(kDATE_FORMAT_PATTERN_VERSION).format(new Date());

   String specificationVersionStamped =
      args[4].endsWith(".") ? args[4] + timestamp : args[4];

   String implementationVersionStamped =
      args[7].endsWith(".") ? args[7] + timestamp : args[7];

   Manifest manifest =
      new Manifest()
      {{
         Attributes atts = getMainAttributes();
         atts.putValue("Manifest-Version",       args[0]);
         atts.putValue("Main-Class",             args[1]);
         atts.putValue("Name",                   args[2]);
         atts.putValue("Specification-Title",    args[3]);
         atts.putValue("Specification-Version",  specificationVersionStamped);
         atts.putValue("Specification-Vendor",   args[5]);
         atts.putValue("Implementation-Title",   args[6]);
         atts.putValue("Implementation-Version", implementationVersionStamped);
         atts.putValue("Implementation-Vendor",  args[8]);
      }};
                                       // get any resource directories        //
   String[] rsrcDirs = null;
   if (args.length > 11)
   {
      rsrcDirs = new String[args.length - 11];
      System.arraycopy(args, 11, rsrcDirs, 0, rsrcDirs.length);
   }

   buildJar(manifest, args[9], args[10], rsrcDirs);
}
/*------------------------------------------------------------------------------

@name       buildJar - build jar
                                                                              */
                                                                             /**
            Generate jar

@history    Mon Aug 18, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void buildJar(
   Manifest  manifest,
   String    dstJarPath,
   String    outClassesPath,
   String... rsrcDirs)
   throws    Exception
{
   System.out.println("JavascriptBundler.buildJar(): entered");

   File         projectDir   = new File(System.getProperty("user.dir"));
   List<Path>   paths        = new ArrayList<>();
   List<Path>   entries      = new ArrayList<>();
   File         dstJar       = new File(dstJarPath);
   List<String> rsrcDirPaths = new ArrayList<>();
   if (rsrcDirs != null)
   {
      rsrcDirPaths.addAll(Arrays.asList(rsrcDirs));
   }

   List<String> bases =
      new ArrayList<String>()
      {{
         add("src");
         add("classes");
         add(outClassesPath);
         addAll(rsrcDirPaths);
      }};

   for (String base : bases)
   {
      File baseDir = new File(projectDir, base);
      if (!baseDir.exists())
      {
         continue;
      }

      Path path = baseDir.toPath();
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

         Path reference =
            rsrcDirPaths.contains(base)
               ? baseDir.getParentFile().toPath() : path;

         Path entry = reference.relativize(basePath);

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

   jarFiles(dstJar, paths, entries, manifest);
   System.out.println("JavascriptBundler.buildJar(): exiting");
}
/*------------------------------------------------------------------------------

@name       buildReactJavaJar - build reactjava.jar
                                                                              */
                                                                             /**
            Generate reactjava.jar

@history    Sun Dec 23, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void buildReactJavaJar()
   throws Exception
{
   System.out.println("JavascriptBundler.buildReactJavaJar(): entered");

   buildJar(
      kREACT_JAVA_JAR_MANIFEST,
      "/Users/brianm/working/io/reactjava/lib/reactjava.jar",
      "out/artifacts/ReactJava_war_exploded/WEB-INF/classes");

   System.out.println("JavascriptBundler.buildReactJavaJar(): exiting");
}
/*------------------------------------------------------------------------------

@name       buildRelease - build release
                                                                              */
                                                                             /**
            Generate reactjava.jar and the enhanced core-dev.jar

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

@name       copyGWTReleaseViaGCS - copy gwt release from cloud storage
                                                                              */
                                                                             /**
            Copy gwt release from cloud storage.

@return     downloaded file

@param      dstDir      destination directory

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File copyGWTReleaseViaGCS(
   File   dstDir,
   String installVersion)
   throws Exception
{
   File   gwtReleaseFile = new File(dstDir, installVersion);
   String gwtReleaseURL  =
      kREACT_JAVA_RELEASE_DOWNLOAD_BUCKET_URL + "releases/gwt/" + installVersion;

   copyFile(gwtReleaseURL, gwtReleaseFile.toPath());

   System.out.println("Downloaded " + installVersion);

   return(gwtReleaseFile);
}
/*------------------------------------------------------------------------------

@name       copyGWTReleaseViaGWTRepository - copy gwt release from repository
                                                                              */
                                                                             /**
            Copy gwt release from GWT repository.

@return     downloaded file

@param      dstDir      destination directory

@history    Thu Oct 22, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File copyGWTReleaseViaGWTRepository(
   File   dstDir,
   String installVersion)
   throws Exception
{
   File   gwtReleaseFile = new File(dstDir, installVersion + ".zip");
   //String gwtReleaseURL  =
   //   kREACT_JAVA_RELEASE_DOWNLOAD_BUCKET_URL + "releases/gwt/" + installVersion;

   copyFile(kGWT_DOWNLOAD_URL_2_8_2, gwtReleaseFile.toPath());

   System.out.println("Downloaded " + installVersion);

   return(gwtReleaseFile);
}
/*------------------------------------------------------------------------------

@name       copyFile - copy project template
                                                                              */
                                                                             /**
            Copy project template.

@param      url    url
@param      dst    dst

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

@param      src                  src path
@param      dst                  dst path
@param      bDeleteExisting      iff true, delete existing
@param      replaceMap           replacement map

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

@param      srcPath              src path
@param      dstPath              dst path
@param      filter               if non-null, file ancestors to be copied
@param      bDeleteExisting      iff true, delete existing
@param      replaceMap           replacement map

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

@name       copyProjectTemplate - copy project template from resource
                                                                              */
                                                                             /**
            Copy project template from resource.

@return     new project folder

@param      projectDir     new project directory
@param      bUpdate        iff true, update

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
      //System.out.println("Downloading latest project template");
      //copyFile(kDOWNLOAD_URL_INTELLIJ_HELLOWORLD_PROJECT_TEMPLATE, temp.toPath());
      throw new IllegalStateException("Templates are not available from the cloud");
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

            Iff jarname == null, use the contents of

               "https://storage.googleapis.com/react4java.io/releases/latest/"
               "latestReactJavaRelease.txt"

            to get the download url so as to not require reactjava.jar to
            include the classes necessary to make gcs access.

@return     project directory

@param      dstDir     destination directory
@param      version    version

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
@param      version     version

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

@param      projectDir     project directory

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void createReactJavaProjectNativeSupport(
   File projectDir)
{
   try
   {
      System.out.println(
         "Creating React Native support for " + projectDir.getAbsolutePath());

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

@param      targetDir    target directory

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

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void deleteTempGWTCache()
   throws IOException
{
   File tempDir = new File(System.getProperty("java.io.tmpdir"));

   System.out.println(
       "Deleting contents of the gwt, core and jetty cache files "
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
               boolean bAccept =
                  name.startsWith("core")
                     || name.startsWith("jetty-")
                     || name.startsWith("gwt");

               System.out.println(
                  "Checking " + new File(dir, name).getAbsolutePath()
                + ": " + (bAccept ? "accepted" : "rejected"));

               return(bAccept);
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

@param      argsList    args list

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void doOp(
   List<String> argsList)
   throws       Exception
{
   File    currentDir   = new File(System.getProperty("user.dir"));
   String  opcode       = null;
   String  version      = null;
   String  versionGWT   = null;
   String  path         = null;
   boolean bReactNative = false;

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
      else if ("-reactnative".equals(arg))
      {
         bReactNative = true;
      }
      else if ("-version".equals(arg))
      {
         if (argsList.size() < i + 1)
         {
            throw new IllegalArgumentException("Need to specify version");
         }
         version = argsList.get(++i);
      }
      else if ("-versiongwt".equals(arg))
      {
         if (argsList.size() < i + 1)
         {
            throw new IllegalArgumentException("Need to specify gwt version");
         }
         versionGWT = argsList.get(++i);
      }
      else if ("buildreleasejar".equals(argLC))
      {
         if (argsList.size() < 12)
         {
            throw new IllegalArgumentException(
               "buildreleasejar requires at least 11 arguments");
         }
         opcode = argLC;
         break;
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
   System.out.println("opcode  =" + opcode);
   System.out.println(
      "projectDir=" + (path != null ? path : currentDir.getAbsolutePath()));

   switch(opcode)
   {
      case "buildgwtdevjar":
      {
         buildGWTDevJar();
         break;
      }
      case "buildrelease":
      {
         buildRelease();
         break;
      }
      case "buildreleasejar":
      {
                                       // remove the opcode                   //
         argsList.remove(0);
         buildJar(argsList.toArray(new String[argsList.size()]));
         break;
      }
      case "creategwtlibrary":
      {
         generateGWTLibrary();
         break;
      }
      case "deletetempgwtcache":
      {
         deleteTempGWTCache();
         break;
      }
      case "ensurereactjavagwtdev":
      {
         ensureReactJavaGWTDev();
         break;
      }
      //case "jsxtransform":
      //{
      //   jsxTransform(path);
      //   break;
      //}
      case "shadowtosrc":
      {
         shadowToSrc();
         break;
      }
      case "srctoshadow":
      {
         srcToShadow();
         break;
      }
      case "updatejarmanifest":
      {
         updateJarManifest();
         break;
      }
      case "updateprojecttemplateresource":
      {
         updateProjectTemplateResource();
         break;
      }
      case "platformweb":
      case "platformios":
      {
         assignPlatform(opcode);
         break;
      }
      case "publishgwtsdk":
      {
         publishGWTSDK();
         break;
      }
      case "publishjar":
      {
         publishJar();
         break;
      }
      case "createproject":
      case "update":
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

         updateReactJavaCore(
            projectDir, version, bReactNative, "update".equals(opcode));
         break;
      }
      default:
      {
         throw new IllegalArgumentException(opcode);
      }
   }
   System.out.println("duration=" + ((System.nanoTime() - start) / 1000000L));
}
/*------------------------------------------------------------------------------

@name       ensureReactJavaGWTDev - ensure ReactJava GWT development jar
                                                                              */
                                                                             /**
            Ensure the target GWT SDK contains a ReactJava compatible
            gwt-dev.jar. If not, modifies the target gwt-dev.jar to be React
            Java compatible.

            This approach allows a GWT SDK to be downloaded without
            change from any source repository, rather than requiring a
            repository being maintained specifically for ReactJava.

@history    Wed Oct 14, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void ensureReactJavaGWTDev()
   throws Exception
{
   ensureReactJavaGWTDev(null);
}
/*------------------------------------------------------------------------------

@name       ensureReactJavaGWTDev - ensure ReactJava GWT development jar
                                                                              */
                                                                             /**
            Ensure the target GWT SDK contains a ReactJava compatible
            gwt-dev.jar. If not, modifies the target gwt-dev.jar to be React
            Java compatible.

            This approach allows a GWT SDK to be downloaded without
            change from any source repository, rather than requiring a
            repository being maintained specifically for ReactJava.

@param      projectDir     any new project directory

@history    Wed Oct 14, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void ensureReactJavaGWTDev(
   File   projectDir)
   throws Exception
{
   System.out.println(new Date().toString());

                                       // save the base project directory     //
   String projectDirPathSave = IConfiguration.getProjectDirectoryPath();
   if (projectDir != null)
   {
      IConfiguration.setProjectDirectoryPath(projectDir.getAbsolutePath());
   }
   try
   {
      File projectIMLFile = IConfiguration.getProjectIMLFile(null);
      if (projectIMLFile == null)
      {
         throw new FileNotFoundException("Cannot find project IML file");
      }

      String content = getFileAsString(projectIMLFile);
      int    idxBeg  = content.indexOf("setting name=\"gwtSdkUrl\"");
      if (idxBeg < 0)
      {
         throw new IllegalStateException(
            "Cannot find 'gwtSdkUrl' entry in project IML file");
      }

      idxBeg = content.indexOf("value=\"", idxBeg) + "value=\"".length();

      String sdkURL = content.substring(idxBeg, content.indexOf('"', idxBeg));
      if (!sdkURL.startsWith("file://"))
      {
         throw new UnsupportedOperationException(
            "gwtSdkUrl must be a file url for now");
      }

      sdkURL = sdkURL.substring("file://".length());
      sdkURL = sdkURL.replace("$USER_HOME$", System.getProperty("user.home"));
      System.out.println("sdkURL=" + sdkURL);

      File gwtDevJarFile = new File(sdkURL, "gwt-dev.jar");
      if (!gwtDevJarFile.exists())
      {
         throw new FileNotFoundException(gwtDevJarFile.getAbsolutePath());
      }
                                       // check the current gwt-dev.jar to see//
                                       // if it supports the current release  //
      JarFile  gwtDevJar        = new JarFile(gwtDevJarFile);
      String   gwtDevJarVersion = null;
      JarEntry gwtVersionEntry  = gwtDevJar.getJarEntry(kGWT_EMBEDDED_FILENAME);
      if (gwtVersionEntry != null)
      {
         gwtDevJarVersion =
            new String(getInputStreamBytes(
               new BufferedInputStream(
                  gwtDevJar.getInputStream(gwtVersionEntry))),
               "UTF-8");
      }

      File rjJarFile =
         new File(IConfiguration.getWarLibraryDir(null), "reactjava.jar");

      if (!rjJarFile.exists())
      {
         throw new FileNotFoundException(rjJarFile.getAbsolutePath());
      }

      JarFile rjJar = new JarFile(rjJarFile);
      String  rjVersion =
         rjJar.getManifest()
           .getMainAttributes()
           .getValue("Specification-Version");

      if (rjVersion.equals(gwtDevJarVersion))
      {
         System.out.println("Existing gwt-dev.jar is ReactJava compatible.");
      }
      else
      {
         System.out.println("Updating gwt-dev.jar to be ReactJava compatible.");

                                       // update the gwt-dev.jar with the     //
                                       // necessary classes from reactjava.jar//                       //
         jarUpdate(
            gwtDevJarFile, rjJarFile, kGWT_DEV_JAR_ENHANCEMENTS, rjVersion);
      }
   }
   finally
   {
                                       // restore the base project directory  //
      IConfiguration.setProjectDirectoryPath(projectDirPathSave);
   }

   System.out.println("Done");
}
/*------------------------------------------------------------------------------

@name       exec - initialize node and npm if not already
                                                                              */
                                                                             /**
            Initialize node and npm if not already.

@return     completion code

@param      commands       commands
@param      workingDir     working directory

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

@return     completion code

@param      commands       commands
@param      workingDir     working directory
@param      container      container

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

@return     completion code

@param      commands       commands
@param      workingDir     working directory
@param      newPath        new path

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

@return     completion code

@param      args           args
@param      workingDir     working directory
@param      container      container
@param      newPath        new path

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

@param      src    src

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
      File rsrcKeyDir  = new File(projectDir, toOSPath("private/keys"));
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

@return     gwt library directory

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File generateGWTLibrary()
   throws Exception
{
                                       // find the core compiler output       //
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

@param      file     file

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

@param      file     file

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

@name       getFileType - get file type
                                                                              */
                                                                             /**
            Get file type.

@return     file type.

@param      file     file

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

@name       getGWTToInstall - get gwt version to install
                                                                              */
                                                                             /**
            Get gwt version to install.

@return     gwt version to install or null if latest is installed

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getGWTToInstall(
   String target)
   throws IOException
{
   System.out.println(
      "Checking whether "
    + (target == null ? "the latest version" : "version " + target)
    + " of gwt is installed...");

   if (target == null)
   {
      target = getGWTLatestVersion();
   }
   //if (!target.endsWith(".zip"))
   //{
   //   target += ".zip";
   //}

   String  installed  = getGWTInstalledVersion();
   boolean bInstalled =
      target != null && installed != null && target.startsWith(installed);

   String  msg =
      bInstalled
         ? "The GWT installation is up to date."
         : target == null
            ? "GWT is not installed."
            : "The GWT installation is out of date.";

   System.out.println(msg);

   return(bInstalled ? null : target);
}
/*------------------------------------------------------------------------------

@name       getGWTInstalledVersion - get version of gwt release if installed
                                                                              */
                                                                             /**
            Get version of gwt release if installed.

@return     version of gwt release if installed; otherwise, null.

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created
@history    Thu Oct 22, 2020 10:30:00 (Giavaneers - LBM) modified to not use
               version file.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getGWTInstalledVersion()
{
   System.out.println("Checking version of gwt if installed...");

   //String version = null;
   //File   release = getGWTInstalledVersionFile();
   //if (release.exists())
   //{
   //   try(FileInputStream in = new FileInputStream(release))
   //   {
   //                                    // check the version                   //
   //      version = new String(getInputStreamBytes(in)).trim();
   //   }
   //}

   File   home    = new File(System.getProperty("user.home"));
   File   gwt     = new File(home, toOSPath(".reactjava/gwt/" + kGWT));
   String version = gwt.exists() ? kGWT : null;

   return(version);
}
/*------------------------------------------------------------------------------

@name       getGWTInstalledVersionFile - get version file of gwt release
                                                                              */
                                                                             /**
            Get version file of gwt release.

@return     version file of gwt release

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File getGWTInstalledVersionFile()
{
   File home    = new File(System.getProperty("user.home"));
   File gwt     = new File(home, toOSPath(".reactjava/gwt/" + kGWT));
   File release = new File(gwt, kGWT_VERSION_FILENAME);

   return(release);
}
/*------------------------------------------------------------------------------

@name       getGWTLatestVersion - get latest version of gwt release
                                                                              */
                                                                             /**
            Get latest version of gwt release.

@return     latest version of gwt release

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created
@history    Thu Oct 22, 2020 10:30:00 (Giavaneers - LBM) modified to not use
               version file.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getGWTLatestVersion()
   throws IOException
{
   //String latestVersion =
   //   new String(
   //      getInputStreamBytes(
   //      new URL(kGWT_DOWNLOAD_URL_CONTAINER_URL)), "UTF-8");
   //
   //return(latestVersion);

   return(kGWT);
}
/*------------------------------------------------------------------------------

@name       getInputStreamBytes - get input stream bytes
                                                                              */
                                                                             /**
            Get input stream bytes.

@return     input stream bytes.

@param      url    url

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
            Get lastest jar and relaunch, unless -version flag is in args list
            in which case get specified version and relaunch.

@param      argsList    args

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void getLatestJarAndRelaunch(
   List<String> argsList)
   throws       Exception
{
   String version    = null;
   String versionGWT = null;
   for (int i = 0; i < argsList.size(); i++)
   {
      String arg = argsList.get(i).toLowerCase();
      if ("-version".equals(arg))
      {
         if (argsList.size() < i + 1)
         {
            throw new IllegalArgumentException("Need to specify version");
         }
         version = argsList.get(++i);
      }
      else if ("-versiongwt".equals(arg))
      {
         if (argsList.size() < i + 1)
         {
            throw new IllegalArgumentException("Need to specify gwt version");
         }
         versionGWT = argsList.get(++i);
      }
   }

   System.out.println(
      "Getting "
    + (version == null ? "latest reactjava.jar" : "reactjava." + version + ".jar")
    + " and relaunching...");

   File   tempDir = createLocalTempFile("latestJarContainer", true);
   File   newJar  = copyReactJavaJar(tempDir, version);
   File   wd      = new File(System.getProperty("user.dir"));
   String cmd     = "java -jar " + newJar.getAbsolutePath() + " -relaunched";

   for (int i = 0; i < argsList.size(); i++)
   {
      String arg = argsList.get(i);
      if ("-versiongwt".equals(arg.toLowerCase()))
      {
         i++;
         continue;
      }

      cmd += " " + arg;
   }
   int retVal = exec(new String[]{cmd}, wd);

   if (version != null)
   {
      int versionNum =
         Integer.parseInt(version.substring(version.lastIndexOf('.') + 1));

                                       // now that the specified jar has been //
                                       // used, post process for any ops that //
                                       // might not have been supported with  //
                                       // an early version                    //
      if (versionGWT != null && versionNum < 1907030000)
      {
                                       // specifying the gwt version was not  //
                                       // supported with the specified version//
                                       // of reactjava.jar                    //
         gwtInitialize(versionGWT);
      }
   }
}
/*------------------------------------------------------------------------------

@name       getLocalNodePath - update bash files to support node installation
                                                                              */
                                                                             /**
            Update bash files to support local node installation.

@return     local node path or null if not found

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

@name       googleAnalyticsInitialize - install google analytics module
                                                                              */
                                                                             /**
            Install google analytics module.

@return     new project folder

@param      projectDir    new project directory

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File googleAnalyticsInitialize(
   File     projectDir)
{
   String   sHome    = System.getProperty("user.home");
   File     home     = new File(sHome);
   File     wd       = kSRCCFG_NODE_MODULES_IN_PROJECT ? projectDir : home;
   String   nodePath = getLocalNodePath();
   String[] args;

   if (kSRCCFG_NODE_MODULES_IN_PROJECT || getNpmPackageOutOfDate("react-ga"))
   {
      System.out.println("Installing/updating react-ga package...");
      args = new String[]{"npm install react-ga --loglevel=error"};
      if (exec(args, wd, nodePath) != 0)
      {
         throw new IllegalStateException("react-ga was not installed");
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

            Could do this with

               "curl -o core-2-8.zip "https://storage.googleapis.com/"
               "core-releases/core-2.8.2.zip"

               but the download server seems very slow...

@param      versionRequested     gwt version or if null, latest

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created
@history    Thu Oct 22, 2020 10:30:00 (Giavaneers - LBM) modified to copy
               release from GWT repository.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void gwtInitialize(
   String versionRequested)
   throws Exception
{
                                       // version to install if not already   //
   String installVersion = getGWTToInstall(versionRequested);
   if (installVersion != null)
   {
      String sHome  = System.getProperty("user.home");
      File   home   = new File(sHome);
      File   gwtDir = new File(home, toOSPath(".reactjava/gwt"));

      if (gwtDir.exists())
      {
         deleteDirectory(gwtDir);
      }

      gwtDir.mkdirs();
                                       // download release from cloud storage //
      System.out.println(
         "\nDownloading gwt release (this may take a few minutes)...");
      //File src = copyGWTReleaseViaGCS(gwtDir, installVersion);
      File src = copyGWTReleaseViaGWTRepository(gwtDir, installVersion);

      System.out.println("\nInstalling gwt...");
      zipExtractFiles(src, gwtDir);
                                       // delete the release zip file         //
      src.delete();
                                       // delete any __MACOSX dir             //
      deleteDirectory(new File(gwtDir, "__MACOSX"));

      System.out.println("Done");
   }
}
/*------------------------------------------------------------------------------

@name       jarFiles - create jar of target files to specified output jar
                                                                              */
                                                                             /**
            Create jar of target files to specified output jar.

@param      jarFile     output jar
@param      targets     array of target files
@param      entries     array of entries
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
            Create jar of target jar entries to specified output jar.

@param      jarFile           output jar
@param      targetsJar        src jar
@param      targets           array of target entries
@param      defaultValue      value to be used if not supplied

@history    Fri Nov 6, 2003 04:35:52 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void jarUpdate(
   File         jarFile,
   File         targetsJar,
   List<String> targets,
   String       defaultValue)
   throws       IOException
{
   System.out.println(
      "JavascriptBundler.jarUpdate(): updating " + jarFile.getAbsolutePath());

   File            tmpJar = File.createTempFile("tempJar", ".tmp");
   JarOutputStream jarOut =
      new JarOutputStream(
         new BufferedOutputStream(new FileOutputStream(tmpJar)));

   JarFile targetsJarFile = new JarFile(targetsJar);

                                       // write the new entries               //
   for (String target : targets)
   {
      JarEntry dstEntry = new JarEntry(target);
      dstEntry.setMethod(ZipEntry.DEFLATED);
      jarOut.putNextEntry(dstEntry);

      JarEntry srcEntry = targetsJarFile.getJarEntry(target);

                                       // new entry is the latest version     //
                                       // if src is null                      //
      InputStream srcStream =
         srcEntry != null
            ? targetsJarFile.getInputStream(srcEntry)
            : new ByteArrayInputStream(defaultValue.getBytes());

      IConfiguration.fastChannelCopy(
         new BufferedInputStream(srcStream), jarOut);
   }
                                       // copy original entries               //
   jarUpdateAddOriginalEntries(jarFile, targets, jarOut, tmpJar);

   System.out.println("JavascriptBundler.jarUpdate(): exiting");
}
/*------------------------------------------------------------------------------

@name       jarUpdate - create jar of target files to specified output jar
                                                                              */
                                                                             /**
            Create jar of target files to specified output jar.

@param      jarFile     output jar
@param      targets     array of target files
@param      entries     jar file entries

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

   File            tmpJar = File.createTempFile("tempJar", ".tmp");
   JarOutputStream jarOut =
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
   jarUpdateAddOriginalEntries(jarFile, entries, jarOut, tmpJar);

   System.out.println("JavascriptBundler.jarUpdate(): exiting");
}
/*------------------------------------------------------------------------------

@name       jarUpdate - create jar of target files to specified output jar
                                                                              */
                                                                             /**
            Create jar of target files to specified output jar.

@param      jarFile     output jar
@param      entries     jar file entries
@param      jarOut      updated jar output stream
@param      tmpJar      temporary jar

@history    Fri Nov 6, 2003 04:35:52 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void jarUpdateAddOriginalEntries(
   File            jarFile,
   List<String>    entries,
   JarOutputStream jarOut,
   File            tmpJar)
   throws          IOException
{
   boolean     bUpdated   = false;
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
      System.out.println(
         "JavascriptBundler.jarUpdateAddOriginalEntries(): updated");
   }
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

@param      projectDir    new project directory

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

@param      sBashProfile   bash profile
@param      entry          entry

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

            2. Create new project with the latest version
               and include React Native support

               create-reactjava-app -reactnative MyNewProject

            3. Create new project with version 0.1.1810191229

               create-reactjava-app -version 0.1.1810191229 MyNewProject

            4. Update project with the latest version

               create-reactjava-app update MyNewProject

            5. Update project with version 0.1.1810191229

               create-reactjava-app -version 0.1.1810191229 update MyNewProject


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

@name       materialUIInitialize - install material-ui modules
                                                                              */
                                                                             /**
            Install material-ui modules.

@return     new project folder

@param      projectDir    new project directory

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File materialUIInitialize(
   File     projectDir)
{
   String   sHome    = System.getProperty("user.home");
   File     home     = new File(sHome);
   File     wd       = kSRCCFG_NODE_MODULES_IN_PROJECT ? projectDir : home;
   String   nodePath = getLocalNodePath();
   String[] args;

   if (kSRCCFG_NODE_MODULES_IN_PROJECT || getNpmPackageOutOfDate("@material-ui/core"))
   {
      System.out.println("Installing/updating @material-ui/core package...");
      args = new String[]{"npm install @material-ui/core --loglevel=error"};
      if (exec(args, wd, nodePath) != 0)
      {
         throw new IllegalStateException("@material-ui/core was not installed");
      }
      System.out.println("Done");
   }
   if (kSRCCFG_NODE_MODULES_IN_PROJECT || getNpmPackageOutOfDate("@material-ui/icons"))
   {
      System.out.println("Installing/updating @material-ui/icons package...");
      args = new String[]{"npm install @material-ui/icons --loglevel=error"};
      if (exec(args, wd, nodePath) != 0)
      {
         throw new IllegalStateException("@material-ui/icons was not installed");
      }
      System.out.println("Done");
   }

   return(projectDir);
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

@param      projectDir    new project directory

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

@name       publishBytesToCloud - publish specified file to cloud
                                                                              */
                                                                             /**
            Publish specified file to cloud.

@param      bytes             source bytes
@param      gcsBucketName     gcs bucket name
@param      gcsObjectPath     gcs object path

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void publishBytesToCloud(
   byte[] bytes,
   String gcsBucketName,
   String gcsObjectPath)
   throws Exception
{
   String uploadURL = "gs://" + gcsBucketName + "/" + gcsObjectPath;

   gcsManager().writeFile(bytes, uploadURL);
}
/*------------------------------------------------------------------------------

@name       publishFileToCloud - publish specified file to cloud
                                                                              */
                                                                             /**
            Publish specified file to cloud.

@param      srcPath           source file path
@param      gcsBucketName     gcs bucket name
@param      gcsObjectPath     gcs object path

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void publishFileToCloud(
   String srcPath,
   String gcsBucketName,
   String gcsObjectPath)
   throws Exception
{
   if (!new File(srcPath).exists())
   {
      throw new IllegalStateException("Cannot find source file");
   }

   String uploadURL = "gs://" + gcsBucketName + "/" + gcsObjectPath;

   gcsManager().writeFile(srcPath, uploadURL, null,
      (Object response, Object requestToken) ->
      {
         System.out.println(
            ((Map<String,Object>)response).get("status").toString());
      });
}
/*------------------------------------------------------------------------------

@name       publishGWTSDK - publish core sdk
                                                                              */
                                                                             /**
            Publish core sdk with enhanced core-dev.jar.

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void publishGWTSDK()
   throws Exception
{
   String path   = "/Users/brianm/.reactjava/gwt/" + kGWT;
   File   sdkDir = new File(path);
   if (!sdkDir.exists())
   {
      throw new IllegalStateException("Cannot find source core sdk");
   }
                                       // zip the sdk                         //
   File tmpZip = File.createTempFile("tmpZip", ".tmp");
   zipDirectory(sdkDir, tmpZip);
                                       // publish to the 'releases' directory //
   String version    = getGWTInstalledVersion();
   String filename   = version + ".zip";
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
                                       // publish the new version file        //
   objectPath = "releases/gwt/" + kGWT_LATEST_RELEASE_FILENAME;
   publishBytesToCloud(filename.getBytes("UTF-8"), bucketName, objectPath);
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

   publishFileToCloud(path, bucketName, objectPath);

                                       // write the download url              //
   byte[] content =
      (kREACT_JAVA_RELEASES_DOWNLOAD_URL + filename).getBytes("UTF-8");

   objectPath = "releases/latest/latestReactJavaRelease.txt";

   publishBytesToCloud(content, bucketName, objectPath);

                                       // publish to 'releases/latest' dir    //
   filename   = "reactjava.jar";
   objectPath = "releases/latest/" + filename;

   publishFileToCloud(path, bucketName, objectPath);
}
/*------------------------------------------------------------------------------

@name       reactAndReactDomInitialize - install react dom module
                                                                              */
                                                                             /**
            Install react dom module.

@return     new project folder

@param      projectDir    new project directory

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static File reactAndReactDomInitialize(
   File     projectDir)
   throws   Exception
{
   String   sHome    = System.getProperty("user.home");
   File     home     = new File(sHome);
   File     wd       = kSRCCFG_NODE_MODULES_IN_PROJECT ? projectDir : home;
   String   nodePath = getLocalNodePath();
   String   projName = projectDir.getName();
   String[] args;

   if (kSRCCFG_NODE_MODULES_IN_PROJECT && kSRCCFG_REACT_NATIVE_ALWAYS)
   {
      System.out.println("Installing/updating and init react-native package...");
      args = new String[]{"yes | npx react-native init " + projName};

      if (exec(args, wd.getParentFile()) != 0)
      {
         throw new IllegalStateException("react-native was not installed");
      }
      System.out.println("Done");
                                       // update react native launch page     //
      reactNativeUpdateLaunchPage(projectDir);

                                       // trim default react native support   //
      //reactNativeTrim(projectDir);
   }
   else if (kSRCCFG_NODE_MODULES_IN_PROJECT || getNpmPackageOutOfDate("react"))
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
                                       //    -> namespace = "ReactJava"    //

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

@param      projectDir    new project directory

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

@param      projectDir    new project directory

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

@param      projectDir    new project directory

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
      System.out.println(
         "Existing React Native support not found for "
            + projectDir.getAbsolutePath());

                                       // react-native init needs an empty    //
                                       // folder to work correctly, so use a  //
                                       // new temporary directory             //

      File tempProjectDir = createLocalTempFile("reactJavaNativeTempDir", true);

                                       // now initialize react native support //

      System.out.println(
         "Initializing react native project:react-native init " + projectName
       + " with workingDir=" + tempProjectDir.getAbsolutePath()
       + " and nodePath=" + nodePath);

      args =
         new String[]{"react-native init " + projectName};

      System.out.println("react-native init " + projectName);

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

@param      projectDir    new project directory

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
          //  "node_modules/react-native/local-cli/server/"
          //+ "util/debugger-ui/index.html"));

            "node_modules/react-native/node_modules/@react-native-community/"
          + "cli/build/commands/server/debugger-ui/index.html"));

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

@name       updateGWTInstalledVersion - update version of gwt release
                                                                              */
                                                                             /**
            Update version of gwt release.

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void updateGWTInstalledVersion()
   throws IOException
{
   String datestamp =
      new SimpleDateFormat(kDATE_FORMAT_PATTERN_VERSION).format(new Date());

   String version = kVERSION_GWT.replace("%version%", datestamp);
   System.out.println("Updating version of gwt release to " + version);

   File release = getGWTInstalledVersionFile();
   if (release.exists())
   {
      release.delete();
   }

   try (FileOutputStream out = new FileOutputStream(release))
   {
      out.write(version.getBytes("UTF-8"));
   }
}
/*------------------------------------------------------------------------------

@name       updateJarManifest - update the jar manifest
                                                                              */
                                                                             /**
            Update the jar manifest.

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

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void updateProjectTemplateResource()
{
   File   projectDir  = new File(System.getProperty("user.dir"));
   File   outDir      = new File(projectDir, toOSPath("out/production/ReactJava"));
   String path        = toOSPath("io/reactjava/compiler/codegenerator" +
    "/ProjectTemplate.zip");
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

@param      projectDir     project directory
@param      version        version
@param      bUpdate        iff true, update

@history    Sun Aug 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void updateReactJavaCore(
   File    projectDir,
   String  version,
   boolean bReactNative,
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
                                       // create a new one                    //
      projectDir.mkdirs();
   }
                                       // initialize local .reactJava dir     //
   reactJavaDirInitialize(projectDir);
                                       // initialize/update node and npm      //
   nodeInitialize(projectDir);
                                       // initialize/update react, react-dom  //
   reactAndReactDomInitialize(projectDir);

                                       // initialize/update browserify        //
   browserifyInitialize(projectDir);
                                       // initialize/update reactiveX         //
   reactiveXInitialize(projectDir);
                                       // initialize/update material-ui/core  //
   materialUIInitialize(projectDir);
                                       // initialize/update google analytics  //
   googleAnalyticsInitialize(projectDir);
                                       // copy project template               //
   copyProjectTemplate(projectDir, bUpdate);

                                       // initialize/update core support      //
   gwtInitialize(null);
                                       // initialize/update reactjava.jar     //
   copyReactJavaJarToProject(projectDir, version);

                                       // modify the target gwt-dev.jar to be //
                                       // ReactJava compatible                //
   System.out.println("Ensuring gwt-dev.jar is ReactJava compatible.");
   ensureReactJavaGWTDev(projectDir);
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

@param     dst         destination file
@param     content     content

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

@param      inZip             zip input stream
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

@param      srcDir      source directory
@param      dst         dst directory

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
//                                       // generate core libarary and index.js //
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
///*------------------------------------------------------------------------------
//
//@name       generateIndexJs - generate index js file
//                                                                              */
//                                                                             /**
//            Generate index js file to the "out/build/src" directory
//
//@return     void
//
//@param     reactNativeDir    react native build directory
//
//@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created
//
//@notes
//                                                                              */
////------------------------------------------------------------------------------
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
//@param     reactNativeDir    react native build directory
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
//@param     artifactDir    artifact directory
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
