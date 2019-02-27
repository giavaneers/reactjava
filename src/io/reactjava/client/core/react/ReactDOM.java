package io.reactjava.client.core.react;
import jsinterop.annotations.JsType;

/*==============================================================================

name:       ReactDOM - native react dom

purpose:    Native react dom

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
// namespace = IConfiguration.kSRCCFG_BUNDLE_SCRIPT ? "ReactJava" : JsPackage.GLOBAL
@JsType(isNative = true, namespace = "ReactJava")
public class ReactDOM
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

@name       render - standard render method
                                                                              */
                                                                             /**
            Standard render method.

@return     void

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native <C> Element render(Element element, C container);

}//====================================// end ReactDOM -----------------------//
