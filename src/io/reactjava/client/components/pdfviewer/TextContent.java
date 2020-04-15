/*==============================================================================

name:       TextContent.java

purpose:    pdf page text content

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
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
                                       // TextContent ========================//
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
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
public @JsProperty JsArray<TextContentItem>
                                items;
                                       // styles                              //
public @JsProperty NativeObject styles;
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
@JsOverlay
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
