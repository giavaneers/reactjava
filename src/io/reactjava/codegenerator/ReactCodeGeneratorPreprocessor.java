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
import io.reactjava.client.core.react.AppComponentTemplate;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.ReactGeneratedCode;
import io.reactjava.client.core.react.Utilities;
import io.reactjava.jsx.IConfiguration;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringEscapeUtils;

                                    // ReactCodeGeneratorPreprocessor =====//
public class ReactCodeGeneratorPreprocessor implements IPreprocessor
{
                                       // class constants --------------------//
                                       // copy of generated                   //
protected static final List<String> kFACTORY_MAP_KEYS = new ArrayList<>();

protected static final String kFACTORY_MAP_COMPONENT_ENTRY =
   "\n"
 + "   kFACTORY_MAP.put(\n"
 + "      \"%classname%\",\n"
 + "      (Function<Properties,Component>)(props) ->\n"
 + "      {\n"
 + "         return(new %classname%());\n"
 + "      });\n";

protected static final String kFACTORY_MAP_PROVIDER_ENTRY =
   "\n"
 + "   kFACTORY_MAP.put(\n"
 + "      \"%classname%\",\n"
 + "      (Function<Properties,IProvider>)(props) ->\n"
 + "      {\n"
 + "         return(new %classname%(props));\n"
 + "      });\n";

