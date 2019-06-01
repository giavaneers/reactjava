/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * The enhancement to this program was created by Giavaneers
 * and is the proprietary product of Giavaneers Inc.
 *
 * COPYRIGHT (c) BY GIAVANEERS, INC.
 * This source code enhancement is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.google.gwt.dev;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.ArtifactSet;
import com.google.gwt.dev.CompileTaskRunner.CompileTask;
import com.google.gwt.dev.cfg.ModuleDef;
import com.google.gwt.dev.cfg.ModuleDefLoader;
import com.google.gwt.dev.javac.UnitCache;
import com.google.gwt.dev.javac.UnitCacheSingleton;
import com.google.gwt.dev.jjs.PermutationResult;
import com.google.gwt.dev.js.JsNamespaceOption;
import com.google.gwt.dev.shell.CheckForUpdates;
import com.google.gwt.dev.shell.CheckForUpdates.UpdateResult;
import com.google.gwt.dev.util.Memory;
import com.google.gwt.dev.util.PersistenceBackedObject;
import com.google.gwt.dev.util.Util;
import com.google.gwt.dev.util.arg.ArgHandlerDeployDir;
import com.google.gwt.dev.util.arg.ArgHandlerExtraDir;
import com.google.gwt.dev.util.arg.ArgHandlerIncrementalCompile;
import com.google.gwt.dev.util.arg.ArgHandlerLocalWorkers;
import com.google.gwt.dev.util.arg.ArgHandlerSaveSourceOutput;
import com.google.gwt.dev.util.arg.ArgHandlerWarDir;
import com.google.gwt.dev.util.arg.ArgHandlerWorkDirOptional;
import com.google.gwt.dev.util.arg.OptionOptimize;
import com.google.gwt.dev.util.log.speedtracer.CompilerEventType;
import com.google.gwt.dev.util.log.speedtracer.SpeedTracerLogger;
import com.google.gwt.dev.util.log.speedtracer.SpeedTracerLogger.Event;
import com.google.gwt.thirdparty.guava.common.collect.Sets;
import com.google.gwt.util.tools.Utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * The main executable entry point for the GWT Java to JavaScript compiler.
 */
public class Compiler {

  static class ArgProcessor extends PrecompileTaskArgProcessor {
    public ArgProcessor(CompilerOptions options) {
      super(options);

      registerHandler(new ArgHandlerLocalWorkers(options));

      // Override the ArgHandlerWorkDirRequired in the super class.
      registerHandler(new ArgHandlerWorkDirOptional(options));
      registerHandler(new ArgHandlerIncrementalCompile(options));

      registerHandler(new ArgHandlerWarDir(options));
      registerHandler(new ArgHandlerDeployDir(options));
      registerHandler(new ArgHandlerExtraDir(options));
      registerHandler(new ArgHandlerSaveSourceOutput(options));
    }

    @Override
    protected String getName() {
      return Compiler.class.getName();
    }
  }

