/*==============================================================================

name:       TypeDsc.java

purpose:    App dependendency info for native React native components,
            ReactJava components and ReactJava providers.

history:    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.compiler.jsx;
                                       // imports --------------------------- //
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.google.gwt.core.ext.TreeLogger;
import io.reactjava.client.core.react.AppComponentTemplate;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.Provider;
import io.reactjava.client.core.react.SEOInfo;
import io.reactjava.client.core.react.Utilities;
import io.reactjava.client.core.react.Utilities.HashList;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jsoup.select.Elements;
                                       // TypeDsc ============================//
public class TypeDsc
{
                                       // constants ------------------------- //
public static final Map<String,String> kREACT_TAGS =
   new HashMap<String,String>()
   {{
      put("React.Fragment", "Fragment");
      put("react.fragment", "Fragment");
      put("Fragment",       "Fragment");
   }};

public static final Map<String,String> kSTD_TAGS =
   new HashMap<String,String>()
   {{
      put("a",         "a");
      put("abbr",      "abbr");
      put("acronym",   "acronym");
      put("app",       "app");
      put("applet",    "applet");
      put("area",      "area");
      put("article",   "article");
      put("aside",     "aside");
      put("audio",     "audio");
      put("b",         "b");
      put("base",      "base");
      put("basefont",  "basefont");
      put("bdi",       "bdi");
      put("bdo",       "bdo");
      put("bgsound",   "bgsound");
      put("big",       "big");
      put("blink",     "blink");
      put("blockquote","blockquote");
      put("body",      "body");
      put("br",        "br");
      put("button",    "button");
      put("canvas",    "canvas");
      put("caption",   "caption");
      put("center",    "center");
      put("cite",      "cite");
      put("code",      "code");
      put("col",       "col");
      put("colgroup",  "colgroup");
      put("comment",   "comment");
      put("datalist",  "datalist");
      put("dd",        "dd");
      put("del",       "del");
      put("details",   "details");
      put("dfn",       "dfn");
      put("dialog",    "dialog");
      put("dir",       "dir");
      put("div",       "div");
      put("dl",        "dl");
      put("dt",        "dt");
      put("em",        "em");
      put("embed",     "embed");
      put("fieldset",  "fieldset");
      put("figcaption","figcaption");
      put("figure",    "figure");
      put("font",      "font");
      put("footer",    "footer");
      put("form",      "form");
      put("fragment",  "fragment");
      put("frame",     "frame");
      put("frameset",  "frameset");
      put("head",      "head");
      put("header",    "header");
      put("h1",        "h1");
      put("h2",        "h2");
      put("h3",        "h3");
      put("h4",        "h4");
      put("h5",        "h5");
      put("h6",        "h6");
      put("hr",        "hr");
      put("html",      "html");
      put("hype",      "hype");
      put("i",         "i");
      put("iframe",    "iframe");
      put("img",       "img");
      put("input",     "input");
      put("ins",       "ins");
      put("isindex",   "isindex");
      put("kbd",       "kbd");
      put("keygen",    "keygen");
      put("label",     "label");
      put("legend",    "legend");
      put("li",        "li");
      put("link",      "link");
      put("listing",   "listing");
      put("main",      "main");
      put("map",       "map");
      put("mark",      "mark");
      put("marquee",   "marquee");
      put("menu",      "menu");
      put("menuitem",  "menuitem");
      put("meta",      "meta");
      put("meter",     "meter");
      put("multicol",  "multicol");
      put("nav",       "nav");
      put("nobr",      "nobr");
      put("noembed",   "noembed");
      put("noframes",  "noframes");
      put("noscript",  "noscript");
      put("object",    "object");
      put("ol",        "ol");
      put("option",    "option");
      put("optgroup",  "optgroup");
      put("output",    "output");
      put("p",         "p");
      put("param",     "param");
      put("plaintext", "plaintext");
      put("pre",       "pre");
      put("progress",  "progress");
      put("q",         "q");
      put("#root",     "#root");       // jsoup                               //
      put("rp",        "rp");
      put("rt",        "rt");
      put("ruby",      "ruby");
      put("s",         "s");
      put("samp",      "samp");
      put("script",    "script");
      put("section",   "section");
      put("select",    "select");
      put("small",     "small");
      put("sound",     "sound");
      put("source",    "source");
      put("spacer",    "spacer");
      put("span",      "span");
      put("strong",    "strong");
      put("style",     "style");
      put("sub",       "sub");
      put("summary",   "summary");
      put("sup",       "sup");
      put("table",     "table");
      put("tbody",     "tbody");
      put("td",        "td");
      put("textarea",  "textarea");
      put("tfoot",     "tfoot");
      put("th",        "th");
      put("thead",     "thead");
      put("time",      "time");
      put("title",     "title");
      put("tr",        "tr");
      put("track",     "track");
      put("tt",        "tt");
      put("strictMode","strictMode");
      put("u",         "u");
      put("ul",        "ul");
      put("var",       "var");
      put("video",     "video");
      put("wbr",       "wbr");
      put("xmp",       "xmp");
		                                 // svg related elements                //
      put("circle",    "circle");
      put("polygon",   "polygon");
      put("svg",       "svg");
   }};

public static final Map<String,String> kINLINE_TAGS =
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
                                       // node modules imported by reactjava  //
                                       // core                                //
protected static final List<String> kREACT_JAVA_CORE_IMPORTED_NODE_MODULES =
   new HashList<String>()
   {{
      add(IConfiguration.getNodeModuleJavascript("rxjs.Observable", null));
      add(IConfiguration.getNodeModuleJavascript("rxjs.Subscription", null));
      add(IConfiguration.getNodeModuleJavascript("rxjs.Subscriber", null));
      add(IConfiguration.getNodeModuleJavascript("rxjs.add.observable.fromPromise", null));
   }};
                                       // all type descriptors                //
protected static final Collection<TypeDsc> types = new HashSet<>();

                                       // typedsc by tag                      //
protected static final Map<String,TypeDsc> byTag = new HashMap<>();

                                       // typedsc by classname                //
protected static final Map<String,TypeDsc> byClassname = new HashMap<>();

                                       // Component root declaration          //
public static final ClassOrInterfaceDeclaration kROOT_COMPONENT =
   new ClassOrInterfaceDeclaration(
      null,                            // tokenRange                          //
      new NodeList<>(),                // modifiers                           //
      new NodeList<>(),                // annotations                         //
      false,                           // isInterface                         //
                                       // name                                //
      new SimpleName(Component.class.getCanonicalName()),
      new NodeList<>(),                // typeParameters                      //
      new NodeList<>(),                // extendedTypes                       //
      new NodeList<>(),                // implementedTypes                    //
      new NodeList<>());               // members                             //

                                       // AppComponentTemplate declaration    //
public static final ClassOrInterfaceDeclaration kROOT_APP_COMPONENT_TEMPLATE =
   new ClassOrInterfaceDeclaration(
      null,                            // tokenRange                          //
      new NodeList<>(),                // modifiers                           //
      new NodeList<>(),                // annotations                         //
      false,                           // isInterface                         //
                                       // name                                //
      new SimpleName(AppComponentTemplate.class.getCanonicalName()),
      new NodeList<>(),                // typeParameters                      //
      new NodeList<>(),                // extendedTypes                       //
      new NodeList<>(),                // implementedTypes                    //
      new NodeList<>());               // members                             //

                                       // Provider root declaration           //
public static final ClassOrInterfaceDeclaration kROOT_PROVIDER =
   new ClassOrInterfaceDeclaration(
      null,                            // tokenRange                          //
      new NodeList<>(),                // modifiers                           //
      new NodeList<>(),                // annotations                         //
      false,                           // isInterface                         //
                                       // name                                //
      new SimpleName(Provider.class.getCanonicalName()),
      new NodeList<>(),                // typeParameters                      //
      new NodeList<>(),                // extendedTypes                       //
      new NodeList<>(),                // implementedTypes                    //
      new NodeList<>());               // members                             //

                                       // Null typeDsc                        //
public static final TypeDsc kOBJECT_INSTANCE =
   new TypeDsc(
      Object.class.getSimpleName(), Object.class.getCanonicalName(),
      null, null, null, null, false, false, false, null);

                                       // Component root typeDsc              //
public static final TypeDsc kROOT_COMPONENT_INSTANCE =
   new TypeDsc(
      Component.class.getSimpleName(), Component.class.getCanonicalName(),
      kROOT_COMPONENT, null, null, null, true, false, false, null);

                                       // AppComponentTemplate root typeDsc   //
public static final TypeDsc kROOT_APP_COMPONENT_TEMPLATE_INSTANCE =
   new TypeDsc(
      AppComponentTemplate.class.getSimpleName(),
      AppComponentTemplate.class.getCanonicalName(),
      kROOT_APP_COMPONENT_TEMPLATE,
      null,
      null,
      kROOT_COMPONENT_INSTANCE,
      true, true, false, null);
                                       // Provider root typeDsc               //
public static final TypeDsc kROOT_PROVIDER_INSTANCE =
   new TypeDsc(
      Provider.class.getSimpleName(), Provider.class.getCanonicalName(),
      kROOT_PROVIDER, null, null, null, false, false, true, null);

public static final int kNUM_BUILTIN_TYPES = types.size();

                                       // class variables ------------------- //
                                       // target app                          //
protected static TypeDsc      targetApp;
protected static Map<String,TypeDsc>   // app dependencies by classname       //
                              appDependenciesByClassname;
protected static Map<String,TypeDsc>   // app dependencies by tag             //
                              appDependenciesByTag;
                                       // all components                      //
protected static Set<TypeDsc> components;
                                       // all app components                  //
protected static Set<TypeDsc> appComponents;
                                       // all providers                       //
protected static Set<TypeDsc> providers;
                                       // all native components               //
protected static Set<TypeDsc> nativeComponents;
                                       // all imported node modules           //
protected static HashList<String>
                              allImportedNodeModules;

                                       // public instance variables --------- //
public String                 tag;     // tag                                 //
                                       // associated classname                //
public String                 classname;
public ClassOrInterfaceDeclaration     // type declaration                    //
                              type;
                                       // super type descriptor               //
public TypeDsc                superTypeDsc;
                                       // sub type descriptors                //
public Collection<TypeDsc>    subTypeDscs;
public CompilationUnit        cu;      // associated compilation unit         //
public String                 source;  // associated source file contents     //
                                       // true iff an app component           //
public Boolean                bAppComponent;
                                       // true iff a component                //
public Boolean                bComponent;
                                       // true iff a native component         //
public Boolean                bNativeComponent;
                                       // true iff a provider                 //
public Boolean                bProvider;
                                       // true iff instanciable               //
public boolean                bInstantiable;
                                       // component imported node modules     //
protected List<String>        importedNodeModules;
protected List<String>        imports; // component imports                   //
                                       // component reactivex imports         //
protected Set<String>         reactiveXImports;
                                       // component seoInfo                   //
protected JSXSEOInfo          seoInfo;
                                       // component render method             //
protected MethodDeclaration   componentRenderMethod;
                                       // component render method body        //
protected String              componentRenderMethodBody;
                                       // component render css procedure      //
protected MethodDeclaration   componentRenderCSSProcedure;
                                       // native component javascript path    //
protected String              nativeComponentJavascriptPath;
                                       // component markup                    //
protected String              markup;
                                       // component markup java blocks        //
protected List<String>        javaBlocks;
                                       // component markup elements           //
protected Elements            markupElements;
                                       // component dependencies: if unordered//
                                       // is a Set; otherwise is a List       //
protected Collection<String>  componentDependencies;
                                       // map of replacement by original tag  //
protected Map<String,String>  replacementByTagMap;
                                       // map of original tag by replacement  //
protected Map<String,String>  tagByReplacementMap;

                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       TypeDsc - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public TypeDsc()
{
   this(null, null, null, null, null, null, null, null, null, null);
}
/*------------------------------------------------------------------------------

@name       TypeDsc - constructor for native component
                                                                              */
                                                                             /**
            Constructor for native component

@param     tag                               tag
@param     nativeComponentJavascriptPath     native component javascript path

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public TypeDsc(
   String tag,
   String nativeComponentJavascriptPath)
{
   this(
      tag, null, null, null, null, null, null, null, null,
      nativeComponentJavascriptPath);
}
/*------------------------------------------------------------------------------

@name       TypeDsc - constructor without specified super type
                                                                              */
                                                                             /**
            Constructor without specified super type

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public TypeDsc(
   String                      classname,
   ClassOrInterfaceDeclaration type,
   String                      source,
   CompilationUnit             cu)
{
   this(null, classname, type, source, cu, null, null, null, null, null);
}
/*------------------------------------------------------------------------------

@name       TypeDsc - constructor
                                                                              */
                                                                             /**
            Constructor

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected TypeDsc(
   String                               tag,
   String                               classname,
   ClassOrInterfaceDeclaration          type,
   String                               source,
   CompilationUnit                      cu,
   TypeDsc                              superTypeDsc,
   Boolean                              bComponent,
   Boolean                              bAppComponent,
   Boolean                              bProvider,
   String                               nativeComponentJavascriptPath)
{
   this.nativeComponentJavascriptPath = nativeComponentJavascriptPath;
   this.type                          = type;
   this.source                        = source;
   this.cu                            = cu;
   this.superTypeDsc                  = superTypeDsc;
   this.classname                     = classname;
   this.bComponent                    = bComponent;
   this.bAppComponent                 = bAppComponent;
   this.bProvider                     = bProvider;
   this.bNativeComponent              = nativeComponentJavascriptPath != null;
   this.tagByReplacementMap           = new HashMap<>();
   this.replacementByTagMap           = new HashMap<>();
   this.tag =
      tag != null
         ? tag
         : classname != null
            ? classname.substring(classname.lastIndexOf('.') + 1) : null;

   putType(this);
}
/*------------------------------------------------------------------------------

@name       addImportedNodeModuleJavascript - add imported node module script
                                                                              */
                                                                             /**
            Add imported node module javascript.

            The module describes the source path and optionally the desired
            name of the associated external separated by the word 'as' as a
            delimiter. For example, to store the pdf.js library under the
            name "PDFJS":

               "pdfjs-dist/build/pdf as PDFJS"


@return     true iff specified module is found

@param      module      module
@param      logger      logger

@history    Sun Dec 02, 2018 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected boolean addImportedNodeModuleJavascript(
   String     module,
   TreeLogger logger)
{
   String regex =
      Utilities.kREGEX_ONE_OR_MORE_WHITESPACE
    + "as"
    + Utilities.kREGEX_ONE_OR_MORE_WHITESPACE;

   String[] splits     = module.split(regex);
   String   moduleName = splits[0];

   String rawPath = IConfiguration.getNodeModuleJavascript(moduleName, logger);
   String path    = rawPath;
   if (path != null)
   {
      path = splits.length != 2 ? path : path + ";" + splits[1];
      importedNodeModules.add(path);
   }

   boolean bFound = path != null;
   if (bFound)
   {
      String tagName = splits.length > 1 ? splits[1] : path;
      if (TypeDsc.forTag(tagName) == null)
      {
                                       // create a native type descriptor     //
         new TypeDsc(tagName, rawPath);
      }
   }

   logger.log(TreeLogger.INFO,
      "FtpeDsc.addImportedNodeModuleJavascript(): module=" + module
    + ", " + (bFound ? "found as " + path : "not found"));

   return(bFound);
}
/*------------------------------------------------------------------------------

@name       addImportedNodeModuleStylesheet - add imported node module css
                                                                              */
                                                                             /**
            Add imported node module css.

@param      module      module
@param      logger      logger

@history    Sun Dec 02, 2018 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void addImportedNodeModuleStylesheet(
   String      module,
   TreeLogger  logger)
{
   String path = null;
   if (!module.endsWith(".css"))
   {
      path = IConfiguration.getNodeModuleCSSFromPackageJSON(module, logger);
   }
   else
   {
      String css = module.substring(0, module.lastIndexOf('.'));
             css = css.replace(".", "/");
             css = css + ".css";

      if (!importedNodeModules.contains(css))
      {
         File stylesheet = new File(IConfiguration.getNodeModulesDir(logger), css);
         if (!stylesheet.exists())
         {
            throw new IllegalStateException(
               "Cannot find node module css " + module + ": "
             + "did you forget to install it?"
             + " " + stylesheet.getAbsolutePath());
         }
         else
         {
            path = stylesheet.getAbsolutePath();

            logger.log(
               logger.DEBUG,
               "TypeDsc.addImportedNodeModuleStylesheet: module=" + module
             + ", cssPath=" + path);
         }
      }
   }
   if (path != null)
   {
      importedNodeModules.add(path);
   }

   logger.log(TreeLogger.INFO,
      "TypeDsc.addImportedNodeModuleStylesheet(): stylesheet=" + path
    + ", " + (path != null ? "" : "not ") + "found");
}
/*------------------------------------------------------------------------------

@name       bindTagNameWithReplacement - bind tag name with replacement
                                                                              */
                                                                             /**
            Bind tag name with replacement.

@param      tag            original tag

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes      see replaceComponentTagsWithSymbols()
                                                                              */
