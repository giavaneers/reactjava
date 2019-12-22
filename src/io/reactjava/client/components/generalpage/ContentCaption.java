/*==============================================================================

name:       ContentCaption.java

purpose:    Content caption.

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

                                       // ContentCaption =====================//
public class ContentCaption extends Component
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

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public final void render()
{
   String content = props().getString("content");
   String id      = Integer.toString(content.hashCode());
/*--
   <@material-ui.core.Grid container justify="flex-start" spacing={16} >
      <@material-ui.core.Grid key=0 item>
         <@material-ui.core.Typography
            component="h1" variant="h5" class="contentCaption" id={id}>
            {content}
         </@material-ui.core.Typography>
      </@material-ui.core.Grid>
   </@material-ui.core.Grid>
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
   String unit3X = IUITheme.cssLengthScale(unit, 3);
/*--
   .contentCaption
   {
      color:         #367DA2;
      flex-grow:     1;
      margin-top:    {unit3X};
      margin-bottom: {unit3X};
   }
--*/
}
}//====================================// end ContentCaption =================//
