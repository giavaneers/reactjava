package io.reactjava.client.components.pdfviewer;

import elemental2.core.Global;
import java.util.HashMap;
import java.util.Map;
import jsinterop.base.Js;

/*==============================================================================

name:       Bookmark - bookmark

purpose:    Tuple of Destination with associated text

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public class Bookmark implements Comparable<Bookmark>
{
                                       // class constants --------------------//
                                       // bookmark by id                      //
public static final Map<String,Bookmark>
                     bookmarkById = new HashMap<>();

                                       // class variables ------------------- //
public static double maxHeight;        // max height                          //
                                       // public instance variables --------- //
public Destination   dest;             // destination                         //
public int           pageIndex;        // page index                          //
                                       // protected instance variables -------//
public    double     height;           // height                              //
public    double     width;            // width                               //
protected String     text;             // associated text                     //
protected String     id;               // string hash                         //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Bookmark - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Bookmark()
{
}
/*------------------------------------------------------------------------------

@name       Bookmark - constructor
                                                                              */
                                                                             /**
            Constructor

@param      dest           explicit destination
@param      pageIndex      page index

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Bookmark(
   int         pageIndex,
   Destination dest)
{
   this.pageIndex = pageIndex;
   this.dest      = dest;
}
/*------------------------------------------------------------------------------

@name       compareTo - standard compareTo method
                                                                              */
                                                                             /**
            Standard compareTo method, based on page index primarily and yPos
            secondarily. Order set so sort() on collection of bookmarks arranges
            members from low offset.y to high.

@param      other    target of comparison

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int compareTo(
   Bookmark other)
{
   int retVal = 1;
   if (this.pageIndex < other.pageIndex)
   {
      retVal = -1;
   }
   else if (this.pageIndex == other.pageIndex)
   {
      double y      = dest.getOffset().y;
      double yOther = other.dest.getOffset().y;

      if (y > yOther)
      {
         retVal = -1;
      }
      else if (y == yOther)
      {
         retVal = 0;
      }
   }

   return retVal;
}
/*------------------------------------------------------------------------------

@name       equals - standard equals method
                                                                              */
                                                                             /**
            Standard equals method.

@param      other    target of comparison

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean equals(
   Bookmark other)
{
   return(pageIndex == other.pageIndex && dest.equals(other.dest));
}
/*------------------------------------------------------------------------------

@name       fromSafeURL - from bookmark safe url to bookmark url
                                                                              */
                                                                             /**
            URL decoded version of bookmark url.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String fromSafeURL(
   String safeURL)
{
   return(Global.decodeURI(safeURL));
}
/*------------------------------------------------------------------------------

@name       getCanvasOffset - get offset in canvas coordinates
                                                                              */
                                                                             /**
            Get offset in canvas coordinates.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Point getCanvasOffset()
{
   Point canvasOffset =
      PDFViewer.sharedInstance().getPDFViewer().getCanvasOffset(
         dest.getOffset(), pageIndex);

   return(canvasOffset);
};
/*------------------------------------------------------------------------------

@name       getId - get id
                                                                              */
                                                                             /**
            Get id.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getId()
{
   return(id);
};
/*------------------------------------------------------------------------------

@name       getText - get text
                                                                              */
                                                                             /**
            Get text. This implementation gets the value lazily if not already
            assigned.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getText()
{
   return(text);
};
/*------------------------------------------------------------------------------

@name       setTextContent - set text content
                                                                              */
                                                                             /**
            Assign text from the corresponding page content.

@param      pageContent page content for the corresponding page.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void setTextContent(
   TextContent pageContent)
{
   double destY = dest.getOffset().y;

   for (int i = 0; i < pageContent.items.length; i++)
   {
                                       // items are sorted by pdf coordinate  //
                                       // y, top of the page to the bottom    //
      TextContentItem item  = Js.uncheckedCast(pageContent.items.getAt(i));
      double          itemY = item.transform[5];

      if (itemY <= destY)
      {
         height = item.height;
         width  = item.width;
         text   = item.str;
         break;
      }
   }
   if (text == null)
   {
      throw new IllegalStateException("Bookmark text not found");
   }
   else
   {
      id = Integer.toString(text.hashCode());
      bookmarkById.put(id, this);

      if (height > maxHeight)
      {
         maxHeight = height;
      }
   }
};
/*------------------------------------------------------------------------------

@name       toString - standard toString() method
                                                                              */
                                                                             /**
            Standard toString() method.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String toString()
{
   Point destOffset = dest.getOffset();

   String sValue =
      "pageIndex=" + pageIndex
    + ", dst.x="   + destOffset.x + ", dst.y=" + destOffset.y
    + ", text="    + text;

   return(sValue);
}
/*------------------------------------------------------------------------------

@name       toURL - to bookmark url
                                                                              */
                                                                             /**
            To bookmark url, for example, "bookmark:{157,0,'XYZ',72,720,0}".

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String toURL()
{
   return("bookmark:" + Destination.getDestRefString(dest));
}
/*------------------------------------------------------------------------------

@name       toSafeURL - to bookmark safe url
                                                                              */
                                                                             /**
            URL encoded version of toURL().

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String toSafeURL()
{
   return(Global.encodeURI(toURL()));
}
}//====================================// end Bookmark -----------------------//
