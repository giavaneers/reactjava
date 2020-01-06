/*==============================================================================

name:       GoogleAPIs - native google apis

purpose:    Native google apis for node module 'googleapis' included
            for an App with something like the following override:

            protected List<String> getImportedNodeModules()
            {
               return(Arrays.asList("googleapis"));
            }

history:    Fri Jan 03, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.moduleapis;
                                       // imports --------------------------- //
import elemental2.core.JsObject;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
                                       // GoogleAPIs =========================//
@JsType(isNative = true, namespace = "ReactJava", name = "googleapis")
public class GoogleAPIs
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
@JsProperty
public JsObject google;                // google                              //

}//====================================// end GoogleAPIs ---------------------//
