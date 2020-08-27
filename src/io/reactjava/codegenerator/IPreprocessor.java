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
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
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
String kKEY_PARSED_APPS                = "apps";
String kKEY_PARSED_COMPONENTS          = "components";
String kKEY_PARSED_PROVIDER_INTERFACES = "providerInterfaces";
String kKEY_PARSED_PROVIDERS           = "providers";
String kKEY_PARSED_TYPES               = "types";

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
      logger.INFO,
      "IPreprocessor.allPreprocessors(): entered for " + classname);

                                       // parse each of the candidates,       //
                                       // building the providers and          //
                                       // components maps                     //
   parseCandidates(providerAndComponentCandidates, logger);

   byte[] processed = contentBytes;
   for (Class preprocessor : preprocessors)
   {
      processed =
         ((IPreprocessor)preprocessor.newInstance()).process(
            classname, processed, encoding, logger);
   }

   logger.log(logger.INFO, "IPreprocessor.allPreprocessors(): exiting");
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

@name       getAssignableTo - get all types assignable to specified type
                                                                              */
                                                                             /**
            Get all types assignable to specified type; that is, all subtypes
            and their substypes.

@return     map of types assignable to specified type

@param      root        specified type
@param      logger      logger

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static Map<String,TypeDsc> getAssignableTo(
   TypeDsc    root,
   TreeLogger logger)
{
   Map<String,TypeDsc> assignables = new HashMap<>();
   Map<String,TypeDsc> types       = getParsedTypes();
   for (TypeDsc chaseDsc : types.values())
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
            if (chaseDsc.superType == null)
            {
               break;
            }

            chaseDsc = getSuperType(chaseDsc, logger);
         }
         if (bAssignable)
         {
            for (TypeDsc assignable : assignableDscs)
            {
               assignables.put(getClassname(assignable.type), assignable);
            }
         }
      }
   }
   return(assignables);
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
   else if (type.equals(TypeDsc.kROOT_IPROVIDER))
   {
      classname = IProvider.class.getName();
   }
   else
   {
      classname = ((Optional<String>)type.getFullyQualifiedName()).get();
   }
   return(classname);
}
/*------------------------------------------------------------------------------

@name       getComponentRenderCSSProcedure - get any renderCSS() procedure
                                                                              */
                                                                             /**
            Get any renderCSS() procedure for the specified component.

@return     any renderCSS() procedure for the specified component.

@param      component      target component

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static MethodDeclaration getComponentRenderCSSProcedure(
   TypeDsc component)
{
   MethodDeclaration method = null;
                                       // find any renderCSS() procedure      //
   for (MethodDeclaration chase : component.type.getMethodsByName("renderCSS"))
   {
      if (chase.getParameters().size() == 0)
      {
         method = chase;
      }
   }
   return(method);
}
/*------------------------------------------------------------------------------

@name       getComponentRenderProcedure - get any component render() procedure
                                                                              */
                                                                             /**
            Get any render() procedure for the specified component.

@return     any render() procedure for the specified component.

@param      component      target component

@history    Sun Jan 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static MethodDeclaration getComponentRenderProcedure(
   TypeDsc component)
{
   MethodDeclaration method = null;
                                       // find any render() procedure         //
   for (MethodDeclaration chase : component.type.getMethodsByName("render"))
   {
      if (chase.getParameters().size() == 0)
      {
         if (!chase.isFinal())
         {
            throw new IllegalStateException(
               "render() method for " + getClassname(component.type)
             + " must be final");
         }

         method = chase;
         break;
      }
   }
   return(method);
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

@name       getDirectImplementors - get all that implement the specified type
                                                                              */
                                                                             /**
            Get all types that directly implement the specified interface type.

@return     map of types assignable to specified type

@param      root        specified interface type
@param      logger      logger

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static Map<String,TypeDsc> getDirectImplementors(
   TypeDsc    root,
   TreeLogger logger)
{
   Map<String,TypeDsc> implementors = new HashMap<>();
   Map<String,TypeDsc> types        = getParsedTypes();
   for (TypeDsc chaseDsc : types.values())
   {
      if (!chaseDsc.type.isInterface())
      {
         NodeList<ClassOrInterfaceType> implementedInterfaces =
            chaseDsc.type.getImplementedTypes();

         for (ClassOrInterfaceType type : implementedInterfaces)
         {
            TypeDsc implemented = getType(type, chaseDsc, logger);
            if (root.equals(implemented))
            {
               implementors.put(getClassname(chaseDsc.type), chaseDsc);
            }
         }
      }
   }
   return(implementors);
}
/*------------------------------------------------------------------------------

@name       getImportForSimpleName - get typedDsc for simple name
                                                                              */
                                                                             /**
            Get import with specified simple name for specified compilation
            unit.

@param      importSimpleName     import simple name
@param      cu                   compilation unti
@param      logger               compiler logger

@history    Sat Jan 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static TypeDsc getImportForSimpleName(
   String          importSimpleName,
   CompilationUnit cu,
   TreeLogger      logger)
{
   TypeDsc importType = null;

   for (ImportDeclaration targetImport : cu.getImports())
   {
      String importName = targetImport.getName().toString();
      if (importName.endsWith("." + importSimpleName))
      {
         importType = getParsedTypes().get(importName);
         break;
      }
   }

   return(importType);
}
/*------------------------------------------------------------------------------

@name       getParsedApps - get map of parsed apps by classname
                                                                              */
                                                                             /**
            Get map of parsed apps by classname.

@return     map of parsed apps by classname

@history    Thu Aug 20, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static Map<String,TypeDsc> getParsedApps(
   TreeLogger logger)
{
   Map<String,TypeDsc> apps = parsed.get(kKEY_PARSED_APPS);
   if (apps.size() == 0)
   {
                                       // lazily create                       //
      apps.putAll(
         getAssignableTo(TypeDsc.kROOT_APP_COMPONENT_TEMPLATE_INSTANCE, logger));
   }
   return(apps);
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
static Map<String,TypeDsc> getParsedComponents(
   TreeLogger logger)
{
   Map<String,TypeDsc> components = parsed.get(kKEY_PARSED_COMPONENTS);
   if (components.size() == 0)
   {
                                       // lazily create                       //
      components.putAll(
         getAssignableTo(TypeDsc.kROOT_COMPONENT_INSTANCE, logger));

      components.putAll(
         getAssignableTo(TypeDsc.kROOT_APP_COMPONENT_TEMPLATE_INSTANCE, logger));
   }
   return(components);
}
/*------------------------------------------------------------------------------

@name       getParsedProviderInterfaces - get map of parsed provider interfaces
                                                                              */
                                                                             /**
            Get map of parsed provider interfaces by classname.

@return     map of parsed provider interfaces by classname

@history    Thu Jan 09, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static Map<String,TypeDsc> getParsedProviderInterfaces(
   TreeLogger logger)
{
   Map<String,TypeDsc> iProviders = parsed.get(kKEY_PARSED_PROVIDER_INTERFACES);
   if (iProviders.size() == 0)
   {
                                       // lazily create                       //
      iProviders.putAll(
         getAssignableTo(TypeDsc.kROOT_IPROVIDER_INSTANCE, logger));
   }
   return(iProviders);
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
static Map<String,TypeDsc> getParsedProviders(
   TreeLogger logger)
{
   Map<String,TypeDsc> providers = parsed.get(kKEY_PARSED_PROVIDERS);
   if (providers.size() == 0)
   {
                                       // get direct implementors of each of  //
                                       // the provider interfaces             //
      Map<String,TypeDsc> direct     = new HashMap<>();
      Map<String,TypeDsc> iProviders = getParsedProviderInterfaces(logger);
      for (TypeDsc iProvider : iProviders.values())
      {
         direct.putAll(getDirectImplementors(iProvider, logger));
      }
      providers.putAll(direct);
                                       // add all assignable to the direct    //
                                       // implementors as well                //
      Map<String,TypeDsc> indirect = new HashMap<>();
      for (TypeDsc directProvider : direct.values())
      {
         providers.putAll(getAssignableTo(directProvider, logger));
      }
   }
   return(providers);
}
/*------------------------------------------------------------------------------

@name       getParsedProvidersAndComponents - get providers and components
                                                                              */
                                                                             /**
            Get map of parsed providers and components by classname.

@return     map of parsed providers and components by classname

@history    Thu Aug 20, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static Map<String,TypeDsc> getParsedProvidersAndComponents(
   TreeLogger logger)
{
   Map<String,TypeDsc> providersAndComponents = new HashMap<>();
   providersAndComponents.putAll(getParsedProviders(logger));
   providersAndComponents.putAll(getParsedComponents(logger));

   return(providersAndComponents);
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

@name       getSuperType - get supertype for specified type
                                                                              */
                                                                             /**
            Get supertype for specified type.

@param      typeDsc        target typeDsc
@param      logger         compiler logger

@history    Sat Jan 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static TypeDsc getSuperType(
   TypeDsc    typeDsc,
   TreeLogger logger)
{
   TypeDsc superTypeDsc = null;
   if (typeDsc.superType != null)
   {
      superTypeDsc =
         getParsedTypes().get(typeDsc.superType.getFullyQualifiedName());
   }
   if (superTypeDsc == null)
   {
      NodeList<ClassOrInterfaceType> extendedTypes =
         typeDsc.type.getExtendedTypes();

      for (ClassOrInterfaceType extendedType : extendedTypes)
      {
         superTypeDsc = getType(extendedType, typeDsc, logger);
         if (superTypeDsc != null)
         {
            typeDsc.superType = superTypeDsc.type;
         }
      }
   }
   return(superTypeDsc);
}
/*------------------------------------------------------------------------------

@name       getType - get typedDsc for specified type
                                                                              */
                                                                             /**
            Get typedDsc for specified type.

@param      type        target type
@param      logger      compiler logger

@history    Sat Jan 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static TypeDsc getType(
   ClassOrInterfaceType type,
   TypeDsc              refType,
   TreeLogger           logger)
{
   String  refClassname    = getClassname(refType.type);
   String  typeClassname   = type.toString();
   String  simpleClassname = type.getName().asString();
   TypeDsc typeDsc         = getParsedTypes().get(typeClassname);

   if (typeDsc == null)
   {
      String typePackage =
         refType.cu.getPackageDeclaration().get().getName().asString();

      String superClassnameWPackage = typePackage + "." + typeClassname;

      typeDsc = getParsedTypes().get(superClassnameWPackage);
   }
   if (typeDsc == null)
   {
      if (typeClassname.equals(simpleClassname))
      {
         typeDsc = getImportForSimpleName(simpleClassname, refType.cu, logger);
      }
   }
   if (typeDsc == null)
   {
                              // chase classname prefix of outer     //
                              // classes starting with same          //
                              // classname prefix as type            //
      String superPrefix = refClassname;
      for (int idx = superPrefix.lastIndexOf('.');
            idx > 0;
            idx = superPrefix.lastIndexOf('.'))
      {
         superPrefix = superPrefix.substring(0, idx);

                              // check for existing type             //

         typeClassname = superPrefix + "." + simpleClassname;
         typeDsc       = getParsedTypes().get(typeClassname);
         if (typeDsc != null)
         {
            break;
         }
       }
   }

   return(typeDsc);
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

@name       isAppComponent - test if is an app component
                                                                              */
                                                                             /**
            Test whether the specified type is an app component, meaning it is
            an instantiable subclass of AppComponentTemplate.

@return     true iff the specified type is an instantiable app component.

@param      type     target type

@history    Sat Jan 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static boolean isAppComponent(
   TypeDsc type)
{
   boolean bInstantiable =
      getParsedApps(null).get(getClassname(type.type)) != null
         && !TypeDsc.kROOT_APP_COMPONENT_TEMPLATE_INSTANCE.equals(type)
         && !type.type.isAbstract();

   return(bInstantiable);
}
/*------------------------------------------------------------------------------

@name       isInstantiatableComponent - test if is an instantiable component
                                                                              */
                                                                             /**
            Test whether the specified type is an instantiable component,
            meaning it is a parsedComponent that is not abstract.

@return     true iff the specified type is an instantiable component.

@param      type     target type

@history    Sat Jan 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static boolean isInstantiatableComponent(
   TypeDsc type)
{
   boolean bInstantiable =
      getParsedComponents(null).get(getClassname(type.type)) != null
         && !TypeDsc.kROOT_COMPONENT_INSTANCE.equals(type)
         && !TypeDsc.kROOT_APP_COMPONENT_TEMPLATE_INSTANCE.equals(type)
         && !type.type.isAbstract();

   return(bInstantiable);
}
/*------------------------------------------------------------------------------

@name       isRenderableComponent - test if is a renderable component
                                                                              */
                                                                             /**
            Test whether the specified type is a renderable component,
            meaning it is an instantiable component that has a zero argument
            final render() method.

@return     true iff the specified type is a renderable component.

@param      type     target type

@history    Sat Jan 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static boolean isRenderableComponent(
   TypeDsc type)
{
   boolean bRenderable =
      isInstantiatableComponent(type)
         && getComponentRenderProcedure(type) != null;

   return(bRenderable);
}
/*------------------------------------------------------------------------------

@name       isInstantiatableProvider - test whether is an instantiable provider
                                                                              */
                                                                             /**
            Test whether the specified type is an instantiable provider,
            meaning it is a parsedProvider that is not abstract, with  a single
            arg constructor, where the constructor type has the simple classname
            "Properties".

@return     true iff the specified type is an instantiable provider.

@param      type     target type

@history    Sat Jan 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static boolean isInstantiatableProvider(
   TypeDsc type)
{
   boolean bInstantiable = false;

   if (getParsedProviders(null).get(getClassname(type.type)) != null
         && !type.type.isAbstract())
   {
      for (BodyDeclaration member : type.type.getMembers())
      {
         if (member.isConstructorDeclaration())
         {
            NodeList<Parameter> params =
               ((ConstructorDeclaration)member).getParameters();

            if (params.size() == 1)
            {
               com.github.javaparser.ast.type.Type paramType =
                  params.get(0).getType();

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
   }

   return(bInstantiable);
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
      logger.log(logger.INFO, "IPreprocessor.parseCandidates(): entered");

      parsed.put(kKEY_PARSED_APPS,                new HashMap<>());
      parsed.put(kKEY_PARSED_COMPONENTS,          new HashMap<>());
      parsed.put(kKEY_PARSED_PROVIDER_INTERFACES, new HashMap<>());
      parsed.put(kKEY_PARSED_PROVIDERS,           new HashMap<>());
      parsed.put(kKEY_PARSED_TYPES,               new HashMap<>());

      getParsedTypes().put(
         Component.class.getName(),
         TypeDsc.kROOT_COMPONENT_INSTANCE);

      getParsedTypes().put(
         AppComponentTemplate.class.getName(),
         TypeDsc.kROOT_APP_COMPONENT_TEMPLATE_INSTANCE);

      getParsedTypes().put(
         IProvider.class.getName(),
         TypeDsc.kROOT_IPROVIDER_INSTANCE);

                                       // generate the types map sans supers  //
      for (String classname : providerAndComponentCandidates.keySet())
      {
         logger.log(
            logger.INFO,
            "IPreprocessor.parseCandidates(): parsing " + classname);

         String          src = providerAndComponentCandidates.get(classname);
         CompilationUnit cu  = StaticJavaParser.parse(src);

                                       // setup lexical preserving printing   //
         LexicalPreservingPrinter.setup(cu);

         generateTypesMap(src, cu);
      }
                                       // add the supers to the types map     //
      for (TypeDsc typeDsc : getParsedTypes().values())
      {
         if (!TypeDsc.kROOT_APP_COMPONENT_TEMPLATE_INSTANCE.equals(typeDsc)
               && !TypeDsc.kROOT_COMPONENT_INSTANCE.equals(typeDsc))
         {
            getSuperType(typeDsc, logger);
         }
      }
      logger.log(logger.INFO, "IPreprocessor.parseCandidates(): exiting");
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
@param      logger         compiler logger

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
byte[] process(
   String             classname,
   byte[]             contentBytes,
   String             encoding,
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
      String classname;
      String src;
      int    numComponentsExpected;
      int    numProvidersExpected;

      if (false)
      {
         classname  = "io.reactjava.client.core.react.Component";
         src =
            IJSXTransform.getFileAsString(
               new File(
                  "/Users/brianm/working/IdeaProjects/ReactJava/ReactJava/src/"
                + "io/reactjava/client/core/react/Component.java"),
               null);

         providerAndComponentCandidates.put(classname, src);
         numComponentsExpected = 1;
         numProvidersExpected  = 0;
      }
      else if (false)
      {
         classname  = "io.reactjava.codegenerator.tests.allinonefile.App";
         src =
            IJSXTransform.getFileAsString(
               new File(
                  "/Users/brianm/working/IdeaProjects/ReactJava/ReactJava/src/"
                + "io/reactjava/codegenerator/tests/"
                + "allinonefile/App.java"),
               null);

         providerAndComponentCandidates.put(classname, src);
         numComponentsExpected = 14;
         numProvidersExpected  = 8;
      }
      else if (true)
      {
         classname  = "io.reactjava.client.components.generalpage.ContentPage";
         src =
            IJSXTransform.getFileAsString(
               new File(
                  "/Users/brianm/working/IdeaProjects/ReactJava/ReactJava/src/"
                + "io/reactjava/client/components/generalpage/ContentPage.java"),
               null);

         providerAndComponentCandidates.put(classname, src);
         numComponentsExpected = 3;
         numProvidersExpected  = 0;
      }

      parseCandidates(providerAndComponentCandidates, logger);

                                       // get components lazily               //
      Map<String,TypeDsc> components = getParsedComponents(logger);

                                       // get providers lazily                //
      Map<String,TypeDsc> providers = getParsedProviders(logger);

      if (components.size() == numComponentsExpected
            && providers.size() == numProvidersExpected)
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
                                       // Component root declaration          //
public static final ClassOrInterfaceDeclaration kROOT_COMPONENT =
   new ClassOrInterfaceDeclaration(
      null,                            // tokenRange                          //
      new NodeList<>(),                // modifiers                           //
      new NodeList<>(),                // annotations                         //
      false,                           // isInterface                         //
                                       // name                                //
      new SimpleName("io.react.client.core.react.Component"),
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
      new SimpleName("io.react.client.core.react.AppComponentTemplate"),
      new NodeList<>(),                // typeParameters                      //
      new NodeList<>(),                // extendedTypes                       //
      new NodeList<>(),                // implementedTypes                    //
      new NodeList<>());               // members                             //

                                       // IProvider root declaration          //
public static final ClassOrInterfaceDeclaration kROOT_IPROVIDER =
   new ClassOrInterfaceDeclaration(
      null,                            // tokenRange                          //
      new NodeList<>(),                // modifiers                           //
      new NodeList<>(),                // annotations                         //
      true,                            // isInterface                         //
                                       // name                                //
      new SimpleName("io.react.client.core.react.IProvider"),
      new NodeList<>(),                // typeParameters                      //
      new NodeList<>(),                // extendedTypes                       //
      new NodeList<>(),                // implementedTypes                    //
      new NodeList<>());               // members                             //

                                       // Component root typeDsc              //
public static final TypeDsc kROOT_COMPONENT_INSTANCE =
   new TypeDsc(kROOT_COMPONENT, null, null, null);

                                       // AppComponentTemplate root typeDsc   //
public static final TypeDsc kROOT_APP_COMPONENT_TEMPLATE_INSTANCE =
   new TypeDsc(kROOT_APP_COMPONENT_TEMPLATE, null, null, kROOT_COMPONENT);

                                       // IProvider root typeDsc              //
public static final TypeDsc kROOT_IPROVIDER_INSTANCE =
   new TypeDsc(kROOT_IPROVIDER, null, null, null);

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
