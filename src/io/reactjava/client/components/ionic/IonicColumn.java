/*==============================================================================

name:       IonicColumn.java

purpose:    ReactJava component implemenation of IonicColumn.

history:    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                  This program was created by Giavaneers
         and is the confidential and proprietary product of Giavaneers Inc.
       Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2017 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.


==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.ionic;
                                       // imports --------------------------- //
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.Properties;

public class IonicColumn extends Component
{
/*------------------------------------------------------------------------------

@name       IonicColumn - default constructor
                                                                              */
                                                                             /**
            Default constructor.

@return     An instance of IonicColumn if successful.

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public IonicColumn(
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
   <div class='column'>
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
   .column
   {
      flex-grow:   0;
      flex-shrink: 0;
      flex-basis: 100%;
      width:      100%;
      max-width:  100%;
      padding:    5px !important;
      position:   relative;
      margin:     0px !important;
      box-sizing: border-box;
   }
--*/
}
}//====================================// end IonicColumn ====================//
