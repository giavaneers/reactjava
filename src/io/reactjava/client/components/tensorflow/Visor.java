/*==============================================================================

name:       Visor.java

purpose:    Tensorflow Visor Component.

history:    Fri Aug 14, 2020 10:30:00 (Giavaneers - LBM) created

notes:

                  This program was created by Giavaneers
        and is the confidential and proprietary product of Giavaneers Inc.
      Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2020 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.


==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.tensorflow;

                                       // imports --------------------------- //
import elemental2.dom.CSSProperties.WidthUnionType;
import elemental2.dom.CSSProperties.ZIndexUnionType;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.NativeObject;

                                       // Visor ==============================//
public class Visor extends Component
{
                                       // class constants ------------------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       bindKeys - bind the tilde key to toggle the visor.
                                                                              */
                                                                             /**
            Bind the tilde key to toggle the visor. Called by default when the
            visor is initially created.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void bindKeys()
{
   visor().bindKeys();
}
/*------------------------------------------------------------------------------

@name       close - close the visor
                                                                              */
                                                                             /**
            Close the visor.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void close()
{
   visor().close();
}
/*------------------------------------------------------------------------------

@name       isFullScreen - test whether visor is open
                                                                              */
                                                                             /**
            Test whether visor is open.

@return     true iff the visor is open

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static boolean isFullScreen()
{
   return(visor().isFullScreen());
}
/*------------------------------------------------------------------------------

@name       isOpen - test whether visor is in 'fullscreen' mode
                                                                              */
                                                                             /**
            Test whether visor is in 'fullscreen' mode.

@return     true iff the visor is in 'fullscreen' mode

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static boolean isOpen()
{
   return(visor().isOpen());
}
/*------------------------------------------------------------------------------

@name       open - open the visor
                                                                              */
                                                                             /**
            Open the visor.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void open()
{
   visor().open();
}
/*------------------------------------------------------------------------------

@name       render - render component
                                                                              */
                                                                             /**
            Render component.

@history    Fri Aug 14, 2020 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public final void render()
{
/*--
   <div class='visor' id='tfjs-visor-container' />
--*/
}
/*------------------------------------------------------------------------------

@name       setActiveTab - set the active tab for the visor
                                                                              */
                                                                             /**
            Set the active tab for the visor.

@param      tabName     tab name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void setActiveTab(
   String tabName)
{
   visor().setActiveTab(tabName);
}
/*------------------------------------------------------------------------------

@name       surface - create a surface on the visor
                                                                              */
                                                                             /**
            Most methods in tfjs-vis that take a surface also take a SurfaceInfo
            so you rarely need to call this method unless you want to make a
            custom plot.

@return     New surface information: container, label and drawArea.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static SurfaceElements surface(
   String name,
   String tab)
{
   return(visor().surface(NativeObject.with("name", name, "tab", tab)));
}
/*------------------------------------------------------------------------------

@name       toggle - toggle the visor between open and close
                                                                              */
                                                                             /**
            Toggle the visor between open and close.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void toggle()
{
   visor().toggle();
}
/*------------------------------------------------------------------------------

@name       toggleFullScreen - toggle the visor between full screen and not
                                                                              */
                                                                             /**
            Toggle the visor between full screen and not.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void toggleFullScreen()
{
   visor().toggleFullScreen();
}
/*------------------------------------------------------------------------------

@name       unbindKeys - unbind the tilde key to toggle the visor.
                                                                              */
                                                                             /**
            Unbind the tilde key to toggle the visor.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void unbindKeys()
{
   visor().unbindKeys();
}
/*------------------------------------------------------------------------------

@name       visor - get visor instance
                                                                              */
                                                                             /**
            Get visor instance

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected static VisorNative visor()
{
   Element visorContainer =
      DomGlobal.document.getElementById("tfjs-visor-container");

   if (visorContainer == null)
   {
      throw new IllegalStateException("'tfjs-visor-container' not found in DOM");
   }

   VisorNative instance = VisorNative.visor();
                                       // change the visor component styling  //
   HTMLElement visorElement = (HTMLElement)visorContainer.firstElementChild;
   visorElement.style.position        = "static";
   visorElement.style.width           = WidthUnionType.of("100%");
   visorElement.style.zIndex          = ZIndexUnionType.of("0");
   visorElement.style.boxShadow       = "none";
   visorElement.style.backgroundColor = "inherit";

   return(instance);
}
}//====================================// end Visor ==========================//
