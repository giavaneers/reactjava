/*==============================================================================

name:       INativeEffectHandler.java

purpose:    ReactJava native effect handler interface.

            Unlike elemental2.dom.EventListener, presented to react.js as a
            javascript function.

history:    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import jsinterop.annotations.JsFunction;
                                       // INativeEffectHandler ===============//
@JsFunction
public interface INativeEffectHandler
{
   void handleEffect();
}//====================================// end INativeEffectHandler ===========//
