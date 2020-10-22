/*==============================================================================

name:       IJSXTransform.java

purpose:    ReactJava JSX Transform Interface

history:    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.compiler.jsx;
                                       // imports --------------------------- //
import com.google.gwt.core.ext.TreeLogger;
import io.reactjava.compiler.codegenerator.IPreprocessor;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
                                       // IJSXTransform ======================//
public interface IJSXTransform extends IPreprocessor
{
                                       // class constants --------------------//
boolean kSRCCFG_JSX_IN_SINGLE_LINE = true;

int     kTAB_SPACES     = 3;
String  kJSX_MARKUP_BEG = "/*--";
String  kJSX_MARKUP_END = "--*/";
String  kJSX_REGEXP_BEG = "/\\*--";
String  kJSX_REGEXP_END = "--\\*/";

String  kJAVA_STRING_BEG = "%javaStringBeg%";
String  kJAVA_STRING_END = "%javaStringEnd%";

                                       // class variables ------------------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       getFileAsJavaString - get file as string with newline characters
                                                                              */
                                                                             /**
            Get file as string with newline characters; that is, a string
            in which instances of System.lineSeparator() are replaced with '\n'.

@return     file as string with newline characters.

@param      file     file
@param      logger   logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getFileAsJavaString(
   File       file,
   TreeLogger logger)
   throws     IOException
{
   ByteArrayInputStream inBytes =
      new ByteArrayInputStream(getFileBytes(file, logger));

   StringBuilder builder = new StringBuilder();
   try (BufferedReader in = new BufferedReader(new InputStreamReader(inBytes)))
   {
      String line;
      while ((line = in.readLine()) != null)
      {
         builder.append(line);
         builder.append("\\n");
      }
   }

   return(builder.toString());
}
/*------------------------------------------------------------------------------

@name       getFileAsString - get file as string
                                                                              */
                                                                             /**
            Get file as string.

@return     file as string.

@param      file     file
@param      logger   logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getFileAsString(
   File       file,
   TreeLogger logger)
   throws     IOException
{
   String src = new String(getFileBytes(file, logger), "UTF-8");
   return(src);
}
/*------------------------------------------------------------------------------

@name       getFileBytes - get file bytes
                                                                              */
                                                                             /**
            Get file bytes.

@return     file bytes.

@param      file     file
@param      logger   logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static byte[] getFileBytes(
   File       file,
   TreeLogger logger)
   throws     IOException
{
   FileInputStream in = new FileInputStream(file);
   byte[] bytes = getInputStreamBytes(in, logger);
   in.close();
   return(bytes);
}
/*------------------------------------------------------------------------------

@name       getInputStreamAsString - get input stream as string
                                                                              */
                                                                             /**
            Get input stream as string.

@return     input stream as string.

@param      in    input stream

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
static String getInputStreamAsString(
   InputStream in,
   TreeLogger  logger)
   throws IOException
{
   return(new String(getInputStreamBytes(in, logger), "UTF-8"));
}
/*------------------------------------------------------------------------------

@name       getInputStreamBytes - get input stream bytes
                                                                              */
                                                                             /**
            Get input stream bytes.

@return     input stream bytes.

@param      in    input stream

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
static byte[] getInputStreamBytes(
   InputStream in,
   TreeLogger  logger)
   throws IOException
{
   ByteArrayOutputStream out = new ByteArrayOutputStream();
   IConfiguration.fastChannelCopy(in, out, in.available());

   return(out.toByteArray());
}
/*------------------------------------------------------------------------------

@name       getURLAsString - get url as string
                                                                              */
                                                                             /**
            Get url as string.

@return     url as string.

@param      url      target url

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getURLAsString(
   String     url,
   TreeLogger logger)
   throws     IOException
{
   String src = new String(getURLBytes(url, logger), "UTF-8");
   return(src);
}
/*------------------------------------------------------------------------------

@name       getURLBytes - get url bytes
                                                                              */
                                                                             /**
            Get url bytes.

@return     url bytes.

@param      url      target url

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static byte[] getURLBytes(
   String     url,
   TreeLogger logger)
   throws     IOException
{
   try (InputStream in = new URL(url).openStream())
   {
      return(getInputStreamBytes(in, logger));
   }
}
/*------------------------------------------------------------------------------

@name       parse - parse specified classname
                                                                              */
                                                                             /**
            Parse specified src.

@return     void

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
//String parse(
//   TypeDsc    component,
//   TreeLogger logger);

/*==============================================================================

name:       INativeComponent - external native component

purpose:    A marker for an external native component that holds the component
            name.

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static interface INativeComponent
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       getName - get component name
                                                                              */
                                                                             /**
            Get component name

@return     component name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getName();

}//====================================// end INativeComponent ===============//
}//====================================// end IJSXTransform ==================//
