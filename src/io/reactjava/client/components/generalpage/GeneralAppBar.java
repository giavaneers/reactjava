/*==============================================================================

name:       GeneralAppBar.java

purpose:    ReactJava website general app bar.

history:    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

notes:

                  This program was created by Giavaneers
        and is the confidential and proprietary product of Giavaneers Inc.
      Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2019 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.


==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.generalpage;
                                       // imports --------------------------- //
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.Event;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.INativeEventHandler;
import java.util.HashMap;
import java.util.function.Consumer;
                                       // GeneralAppBar ======================//
public class GeneralAppBar extends Component
{
                                       // class constants ------------------- //
public static final String   kPROPERTY_KEY_0PEN               = "open";
public static final String   kPROPERTY_KEY_0PEN_HANDLER       = "openHandler";

public static final String   kAPP_BAR_BUTTON_TEXT_COMMUNITY   = "Community";
public static final String   kAPP_BAR_BUTTON_TEXT_DOCS        = "Docs";
public static final String   kAPP_BAR_BUTTON_TEXT_GITHUB      = "GitHub";
public static final String   kAPP_BAR_BUTTON_TEXT_LOGIN       = "Login";
public static final String   kAPP_BAR_BUTTON_TEXT_MENU_BUTTON = "MenuButton";
public static final String   kAPP_BAR_BUTTON_TEXT_REACTJAVA   = "ReactJava";
public static final String   kAPP_BAR_BUTTON_TEXT_TUTORIAL    = "Tutorial";
public static final String[] kAPP_BAR_BUTTON_TEXT =
{
                                       // order of appearance                 //
   kAPP_BAR_BUTTON_TEXT_DOCS,
   kAPP_BAR_BUTTON_TEXT_TUTORIAL,
   kAPP_BAR_BUTTON_TEXT_COMMUNITY,
   kAPP_BAR_BUTTON_TEXT_GITHUB,
   kAPP_BAR_BUTTON_TEXT_LOGIN
};

public static final String kGITHUB_URL = "https://github.com/giavaneers";

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       clickHandler - onClick event handler
                                                                              */
                                                                             /**
            onClick event handler as a public instance variable, accessible in
            markup.

@return     void

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler clickHandler = (Event e) ->
{
                                       // the element handling the click      //
                                       // (not necessarily the root target)   //
   switch(((Element)e.currentTarget).getAttribute("id"))
   {
      case kAPP_BAR_BUTTON_TEXT_COMMUNITY:
      {
         break;
      }
      case kAPP_BAR_BUTTON_TEXT_DOCS:
      {
         break;
      }
      case kAPP_BAR_BUTTON_TEXT_GITHUB:
      {
         DomGlobal.window.open(kGITHUB_URL, "_blank");
         break;
      }
      case kAPP_BAR_BUTTON_TEXT_LOGIN:
      {
         break;
      }
      case kAPP_BAR_BUTTON_TEXT_MENU_BUTTON:
      {
         ((Consumer)props().get(kPROPERTY_KEY_0PEN_HANDLER)).accept(
            new HashMap<String,Object>()
            {{
               put("bOpen", true);
            }});
         break;
      }
      case kAPP_BAR_BUTTON_TEXT_REACTJAVA:
      {
         break;
      }
      case kAPP_BAR_BUTTON_TEXT_TUTORIAL:
      {
         break;
      }
   }
};
/*------------------------------------------------------------------------------

@name       render - render component
                                                                              */
                                                                             /**
            Render component.

@return     void

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void render()
{
   boolean bOpen = props().getBoolean(kPROPERTY_KEY_0PEN);
/*--
   <@material-ui.core.AppBar position="fixed" color="default" class="appBar">
      <@material-ui.core.Toolbar disableGutters={!bOpen}>
         <@material-ui.core.IconButton
            color="inherit"
            id={kAPP_BAR_BUTTON_TEXT_MENU_BUTTON}
            onClick={clickHandler}
            class="menuButton"
         >
            <@material-ui.icons.Menu />
         </@material-ui.core.IconButton>
         <@material-ui.core.Typography
            variant="h6" color="inherit" noWrap class="toolbarTitle"
            id={kAPP_BAR_BUTTON_TEXT_REACTJAVA}
            onClick={clickHandler}>
            {kAPP_BAR_BUTTON_TEXT_REACTJAVA}
         </@material-ui.core.Typography>
--*/
      for (String text : kAPP_BAR_BUTTON_TEXT)
      {
         String color =
            kAPP_BAR_BUTTON_TEXT_LOGIN.equals(text) ? "primary" : null;

         String variant =
            kAPP_BAR_BUTTON_TEXT_LOGIN.equals(text) ? "outlined" : null;
/*--
                                       <!-- each App Bar  button -------------->
         <@material-ui.core.Button id={text} onClick={clickHandler}
            color={color} variant={variant}>
            {text}
         </@material-ui.core.Button>
--*/
         }
/*--
      </@material-ui.core.Toolbar>
   </@material-ui.core.AppBar>
--*/
}
/*------------------------------------------------------------------------------

@name       renderCSS - get component css
                                                                              */
                                                                             /**
            Get component css.

@return     void

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
   String h6FontSize = getTheme().getTypography().getH6().getFontSize();
   String unit       = "" + getTheme().getSpacing().getUnit() + "px";
/*--
   .appBar
   {
      background-color: #95beff;
      z-index:          {getTheme().getZIndex().getDrawer() + 1};
   }
   .hide:
   {
      display: none;
   }
   .menuButton:
   {
      margin-left:  12px;
      margin-right: 20px;
   }
   .toolbarIcon
   {
      margin-right: {unit};
      height:       {h6FontSize};
   }
   .toolbarTitle
   {
      flex:   1;
      cursor: grab;
   }
--*/
}
}//====================================// end GeneralAppBar ==================//
