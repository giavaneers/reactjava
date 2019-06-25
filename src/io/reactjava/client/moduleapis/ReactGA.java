/*==============================================================================

name:       ReactGA - native google analytics api

purpose:    Native google analytics api for node module 'react-ga' included
            for an App with something like the following override:

            protected List<String> getImportedNodeModules()
            {
               return(Arrays.asList("react-ga"));
            }

history:    Wed Jun 10, 2019 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.moduleapis;
                                       // imports --------------------------- //
import jsinterop.annotations.JsType;
                                       // ReactGA ============================//
@JsType(isNative = true, namespace = "ReactJava", name = "reactGa")
public class ReactGA
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       initialize - initialize google analytics api
                                                                              */
                                                                             /**
            Initialize google analytics api.

@param      googleAnalyticsId    id

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native static void initialize(
   String googleAnalyticsId);

/*------------------------------------------------------------------------------

@name       pageview - report a pageview
                                                                              */
                                                                             /**
            Report a pageview.

@param      rawPath    path of page

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native static void pageview(
   String rawPath);

}//====================================// end ReactGA ------------------------//
