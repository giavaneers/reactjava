/*==============================================================================

name:       IJavascriptResources.java

purpose:    AngularJava code generator interface.

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:

                  This program was created by Giavaneers
        and is the confidential and proprietary product of Giavaneers Inc.
      Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2017 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.


==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.resources.javascript;
                                       // imports --------------------------- //
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ExternalTextResource;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
                                       // IJavascriptResources ===============//
public interface IJavascriptResources extends ClientBundle
{
                                       // class constants --------------------//
                                       // scripts are typically compressed,   //
                                       // whether they are preloaded or not,  //
                                       // since measurements indicate the     //
                                       // performance cost in decompression   //
                                       // is less than network transport time //
                                       // for most network speeds             //
public static final boolean kSRCCFG_SCRIPTS_COMPRESSED = true;

                                       // measurements indicate resources as  //
                                       // preload strategy is much slower     //
                                       // than writing each to the artifact   //
public static final boolean kSRCCFG_SCRIPTS_AS_RESOURCES = false;

                                       // using 'tiny' (double compression)   //
                                       // rather than 'min'                   //
                                       // results in about 25% additional     //
                                       // compression at the price of twice   //
                                       // the decompression time              //
                                       // 'max' for development...            //
public static final String  kSRCCFG_SCRIPTS_SET = "max";

                                       // scripts path prefix                 //
public static final String  kDIR = kSRCCFG_SCRIPTS_SET + "/";

                                       // main script path                    //
public static final String  kSCRIPT_MAIN = kDIR + "main.js";

                                       // reduces deployment size if scripts  //
                                       // are configured not to be resources  //
                                       // by making all script resources      //
                                       // essentially empty                   //
public static final String  kSCRIPT_NONE = kDIR + "EmptyScript.js";

                                       // platform scripts                    //
public static final String  kSCRIPT_PLAT_PAKO    = kDIR + "es5/pako.js";
public static final String  kSCRIPT_PLAT_CORE    = kDIR + "es5/core.js";
public static final String  kSCRIPT_PLAT_ZONE    = kDIR + "es5/zone.js";
public static final String  kSCRIPT_PLAT_REFLECT = kDIR + "es5/reflect-metadata.js";
public static final String  kSCRIPT_PLAT_RX      = kDIR + "es5/Rx.5.5.10.js";
public static final String  kSCRIPT_PLAT_TSLIB   = kDIR + "es5/tslib.js";

public static final Set<String> kSCRIPTS_PLATFORM =
   new LinkedHashSet<String>()
   {
      {
         if (kSRCCFG_SCRIPTS_COMPRESSED)
         {
            add(kSCRIPT_PLAT_PAKO);
         }
         add(kSCRIPT_PLAT_CORE);
         add(kSCRIPT_PLAT_ZONE);
         add(kSCRIPT_PLAT_REFLECT);
         add(kSCRIPT_PLAT_RX);
         add(kSCRIPT_PLAT_TSLIB);
      }
   };
                                       // angulargwt scripts                  //
public static final String kSCRIPTS_ANGULAR_GWT        = "angularGWT";
public static final String kSCRIPT_ANGULAR_GWT         = kDIR + "AngularGWT.js";

                                       // animations scripts                  //
public static final String kSCRIPTS_ANIMATIONS         = "animations";
public static final String kSCRIPT_ANIMATIONS_BROWSER  =
   kDIR + "@angular/animations/bundles/animations-browser.umd.js";
public static final String kSCRIPT_ANIMATIONS          =
   kDIR + "@angular/animations/bundles/animations.umd.js";

                                       // animations browser scripts          //
public static final String kSCRIPTS_ANIMATIONS_BROWSER = "animationsBrowser";
public static final String kSCRIPT_ANIMS_BROWSER_ANIM  =
   kDIR + "@angular/animations/bundles/animations.umd.js";
public static final String kSCRIPT_ANIMS_BROWSER_UMD   =
   kDIR + "@angular/animations/bundles/animations-browser.umd.js";
public static final String kSCRIPT_ANIMS_BROWSER_PLAT  =
   kDIR + "@angular/platform-browser/bundles/platform-browser-animations.umd.js";

                                       // browser scripts                     //
public static final String kSCRIPTS_BROWSER            = "browser";
public static final String kSCRIPT_BROWSER_PLAT        =
   kDIR + "@angular/platform-browser/bundles/platform-browser.umd.js";

public static final String kSCRIPTS_BROWSER_ESM5       = "browserESM5";
public static final String kSCRIPT_BROWSER_PLAT_ESM5   =
   kDIR + "@angular/platform-browser/esm5/platform-browser.js";

                                       // browser dynamic scripts             //
public static final String kSCRIPTS_BROWSER_DYNAMIC    = "browserDynamic";
public static final String kSCRIPT_BROWSER_DYNAMIC     =
   kDIR + "@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js";

public static final String kSCRIPTS_BROWSER_DYNAMIC_ESM5 = "browserDynamicESM5";
public static final String kSCRIPT_BROWSER_DYNAMIC_ESM5  =
   kDIR + "@angular/platform-browser-dynamic/esm5/platform-browser-dynamic.js";

                                       // common scripts                      //
public static final String kSCRIPTS_COMMON             = "common";
public static final String kSCRIPT_COMMON              =
   kDIR + "@angular/common/bundles/common.umd.js";

public static final String kSCRIPTS_COMMON_ESM5        = "commonESM5";
public static final String kSCRIPT_COMMON_ESM5         =
   kDIR + "@angular/common/esm5/common.js";

                                       // compiler scripts                    //
public static final String kSCRIPTS_COMPILER           = "compiler";
public static final String kSCRIPT_COMPILER            =
   kDIR + "@angular/compiler/bundles/compiler.umd.js";

public static final String kSCRIPTS_COMPILER_ESM5      = "compilerESM5";
public static final String kSCRIPT_COMPILER_ESM5       =
   kDIR + "@angular/compiler/esm5/compiler.js";

                                       // core scripts                        //
public static final String kSCRIPTS_CORE               = "core";
public static final String kSCRIPT_CORE                =
   kDIR + "@angular/core/bundles/core.umd.js";

public static final String kSCRIPTS_CORE_ESM5          = "coreESM5";
public static final String kSCRIPT_CORE_ESM5           =
   kDIR + "@angular/core/esm5/core.js";
/*
                                       // firebase auth scripts               //
public static final String kSCRIPTS_FIREAUTH = "firebaseAuth";

// built with google closure library calcdeps.py starting from firebase-js
// 'packages/auth/src/auth.js'.
//
// Command line invocation example:
// brianm$ > /Users/brianm/Desktop/closure-library-master/closure/bin/calcdeps.py
//           -i auth.js -p /Users/brianm/Desktop/closure-library-master/
//           -p . -o script > auth-calc.js
//
// see: https://developers.google.com/closure/library/docs/calcdeps

public static final String kSCRIPT_FIREAUTH_CALCDEPS =
   "@firebase/auth/auth-calcdeps.js";

public static final String kSCRIPT_FIREBASE =
   "@firebase/auth/firebase.js";
*/
                                       // form scripts                        //
