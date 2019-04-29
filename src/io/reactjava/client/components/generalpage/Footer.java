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
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.IUITheme;

                                       // Footer =============================//
public class Footer extends Component
{
                                       // class constants ------------------- //
public static final FooterDsc[] kFOOTERS =
{
   new FooterDsc(
      "Project",
      new String[]
      {
         "Team", "History", "Contact us"
      }),
   new FooterDsc(
      "Features",
      new String[]
      {
         "Cool stuff", "Random feature", "Team feature", "Developer stuff",
         "Another one"
      }),
   new FooterDsc(
      "Resources",
      new String[]
      {
         "Resource", "Resource name", "Another resource", "Final resource"
      }),
   new FooterDsc(
      "Legal",
      new String[]
      {
         "License", "Privacy policy", "Terms of use"
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
         for (String description : footer.descriptions)
         {
/*--
            <@material-ui.core.Typography
               key={description} variant="subtitle1" color="textSecondary">
               {description}
            </@material-ui.core.Typography>
--*/
         }
/*--
         </@material-ui.core.Grid>
--*/
      }
/*--
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
   String unitX6     = IUITheme.cssLengthScale(unit, 6);
   String unitX8     = IUITheme.cssLengthScale(unit, 8);
/*--
   .footer
   {
      border-top: {"1px solid " + getTheme().getPalette().getDivider()};
      margin-top: {unitX8};
      padding:    {unitX6};
   }
   .layout
   {
      margin-left:  {unitX3};
      margin-right: {unitX3};
      width:        auto;
   }
--*/
}
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
public String   title;                 // title                               //
public String[] descriptions;          // descriptions                        //
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
   String   title,
   String[] descriptions)
{
   this.title         = title;
   this.descriptions  = descriptions;
}
}//====================================// end FooterDsc ======================//
}//====================================// end Footer =========================//
