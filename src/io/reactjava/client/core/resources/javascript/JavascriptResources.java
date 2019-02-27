/*==============================================================================

name:       JavascriptResources.java

purpose:    JavascriptResources.

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
import com.giavaneers.util.gwt.APIRequestor;
import com.giavaneers.util.gwt.Logger;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ExternalTextResource;
import com.google.gwt.resources.client.ResourceCallback;
import com.google.gwt.resources.client.ResourceException;
import com.google.gwt.resources.client.TextResource;
import java.util.HashMap;
import java.util.Map;
                                       // JavascriptResources ================//
public class JavascriptResources
{
                                       // class constants --------------------//
private static final Logger kLOGGER = Logger.newInstance();

public static final IJavascriptResources kINSTANCE =
   IJavascriptResources.kSRCCFG_SCRIPTS_AS_RESOURCES
      ? GWT.create(IJavascriptResources.class) : null;

public static final Map<String,ExternalTextResource> kRESOURCEMAP =
   IJavascriptResources.kSRCCFG_SCRIPTS_AS_RESOURCES
   ?
   new HashMap<String,ExternalTextResource>()
   {
      {
                                       // platform scripts                    //
         put(kINSTANCE.kSCRIPT_PLAT_PAKO,          kINSTANCE.Pako());
         put(kINSTANCE.kSCRIPT_PLAT_CORE,          kINSTANCE.PlatformCore());
         put(kINSTANCE.kSCRIPT_PLAT_ZONE,          kINSTANCE.PlatformZone());
         put(kINSTANCE.kSCRIPT_PLAT_REFLECT,       kINSTANCE.PlatformReflect());
         put(kINSTANCE.kSCRIPT_PLAT_RX,            kINSTANCE.PlatformRx());
         put(kINSTANCE.kSCRIPT_PLAT_TSLIB,         kINSTANCE.PlatformTsLib());

                                       // angulargwt scripts                  //
         put(kINSTANCE.kSCRIPT_ANGULAR_GWT,        kINSTANCE.AngularGWT());

                                       // animations scripts                  //
         put(kINSTANCE.kSCRIPT_ANIMATIONS_BROWSER, kINSTANCE.AnimationsBrowser());
         put(kINSTANCE.kSCRIPT_ANIMATIONS,         kINSTANCE.Animations());

                                       // animations browser scripts          //
         put(kINSTANCE.kSCRIPT_ANIMS_BROWSER_ANIM, kINSTANCE.AnimationsBrowserAnimations());
         put(kINSTANCE.kSCRIPT_ANIMS_BROWSER_UMD,  kINSTANCE.AnimationsBrowserUMD());
         put(kINSTANCE.kSCRIPT_ANIMS_BROWSER_PLAT, kINSTANCE.AnimationsBrowserPlatform());

                                       // browser scripts                     //
         put(kINSTANCE.kSCRIPT_BROWSER_PLAT,       kINSTANCE.BrowserPlatform());

                                       // browser dynamic scripts             //
         put(kINSTANCE.kSCRIPT_BROWSER_DYNAMIC,    kINSTANCE.BrowserDynamic());

                                       // common scripts                      //
         put(kINSTANCE.kSCRIPT_COMMON,             kINSTANCE.Common());

                                       // compiler scripts                    //
         put(kINSTANCE.kSCRIPT_COMPILER,           kINSTANCE.Compiler());

                                       // core scripts                        //
         put(kINSTANCE.kSCRIPT_CORE,               kINSTANCE.Core());

                                       // firebase auth scripts               //
         //put(kINSTANCE.kSCRIPT_FIREAUTH_CALCDEPS,  kINSTANCE.FireAuth());
         //put(kINSTANCE.kSCRIPT_FIREBASE,           kINSTANCE.Firebase());

                                       // form scripts                        //
         put(kINSTANCE.kSCRIPT_FORMS,              kINSTANCE.Forms());

                                       // http scripts                        //
         put(kINSTANCE.kSCRIPT_HTTP,               kINSTANCE.Http());

                                       // material scripts                    //
         put(kINSTANCE.kSCRIPT_MATERIAL_PLAT,      kINSTANCE.MaterialPlatform());
         put(kINSTANCE.kSCRIPT_MATERIAL_PORTAL,    kINSTANCE.MaterialPortal());
         put(kINSTANCE.kSCRIPT_MATERIAL_KEYCODES,  kINSTANCE.MaterialKeycodes());
         put(kINSTANCE.kSCRIPT_MATERIAL_COERCION,  kINSTANCE.MaterialCoercion());
         put(kINSTANCE.kSCRIPT_MATERIAL_COLLECTIONS,kINSTANCE.MaterialCollections());
         put(kINSTANCE.kSCRIPT_MATERIAL_TABLE,     kINSTANCE.MaterialTable());
         put(kINSTANCE.kSCRIPT_MATERIAL_BIDI,      kINSTANCE.MaterialBidi());
         put(kINSTANCE.kSCRIPT_MATERIAL_STEPPER,   kINSTANCE.MaterialStepper());
         //put(kINSTANCE.kSCRIPT_MATERIAL_RXJS,      kINSTANCE.MaterialRxJs());
         put(kINSTANCE.kSCRIPT_MATERIAL_OBSERVERS, kINSTANCE.MaterialObservers());
         put(kINSTANCE.kSCRIPT_MATERIAL_SCROLLING, kINSTANCE.MaterialScrolling());
         put(kINSTANCE.kSCRIPT_MATERIAL_LAYOUT,    kINSTANCE.MaterialLayout());
         put(kINSTANCE.kSCRIPT_MATERIAL_OVERLAY,   kINSTANCE.MaterialOverlay());
         put(kINSTANCE.kSCRIPT_MATERIAL_A11Y,      kINSTANCE.MaterialA11Y());
         put(kINSTANCE.kSCRIPT_MATERIAL_UMD,       kINSTANCE.MaterialUMD());

                                       // router scripts                      //
         put(kINSTANCE.kSCRIPT_ROUTER, kINSTANCE.Router());
      }
   }
   : null;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       getScriptTextByPath - get script resource text by script path
                                                                              */
                                                                             /**
            Get script resource text by script path.

@return     void

@param      path              script path
@param      requestToken      client request token
@param      requestor         client requestor

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void getScriptTextByPath(
   String       path,
   Object       requestToken,
   APIRequestor requestor)
{
   try
   {
      kLOGGER.logInfo("JavascriptResources.getScriptTextByPath(): entered");

      kRESOURCEMAP.get(path).getText(
         new ResourceCallback<TextResource>()
         {
            public void onError(ResourceException e)
            {
               kLOGGER.logInfo("JavascriptResources.getScriptTextByPath(): ERR");
               requestor.apiResponse(e, requestToken);
            }
            public void onSuccess(TextResource r)
            {
               kLOGGER.logInfo("JavascriptResources.getScriptTextByPath(): OK");
               requestor.apiResponse(r.getText(), requestToken);
            }
      });
   }
   catch(ResourceException e)
   {
      requestor.apiResponse(e, requestToken);
   }
}
}//====================================// end JavascriptResources ============//
