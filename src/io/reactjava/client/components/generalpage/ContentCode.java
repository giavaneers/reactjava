/*==============================================================================

name:       ContentCode.java

purpose:    Content code.

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

                                       // ContentCode ========================//
public class ContentCode extends Component
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
/*--
   <div class='contentCode'>
      <Prism background='#666666'>
         {props().getString("content")}
      </Prism>
   </div>
--*/
}
/*------------------------------------------------------------------------------

@name       renderCSS - get component css
                                                                              */
                                                                             /**
            Get component css.

@return     void

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
   String unit   = "" + getTheme().getSpacing().getUnit() + "px";
   String unit3X = IUITheme.cssLengthScale(unit, 3);
/*--
   .contentCode
   {
      font-size:     12px;
      margin-top:    {unit3X};
      margin-bottom: {unit3X};
   }
--*/                                   // override some prism token colors    //
/*--
   .token.comment
   {
      color:         #6dcff6;
   }
--*/
}
}//====================================// end ContentCode ====================//
