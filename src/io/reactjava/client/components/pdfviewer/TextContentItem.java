package io.reactjava.client.components.pdfviewer;

import io.reactjava.client.core.react.NativeObject;
import jsinterop.annotations.JsProperty;

/*==============================================================================

name:       TextContentItem - pdf page text content item

purpose:    pdf page text content item

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public class TextContentItem extends NativeObject
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
public @JsProperty
                                       String   str;       // text string                         //
public @JsProperty String   dir;       // layout : {"ltr", ...}               //
public @JsProperty double   width;     // width                               //
public @JsProperty double   height;    // height                              //
public @JsProperty double[] transform; // transform, six doubles              //
public @JsProperty String   fontName;  // font name                           //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       TextItem - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private TextContentItem()
{
}
/*------------------------------------------------------------------------------

@name       toString - standard toString() method
                                                                              */
                                                                             /**
            Standard toString() method.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String toString(
   TextContentItem item)
{
   return(toString(item, -1));
}
/*------------------------------------------------------------------------------

@name       toString - standard toString() method
                                                                              */
                                                                             /**
            Standard toString() method.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String toString(
   TextContentItem item,
   int             pageIndex)
{
   String t = "";
   for (double elem : item.transform)
   {
      if (t.length() > 0)
      {
         t += ", ";
      }
      t += elem;
   }
   t += "]";

   String s =
      "str="          + item.str
    + ", dir="        + item.dir
    + ", width="      + item.width
    + ", height="     + item.height
    + ", transform=[" + t
    + ", fontName="   + item.fontName;

   if (pageIndex >= 0)
   {
      Point canvasOffset =
         PDFViewer.sharedInstance().getPDFViewer().getCanvasOffset(
            new Point(item.transform[4], item.transform[5]), pageIndex);

      s += ", canvasX=" + canvasOffset.x + ", canvasY=" + canvasOffset.y;
   }

   return(s);
}
}//====================================// end TextContentItem ----------------//
