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
                                       // AppComponentTemplate ============== //
public class AppComponentTemplate<P extends Properties> extends Component<P>
{
                                       // constants ------------------------- //
public static Collection<String> importedNodeModules;
public static SEOInfo            seoInfo;
public static ICloudServices     cloudServicesConfig;

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

@name       getImportedNodeModulesAndSEO - imported modules and SEO info
                                                                              */
                                                                             /**
            Get googleAnalyticsId, imported modules and SEOInfo as a single
            collection delimited by "<delimiter>" entries.

@return     collection of googleAnalyticsId, imported module names and SEOInfo

@history    Sun Jun 16, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static Collection<String> getImportedNodeModulesAndSEO()
{
   Collection importedNodeModulesAndSEO = new ArrayList();
   if (cloudServicesConfig != null)
   {
      String googleAnalyticsId = cloudServicesConfig.getTrackingId();
      if (googleAnalyticsId != null && googleAnalyticsId.length() > 0)
      {
         importedNodeModulesAndSEO.add(googleAnalyticsId);

                                       // automatically add the supporting    //
                                       // node module                         //
         importedNodeModules.add("react-ga");
      }
   }

   importedNodeModulesAndSEO.add("<delimiter>");
   if (importedNodeModules != null)
   {
      importedNodeModulesAndSEO.addAll(importedNodeModules);
   }

   importedNodeModulesAndSEO.add("<delimiter>");
   importedNodeModulesAndSEO.add(
      seoInfo != null ? seoInfo.toString() : SEOInfo.kNULL_VALUE);

   return(importedNodeModulesAndSEO);
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
            System.out.println(getImportedNodeModulesAndSEO().toString());
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
               System.out.println(getImportedNodeModulesAndSEO().toString());
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