public static final String kSCRIPTS_FORMS              = "forms";
public static final String kSCRIPT_FORMS               =
   kDIR + "@angular/forms/bundles/forms.umd.js";

public static final String kSCRIPTS_FORMS_ESM5         = "formsESM5";
public static final String kSCRIPT_FORMS_ESM5          =
   kDIR + "@angular/forms/esm5/forms.js";

                                       // http scripts                        //
public static final String kSCRIPTS_HTTP               = "http";
public static final String kSCRIPT_HTTP                =
   kDIR + "@material/@angular/http/bundles/http.umd.js";

public static final String kSCRIPTS_HTTP_ESM5          = "httpESM5";
public static final String kSCRIPT_HTTP_ESM5           =
   kDIR + "@material/@angular/http/esm5/http.js";

                                       // material scripts                    //
public static final String kSCRIPTS_MATERIAL           = "material";
public static final String kSCRIPT_MATERIAL_PLAT       =
   kDIR + "@material/@angular/cdk/bundles/cdk-platform.umd.js";
public static final String kSCRIPT_MATERIAL_PORTAL     =
   kDIR + "@material/@angular/cdk/bundles/cdk-portal.umd.js";
public static final String kSCRIPT_MATERIAL_KEYCODES   =
   kDIR + "@material/@angular/cdk/bundles/cdk-keycodes.umd.js";
public static final String kSCRIPT_MATERIAL_COERCION   =
   kDIR + "@material/@angular/cdk/bundles/cdk-coercion.umd.js";
public static final String kSCRIPT_MATERIAL_COLLECTIONS =
   kDIR + "@material/@angular/cdk/bundles/cdk-collections.umd.js";
public static final String kSCRIPT_MATERIAL_TABLE      =
   kDIR + "@material/@angular/cdk/bundles/cdk-table.umd.js";
public static final String kSCRIPT_MATERIAL_BIDI       =
   kDIR + "@material/@angular/cdk/bundles/cdk-bidi.umd.js";
public static final String kSCRIPT_MATERIAL_STEPPER    =
   kDIR + "@material/@angular/cdk/bundles/cdk-stepper.umd.js";
