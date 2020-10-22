/*==============================================================================

name:       IPreprocessor.java

purpose:    ReactJava Code Generation Preprocessor Interface.

            Since a GWT Oracle is not available yet, uses JavaParser to parse
            the various provider and component classes to find all providers
            and components of top and inner classes of all associated source
            files.

history:    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created
            Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) added JavaParser

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.compiler.codegenerator;
                                       // imports --------------------------- //
import com.google.gwt.core.ext.TreeLogger;
import io.reactjava.client.core.react.ReactGeneratedCode;
import io.reactjava.compiler.jsx.JSXParser;
import io.reactjava.compiler.jsx.JSXTransform;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
                                       // IPreprocessor ======================//
public interface IPreprocessor
{
                                       // class constants --------------------//
                                       // packages excluded from parsing      //
String[] kPACKAGES_EXCLUDE =
{
   "com.giavaneers.net",
   "com.giavaneers.util",
   "com.google",
   "io.reactjava.client.core",
   "io.reactjava.compiler",
   "elemental2",
   "core",
   "java",
   "javax",
   "jsinterop",
   "org.jsoup"
};
                                       // classes otherwise excluded that     //
                                       // should be included in parsing       //
Set<String> kCLASSNAMES_INCLUDE =
   new HashSet<String>()
{{
   add(ReactGeneratedCode.class.getName());
}};
                                       // all preprocessors on the classpath  //
PreprocessorDsc[] preprocessors =
{
   new PreprocessorDsc(JSXParser.class),
   new PreprocessorDsc(ReactCodeGeneratorPreprocessor.class),
   new PreprocessorDsc(JSXTransform.class)
};
                                       // preprocessors tree logger           //
TreeLogger[]      logger = new TreeLogger[1];

                                       // classnames seen on initial build    //
Set<String>       seenOnInitialBuild = new HashSet<>();

                                       // invocation stack trace captured     //
boolean[]         bInvocationStackTraceCaptured  = new boolean[1];

/*------------------------------------------------------------------------------

@name       allPreprocessors - process specified source
                                                                              */
                                                                             /**
            Process specified source. Invoked for each resource before it is
            precompiled and after all provider and component candidates have
            been gathered.

            The invocation stack trace is:

            at io.reactjava.compiler.codegenerator.IPreprocessor
               .allPreprocessors(IPreprocessor.java:123)
            at com.google.gwt.dev.javac
               .CompilationUnitBuilder$ResourceCompilationUnitBuilder
               .doGetSource(CompilationUnitBuilder.java:178)
            at com.google.gwt.dev.javac.CompilationUnitBuilder
               .getSource(CompilationUnitBuilder.java:334)
            at com.google.gwt.dev.javac.JdtCompiler$Adapter
               .getContents(JdtCompiler.java:173)
            at org.eclipse.jdt.internal.compiler.parser.Parser
               .parse(Parser.java:11345)
            at org.eclipse.jdt.internal.compiler.parser.Parser
               .parse(Parser.java:11317)
            at com.google.gwt.dev.javac.JdtCompiler$ParserImpl
               .parse(JdtCompiler.java:229)
            at org.eclipse.jdt.internal.compiler.parser.Parser
               .dietParse(Parser.java:9732)
            at org.eclipse.jdt.internal.compiler.Compiler
               .internalBeginToCompile(Compiler.java:809)
            at org.eclipse.jdt.internal.compiler.Compiler
               .beginToCompile(Compiler.java:385)
            at org.eclipse.jdt.internal.compiler.Compiler
               .compile(Compiler.java:428)
            at com.google.gwt.dev.javac.JdtCompiler
               .doCompile(JdtCompiler.java:1040)
            at com.google.gwt.dev.javac
               .CompilationStateBuilder$CompileMoreLater
                  .compile(CompilationStateBuilder.java:334)
            at com.google.gwt.dev.javac.CompilationStateBuilder
               .doBuildFrom(CompilationStateBuilder.java:670)
            at com.google.gwt.dev.javac.CompilationStateBuilder
               .buildFrom(CompilationStateBuilder.java:488)
            at com.google.gwt.dev.javac.CompilationStateBuilder
               .buildFrom(CompilationStateBuilder.java:474)
            at com.google.gwt.dev.cfg.ModuleDef
               .getCompilationState(ModuleDef.java:423)
            at com.google.gwt.dev.Precompile.precompile(Precompile.java:230)
            at com.google.gwt.dev.Precompile.precompile(Precompile.java:210)
            at com.google.gwt.dev.Precompile.precompile(Precompile.java:151)
            at com.google.gwt.dev.Compiler.compile(Compiler.java:204)
            at com.google.gwt.dev.Compiler.compile(Compiler.java:155)
            at com.google.gwt.dev.Compiler.compile(Compiler.java:144)
            at com.google.gwt.dev.Compiler$1.run(Compiler.java:118)
            at com.google.gwt.dev.CompileTaskRunner
               .doRun(CompileTaskRunner.java:55)
            at com.google.gwt.dev.CompileTaskRunner
               .runWithAppropriateLogger(CompileTaskRunner.java:50)
            at com.google.gwt.dev.Compiler.main(Compiler.java:125)
            at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
            at sun.reflect.NativeMethodAccessorImpl
               .invoke(NativeMethodAccessorImpl.java:62)
            at sun.reflect.DelegatingMethodAccessorImpl
               .invoke(DelegatingMethodAccessorImpl.java:43)
            at java.lang.reflect.Method.invoke(Method.java:498)
            at com.intellij.rt.execution.CommandLineWrapper
               .main(CommandLineWrapper.java:64)


@return     processed source as modified from original by the preprocessors

@param      classname      classname to be processed
@param      contentBytes   original source to be processed
@param      encoding       cantent encoding

@param      providerAndComponentCandidates
                           map of source by classname for all provider and
                           component candidates, created by compiler
                           (com.google.gwt.dev.javac.CompilationStateBuilder
                            .doBuildFrom())

@param      logger         compiler logger

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static byte[] allPreprocessors(
   String             classname,
   byte[]             contentBytes,
   String             encoding,
   Map<String,String> providerAndComponentCandidates,
   TreeLogger         logger)
   throws             Exception
{
   byte[] processed = contentBytes;
   if (isCandidate(classname))
   {
      logger = getFileLogger(logger);
      logger.log(
         logger.INFO,
         "IPreprocessor.allPreprocessors(): entered for classname=" + classname);

      //captureInvocationStackTrace(logger);

                                       // test if this is for an updated      //
                                       // source of an incremental build      //
      boolean bUpdate = seenOnInitialBuild.contains(classname);
      if (!bUpdate)
      {
         seenOnInitialBuild.add(classname);
      }
                                       // allow each preprocessor to make any //
                                       // changes                             //

      for (PreprocessorDsc preprocessorDsc : preprocessors)
      {
         long start = System.nanoTime();
         processed =
            ((IPreprocessor)preprocessorDsc.clas.newInstance()).process(
               classname, processed, encoding, providerAndComponentCandidates,
               bUpdate, logger);

         preprocessorDsc.targetsProcessed++;
         preprocessorDsc.excecutionNsec += (System.nanoTime() - start);
      }

      logger.log(logger.INFO, "IPreprocessor.allPreprocessors(): exiting");
   }

   return(processed);
}
/*------------------------------------------------------------------------------

@name       captureInvocationStackTrace - capture invocation stack trace
                                                                              */
                                                                             /**
            Capture invocation stack trace.

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void captureInvocationStackTrace(
   TreeLogger logger)
{
   try
   {
      if (!bInvocationStackTraceCaptured[0])
      {
         throw new Exception("Not an error; capturing stack trace");
      }
   }
   catch(Exception e)
   {
      bInvocationStackTraceCaptured[0] = true;
      logger.log(logger.INFO, "Invocation stack trace:", e);
   }
}
/*------------------------------------------------------------------------------

@name       getFileLogger - get file logger
                                                                              */
                                                                             /**
            Get file logger.

@return     file logger

@param      invocationLogger     logger passed with invocation

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static TreeLogger getFileLogger(
   TreeLogger invocationLogger)
{
   if (logger[0] == null)
   {
      logger[0] =
         FileLogger.newInstance(
            invocationLogger, "antlog.codegenerator.preprocessor.txt");

      logger[0].log(TreeLogger.INFO, new Date().toString());
   }
   return(logger[0]);
}
/*------------------------------------------------------------------------------

@name       getSimpleClassname - get simple classname for classname
                                                                              */
                                                                             /**
            Get simple classname for specified classname.

@return     simple classname for specified classname

@param      classname      classname

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static String getSimpleClassname(
   String classname)
{
   String simple = classname;

   int idx = classname.lastIndexOf('.');
   if (idx > 0)
   {
      simple = classname.substring(idx + 1);
   }
   return(simple);
}
/*------------------------------------------------------------------------------

@name       initialize - initialize
                                                                              */
                                                                             /**
            Initialize.

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void initialize();

/*------------------------------------------------------------------------------

@name       initialize - initialize all preprocessors
                                                                              */
                                                                             /**
            Initialize all preprocessors.

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void initialize(
   TreeLogger logger)
   throws     Exception
{
   if (logger != null)
   {
      logger.log(logger.DEBUG, "IPreprocessor.initialize(): entered");
   }

   for (PreprocessorDsc preprocessorDsc : preprocessors)
   {
      ((IPreprocessor)preprocessorDsc.clas.newInstance()).initialize();
   }

   if (logger != null)
   {
      logger.log(logger.DEBUG, "IPreprocessor.initialize(): exiting");
   }
}
/*------------------------------------------------------------------------------

@name       isAppDependency - test if is an app dependency
                                                                              */
                                                                             /**
            Test whether the specified type is an app dependency, including the
            app itself.

@return     true iff the specified type is an app dependency.

@param      classname      target classname
@param      logger         logger

@history    Sat Jan 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
//static boolean isAppDependency(
//   String     classname,
//   TreeLogger logger)
//{
//   ComponentDsc app = JSXParser.getTargetApp(logger);
//
//   boolean bDependency =
//      (app != null && classname.equals(app.getClassname()))
//         || getParsedAppsDependencyByTag()
//               .get(getSimpleClassname(classname)) != null;
//
//   return(bDependency);
//}
/*------------------------------------------------------------------------------

@name       isCandidate - test whether specified classname is a candidate
                                                                              */
                                                                             /**
            Test whether specified classname is a candidate.

@return     true iff the specified classname is a candidate.

@param      classname      target classname

@history    Fri Sep 04, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static boolean isCandidate(
   String classname)
{
   boolean bCandidate = kCLASSNAMES_INCLUDE.contains(classname);
   if (!bCandidate)
   {
      bCandidate = true;
      for (String prexif : kPACKAGES_EXCLUDE)
      {
         if (classname.startsWith(prexif))
         {
            bCandidate = false;
            break;
         }
      }
   }
   return(bCandidate);
}
/*------------------------------------------------------------------------------

@name       main - standard main method
                                                                              */
                                                                             /**
            Standard main method.

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void main(
   String[] args)
{
   PreprocessorUnitTest.main(args);
}
/*------------------------------------------------------------------------------

@name       printPerformance - print performance
                                                                              */
                                                                             /**
            Print performance.

@param      logger      logger

@history    Fri Sep 04, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void printPerformance(
   TreeLogger logger)
{
   logger.log(logger.INFO, "Preprocessors performance --------------------");
   for (PreprocessorDsc preprocessorDsc : preprocessors)
   {
      logger.log(
         logger.INFO,
         preprocessorDsc.clas.getName()
       + ": targetsProcessed=" + preprocessorDsc.targetsProcessed
       + ", excecutionMsec="   + (preprocessorDsc.excecutionNsec / 1000000));
   }
}
/*------------------------------------------------------------------------------

@name       process - process specified source
                                                                              */
                                                                             /**
            Process specified source.

@return     processed source

@param      classname      classname to be processed
@param      contentBytes   content to be processed
@param      encoding       content encoding

@param      providerAndComponentCandidates
                           provider and component candidates from compiler,
                           where each corresponds to a single source file which
                           may or may not have a number of inner classes that
                           may or may not be additional components or providers

@param      bUpdate        iff true, this is an update of an incremental build
@param      logger         compiler logger

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
byte[] process(
   String             classname,
   byte[]             contentBytes,
   String             encoding,
   Map<String,String> providerAndComponentCandidates,
   boolean            bUpdate,
   TreeLogger         logger)
   throws             Exception;

/*==============================================================================

name:       PreprocessorDsc - preprocessor descriptor

purpose:    Preprocessor descriptor

history:    Fri Sep 04, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
class PreprocessorDsc
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public Class   clas;                   // class                               //
public int     targetsProcessed;       // number of targets processed         //
public long    excecutionNsec;         // execution time                      //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       PreprocessorDsc - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Sep 04, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public PreprocessorDsc()
{
}
/*------------------------------------------------------------------------------

@name       PreprocessorDsc - constructor for specified class
                                                                              */
                                                                             /**
            Constructor for specified class

@param      clas     class

@history    Fri Sep 04, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public PreprocessorDsc(
   Class clas)
{
   this.clas = clas;
}
}//====================================// end PreprocessorDsc ================//
}//====================================// end IPreprocessor ==================//
