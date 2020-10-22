/*==============================================================================

name:       IPostprocessor.java

purpose:    ReactJava Code Generation Postprocessor Interface.

history:    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.compiler.codegenerator;
                                       // imports --------------------------- //
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.dev.CompilerContext;
import com.google.gwt.dev.jjs.PrecompilationContext;
import java.util.Date;
                                       // IPostprocessor =====================//
public interface IPostprocessor
{
                                       // class constants --------------------//
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // all postprocessors on the classpath //
PostprocessorDsc[] postprocessors =
{
   new PostprocessorDsc(ReactCodePackager.class),
};
                                       // initial build completed             //
boolean[]    bIncrementalBuildCompleted = new boolean[1];

                                       // postprocessors tree logger          //
TreeLogger[] logger = new TreeLogger[1];

/*------------------------------------------------------------------------------

@name       allPostprocessors - process specified source
                                                                              */
                                                                             /**
            Process specified source.

@param      logger                     logger
@param      compilerContext            compiler context
@param      precompilationContext      precompilation context

@history    Sun Mar 16, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
static void allPostprocessors(
   TreeLogger            logger,
   CompilerContext       compilerContext,
   PrecompilationContext precompilationContext)
   throws                Exception
{
   logger = getFileLogger(logger);
   try
   {
      boolean bUpdate = bIncrementalBuildCompleted[0] == true;
      logger.log(
         logger.DEBUG,
         "IPostprocessor.allPostprocessors(): entered for "
            + (bUpdate ? "incremental" : "initial") + " build");

      for (PostprocessorDsc postprocessorDsc : postprocessors)
      {
         long start = System.nanoTime();

         ((IPostprocessor)postprocessorDsc.clas.newInstance()).process(
            logger, compilerContext, precompilationContext, bUpdate);

         postprocessorDsc.targetsProcessed++;
         postprocessorDsc.excecutionNsec += System.nanoTime() - start;
      }
   }
   catch(Exception e)
   {
      logger.log(logger.ERROR, "IPostprocessor.allPostprocessors()", e);
      throw e;
   }
   finally
   {
      IPreprocessor.printPerformance(logger);
      IPostprocessor.printPerformance(logger);

      bIncrementalBuildCompleted[0] = true;

      logger.log(logger.INFO, "IPostprocessor.allPostprocessors(): exiting");
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
            invocationLogger, "antlog.codegenerator.postprocessor.txt");

      logger[0].log(TreeLogger.INFO, new Date().toString());
   }
   return(logger[0]);
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
   PostprocessorUnitTest.main(args);
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
   logger.log(logger.INFO, "Postprocessors performance --------------------");
   for (PostprocessorDsc postprocessorDsc : postprocessors)
   {
      logger.log(
         logger.INFO,
         postprocessorDsc.clas.getName()
       + ": targetsProcessed=" + postprocessorDsc.targetsProcessed
       + ", excecutionMsec="   + (postprocessorDsc.excecutionNsec / 1000000));
   }
}
/*------------------------------------------------------------------------------

@name       process - process specified source
                                                                              */
                                                                             /**
            Process specified source.

@param      logger                     logger
@param      compilerContext            compiler context
@param      precompilationContext      precompilation context
@param      bUpdate                    iff true, this is an update of an
                                       incremental build

@history    Sun Mar 16, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
void process(
   TreeLogger            logger,
   CompilerContext       compilerContext,
   PrecompilationContext precompilationContext,
   boolean               bUpdate)
   throws                Exception;

/*==============================================================================

name:       PostprocessorDsc - postprocessor descriptor

purpose:    postprocessor descriptor

history:    Fri Sep 04, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
class PostprocessorDsc
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

@name       PostprocessorDsc - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Sep 04, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public PostprocessorDsc()
{
}
/*------------------------------------------------------------------------------

@name       PostprocessorDsc - constructor for specified class
                                                                              */
                                                                             /**
            Constructor for specified class

@param      clas     class

@history    Fri Sep 04, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public PostprocessorDsc(
   Class clas)
{
   this.clas = clas;
}
}//====================================// end PostprocessorDsc ===============//
}//====================================// end IPostprocessor =================//
