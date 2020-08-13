/*==============================================================================

name:       ContentPage.java

purpose:    Content chapter.

history:    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

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
import elemental2.core.JsObject;
import io.reactjava.client.components.pdfviewer.PDFViewer;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.IUITheme;
import io.reactjava.client.core.react.NativeObject;
import io.reactjava.client.core.react.Properties;
import io.reactjava.client.core.react.Utilities;
import java.util.ArrayList;
import java.util.List;
                                       // ContentPage ========================//
public class ContentPage extends Component
{
                                       // class constants ------------------- //
                                       // property keys                       //
public static final String kKEY_CONTENT  = "content";

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
protected List<ContentDsc>  content;   // array of content descriptors        //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       getContent - get content
                                                                              */
                                                                             /**
            Get content.

@return     content descriptor list

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected List<ContentDsc> getContent()
{
   if (content == null)
   {
      content = (List<ContentDsc>)props().get(kKEY_CONTENT);
      if (content == null)
      {
         content = ContentDsc.parse(props().getString(kKEY_CONTENT));
      }
   }
   return(content);
}
/*------------------------------------------------------------------------------

@name       getContentProperties - get any content properties
                                                                              */
                                                                             /**
            Get any content properties.

@return     any content properties, or null if none

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected Properties getContentProperties()
{
   Properties properties = Properties.newInstance();
   for (ContentDsc dsc : getContent())
   {
      if (dsc.type == ContentDsc.kTYPE_PROPERTIES)
      {
         properties = dsc.properties;
         break;
      }
   }
   return(properties);
}
/*------------------------------------------------------------------------------

@name       getPDFOptions - get pdf options
                                                                              */
                                                                             /**
            Get pdf options

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public NativeObject getPDFOptions()
{
   return((NativeObject)props().get(PDFViewer.kPROPERTY_PDF_OPTIONS));
}
/*------------------------------------------------------------------------------

@name       render - render component
                                                                              */
                                                                             /**
            Render component.

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final void render()
{
   if (getPDFOptions() != null)
   {
                                    // account for any properties from a      //
                                    // manifest to any specified pdf options  //
/*--
      <PDFViewer pdfoptions={resolvePDFOptions()} />
--*/
   }
   else
   {
/*--  <div class="contentChapter">                                          --*/
      for (ContentDsc dsc : getContent())
      {
         switch(dsc.type)
         {
            case ContentDsc.kTYPE_CAPTION:
            {
/*--           <ContentCaption content={dsc.text} id={dsc.id}/>             --*/
               break;
            }
            case ContentDsc.kTYPE_BODY:
            {
/*--           <ContentBody content={dsc.text}/>                            --*/
               break;
            }
            case ContentDsc.kTYPE_CODE:
            {
/*--           <ContentCode content={dsc.text}/>                            --*/
               break;
            }
            case ContentDsc.kTYPE_IMAGE:
            {
/*--           <ContentImage content={dsc.text}/>                           --*/
               break;
            }
            case ContentDsc.kTYPE_TITLE:
            {
/*--           <ContentTitle content={dsc.text} id={dsc.id}/>               --*/
               break;
            }
         }
      }
/*--  </div> --*/
   }
}
/*------------------------------------------------------------------------------

@name       renderCSS - get component css
                                                                              */
                                                                             /**
            Get component css.

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
   String unit   = "" + getTheme().getSpacing().getUnit() + "px";
   String unit8X = IUITheme.cssLengthScale(unit, 8);
/*--
   .contentChapter
   {
      margin:      0 auto;
      max-width:   600px;
      padding-top: {unit8X};
   }
   .contentChapterPDF
   {
      margin:      0 auto;
      padding-top: {unit8X};
   }
--*/
}
/*------------------------------------------------------------------------------

@name       resolvePDFOptions - resolve pdf options
                                                                              */
                                                                             /**
            Account for any properties from a manifest to any specified pdf
            options.

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected NativeObject resolvePDFOptions()
{
   NativeObject pdfOptions = getPDFOptions();
   Properties   properties = getContentProperties();

   Object bookmarks = pdfOptions.get(PDFViewer.kPROPERTY_BOOKMARKS);
   if (bookmarks == null)
   {
      pdfOptions.set(
         PDFViewer.kPROPERTY_BOOKMARKS,
         properties.get(PDFViewer.kPROPERTY_BOOKMARKS));
   }
   Object cover = pdfOptions.get(PDFViewer.kPROPERTY_COVER);
   if (cover == null)
   {
      pdfOptions.set(
         PDFViewer.kPROPERTY_COVER,
         properties.get(PDFViewer.kPROPERTY_COVER));
   }

   return(pdfOptions);
}
/*==============================================================================

name:       ContentDsc - content descriptor

purpose:    Content descriptor

history:    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class ContentDsc
{
                                       // constants ------------------------- //
                                       // type                                //
public static final int    kTYPE_BODY       = 1;
public static final int    kTYPE_CAPTION    = 2;
public static final int    kTYPE_CODE       = 3;
public static final int    kTYPE_IMAGE      = 4;
public static final int    kTYPE_PROPERTIES = 5;
public static final int    kTYPE_REFERENCE  = 6;
public static final int    kTYPE_TITLE      = 7;
public static final int    kTYPE_URL        = 8;

                                       // tokens                              //
public static final String kTOKEN_BODY      = ".body";
public static final String kTOKEN_CAPTION   = ".caption";
public static final String kTOKEN_CODE      = ".code";
public static final String kTOKEN_END       = ".end";
public static final String kTOKEN_IMAGE     = ".image";
public static final String kTOKEN_TITLE     = ".title";
public static final String kTOKEN_REFERENCE = ".reference";
public static final String kTOKEN_URL       = ".url";

                                       // class variables ------------------- //
public static Properties   manifests;  // getManifests map                    //
                                       // public instance variables --------- //
public int                 type;       // type                                //
public String              text;       // content                             //
public String              id;         // elementId                           //
public Properties          properties; // properties                          //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       ContentBodyDsc - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ContentDsc()
{
   this(-1, "undefined");
}
/*------------------------------------------------------------------------------

@name       ContentBodyDsc - constructor for specified type and content
                                                                              */
                                                                             /**
            Constructor for specified type and content

@param      type        type
@param      text        text

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ContentDsc(
   int    type,
   Object val)
{
   this.type = type;
   if (val instanceof Properties)
   {
      this.properties = (Properties)val;
   }
   else if (val instanceof String)
   {
      this.text = (String)val;

      if (text != null && text.length() > 0)
      {
         if (kTYPE_REFERENCE == type)
         {
                                       // example:                            //
                                       // <a href="path">Getting Started</a>  //
            int idxBeg = text.indexOf('"', text.indexOf("href"));
            if (idxBeg < 0)
            {
               throw new IllegalStateException("format for reference unknown");
            }
            int idxEnd = text.indexOf('"', idxBeg + 1);
            if (idxEnd < 0)
            {
               throw new IllegalStateException("format for reference unknown");
            }
            this.id = text.substring(idxBeg + 1, idxEnd).trim();

            idxBeg = text.indexOf('>', idxEnd);
            if (idxBeg < 0)
            {
               throw new IllegalStateException("format for reference unknown");
            }
            idxEnd = text.indexOf('<', idxBeg);
            if (idxEnd < 0)
            {
               throw new IllegalStateException("format for reference unknown");
            }

            this.text = text.substring(idxBeg + 1, idxEnd).trim();
         }
         else
         {
            this.id = Integer.toString(text.hashCode());
         }
      }
   }
   else
   {
      throw new IllegalArgumentException("Must be String or Properties");
   }
}
/*------------------------------------------------------------------------------

@name       equals - standard equals method
                                                                              */
                                                                             /**
            Standard equals method.

@return     true iff target equals this

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public boolean equals(
   Object target)
{
   ContentDsc test    = null;
   boolean    bEquals =
      this == target
      || (this.getClass() == target.getClass()
            && type == (test = (ContentDsc)target).type
            && text == null ? test.text == null : text.equals(test.text)
            && id   == null ? test .id  == null : id.equals(test.id));

   return(bEquals);
}
/*------------------------------------------------------------------------------

@name       parse - parse specified raw content
                                                                              */
                                                                             /**
            Parse specified raw content.

            If raw content is a url, fetch the refernced content and parse that.

@return     list of content descriptors

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static List<ContentDsc> parse(
   String raw)
{
   List<ContentDsc> content = new ArrayList<>();
   int              idxBeg  = -1;
   int              idxEnd  = 0;
   int              type;
   String           key;
   Properties       properties = Properties.newInstance();

   while(raw != null)
   {
      type   = 0;
      key    = null;
      idxBeg = raw.indexOf('.', idxEnd);
      if (idxBeg < 0)
      {
         break;
      }
      String chase = raw.substring(idxBeg);
      if (chase.startsWith(kTOKEN_BODY))
      {
         type    = kTYPE_BODY;
         idxBeg += kTOKEN_BODY.length();
      }
      else if (chase.startsWith(kTOKEN_CAPTION))
      {
         type    = kTYPE_CAPTION;
         idxBeg += kTOKEN_CAPTION.length();
      }
      else if (chase.startsWith(kTOKEN_CODE))
      {
         type    = kTYPE_CODE;
         idxBeg += kTOKEN_CODE.length();
      }
      else if (chase.startsWith(kTOKEN_IMAGE))
      {
         type    = kTYPE_IMAGE;
         idxBeg += kTOKEN_IMAGE.length();
      }
      else if (chase.startsWith(kTOKEN_REFERENCE))
      {
         type    = kTYPE_REFERENCE;
         idxBeg += kTOKEN_REFERENCE.length();
      }
      else if (chase.startsWith(kTOKEN_TITLE))
      {
         type    = kTYPE_TITLE;
         idxBeg += kTOKEN_TITLE.length();
      }
      else if (chase.startsWith(kTOKEN_URL))
      {
         type    = kTYPE_URL;
         idxBeg += kTOKEN_URL.length();
      }
      else
      {
         key     = chase.split(Utilities.kREGEX_ONE_OR_MORE_WHITESPACE)[0];
         idxBeg += key.length();
         key     = key.substring(1);
      }

      idxEnd = raw.indexOf(kTOKEN_END, idxBeg);
      if (idxEnd < 0)
      {
         throw new IllegalStateException("No matching '.end' token found");
      }

      String value = raw.substring(idxBeg, idxEnd).trim();
      if (key != null)
      {
         properties = Properties.with(properties, key, value);
      }
      else
      {
         content.add(new ContentDsc(type, value));
      }

      idxEnd += kTOKEN_END.length();
   }

   if (JsObject.keys(properties).length > 0)
   {
      content.add(new ContentDsc(kTYPE_PROPERTIES, properties));
   }

   return(content);
}
}//====================================// end ContentDsc =====================//
}//====================================// end ContentPage ====================//
