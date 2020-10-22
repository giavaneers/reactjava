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
import java.util.List;
                                       // SEOInfo =========================== //
public class SEOInfo
{
                                       // constants ------------------------- //
public static final String kPAGE_HASH_DEFAULT = "";

                                       // padeInfo indices                    //
public static final int    kIDX_PAGEINFO_PAGE_HASH            = 0;
public static final int    kIDX_PAGEINFO_TITLE                = 1;
public static final int    kIDX_PAGEINFO_DESCRIPTION          = 2;
public static final int    kIDX_PAGEINFO_STRUCTURED_DATA_TYPE = 3;
public static final int    kIDX_PAGEINFO_STRUCTURED_DATA      = 4;
public static final int    kNUM_PAGEINFO_MEMBERS              = 5;

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
protected String     deployPath;       // deploy path; e.g.'http://myapp.com' //
protected String     defaultPageHash;  // default path hash value             //
protected String[][] pageInfos;        // page infos                          //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       SEOInfo - default constructor
                                                                              */
                                                                             /**
            Default constructor.

@history    Wed Jun 19, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected SEOInfo()
{
}
/*------------------------------------------------------------------------------

@name       SEOInfo - constructor for specified string
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
protected SEOInfo(
   String     deployPath,
   String     defaultPageHash,
   String[][] pageInfos)
{
   this.deployPath      = deployPath;
   this.defaultPageHash = defaultPageHash;
   this.pageInfos       = pageInfos;
   if (pageInfos != null)
   {
                                       // add a default if required           //
      String[] defaultPageInfoToAdd = null;
      for (String[] pageInfo : pageInfos)
      {
         if (pageInfo[kIDX_PAGEINFO_PAGE_HASH].equals(kPAGE_HASH_DEFAULT))
         {
                                       // already has a default entry         //
            defaultPageInfoToAdd = null;
            break;
         }
         else if (pageInfo[kIDX_PAGEINFO_PAGE_HASH].equals(defaultPageHash))
         {
                                       // found the default entry             //
            defaultPageInfoToAdd = pageInfo;
            break;
         }
      }
      if (defaultPageInfoToAdd != null)
      {
                                       // add as the default                  //
         String[][] newPageInfos =
            new String[this.pageInfos.length + 1][kNUM_PAGEINFO_MEMBERS];

         System.arraycopy(
            this.pageInfos, 0, newPageInfos, 0, this.pageInfos.length);

         newPageInfos[this.pageInfos.length] = defaultPageInfoToAdd;
         this.pageInfos = newPageInfos;
      }
   }
}
/*------------------------------------------------------------------------------

@name       newInstance - factory method parsed at compile time
                                                                              */
                                                                             /**
            Factory method parsed at compile time.

@param      args     array of StringLiteralLists

@history    Sat Sep 26, 2020 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static SEOInfo newInstance(
   StringLiteralList... args)
{
   if (args.length == 0 || args[0].elements.length != 2)
   {
      throw new IllegalArgumentException(
         "Must at least specify the deploy path and default page hash");
   }
   String deployPath      = args[0].elements[0];
   String defaultPageHash = args[0].elements[1];

   List<String[]> pageInfosList = new ArrayList<>();
   for (int i = 1; i < args.length; i++)
   {
      StringLiteralList list = args[i];
      if (list.elements.length != 3 && list.elements.length != 5)
      {
         throw new IllegalArgumentException(
            "Each pageInfo specification must include "
          + "either three or five arguments");
      }

      pageInfosList.add(
         new String[]
         {
            list.elements[0],
            list.elements[1],
            list.elements[2],
            list.elements.length == 5 ? list.elements[3] : null,
            list.elements.length == 5 ? list.elements[4] : null
         });
   }

   String[][] pageInfos =
      pageInfosList.toArray(
         new String[pageInfosList.size()][kNUM_PAGEINFO_MEMBERS]);

   return(new SEOInfo(deployPath, defaultPageHash, pageInfos));
}
}//====================================// end SEOInfo ========================//
