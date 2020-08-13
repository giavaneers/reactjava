/*==============================================================================

name:       OnSubscribe.java

purpose:    OnSubscribe

history:    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

notes:      from @author dtimofeev since 20.12.2016.

                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import jsinterop.annotations.JsFunction;
import io.reactjava.client.core.rxjs.subscription.TearDownSubscription;

                                       // OnSubscribe ========================//
@JsFunction
public interface OnSubscribe<T>
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //

TearDownSubscription call(Subscriber<T> subscriber);

}//====================================// OnSubscribe ========================//
