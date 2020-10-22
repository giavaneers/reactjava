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
package io.reactjava.compiler.jsx;
                                       // imports --------------------------- //
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import io.reactjava.client.providers.platform.IPlatform;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.Utilities;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
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
 + "io.reactjava.client.core.react.Properties.with("
 + "\"id\", getNextId()));"
 + "root = root == null ? elem : root;";

public static final String kINLINE_FTR =
   "if(root != null){element="
 + "io.reactjava.client.core.react.ElementDsc.createElement(root, this);}"
 + "return(element);};"
 + "setComponentFcn(fcn);";

public static final String kINLINE_FTR_CSS =
   "if(css.length() > 0){this.css = css;}";

public static final String kINLINE_HDR =
   "java.util.function.Function"
 + "<io.reactjava.client.core.react.Properties,"
 + "io.reactjava.client.core.react.ReactElement> fcn="
 + "(props) ->{"
 + "String rjcomponentid = props.getString(\"id\");"
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
                                       // class variables ------------------- //
                                       // map of superset component source by //
                                       // classname                           //
protected static Map<String,String>
                             components;
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

@name       generateRenderableSource - generate renderable source
                                                                              */
                                                                             /**
            Get unique component tags.

@return     markup with replaced component tag names

@param      body        parsed method body
@param      logger      logger

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String generateRenderableSource(
   String     body,
   TreeLogger logger)
{
   String renderable = kINLINE_HDR + body + kINLINE_FTR;

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

@name       getComponentForTagName - get component for specified tag name
                                                                              */
                                                                             /**
            Get component for the specified tagName used by the specified
            reference component.

            If the tagName is the simple classname of an import statement,
            the component classname is the classname of the import statement
            (which may or may not exist in the 'parsedComponents' map).

            Otherwise, if the tagName is a fully qualified classname, the
            component classname is the tagName (which may or may not exist in
            the 'parsedComponents' map).

            Otherwise, if the tagName is a simple classname or a partially
            qualified classname, the component classname is found by an
            iterative search building a candidate qualified classname from the
            reference classname, substituting the reference simple classname
            with the tagName, iteratively working backwards along the classname
            path until finding an entry in the 'parsedComponents' map or
            reaching the reference package name.

            Otherwise, if the tagName is a simple classname or a partially
            qualified classname, the component classname is assumed to be a
            relative inner classname and is found by appending the tagName to
            the reference classname and checking for an entry in the
            'parsedComponents' map.

            For example, to map the component for tagName 'D' for the reference
            component with classname 'packagename.A.B.C'

            try 1 -  check for an import whose path ends in '.D'
            try 2 -  check the 'parsedComponents' map for 'D'
                     (in case 'C' is a fully qualified name)
            try 3 -  check the 'parsedComponents' map for 'packagename.A.B.D'
            try 4 -  check the 'parsedComponents' map for 'packagename.A.D'
            try 5 -  check the 'parsedComponents' map for 'packagename.D'
            try 6 -

            For another example, to map the component for tagName 'A.D' for the
            reference component with classname 'packagename.A.B.C'

            try 1 -  check for an import whose path ends in '.A.D'
            try 2 -  check the 'parsedComponents' map for 'A.D'
                     (in case 'A.D' is a fully qualified name)
            try 3 -  check the 'parsedComponents' map for 'packagename.A.B.A.D'
            try 4 -  check the 'parsedComponents' map for 'packagename.A.A.D'
            try 5 -  check the 'parsedComponents' map for 'packagename.A.D'


@param      tagName     target tag name
@param      refType     typeDsc of component using the tag name
@param      logger      compiler logger

@history    Mon Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
//public TypeDsc getComponentForTagName(
//   String      tagName,
//   TypeDsc     refType,
//   TreeLogger  logger)
//{
//   String  refClassname = refType.getClassname();
//   TypeDsc component;
//                                       // check if tag is the simple classname//
//                                       // of an import statement              //
//   if (refType == null)
//   {
//      throw new IllegalStateException(
//         "Reference type cannot be found for " + refClassname);
//   }
//
//   Map<String,TypeDsc> components  = IPreprocessor.getParsedComponents(logger);
//
//   component = refType.getImportForSimpleName(tagName, logger);
//   if (component != null && !components.values().contains(component))
//   {
//      throw new IllegalStateException(
//         "Type is not a known component: " + component);
//   }
//   if (component == null)
//   {
//                                       // check if tag is a fully qualified   //
//                                       // component name                      //
//      component = IPreprocessor.getParsedComponents(logger).get(tagName);
//   }
//   if (component == null)
//   {
//      String packageName =
//         refType.cu.getPackageDeclaration().get().getName().asString();
//
//                                       // search building a candidate         //
//                                       // qualified classname from the        //
//                                       // reference classname, substituting   //
//                                       // the reference simple classname with //
//                                       // the tagName, iteratively working    //
//                                       // backwards along the classname path  //
//                                       // until finding an entry in the       //
//                                       // 'parsedComponents' map or reaching  //
//                                       // the reference package name          //
//      String prefix = refClassname;
//      while (component == null)
//      {
//         int idx = prefix.lastIndexOf('.');
//         if (idx < 0)
//         {
//            break;
//         }
//
//         prefix = prefix.substring(0, idx);
//         if (prefix.length() < packageName.length())
//         {
//            break;
//         }
//
//         String chase = prefix + "." + tagName;
//         component    = IPreprocessor.getParsedComponents(logger).get(chase);
//      }
//   }
//   if (component == null)
//   {
//                                       // assuming the tagName to be a        //
//                                       // relative inner classname, try       //
//                                       // appending the tagName to reference  //
//                                       // classname and checking for an entry //
//                                       // in the 'parsedComponents' map       //
//      String chase = refClassname + "." + tagName;
//      component = IPreprocessor.getParsedComponents(logger).get(chase);
//   }
//
//   return(component);
//}
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
//public static List<MarkupDsc> getMethodMarkupDscs(
//   String      src,
//   String      methodName,
//   TreeLogger  logger)
//{
//   logger.log(
//      logger.DEBUG,
//      "JSXTransform.getMethodMarkupDscs(): entered for methodName=" + methodName);
//
//                                       // locate any specified method         //
//   List<MarkupDsc> markupDscs = null;
//   String          content    = null;
//   String          regex      =
//      Utilities.kREGEX_ONE_OR_MORE_WHITESPACE  + methodName
//    + Utilities.kREGEX_ZERO_OR_MORE_WHITESPACE + "\\Q(\\E"
//    + Utilities.kREGEX_ZERO_OR_MORE_WHITESPACE + "\\Q)\\E"
//    + Utilities.kREGEX_ZERO_OR_MORE_WHITESPACE + "\\Q{\\E";
//
//   int idxContentBeg = -1;
//   int idxContent    = -1;
//
//   String[] splits = src.split(regex);
//   if (splits.length == 2)
//   {
//                                       // specified method found              //
//      idxContent    = src.indexOf(splits[1]);
//      idxContentBeg = idxContent;
//
//      for (int depth = 0; depth >= 0;)
//      {
//                                       // find next close brace               //
//         int idxBraceClose = src.indexOf("}", idxContent + 1);
//
//                                       // find next open brace                //
//         int idxBraceOpen = src.indexOf("{", idxContent + 1);
//         if (idxBraceOpen >= 0 && idxBraceOpen < idxBraceClose)
//         {
//            idxContent = idxBraceOpen;
//            depth++;
//            continue;
//         }
//         idxContent = idxBraceClose;
//         if (idxContent >= 0)
//         {
//            depth--;
//         }
//
//         content = src.substring(idxContentBeg, idxContent);
//      }
//   }
//
//   if (content != null)
//   {
//                                       // the first dsc is the entire content //
//      markupDscs = new ArrayList<>();
//      markupDscs.add(new MarkupDsc(idxContentBeg, idxContent));
//
//                                       // any additional are for each section //
//      for (int idxBeg = 0, idxEnd = idxBeg, idxMax = content.length(); true;)
//      {
//         idxBeg = content.indexOf(kJSX_MARKUP_BEG, idxEnd);
//         if (idxBeg < 0 || idxBeg > idxMax)
//         {
//            break;
//         }
//         idxEnd = content.indexOf(kJSX_MARKUP_END, idxBeg);
//         idxEnd = content.indexOf("\n", idxEnd) + 1;
//         if (idxEnd < 0 || idxEnd > idxMax || idxEnd < idxBeg)
//         {
//            throw new IllegalStateException(
//               "Unmatched markup delimiters.\n" + content);
//         }
//                                       // subsequent descriptors are the      //
//                                       // number of lines of each markup      //
//                                       // section                             //
//         String[] lines = content.substring(idxBeg, idxEnd).split("\n");
//         markupDscs.add(new MarkupDsc(lines.length, 0));
//      }
//   }
//
//   return(markupDscs);
//}
/*------------------------------------------------------------------------------

@name       getMethodMarkupDscs - get method markup dscs from source
                                                                              */
                                                                             /**
            Get method markup descriptors from source.

@return     method markup descriptors from source.

@param      content        method body
@param      logger         logger

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static List<MarkupDsc> getMethodMarkupDscs(
   String     content,
   TreeLogger logger)
{
   logger.log(logger.DEBUG, "JSXTransform.getMethodMarkupDscs(): entered");

   List<MarkupDsc> markupDscs = null;
   if (content != null)
   {
                                       // the first dsc is the entire content //
      markupDscs = new ArrayList<>();
      markupDscs.add(new MarkupDsc(0, content.length()));

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

@name       handleElement - handle element
                                                                              */
                                                                             /**
            SElement handler.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void handleElement(
   TypeDsc            component,
   List<StringBuffer> bufs,
   Element            element,
   List<String>       javaBlocks,
   TreeLogger         logger)
{
   boolean bFinished = false;
   boolean bPushed   = false;
   String tagName   = component.resolveTagNameIfReplaced(element.tagName());
   boolean bBodyPart =
      !(element instanceof Document)
         && !"html".equals(tagName)
         && !"head".equals(tagName)
         && !"body".equals(tagName);

   if (bBodyPart)
   {
      String tagProperties = parseElementAttributes(component, element, logger);

      StringBuffer buf = getCurrentBuffer(bufs);
      if (tagRoot == null)
      {
         tagRoot = tagName;
      }

      write(buf, kPARSED_MARKUP_SECTION_BEGINNING);
      write(buf, "parents.size() > 0 ? parents.peek() : null,");
      writeNewline(buf);

      boolean bStdTagName   = TypeDsc.isStandardTagName(tagName);
      boolean bReactTagName = TypeDsc.isReactTagName(tagName);
      boolean bLibTagName   =
         !bStdTagName
            && !bReactTagName
            && TypeDsc.isNativeComponentTagName(tagName, logger);

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
         TypeDsc tagComponent = TypeDsc.forTag(tagName);
         if (tagComponent == null)
         {
            throw new IllegalStateException(
               tagName + " is neither a standard tag name "
             + "nor a known component simple class name "
             + "nor a known library component class name. "
             + "Are you sure you have included all source paths "
             + "in your module xml file?");
         }

         String componentClassname = tagComponent.classname;

         //LBM-START 200810
         //write(
         //   buf,
         //   "io.reactjava.client.core.react.ReactJava.getNativeFunctionalComponent(\""
         // + componentClassname
         // + "\"),");
         //
         //writeNewline(buf);
         //
         //write(
         //   buf,
         //   "new "
         // + componentClassname
         // + "().initializeInternal(" + tagProperties + ")");

         write(buf, "\"" + componentClassname + "\",");
         writeNewline(buf);
         write(buf, tagProperties);
         //LBM-END 200810
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

         handleElement(component, bufs, (Element)child, javaBlocks, logger);
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
            writeJavaBlock(component, bufs, javaBlocks.remove(0));

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
   boolean      bSpanParent      = "span".equals(parentName);
   boolean      bOptionParent    = "option".equals(parentName);
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
   else if (!bSpanParent && !bOptionParent && !bPrism && !bReactNativeText)
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

@name       parse - parse specified component
                                                                              */
                                                                             /**
            Parse the specified component in the working content.

@return     possibly modified content

@param      component      component
@param      logger         logger

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected String parse(
   TypeDsc    component,
   TreeLogger logger)
{
   if (component.isRenderableComponent())
   {
      parseMarkup(component, logger);
      parseCSS(component, logger);
   }
                                       // handle any inner class dependencies //
   for (BodyDeclaration member : component.type.getMembers())
   {
      if (member.isClassOrInterfaceDeclaration())
      {
         String innerClassname =
            component.classname + "." +
               member.asClassOrInterfaceDeclaration().getName().asString();

         if (TypeDsc.isComponentAppDependencyClassname(innerClassname))
         {
            parse(TypeDsc.forClassname(innerClassname), logger);
         }
      }
   }

   String modifiedContent = LexicalPreservingPrinter.print(component.cu);
          modifiedContent = parseCompileTimeDirectives(modifiedContent, logger);

   return(modifiedContent);
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
//public String parse(
//   String             classname,
//   String             src,
//   Map<String,String> components,
//   TreeLogger         logger)
//{
//   String parsed = null;//parseMarkup(classname, src, components, logger);
//          parsed = parseCSS(parsed, logger);
//          parsed = parseCompileTimeDirectives(parsed, logger);
//
//   return(parsed);
//}
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
   TypeDsc            component,
   List<StringBuffer> bufs,
   TreeLogger         logger)
{
   if (component.markup != null && component.markup.trim().length() > 0)
   {
      Element      element    = component.getComponentMarkupElements().get(0);
      List<String> javaBlocks = new ArrayList<>(component.getJavaBlocks());
      handleElement(component, bufs, element, javaBlocks, logger);
   }
}
/*------------------------------------------------------------------------------

@name       parseAnyTagAttributeValue - parse any tag attribute value
                                                                              */
                                                                             /**
            Parse any tag attribute value.

@return     Iff tag attribute value, parsed value; otherwise, null.

@param      tagAttributeValue    tag attribute value

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String parseAnyTagAttributeValue(
   TypeDsc component,
   String  tagAttributeValue)
{
   String parsed = null;

   for (String tag : JSXParser.getComponentTags(tagAttributeValue))
   {
      String orig = component.tagByReplacementMap.get(tag);
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
            tagAttributeValue.replace(tag, createElement)
               .replace("<","").replace("/>","");
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
//public String parseCSS(
//   String     src,
//   TreeLogger logger)
//{
//   String          parsed = src;
//   List<MarkupDsc> dscs   = getMethodMarkupDscs(src, "renderCSS", logger);
//   if (dscs != null && dscs.size() > 0)
//   {
//      MarkupDsc first   = dscs.get(0);
//      int       idxBeg  = first.idxBeg;
//      int       idxEnd  = first.idxEnd;
//      String    content = src.substring(idxBeg, idxEnd);
//      if (content.trim().length() > 0)
//      {
//         String css     = generateStyleSource(content, logger);
//                parsed  = src.substring(0, idxBeg) + css + src.substring(idxEnd);
//      }
//   }
//
//   return(parsed);
//}
/*------------------------------------------------------------------------------

@name       parseCSS - parse markup of specified classname
                                                                              */
                                                                             /**
            Parse markup of specified src.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void parseCSS(
   TypeDsc    component,
   TreeLogger logger)
{
   logger.log(logger.INFO, "JSXTransform.parseCSS(): entered");

   MethodDeclaration method = component.getComponentRenderCSSProcedure(true);
   if (method != null)
   {
                                       // get the renderCSS method body       //
      String body = LexicalPreservingPrinter.print(method.getBody().get());

                                       // between the brackets                //
      body = body.substring(1, body.length() - 1);

      List<MarkupDsc> dscs = getMethodMarkupDscs(body, logger);
      if (dscs != null && dscs.size() > 0)
      {
         String content = body;
         if (content.trim().length() > 0)
         {
            String parsed = generateStyleSource(content, logger);
            if (parsed.length() > 1)
            {
                                       // update the method body              //
               parsed = "{" + parsed + "}";

               BlockStmt block = StaticJavaParser.parseBlock(parsed);
               LexicalPreservingPrinter.setup(block);
               method.setBody(block);
            }
         }
      }
   }
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
   TypeDsc    component,
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

   if (idValue == null && !TypeDsc.isReactTagName(element.tag().getName()))
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

      if (iAtt == 0)
      {
                                       // add the componentId as an attribute //
         properties += "\"rjcomponentid\", rjcomponentid, ";
      }
      if (iAtt > 0)
      {
         properties += ", ";
      }
      if (attVal == null || attVal.length() == 0)
      {
                                       // make the value a string instead of  //
                                       // boolean as React workaround (see    //
                                       // "https://github.com/"               //
                                       // "styled-components/"                //
                                       // "styled-components/issues/1198"     //
//       properties += "\"" + key + "\", \"true\"";
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
               else
               {
                  String trimmed = value.trim();
                  if ("true".equals(trimmed) || "false".equals(trimmed))
                  {

                                       // make the value a string instead of  //
                                       // boolean as React workaround (see    //
                                       // "https://github.com/"               //
                                       // "styled-components/issues/1198"     //
                     //value           = trimmed;
                     //bWrapWithQuotes = true;
                  }
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
               String elementCreate = parseAnyTagAttributeValue(component, value);
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

               //logger.log(
               //   logger.DEBUG,
               //   "JSXTransform.parseElementAttributes(): item=" + item);

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

   logger.log(
      logger.DEBUG,
      "JSXTransform.parseElementAttributes(): properties=" + properties);

   return(properties);
}
/*------------------------------------------------------------------------------

@name       parseMarkup - parse markup of specified classname
                                                                              */
                                                                             /**
            Parse markup of specified src.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void parseMarkup(
   TypeDsc    component,
   TreeLogger logger)
{
   logger.log(logger.INFO, "JSXTransform.parseMarkup(): entered");

   String body = component.getComponentRenderMethodBody();
   if (body != null && !body.contains("setComponentFcn"))
   {
      List<MarkupDsc> dscs = getMethodMarkupDscs(body, logger);
      if (dscs != null)
      {
         if (dscs.size() == 1)
         {
                                       // ensure at least the default         //
                                       // component function                  //
            body += kINLINE_BODY_DEFAULT;
         }
         else
         {
            body = parseMarkup(component, dscs, logger);
         }

         updateRenderProcedureBody(component, body, logger);
      }
   }
}
/*------------------------------------------------------------------------------

@name       parseMarkup - parse specified markup
                                                                              */
                                                                             /**
            Parse specified markup.

@return     parsed markup

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String parseMarkup(
   TypeDsc            component,
   List<MarkupDsc>    markupDscs,
   TreeLogger         logger)
{
   MarkupDsc          first = markupDscs.remove(0);
   List<StringBuffer> bufs  = new ArrayList<>();

   parse(component, bufs, logger);

   boolean      bAnyMarkup = false;
   Boolean      bMarkup    = null;
   StringBuffer full       = new StringBuffer();
   for (StringBuffer chase : bufs)
   {
      String sChase = chase.toString();
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
         "JSXTransform.parseMarkup(): no markup found for "
            + component.classname);
   }

   return(full.toString());
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
   TypeDsc component,
   String  raw)
{
   String classname = component.classname;
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
            parsePropertyReferencesMakeSubstitutions(
               classname, raw.substring(idxEnd, idxBeg));

                                       // find index of matching curly brace  //
         idxEnd = indexOfMatchingCurlyBrace(raw, idxBeg);
         if (idxEnd < 0)
         {
            throw new  IllegalStateException("Unmatched curly braces in markup");
         }
                                       // make sure to  copy the '}'          //
         res +=
            parsePropertyReferencesMakeSubstitutions(
               classname, raw.substring(idxBeg, ++idxEnd));
      }

      res +=
         parsePropertyReferencesMakeSubstitutions(
            classname, raw.substring(idxEnd));
   }

   return(res);
}
/*------------------------------------------------------------------------------

@name       parsePropertyReferencesMakeSubstitutions - parse property references
                                                                              */
                                                                             /**
            Make substitutions for property references.

@return     parsed

@param      raw      unparsed

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static String parsePropertyReferencesMakeSubstitutions(
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
         .replace(";",    ";\n")
         .replace("{",    "\n{\n")
         .replace("}",    "\n}\n")
         .replace("\n\n", "\n");

   return(pretty);
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

@param      providerAndComponentCandidates
                           provider and component candidates from compiler,
                           where each corresponds to a single source file which
                           may or may not have a number of inner classes that
                           may or may not be additional components or providers

@param      bUpdate        iff true, this is an update of an incremental build
@param      logger         compiler logger

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public byte[] process(
   String             classname,
   byte[]             contentBytes,
   String             encoding,
   Map<String,String> providerAndComponentCandidates,
   boolean            bUpdate,
   TreeLogger         logger)
   throws             Exception
{
   if (TypeDsc.isComponentAppDependencyClassname(classname))
   {
      TypeDsc component = TypeDsc.forClassname(classname);
      logger.log(logger.INFO, "JSXTransform.process(): for " + classname);

      //logger.log(
      //   logger.DEBUG,
      //   "JSXTransform.process(): content=" + new String(contentBytes, "UTF-8"));

      long start = System.currentTimeMillis();

                                       // parse and modify any render() method//
                                       // returning the modified content      //
      String updated = parse(component, logger);
      if (!updated.equals(component.source))
      {
         contentBytes = updated.getBytes(encoding);

         logger.log(
            logger.INFO,
            "JSXTransform.process(): updated source for " + classname
          + "=\n" + pretty(updated));
      }
      else
      {
         logger.log(
            logger.INFO, "JSXTransform.process(): using cached source for "
               + classname);
      }

      logger.log(
         logger.INFO,
         "JSXTransform.process(): elapsed msec="
            + (System.currentTimeMillis() - start));
   }

   return(contentBytes);
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

@name       updateRenderProcedureBody - update any render procedure body
                                                                              */
                                                                             /**
            Update any render procedure body.

@param      component      component
@param      body           modified body
@param      logger         logger

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void updateRenderProcedureBody(
   TypeDsc    component,
   String     body,
   TreeLogger logger)
{
   long      start   = System.currentTimeMillis();
   String    updated = "{" + generateRenderableSource(body, logger) + "}";
   BlockStmt block   = StaticJavaParser.parseBlock(updated);
   LexicalPreservingPrinter.setup(block);
   component.getComponentRenderMethod().setBody(block);

   //logger.log(
   //   logger.DEBUG,
   //   "JSXTransform.updateRenderProcedureBody(): "
   // + "component=" + component.type.getFullyQualifiedName()
   // + "generated=" + pretty("\nrender()\n" + block));

   logger.log(
      logger.INFO,
      "JSXTransform.updateRenderProcedureBody(): elapsed msec="
         + (System.currentTimeMillis() - start));
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
   TypeDsc            component,
   List<StringBuffer> bufs,
   String             javaBlock)
{
   StringBuffer buf = getCurrentBuffer(bufs);
   if (javaBlock != null)
   {
      write(
         buf,
         parsePropertyReferencesMakeSubstitutions(
            component.classname, javaBlock));
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