//------------------------------------------------------------------------------
public String bindTagNameWithReplacement(
   String tag)
{
   String replacement = "tag" + replacementByTagMap.size();
   replacementByTagMap.put(tag, replacement);
   tagByReplacementMap.put(replacement, tag);

   return(replacement);
}
/*------------------------------------------------------------------------------

@name       forClassname - get typeDsc for specified classname
                                                                              */
                                                                             /**
            Get typeDsc for specified classname. Note that not all types have a
            classname (e.g. native components), and so this map may not contain
            all type descriptors.

@return     typeDsc for specified classname

@param      classname      classname

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static TypeDsc forClassname(
   String classname)
{
   return(classname != null ? byClassname.get(classname) : null);
}
/*------------------------------------------------------------------------------

@name       forTag - get typeDsc for specified tag
                                                                              */
                                                                             /**
            Get typeDsc for specified tag. Note that tags are not necessarily
            unique, and so this map may not contain all type descriptors.

@return     typeDsc for specified tag

@param      tag      tag

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static TypeDsc forTag(
   String tag)
{
   return(tag != null ? byTag.get(tag) : null);
}
/*------------------------------------------------------------------------------

@name       getAllImportedNodeModules - all imported node modules
                                                                              */
                                                                             /**
            Get all imported node modules for the target app and its
            dependencies.

@return     all imported node modules for the target app its dependencies

@param      logger      logger

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static List<String> getAllImportedNodeModules(
   TreeLogger logger)
{
   if (allImportedNodeModules == null)
   {
                                       // add those used by the core          //
      allImportedNodeModules =
         new HashList<>(kREACT_JAVA_CORE_IMPORTED_NODE_MODULES);

                                       // add referenced material-ui modules  //
      allImportedNodeModules.addAll(handleMaterialUIImports(logger));

      TypeDsc app = TypeDsc.getTargetApp(logger);
      if (app != null)
      {
                                       // add those imported by dependencies   //
         for (TypeDsc dependency : TypeDsc.getAppDependenciesByTag().values())
         {
            if (dependency.importedNodeModules != null)
            {
               for (String importedNodeModule : dependency.importedNodeModules)
               {
                  allImportedNodeModules.add(importedNodeModule);
               }
            }
         }
      }

      for (String importedNodeModule : allImportedNodeModules)
      {
         logger.log(
            logger.INFO,
            "TypeDsc.getAllImportedNodeModules: " + allImportedNodeModules);
      }
   }

   return(allImportedNodeModules);
}
/*------------------------------------------------------------------------------

@name       getAppComponents - get all app components
                                                                              */
                                                                             /**
            Get all app components.

@return     all app components

@history    Sat Sep 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Set<TypeDsc> getAppComponents()
{
   if (appComponents == null)
   {
      appComponents = new HashSet<>();
   }
   return(appComponents);
}
/*------------------------------------------------------------------------------

@name       getAppDependenciesByClassname - get app dependencies by classname
                                                                              */
                                                                             /**
            Get map of parsed apps by classname.

@return     map of parsed apps by classname

@history    Thu Aug 20, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Map<String,TypeDsc> getAppDependenciesByClassname()
{
   if (appDependenciesByClassname == null)
   {
      appDependenciesByClassname = new HashMap<>();
   }
   return(appDependenciesByClassname);
}
/*------------------------------------------------------------------------------

@name       getAppDependenciesByTag - get parsed app dependencies by tag
                                                                              */
                                                                             /**
            Get map of parsed apps by tag.

@return     map of parsed apps by tag

@history    Thu Aug 20, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Map<String,TypeDsc> getAppDependenciesByTag()
{
   if (appDependenciesByTag == null)
   {
      appDependenciesByTag = new HashMap<>();
   }
   return(appDependenciesByTag);
}
/*------------------------------------------------------------------------------

@name       getAssignableTo - get all types assignable to specified type
                                                                              */
                                                                             /**
            Get all types assignable to specified type; that is, all subtypes
            and their substypes.

@return     map of types assignable to specified type

@param      root        specified type

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static Map<String,TypeDsc> getAssignableTo(
   TypeDsc root)
{
   Map<String,TypeDsc> assignables = new HashMap<>();
   for (TypeDsc chaseDsc : getTypes())
   {
      if (root.type.isInterface() == chaseDsc.type.isInterface())
      {
         List<TypeDsc> assignableDscs = new ArrayList<>();
         boolean       bAssignable    = false;
         while(chaseDsc != null)
         {
            assignableDscs.add(chaseDsc);

            if (root.equals(chaseDsc))
            {
               bAssignable = true;
               break;
            }
            if (chaseDsc.superTypeDsc == null)
            {
               break;
            }

            chaseDsc = chaseDsc.getSuperType();
         }
         if (bAssignable)
         {
            for (TypeDsc assignable : assignableDscs)
            {
               assignables.put(assignable.classname, assignable);
            }
         }
      }
   }
   return(assignables);
}
/*------------------------------------------------------------------------------

@name       getComponentImportedNodeModules - importedNodeModules method
                                                                              */
                                                                             /**
            Get any imported mode modules for the specified component.

@param      logger         logger

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public List<String> getComponentImportedNodeModules(
   TreeLogger logger)
{
   if (importedNodeModules == null)
   {
      importedNodeModules = new HashList<>();
                                       // add any reactivex imports           //
      importedNodeModules.addAll(handleReactiveXImports(logger));

                                       // add any material-ui imports for tags//
      importedNodeModules.addAll(handleMaterialUIImports(logger));

                                       // add any specified by component      //
      importedNodeModules.addAll(handleComponentImportedNodeModules(logger));
   }

   String msg = "";
   for (String module : importedNodeModules)
   {
      msg += (msg.length() > 0 ? ", " : "found ") + module;
   }
   if (msg.length() == 0)
   {
      msg += "none";
   }
   logger.log(logger.INFO, "TypeDsc.getComponentImportedNodeModules(): " + msg);

   return(importedNodeModules);
}
/*------------------------------------------------------------------------------

@name       getComponentImports - get imports in component cu
                                                                              */
                                                                             /**
            Get imports in component cu.

@return     imports in component cu

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public List<String> getComponentImports()
{
   if (imports == null)
   {
      imports = new HashList<>();
      if (cu != null)
      {
         for (ImportDeclaration targetImport : cu.getImports())
         {
            imports.add(targetImport.getName().toString());
         }
      }
   }
   return(imports);
}
/*------------------------------------------------------------------------------

@name       getComponentInfoMethod - get specified component info method
                                                                              */
                                                                             /**
            Get specified component info method for the specified component.

@return     any specified component info method for the specified component.

@param      methodName     method name

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public MethodDeclaration getComponentInfoMethod(
   String  methodName,
   String  returnType)
{
   MethodDeclaration method = null;

   List<MethodDeclaration> methods = type.getMethodsByName(methodName);
   for (MethodDeclaration chase : methods)
   {
      if (chase.getParameters().size() == 0)
      {
         if (returnType != null && !returnType.equals(chase.getTypeAsString()))
         {
            throw new IllegalStateException(
               methodName + " method for " + classname
                  + " must be return a " + returnType);
         }
         method = chase;
         break;
      }
   }
   return(method);
}
/*------------------------------------------------------------------------------

@name       getComponentInfoMethodValue - get component info method value
                                                                              */
                                                                             /**
            Get component info method value for the specified component.

@param      methodName     component info method name
@param      logger         logger

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public List<String> getComponentInfoMethodValue(
   String     methodName,
   String     returnType,
   TreeLogger logger)
{
   List<String> value = null;
   BlockStmt    block = null;
   long         start = System.currentTimeMillis();

   MethodDeclaration method = getComponentInfoMethod(methodName, returnType);
   if (method != null)
   {
      block = method.getBody().get();
   }
   if (block != null)
   {
      for (Statement statement : block.getStatements())
      {
         if (!statement.isReturnStmt())
         {
            continue;
         }

         Expression returnExpression =
            ((EnclosedExpr)((ReturnStmt)statement).getExpression().get())
               .getInner();

         if (returnExpression.isMethodCallExpr())
         {
            value = new ArrayList<>();

            NodeList<Expression> arguments =
               ((MethodCallExpr)returnExpression).getArguments();

            for (Expression expression : arguments)
            {
               if (returnType != null && returnType.equals("StringLiteralList"))
               {
                  if (!(expression instanceof StringLiteralExpr))
                  {
                     throw new IllegalArgumentException(
                        "Elements of a StringLiteralList must be String literals");
                  }

                  value.add(((StringLiteralExpr)expression).asString());
               }
               else if (returnType != null && returnType.equals("SEOInfo"))
               {
                  if (!(expression instanceof MethodCallExpr))
                  {
                     throw new IllegalArgumentException(
                        "Return value must be invocation of SEOInfo.newInstance()");
                  }

                  if (!expression.getParentNode().isPresent())
                  {
                       throw new IllegalStateException(
                        "Parent node not expected to be null");
                  }

                  MethodCallExpr parentNode =
                     (MethodCallExpr)expression.getParentNode().get();

                  value.add(parentNode.toString());
                  break;
               }
               else
               {
                  throw new UnsupportedOperationException(
                     methodName + "() must return an invocation of "
                   + returnType + ".newInstance() for now");
               }
            }
            break;
         }
         else
         {
            throw new UnsupportedOperationException(
               methodName + "() must return an invocation of "
             + returnType + ".newInstance() for now");
         }
      }
   }

   logger.log(
      logger.INFO,
      "TypeDsc.getComponentInfoMethodValue(): for " + methodName + "(): "
    + "elapsed msec=" + (System.currentTimeMillis() - start));

   return(value);
}
/*------------------------------------------------------------------------------

@name       getComponentMarkup - get component markup
                                                                              */
                                                                             /**
            Get component markup.

@return     component markup

@history    Sat Sep 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getComponentMarkup()
{
   return(markup);
}
/*------------------------------------------------------------------------------

@name       getComponentMarkupElements - get component markup elements
                                                                              */
                                                                             /**
            Get component markup elements.

@return     component markup elements

@history    Sat Sep 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Elements getComponentMarkupElements()
{
   return(markupElements);
}
/*------------------------------------------------------------------------------

@name       getComponentPackage - get component package
                                                                              */
                                                                             /**
            Get component package.

@return     component package

@history    Sat Sep 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getComponentPackage()
{
   String componentPkg =
      cu != null && cu.getPackageDeclaration().isPresent()
         ? cu.getPackageDeclaration().get().getNameAsString() : null;

   return(componentPkg);
}
/*------------------------------------------------------------------------------

@name       getComponentReactiveXImports - get component reactivex imports
                                                                              */
                                                                             /**
            Get component reactivex imports.

@history    Sat Jan 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Set<String> getComponentReactiveXImports()
{
   if (reactiveXImports == null)
   {
      reactiveXImports = new HashSet<>();
      for (String imported : getComponentImports())
      {
         if (imported.startsWith("io.reactjava.client.core.rxjs."))
         {
            reactiveXImports.add(imported);
         }
      }
   }
   return(reactiveXImports);
}
/*------------------------------------------------------------------------------

@name       getComponentRenderCSSProcedure - get any renderCSS() procedure
                                                                              */
                                                                             /**
            Get any renderCSS() procedure for the specified component.

@return     any renderCSS() procedure for the specified component.

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public MethodDeclaration getComponentRenderCSSProcedure()
{
   return(getComponentRenderCSSProcedure(false));
}
/*------------------------------------------------------------------------------

@name       getComponentRenderCSSProcedure - get any renderCSS() procedure
                                                                              */
                                                                             /**
            Get any renderCSS() procedure for the specified component.

@return     any renderCSS() procedure for the specified component.

@param      bGetFresh      iff true, ignore any cached value

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public MethodDeclaration getComponentRenderCSSProcedure(
   boolean bGetFresh)
{
   if (bGetFresh || componentRenderCSSProcedure == null)
   {
                                          // find any renderCSS() procedure      //
      for (MethodDeclaration chase : type.getMethodsByName("renderCSS"))
      {
         if (chase.getParameters().size() == 0)
         {
            componentRenderCSSProcedure = chase;
            break;
         }
      }
   }
   return(componentRenderCSSProcedure);
}
/*------------------------------------------------------------------------------

@name       getComponentRenderMethod - get any component render() method
                                                                              */
                                                                             /**
            Get any render() method for the specified component.

@return     any render() method for the specified component.

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public MethodDeclaration getComponentRenderMethod()
{
   return(getComponentRenderMethod(false));
}
/*------------------------------------------------------------------------------

@name       getComponentRenderMethod - get any component render() method
                                                                              */
                                                                             /**
            Get any render() method for the specified component.

@return     any render() method for the specified component.

@param      bGetFresh      iff true, ignore any cached value

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public MethodDeclaration getComponentRenderMethod(
   boolean bGetFresh)
{
   if (bGetFresh || componentRenderMethod == null)
   {
                                       // find any render() procedure         //
      for (MethodDeclaration chase : type.getMethodsByName("render"))
      {
         if (chase.getParameters().size() == 0)
         {
            if (!chase.isFinal())
            {
               throw new IllegalStateException(
                  "render() method for " + classname + " must be final");
            }

            componentRenderMethod = chase;
            break;
         }
      }
   }
   return(componentRenderMethod);
}
/*------------------------------------------------------------------------------

@name       getComponentRenderMethodBody - component render() method body
                                                                              */
                                                                             /**
            Get any render() method body for the specified component.

@return     any render() method body for the specified component.

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getComponentRenderMethodBody()
{
   return(getComponentRenderMethodBody(false));
}
/*------------------------------------------------------------------------------

@name       getComponentRenderMethodBody - component render() method body
                                                                              */
                                                                             /**
            Get any render() method body for the specified component.

@return     any render() method body for the specified component.

@param      bGetFresh      iff true, ignore any cached value

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getComponentRenderMethodBody(
   boolean bGetFresh)
{
   if (bGetFresh || componentRenderMethodBody == null)
   {
      MethodDeclaration method = getComponentRenderMethod(bGetFresh);
      if (method != null)
      {
         componentRenderMethodBody =
            LexicalPreservingPrinter.print(method.getBody().get());

                                       // between the brackets                //
         componentRenderMethodBody =
            componentRenderMethodBody.substring(
               1, componentRenderMethodBody.length() - 1);
      }
   }
   return(componentRenderMethodBody);
}
/*------------------------------------------------------------------------------

@name       getComponentSEOInfo - parse component getSEOInfo() method
                                                                              */
                                                                             /**
            Parse component getSEOInfo() method.

@param      logger         logger

@history    Sat Sep 26, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public JSXSEOInfo getComponentSEOInfo(
   TreeLogger logger)
{
   if (seoInfo == null)
   {
      seoInfo = new JSXSEOInfo();
      List<String> list =
         getComponentInfoMethodValue("getSEOInfo", "SEOInfo", logger);

      if (list != null && list.size() > 0)
      {
                                    // example:                               //
                                    // SEOInfo.newInstance(
                                    //    StringLiteralList.newInstance(      //
                                    //       kSEO_DEPLOY_PATH, kPATH_A),      //
                                    //    StringLiteralList.newInstance(      //
                                    //       kPATH_A, kTITLE_LANDING,         //
                                    //       kDESCRIPTION_LANDING),           //
                                    //    StringLiteralList.newInstance(      //
                                    //       kPATH_B, kTITLE_USER_GUIDE,      //
                                    //       kDESCRIPTION_USER_GUIDE))        //
         String         deployPath      = null;
         String         defaultPageHash = null;
         List<String[]> pageInfosList   = new ArrayList<>();
         String         pageHash;
         String         title;
         String         description;
         String         structuredDataType;
         String         structuredData;

                                    // remove enclosing SEOInfo.newInstance   //
         String token0 = "SEOInfo.newInstance(";
         String value  = list.get(0);
         value = value.substring(value.indexOf(token0) + token0.length());
         value = value.substring(0, value.length() - 1);

         int idxBeg;
         int idxEnd;

         String[] splits = value.split("StringLiteralList.newInstance");
         for (int i = 1; i < splits.length; i++)
         {
                                    // remove enclosing parens                //
            String split = splits[i];
            idxBeg = split.indexOf('(');
            idxEnd = split.indexOf(')', idxBeg);
            String[] args = split.substring(idxBeg + 1, idxEnd).trim().split(",");
            if (i == 1 )
            {
               if (args.length != 2)
               {
                  throw new IllegalArgumentException(
                     "The first StringLiteralList must at least specify the deploy"
                   + " path and default page hash");
               }
               deployPath      = resolveStringLiteral(args[0].trim());
               defaultPageHash = resolveStringLiteral(args[1].trim());
               continue;
            }
            if (args.length != 3 && args.length != 5)
            {
               throw new IllegalArgumentException(
                  "The StringLiteralLists after the first must have either "
                + "three or five elements");
            }

            pageHash           = resolveStringLiteral(args[0].trim());
            title              = resolveStringLiteral(args[1].trim());
            description        = resolveStringLiteral(args[2].trim());
            structuredDataType =
               args.length == 5 ? resolveStringLiteral(args[3].trim()) : null;
            structuredData     =
               args.length == 5 ? resolveStringLiteral(args[4].trim()) : null;

            pageInfosList.add(
               new String[]
               {
                  pageHash,
                  title,
                  description,
                  structuredDataType,
                  structuredData
               });
         }

         String[][] pageInfos =
            pageInfosList.toArray(
               new String[pageInfosList.size()][SEOInfo.kNUM_PAGEINFO_MEMBERS]);

         seoInfo = new JSXSEOInfo(deployPath, defaultPageHash, pageInfos);
      }

      String msg = "";
      for (String module : importedNodeModules)
      {
         msg += (msg.length() > 0 ? ", " : "") + module;
      }
      if (msg.length() == 0)
      {
         msg += "none";
      }

      logger.log(logger.INFO, "TypeDsc.getComponentSEOInfo(): " + seoInfo);
   }
   return(seoInfo);
}
/*------------------------------------------------------------------------------

@name       getComponents - get all components
                                                                              */
                                                                             /**
            Get all components.

@return     all components

@history    Sat Sep 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Set<TypeDsc> getComponents()
{
   if (components == null)
   {
      components = new HashSet<>();
   }
   return(components);
}
/*------------------------------------------------------------------------------

@name       getImportForSimpleName - get typedDsc for simple name
                                                                              */
                                                                             /**
            Get import with specified simple name for specified compilation
            unit.

@param      importSimpleName     import simple name

@history    Sat Jan 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public TypeDsc getImportForSimpleName(
   String  importSimpleName)
{
   TypeDsc importType = null;

   for (String imported : getComponentImports())
   {
      if (imported.endsWith("." + importSimpleName))
      {
         importType = forClassname(imported);
         break;
      }
   }
   return(importType);
}
/*------------------------------------------------------------------------------

@name       getJavaBlocks - get markuop java blocks
                                                                              */
                                                                             /**
            Get markuop java blocks.

@return     markuop java blocks

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public List<String> getJavaBlocks()
{
   if (javaBlocks == null)
   {
      javaBlocks = new ArrayList<>();
   }
   return(javaBlocks);
}
/*------------------------------------------------------------------------------

@name       getNativeComponents - get all native components
                                                                              */
                                                                             /**
            Get all native components.

@return     all native components

@history    Sat Sep 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Set<TypeDsc> getNativeComponents()
{
   if (nativeComponents == null)
   {
      nativeComponents = new HashSet<>();
   }
   return(nativeComponents);
}
/*------------------------------------------------------------------------------

@name       getOrderedComponentDependencies - get ordered component dependencies
                                                                              */
                                                                             /**
            Get ordered tags of component dependencies. Tags are ordered such
            that no tag in the set  preceeds another that contains it as a
            substring.

@return     ordered tags of component dependencies

@history    Sat Sep 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public List<String> getOrderedComponentDependencies()
{
   List<String> orderedComponentDependencies =
      (componentDependencies != null
         && componentDependencies instanceof List)
            ? (List)componentDependencies : null;

   if (orderedComponentDependencies == null
         && componentDependencies.size() > 0)
   {
      List<String> ordered   = new ArrayList<>();
      List<String> unordered = new ArrayList<>(componentDependencies);
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
         if (ordered.size() == unordered.size())
         {
            break;
         }
      }

      componentDependencies = orderedComponentDependencies;
   }

   return(orderedComponentDependencies);
}
/*------------------------------------------------------------------------------

@name       getProviders - get all providers
                                                                              */
                                                                             /**
            Get all providers.

@return     all providers

@history    Sat Sep 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Set<TypeDsc> getProviders()
{
   if (providers == null)
   {
      providers = new HashSet<>();
   }
   return(providers);
}
/*------------------------------------------------------------------------------

@name       getSubTypes - get subtypes for specified type
                                                                              */
                                                                             /**
            Get subtypes for specified type.

@history    Sat Jan 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Collection<TypeDsc> getSubTypes()
{
   if (subTypeDscs == null)
   {
      subTypeDscs = new HashSet<>();
   }
   return(subTypeDscs);
}
/*------------------------------------------------------------------------------

@name       getSuperType - get supertype for specified type
                                                                              */
                                                                             /**
            Get supertype for specified type.

@history    Sat Jan 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public TypeDsc getSuperType()
{
   if (superTypeDsc == null)
   {
      String simpleClassname = null;

      if (type != null)
      {
         NodeList<ClassOrInterfaceType> extendedTypes = type.getExtendedTypes();
         if (extendedTypes.size() > 0)
         {
            simpleClassname = extendedTypes.get(0).getNameAsString();

                                       // check for the same package          //
            String typePackage =
               cu.getPackageDeclaration().get().getName().asString();

            String superClassnameWPackage = typePackage + "." + simpleClassname;
            superTypeDsc = forClassname(superClassnameWPackage);
         }
      }
      if (superTypeDsc == null)
      {
                                       // check for an import                 //
         superTypeDsc = getImportForSimpleName(simpleClassname);
      }
      if (superTypeDsc == null && classname != null)
      {
                                       // chase classname prefix of outer     //
                                       // classes starting with same          //
                                       // classname prefix as type            //
         String superPrefix = classname;
         for (int idx = superPrefix.lastIndexOf('.');
               idx > 0;
               idx = superPrefix.lastIndexOf('.'))
         {
            superPrefix = superPrefix.substring(0, idx);

                                 // check for existing type             //

            String candidateClassname = superPrefix + "." + simpleClassname;
            superTypeDsc = forClassname(candidateClassname);
            if (superTypeDsc != null)
            {
               break;
            }
          }
      }
      if (superTypeDsc == null)
      {
         superTypeDsc = kOBJECT_INSTANCE;
      }
   }
   if (superTypeDsc != null && superTypeDsc != TypeDsc.kOBJECT_INSTANCE)
   {
      superTypeDsc.getSubTypes().add(this);
   }

   return(superTypeDsc);
}
/*------------------------------------------------------------------------------

@name       getTags - get all tags
                                                                              */
                                                                             /**
            Get all tags.

@return     all tags

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Set<String> getTags()
{
   return(byTag.keySet());
}
/*------------------------------------------------------------------------------

@name       getTargetApp - get the target app
                                                                              */
                                                                             /**
            Get the target app.

@return     target app or null if none found.

@param      logger      logger

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static TypeDsc getTargetApp(
   TreeLogger logger)
{
   if (targetApp == null)
   {
      for (TypeDsc candidate : getAppComponents())
      {
         if (targetApp == null || candidate.superTypeDsc == targetApp)
         {
            targetApp = candidate;
         }
      }

      logger.log(
         logger.INFO,
         "TypeDsc.getTargetApp(): "
       + (targetApp != null ? "App=" + targetApp.classname : "no App found"));
   }

   return(targetApp);
}
/*------------------------------------------------------------------------------

@name       getTypes - get all type descriptors
                                                                              */
                                                                             /**
            Get all type descriptors.

@return     all type descriptors

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Collection<TypeDsc> getTypes()
{
   return(types);
}
/*------------------------------------------------------------------------------

@name       handleComponentImportedNodeModules - importedNodeModules method
                                                                              */
                                                                             /**
            Get any imported mode modules for the specified component.

@param      logger         logger

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public List<String> handleComponentImportedNodeModules(
   TreeLogger logger)
{
   List<String> importedNodeModules = new ArrayList<>();

   List<String> value =
      getComponentInfoMethodValue(
         "getImportedNodeModules", "StringLiteralList", logger);

   if (value != null)
   {
      for (String moduleSpecifier : value)
      {
                                       // find whether looking for javascript //
                                       // or css or both                      //
         String[] splits  = moduleSpecifier.trim().split(":");
         String   module  = splits[0];
         boolean  bScript = module.endsWith(".js");
         boolean  bStyle  = module.endsWith(".css");

         if (!bScript && !bStyle)
         {
            for (int iSplit = 1; iSplit < splits.length; iSplit++)
            {
               String split = splits[iSplit].trim().toLowerCase();
               if ("javascript".equals(split))
               {
                  bScript = true;
               }
               else if ("css".equals(split))
               {
                  bStyle = true;
               }
            }
         }

         bScript |= bStyle == false;
         if (bScript)
         {
            if (!addImportedNodeModuleJavascript(module, logger))
            {
               throw new IllegalArgumentException(
                  "module=" + module + " not found");
            }
         }
         if (bStyle)
         {
            addImportedNodeModuleStylesheet(module, logger);
         }
      }
   }

   return(importedNodeModules);
}
/*------------------------------------------------------------------------------

@name       handleMaterialUIImports - materialUI method
                                                                              */
                                                                             /**
            Get any imported materialUI modules for the specified component.

@param      logger         logger

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static Set<String> handleMaterialUIImports(
   TreeLogger logger)
{
   Set<String> importedNodeModules = new HashSet<>();
   String      prefix              = "@material-ui.";

   for (TypeDsc dependency : getNativeComponents())
   {
     if (dependency.tag.startsWith(prefix))
     {
        importedNodeModules.add(dependency.nativeComponentJavascriptPath);
     }
   }
   return(importedNodeModules);
}
/*------------------------------------------------------------------------------

@name       handleReactiveXImports - reactiveX modules
                                                                              */
                                                                             /**
            Get any imported reactiveX modules for the specified component.

@param      logger         logger

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Set<String> handleReactiveXImports(
   TreeLogger logger)
{
   Set<String> importedNodeModules = new HashSet<>();
   String      prefix              = "io.reactjava.client.core.rxjs.";

   for (String imported : getComponentReactiveXImports())
   {
      if (!imported.startsWith(prefix))
      {
         throw new IllegalStateException(
            "All reactivex imports are expected to start with '"
               + prefix + "': " + imported);
      }

      String target = imported.substring(prefix.length());
             target = target.substring(target.lastIndexOf('.') + 1);
             target = "rxjs." + target;

      String path = IConfiguration.getNodeModuleJavascript(target, logger);
      if (path != null)
      {
         importedNodeModules.add(path);
      }
   }
   return(importedNodeModules);
}
/*------------------------------------------------------------------------------

@name       hasPropertiesConstructor - test if has a 'Properties' constructor
                                                                              */
                                                                             /**
            Test whether has a single arg constructor, where the constructor
            type has the simple classname "Properties".

@return     true iff has a single arg 'Properties' constructor.

@history    Sat Jan 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
boolean hasPropertiesConstructor()
{
   boolean bInstantiable = false;

   for (BodyDeclaration member : type.getMembers())
   {
      if (member.isConstructorDeclaration())
      {
         NodeList<Parameter> params =
            ((ConstructorDeclaration)member).getParameters();

         if (params.size() == 1)
         {
            Type paramType = params.get(0).getType();
            if (paramType.isClassOrInterfaceType())
            {
               String simpleName =
                  ((ClassOrInterfaceType)paramType).getNameAsString();

               if ("Properties".equals(simpleName))
               {
                  bInstantiable = true;
                  break;
               }
            }
         }
      }
   }

   return(bInstantiable);
}
/*------------------------------------------------------------------------------

@name       initialize - assign the superclass and other attributes
                                                                              */
                                                                             /**
            Assign the superclass and other attributes.

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void initialize()
{
   if (isAppComponent())
   {
      getAppComponents().add(this);
      getComponents().add(this);
   }
   else if (isComponent())
   {
      getComponents().add(this);
   }
   else if (isProvider())
   {
      getProviders().add(this);
   }
}
/*------------------------------------------------------------------------------

@name       initializeForUpdates - prepare for updates
                                                                              */
                                                                             /**
            Prepare for updates.

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void initializeForUpdates()
{
   targetApp = null;
   getAppDependenciesByTag().clear();
   getAppDependenciesByClassname().clear();
}
/*------------------------------------------------------------------------------

@name       isAppComponent - test whether is an app component
                                                                              */
                                                                             /**
            Test whether is an app component.

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean isAppComponent()
{
   if (bAppComponent == null)
   {
      bAppComponent = getAppComponents().contains(this)
         || forTag(AppComponentTemplate.class.getSimpleName())
               .isAssignableFrom(this);
      if (bAppComponent)
      {
         bNativeComponent = false;
         bComponent       = true;
         bProvider        = false;
         bInstantiable    =
            !type.isAbstract()
            && !TypeDsc.kROOT_COMPONENT_INSTANCE.equals(this)
            && !TypeDsc.kROOT_APP_COMPONENT_TEMPLATE_INSTANCE.equals(this);
      }
   }
   return(bAppComponent);
}
/*------------------------------------------------------------------------------

@name       isAssignableFrom - test if specified class is a subclass
                                                                              */
                                                                             /**
            Test if the specified class is a subclass of this class

@param      test    candidate subclass

@return     true iff the specified class is a subclass of this class

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean isAssignableFrom(
   TypeDsc test)
{
   boolean bSubclass = false;

   for (TypeDsc chase = test, last = null;
         chase != null && chase != kOBJECT_INSTANCE && last != chase;
         last = chase, chase = chase.getSuperType())
   {
      if (this.equals(chase))
      {
         bSubclass = true;
         break;
      }
   }

   return(bSubclass);
}
/*------------------------------------------------------------------------------

@name       isComponent - test whether is a component
                                                                              */
                                                                             /**
            Test whether is a component.

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean isComponent()
{
   if (bComponent == null)
   {
      bComponent =
         getComponents().contains(this)
            || forTag(Component.class.getSimpleName()).isAssignableFrom(this);

      if (bComponent)
      {
         bNativeComponent = false;
         bProvider        = false;
         bInstantiable    =
            !type.isAbstract()
            && !TypeDsc.kROOT_COMPONENT_INSTANCE.equals(this)
            && !TypeDsc.kROOT_APP_COMPONENT_TEMPLATE_INSTANCE.equals(this);
      }
   }
   return(bComponent);
}
/*------------------------------------------------------------------------------

@name       isComponentAppDependency - if a component app dependency
                                                                              */
                                                                             /**
            Test whether is a component that is an app dependency.

@return     true iff a component app dependency

@param      candidate      candidate typedsc

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean isComponentAppDependency(
   TypeDsc candidate)
{
   boolean bComponentAppDependency =
      candidate != null
         && candidate.isComponent()
         && TypeDsc.getAppDependenciesByClassname()
               .get(candidate.classname) != null;

   return(bComponentAppDependency);
}
/*------------------------------------------------------------------------------

@name       isComponentAppDependencyClassname - if a component app dependency
                                                                              */
                                                                             /**
            Test whether is a component that is an app dependency.

@return     the corresponding TypeDsc if is a component that is an app
            dependency; otherwise, null

@param      classname      candidte classname

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean isComponentAppDependencyClassname(
   String classname)
{
   return(isComponentAppDependency(TypeDsc.forClassname(classname)));
}
/*------------------------------------------------------------------------------

@name       isComponentAppDependencyTag - if a component app dependency
                                                                              */
                                                                             /**
            Test whether is a component that is an app dependency.

@return     the corresponding TypeDsc if is a component that is an app
            dependency; otherwise, null

@param      tag      candidate tag

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean isComponentAppDependencyTag(
   String tag)
{
   return(isComponentAppDependency(TypeDsc.forTag(tag)));
}
/*------------------------------------------------------------------------------

@name       isInstantiatable - test whether is instantiable
                                                                              */
                                                                             /**
            Test whether is instantiable. An instantiable component is one
            that is neither the root Component nor the root AppComponentTemplate
            and is not abstract. An instantiable provider has a single argument
            constructor whose argument is the Properties type, and is not
            abstract.

@return     true iff is instantiable.

@history    Sat Jan 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean isInstantiable()
{
   return(bInstantiable);
}
/*------------------------------------------------------------------------------

@name       isNativeComponent - test whether is a native component
                                                                              */
                                                                             /**
            Test whether is a native component.

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean isNativeComponent()
{
   if (bNativeComponent == null)
   {
      bNativeComponent =
         getNativeComponents().contains(this)
            || nativeComponentJavascriptPath != null;

      if (bNativeComponent)
      {
         bAppComponent = false;
         bComponent = false;
         bProvider  = false;
      }
   }
   return(bNativeComponent);
}
/*------------------------------------------------------------------------------

@name       isNativeComponentTagName - check whether native component tag name
                                                                              */
                                                                             /**
            Check whether native component tag name.

@return     true iff native component tag name

@param      tagName     candidate

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean isNativeComponentTagName(
   String     tagName,
   TreeLogger logger)
{
   boolean bLibraryTag = false;
   TypeDsc candidate   = TypeDsc.getAppDependenciesByTag().get(tagName);
   if (candidate != null)
   {
      bLibraryTag = candidate.bNativeComponent;
   }

   return(bLibraryTag);
}
/*------------------------------------------------------------------------------

@name       isProvider - test whether is a provider
                                                                              */
                                                                             /**
            Test whether is a provider.

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean isProvider()
{
   if (bProvider == null)
   {
      bProvider =
         getProviders().contains(this)
            ||  forTag(Provider.class.getSimpleName()).isAssignableFrom(this);

      if (bProvider)
      {
         bNativeComponent = false;
         bAppComponent = false;
         bComponent       = false;
         bInstantiable    =
            !type.isAbstract()
            && !TypeDsc.kROOT_PROVIDER_INSTANCE.equals(this)
            && hasPropertiesConstructor();
      }
   }
   return(bProvider);
}
/*------------------------------------------------------------------------------

@name       isReactJavaComponentTagName - check if ReactJava component tag name
                                                                              */
                                                                             /**
            Check whether is ReactJava component tag name.

@return     true iff is ReactJava component tag name

@param      tagName     candidate

@history    Wed Sep 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean isReactJavaComponentTagName(
   String     tagName,
   TreeLogger logger)
{
   return(TypeDsc.forTag(tagName) != null);
}
/*------------------------------------------------------------------------------

@name       isReactTagName - check whether react tag name
                                                                              */
                                                                             /**
            Check whether react tag name.

@return     true iff react tag name

@param      tagName     candidate

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean isReactTagName(
   String tagName)
{
   boolean bReactTagName =
      tagName != null && kREACT_TAGS.get(tagName) != null;

   if (bReactTagName)
   {
                                       // at runtime, React objects if        //
                                       // React.Fragment has properties other //
                                       // than 'key' and 'children' but       //
                                       // currently need an id in the         //
                                       // component fcn, since                //
                                       // Component.setId() cannot have null  //
                                       // assignment value and for now the    //
                                       // component takes the id of the root  //
                                       // element...                          //
      throw new IllegalStateException(
         "React.Fragment not currently supported,  would 'div' work instead?");
   }

   return(bReactTagName);
}
/*------------------------------------------------------------------------------

@name       isRenderableComponent - test if is a renderable component
                                                                              */
                                                                             /**
            Test whether the specified type is a renderable component,
            meaning it is an instantiable component that has a zero argument
            final render() method.

@return     true iff the specified type is a renderable component.

@history    Sat Jan 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
boolean isRenderableComponent()
{
   boolean bRenderable =
      isComponent() && isInstantiable() && getComponentRenderMethod() != null;

   return(bRenderable);
}
/*------------------------------------------------------------------------------

@name       isStandardTagName - check whether standard tag name
                                                                              */
                                                                             /**
            Check whether standard tag name.

@return     true iff standard tag name

@param      tagName     candidate

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean isStandardTagName(
   String tagName)
{
   return(tagName != null && kSTD_TAGS.get(tagName) != null);
}
/*------------------------------------------------------------------------------

@name       putType - put type descriptor for specified tag
                                                                              */
                                                                             /**
            Put type descriptor for specified tag.

@param      typeDsc     type descriptor

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static void putType(
   TypeDsc typeDsc)
{
   types.add(typeDsc);
   byTag.put(typeDsc.tag, typeDsc);

   if (typeDsc.classname != null)
   {
      byClassname.put(typeDsc.classname, typeDsc);
   }
                                       // do this part of init here since     //
                                       // these are not created until after   //
                                       // the types map has been created      //
   if (typeDsc.isNativeComponent())
   {
      getNativeComponents().add(typeDsc);
   }
}
/*------------------------------------------------------------------------------

@name       removeType - remove type descriptor
                                                                              */
                                                                             /**
            Remove type descriptor. Remove the descriptor from any collections.

@param      typeDsc     type descriptor

@history    Thu Sep 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static void removeType(
   TypeDsc typeDsc)
{
   for (TypeDsc subtype : typeDsc.getSubTypes())
   {
      subtype.superTypeDsc = null;
   }

   getComponents().remove(typeDsc);
   getAppComponents().remove(typeDsc);
   getProviders().remove(typeDsc);
   getNativeComponents().remove(typeDsc);

   getTypes().remove(typeDsc);
   byTag.remove(typeDsc.tag);
   getAppDependenciesByTag().remove(typeDsc.tag);

   if (typeDsc.classname != null)
   {
      byClassname.remove(typeDsc.classname, typeDsc);
      getAppDependenciesByClassname().remove(typeDsc.classname);
   }
   if (typeDsc == targetApp)
   {
      targetApp = null;
   }
}
/*------------------------------------------------------------------------------

@name       resolveReplacementTagName - resolve tag with any replaced value
                                                                              */
                                                                             /**
            Resolve tagName with any replaced value.

@return     original tagName or null, if not replaced

@param      replacement    replacement for original tag

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes      see replaceComponentTagsWithSymbols()
                                                                              */
