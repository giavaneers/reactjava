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
                                       // (none)                              //
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
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public String           title;         // title                               //
public FooterTopicDsc[] topics;        // topics                              //
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

@name       FooterDsc - constructor for specified title and descriptions
                                                                              */
                                                                             /**
            Constructor for specified title and descriptions

@param      title             title
@param      descriptions      array of descriptions

@history    Fri Feb 15, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public FooterDsc(
   String           title,
   FooterTopicDsc[] topics)
{
   this.title  = title;
   this.topics = topics;
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
public static final String  kTARGET_BLANK  = "_blank";
public static final String  kTARGET_PARENT = "_parent";
public static final String  kTARGET_SELF   = "_self";
public static final String  kTARGET_TOP    = "_top";

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
}//====================================// end FooterTopicDsc =================//
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
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public boolean      bMenuButton;       // has menu button or not              //
public ButtonDsc[]  appBarButtons;     // app bar button descriptors          //
public String       image;             // image                               //
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
   SectionDsc[] sections)
{
   this.title         = title;
   this.image         = image;
   this.bMenuButton   = bMenuButton;
   this.appBarButtons = appBarButtons;
   this.pushPaths     = pushPaths;
   this.sections      = sections;
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
}//====================================// end SectionDsc =====================//
}//====================================// end Descriptors ====================//
