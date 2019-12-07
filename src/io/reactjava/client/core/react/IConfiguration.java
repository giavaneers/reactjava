/*==============================================================================

name:       IConfiguration.java

purpose:    ReactJava Application configuration interface.

history:    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import io.reactjava.client.core.providers.auth.IAuthenticationService;
import io.reactjava.client.core.providers.auth.firebase.FirebaseAuthenticationService;
import io.reactjava.client.core.providers.database.IDatabaseService;
import io.reactjava.client.core.providers.database.firebase.FirebaseDatabaseService;
import io.reactjava.client.core.providers.http.HttpClient;
import io.reactjava.client.core.providers.http.IHttpClientBase;
import io.reactjava.client.core.providers.platform.IPlatform;
import io.reactjava.client.core.providers.platform.web.PlatformWeb;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import jsinterop.annotations.JsPackage;
                                       // IConfiguration =====================//
public interface IConfiguration
{
                                       // class constants --------------------//
boolean kSRCCFG_RENDER_INLINE = true;
boolean kSRCCFG_BUNDLE_SCRIPT = true;

                                       // jsinterop namespace and name        //
String kJSINTEROP_NAMESPACE =
   kSRCCFG_BUNDLE_SCRIPT ? "ReactJava" : JsPackage.GLOBAL;

                                       // standard keys                       //
String kKEY_BUNDLE_SCRIPTS            = "bundleScripts";
String kKEY_CONFIGURATION_NAME        = "configurationName";
String kKEY_GLOBAL_CSS                = "globalCSS";
String kKEY_GLOBAL_IMAGES             = "globalImages";
String kKEY_IMPORTED_NODE_MODULES     = "importedNodeModules";
String kKEY_NAV_ROUTES                = "navRoutes";
String kKEY_NAV_ROUTES_NESTED         = "navRoutesNested";
String kKEY_PRODUCTION_MODE           = "productionMode";
String kKEY_PROVIDERS                 = "providers";
String kKEY_REQUIRED_PLATFORM_IMPORTS = "requiredPlatformImports";
String kKEY_REQUIRED_PLATFORM_SCRIPTS = "requiredPlatformScripts";
String kKEY_SCRIPTS_COMPRESSED        = "scriptsCompressed";
String kKEY_SCRIPTS_LOAD_LAZY         = "scriptsLoadLazy";
String kKEY_TAG_MAP_CUSTOM            = "tagMapCustom";
String kKEY_TAG_MAP_DEFAULT           = "tagMapDefault";
String kKEY_THEME                     = "theme";

                                       // default values                      //
Collection<String> kBUNDLE_SCRIPTS_DEFAULT =
   new ArrayList<String>()
   {{
      if (kSRCCFG_BUNDLE_SCRIPT)
      {
         add("reactjavaapp.js");
      }
   }};

Collection<String> kGLOBAL_CSS_DEFAULT =
   new ArrayList(Arrays.asList(new String[]
   {
      "https://fonts.googleapis.com/css?family=Roboto:300,400,500",
      "https://fonts.googleapis.com/icon?family=Material+Icons",
      "index.css",
   }));
Collection<String> kGLOBAL_IMAGES_DEFAULT =
   new ArrayList(Arrays.asList(new String[]
   {
      "favicon.ico",
   }));

String  kKEY_CONFIGURATION_NAME_DEFAULT = "default";
boolean kPRODUCTION_MODE_DEFAULT        = false;

String  kNODE_MODULES_DIR = "$NODE_MODULES_DIR$/";

Map<String,String> kPROVIDERS_DEFAULT =
   new HashMap<String,String>()
   {{
      put(
         IHttpClientBase.class.getName(),
         HttpClient.class.getName());
      put(
         IAuthenticationService.class.getName(),
         FirebaseAuthenticationService.class.getName());
      put(
         IDatabaseService.class.getName(),
         FirebaseDatabaseService.class.getName());
      put(
         IPlatform.class.getName(),
         PlatformWeb.class.getName());
   }};

Collection<String> kREQUIRED_PLATFORM_IMPORTS_DEFAULT =
   Arrays.asList(new String[]
   {
   });

Collection<String> kREQUIRED_PLATFORM_SCRIPTS_DEFAULT =
   new ArrayList<String>()
   {{
      add("max/es5/pako.js");
      //add("max/es5/core.js");
      //add("max/es5/zone.js");
      //add("max/es5/reflect-metadata.js");
      //add("max/es5/Rx.5.5.10.js");

      if (!kSRCCFG_BUNDLE_SCRIPT)
      {
         add(
                                       // node_module version with std backup //
            kNODE_MODULES_DIR + "react/umd/react.development.js,"
               + "max/react.development.js");

         add(
                                       // node_module version with std backup //
            kNODE_MODULES_DIR + "react-dom/umd/react-dom.development.js,"
               + "max/react-dom.development.js");
      }
   }};

boolean kSCRIPTS_COMPRESSED_DEFAULT = true;
boolean kSCRIPTS_LOAD_LAZY_DEFAULT  = true;

                                       // works at compile time since elements//
                                       // of kKEY_THEME require js environment//
Map<String,Object> kCONFIGURATION_DEFAULT_SANS_THEME =
   new HashMap<String,Object>()
   {{
      put(kKEY_BUNDLE_SCRIPTS,            kBUNDLE_SCRIPTS_DEFAULT);
      put(kKEY_CONFIGURATION_NAME,        kKEY_CONFIGURATION_NAME_DEFAULT);
      put(kKEY_GLOBAL_CSS,                kGLOBAL_CSS_DEFAULT);
      put(kKEY_GLOBAL_IMAGES,             kGLOBAL_IMAGES_DEFAULT);
      put(kKEY_PRODUCTION_MODE,           kPRODUCTION_MODE_DEFAULT);
      put(kKEY_PROVIDERS,                 kPROVIDERS_DEFAULT);
      put(kKEY_REQUIRED_PLATFORM_IMPORTS, kREQUIRED_PLATFORM_IMPORTS_DEFAULT);
      put(kKEY_REQUIRED_PLATFORM_SCRIPTS, kREQUIRED_PLATFORM_SCRIPTS_DEFAULT);
      put(kKEY_SCRIPTS_COMPRESSED,        kSCRIPTS_COMPRESSED_DEFAULT);
      put(kKEY_SCRIPTS_LOAD_LAZY,         kSCRIPTS_LOAD_LAZY_DEFAULT);
   }};
                                       // class variables ------------------- //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - create configuration
                                                                              */
                                                                             /**
            Create react configuration.

@return     Observable for boot completion

@param     app      application component template

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
static IConfiguration assignSharedInstance(
   AppComponentTemplate app)
{
   String configName =
      Utilities.getHrefParams().get(IConfiguration.kKEY_CONFIGURATION_NAME);

   if (configName == null)
   {
      configName = app.getConfigurationName();
   }

   IConfiguration configuration = getConfiguration(app, configName);

                                       // nav routes -------------------------//
   Map<String,Class> navRoutes = null;

   String jsonNavRoutes =
      Utilities.getHrefParams().get(IConfiguration.kKEY_NAV_ROUTES);

   if (jsonNavRoutes != null)
   {
                                       // parse url param to navRoutes        //
      navRoutes = null;
   }
   if (navRoutes == null)
   {
      navRoutes = app.getNavRoutes();
   }
   if (navRoutes != null)
   {
      configuration.setNavRoutes(navRoutes);
   }
                                       // imported node modules --------------//
   Collection<String> importedModules = configuration.getImportedNodeModules();
   if (importedModules == null)
   {
      importedModules = app.getImportedNodeModules();
      if (importedModules != null && importedModules.size() > 0)
      {
         configuration.setImportedNodeModules(importedModules);
      }
   }
                                       // assign the shared instance          //
   Configuration.sharedInstance = configuration;

   return(configuration);
}
/*------------------------------------------------------------------------------

@name       getBundleScripts - get bundle scripts
                                                                              */
                                                                             /**
            Get bundle scripts.

@return     bundle scripts

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
Collection<String> getBundleScripts();

/*------------------------------------------------------------------------------

@name       getGlobalCSS - get global css
                                                                              */
                                                                             /**
            Get global css.

@return     global css

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
Collection<String> getGlobalCSS();

/*------------------------------------------------------------------------------

@name       getGlobalImages - get global images
                                                                              */
                                                                             /**
            Get global images.

@return     global images

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
Collection<String> getGlobalImages();

/*------------------------------------------------------------------------------

@name       getImportedNodeModules - get imported node modules
                                                                              */
                                                                             /**
            Get imported node modules.

@history    Sun Dec 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
Collection<String> getImportedNodeModules();

/*------------------------------------------------------------------------------

@name       getConfiguration - get configuration for specified name
                                                                              */
                                                                             /**
            Get configuration for specified name. If the specified name is null,
            or a configuration with the specified name is not found, the default
            configuration is used.

@return     configuration for specified name

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IConfiguration getConfiguration(
   AppComponentTemplate app,
   String               configurationName)
{
   if (Configuration.sharedInstance() != null)
   {
      throw new IllegalStateException("Configuration has already been assigned");
   }

   String  configNameDefault = IConfiguration.kKEY_CONFIGURATION_NAME_DEFAULT;
   boolean bAssignPlatform   = false;

   if (configurationName == null)
   {
      configurationName = configNameDefault;
   }
   IConfiguration   config  = null;
   IConfiguration[] configs = app.getConfigurations();
   if (configs != null)
   {
                                       // if configurationName is null and a  //
                                       // default configuration is supplied   //
                                       // (that is, a configuration with no   //
                                       // name or the name 'default', it will //
                                       // be used                             //
      for (IConfiguration candidate : configs)
      {
         String candidateName = candidate.getName();
         if (candidateName == null)
         {
            candidateName = configNameDefault;
         }
         if (configurationName.equals(candidateName))
         {
            config = candidate;

            if (configurationName.equals(configNameDefault))
            {
               bAssignPlatform = true;
            }
            break;
         }
      }
   }
   if (config == null)
   {
      config = new Configuration();
      bAssignPlatform = true;
   }
   if (bAssignPlatform || config.getProvider(IPlatform.class.getName()) == null)
   {
                                       // assign the platform                 //
      config.setProvider(
         IPlatform.class.getName(),
         ReactGeneratedCode.getPlatformProvider());
   }

   return(config);
}
/*------------------------------------------------------------------------------

@name       getName - get name
                                                                              */
                                                                             /**
            Get name.

@return     name

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
String getName();

/*------------------------------------------------------------------------------

@name       getNavRoutes - get nav routes
                                                                              */
                                                                             /**
            Get nav routes.

@return     nav routes

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
Map<String,Class> getNavRoutes();

/*------------------------------------------------------------------------------

@name       getNavRoutesNested - get any nested nav routes
                                                                              */
                                                                             /**
            Get any nested nav routes.

@return     any nested nav routes

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
Map<String,Class> getNavRoutesNested();

/*------------------------------------------------------------------------------

@name       getProductionMode - get production mode
                                                                              */
                                                                             /**
            Get production mode.

@return     production mode

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
boolean getProductionMode();

/*------------------------------------------------------------------------------

@name       getProvider - get provider for specified interface
                                                                              */
                                                                             /**
            Get provider for specified interface.

@return     provider for specified interface

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
String getProvider(
   String interfaceClassname);

/*------------------------------------------------------------------------------

@name       getProviders - get map of provider interface to provider impl
                                                                              */
                                                                             /**
            Get map of provider interface to provider implementation.

@return     Map of provider interface to provider implementation

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
Map<String,String> getProviders();

/*------------------------------------------------------------------------------

@name       getRequiredPlatformImports - get required platform imports
                                                                              */
                                                                             /**
            Get required platform imports.

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
Collection<String> getRequiredPlatformImports();

/*------------------------------------------------------------------------------

@name       getRequiredPlatformScripts - get required platform scripts
                                                                              */
                                                                             /**
            Get required platform scripts.

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
Collection<String> getRequiredPlatformScripts();

/*------------------------------------------------------------------------------

@name       getScriptsCompressed - get whether scripts are compressed
                                                                              */
                                                                             /**
            Get whether scripts are compressed.

@return     true iff scripts are compressed

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public boolean getScriptsCompressed();

/*------------------------------------------------------------------------------

@name       getScriptsLoadLazy - get whether javascripts load lazy
                                                                              */
                                                                             /**
            Get whether javascripts load lazy. Lazily loaded leaves the scripts
            on the server which are downloaded on demand; preloaded are embedded
            within a single compressed package and downloaded all at once.

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
boolean getScriptsLoadLazy();

/*------------------------------------------------------------------------------

@name       getTagMap - get any custom tag map
                                                                              */
                                                                             /**
            Get any custom map of any replacement tag by standard tag. A tag is
            resolved by first checking whether there is a replacement in any
            custom tag map and if not, checking whether there is a replacement
            in the default tag map.

            For example, in the React configuration, there exists an entry
            for the tag "div" whose entry is
            "node_modules.react-native.Libraries.Components.View".

@return     tag map

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
Map<String,String> getTagMap();

/*------------------------------------------------------------------------------

@name       getTheme - get default theme
                                                                              */
                                                                             /**
            Get default theme.

@return     default theme

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
IUITheme getTheme();

/*------------------------------------------------------------------------------

@name       getTagMapDefault - get default tag map
                                                                              */
                                                                             /**
            Get default map of any replacement tag by standard tag. A tag is
            resolved by first checking whether there is a replacement in any
            custom tag map and if not, checking whether there is a replacement
            in the default tag map.

            For example, in the React configuration, there exists an entry
            for the tag "div" whose entry is
            "node_modules.react-native.Libraries.Components.View".

@return     tag map

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
Map<String,String> getTagMapDefault();

/*------------------------------------------------------------------------------

@name       setBundleScripts - set bundle scripts
                                                                              */
                                                                             /**
            Set bundle scripts.

@return     this

@param      scripts     bundle scripts

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
IConfiguration setBundleScripts(Collection<String> scripts);

/*------------------------------------------------------------------------------

@name       setGlobalCSS - set global css
                                                                              */
                                                                             /**
            Set global css.

@return     void

@param      globalCSS      global css urls

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
IConfiguration setGlobalCSS(Collection<String> globalCSS);

/*------------------------------------------------------------------------------

@name       setGlobalCSS - set global css
                                                                              */
                                                                             /**
            Set global css.

@return     void

@param      globalCSS      global css urls

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
IConfiguration setGlobalCSS(String[] globalCSS);

/*------------------------------------------------------------------------------

@name       getImportedNodeModules - get imported node modules
                                                                              */
                                                                             /**
            Get imported node modules.

@history    Sun Dec 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
IConfiguration setImportedNodeModules(Collection<String> modules);

/*------------------------------------------------------------------------------

@name       setName - set name
                                                                              */
                                                                             /**
            Set name.

@return     this

@param      name     name

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
IConfiguration setName(String name);

/*------------------------------------------------------------------------------

@name       setNavRoutes - set nav routes
                                                                              */
                                                                             /**
            Set nav routes.

@return     this

@param      navRoutes      nav routes

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
IConfiguration setNavRoutes(Map<String,Class> navRoutes);

/*------------------------------------------------------------------------------

@name       setNavRoutesNested - set nested nav routes
                                                                              */
                                                                             /**
            Set nested nav routes.

@return     this

@param      navRoutesNested      nested nav routes

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
IConfiguration setNavRoutesNested(Map<String,Class> navRoutesNested);

/*------------------------------------------------------------------------------

@name       setProductionMode - set production mode
                                                                              */
                                                                             /**
            Set production mode.

@return     void

@param      bProdMode      iff true, production mode; otherwise debug mode

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
IConfiguration setProductionMode(boolean bProdMode);

/*------------------------------------------------------------------------------

@name       setProvider - set provider for specified interface
                                                                              */
                                                                             /**
            Set provider for specified interface

@return     this

@param      interfaceClassname      interface classname
@param      providerClassname       provider classname

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
IConfiguration setProvider(
   String interfaceClassname,
   String providerClassname);

/*------------------------------------------------------------------------------

@name       setScriptsLoadLazy - set whether scripts load lazy
                                                                              */
                                                                             /**
            Set whether scripts load lazy.

@return     void

@param      bScriptsLoadLazy     iff true, scripts load lazy

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
IConfiguration setScriptsLoadLazy(boolean bScriptsLoadLazy);

/*------------------------------------------------------------------------------

@name       setTagMap - assign a custom tag map
                                                                              */
                                                                             /**
            Assign a custom map of any replacement tag by standard tag. A tag is
            resolved by first checking whether there is a replacement in any
            custom tag map and if not, checking whether there is a replacement
            in the default tag map.

            For example, in the React configuration, there exists an entry
            for the tag "div" whose entry is
            "node_modules.react-native.Libraries.Components.View".

@param      tagMap      tag map

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
void setTagMap(Map<String,String> tagMap);

}//====================================// end IConfiguration =================//
