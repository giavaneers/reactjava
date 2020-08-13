/*==============================================================================

name:       Atom.java

purpose:    Speck Atom

history:    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.speck;
                                       // imports --------------------------- //
                                       // (none)                              //
                                       // Atom ===============================//
public class Atom
{
                                       // class constants ------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public String symbol;                  // symbol                              //
public float  x;                       // position x                          //
public float  y;                       // position y                          //
public float  z;                       // position z                          //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       Atom - constructor
                                                                              */
                                                                             /**
            constructor

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Atom(
   String  symbol,
   float[] position)
{
   this.symbol = symbol;
   this.x      = position[0];
   this.y      = position[1];
   this.z      = position[2];
}
/*------------------------------------------------------------------------------

@name       Atom - constructor
                                                                              */
                                                                             /**
            constructor

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Atom(
   String symbol,
   float  x,
   float  y,
   float  z)
{
   this.symbol = symbol;
   this.x      = x;
   this.y      = y;
   this.z      = z;
}
}//====================================// end Atom ===========================//
