package io.reactjava.client.components.pdfviewer;

import elemental2.core.JsArray;
import io.reactjava.client.core.react.NativeObject;
import jsinterop.annotations.JsProperty;
import jsinterop.base.Js;

/*==============================================================================

name:       TextContent - pdf page text content

purpose:    pdf page text content

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public class TextContent extends NativeObject
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // array of text items                 //
public @JsProperty
                                       JsArray<TextContentItem> items;
                                       // styles                              //
public @JsProperty
                                       NativeObject      styles;
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       TextContent - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private TextContent()
{
}
/*------------------------------------------------------------------------------

@name       toString - toString() method
                                                                              */
                                                                             /**
            Standard toString() method.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String toString(
   TextContent textContent)
{
   String s = "";
   for (int i = 0; i < textContent.items.length; i++)
   {
      TextContentItem item = Js.uncheckedCast(textContent.items.getAt(i));
      s += TextContentItem.toString(item) + "\n";
   }

   return(s);
}
}//====================================// end TextContent --------------------//
