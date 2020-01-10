/*==============================================================================

name:       JSXTransform.java

purpose:    ReactJava JSX Transform intended to be used without
            ReactCodeGenerator.

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
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.dev.util.log.PrintWriterTreeLogger;
import io.reactjava.client.core.providers.platform.IPlatform;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.Utilities;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
                                       // JSXTransform =======================//
public class JSXTransform implements IJSXTransform
{
                                       // class constants --------------------//
public static final byte[] kSPACES = new byte[100];

public static final String kATTRIB_KEY_ID = "id";

public static final String kHTML_NON_BREAKING_SPACE    = "&nbsp;";
public static final String kUNICODE_NON_BREAKING_SPACE = "\\u00a0";
public static final String kUNICODE_TAB                = "\\u00a0\\u00a0\\u00a0";

public static final String kINLINE_BODY_DEFAULT =
   "elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0"
 + " ? parents.peek() : null,\"div\","
 + "io.reactjava.client.core.react.Properties.with(\"id\", getNextId()));"
 + "root = root == null ? elem : root;";

public static final String kINLINE_FTR =
   "if(root != null){element="
 + "io.reactjava.client.core.react.ElementDsc.createElement(root);"
 + "setId(element.props.getString(\"id\"));}"
 + "return(element);};"
 + "setComponentFcn(fcn);";

public static final String kINLINE_FTR_CSS =
   "if(css.length() > 0){this.css = css;}";

public static final String kINLINE_HDR =
   "java.util.function.Function"
 + "<io.reactjava.client.core.react.Properties,"
 + "io.reactjava.client.core.react.ReactElement> fcn="
 + "(props) ->{"
 + "io.reactjava.client.core.react.ReactElement element = null;"
 + "java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents="
 + "new java.util.Stack<>();"
 + "io.reactjava.client.core.react.ElementDsc root = null;"
 + "io.reactjava.client.core.react.ElementDsc elem;"
 + "useState(\"" + Component.kFORCE_UPDATE_KEY + "\",0);";

public static final String kMARKUP_JAVABLOCK_PLACEHOLDER =
   "<!-- javablock -->";

public static final String kPARSED_MARKUP_SECTION_BEGINNING =
"elem =io.reactjava.client.core.react.ElementDsc.create(";

                                       // map of replacement tokens           //
public static final Map<String,String> kREPLACE_TOKENS =
   new HashMap<String,String>()
   {{
      put("props()",       "props");
      put("getChildren()", "props.getChildren()");
   }};
                                       // maps of tag replacements            //
public static final Map<String,String> kREPLACEMENT_BY_TAG = new HashMap<>();
public static final Map<String,String> kTAG_BY_REPLACEMENT = new HashMap<>();

                                       // class variables ------------------- //
                                       // map of superset candidate source by //
                                       // classname                           //
protected static Map<String,String>
                             candidates;
                                       // map of superset component source by //
                                       // classname                           //
protected static Map<String,String>
                             components;
                                       // map of superset classname by tag    //
protected static Map<String,String>
                             tagsMap;
                                       // protected instance variables ------ //
protected int                indent;   // indent spaces                       //
protected String             tagRoot;  // root tag                            //
                                       // private instance variables -------- //
                                       // (none)                              //
static
{
   Arrays.fill(kSPACES, (byte)(' '));
}
/*------------------------------------------------------------------------------

@name       JSXTransform - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public JSXTransform()
{
}
/*------------------------------------------------------------------------------

@name       addInlineHeader - get inline header
                                                                              */
                                                                             /**
            Add inline header to specified string buffer.

@return     specified string buffer

@param      buf      string buffer

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public StringBuffer addInlineHeader(
   StringBuffer buf)
{
   write(buf, kINLINE_HDR);
   writeNewline(buf);
   return(buf);
}
/*------------------------------------------------------------------------------

@name       addInlineFooter - get inline footer
                                                                              */
                                                                             /**
            Add inline footer to specified string buffer.

@return     specified string buffer

@param      buf      string buffer

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public StringBuffer addInlineFooter(
   StringBuffer buf)
{
   write(buf, kINLINE_FTR);
   return(buf);
}
/*------------------------------------------------------------------------------

@name       ensureNullaryConstructor - parse zero arg constructor
                                                                              */
                                                                             /**
            Parse zero constructor arg of specified src.

@return     parsed specified src with zero constructor arg added if required.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String ensureNullaryConstructor(
   String          classname,
   String          src,
   TreeLogger      logger)
{
   String          parsed = src;
   //int             idx    = classname.lastIndexOf('.');
   //String          simple = idx < 0 ? classname : classname.substring(idx + 1);
   //List<MarkupDsc> dscs   = getMethodMarkupDscs(src, simple, logger);
   //if (dscs == null || dscs.size() == 0)
   //{
   //                                    // locate class declaration            //
   //   String kREGEX_TOKEN = "\\s+class\\s+" + simple + "\\s+[^{}:;]*\\Q{\\E";
   //
   //   StringBuffer buf = new StringBuffer();
   //   Matcher m   = Pattern.compile(kREGEX_TOKEN).matcher(src);
   //
   //   while(m.find())
   //   {
   //      String token    = m.group();
   //      int    tokenLen = token.length();
   //      if (tokenLen > 0)
   //      {
   //         String replacement = token + simple + "(){super();}";
   //         m.appendReplacement(buf, replacement);
   //      }
   //   }
   //   m.appendTail(buf);
   //
   //  parsed = buf.toString();
   //
   //}

   return(parsed);
}
/*------------------------------------------------------------------------------

@name       ensureOneArgConstructor - parse one arg constructor
                                                                              */
                                                                             /**
            Parse zero constructor arg of specified src.

@return     parsed specified src with zero constructor arg added if required.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String ensureOneArgConstructor(
   String     classname,
   String     src,
   TreeLogger logger)
{
   String parsed = src;
   return(parsed);
}
/*------------------------------------------------------------------------------

@name       filter - filter classname against unsupported
                                                                              */
                                                                             /**
            filter classname against unsupported.

@return     true iff supported

@param      classname      candidate classname

@history    Sat May 22, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean filter(
   String  classname)
{
   boolean bSupported = true;
   for (int i = 0; bSupported && i < kFILTER_UNSUPPORTED.length; i++)
   {
      bSupported = !classname.startsWith(kFILTER_UNSUPPORTED[i]);
   }
   return(bSupported);
}
/*------------------------------------------------------------------------------

@name       generateRenderableSource - generate renderable source
                                                                              */
                                                                             /**
            Get unique component tags.

@return     markup with replaced component tag names

@param      classname            classname
@param      src                  src
@param      markupDsc            markupDsc
@param      parsedMethodBody     parsedMethodBody
@param      logger               logger

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String generateRenderableSource(
   String     classname,
   String     src,
   MarkupDsc  markupDsc,
   String     parsedMethodBody,
   TreeLogger logger)
{
   String renderable =
      src.substring(0, markupDsc.idxBeg)
         + kINLINE_HDR.replace("%classname%", classname)
         + parsedMethodBody
         + kINLINE_FTR
         + src.substring(markupDsc.idxEnd);

   return(renderable);
}
/*------------------------------------------------------------------------------

@name       generateStyleSource - parse content
                                                                              */
                                                                             /**
            Parse content, for example,

            String topValue  = "" + this.top  + "px";
         /*--
            .panel
            {
               height:           30px;
               width:            30px;
         --*/                                                                /**
            String leftValue = "" + this.left + "px";
         /*--
               top:              {topValue};
               left:             {leftValue};
            }
         --*/                                                                /**

         becomes,

            String topValue  = "" + this.top  + "px";
            String css = ".panel{height:30px;width:30px;";







            String leftValue = "" + this.left + "px";
            css += "top:" + topValue + ";left:" + leftValue + ";}";if(css.length>0){"this.css = css;};




@return     markup

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String generateStyleSource(
   String     content,
   TreeLogger logger)
{
   String     parsed = "";
   String     cssBeg = "/*--";
   String     cssEnd = "--*/";
   int        idxBeg = 0;
   int        idxEnd = content.indexOf(cssBeg, idxBeg);
   String     cssBlk;
   String     cssHdr;

   while(true)
   {
      cssHdr = idxBeg == 0 ? "String css = \"" : "css += \"";

                                       // java block ------------------------ //
      if (idxEnd < 0)
      {
                                       // trailing block                      //
         parsed += content.substring(idxBeg);
         break;
      }
      parsed += content.substring(idxBeg, idxEnd);

                                       // css ------------------------------- //
      idxBeg  = idxEnd + cssBeg.length();
      idxEnd  = content.indexOf(cssEnd, idxBeg);
      cssBlk  = content.substring(idxBeg, idxEnd);
      parsed += cssHdr + resolveCSS(toCompactSingleLine(cssBlk)) + "\";";

      idxBeg = idxEnd + cssEnd.length();
      idxEnd = content.indexOf(cssBeg, idxBeg);
      if (idxEnd < 0)
      {
         parsed += kINLINE_FTR_CSS;
      }
      for (String line : cssBlk.split("\n"))
      {
         parsed += "\n";
      }
   }

   return(parsed);
}
/*------------------------------------------------------------------------------

@name       getComponent - get component from specified content
                                                                              */
                                                                             /**
            Parse component from specified content.

@param      classname      candidate classname
@param      content        candidate source
@param      components     components identified by at least this preprocessor
@param      logger         compiler logger

@history    Fri Dec 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void getComponent(
   String             classname,
   String             content,
   Map<String,String> components,
   TreeLogger         logger)
   throws             IOException
{
   if (filter(classname))
   {
      String parsed = parse(classname, content, getTagsMap(), logger);
      if (!parsed.equals(content))
      {
                                       // add any missing constructors        //

         parsed = ensureNullaryConstructor(classname, parsed, logger);
         parsed = ensureOneArgConstructor(classname, parsed, logger);

                                       // add/update component                //
         components.put(classname, parsed);

         logger.log(
            logger.INFO,
            "JSXTransform.getComponent(): added parsed component " + classname);
      }
   }
}
/*------------------------------------------------------------------------------

@name       getComponents - get components from map of candidates
                                                                              */
                                                                             /**
            Parse components from map of candidates.

@param      candidates     component and provider candidates noted by compiler
@param      components     components identified by at least this preprocessor
@param      logger         compiler logger

@history    Fri Dec 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void getComponents(
   Map<String,String> candidates,
   Map<String,String> components,
   TreeLogger         logger)
{
   for (String classname : candidates.keySet())
   {
      try
      {
         getComponent(classname, candidates.get(classname), components, logger);
      }
      catch(Exception e)
      {
         throw new RuntimeException("classname=" + classname, e);
      }
   }
}
/*------------------------------------------------------------------------------

@name       getComponentTags - get unique component tags
                                                                              */
                                                                             /**
            Get unique component tags. Replaces React.Fragment shorthand ('<>')
            with non-shorthand version in the process.

@return     list of unique component tags

@param      markup      original markup

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static List<String> getComponentTags(
   String markup)
{
   Set<String> tags = new HashSet<>();
                                       // opening angle bracket,              //
                                       // followed by zero or more chars      //
                                       // other than closing angle bracket,   //
                                       //followed by closing angle bracket    //
   String[] whitespace   = {" ", "\n", "\t", "\f", "\r"};
   String   kREGEX_TOKEN = "\\<([^>]*)\\>";

   Matcher m = Pattern.compile(kREGEX_TOKEN).matcher(markup);
   while (m.find())
   {
      String tag = m.group();
      if (tag.startsWith("<!--"))
      {
         continue;
      }

      String trimmed = tag.replace("<","").replace(">","").trim();
      if (!trimmed.startsWith("/"))
      {
                                       // check for tags as attriutes         //
         tags.addAll(getComponentTags(tag.substring(1)));

         tag = trimmed.replace("/","");

         for (int iWhite = 0, idx = 0; iWhite < whitespace.length; iWhite++)
         {
            idx = tag.indexOf(whitespace[iWhite]);
            if (idx > 0)
            {
               tag = tag.substring(0, idx);
            }
         }

         tags.add(tag);
      }
   }
   return(getComponentTagsOrdered(tags));
}
/*------------------------------------------------------------------------------

@name       getComponentTagsOrdered - order the specified set of tags
                                                                              */
                                                                             /**
            Order the specified set of tags them such that no tag in the set
            preceeds another that contains it as a substring.

@return     odered set of specified tag names

@param      tags     unordered set of tag names

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static List<String> getComponentTagsOrdered(
   Set<String> tags)
{
   List<String> ordered = new ArrayList<String>();
   if (tags.size() > 0)
   {
      List<String> unordered = new ArrayList<String>(tags);
      while(true)
      {
         int orderedSize = ordered.size();
         for (String orderedTag : ordered)
         {
            unordered.remove(orderedTag);
         }
         for (String unorderedTag : unordered)
         {
            boolean bSubstring = false;
            for (String chase : unordered)
            {
               if (chase.equals(unorderedTag))
               {
                  continue;
               }
               if (chase.contains(unorderedTag))
               {
                  bSubstring = true;
                  break;
               }
            }
            if (!bSubstring)
            {
               ordered.add(unorderedTag);
               break;
            }
         }
         if (ordered.size() == orderedSize)
         {
            throw new IllegalStateException("Cannot order component tag names");
         }
         if (ordered.size() == tags.size())
         {
            break;
         }
      }
   }

   return(ordered);
}
/*------------------------------------------------------------------------------

@name       getCurrentBuffer - get current string buffer from specified list
                                                                              */
                                                                             /**
            Get current string buffer from specified list.

@return     current string buffer from specified list

@param      bufs     string buffer

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static StringBuffer getCurrentBuffer(
   List<StringBuffer> bufs)
{
   if (bufs.size() == 0)
   {
      bufs.add(new StringBuffer());
   }

   return(bufs.get(bufs.size() - 1));
}
/*------------------------------------------------------------------------------

@name       getIsLibraryTagName - check whether library tag name
                                                                              */
                                                                             /**
            Check whether library tag name.

@return     true iff library tag name

@param      tagName     candidate

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean getIsLibraryTagName(
   String     tagName,
   TreeLogger logger)
{
   return(IConfiguration.getLibraryComponent(tagName, logger) != null);
}
/*------------------------------------------------------------------------------

@name       getIsReactTagName - check whether react tag name
                                                                              */
                                                                             /**
            Check whether react tag name.

@return     true iff react tag name

@param      tagName     candidate

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean getIsReactTagName(
   String tagName)
{
   return(tagName != null && kREACT_TAGS.get(tagName) != null);
}
/*------------------------------------------------------------------------------

@name       getIsStandardTagName - check whether standard tag name
                                                                              */
                                                                             /**
            Check whether standard tag name.

@return     true iff standard tag name

@param      tagName     candidate

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean getIsStandardTagName(
   String tagName)
{
   return(tagName != null && kSTD_TAGS.get(tagName) != null);
}
/*------------------------------------------------------------------------------

@name       getMethodMarkupDscs - get method markup dscs from source
                                                                              */
                                                                             /**
            Get method markup descriptors from source.

@return     method markup descriptors from source.

@param      src      src file string.

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static List<MarkupDsc> getMethodMarkupDscs(
   String      src,
   String      methodName,
   TreeLogger  logger)
{
   logger.log(
      logger.DEBUG,
      "JSXTransform.getMethodMarkupDscs(): entered");

                                       // locate any specified method         //
   List<MarkupDsc> markupDscs = null;
   String          content    = null;
   String          regex      =
      Utilities.kREGEX_ONE_OR_MORE_WHITESPACE  + methodName
    + Utilities.kREGEX_ZERO_OR_MORE_WHITESPACE + "\\Q(\\E"
    + Utilities.kREGEX_ZERO_OR_MORE_WHITESPACE + "\\Q)\\E"
    + Utilities.kREGEX_ZERO_OR_MORE_WHITESPACE + "\\Q{\\E";

   int idxContentBeg = -1;
   int idxContent    = -1;

   String[] splits = src.split(regex);
   if (splits.length == 2)
   {
                                       // specified method found              //
      idxContent    = src.indexOf(splits[1]);
      idxContentBeg = idxContent;

      for (int depth = 0; depth >= 0;)
      {
                                       // find next close brace               //
         int idxBraceClose = src.indexOf("}", idxContent + 1);

                                       // find next open brace                //
         int idxBraceOpen = src.indexOf("{", idxContent + 1);
         if (idxBraceOpen >= 0 && idxBraceOpen < idxBraceClose)
         {
            idxContent = idxBraceOpen;
            depth++;
            continue;
         }
         idxContent = idxBraceClose;
         if (idxContent >= 0)
         {
            depth--;
         }

         content = src.substring(idxContentBeg, idxContent);
      }
   }

   if (content != null)
   {
                                       // the first dsc is the entire content //
      markupDscs = new ArrayList<>();
      markupDscs.add(new MarkupDsc(idxContentBeg, idxContent));

                                       // any additional are for each section //
      for (int idxBeg = 0, idxEnd = idxBeg, idxMax = content.length(); true;)
      {
         idxBeg = content.indexOf(kJSX_MARKUP_BEG, idxEnd);
         if (idxBeg < 0 || idxBeg > idxMax)
         {
            break;
         }
         idxEnd = content.indexOf(kJSX_MARKUP_END, idxBeg);
         idxEnd = content.indexOf("\n", idxEnd) + 1;
         if (idxEnd < 0 || idxEnd > idxMax || idxEnd < idxBeg)
         {
            throw new IllegalStateException(
               "Unmatched markup delimiters.\n" + content);
         }
                                       // subsequent descriptors are the      //
                                       // number of lines of each markup      //
                                       // section                             //
         String[] lines = content.substring(idxBeg, idxEnd).split("\n");
         markupDscs.add(new MarkupDsc(lines.length, 0));
      }
   }

   return(markupDscs);
}
/*------------------------------------------------------------------------------

@name       getJavaLiteralExpressions - get java literal expressions
                                                                              */
                                                                             /**
            Get sequence of java literal expressions in the specified markup.

@return     sequence of unique java literal expressions in the specified markup

@param      markup      original markup

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static List<String> getJavaLiteralExpressions(
   String markup)
{
   List<String> expressions = new ArrayList<>();
                                       // find sequence of literal expressions//

                                       // opening brace,                      //
                                       // followed by zero or more chars      //
                                       // other than closing brace,           //
                                       //followed by closing brace            //
   String kREGEX_TOKEN = "\\{([^}]*)\\}";

   Matcher m = Pattern.compile(kREGEX_TOKEN).matcher(markup);
   while (m.find())
   {
      expressions.add(m.group());
   }
   return(expressions);
}
/*------------------------------------------------------------------------------

@name       getJSXTransform - get jsx transform for current platform
                                                                              */
                                                                             /**
            Get jsx transform for current platform.

@return     jsx transform for current platform

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static IJSXTransform getJSXTransform(
   TreeLogger logger)
{
   IJSXTransform jsx =
      IPlatform.kPLATFORM_WEB.equals(IConfiguration.getPlatform(logger).getOS())
         ? new JSXTransform()
         : new JSXTransformMobile();

   return(jsx);
}
/*------------------------------------------------------------------------------

@name       getTagIsRoot - test whether tag is root
                                                                              */
                                                                             /**
            Test whether tag is root.

@return     true iff tag is root

@param      tagName     candidate tag name

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean getTagIsRoot(
   String tagName)
{
   return(indent == 0 && tagName.equals(tagRoot));
}
/*------------------------------------------------------------------------------

@name       getTagsMap - get tags map
                                                                              */
                                                                             /**
            Get tags map.

@return     tags map

@history    Fri Dec 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static Map<String,String> getTagsMap()
{
   if (tagsMap == null)
   {
      tagsMap = new HashMap<>();
      for (String classname : candidates.keySet())
      {
         String tag = classname.substring(classname.lastIndexOf('.') + 1);
         tagsMap.put(tag, classname);
      }
   }
   return(tagsMap);
}
/*------------------------------------------------------------------------------

@name       handleElement - handle element
                                                                              */
                                                                             /**
            SElement handler.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void handleElement(
   String             classname,
   List<StringBuffer> bufs,
   Element            element,
   List<String>       javaBlocks,
   Map<String,String> components,
   TreeLogger         logger)
{
   boolean bFinished = false;
   boolean bPushed   = false;
   String  tagName   = resolveTagName(element.tagName());
   boolean bBodyPart =
      !(element instanceof Document)
         && !"html".equals(tagName)
         && !"head".equals(tagName)
         && !"body".equals(tagName);

   if (bBodyPart)
   {
      String tagProperties = parseElementAttributes(element, logger);

      StringBuffer buf = getCurrentBuffer(bufs);
      //if (!bInlineHdr)
      //{
      //   addInlineHeader(buf);
      //   bInlineHdr = true;
      //}
      if (tagRoot == null)
      {
         tagRoot = tagName;
      }

      write(buf, kPARSED_MARKUP_SECTION_BEGINNING);
      write(buf, "parents.size() > 0 ? parents.peek() : null,");
      writeNewline(buf);

      boolean bStdTagName   = getIsStandardTagName(tagName);
      boolean bReactTagName = getIsReactTagName(tagName);
      boolean bLibTagName   =
         !bStdTagName && !bReactTagName && getIsLibraryTagName(tagName, logger);

      if (bStdTagName || bReactTagName || bLibTagName)
      {
         String name = tagName;
         if (bStdTagName)
         {
                                       // resolve std tag name for platform   //
            boolean bText = false;
            for (int i = 0, iMax = element.childNodeSize(); i < iMax; i++)
            {
               if (bText = element.childNode(i) instanceof TextNode)
               {
                  break;
               }
            }

            name =
               resolvePlatformTag(
                  tagName, bText, IConfiguration.getPlatform(logger), logger);

            if (!name.equals(tagName))
            {
                                       // change the element tag name too     //
               element.tagName(name);
            }
         }
         else if (!bReactTagName)
         {
                                       // library tag name                    //
            int    idx   = tagName.lastIndexOf('.');
            String plain = idx >= 0 ? tagName.substring(idx + 1) : tagName;
                   name  = plain;
         }

         write(buf, "\"" + name + "\",");
         writeNewline(buf);
         write(buf, tagProperties);
      }
      else
      {
         String componentClassname =
            components != null ? components.get(tagName) : null;

         if (componentClassname == null)
         {
            throw new IllegalStateException(
               tagName + " is neither a standard tag name "
             + "nor a known component simple class name "
             + "nor a known library component class name. "
             + "Are you sure you have included all source paths "
             + "in your module xml file?");
         }

         write(
            buf,
            "io.reactjava.client.core.react.ReactJava.getNativeFunctionalComponent(\""
          + componentClassname
          + "\"),");

         writeNewline(buf);
         //write(
         //   buf,
         //   "new "
         // + componentClassname
         // + "(" + tagProperties + ").props()");

         write(
            buf,
            "new "
          + componentClassname
          + "().initialize(" + tagProperties + ")");
      }
   }

   for (int i = 0, iMax = element.childNodeSize(); i < iMax; i++)
   {
      Node child = element.childNode(i);
      if (child instanceof TextNode)
      {
         if ((child = handleText(bufs, (TextNode)child, logger)) == null)
         {
                                          // a text node with all whitespace  //
            continue;
         }
      }
      if (child instanceof Element)
      {
         if (bBodyPart && !bFinished)
         {
            bPushed   = handleElementFinish(bufs, element, logger);
            bFinished = true;
         }

         handleElement(
            classname, bufs, (Element)child, javaBlocks, components, logger);
      }
      else if (child instanceof Comment)
      {
         if (child.toString().contains(kMARKUP_JAVABLOCK_PLACEHOLDER))
         {
            if (bBodyPart && !bFinished)
            {
               bPushed   = handleElementFinish(bufs, element, logger);
               bFinished = true;
            }
            if (bufs.size() > 0)
            {
               replaceCurrentBufferWithCompactSingleLine(bufs);
            }
                                       // add a new one for the javablock     //
            bufs.add(new StringBuffer());
            writeJavaBlock(classname, bufs, javaBlocks.remove(0));

                                       // add another for the next inline     //
            bufs.add(new StringBuffer());
         }
      }
   }
   if (bBodyPart && !bFinished)
   {
      bPushed = handleElementFinish(bufs, element, logger);
   }
   if (bPushed)
   {
      handleElementPopParent(bufs, element, logger);
   }
}
/*------------------------------------------------------------------------------

@name       handleElementFinish - end tag handler
                                                                              */
                                                                             /**
            End tag handler.

@return     void

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean handleElementFinish(
   List<StringBuffer> bufs,
   Element            element,
   TreeLogger         logger)
{
   StringBuffer buf = getCurrentBuffer(bufs);
   write(buf, ");");
   outdent();
   outdent();
   writeNewline(buf);

   write(buf, "root = root == null ? elem : root;");
   writeNewline(buf);

   return(handleElementPushParent(bufs, element, logger));
}
/*------------------------------------------------------------------------------

@name       handleElementPopParent - handle element
                                                                              */
                                                                             /**
            SElement handler.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void handleElementPopParent(
   List<StringBuffer> bufs,
   Element            element,
   TreeLogger         logger)
{
   StringBuffer buf = getCurrentBuffer(bufs);
   write(buf, "parents.pop();");
   writeNewline(buf);
}
/*------------------------------------------------------------------------------

@name       handleElementPushParent - handle element
                                                                              */
                                                                             /**
            SElement handler.

@return     void

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean handleElementPushParent(
   List<StringBuffer> bufs,
   Element            element,
   TreeLogger         logger)
{
   boolean bPush = !element.children().isEmpty();
   if (bPush)
   {
      StringBuffer buf = getCurrentBuffer(bufs);
      write(buf, "parents.push(elem);");
      writeNewline(buf);
   }

   return(bPush);
}
/*------------------------------------------------------------------------------

@name       handleText - text handler
                                                                              */
                                                                             /**
            Text handler. The strategy is:

            1. wrap all text nodes whose parent is not a span inside a new span.
            2. otherwise, add the text to the buf.

@return     void

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Node handleText(
   List<StringBuffer> bufs,
   TextNode           textNode,
   TreeLogger         logger)
{
   Node         retVal;
   StringBuffer buf              = getCurrentBuffer(bufs);
   String       text             = textNode.toString();
   String       trimmed          = text.trim();
   Node         parent           = textNode.parent();
   String       parentName       = parent.nodeName();
   String       parentClassname  = parent.attributes().get("className");
   boolean      bSpan            = "span".equals(parentName);
   boolean      bReactNativeText = "ReactNative.Text".equals(parentName);
   boolean      bPrism           =
      "code".equals(parentName)
         && parentClassname != null
         && parentClassname.startsWith("language-");

   if (trimmed.length() == 0)
   {
                                       // skip text nodes with all whitespace //
      retVal = null;
   }
   else if ("{props.getChildren()}".equals(trimmed))
   {
                                       // special case?                       //
      write(buf, ", props.getChildren()");
                                       // text node has been processed        //
      retVal = null;
   }
   else if (!bSpan && !bPrism && !bReactNativeText)
   {
                                       // wrap in a span tag so any sibling   //
                                       // nodes like <code> or <strong> will  //
                                       // be handled appropriately            //
      Element span = new Element("span");
      textNode.replaceWith(span);
      span.appendChild(textNode);
      retVal = span;
   }
   else
   {
      boolean bWrapWithQuotes = true;
      boolean bEscapeQuotes   = true;
      String  parsed          = "";
      int     idx             = 0;

      for (String expression : getJavaLiteralExpressions(trimmed))
      {
         idx = text.indexOf(expression, idx);
         if (parsed.length() == 0)
         {
            parsed = text.substring(0, idx);
         }

         if (idx >= 0)
         {
            parsed += expression.substring(1, expression.length() - 1);
         }

         idx  += expression.length();
      }
      if (idx > 0)
      {
         if (idx < text.length())
         {
            parsed += text.substring(idx);
         }
                                       // check for text from a url           //
         text            = handleTextFromURL(parsed, logger);
         bWrapWithQuotes = !text.equals(parsed);
         bEscapeQuotes   = false;
      }
                                       // escape any double-quotes            //
      if (bEscapeQuotes)
      {
         text = text.replace("\"","\\\"");
      }
      if (bWrapWithQuotes)
      {
                                       // wrap with double-quotes             //
         text = "\"" + text + "\"";
      }
                                       // as workaround for react problem     //
                                       // with '&nbsp;', explicitly replace   //
                                       // html non-breaking space with        //
                                       // unicode literal                     //
      text = text.replace(kHTML_NON_BREAKING_SPACE, kUNICODE_NON_BREAKING_SPACE);

      write(buf, ",");
      write(buf, text);
                                       // text node has been processed        //
      retVal = null;
   }

   return(retVal);
}
/*------------------------------------------------------------------------------

@name       handleTextFromTextResource - handle text from a text resource
                                                                              */
                                                                             /**
            Get text from a text resource.

            A text resource descriptor has the following format:

               path0/path1/path2/...pathN:.field0.field1.field2...fieldN

            where  path0/path1/path2...pathN      is the relative filepath
            and    .field0.field1.field2...fieldN is the label of the text

@return     text from the specified text resource.

@param      textResource      text resource descriptor

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String handleTextFromTextResource(
   String     textResource,
   TreeLogger logger)
{
   String text;
   try
   {
      String[] tokens = textResource.substring("text://".length()).split(":");
      if (tokens.length > 2)
      {
         throw new Exception("Text resource format error: " + textResource);
      }

      File   warDir = IConfiguration.getProjectDirectory("war", logger);
      File   file   = new File(warDir, tokens[0]);
             text   = IJSXTransform.getFileAsJavaString(file, logger);

      if (tokens.length == 2)
      {
         int idxBeg  = text.indexOf(tokens[1]);
         if (idxBeg < 0)
         {
            throw new Exception("Text resource label error: " + tokens[1]);
         }
             idxBeg += tokens[1].length();
         int idxEnd  = text.indexOf("\\n.end", idxBeg + 1);
         if (idxEnd < 0)
         {
            idxEnd = text.length();
         }

         text = text.substring(idxBeg, idxEnd);
         if (text.startsWith("\\n"))
         {
            text = text.substring(2);
         }
      }
   }
   catch(Exception e)
   {
      text = e.getMessage();
   }

   return(kJAVA_STRING_BEG  + text + kJAVA_STRING_END);
}
/*------------------------------------------------------------------------------

@name       handleTextFromURL - handle text from a possible URL
                                                                              */
                                                                             /**
            Get text from a possible URL

@return     text from the specified URL, or iff not a url, the specified text.

@param      url      text or url
@param      logger   any logger

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String handleTextFromURL(
   String     url,
   TreeLogger logger)
{
   String text = url;
   int    idx  = url.indexOf("://");
   if (idx > 0)
   {
      try
      {
         switch(url.substring(0, idx))
         {
            case "http":
            case "htts":
            {
               text = IJSXTransform.getURLAsString(url, logger);
               break;
            }
            case "text":
            {
               text = handleTextFromTextResource(url, logger);
            }
         }
      }
      catch(Exception e)
      {
         text = "Error reading " + url;
      }
   }

   return(text);
}
/*------------------------------------------------------------------------------

@name       indent - indent one tab
                                                                              */
                                                                             /**
            Indent one tab.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void indent()
{
   if (!kSRCCFG_JSX_IN_SINGLE_LINE)
   {
      indent += kTAB_SPACES;
   }
}
/*------------------------------------------------------------------------------

@name       indexOfMatchingCurlyBrace - indent one tab
                                                                              */
                                                                             /**
            Indent one tab.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static int indexOfMatchingCurlyBrace(
   String raw,
   int    idxBeg)
{
   int depth = 0;
   int idx   = -1;

   while(true)
   {
      int candidate = raw.indexOf('}', idxBeg);
      if (candidate < 0)
      {
                                       // no matching end brace               //
         break;
      }

      int nextOpenBrace = raw.indexOf('{', idxBeg + 1);
      if (nextOpenBrace > 0 && nextOpenBrace < candidate)
      {
         idxBeg = nextOpenBrace;
         depth++;
      }
      else if (depth > 0)
      {
         depth--;
      }
      else
      {
         idx = candidate;
         break;
      }
   }

   return(idx);
}
/*------------------------------------------------------------------------------

@name       initialize - initialize
                                                                              */
                                                                             /**
            Initialize. This implementation is null.

@history    Sun Jul 14, 2019 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void initialize()
{
}
/*------------------------------------------------------------------------------

@name       main - standard main method
                                                                              */
                                                                             /**
            Standard main method. This implementation is null.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void main(
   String[] args)
{
   String testNum =
      args.length == 0
         ? "0"
         : args[0].toLowerCase().equals("unittests")
            || args[0].toLowerCase().equals("unittest")
            ? "0" : args[0];

   boolean bOK = unitTest(Integer.parseInt(testNum));

   System.out.println("---------------------------");
   System.out.println("Unit tests -> " + (bOK ? "SUCCESS!" : "ERROR"));
   System.out.println("---------------------------");
}
/*------------------------------------------------------------------------------

@name       normalizeParsed - transform to single line
                                                                              */
                                                                             /**
            Transform to single line.

@return     transformed

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String normalizeParsed(
   String  raw,
   boolean bEscapeQuotes)
{
   String normalized =
      raw.replace("\n"," ")
      .replace("\t"," ")
      .replaceAll("\\s+"," ");

   if (normalized.startsWith(" "))
   {
      normalized = normalized.replaceFirst("\\s+","");
   }
   if (bEscapeQuotes)
   {
      normalized = normalized.replace("\"","\\\"");
   }

   return(normalized);
}
/*------------------------------------------------------------------------------

@name       normalizeParsedCharacterEntities - transform to character entities
                                                                              */
                                                                             /**
            Transform whitespace to to character entities.

@return     transformed

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes


                                                                              */
