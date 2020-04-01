/*==============================================================================

name:       INativeFunction2Args.java

purpose:    ReactJava native function interface.

            Presented to react.js as a javascript function.

history:    Tue Feb 25, 2030 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import jsinterop.annotations.JsFunction;
                                       // INativeFunction2Args ===============//
@JsFunction
public interface INativeFunction2Args<T,U>
{
   void call(T arg1, U arg2);
}//====================================// end INativeFunction2Args ===========//
