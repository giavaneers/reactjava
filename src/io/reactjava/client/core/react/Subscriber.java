/*==============================================================================

name:       Subscriber.java

purpose:    Subscriber

history:    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import jsinterop.annotations.JsType;
import io.reactjava.client.core.rxjs.subscription.Subscription;

                                       // Subscriber =========================//
@JsType(namespace = "ReactJava", isNative = true)
public class Subscriber<T> extends Subscription
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

public native void error(Object object);

public native void next(T value);

public native void complete();
}//====================================// end Subscriber ---------------------//
