/*==============================================================================

name:       React - native react api

purpose:    Native react api

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import jsinterop.annotations.JsType;
                                       // React ==============================//
// namespace = IConfiguration.kSRCCFG_BUNDLE_SCRIPT ? "ReactJava" : JsPackage.GLOBAL
@JsType(isNative = true, namespace = "ReactJava")
public class React
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

@name       React - default constructor
                                                                              */
                                                                             /**
            Default constructor

@return     An instance of React if successful.

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private React()
{
}
/*------------------------------------------------------------------------------

@name       createElement - create react element of an HTML standard type
                                                                              */
                                                                             /**
            Create react element of the specified HTML standard type.

@return     react element of the specified HTML standard type

@param      type           standard html tag
@param      props          properties

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native <P extends Properties> Element createElement(
   String type, P props);

public static native <P extends Properties> Element createElement(
   String type, P props, String value);

public static native <P extends Properties> Element createElement(
   String type, P props, Element ...children);

/*------------------------------------------------------------------------------

@name       createElement - create react element of the specified class
                                                                              */
                                                                             /**
            Convenience method to create react element of the specified class.

@return     react element of the specified class

@param      type        standard html tag
@param      props       properties
@param      children    child elements

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native <P extends Properties> Element createElement(
   INativeRenderableComponent<P> type, P props);

public static native <P extends Properties> Element createElement(
   INativeRenderableComponent<P> type, P props, String value);

public static native <P extends Properties> Element createElement(
   INativeRenderableComponent<P> type, P props, Element ...children);

/*------------------------------------------------------------------------------

@name       createElement - create react element of the specified class
                                                                              */
                                                                             /**
            Convenience method to create react element of the specified class.

@return     react element of the specified class

@param      type        standard html tag
@param      props       properties
@param      children    child elements

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native <P extends Properties> Element createElement(
   INativeComponentConstructor<P> type, P props);

public static native <P extends Properties> Element createElement(
   INativeComponentConstructor<P> type, P props, String value);

public static native <P extends Properties> Element createElement(
   INativeComponentConstructor<P> type, P props, Element ...children);

}//====================================// end React --------------------------//
