/*==============================================================================

name:       IonicGrid.java

purpose:    ReactJava component implemenation of IonicGrid.

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

                                       // IonicGrid ==========================//
public class IonicGrid extends Component
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
   String divClass = "grid";
   if (props.getBoolean("fixed"))
   {
      divClass += " gridFixed";
   }
/*--
   <div class={divClass}>
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
   .grid
   {
      padding:        5px;
      margin-left:    auto;
      margin-right:   auto;
      display:        flex;
      flex-direction: column;
      width:          100%;
      max-width:      100%;
      height:         90%;
   }

   @media (min-width: 576px) {.grid{width: 540px;}}
   @media (min-width: 768px) {.grid{width: 720px;}}
   @media (min-width: 992px) {.grid{width: 960px;}}
   @media (min-width: 1200px){.grid{width: 1140px;}}
--*/
}
}//====================================// end IonicGrid ====================//
