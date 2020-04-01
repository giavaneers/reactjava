package io.reactjava.client.components.pdfviewer;
/*==============================================================================

name:       Transform - transform

purpose:    Transform converting pdf coordinate system to the normal canvas
            like coordinates taking in account scale and rotation  and offset.

            Wrapper for native double array, where

               [0] rotation A
               [1] rotation B
               [2] rotation C
               [3] rotation D
               [4] offset   X
               [5] offset   Y

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public class Transform
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
public double rotationA;               // rotation A                          //
public double rotationB;               // rotation B                          //
public double rotationC;               // rotation C                          //
public double rotationD;               // rotation D                          //
public double offsetX;                 // offset x                            //
public double offsetY;                 // offset y                            //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Point - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Transform()
{
}
/*------------------------------------------------------------------------------

@name       Point - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Transform(
   double rotationA,
   double rotationB,
   double rotationC,
   double rotationD,
   double offsetX,
   double offsetY)
{
   this.rotationA = rotationA;
   this.rotationB = rotationB;
   this.rotationC = rotationC;
   this.rotationD = rotationD;
   this.offsetX   = offsetX;
   this.offsetY   = offsetY;
}
/*------------------------------------------------------------------------------

@name       Point - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Transform(
   double[] array)
{
   this.rotationA = array[0];
   this.rotationB = array[1];
   this.rotationC = array[2];
   this.rotationD = array[3];
   this.offsetX   = array[4];
   this.offsetY   = array[5];
}
/*------------------------------------------------------------------------------

@name       Point - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public double[] asDoubleArray()
{
   return(
      new double[]
      {
         rotationA, rotationB, rotationC, rotationD, offsetX, offsetY
      });
}
}//====================================// end Transform ----------------------//
