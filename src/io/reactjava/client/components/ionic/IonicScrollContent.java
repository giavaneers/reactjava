/*==============================================================================

name:       IonicScrollContent.java

purpose:    ReactJava component implemenation of IonicScrollContent.

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

                                       // IonicScrollContent =================//
public class IonicScrollContent extends Component
{
/*------------------------------------------------------------------------------

@name       IonicScrollContent - default constructor
                                                                              */
                                                                             /**
            Default constructor.

@return     An instance of IonicScrollContent if successful.

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IonicScrollContent(
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
/*--
   <div class='scrollContent'>
      {getChildren()}
   </div>
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
   .scrollContent
   {
      left:                       0px;
      right:                      0px;
      top:                        0px;
      bottom:                     0px;
      position:                   absolute;
      z-index:                    1;
      display:                    block;
      overflow-x:                 hidden;
      overflow-y:                 scroll;
      -webkit-overflow-scrolling: touch;
      will-change:                scroll-position;
      contain:                    size style layout;
      box-sizing:                 border-box;
      color:                      rgb(0,0,0);
   }
--*/
}
}//====================================// end IonicScrollContent =============//
