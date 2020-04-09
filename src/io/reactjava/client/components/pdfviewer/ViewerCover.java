/*==============================================================================

name:       ViewerCover.java

purpose:    ViewerCover

history:    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.pdfviewer;
                                       // imports --------------------------- //
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.INativeEffectHandler;
import io.reactjava.client.core.react.NativeObject;
import io.reactjava.client.core.react.Properties;
import io.reactjava.client.core.react.Utilities;

                                       // ViewerCover ========================//
public class ViewerCover<P extends Properties> extends Component
{
                                       // class constants ------------------- //
public static final String  kCOMPONENT_ID_VIEWER_COVER = "ReactJavaViewerCover";
public static final String  kSTATE_SHOW                = "show";

                                       // property keys                       //
public static final String  kPROPERTY_COVER      = "cover";
public static final String  kPROPERTY_COVERSTYLE = "coverstyle";

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       defaultElementId - provide a default elementId
                                                                              */
                                                                             /**
            Provide a default elementId.

@returns    a default elementId, or null if none.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected String defaultElementId()
{
   return(kCOMPONENT_ID_VIEWER_COVER);
}
/*------------------------------------------------------------------------------

@name       getCover - get any cover
                                                                              */
                                                                             /**
            Get any cover, either a color or an image url

@return     any cover, or null if none

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getCover()
{
   return(props().getString(kPROPERTY_COVER));
}
/*------------------------------------------------------------------------------

@name       getCoverColor - get any cover color
                                                                              */
                                                                             /**
            Get any cover color or transparent if none

@return     any cover, or transparent if none

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected String getCoverColor()
{
   String cover = getCover();
   if (cover != null)
   {
      String coverLC = cover.toLowerCase().trim();
      if (!coverLC.startsWith("#")
            && !coverLC.startsWith("rgb")
            && !coverLC.startsWith("hsl")
            && !Utilities.kCSS_COLORS.contains(cover))
      {
         cover = "transparent";
      }
   }
   return(cover);
}
/*------------------------------------------------------------------------------

@name       getCoverElement - get any cover element
                                                                              */
                                                                             /**
            Get any cover element

@return     any cover element, or null if none

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Element getCoverElement()
{
   Element element = DomGlobal.document.querySelector(".animation");
   return(element);
}
/*------------------------------------------------------------------------------

@name       getCoverImage - get any cover image url
                                                                              */
                                                                             /**
            Get any cover image url, or null if none

@return     any cover image url, or null if none

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected String getCoverImage()
{
   String cover = getCover();
   if (cover != null)
   {
      String coverLC = cover.toLowerCase().trim();
      if (!coverLC.endsWith("gif")
            && !coverLC.endsWith("jpg")
            && !coverLC.endsWith("jpeg")
            && !coverLC.endsWith("png"))
      {
         cover = null;
      }
   }
   return(cover);
}
/*------------------------------------------------------------------------------

@name       getCoverStyle - get any cover style
                                                                              */
                                                                             /**
            Get any cover style

@return     any cover style or an empty NativeObject if null.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public NativeObject getCoverStyle()
{
   NativeObject coverStyle = (NativeObject)props().get(kPROPERTY_COVERSTYLE);
   if (coverStyle == null)
   {
      coverStyle = new NativeObject();
   }
   return(coverStyle);
}
/*------------------------------------------------------------------------------

@name       resolveCoverStyle - resolve cover style
                                                                              */
                                                                             /**
            Resolve cover stylem adding some attributes

@return     resolved cover style

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected NativeObject resolveCoverStyle()
{
   NativeObject resolved =
      NativeObject.with(
         getCoverStyle(),
         "backgroundColor", getCoverColor(),
         "zIndex",          10);

   return(resolved);
}
/*------------------------------------------------------------------------------

@name       getShow - get show state value
                                                                              */
                                                                             /**
            Get show state value

@return     show state value

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected boolean getShow()
{
   return(getStateBoolean(kSTATE_SHOW));
}
/*------------------------------------------------------------------------------

@name       handleEffect - handleEffect handler
                                                                              */
                                                                             /**
            handleEffect handler as a public instance variable.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEffectHandler handleEffect = () ->
{
   if (!getShow())
   {
      Element coverElement = getCoverElement();

      coverElement.addEventListener(
         "animationend",
         (Event) ->
         {
            coverElement.remove();
         });
                                       // start the animation                 //
      coverElement.classList.toggle("active");
   }
                                       // no cleanup function                 //
   return(INativeEffectHandler.kNO_CLEANUP_FCN);
};
/*------------------------------------------------------------------------------

@name       render - render component
                                                                              */
                                                                             /**
            Render component.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final void render()
{
   useState(kSTATE_SHOW, true);
   useEffect(handleEffect);
/*--
   <div class="animation" style={resolveCoverStyle()}>
--*/
   if (getShow())
   {
/*--
      <@material-ui.core.LinearProgress />
--*/
   }
/*--
   </div>
--*/
}
/*------------------------------------------------------------------------------

@name       renderCSS - get component css
                                                                              */
                                                                             /**
            Get component css.

@history    Thu Dec 12, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
/*--
.animation.active
{
   animation-name:     coverAnimation;
   animation-duration: 1s;
}
@keyframes coverAnimation
{
  from {background-color: {getCoverColor()};}
  to   {background-color: transparent;}
}
--*/
}
}//====================================// end ViewerCover ====================//