                                       // not doing rsrc embedding this way...//
//protected static final String kRESOURCE_MAP_ENTRY =
//   "\n"
// + "   kRESOURCE_MAP.put(\n"
// + "      \"%rsrcpath%\",\n"
// + "      \"%rsrcstring%\");\n";
                                       // class variables ------------------- //
                                       // none                                //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
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

@name       addEmbeddedResource - add embedded resource
                                                                              */
                                                                             /**
            Add embedded resource with specified pathname.

            The specified pathname describes the source path and optionally the
            desired name of the associated external separated by the word 'as'
            as a delimiter which is ignored by this implementation.

@return     resource map entry

@param      rsrcPath          resource pathname
@param      logger            logger

@history    Fri Aug 21, 2020 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
                                       // not doing rsrc embedding this way...//
//protected String addEmbeddedResource(
//   String     rsrcPath,
//   TreeLogger logger)
//   throws     Exception
//{
//   String regex =
//      Utilities.kREGEX_ONE_OR_MORE_WHITESPACE
//    + "as"
//    + Utilities.kREGEX_ONE_OR_MORE_WHITESPACE;
//
//   rsrcPath = rsrcPath.split(regex)[0];
//
//   logger.log(
//      logger.INFO,
//      "ReactCodeGeneratorPreprocessor.addEmbeddedResource(): "
//    + "adding embedded resource=" + rsrcPath);
//
//   String content  = getResourceContent(rsrcPath, logger);
//   String mapEntry =
//      kRESOURCE_MAP_ENTRY
//         .replace("%rsrcpath%", rsrcPath)
//         .replace("%rsrcstring%", content);
//
//   logger.log(
//      logger.INFO,
//      "ReactCodeGeneratorPreprocessor.addEmbeddedResource(): "
//    + "map entry:\n" + mapEntry);
//
//   return(mapEntry);
//}
/*------------------------------------------------------------------------------

@name       getResourceContent - get resource content for the specified path
                                                                              */
                                                                             /**
            Get resource content for the specified path.

@return     resource content for the specified path.

@param      rsrcPath    resource path
@param      logger      logger

@history    Sun Jul 14, 2019 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
                                       // not doing rsrc embedding this way...//
//protected String getResourceContent(
//   String     rsrcPath,
//   TreeLogger logger)
//   throws     Exception
//{
//   String content = "";
//
//   if (rsrcPath.startsWith("http"))
//   {
//      throw new UnsupportedOperationException(
//         "Network paths unsupported for now");
//   }
//   if (rsrcPath.startsWith("file:"))
//   {
//      rsrcPath = rsrcPath.substring("file:".length());
//   }
//
//   if (!rsrcPath.startsWith("/"))
//   {
//                                       // make relative paths absolute        //
//      File projectDir = IConfiguration.getProjectDirectory("war", null);
//      rsrcPath = new File(projectDir, rsrcPath).getAbsolutePath();
//   }
//
//   content = new String(Files.readAllBytes(Paths.get(rsrcPath)), "UTF-8");
//   content = StringEscapeUtils.escapeJava(content);
//
//   return(content);
//}
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
@param      logger         compiler logger

@history    Fri Dec 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public byte[] process(
   String     classname,
   byte[]     contentBytes,
   String     encoding,
   TreeLogger logger)
   throws     Exception
{
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
                                       // entry for each parsed provider      //
      String factoryMapEntries = " provider entries ---\n";
      Map<String,TypeDsc> providers = IPreprocessor.getParsedProviders(logger);

      for (TypeDsc provider : providers.values())
      {
         if (!IPreprocessor.isInstantiatableProvider(provider))
         {
            continue;
         }
         String providerClassname = IPreprocessor.getClassname(provider.type);
         logger.log(
            logger.INFO,
            "ReactCodeGeneratorPreprocessor.process(): "
          + "adding FactoryMap entry for " + providerClassname);

         factoryMapEntries +=
            kFACTORY_MAP_PROVIDER_ENTRY.replace(
               "%classname%", providerClassname);

                                       // save the entry so it can be checked //
                                       // in ReactCodeGenerator               //
                                       // checkComponentFactoryMap()          //
                                       // (temporary check before fix)        //
         kFACTORY_MAP_KEYS.add(providerClassname);
      }
                                       // attempt to add a factory map        //
                                       // entry for each parsed component     //
      factoryMapEntries += "\n// component entries ---\n";
      Map<String,TypeDsc> components = IPreprocessor.getParsedComponents(logger);

      for (TypeDsc component : components.values())
      {
         if (!IPreprocessor.isRenderableComponent(component))
         {
            continue;
         }

         String componentClassname = IPreprocessor.getClassname(component.type);
         if (AppComponentTemplate.class.getName().equals(componentClassname)
               || Component.class.getName().equals(componentClassname))
         {
            continue;
         }

         logger.log(
            logger.INFO,
            "ReactCodeGeneratorPreprocessor.process(): "
          + "adding FactoryMap entry for " + componentClassname);

         String mapEntry =
            kFACTORY_MAP_COMPONENT_ENTRY.replace(
               "%classname%", componentClassname);

         logger.log(
            logger.INFO,
            "ReactCodeGeneratorPreprocessor.process(): map entry:\n" + mapEntry);

         factoryMapEntries += mapEntry;
                                       // save the entry so it can be checked //
                                       // in ReactCodeGenerator               //
                                       // checkComponentFactoryMap()          //
                                       // (temporary check before fix)        //
         kFACTORY_MAP_KEYS.add(componentClassname);
      }
                                       // not doing rsrc embedding this way...//
      //                                 // attempt to add resource entries     //
      //                                 // for AppComponentTemplate subclasses //
      //String resourceMapEntries = processEmbeddedResources(logger);

      String platformProviderClassname =
         IConfiguration.getPlatform(logger).getClass().getName();

      content =
         content
         .replace("%factoryMapEntries%",  factoryMapEntries)

                                       // not doing rsrc embedding this way...//
         //.replace("%resourceMapEntries%", resourceMapEntries)
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
/*------------------------------------------------------------------------------

@name       processEmbeddedResources - add embedded resources
                                                                              */
                                                                             /**
            Add resource entries for AppComponentTemplate subclasses.

@return     resource map entries

@param      logger      logger

@history    Fri Aug 21, 2020 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
                                       // not doing rsrc embedding this way...//
//protected String processEmbeddedResources(
//   TreeLogger logger)
//   throws     Exception
//{
//   String rsrcMapEntries = " resource entries ---\n";
//
//                                       // other than 'Component' and          //
//                                       // 'AppComponentTemplate'              //
//   Map<String,TypeDsc> providersAndComponents =
//      IPreprocessor.getParsedProvidersAndComponents(logger);
//
//   providersAndComponents.remove(Component.class.getName());
//   providersAndComponents.remove(AppComponentTemplate.class.getName());
//
//   Map<String,TypeDsc> apps = IPreprocessor.getParsedApps(logger);
//   apps.remove(AppComponentTemplate.class.getName());
//   for (TypeDsc app : apps.values())
//   {
//      String appClassname = IPreprocessor.getClassname(app.type);
//
//      logger.log(
//         logger.INFO,
//         "ReactCodeGeneratorPreprocessor.process(): "
//       + "checking for embedded resources for " + appClassname);
//
//      Collection<String> embeddedResources =
//         AppComponentInspector.getParsedEmbeddedResources(
//            AppComponentInspector.getAppInfoByTypeDsc(
//               app, providersAndComponents, logger), logger);
//
//      if (embeddedResources.size() == 0)
//      {
//         logger.log(
//            TreeLogger.INFO,
//            "ReactCodeGeneratorPreprocessor.processEmbeddedResources(): "
//          + "no embedded resources specified");
//
//         continue;
//      }
//
//      for (String rsrcName : embeddedResources)
//      {
//         if (!rsrcName.startsWith("embedded:"))
//         {
//            continue;
//         }
//
//         rsrcName        = rsrcName.substring("embedded:".length());
//         rsrcMapEntries += addEmbeddedResource(rsrcName, logger);
//      }
//   }
//   return(rsrcMapEntries);
//}
}//====================================// end ReactCodeGeneratorPreprocessor =//
