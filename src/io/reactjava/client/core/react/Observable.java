/*==============================================================================

name:       Observable.java

purpose:    Observable

history:    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import elemental2.promise.Promise;
import io.reactjava.client.core.rxjs.functions.Action0;
import io.reactjava.client.core.rxjs.functions.Action1;
import io.reactjava.client.core.rxjs.observable.Observer;
import io.reactjava.client.core.rxjs.subscription.Subscription;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
                                       // Observable =========================//
@JsType(namespace = "ReactJava", isNative = true)
public class Observable<T>
   extends io.reactjava.client.core.rxjs.observable.Observable<T>
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
@JsProperty
public Promise promise;                // property on PromiseObservable       //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       create - create
                                                                              */
                                                                             /**
            Create.

@param      onSubscribe    on subscribe

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public native static <R> Observable<R> create(
   OnSubscribe<? extends R> onSubscribe);

/*------------------------------------------------------------------------------

@name       create - create
                                                                              */
                                                                             /**
            Create.

@param      promise      promise

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public static <T> Observable<T> fromPromise(
   Promise<T> promise)
{
   return(
      Js.uncheckedCast(
         io.reactjava.client.core.rxjs.observable.Observable.fromPromise(
            promise)));
}
/*------------------------------------------------------------------------------

@name       subscribe - subscribe
                                                                              */
                                                                             /**
            Create.

@return     subscription

@param      subscriber  subscribing component
@param      onNext      onNext

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Subscription subscribe(
   Component          subscriber,
   Action1<? super T> onNext)
{
   Subscription subscription =
      subscriber.getMounted()
         ? subscriber.addSubscription(super.subscribe(onNext))
         : null;

   return(subscription);
}
/*------------------------------------------------------------------------------

@name       subscribe - subscribe
                                                                              */
                                                                             /**
            Create.

@return     subscription

@param      subscriber  subscribing component
@param      onNext      onNext
@param      onError     onError

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Subscription subscribe(
   Component          subscriber,
   Action1<? super T> onNext,
   Action1<?>         onError)
{
   Subscription subscription =
      subscriber.getMounted()
         ? subscriber.addSubscription(super.subscribe(onNext, onError))
         : null;

   return(subscription);
}
/*------------------------------------------------------------------------------

@name       subscribe - subscribe
                                                                              */
                                                                             /**
            Create.

@return     subscription

@param      subscriber     subscribing component
@param      onNext         onNext
@param      onError        onError
@param      onCompleted    onCompleted

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Subscription subscribe(
   Component          subscriber,
   Action1<? super T> onNext,
   Action1<?>         onError,
   Action0            onCompleted)
{
   Subscription subscription =
      subscriber.getMounted()
         ? subscriber.addSubscription(
            super.subscribe(onNext, onError, onCompleted))
         : null;

   return(subscription);
}
/*------------------------------------------------------------------------------

@name       subscribe - subscribe
                                                                              */
                                                                             /**
            Create.

@return     subscription

@param      subscriber  subscribing component
@param      observer    observer

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsOverlay
public final Subscription subscribe(
   Component subscriber,
   Observer  observer)
{
    Subscription subscription =
      subscriber.getMounted()
         ? subscriber.addSubscription(super.subscribe(observer))
         : null;

  return(subscription);
}
}//====================================// end Observable ---------------------//
