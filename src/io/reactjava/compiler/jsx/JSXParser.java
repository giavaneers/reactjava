/*==============================================================================

name:       JSXParser - app dependencies parser

purpose:    App dependencies parser

history:    Mon Sep 07, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                     COPYRIGHT (c) BY GIAVANEERS, INC.
      This source code is licensed under the MIT license found in the
          LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.compiler.jsx;
                                       // imports --------------------------- //
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.google.gwt.core.ext.TreeLogger;
import io.reactjava.client.core.react.AppComponentTemplate;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.Utilities;
import io.reactjava.compiler.codegenerator.IPreprocessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Element;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
                                       // JSXParser ==========================//
public class JSXParser implements IPreprocessor
{
                                       // constants ------------------------- //
public static final int     kTAB_SPACES     = 3;
public static final String  kJSX_MARKUP_BEG = "/*--";
public static final String  kJSX_MARKUP_END = "--*/";
public static final String  kJSX_REGEXP_BEG = "/\\*--";
public static final String  kJSX_REGEXP_END = "--\\*/";

public static final String kMARKUP_JAVABLOCK_PLACEHOLDER =
   "<!-- javablock -->";
                                       // map of replacement tokens           //
public static final Map<String,String> kREPLACE_TOKENS =
   new HashMap<String,String>()
   {{
      put("props()",       "props");
      put("getChildren()", "props.getChildren()");
   }};
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       generateTypesMap - get map of all types
                                                                              */
                                                                             /**
            Get map of all types for the specified providers and component
            candidates.

@param      providerAndComponentCandidates   providers and component candidates
@param      logger                           logger

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static void generateTypesMap(
   String             classname,
   byte[]             contentBytes,
   String             encoding,
   Map<String,String> providerAndComponentCandidates,
   boolean            bUpdate,
   TreeLogger logger)
   throws             Exception
{
   List<TypeDsc> added     = new ArrayList<>();
   long          parseTime = 0;

   if (!bUpdate)
   {
                                       // generate the types map sans supers  //
      for (String tClassname : providerAndComponentCandidates.keySet())
      {
         logger.log(
            logger.INFO, "JSXParser.generateTypesMap(): parsing " + tClassname);

         long            start = System.currentTimeMillis();
         String          src   = providerAndComponentCandidates.get(tClassname);
         CompilationUnit cu    = StaticJavaParser.parse(src);

                                          // setup lexical preserving printing   //
         LexicalPreservingPrinter.setup(cu);

         parseTime += System.currentTimeMillis() - start;

         generateTypesMap(tClassname, src, cu, added);
      }
   }
   else
   {
      logger.log(
         logger.INFO, "JSXParser.generateTypesMap(): updating " + classname);

      TypeDsc.removeType(TypeDsc.forClassname(classname));

      String contents = new String(contentBytes, encoding);

      CompilationUnit cu = StaticJavaParser.parse(contents);

                                          // setup lexical preserving printing   //
      LexicalPreservingPrinter.setup(cu);

      generateTypesMap(classname, contents, cu, added);
   }
                                       // now that all the candidates have    //
                                       // been parsed assign the supertypes   //
   for (TypeDsc typeDsc : TypeDsc.getTypes())
   {
      typeDsc.getSuperType();
   }
                                       // initialize the types map elements   //
                                       // now that all the candidates have    //
                                       // been parsed and supers assigned     //
   for (TypeDsc typeDsc : TypeDsc.getTypes())
   {
      if (!TypeDsc.kROOT_APP_COMPONENT_TEMPLATE_INSTANCE.equals(typeDsc)
            && !TypeDsc.kROOT_COMPONENT_INSTANCE.equals(typeDsc)
            && !TypeDsc.kROOT_PROVIDER_INSTANCE.equals(typeDsc))
      {
         typeDsc.initialize();
      }
   }

   logger.log(
      logger.INFO,
      "JSXParser.generateTypesMap(): total parse time=" + parseTime);
}
/*------------------------------------------------------------------------------

@name       generateTypesMap - get map of all types for the specified cu
                                                                              */
                                                                             /**
            Get map of all types for the specified compilation unit. This is
            a map of TypeDeclaration by classname for all top and inner classes
            in the compilation unit.

@param      cu    compilation unit

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static List<TypeDsc> generateTypesMap(
   String          classname,
   String          source,
   CompilationUnit cu,
   List<TypeDsc>   added)
{
   for (TypeDeclaration type : cu.getTypes())
   {
      if (type instanceof ClassOrInterfaceDeclaration)
      {
         generateTypesMap(
            classname, (ClassOrInterfaceDeclaration)type, source, cu, added);
      }
   }
   return(added);
}
/*------------------------------------------------------------------------------

@name       generateTypesMap - get map of all types for the specified type
                                                                              */
                                                                             /**
            Get map of all types for the specified type. This is
            a map of TypeDeclaration by classname for the specified type
            and all its inner classes.

@param      type     target type declaration

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static void generateTypesMap(
   String                      classname,
   ClassOrInterfaceDeclaration type,
   String                      source,
   CompilationUnit             cu,
   List<TypeDsc>               added)
{
                                       // create a type descriptor and put it //
                                       // in the cache                        //
   new TypeDsc(classname, type, source, cu);
                                       // chase any inner classes             //
   for (Node child : type.getChildNodes())
   {
      if (child instanceof ClassOrInterfaceDeclaration)
      {
         ClassOrInterfaceDeclaration declaration =
            (ClassOrInterfaceDeclaration)child;

         String innerClassname = classname + "." + declaration.getNameAsString();

         generateTypesMap(innerClassname, declaration, source, cu, added);
      }
   }
}
/*------------------------------------------------------------------------------

@name       getComponentMarkup - get any target app markup
                                                                              */
                                                                             /**
            Get any target app markup.

@return     target app markup or null if none found.

@param      component      component

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static String getComponentMarkup(
   TypeDsc component)
{
   //StringBuffer buf = new StringBuffer();
   //                                    // remove all but markup               //
   //for (int idxBeg = 0, idxEnd = 0; true;)
   //{
   //   idxBeg = body.indexOf("/*--", idxEnd);
   //   if (idxBeg < 0)
   //   {
   //      break;
   //   }
   //
   //   idxBeg += 4;
   //   idxEnd = body.indexOf("--*/", idxBeg);
   //   if (idxEnd < 0)
   //   {
   //      throw new IllegalStateException("Unmatched markup delimiters");
   //   }
   //
   //   buf.append(body, idxBeg, idxEnd);
   //}
   //
   //String markup = buf.length() > 0 ? buf.toString() : null;
   //if (markup == null)
   //{
   //   logger.log(
   //      logger.INFO,
   //      "JSXParser.getComponentMarkup(): no markup found");
   //}
   //else
   //{
   //   logger.log(
   //      logger.INFO,
   //      "JSXParser.getComponentMarkup(): markup=" + markup);
   //}
   //
   //return(markup);

   String markup           = null;
   String renderMethodBody = component.getComponentRenderMethodBody(true);
   if (renderMethodBody != null)
   {
      markup = parseContent(component, renderMethodBody);
   }
   return(markup);
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

