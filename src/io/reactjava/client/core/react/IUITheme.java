/*==============================================================================

name:       IUITheme.java

purpose:    ReactJava UI Theme interface.

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import elemental2.core.JsRegExp;
import elemental2.dom.CSSStyleDeclaration;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.ViewCSS;
import io.reactjava.client.core.react.IUITheme.Mixins.Toolbar;
import io.reactjava.client.core.react.IUITheme.Palette.Action;
import io.reactjava.client.core.react.IUITheme.Palette.Background;
import io.reactjava.client.core.react.IUITheme.Palette.Colors;
import io.reactjava.client.core.react.IUITheme.Palette.Common;
import io.reactjava.client.core.react.IUITheme.Palette.Error;
import io.reactjava.client.core.react.IUITheme.Palette.Greys;
import io.reactjava.client.core.react.IUITheme.Palette.Primary;
import io.reactjava.client.core.react.IUITheme.Palette.Secondary;
import io.reactjava.client.core.react.IUITheme.Palette.Text;
import io.reactjava.client.core.react.IUITheme.Transitions.Duration;
import io.reactjava.client.core.react.IUITheme.Transitions.Easing;
import io.reactjava.client.core.react.IUITheme.Typography.Body1;
import io.reactjava.client.core.react.IUITheme.Typography.Body1Next;
import io.reactjava.client.core.react.IUITheme.Typography.Body2;
import io.reactjava.client.core.react.IUITheme.Typography.Body2Next;
import io.reactjava.client.core.react.IUITheme.Typography.Button;
import io.reactjava.client.core.react.IUITheme.Typography.ButtonNext;
import io.reactjava.client.core.react.IUITheme.Typography.Caption;
import io.reactjava.client.core.react.IUITheme.Typography.CaptionNext;
import io.reactjava.client.core.react.IUITheme.Typography.Display1;
import io.reactjava.client.core.react.IUITheme.Typography.Display2;
import io.reactjava.client.core.react.IUITheme.Typography.Display3;
import io.reactjava.client.core.react.IUITheme.Typography.Display4;
import io.reactjava.client.core.react.IUITheme.Typography.H1;
import io.reactjava.client.core.react.IUITheme.Typography.H2;
import io.reactjava.client.core.react.IUITheme.Typography.H3;
import io.reactjava.client.core.react.IUITheme.Typography.H4;
import io.reactjava.client.core.react.IUITheme.Typography.H5;
import io.reactjava.client.core.react.IUITheme.Typography.H6;
import io.reactjava.client.core.react.IUITheme.Typography.Headline;
import io.reactjava.client.core.react.IUITheme.Typography.Overline;
import io.reactjava.client.core.react.IUITheme.Typography.Subheading;
import io.reactjava.client.core.react.IUITheme.Typography.Subtitle1;
import io.reactjava.client.core.react.IUITheme.Typography.Subtitle2;
import io.reactjava.client.core.react.IUITheme.Typography.Title;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
                                       // IUITheme ===========================//
public interface IUITheme
{
                                       // class constants --------------------//
                                       // standard keys                       //
String kKEY_BREAKPOINTS = "breakpoints";
String kKEY_DIRECTION   = "direction";
String kKEY_MIXINS      = "mixins";
String kKEY_OVERRIDES   = "overrides";
String kKEY_PALETTE     = "palette";
String kKEY_SHADOWS     = "shadows";
String kKEY_SHAPE       = "shape";
String kKEY_SPACING     = "spacing";
String kKEY_TRANSITIONS = "transitions";
String kKEY_TYPOGRAPHY  = "typography";
String kKEY_Z_INDEX     = "zIndex";

String[] kKEYS =
{
   kKEY_BREAKPOINTS,
   //kKEY_DIRECTION,
   //kKEY_MIXINS,
   //kKEY_OVERRIDES,
   //kKEY_PALETTE,
   //kKEY_SHADOWS,
   //kKEY_SHAPE
   //kKEY_SPACING,
   kKEY_TRANSITIONS,
   //kKEY_TYPOGRAPHY,
   //kKEY_Z_INDEX
};
                                       // default values                     //
String kVAL_DIRECTION   = "ltr";
Object kVAL_MIXINS      = null;
Object kVAL_OVERRIDES   = null;
                                       // class variables ------------------- //
/*------------------------------------------------------------------------------

@name       cssLengthAdd - add specified addend to specified css length value
                                                                              */
                                                                             /**
            Add specified addend to specified css length value.

@return     Result css length value with trailing units specifier; eg, '1.2px'.

@param      cssLength      original css length value with trailing units
                           specifier; eg, '1.2px'.

@param      addend         addend.

@history    Mon Feb 29, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
static String cssLengthAdd(
   String cssLength,
   double addend)
{
   String   resultCSSLength = null;
   String   kREGEX_NUMBER   = "[0-9]*\\.?[0-9]*";
   String[] group           = new JsRegExp(kREGEX_NUMBER).exec(cssLength);
   if (group.length == 1)
   {
      String original = group[0];
      String result   = Double.toString(Double.parseDouble(original) + addend);

      resultCSSLength = cssLength.replace(original, result);
   }
   return(resultCSSLength);
}
/*------------------------------------------------------------------------------

@name       cssLengthScale - scale specified css length value
                                                                              */
                                                                             /**
            Scale specified css length value by specified factor.

@return     scaled css length value with trailing units specifier; eg, '1.2px'.

@param      cssLength      original css length value with trailing units
                           specifier; eg, '1.2px'.

@param      scale          scaling factor.

@history    Mon Feb 29, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
static String cssLengthScale(
   String cssLength,
   double scale)
{
   String   scaledCSSLength = null;
   String   kREGEX_NUMBER   = "[0-9]*\\.?[0-9]*";
   String[] group           = new JsRegExp(kREGEX_NUMBER).exec(cssLength);
   if (group.length == 1)
   {
      String original = group[0];
      String scaled   = Double.toString(Double.parseDouble(original) * scale);

      scaledCSSLength = cssLength.replace(original, scaled);
   }
   return(scaledCSSLength);
}
/*------------------------------------------------------------------------------

@name       defaultInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IUITheme defaultInstance()
{
   return(new DefaultInstance());
}
/*------------------------------------------------------------------------------

@name       getBreakpoints - get breakpoints
                                                                              */
                                                                             /**
            Get breakpoints.

@return     breakpoints

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Breakpoints getBreakpoints();

/*------------------------------------------------------------------------------

@name       getDirection - get direction
                                                                              */
                                                                             /**
            Get direction.

@return     direction

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getDirection();

/*------------------------------------------------------------------------------

@name       getMixins - get mixins
                                                                              */
                                                                             /**
            Get mixins.

@return     mixins

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IMixins getMixins();

/*------------------------------------------------------------------------------

@name       getOverrides - get overrides
                                                                              */
                                                                             /**
            Get overrides.

@return     overrides

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Object getOverrides();

/*------------------------------------------------------------------------------

@name       getPalette - get palette
                                                                              */
                                                                             /**
            Get palette.

@return     palette

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IPalette getPalette();

/*------------------------------------------------------------------------------

@name       getShape - get shape
                                                                              */
                                                                             /**
            Get shape.

@return     shape

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IShape getShape();

/*------------------------------------------------------------------------------

@name       getSpacing - get spacing
                                                                              */
                                                                             /**
            Get spacing.

@return     spacing

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
ISpacing getSpacing();

/*------------------------------------------------------------------------------

@name       getTransitions - get transitions
                                                                              */
                                                                             /**
            Get transitions.

@return     transitions

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
ITransitions getTransitions();

/*------------------------------------------------------------------------------

@name       getTypography - get typography
                                                                              */
                                                                             /**
            Get typography.

@return     typography

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
ITypography getTypography();

/*------------------------------------------------------------------------------

@name       getZIndex - get z index
                                                                              */
                                                                             /**
            Get z index.

@return     z index

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IZIndex getZIndex();

/*------------------------------------------------------------------------------

@name       remToPx - convert specified rem value to equivalent pixels
                                                                              */
                                                                             /**
            Convert specified rem value to equivalent pixels by reference
            to the root default font size.

@return     equivalent px value with trailing units, 'px'; eg, '1.2px'.

@param      rem      rem value with trailing units, 'rem'; eg, '2.3rem'.

@history    Mon Feb 29, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
static String remToPx(
   String rem)
{
   double remVal = Double.parseDouble(rem.replace("rem","").trim());

   Element docElem  = DomGlobal.document.documentElement;
   ViewCSS viewCSS  = Js.<ViewCSS>uncheckedCast(DomGlobal.window);
   CSSStyleDeclaration style    = viewCSS.getComputedStyle(docElem);
   String                 fontSize = style.getPropertyValue("font-size").trim();
   if (!fontSize.endsWith("px"))
   {
      throw new UnsupportedOperationException("Many are called...");
   }

   double pxVal = Double.parseDouble(fontSize.replace("px", "")) * remVal;

   return(Double.toString(pxVal) + "px");
}
/*------------------------------------------------------------------------------

@name       toPx - convert specified numeric value to equivalent pixel string
                                                                              */
                                                                             /**
            Convert specified numeric value to equivalent pixel string.

@return     equivalent px value with trailing units, 'px'.

@param      num      numeric value.

@history    Mon Feb 29, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
static String toPx(
   Number num)
{
   return(Double.toString(num.doubleValue()) + "px");
}
/*==============================================================================

name:       Breakpoints - breakpoints

purpose:    Breakpoints default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public class Breakpoints extends NativeObject implements IBreakpoints
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Breakpoints - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Breakpoints()
{
   set(kKEY_BREAKPOINTS_FCN_BETWEEN, kVAL_BREAKPOINTS_FCN_BETWEEN);
   set(kKEY_BREAKPOINTS_FCN_DOWN,    kVAL_BREAKPOINTS_FCN_DOWN);
   set(kKEY_BREAKPOINTS_FCN_ONLY,    kVAL_BREAKPOINTS_FCN_ONLY);
   set(kKEY_BREAKPOINTS_FCN_UP,      kVAL_BREAKPOINTS_FCN_UP);
   set(kKEY_BREAKPOINTS_FCN_WIDTH,   kVAL_BREAKPOINTS_FCN_WIDTH);
   set(kKEY_BREAKPOINTS_SIZE_LG,     kVAL_BREAKPOINTS_SIZE_LG);
   set(kKEY_BREAKPOINTS_SIZE_MD,     kVAL_BREAKPOINTS_SIZE_MD);
   set(kKEY_BREAKPOINTS_SIZE_SM,     kVAL_BREAKPOINTS_SIZE_SM);
   set(kKEY_BREAKPOINTS_SIZE_XL,     kVAL_BREAKPOINTS_SIZE_XL);
   set(kKEY_BREAKPOINTS_SIZE_XS,     kVAL_BREAKPOINTS_SIZE_XS);
}
/*------------------------------------------------------------------------------

@name       getFcnBetween - get between function
                                                                              */
                                                                             /**
            Get between function.

@return     between function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Function getFcnBetween()
{
   return((Function)get(kKEY_BREAKPOINTS_FCN_BETWEEN));
}
/*------------------------------------------------------------------------------

@name       getFcnDown - get down function
                                                                              */
                                                                             /**
            Get down function.

@return     down function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Function getFcnDown()
{
   return((Function)get(kKEY_BREAKPOINTS_FCN_DOWN));
}
/*------------------------------------------------------------------------------

@name       getFcnOnly - get only function
                                                                              */
                                                                             /**
            Get only function.

@return     only function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Function getFcnOnly()
{
   return((Function)get(kKEY_BREAKPOINTS_FCN_ONLY));
}
/*------------------------------------------------------------------------------

@name       getFcnUp - get up function
                                                                              */
                                                                             /**
            Get up function.

@return     up function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Function getFcnUp()
{
   return((Function)get(kKEY_BREAKPOINTS_FCN_UP));
}
/*------------------------------------------------------------------------------

@name       getFcnWidth - get width function
                                                                              */
                                                                             /**
            Get width function.

@return     width function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Function getFcnWidth()
{
   return((Function)get(kKEY_BREAKPOINTS_FCN_WIDTH));
}
/*------------------------------------------------------------------------------

@name       getSizeLarge - get large size
                                                                              */
                                                                             /**
            Get large size.

@return     large size

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getSizeLarge()
{
   return(getInt(kKEY_BREAKPOINTS_SIZE_LG));
}
/*------------------------------------------------------------------------------

@name       getSizeMedium - get medium size
                                                                              */
                                                                             /**
            Get medium size.

@return     medium size

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getSizeMedium()
{
   return(getInt(kKEY_BREAKPOINTS_SIZE_MD));
}
/*------------------------------------------------------------------------------

@name       getSizeSmall - get small size
                                                                              */
                                                                             /**
            Get small size.

@return     small size

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getSizeSmall()
{
   return(getInt(kKEY_BREAKPOINTS_SIZE_SM));
}
/*------------------------------------------------------------------------------

@name       getSizeExtraLarge - get extra large size
                                                                              */
                                                                             /**
            Get extra large size.

@return     extra large size

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getSizeExtraLarge()
{
   return(getInt(kKEY_BREAKPOINTS_SIZE_XL));
}
/*------------------------------------------------------------------------------

@name       getSizeExtraSmall - get extra small size
                                                                              */
                                                                             /**
            Get extra small size.

@return     extra small size

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getSizeExtraSmall()
{
   return(getInt(kKEY_BREAKPOINTS_SIZE_XS));
}
}//====================================// end Breakpoints ====================//
/*==============================================================================

name:       DefaultInstance - theme default instance

purpose:    Theme default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
class DefaultInstance extends NativeObject implements IUITheme
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       DefaultInstance - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public DefaultInstance()
{
   set(kKEY_BREAKPOINTS, IBreakpoints.defaultInstance());
   set(kKEY_DIRECTION,   kVAL_DIRECTION);
   set(kKEY_MIXINS,      kVAL_MIXINS);
   set(kKEY_OVERRIDES,   kVAL_OVERRIDES);
   set(kKEY_PALETTE,     IPalette.defaultInstance());
   set(kKEY_SHAPE,       IShape.defaultInstance());
   set(kKEY_SPACING,     ISpacing.defaultInstance());
   set(kKEY_TRANSITIONS, ITransitions.defaultInstance());
   set(kKEY_TYPOGRAPHY,  ITypography.defaultInstance());
   set(kKEY_Z_INDEX,     IZIndex.defaultInstance());
}
/*------------------------------------------------------------------------------

@name       getBreakpoints - get breakpoints
                                                                              */
                                                                             /**
            Get breakpoints.

@return     breakpoints

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Breakpoints getBreakpoints()
{
   return((Breakpoints)get(kKEY_BREAKPOINTS));
}
/*------------------------------------------------------------------------------

@name       getDirection - get direction
                                                                              */
                                                                             /**
            Get direction.

@return     direction

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getDirection()
{
   return((String)get(kKEY_DIRECTION));
}
/*------------------------------------------------------------------------------

@name       getMixins - get mixins
                                                                              */
                                                                             /**
            Get mixins.

@return     mixins

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IMixins getMixins()
{
   return((IMixins)get(kKEY_MIXINS));
}
/*------------------------------------------------------------------------------

@name       getOverrides - get overrides
                                                                              */
                                                                             /**
            Get overrides.

@return     overrides

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Object getOverrides()
{
   return(get(kKEY_OVERRIDES));
}
/*------------------------------------------------------------------------------

@name       getPalette - get palette
                                                                              */
                                                                             /**
            Get palette.

@return     palette

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IPalette getPalette()
{
   return((IPalette)get(kKEY_PALETTE));
}
/*------------------------------------------------------------------------------

@name       getShape - get shape
                                                                              */
                                                                             /**
            Get shape.

@return     shape

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IShape getShape()
{
   return((IShape)get(kKEY_SHAPE));
}
/*------------------------------------------------------------------------------

@name       getSpacing - get spacing
                                                                              */
                                                                             /**
            Get spacing.

@return     spacing

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ISpacing getSpacing()
{
   return((ISpacing)get(kKEY_SPACING));
}
/*------------------------------------------------------------------------------

@name       getTransitions - get transitions
                                                                              */
                                                                             /**
            Get transitions.

@return     transitions

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ITransitions getTransitions()
{
   return((ITransitions)get(kKEY_TRANSITIONS));
}
/*------------------------------------------------------------------------------

@name       getTypography - get typography
                                                                              */
                                                                             /**
            Get typography.

@return     typography

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ITypography getTypography()
{
   return((ITypography)get(kKEY_TYPOGRAPHY));
}
/*------------------------------------------------------------------------------

@name       getZIndex - get z index
                                                                              */
                                                                             /**
            Get z index.

@return     z index

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IZIndex getZIndex()
{
   return((IZIndex)get(kKEY_Z_INDEX));
}
}//====================================// end DefaultInstance ================//
/*==============================================================================

name:       IBreakpoints - breakpoints interface

purpose:    IBreakpoints interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IBreakpoints
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_BREAKPOINTS_FCN_BETWEEN = "between";
String   kKEY_BREAKPOINTS_FCN_DOWN    = "down";
String   kKEY_BREAKPOINTS_FCN_ONLY    = "only";
String   kKEY_BREAKPOINTS_FCN_UP      = "up";
String   kKEY_BREAKPOINTS_FCN_WIDTH   = "width";
String   kKEY_BREAKPOINTS_SIZE_LG     = "lg";
String   kKEY_BREAKPOINTS_SIZE_MD     = "md";
String   kKEY_BREAKPOINTS_SIZE_SM     = "sm";
String   kKEY_BREAKPOINTS_SIZE_XL     = "xl";
String   kKEY_BREAKPOINTS_SIZE_XS     = "xs";

String[] kKEYS =
{
   kKEY_BREAKPOINTS_FCN_BETWEEN,
   kKEY_BREAKPOINTS_FCN_DOWN,
   kKEY_BREAKPOINTS_FCN_ONLY,
   kKEY_BREAKPOINTS_FCN_UP,
   kKEY_BREAKPOINTS_FCN_WIDTH,
   kKEY_BREAKPOINTS_SIZE_LG,
   kKEY_BREAKPOINTS_SIZE_MD,
   kKEY_BREAKPOINTS_SIZE_SM,
   kKEY_BREAKPOINTS_SIZE_XL,
   kKEY_BREAKPOINTS_SIZE_XS
};
                                       // default values                      //
Function kVAL_BREAKPOINTS_FCN_BETWEEN = null;
Function kVAL_BREAKPOINTS_FCN_DOWN    = null;
Function kVAL_BREAKPOINTS_FCN_ONLY    = null;
Function kVAL_BREAKPOINTS_FCN_UP      = null;
Function kVAL_BREAKPOINTS_FCN_WIDTH   = null;
int      kVAL_BREAKPOINTS_SIZE_LG     = 1280;
int      kVAL_BREAKPOINTS_SIZE_MD     = 960;
int      kVAL_BREAKPOINTS_SIZE_SM     = 600;
int      kVAL_BREAKPOINTS_SIZE_XL     = 1920;
int      kVAL_BREAKPOINTS_SIZE_XS     = 0;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IBreakpoints defaultInstance()
{
   return(new Breakpoints());
}
/*------------------------------------------------------------------------------

@name       getFcnBetween - get between function
                                                                              */
                                                                             /**
            Get between function.

@return     between function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Function getFcnBetween();

/*------------------------------------------------------------------------------

@name       getFcnDown - get down function
                                                                              */
                                                                             /**
            Get down function.

@return     down function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Function getFcnDown();

/*------------------------------------------------------------------------------

@name       getFcnOnly - get only function
                                                                              */
                                                                             /**
            Get only function.

@return     only function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Function getFcnOnly();

/*------------------------------------------------------------------------------

@name       getFcnUp - get up function
                                                                              */
                                                                             /**
            Get up function.

@return     up function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Function getFcnUp();

/*------------------------------------------------------------------------------

@name       getFcnWidth - get width function
                                                                              */
                                                                             /**
            Get width function.

@return     width function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Function getFcnWidth();

/*------------------------------------------------------------------------------

@name       getSizeLarge - get large size
                                                                              */
                                                                             /**
            Get large size.

@return     large size

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getSizeLarge();

/*------------------------------------------------------------------------------

@name       getSizeMedium - get medium size
                                                                              */
                                                                             /**
            Get medium size.

@return     medium size

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getSizeMedium();

/*------------------------------------------------------------------------------

@name       getSizeSmall - get small size
                                                                              */
                                                                             /**
            Get small size.

@return     small size

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getSizeSmall();

/*------------------------------------------------------------------------------

@name       getSizeExtraLarge - get extra large size
                                                                              */
                                                                             /**
            Get extra large size.

@return     extra large size

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getSizeExtraLarge();

/*------------------------------------------------------------------------------

@name       getSizeExtraSmall - get extra small size
                                                                              */
                                                                             /**
            Get extra small size.

@return     extra small size

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getSizeExtraSmall();

}//====================================// end IBreakpoints ===================//
/*==============================================================================

name:       IMixins - mixins interface

purpose:    Mixins interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IMixins
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_MIXINS_GUTTERS = "gutters";
String   kKEY_MIXINS_TOOLBAR = "toolbar";
                                       // default values                      //
Function kVAL_MIXINS_GUTTERS = null;
IToolbar kVAL_MIXINS_TOOLBAR = IToolbar.defaultInstance();

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IMixins defaultInstance()
{
   return(new Mixins());
}
/*------------------------------------------------------------------------------

@name       getGutters - get gutters
                                                                              */
                                                                             /**
            Get gutters.

@return     gutters function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Function getGutters();

/*------------------------------------------------------------------------------

@name       getToolbar - get toolbar
                                                                              */
                                                                             /**
            Get toolbar.

@return     toolbar

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IToolbar getToolbar();

/*==============================================================================

name:       IToolbar - mixins toolbar interface

purpose:    Mixins toolbar interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IToolbar
{
                                       // constants ------------------------- //
                                       // keys                                //
String kKEY_MIXINS_TOOLBAR_MIN_HEIGHT = "minHeight";
                                       // default values                      //
int    kVAL_MIXINS_TOOLBAR_MIN_HEIGHT = 56;

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IToolbar defaultInstance()
{
   return(new Toolbar());
}
/*------------------------------------------------------------------------------

@name       getMinHeight - get min height
                                                                              */
                                                                             /**
            Get min height.

@return     min height

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getMinHeight();

}//====================================// end IToolbar =======================//
}//====================================// end IMixins ========================//
/*==============================================================================

name:       IPalette - palette interface

purpose:    Palette interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IPalette
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_PALETTE_ACTION                           = "action";
String   kKEY_PALETTE_BACKGROUND                       = "background";
String   kKEY_PALETTE_COMMON                           = "common";
String   kKEY_PALETTE_CONTRAST_THRESHOLD               = "contrastThreshold";
String   kKEY_PALETTE_DIVIDER                          = "divider";
String   kKEY_PALETTE_ERROR                            = "error";
String   kKEY_PALETTE_FCN_AUGMENT_COLOR                = "augmentColor";
String   kKEY_PALETTE_FCN_GET_CONTRAST_TEXT            = "getContrastText";
String   kKEY_PALETTE_GREY                             = "grey";
String   kKEY_PALETTE_PRIMARY                          = "primary";
String   kKEY_PALETTE_PROPS                            = "props";
String   kKEY_PALETTE_SECONDARY                        = "secondary";
String   kKEY_PALETTE_TEXT                             = "text";
String   kKEY_PALETTE_TONAL_OFFSET                     = "tonalOffset";
String   kKEY_PALETTE_TYPE                             = "type";

                                       // default values                      //
int      kVAL_PALETTE_CONTRAST_THRESHOLD              = 3;
String   kVAL_PALETTE_DIVIDER                          = "rgba(0, 0, 0, 0.12)";
Function kVAL_PALETTE_FCN_AUGMENT_COLOR                = null;
Function kVAL_PALETTE_FCN_GET_CONTRAST_TEXT            = null;
String   kVAL_PALETTE_PROPS                            = null;
double   kVAL_PALETTE_TONAL_OFFSET                     = 0.2;
String   kVAL_PALETTE_TYPE                             = "light";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IPalette defaultInstance()
{
   return(new Palette());
}
/*------------------------------------------------------------------------------

@name       getAction - get action
                                                                              */
                                                                             /**
            Get action.

@return     action

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IAction getAction();

/*------------------------------------------------------------------------------

@name       getBackground - get background
                                                                              */
                                                                             /**
            Get background.

@return     background

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IBackground getBackground();

/*------------------------------------------------------------------------------

@name       getCommon - get common
                                                                              */
                                                                             /**
            Get common.

@return     common

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
ICommon getCommon();

/*------------------------------------------------------------------------------

@name       getContrastThreshold - get contrast threshold
                                                                              */
                                                                             /**
            Get contrast threshold.

@return     contrast threshold

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getContrastThreshold();

/*------------------------------------------------------------------------------

@name       getDivider - get divider
                                                                              */
                                                                             /**
            Get divider.

@return     divider

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getDivider();

/*------------------------------------------------------------------------------

@name       getError - get error colors
                                                                              */
                                                                             /**
            Get error colors.

@return     error colors

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IColors getError();

/*------------------------------------------------------------------------------

@name       getFcnAugmentColor - get augment color function
                                                                              */
                                                                             /**
            Get caugment color function.

@return     augment color function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Function getFcnAugmentColor();

/*------------------------------------------------------------------------------

@name       getFcnGetContrastText - get contrast text function
                                                                              */
                                                                             /**
            Get contrast text function.

@return     contrast text function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Function getFcnGetContrastText();

/*------------------------------------------------------------------------------

@name       getGrey - get grey
                                                                              */
                                                                             /**
            Get grey.

@return     grey

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IGreys getGrey();

/*------------------------------------------------------------------------------

@name       getPrimary - get primary colors
                                                                              */
                                                                             /**
            Get primary colors.

@return     primary colors

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IColors getPrimary();

/*------------------------------------------------------------------------------

@name       getProps - get props
                                                                              */
                                                                             /**
            Get props.

@return     props

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
NativeObject getProps();

/*------------------------------------------------------------------------------

@name       getSecondary - get secondary colors
                                                                              */
                                                                             /**
            Get secondary colors.

@return     secondary colors

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IColors getSecondary();

/*------------------------------------------------------------------------------

@name       getText - get text colors
                                                                              */
                                                                             /**
            Get text colors.

@return     text colors

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IColors getText();

/*------------------------------------------------------------------------------

@name       getTonalOffset - get tonal offset
                                                                              */
                                                                             /**
            Get tonal offset.

@return     tonal offset

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
double getTonalOffset();

/*------------------------------------------------------------------------------

@name       getType - get type
                                                                              */
                                                                             /**
            Get type.

@return     type

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getType();

/*==============================================================================

name:       IAction - palette action interface

purpose:    Palette action interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IAction
{
                                       // constants ------------------------- //
                                       // keys                                //
String kKEY_PALETTE_ACTION_ACTIVE              = "active";
String kKEY_PALETTE_ACTION_DISABLED            = "disabled";
String kKEY_PALETTE_ACTION_DISABLED_BACKGROUND = "disabledBackground";
String kKEY_PALETTE_ACTION_HOVER               = "hover";
String kKEY_PALETTE_ACTION_HOVER_OPACITY       = "hoverOpacity";
String kKEY_PALETTE_ACTION_SELECTED            = "selected";

                                       //default values                       //
String kVAL_PALETTE_ACTION_ACTIVE              = "rgba(0, 0, 0, 0.54)";
String kVAL_PALETTE_ACTION_DISABLED            = "rgba(0, 0, 0, 0.26)";
String kVAL_PALETTE_ACTION_DISABLED_BACKGROUND = "rgba(0, 0, 0, 0.12)";
String kVAL_PALETTE_ACTION_HOVER               = "rgba(0, 0, 0, 0.08";
double kVAL_PALETTE_ACTION_HOVER_OPACITY       = 0.08;
String kVAL_PALETTE_ACTION_SELECTED            = "rgba(0, 0, 0, 0.14)";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IAction defaultInstance()
{
   return(new Action());
}
/*------------------------------------------------------------------------------

@name       getActive - get palette active action
                                                                              */
                                                                             /**
            Get palette active action.

@return     palette active action

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getActive();

/*------------------------------------------------------------------------------

@name       getDisabled - get palette disabled action
                                                                              */
                                                                             /**
            Get palette disabled action.

@return     palette disabled action

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getDisabled();

/*------------------------------------------------------------------------------

@name       getDisabledBackground - get palette disabled background action
                                                                              */
                                                                             /**
            Get palette disabled background action.

@return     palette disabled background action

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getDisabledBackground();

/*------------------------------------------------------------------------------

@name       getHover - get palette hover action
                                                                              */
                                                                             /**
            Get palette hover action.

@return     palette hover action

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getHover();

/*------------------------------------------------------------------------------

@name       getHoverOpacity - get palette hover opacity action
                                                                              */
                                                                             /**
            Get palette hover opacity action.

@return     palette hover opacity action

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
double getHoverOpacity();

/*------------------------------------------------------------------------------

@name       getSelected - get palette selected action
                                                                              */
                                                                             /**
            Get palette selected action.

@return     palette selected action

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getSelected();

}//====================================// end IAction ========================//
/*==============================================================================

name:       IBackground - palette background interface

purpose:    Palette background interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IBackground
{
                                       // constants ------------------------- //
                                       // keys                                //
String kKEY_PALETTE_BACKGROUND_DEFAULT = "default";
String kKEY_PALETTE_BACKGROUND_PAPER   = "paper";
                                       // default values                      //
String kVAL_PALETTE_BACKGROUND_DEFAULT = "#fafafa";
String kVAL_PALETTE_BACKGROUND_PAPER   = "#fff";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IBackground defaultInstance()
{
   return(new Background());
}
/*------------------------------------------------------------------------------

@name       getDefault - get palette background default
                                                                              */
                                                                             /**
            Get palette background default.

@return     palette background default

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getDefault();

/*------------------------------------------------------------------------------

@name       getPaper - get palette background paper
                                                                              */
                                                                             /**
            Get palette background paper.

@return     palette background paper

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getPaper();

}//====================================// end IBackground ====================//
/*==============================================================================

name:       IColors - palette colors interface

purpose:    Palette colors interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IColors
{
                                       // constants ------------------------- //
                                       // keys                                //
String kKEY_PALETTE_COLORS_CONTRAST_TEXT = "contrastText";
String kKEY_PALETTE_COLORS_DARK          = "dark";
String kKEY_PALETTE_COLORS_LIGHT         = "light";
String kKEY_PALETTE_COLORS_MAIN          = "main";

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IColors defaultInstance()
{
   return(new Colors());
}
/*------------------------------------------------------------------------------

@name       getLight - get palette colors light
                                                                              */
                                                                             /**
            Get palette common colors light.

@return     palette common colors light

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getLight();

/*------------------------------------------------------------------------------

@name       getDark - get palette colors dark
                                                                              */
                                                                             /**
            Get palette common colors dark.

@return     palette common colors dark

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getDark();

/*------------------------------------------------------------------------------

@name       getMain - get palette colors main
                                                                              */
                                                                             /**
            Get palette common colors main.

@return     palette common colors main

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getMain();

/*------------------------------------------------------------------------------

@name       getContrastText - get palette colors contrast text
                                                                              */
                                                                             /**
            Get palette common colors contrast text.

@return     palette common colors contrast text

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getContrastText();

}//====================================// end IColors ========================//
/*==============================================================================

name:       ICommon - palette common interface

purpose:    Palette common interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ICommon
{
                                       // constants ------------------------- //
                                       // keys                                //
String kKEY_PALETTE_COMMON_BLACK = "black";
String kKEY_PALETTE_COMMON_WHITE = "white";
                                       // default values                      //
String kVAL_PALETTE_COMMON_BLACK = "#000";
String kVAL_PALETTE_COMMON_WHITE = "#fff";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static ICommon defaultInstance()
{
   return(new Common());
}
/*------------------------------------------------------------------------------

@name       getBlack - get palette common black
                                                                              */
                                                                             /**
            Get palette common black.

@return     palette common black

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getBlack();

/*------------------------------------------------------------------------------

@name       getWhite - get palette common white
                                                                              */
                                                                             /**
            Get palette common white.

@return     palette common white

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getWhite();

}//====================================// end ICommon ========================//
/*==============================================================================

name:       IError - palette error interface

purpose:    Palette error interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IError extends IColors
{
                                       // constants ------------------------- //
                                       // keys                                //
String kKEY_PALETTE_ERROR_CONTRAST_TEXT = "contrastText";
String kKEY_PALETTE_ERROR_DARK          = "dark";
String kKEY_PALETTE_ERROR_LIGHT         = "light";
String kKEY_PALETTE_ERROR_MAIN          = "main";
                                       // default values                      //
String kVAL_PALETTE_ERROR_CONTRAST_TEXT = "#fff";
String kVAL_PALETTE_ERROR_DARK          = "#d32f2f";
String kVAL_PALETTE_ERROR_LIGHT         = "#e57373";
String kVAL_PALETTE_ERROR_MAIN          = "#f44336";

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static IError defaultInstance()
{
   return(new Error());
}
}//====================================// end IError =========================//
/*==============================================================================

name:       IGreys - palette greys interface

purpose:    Palette greys interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IGreys
{
                                       // constants ------------------------- //
                                       // keys                                //
String kKEY_PALETTE_GREY_50   = "50";
String kKEY_PALETTE_GREY_100  = "100";
String kKEY_PALETTE_GREY_200  = "200";
String kKEY_PALETTE_GREY_300  = "300";
String kKEY_PALETTE_GREY_400  = "400";
String kKEY_PALETTE_GREY_500  = "500";
String kKEY_PALETTE_GREY_600  = "600";
String kKEY_PALETTE_GREY_700  = "700";
String kKEY_PALETTE_GREY_800  = "800";
String kKEY_PALETTE_GREY_900  = "900";
String kKEY_PALETTE_GREY_A100 = "A100";
String kKEY_PALETTE_GREY_A200 = "A200";
String kKEY_PALETTE_GREY_A400 = "A400";
String kKEY_PALETTE_GREY_A700 = "A700";
                                       // default values                      //
String kVAL_PALETTE_GREY_50   = "#fafafa";
String kVAL_PALETTE_GREY_100  = "#f5f5f5";
String kVAL_PALETTE_GREY_200  = "#eeeeee";
String kVAL_PALETTE_GREY_300  = "#e0e0e0";
String kVAL_PALETTE_GREY_400  = "#bdbdbd";
String kVAL_PALETTE_GREY_500  = "#9e9e9e";
String kVAL_PALETTE_GREY_600  = "#757575";
String kVAL_PALETTE_GREY_700  = "#616161";
String kVAL_PALETTE_GREY_800  = "#424242";
String kVAL_PALETTE_GREY_900  = "#212121";
String kVAL_PALETTE_GREY_A100 = "#d5d5d5";
String kVAL_PALETTE_GREY_A200 = "#aaaaaa";
String kVAL_PALETTE_GREY_A400 = "#303030";
String kVAL_PALETTE_GREY_A700 = "#616161";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IGreys defaultInstance()
{
   return(new Greys());
}
/*------------------------------------------------------------------------------

@name       get50 - get palette greys 50
                                                                              */
                                                                             /**
            Get palette greys 50.

@return     palette common greys 50

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String get50();

/*------------------------------------------------------------------------------

@name       get100 - get palette greys 100
                                                                              */
                                                                             /**
            Get palette greys 100.

@return     palette common greys 100

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String get100();

/*------------------------------------------------------------------------------

@name       get200 - get palette greys 200
                                                                              */
                                                                             /**
            Get palette greys 200.

@return     palette common greys 200

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String get200();

/*------------------------------------------------------------------------------

@name       get300 - get palette greys 300
                                                                              */
                                                                             /**
            Get palette greys 300.

@return     palette common greys 300

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String get300();

/*------------------------------------------------------------------------------

@name       get400 - get palette greys 400
                                                                              */
                                                                             /**
            Get palette greys 400.

@return     palette common greys 400

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String get400();

/*------------------------------------------------------------------------------

@name       get500 - get palette greys 500
                                                                              */
                                                                             /**
            Get palette greys 500.

@return     palette common greys 500

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String get500();

/*------------------------------------------------------------------------------

@name       get600 - get palette greys 600
                                                                              */
                                                                             /**
            Get palette greys 600.

@return     palette common greys 600

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String get600();

/*------------------------------------------------------------------------------

@name       get700 - get palette greys 700
                                                                              */
                                                                             /**
            Get palette greys 700.

@return     palette common greys 700

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String get700();

/*------------------------------------------------------------------------------

@name       get800 - get palette greys 800
                                                                              */
                                                                             /**
            Get palette greys 800.

@return     palette common greys 800

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String get800();

/*------------------------------------------------------------------------------

@name       get900 - get palette greys 900
                                                                              */
                                                                             /**
            Get palette greys 900.

@return     palette common greys 900

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String get900();

/*------------------------------------------------------------------------------

@name       getA100 - get palette greys A100
                                                                              */
                                                                             /**
            Get palette greys A100.

@return     palette common greys A100

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getA100();

/*------------------------------------------------------------------------------

@name       getA200 - get palette greys A200
                                                                              */
                                                                             /**
            Get palette greys A200.

@return     palette common greys A200

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getA200();

/*------------------------------------------------------------------------------

@name       getA400 - get palette greys A400
                                                                              */
                                                                             /**
            Get palette greys A400.

@return     palette common greys A400

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getA400();

/*------------------------------------------------------------------------------

@name       getA700 - get palette greys A700
                                                                              */
                                                                             /**
            Get palette greys A700.

@return     palette common greys A700

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getA700();

}//====================================// end IGreys =========================//
/*==============================================================================

name:       IPrimary - palette primary interface

purpose:    Palette primary interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IPrimary extends IColors
{
                                       // constants ------------------------- //
                                       // keys                                //
String kKEY_PALETTE_PRIMARY_CONTRAST_TEXT = "contrastText";
String kKEY_PALETTE_PRIMARY_DARK          = "dark";
String kKEY_PALETTE_PRIMARY_LIGHT         = "light";
String kKEY_PALETTE_PRIMARY_MAIN          = "main";
                                       // default values                      //
String kVAL_PALETTE_PRIMARY_CONTRAST_TEXT = "#fff";
String kVAL_PALETTE_PRIMARY_DARK          = "#303f9f";
String kVAL_PALETTE_PRIMARY_LIGHT         = "#7986cb";
String kVAL_PALETTE_PRIMARY_MAIN          = "#3f51b5";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static IPrimary defaultInstance()
{
   return(new Primary());
}
}//====================================// end IPrimary =======================//
/*==============================================================================

name:       ISecondary - palette secondary interface

purpose:    Palette secondary interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ISecondary extends IColors
{
                                       // constants ------------------------- //
                                       // keys                                //
String kKEY_PALETTE_SECONDARY_CONTRAST_TEXT = "contrastText";
String kKEY_PALETTE_SECONDARY_DARK          = "dark";
String kKEY_PALETTE_SECONDARY_LIGHT         = "light";
String kKEY_PALETTE_SECONDARY_MAIN          = "main";
                                       // default values                      //
String kVAL_PALETTE_SECONDARY_CONTRAST_TEXT = "#fff";
String kVAL_PALETTE_SECONDARY_DARK          = "#c51162";
String kVAL_PALETTE_SECONDARY_LIGHT         = "#ff4081";
String kVAL_PALETTE_SECONDARY_MAIN          = "#f50057";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static ISecondary defaultInstance()
{
   return(new Secondary());
}
}//====================================// end ISecondary =====================//
/*==============================================================================

name:       IText - palette text interface

purpose:    Palette text interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IText extends IColors
{
                                       // constants ------------------------- //
                                       // keys                                //
String kKEY_PALETTE_TEXT_DISABLED  = "disabled";
String kKEY_PALETTE_TEXT_HINT      = "hint";
String kKEY_PALETTE_TEXT_PRIMARY   = "primary";
String kKEY_PALETTE_TEXT_SECONDARY = "secondary";
                                       // default values                      //
String kVAL_PALETTE_TEXT_DISABLED  = "rgba(0, 0, 0, 0.38)";
String kVAL_PALETTE_TEXT_HINT      = "rgba(0, 0, 0, 0.38)";
String kVAL_PALETTE_TEXT_PRIMARY   = "rgba(0, 0, 0, 0.87)";
String kVAL_PALETTE_TEXT_SECONDARY = "rgba(0, 0, 0, 0.54)";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IText defaultInstance()
{
   return(new Text());
}
/*------------------------------------------------------------------------------

@name       getDisabled - palette text colors disabled
                                                                              */
                                                                             /**
            Get palette text colors disabled.

@return     palette text colors disabled

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getDisabled();

/*------------------------------------------------------------------------------

@name       getHint - palette text colors hint
                                                                              */
                                                                             /**
            Get palette text colors hint.

@return     palette text colors hint

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getHint();

/*------------------------------------------------------------------------------

@name       getPrimary - palette text colors primary
                                                                              */
                                                                             /**
            Get palette text colors primary.

@return     palette text colors primary

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getPrimary();

/*------------------------------------------------------------------------------

@name       getSecondary - palette text colors secondary
                                                                              */
                                                                             /**
            Get palette text colors secondary.

@return     palette text colors secondary

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getSecondary();

}//====================================// end IText ==========================//
}//====================================// end IPalette =======================//
/*==============================================================================

name:       IShadows - shadows interface

purpose:    Shadows interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IShadows
{
                                       // constants ------------------------- //
                                       // default values                      //
String kVAL_SHADOWS_00 = "none";
String kVAL_SHADOWS_01 = "0px 1px 3px 0px rgba(0,0,0,0.2),0px 1px 1px 0px rgba(0,0,0,0.14),0px 2px 1px -1px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_02 = "0px 1px 5px 0px rgba(0,0,0,0.2),0px 2px 2px 0px rgba(0,0,0,0.14),0px 3px 1px -2px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_03 = "0px 1px 8px 0px rgba(0,0,0,0.2),0px 3px 4px 0px rgba(0,0,0,0.14),0px 3px 3px -2px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_04 = "0px 2px 4px -1px rgba(0,0,0,0.2),0px 4px 5px 0px rgba(0,0,0,0.14),0px 1px 10px 0px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_05 = "0px 3px 5px -1px rgba(0,0,0,0.2),0px 5px 8px 0px rgba(0,0,0,0.14),0px 1px 14px 0px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_06 = "0px 3px 5px -1px rgba(0,0,0,0.2),0px 6px 10px 0px rgba(0,0,0,0.14),0px 1px 18px 0px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_07 = "0px 4px 5px -2px rgba(0,0,0,0.2),0px 7px 10px 1px rgba(0,0,0,0.14),0px 2px 16px 1px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_08 = "0px 5px 5px -3px rgba(0,0,0,0.2),0px 8px 10px 1px rgba(0,0,0,0.14),0px 3px 14px 2px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_09 = "0px 5px 6px -3px rgba(0,0,0,0.2),0px 9px 12px 1px rgba(0,0,0,0.14),0px 3px 16px 2px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_10 = "0px 6px 6px -3px rgba(0,0,0,0.2),0px 10px 14px 1px rgba(0,0,0,0.14),0px 4px 18px 3px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_11 = "0px 6px 7px -4px rgba(0,0,0,0.2),0px 11px 15px 1px rgba(0,0,0,0.14),0px 4px 20px 3px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_12 = "0px 7px 8px -4px rgba(0,0,0,0.2),0px 12px 17px 2px rgba(0,0,0,0.14),0px 5px 22px 4px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_13 = "0px 7px 8px -4px rgba(0,0,0,0.2),0px 13px 19px 2px rgba(0,0,0,0.14),0px 5px 24px 4px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_14 = "0px 7px 9px -4px rgba(0,0,0,0.2),0px 14px 21px 2px rgba(0,0,0,0.14),0px 5px 26px 4px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_15 = "0px 8px 9px -5px rgba(0,0,0,0.2),0px 15px 22px 2px rgba(0,0,0,0.14),0px 6px 28px 5px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_16 = "0px 8px 10px -5px rgba(0,0,0,0.2),0px 16px 24px 2px rgba(0,0,0,0.14),0px 6px 30px 5px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_17 = "0px 8px 11px -5px rgba(0,0,0,0.2),0px 17px 26px 2px rgba(0,0,0,0.14),0px 6px 32px 5px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_18 = "0px 9px 11px -5px rgba(0,0,0,0.2),0px 18px 28px 2px rgba(0,0,0,0.14),0px 7px 34px 6px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_19 = "0px 9px 12px -6px rgba(0,0,0,0.2),0px 19px 29px 2px rgba(0,0,0,0.14),0px 7px 36px 6px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_20 = "0px 10px 13px -6px rgba(0,0,0,0.2),0px 20px 31px 3px rgba(0,0,0,0.14),0px 8px 38px 7px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_21 = "0px 10px 13px -6px rgba(0,0,0,0.2),0px 21px 33px 3px rgba(0,0,0,0.14),0px 8px 40px 7px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_22 = "0px 10px 14px -6px rgba(0,0,0,0.2),0px 22px 35px 3px rgba(0,0,0,0.14),0px 8px 42px 7px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_23 = "0px 11px 14px -7px rgba(0,0,0,0.2),0px 23px 36px 3px rgba(0,0,0,0.14),0px 9px 44px 8px rgba(0,0,0,0.12)";
String kVAL_SHADOWS_24 = "0px 11px 15px -7px rgba(0,0,0,0.2),0px 24px 38px 3px rgba(0,0,0,0.14),0px 9px 46px 8px rgba(0,0,0,0.12)";

String[] kSHADOWS_DEFAULT =
{
   kVAL_SHADOWS_00,
   kVAL_SHADOWS_01,
   kVAL_SHADOWS_02,
   kVAL_SHADOWS_03,
   kVAL_SHADOWS_04,
   kVAL_SHADOWS_05,
   kVAL_SHADOWS_06,
   kVAL_SHADOWS_07,
   kVAL_SHADOWS_08,
   kVAL_SHADOWS_09,
   kVAL_SHADOWS_11,
   kVAL_SHADOWS_11,
   kVAL_SHADOWS_12,
   kVAL_SHADOWS_13,
   kVAL_SHADOWS_14,
   kVAL_SHADOWS_15,
   kVAL_SHADOWS_16,
   kVAL_SHADOWS_17,
   kVAL_SHADOWS_18,
   kVAL_SHADOWS_19,
   kVAL_SHADOWS_20,
   kVAL_SHADOWS_21,
   kVAL_SHADOWS_22,
   kVAL_SHADOWS_23,
   kVAL_SHADOWS_24,
};
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IShadows defaultInstance()
{
   return(new Shadows());
}
/*------------------------------------------------------------------------------

@name       getShadows - get shadows
                                                                              */
                                                                             /**
            Get shadows.

@return     shadows

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
List<String> getShadows();

}//====================================// end IShadows =======================//
/*==============================================================================

name:       IShape - shape interface

purpose:    Shape interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IShape
{
                                       // constants ------------------------- //
                                       // keys                                //
String kKEY_SHAPE_BORDER_RADIUS = "borderRadius";
                                       // default values                      //
int    kVAL_SHAPE_BORDER_RADIUS = 4;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IShape defaultInstance()
{
   return(new Shape());
}
/*------------------------------------------------------------------------------

@name       getBorderRadius - get border radius
                                                                              */
                                                                             /**
            Get border radius.

@return     border radius

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getBorderRadius();

}//====================================// end IShape =========================//
/*==============================================================================

name:       ISpacing - spacing interface

purpose:    Spacing interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ISpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String kKEY_SPACING_UNIT  = "unit";
                                       // default values                      //
int    kVAL_SPACING_UNIT = 8;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static ISpacing defaultInstance()
{
   return(new Spacing());
}
/*------------------------------------------------------------------------------

@name       getUnit - get unit
                                                                              */
                                                                             /**
            Get unit.

@return     unit

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getUnit();

}//====================================// end ISpacing =======================//
/*==============================================================================

name:       ITransitions - transitions interface

purpose:    Transitions interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ITransitions
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TRANSITIONS_DURATION                     = "duration";
String   kKEY_TRANSITIONS_EASING                       = "easing";
String   kKEY_TRANSITIONS_FCN_CREATE                   = "create";
String   kKEY_TRANSITIONS_FCN_GET_AUTO_HEIGHT_DURATION = "getAutoHeightDuration";

String[] kKEYS =
{
   kKEY_TRANSITIONS_DURATION,
   kKEY_TRANSITIONS_EASING,
   kKEY_TRANSITIONS_FCN_CREATE,
   kKEY_TRANSITIONS_FCN_GET_AUTO_HEIGHT_DURATION
};
                                       // default values                      //
                                       // default values                      //
Function kVAL_TRANSITIONS_FCN_CREATE                   = null;
Function kVAL_TRANSITIONS_FCN_GET_AUTO_HEIGHT_DURATION = null;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static ITransitions defaultInstance()
{
   return(new Transitions());
}
/*------------------------------------------------------------------------------

@name       getDuration - get duration
                                                                              */
                                                                             /**
            Get duration.

@return     duration

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IDuration getDuration();

/*------------------------------------------------------------------------------

@name       getEasing - get easing
                                                                              */
                                                                             /**
            Get easing.

@return     easing

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IEasing getEasing();

/*------------------------------------------------------------------------------

@name       getFcnAutoHeightDuration - get autoHeightDuration function
                                                                              */
                                                                             /**
            Get autoHeightDuration function.

@return     autoHeightDuration function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Function getFcnAutoHeightDuration();

/*------------------------------------------------------------------------------

@name       getFcnCreate - get create function
                                                                              */
                                                                             /**
            Get create function.

@return     create function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Function getFcnCreate();

/*==============================================================================

name:       IDuration - transitions duration interface

purpose:    Transitions duration interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IDuration
{
                                       // constants ------------------------- //
                                       // keys                                //
String kKEY_TRANSITIONS_DURATION_COMPLEX         = "complex";
String kKEY_TRANSITIONS_DURATION_ENTERING_SCREEN = "enteringScreen";
String kKEY_TRANSITIONS_DURATION_LEAVING_SCREEN  = "leavingScreen";
String kKEY_TRANSITIONS_DURATION_SHORT           = "short";
String kKEY_TRANSITIONS_DURATION_SHORTER         = "shorter";
String kKEY_TRANSITIONS_DURATION_SHORTEST        = "shortest";
String kKEY_TRANSITIONS_DURATION_STANDARD        = "standard";

String[] kKEYS =
{
   kKEY_TRANSITIONS_DURATION_COMPLEX,
   kKEY_TRANSITIONS_DURATION_ENTERING_SCREEN,
   kKEY_TRANSITIONS_DURATION_LEAVING_SCREEN,
   kKEY_TRANSITIONS_DURATION_SHORT,
   kKEY_TRANSITIONS_DURATION_SHORTER,
   kKEY_TRANSITIONS_DURATION_SHORTEST,
   kKEY_TRANSITIONS_DURATION_STANDARD
};
                                       // default values                      //
int    kVAL_TRANSITIONS_DURATION_COMPLEX         = 375;
int    kVAL_TRANSITIONS_DURATION_ENTERING_SCREEN = 225;
int    kVAL_TRANSITIONS_DURATION_LEAVING_SCREEN  = 195;
int    kVAL_TRANSITIONS_DURATION_SHORT           = 250;
int    kVAL_TRANSITIONS_DURATION_SHORTER         = 200;
int    kVAL_TRANSITIONS_DURATION_SHORTEST        = 150;
int    kVAL_TRANSITIONS_DURATION_STANDARD        = 300;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IDuration defaultInstance()
{
   return(new Duration());
}
/*------------------------------------------------------------------------------

@name       getComplex - get complex
                                                                              */
                                                                             /**
            Get complex.

@return     complex

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getComplex();

/*------------------------------------------------------------------------------

@name       getEnteringScreen - get entering screen
                                                                              */
                                                                             /**
            Get entering screen.

@return     entering screen

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getEnteringScreen();

/*------------------------------------------------------------------------------

@name       getLeavingScreen - get leaving screen
                                                                              */
                                                                             /**
            Get leaving screen.

@return     leaving screen

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getLeavingScreen();

/*------------------------------------------------------------------------------

@name       getShort - get short
                                                                              */
                                                                             /**
            Get short.

@return     short

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getShort();

/*------------------------------------------------------------------------------

@name       getShorter - get shorter
                                                                              */
                                                                             /**
            Get shorter.

@return     shorter

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getShorter();

/*------------------------------------------------------------------------------

@name       getShortest - get shortest
                                                                              */
                                                                             /**
            Get shortest.

@return     shortest

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getShortest();

/*------------------------------------------------------------------------------

@name       getStandard - get standard
                                                                              */
                                                                             /**
            Get standard.

@return     standard

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getStandard();

/*------------------------------------------------------------------------------

@name       setComplex - set complex
                                                                              */
                                                                             /**
            Set complex.

@param      complex     complex

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void setComplex(int complex);

/*------------------------------------------------------------------------------

@name       setEnteringScreen - Set entering screen
                                                                              */
                                                                             /**
            Set entering screen.

@param      enteringScreen    entering screen

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void setEnteringScreen(int enteringScreen);

/*------------------------------------------------------------------------------

@name       setLeavingScreen - set leaving screen
                                                                              */
                                                                             /**
            Set leaving screen.

@param      leavingScreen    leaving screen

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void setLeavingScreen(int leavingScreen);

/*------------------------------------------------------------------------------

@name       setShort - set short
                                                                              */
                                                                             /**
            Set short.

@param      shortVal    short

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void setShort(int shortVal);

/*------------------------------------------------------------------------------

@name       setShorter - set shorter
                                                                              */
                                                                             /**
            Set shorter.

@param      shorter    shorter

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void setShorter(int shorter);

/*------------------------------------------------------------------------------

@name       setShortest - set shortest
                                                                              */
                                                                             /**
            Set shortest.

@param      shortest    shortest

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void setShortest(int shortest);

/*------------------------------------------------------------------------------

@name       setStandard - set standard
                                                                              */
                                                                             /**
            Set standard.

@param      standard    standard

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void setStandard(int standard);

}//====================================// end IDuration ======================//
/*==============================================================================

name:       IEasing - transitions easing interface

purpose:    Transitions easing interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IEasing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TRANSITIONS_EASE_IN     = "easeIn";
String   kKEY_TRANSITIONS_EASE_IN_OUT = "easeInOut";
String   kKEY_TRANSITIONS_EASE_OUT    = "easeOut";
String   kKEY_TRANSITIONS_EASE_SHARP  = "easeSharp";

String[] kKEYS =
{
   kKEY_TRANSITIONS_EASE_IN,
   kKEY_TRANSITIONS_EASE_IN_OUT,
   kKEY_TRANSITIONS_EASE_OUT,
   kKEY_TRANSITIONS_EASE_SHARP
};
                                       // default values                      //
String   kVAL_TRANSITIONS_EASE_IN     = "cubic-bezier(0.4, 0, 1, 1)";
String   kVAL_TRANSITIONS_EASE_IN_OUT = "cubic-bezier(0.4, 0, 0.2, 1)";
String   kVAL_TRANSITIONS_EASE_OUT    = "cubic-bezier(0.0, 0, 0.2, 1)";
String   kVAL_TRANSITIONS_EASE_SHARP  = "cubic-bezier(0.4, 0, 0.6, 1)";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IEasing defaultInstance()
{
   return(new Easing());
}
/*------------------------------------------------------------------------------

@name       easeIn - get ease in
                                                                              */
                                                                             /**
            Get ease in.

@return     ease in

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String easeIn();

/*------------------------------------------------------------------------------

@name       easeInOut - get ease inOut
                                                                              */
                                                                             /**
            Get ease inOut.

@return     ease inOut

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String easeInOut();

/*------------------------------------------------------------------------------

@name       easeOut - get ease out
                                                                              */
                                                                             /**
            Get ease out.

@return     ease out

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String easeOut();

/*------------------------------------------------------------------------------

@name       sharp - get sharp
                                                                              */
                                                                             /**
            Get sharp.

@return     sharp

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String sharp();

}//====================================// end IEasing ========================//
}//====================================// end ITransitions ===================//
/*==============================================================================

name:       ITypography - typography interface

purpose:    Typography interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ITypography
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_BODY_1              = "body1";
String   kKEY_TYPOGRAPHY_BODY_1_NEXT         = "body1Next";
String   kKEY_TYPOGRAPHY_BODY_2              = "body2";
String   kKEY_TYPOGRAPHY_BODY_2_NEXT         = "body2Next";
String   kKEY_TYPOGRAPHY_BUTTON              = "button";
String   kKEY_TYPOGRAPHY_BUTTON_NEXT         = "buttonNext";
String   kKEY_TYPOGRAPHY_CAPTION             = "captionNext";
String   kKEY_TYPOGRAPHY_CAPTION_NEXT        = "captionNext";
String   kKEY_TYPOGRAPHY_COLOR               = "color";
String   kKEY_TYPOGRAPHY_DISPLAY1            = "display1";
String   kKEY_TYPOGRAPHY_DISPLAY2            = "display2";
String   kKEY_TYPOGRAPHY_DISPLAY3            = "display3";
String   kKEY_TYPOGRAPHY_DISPLAY4            = "display4";
String   kKEY_TYPOGRAPHY_FCN_PX_TO_REM       = "pxToRem";
String   kKEY_TYPOGRAPHY_FCN_ROUND           = "round";
String   kKEY_TYPOGRAPHY_FONT_FAMILY         = "fontFamily";
String   kKEY_TYPOGRAPHY_FONT_SIZE           = "fontSize";
String   kKEY_TYPOGRAPHY_FONT_WEIGHT         = "fontWeight";
String   kKEY_TYPOGRAPHY_FONT_WEIGHT_LIGHT   = "fontWeightLight";
String   kKEY_TYPOGRAPHY_FONT_WEIGHT_MEDIUM  = "fontWeightMedium";
String   kKEY_TYPOGRAPHY_FONT_WEIGHT_REGULAR = "fontWeightRegular";
String   kKEY_TYPOGRAPHY_H1                  = "h1";
String   kKEY_TYPOGRAPHY_H2                  = "h2";
String   kKEY_TYPOGRAPHY_H3                  = "h3";
String   kKEY_TYPOGRAPHY_H4                  = "h4";
String   kKEY_TYPOGRAPHY_H5                  = "h5";
String   kKEY_TYPOGRAPHY_H6                  = "h6";
String   kKEY_TYPOGRAPHY_HEADLINE            = "headline";
String   kKEY_TYPOGRAPHY_LETTER_SPACING      = "letterSpacing";
String   kKEY_TYPOGRAPHY_LINE_HEIGHT         = "lineHeight";
String   kKEY_TYPOGRAPHY_MARGIN_LEFT         = "marginLeft";
String   kKEY_TYPOGRAPHY_OVERLINE            = "overline";
String   kKEY_TYPOGRAPHY_SUBHEADING          = "subheading";
String   kKEY_TYPOGRAPHY_SUBTITLE_1          = "subtitle1";
String   kKEY_TYPOGRAPHY_SUBTITLE_2          = "subtitle2";
String   kKEY_TYPOGRAPHY_TEXT_TRANSFORM      = "textTransform";
String   kKEY_TYPOGRAPHY_TITLE               = "title";
String   kKEY_TYPOGRAPHY_USE_NEXT_VARIANTS   = "useNextVariants";

                                       // default values                     //
Function kVAL_TYPOGRAPHY_FCN_PX_TO_REM       = null;
Function kVAL_TYPOGRAPHY_FCN_ROUND           = null;
String[] kVAL_TYPOGRAPHY_FONT_FAMILY         = {"Roboto", "Helvetica", "Arial", "sans-serif"};
int      kVAL_TYPOGRAPHY_FONT_SIZE           = 14;
int      kVAL_TYPOGRAPHY_FONT_WEIGHT_LIGHT   = 300;
int      kVAL_TYPOGRAPHY_FONT_WEIGHT_MEDIUM  = 500;
int      kVAL_TYPOGRAPHY_FONT_WEIGHT_REGULAR = 400;
boolean  kVAL_TYPOGRAPHY_USE_NEXT_VARIANTS   = true;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static ITypography defaultInstance()
{
   return(new Typography());
}
/*------------------------------------------------------------------------------

@name       getBody1 - get body1
                                                                              */
                                                                             /**
            Get body1.

@return     body1

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IBody1 getBody1();

/*------------------------------------------------------------------------------

@name       getBody1Next - get body1 next
                                                                              */
                                                                             /**
            Get body1 next.

@return     body1 next

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IBody1Next getBody1Next();

/*------------------------------------------------------------------------------

@name       getBody2 - get body2
                                                                              */
                                                                             /**
            Get body2.

@return     body2

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IBody2 getBody2();

/*------------------------------------------------------------------------------

@name       getBody2Next - get body2 next
                                                                              */
                                                                             /**
            Get body2 next.

@return     body2 next

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IBody2Next getBody2Next();

/*------------------------------------------------------------------------------

@name       getButton - get button
                                                                              */
                                                                             /**
            Get button.

@return     button

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IButton getButton();

/*------------------------------------------------------------------------------

@name       getButtonNext - get button next
                                                                              */
                                                                             /**
            Get button next.

@return     button next

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IButtonNext getButtonNext();

/*------------------------------------------------------------------------------

@name       getCaption - get caption
                                                                              */
                                                                             /**
            Get caption.

@return     caption

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
ICaption getCaption();

/*------------------------------------------------------------------------------

@name       getCaptionNext - get caption next
                                                                              */
                                                                             /**
            Get caption next.

@return     caption next

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
ICaptionNext getCaptionNext();

/*------------------------------------------------------------------------------

@name       getDisplay1 - get display1
                                                                              */
                                                                             /**
            Get display1.

@return     display1

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IDisplay1 getDisplay1();

/*------------------------------------------------------------------------------

@name       getDisplay2 - get display2
                                                                              */
                                                                             /**
            Get display2.

@return     display2

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IDisplay2 getDisplay2();

/*------------------------------------------------------------------------------

@name       getDisplay3 - get display3
                                                                              */
                                                                             /**
            Get display3.

@return     display3

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IDisplay3 getDisplay3();

/*------------------------------------------------------------------------------

@name       getDisplay4 - get display4
                                                                              */
                                                                             /**
            Get display4.

@return     display4

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IDisplay4 getDisplay4();

/*------------------------------------------------------------------------------

@name       getFcnPxToRem - get pxToRem function
                                                                              */
                                                                             /**
            Get pxToRem function.

@return     pxToRem function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Function getFcnPxToRem();

/*------------------------------------------------------------------------------

@name       getFcnRound - get round function
                                                                              */
                                                                             /**
            Get round function.

@return     round function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Function getFcnRound();

/*------------------------------------------------------------------------------

@name       getFontFamily - get font family
                                                                              */
                                                                             /**
            Get font family.

@return     font family

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String[] getFontFamily();

/*------------------------------------------------------------------------------

@name       getFontSize - get font size
                                                                              */
                                                                             /**
            Get font size.

@return     font size

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getFontSize();

/*------------------------------------------------------------------------------

@name       getFontWeightLight - get font weight light
                                                                              */
                                                                             /**
            Get font weight light.

@return     font weight light

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getFontWeightLight();

/*------------------------------------------------------------------------------

@name       getFontWeightMedium - get font weight medium
                                                                              */
                                                                             /**
            Get font weight medium.

@return     font weight medium

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getFontWeightMedium();

/*------------------------------------------------------------------------------

@name       getFontWeightRegular - get font weight regular
                                                                              */
                                                                             /**
            Get font weight regular.

@return     font weight regular

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getFontWeightRegular();

/*------------------------------------------------------------------------------

@name       getH1 - get h1
                                                                              */
                                                                             /**
            Get h1.

@return     h1

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IH1 getH1();

/*------------------------------------------------------------------------------

@name       getH2 - get h2
                                                                              */
                                                                             /**
            Get h2.

@return     h2

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IH2 getH2();

/*------------------------------------------------------------------------------

@name       getH3 - get h3
                                                                              */
                                                                             /**
            Get h3.

@return     h3

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IH3 getH3();

/*------------------------------------------------------------------------------

@name       getH4 - get h4
                                                                              */
                                                                             /**
            Get h4.

@return     h4

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IH4 getH4();

/*------------------------------------------------------------------------------

@name       getH5 - get h5
                                                                              */
                                                                             /**
            Get h5.

@return     h5

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IH5 getH5();

/*------------------------------------------------------------------------------

@name       getH6 - get h6
                                                                              */
                                                                             /**
            Get h6.

@return     h6

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IH6 getH6();

/*------------------------------------------------------------------------------

@name       getHeadline - get headline
                                                                              */
                                                                             /**
            Get headline.

@return     headline

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IHeadline getHeadline();

/*------------------------------------------------------------------------------

@name       getOverline - get overline
                                                                              */
                                                                             /**
            Get overline.

@return     overline

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
IOverline getOverline();

/*------------------------------------------------------------------------------

@name       getSubheading - get subheading
                                                                              */
                                                                             /**
            Get subheading.

@return     subheading

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
ISubheading getSubheading();

/*------------------------------------------------------------------------------

@name       getSubtitle1 - get subtitle1
                                                                              */
                                                                             /**
            Get subtitle1.

@return     subtitle1

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
ITypoLetterSpacing getSubtitle1();

/*------------------------------------------------------------------------------

@name       getSubtitle2 - get subtitle2
                                                                              */
                                                                             /**
            Get subtitle2.

@return     subtitle2

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
ITypoLetterSpacing getSubtitle2();

/*------------------------------------------------------------------------------

@name       getTitle - get title
                                                                              */
                                                                             /**
            Get title.

@return     title

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
ITypoBase getTitle();

/*------------------------------------------------------------------------------

@name       getUseNextVariants - get use next variants
                                                                              */
                                                                             /**
            Get use next variants.

@return     use next variants

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
boolean getUseNextVariants();

/*==============================================================================

name:       IBody1 - typography body1 interface

purpose:    TypoBase body1 interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IBody1 extends ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_BODY_1_COLOR          = "color";
String   kKEY_TYPOGRAPHY_BODY_1_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_BODY_1_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_BODY_1_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_BODY_1_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_BODY_1_LINE_HEIGHT    = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_BODY_1_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_BODY_1_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_BODY_1_FONT_SIZE      = "1rem";
int      kVAL_TYPOGRAPHY_BODY_1_FONT_WEIGHT    = 400;
String   kVAL_TYPOGRAPHY_BODY_1_LETTER_SPACING = "0.00938em";
double   kVAL_TYPOGRAPHY_BODY_1_LINE_HEIGHT    = 1.5;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IBody1 defaultInstance()
{
   return(new Body1());
}
}//====================================// end IBody1 =========================//
/*==============================================================================

name:       IBody1Next - typography body1Next interface

purpose:    TypoBase body1Next interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IBody1Next extends ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_BODY_1_NEXT_COLOR          = "color";
String   kKEY_TYPOGRAPHY_BODY_1_NEXT_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_BODY_1_NEXT_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_BODY_1_NEXT_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_BODY_1_NEXT_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_BODY_1_NEXT_LINE_HEIGHT    = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_BODY_1_NEXT_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_BODY_1_NEXT_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_BODY_1_NEXT_FONT_SIZE      = "1rem";
int      kVAL_TYPOGRAPHY_BODY_1_NEXT_FONT_WEIGHT    = 400;
String   kVAL_TYPOGRAPHY_BODY_1_NEXT_LETTER_SPACING = "0.00938em";
double   kVAL_TYPOGRAPHY_BODY_1_NEXT_LINE_HEIGHT    = 1.5;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IBody1Next defaultInstance()
{
   return(new Body1Next());
}
}//====================================// end IBody1Next =====================//
/*==============================================================================

name:       IBody2 - typography body2 interface

purpose:    TypoBase body2 interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IBody2 extends ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_BODY_2_COLOR          = "color";
String   kKEY_TYPOGRAPHY_BODY_2_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_BODY_2_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_BODY_2_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_BODY_2_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_BODY_2_LINE_HEIGHT    = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_BODY_2_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_BODY_2_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_BODY_2_FONT_SIZE      = "0.875rem";
int      kVAL_TYPOGRAPHY_BODY_2_FONT_WEIGHT    = 400;
String   kVAL_TYPOGRAPHY_BODY_2_LETTER_SPACING = "0.01071em";
double   kVAL_TYPOGRAPHY_BODY_2_LINE_HEIGHT    = 1.5;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IBody2 defaultInstance()
{
   return(new Body2());
}
}//====================================// end IBody2 =========================//
/*==============================================================================

name:       IBody2Next - typography body2Next interface

purpose:    TypoBase body2Next interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IBody2Next extends ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_BODY_2_NEXT_COLOR          = "color";
String   kKEY_TYPOGRAPHY_BODY_2_NEXT_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_BODY_2_NEXT_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_BODY_2_NEXT_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_BODY_2_NEXT_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_BODY_2_NEXT_LINE_HEIGHT    = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_BODY_2_NEXT_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_BODY_2_NEXT_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_BODY_2_NEXT_FONT_SIZE      = "0.875rem";
int      kVAL_TYPOGRAPHY_BODY_2_NEXT_FONT_WEIGHT    = 400;
String   kVAL_TYPOGRAPHY_BODY_2_NEXT_LETTER_SPACING = "0.01071em";
double   kVAL_TYPOGRAPHY_BODY_2_NEXT_LINE_HEIGHT    = 1.5;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IBody2Next defaultInstance()
{
   return(new Body2Next());
}
}//====================================// end IBody2Next =====================//
/*==============================================================================

name:       IButton - typography button interface

purpose:    TypoBase button interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IButton extends ITypoLetterSpacingTextTransform
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_BUTTON_COLOR          = "color";
String   kKEY_TYPOGRAPHY_BUTTON_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_BUTTON_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_BUTTON_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_BUTTON_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_BUTTON_LINE_HEIGHT    = "lineHeight";
String   kKEY_TYPOGRAPHY_BUTTON_TEXT_TRANSFORM = "textTransform";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_BUTTON_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_BUTTON_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_BUTTON_FONT_SIZE      = "0.875rem";
int      kVAL_TYPOGRAPHY_BUTTON_FONT_WEIGHT    = 500;
String   kVAL_TYPOGRAPHY_BUTTON_LETTER_SPACING = "0.02857em";
double   kVAL_TYPOGRAPHY_BUTTON_LINE_HEIGHT    = 1.75;
String   kVAL_TYPOGRAPHY_BUTTON_TEXT_TRANSFORM = "uppercase";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IButton defaultInstance()
{
   return(new Button());
}
}//====================================// end IButton ========================//
/*==============================================================================

name:       IButtonNext - typography buttonNext interface

purpose:    TypoBase buttonNext interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IButtonNext extends ITypoLetterSpacingTextTransform
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_BUTTON_NEXT_COLOR          = "color";
String   kKEY_TYPOGRAPHY_BUTTON_NEXT_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_BUTTON_NEXT_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_BUTTON_NEXT_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_BUTTON_NEXT_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_BUTTON_NEXT_LINE_HEIGHT    = "lineHeight";
String   kKEY_TYPOGRAPHY_BUTTON_NEXT_TEXT_TRANSFORM = "textTransform";
String   kVAL_TYPOGRAPHY_BUTTON_NEXT_COLOR          = "rgba(0, 0, 0, 0.87)";

                                       // default values                      //
String[] kVAL_TYPOGRAPHY_BUTTON_NEXT_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_BUTTON_NEXT_FONT_SIZE      = "0.875rem";
int      kVAL_TYPOGRAPHY_BUTTON_NEXT_FONT_WEIGHT    = 500;
String   kVAL_TYPOGRAPHY_BUTTON_NEXT_LETTER_SPACING = "0.02857em";
double   kVAL_TYPOGRAPHY_BUTTON_NEXT_LINE_HEIGHT    = 1.75;
String   kVAL_TYPOGRAPHY_BUTTON_NEXT_TEXT_TRANSFORM = "uppercase";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IButtonNext defaultInstance()
{
   return(new ButtonNext());
}
}//====================================// end IButtonNext ====================//
/*==============================================================================

name:       ICaption - typography caption interface

purpose:    TypoBase caption interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ICaption extends ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_CAPTION_COLOR          = "color";
String   kKEY_TYPOGRAPHY_CAPTION_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_CAPTION_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_CAPTION_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_CAPTION_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_CAPTION_LINE_HEIGHT    = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_CAPTION_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_CAPTION_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_CAPTION_FONT_SIZE      = "0.75rem";
int      kVAL_TYPOGRAPHY_CAPTION_FONT_WEIGHT    = 400;
String   kVAL_TYPOGRAPHY_CAPTION_LETTER_SPACING = "0.03333em";
double   kVAL_TYPOGRAPHY_CAPTION_LINE_HEIGHT    = 1.66;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static ICaption defaultInstance()
{
   return(new Caption());
}
}//====================================// end ICaption =======================//
/*==============================================================================

name:       ICaptionNext - typography captionNext interface

purpose:    TypoBase captionNext interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ICaptionNext extends ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_CAPTION_NEXT_COLOR          = "color";
String   kKEY_TYPOGRAPHY_CAPTION_NEXT_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_CAPTION_NEXT_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_CAPTION_NEXT_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_CAPTION_NEXT_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_CAPTION_NEXT_LINE_HEIGHT    = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_CAPTION_NEXT_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_CAPTION_NEXT_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_CAPTION_NEXT_FONT_SIZE      = "0.75rem";
int      kVAL_TYPOGRAPHY_CAPTION_NEXT_FONT_WEIGHT    = 400;
String   kVAL_TYPOGRAPHY_CAPTION_NEXT_LETTER_SPACING = "0.03333em";
double   kVAL_TYPOGRAPHY_CAPTION_NEXT_LINE_HEIGHT    = 1.66;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static ICaptionNext defaultInstance()
{
   return(new CaptionNext());
}
}//====================================// end ICaptionNext ===================//
/*==============================================================================

name:       IDisplay1 - typography caption interface

purpose:    IDisplay1 caption interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IDisplay1 extends ITypoBase
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_DISPLAY1_COLOR       = "color";
String   kKEY_TYPOGRAPHY_DISPLAY1_FONT_FAMILY = "fontFamily";
String   kKEY_TYPOGRAPHY_DISPLAY1_FONT_SIZE   = "fontSize";
String   kKEY_TYPOGRAPHY_DISPLAY1_FONT_WEIGHT = "fontWeight";
String   kKEY_TYPOGRAPHY_DISPLAY1_LINE_HEIGHT = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_DISPLAY1_COLOR       = "rgba(0, 0, 0, 0.54)";
String[] kVAL_TYPOGRAPHY_DISPLAY1_FONT_FAMILY = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_DISPLAY1_FONT_SIZE   = "2.125rem";
int      kVAL_TYPOGRAPHY_DISPLAY1_FONT_WEIGHT = 400;
String   kVAL_TYPOGRAPHY_DISPLAY1_LINE_HEIGHT = "1.20588em";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IDisplay1 defaultInstance()
{
   return(new Display1());
}
}//====================================// end IDisplay1 ======================//
/*==============================================================================

name:       IDisplay2 - typography display2 interface

purpose:    IDisplay2 caption interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IDisplay2 extends ITypoMarginLeft
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_DISPLAY2_COLOR       = "color";
String   kKEY_TYPOGRAPHY_DISPLAY2_FONT_FAMILY = "fontFamily";
String   kKEY_TYPOGRAPHY_DISPLAY2_FONT_SIZE   = "fontSize";
String   kKEY_TYPOGRAPHY_DISPLAY2_FONT_WEIGHT = "fontWeight";
String   kKEY_TYPOGRAPHY_DISPLAY2_LINE_HEIGHT = "lineHeight";
String   kKEY_TYPOGRAPHY_DISPLAY2_MARGIN_LEFT = "marginLeft";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_DISPLAY2_COLOR       = "rgba(0, 0, 0, 0.54)";
String[] kVAL_TYPOGRAPHY_DISPLAY2_FONT_FAMILY = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_DISPLAY2_FONT_SIZE   = "2.8125rem";
int      kVAL_TYPOGRAPHY_DISPLAY2_FONT_WEIGHT = 400;
String   kVAL_TYPOGRAPHY_DISPLAY2_LINE_HEIGHT = "1.13333em";
String   kVAL_TYPOGRAPHY_DISPLAY2_MARGIN_LEFT = "-.02em";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IDisplay2 defaultInstance()
{
   return(new Display2());
}
}//====================================// end IDisplay2 ======================//
/*==============================================================================

name:       IDisplay3 - typography display3 interface

purpose:    IDisplay3 caption interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IDisplay3 extends ITypoMarginLeftLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_DISPLAY3_COLOR          = "color";
String   kKEY_TYPOGRAPHY_DISPLAY3_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_DISPLAY3_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_DISPLAY3_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_DISPLAY3_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_DISPLAY3_LINE_HEIGHT    = "lineHeight";
String   kKEY_TYPOGRAPHY_DISPLAY3_MARGIN_LEFT    = "marginLeft";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_DISPLAY3_COLOR          = "rgba(0, 0, 0, 0.54)";
String[] kVAL_TYPOGRAPHY_DISPLAY3_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_DISPLAY3_FONT_SIZE      = "3.5rem";
int      kVAL_TYPOGRAPHY_DISPLAY3_FONT_WEIGHT    = 400;
String   kVAL_TYPOGRAPHY_DISPLAY3_LETTER_SPACING = "-.02em";
String   kVAL_TYPOGRAPHY_DISPLAY3_LINE_HEIGHT    = "1.30357em";
String   kVAL_TYPOGRAPHY_DISPLAY3_MARGIN_LEFT    = "-.02em";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IDisplay3 defaultInstance()
{
   return(new Display3());
}
}//====================================// end IDisplay3 ======================//
/*==============================================================================

name:       IDisplay4 - typography display4 interface

purpose:    IDisplay4 caption interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IDisplay4 extends ITypoMarginLeftLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_DISPLAY4_COLOR          = "color";
String   kKEY_TYPOGRAPHY_DISPLAY4_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_DISPLAY4_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_DISPLAY4_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_DISPLAY4_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_DISPLAY4_LINE_HEIGHT    = "lineHeight";
String   kKEY_TYPOGRAPHY_DISPLAY4_MARGIN_LEFT    = "marginLeft";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_DISPLAY4_COLOR          = "rgba(0, 0, 0, 0.54)";
String[] kVAL_TYPOGRAPHY_DISPLAY4_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_DISPLAY4_FONT_SIZE      = "7rem";
int      kVAL_TYPOGRAPHY_DISPLAY4_FONT_WEIGHT    = 300;
String   kVAL_TYPOGRAPHY_DISPLAY4_LETTER_SPACING = "-.04em";
String   kVAL_TYPOGRAPHY_DISPLAY4_LINE_HEIGHT    = "1.14286em";
String   kVAL_TYPOGRAPHY_DISPLAY4_MARGIN_LEFT    = "-.04em";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IDisplay4 defaultInstance()
{
   return(new Display4());
}
}//====================================// end IDisplay4 ======================//
/*==============================================================================

name:       IH1 - typography h1 interface

purpose:    TypoBase h1 interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IH1 extends ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_H1_COLOR          = "color";
String   kKEY_TYPOGRAPHY_H1_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_H1_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_H1_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_H1_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_H1_LINE_HEIGHT    = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_H1_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_H1_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_H1_FONT_SIZE      = "6rem";
int      kVAL_TYPOGRAPHY_H1_FONT_WEIGHT    = 300;
String   kVAL_TYPOGRAPHY_H1_LETTER_SPACING = "-0.01562em";
double   kVAL_TYPOGRAPHY_H1_LINE_HEIGHT    = 1;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IH1 defaultInstance()
{
   return(new H1());
}
}//====================================// end IH1 ============================//
/*==============================================================================

name:       IH2 - typography h2 interface

purpose:    TypoBase h2 interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IH2 extends ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_H2_COLOR          = "color";
String   kKEY_TYPOGRAPHY_H2_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_H2_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_H2_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_H2_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_H2_LINE_HEIGHT    = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_H2_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_H2_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_H2_FONT_SIZE      = "3.75rem";
int      kVAL_TYPOGRAPHY_H2_FONT_WEIGHT    = 300;
String   kVAL_TYPOGRAPHY_H2_LETTER_SPACING = "-0.00833em";
double   kVAL_TYPOGRAPHY_H2_LINE_HEIGHT    = 1;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IH2 defaultInstance()
{
   return(new H2());
}
}//====================================// end IH2 ============================//
/*==============================================================================

name:       IH3 - typography h3 interface

purpose:    TypoBase h3 interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IH3 extends ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_H3_COLOR          = "color";
String   kKEY_TYPOGRAPHY_H3_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_H3_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_H3_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_H3_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_H3_LINE_HEIGHT    = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_H3_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_H3_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_H3_FONT_SIZE      = "3rem";
int      kVAL_TYPOGRAPHY_H3_FONT_WEIGHT    = 400;
String   kVAL_TYPOGRAPHY_H3_LETTER_SPACING = "0em";
double   kVAL_TYPOGRAPHY_H3_LINE_HEIGHT    = 1.04;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IH3 defaultInstance()
{
   return(new H3());
}
}//====================================// end IH3 ============================//
/*==============================================================================

name:       IH4 - typography h4 interface

purpose:    TypoBase h4 interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IH4 extends ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_H4_COLOR          = "color";
String   kKEY_TYPOGRAPHY_H4_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_H4_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_H4_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_H4_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_H4_LINE_HEIGHT    = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_H4_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_H4_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_H4_FONT_SIZE      = "2.125rem";
int      kVAL_TYPOGRAPHY_H4_FONT_WEIGHT    = 400;
String   kVAL_TYPOGRAPHY_H4_LETTER_SPACING = "0.00735em";
double   kVAL_TYPOGRAPHY_H4_LINE_HEIGHT    = 1.17;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IH4 defaultInstance()
{
   return(new H4());
}
}//====================================// end IH4 ============================//
/*==============================================================================

name:       IH5 - typography h5 interface

purpose:    TypoBase h5 interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IH5 extends ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_H5_COLOR          = "color";
String   kKEY_TYPOGRAPHY_H5_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_H5_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_H5_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_H5_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_H5_LINE_HEIGHT    = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_H5_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_H5_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_H5_FONT_SIZE      = "1.5rem";
int      kVAL_TYPOGRAPHY_H5_FONT_WEIGHT    = 400;
String   kVAL_TYPOGRAPHY_H5_LETTER_SPACING = "0em";
double   kVAL_TYPOGRAPHY_H5_LINE_HEIGHT    = 1.33;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IH5 defaultInstance()
{
   return(new H5());
}
}//====================================// end IH5 ============================//
/*==============================================================================

name:       IH6 - typography h6 interface

purpose:    TypoBase h6 interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IH6 extends ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_H6_COLOR          = "color";
String   kKEY_TYPOGRAPHY_H6_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_H6_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_H6_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_H6_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_H6_LINE_HEIGHT    = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_H6_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_H6_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_H6_FONT_SIZE      = "1.25rem";
int      kVAL_TYPOGRAPHY_H6_FONT_WEIGHT    = 500;
String   kVAL_TYPOGRAPHY_H6_LETTER_SPACING = "0.0075em";
double   kVAL_TYPOGRAPHY_H6_LINE_HEIGHT    = 1.6;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IH6 defaultInstance()
{
   return(new H6());
}
}//====================================// end IH6 ============================//
/*==============================================================================

name:       IHeadline - typography headline interface

purpose:    TypoBase headline interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IHeadline extends ITypoBase
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_HEADLINE_COLOR       = "color";
String   kKEY_TYPOGRAPHY_HEADLINE_FONT_FAMILY = "fontFamily";
String   kKEY_TYPOGRAPHY_HEADLINE_FONT_SIZE   = "fontSize";
String   kKEY_TYPOGRAPHY_HEADLINE_FONT_WEIGHT = "fontWeight";
String   kKEY_TYPOGRAPHY_HEADLINE_LINE_HEIGHT = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_HEADLINE_COLOR       = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_HEADLINE_FONT_FAMILY = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_HEADLINE_FONT_SIZE   = "1.5rem";
int      kVAL_TYPOGRAPHY_HEADLINE_FONT_WEIGHT = 400;
String   kVAL_TYPOGRAPHY_HEADLINE_LINE_HEIGHT = "1.35417em";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IHeadline defaultInstance()
{
   return(new Headline());
}
}//====================================// end IHeadline ======================//
/*==============================================================================

name:       IOverline - typography overline interface

purpose:    TypoBase overline interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IOverline extends ITypoLetterSpacingTextTransform
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_OVERLINE_COLOR          = "color";
String   kKEY_TYPOGRAPHY_OVERLINE_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_OVERLINE_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_OVERLINE_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_OVERLINE_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_OVERLINE_LINE_HEIGHT    = "lineHeight";
String   kKEY_TYPOGRAPHY_OVERLINE_TEXT_TRANSFORM = "textTransform";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_OVERLINE_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_OVERLINE_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_OVERLINE_FONT_SIZE      = "0.75rem";
int      kVAL_TYPOGRAPHY_OVERLINE_FONT_WEIGHT    = 400;
String   kVAL_TYPOGRAPHY_OVERLINE_LETTER_SPACING = "0.08333em";
double   kVAL_TYPOGRAPHY_OVERLINE_LINE_HEIGHT    = 2.66;
String   kVAL_TYPOGRAPHY_OVERLINE_TEXT_TRANSFORM = "uppercase";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IOverline defaultInstance()
{
   return(new Overline());
}
}//====================================// end IOverline ======================//
/*==============================================================================

name:       ISubheading - typography subheading interface

purpose:    ISubheading subheading interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ISubheading extends ITypoBase
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_SUBHEADING_COLOR       = "color";
String   kKEY_TYPOGRAPHY_SUBHEADING_FONT_FAMILY = "fontFamily";
String   kKEY_TYPOGRAPHY_SUBHEADING_FONT_SIZE   = "fontSize";
String   kKEY_TYPOGRAPHY_SUBHEADING_FONT_WEIGHT = "fontWeight";
String   kKEY_TYPOGRAPHY_SUBHEADING_LINE_HEIGHT = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_SUBHEADING_COLOR       = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_SUBHEADING_FONT_FAMILY = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_SUBHEADING_FONT_SIZE   = "1rem";
int      kVAL_TYPOGRAPHY_SUBHEADING_FONT_WEIGHT = 400;
String   kVAL_TYPOGRAPHY_SUBHEADING_LINE_HEIGHT = "1.5em";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static ISubheading defaultInstance()
{
   return(new Subheading());
}
}//====================================// end ISubheading ====================//
/*==============================================================================

name:       ISubtitle1 - typography subtitle1 interface

purpose:    TypoBase subtitle1 interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ISubtitle1 extends ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_SUBTITLE_1_COLOR          = "color";
String   kKEY_TYPOGRAPHY_SUBTITLE_1_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_SUBTITLE_1_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_SUBTITLE_1_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_SUBTITLE_1_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_SUBTITLE_1_LINE_HEIGHT    = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_SUBTITLE_1_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_SUBTITLE_1_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_SUBTITLE_1_FONT_SIZE      = "1rem";
int      kVAL_TYPOGRAPHY_SUBTITLE_1_FONT_WEIGHT    = 400;
String   kVAL_TYPOGRAPHY_SUBTITLE_1_LETTER_SPACING = "0.00938em";
double   kVAL_TYPOGRAPHY_SUBTITLE_1_LINE_HEIGHT    = 1.75;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static ISubtitle1 defaultInstance()
{
   return(new Subtitle1());
}
}//====================================// end ISubtitle1 =====================//
/*==============================================================================

name:       ISubtitle2 - typography subtitle3 interface

purpose:    TypoBase subtitle3 interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ISubtitle2 extends ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_SUBTITLE_2_COLOR          = "color";
String   kKEY_TYPOGRAPHY_SUBTITLE_2_FONT_FAMILY    = "fontFamily";
String   kKEY_TYPOGRAPHY_SUBTITLE_2_FONT_SIZE      = "fontSize";
String   kKEY_TYPOGRAPHY_SUBTITLE_2_FONT_WEIGHT    = "fontWeight";
String   kKEY_TYPOGRAPHY_SUBTITLE_2_LETTER_SPACING = "letterSpacing";
String   kKEY_TYPOGRAPHY_SUBTITLE_2_LINE_HEIGHT    = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_SUBTITLE_2_COLOR          = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_SUBTITLE_2_FONT_FAMILY    = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_SUBTITLE_2_FONT_SIZE      = "0.875rem";
int      kVAL_TYPOGRAPHY_SUBTITLE_2_FONT_WEIGHT    = 500;
String   kVAL_TYPOGRAPHY_SUBTITLE_2_LETTER_SPACING = "0.00714em";
double   kVAL_TYPOGRAPHY_SUBTITLE_2_LINE_HEIGHT    = 1.57;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static ISubtitle2 defaultInstance()
{
   return(new Subtitle2());
}
}//====================================// end ISubtitle2 =====================//
/*==============================================================================

name:       ITitle - typography title interface

purpose:    ITitle caption interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ITitle extends ITypoBase
{
                                       // constants ------------------------- //
                                       // keys                                //
String   kKEY_TYPOGRAPHY_TITLE_COLOR       = "color";
String   kKEY_TYPOGRAPHY_TITLE_FONT_FAMILY = "fontFamily";
String   kKEY_TYPOGRAPHY_TITLE_FONT_SIZE   = "fontSize";
String   kKEY_TYPOGRAPHY_TITLE_FONT_WEIGHT = "fontWeight";
String   kKEY_TYPOGRAPHY_TITLE_LINE_HEIGHT = "lineHeight";

                                       // default values                      //
String   kVAL_TYPOGRAPHY_TITLE_COLOR       = "rgba(0, 0, 0, 0.87)";
String[] kVAL_TYPOGRAPHY_TITLE_FONT_FAMILY = {"Roboto", "Helvetica", "Arial", "sans-serif"};
String   kVAL_TYPOGRAPHY_TITLE_FONT_SIZE   = "1.3125rem";
int      kVAL_TYPOGRAPHY_TITLE_FONT_WEIGHT = 500;
String   kVAL_TYPOGRAPHY_TITLE_LINE_HEIGHT = "1.16667em";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static ITitle defaultInstance()
{
   return(new Title());
}
}//====================================// end ITitle =========================//
/*==============================================================================

name:       ITypoBase - typography1 interface

purpose:    TypoBase interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ITypoBase
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       getColor - get color
                                                                              */
                                                                             /**
            Get color.

@return     color

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getColor();

/*------------------------------------------------------------------------------

@name       getFontFamily - get font family
                                                                              */
                                                                             /**
            Get font family.

@return     font family

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String[] getFontFamily();

/*------------------------------------------------------------------------------

@name       getFontSize - get font size
                                                                              */
                                                                             /**
            Get font size.

@return     font size

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getFontSize();

/*------------------------------------------------------------------------------

@name       getFontWeight - get font weight
                                                                              */
                                                                             /**
            Get font weight.

@return     font weight

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getFontWeight();

/*------------------------------------------------------------------------------

@name       getLineHeight - get line height
                                                                              */
                                                                             /**
            Get line height.

@return     line height

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getLineHeight();

}//====================================// end ITypoBase ======================//
/*==============================================================================

name:       ITypoMarginLeft - typography2 interface

purpose:    TypoMarginLeft interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ITypoMarginLeft extends ITypoBase
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       getMarginLeft - get left margin
                                                                              */
                                                                             /**
            Get left margin.

@return     left margin

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getMarginLeft();

}//====================================// end ITypoMarginLeft ================//
/*==============================================================================

name:       ITypoLetterSpacing - typography3 interface

purpose:    ITypoLetterSpacing interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ITypoLetterSpacing extends ITypoBase
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       getLetterSpacing - get letter spacing
                                                                              */
                                                                             /**
            Get letter spacing.

@return     letter spacing

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getLetterSpacing();

}//====================================// end ITypoLetterSpacing =============//
/*==============================================================================

name:       ITypoMarginLeftLetterSpacing - typography4 interface

purpose:    ITypoMarginLeftLetterSpacing interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ITypoMarginLeftLetterSpacing
   extends ITypoMarginLeft, ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

}//====================================// end ITypoMarginLeftLetterSpacing ===//
/*==============================================================================

name:       ITypoLetterSpacingTextTransform - typography4 interface

purpose:    ITypoLetterSpacingTextTransform interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface ITypoLetterSpacingTextTransform extends ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       getTextTransform - get text transform
                                                                              */
                                                                             /**
            Get text transform.

@return     text transform

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getTextTransform();

}//====================================// end ITypoLetterSpacingTextTransform //
}//====================================// end ITypography ====================//
/*==============================================================================

name:       IZIndex - z index interface

purpose:    Z index interface

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
interface IZIndex
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       assignSharedInstance - get default instance
                                                                              */
                                                                             /**
            Get default instance.

@return     default instance

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static IZIndex defaultInstance()
{
   return(new ZIndex());
}
/*------------------------------------------------------------------------------

@name       getAppBar - get app bar
                                                                              */
                                                                             /**
            Get app bar.

@return     app bar

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getAppBar();

/*------------------------------------------------------------------------------

@name       getDrawer - get drawer
                                                                              */
                                                                             /**
            Get drawer.

@return     drawer

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getDrawer();

/*------------------------------------------------------------------------------

@name       getMobileStepper - get mobile stepper
                                                                              */
                                                                             /**
            Get mobile stepper.

@return     mobile stepper

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getMobileStepper();

/*------------------------------------------------------------------------------

@name       getModal - get modal
                                                                              */
                                                                             /**
            Get modal.

@return     modal

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getModal();

/*------------------------------------------------------------------------------

@name       getSnackbar - get snackbar
                                                                              */
                                                                             /**
            Get snackbar.

@return     snackbar

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getSnackbar();

/*------------------------------------------------------------------------------

@name       getTooltip - get tooltip
                                                                              */
                                                                             /**
            Get tooltip.

@return     tooltip

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
int getTooltip();

}//====================================// end IZIndex ========================//
/*==============================================================================

name:       Mixins - mixins

purpose:    Mixins default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
class Mixins extends NativeObject implements IMixins
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Mixins - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Mixins()
{
}
/*------------------------------------------------------------------------------

@name       getGutters - get gutters
                                                                              */
                                                                             /**
            Get gutters.

@return     gutters function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Function getGutters()
{
   return((Function)get(kKEY_MIXINS_GUTTERS));
}
/*------------------------------------------------------------------------------

@name       getToolbar - get toolbar
                                                                              */
                                                                             /**
            Get toolbar.

@return     toolbar

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IToolbar getToolbar()
{
   return((IToolbar)get(kKEY_MIXINS_TOOLBAR));
}
/*==============================================================================

name:       Toolbar - mixins

purpose:    Toolbar default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Toolbar extends NativeObject implements IToolbar
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Toolbar - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Toolbar()
{
   set(kKEY_MIXINS_TOOLBAR_MIN_HEIGHT, kVAL_MIXINS_TOOLBAR_MIN_HEIGHT);
}
/*------------------------------------------------------------------------------

@name       getMinHeight - get min height
                                                                              */
                                                                             /**
            Get min height.

@return     min height

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getMinHeight()
{
   return(getInt(kKEY_MIXINS_TOOLBAR_MIN_HEIGHT));
}
}//====================================// end Toolbar ========================//
}//====================================// end Mixins =========================//
/*==============================================================================

name:       Palette - palette

purpose:    Palette default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
class Palette extends NativeObject implements IPalette
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Palette - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Palette()
{
   set(kKEY_PALETTE_ACTION,                IAction.defaultInstance());
   set(kKEY_PALETTE_BACKGROUND,            IBackground.defaultInstance());
   set(kKEY_PALETTE_COMMON,                ICommon.defaultInstance());
   set(kKEY_PALETTE_CONTRAST_THRESHOLD,    kVAL_PALETTE_CONTRAST_THRESHOLD);
   set(kKEY_PALETTE_DIVIDER,               kVAL_PALETTE_DIVIDER);
   set(kKEY_PALETTE_ERROR,                 IError.defaultInstance());
   set(kKEY_PALETTE_FCN_AUGMENT_COLOR,     kVAL_PALETTE_FCN_AUGMENT_COLOR);
   set(kKEY_PALETTE_FCN_GET_CONTRAST_TEXT, kVAL_PALETTE_FCN_GET_CONTRAST_TEXT);
   set(kKEY_PALETTE_GREY,                  IGreys.defaultInstance());
   set(kKEY_PALETTE_PRIMARY,               IPrimary.defaultInstance());
   set(kKEY_PALETTE_PROPS,                 kVAL_PALETTE_PROPS);
   set(kKEY_PALETTE_SECONDARY,             ISecondary.defaultInstance());
   set(kKEY_PALETTE_TEXT,                  IText.defaultInstance());
   set(kKEY_PALETTE_TONAL_OFFSET,          kVAL_PALETTE_TONAL_OFFSET);
   set(kKEY_PALETTE_TYPE,                  kVAL_PALETTE_TYPE);
}
/*------------------------------------------------------------------------------

@name       getAction - get action
                                                                              */
                                                                             /**
            Get action.

@return     action

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IAction getAction()
{
   return((IAction)get(kKEY_PALETTE_ACTION));
}
/*------------------------------------------------------------------------------

@name       getBackground - get background
                                                                              */
                                                                             /**
            Get background.

@return     background

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IBackground getBackground()
{
   return((IBackground)get(kKEY_PALETTE_BACKGROUND));
}
/*------------------------------------------------------------------------------

@name       getCommon - get common
                                                                              */
                                                                             /**
            Get common.

@return     common

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ICommon getCommon()
{
   return((ICommon)get(kKEY_PALETTE_COMMON));
}
/*------------------------------------------------------------------------------

@name       getContrastThreshold - get contrast threshold
                                                                              */
                                                                             /**
            Get contrast threshold.

@return     contrast threshold

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getContrastThreshold()
{
   return(getInt(kKEY_PALETTE_CONTRAST_THRESHOLD));
}
/*------------------------------------------------------------------------------

@name       getDivider - get divider
                                                                              */
                                                                             /**
            Get divider.

@return     divider

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getDivider()
{
   return((String)get(kKEY_PALETTE_DIVIDER));
}
/*------------------------------------------------------------------------------

@name       getError - get error colors
                                                                              */
                                                                             /**
            Get error colors.

@return     error colors

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IError getError()
{
   return((IError)get(kKEY_PALETTE_ERROR));
}
/*------------------------------------------------------------------------------

@name       getFcnAugmentColor - get augment color function
                                                                              */
                                                                             /**
            Get caugment color function.

@return     augment color function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Function getFcnAugmentColor()
{
   return((Function)get(kKEY_PALETTE_FCN_AUGMENT_COLOR));
}
/*------------------------------------------------------------------------------

@name       getFcnGetContrastText - get contrast text function
                                                                              */
                                                                             /**
            Get contrast text function.

@return     contrast text function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Function getFcnGetContrastText()
{
   return((Function)get(kKEY_PALETTE_FCN_GET_CONTRAST_TEXT));
}
/*------------------------------------------------------------------------------

@name       getGrey - get grey
                                                                              */
                                                                             /**
            Get grey.

@return     grey

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IGreys getGrey()
{
   return((IGreys)get(kKEY_PALETTE_GREY));
}
/*------------------------------------------------------------------------------

@name       getPrimary - get primary colors
                                                                              */
                                                                             /**
            Get primary colors.

@return     primary colors

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IColors getPrimary()
{
   return((IColors)get(kKEY_PALETTE_PRIMARY));
}
/*------------------------------------------------------------------------------

@name       getProps - get props
                                                                              */
                                                                             /**
            Get props.

@return     props

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public NativeObject getProps()
{
   return((NativeObject)get(kKEY_PALETTE_PROPS));
}
/*------------------------------------------------------------------------------

@name       getSecondary - get secondary colors
                                                                              */
                                                                             /**
            Get secondary colors.

@return     secondary colors

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IColors getSecondary()
{
   return((IColors)get(kKEY_PALETTE_SECONDARY));
}
/*------------------------------------------------------------------------------

@name       getText - get text colors
                                                                              */
                                                                             /**
            Get text colors.

@return     text colors

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IColors getText()
{
   return((IColors)get(kKEY_PALETTE_TEXT));
}
/*------------------------------------------------------------------------------

@name       getTonalOffset - get tonal offset
                                                                              */
                                                                             /**
            Get tonal offset.

@return     tonal offset

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public double getTonalOffset()
{
   return((Double)get(kKEY_PALETTE_TONAL_OFFSET));
}
/*------------------------------------------------------------------------------

@name       getType - get type
                                                                              */
                                                                             /**
            Get type.

@return     type

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getType()
{
   return((String)get(kKEY_PALETTE_TYPE));
}
/*==============================================================================

name:       Action - palette

purpose:    Action default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Action extends NativeObject implements IAction
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Action - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Action()
{
   set(kKEY_PALETTE_ACTION_ACTIVE,              kVAL_PALETTE_ACTION_ACTIVE);
   set(kKEY_PALETTE_ACTION_DISABLED,            kVAL_PALETTE_ACTION_DISABLED);
   set(kKEY_PALETTE_ACTION_DISABLED_BACKGROUND, kVAL_PALETTE_ACTION_DISABLED_BACKGROUND);
   set(kKEY_PALETTE_ACTION_HOVER,               kVAL_PALETTE_ACTION_HOVER);
   set(kKEY_PALETTE_ACTION_HOVER_OPACITY,       kVAL_PALETTE_ACTION_HOVER_OPACITY);
   set(kKEY_PALETTE_ACTION_SELECTED,            kVAL_PALETTE_ACTION_SELECTED);
}
/*------------------------------------------------------------------------------

@name       getActive - get palette active action
                                                                              */
                                                                             /**
            Get palette active action.

@return     palette active action

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getActive()
{
   return((String)get(kKEY_PALETTE_ACTION_ACTIVE));
}
/*------------------------------------------------------------------------------

@name       getDisabled - get palette disabled action
                                                                              */
                                                                             /**
            Get palette disabled action.

@return     palette disabled action

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getDisabled()
{
   return((String)get(kKEY_PALETTE_ACTION_DISABLED));
}
/*------------------------------------------------------------------------------

@name       getDisabledBackground - get palette disabled background action
                                                                              */
                                                                             /**
            Get palette disabled background action.

@return     palette disabled background action

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getDisabledBackground()
{
   return((String)get(kKEY_PALETTE_ACTION_DISABLED_BACKGROUND));
}
/*------------------------------------------------------------------------------

@name       getHover - get palette hover action
                                                                              */
                                                                             /**
            Get palette hover action.

@return     palette hover action

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getHover()
{
   return((String)get(kKEY_PALETTE_ACTION_HOVER));
}
/*------------------------------------------------------------------------------

@name       getHoverOpacity - get palette hover opacity action
                                                                              */
                                                                             /**
            Get palette hover opacity action.

@return     palette hover opacity action

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public double getHoverOpacity()
{
   return((Double)get(kKEY_PALETTE_ACTION_HOVER_OPACITY));
}
/*------------------------------------------------------------------------------

@name       getSelected - get palette selected action
                                                                              */
                                                                             /**
            Get palette selected action.

@return     palette selected action

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getSelected()
{
   return((String)get(kKEY_PALETTE_ACTION_SELECTED));
}
}//====================================// end Action =========================//
/*==============================================================================

name:       Background - palette

purpose:    Background default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Background extends NativeObject implements IBackground
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Background - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Background()
{
   set(kKEY_PALETTE_BACKGROUND_DEFAULT, kVAL_PALETTE_BACKGROUND_DEFAULT);
   set(kKEY_PALETTE_BACKGROUND_PAPER,   kVAL_PALETTE_BACKGROUND_PAPER);
}
/*------------------------------------------------------------------------------

@name       getDefault - get palette background default
                                                                              */
                                                                             /**
            Get palette background default.

@return     palette background default

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getDefault()
{
   return((String)get(kKEY_PALETTE_BACKGROUND_DEFAULT));
}
/*------------------------------------------------------------------------------

@name       getPaper - get palette background paper
                                                                              */
                                                                             /**
            Get palette background paper.

@return     palette background paper

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getPaper()
{
   return((String)get(kKEY_PALETTE_BACKGROUND_PAPER));
}
}//====================================// end Background =====================//
/*==============================================================================

name:       Colors - palette colors

purpose:    Colors default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Colors extends NativeObject implements IColors
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Colors - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Colors()
{
}
/*------------------------------------------------------------------------------

@name       getLight - get palette colors light
                                                                              */
                                                                             /**
            Get palette common colors light.

@return     palette common colors light

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getLight()
{
   return((String)get(kKEY_PALETTE_COLORS_LIGHT));
}
/*------------------------------------------------------------------------------

@name       getDark - get palette colors dark
                                                                              */
                                                                             /**
            Get palette common colors dark.

@return     palette common colors dark

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getDark()
{
   return((String)get(kKEY_PALETTE_COLORS_DARK));
}
/*------------------------------------------------------------------------------

@name       getMain - get palette colors main
                                                                              */
                                                                             /**
            Get palette common colors main.

@return     palette common colors main

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getMain()
{
   return((String)get(kKEY_PALETTE_COLORS_MAIN));
}
/*------------------------------------------------------------------------------

@name       getContrastText - get palette colors contrast text
                                                                              */
                                                                             /**
            Get palette common colors contrast text.

@return     palette common colors contrast text

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getContrastText()
{
   return((String)get(kKEY_PALETTE_COLORS_CONTRAST_TEXT));
}
}//====================================// end Colors =========================//
/*==============================================================================

name:       Common - palette

purpose:    Common default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Common extends NativeObject implements ICommon
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Common - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Common()
{
   set(kKEY_PALETTE_COMMON_BLACK, kVAL_PALETTE_COMMON_BLACK);
   set(kKEY_PALETTE_COMMON_WHITE, kVAL_PALETTE_COMMON_WHITE);
}
/*------------------------------------------------------------------------------

@name       getBlack - get palette common black
                                                                              */
                                                                             /**
            Get palette common black.

@return     palette common black

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getBlack()
{
   return((String)get(kKEY_PALETTE_COMMON_BLACK));
}
/*------------------------------------------------------------------------------

@name       getWhite - get palette common white
                                                                              */
                                                                             /**
            Get palette common white.

@return     palette common white

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getWhite()
{
   return((String)get(kKEY_PALETTE_COMMON_WHITE));
}
}//====================================// end Common =========================//
/*==============================================================================

name:       Error - palette error

purpose:    Error default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Error extends Colors implements IError
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Error - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Error()
{
   set(kKEY_PALETTE_ERROR_LIGHT,         kVAL_PALETTE_ERROR_LIGHT);
   set(kKEY_PALETTE_ERROR_DARK,          kVAL_PALETTE_ERROR_DARK);
   set(kKEY_PALETTE_ERROR_MAIN,          kVAL_PALETTE_ERROR_MAIN);
   set(kKEY_PALETTE_ERROR_CONTRAST_TEXT, kVAL_PALETTE_ERROR_CONTRAST_TEXT);
}
}//====================================// end Error ==========================//
/*==============================================================================

name:       Greys - palette greys

purpose:    Greys default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Greys extends NativeObject implements IGreys
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Greys - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Greys()
{
   set(kKEY_PALETTE_GREY_50,   kVAL_PALETTE_GREY_50);
   set(kKEY_PALETTE_GREY_100,  kVAL_PALETTE_GREY_100);
   set(kKEY_PALETTE_GREY_200,  kVAL_PALETTE_GREY_200);
   set(kKEY_PALETTE_GREY_300,  kVAL_PALETTE_GREY_300);
   set(kKEY_PALETTE_GREY_400,  kVAL_PALETTE_GREY_400);
   set(kKEY_PALETTE_GREY_500,  kVAL_PALETTE_GREY_500);
   set(kKEY_PALETTE_GREY_600,  kVAL_PALETTE_GREY_600);
   set(kKEY_PALETTE_GREY_700,  kVAL_PALETTE_GREY_700);
   set(kKEY_PALETTE_GREY_800,  kVAL_PALETTE_GREY_800);
   set(kKEY_PALETTE_GREY_900,  kVAL_PALETTE_GREY_900);
   set(kKEY_PALETTE_GREY_A100, kVAL_PALETTE_GREY_A100);
   set(kKEY_PALETTE_GREY_A200, kVAL_PALETTE_GREY_A200);
   set(kKEY_PALETTE_GREY_A400, kVAL_PALETTE_GREY_A400);
   set(kKEY_PALETTE_GREY_A700, kVAL_PALETTE_GREY_A700);
}
/*------------------------------------------------------------------------------

@name       get50 - get palette greys 50
                                                                              */
                                                                             /**
            Get palette greys 50.

@return     palette common greys 50

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String get50()
{
   return((String)get(kKEY_PALETTE_GREY_50));
}
/*------------------------------------------------------------------------------

@name       get100 - get palette greys 100
                                                                              */
                                                                             /**
            Get palette greys 100.

@return     palette common greys 100

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String get100()
{
   return((String)get(kKEY_PALETTE_GREY_100));
}
/*------------------------------------------------------------------------------

@name       get200 - get palette greys 200
                                                                              */
                                                                             /**
            Get palette greys 200.

@return     palette common greys 200

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String get200()
{
   return((String)get(kKEY_PALETTE_GREY_200));
}
/*------------------------------------------------------------------------------

@name       get300 - get palette greys 300
                                                                              */
                                                                             /**
            Get palette greys 300.

@return     palette common greys 300

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String get300()
{
   return((String)get(kKEY_PALETTE_GREY_300));
}
/*------------------------------------------------------------------------------

@name       get400 - get palette greys 400
                                                                              */
                                                                             /**
            Get palette greys 400.

@return     palette common greys 400

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String get400()
{
   return((String)get(kKEY_PALETTE_GREY_400));
}
/*------------------------------------------------------------------------------

@name       get500 - get palette greys 500
                                                                              */
                                                                             /**
            Get palette greys 500.

@return     palette common greys 500

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String get500()
{
   return((String)get(kKEY_PALETTE_GREY_500));
}
/*------------------------------------------------------------------------------

@name       get600 - get palette greys 600
                                                                              */
                                                                             /**
            Get palette greys 600.

@return     palette common greys 600

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String get600()
{
   return((String)get(kKEY_PALETTE_GREY_600));
}
/*------------------------------------------------------------------------------

@name       get700 - get palette greys 700
                                                                              */
                                                                             /**
            Get palette greys 700.

@return     palette common greys 700

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String get700()
{
   return((String)get(kKEY_PALETTE_GREY_700));
}
/*------------------------------------------------------------------------------

@name       get800 - get palette greys 800
                                                                              */
                                                                             /**
            Get palette greys 800.

@return     palette common greys 800

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String get800()
{
   return((String)get(kKEY_PALETTE_GREY_800));
}
/*------------------------------------------------------------------------------

@name       get900 - get palette greys 900
                                                                              */
                                                                             /**
            Get palette greys 900.

@return     palette common greys 900

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String get900()
{
   return((String)get(kKEY_PALETTE_GREY_900));
}
/*------------------------------------------------------------------------------

@name       getA100 - get palette greys A100
                                                                              */
                                                                             /**
            Get palette greys A100.

@return     palette common greys A100

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getA100()
{
   return((String)get(kKEY_PALETTE_GREY_A100));
}
/*------------------------------------------------------------------------------

@name       getA200 - get palette greys A200
                                                                              */
                                                                             /**
            Get palette greys A200.

@return     palette common greys A200

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getA200()
{
   return((String)get(kKEY_PALETTE_GREY_A200));
}
/*------------------------------------------------------------------------------

@name       getA400 - get palette greys A400
                                                                              */
                                                                             /**
            Get palette greys A400.

@return     palette common greys A400

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getA400()
{
   return((String)get(kKEY_PALETTE_GREY_A400));
}
/*------------------------------------------------------------------------------

@name       getA700 - get palette greys A700
                                                                              */
                                                                             /**
            Get palette greys A700.

@return     palette common greys A700

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getA700()
{
   return((String)get(kKEY_PALETTE_GREY_A700));
}
}//====================================// end Greys ==========================//
/*==============================================================================

name:       Primary - palette primary

purpose:    Primary default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Primary extends Colors implements IPrimary
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Primary - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Primary()
{
   set(kKEY_PALETTE_PRIMARY_LIGHT,         kVAL_PALETTE_PRIMARY_LIGHT);
   set(kKEY_PALETTE_PRIMARY_DARK,          kVAL_PALETTE_PRIMARY_DARK);
   set(kKEY_PALETTE_PRIMARY_MAIN,          kVAL_PALETTE_PRIMARY_MAIN);
   set(kKEY_PALETTE_PRIMARY_CONTRAST_TEXT, kVAL_PALETTE_PRIMARY_CONTRAST_TEXT);
}
}//====================================// end Primary ========================//
/*==============================================================================

name:       Secondary - palette secondary

purpose:    Secondary default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Secondary extends Colors implements ISecondary
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Secondary - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Secondary()
{
   set(kKEY_PALETTE_SECONDARY_LIGHT,         kVAL_PALETTE_SECONDARY_LIGHT);
   set(kKEY_PALETTE_SECONDARY_DARK,          kVAL_PALETTE_SECONDARY_DARK);
   set(kKEY_PALETTE_SECONDARY_MAIN,          kVAL_PALETTE_SECONDARY_MAIN);
   set(kKEY_PALETTE_SECONDARY_CONTRAST_TEXT, kVAL_PALETTE_SECONDARY_CONTRAST_TEXT);
}
}//====================================// end Secondary ======================//
/*==============================================================================

name:       Text - palette text

purpose:    Text default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Text extends Colors implements IText
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Text - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Text()
{
   set(kKEY_PALETTE_TEXT_DISABLED,  kVAL_PALETTE_TEXT_DISABLED);
   set(kKEY_PALETTE_TEXT_HINT,      kVAL_PALETTE_TEXT_HINT);
   set(kKEY_PALETTE_TEXT_PRIMARY,   kVAL_PALETTE_TEXT_PRIMARY);
   set(kKEY_PALETTE_TEXT_SECONDARY, kVAL_PALETTE_TEXT_SECONDARY);
}
/*------------------------------------------------------------------------------

@name       getDisabled - palette text colors disabled
                                                                              */
                                                                             /**
            Get palette text colors disabled.

@return     palette text colors disabled

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getDisabled()
{
   return((String)get(kKEY_PALETTE_TEXT_DISABLED));
}
/*------------------------------------------------------------------------------

@name       getHint - palette text colors hint
                                                                              */
                                                                             /**
            Get palette text colors hint.

@return     palette text colors hint

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getHint()
{
   return((String)get(kKEY_PALETTE_TEXT_HINT));
}
/*------------------------------------------------------------------------------

@name       getPrimary - palette text colors primary
                                                                              */
                                                                             /**
            Get palette text colors primary.

@return     palette text colors primary

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getPrimary()
{
   return((String)get(kKEY_PALETTE_TEXT_PRIMARY));
}
/*------------------------------------------------------------------------------

@name       getSecondary - palette text colors secondary
                                                                              */
                                                                             /**
            Get palette text colors secondary.

@return     palette text colors secondary

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getSecondary()
{
   return((String)get(kKEY_PALETTE_TEXT_SECONDARY));
}
}//====================================// end Text ===========================//
}//====================================// end Palette ========================//
/*==============================================================================

name:       Shadows - shadows

purpose:    Shadows default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
class Shadows extends NativeObject implements IShadows
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Shadows - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Shadows()
{
   set(kKEY_SHADOWS, new ArrayList(Arrays.asList(kSHADOWS_DEFAULT)));
}
/*------------------------------------------------------------------------------

@name       getShadows - get shadows
                                                                              */
                                                                             /**
            Get shadows.

@return     shadows

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public List<String> getShadows()
{
   return((List<String>)get(kKEY_SHADOWS));
}
}//====================================// end Shadows ========================//
/*==============================================================================

name:       Shape - shape

purpose:    Shape default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
class Shape extends NativeObject implements IShape
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Shape - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Shape()
{
   set(kKEY_SHAPE_BORDER_RADIUS, kVAL_SHAPE_BORDER_RADIUS);
}
/*------------------------------------------------------------------------------

@name       getBorderRadius - get border radius
                                                                              */
                                                                             /**
            Get border radius.

@return     border radius

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getBorderRadius()
{
   return(getInt(kKEY_SHAPE_BORDER_RADIUS));
}
}//====================================// end Shape ==========================//
/*==============================================================================

name:       Spacing - spacing

purpose:    Spacing default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
class Spacing extends NativeObject implements ISpacing
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Spacing - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Spacing()
{
   set(kKEY_SPACING_UNIT, kVAL_SPACING_UNIT);
}
/*------------------------------------------------------------------------------

@name       getUnit - get unit
                                                                              */
                                                                             /**
            Get unit.

@return     unit

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getUnit()
{
   return(getInt(kKEY_SPACING_UNIT));
}
}//====================================// end Spacing ========================//
/*==============================================================================

name:       Transitions - transitions

purpose:    Transitions default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Transitions extends NativeObject implements ITransitions
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Transitions - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Transitions()
{
   set(kKEY_TRANSITIONS_DURATION,   IDuration.defaultInstance());
   set(kKEY_TRANSITIONS_EASING,     IEasing.defaultInstance());
   set(kKEY_TRANSITIONS_FCN_GET_AUTO_HEIGHT_DURATION,
                                    kVAL_TRANSITIONS_FCN_GET_AUTO_HEIGHT_DURATION);
   set(kKEY_TRANSITIONS_FCN_CREATE, kVAL_TRANSITIONS_FCN_CREATE);
}
/*------------------------------------------------------------------------------

@name       getDuration - get duration
                                                                              */
                                                                             /**
            Get duration.

@return     duration

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IDuration getDuration()
{
   return((IDuration)get(kKEY_TRANSITIONS_DURATION));
}
/*------------------------------------------------------------------------------

@name       getEasing - get easing
                                                                              */
                                                                             /**
            Get easing.

@return     easing

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IEasing getEasing()
{
   return((IEasing)get(kKEY_TRANSITIONS_EASING));
}
/*------------------------------------------------------------------------------

@name       getFcnAutoHeightDuration - get autoHeightDuration function
                                                                              */
                                                                             /**
            Get autoHeightDuration function.

@return     autoHeightDuration function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Function getFcnAutoHeightDuration()
{
   return((Function)get(kKEY_TRANSITIONS_FCN_GET_AUTO_HEIGHT_DURATION));
}
/*------------------------------------------------------------------------------

@name       getFcnCreate - get create function
                                                                              */
                                                                             /**
            Get create function.

@return     create function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Function getFcnCreate()
{
   return((Function)get(kKEY_TRANSITIONS_FCN_CREATE));
}
/*==============================================================================

name:       Duration - transition duration

purpose:    Duration default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Duration extends NativeObject implements IDuration
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Duration - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Duration()
{
   set(kKEY_TRANSITIONS_DURATION_COMPLEX,  kVAL_TRANSITIONS_DURATION_COMPLEX);
   set(kKEY_TRANSITIONS_DURATION_ENTERING_SCREEN,
                                           kVAL_TRANSITIONS_DURATION_ENTERING_SCREEN);
   set(kKEY_TRANSITIONS_DURATION_LEAVING_SCREEN,
                                           kVAL_TRANSITIONS_DURATION_LEAVING_SCREEN);
   set(kKEY_TRANSITIONS_DURATION_SHORT,    kVAL_TRANSITIONS_DURATION_SHORT);
   set(kKEY_TRANSITIONS_DURATION_SHORTER,  kVAL_TRANSITIONS_DURATION_SHORTER);
   set(kKEY_TRANSITIONS_DURATION_SHORTEST, kVAL_TRANSITIONS_DURATION_SHORTEST);
   set(kKEY_TRANSITIONS_DURATION_STANDARD, kVAL_TRANSITIONS_DURATION_STANDARD);
}
/*------------------------------------------------------------------------------

@name       getComplex - get complex
                                                                              */
                                                                             /**
            Get complex.

@return     complex

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getComplex()
{
   return(getInt(kKEY_TRANSITIONS_DURATION_COMPLEX));
}
/*------------------------------------------------------------------------------

@name       getEnteringScreen - get entering screen
                                                                              */
                                                                             /**
            Get entering screen.

@return     entering screen

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getEnteringScreen()
{
   return(getInt(kKEY_TRANSITIONS_DURATION_ENTERING_SCREEN));
}
/*------------------------------------------------------------------------------

@name       getLeavingScreen - get leaving screen
                                                                              */
                                                                             /**
            Get leaving screen.

@return     leaving screen

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getLeavingScreen()
{
   return(getInt(kKEY_TRANSITIONS_DURATION_LEAVING_SCREEN));
}
/*------------------------------------------------------------------------------

@name       getShort - get short
                                                                              */
                                                                             /**
            Get short.

@return     short

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getShort()
{
   return(getInt(kKEY_TRANSITIONS_DURATION_SHORT));
}
/*------------------------------------------------------------------------------

@name       getShorter - get shorter
                                                                              */
                                                                             /**
            Get shorter.

@return     shorter

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getShorter()
{
   return(getInt(kKEY_TRANSITIONS_DURATION_SHORTER));
}
/*------------------------------------------------------------------------------

@name       getShortest - get shortest
                                                                              */
                                                                             /**
            Get shortest.

@return     shortest

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getShortest()
{
   return(getInt(kKEY_TRANSITIONS_DURATION_SHORTEST));
}
/*------------------------------------------------------------------------------

@name       getStandard - get standard
                                                                              */
                                                                             /**
            Get standard.

@return     standard

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getStandard()
{
   return(getInt(kKEY_TRANSITIONS_DURATION_STANDARD));
}
/*------------------------------------------------------------------------------

@name       setComplex - set complex
                                                                              */
                                                                             /**
            Set complex.

@param      complex     complex

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void setComplex(
   int complex)
{
   set(kKEY_TRANSITIONS_DURATION_COMPLEX, complex);
}
/*------------------------------------------------------------------------------

@name       setEnteringScreen - Set entering screen
                                                                              */
                                                                             /**
            Set entering screen.

@param      enteringScreen    entering screen

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void setEnteringScreen(
   int enteringScreen)
{
   set(kKEY_TRANSITIONS_DURATION_ENTERING_SCREEN, enteringScreen);
}
/*------------------------------------------------------------------------------

@name       setLeavingScreen - set leaving screen
                                                                              */
                                                                             /**
            Set leaving screen.

@param      leavingScreen    leaving screen

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void setLeavingScreen(
   int leavingScreen)
{
   set(kKEY_TRANSITIONS_DURATION_LEAVING_SCREEN, leavingScreen);
}
/*------------------------------------------------------------------------------

@name       setShort - set short
                                                                              */
                                                                             /**
            Set short.

@param      shortVal    short

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void setShort(
   int shortVal)
{
   set(kKEY_TRANSITIONS_DURATION_SHORT, shortVal);
}
/*------------------------------------------------------------------------------

@name       setShorter - set shorter
                                                                              */
                                                                             /**
            Set shorter.

@param      shorter    shorter

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void setShorter(
   int shorter)
{
   set(kKEY_TRANSITIONS_DURATION_SHORTER, shorter);
}
/*------------------------------------------------------------------------------

@name       setShortest - set shortest
                                                                              */
                                                                             /**
            Set shortest.

@param      shortest    shortest

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void setShortest(
   int shortest)
{
   set(kKEY_TRANSITIONS_DURATION_SHORTEST, shortest);
}
/*------------------------------------------------------------------------------

@name       setStandard - set standard
                                                                              */
                                                                             /**
            Set standard.

@param      standard    standard

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void setStandard(
   int standard)
{
   set(kKEY_TRANSITIONS_DURATION_STANDARD, standard);
}
}//====================================// end Duration =======================//
/*==============================================================================

name:       Easing - transition easing

purpose:    Easing default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Easing extends NativeObject implements IEasing
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Easing - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Easing()
{
   set(kKEY_TRANSITIONS_EASE_IN,     kVAL_TRANSITIONS_EASE_IN);
   set(kKEY_TRANSITIONS_EASE_IN_OUT, kVAL_TRANSITIONS_EASE_IN_OUT);
   set(kKEY_TRANSITIONS_EASE_OUT,    kVAL_TRANSITIONS_EASE_OUT);
   set(kKEY_TRANSITIONS_EASE_SHARP,  kVAL_TRANSITIONS_EASE_SHARP);
}
/*------------------------------------------------------------------------------

@name       easeIn - get ease in
                                                                              */
                                                                             /**
            Get ease in.

@return     ease in

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String easeIn()
{
   return((String)get(kKEY_TRANSITIONS_EASE_IN));
}
/*------------------------------------------------------------------------------

@name       easeInOut - get ease inOut
                                                                              */
                                                                             /**
            Get ease inOut.

@return     ease inOut

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String easeInOut()
{
   return((String)get(kKEY_TRANSITIONS_EASE_IN_OUT));
}
/*------------------------------------------------------------------------------

@name       easeOut - get ease out
                                                                              */
                                                                             /**
            Get ease out.

@return     ease out

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String easeOut()
{
   return((String)get(kKEY_TRANSITIONS_EASE_OUT));
}
/*------------------------------------------------------------------------------

@name       sharp - get sharp
                                                                              */
                                                                             /**
            Get sharp.

@return     sharp

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String sharp()
{
   return((String)get(kKEY_TRANSITIONS_EASE_SHARP));
}
}//====================================// end Easing =========================//
}//====================================// end Transitions ====================//
/*==============================================================================

name:       Typography - typography

purpose:    Typography default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Typography extends NativeObject implements ITypography
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Typography - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Typography()
{
   set(kKEY_TYPOGRAPHY_BODY_1,              IBody1.defaultInstance());
   set(kKEY_TYPOGRAPHY_BODY_1_NEXT,         IBody1Next.defaultInstance());
   set(kKEY_TYPOGRAPHY_BODY_2,              IBody2.defaultInstance());
   set(kKEY_TYPOGRAPHY_BODY_2_NEXT,         IBody2Next.defaultInstance());
   set(kKEY_TYPOGRAPHY_BUTTON,              IButton.defaultInstance());
   set(kKEY_TYPOGRAPHY_BUTTON_NEXT,         IButtonNext.defaultInstance());
   set(kKEY_TYPOGRAPHY_CAPTION,             ICaption.defaultInstance());
   set(kKEY_TYPOGRAPHY_CAPTION_NEXT,        ICaptionNext.defaultInstance());
   set(kKEY_TYPOGRAPHY_DISPLAY1,            IDisplay1.defaultInstance());
   set(kKEY_TYPOGRAPHY_DISPLAY2,            IDisplay2.defaultInstance());
   set(kKEY_TYPOGRAPHY_DISPLAY3,            IDisplay3.defaultInstance());
   set(kKEY_TYPOGRAPHY_DISPLAY4,            IDisplay4.defaultInstance());
   set(kKEY_TYPOGRAPHY_FCN_PX_TO_REM,       kVAL_TYPOGRAPHY_FCN_PX_TO_REM);
   set(kKEY_TYPOGRAPHY_FCN_ROUND,           kVAL_TYPOGRAPHY_FCN_ROUND);
   set(kKEY_TYPOGRAPHY_FONT_FAMILY,         kVAL_TYPOGRAPHY_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_FONT_SIZE,           kVAL_TYPOGRAPHY_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_FONT_WEIGHT_LIGHT,   kVAL_TYPOGRAPHY_FONT_WEIGHT_LIGHT);
   set(kKEY_TYPOGRAPHY_FONT_WEIGHT_MEDIUM,  kVAL_TYPOGRAPHY_FONT_WEIGHT_MEDIUM);
   set(kKEY_TYPOGRAPHY_FONT_WEIGHT_REGULAR, kVAL_TYPOGRAPHY_FONT_WEIGHT_REGULAR);
   set(kKEY_TYPOGRAPHY_H1,                  IH1.defaultInstance());
   set(kKEY_TYPOGRAPHY_H2,                  IH2.defaultInstance());
   set(kKEY_TYPOGRAPHY_H3,                  IH3.defaultInstance());
   set(kKEY_TYPOGRAPHY_H4,                  IH4.defaultInstance());
   set(kKEY_TYPOGRAPHY_H5,                  IH5.defaultInstance());
   set(kKEY_TYPOGRAPHY_H6,                  IH6.defaultInstance());
   set(kKEY_TYPOGRAPHY_HEADLINE,            IHeadline.defaultInstance());
   set(kKEY_TYPOGRAPHY_OVERLINE,            IOverline.defaultInstance());
   set(kKEY_TYPOGRAPHY_SUBHEADING,          ISubheading.defaultInstance());
   set(kKEY_TYPOGRAPHY_SUBTITLE_1,          ISubtitle1.defaultInstance());
   set(kKEY_TYPOGRAPHY_SUBTITLE_2,          ISubtitle2.defaultInstance());
   set(kKEY_TYPOGRAPHY_TITLE,               ITitle.defaultInstance());
   set(kKEY_TYPOGRAPHY_USE_NEXT_VARIANTS,   kVAL_TYPOGRAPHY_USE_NEXT_VARIANTS);
}
/*------------------------------------------------------------------------------

@name       getBody1 - get body1
                                                                              */
                                                                             /**
            Get body1.

@return     body1

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IBody1 getBody1()
{
   return((IBody1)get(kKEY_TYPOGRAPHY_BODY_1));
}
/*------------------------------------------------------------------------------

@name       getBody1Next - get body1 next
                                                                              */
                                                                             /**
            Get body1 next.

@return     body1 next

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IBody1Next getBody1Next()
{
   return((IBody1Next)get(kKEY_TYPOGRAPHY_BODY_1_NEXT));
}
/*------------------------------------------------------------------------------

@name       getBody2 - get body2
                                                                              */
                                                                             /**
            Get body2.

@return     body2

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IBody2 getBody2()
{
   return((IBody2)get(kKEY_TYPOGRAPHY_BODY_2));
}
/*------------------------------------------------------------------------------

@name       getBody2Next - get body2 next
                                                                              */
                                                                             /**
            Get body2 next.

@return     body2 next

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IBody2Next getBody2Next()
{
   return((IBody2Next)get(kKEY_TYPOGRAPHY_BODY_2_NEXT));
}
/*------------------------------------------------------------------------------

@name       getButton - get button
                                                                              */
                                                                             /**
            Get button.

@return     button

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IButton getButton()
{
   return((IButton)get(kKEY_TYPOGRAPHY_BUTTON));
}
/*------------------------------------------------------------------------------

@name       getButtonNext - get button next
                                                                              */
                                                                             /**
            Get button next.

@return     button next

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IButtonNext getButtonNext()
{
   return((IButtonNext)get(kKEY_TYPOGRAPHY_BUTTON_NEXT));
}
/*------------------------------------------------------------------------------

@name       getCaption - get caption
                                                                              */
                                                                             /**
            Get caption.

@return     caption

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ICaption getCaption()
{
   return((ICaption)get(kKEY_TYPOGRAPHY_CAPTION));
}
/*------------------------------------------------------------------------------

@name       getCaptionNext - get caption next
                                                                              */
                                                                             /**
            Get caption next.

@return     caption next

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ICaptionNext getCaptionNext()
{
   return((ICaptionNext)get(kKEY_TYPOGRAPHY_CAPTION_NEXT));
}
/*------------------------------------------------------------------------------

@name       getDisplay1 - get display1
                                                                              */
                                                                             /**
            Get display1.

@return     display1

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IDisplay1 getDisplay1()
{
   return((IDisplay1)get(kKEY_TYPOGRAPHY_DISPLAY1));
}
/*------------------------------------------------------------------------------

@name       getDisplay2 - get display2
                                                                              */
                                                                             /**
            Get display2.

@return     display2

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IDisplay2 getDisplay2()
{
   return((IDisplay2)get(kKEY_TYPOGRAPHY_DISPLAY2));
}
/*------------------------------------------------------------------------------

@name       getDisplay3 - get display3
                                                                              */
                                                                             /**
            Get display3.

@return     display3

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IDisplay3 getDisplay3()
{
   return((IDisplay3)get(kKEY_TYPOGRAPHY_DISPLAY3));
}
/*------------------------------------------------------------------------------

@name       getDisplay4 - get display4
                                                                              */
                                                                             /**
            Get display4.

@return     display4

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IDisplay4 getDisplay4()
{
   return((IDisplay4)get(kKEY_TYPOGRAPHY_DISPLAY4));
}
/*------------------------------------------------------------------------------

@name       getFcnPxToRem - get pxToRem function
                                                                              */
                                                                             /**
            Get pxToRem function.

@return     pxToRem function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Function getFcnPxToRem()
{
   return((Function)get(kKEY_TYPOGRAPHY_FCN_PX_TO_REM));
}
/*------------------------------------------------------------------------------

@name       getFcnRound - get round function
                                                                              */
                                                                             /**
            Get round function.

@return     round function

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Function getFcnRound()
{
   return((Function)get(kKEY_TYPOGRAPHY_FCN_ROUND));
}
/*------------------------------------------------------------------------------

@name       getFontFamily - get font family
                                                                              */
                                                                             /**
            Get font family.

@return     font family

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String[] getFontFamily()
{
   return((String[])get(kKEY_TYPOGRAPHY_FONT_FAMILY));
}
/*------------------------------------------------------------------------------

@name       getFontSize - get font size
                                                                              */
                                                                             /**
            Get font size.

@return     font size

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getFontSize()
{
   return((String)get(kKEY_TYPOGRAPHY_FONT_SIZE));
}
/*------------------------------------------------------------------------------

@name       getFontWeightLight - get font weight light
                                                                              */
                                                                             /**
            Get font weight light.

@return     font weight light

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getFontWeightLight()
{
   return(getInt(kKEY_TYPOGRAPHY_FONT_WEIGHT_LIGHT));
}
/*------------------------------------------------------------------------------

@name       getFontWeightMedium - get font weight medium
                                                                              */
                                                                             /**
            Get font weight medium.

@return     font weight medium

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getFontWeightMedium()
{
   return(getInt(kKEY_TYPOGRAPHY_FONT_WEIGHT_MEDIUM));
}
/*------------------------------------------------------------------------------

@name       getFontWeightRegular - get font weight regular
                                                                              */
                                                                             /**
            Get font weight regular.

@return     font weight regular

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getFontWeightRegular()
{
   return(getInt(kKEY_TYPOGRAPHY_FONT_WEIGHT_REGULAR));
}
/*------------------------------------------------------------------------------

@name       getH1 - get h1
                                                                              */
                                                                             /**
            Get h1.

@return     h1

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IH1 getH1()
{
   return((IH1)get(kKEY_TYPOGRAPHY_H1));
}
/*------------------------------------------------------------------------------

@name       getH2 - get h2
                                                                              */
                                                                             /**
            Get h2.

@return     h2

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IH2 getH2()
{
   return((IH2)get(kKEY_TYPOGRAPHY_H2));
}
/*------------------------------------------------------------------------------

@name       getH3 - get h3
                                                                              */
                                                                             /**
            Get h3.

@return     h3

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IH3 getH3()
{
   return((IH3)get(kKEY_TYPOGRAPHY_H3));
}
/*------------------------------------------------------------------------------

@name       getH4 - get h4
                                                                              */
                                                                             /**
            Get h4.

@return     h4

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IH4 getH4()
{
   return((IH4)get(kKEY_TYPOGRAPHY_H4));
}
/*------------------------------------------------------------------------------

@name       getH5 - get h5
                                                                              */
                                                                             /**
            Get h5.

@return     h5

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IH5 getH5()
{
   return((IH5)get(kKEY_TYPOGRAPHY_H5));
}
/*------------------------------------------------------------------------------

@name       getH6 - get h6
                                                                              */
                                                                             /**
            Get h6.

@return     h6

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IH6 getH6()
{
   return((IH6)get(kKEY_TYPOGRAPHY_H6));
}
/*------------------------------------------------------------------------------

@name       getHeadline - get headline
                                                                              */
                                                                             /**
            Get headline.

@return     headline

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IHeadline getHeadline()
{
   return((IHeadline)get(kKEY_TYPOGRAPHY_HEADLINE));
}
/*------------------------------------------------------------------------------

@name       getOverline - get overline
                                                                              */
                                                                             /**
            Get overline.

@return     overline

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IOverline getOverline()
{
   return((IOverline)get(kKEY_TYPOGRAPHY_OVERLINE));
}
/*------------------------------------------------------------------------------

@name       getSubheading - get subheading
                                                                              */
                                                                             /**
            Get subheading.

@return     subheading

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ISubheading getSubheading()
{
   return((ISubheading)get(kKEY_TYPOGRAPHY_SUBHEADING));
}
/*------------------------------------------------------------------------------

@name       getSubtitle1 - get subtitle1
                                                                              */
                                                                             /**
            Get subtitle1.

@return     subtitle1

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ISubtitle1 getSubtitle1()
{
   return((ISubtitle1)get(kKEY_TYPOGRAPHY_SUBTITLE_1));
}
/*------------------------------------------------------------------------------

@name       getSubtitle2 - get subtitle2
                                                                              */
                                                                             /**
            Get subtitle2.

@return     subtitle2

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ISubtitle2 getSubtitle2()
{
   return((ISubtitle2)get(kKEY_TYPOGRAPHY_SUBTITLE_2));
}
/*------------------------------------------------------------------------------

@name       getTitle - get title
                                                                              */
                                                                             /**
            Get title.

@return     title

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ITitle getTitle()
{
   return((ITitle)get(kKEY_TYPOGRAPHY_TITLE));
}
/*------------------------------------------------------------------------------

@name       getUseNextVariants - get use next variants
                                                                              */
                                                                             /**
            Get use next variants.

@return     use next variants

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean getUseNextVariants()
{
   return((Boolean)get(kKEY_TYPOGRAPHY_USE_NEXT_VARIANTS));
}
/*==============================================================================

name:       Body1 - typography body1

purpose:    Body1 default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Body1 extends TypoLetterSpacing implements IBody1
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Body1 - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Body1()
{
   set(kKEY_TYPOGRAPHY_BODY_1_COLOR,          kVAL_TYPOGRAPHY_BODY_1_COLOR);
   set(kKEY_TYPOGRAPHY_BODY_1_FONT_FAMILY,    kVAL_TYPOGRAPHY_BODY_1_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_BODY_1_FONT_SIZE,      kVAL_TYPOGRAPHY_BODY_1_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_BODY_1_FONT_WEIGHT,    kVAL_TYPOGRAPHY_BODY_1_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_BODY_1_LINE_HEIGHT,    kVAL_TYPOGRAPHY_BODY_1_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_BODY_1_LETTER_SPACING, kVAL_TYPOGRAPHY_BODY_1_LETTER_SPACING);
}
}//====================================// end Body1 ==========================//
/*==============================================================================

name:       Body1Next - typography body1Next

purpose:    Body1Next default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Body1Next extends TypoLetterSpacing implements IBody1Next
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Body1Next - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Body1Next()
{
   set(kKEY_TYPOGRAPHY_BODY_1_NEXT_COLOR,          kVAL_TYPOGRAPHY_BODY_1_NEXT_COLOR);
   set(kKEY_TYPOGRAPHY_BODY_1_NEXT_FONT_FAMILY,    kVAL_TYPOGRAPHY_BODY_1_NEXT_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_BODY_1_NEXT_FONT_SIZE,      kVAL_TYPOGRAPHY_BODY_1_NEXT_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_BODY_1_NEXT_FONT_WEIGHT,    kVAL_TYPOGRAPHY_BODY_1_NEXT_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_BODY_1_NEXT_LINE_HEIGHT,    kVAL_TYPOGRAPHY_BODY_1_NEXT_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_BODY_1_NEXT_LETTER_SPACING, kVAL_TYPOGRAPHY_BODY_1_NEXT_LETTER_SPACING);
}
}//====================================// end Body1Next ======================//
/*==============================================================================

name:       Body2 - typography body2

purpose:    Body2 default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Body2 extends TypoLetterSpacing implements IBody2
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Body2 - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Body2()
{
   set(kKEY_TYPOGRAPHY_BODY_2_COLOR,          kVAL_TYPOGRAPHY_BODY_2_COLOR);
   set(kKEY_TYPOGRAPHY_BODY_2_FONT_FAMILY,    kVAL_TYPOGRAPHY_BODY_2_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_BODY_2_FONT_SIZE,      kVAL_TYPOGRAPHY_BODY_2_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_BODY_2_FONT_WEIGHT,    kVAL_TYPOGRAPHY_BODY_2_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_BODY_2_LINE_HEIGHT,    kVAL_TYPOGRAPHY_BODY_2_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_BODY_2_LETTER_SPACING, kVAL_TYPOGRAPHY_BODY_2_LETTER_SPACING);
}
}//====================================// end Body2 ==========================//
/*==============================================================================

name:       Body2Next - typography body2Next

purpose:    Body2Next default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Body2Next extends TypoLetterSpacing implements IBody2Next
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Body2Next - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Body2Next()
{
   set(kKEY_TYPOGRAPHY_BODY_2_NEXT_COLOR,          kVAL_TYPOGRAPHY_BODY_2_NEXT_COLOR);
   set(kKEY_TYPOGRAPHY_BODY_2_NEXT_FONT_FAMILY,    kVAL_TYPOGRAPHY_BODY_2_NEXT_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_BODY_2_NEXT_FONT_SIZE,      kVAL_TYPOGRAPHY_BODY_2_NEXT_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_BODY_2_NEXT_FONT_WEIGHT,    kVAL_TYPOGRAPHY_BODY_2_NEXT_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_BODY_2_NEXT_LINE_HEIGHT,    kVAL_TYPOGRAPHY_BODY_2_NEXT_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_BODY_2_NEXT_LETTER_SPACING, kVAL_TYPOGRAPHY_BODY_2_NEXT_LETTER_SPACING);
}
}//====================================// end Body2Next ======================//
/*==============================================================================

name:       Button - typography button

purpose:    Button default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Button extends TypoLetterSpacingTextTransform implements IButton
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Button - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Button()
{
   set(kKEY_TYPOGRAPHY_BUTTON_COLOR,          kVAL_TYPOGRAPHY_BUTTON_COLOR);
   set(kKEY_TYPOGRAPHY_BUTTON_FONT_FAMILY,    kVAL_TYPOGRAPHY_BUTTON_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_BUTTON_FONT_SIZE,      kVAL_TYPOGRAPHY_BUTTON_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_BUTTON_FONT_WEIGHT,    kVAL_TYPOGRAPHY_BUTTON_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_BUTTON_LINE_HEIGHT,    kVAL_TYPOGRAPHY_BUTTON_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_BUTTON_LETTER_SPACING, kVAL_TYPOGRAPHY_BUTTON_LETTER_SPACING);
   set(kKEY_TYPOGRAPHY_BUTTON_TEXT_TRANSFORM, kVAL_TYPOGRAPHY_BUTTON_TEXT_TRANSFORM);
}
}//====================================// end Button =========================//
/*==============================================================================

name:       ButtonNext - typography buttonNext

purpose:    ButtonNext default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class ButtonNext extends TypoLetterSpacingTextTransform implements IButtonNext
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       ButtonNext - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ButtonNext()
{
   set(kKEY_TYPOGRAPHY_BUTTON_NEXT_COLOR,          kVAL_TYPOGRAPHY_BUTTON_NEXT_COLOR);
   set(kKEY_TYPOGRAPHY_BUTTON_NEXT_FONT_FAMILY,    kVAL_TYPOGRAPHY_BUTTON_NEXT_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_BUTTON_NEXT_FONT_SIZE,      kVAL_TYPOGRAPHY_BUTTON_NEXT_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_BUTTON_NEXT_FONT_WEIGHT,    kVAL_TYPOGRAPHY_BUTTON_NEXT_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_BUTTON_NEXT_LINE_HEIGHT,    kVAL_TYPOGRAPHY_BUTTON_NEXT_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_BUTTON_NEXT_LETTER_SPACING, kVAL_TYPOGRAPHY_BUTTON_NEXT_LETTER_SPACING);
   set(kKEY_TYPOGRAPHY_BUTTON_NEXT_TEXT_TRANSFORM, kVAL_TYPOGRAPHY_BUTTON_NEXT_TEXT_TRANSFORM);
}
}//====================================// end ButtonNext =====================//
/*==============================================================================

name:       Caption - typography caption

purpose:    Caption default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Caption extends TypoLetterSpacing implements ICaption
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Caption - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Caption()
{
   set(kKEY_TYPOGRAPHY_CAPTION_COLOR,          kVAL_TYPOGRAPHY_CAPTION_COLOR);
   set(kKEY_TYPOGRAPHY_CAPTION_FONT_FAMILY,    kVAL_TYPOGRAPHY_CAPTION_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_CAPTION_FONT_SIZE,      kVAL_TYPOGRAPHY_CAPTION_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_CAPTION_FONT_WEIGHT,    kVAL_TYPOGRAPHY_CAPTION_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_CAPTION_LINE_HEIGHT,    kVAL_TYPOGRAPHY_CAPTION_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_CAPTION_LETTER_SPACING, kVAL_TYPOGRAPHY_CAPTION_LETTER_SPACING);
}
}//====================================// end Caption ========================//
/*==============================================================================

name:       CaptionNext - typography captionNext

purpose:    CaptionNext default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class CaptionNext extends TypoLetterSpacing implements ICaptionNext
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       CaptionNext - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public CaptionNext()
{
   set(kKEY_TYPOGRAPHY_CAPTION_NEXT_COLOR,          kVAL_TYPOGRAPHY_CAPTION_NEXT_COLOR);
   set(kKEY_TYPOGRAPHY_CAPTION_NEXT_FONT_FAMILY,    kVAL_TYPOGRAPHY_CAPTION_NEXT_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_CAPTION_NEXT_FONT_SIZE,      kVAL_TYPOGRAPHY_CAPTION_NEXT_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_CAPTION_NEXT_FONT_WEIGHT,    kVAL_TYPOGRAPHY_CAPTION_NEXT_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_CAPTION_NEXT_LINE_HEIGHT,    kVAL_TYPOGRAPHY_CAPTION_NEXT_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_CAPTION_NEXT_LETTER_SPACING, kVAL_TYPOGRAPHY_CAPTION_NEXT_LETTER_SPACING);
}
}//====================================// end CaptionNext ====================//
/*==============================================================================

name:       Display1 - typography display1

purpose:    Display1 default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Display1 extends TypoBase implements IDisplay1
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Display1 - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Display1()
{
   set(kKEY_TYPOGRAPHY_DISPLAY1_COLOR,       kVAL_TYPOGRAPHY_DISPLAY1_COLOR);
   set(kKEY_TYPOGRAPHY_DISPLAY1_FONT_FAMILY, kVAL_TYPOGRAPHY_DISPLAY1_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_DISPLAY1_FONT_SIZE,   kVAL_TYPOGRAPHY_DISPLAY1_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_DISPLAY1_FONT_WEIGHT, kVAL_TYPOGRAPHY_DISPLAY1_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_DISPLAY1_LINE_HEIGHT, kVAL_TYPOGRAPHY_DISPLAY1_LINE_HEIGHT);
}
}//====================================// end Display1 =======================//
/*==============================================================================

name:       Display2 - typography display2

purpose:    Display2 default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Display2 extends TypoMarginLeft implements IDisplay2
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Display2 - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Display2()
{
   set(kKEY_TYPOGRAPHY_DISPLAY2_COLOR,       kVAL_TYPOGRAPHY_DISPLAY2_COLOR);
   set(kKEY_TYPOGRAPHY_DISPLAY2_FONT_FAMILY, kVAL_TYPOGRAPHY_DISPLAY2_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_DISPLAY2_FONT_SIZE,   kVAL_TYPOGRAPHY_DISPLAY2_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_DISPLAY2_FONT_WEIGHT, kVAL_TYPOGRAPHY_DISPLAY2_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_DISPLAY2_LINE_HEIGHT, kVAL_TYPOGRAPHY_DISPLAY2_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_DISPLAY2_MARGIN_LEFT, kVAL_TYPOGRAPHY_DISPLAY2_MARGIN_LEFT);
}
}//====================================// end Display2 =======================//
/*==============================================================================

name:       Display2 - typography display2

purpose:    Display2 default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Display3
   extends TypoMarginLeftLetterSpacing implements IDisplay3
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Display3 - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Display3()
{
   set(kKEY_TYPOGRAPHY_DISPLAY3_COLOR,          kVAL_TYPOGRAPHY_DISPLAY3_COLOR);
   set(kKEY_TYPOGRAPHY_DISPLAY3_FONT_FAMILY,    kVAL_TYPOGRAPHY_DISPLAY3_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_DISPLAY3_FONT_SIZE,      kVAL_TYPOGRAPHY_DISPLAY3_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_DISPLAY3_FONT_WEIGHT,    kVAL_TYPOGRAPHY_DISPLAY3_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_DISPLAY3_LINE_HEIGHT,    kVAL_TYPOGRAPHY_DISPLAY3_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_DISPLAY3_MARGIN_LEFT,    kVAL_TYPOGRAPHY_DISPLAY3_MARGIN_LEFT);
   set(kKEY_TYPOGRAPHY_DISPLAY3_LETTER_SPACING, kVAL_TYPOGRAPHY_DISPLAY3_LETTER_SPACING);
}
}//====================================// end Display3 =======================//
/*==============================================================================

name:       Display4 - typography display4

purpose:    Display4 default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Display4
   extends TypoMarginLeftLetterSpacing implements IDisplay4
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Display4 - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Display4()
{
   set(kKEY_TYPOGRAPHY_DISPLAY4_COLOR,          kVAL_TYPOGRAPHY_DISPLAY4_COLOR);
   set(kKEY_TYPOGRAPHY_DISPLAY4_FONT_FAMILY,    kVAL_TYPOGRAPHY_DISPLAY4_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_DISPLAY4_FONT_SIZE,      kVAL_TYPOGRAPHY_DISPLAY4_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_DISPLAY4_FONT_WEIGHT,    kVAL_TYPOGRAPHY_DISPLAY4_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_DISPLAY4_LINE_HEIGHT,    kVAL_TYPOGRAPHY_DISPLAY4_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_DISPLAY4_MARGIN_LEFT,    kVAL_TYPOGRAPHY_DISPLAY4_MARGIN_LEFT);
   set(kKEY_TYPOGRAPHY_DISPLAY4_LETTER_SPACING, kVAL_TYPOGRAPHY_DISPLAY4_LETTER_SPACING);
}
}//====================================// end Display4 =======================//
/*==============================================================================

name:       H1 - typography caption

purpose:    H1 default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class H1 extends TypoLetterSpacing implements IH1
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       H1 - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public H1()
{
   set(kKEY_TYPOGRAPHY_H1_COLOR,          kVAL_TYPOGRAPHY_H1_COLOR);
   set(kKEY_TYPOGRAPHY_H1_FONT_FAMILY,    kVAL_TYPOGRAPHY_H1_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_H1_FONT_SIZE,      kVAL_TYPOGRAPHY_H1_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_H1_FONT_WEIGHT,    kVAL_TYPOGRAPHY_H1_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_H1_LINE_HEIGHT,    kVAL_TYPOGRAPHY_H1_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_H1_LETTER_SPACING, kVAL_TYPOGRAPHY_H1_LETTER_SPACING);
}
}//====================================// end H1 =============================//
/*==============================================================================

name:       H2 - typography caption

purpose:    H2 default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class H2 extends TypoLetterSpacing implements IH2
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       H2 - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public H2()
{
   set(kKEY_TYPOGRAPHY_H2_COLOR,          kVAL_TYPOGRAPHY_H2_COLOR);
   set(kKEY_TYPOGRAPHY_H2_FONT_FAMILY,    kVAL_TYPOGRAPHY_H2_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_H2_FONT_SIZE,      kVAL_TYPOGRAPHY_H2_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_H2_FONT_WEIGHT,    kVAL_TYPOGRAPHY_H2_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_H2_LINE_HEIGHT,    kVAL_TYPOGRAPHY_H2_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_H2_LETTER_SPACING, kVAL_TYPOGRAPHY_H2_LETTER_SPACING);
}
}//====================================// end H2 =============================//
/*==============================================================================

name:       H3 - typography caption

purpose:    H3 default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class H3 extends TypoLetterSpacing implements IH3
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       H3 - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public H3()
{
   set(kKEY_TYPOGRAPHY_H3_COLOR,          kVAL_TYPOGRAPHY_H3_COLOR);
   set(kKEY_TYPOGRAPHY_H3_FONT_FAMILY,    kVAL_TYPOGRAPHY_H3_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_H3_FONT_SIZE,      kVAL_TYPOGRAPHY_H3_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_H3_FONT_WEIGHT,    kVAL_TYPOGRAPHY_H3_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_H3_LINE_HEIGHT,    kVAL_TYPOGRAPHY_H3_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_H3_LETTER_SPACING, kVAL_TYPOGRAPHY_H3_LETTER_SPACING);
}
}//====================================// end H3 =============================//
/*==============================================================================

name:       H4 - typography caption

purpose:    H4 default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class H4 extends TypoLetterSpacing implements IH4
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       H4 - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public H4()
{
   set(kKEY_TYPOGRAPHY_H4_COLOR,          kVAL_TYPOGRAPHY_H4_COLOR);
   set(kKEY_TYPOGRAPHY_H4_FONT_FAMILY,    kVAL_TYPOGRAPHY_H4_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_H4_FONT_SIZE,      kVAL_TYPOGRAPHY_H4_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_H4_FONT_WEIGHT,    kVAL_TYPOGRAPHY_H4_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_H4_LINE_HEIGHT,    kVAL_TYPOGRAPHY_H4_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_H4_LETTER_SPACING, kVAL_TYPOGRAPHY_H4_LETTER_SPACING);
}
}//====================================// end H4 =============================//
/*==============================================================================

name:       H5 - typography caption

purpose:    H5 default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class H5 extends TypoLetterSpacing implements IH5
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       H5 - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public H5()
{
   set(kKEY_TYPOGRAPHY_H5_COLOR,          kVAL_TYPOGRAPHY_H5_COLOR);
   set(kKEY_TYPOGRAPHY_H5_FONT_FAMILY,    kVAL_TYPOGRAPHY_H5_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_H5_FONT_SIZE,      kVAL_TYPOGRAPHY_H5_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_H5_FONT_WEIGHT,    kVAL_TYPOGRAPHY_H5_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_H5_LINE_HEIGHT,    kVAL_TYPOGRAPHY_H5_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_H5_LETTER_SPACING, kVAL_TYPOGRAPHY_H5_LETTER_SPACING);
}
}//====================================// end H5 =============================//
/*==============================================================================

name:       H6 - typography caption

purpose:    H6 default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class H6 extends TypoLetterSpacing implements IH6
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       H6 - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public H6()
{
   set(kKEY_TYPOGRAPHY_H6_COLOR,          kVAL_TYPOGRAPHY_H6_COLOR);
   set(kKEY_TYPOGRAPHY_H6_FONT_FAMILY,    kVAL_TYPOGRAPHY_H6_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_H6_FONT_SIZE,      kVAL_TYPOGRAPHY_H6_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_H6_FONT_WEIGHT,    kVAL_TYPOGRAPHY_H6_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_H6_LINE_HEIGHT,    kVAL_TYPOGRAPHY_H6_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_H6_LETTER_SPACING, kVAL_TYPOGRAPHY_H6_LETTER_SPACING);
}
}//====================================// end H6 =============================//
/*==============================================================================

name:       Headline - typography caption

purpose:    Headline default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Headline extends TypoBase implements IHeadline
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Headline - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Headline()
{
   set(kKEY_TYPOGRAPHY_HEADLINE_COLOR,          kVAL_TYPOGRAPHY_HEADLINE_COLOR);
   set(kKEY_TYPOGRAPHY_HEADLINE_FONT_FAMILY,    kVAL_TYPOGRAPHY_HEADLINE_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_HEADLINE_FONT_SIZE,      kVAL_TYPOGRAPHY_HEADLINE_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_HEADLINE_FONT_WEIGHT,    kVAL_TYPOGRAPHY_HEADLINE_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_HEADLINE_LINE_HEIGHT,    kVAL_TYPOGRAPHY_HEADLINE_LINE_HEIGHT);
}
}//====================================// end Headline =======================//
/*==============================================================================

name:       Overline - typography overline

purpose:    Overline default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Overline 
   extends TypoLetterSpacingTextTransform implements IOverline
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Overline - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Overline()
{
   set(kKEY_TYPOGRAPHY_OVERLINE_COLOR,          kVAL_TYPOGRAPHY_OVERLINE_COLOR);
   set(kKEY_TYPOGRAPHY_OVERLINE_FONT_FAMILY,    kVAL_TYPOGRAPHY_OVERLINE_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_OVERLINE_FONT_SIZE,      kVAL_TYPOGRAPHY_OVERLINE_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_OVERLINE_FONT_WEIGHT,    kVAL_TYPOGRAPHY_OVERLINE_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_OVERLINE_LINE_HEIGHT,    kVAL_TYPOGRAPHY_OVERLINE_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_OVERLINE_LETTER_SPACING, kVAL_TYPOGRAPHY_OVERLINE_LETTER_SPACING);
   set(kKEY_TYPOGRAPHY_OVERLINE_TEXT_TRANSFORM, kVAL_TYPOGRAPHY_OVERLINE_TEXT_TRANSFORM);
}
}//====================================// end Overline =======================//
/*==============================================================================

name:       Subheading - typography subheading

purpose:    Subheading default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Subheading extends TypoBase implements ISubheading
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Subheading - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Subheading()
{
   set(kKEY_TYPOGRAPHY_SUBHEADING_COLOR,       kVAL_TYPOGRAPHY_SUBHEADING_COLOR);
   set(kKEY_TYPOGRAPHY_SUBHEADING_FONT_FAMILY, kVAL_TYPOGRAPHY_SUBHEADING_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_SUBHEADING_FONT_SIZE,   kVAL_TYPOGRAPHY_SUBHEADING_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_SUBHEADING_FONT_WEIGHT, kVAL_TYPOGRAPHY_SUBHEADING_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_SUBHEADING_LINE_HEIGHT, kVAL_TYPOGRAPHY_SUBHEADING_LINE_HEIGHT);
}
}//====================================// end Subheading =====================//
/*==============================================================================

name:       Subtitle1 - typography caption

purpose:    Subtitle1 default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Subtitle1 extends TypoLetterSpacing implements ISubtitle1
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Subtitle1 - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Subtitle1()
{
   set(kKEY_TYPOGRAPHY_SUBTITLE_1_COLOR,          kVAL_TYPOGRAPHY_SUBTITLE_1_COLOR);
   set(kKEY_TYPOGRAPHY_SUBTITLE_1_FONT_FAMILY,    kVAL_TYPOGRAPHY_SUBTITLE_1_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_SUBTITLE_1_FONT_SIZE,      kVAL_TYPOGRAPHY_SUBTITLE_1_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_SUBTITLE_1_FONT_WEIGHT,    kVAL_TYPOGRAPHY_SUBTITLE_1_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_SUBTITLE_1_LINE_HEIGHT,    kVAL_TYPOGRAPHY_SUBTITLE_1_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_SUBTITLE_1_LETTER_SPACING, kVAL_TYPOGRAPHY_SUBTITLE_1_LETTER_SPACING);
}
}//====================================// end Subtitle1 ======================//
/*==============================================================================

name:       Subtitle2 - typography subtitle2

purpose:    Subtitle2 default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Subtitle2 extends TypoLetterSpacing implements ISubtitle2
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Subtitle1 - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Subtitle2()
{
   set(kKEY_TYPOGRAPHY_SUBTITLE_2_COLOR,          kVAL_TYPOGRAPHY_SUBTITLE_2_COLOR);
   set(kKEY_TYPOGRAPHY_SUBTITLE_2_FONT_FAMILY,    kVAL_TYPOGRAPHY_SUBTITLE_2_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_SUBTITLE_2_FONT_SIZE,      kVAL_TYPOGRAPHY_SUBTITLE_2_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_SUBTITLE_2_FONT_WEIGHT,    kVAL_TYPOGRAPHY_SUBTITLE_2_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_SUBTITLE_2_LINE_HEIGHT,    kVAL_TYPOGRAPHY_SUBTITLE_2_LINE_HEIGHT);
   set(kKEY_TYPOGRAPHY_SUBTITLE_2_LETTER_SPACING, kVAL_TYPOGRAPHY_SUBTITLE_2_LETTER_SPACING);
}
}//====================================// end Subtitle2 ======================//
/*==============================================================================

name:       Title - typography display1

purpose:    Title default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Title extends TypoBase implements ITitle
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Title - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Title()
{
   set(kKEY_TYPOGRAPHY_TITLE_COLOR,       kVAL_TYPOGRAPHY_TITLE_COLOR);
   set(kKEY_TYPOGRAPHY_TITLE_FONT_FAMILY, kVAL_TYPOGRAPHY_TITLE_FONT_FAMILY);
   set(kKEY_TYPOGRAPHY_TITLE_FONT_SIZE,   kVAL_TYPOGRAPHY_TITLE_FONT_SIZE);
   set(kKEY_TYPOGRAPHY_TITLE_FONT_WEIGHT, kVAL_TYPOGRAPHY_TITLE_FONT_WEIGHT);
   set(kKEY_TYPOGRAPHY_TITLE_LINE_HEIGHT, kVAL_TYPOGRAPHY_TITLE_LINE_HEIGHT);
}
}//====================================// end Title ==========================//
/*==============================================================================

name:       TypoBase - typography1

purpose:    TypoBase default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class TypoBase extends NativeObject implements ITypoBase
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       TypoBase - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public TypoBase()
{
}
/*------------------------------------------------------------------------------

@name       getColor - get color
                                                                              */
                                                                             /**
            Get color.

@return     color

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getColor()
{
   return((String)get(kKEY_TYPOGRAPHY_COLOR));
}
/*------------------------------------------------------------------------------

@name       getFontFamily - get font family
                                                                              */
                                                                             /**
            Get font family.

@return     font family

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String[] getFontFamily()
{
   return((String[])get(kKEY_TYPOGRAPHY_FONT_FAMILY));
}
/*------------------------------------------------------------------------------

@name       getFontSize - get font size
                                                                              */
                                                                             /**
            Get font size.

@return     font size

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getFontSize()
{
   return((String)get(kKEY_TYPOGRAPHY_FONT_SIZE));
}
/*------------------------------------------------------------------------------

@name       getFontWeight - get font weight
                                                                              */
                                                                             /**
            Get font weight.

@return     font weight

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getFontWeight()
{
   return(getInt(kKEY_TYPOGRAPHY_FONT_WEIGHT));
}
/*------------------------------------------------------------------------------

@name       getLineHeight - get line height
                                                                              */
                                                                             /**
            Get line height.

@return     line height

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getLineHeight()
{
   return((String)get(kKEY_TYPOGRAPHY_LINE_HEIGHT));
}
}//====================================// end TypoBase ====================//
/*==============================================================================

name:       TypoMarginLeft - typography2

purpose:    TypoMarginLeft default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class TypoMarginLeft extends TypoBase implements ITypoMarginLeft
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       TypoMarginLeft - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public TypoMarginLeft()
{
}
/*------------------------------------------------------------------------------

@name       getMarginLeft - get left margin
                                                                              */
                                                                             /**
            Get left margin.

@return     left margin

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getMarginLeft()
{
   return((String)get(kKEY_TYPOGRAPHY_MARGIN_LEFT));
}
}//====================================// end TypoMarginLeft ====================//
/*==============================================================================

name:       TypoLetterSpacing - typography3

purpose:    TypoLetterSpacing default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class TypoLetterSpacing extends TypoBase implements ITypoLetterSpacing
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       TypoLetterSpacing - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public TypoLetterSpacing()
{
}
/*------------------------------------------------------------------------------

@name       getLetterSpacing - get letter spacing
                                                                              */
                                                                             /**
            Get letter spacing.

@return     letter spacing

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getLetterSpacing()
{
   return((String)get(kKEY_TYPOGRAPHY_LETTER_SPACING));
}
}//====================================// end TypoLetterSpacing ==============//
/*==============================================================================

name:       TypoMarginLeftLetterSpacing - typography4

purpose:    TypoMarginLeftLetterSpacing default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class TypoMarginLeftLetterSpacing
   extends TypoMarginLeft implements ITypoMarginLeftLetterSpacing
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       TypoMarginLeftLetterSpacing - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public TypoMarginLeftLetterSpacing()
{
}
/*------------------------------------------------------------------------------

@name       getLetterSpacing - get letter spacing
                                                                              */
                                                                             /**
            Get letter spacing.

@return     letter spacing

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getLetterSpacing()
{
   return((String)get(kKEY_TYPOGRAPHY_LETTER_SPACING));
}
}//====================================// end TypoMarginLeftLetterSpacing ====//
/*==============================================================================

name:       TypoLetterSpacingTextTransform - typography5

purpose:    TypoLetterSpacingTextTransform default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class TypoLetterSpacingTextTransform
   extends TypoLetterSpacing implements ITypoLetterSpacingTextTransform
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       TypoLetterSpacingTextTransform - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public TypoLetterSpacingTextTransform()
{
}
/*------------------------------------------------------------------------------

@name       getTextTransform - get text transform
                                                                              */
                                                                             /**
            Get text transform.

@return     text transform

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getTextTransform()
{
   return((String)get(kKEY_TYPOGRAPHY_TEXT_TRANSFORM));
}
}//====================================// end TypoLetterSpacingTextTransform =//
}//====================================// end Typography =====================//
/*==============================================================================

name:       ZIndex - z index

purpose:    ZIndex default instance

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class ZIndex extends NativeObject implements IZIndex
{
                                       // constants ------------------------- //
String kKEY_Z_INDEX_MOBILE_APP_BAR  = "appBar";
String kKEY_Z_INDEX_MOBILE_DRAWER   = "drawer";
String kKEY_Z_INDEX_MOBILE_STEPPER  = "mobileStepper";
String kKEY_Z_INDEX_MOBILE_MODAL    = "modal";
String kKEY_Z_INDEX_MOBILE_SNACKBAR = "snackbar";
String kKEY_Z_INDEX_MOBILE_TOOLTIP  = "tooltip";

int    kVAL_Z_INDEX_MOBILE_APP_BAR  = 1100;
int    kVAL_Z_INDEX_MOBILE_DRAWER   = 1200;
int    kVAL_Z_INDEX_MOBILE_STEPPER  = 1000;
int    kVAL_Z_INDEX_MOBILE_MODAL    = 1300;
int    kVAL_Z_INDEX_MOBILE_SNACKBAR = 1400;
int    kVAL_Z_INDEX_MOBILE_TOOLTIP  = 1500;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       ZIndex - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ZIndex()
{
   set(kKEY_Z_INDEX_MOBILE_APP_BAR,  kVAL_Z_INDEX_MOBILE_APP_BAR);
   set(kKEY_Z_INDEX_MOBILE_DRAWER,   kVAL_Z_INDEX_MOBILE_DRAWER);
   set(kKEY_Z_INDEX_MOBILE_STEPPER,  kVAL_Z_INDEX_MOBILE_STEPPER);
   set(kKEY_Z_INDEX_MOBILE_MODAL,    kVAL_Z_INDEX_MOBILE_MODAL);
   set(kKEY_Z_INDEX_MOBILE_SNACKBAR, kVAL_Z_INDEX_MOBILE_SNACKBAR);
   set(kKEY_Z_INDEX_MOBILE_TOOLTIP,  kVAL_Z_INDEX_MOBILE_TOOLTIP);
}
/*------------------------------------------------------------------------------

@name       getAppBar - get app bar
                                                                              */
                                                                             /**
            Get app bar.

@return     app bar

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getAppBar()
{
   return(getInt(kKEY_Z_INDEX_MOBILE_APP_BAR));
}
/*------------------------------------------------------------------------------

@name       getDrawer - get drawer
                                                                              */
                                                                             /**
            Get drawer.

@return     drawer

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getDrawer()
{
   return(getInt(kKEY_Z_INDEX_MOBILE_DRAWER));
}
/*------------------------------------------------------------------------------

@name       getMobileStepper - get mobile stepper
                                                                              */
                                                                             /**
            Get mobile stepper.

@return     mobile stepper

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getMobileStepper()
{
   return(getInt(kKEY_Z_INDEX_MOBILE_STEPPER));
}
/*------------------------------------------------------------------------------

@name       getModal - get modal
                                                                              */
                                                                             /**
            Get modal.

@return     modal

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getModal()
{
   return(getInt(kKEY_Z_INDEX_MOBILE_MODAL));
}
/*------------------------------------------------------------------------------

@name       getSnackbar - get snackbar
                                                                              */
                                                                             /**
            Get snackbar.

@return     snackbar

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getSnackbar()
{
   return(getInt(kKEY_Z_INDEX_MOBILE_SNACKBAR));
}
/*------------------------------------------------------------------------------

@name       getTooltip - get tooltip
                                                                              */
                                                                             /**
            Get tooltip.

@return     tooltip

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public int getTooltip()
{
   return(getInt(kKEY_Z_INDEX_MOBILE_TOOLTIP));
}
}//====================================// end ZIndex =========================//
}//====================================// end IUITheme =======================//
