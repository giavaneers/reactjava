/*==============================================================================

name:       ReactCodeGeneratorImplementation.java

purpose:    ReactJava code generator implementation, will be autogenerated at
            compile time.

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
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
                                       // ReactCodeGeneratorImplementation ===//
public class ReactCodeGeneratorImplementation implements IReactCodeGenerator
{
                                       // class constants --------------------//
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
      "io.reactjava.client.core.providers.auth.firebase.FirebaseAuthenticationService",
      (Function<Properties,IProvider>)(props) ->
      {
         return(new io.reactjava.client.core.providers.auth.firebase.FirebaseAuthenticationService(props));
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

@name       ReactCodeGeneratorImplementation - default constructor
                                                                              */
                                                                             /**
            Default constructor

@return     An instance of ReactCodeGeneratorImplementation if successful.

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsConstructor
public ReactCodeGeneratorImplementation()
{
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
public ReactElement boot(
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

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Function getFactory(
   String classname)
{
   return(kFACTORY_MAP.get(classname));
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
public String getPlatformProvider()
{
   return("%platformProviderClassname%");
}
}//====================================// ReactCodeGeneratorImplementation ===//
