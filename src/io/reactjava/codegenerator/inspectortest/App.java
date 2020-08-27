/*==============================================================================

name:       App.java

purpose:    Google Analytics Reporting Example App.

            see   "https://flaviocopes.com/google-analytics-api-nodejs/"
                  "#import-the-google-library"

history:    Fri Jan 03, 2020 10:30:00 (Giavaneers - LBM) created

notes:

                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.codegenerator.inspectortest;
                                       // imports --------------------------- //
import io.reactjava.client.core.react.Configuration.CloudServices;
import io.reactjava.client.core.react.IConfiguration.ICloudServices;
import java.util.Arrays;
import java.util.List;
                                       // App ================================//
public class App extends AppComponentTemplate
{
                                       // class constants --------------------//
                                       // unlike for other config parameters, //
                                       // if the only cloud service to be     //
                                       // used is Google Analytics, only the  //
                                       // 'trackingId' parameter need be      //
                                       // specified                           //
public static final ICloudServices kCLOUD_SERVICES_CONFIG =
   new CloudServices().setTrackingId("G-ZNP1NLDLLB");

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       getCloudServicesConfig - get cloud services configuration
                                                                              */
                                                                             /**
            Get cloud services configuration.

@return     cloud services configuration.

@history    Fri Jan 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected ICloudServices getCloudServicesConfig()
{
   return(kCLOUD_SERVICES_CONFIG);
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
   return(Arrays.asList(
      "embedded:javascript/tfjs-vis@1.4.0.umd.min.js as ReactJava.tfvis"
   ));
}
/*------------------------------------------------------------------------------

@name       getImportedNodeModules - get imported node modules
                                                                              */
                                                                             /**
            Get imported node modules.

@return     list of node module names.

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected List<String> getImportedNodeModules()
{
   return(Arrays.asList(
      "googleapis.build.src.googleapis"
   ));
}
/*------------------------------------------------------------------------------

@name       render - render component
                                                                              */
                                                                             /**
            Render component. This implementation is all markup, with no java
            code included.

@history    Fri Jan 03, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final void render()
{
/*--
   <h1 style='color:blue;marginTop:30px;fontSize:20px'>
      Analytics ReportData Example
   </h1>
--*/
}
}//====================================// end App ============================//