  /**
   * Locates the unit cache dir relative to the war dir and returns a UnitCache instance.
   */
  public static UnitCache getOrCreateUnitCache(TreeLogger logger, CompilerOptions options) {
    File persistentUnitCacheDir = null;
    if (options.getWarDir() != null && options.getWarDir().isDirectory()) {
      persistentUnitCacheDir = new File(options.getWarDir(), "../");
    }
    // TODO: returns the same UnitCache even if the passed directory changes. Make this less
    // surprising.
    return UnitCacheSingleton.get(logger, null, persistentUnitCacheDir, options);
  }

/*LBM-START*/
/*------------------------------------------------------------------------------

@name       loadReactJavaOverrides - modify classpath and relaunch
                                                                              */
                                                                             /**
            If reactjava.jar is on the classpath, and it is not in first
            position, relaunch with the same classpath modified to reorder
            reactjava.jar to the first position.

@history    Sun Jan 06, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void loadReactJavaOverrides()
{
   try
   {
      String pathSeparator = System.getProperty("path.separator");
      String cp            = System.getProperty("java.class.path");

      java.util.List<String> cpList =
         new java.util.ArrayList<String>(
            java.util.Arrays.asList(cp.split(pathSeparator)));

      String pathReactJava = null;
      for (String path : cpList)
      {
         if (path.contains("reactjava.jar"))
         {
            pathReactJava = path;
            break;
         }
      }
      if (pathReactJava == null)
      {
         throw new IllegalStateException(
            "Cannot find reactjava.jar in the classpath");
      }

      File reactJavaJarFile = new File(pathReactJava);

      io.reactjava.codegenerator.ReactJavaClassLoader loader =
         new io.reactjava.codegenerator.ReactJavaClassLoader(
            new java.net.URL[] {reactJavaJarFile.toURI().toURL()},
            Compiler.class.getClassLoader());

      JarFile reactJavaJar = new JarFile(reactJavaJarFile);
      Enumeration jarEntries = reactJavaJar.entries();
      while (jarEntries.hasMoreElements())
      {
         JarEntry entry    = (JarEntry)jarEntries.nextElement();
         String   override = entry.getName().replace("/",".");
         if (override.startsWith("com.google.gwt.dev.")
               && override.endsWith(".class"))
         {
            override = override.replace(".class", "");

                                       // only loads the overrides in         //
                                       // reactjava.jar                       //
            loader.loadClass(override);
         }
      }
   }
   catch(Exception e)
   {
      e.printStackTrace();
      // Exit w/ non-success code.
      System.exit(1);
   }
}
/*------------------------------------------------------------------------------

@name       modifyClasspathAndAndRelaunch - modify classpath and relaunch
                                                                              */
                                                                             /**
            If reactjava.jar is on the classpath, and it is not in first
            position, relaunch with the same classpath modified to reorder
            reactjava.jar to the first position.

@return     true iff relaunched

@param      args     launch arguments

@history    Sun Jan 06, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean modifyClasspathAndAndRelaunch(
   String[] args,
   boolean  bAllowRelaunch)
{
   boolean bRelaunched = false;
   try
   {
      String sArgs = "";
      for (String arg : args)
      {
         sArgs += arg + " ";
      }
      String pathSeparator = System.getProperty("path.separator");
      String cp            = System.getProperty("java.class.path");

      System.out.println("Launched with args      = " + sArgs);
      System.out.println("Launched with classpath = " + cp);

      java.util.List<String> cpList =
         new java.util.ArrayList<String>(
            java.util.Arrays.asList(cp.split(pathSeparator)));

      String pathReactJava = null;
      for (String path : cpList)
      {
         if (path.contains("reactjava.jar"))
         {
            pathReactJava = path;
            break;
         }
      }

      if (pathReactJava != null && !pathReactJava.equals(cpList.get(0)))
      {
         if (!bAllowRelaunch)
         {
            System.out.println(
               "Prevented from modifying classpath and relaunching...");
         }
         else
         {
            bRelaunched = true;
            System.out.println("Modifying classpath and relaunching...");

                                       // reactjava.jar goes to the front     //
            cp = pathReactJava + pathSeparator + cp;
            String classname = Compiler.class.getName();

            String[] sansArgs = {"java","-cp", cp, classname};
            String[] commands = new String[sansArgs.length + args.length];
            System.arraycopy(sansArgs, 0, commands, 0, sansArgs.length);
            System.arraycopy(args, 0, commands, sansArgs.length, args.length);

            ProcessBuilder pb =
               new ProcessBuilder(commands).redirectOutput(
                  ProcessBuilder.Redirect.INHERIT);

            Process process = pb.start();

            if (process.waitFor() != 0)
            {
               throw new IllegalStateException("Compiler returned error=");
            }
         }
      }
   }
   catch(Exception e)
   {
      e.printStackTrace();
      // Exit w/ non-success code.
      System.exit(1);
   }
   return(bRelaunched);
}

  public static void main(String[] args) {

    if (com.google.gwt.dev.Compiler.modifyClasspathAndAndRelaunch(args, true))
    {
       System.exit(0);
    }
    //com.google.gwt.dev.Compiler.loadReactJavaOverrides();
/*LBM-END*/
    Memory.initialize();
    if (System.getProperty("core.jjs.dumpAst") != null) {
      System.out.println("Will dump AST to: "
          + System.getProperty("core.jjs.dumpAst"));
    }

    SpeedTracerLogger.init();

