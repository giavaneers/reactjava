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
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.IUITheme;
import io.reactjava.client.core.react.Properties;
import java.util.ArrayList;
import java.util.List;
                                       // ContentPage ========================//
public class ContentPage extends Component
{
                                       // class constants ------------------- //
                                       // property keys                       //
public static final String  kKEY_CONTENT  = "content";

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

@name       render - render component
                                                                              */
                                                                             /**
            Render component.

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void render()
{
/*--  <div class="contentChapter">
--*/
      for (ContentDsc dsc : getContent())
      {
         switch(dsc.type)
         {
            case ContentDsc.kTYPE_CAPTION:
            {
/*--           <ContentCaption content={dsc.text} id={dsc.id}></ContentCaption>
--*/
               break;
            }
            case ContentDsc.kTYPE_BODY:
            {
/*--           <ContentBody content={dsc.text}></ContentBody>
--*/
               break;
            }
            case ContentDsc.kTYPE_CODE:
            {
/*--           <ContentCode content={dsc.text}></ContentCode>
--*/
               break;
            }
            case ContentDsc.kTYPE_IMAGE:
            {
/*--           <ContentImage content={dsc.text}></ContentImage>
--*/
               break;
            }
            case ContentDsc.kTYPE_TITLE:
            {
/*--           <ContentTitle content={dsc.text} id={dsc.id}></ContentTitle>
--*/
               break;
            }
         }
      }
/*--  </div>
--*/
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
--*/
}
/*==============================================================================

name:       ContentBodyDsc - content descriptor

purpose:    Content descriptor

history:    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class ContentDsc
{
                                       // constants ------------------------- //
                                       // type                                //
public static final int    kTYPE_BODY      = 1;
public static final int    kTYPE_CAPTION   = 2;
public static final int    kTYPE_CODE      = 3;
public static final int    kTYPE_IMAGE     = 4;
public static final int    kTYPE_TITLE     = 5;
public static final int    kTYPE_REFERENCE = 6;

                                       // tokens                              //
public static final String kTOKEN_BODY      = ".body";
public static final String kTOKEN_CAPTION   = ".caption";
public static final String kTOKEN_CODE      = ".code";
public static final String kTOKEN_END       = ".end";
public static final String kTOKEN_IMAGE     = ".image";
public static final String kTOKEN_TITLE     = ".title";
public static final String kTOKEN_REFERENCE = ".reference";

                                       // class variables ------------------- //
public static Properties   manifests;  // getManifests map                       //
                                       // public instance variables --------- //
public int                 type;       // type                                //
public String              text;       // content                             //
public String              id;         // elementId                           //
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
   String text)
{
   this.type = type;
   this.text = text;

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
/*------------------------------------------------------------------------------

@name       parse - parse specified raw content
                                                                              */
                                                                             /**
            Parse specified raw content.

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

   while(true)
   {
      type   = 0;
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
      else
      {
         idxEnd++;
         continue;
      }

      idxEnd = raw.indexOf(kTOKEN_END, idxBeg);
      if (idxEnd < 0)
      {
         throw new IllegalStateException("No matching '.end' token found");
      }

      content.add(new ContentDsc(type, raw.substring(idxBeg, idxEnd).trim()));
      idxEnd += kTOKEN_END.length();
   }

   return(content);
}
}//====================================// end ContentBodyDsc =====================//
}//====================================// end ContentPage ====================//
