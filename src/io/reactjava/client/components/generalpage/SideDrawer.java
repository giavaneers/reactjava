/*==============================================================================

name:       SideDrawer.java

purpose:    ReactJava website side Drawer.

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
import io.reactjava.client.components.generalpage.ContentPage.ContentDsc;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.INativeEventHandler;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
                                          // SideDrawer ======================//
public class SideDrawer extends Component
{
                                       // class constants ------------------- //
public static final String   kPROPERTY_KEY_CONTENT      = "content";
public static final String   kPROPERTY_KEY_0PEN         = "open";
public static final String   kPROPERTY_KEY_0PEN_HANDLER = "openhandler";

public static final String[] kMENU_LIST =
{
   "Inbox", "Starred", "Send email", "Drafts", "All mail", "Trash", "Spam"
};
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       clickHandler - cell onClick event handler
                                                                              */
                                                                             /**
            Cell onClick event handler as a public instance variable, accessible
            via 'this' in markup.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler clickHandler = (Event e) ->
{
   ((Consumer)props().get(kPROPERTY_KEY_0PEN_HANDLER)).accept(
      new HashMap<String,Object>()
      {{
         put("id", ((Element)e.currentTarget).getAttribute("id"));
         put("bOpen", false);
      }});
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
public void render()
{
   boolean          bOpen   = props().getBoolean(kPROPERTY_KEY_0PEN);
   Object           theme   = props().get("theme");
   List<ContentDsc> content =
      (List<ContentDsc>)props().get(kPROPERTY_KEY_CONTENT);
/*--
   <@material-ui.core.Drawer open={bOpen} theme={theme}>
      <div tabIndex={0} role="button">
         <div class="list">
            <@material-ui.core.List>
--*/
            if (content != null)
            {
               for (int iDsc = 0; iDsc < content.size(); iDsc++)
               {
                  ContentDsc dsc = content.get(iDsc);

                  if (dsc.type != ContentDsc.kTYPE_TITLE
                        && dsc.type != ContentDsc.kTYPE_CAPTION
                        && dsc.type != ContentDsc.kTYPE_REFERENCE)
                  {
                     continue;
                  }
                  if (ContentDsc.kTYPE_REFERENCE == dsc.type)
                  {
                     if (iDsc > 0 && content.get(iDsc - 1).type != dsc.type)
                     {
                     /*-- <@material-ui.core.Divider /> --*/
                     }
                  }
/*--              <@material-ui.core.ListItem
                     button key={dsc.text} id={dsc.id} onClick={clickHandler}>
                     <@material-ui.core.ListItemText primary={dsc.text} />
                  </@material-ui.core.ListItem>
--*/
                  if (ContentDsc.kTYPE_REFERENCE == dsc.type)
                  {
                     /*-- <@material-ui.core.Divider /> --*/
                  }
               }
            }
            else
            {
                                       // save this as an example of how to   //
                                       // add dividers and icons              //
               for (int idx = 0; idx <  kMENU_LIST.length; idx++)
               {
                  if (idx == 4)
                  {
                     /*-- <@material-ui.core.Divider /> --*/
                  }
                  String text = kMENU_LIST[idx];

   /*--           <@material-ui.core.ListItem button key={text}>
                     <@material-ui.core.ListItemIcon>

   --*/              if (idx % 2 == 0){/*-- <@material-ui.icons.Inbox /> --*/}
                     else             {/*-- <@material-ui.icons.Mail />  --*/}
   /*--
                     </@material-ui.core.ListItemIcon>
                     <@material-ui.core.ListItemText primary={text} />
                  </@material-ui.core.ListItem>
   --*/        }
            }
/*--
            </@material-ui.core.List>
         </div>
      </div>
   </@material-ui.core.Drawer>
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
/*--
   .list
   {
      width: 300px;
   }
--*/
}
}//====================================// end SideDrawer =====================//