    /*
     * NOTE: main always exits with a call to System.exit to terminate any
     * non-daemon threads that were started in Generators. Typically, this is to
     * shutdown AWT related threads, since the contract for their termination is
     * still implementation-dependent.
     */
    final CompilerOptions options = new CompilerOptionsImpl();
    if (new ArgProcessor(options).processArgs(args)) {
      CompileTask task = new CompileTask() {
        @Override
        public boolean run(TreeLogger logger) throws UnableToCompleteException {
          FutureTask<UpdateResult> updater = null;
          if (!options.isUpdateCheckDisabled()) {
            updater = CheckForUpdates.checkForUpdatesInBackgroundThread(logger,
                CheckForUpdates.ONE_DAY);
          }
          boolean success = Compiler.compile(logger, options);
          if (success) {
            CheckForUpdates.logUpdateAvailable(logger, updater);
          }
          return success;
        }
      };
      if (CompileTaskRunner.runWithAppropriateLogger(options, task)) {
        // Exit w/ success code.
        System.exit(0);
      }
    }
    // Exit w/ non-success code.
    System.exit(1);
  }

  public static boolean compile(
      TreeLogger logger, CompilerOptions compilerOptions)
      throws UnableToCompleteException {
    List<ModuleDef> moduleDefs = new ArrayList<>();
    for (String moduleName : compilerOptions.getModuleNames()) {
      moduleDefs.add(ModuleDefLoader.loadFromClassPath(logger, moduleName, true));
    }

    boolean result = true;
    for (ModuleDef moduleDef : moduleDefs) {
      result &= compile(logger, compilerOptions, moduleDef);
    }
    return result;
  }

  public static boolean compile(
      TreeLogger logger, CompilerOptions compilerOptions, ModuleDef moduleDef)
      throws UnableToCompleteException {
    MinimalRebuildCache minimalRebuildCache = compilerOptions.isIncrementalCompileEnabled()
        ? new MinimalRebuildCache()
        : new NullRebuildCache();
    return compile(logger, compilerOptions, minimalRebuildCache, moduleDef);
  }

  public static boolean compile(
      TreeLogger logger,
      CompilerOptions compilerOptions,
      MinimalRebuildCache minimalRebuildCache,
      ModuleDef moduleDef)
      throws UnableToCompleteException {

    CompilerOptionsImpl  options = new CompilerOptionsImpl(compilerOptions);
    boolean tempWorkDir = false;
    try {
      if (options.getWorkDir() == null) {
        options.setWorkDir(Utility.makeTemporaryDirectory(null, "gwtc"));
        tempWorkDir = true;
      }
      if ((options.isSoycEnabled() || options.isJsonSoycEnabled())
          && options.getExtraDir() == null) {
        options.setExtraDir(new File("extras"));
      }
      if (options.isIncrementalCompileEnabled()) {
        // Disable options that disrupt contiguous output JS source per class.
        options.setClusterSimilarFunctions(false);
        options.setOptimizationLevel(OptionOptimize.OPTIMIZE_LEVEL_DRAFT);
        options.setRunAsyncEnabled(false);

        // Disable options that disrupt reference consistency across multiple compiles.
        // TODO(stalcup): preserve Namespace state in MinimalRebuildCache across compiles.
        options.setNamespace(JsNamespaceOption.NONE);
      }

      CompilerContext compilerContext =
          new CompilerContext.Builder()
              .options(options)
              .minimalRebuildCache(minimalRebuildCache)
              .unitCache(getOrCreateUnitCache(logger, options))
              .module(moduleDef)
              .build();
      String moduleName = moduleDef.getCanonicalName();
      if (options.isValidateOnly()) {
        if (!Precompile.validate(logger, compilerContext)) {
          return false;
        }
      } else {
        long beforeCompileMs = System.currentTimeMillis();
        TreeLogger branch = logger.branch(TreeLogger.INFO,
            "Compiling module " + moduleName);

        Precompilation precompilation = Precompile.precompile(branch, compilerContext);
        if (precompilation == null) {
          return false;
        }
        // TODO: move to precompile() after params are refactored
        if (!options.shouldSaveSource()) {
          precompilation.removeSourceArtifacts(branch);
        }

        Event compilePermutationsEvent =
            SpeedTracerLogger.start(CompilerEventType.COMPILE_PERMUTATIONS);
        Permutation[] allPerms = precompilation.getPermutations();
        List<PersistenceBackedObject<PermutationResult>> resultFiles =
            CompilePerms.makeResultFiles(
                options.getCompilerWorkDir(moduleName), allPerms, options);
        CompilePerms.compile(branch, compilerContext, precompilation, allPerms,
            options.getLocalWorkers(), resultFiles);
        compilePermutationsEvent.end();

        ArtifactSet generatedArtifacts = precompilation.getGeneratedArtifacts();
        PrecompileTaskOptions precompileOptions = precompilation.getUnifiedAst().getOptions();

        precompilation = null; // No longer needed, so save the memory
        long afterCompileMs = System.currentTimeMillis();
        double compileSeconds = (afterCompileMs - beforeCompileMs) / 1000d;
        branch.log(TreeLogger.INFO,
            String.format("Compilation succeeded -- %.3fs", compileSeconds));

        long beforeLinkMs = System.currentTimeMillis();
        Event linkEvent = SpeedTracerLogger.start(CompilerEventType.LINK);
        File absPath = new File(options.getWarDir(), moduleDef.getName());
        absPath = absPath.getAbsoluteFile();

        String logMessage = "Linking into " + absPath;
        if (options.getExtraDir() != null) {
          File absExtrasPath = new File(options.getExtraDir(),
              moduleDef.getName());
          absExtrasPath = absExtrasPath.getAbsoluteFile();
          logMessage += "; Writing extras to " + absExtrasPath;
        }
        Link.link(logger.branch(TreeLogger.TRACE, logMessage), moduleDef,
            moduleDef.getPublicResourceOracle(), generatedArtifacts, allPerms, resultFiles,
            Sets.<PermutationResult>newHashSet(), precompileOptions, options);
        linkEvent.end();
        long afterLinkMs = System.currentTimeMillis();
        double linkSeconds = (afterLinkMs - beforeLinkMs) / 1000d;
        branch.log(TreeLogger.INFO, String.format("Linking succeeded -- %.3fs", linkSeconds));
      }

    } catch (IOException e) {
      logger.log(TreeLogger.ERROR, "Unable to create compiler work directory",
          e);
      return false;
    } finally {
      if (tempWorkDir) {
        Util.recursiveDelete(options.getWorkDir(), false);
      }
    }
    return true;
  }
}
