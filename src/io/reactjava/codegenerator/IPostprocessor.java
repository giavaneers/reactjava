/*==============================================================================

name:       IPostprocessor.java

purpose:    ReactJava Code Generation Postprocessor Interface.

history:    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

notes:

                  This program was created by Giavaneers
        and is the confidential and proprietary product of Giavaneers Inc.
      Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2018 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.codegenerator;
                                       // imports --------------------------- //
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.dev.CompilerContext;
import com.google.gwt.dev.jjs.PrecompilationContext;
                                       // IPostprocessor =====================//
public interface IPostprocessor
{
                                       // class constants --------------------//
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // all preprocessors on the classpath  //
Class[] postprocessors =
{
   ReactCodeGenerator.class
};

/*------------------------------------------------------------------------------

@name       allPostprocessors - process specified source
                                                                              */
                                                                             /**
            Process specified source.

@return     processed source

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
   logger.log(logger.DEBUG, "IPostprocessor.allPostprocessors(): entered");
   for (Class postprocessor : postprocessors)
   {
      ((IPostprocessor)postprocessor.newInstance()).process(
         logger, compilerContext, precompilationContext);
   }
   logger.log(logger.DEBUG, "IPostprocessor.allPostprocessors(): exiting");
}
/*------------------------------------------------------------------------------

@name       process - process specified source
                                                                              */
                                                                             /**
            Process specified source.

@return     processed source

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
