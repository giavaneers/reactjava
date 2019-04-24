/*==============================================================================

name:       ContentBody.java

purpose:    Content body.

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
import io.reactjava.client.core.react.ReactElement;
import io.reactjava.client.core.react.ElementDsc;
import io.reactjava.client.core.react.Properties;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
                                       // ContentBody ========================//
public class ContentBody extends Component
{
                                       // class constants ------------------- //
                                       // protected instance variables -------//
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       render - render component
                                                                              */
                                                                             /**
            Render component.

@return     void

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void render()
{
   Function<Properties, ReactElement> fcn = (props) ->
   {
      ElementDsc root =
         ElementDsc.create(
            null, "Typography",
            Properties.with(
               "component", "h1", "variant", "body1", "id", getNextId()));

      List<ContentBody.ContentDsc> content =
         ContentBody.ContentDsc.parse(props.getString("content"));

      for (ContentBody.ContentDsc dsc : content)
      {
         ElementDsc.create(
            root,
            dsc.tag,
            Properties.with(dsc.props, "id", getNextId()),
            dsc.text);
      }

      ReactElement element = ElementDsc.createElement(root);
      return(element);
   };
   this.componentFcn = fcn;
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
                                       // markup delimiter                    //
public static final String kTOKEN_MARKUP = "%markup%";

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public String       tag;               // tag                                 //
public Properties   props;             // props                               //
public String       text;              // text                                //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       ContentDsc - default constructor
                                                                              */
                                                                             /**
            Default constructor

@return     An instance of ContentDsc if successful.

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ContentDsc()
{
   this("undefined", null, null);
}
/*------------------------------------------------------------------------------

@name       ContentDsc - constructor for specified type and content
                                                                              */
                                                                             /**
            Constructor for specified type and content

@return     An instance of ContentDsc if successful.

@param      type        type
@param      content     content

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ContentDsc(
   String text)
{
   this("span", null, text);
}
/*------------------------------------------------------------------------------

@name       ContentDsc - constructor for specified type and content
                                                                              */
                                                                             /**
            Constructor for specified type and content

@return     An instance of ContentDsc if successful.

@param      type        type
@param      content     content

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ContentDsc(
   String   tag,
   String[] atts,
   String   text)
{
   this.tag   = tag;
   this.props = atts != null ? Properties.with((Object[])atts) : new Properties();
   this.text  = text;
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
public static List<ContentBody.ContentDsc> parse(
   String raw)
{
   if (raw == null)
   {
      throw new IllegalArgumentException("Raw may not be null");
   }

   List<ContentDsc> content = new ArrayList<>();
   String[]         parts   = raw.split(kTOKEN_MARKUP);

   for (int i = 0; i < parts.length; i++)
   {
      boolean bMarkup = i % 2 != 0;
      String part     = parts[i];
      if (part.length() == 0)
      {
         continue;
      }
      content.add(bMarkup ? ContentDsc.parseMarkup(part) : new ContentDsc(part));
   }

   return(content);
}
/*------------------------------------------------------------------------------

@name       parse - parse specified markup
                                                                              */
                                                                             /**
            Parse specified markup.

@return     markup descriptor

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static ContentDsc parseMarkup(
   String markup)
{
   int idxBeg = markup.indexOf('<');
   if (idxBeg < 0)
   {
      throw new IllegalStateException(markup);
   }

   int idxEnd = markup.indexOf('>', idxBeg);
   if (idxEnd < 0)
   {
      throw new IllegalStateException(markup);
   }

   String[] tokens = markup.substring(idxBeg + 1, idxEnd).trim().split(" ");
   String   tag    = tokens[0];

   List<String> attsList = new ArrayList();
   for (int i = 1; i < tokens.length; i++)
   {
      String[] splits = tokens[i].split("=");
      attsList.add(splits[0]);
      if (splits[1].startsWith("\"") &&  splits[1].endsWith("\""))
      {
         splits[1] = splits[1].substring(1, splits[1].length() - 1);
      }
      attsList.add(splits[1]);
   }

   String[] atts = attsList.toArray(new String[0]);

                                       // find any text                       //
   idxBeg = idxEnd + 1;
   idxEnd = markup.indexOf('<', idxBeg);

   String text = idxEnd > 0 ? markup.substring(idxBeg, idxEnd) : null;

   return(new ContentDsc(tag, atts, text));
}
}//====================================// end ContentDsc =====================//
}//====================================// end ContentBody ====================//
