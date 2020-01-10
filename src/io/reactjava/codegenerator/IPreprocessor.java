/*==============================================================================

name:       IPreprocessor.java

purpose:    ReactJava Code Generation Preprocessor Interface.

            Since a GWT Oracle is not available yet, uses JavaParser to parse
            the various provider and component classes to find all providers
            and components of top and inner classes of all associated source
            files.

history:    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created
            Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) added JavaParser

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.codegenerator;
                                       // imports --------------------------- //
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.dev.util.log.AbstractTreeLogger;
import com.google.gwt.dev.util.log.PrintWriterTreeLogger;
import io.reactjava.client.core.react.AppComponentTemplate;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.IProvider;
import io.reactjava.jsx.IConfiguration;
import io.reactjava.jsx.IJSXTransform;
import io.reactjava.jsx.JSXTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
                                       // IPreprocessor ======================//
public interface IPreprocessor
{
                                       // class constants --------------------//
                                       // parsed map keys                     //
String kKEY_PARSED_COMPONENTS = "components";
String kKEY_PARSED_PROVIDERS  = "providers";
String kKEY_PARSED_TYPES      = "types";
                                       // parsed candidates                   //
Map<String,Map<String,TypeDsc>> parsed  = new HashMap<>();

                                       // all preprocessors on the classpath  //
Class[] preprocessors =
{
   JSXTransform.class,
   ReactCodeGeneratorPreprocessor.class
};
                                       // components noted by a preprocessor  //
                                       // (will be replaced)                  //
Map<String,String> components = new HashMap<>();

                                       // preprocessors tree logger           //
TreeLogger[]       logger = new TreeLogger[1];

/*------------------------------------------------------------------------------

@name       process - process specified source
                                                                              */
                                                                             /**
            Process specified source.

@return     processed source

@param      classname      classname to be processed
@param      contentBytes   cantent to be processed
@param      encoding       cantent encoding

@param      providerAndComponentCandidates
                           provider and component candidates from compiler
                           (com.google.gwt.dev.javac.CompilationStateBuilder
                            .doBuildFrom())

@param      logger         compiler logger

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static byte[] allPreprocessors(
   String             classname,
   byte[]             contentBytes,
   String             encoding,
   Map<String,String> providerAndComponentCandidates,
   TreeLogger         logger)
   throws             Exception
{
   logger = getFileLogger(logger);

   logger.log(
      logger.DEBUG, "IPreprocessor.allPreprocessors(): entered for " + classname);

                                       // parse each of the candidates,       //
                                       // building the providers and          //
                                       // components maps                     //
   parseCandidates(providerAndComponentCandidates, logger);

   byte[] processed = contentBytes;
   for (Class preprocessor : preprocessors)
   {
      processed =
         ((IPreprocessor)preprocessor.newInstance()).process(
            classname, processed, encoding,
            providerAndComponentCandidates, components, logger);
   }

   logger.log(logger.DEBUG, "IPreprocessor.allPreprocessors(): exiting");
   return(processed);
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
static void generateTypesMap(
   String          source,
   CompilationUnit cu)
{
   for (TypeDeclaration type : cu.getTypes())
   {
      if (type instanceof ClassOrInterfaceDeclaration)
      {
         generateTypesMap((ClassOrInterfaceDeclaration)type, source, cu);
      }
   }
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
static void generateTypesMap(
   ClassOrInterfaceDeclaration type,
   String                      source,
   CompilationUnit             cu)
{
   getParsedTypes().put(getClassname(type), new TypeDsc(type, source, cu));
   for (Node child : type.getChildNodes())
   {
      if (child instanceof ClassOrInterfaceDeclaration)
      {
         generateTypesMap((ClassOrInterfaceDeclaration)child, source, cu);
      }
   }
}
/*------------------------------------------------------------------------------

@name       getClassname - get classname for specified type declaration
                                                                              */
                                                                             /**
            Get classname for specified type declaration.

@return     classname for specified type declaration

@param      type     type declaration

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static String getClassname(
   TypeDeclaration type)
{
   String classname;
   if (type.equals(TypeDsc.kROOT_APP_COMPONENT_TEMPLATE))
   {
      classname = AppComponentTemplate.class.getName();
   }
   else if (type.equals(TypeDsc.kROOT_COMPONENT))
   {
      classname = Component.class.getName();
   }
   else
   {
      classname = ((Optional<String>)type.getFullyQualifiedName()).get();
   }
   return(classname);
}
/*------------------------------------------------------------------------------

@name       getFileLogger - get file logger
                                                                              */
                                                                             /**
            Get file logger.

@return     file logger

@param      invocationLogger     logger passed with invocation

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static TreeLogger getFileLogger(
   TreeLogger invocationLogger)
{
   if (logger[0] == null)
   {
      try
      {
         Type maxDetail =
            invocationLogger == null || !(logger[0] instanceof AbstractTreeLogger)
               ? TreeLogger.ALL
               :  ((AbstractTreeLogger)invocationLogger).getMaxDetail();

         File logFile =
            new File(
               IConfiguration.getProjectDirectory(null, invocationLogger),
               "antlog.codegenerator.preprocessor.txt");

         logFile.delete();

         logger[0] = new PrintWriterTreeLogger(logFile);
         ((PrintWriterTreeLogger)logger[0]).setMaxDetail(maxDetail);

         logger[0].log(TreeLogger.INFO, new Date().toString());
         logger[0].log(TreeLogger.INFO, "nanoTime=" + System.nanoTime());
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
   return(logger[0]);
}
/*------------------------------------------------------------------------------

@name       getParsedComponents - get map of parsed components by classname
                                                                              */
                                                                             /**
            Get map of parsed components by classname.

@return     map of parsed components by classname

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static Map<String,TypeDsc> getParsedComponents()
{
   Map<String,TypeDsc> components = parsed.get(kKEY_PARSED_COMPONENTS);
   if (components.size() == 0)
   {
                                       // lazily create                       //
      Map<String,TypeDsc> types = getParsedTypes();
      for (String classname : types.keySet())
      {
         if (components.get(classname) != null)
         {
            continue;
         }

         List<String> classnames = new ArrayList<>();
         boolean      bComponent = false;
         for (String chase = classname; true;)
         {
            classnames.add(chase);

            TypeDsc chaseDsc = types.get(chase);
            if (TypeDsc.kROOT_APP_COMPONENT_TEMPLATE_INSTANCE.equals(chaseDsc)
                  || TypeDsc.kROOT_COMPONENT_INSTANCE.equals(chaseDsc))
            {
               bComponent = true;
               break;
            }
            if (chaseDsc.superType == null)
            {
               break;
            }

            chase = getClassname(chaseDsc.superType);
         }
         if (bComponent)
         {
            for (String componentClassname : classnames)
            {
               components.put(componentClassname, types.get(componentClassname));
            }
         }
      }
   }
   return(components);
}
/*------------------------------------------------------------------------------

@name       getParsedProviders - get map of parsed providers by classname
                                                                              */
                                                                             /**
            Get map of parsed providers by classname.

@return     map of parsed providers by classname

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static Map<String,TypeDsc> getParsedProviders()
{
   Map<String,TypeDsc> providers = parsed.get(kKEY_PARSED_PROVIDERS);
   if (providers.size() == 0)
   {
                                       // lazily create                       //
      Map<String,TypeDsc> types = getParsedTypes();
      for (String classname : types.keySet())
      {
         if (providers.get(classname) != null)
         {
            continue;
         }
         if (Component.class.getName().equals(classname))
         {
            continue;
         }
         if (AppComponentTemplate.class.getName().equals(classname))
         {
            continue;
         }

         List<String> classnames = new ArrayList<>();
         boolean      bProvider  = false;
         TypeDsc      chaseDsc;
         String       chase = classname;

         while (!bProvider)
         {
            classnames.add(chase);
            chaseDsc = types.get(chase);

            NodeList<ClassOrInterfaceType> implementedInterfaces =
               chaseDsc.type.getImplementedTypes();

            if (implementedInterfaces.size() > 0)
            {
               checkImplemented:
               for (ClassOrInterfaceType implemented : implementedInterfaces)
               {
                  String implementedString = implemented.asString();
                  String providerClassname = IProvider.class.getName();
                  if (providerClassname.equals(implementedString))
                  {
                     bProvider = true;
                     break checkImplemented;
                  }
                  else if (IProvider.class.getSimpleName().equals(implementedString))
                  {
                     for (ImportDeclaration imported : chaseDsc.cu.getImports())
                     {
                        if (providerClassname.equals(imported.getName().asString()))
                        {
                           bProvider = true;
                           break checkImplemented;
                        }
                     }
                  }
               }
            }
            if (chaseDsc.superType == null
                  || chaseDsc.superType == TypeDsc.kROOT_COMPONENT
                  || chaseDsc.superType == TypeDsc.kROOT_APP_COMPONENT_TEMPLATE)
            {
               break;
            }
            chase = getClassname(chaseDsc.superType);
         }
         if (bProvider)
         {
            for (String providerClassname : classnames)
            {
               providers.put(providerClassname, types.get(providerClassname));
            }
         }
      }
   }
   return(providers);
}
/*------------------------------------------------------------------------------

@name       getParsedTypes - get map of parsed types by classname
                                                                              */
                                                                             /**
            Get map of parsed types by classname.

@return     map of parsed types by classname

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static Map<String,TypeDsc> getParsedTypes()
{
   return(parsed.get(kKEY_PARSED_TYPES));
}
/*------------------------------------------------------------------------------

@name       initialize - initialize
                                                                              */
                                                                             /**
            Initialize.

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void initialize();

/*------------------------------------------------------------------------------

@name       initialize - initialize all preprocessors
                                                                              */
                                                                             /**
            Initialize all preprocessors.

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void initialize(
   TreeLogger logger)
   throws     Exception
{
   logger.log(logger.DEBUG, "IPreprocessor.initialize(): entered");

   for (Class preprocessor : preprocessors)
   {
      ((IPreprocessor)preprocessor.newInstance()).initialize();
   }

   logger.log(logger.DEBUG, "IPreprocessor.initialize(): exiting");
}
/*------------------------------------------------------------------------------

@name       main - standard main method
                                                                              */
                                                                             /**
            Standard main method.

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void main(
   String[] args)
{
   unitTest(args);
}
/*------------------------------------------------------------------------------

@name       parseCandidate - parse provider and component candidates
                                                                              */
                                                                             /**
            Parse provider and component candidates, storing results in the
            'providers' and 'components' maps.

@param      typeDsc        typeDsc
@param      logger         compiler logger

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void parseCandidate(
   TypeDsc    typeDsc,
   TreeLogger logger)
{
   NodeList<ClassOrInterfaceType> extendedTypes =
      typeDsc.type.getExtendedTypes();

   for (ClassOrInterfaceType extendedType : extendedTypes)
   {
      if (typeDsc.type.isInterface())
      {
         extendedType = extendedType;
      }
      else
      {
         String  classname       = getClassname(typeDsc.type);
         String  superClassname  = extendedType.toString();
         String  simpleClassname = extendedType.getName().asString();
         TypeDsc superTypeDsc    = getParsedTypes().get(superClassname);
         if (superTypeDsc == null)
         {
            String typePackage =
               typeDsc.cu.getPackageDeclaration().get().getName().asString();

            String superClassnameWPackage = typePackage + "." + superClassname;

            superTypeDsc = getParsedTypes().get(superClassnameWPackage);
         }
         if (superTypeDsc == null)
         {
            if (superClassname.equals(simpleClassname))
            {
               for (ImportDeclaration targetImport : typeDsc.cu.getImports())
               {
                  String importName = targetImport.getName().toString();
                  if (importName.endsWith("." + simpleClassname))
                  {
                     superTypeDsc = getParsedTypes().get(importName);
                     break;
                  }
               }
            }
         }
         if (superTypeDsc == null)
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

               superClassname = superPrefix + "." + simpleClassname;
               superTypeDsc   = getParsedTypes().get(superClassname);
               if (superTypeDsc != null)
               {
                  break;
               }
             }
         }
         if (superTypeDsc != null)
         {
            typeDsc.superType = superTypeDsc.type;
         }
      }
   }
                                       // handle any inner classes            //
   for (Node child : typeDsc.type.getChildNodes())
   {
      if (child instanceof ClassOrInterfaceDeclaration)
      {
         String innerClassname = getClassname((TypeDeclaration)child);
         parseCandidate(getParsedTypes().get(innerClassname), logger);
      }
   }

   logger.log(logger.INFO, "IPreprocessor.parseCandidate(): exiting");
}
/*------------------------------------------------------------------------------

@name       parseCandidates - parse provider and component candidates
                                                                              */
                                                                             /**
            Parse provider and component candidates, storing results in the
            'providers' and 'components' maps.

@param      providerAndComponentCandidates
                           provider and component candidates from compiler,
                           where each corresponds to a single source file which
                           may or may not have a number of inner classes that
                           may or may not be additional components or providers

@param      logger         compiler logger

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void parseCandidates(
   Map<String,String> providerAndComponentCandidates,
   TreeLogger         logger)
{
   if (parsed.size() == 0)
   {
      logger.log(logger.INFO, "IPreprocessor.parse(): entered");

      parsed.put(kKEY_PARSED_COMPONENTS, new HashMap<>());
      parsed.put(kKEY_PARSED_PROVIDERS,  new HashMap<>());
      parsed.put(kKEY_PARSED_TYPES,      new HashMap<>());

                                       // seed the root entries               //
      getParsedTypes().put(
         Component.class.getName(),
         TypeDsc.kROOT_COMPONENT_INSTANCE);

      getParsedTypes().put(
         AppComponentTemplate.class.getName(),
         TypeDsc.kROOT_APP_COMPONENT_TEMPLATE_INSTANCE);

                                       // generate the types map sans supers  //
      for (String classname : providerAndComponentCandidates.keySet())
      {
         String          source   = providerAndComponentCandidates.get(classname);
         CompilationUnit cu       = StaticJavaParser.parse(source);

         generateTypesMap(source, cu);
      }
                                       // add the supers to the types map     //
      for (TypeDsc typeDsc : getParsedTypes().values())
      {
         if (!TypeDsc.kROOT_APP_COMPONENT_TEMPLATE_INSTANCE.equals(typeDsc)
               && !TypeDsc.kROOT_COMPONENT_INSTANCE.equals(typeDsc))
         {
            parseCandidate(typeDsc, logger);
         }
      }
      logger.log(logger.INFO, "IPreprocessor.parse(): exiting");
   }
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
                           provider and component candidates from compiler
                           (com.google.gwt.dev.javac.CompilationStateBuilder
                            .doBuildFrom())

@param      components     components identified by preprocessors
@param      logger         compiler logger

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
byte[] process(
   String             classname,
   byte[]             contentBytes,
   String             encoding,
   Map<String,String> providerAndComponentCandidates,
   Map<String,String> components,
   TreeLogger         logger)
   throws             Exception;

/*------------------------------------------------------------------------------

@name       unitTest - unit test method
                                                                              */
                                                                             /**
            Unit test method.

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void unitTest(
   String[] args)
{
   TreeLogger logger = getFileLogger(null);
   try
   {
      Map<String,String> providerAndComponentCandidates = new HashMap<>();

      String classname  =
         "io.reactjava.codegenerator.tests.allinonefile.App";

      String src =
         IJSXTransform.getFileAsString(
            new File(
               "/Users/brianm/working/IdeaProjects/ReactJava/ReactJava/src/"
             + "io/reactjava/codegenerator/tests/"
             + "allinonefile/App.java"),
            null);

      providerAndComponentCandidates.put(classname, src);

      parseCandidates(providerAndComponentCandidates, logger);

                                       // get components lazily               //
      Map<String,TypeDsc> components = getParsedComponents();

                                       // get providers lazily                //
      Map<String,TypeDsc> providers = getParsedProviders();

      if (components.size() == 14 && providers.size() == 6)
      {
         logger.log(TreeLogger.INFO, "IPreprocessor.unitTest(): successful");
      }
      else
      {
         logger.log(TreeLogger.INFO, "IPreprocessor.unitTest(): unsuccessful");
      }
   }
   catch(Throwable t)
   {
      logger.log(TreeLogger.ERROR, t.toString());
   }
}
/*==============================================================================

name:       TypeDsc - type descriptor

purpose:    Type descriptor

history:    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
class TypeDsc
{
                                       // constants ------------------------- //
public static final ClassOrInterfaceDeclaration kROOT_COMPONENT =
   new ClassOrInterfaceDeclaration().setName(
      Component.class.getName());

public static final ClassOrInterfaceDeclaration kROOT_APP_COMPONENT_TEMPLATE =
   new ClassOrInterfaceDeclaration().setName(
      AppComponentTemplate.class.getName());

public static final TypeDsc kROOT_COMPONENT_INSTANCE =
   new TypeDsc(kROOT_COMPONENT, null, null, kROOT_COMPONENT);

public static final TypeDsc kROOT_APP_COMPONENT_TEMPLATE_INSTANCE =
   new TypeDsc(kROOT_COMPONENT, null, null, kROOT_COMPONENT);

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public ClassOrInterfaceDeclaration     // type declaration                    //
                       type;
public String          source;         // associated source file contents     //
public CompilationUnit cu;             // associated compilation unit         //
public ClassOrInterfaceDeclaration     // super type declaration              //
                       superType;
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
   this(null, null, null, null);
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
   ClassOrInterfaceDeclaration type,
   String                      source,
   CompilationUnit             cu)
{
   this(type, source, cu, null);
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
public TypeDsc(
   ClassOrInterfaceDeclaration type,
   String                      source,
   CompilationUnit             cu,
   ClassOrInterfaceDeclaration superType)
{
   this.type      = type;
   this.source    = source;
   this.cu        = cu;
   this.superType = superType;
}
}//====================================// end TypeDsc ========================//
}//====================================// end IPreprocessor ==================//
