package io.reactjava.client.components.pdfviewer;
/*==============================================================================

name:       Point - x, y coordinate tuple

purpose:    x, y coordinate tuple

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public class Point
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
public double x;                       // x coordinate                        //
public double y;                       // y coordinate                        //
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
public Point()
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
public Point(
   double x,
   double y)
{
   this.x = x;
   this.y = y;
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
public Point(
   double[] array)
{
   this.x = array[0];
   this.y = array[1];
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
   return(new double[]{x, y});
}
}//====================================// end Point --------------------------//
