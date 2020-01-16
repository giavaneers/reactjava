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
package io.reactjava.codegenerator;
                                       // imports --------------------------- //
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.dev.CompilerContext;
import com.google.gwt.dev.jjs.PrecompilationContext;
import com.google.gwt.dev.util.log.AbstractTreeLogger;
import com.google.gwt.dev.util.log.PrintWriterTreeLogger;
import io.reactjava.jsx.IConfiguration;
import java.io.File;
import java.util.Date;
                                       // IPostprocessor =====================//
public interface IPostprocessor
{
                                       // class constants --------------------//
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // all preprocessors on the classpath  //
Class[] postprocessors =
{
   ReactCodePackager.class
};
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
   long start = System.nanoTime();
   logger     = getFileLogger(logger, start);

   try
   {
      logger.log(logger.DEBUG, "IPostprocessor.allPostprocessors(): entered");
      for (Class postprocessor : postprocessors)
      {
         ((IPostprocessor)postprocessor.newInstance()).process(
            logger, compilerContext, precompilationContext);
      }
   }
   catch(Exception e)
   {
      logger.log(logger.ERROR, "IPostprocessor.allPostprocessors()", e);
      throw e;
   }
   finally
   {
      logger.log(logger.DEBUG, "IPostprocessor.allPostprocessors(): exiting");
   }
}
/*------------------------------------------------------------------------------

@name       getFileLogger - get file logger
                                                                              */
                                                                             /**
            Get file logger.

@return     file logger

@param      invocationLogger     logger passed with invocation
@param      nanoTime             time of invocation

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static TreeLogger getFileLogger(
   TreeLogger invocationLogger,
   long       nanoTime)
{
   if (logger[0] == null)
   {
      try
      {
         Type maxDetail =
            invocationLogger == null || !(logger[0] instanceof AbstractTreeLogger)
               ? TreeLogger.ALL
               :  ((AbstractTreeLogger)invocationLogger).getMaxDetail();

         File logFile =
            new File(
               IConfiguration.getProjectDirectory(null, invocationLogger),
               "antlog.codegenerator.postprocessor.txt");

         logFile.delete();

         logger[0] = new PrintWriterTreeLogger(logFile);
         ((PrintWriterTreeLogger)logger[0]).setMaxDetail(maxDetail);

         logger[0].log(TreeLogger.INFO, new Date().toString());
         logger[0].log(TreeLogger.INFO, "nanoTime=" + nanoTime);
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
   return(logger[0]);
}
/*------------------------------------------------------------------------------

@name       process - process specified source
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
void process(
   TreeLogger            logger,
   CompilerContext       compilerContext,
   PrecompilationContext precompilationContext)
   throws                Exception;

}//====================================// end IPostprocessor =================//
