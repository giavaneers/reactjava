/*==============================================================================

name:       Destination.java

purpose:    Navigable destination

history:    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.pdfviewer;
                                       // imports --------------------------- //
import elemental2.core.JsArray;
import io.reactjava.client.core.react.NativeObject;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
                                       // Destination ========================//
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Array")
public class Destination extends JsArray
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

@name       Destination - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Destination()
{
}
/*------------------------------------------------------------------------------

@name       equals - standard equals method
                                                                              */
                                                                             /**
            Standard equals method. This implementation compares the destRef.

@param      other    target of comparison

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final boolean equals(
   Destination other)
{
   NativeObject refDestRef = getDestRef();
   NativeObject tstDestRef = other.getDestRef();

   boolean bEquals    =
       refDestRef.getInt("num") == tstDestRef.getInt("num")
       && refDestRef.getInt("gen") == tstDestRef.getInt("gen");

   return(bEquals);
}
/*------------------------------------------------------------------------------

@name       getDestRef - get destination reference
                                                                              */
                                                                             /**
            Get destination reference.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final NativeObject getDestRef()
{
   NativeObject destRef = (NativeObject)getAt(0);
   return(destRef);
};
/*------------------------------------------------------------------------------

@name       getDestRefString - get destRef string
                                                                              */
                                                                             /**
            Get destRef string, for example, "{157,0,'XYZ',72,720,0}".

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public static String getDestRefString(
   Destination dest)
{
   String sValue = "";
   if (dest == null)
   {
      sValue += "undefined";
   }
   else
   {
      NativeObject destRef = dest.getDestRef();
      if (destRef == null)
      {
         sValue += "undefined";
      }
      else
      {
         sValue  +=
            "{"  + destRef.getInt("num")
          + ", " + destRef.getInt("gen")
          + ", " + ((NativeObject)dest.getAt(1)).getString("name")
          + ", " + dest.getAt(2)
          + ", " + dest.getAt(3)
          + ", " + dest.getAt(4)
          + "}";
      }
   }

   return(sValue);
}
/*------------------------------------------------------------------------------

@name       getName - get destination name
                                                                              */
                                                                             /**
            Get destination name.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final String getName()
{
   String name = ((NativeObject)getAt(1)).getString("name");
   return(name);
};
/*------------------------------------------------------------------------------

@name       getOffset - get destination offset
                                                                              */
                                                                             /**
            Get destination offset.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Point getOffset()
{
   double x = 0;
   double y = 0;

   switch(getName())
   {
      case "XYZ":
      {
         x = ((Number)getAt(2)).doubleValue();
         y = ((Number)getAt(3)).doubleValue();
         break;
      }
      default:
      {
         throw new UnsupportedOperationException("Only 'XYZ' supported for now");
      }
   }

   return(new Point(x, y));
}
/*------------------------------------------------------------------------------

@name       newInstance - factory method
                                                                              */
                                                                             /**
            Constructor for destination such as
            [{"num":157,"gen":0},{"name":"XYZ"},72,720,0]

@return     new Destination instance

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public static Destination newInstance(
   int    refNum,
   int    generation,
   String name,
   int    num1,
   int    num2,
   int    num3)
{
   return(newInstance(refNum, generation, name, num1, num2, num3, -1));
}
/*------------------------------------------------------------------------------

@name       newInstance - factory method
                                                                              */
                                                                             /**
            Constructor for destination such as
            [{"num":157,"gen":0},{"name":"XYZ"},72,720,0]

@return     new Destination instance

@param      refNum         object reference
@param      generation     generation, 0 being the original
@param      name           name: {'XYZ', 'Fit','FitB', 'FitH', 'FitBH', 'FitV',
                                  'FitBV', 'FitR'}

@param      num1           name == 'XYZ' || 'FitV' || 'FitBV' || 'FitR'
                                                     -> page x coordinate,
                           name == 'FitH' || 'FitBH' -> page y coordinate

@param      num2           name == 'XYZ'             -> page y coordinate

@param      num3           name == 'XYZ'             -> page scale,
                           name == ''FitR'           -> width

@param      num4           name == 'FitR'            -> height

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public static Destination newInstance(
   int    refNum,
   int    generation,
   String name,
   int    num1,
   int    num2,
   int    num3,
   int    num4)
{
   Destination destination = new Destination();
   destination.setAt(0, NativeObject.with("num", refNum, "gen", generation));
   destination.setAt(1, NativeObject.with("name", name));
   destination.setAt(2, num1);
   destination.setAt(3, num2);
   destination.setAt(4, num3);
   return(destination);
}
/*------------------------------------------------------------------------------

@name       newInstance - factory method for specified bookmark string
                                                                              */
                                                                             /**
            Constructor for specified bookmark string such as

               "bookmark:{157,0,'XYZ',72,720,0}"

               or

               "{157,0,'XYZ',72,720,0}"

               or

               "157,0,'XYZ',72,720,0"



@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public static Destination newInstance(
   String bookmark)
{
   Destination destination = null;

   bookmark = bookmark.trim();

   if (bookmark.startsWith("bookmark"))
   {
      bookmark = bookmark.substring("bookmark".length()).trim();
   }
   if (bookmark.startsWith(":"))
   {
      bookmark = bookmark.substring(1).trim();
   }
   if (bookmark.startsWith("{"))
   {
      bookmark = bookmark.substring(1).trim();
   }
   if (bookmark.endsWith("}"))
   {
      bookmark = bookmark.substring(0, bookmark.length() - 1).trim();
   }
   String[] splits = bookmark.split(",");
   if (splits.length == 6)
   {
      destination =
         Destination.newInstance(
            Integer.parseInt(splits[0].trim()),
            Integer.parseInt(splits[1].trim()),
            splits[2].trim().replace("'", ""),
            Integer.parseInt(splits[3].trim()),
            Integer.parseInt(splits[4].trim()),
            Integer.parseInt(splits[5].trim()));
   }

   return(destination);
}
/*------------------------------------------------------------------------------

@name       toStringOverride - standard toString() override method
                                                                              */
                                                                             /**
            Standard toString() method already implemented in JsArrayLike,
            so provide tis version instead.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final String toStringOverride()
{
   NativeObject ref = (NativeObject)getAt(0);

   String sValue =
      "ref -> "
    + (ref == null
          ? "undefined"
          : "(num=" + ref.getInt("num") +", gen=" + ref.getInt("gen") + ")");

   NativeObject name = (NativeObject)getAt(1);
   sValue +=", name="+ (name == null ? "undefined" : name.getString("name"));

   sValue +=", num1="+ getAt(2);
   sValue +=", num2="+ getAt(3);
   sValue +=", num3="+ getAt(4);
   sValue +=", num4="+ getAt(5);

   return(sValue);
}
}//====================================// end Destination --------------------//