//------------------------------------------------------------------------------
//public static String normalizeParsedCharacterEntities(
//   String  raw,
//   boolean bEscapeQuotes)
//{
//   //String normalized =
//   //   raw.replace("\n", kUNICODE_CARRIAGE_RETURN)
//   //   .replace("\t",    kUNICODE_TAB)
//   //   .replace(" ",     kUNICODE_NON_BREAKING_SPACE);
//
//   String normalized = raw;
//   if (bEscapeQuotes)
//   {
//      normalized = normalized.replace("\"","\\\"");
//   }
//
//   return(normalized);
//}
/*------------------------------------------------------------------------------

@name       outdent - outdent one tab
                                                                              */
                                                                             /**
            IOutdent one tab.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void outdent()
{
   if (!kSRCCFG_JSX_IN_SINGLE_LINE)
   {
      indent -= kTAB_SPACES;
      if (indent < 0)
      {
         throw new IllegalStateException("Indent may not be less than zero");
      }
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
public String parse(
   String     classname,
   TreeLogger logger)
{
   //File srcFile = getComponentSourceFile(classname, logger);
   //if (srcFile == null)
   //{
   //   throw new IllegalStateException("Cannot find source file for " + classname);
   //}
   //
   //String src    = IJSXTransform.getFileAsString(srcFile, logger);
   //String parsed = parse(classname, src, new HashMap<>(), logger);
   //
   return(null);
}
/*------------------------------------------------------------------------------

@name       parse - parse specified classname
                                                                              */
                                                                             /**
            Parse specified src.

@return     parsed source unless neither render() nor renderCSS() method is
            found, in which case returns null.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String parse(
   String             classname,
   String             src,
   Map<String,String> components,
   TreeLogger         logger)
{
   String parsed = parseMarkup(classname, src, components, logger);
          parsed = parseCSS(parsed, logger);
          parsed = parseCompileTimeDirectives(parsed, logger);

   return(parsed);
}
/*------------------------------------------------------------------------------

@name       parse - parse specified markup
                                                                              */
                                                                             /**
            Parse specified markup.

@return     parsed markup

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String parse(
   String             classname,
   String             src,
   List<MarkupDsc>    markupDscs,
   Map<String,String> components,
   TreeLogger         logger)
{
   List<String>       javaBlks = new ArrayList<>();
   MarkupDsc          first    = markupDscs.remove(0);
   String             content  = src.substring(first.idxBeg, first.idxEnd);
   String             markup   = parseContent(content, javaBlks);
   List<StringBuffer> bufs     = new ArrayList<>();

   parse(classname, bufs, javaBlks, markup, components, logger);

   boolean      bAnyMarkup = false;
   Boolean      bMarkup    = null;
   StringBuffer full       = new StringBuffer();
   for (StringBuffer chase : bufs)
   {
      String sChase = chase.toString();
      //if (!bInlineHdr)
      //{
      //   sChase     = kINLINE_HDR + sChase;
      //   bInlineHdr = true;
      //}
      if (bMarkup == null)
      {
         bMarkup = sChase.startsWith(kPARSED_MARKUP_SECTION_BEGINNING);
      }

      full.append(sChase);
      if (bMarkup)
      {
         bAnyMarkup = true;
         if (markupDscs.size() > 0)
         {
            MarkupDsc markupDsc = markupDscs.remove(0);
            //if (markupDscs.size() == 0)
            //{
            //   addInlineFooter(full);
            //}
            int numLines = markupDsc.idxBeg;
            for (int i = 1; i < numLines; i++)
            {
               full.append("\n");
            }
         }
      }
      bMarkup = !bMarkup;
   }
   if (!bAnyMarkup)
   {
      logger.log(
         logger.WARN,
         "JSXTransform.parse(): no markup found for " + classname);
   }

   return(full.toString());
}
/*------------------------------------------------------------------------------

@name       parse - parse specified markup
                                                                              */
                                                                             /**
            Parse specified markup.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void parse(
   String             classname,
   List<StringBuffer> bufs,
   List<String>       javaBlocks,
   String             markup,
   Map<String,String> components,
   TreeLogger         logger)
{
   if (markup != null && markup.trim().length() > 0)
   {
                                       // parse markup with Jsoup             //
      Elements elements = parseDocument(classname, markup, logger);

      handleElement(
         classname, bufs, elements.get(0), javaBlocks, components, logger);
   }
}
/*------------------------------------------------------------------------------

@name       parseAnyTagAttributeValue - parse any tag attribute value
                                                                              */
                                                                             /**
            Parse any tag attribute value.

@return     Iff tag attribute value, parsed value; otherwise, null.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String parseAnyTagAttributeValue(
   String     value,
   TreeLogger logger)
{
   String parsed = null;

   for (String tag : getComponentTags(value))
   {
      String orig = kTAG_BY_REPLACEMENT.get(tag);
      if (orig != null)
      {
         int idx = orig.lastIndexOf('.');
         if (idx > 0)
         {
            orig = orig.substring(idx + 1);
         }

         String createElement =
            "io.reactjava.client.core.react.ElementDsc.createElement("
           + "io.reactjava.client.core.react.ElementDsc.create("
           + "null,\"" + orig + "\", Properties.with(\"id\",getNextId())))";

         parsed =
            value.replace(tag, createElement).replace("<","").replace("/>","");
      }
   }

   return(parsed);
}
/*------------------------------------------------------------------------------

@name       parseCompileTimeDirectives - parse any compile time directives
                                                                              */
                                                                             /**
            Parse any compile time directives.

@return     parsed source with any compile time directives processed.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String parseCompileTimeDirectives(
   String     src,
   TreeLogger logger)
{
   String parsed = "";
   String key;
   String resolved;
   String unresolved;

   for (int idxBeg = 0, idxEnd = 0; idxBeg < src.length(); idxBeg = idxEnd)
   {
      key    = "CompileTime.resolve(";
      idxBeg = src.indexOf(key, idxEnd);
      if (idxBeg < 0)
      {
         parsed += src.substring(idxEnd);
         break;
      }

      parsed += src.substring(idxEnd, idxBeg);

      idxEnd = src.indexOf(')', idxBeg + key.length());
      if (idxEnd < 0)
      {
         throw new IllegalStateException(
            "Compile time directive parenthesis mismatch");
      }

      unresolved = src.substring(idxBeg + key.length(), idxEnd++).trim();

                                       // unwrap from enclosing "             //
      if (!unresolved.startsWith("\"") || !unresolved.endsWith("\""))
      {
         throw new IllegalArgumentException("Argument mus be a string");
      }

      unresolved = unresolved.substring(1, unresolved.length() - 1).trim();
      resolved   = handleTextFromURL(unresolved, logger);
      resolved   =
         resolved.substring(
            kJAVA_STRING_BEG.length(),
            resolved.length() - kJAVA_STRING_END.length());

                                       // escape any double quotes and wrap   //
                                       // in double quotes                    //
      resolved = resolved.replaceAll("\"","\\\\\"");
      resolved = "\"" + resolved + "\"";
      parsed  += resolved;
   }

   return(parsed);
}
/*------------------------------------------------------------------------------

@name       parseContent - parse content
                                                                              */
                                                                             /**
            Parse content.

@return     markup

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String parseContent(
   String       content,
   List<String> javaBlks)
{
   String markup = "";

   String[] sections = content.split(kJSX_REGEXP_BEG);
   for (String section : sections)
   {
      String[] parts = section.split(kJSX_REGEXP_END);
      for (String part : parts)
      {
         if (part.trim().startsWith("<"))
         {
            markup += part;
         }
         else
         {
            javaBlks.add(part);
            markup += kMARKUP_JAVABLOCK_PLACEHOLDER;
         }
      }
   }

   return(markup);
}
/*------------------------------------------------------------------------------

@name       parseCSS - parse markup of specified classname
                                                                              */
                                                                             /**
            Parse markup of specified src.

@return     parsed markup of specified unless no render() method found, in which
            case returns original source.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String parseCSS(
   String     src,
   TreeLogger logger)
{
   String          parsed = src;
   List<MarkupDsc> dscs   = getMethodMarkupDscs(src, "renderCSS", logger);
   if (dscs != null && dscs.size() > 0)
   {
      MarkupDsc first   = dscs.get(0);
      int       idxBeg  = first.idxBeg;
      int       idxEnd  = first.idxEnd;
      String    content = src.substring(idxBeg, idxEnd);
      if (content.trim().length() > 0)
      {
         String css     = generateStyleSource(content, logger);
                parsed  = src.substring(0, idxBeg) + css + src.substring(idxEnd);
      }
   }

   return(parsed);
}
/*------------------------------------------------------------------------------

@name       parseDocument - parse specified markup document
                                                                              */
                                                                             /**
            Parse specified markup document.

@return     markup document elements

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Elements parseDocument(
   String     classname,
   String     markup,
   TreeLogger logger)
{
   markup = replaceComponentTagsWithSymbols(markup, logger);
   markup = parsePropertyReferences(classname, markup);

                                       // using xmlParser instead of          //
                                       // htmlParser requires many standard   //
                                       // html self-closing tags to be        //
                                       // explicitly closed, such as          //
                                       // <br> and <input> and others...      //
   Elements elements =
      Parser.htmlParser().settings(
         new ParseSettings(true, true)).parseInput(markup, "").getAllElements();

   return(elements);
}
/*------------------------------------------------------------------------------

@name       parseElementAttributes - parse tag properties
                                                                              */
                                                                             /**
            Parse specified tag properties.

            For a standard tag, should be of the form:

               "Properties.with("className", "App-title", ...)"

               or,

               "new Properties()"

            For a component tag, should be of the form:



@return     properties string or null iff none.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String parseElementAttributes(
   Element    element,
   TreeLogger logger)
{
   List<Attribute> attributes =
      new ArrayList(element.attributes().asList());

   String idValue = null;
   for (Attribute attribute : attributes)
   {
      if (kATTRIB_KEY_ID.equals(attribute.getKey()))
      {
         idValue = attribute.getValue();
         break;
      }
   }
   if (idValue == null && !getIsReactTagName(element.tag().getName()))
   {
      idValue = "getNextId()";
      attributes.add(new Attribute(kATTRIB_KEY_ID, idValue));
   }

   String properties =
      getTagIsRoot(element.tagName())
         ? "io.reactjava.client.core.react.Properties.with(props, "
         : "io.reactjava.client.core.react.Properties.with(";

   List<Attribute> attributesSansTemplate = new ArrayList<Attribute>();
   String          template               = null;

   for (int iAtt = 0, iAttMax = attributes.size(); iAtt < iAttMax; iAtt++)
   {
      Attribute att = attributes.get(iAtt);
      String    key = att.getKey();

      if (key.startsWith("props"))
      {
         if (template == null)
         {
            template = key;
         }
      }
      else
      {
         attributesSansTemplate.add(att);
      }
   }

   if (template != null)
   {
      template =
         template.split("=")[1]
         .replace("{","")
         .replace("}","")
         .replace("this","properties");

      properties += template + ", ";
   }

   for (int iAtt = 0; iAtt < attributesSansTemplate.size(); iAtt++)
   {
      Attribute att    = attributes.get(iAtt);
      String    key    = att.getKey();
      String    attVal = att.getValue();
      String    value  = attVal;

      if (iAtt > 0)
      {
         properties += ", ";
      }
      if (attVal == null || attVal.length() == 0)
      {
         properties += "\"" + key + "\", true";
      }
      else
      {
         boolean bWrapWithQuotes   = !attVal.startsWith("{");
         boolean bJavaLiteralValue = attVal.startsWith("{") && attVal.endsWith("}");

         key = toReactAttributeName(key);
         if (!"style".equals(key))
         {
            if (bJavaLiteralValue)
            {
                                       // remove leading and trailing braces  //
               value = attVal.substring(1, attVal.length() - 1);

               String text = handleTextFromURL(value, logger);
               if (!value.equals(text))
               {
                                       // escape double quotes                //
                  value = text.replace("\"","\\\"");
                  bWrapWithQuotes = true;
               }
            }
            else
            {
                                       // fixup jsoup self-closing div error  //
               value = attVal.replace("{","").replace("}/","}").replace("}","");
            }

            if (!value.contains("props.get(") && !value.contains("getNextId("))
            {
                                       // check for tag as an attribute value //
               String elementCreate = parseAnyTagAttributeValue(value, logger);
               if (elementCreate != null)
               {
                  value = elementCreate;
               }
               else if (bWrapWithQuotes)
               {
                  value = "\"" + value + "\"";
               }
            }

            logger.log(
               logger.DEBUG,
               "JSXTransform.parseElementAttributes(): "
             + "attVal=" + attVal + ", value=" + value);
         }
         else if (bJavaLiteralValue)
         {
                                       // complete 'style' value is a literal //
            value = value.substring(1, value.length() -1);
         }
         else
         {
                                       // the 'style' value is a map          //
            String[] items = value.replace("\"","").split(";");

            value = "io.reactjava.client.core.react.Properties.with(";
            for (int iItem = 0; iItem < items.length; iItem++)
            {
               String   item   = items[iItem];
               String[] fields = item.split(":");

               logger.log(
                  logger.DEBUG,
                  "JSXTransform.parseElementAttributes(): item=" + item);

               if (iItem > 0)
               {
                  value += ", ";
               }

               String resolvedName  = toReactAttributeName(fields[0]).trim();
               Object resolvedValue =
                  IConfiguration.getPlatform(logger)
                     .resolveCSSPropertyValue(resolvedName, fields[1]);

               String sResolvedValue = resolvedValue.toString();
               if (resolvedValue instanceof String)
               {
                  sResolvedValue = sResolvedValue.trim();
                  if (sResolvedValue.startsWith("{"))
                  {
                     sResolvedValue =
                        sResolvedValue.replace("{", "").replace("}", "");
                  }
                  else
                  {
                     sResolvedValue = "\"" + sResolvedValue + "\"";
                  }
               }

               value += "\"" + resolvedName + "\", " + sResolvedValue;
            }
            value += ")";
         }

         properties += "\"" + key + "\", " + value;
      }
   }

   properties += ")";

   return(properties);
}
/*------------------------------------------------------------------------------

@name       parseMarkup - parse markup of specified classname
                                                                              */
                                                                             /**
            Parse markup of specified src.

@return     parsed markup of specified unless no render() method found, in which
            case returns original source.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String parseMarkup(
   String             classname,
   String             src,
   Map<String,String> components,
   TreeLogger         logger)
{
   String          parsed = src;
   List<MarkupDsc> dscs   = getMethodMarkupDscs(src, "render", logger);
   if (dscs == null)
   {
   }
   else
   {
      MarkupDsc first   = dscs.get(0);
      String    content = src.substring(first.idxBeg, first.idxEnd);
      if (dscs.size() == 1)
      {
         if (!content.contains("setComponentFcn"))
         {
                                       // ensure at least the default         //
                                       // component function                  //
            parsed  = src.substring(0, first.idxBeg);
            parsed += kINLINE_HDR;
            parsed += content;
            parsed += kINLINE_BODY_DEFAULT;
            parsed += kINLINE_FTR;
            parsed += src.substring(first.idxEnd);
         }
         else
         {
                                       // ensure parsed is different from src //
                                       // so classname is marked as component //
            parsed += " ";
         }
      }
      else
      {
         String body = parse(classname, src, dscs, components, logger);
         parsed = generateRenderableSource(classname, src, first, body, logger);
      }
   }

   return(parsed);
}
/*------------------------------------------------------------------------------

@name       parsePropertyReferences - parse property references
                                                                              */
                                                                             /**
            Parse property references in specified string.

@return     parsed

@param      raw      unparsed

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static String parsePropertyReferences(
   String  classname,
   String  raw)
{
   String res = raw;
   if (res == null)
   {
   }
   else
   {
      res = "";
      int idxBeg = 0;
      int idxEnd = 0;
      while(true)
      {
         idxBeg = raw.indexOf('{', idxEnd);
         if (idxBeg < 0)
         {
            break;
         }
                                       // make sure to copy the '{'           //
         res +=
            parsePropertyReferencesMakeSustitutions(
               classname, raw.substring(idxEnd, idxBeg));

                                       // find index of matching curly brace  //
         idxEnd = indexOfMatchingCurlyBrace(raw, idxBeg);
         if (idxEnd < 0)
         {
            throw new  IllegalStateException("Unmatched curly braces in markup");
         }
                                       // make sure to  copy the '}'          //
         res +=
            parsePropertyReferencesMakeSustitutions(
               classname, raw.substring(idxBeg, ++idxEnd));
      }

      res +=
         parsePropertyReferencesMakeSustitutions(
            classname, raw.substring(idxEnd));
   }

   return(res);
}
/*------------------------------------------------------------------------------

@name       parsePropertyReferencesMakeSustitutions - parse property references
                                                                              */
                                                                             /**
            Make substitutions for property references.

@return     parsed

@param      raw      unparsed

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static String parsePropertyReferencesMakeSustitutions(
   String  classname,
   String  raw)
{
   String res = raw;
   if (res == null)
   {
   }
   else
   {
      for (String token : kREPLACE_TOKENS.keySet())
      {
         res = res.replace(token, kREPLACE_TOKENS.get(token));
      }
                                       // replace references to 'this'        //
      String simple = classname.substring(classname.lastIndexOf('.') + 1);
      res = res.replace("this.", simple + ".this.");
   }

   return(res);
}
/*------------------------------------------------------------------------------

@name       pretty - make generated source more readable
                                                                              */
                                                                             /**
            Make generated source more readable.

@return     more readable generated source

@param      generated      generated source

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes      see replaceComponentTagsWithSymbols()
                                                                              */
