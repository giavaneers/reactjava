/*==============================================================================

name:       Configuration.java

purpose:    ReactJava Application Configuration.

history:    Mon June 4, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import com.giavaneers.util.gwt.Logger;
import elemental2.dom.DomGlobal;
import elemental2.dom.Location;
import io.reactjava.client.providers.auth.firebase.FirebaseCore;
import io.reactjava.client.core.rxjs.observable.Observable;
import io.reactjava.client.core.rxjs.observable.Subscriber;
import io.reactjava.client.moduleapis.ReactGA;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
                                       // Configuration ======================//
public class Configuration extends NativeObject implements IConfiguration
{
                                       // constants ------------------------- //
private static final Logger kLOGGER = Logger.newInstance();
                                       // class variables ------------------- //
                                       // shared instance                     //
protected static IConfiguration sharedInstance;

                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Configuration - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Configuration()
{
   super();
   for (String key : kCONFIGURATION_DEFAULT_SANS_THEME.keySet())
   {
      set(key, kCONFIGURATION_DEFAULT_SANS_THEME.get(key));
   }
                                       // add theme at execute time since     //
                                       // requires js environment which does  //
                                       // not exists at compile time          //
   set(kKEY_THEME, IUITheme.defaultInstance());
}
/*------------------------------------------------------------------------------

@name       getApp - get app
                                                                              */
                                                                             /**
            Get app.

@return     app

@history    Mon Dec 23, 2019 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public AppComponentTemplate getApp()
{
   return((AppComponentTemplate)get(kKEY_APP));
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
public Collection<String> getBundleScripts()
{
  return((Collection<String>)get(kKEY_BUNDLE_SCRIPTS));
}
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
public Collection<String> getGlobalCSS()
{
   Collection<String> globalCSS = (Collection<String>)get(kKEY_GLOBAL_CSS);
   return(globalCSS);
}
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
public Collection<String> getGlobalImages()
{
   Collection<String> globalCSS = (Collection<String>)get(kKEY_GLOBAL_IMAGES);
   return(globalCSS);
}
/*------------------------------------------------------------------------------

@name       getGoogleAnalyticsId - get google analytics id
                                                                              */
                                                                             /**
            Get google analytics id. This impementation is to be overridden.

@return     google analytics id.

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ICloudServices getCloudServicesConfig()
{
   return((ICloudServices)get(kKEY_CLOUD_SERVICES_CONFIG));
}
/*------------------------------------------------------------------------------

@name       getImportedNodeModules - get imported node modules
                                                                              */
                                                                             /**
            Get imported node modules.

@history    Sun Dec 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Collection<String> getImportedNodeModules()
{
  return((Collection<String>)get(kKEY_IMPORTED_NODE_MODULES));
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
public String getName()
{
  return(getString(kKEY_CONFIGURATION_NAME));
}
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
public Map<String,Class> getNavRoutes()
{
  return((Map<String,Class>)get(kKEY_NAV_ROUTES));
}
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
public Map<String,Class> getNavRoutesNested()
{
  return((Map<String,Class>)get(kKEY_NAV_ROUTES_NESTED));
}
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
public boolean getProductionMode()
{
   boolean bProductionMode = getBoolean(kKEY_PRODUCTION_MODE);
   return(bProductionMode);
}
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
public String getProvider(
   String interfaceClassname)
{
   return(getProviders().get(interfaceClassname));
}
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
public Map<String,String> getProviders()
{
   Map<String,String> providers = (Map<String,String>)get(kKEY_PROVIDERS);
   return(providers);
}
/*------------------------------------------------------------------------------

@name       getRequiredPlatformImports - get required platform imports
                                                                              */
                                                                             /**
            Get required platform imports.

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Collection<String> getRequiredPlatformImports()
{
   Collection<String> requiredPlatformImports =
      (Collection<String>)get(kKEY_REQUIRED_PLATFORM_IMPORTS);

   return(requiredPlatformImports);
}
/*------------------------------------------------------------------------------

@name       getRequiredPlatformScripts - get required platform scripts
                                                                              */
                                                                             /**
            Get required platform scripts.

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Collection<String> getRequiredPlatformScripts()
{
   Collection<String> requiredPlatformScripts =
      (Collection<String>)get(kKEY_REQUIRED_PLATFORM_SCRIPTS);

   return(requiredPlatformScripts);
}
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
public boolean getScriptsCompressed()
{
   boolean bScriptsCompressed = getBoolean(kKEY_SCRIPTS_COMPRESSED);
   return(bScriptsCompressed);
}
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
public boolean getScriptsLoadLazy()
{
   boolean bScriptsLoadLazy = getBoolean(kKEY_SCRIPTS_LOAD_LAZY);
   return(bScriptsLoadLazy);
}
/*------------------------------------------------------------------------------

@name       getSEOInfo - get seo info
                                                                              */
                                                                             /**
            Get seo info.

@return     seo info

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public SEOInfo getSEOInfo()
{
  return((SEOInfo)get(kKEY_SEO_INFO));
}
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
public Map<String,String> getTagMap()
{
  return((Map<String,String>)get(kKEY_TAG_MAP_CUSTOM));
}
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
public Map<String,String> getTagMapDefault()
{
  return((Map<String,String>)get(kKEY_TAG_MAP_DEFAULT));
}
/*------------------------------------------------------------------------------

@name       getTheme - get theme property value
                                                                              */
                                                                             /**
            Get theme property value

@return     theme value, or null if not found.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IUITheme getTheme()
{
   IUITheme theme = (IUITheme)get("theme");
   if (theme == null)
   {
      theme = IUITheme.defaultInstance();
      set("theme", theme);
   }
   return(theme);
}
/*------------------------------------------------------------------------------

@name       initialize - initialize
                                                                              */
                                                                             /**
            Initialize. When invoked, all scripts and node modules have been
            loaded.

@history    Mon Dec 23, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Observable<IConfiguration> initialize()
{
   initializeNavRoutes();
   initializeGoogleAnalytics();
   return(initializeCloudServices());
}
/*------------------------------------------------------------------------------

@name       initializeCloudServices - initialize cloud services
                                                                              */
                                                                             /**
            Initialize cloud services. When invoked, all scripts and node
            modules have been loaded.

@history    Mon Dec 23, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Observable<IConfiguration> initializeCloudServices()
{
   IConfiguration configuration       = this;
   ICloudServices cloudServicesConfig = getCloudServicesConfig();

   boolean bInit =
      cloudServicesConfig != null
       && ICloudServices.kPLATFORM_FIREBASE.equals(
            cloudServicesConfig.getCloudPlatform());

   Observable<IConfiguration> observable = Observable.create(
      (Subscriber<IConfiguration> subscriber) ->
      {
         if (bInit)
         {
            FirebaseCore.configure(cloudServicesConfig).subscribe(
               response ->
               {
                  subscriber.next(configuration);
                  subscriber.complete();
               },
               error ->
               {
                  subscriber.error(error);
               });
         }
         else if (cloudServicesConfig == null)
         {
            subscriber.next(configuration);
            subscriber.complete();
         }
         else
         {
            subscriber.error("Only Firebase supported for now");
         }
         return(subscriber);
      });

   return(observable);
}
/*------------------------------------------------------------------------------

@name       initializeGoogleAnalytics - initialize google analytics
                                                                              */
                                                                             /**
            Initialize google analytics. When invoked, all scripts and node
            modules have been loaded.

@history    Mon Dec 23, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void initializeGoogleAnalytics()
{
   ICloudServices cloudServicesConfig = getCloudServicesConfig();
   if (cloudServicesConfig != null)
   {
      String trackingId = cloudServicesConfig.getTrackingId();
      if (trackingId != null)
      {
         ReactGA.initialize(trackingId);

         Location location   = DomGlobal.location;
         String   searchPath = location.getPathname() + location.getSearch();

         ReactGA.pageview(searchPath);

         kLOGGER.logInfo(
            "Configuration.initialize(): initialized Google Analytics with"
          + " googlaAnalyticsId=" + trackingId
          + " and search path=" + searchPath);
      }
   }
}
/*------------------------------------------------------------------------------

@name       initializeNavRoutes - initialize nav routes
                                                                              */
                                                                             /**
            Initialize nav routes. When invoked, all scripts and node modules
            have been loaded.

@history    Mon Dec 23, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void initializeNavRoutes()
{
   Map<String,Class> routes = getNavRoutes();
   if (routes == null)
   {
      setNavRoutes(
         new HashMap<String,Class>()
         {{
            put("", getApp().getClass());
         }});
   }
}
/*------------------------------------------------------------------------------

@name       setApp - set app
                                                                              */
                                                                             /**
            Set app.

@return     this

@param      app      app

@history    Mon Dec 23, 2019 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public IConfiguration setApp(
   AppComponentTemplate app)
{
   if (app != null)
   {
      set(kKEY_APP, app);
   }
   return(this);
}
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
public IConfiguration setBundleScripts(
   Collection<String> scripts)
{
   if (scripts != null)
   {
      set(kKEY_BUNDLE_SCRIPTS, scripts);
   }
   return(this);
}
/*------------------------------------------------------------------------------

@name       setGlobalCSS - set global css
                                                                              */
                                                                             /**
            Set global css.

@return     this configuration

@param      globalCSS      global css urls

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public IConfiguration setGlobalCSS(
   Collection<String> globalCSS)
{
   if (globalCSS != null)
   {
      set(kKEY_GLOBAL_CSS, globalCSS);
   }
   return(this);
}
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
public IConfiguration setGlobalCSS(
   String[] globalCSS)
{
   if (globalCSS != null)
   {
      setGlobalCSS(new ArrayList(Arrays.asList(globalCSS)));
   }
   return(this);
}
/*------------------------------------------------------------------------------

@name       setCloudServicesConfig - set cloud services config
                                                                              */
                                                                             /**
            Set cloud services config

@return     this configuration

@param      cloudServicesConfig    cloud services config.

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IConfiguration setCloudServicesConfig(
   ICloudServices cloudServicesConfig)
{
   if (cloudServicesConfig != null)
   {
      set(kKEY_CLOUD_SERVICES_CONFIG, cloudServicesConfig);
   }
   return(this);
}
/*------------------------------------------------------------------------------

@name       setImportedNodeModules - set imported node modules
                                                                              */
                                                                             /**
            Set imported node modules.

@return     this

@param      modules     imported node modules

@history    Sun Dec 02, 2018 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public IConfiguration setImportedNodeModules(
   Collection<String> modules)
{
   if (modules != null)
   {
      set(kKEY_IMPORTED_NODE_MODULES, modules);
   }
   return(this);
}
/*------------------------------------------------------------------------------

@name       setName - set name
                                                                              */
                                                                             /**
            Set name.

@return     void

@param      name     name

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public IConfiguration setName(
   String name)
{
   if (name != null)
   {
      set(kKEY_CONFIGURATION_NAME, name);
   }
   return(this);
}
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
public IConfiguration setNavRoutes(Map<String,Class> navRoutes)
{
   set(kKEY_NAV_ROUTES, navRoutes);
   return(this);
}
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
public IConfiguration setNavRoutesNested(Map<String,Class> navRoutesNested)
{
   set(kKEY_NAV_ROUTES_NESTED, navRoutesNested);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setProductionMode - set production mode
                                                                              */
                                                                             /**
            Set production mode.

@return     void

@param      bProductionMode      iff true, production mode; otherwise debug mode

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public IConfiguration setProductionMode(
   boolean bProductionMode)
{
   set(kKEY_PRODUCTION_MODE, bProductionMode ? "true" : "false");
   return(this);
}
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
public IConfiguration setProvider(
   String interfaceClassname,
   String providerClassname)
{
   getProviders().put(interfaceClassname, providerClassname);
   return(this);
}
/*------------------------------------------------------------------------------

@name       setProviders - set get map of provider interface to provider impl
                                                                              */
                                                                             /**
            Set map of provider interface to provider implementation.

@return     this

@param      providers   map of provider interface to provider implementation

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public IConfiguration setProviders(
   Map<String,String> providers)
{
   set(kKEY_PROVIDERS, providers);
   return(this);
}
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
public IConfiguration setScriptsLoadLazy(
   boolean bScriptsLoadLazy)
{
   set(kKEY_SCRIPTS_LOAD_LAZY, bScriptsLoadLazy ? "true" : "false");
   return(this);
}
/*------------------------------------------------------------------------------

@name       setSEOInfo - set seo info
                                                                              */
                                                                             /**
            Set seo info.

@param      seoInfo     seo info

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public IConfiguration setSEOInfo(
   SEOInfo seoInfo)
{
   set(kKEY_SEO_INFO, seoInfo);
   return(this);
}
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
public void setTagMap(
   Map<String,String> tagMap)
{
   set(kKEY_TAG_MAP_CUSTOM, tagMap);
}
/*------------------------------------------------------------------------------

@name       sharedInstance - get shared instance
                                                                              */
                                                                             /**
            Get shared instance.

@return     shared instance or null if not yet booted.

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static IConfiguration sharedInstance()
{
   return(sharedInstance);
}
/*==============================================================================

name:       CloudServices - cloud services configuration

purpose:    Cloud services configuration

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class CloudServices implements ICloudServices
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
protected String apiKey;               // apiKey                              //
protected String appId;                // appId                               //
protected String authDomain;           // authDomain                          //
protected String cloudPlatform;        // cloudPlatform                       //
protected String databaseURL;          // databaseURL                         //
protected String messagingSenderId;    // messagingSenderId                   //
protected String projectId;            // projectId                           //
protected String storageBucket;        // storageBucket                       //
protected String trackingId;           // trackingId                          //

/*------------------------------------------------------------------------------

@name       getAPIKey - get apiKey
                                                                              */
                                                                             /**
            Get apiKey.

@return     apiKey

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getAPIKey()
{
   return(apiKey);
}
/*------------------------------------------------------------------------------

@name       getAppId - get appId
                                                                              */
                                                                             /**
            Get appId.

@return     appId

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getAppId()
{
   return(appId);
}
/*------------------------------------------------------------------------------

@name       getAuthDomain - get auth domain
                                                                              */
                                                                             /**
            Get auth domain.

@return     auth domain

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getAuthDomain()
{
   return(authDomain);
}
/*------------------------------------------------------------------------------

@name       getCloudPlatform - get cloud platform
                                                                              */
                                                                             /**
            Get cloud platform.

@return     cloud platform

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getCloudPlatform()
{
   if (cloudPlatform == null)
   {
      cloudPlatform = kPLATFORM_DEFAULT;
   }
   return(cloudPlatform);
}
/*------------------------------------------------------------------------------

@name       getDatabaseURL - get database url
                                                                              */
                                                                             /**
            Get database url.

@return     database url

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getDatabaseURL()
{
   return(databaseURL);
}
/*------------------------------------------------------------------------------

@name       getCloudMessagingSenderId - get cloud messaging sender id
                                                                              */
                                                                             /**
            Get cloud messaging sender id.

@return     cloud messaging sender id

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getMessagingSenderId()
{
   return(messagingSenderId);
}
/*------------------------------------------------------------------------------

@name       getProjectId - get projectId
                                                                              */
                                                                             /**
            Get projectId.

@return     projectId

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getProjectId()
{
   return(projectId);
}
/*------------------------------------------------------------------------------

@name       getStorageBucket - get cloud storage bucket
                                                                              */
                                                                             /**
            Get cloud storage bucket.

@return     cloud storage bucket

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getStorageBucket()
{
   return(storageBucket);
}
/*------------------------------------------------------------------------------

@name       getTrackingId - get tracking id
                                                                              */
                                                                             /**
            Get tracking id.

@return     tracking id

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getTrackingId()
{
   return(trackingId);
}
/*------------------------------------------------------------------------------

@name       setAPIKey - set apiKey
                                                                              */
                                                                             /**
            Set apiKey.

@return     cloud services configfuration

@param      apiKey    apiKey

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public ICloudServices setAPIKey(
   String apiKey)
{
   this.apiKey = apiKey;
   return(this);
}
/*------------------------------------------------------------------------------

@name       setAppId - set appId
                                                                              */
                                                                             /**
            Set appId.

@return     cloud services configfuration

@param      appId    appId

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public ICloudServices setAppId(
   String appId)
{
   this.appId = appId;
   return(this);
}
/*------------------------------------------------------------------------------

@name       setAuthDomain - set auth domain
                                                                              */
                                                                             /**
            Set auth domain.

@return     cloud services configfuration

@param      authDomain    auth domain

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public ICloudServices setAuthDomain(
   String authDomain)
{
   this.authDomain = authDomain;
   return(this);
}
/*------------------------------------------------------------------------------

@name       setCloudPlatform - set cloud platform
                                                                              */
                                                                             /**
            Set cloud platform.

@return     cloud services configfuration

@param      cloudPlatform    cloud platform

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public ICloudServices setCloudPlatform(
   String cloudPlatform)
{
   this.cloudPlatform = cloudPlatform;
   return(this);
}
/*------------------------------------------------------------------------------

@name       setDatabaseURL - set database url
                                                                              */
                                                                             /**
            Set database url.

@return     cloud services configfuration

@param      databaseURL    databaseURL

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public ICloudServices setDatabaseURL(
   String databaseURL)
{
   this.databaseURL = databaseURL;
   return(this);
}
/*------------------------------------------------------------------------------

@name       setMessagingSenderId - set cloud messaging sender id
                                                                              */
                                                                             /**
            Set cloud messaging sender id.

@return     cloud services configfuration

@param      messagingSenderId    cloud messaging sender id

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public ICloudServices setMessagingSenderId(
   String messagingSenderId)
{
   this.messagingSenderId = messagingSenderId;
   return(this);
}
/*------------------------------------------------------------------------------

@name       setProjectId - set projectId
                                                                              */
                                                                             /**
            Set projectId.

@return     cloud services configfuration

@param      projectId    projectId

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public ICloudServices setProjectId(
   String projectId)
{
   this.projectId = projectId;
   return(this);
}
/*------------------------------------------------------------------------------

@name       setStorageBucket - set cloud storage bucket
                                                                              */
                                                                             /**
            Set cloud storage bucket.

@return     cloud services configfuration

@param      storageBucket    cloud storage bucket

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public ICloudServices setStorageBucket(
   String storageBucket)
{
   this.storageBucket = storageBucket;
   return(this);
}
/*------------------------------------------------------------------------------

@name       setTrackingId - set tracking id
                                                                              */
                                                                             /**
            Set tracking id.

@return     cloud services configfuration

@param      trackingId     trackingId

@history    Wed Jan 01, 2020 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public ICloudServices setTrackingId(
   String trackingId)
{
   this.trackingId = trackingId;
   return(this);
}
}//====================================// end CompileTime ====================//
}//====================================// end Configuration ------------------//
