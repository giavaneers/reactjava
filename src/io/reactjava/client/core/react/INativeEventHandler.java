/*==============================================================================

name:       INativeEventHandler.java

purpose:    ReactJava native event handler interface.

            Unlike elemental2.dom.EventListener, presented to react.js as a
            javascript function.

history:    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

notes:

                  This program was created by Giavaneers
        and is the confidential and proprietary product of Giavaneers Inc.
      Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2018 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import elemental2.dom.Event;
import jsinterop.annotations.JsFunction;
                                       // INativeEventHandler ================//
@JsFunction
public interface INativeEventHandler
{
   void handleEvent(Event evt);
}//====================================// end INativeEventHandler ============//
