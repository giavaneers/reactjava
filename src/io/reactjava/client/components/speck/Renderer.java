/*==============================================================================

name:       Renderer

purpose:    Native Speck Renderer

history:    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.speck;

import elemental2.dom.Element;
import jsinterop.annotations.JsType;
                                       // imports --------------------------- //
                                       // (none)                              //
                                       // Renderer ===========================//
@JsType(isNative  = true, namespace = "ReactJava.ReactJavaComponents.Speck")
public class Renderer
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Renderer - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Renderer(
   Element imposterCanvas,
   int     resolution,
   int     aoRes)
{
}
/*------------------------------------------------------------------------------

@name       getAOProgress - getAOProgress
                                                                              */
                                                                             /**
            Get AOProgress

@return     progress

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native float getAOProgress();

/*------------------------------------------------------------------------------

@name       render - render
                                                                              */
                                                                             /**
            Render

@param      view           view

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native void render(View view);

/*------------------------------------------------------------------------------

@name       reset - reset
                                                                              */
                                                                             /**
            Reset

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native void reset();

/*------------------------------------------------------------------------------

@name       setSystem - set system and view
                                                                              */
                                                                             /**
            Set system and view

@param      newSystem      new system instance
@param      view           view

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native void setSystem(System newSystem, View view);

}//====================================// end Renderer -----------------------//
