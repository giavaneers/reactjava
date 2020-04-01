package io.reactjava.client.components.pdfviewer;

import elemental2.dom.Element;
import elemental2.dom.HTMLDivElement;
import io.reactjava.client.core.react.NativeObject;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/*==============================================================================

name:       ViewerOptions - native pdfjs viewer options api

purpose:    Native pdfjs viewer options api

history:    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class ViewerOptions extends NativeObject
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
@JsProperty                            // container for the viewer element    //
public HTMLDivElement container;
@JsProperty                            // application event bus               //
public EventBus       eventBus;
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       ViewerOptions - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private ViewerOptions()
{
}
/*------------------------------------------------------------------------------

@name       ViewerOptions - constructor
                                                                              */
                                                                             /**
            Constructor

@param      props    props

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private ViewerOptions(
   NativeObject args)
{
}
/*------------------------------------------------------------------------------

@name       newInstance - new instance for specified link service and event bus
                                                                              */
                                                                             /**
            New instance for specified link service and event bus.

@params     scrollEvtSrcContainer      ancestor sourcing any scroll events
@params     viewer                     pdf viewer
@params     pdfLinkService             link service
@params     pdfFindController          search service

@history    Thu Feb 27, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public static ViewerOptions newInstance(
   Element           scrollEvtSrcContainer,
   Element           viewer,
   PDFLinkService    pdfLinkService,
   PDFFindController pdfFindController)
{
   ViewerOptions options =
      new ViewerOptions(NativeObject.with(
         "container",      scrollEvtSrcContainer,
         "viewer",         viewer,
         "linkService",    pdfLinkService,
         "findController", pdfFindController));

   return(options);
}
}//====================================// end ViewerOptions ------------------//
