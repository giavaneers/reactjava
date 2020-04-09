/*==============================================================================

name:       Prism.java

purpose:    Code highliting component.

history:    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.generalpage;
                                       // imports --------------------------- //
import elemental2.dom.Element;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.INativeEffectHandler;
import io.reactjava.client.core.react.NativeObject;
import java.util.function.Function;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
                                       // Prism ==============================//
public class Prism extends Component
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       handleEffect - effect handler
                                                                              */
                                                                             /**
            effect handler.

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public INativeEffectHandler handleEffect = () ->
{
   PrismNative.highlightAllUnder(getDOMElement());
                                       // no cleanup function                 //
   return(INativeEffectHandler.kNO_CLEANUP_FCN);
};
/*------------------------------------------------------------------------------

@name       render - render markup
                                                                              */
                                                                             /**
            Render markup.

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final void render()
{
   useEffect(handleEffect);
                                       // must use style overrides rather than//
                                       // css since prismjs css is injected   //
                                       // later than component css            //
   NativeObject style = NativeObject.with("borderRadius", "0.5em");

   Object background = props().get("background");
   if (background != null)
   {
      style.set("background", background);
   }
/*--
   <pre style={style}>
      <code className="language-java">
      {getChildren()}
      </code>
   </pre>
--*/
}
/*==============================================================================

name:       PrismNative - native prism api

purpose:    Native prism api

history:    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Prism")
public static class PrismNative
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

@name       PrismNative - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
private PrismNative()
{
}
/*------------------------------------------------------------------------------

@name       highlightAll - highlight all code elements
                                                                              */
                                                                             /**
            Highlight all code elements.

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public static void highlightAll()
{
   highlightAll(false, null);
}
/*------------------------------------------------------------------------------

@name       highlightAll - highlight all code elements
                                                                              */
                                                                             /**
            Highlight all code elements.

@param      bAsync      iff true, asynchronous
@param      callback    any callback

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void highlightAll(
   boolean  bAsync,
   Function callback);

/*------------------------------------------------------------------------------

@name       highlightAllUnder - highlight all code elements of container
                                                                              */
                                                                             /**
            Highlight all code elements of container.

@param      container      target container

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public static void highlightAllUnder(
   Element container)
{
   if (container != null)
   {
      highlightAllUnder(container, false, null);
   }
}
/*------------------------------------------------------------------------------

@name       highlightAllUnder - highlight all code elements of container
                                                                              */
                                                                             /**
            Highlight all code elements of container.

@param      container      target container
@param      bAsync         iff true, asynchronous
@param      callback       any callback

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void highlightAllUnder(
   Element  container,
   boolean  bAsync,
   Function callback);

/*------------------------------------------------------------------------------

@name       highlightElement - highlight specified element
                                                                              */
                                                                             /**
            Highlight specified element.

@param      element        target element

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public static void highlightElement(
   Element element)
{
   highlightElement(element, false, null);
}
/*------------------------------------------------------------------------------

@name       highlightElement - highlight specified element
                                                                              */
                                                                             /**
            Highlight specified element.

@param      element     target element
@param      bAsync      target element
@param      callback    target element

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void highlightElement(
   Element element,
   boolean  bAsync,
   Function callback);

}//====================================// end PrismNative --------------------//
}//====================================// end Prism ==========================//
