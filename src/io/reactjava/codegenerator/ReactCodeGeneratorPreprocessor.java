/*==============================================================================

name:       ReactCodeGeneratorPreprocessor.java

purpose:    Builds ReactCodeGeneratorImplementation from template.

return:     void

history:    Sun Jan 06, 2019 10:30:00 (Giavaneers - LBM) created

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
import com.google.gwt.dev.util.log.AbstractTreeLogger;
import com.google.gwt.dev.util.log.PrintWriterTreeLogger;
import io.reactjava.client.core.react.ReactGeneratedCode;
import io.reactjava.jsx.IConfiguration;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
                                       // ReactCodeGeneratorPreprocessor =====//
public class ReactCodeGeneratorPreprocessor implements IPreprocessor
{
                                       // class constants --------------------//
                                       // copy of generated                   //
protected static final List<String> kFACTORY_MAP_KEYS = new ArrayList<>();

                                       // class variables ------------------- //
                                       // none                                //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
protected static TreeLogger logger;    // any tree logger                     //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       ReactCodeGeneratorPreprocessor - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ReactCodeGeneratorPreprocessor()
{
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
   if (logger == null)
   {
      try
      {
         Type maxDetail =
            invocationLogger == null || !(logger instanceof AbstractTreeLogger)
               ? TreeLogger.ALL
               :  ((AbstractTreeLogger)invocationLogger).getMaxDetail();

         File logFile =
            new File(
               IConfiguration.getProjectDirectory(null, invocationLogger),
               "antlog.codegenerator.preprocessor.txt");

         logFile.delete();

         logger = new PrintWriterTreeLogger(logFile);
         ((PrintWriterTreeLogger)logger).setMaxDetail(maxDetail);

         logger.log(logger.INFO, new Date().toString());
         logger.log(logger.INFO, "nanoTime=" + nanoTime);
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
   return(logger);
}
/*------------------------------------------------------------------------------

@name       initialize - initialize
                                                                              */
                                                                             /**
            Initialize. This implementation delets any previous log file.

@history    Sun Jul 14, 2019 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void initialize()
{
   logger = null;
}
/*------------------------------------------------------------------------------

@name       process - standard IPreprocessor process method
                                                                              */
                                                                             /**
            Parse specified byte array.

@return     void

@param      classname      classname to be processed
@param      contentBytes   cantent to be processed
@param      encoding       cantent encoding
@param      candidates     component candidates noted by compiler
@param      components     components noted by a previous preprocessor
@param      logger         compiler logger

@history    Fri Dec 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public byte[] process(
   String             classname,
   byte[]             contentBytes,
   String             encoding,
   Map<String,String> candidates,
   Map<String,String> components,
   TreeLogger         logger)
   throws             Exception
{
   long start = System.nanoTime();

   logger = getFileLogger(logger, start);
   logger.log(logger.DEBUG, "process(): entered");

   logger.log(
      logger.DEBUG,
      "ReactCodeGeneratorPreprocessor.process(): for " + classname);

   if (ReactGeneratedCode.class.getName().equals(classname))
   {
      logger.log(
         logger.INFO,
         "ReactCodeGeneratorPreprocessor.process(): processing " + classname);

      String content = new String(contentBytes, encoding);

                                       // attempt to add a factory map        //
                                       // entry for each candidate component  //
      String mapEntries = "";
      for (String componentClassname : components.keySet())
      {
         logger.log(
            logger.INFO,
            "ReactCodeGeneratorPreprocessor.process(): "
          + "adding FactoryMap entry for " + componentClassname);

         String entry =
            "\n"
          + "   kFACTORY_MAP.put(\n"
          + "      \"" +  componentClassname + "\",\n"
          + "      (Function<Properties,Component>)(props) ->\n"
          + "      {\n"
          + "         Component component = new " + componentClassname + "();\n"
          + "         if (props != null)\n"
          + "         {\n"
          + "            component.initialize(props);\n"
          + "         }\n"
          + "         return(component);\n"
          + "      });\n";

         mapEntries += entry;
                                       // save the entry so it can be checked //
                                       // in ReactCodeGenerator               //
                                       // checkComponentFactoryMap()          //
                                       // (temporary check before fix)        //
         kFACTORY_MAP_KEYS.add(componentClassname);
      }

      String platformProviderClassname =
         IConfiguration.getPlatform(logger).getClass().getName();

      content =
         content
         .replace("%componentEntries%", mapEntries)
         .replace( "%platformProviderClassname%", platformProviderClassname);

      logger.log(
         logger.INFO,
         "ReactCodeGeneratorPreprocessor.process(): "
       + "assigned platform provider classname=" + platformProviderClassname);

      logger.log(
         logger.DEBUG,
         "ReactCodeGeneratorPreprocessor.process(): parsed:"+ "\n" + content);

      contentBytes = content.getBytes(encoding);
   }

   return(contentBytes);
}
}//====================================// end ReactCodeGeneratorPreprocessor =//
