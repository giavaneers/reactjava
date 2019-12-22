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
import elemental2.dom.Element;
import io.reactjava.client.components.generalpage.Descriptors.FooterDsc;
import io.reactjava.client.components.generalpage.Descriptors.FooterDsc.FooterCategoryDsc;
import io.reactjava.client.components.generalpage.Descriptors.FooterDsc.FooterCategoryDsc.FooterTopicDsc;
import io.reactjava.client.components.generalpage.Descriptors.FooterDsc.FooterCreditDsc;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.IUITheme;
import io.reactjava.client.core.react.Properties;

                                       // Footer =============================//
public class Footer<P extends Properties> extends Component
{
                                       // class constants ------------------- //
                                       // property keys                       //
public static final String    kKEY_FOOTER     = "footer";
public static final FooterDsc kFOOTER_DEFAULT = new FooterDsc();

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
protected FooterDsc footer;            // footer descriptor                   //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       getFooters - get footer descriptor
                                                                              */
                                                                             /**
            Get footer descriptor.

@return     footer descriptor

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected FooterDsc getFooter()
{
   if (footer == null)
   {
      footer = (FooterDsc)props().get(kKEY_FOOTER);
      if (footer == null)
      {
         footer = kFOOTER_DEFAULT;
      }
   }
   return(footer);
}
/*------------------------------------------------------------------------------

@name       render - render component
                                                                              */
                                                                             /**
            Render component.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public final void render()
{
/*--
   <footer class="footer layout">
      <@material-ui.core.Grid container spacing={32} justify="space-evenly">
--*/
      FooterCategoryDsc[] categories = getFooter().categories;
      if (categories != null)
      {
         for (FooterCategoryDsc category : getFooter().categories)
         {
/*--
         <@material-ui.core.Grid item xs key={category.title}>
            <@material-ui.core.Typography
               variant="h6" color="textPrimary" gutterBottom>
               {category.title}
            </@material-ui.core.Typography>
--*/
            for (FooterTopicDsc topic : category.topics)
            {
               String url = topic.url;
               if (url.startsWith("#"))
               {
                  url = DomGlobal.location.getPathname() + url;
               }
/*--
            <a href={url} target={topic.target} class="topic" >
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
      }
/*--
      </@material-ui.core.Grid>
      <@material-ui.core.Grid container spacing={32} align="center" justify="space-evenly" >
         <div id="footerCreditLogo">
         </div>
         <@material-ui.core.Typography
            id="footerCreditText"
            class="footerCredit"
            color="textSecondary"
            gutterBottom>
         </@material-ui.core.Typography>
      </@material-ui.core.Grid>
   </footer>
--*/
   renderAnyCredit();
}
/*------------------------------------------------------------------------------

@name       renderAnyCredit - render any credit
                                                                              */
                                                                             /**
            Render any credit.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void renderAnyCredit()
{
   FooterCreditDsc credit = getFooter().credit;
   if (credit != null)
   {
      DomGlobal.setTimeout(
      (e) ->
      {
         Element element;
         if (credit.logo != null)
         {
            element = DomGlobal.document.getElementById("footerCreditLogo");
            if (element != null)
            {
               element.innerHTML = credit.logo;
            }
         }
         if (credit.text != null)
         {
            element = DomGlobal.document.getElementById("footerCreditText");
            if (element != null)
            {
               element.innerHTML = credit.text;
            }
         }
      }, 0);
   }
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
