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
import elemental2.core.JsArray;
import jsinterop.annotations.JsType;
                                       // React ==============================//
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
public static native <P extends Properties> ReactElement createElement(
   String type, P props);

public static native <P extends Properties> ReactElement createElement(
   String type, P props, String value);

public static native <P extends Properties> ReactElement createElement(
   String type, P props, ReactElement...children);

/*------------------------------------------------------------------------------

@name       createElement - create react element of the specified class
                                                                              */
                                                                             /**
            Convenience method to create react element of the specified class.

@return     react element of the specified class

@param      type        standard html tag
@param      props       properties

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native <P extends Properties> ReactElement createElement(
   INativeFunctionalComponent<P> type, P props);

public static native <P extends Properties> ReactElement createElement(
   INativeFunctionalComponent<P> type, P props, String value);

public static native <P extends Properties> ReactElement createElement(
   INativeFunctionalComponent<P> type, P props, ReactElement...children);

/*------------------------------------------------------------------------------

@name       createElement - create react element of the specified class
                                                                              */
                                                                             /**
            Convenience method to create react element of the specified class.

@return     react element of the specified class

@param      type        standard html tag
@param      props       properties

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native <P extends Properties> ReactElement createElement(
   INativeComponentConstructor<P> type, P props);

public static native <P extends Properties> ReactElement createElement(
   INativeComponentConstructor<P> type, P props, String value);

public static native <P extends Properties> ReactElement createElement(
   INativeComponentConstructor<P> type, P props, ReactElement...children);

/*------------------------------------------------------------------------------

@name       useEffect - useEffect hook
                                                                              */
                                                                             /**
            useEffect hook.

@param      effectHandler     effect function to be invoked when component
                              mounted, unmounted, and updated.

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void useEffect(
   INativeEffectHandler effectHandler);

/*------------------------------------------------------------------------------

@name       useEffect - useEffect hook
                                                                              */
                                                                             /**
            useEffect hook.

@param      effectHandler     effect function.

@param      dependencies      array of property and state values when changed
                              will also cause the effect handler to be
                              invoked; passing an empty array will cause the
                              effect handler to be invoked only on component
                              mounted and unmounted (not on update).

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void useEffect(
   INativeEffectHandler effectHandler,
   Object[]             dependencies);

/*------------------------------------------------------------------------------

@name       useRef - useRef hook
                                                                              */
                                                                             /**
            useRef hook.

@param      value       initial ref value

@history    Thu Jul 25, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native JsArray useRef(
   Object value);

/*------------------------------------------------------------------------------

@name       useState - useState hook
                                                                              */
                                                                             /**
            useState hook.

@return     Properties instance containing the following attributes:


@param      value       initial state

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native JsArray useState(
   Object value);

}//====================================// end React --------------------------//
