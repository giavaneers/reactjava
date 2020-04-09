/*==============================================================================

name:       ReactElement - native react element

purpose:    Native react element

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class ReactElement
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
@JsProperty
public Properties props;               // native properties                   //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       forceUpdate - force update on component tree
                                                                              */
                                                                             /**
            Force update on component tree.

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void forceUpdate();

/*------------------------------------------------------------------------------

@name       getChildren - get children elements
                                                                              */
                                                                             /**
            Get children elements.

@return     children elements

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final ReactElement[] getChildren()
{
   return(Js.uncheckedCast(props.get("children")));
}
/*------------------------------------------------------------------------------

@name       setChildren - set children elements
                                                                              */
                                                                             /**
            Set children elements.

@param      children    children elements

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final void setChildren(
   ReactElement[] children)
{
   props.set("children", children);
}
}//====================================// end ReactElement ------------------------//