//------------------------------------------------------------------------------
public static String pretty(
   String generated)
{
   String pretty =
      generated
         .replace(";", ";\n")
         .replace("{", "\n{\n")
         .replace("}", "\n}\n");

   return(pretty);
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
@param      candidatesNew  component and provider candidates noted by compiler
@param      components     components identified by preprocessors
@param      logger         compiler logger

@history    Fri Dec 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public byte[] process(
   String             classname,
   byte[]             contentBytes,
   String             encoding,
   Map<String,String> candidatesNew,
   Map<String,String> components,
   TreeLogger         logger)
   throws             Exception
{
   logger.log(logger.DEBUG, "JSXTransform.process(): for " + classname);

   if (candidates == null)
   {
      candidates = candidatesNew;
   }
   if (components.size() == 0)
   {
      getComponents(candidates, components, logger);
   }

   String parsed = components.get(classname);
   if (parsed != null)
   {
      String contentOrig = candidates.get(classname);
      String content     = new String(contentBytes, encoding);
      if (!content.equals(contentOrig))
      {
         logger.log(
            logger.INFO,
            "JSXTransform.process(): updating " + classname + "."
          + "\nFor better debugging performance, consider supporting "
          + "incremental build...");

         candidates.put(classname, content);

         getComponent(classname, content, components, logger);

         parsed = components.get(classname);
      }
      else
      {
         logger.log(
            logger.INFO, "JSXTransform.process(): using cached " + classname);
      }

      contentBytes = parsed.getBytes(encoding);
   }

   return(contentBytes);
}
/*------------------------------------------------------------------------------

@name       replaceComponentTagsWithSymbols - replace tags with symbols
                                                                              */
                                                                             /**
            Replace component tags with symbols to accommodate Jsoup's
            difficulty in parsing component tags such as
            '@material-ui.core.Button'.

@return     markup with replaced component tag names

@param      markup      original markup

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String replaceComponentTagsWithSymbols(
   String     markup,
   TreeLogger logger)
{
                                       // clear the maps                      //
   kREPLACEMENT_BY_TAG.clear();
   kTAG_BY_REPLACEMENT.clear();
                                       // replace react fragment shorthands   //
   markup = replaceReactFragmentShorthands(markup);

                                       // find all unique tags ordered such   //
                                       // that no tag in the list preceeds    //
                                       // another that contains it as a       //
                                       // substring                           //
   List<String> tags = getComponentTags(markup);
   for (String tag : tags)
   {
      if ("!--".equals(tag))
      {
                                       // skip comments                       //
         continue;
      }
      if (getIsStandardTagName(tag))
      {
                                       // skip standard tags                  //
         continue;
      }
      if (getIsReactTagName(tag))
      {
                                       // replace react tags directly         //
         markup = markup.replace(tag, kREACT_TAGS.get(tag));
         continue;
      }
                                       // note the dependency now if it is a  //
                                       // library tag since a tag may be an   //
                                       // attribute value rather than an      //
                                       // element                             //
      getIsLibraryTagName(tag, logger);

      String replacement = "tag" + kREPLACEMENT_BY_TAG.size();

      kREPLACEMENT_BY_TAG.put(tag, replacement);
      kTAG_BY_REPLACEMENT.put(replacement, tag);

      logger.log(
         logger.DEBUG,
         "JSXTrandform.replaceComponentTagsWithSymbols(): "
       + "replacing " + tag + " with " + replacement);

                                       // make replacements in markup         //
      markup = markup.replace(tag, replacement);
   }
   return(markup);
}
/*------------------------------------------------------------------------------

@name       replaceCurrentBufferWithCompactSingleLine - replace with sinlge line
                                                                              */
                                                                             /**
            Replace the current string buffer with one containing a compact,
            single line.

@return     current string buffer

@param      bufs     string buffer list

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public StringBuffer replaceCurrentBufferWithCompactSingleLine(
   List<StringBuffer> bufs)
{
   StringBuffer replacement =
      new StringBuffer(
         toCompactSingleLine(bufs.remove(bufs.size() - 1).toString()));

   bufs.add(replacement);
   return(replacement);
}
/*------------------------------------------------------------------------------

@name       replaceReactFragmentShorthands - replace react fragment shorthands
                                                                              */
                                                                             /**
            Replace react fragment shorthands with 'React.Fragment'.

@return     markup with replaced fragment shorthands

@param      markup      original markup

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String replaceReactFragmentShorthands(
   String markup)
{
   String shorthandStartTag =
      Utilities.kREGEX_ZERO_OR_MORE_WHITESPACE + "\\Q<\\E"
    + Utilities.kREGEX_ZERO_OR_MORE_WHITESPACE
    + "\\Q>\\E";

   markup = markup.replaceAll(shorthandStartTag, "<React.Fragment>");

   String shorthandEndTag =
      Utilities.kREGEX_ZERO_OR_MORE_WHITESPACE + "\\Q<\\E"
    + Utilities.kREGEX_ZERO_OR_MORE_WHITESPACE + "/"
    + Utilities.kREGEX_ZERO_OR_MORE_WHITESPACE + "\\Q>\\E";

   markup = markup.replaceAll(shorthandEndTag, "</React.Fragment>");

   return(markup);
}
/*------------------------------------------------------------------------------

@name       resolveCSS - resolve css with possible java references
                                                                              */
                                                                             /**
            Resolve css with possible java references.

            This implementation finds all tokens that start with '{',
            end with '}' and contain one or more non-white characters other than
            ':' or ';' and replaces each with the token removing the leading '{'
            and trailing '}'.

            For example,

               String mediaKey   = "min-width";
               String bkptsUp    = "320px";
               int    unit       = 8;
               String cellHeight = "100px";
               String cellWidth  = "100px";
               String background = "rgb(255,255,255)";
               ...

               .cardActions
               {
                  @media ({mediaKey}: {bkptsUp})
                  {
                     paddingBottom: {unit * 2};
                     .board{height: 300px;        width: 300px;}
                     .cell {height: {cellHeight}; width:{cellWidth};}
                  }
               }
               .cardHeader
               {
                  background-color: {background};
               }

            becomes,

               .cardActions
               {
                  @media (min-width: 320px)
                  {
                     paddingBottom: 16;
                     .board{height: 300px;  width: 300px;}
                     .cell {height: 100px; width: 100px;}
                 }
               }
               .cardHeader
               {
                  background-color: rgb(255,255,255);
               }


@return     css with resolved java references.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String resolveCSS(
   String css)
{
                                       // opening brace,                      //
                                       // followed by zero or more whitespace,//
                                       // followed by one or more characters  //
                                       // other than '{' or '}' or ':' or ';' //
                                       // or whitespace,                      //
                                       // followed by zero or more characters //
                                       // other than '{' or '}' or ':' or ';',//
                                       // followed by closing brace           //
   String kREGEX_TOKEN = "\\{\\s*[^{}:;\\s]+[^{}:;]*\\}";

   StringBuffer buf = new StringBuffer();
   Matcher      m   = Pattern.compile(kREGEX_TOKEN).matcher(css);
   while(m.find())
   {
      String token    = m.group();
      int    tokenLen = token.length();
      if (tokenLen > 0)
      {
         String replacement = token.substring(1, tokenLen - 1);
                replacement = "\" + (" + replacement + ") + \"";
         m.appendReplacement(buf, replacement);
      }
   }
   m.appendTail(buf);

   String resolved = buf.toString();
   return(resolved);
}
/*------------------------------------------------------------------------------

@name       resolvePlatformTag - resolve specified tag name for platform
                                                                              */
                                                                             /**
            Resolve specified tag name for platform

@return     The resolved tag name

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String resolvePlatformTag(
   String     tagName,
   boolean    bText,
   IPlatform  platform,
   TreeLogger logger)
{
   String replacement = tagName;
   String resolved    = platform.resolveTag(tagName, bText);
   if (resolved != null)
   {
      replacement = resolved;
   }

   if (logger != null)
   {
      logger.log(
         Type.INFO,
         "JSXTransform.resolvePlatformTag(): "
       + "tagName=" + tagName + ", replacement=" + replacement);
   }

   return(replacement);
}
/*------------------------------------------------------------------------------

@name       resolveTagName - resolve tagName with any replaced value
                                                                              */
                                                                             /**
            Resolve tagName with any replaced value.

@return     markup with replaced component tag names

@param      tagName     possible replacement for original tag

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes      see replaceComponentTagsWithSymbols()
                                                                              */
//------------------------------------------------------------------------------
public static String resolveTagName(
   String tagName)
{
   String original = kTAG_BY_REPLACEMENT.get(tagName);
   return(original != null ? original : tagName);
}
/*------------------------------------------------------------------------------

@name       setPlatform - set platform
                                                                              */
                                                                             /**
            Set platform. This method is for development and test purposes.

@param      os    platform os

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void setPlatform(
   String os)
{
   if (!IPlatform.kPLATFORMS.contains(os))
   {
      throw new IllegalArgumentException(os + " is unknown os");
   }

   //platform =
   //   IPlatform.kPLATFORM_WEB.equals(os)
   //      ? new PlatformWeb()
   //      : IPlatform.kPLATFORM_IOS.equals(os)
   //         ? new PlatformIOS()
   //         : new PlatformAndroid();

   throw new UnsupportedOperationException();
}
/*------------------------------------------------------------------------------

@name       toCompactSingleLine - convert string to compact single line
                                                                              */
                                                                             /**
            Convert specified string to compact, single line.

@return     specified string to compact, single line.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String toCompactSingleLine(
   String raw)
{
   String compact = raw;
   if (kSRCCFG_JSX_IN_SINGLE_LINE)
   {
      //compact =
      //   raw.replaceAll(kREGEX_ONE_OR_MORE_WHITESPACE, " ")
      //      .replace(" = ","=")
      //      .replace("( ","(")
      //      .replace("; ",";")
      //      .replace(", ",",")
      //      .trim();

      StringBuffer buf   = new StringBuffer();
      String[]     parts = compact.split(kJAVA_STRING_END);
      for (String part  : parts)
      {
         int idxBeg = part.indexOf(kJAVA_STRING_BEG);
         if (idxBeg >= 0)
         {
            buf.append(part.substring(0, idxBeg).replace("\n", " "));
            buf.append(part.substring(idxBeg + kJAVA_STRING_BEG.length()));
         }
         else
         {
            buf.append(part.replace("\n", " "));
         }
      }

      compact = buf.toString().trim();
   }

   return(compact);
}
/*------------------------------------------------------------------------------

@name       toReactAttributeName - convert attribute name to react syntax
                                                                              */
                                                                             /**
            Replace all '-x' sequences to 'X'.

@return     The specified attribute name in rect syntax.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes      A regex version of this is needed...
                                                                              */
//------------------------------------------------------------------------------
public String toReactAttributeName(
   String attributeName)
{
   String reactName = "class".equals(attributeName) ? "className" : attributeName;
   String parts[]   = reactName.split("-");
   if (parts.length > 1)
   {
      StringBuffer buf = new StringBuffer(parts[0]);
      for (int i = 1; i < parts.length; i++)
      {
         String part = parts[i];
         buf.append(part.substring(0, 1).toUpperCase() + part.substring(1));
      }
      reactName = buf.toString();
   }

   return(reactName);
}
/*------------------------------------------------------------------------------

@name       trim - trim specified markup
                                                                              */
                                                                             /**
            Trim specified markup.

            Markup can be a mix of java blocks and html sections, where
            java blocks are surrounded by matching curly braces.

            Java sections are not transformed, while html sections are.

@return     void

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String trim(
   String  markup)
{
   String trimmed = markup;
   if (trimmed != null)
   {
      int     idxMarkupBeg = markup.indexOf('<');
      int     idxJavaBeg   = markup.indexOf('{');
      int     idxJava      = idxJavaBeg;
      int     idxJavaEnd   = -1;
      boolean bJavaBlock   = idxJavaBeg >= 0 && idxJavaBeg < idxMarkupBeg;
      int     blockDepth   = bJavaBlock ? 1 : 0;

      while (blockDepth > 0)
      {
         idxJavaEnd = markup.indexOf('}', idxJava);
         if (idxJavaEnd < 0)
         {
            throw new IllegalStateException("Curly braces unmatched");
         }
         int idx = markup.indexOf('{', idxJava + 1);
         if (idx < idxJavaEnd)
         {
            blockDepth++;
            idxJava = idx;
            continue;
         }
         blockDepth--;
         idxJava = idxJavaEnd + 1;
      }

      trimmed = "";
      if (bJavaBlock)
      {
         trimmed +=
            //markup.substring(idxJavaBeg, idxJavaEnd + 1).replace("this","props");
            markup.substring(idxJavaBeg, idxJavaEnd + 1);
      }

      trimmed += trimMarkup(markup.substring(idxJavaEnd + 1));
   }

   return(trimmed);
}
/*------------------------------------------------------------------------------

@name       trimMarkup - trim specified markup
                                                                              */
                                                                             /**
            Trim specified markup.

            Markup can be a mix of java blocks and html sections, where
            java blocks are surrounded by matching curly braces.

            Java sections are not transformed, while html sections are.

@return     void

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String trimMarkup(
   String markup)
{
   markup = markup.replace("\n"," ");
                                       // all whitespace to a single space    //
   Pattern whitespace = Pattern.compile("\\s\\s");
   Matcher matcher    = whitespace.matcher(markup);
   boolean bMatched   = true;
   while(bMatched)
   {
      markup  = matcher.replaceAll(" ");
      matcher = whitespace.matcher(markup);
      if (!matcher.find())
      {
         bMatched = false;
      }
   }
                                       // remove some spaces                  //
   markup =
      markup.replace(" =","=")
      .replace("= ","=")
      .replace(" />","/>")
      .replace(" <!-- javablock -->","<!-- javablock -->")
      .replace("<!-- javablock --> ","<!-- javablock -->");

   return(markup);
}
/*------------------------------------------------------------------------------

@name       unitTest - do unit tests
                                                                              */
                                                                             /**
            Do unit test.

@return     void

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean unitTest(
   int testNum)
{
   String[] expectedResults =
   {
                                       // test 0                              //
      "",
                                       // test 1                              //
      "public void render() { String buttonClass = \"button ionButtonMd ionButtonBlock ionButtonBlockMd ionButton\"; String buttonClassLogin = buttonClass + \"ionButtonLogin\"; java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"io.reactjava.client.components.ionic.IonicScrollContent\"),new io.reactjava.client.components.ionic.IonicScrollContent(Properties.with(\"id\",getNextId())).props());root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"io.reactjava.client.components.ionic.IonicGrid\"),new io.reactjava.client.components.ionic.IonicGrid(Properties.with(\"fixed\",\"true\",\"id\",getNextId())).props());root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"io.reactjava.client.components.ionic.IonicRow\"),new io.reactjava.client.components.ionic.IonicRow(Properties.with(\"id\",getNextId())).props());root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"io.reactjava.client.components.ionic.IonicColumn\"),new io.reactjava.client.components.ionic.IonicColumn(Properties.with(\"id\",getNextId())).props());root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"splashBg\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"splashInfo\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"splashLogo\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"splashIntro\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" Your collection of films and music streamed to you anywhere in the world \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"padding\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"Button\",Properties.with(\"variant\",\"contained\",\"fullWidth\",true,\"onClick\",props.get(\"signUpHandler\"),\"className\",\"button ionButtonMd ionButtonBlock ionButtonBlockMd ionButton\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" Sign Up \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"Button\",Properties.with(\"variant\",\"contained\",\"fullWidth\",true,\"onClick\",props.get(\"signInHandler\"),\"className\",\"button ionButtonMd ionButtonBlock ionButtonBlockMd ionButton ionButtonLogin\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" Sign In \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} } ",
                                       // test 2                              //
      "public void render() { java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"scrollContent\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" {props.getChildren()} \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} } ",
                                       // test 3                              //
      "public void render() { java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"h1\",Properties.with(\"className\",\"hello\",\"style\",Properties.with(\"color\",\"blue\",\"fontFamily\",\"helvetica\"),\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\"Hello&nbsp;World!\");root=root == null ? elem : root;parents.pop();if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} } ",
                                       // test 4                              //
      "public void render() { int a = 0; } ",
                                       // test 5                              //
      "public void render() {java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root = null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"A\"),new A(Properties.with(\"id\", getNextId())).props());root = root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"B\"),new B(Properties.with(\"id\", getNextId())).props());root = root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"B\"),new B(Properties.with(\"id\", getNextId())).props());root = root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\", getNextId()),\" \");root = root == null ? elem : root;parents.pop();} ",
                                       // test 6                              //
      "public void render() { String buttonClass = \"button ionButton ionButtonMd\"; if (props.getBoolean(\"login\")) { buttonClass += \" ionButtonLogin\"; } java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"button\",Properties.with(\"className\",buttonClass,\"onClick\",props.get(\"onClick\"),\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"className\",\"buttonInner\",\"id\",getNextId()),getText());root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"buttonEffect\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} } ",
                                       // test 7                              //
      "public void render() { String imgURL = \"images/logo.png\"; java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"io.reactjava.client.components.ionic.IonicScrollContent\"),new io.reactjava.client.components.ionic.IonicScrollContent(Properties.with(\"fixed\",\"true\",\"id\",getNextId())).props());root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"App\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"header\",Properties.with(\"className\",\"App-header\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"img\",Properties.with(\"src\",imgURL,\"className\",\"App-logo\",\"alt\",\"logo\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"h1\",Properties.with(\"className\",\"App-title\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\"Welcome to ReactJava\");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"p\",Properties.with(\"className\",\"App-intro\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" To get started,edit \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"code\",Properties.with(\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),getText());root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" and \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"strong\",Properties.with(\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\"save\");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" to reload. \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} } ",
                                       // test 8                              //
      "public void render() { java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"board\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root; for (int i = 0; i < 9; i++) { elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"io.reactjava.client.examples.tictactoe.SubBoardView\"),new io.reactjava.client.examples.tictactoe.SubBoardView(Properties.with(\"board\",props.get(App.kKEY_BOARD),\"subBoardIndex\",i,\"key\",i,\"moveFcn\",props.get(App.kKEY_MOVE_FCN),\"id\",getNextId())).props());root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root; } elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} } ",
                                       // test 9                              //
      "public void render() { java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"div0\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div00\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div01\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div010\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div0100\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div0101\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div02\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div020\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div03\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div030\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} parents.pop();} ",
                                       // test 10                             //
      "public void render() { java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"main-div\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"h1\",Properties.with(\"className\",\"hello\",\"style\",Properties.with(\"color\",\"white\"),\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\"Let's get setup\");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"h2\",Properties.with(\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\"Sign in now so we can access your music\");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"br\",Properties.with(\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"input\",Properties.with(\"type\",\"email\",\"className\",\"form-input\",\"placeholder\",\"Email\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"br\",Properties.with(\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"input\",Properties.with(\"type\",\"password\",\"className\",\"form-input\",\"placeholder\",\"Password\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"signin-button\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\"Sign In\");root=root == null ? elem : root;parents.pop();parents.pop();if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} } ",
                                       // test 11                             //
      "",
                                       // test 12                             //
      "",
                                       // test 13                             //
      "",
                                       // test 14                             //
      "",
                                       // test 15                             //
      "",
                                       // test 16                             //
      "",
                                       // test 17                             //
      "",
                                       // test 18                             //
      "",
                                       // test 19                             //
      "",
   };

   boolean bRetVal = true;

   try
   {
      Map<String,String> components = null;
      String             javaBlock  = null;
      String             classname  = "Component";
      String             header     = "public void render()\n{\n";
      String             footer     = "\n}\n";
      String             src        = null;
      String             content    = null;
      TreeLogger         logger     = new PrintWriterTreeLogger();
      boolean            bCapturing = false;

      switch(testNum)
      {
         case 0:
         {
                                       // run unit tests                      //
            for (int iTest = 1; bRetVal && iTest < expectedResults.length; iTest++)
            {
               String expectedResult = expectedResults[iTest];
               if (!bCapturing && expectedResult.length() == 0)
               {
                  continue;
               }
               try
               {
                  bRetVal = unitTest(iTest);
               }
               catch(Throwable t)
               {
                  t.printStackTrace();
                  bRetVal = false;
               }
            }
            break;
         }
         case 1:
         {
            components = new HashMap<String,String>()
            {{
               put("IonicScrollContent", "io.reactjava.client.components.ionic.IonicScrollContent");
               put("IonicGrid",          "io.reactjava.client.components.ionic.IonicGrid");
               put("IonicRow",           "io.reactjava.client.components.ionic.IonicRow");
               put("IonicColumn",        "io.reactjava.client.components.ionic.IonicColumn");
            }};

            javaBlock =
               "\n\n"
             + "   String buttonClass = \"button ionButtonMd ionButtonBlock ionButtonBlockMd ionButton\";\n"
             + "   String buttonClassLogin = buttonClass + \"ionButtonLogin\";\n"
             + "\n\n";

            content =
               header
             + javaBlock
             + "\n"
             + "/*--\n"
             + "<IonicScrollContent>\n"
             + "  <IonicGrid fixed='true'>\n"
             + "     <IonicRow>\n"
             + "        <IonicColumn>\n"
             + "           <div className='splashBg' />\n"
             + "           <div className='splashInfo'>\n"
             + "              <div className='splashLogo' />\n"
             + "              <div className='splashIntro'>\n"
             + "                 Your collection of films and music streamed to you\n"
             + "                 anywhere in the world\n"
             + "              </div>\n"
             + "           </div>\n"
             + "           <div className='padding'>\n"
             + "              <@material-ui.core.Button\n"
             + "                 variant='contained' fullWidth\n"
             + "                 onClick=props().get(\"signUpHandler\")\n"
             + "                 className='button ionButtonMd ionButtonBlock ionButtonBlockMd ionButton'\n"
             + "              >\n"
             + "                 Sign Up\n"
             + "              </@material-ui.core.Button>\n"
             + "              <@material-ui.core.Button\n"
             + "                 variant='contained' fullWidth\n"
             + "                 onClick=props().get(\"signInHandler\")\n"
             + "                 className='button ionButtonMd ionButtonBlock ionButtonBlockMd ionButton ionButtonLogin'\n"
             + "              >\n"
             + "                 Sign In\n"
             + "              </@material-ui.core.Button>\n"
             + "           </div>\n"
             + "        </IonicColumn>\n"
             + "     </IonicRow>\n"
             + "  </IonicGrid>\n"
             + "</IonicScrollContent>\n"
             + "--*/\n"
             + footer;
            break;
         }
         case 2:
         {
            content =
               header
             + "\n"
             + "/*--\n"
             + "   <div class='scrollContent'>\n"
             + "      {getChildren()}\n"
             + "   </div>\n"
             + "--*/\n"
             + footer;
            break;
         }
         case 3:
         {
            content =
               header
             + "\n"
             + "/*--\n"
             + "<h1 class='hello' style='color:blue;font-family:helvetica;'>Hello&nbsp;World!</h1>"
             + "--*/\n"
             + footer;
            break;
         }
         case 4:
         {
            content = header + "int a = 0;\n" + footer;
            break;
         }
         case 5:
         {
            components = new HashMap<String,String>()
            {{
               put("A", "A");
               put("B", "B");
            }};
            content = header + "/*--\n<A><B /></A>--*/" + footer;
            break;
         }
         case 6:
         {
            javaBlock =
               "\n\n\n\n"
             + "String buttonClass = \"button ionButton ionButtonMd\";\n"
             + "if (props().getBoolean(\"login\"))\n"
             + "{\n"
             + "   buttonClass += \" ionButtonLogin\";\n"
             + "}\n"
             + "\n\n\n\n";

            content =
               header
             + javaBlock
             + "/*--\n"
             + "\n\n\n\n"
             + "<button class={buttonClass} onClick=props().get(\"onClick\")>\n"
             + "  <span class='buttonInner'>{getText()}</span>\n"
             + "  <div class='buttonEffect'/>\n"
             + "</button>\n"
             + "--*/\n"
             + footer;
            break;
         }
         case 7:
         {
            components = new HashMap<String,String>()
            {{
               put("IonicScrollContent", "io.reactjava.client.components.ionic.IonicScrollContent");
               put("IonicGrid",          "io.reactjava.client.components.ionic.IonicGrid");
               put("IonicRow",           "io.reactjava.client.components.ionic.IonicRow");
               put("IonicColumn",        "io.reactjava.client.components.ionic.IonicColumn");
            }};
            javaBlock =
               "\n\n\n\n"
             + "String imgURL = \"images/logo.png\";"
             + "\n\n";

            content =
               header
             + javaBlock
             + "/*--\n"
             + "<IonicScrollContent fixed='true'>\n"
             + "   <div class=\"App\">\n"
            + "      <header class=\"App-header\">\n"
             + "         <img src={imgURL} class=\"App-logo\" alt=\"logo\" />\n"
             + "         <h1 class=\"App-title\">Welcome to ReactJava</h1>\n"
             + "      </header>\n"
             + "      <p class=\"App-intro\">\n"
             + "         To get started, edit <code>{getText()}</code> and\n"
             + "         <strong>save</strong> to reload.\n"
             + "      </p>\n"
             + "   </div>\n"
             + "</IonicScrollContent>\n"
             + "--*/\n"
             + footer;
            break;
         }
         case 8:
         {
            components = new HashMap<String,String>()
            {{
               put("SubBoardView", "io.reactjava.client.examples.tictactoe.SubBoardView");
            }};

            classname = "io.reactjava.client.examples.tictactoe.SubBoardView";
            content =
               header
             + "/*--  <div class='board'> --*/\n"
             + "for (int i = 0; i < 9; i++)\n"
             + "{\n"
             + "   /*--\n"
             + "   <SubBoardView\n"
             + "      board={props().get(App.kKEY_BOARD)}\n"
             + "      subBoardIndex={i}\n"
             + "      key={i}\n"
             + "      moveFcn={props().get(App.kKEY_MOVE_FCN)}\n"
             + "   >\n"
             + "   </SubBoardView>\n"
             + "   --*/\n"
             + "}\n"
             + "/*-- </div> --*/\n"
             + footer;
           break;
         }
         case 9:
         {
            components = new HashMap<String,String>()
            {{
               put("ExampleComponent", "io.reactjava.client.examples.ExampleComponent");
            }};

            classname = "io.reactjava.client.examples.ExampleComponent";
            content =
               header
             + "/*--\n"
             + "<div class='div0' >\n"
             + "   <div class='div00' />\n"
             + "   <div class='div01'>\n"
             + "      <div class='div010'>\n"
             + "         <div class='div0100'/>\n"
             + "         <div class='div0101'/>\n"
             + "      </div>\n"
             + "   </div>\n"
             + "   <div class='div02'>\n"
             + "      <div class='div020'>\n"
             + "   </div>\n"
             + "   <div class='div03'>\n"
             + "      <div class='div030'/>\n"
             + "   </div>\n"
             + "</div>\n"
             + "--*/\n"
             + footer;
            break;
         }
         case 10:
         {
            components = new HashMap<String,String>()
            {{
               put("App", "io.reactjava.client.examples.helloworld.App");
            }};

            classname = "io.reactjava.client.examples.helloworld.App";
            content =
               header
             + "/*--\n"
             + "<div class='main-div'>\n"
             + "<h1 class='hello' style='color:white;'>Let's get setup</h1>\n"
             + "<h2>Sign in now so we can access your music</h2><br>\n"
             + "<input type='email' class='form-input' placeholder='Email' >\n"
             + "<br>\n"
             + "<input type='password' class='form-input' placeholder='Password' >\n"
             + "<div class='signin-button' >Sign In </div>\n"
             + "</div>\n"
             + "--*/\n"
             + footer;
           break;
         }
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         {
            components = new HashMap<String,String>()
            {{
               put("IonicScrollContent", "io.reactjava.client.components.ionic.IonicScrollContent");
               put("IonicGrid",          "io.reactjava.client.components.ionic.IonicGrid");
               put("IonicRow",           "io.reactjava.client.components.ionic.IonicRow");
               put("IonicColumn",        "io.reactjava.client.components.ionic.IonicColumn");
               put("IonicButton",        "io.reactjava.client.components.ionic.IonicButton");
            }};

            switch(testNum)
            {
               case 11:
               {
                  classname  = "io.reactjava.client.examples.titles.Welcome";
                  src =
                     IJSXTransform.getFileAsString(
                        new File(
                           "/Users/brianm/working/IdeaProjects/ReactJava/"
                         + "TitlesReactJava/src/io/reactjava/client/"
                         + "examples/titles/Welcome.java"),
                        null);
                  content = src;
                  break;
               }
               case 12:
               {
                  classname  = "io.reactjava.client.examples.tictactoe.Modal";
                  src =
                     IJSXTransform.getFileAsString(
                        new File(
                           "/Users/brianm/working/IdeaProjects/ReactJava/"
                         + "TicTacToeReactJava/src/io/reactjava/client/"
                         + "examples/tictactoe/Modal.java"),
                        null);
                  break;
               }
               case 13:
               {
                  classname  = "io.reactjava.client.components.ionic.IonicButton";
                  src =
                     IJSXTransform.getFileAsString(
                        new File(
                           "/Users/brianm/working/IdeaProjects/ReactJava/"
                         + "TitlesReactJava/src/io/reactjava/client/"
                         + "components/ionic/IonicButton.java"),
                        null);
                  break;
               }
               case 14:
               {
                  components.put(
                     "BoardView",
                     "io.reactjava.client.examples.tictactoe.BoardView");

                  components.put(
                     "Modal",
                     "io.reactjava.client.examples.tictactoe.Modal");

                  classname  = "io.reactjava.client.examples.tictactoe.App";
                  src =
                     IJSXTransform.getFileAsString(
                        new File(
                           "/Users/brianm/working/io/reactjava/client/"
                         + "examples/tictactoe/App.java"),
                        null);
                  break;
               }
               case 15:
               {
                  components.put(
                     "BoardView",
                     "io.reactjava.client.examples.tictactoe.BoardView");

                  components.put(
                     "Modal",
                     "io.reactjava.client.examples.tictactoe.Modal");

                  classname  = "io.reactjava.client.examples.tictactoe.App";
                  src =
                     IJSXTransform.getFileAsString(
                        new File(
                           "/Users/brianm/working/io/reactjava/client/"
                         + "examples/tictactoe/CellView.java"),
                        null);
                  break;
               }
               case 16:
               {
                  if (true)
                  {
                     classname  = "io.reactjava.client.examples.helloworld.App";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "ReactJavaExamples/src/"
                            + "io/reactjava/client/examples/helloworld/App.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     classname  = "io.reactjava.client.examples.simple.App";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "SimpleReactJava/src/"
                            + "io/reactjava/client/examples/simple/App.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     components.put(
                        "BoardView",
                        "io.reactjava.client.examples.tictactoe.BoardView");

                     components.put(
                        "CellView",
                        "io.reactjava.client.examples.tictactoe.CellView");

                     components.put(
                        "Modal",
                        "io.reactjava.client.examples.tictactoe.Modal");

                     components.put(
                        "SubBoardView",
                        "io.reactjava.client.examples.tictactoe.SubBoardView");

                     classname  = "io.reactjava.client.examples.tictactoe.SubBoardView";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "ReactJavaExamples/src/"
                            + "io/reactjava/client/examples/tictactoe/App.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     components.put(
                        "CellView",
                        "io.reactjava.client.examples.tictactoe.CellView");

                     classname  = "io.reactjava.client.examples.tictactoe.SubBoardView";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "TicTacToeReactJava/src/"
                            + "io/reactjava/client/examples/tictactoe/SubBoardView.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     classname  = "io.reactjava.client.examples.titles.Welcome";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "TitlesReactJava/src/"
                            + "io/reactjava/client/examples/titles/Welcome.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     classname  = "io.reactjava.client.examples.materialui.pricing.App";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "ReactJavaExamples/src/"
                            + "io/reactjava/client/examples/materialui/pricing/App.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     components.put(
                        "ContentBody",
                        "io.reactjava.client.examples.materialui.generalpage.ContentBody");
                     components.put(
                        "ContentCaption",
                        "io.reactjava.client.examples.materialui.generalpage.ContentCaption");
                     components.put(
                        "ContentCode",
                        "io.reactjava.client.examples.materialui.generalpage.ContentCode");
                     components.put(
                        "ContentImage",
                        "io.reactjava.client.examples.materialui.generalpage.ContentImage");
                     components.put(
                        "ContentTitle",
                        "io.reactjava.client.examples.materialui.generalpage.ContentTitle");

                     classname  = "io.reactjava.client.examples.materialui.generalpage.ContentPage";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "ReactJavaExamples/src/"
                            + "io/reactjava/client/examples/materialui/generalpage/ContentPage.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     classname  = "io.reactjava.client.examples.materialui.generalpage.ContentTitle";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "ReactJavaExamples/src/"
                            + "io/reactjava/client/examples/materialui/generalpage/ContentTitle.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     classname  =
                        "io.reactjava.client.components.generalpage.ContentBody";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "ReactJava/src/"
                            + "io/reactjava/client/components/generalpage/ContentBody.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     components.put(
                        "ContentBody",
                        "io.reactjava.client.components.generalpage.ContentBody");
                     components.put(
                        "ContentCaption",
                        "io.reactjava.client.components.generalpage.ContentCaption");
                     components.put(
                        "ContentCode",
                        "io.reactjava.client.components.generalpage.ContentCode");
                     components.put(
                        "ContentImage",
                        "io.reactjava.client.components.generalpage.ContentImage");
                     components.put(
                        "ContentPage",
                        "io.reactjava.client.components.generalpage.ContentPage");
                     components.put(
                        "ContentTitle",
                        "io.reactjava.client.components.generalpage.ContentTitle");
                     components.put(
                        "Footer",
                        "io.reactjava.client.components.generalpage.Footer");
                     components.put(
                        "GeneralAppBar",
                        "io.reactjava.client.components.generalpage.GeneralAppBar");
                     components.put(
                        "SideDrawer",
                        "io.reactjava.client.components.generalpage.SideDrawer");

                     classname  = "io.reactjava.client.components.generalpage.GeneralPage";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "ReactJava/src/"
                            + "io/reactjava/client/components/generalpage/GeneralPage.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     classname  = "io.reactjava.client.components.generalpage.Prism";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "ReactJava/src/"
                            + "io/reactjava/client/components/generalpage/Prism.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     classname  = "io.reactjava.client.components.generalpage.ContentCaption";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "ReactJava/src/"
                            + "io/reactjava/client/components/generalpage/ContentCaption.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     classname  = "io.reactjava.client.components.generalpage.GeneralAppBar";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "ReactJava/src/"
                            + "io/reactjava/client/components/generalpage/GeneralAppBar.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     components.put(
                        "ContentBody",
                        "io.reactjava.client.components.generalpage.ContentBody");
                     components.put(
                        "ContentCaption",
                        "io.reactjava.client.components.generalpage.ContentCaption");
                     components.put(
                        "ContentCode",
                        "io.reactjava.client.components.generalpage.ContentCode");
                     components.put(
                        "ContentImage",
                        "io.reactjava.client.components.generalpage.ContentImage");
                     components.put(
                        "ContentPage",
                        "io.reactjava.client.components.generalpage.ContentPage");
                     components.put(
                        "ContentTitle",
                        "io.reactjava.client.components.generalpage.ContentTitle");
                     components.put(
                        "Footer",
                        "io.reactjava.client.components.generalpage.Footer");
                     components.put(
                        "GeneralAppBar",
                        "io.reactjava.client.components.generalpage.GeneralAppBar");
                     components.put(
                        "SideDrawer",
                        "io.reactjava.client.components.generalpage.SideDrawer");

                     classname  = "platformswebsite.general.LandingPage";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "PlatformsWebsite/src/"
                            + "platformswebsite/general/LandingPage.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     components.put(
                        "GeneralPage",
                        "io.reactjava.client.components.generalpage.GeneralPage");

                     components.put(
                        "LandingPage",
                        "com.giavaneers.web.platformswebsite.general.LandingPage");

                     classname = "com.giavaneers.web.platformswebsite.general.AppBase";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "PlatformsWebsite/src/"
                            + "com/giavaneers/web/platformswebsite/general/AppBase.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     components.put(
                        "Prism",
                        "io.reactjava.client.components.generalpage.Prism");

                     classname  = "io.reactjava.client.components.generalpage.App";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "ReactJavaExamples/src/"
                            + "io/reactjava/client/examples/displaycode/App.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     classname  = "io.reactjava.client.examples.threebythree.state.SquareByRenderCSS";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "ReactJavaExamples/src/"
                            + "io/reactjava/client/examples/threebythree/state/SquareByRenderCSS.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     components.put(
                        "Board",
                        "io.reactjava.client.examples.threebythree.progress.Board");

                     classname  = "io.reactjava.client.examples.threebythree.progress.App";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "ReactJavaExamples/src/"
                            + "io/reactjava/client/examples/threebythree/progress/App.java"),
                           null);
                      content = src;
                  }
                  else if (false)
                  {
                     classname  = "landingpage.App";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "LandingPage/src/"
                            + "landingpage/App.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     classname  = "io.reactjava.client.examples.statevariable.simple.App";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "ReactJavaExamples/src/"
                            + "io/reactjava/client/examples/statevariable/simple/App.java"),
                           null);
                     content = src;
                  }
                  else if (false)
                  {
                     classname  = "io.reactjava.client.examples.seo.ViewA";
                     src =
                        IJSXTransform.getFileAsString(
                           new File(
                              "/Users/brianm/working/IdeaProjects/ReactJava/"
                            + "ReactJavaExamples/src/"
                            + "io/reactjava/client/examples/seo/ViewA.java"),
                           null);
                     content = src;
                  }
                  break;
               }
            }
            break;
         }
         case 17:
         {
                                       // this case simply parsed markup      //
            if (false)
            {
               src =
                  "<!-- javablock -->\n"
                + "<div class=\"container\">\n"
                + " <div class=\"board\">\n"
                + "<!-- javablock -->\n"
                + "  <div class=\"cell\" style=\"background-color:{this.getColor(i)}\" onClick=\"{this.clickHandler}\" />\n"
                + "<!-- javablock -->\n"
                + " </div> \n"
                + " <ul>\n"
                + "<!-- javablock -->\n"
                + "  <li key=\"{sKey}\">{color}</li>\n"
                + "<!-- javablock -->\n"
                + " </ul> \n"
                + "</div>\n"
                + "<!-- javablock -->\n";
            }
            else
            {
               src =
                  "<React.Fragment>\n"
                + " <h1>Hello</h1>\n"
                + " <p>world!</p>\n"
                + "</React.Fragment>\n";
            }
            break;
         }
         case 18:
         {
                                       // this case simply parsed markup      //
            components = new HashMap<>();
            File test =
               new File(
                  IConfiguration.getProjectDirectory("resources", null),
                  "tests/JSXTest.html");

            src = IJSXTransform.getFileAsString(test, null);
            break;
         }
         case 19:
         {
                                       // react fragment                      //
            classname = "reactjavawebsite.App";
            content   =
               header
             + "/*--\n"
             + "<React.Fragment>\n"
             + " <h1>Hello</h1>\n"
             + " <p>world!</p>\n"
             + "</React.Fragment>\n"
             + "--*/\n"
             + footer;
            break;
         }
         case 20:
         {
                                       // react fragment shorthand syntax     //
            classname = "reactjavawebsite.App";
            content   =
               header
             + "/*--\n"
             + "<>\n"
             + " <h1>Hello</h1>\n"
             + " <p>world!</p>\n"
             + "</>\n"
             //+ "<@material-ui.core.Button\n"
             //+ "    class='button'\n"
             //+ "    variant='contained'\n"
             //+ "    fullWidth={true}\n"
             //+ "    onClick={this.buttonClickHandler}>\n"
             //+ "    Change Colors\n"
             //+ "</@material-ui.core.Button>\n"
             + "--*/\n"
             + footer;
            break;
         }
      }

      switch(testNum)
      {
         case 0:
         {
            break;
         }
         case 17:
         case 18:
         {
                                       // these cases simply parse markup     //
            System.out.println(parseDocument(classname, src, logger).get(0).toString());
            break;
         }
         default:
         {
            String generated =
               new JSXTransform().parse(classname, content, components, logger);

            String pretty = JSXTransform.pretty(generated);

                                       // assign second arg 'true' when       //
                                       // capturing test result and 'false'   //
                                       // when running test                   //
            String normalized = normalizeParsed(generated, bCapturing);

            bRetVal =
               bCapturing ? true : expectedResults[testNum].equals(normalized);

            System.out.println("Unit test " + testNum + " -> "
               + (bRetVal ? "SUCCESS!" : "ERROR"));
         }
      }

      //System.out.println(generateInjectScript(null).getAbsolutePath());
   }
   catch(Exception e)
   {
      e.printStackTrace();
      bRetVal = false;
   }
   return(bRetVal);
}
/*------------------------------------------------------------------------------

@name       writeIndent - output indent
                                                                              */
                                                                             /**
            Output indent.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void write(
   StringBuffer buf,
   String       str)
{
   buf.append(str);
}
/*------------------------------------------------------------------------------

@name       writeJavaBlock - output java block
                                                                              */
                                                                             /**
            Output java block.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void writeJavaBlock(
   String             classname,
   List<StringBuffer> bufs,
   String             javaBlock)
{
   StringBuffer buf = getCurrentBuffer(bufs);
   if (javaBlock != null)
   {
      write(buf, parsePropertyReferencesMakeSustitutions(classname, javaBlock));
   }
}
/*------------------------------------------------------------------------------

@name       writeNewline - output indent
                                                                              */
                                                                             /**
            Output indent.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void writeNewline(
   StringBuffer buf)
{
   if (!kSRCCFG_JSX_IN_SINGLE_LINE)
   {
      write(buf, "\n" + new String(kSPACES, 0, indent));
   }
}
/*==============================================================================

name:       MarkupDsc - markup descriptor

purpose:    Markup descriptor

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class MarkupDsc
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public int     idxBeg;                 // beginning index                     //
public int     idxEnd;                 // end index                           //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       MarkupDsc - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public MarkupDsc(
   int idxBeg,
   int idxEnd)
{
   this.idxBeg  = idxBeg;
   this.idxEnd  = idxEnd;
}
}//====================================// end MarkupDsc ======================//
}//====================================// end JSXTransform ===================//