//------------------------------------------------------------------------------
public String resolveReplacementTagName(
   String replacement)
{
   return(tagByReplacementMap.get(replacement));
}
/*------------------------------------------------------------------------------

@name       resolveTagNameIfReplaced - resolve tag with any replaced value
                                                                              */
                                                                             /**
            Resolve tagName with any replaced value.

@return     markup with replaced component tag names

@param      tagName     possible replacement for original tag

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes      see replaceComponentTagsWithSymbols()
                                                                              */
//------------------------------------------------------------------------------
public String resolveTagNameIfReplaced(
   String  tagName)
{
   String original = tagByReplacementMap.get(tagName);
   return(original != null ? original : tagName);
}
/*------------------------------------------------------------------------------

@name       resolveStringLiteral - resolve string literal
                                                                              */
                                                                             /**
            Resolve string literal as either itself or the value of a field.

@return     resolved string literal

@param      target      target string literal

@history    Sat Sep 26, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String resolveStringLiteral(
   String target)
{
   String resolved = target;
   if (!target.startsWith("\"") || !target.endsWith("\""))
   {
                                       // find a field with the target name   //
      if (!type.getFieldByName(target).isPresent())
      {
         throw new IllegalStateException(
            "If a string literal is a variable, "
          + "it must be a component field for now");
      }
      FieldDeclaration field = type.getFieldByName(target).get();
      if (!field.getVariable(0).getInitializer().isPresent())
      {
         throw new IllegalStateException(
            "If a string literal is a variable, "
          + "it must have been initialized with a value");
      }

      Expression expression = field.getVariable(0).getInitializer().get();
      if (expression instanceof StringLiteralExpr)
      {
         resolved = ((StringLiteralExpr)expression).getValue();
      }
      else if (expression instanceof BinaryExpr)
      {
         resolved =
            resolveStringLiteralBinaryExpression((BinaryExpr)expression);
      }
      else
      {
         throw new IllegalStateException(
            "If a string literal is a variable, "
          + "its value must be a string literal or a string literal expression");
      }
   }
   return(resolved);
}
/*------------------------------------------------------------------------------

@name       resolveStringLiteralBinaryExpression - resolve string literal expr
                                                                              */
                                                                             /**
            Resolve string literal binary expression.

@return     resolved string literal

@param      binary      target string literal binary expression

@history    Sat Sep 26, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String resolveStringLiteralBinaryExpression(
   BinaryExpr binary)
{
   return(resolveStringLiteralBinaryExpression(binary, ""));
}
/*------------------------------------------------------------------------------

@name       resolveStringLiteralBinaryExpression - resolve string literal expr
                                                                              */
                                                                             /**
            Resolve string literal binary expression.

@return     resolved string literal

@param      binary      target string literal binary expression

@history    Sat Sep 26, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String resolveStringLiteralBinaryExpression(
   BinaryExpr binary,
   String     resolved)
{
   if (binary.getOperator() != Operator.PLUS)
   {
      throw new IllegalStateException(
         "If a string literal is a variable "
       + "its value must be a string literal expression");
   }

   Expression expression = binary.getLeft();
   if (expression instanceof BinaryExpr)
   {
      resolved +=
         resolveStringLiteralBinaryExpression((BinaryExpr)expression, resolved);
   }
   else if (expression instanceof StringLiteralExpr)
   {
      resolved += ((StringLiteralExpr)expression).getValue();
   }

   expression = binary.getRight();
   if (!(expression instanceof StringLiteralExpr))
   {
      throw new IllegalStateException(
         "If a string literal is a variable "
       + "its value must be a string literal expression");
   }
   resolved += ((StringLiteralExpr) expression).getValue();

   return(resolved);
}
}//====================================// end TypeDsc ========================//
