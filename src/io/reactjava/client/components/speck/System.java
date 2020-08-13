/*==============================================================================

name:       System

purpose:    Native Speck System

history:    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.speck;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;
                                       // imports --------------------------- //
                                       // (none)                              //
                                       // System =============================//
@JsType(isNative  = true, namespace = "ReactJava.ReactJavaComponents.Speck")
public class System
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

@name       System - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private System()
{
}
/*------------------------------------------------------------------------------

@name       addAtom - add atom
                                                                              */
                                                                             /**
            Add atom

@param      s           system instance
@param      symbol      atom symbol
@param      x           atom position x
@param      y           atom position y
@param      z           atom position z

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void addAtom(
   System s,
   String symbol,
   float  x,
   float  y,
   float  z);

/*------------------------------------------------------------------------------

@name       calculateBonds - calculate bonds
                                                                              */
                                                                             /**
            Calculate bonds

@param      s           system instance

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void calculateBonds(System s);

/*------------------------------------------------------------------------------

@name       center - center image
                                                                              */
                                                                             /**
            Center image

@param      s           system instance

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void center(System s);

/*------------------------------------------------------------------------------

@name       newInstance - factory method
                                                                              */
                                                                             /**
            Factory method

@return     system instance

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod(namespace = "ReactJava.ReactJavaComponents.Speck", name = "System.new")
public static native System newInstance();

}//====================================// end System -------------------------//
