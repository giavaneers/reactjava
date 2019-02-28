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
import io.reactjava.client.core.react.ReactCodeGeneratorImplementation;
import java.io.IOException;
import java.util.Map;
                                       // ReactCodeGeneratorPreprocessor =====//
public class ReactCodeGeneratorPreprocessor implements IPreprocessor
{
                                       // class constants --------------------//
                                       // none                                //
                                       // class variables ------------------- //
                                       // none                                //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       ReactCodeGeneratorPreprocessor - default constructor
                                                                              */
                                                                             /**
            Default constructor

@return     An instance of ReactCodeGenerator if successful.

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ReactCodeGeneratorPreprocessor()
{
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
   throws             IOException
{
   logger.log(
      logger.DEBUG,
      "ReactCodeGeneratorPreprocessor.process(): for " + classname);

   if (ReactCodeGeneratorImplementation.class.getName().equals(classname))
   {
      logger.log(
         logger.INFO,
         "ReactCodeGeneratorPreprocessor.process(): processing.");

      String content = new String(contentBytes, encoding);

                                       // attempt to add a factory map        //
                                       // entry for each candidate component  //
      String mapEntries = "";
      for (String componentClassname : components.keySet())
      {
         String entry =
            "\n"
          + "   kFACTORY_MAP.put(\n"
          + "      \"" +  componentClassname + "\",\n"
          + "      (Function<Properties,Component>)(props) ->\n"
          + "      {\n"
          + "         return(new " + componentClassname + "(props));\n"
          + "      });\n";

         mapEntries += entry;
      }

      content =
         content
         .replace("%componentEntries%", mapEntries)
         .replace(
            "%platformProviderClassname%",
            "io.reactjava.client.core.providers.platform.web.PlatformWeb");

      logger.log(
         logger.DEBUG,
         "ReactCodeGeneratorPreprocessor.process(): parsed:"+ "\n" + content);

      contentBytes = content.getBytes(encoding);
   }

   return(contentBytes);
}
}//====================================// end ReactCodeGeneratorPreprocessor =//
