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
