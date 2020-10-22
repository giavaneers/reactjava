/*==============================================================================

name:       JSXSEOInfo - SEO information

purpose:    SEO information

history:    Sun Sep 27, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.compiler.jsx;
                                       // imports --------------------------- //
import io.reactjava.client.core.react.SEOInfo;

                                       // JSXSEOInfo =========================//
public class JSXSEOInfo extends SEOInfo
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

@name       JSXSEOInfo - default constructor
                                                                              */
                                                                             /**
            Default constructor.

@history    Wed Jun 19, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected JSXSEOInfo()
{
   super();
}
/*------------------------------------------------------------------------------

@name       JSXSEOInfo - constructor for specified string
                                                                              */
                                                                             /**
            Constructor for specified string.

@param      deployPath           SEO deploy path
@param      defaultPageHash      default page hash
@param      pageInfos            collection of SEOPageInfo

@history    Wed Jun 19, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected JSXSEOInfo(
   String     deployPath,
   String     defaultPageHash,
   String[][] pageInfos)
{
   super(deployPath, defaultPageHash, pageInfos);
}
/*------------------------------------------------------------------------------

@name       getDefaultPageHash - get default page hash
                                                                              */
                                                                             /**
            Get default page hash.

@return     default page hash

@history    Sat Sep 26, 2020 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getDefaultPageHash()
{
   return(defaultPageHash);
}
/*------------------------------------------------------------------------------

@name       getDeployPath - get deploy path
                                                                              */
                                                                             /**
            Get deploy path.

@return     deploy path

@history    Sat Sep 26, 2020 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getDeployPath()
{
   return(deployPath);
}
/*------------------------------------------------------------------------------

@name       getPageInfos - get pageInfos
                                                                              */
                                                                             /**
            Get pageInfos.

@return     pageInfos

@history    Sat Sep 26, 2020 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String[][] getPageInfos()
{
   return(pageInfos);
}
/*------------------------------------------------------------------------------

@name       toString - standard toString() method
                                                                              */
                                                                             /**
            Standard toString() method.

@return     string representation

@history    Sat Sep 26, 2020 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String toString()
{
   String dsc = "empty value";
   if (deployPath != null)
   {
      dsc = "deployPath=" + deployPath;
   }
   if (defaultPageHash != null)
   {
      dsc += ", defaultPageHash=" + defaultPageHash;
   }
   if (pageInfos != null)
   {
      for (String[] pageInfo : pageInfos)
      {
         dsc += ", pageInfo: ";
         dsc += "pageHash="        + pageInfo[kIDX_PAGEINFO_PAGE_HASH];
         dsc += ", title="         + pageInfo[kIDX_PAGEINFO_TITLE];
         dsc += ", description="   + pageInfo[kIDX_PAGEINFO_DESCRIPTION];
         dsc += ", strucDataType=" + pageInfo[kIDX_PAGEINFO_STRUCTURED_DATA_TYPE];
         dsc += ", strucdData="    + pageInfo[kIDX_PAGEINFO_STRUCTURED_DATA];
      }
   }
   return (dsc);
}
}//====================================// end JSXSEOInfo =====================//
