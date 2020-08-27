/*==============================================================================

name:       AppComponentTemplate.java

purpose:    AppComponentTemplate proxy for compilation by AppComponentInspector

history:    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.codegenerator.inspectortest;
                                       // imports --------------------------- //
import io.reactjava.client.core.react.IConfiguration.ICloudServices;
import io.reactjava.client.core.react.SEOInfo;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
                                       // AppComponentTemplate ============== //
public class AppComponentTemplate<P extends Properties> extends Component<P>
{
                                       // constants ------------------------- //
public static Collection<String> importedNodeModules;
public static SEOInfo            seoInfo;
public static ICloudServices     cloudServicesConfig;
public static List<String>       embeddedResources;

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       AppComponentTemplate - constructor for specified properties
                                                                              */
                                                                             /**
            AppComponentTemplate for specified properties

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public AppComponentTemplate()
{
   cloudServicesConfig = getCloudServicesConfig();
   seoInfo             = getSEOInfo();
   importedNodeModules = getImportedNodeModules();
   importedNodeModules =
      importedNodeModules == null
         ? new ArrayList<>() : new ArrayList<>(importedNodeModules);

   embeddedResources = getCustomJavascripts();
   embeddedResources =
      embeddedResources == null
         ? new ArrayList<>() : new ArrayList<>(embeddedResources);

                                       // stop any subsequent operation       //
   throw new UnsupportedOperationException();
}
/*------------------------------------------------------------------------------

@name       AppComponentTemplate - constructor for specified properties
                                                                              */
                                                                             /**
            AppComponentTemplate for specified properties

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public AppComponentTemplate(P props)
{
   this();
}
/*------------------------------------------------------------------------------

@name       getCloudServicesConfig - get cloud services configuration
                                                                              */
                                                                             /**
            Get cloud services configuration. This impementation is to be
            overridden.

@return     cloud services configuration.

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected ICloudServices getCloudServicesConfig()
{
   return(cloudServicesConfig);
}
/*------------------------------------------------------------------------------

@name       getCustomJavascripts - get custom javascripts
                                                                              */
                                                                             /**
            Get custom javascripts. This method is typically invoked at
            boot time.

@return     ordered list of javascript urls

@history    Fri May 22, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected List<String> getCustomJavascripts()
{
   return(embeddedResources);
}
/*------------------------------------------------------------------------------

@name       getImportedNodeModules - get imported node modules
                                                                              */
                                                                             /**
            Get imported node modules.

@return     collection of node module names

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected Collection<String> getImportedNodeModules()
{
   return(importedNodeModules);
}
/*------------------------------------------------------------------------------

@name       getImportedNodeModulesAndEmbeddedResourcesAndSEO - app info
                                                                              */
                                                                             /**
            Get googleAnalyticsId, imported modules, embedded resources and
            SEOInfo as a single collection delimited by "<delimiter>" entries.

@return     collection of googleAnalyticsId, imported module names and SEOInfo

@history    Sun Jun 16, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static Collection<String> getImportedNodeModulesAndEmbeddedResourcesAndSEO()
{
   Collection importedNodeModulesAndEmbeddedResourcesAndSEO = new ArrayList();
   if (cloudServicesConfig != null)
   {
      String googleAnalyticsId = cloudServicesConfig.getTrackingId();
      if (googleAnalyticsId != null && googleAnalyticsId.length() > 0)
      {
         importedNodeModulesAndEmbeddedResourcesAndSEO.add(googleAnalyticsId);

                                       // automatically add the supporting    //
                                       // node module for posting events      //
         importedNodeModules.add("react-ga");
      }
   }

   importedNodeModulesAndEmbeddedResourcesAndSEO.add("<delimiter>");
   if (importedNodeModules != null)
   {
                                       // add imported node modules           //

      importedNodeModulesAndEmbeddedResourcesAndSEO.addAll(importedNodeModules);
   }

   importedNodeModulesAndEmbeddedResourcesAndSEO.add("<delimiter>");
   if (embeddedResources != null)
   {
                                       // add embedded resources              //
                                       // (just javascripts for now)          //

      importedNodeModulesAndEmbeddedResourcesAndSEO.addAll(embeddedResources);
   }

   importedNodeModulesAndEmbeddedResourcesAndSEO.add("<delimiter>");

                                       // add any SEO info                    //
   importedNodeModulesAndEmbeddedResourcesAndSEO.add(
      seoInfo != null ? seoInfo.toString() : SEOInfo.kNULL_VALUE);

   return(importedNodeModulesAndEmbeddedResourcesAndSEO);
}
/*------------------------------------------------------------------------------

@name       getSEOInfo - get seo information
                                                                              */
                                                                             /**
            Get SEO info. This method is typically invoked at compile time.

            The intention is to provide a title, description, and base url for
            the app deployment in order to create a redirect target for each
            hash, along with an associated sitemap.

@return     SEOInfo

@history    Sun Jun 16, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected SEOInfo getSEOInfo()
{
   return(seoInfo);
}
/*------------------------------------------------------------------------------

@name       main - standard main routine
                                                                              */
                                                                             /**
            Standard main routine.

@param      args     args[0] - platform specification

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void main(
   String[] args)
{
   String appClassname = args[0];
   try
   {
      Class appClass = Class.forName(appClassname);
      try
      {
                                       // check for the nullary constructor,  //
                                       // either provided explicitly or       //
                                       // by the compiler if no other exists  //
         Constructor ctor = appClass.getConstructor();
         try
         {
                                       // invoke the nullary constructor      //
            ctor.newInstance();
         }
         catch(Exception uoe)
         {
                                       // constructor should generate an error//
                                       // return result to invoker            //
            System.out.println(
               getImportedNodeModulesAndEmbeddedResourcesAndSEO().toString());
         }
      }
      catch(Exception e)
      {
         try
         {
                                       // check for props constructor         //
            Constructor ctor = appClass.getConstructor(Properties.class);
            try
            {
                                       // invoke the props constructor        //
               ctor.newInstance(new Properties());
            }
            catch(Exception uoe)
            {
                                       // constructor should generate an error//
                                       // return result to invoker            //
               System.out.println(
                  getImportedNodeModulesAndEmbeddedResourcesAndSEO().toString());
            }
         }
         catch(Exception ee)
         {
            System.err.println(
               "If you supply any custom constructor for " + appClassname
             + ", you must also supply at least a nullary constructor as well.");
         }
      }
   }
   catch(Throwable t)
   {
      System.err.println(t);
   }
}
}//====================================// end AppComponentTemplate -----------//
