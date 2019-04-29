/*==============================================================================

name:       IPreprocessor.java

purpose:    ReactJava Code Generation Preprocessor Interface.

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
import io.reactjava.jsx.JSXTransform;
import java.util.HashMap;
import java.util.Map;
                                       // IPreprocessor ======================//
public interface IPreprocessor
{
                                       // class constants --------------------//
                                       // all preprocessors on the classpath  //
Class[] preprocessors =
{
   JSXTransform.class,
   ReactCodeGeneratorPreprocessor.class
};
                                       // components noted by a preprocessor  //
Map<String,String> components = new HashMap<>();

/*------------------------------------------------------------------------------

@name       process - process specified source
                                                                              */
                                                                             /**
            Process specified source.

@return     processed source

@param      classname      classname to be processed
@param      contentBytes   cantent to be processed
@param      encoding       cantent encoding
@param      candidates     component candidates identified by compiler
@param      logger         compiler logger

@history    Sun Mar 16, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
static byte[] allPreprocessors(
   String             classname,
   byte[]             contentBytes,
   String             encoding,
   Map<String,String> candidates,
   TreeLogger         logger)
   throws             Exception
{
   logger.log(
      logger.DEBUG, "IPreprocessor.allPreprocessors(): entered for " + classname);

   byte[] processed = contentBytes;
   for (Class preprocessor : preprocessors)
   {
      processed =
         ((IPreprocessor)preprocessor.newInstance()).process(
            classname, processed, encoding, candidates, components, logger);
   }

   logger.log(logger.DEBUG, "IPreprocessor.allPreprocessors(): exiting");
   return(processed);
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
@param      candidates     component candidates identified by compiler
@param      components     components identified by preprocessors
@param      logger         compiler logger

@history    Sun Mar 16, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
byte[] process(
   String             classname,
   byte[]             contentBytes,
   String             encoding,
   Map<String,String> candidates,
   Map<String,String> components,
   TreeLogger         logger)
   throws             Exception;

}//====================================// end IPreprocessor ==================//
