/*==============================================================================

name:       SEOInfo - SEO information

purpose:    SEO information

history:    Wed Jun 19, 2019 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import java.util.ArrayList;
import java.util.Collection;
                                       // SEOInfo =========================== //
public class SEOInfo
{
                                       // constants ------------------------- //
public static final String kDELIMITER  = "<SEOInfo>";
public static final String kNULL_VALUE = kDELIMITER;

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // deploy path; e.g.'http://myapp.com' //
public String                  deployPath;
                                       // page infos                          //
public Collection<SEOPageInfo> pageInfos;
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       SEOInfo - constructor for specified string
                                                                              */
                                                                             /**
            Constructor for specified string.

@param      deployPath     SEO deploy path
@param      pageInfos      collection of SEOPageInfo

@history    Wed Jun 19, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public SEOInfo(
   String                  deployPath,
   Collection<SEOPageInfo> pageInfos)
{
   this.deployPath = deployPath;
   this.pageInfos  = pageInfos;
}
/*------------------------------------------------------------------------------

@name       SEOInfo - constructor for specified string
                                                                              */
                                                                             /**
            Constructor for specified string.

@param      seoInfo     SEO information string

@history    Wed Jun 19, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public SEOInfo(
   String seoInfo)
{
   this.pageInfos  = new ArrayList<>();
   String[] splits = seoInfo.split(SEOInfo.kDELIMITER);
   if (splits.length > 0)
   {
      this.deployPath = splits[0];
      if (splits.length > 1)
      {
         splits = splits[1].split(SEOPageInfo.kDELIMITER);
         for (int i = 0; i < splits.length; i++)
         {
            pageInfos.add(new SEOPageInfo(splits[i]));
         }
      }
   }
}
/*------------------------------------------------------------------------------

@name       toString - standard toString method
                                                                              */
                                                                             /**
            Standard toString method.

@return     string representation which can be parsed by matching constructor.

@history    Wed Jun 19, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public String toString()
{
   String value = (deployPath != null ? deployPath : "") + kDELIMITER;

   if (pageInfos != null)
   {
      for (SEOPageInfo info : pageInfos)
      {
         value += info;
      }
   }

   return(value);
}
/*==============================================================================

name:       SEOPageInfo - SEO page information

purpose:    SEO page information

history:    Wed Jun 19, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class SEOPageInfo
{
                                       // constants ------------------------- //
public static final String kDELIMITER      = "<SEOPageInfo>";
public static final String kDELIMITER_ELEM = "<SEOPageInfoElem>";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public String pageHash;                // page hash                           //
public String title;                   // title                               //
public String description;             // description                         //
public String structuredDataType;      // structured data type                //
public String structuredData;          // structured data                 //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       SEOPageInfo - constructor for specified string
                                                                              */
                                                                             /**
            Constructor.

@param      pageHash       page hash
@param      title          page title
@param      description    page description

@history    Wed Jun 19, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public SEOPageInfo(
   String pageHash,
   String title,
   String description)
{
   this(pageHash, title, description, null, null);
}
/*------------------------------------------------------------------------------

@name       SEOPageInfo - constructor for specified string
                                                                              */
                                                                             /**
            Constructor.

@param      pageHash                page hash
@param      title                   page title
@param      description             page description
@param      structuredDataType      page description
@param      structuredData          page description

@history    Wed Jun 19, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public SEOPageInfo(
   String pageHash,
   String title,
   String description,
   String structuredDataType,
   String structuredData)
{
   this.pageHash           = pageHash;
   this.title              = title;
   this.description        = description;
   this.structuredDataType = structuredDataType;
   this.structuredData     = structuredData;
}
/*------------------------------------------------------------------------------

@name       SEOPageInfo - constructor for specified string
                                                                              */
                                                                             /**
            Constructor for specified string.

@param      infoString     SEO page information string

@history    Wed Jun 19, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected SEOPageInfo(
   String infoString)
{
   String[] splits = infoString.split(kDELIMITER_ELEM);
   this.pageHash   = splits[0];
   if (splits.length > 1)
   {
      this.title = splits[1];
   }
   if (splits.length > 2)
   {
      this.description = splits[2];
   }
   if (splits.length > 3)
   {
      this.structuredDataType = splits[3];
   }
   if (splits.length > 4)
   {
      this.structuredData = splits[4];
   }
}
/*------------------------------------------------------------------------------

@name       toString - standard toString method
                                                                              */
                                                                             /**
            Standard toString method.

@return     string representation which can be parsed by matching constructor.

@history    Wed Jun 19, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public String toString()
{
   String value =
      (pageHash           != null ? pageHash           : "") + kDELIMITER_ELEM
    + (title              != null ? title              : "") + kDELIMITER_ELEM
    + (description        != null ? description        : "") + kDELIMITER_ELEM
    + (structuredDataType != null ? structuredDataType : "") + kDELIMITER_ELEM
    + (structuredData     != null ? structuredData     : "") + kDELIMITER;

   return(value);
}
}//====================================// end SEOPageInfo ====================//
}//====================================// end SEOInfo ========================//
