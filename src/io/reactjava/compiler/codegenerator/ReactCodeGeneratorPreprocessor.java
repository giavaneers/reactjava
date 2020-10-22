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
package io.reactjava.compiler.codegenerator;
                                       // imports --------------------------- //
import com.google.gwt.core.ext.TreeLogger;
import io.reactjava.client.core.react.ReactGeneratedCode;
import io.reactjava.compiler.jsx.IConfiguration;
import io.reactjava.compiler.jsx.TypeDsc;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
@param      bUpdate        iff true, this is an update of an incremental build
@param      logger         compiler logger

@history    Fri Dec 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public byte[] process(
   String             classname,
   byte[]             contentBytes,
   String             encoding,
   Map<String,String> providerAndComponentCandidates,
   boolean            bUpdate,
   TreeLogger logger)
   throws             Exception
{
   if (ReactGeneratedCode.class.getName().equals(classname))
   {
      logger.log(
         logger.INFO, "ReactCodeGeneratorPreprocessor.process(): processing");

      String content = new String(contentBytes, encoding);

      Set<TypeDsc> appDependencies =
         new HashSet<>(TypeDsc.getAppDependenciesByTag().values());

                                       // attempt to add a factory map        //
                                       // entry for each parsed provider      //
      String factoryMapEntries = " provider entries ---\n";

      for (TypeDsc provider : TypeDsc.getProviders())
      {
                                       // the ability to identify providers   //
                                       // that are app dependencies might not //
                                       // be worth the processing time, so    //
                                       // add a factory map entry for all of  //
                                       // them for now                        //
         if (!provider.isInstantiable())
         {
            continue;
         }

         logger.log(
            logger.INFO,
            "ReactCodeGeneratorPreprocessor.process(): "
          + "adding FactoryMap entry for " + provider.classname);

         factoryMapEntries +=
            kFACTORY_MAP_PROVIDER_ENTRY.replace(
               "%classname%", provider.classname);

                                       // save the entry so it can be checked //
                                       // in ReactCodeGenerator               //
                                       // checkComponentFactoryMap()          //
                                       // (temporary check before fix)        //
         kFACTORY_MAP_KEYS.add(provider.classname);
      }
                                       // attempt to add a factory map        //
                                       // entry for each parsed component     //
      factoryMapEntries += "\n// component entries ---\n";

      for (TypeDsc component : TypeDsc.getComponents())
      {
         if (!appDependencies.contains(component) || !component.isInstantiable())
         {
            continue;
         }

         logger.log(
            logger.INFO,
            "ReactCodeGeneratorPreprocessor.process(): "
          + "adding FactoryMap entry for " + component.classname);

         String mapEntry =
            kFACTORY_MAP_COMPONENT_ENTRY.replace(
               "%classname%", component.classname);

         logger.log(
            logger.INFO,
            "ReactCodeGeneratorPreprocessor.process(): map entry:\n" + mapEntry);

         factoryMapEntries += mapEntry;
                                       // save the entry so it can be checked //
                                       // in ReactCodeGenerator               //
                                       // checkComponentFactoryMap()          //
                                       // (temporary check before fix)        //
         kFACTORY_MAP_KEYS.add(component.classname);
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
}//====================================// end ReactCodeGeneratorPreprocessor =//
