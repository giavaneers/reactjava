/*==============================================================================

name:       IonicScrollContent.java

purpose:    ReactJava component implemenation of IonicScrollContent.

history:    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                     COPYRIGHT (c) BY GIAVANEERS, INC.
      This source code is licensed under the MIT license found in the
          LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.ionic;
                                       // imports --------------------------- //
import io.reactjava.client.core.react.Component;

                                       // IonicScrollContent =================//
public class IonicScrollContent extends Component
{
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
