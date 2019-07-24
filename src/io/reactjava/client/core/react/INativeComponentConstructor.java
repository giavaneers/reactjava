/*==============================================================================

name:       INativeComponentConstructor.java

purpose:    ReactJava native component constructor interface.

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
                                       // INativeComponentConstructor ========//
@JsFunction
public interface INativeComponentConstructor<P extends Properties>
{
   void create(P props);
}//====================================// end INativeComponentConstructor ====//
