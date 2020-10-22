/*==============================================================================

name:       StringLiteralList - String Literal List

purpose:    String Literal List.

history:    Wed Sep 23, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
                                       // (none)                              //
                                       // StringLiteralList ================= //
public class StringLiteralList
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
public String[] elements;              // elements                            //

/*------------------------------------------------------------------------------

@name       StringLiteralList - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Wed Sep 23, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
StringLiteralList(
   String ...elements)
{
   this.elements = elements;
}
/*------------------------------------------------------------------------------

@name       newInstance - factory method
                                                                              */
                                                                             /**
            Factory method

@return     new instance

@param      elements    array of string literals

@history    Wed Sep 23, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static StringLiteralList newInstance(
  String ...elements)
{
   return(new StringLiteralList(elements));
}
}//====================================// end StringLiteralList ========================//