//public static final String kSCRIPT_MATERIAL_RXJS       =
//   kDIR + "@material/@angular/cdk/bundles/cdk-rxjs.umd.js";
public static final String kSCRIPT_MATERIAL_OBSERVERS  =
   kDIR + "@material/@angular/cdk/bundles/cdk-observers.umd.js";
public static final String kSCRIPT_MATERIAL_SCROLLING  =
   kDIR + "@material/@angular/cdk/bundles/cdk-scrolling.umd.js";
public static final String kSCRIPT_MATERIAL_LAYOUT     =
   kDIR + "@material/@angular/cdk/bundles/cdk-layout.umd.js";
public static final String kSCRIPT_MATERIAL_OVERLAY    =
   kDIR + "@material/@angular/cdk/bundles/cdk-overlay.umd.js";
public static final String kSCRIPT_MATERIAL_A11Y       =
   kDIR + "@material/@angular/cdk/bundles/cdk-a11y.umd.js";
public static final String kSCRIPT_MATERIAL_UMD        =
   kDIR + "@material/@angular/material/bundles/material.umd.js";

                                       // router scripts                      //
public static final String kSCRIPTS_ROUTER             = "router";
public static final String kSCRIPT_ROUTER              =
   kDIR + "@angular/router/bundles/router.umd.js";

public static final Map<String,String[]> kSCRIPT_URLS =
   new HashMap<String, String[]>()
   {{
      put(kSCRIPTS_ANGULAR_GWT, new String[]{kSCRIPT_ANGULAR_GWT});
      put(
         kSCRIPTS_ANIMATIONS,
         new String[]
         {
            kSCRIPT_ANIMATIONS_BROWSER,
            kSCRIPT_ANIMATIONS,
         });
      put(
         kSCRIPTS_ANIMATIONS_BROWSER,
         new String[]
         {
            kSCRIPT_ANIMS_BROWSER_ANIM,
            kSCRIPT_ANIMS_BROWSER_UMD,
            kSCRIPT_ANIMS_BROWSER_PLAT
         });
      put(kSCRIPTS_BROWSER,              new String[]{kSCRIPT_BROWSER_PLAT});
      put(kSCRIPTS_BROWSER_ESM5,         new String[]{kSCRIPT_BROWSER_PLAT_ESM5});
      put(kSCRIPTS_BROWSER_DYNAMIC,      new String[]{kSCRIPT_BROWSER_DYNAMIC});
      put(kSCRIPTS_BROWSER_DYNAMIC_ESM5, new String[]{kSCRIPT_BROWSER_DYNAMIC_ESM5});
      put(kSCRIPTS_COMMON,               new String[]{kSCRIPT_COMMON});
      put(kSCRIPTS_COMMON_ESM5,          new String[]{kSCRIPT_COMMON_ESM5});
      put(kSCRIPTS_COMPILER,             new String[]{kSCRIPT_COMPILER});
      put(kSCRIPTS_COMPILER_ESM5,        new String[]{kSCRIPT_COMPILER_ESM5});
      put(kSCRIPTS_CORE,                 new String[]{kSCRIPT_CORE});
      put(kSCRIPTS_CORE_ESM5,            new String[]{kSCRIPT_CORE_ESM5});
      //put(kSCRIPTS_FIREAUTH,           new String[]{kSCRIPT_FIREBASE});
      put(kSCRIPTS_FORMS,                new String[]{kSCRIPT_FORMS});
      put(kSCRIPTS_FORMS_ESM5,           new String[]{kSCRIPT_FORMS_ESM5});
      put(kSCRIPTS_HTTP,                 new String[]{kSCRIPT_HTTP});
      put(kSCRIPTS_HTTP_ESM5,            new String[]{kSCRIPT_HTTP_ESM5});
      put(
         kSCRIPTS_MATERIAL,
         new String[]
         {
                                    // order matters - interdependencies   //
            kSCRIPT_MATERIAL_PLAT,
            kSCRIPT_MATERIAL_PORTAL,
            kSCRIPT_MATERIAL_KEYCODES,
            kSCRIPT_MATERIAL_COERCION,
            kSCRIPT_MATERIAL_COLLECTIONS,
            kSCRIPT_MATERIAL_TABLE,
            kSCRIPT_MATERIAL_BIDI,
            kSCRIPT_MATERIAL_STEPPER,
            //kSCRIPT_MATERIAL_RXJS,
            kSCRIPT_MATERIAL_OBSERVERS,
            kSCRIPT_MATERIAL_SCROLLING,
            kSCRIPT_MATERIAL_LAYOUT,
            kSCRIPT_MATERIAL_OVERLAY,
            kSCRIPT_MATERIAL_A11Y,
            kSCRIPT_MATERIAL_UMD
         });
      put(kSCRIPTS_ROUTER, new String[]{kSCRIPT_ROUTER});
   }};
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // platform scripts                    //
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_PLAT_PAKO :          kSCRIPT_NONE)
ExternalTextResource Pako();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_PLAT_CORE :          kSCRIPT_NONE)
ExternalTextResource PlatformCore();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_PLAT_ZONE :          kSCRIPT_NONE)
ExternalTextResource PlatformZone();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_PLAT_REFLECT :       kSCRIPT_NONE)
ExternalTextResource PlatformReflect();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_PLAT_RX :            kSCRIPT_NONE)
ExternalTextResource PlatformRx();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_PLAT_TSLIB :         kSCRIPT_NONE)
ExternalTextResource PlatformTsLib();
                                       // angulargwt scripts                  //
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_ANGULAR_GWT :        kSCRIPT_NONE)
ExternalTextResource AngularGWT();
                                       // animations scripts                  //
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_ANIMATIONS_BROWSER : kSCRIPT_NONE)
ExternalTextResource AnimationsBrowser();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_ANIMATIONS :         kSCRIPT_NONE)
ExternalTextResource Animations();
                                       // animations browser scripts          //
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_ANIMS_BROWSER_ANIM : kSCRIPT_NONE)
ExternalTextResource AnimationsBrowserAnimations();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_ANIMS_BROWSER_UMD :  kSCRIPT_NONE)
ExternalTextResource AnimationsBrowserUMD();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_ANIMS_BROWSER_PLAT : kSCRIPT_NONE)
ExternalTextResource AnimationsBrowserPlatform();
                                       // browser scripts                     //
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_BROWSER_PLAT :       kSCRIPT_NONE)
ExternalTextResource BrowserPlatform();
                                       // browser dynamic scripts             //
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_BROWSER_DYNAMIC :    kSCRIPT_NONE)
ExternalTextResource BrowserDynamic();
                                       // common scripts                      //
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_COMMON :             kSCRIPT_NONE)
ExternalTextResource Common();
                                       // compiler scripts                    //
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_COMPILER :           kSCRIPT_NONE)
ExternalTextResource Compiler();
                                       // core scripts                        //
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_CORE :               kSCRIPT_NONE)
ExternalTextResource Core();
                                       // firebase auth scripts               //
