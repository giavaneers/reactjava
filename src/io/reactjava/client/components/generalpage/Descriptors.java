/*==============================================================================

name:       Descriptors.java

purpose:    Various General Page Descriptors

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
import java.util.Arrays;
import java.util.function.Consumer;
                                       // Descriptors ========================//
public class Descriptors
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*==============================================================================

name:       AppBarDsc - app bar descriptor

purpose:    App bar descriptor

history:    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class AppBarDsc
{
                                       // constants ------------------------- //
public static final AppBarDsc kDEFAULT =
   new AppBarDsc(null, false, null, false, null);

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public String      title;              // title                               //
public ButtonDsc[] buttonDscs;         // button map                          //
public boolean     bMenuButton;        // has menu button or not              //
public boolean     bOpen;              // side drawer open or not             //
public Consumer    openHandler;        // any open handler                    //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       AppBarDsc - constructor for specified image, push values, and sections
                                                                              */
                                                                             /**
            Constructor for specified image, push values, and sections.

@param      title          title
@param      buttonDscs     array of button descriptors
@param      bOpen          true iff side drawer is open
@param      openHandler    any open handler

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public AppBarDsc(
   String      title,
   boolean     bMenuButton,
   ButtonDsc[] buttonDscs,
   boolean     bOpen,
   Consumer    openHandler)
{
   this.title       = title;
   this.bMenuButton = bMenuButton;
   this.buttonDscs  = buttonDscs;
   this.bOpen       = bOpen;
   this.openHandler = openHandler;
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
   AppBarDsc test    = null;
   boolean   bEquals =
      this == target
      || (getClass()  == target.getClass()
            && bMenuButton == (test = (AppBarDsc)target).bMenuButton
            && bOpen       == test.bOpen
            && title == null ? test.title == null : title.equals(test.title)
            && buttonDscs == null
                  ? test .buttonDscs != null
                  : Arrays.equals(buttonDscs, test.buttonDscs)
            && openHandler == null
                  ? test.openHandler == null
                  : openHandler.equals(test.openHandler));
   return(bEquals);
}
}//====================================// end AppBarDsc ======================//
/*==============================================================================

name:       ButtonDsc -  general button descriptor

purpose:    General button descriptor

history:    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class ButtonDsc
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public String             text;        // text                                //
public String             url;         // url                                 //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       ButtonDsc - constructor for specified image, push values, and sections
                                                                              */
                                                                             /**
            Constructor for specified image, push values, and sections.

@param      text     text
@param      url      url

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ButtonDsc(
   String text,
   String url)
{
   this.text = text;
   this.url  = url;
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
   ButtonDsc test    = null;
   boolean   bEquals =
      this == target
      || (this.getClass() == target.getClass()
            && (test = (ButtonDsc)target).text == null
                  ? text == null : text.equals(test.text)
            && url == null ? test.url == null : url.equals(test.url));
   return(bEquals);
}
}//====================================// end ButtonDsc ======================//
/*==============================================================================

name:       FooterDsc - footer descriptor

purpose:    Footer descriptor

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class FooterDsc
{
                                       // constants ------------------------- //
public static final String  kTARGET_BLANK  = "_blank";
public static final String  kTARGET_PARENT = "_parent";
public static final String  kTARGET_SELF   = "_self";
public static final String  kTARGET_TOP    = "_top";

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public FooterCategoryDsc[]  categories;// categories                          //
public FooterCreditDsc      credit;    // credit                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       FooterDsc - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public FooterDsc()
{
}
/*------------------------------------------------------------------------------

@name       FooterDsc - constructor for specified categories and credit
                                                                              */
                                                                             /**
            Constructor for specified categories and credit

@param      categories     category descriptors
@param      credit         credit descriptor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public FooterDsc(
   FooterCategoryDsc[] categories,
   FooterCreditDsc     credit)
{
   this.categories = categories;
   this.credit     = credit;
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
   FooterDsc test    = null;
   boolean   bEquals =
      this == target
      || (this.getClass() == target.getClass()
            && (test = (FooterDsc)target).credit.equals(credit)
            && categories == null
               ? test.categories == null
               : Arrays.equals(categories, test.categories));
   return(bEquals);
}
/*==============================================================================

name:       FooterCategoryDsc - footer category descriptor

purpose:    Footer category descriptor

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class FooterCategoryDsc
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public String           title;         // title                               //
public FooterTopicDsc[] topics;        // topics                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       FooterCategoryDsc - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public FooterCategoryDsc()
{
}
/*------------------------------------------------------------------------------

@name       FooterCategoryDsc - constructor for specified title and descriptions
                                                                              */
                                                                             /**
            Constructor for specified title and descriptions

@param      title       title
@param      topics      array of topics

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public FooterCategoryDsc(
   String           title,
   FooterTopicDsc[] topics)
{
   this.title  = title;
   this.topics = topics;
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
   FooterCategoryDsc test = null;

   boolean bEquals =
      this == target
      || (getClass() == target.getClass()
           && (test = (FooterCategoryDsc)target).title == null
                 ? title == null : title.equals(test.title)
           && topics == null
                 ? test.topics == null : Arrays.equals(topics, test.topics));
   return(bEquals);
}
/*==============================================================================

name:       FooterTopicDsc - footer topic descriptor

purpose:    Footer topic descriptor

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class FooterTopicDsc
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public String topic;                   // topic                               //
public String url;                     // url                                 //
public String target;                  // target ("_blank", "_self", etc)     //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       FooterTopicDsc - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public FooterTopicDsc()
{
}
/*------------------------------------------------------------------------------

@name       FooterTopicDsc - constructor for specified title and descriptions
                                                                              */
                                                                             /**
            Constructor for specified title and descriptions

@param      topic    topic
@param      url      url

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public FooterTopicDsc(
   String topic,
   String url)
{
   this(topic, url, kTARGET_BLANK);
}
/*------------------------------------------------------------------------------

@name       FooterTopicDsc - constructor for specified title and descriptions
                                                                              */
                                                                             /**
            Constructor for specified title and descriptions

@param      topic    topic
@param      url      url
@param      target   target

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public FooterTopicDsc(
   String topic,
   String url,
   String target)
{
   this.topic  = topic;
   this.url    = url;
   this.target = target;
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
   Object object)
{
   FooterTopicDsc test = null;

   boolean bEquals =
      this == object
      || (getClass() == object.getClass()
            && (test = (FooterTopicDsc)object).target == null
               ? target == null : target.equals(test.target)
            && topic == null ? test.topic == null : topic.equals(test.topic));
   return(bEquals);
}
}//====================================// end FooterTopicDsc =================//
}//====================================// end FooterCategoryDsc ==============//
/*==============================================================================

name:       FooterCreditDsc - footer credit descriptor

purpose:    Footer credit descriptor

history:    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class FooterCreditDsc
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public String logo;                    // logo                                //
public String text;                    // text                                //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       FooterCreditDsc - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public FooterCreditDsc()
{
}
/*------------------------------------------------------------------------------

@name       FooterCreditDsc - constructor for specified title and descriptions
                                                                              */
                                                                             /**
            Constructor for specified title and descriptions

@param      logo     credit logo
@param      text     credit text

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public FooterCreditDsc(
   String logo,
   String text)
{
   this.logo = logo;
   this.text = text;
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
   FooterCreditDsc test = null;

   boolean bEquals =
      this == target
      || (getClass() == target.getClass()
           && (test = (FooterCreditDsc)target).logo == null
                 ? logo == null : logo.equals(test.logo)
           && text == null ? text == null : text.equals(test.text));

   return(bEquals);
}
}//====================================// end FooterCreditDsc ================//
}//====================================// end FooterDsc ======================//
/*==============================================================================

name:       PageDsc -  general button descriptor

purpose:    General Page Descriptor

history:    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class PageDsc
{
                                       // constants ------------------------- //
public static final PageDsc kDEFAULT =
   new PageDsc(null, null, false, null, null, null, null);

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public boolean      bMenuButton;       // has menu button or not              //
public ButtonDsc[]  appBarButtons;     // app bar button descriptors          //
public String       image;             // image                               //
public FooterDsc    footer;            // footer descriptor                   //
public String[]     pushPaths;         // push paths                          //
public SectionDsc[] sections;          // section descriptors                 //
public String       title;             // title                               //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       PageDsc - constructor for specified image, push values, and sections
                                                                              */
                                                                             /**
            Constructor for specified image, push values, and sections.

@param      title             title
@param      image             image
@param      appBarButtons     app bar buttons
@param      pushPaths         push paths
@param      sections          sections

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public PageDsc(
   String       title,
   String       image,
   boolean      bMenuButton,
   ButtonDsc[]  appBarButtons,
   String[]     pushPaths,
   SectionDsc[] sections,
   FooterDsc    footer)
{
   this.title         = title;
   this.image         = image;
   this.bMenuButton   = bMenuButton;
   this.appBarButtons = appBarButtons;
   this.pushPaths     = pushPaths;
   this.sections      = sections;
   this.footer        = footer;
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
   PageDsc test    = null;
   boolean bEquals =
      this == target
      || (getClass()  == target.getClass()
            && bMenuButton == (test = (PageDsc)target).bMenuButton
            && appBarButtons == null
                  ? test .appBarButtons != null
                  : Arrays.equals(appBarButtons, test.appBarButtons)
            && pushPaths == null
                  ? test .pushPaths != null
                  : Arrays.equals(pushPaths, test.pushPaths)
            && sections == null
                  ? test .sections != null
                  : Arrays.equals(sections, test.sections)
            && image == null ? test.image == null : image.equals(test.image)
            && title == null ? test.title == null : title.equals(test.title));

   return(bEquals);
}
}//====================================// end PageDsc ========================//
/*==============================================================================

name:       SectionDsc -  general page section descriptor

purpose:    General Page Section Descriptor

history:    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class SectionDsc
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public String   buttonText;            // button text                         //
public String   buttonVariant;         // button variant                      //
public String[] descriptions;          // descriptions                        //
public String   imagePath;             // image path                          //
public String   subheader;             // subheader                           //
public String   title;                 // title                               //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       SectionDsc - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public SectionDsc()
{
}
/*------------------------------------------------------------------------------

@name       SectionDsc - constructor for specified title and descriptions
                                                                              */
                                                                             /**
            Constructor for specified title and descriptions

@param      title             title
@param      imagePath         imagePath
@param      descriptions      array of descriptions
@param      buttonText        button text
@param      buttonVariant     button variant

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public SectionDsc(
   String   title,
   String   imagePath,
   String[] descriptions,
   String   buttonText,
   String   buttonVariant)
{
   this(title, null, imagePath, descriptions, buttonText, buttonVariant);
}
/*------------------------------------------------------------------------------

@name       SectionDsc - constructor for specified title and descriptions
                                                                              */
                                                                             /**
            Constructor for specified title and descriptions

@param      title             title
@param      subheader         subheader
@param      imagePath         imagePath
@param      descriptions      array of descriptions
@param      buttonText        button text
@param      buttonVariant     button variant

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public SectionDsc(
   String   title,
   String   subheader,
   String   imagePath,
   String[] descriptions,
   String   buttonText,
   String   buttonVariant)
{
   this.title         = title;
   this.subheader     = subheader;
   this.imagePath     = imagePath;
   this.descriptions  = descriptions;
   this.buttonText    = buttonText;
   this.buttonVariant = buttonVariant;
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
   SectionDsc test    = null;
   boolean    bEquals =
      this == target
      || (getClass() == target.getClass()
            && (test = (SectionDsc)target).title == null
                  ? title == null : title.equals(test.title)
            && descriptions == null
                  ? test.descriptions != null
                  : Arrays.equals(descriptions, test.descriptions)
            && buttonText == null
               ? test.buttonText == null : buttonText.equals(test.buttonText)
            && buttonVariant == null
               ? test.buttonVariant == null
               : buttonVariant.equals(test.buttonVariant)
            && imagePath == null
               ? test.imagePath == null : imagePath.equals(test.imagePath)
            && subheader == null
               ? test.subheader == null : subheader.equals(test.subheader));

   return(bEquals);
}
}//====================================// end SectionDsc =====================//
}//====================================// end Descriptors ====================//
