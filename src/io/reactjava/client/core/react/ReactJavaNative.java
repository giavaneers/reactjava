/*==============================================================================

name:       ReactJavaNative - native reactjava api

purpose:    Native reactjava api

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
                                       // ReactJavaNative ====================//
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "ReactJava")
public class ReactJavaNative
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

@name       ReactJavaNative - default constructor
                                                                              */
                                                                             /**
            Default constructor

@return     An instance of ReactJavaNative if successful.

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private ReactJavaNative()
{
}
/*------------------------------------------------------------------------------

@name       getType - get native component type
                                                                              */
                                                                             /**
            Get native component type.

@return     native component type

@param      type     native component type name

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native INativeComponentConstructor getType(String type);

}//====================================// end ReactJavaNative ----------------//
