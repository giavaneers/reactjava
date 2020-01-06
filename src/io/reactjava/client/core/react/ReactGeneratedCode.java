/*==============================================================================

name:       ReactGeneratedCode.java

purpose:    Will be autogenerated at compile time.

history:    Sat Jan 05, 2019 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import jsinterop.annotations.JsMethod;
                                       // ReactGeneratedCode =================//
public class ReactGeneratedCode
{
                                       // class constants --------------------//
public static final String kREACT_JAVA_DIR_NAME       = "reactjava";
public static final String kIMPORTED_STYLESHEETS_LIST = "importedStyleSheets.txt";

protected static final Map<String,Function> kFACTORY_MAP;

                                       // class variables ------------------- //
                                       // none                                //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
static
{
   kFACTORY_MAP = new HashMap<>();

   kFACTORY_MAP.put(
      "io.reactjava.client.core.providers.analytics.firebase.GoogleAnalyticsService",
      (Function<Properties,IProvider>)(props) ->
      {
         return(new io.reactjava.client.core.providers.analytics.firebase.GoogleAnalyticsService(props));
      });

   kFACTORY_MAP.put(
      "io.reactjava.client.core.providers.auth.firebase.FirebaseAuthenticationService",
      (Function<Properties,IProvider>)(props) ->
      {
         return(new io.reactjava.client.core.providers.auth.firebase.FirebaseAuthenticationService(props));
      });

   kFACTORY_MAP.put(
      "io.reactjava.client.core.providers.database.firebase.FirebaseDatabaseService",
      (Function<Properties,IProvider>)(props) ->
      {
         return(new io.reactjava.client.core.providers.database.firebase.FirebaseDatabaseService(props));
      });

   kFACTORY_MAP.put(
      "io.reactjava.client.core.providers.http.HttpClientBase",
      (Function<Properties,IProvider>)(props) ->
      {
         return(new io.reactjava.client.core.providers.http.HttpClientBase(props));
      });

   kFACTORY_MAP.put(
      "io.reactjava.client.core.providers.platform.mobile.android.PlatformAndroid",
      (Function<Properties,IProvider>)(props) ->
      {
         return(new io.reactjava.client.core.providers.platform.mobile.android.PlatformAndroid(props));
      });

   kFACTORY_MAP.put(
      "io.reactjava.client.core.providers.platform.mobile.ios.PlatformIOS",
      (Function<Properties,IProvider>)(props) ->
      {
         return(new io.reactjava.client.core.providers.platform.mobile.ios.PlatformIOS(props));
      });

   kFACTORY_MAP.put(
      "io.reactjava.client.core.providers.platform.web.PlatformWeb",
      (Function<Properties,IProvider>)(props) ->
      {
         return(new io.reactjava.client.core.providers.platform.web.PlatformWeb(props));
      });
//%componentEntries%
}
/*------------------------------------------------------------------------------

@name       boot - application entry point
                                                                              */
                                                                             /**
            aApplication entry point.

@return     void

@param      className      app class name

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public static ReactElement boot(
   String className)
{
   return(
      ReactJava.boot(
      (AppComponentTemplate)ReactJava.getComponentFactory(
         className).apply(null)));
}
/*------------------------------------------------------------------------------

@name       getFactory - get factory for specified classname
                                                                              */
                                                                             /**
            Get factory for specified classname.

@return     factory for specified classname

@param      classname      specified classname

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Function getFactory(
   String classname)
{
   return(kFACTORY_MAP.get(classname));
}
/*------------------------------------------------------------------------------

@name       getFactoryMap - get factory map
                                                                              */
                                                                             /**
            Get factory map.

@return     factory map

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Map<String,Function> getFactoryMap()
{
   return(kFACTORY_MAP);
}
/*------------------------------------------------------------------------------

@name       getPlatformProvider - get platform provider to be injected
                                                                              */
                                                                             /**
            Get platform provider to be injected.

@return     platform provider to be injected

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getPlatformProvider()
{
   return("%platformProviderClassname%");
}
}//====================================// ReactGeneratedCode =================//
