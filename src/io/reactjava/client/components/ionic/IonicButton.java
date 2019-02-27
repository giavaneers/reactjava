/*==============================================================================

name:       IonicButton.java

purpose:    ReactJava component implemenation of IonicButton.

history:    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                  This program was created by Giavaneers
         and is the confidential and proprietary product of Giavaneers Inc.
       Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2018 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.


==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.ionic;
                                       // imports --------------------------- //
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.Properties;

                                       // IonicButton ========================//
public class IonicButton extends Component
{
/*------------------------------------------------------------------------------

@name       IonicButton - default constructor
                                                                              */
                                                                             /**
            Default constructor.

@return     An instance of IonicButton if successful.

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IonicButton(
   Properties props)
{
   super(props);
}
/*------------------------------------------------------------------------------

@name       render - render markup
                                                                              */
                                                                             /**
            Render markup.

@return     void

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void render()
{
   String buttonClass = "ionButtonMd ionButtonBlock ionButtonBlockMd ionButton";
   if ("true".equals(getProperties().getString("login")))
   {
      buttonClass += " ionButtonLogin";
   }
/*--
   <button class={buttonClass} onClick={props.getEventHandler("onClick")}>
      <span class='buttonInner'>{props.getString("text")}</span>
      <div class='buttonEffect'/>
   </button>
--*/
}
/*------------------------------------------------------------------------------

@name       renderCSS - parse styles
                                                                              */
                                                                             /**
            Parse styles.

@return     void

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
/*--
   button
   {
      -webkit-appearance: button;
      border:             0;
      cursor:             pointer;
      font-family:        inherit;
      font-style:         inherit;
      font-variant:       inherit;
      line-height:        1;
      text-transform:     none;
   }
   .ionButton
   {
      appearance:           none;
      -moz-appearance:      none;
      -ms-appearance:       none;
      -webkit-appearance:   none;
      contain:              content;
      cursor:               pointer;
      display:              inline-block;
      font-kerning:         none;
      -webkit-font-kerning: none;
      position:             relative;
      text-align:           center;
      text-decoration:      none;
      text-overflow:        ellipsis;
      text-transform:       none;
      transition:           background-color, opacity 100ms linear;
      -webkit-transition:   background-color, opacity 100ms linear;
      user-select:          none;
      -moz-user-select:     none;
      -ms-user-select:      none;
      -webkit-user-select:  none;
      vertical-align:       top;
      vertical-align:       -webkit-baseline-middle;
      white-space:          nowrap;
      z-index:              0;
   }
   .ionButtonBlock
   {
      clear:   both;
      contain: strict;
      display: block;
      width:   100%;
   }
   .ionButtonMd
   {
      background-color:   #488aff;
      border-radius:      2px;
      box-shadow:         0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.2), 0 1px 5px 0 rgba(0, 0, 0, 0.12);
      -webkit-box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.2), 0 1px 5px 0 rgba(0, 0, 0, 0.12);
      color:              #fff;
      font-size:          1.4rem;
      font-weight:        500;
      height:             3.6rem;
      margin:             0.4rem 0.2rem;
      overflow:           hidden;
      padding:            0 1.1em;
      text-transform:     uppercase;
      transition:         background-color 300ms cubic-bezier(0.4, 0, 0.2, 1), color 300ms cubic-bezier(0.4, 0, 0.2, 1), -webkit-box-shadow 300ms cubic-bezier(0.4, 0, 0.2, 1);
      transition:         box-shadow 300ms cubic-bezier(0.4, 0, 0.2, 1), background-color 300ms cubic-bezier(0.4, 0, 0.2, 1), color 300ms cubic-bezier(0.4, 0, 0.2, 1);
      transition:         box-shadow 300ms cubic-bezier(0.4, 0, 0.2, 1), background-color 300ms cubic-bezier(0.4, 0, 0.2, 1), color 300ms cubic-bezier(0.4, 0, 0.2, 1), -webkit-box-shadow 300ms cubic-bezier(0.4, 0, 0.2, 1);
      -webkit-transition: background-color 300ms cubic-bezier(0.4, 0, 0.2, 1), color 300ms cubic-bezier(0.4, 0, 0.2, 1), -webkit-box-shadow 300ms cubic-bezier(0.4, 0, 0.2, 1);
   }
   .ionButtonBlockMd
   {
      margin-left:  0;
      margin-right: 0;
   }
   .ionButtonMd:hover:not(.disable-hover)
   {
      background-color: #488aff;
   }
   .ionButton:focus, :active
   {
      outline: none;
   }
   .ionButtonLogin
   {
      background-color:   white;
      box-shadow:         0px 1px 2px rgba(0, 0, 0, 0.2);
      -webkit-box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.2);
      color:              #333;
      margin-top:         25px;
   }
   .buttonEffect
   {
      background-color:           rgb(255, 255, 255);
      border-radius:              50% !important;
      box-sizing:                 border-box;
      display:                    block;
      left:                       0px;
      opacity:                    0.2;
      pointer-events:             none;
      position:                   absolute;
      top:                        0px;
      touch-action:               manipulation;
      transform-origin:           center center 0px;
      transition-timing-function: ease-in-out;
      z-index:                    0;
   }
   .buttonInner
   {
      align-items:       center;
      box-sizing:        border-box;
      -webkit-box-pack:  center;
      -webkit-box-align: center;
      display:           flex;
      flex-flow:         row nowrap !important;
      flex-shrink:       0;
      height:            100%;
      justify-content:   center;
      touch-action:      manipulation;
      width:             100%;
   }
--*/
}
}//====================================// end IonicButton ====================//
