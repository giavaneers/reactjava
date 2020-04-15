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
import io.reactjava.client.components.pdfviewer.Bookmark;
import io.reactjava.client.components.pdfviewer.PDFViewer;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.INativeEventHandler;
import io.reactjava.client.core.react.INativeFunction;
import io.reactjava.client.core.react.INativeFunction1Arg;
import io.reactjava.client.core.react.NativeObject;
import io.reactjava.client.core.rxjs.functions.Action1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
                                          // SideDrawer ======================//
public class SideDrawer extends Component
{
                                       // class constants --------------------//
public static final String kPROPERTY_KEY_CONTENT      = "content";
public static final String kPROPERTY_KEY_OPEN_HANDLER = "openhandler";

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
   setState("show", false);

   String   id       = ((Element)e.currentTarget).getAttribute("id");
   Bookmark bookmark = Bookmark.bookmarkById.get(id);
   if (bookmark != null)
   {
      id = bookmark.toURL();
   }

   Map<String,Object> handlerArgs = new HashMap<>();
   handlerArgs. put("id", id);

   ((Consumer)props().get(kPROPERTY_KEY_OPEN_HANDLER)).accept(handlerArgs);
};
/*------------------------------------------------------------------------------

@name       getBookmarks - get any pdf document bookmarks
                                                                              */
                                                                             /**
            Get any pdf document bookmarks.

@return     any pdf document bookmarks

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected List<List<Bookmark>> getBookmarks()
{
   List<List<Bookmark>> bookmarks = (List<List<Bookmark>>)getState("bookmarks");
   if (bookmarks == null)
   {
      PDFViewer.getInstance().subscribe(
         this,
         (PDFViewer pdfViewer) ->
         {
            pdfViewer.getBookmarks().subscribe(
               this,
               (List<List<Bookmark>> viewerBookmarks) ->
               {
                  setState("bookmarks", viewerBookmarks);
               },
               error ->{});
         },
         error ->{});

      bookmarks = new ArrayList<>();
   }

   return(bookmarks);
}
/*------------------------------------------------------------------------------

@name       getContent - get any content
                                                                              */
                                                                             /**
            Get any content.

@return     any content

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected List<ContentDsc> getContent()
{
   return((List<ContentDsc>)props().get(kPROPERTY_KEY_CONTENT));
}
/*------------------------------------------------------------------------------

@name       onCloseHandler - onClose event handler
                                                                              */
                                                                             /**
            onClose event handler as a public instance variable.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeFunction onCloseHandler = () ->
{
   setState("show", false);
};
/*------------------------------------------------------------------------------

@name       render - render component
                                                                              */
                                                                             /**
            Render component.

@history    Sat Oct 27, 2018 10:30:00 (Giavaneers - LBM) created

@notes
class="itemText"
                                                                              */
//------------------------------------------------------------------------------
public final void render()
{
   useState("show",      false);
   useState("bookmarks", null);

   List<List<Bookmark>> bookmarks  = getBookmarks();
   List<ContentDsc>     content    = getContent();
   boolean              bShow      = getStateBoolean("show");
   boolean              bBookmarks = bookmarks != null && bookmarks.size() > 0;
   boolean              bHash      = props().getBoolean("displayhash");
   boolean              bContent   = content != null && content.size() > 0;

   if (bShow)
   {
      if (!bBookmarks && !bContent)
      {
         onCloseHandler.call();
      }
      else
      {
/*--
      <@material-ui.core.Drawer open={bShow} onClose={onCloseHandler} >
         <@material-ui.core.List>
--*/
                                       // add any pdf bookmarks               //
         for (List<Bookmark> pageMarks : bookmarks)
         {
            for (Bookmark bookmark : pageMarks)
            {
               String bookmarkText = bookmark.getText();
               double unitScale    = bookmark.height / Bookmark.maxHeight;
               double marginScale  = (1.0 - unitScale) * 100;

               if (bHash)
               {
                  bookmarkText += " (#" + bookmark.getId() + ")";

                  NativeObject markStyle =
                     NativeObject.with(
                        "fontSize",   "1em",
                        "fontWeight", 300,
                        "marginLeft", marginScale + "px");

                                       // a list whose elements can be copied //
                                       // to get all url hash values          //
/*--
               <div style={markStyle}>{bookmarkText}</div>
--*/
               }
               else
               {
                  NativeObject markStyle =
                     NativeObject.with(
                        //"color",      "#367DA2",
                        "fontSize",   unitScale   + "em",
                        "fontWeight", 350,
                        "marginLeft", marginScale + "px");
/*--
               <@material-ui.core.ListItem
                  button id={bookmark.getId()}
                  onClick={bHash ? null : clickHandler}
                >
                  <@material-ui.core.ListItemText disableTypography>
                     <@material-ui.core.Typography style={markStyle}>
                        {bookmarkText}
                     </@material-ui.core.Typography>
                  </@material-ui.core.ListItemText>
               </@material-ui.core.ListItem>
--*/
               }
            }
         }
                                       // add any additional manifest entries //
         if (bBookmarks)
         {
/*--
            <@material-ui.core.Divider />
--*/
         }

         NativeObject refStyle =
            NativeObject.with(
            //"color",      "#367DA2",
            "fontSize",   "1em",
            "fontWeight", 350);

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
/*--
            <@material-ui.core.Divider />
--*/
               }
            }
/*--
            <@material-ui.core.ListItem
               button key={dsc.text} id={dsc.id} onClick={clickHandler}
            >
               <@material-ui.core.ListItemText disableTypography>
                  <@material-ui.core.Typography style={refStyle}>
                     {dsc.text}
                  </@material-ui.core.Typography>
               </@material-ui.core.ListItemText>
            </@material-ui.core.ListItem>
--*/
            if (ContentDsc.kTYPE_REFERENCE == dsc.type)
            {
/*--
            <@material-ui.core.Divider />
--*/
            }
         }
      }
/*--
      </@material-ui.core.List>
   </@material-ui.core.Drawer>
--*/
   }
};
}//====================================// end SideDrawer =====================//