@name       getDependencyTagsFromMarkup - get the target app dependencies
                                                                              */
                                                                             /**
            Get the target app dependencies.

@return     target app dependencies.

@param      markup      component markup
@param      logger      logger

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static Set<String> getDependencyTagsFromMarkup(
   TypeDsc    component,
   String     markup,
   TreeLogger logger)
{
   Set<String> dependencies = new HashSet<>();
   if (markup != null)
   {
      markup = replaceComponentTagsWithSymbols(component, markup, logger);
      markup = parsePropertyReferences(component, markup);

      component.markup = markup;
                                       // using xmlParser instead of          //
                                       // htmlParser requires many standard   //
                                       // html self-closing tags to be        //
                                       // explicitly closed, such as          //
                                       // <br> and <input> and others...      //
      component.markupElements =
         Parser.htmlParser().settings(
            new ParseSettings(true, true))
               .parseInput(markup, "")
               .getAllElements();

      for (Element element : component.markupElements)
      {
         getDependenciesFromMarkupElement(component, element, dependencies, logger);
      }

      Set<String> resolved = new HashSet<>();
      for (String dependency : dependencies)
      {
         resolved.add(component.resolveTagNameIfReplaced(dependency));
      }
      dependencies = resolved;
   }

   return(dependencies);
}
/*------------------------------------------------------------------------------

@name       getDependenciesFromMarkupElement - get the target app dependencies
                                                                              */
                                                                             /**
            Get the target app dependencies.

@param      element           element
@param      dependencies      dependencies
@param      logger            logger

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void getDependenciesFromMarkupElement(
   TypeDsc     component,
   Element     element,
   Set<String> dependencies,
   TreeLogger  logger)
{
   String replacementTagName = element.tagName();
   if (!TypeDsc.isStandardTagName(replacementTagName))
   {
      dependencies.add(replacementTagName);

      String tagName = component.resolveReplacementTagName(replacementTagName);
      if (TypeDsc.forTag(tagName) == null)
      {
                                       // create a native type descriptor     //
         String javascriptPath =
            IConfiguration.getNodeModuleJavascript(tagName, logger);

         if (javascriptPath != null)
         {
            new TypeDsc(tagName, javascriptPath);
         }
      }
   }
}
/*------------------------------------------------------------------------------

@name        getSimpleClassname - get  simple classname for type declaration
                                                                              */
                                                                             /**
            Get simple classname for specified type declaration.

@return     simple classname for specified type declaration

@param      type     type declaration

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static String getSimpleClassname(
   TypeDeclaration type)
{
   String simple;
   if (type.equals(TypeDsc.kROOT_APP_COMPONENT_TEMPLATE))
   {
      simple = AppComponentTemplate.class.getSimpleName();
   }
   else if (type.equals(TypeDsc.kROOT_COMPONENT))
   {
      simple = Component.class.getSimpleName();
   }
   //else if (type.equals(ProviderDsc.kROOT_IPROVIDER))
   //{
   //   simple = IProvider.class.getSimpleName();
   //}
   else
   {
      simple = type.getNameAsString();
   }
   return(simple);
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

@name       parseAppDependencies - create app dependencies graph
                                                                              */
                                                                             /**
            Creates a component dependencies graph for the app by parsing its
            markup.

@param      logger      logger

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void parseAppDependencies(
   TreeLogger logger)
{
   logger.log(
      logger.INFO, "JSXParser.parseAppDependencies(): entered");
   long start = System.currentTimeMillis();

   TypeDsc.initializeForUpdates();

   parseAppDependencies(TypeDsc.getTargetApp(logger), logger);

   String msg = "\n";
   for (TypeDsc dsc : TypeDsc.getAppDependenciesByTag().values())
   {
      msg += (dsc.classname != null ? dsc.classname : dsc.tag) + "\n";
   }
   msg  = "JSXParser.parseAppDependencies(): exiting with dependencies:" + msg;
   msg += "elapsed msec=" + (System.currentTimeMillis() - start);

   logger.log( logger.INFO, msg);
}
/*------------------------------------------------------------------------------

@name       parseAppDependencies - create app dependencies graph
                                                                              */
                                                                             /**
            Creates a component dependencies graph for the app by parsing its
            markup.

@param      logger      logger

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void parseAppDependencies(
   TypeDsc    component,
   TreeLogger logger)
{
   if (component != null
         && !TypeDsc.isComponentAppDependency(component)
         && !Component.class.getName().equals(component.classname)
         && !AppComponentTemplate.class.getName().equals(component.classname))
   {
      logger.log(
         logger.INFO,
         "JSXParser.parseAppDependencies(): adding "
       + (component.classname != null ? component.classname : component.tag)
       + " which is " + (component.isComponent() ? "" : "not ") + "a component");

                                       // add the dependency                  //
      TypeDsc.getAppDependenciesByTag().put(component.tag, component);
      if (component.classname != null)
      {
         TypeDsc.getAppDependenciesByClassname().put(
            component.classname, component);
      }

      if (component.isComponent())
      {
                                       // parse any component info            //
         parseComponentInfo(component, logger);

                                       // parse any component imports         //
         parseComponentImports(component, logger);

                                       // parse any component superclass      //
         parseComponentSuperclass(component, logger);

                                       // parse any component references to   //
                                       // classes of the same package         //
         parseComponentClassReferences(component, logger);

                                       // parse any component markup          //
         parseComponentMarkup(component, logger);
      }
   }
}
/*------------------------------------------------------------------------------

@name       parseComponentClassReferences - parse package references
                                                                              */
                                                                             /**
            Parse any component references to classes.

@param      component   component
@param      logger      logger

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void parseComponentClassReferences(
   TypeDsc    component,
   TreeLogger logger)
{
   //                                    // only field declarations for now...  //
   //for (FieldDeclaration field : component.type.getFields())
   //{
   //   for (VariableDeclarator variable : field.getVariables())
   //   {
   //      if ("Class".equals(variable.getType().asString()))
   //      {
   //         if (variable.getInitializer().isPresent())
   //         {
   //                                    // example: 'Chat.class'               //
   //            String initializer = variable.getInitializer().get().toString();
   //            if (initializer.endsWith(".class"))
   //            {
   //               String  = initializer.substring(0, initializer.length()-6);
   //               parseAppDependencies(TypeDsc.forTag(tag), logger);
   //            }
   //         }
   //      }
   //   }
   //}

   String source = component.source;
   if (source != null)
   {
                                       // match all words ending in '.class'  //
      String  regex   = "\\b(\\w+(\\.class))";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(source);

      while(matcher.find())
      {
         String match = source.substring(matcher.start(), matcher.end());
         String tag   = match.substring(0, match.length() - 6);
         parseAppDependencies(TypeDsc.forTag(tag), logger);
      }
   }
}
/*------------------------------------------------------------------------------

@name       parseComponentImports - parse any component imports
                                                                              */
                                                                             /**
            Parse any component imports.

@param      component   component
@param      logger      logger

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void parseComponentImports(
   TypeDsc    component,
   TreeLogger logger)
{
   component.getComponentReactiveXImports();

   for (String imported : component.getComponentImports())
   {
      TypeDsc candidate = TypeDsc.forClassname(imported);
      if (candidate != null)
      {
         if (TypeDsc.getAppDependenciesByTag().get(candidate.tag) != null)
         {
                                    // already processed this one          //
            continue;
         }
         if (candidate.isComponent())
         {
                                    // chase the dependency's dependencies //
            parseAppDependencies(candidate, logger);
         }
      }
   }
}
/*------------------------------------------------------------------------------

@name       parseComponentInfo - parse any component info
                                                                              */
                                                                             /**
            Parse any component info.

@param      component   component
@param      logger      logger

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void parseComponentInfo(
   TypeDsc    component,
   TreeLogger logger)
{
   component.getComponentImportedNodeModules(logger);
   component.getComponentSEOInfo(logger);
}
/*------------------------------------------------------------------------------

@name       parseComponentMarkup - parse any component markup
                                                                              */
                                                                             /**
            Parse any component markup.

@param      component      target component
@param      logger         logger

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void parseComponentMarkup(
   TypeDsc    component,
   TreeLogger logger)
{
   String markup = getComponentMarkup(component);
   if (markup != null)
   {
      for (String tag : getDependencyTagsFromMarkup(component,markup,logger))
      {
         if (TypeDsc.getAppDependenciesByTag().get(tag) != null)
         {
                                 // already processed this one          //
            continue;
         }

         TypeDsc dependency = TypeDsc.forTag(tag);
         if (dependency != null)
         {
            if (dependency.isNativeComponent() || dependency.isComponent())
            {
                                 // chase the dependency's dependencies //
               parseAppDependencies(dependency, logger);
            }
         }
      }
   }
}
/*------------------------------------------------------------------------------

@name       parseComponentSuperclass - parse any component superclass
                                                                              */
                                                                             /**
            Parse any component superclass.

@param      component   component
@param      logger      logger

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void parseComponentSuperclass(
   TypeDsc    component,
   TreeLogger logger)
{
                                    // chase the supertype's dependencies     //
    parseAppDependencies(component.getSuperType(), logger);
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
public static String parseContent(
   TypeDsc component,
   String  content)
{
   List<String> javaBlocks = component.getJavaBlocks();
   String       markup     = "";

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
            javaBlocks.add(part);
            markup += kMARKUP_JAVABLOCK_PLACEHOLDER;
         }
      }
   }

   return(markup);
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

@param      classname      classname
@param      raw            unparsed

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

@name       process - process specified source
                                                                              */
                                                                             /**
            Process specified source.

@return     processed source

@param      classname         classname to be processed
@param      contentBytes      content to be processed
@param      encoding          content encoding

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
                                       // only runs once on initial build and //
                                       // then again for each updated source  //
                                       // on subsequent incremental builds    //
   boolean bInitial = TypeDsc.getTypes().size() == TypeDsc.kNUM_BUILTIN_TYPES;
   if (bInitial || bUpdate)
   {
      logger.log(
         logger.INFO,
         "JSXParser.process(): entered for "
            + (bUpdate ? "for incremental update of " : "") + classname);

      long start = System.currentTimeMillis();

                                       // generate types map for all the      //
                                       // provider and component candidates   //
      generateTypesMap(
         classname, contentBytes, encoding, providerAndComponentCandidates,
         bUpdate, logger);

      logger.log(
         logger.INFO,
         "JSXParser.process(): generateTypesMap elapsed msec="
            + (System.currentTimeMillis() - start));

                                       // parse app dependencies              //
      parseAppDependencies(logger);

      logger.log(
         logger.INFO,
         "JSXParser.process(): exiting:"
       + " elapsed msec=" + (System.currentTimeMillis() - start));
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
   TypeDsc    component,
   String     markup,
   TreeLogger logger)
{
                                       // clear the maps                      //
   //kREPLACEMENT_BY_TAG.clear();
   //kTAG_BY_REPLACEMENT.clear();
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
      if (TypeDsc.isStandardTagName(tag))
      {
                                       // skip standard tags                  //
         continue;
      }
      if (TypeDsc.isReactTagName(tag))
      {
                                       // replace react tags directly         //
         markup = markup.replace(tag, TypeDsc.kREACT_TAGS.get(tag));
         continue;
      }
                                       // note the dependency now if it is a  //
                                       // native component tag since a tag    //
                                       // may be an attribute value rather    //
                                       // than an element                     //
//importedNodeModulesStart//
//    isLibraryTagName(tag, logger);

      String replacement = component.bindTagNameWithReplacement(tag);

      //logger.log(
      //   logger.DEBUG,
      //   "JSXParser.replaceComponentTagsWithSymbols(): "
      // + "replacing " + tag + " with " + replacement);

                                       // make replacements in markup         //
      markup = markup.replace(tag, replacement);
   }
   return(markup);
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
}//====================================// end JSXParser ======================//
