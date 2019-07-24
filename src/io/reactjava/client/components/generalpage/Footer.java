/*==============================================================================

name:       Footer.java

purpose:    ReactJava website Footer.

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
import elemental2.dom.DomGlobal;
import io.reactjava.client.components.generalpage.Descriptors.FooterDsc;
import io.reactjava.client.components.generalpage.Descriptors.FooterDsc.FooterTopicDsc;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.IUITheme;

                                       // Footer =============================//
public class Footer extends Component
{
                                       // class constants ------------------- //
public static final String      kLOCATION = DomGlobal.location.getPathname();
public static final FooterDsc[] kFOOTERS  =
{
   new FooterDsc(
      "Project",
      new FooterTopicDsc[]
      {
         new FooterTopicDsc("Team", "http://www.giavaneers.com"),
         new FooterTopicDsc("History", "http://www.giavaneers.com"),
         new FooterTopicDsc("Contact us", "http://www.giavaneers.com/contact")
      }),
   new FooterDsc(
      "Features",
      new FooterTopicDsc[]
      {
         new FooterTopicDsc("Get Started",       kLOCATION + "#getStarted"),
         new FooterTopicDsc("User Guide",        kLOCATION + "#userGuide"),
         new FooterTopicDsc("Contributor Guide", kLOCATION + "#contributorGuide"),
      }),
   new FooterDsc(
      "Resources",
      new FooterTopicDsc[]
      {
         new FooterTopicDsc("Other Projects",  "http://www.giavaneers.com/platforms"),
         new FooterTopicDsc("Google Console",  "https://console.cloud.google.com"),
         new FooterTopicDsc("Google Search",   "https://search.google.com/search-console"),
         new FooterTopicDsc("Google Analytics","https://analytics.google.com/analytics"),
      }),
   new FooterDsc(
      "Legal",
      new FooterTopicDsc[]
      {
         new FooterTopicDsc("License",        ""),
         new FooterTopicDsc("Privacy policy", ""),
         new FooterTopicDsc("Terms of use",   "")
      }),
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
/*--
   <footer class="footer layout">
      <@material-ui.core.Grid container spacing={32} justify="space-evenly">
--*/
      for (FooterDsc footer : kFOOTERS)
      {
/*--
         <@material-ui.core.Grid item xs key={footer.title}>
            <@material-ui.core.Typography
               variant="h6" color="textPrimary" gutterBottom>
               {footer.title}
            </@material-ui.core.Typography>
--*/
         for (FooterTopicDsc topic : footer.topics)
         {
/*--
            <a href={topic.url} target={topic.target} class="topic" >
               <@material-ui.core.Typography
                  key={topic.topic} variant="subtitle1" color="textSecondary">
                  {topic.topic}
               </@material-ui.core.Typography>
            </a>
--*/
         }
/*--
         </@material-ui.core.Grid>
--*/
      }
/*--
      </@material-ui.core.Grid>
      <@material-ui.core.Grid container spacing={32} align="center" justify="space-evenly" >
         <a href="http://www.giavaneers.com" target="_blank">
            <img src="images/GiavaneersMark.png" class="logo" />
         </a>
         <@material-ui.core.Typography
            class="footerCredit" color="textSecondary" gutterBottom>
            Website created with React and
            <a href="http://www.reactjava.io" target="_blank">ReactJava</a>
         </@material-ui.core.Typography>
      </@material-ui.core.Grid>
   </footer>
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
   String unit       = "" + getTheme().getSpacing().getUnit() + "px";
   String unitX3     = IUITheme.cssLengthScale(unit, 3);
   String unitX45    = IUITheme.cssLengthScale(unit, 4.5);
   String unitX6     = IUITheme.cssLengthScale(unit, 6);
   String unitX8     = IUITheme.cssLengthScale(unit, 8);
/*--
   .footer
   {
      border-top:   {"1px solid " + getTheme().getPalette().getDivider()};
      margin-top:   {unitX8};
      padding:      {unitX6};
   }
   .footerCredit
   {
      margin-top:   {unitX45};
   }
   .layout
   {
      margin-left:  {unitX3};
      margin-right: {unitX3};
      width:        auto;
   }
   .logo
   {
      margin-top:   {unitX3};
      height:       {unitX6};
   }
   .topic
   {
      text-decoration: none;
   }
--*/
}
}//====================================// end Footer =========================//
