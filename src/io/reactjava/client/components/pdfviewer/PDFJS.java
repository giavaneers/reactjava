/*==============================================================================

name:       PDFJS.java

purpose:    Native pdfjs api

history:    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.pdfviewer;
                                       // imports --------------------------- //
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
                                       // PDFJS ==============================//
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "pdfjsLib")
public class PDFJS
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // global worker option                //
@JsProperty
public static GlobalWorkerOptions GlobalWorkerOptions;

                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       PDFJS - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public PDFJS()
{
}
/*------------------------------------------------------------------------------

@name       getDocument - get pdf document for specified url
                                                                              */
                                                                             /**
            Get pdf document for specified url.

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native DocumentLoadingTask getDocument(
   String documentURL);

}//====================================// end PDFJS --------------------------//