//@Source(kSCRIPT_FIREAUTH_CALCDEPS)
//ExternalTextResource FireAuth();
//@Source(kSCRIPT_FIREBASE)
//ExternalTextResource Firebase();
                                       // form scripts                        //
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_FORMS :              kSCRIPT_NONE)
ExternalTextResource Forms();
                                       // http scripts                        //
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_HTTP :               kSCRIPT_NONE)
ExternalTextResource Http();
                                       // material scripts                    //
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_MATERIAL_PLAT :      kSCRIPT_NONE)
ExternalTextResource MaterialPlatform();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_MATERIAL_PORTAL :    kSCRIPT_NONE)
ExternalTextResource MaterialPortal();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_MATERIAL_KEYCODES :  kSCRIPT_NONE)
ExternalTextResource MaterialKeycodes();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_MATERIAL_COERCION :  kSCRIPT_NONE)
ExternalTextResource MaterialCoercion();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_MATERIAL_COLLECTIONS :kSCRIPT_NONE)
ExternalTextResource MaterialCollections();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_MATERIAL_TABLE :     kSCRIPT_NONE)
ExternalTextResource MaterialTable();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_MATERIAL_BIDI :      kSCRIPT_NONE)
ExternalTextResource MaterialBidi();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_MATERIAL_STEPPER :   kSCRIPT_NONE)
ExternalTextResource MaterialStepper();
//@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_MATERIAL_RXJS :      kSCRIPT_NONE)
//ExternalTextResource MaterialRxJs();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_MATERIAL_OBSERVERS : kSCRIPT_NONE)
ExternalTextResource MaterialObservers();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_MATERIAL_SCROLLING : kSCRIPT_NONE)
ExternalTextResource MaterialScrolling();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_MATERIAL_LAYOUT :    kSCRIPT_NONE)
ExternalTextResource MaterialLayout();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_MATERIAL_OVERLAY :   kSCRIPT_NONE)
ExternalTextResource MaterialOverlay();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_MATERIAL_A11Y :      kSCRIPT_NONE)
ExternalTextResource MaterialA11Y();
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_MATERIAL_UMD :       kSCRIPT_NONE)
ExternalTextResource MaterialUMD();
                                       // router scripts                      //
@Source(kSRCCFG_SCRIPTS_AS_RESOURCES ? kSCRIPT_ROUTER :             kSCRIPT_NONE)
ExternalTextResource Router();
}//====================================// end IJavascriptResources ===========//
