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
package io.reactjava.jsx;
                                       // imports --------------------------- //
import com.google.gwt.core.ext.TreeLogger;
import io.reactjava.codegenerator.IPreprocessor;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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

Map<String,String> kREACT_TAGS =
   new HashMap<String,String>()
   {{
      put("React.Fragment", "Fragment");
      put("react.fragment", "Fragment");
      put("Fragment",       "Fragment");
   }};

Map<String,String> kSTD_TAGS =
   new HashMap<String,String>()
   {{
      put("a",         "a");
      put("abbr",      "abbr");
      put("acronym",   "acronym");
      put("article",   "article");
      put("audio",     "audio");
      put("b",         "b");
      put("bdo",       "bdo");
      put("big",       "big");
      put("br",        "br");
      put("button",    "button");
      put("canvas",    "canvas");
      put("caption",   "caption");
      put("cite",      "cite");
      put("code",      "code");
      put("col",       "col");
      put("colgroup",  "colgroup");
      put("dfn",       "dfn");
      put("div",       "div");
      put("em",        "em");
      put("footer",    "footer");
      put("form",      "form");
      put("fragment",  "fragment");
      put("header",    "header");
      put("h1",        "h1");
      put("h2",        "h2");
      put("h3",        "h3");
      put("h4",        "h4");
      put("h5",        "h5");
      put("h6",        "h6");
      put("i",         "i");
      put("iframe",    "iframe");
      put("img",       "img");
      put("input",     "input");
      put("kbd",       "kbd");
      put("label",     "label");
      put("li",        "li");
      put("main",      "main");
      put("map",       "map");
      put("object",    "object");
      put("ol",        "ol");
      put("pre",       "pre");
      put("option",    "option");
      put("optgroup",  "optgroup");
      put("p",         "p");
      put("q",         "q");
      put("samp",      "samp");
      put("script",    "script");
      put("section",   "section");
      put("select",    "select");
      put("small",     "small");
      put("source",    "source");
      put("span",      "span");
      put("strong",    "strong");
      put("sub",       "sub");
      put("sup",       "sup");
      put("table",     "table");
      put("td",        "td");
      put("textarea",  "textarea");
      put("th",        "th");
      put("time",      "time");
      put("tr",        "tr");
      put("tt",        "tt");
      put("strictMode","strictMode");
      put("ul",        "ul");
      put("var",       "var");
		                                 // svg related elements                //
      put("circle",    "circle");
      put("polygon",   "polygon");
      put("svg",       "svg");
   }};

Map<String,String> kINLINE_TAGS =
   new HashMap<String,String>()
   {{
      put("a",         "a");
      put("abbr",      "abbr");
      put("acronym",   "acronym");
      put("b",         "b");
      put("bdo",       "bdo");
      put("big",       "big");
      put("br",        "br");
      put("button",    "button");
      put("cite",      "cite");
      put("code",      "code");
      put("dfn",       "dfn");
      put("em",        "em");
      put("i",         "i");
      put("img",       "img");
      put("input",     "input");
      put("kbd",       "kbd");
      put("label",     "label");
      put("map",       "map");
      put("object",    "object");
      put("q",         "q");
      put("samp",      "samp");
      put("script",    "script");
      put("select",    "select");
      put("small",     "small");
      put("span",      "span");
      put("strong",    "strong");
      put("sub",       "sub");
      put("sup",       "sup");
      put("textarea",  "textarea");
      put("time",      "time");
      put("tt",        "tt");
      put("var",       "var");
   }};

String[] kFILTER_UNSUPPORTED =
{
   "com.giavaneers.net",
   "com.giavaneers.util.core",
   "com.google",
   "io.reactjava.client.core",
   "io.reactjava.codegenerator",
   "io.reactjava.jsx",
   "elemental2",
   "core",
   "java",
   "javax",
   "jsinterop",
   "org.jsoup"
};
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
String parse(
   String             classname,
   String             content,
   Map<String,String> components,
   TreeLogger         logger)
   throws             IOException;

/*==============================================================================

name:       ILibraryComponent - external library component

purpose:    A marker for an external library component that holds the component
            name.

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static interface ILibraryComponent
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

}//====================================// end ILibraryComponent ==============//
}//====================================// end IJSXTransform ==================//
