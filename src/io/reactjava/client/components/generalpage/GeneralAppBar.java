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
import elemental2.dom.Element;
import elemental2.dom.Event;
import io.reactjava.client.components.generalpage.Descriptors.AppBarDsc;
import io.reactjava.client.components.generalpage.Descriptors.ButtonDsc;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.INativeEventHandler;
import java.util.HashMap;
import java.util.Map;
                                       // GeneralAppBar ======================//
public class GeneralAppBar extends Component
{
                                       // class constants ------------------- //
public static final String kPROPERTY_KEY_APP_BAR_DSC  = "appbardsc";
public static final String kAPP_BAR_BUTTON_TEXT_LOGIN = "Login";
public static final String kAPP_BAR_BUTTON_TEXT_MENU  = "MenuButton";

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
protected AppBarDsc          appBarDsc;// app bar descriptor                  //
protected Map<String,String> buttonMap;// map of button url by text           //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       clickHandler - onClick event handler
                                                                              */
                                                                             /**
            onClick event handler as a public instance variable, accessible in
            markup.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler clickHandler = (Event e) ->
{
   AppBarDsc dsc = getAppBarDsc();
   if (dsc.openHandler != null)
   {
                                       // the element handling the click      //
                                       // (not necessarily the root target)   //
      String id  = ((Element)e.currentTarget).getAttribute("id");
      String url = getButtonMap().get(id);

      dsc.openHandler.accept(
         new HashMap<String,Object>()
         {{
            put("id",    id);
            put("url",   url);
            put("bOpen", kAPP_BAR_BUTTON_TEXT_MENU.equals(id));
         }});
   }
};
/*------------------------------------------------------------------------------

@name       getAppBarDsc - get app bar descriptor
                                                                              */
                                                                             /**
            Get app bar descriptor.

@return     app bar descriptor

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected AppBarDsc getAppBarDsc()
{
   if (appBarDsc == null)
   {
      appBarDsc = (AppBarDsc)props().get(kPROPERTY_KEY_APP_BAR_DSC);
   }
   return(appBarDsc);
}
/*------------------------------------------------------------------------------

@name       getButtonMap - get map of button url by text
                                                                              */
                                                                             /**
            Get map of button url by text.

@return     map of button url by text

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected Map<String,String> getButtonMap()
{
   if (buttonMap == null)
   {
      buttonMap = new HashMap<>();
      for (ButtonDsc dsc : getAppBarDsc().buttonDscs)
      {
         buttonMap.put(dsc.text, dsc.url);
      }
      String title = getAppBarDsc().title;
      if (buttonMap.get(title) == null)
      {
                                       // handle the title                    //
         buttonMap.put(title, "path:");
      }
   }
   return(buttonMap);
}
/*------------------------------------------------------------------------------

@name       render - render component
                                                                              */
                                                                             /**
            Render component.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void render()
{
/*--
   <@material-ui.core.AppBar position="fixed" color="default" class="appBar">
      <@material-ui.core.Toolbar
         disableGutters={getAppBarDsc().bOpen ? true : null}>
--*/
   if (getAppBarDsc().bMenuButton)
   {
/*--
         <@material-ui.core.IconButton
            color="inherit"
            id={kAPP_BAR_BUTTON_TEXT_MENU}
            onClick={clickHandler}
            class="menuButton"
         >
            <@material-ui.icons.Menu />
         </@material-ui.core.IconButton>
--*/
   }
/*--
         <div
            id={getAppBarDsc().title}
            onClick={clickHandler}
            class="toolbarTitle"
         >
            <@material-ui.core.Typography variant="h6" color="inherit" noWrap>
               {getAppBarDsc().title}
            </@material-ui.core.Typography>
         </div>
--*/
   for (ButtonDsc buttonDsc : getAppBarDsc().buttonDscs)
   {
      String color =
         kAPP_BAR_BUTTON_TEXT_LOGIN.equals(buttonDsc.text) ? "primary" : null;

      String variant =
         kAPP_BAR_BUTTON_TEXT_LOGIN.equals(buttonDsc.text) ? "outlined" : null;
/*--
                                       <!-- each App Bar  button -------------->
         <@material-ui.core.Button id={buttonDsc.text} onClick={clickHandler}
            color={color} variant={variant}>
            {buttonDsc.text}
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
