package io.reactjava.client.components.pdfviewer;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/*==============================================================================

name:       PDFJS - native pdfjs api

purpose:    Native pdfjs api

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
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
