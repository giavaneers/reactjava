/*==============================================================================

name:       FirebaseAuthenticationService.java

purpose:    Firebase Authentication Service.

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:      '-generateJsInteropExports' must be included in Dev Mode parameters
            setting of GWT debug configuration for java methods to be
            exported to javascript

                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.providers.auth.firebase;

                                       // imports --------------------------- //
import com.giavaneers.util.gwt.Logger;
import elemental2.promise.Promise;
import io.reactjava.client.core.providers.auth.IAuthenticationService;
import io.reactjava.client.core.react.Properties;
import io.reactjava.client.core.rxjs.observable.Observable;
import io.reactjava.client.core.rxjs.observable.Subscriber;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
                                       // FirebaseAuthenticationService ======//
@JsType                                // export to Javascript                //
public class FirebaseAuthenticationService implements IAuthenticationService
{
                                       // class constants --------------------//
private static final Logger kLOGGER = Logger.newInstance();

                                       // class variables ------------------- //
                                       // (none)
                                       // public instance variables --------- //
                                       // (none)
                                       // protected instance variables -------//
                                       // (none)
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       FirebaseAuthenticationService - constructor
                                                                              */
                                                                             /**
            Constructor

@param      props    props

@history    Wed Apr 27, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public FirebaseAuthenticationService(
   Properties props)
{
}
/*------------------------------------------------------------------------------

@name       configure - configuration routine
                                                                              */
                                                                             /**
            Configuration routine.

@return     void

@param      configurationData    configuration data

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Observable configure(
   Object configurationData)
{
   return(FirebaseCore.configure(configurationData));
}
/*------------------------------------------------------------------------------

@name       createUserWithEmailAndPassword  - create user with email and pswd
                                                                              */
                                                                             /**
            Creates a new user account associated with the specified email
            address and password.

            On successful creation of the user account, this user will also be
            signed in to the application.

            User account creation can fail if the account already exists or the
            password is invalid.

@return     void

@param      email       email
@param      password    password

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public Observable<String> createUserWithEmailAndPassword(
   String email,
   String password)
{
   Observable<String> observable = Observable.create(
      (Subscriber<String> subscriber) ->
      {
         Promise promise =
            getAuth().createUserWithEmailAndPassword(email, password);

         promise.then(
            response ->
            {
               subscriber.next(response.toString());
               subscriber.complete();
               return(promise);
            },
            error ->
            {
               subscriber.error(FirebaseCore.getErrorMessage(error));
               return(null);
            });

         return(subscriber);
      });
   return(observable);
}
/*------------------------------------------------------------------------------

@name       getAuth - get auth
                                                                              */
                                                                             /**
            Get auth.

@return     auth

@history    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Auth getAuth()
{
   return((Auth)Js.uncheckedCast(FirebaseCore.getAuth()));
}
/*------------------------------------------------------------------------------

@name       initNative - get the Auth service for the default app
                                                                              */
                                                                             /**
            Get the Auth service for the default app

@param      defaultAuth    default authentication provider

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public final static native void initNative(
   Auth defaultAuth)
   throws Exception
/*-{
      var email    = 'giavaneersdeveloper@gmail.com';
      var password = '1545 1/2 Pacific Avenue';
      try
      {
         var promise;
         promise = defaultAuth.createUserWithEmailAndPassword(email, password)
            .then(function(user)
            {
                                       // promise succeeded                   //
               promise = defaultAuth.signOut();
               return(promise);
            })
            .then(function()
            {
                                       // promise succeeded                   //
               promise = defaultAuth.signInWithEmailAndPassword(email, password);
               return(promise);
            })
            .then(function(user)
            {
                                       // promise succeeded                   //
               promise = defaultAuth.signOut();
               return(promise);
            })
            .then(function(user)
            {
                                       // promise succeeded                   //
               console.log("Successful!");
            },
            function(error)
            {
               switch(error.code)
               {
                  case 'auth/email-already-in-use':
                  {
                                       // createUserWithEmailAndPassword()    //
                     $wnd.console.log(error.message);
                     break;
                  }
                  case 'auth/operation-not-allowed':
                  {
                                       // createUserWithEmailAndPassword()    //
                     $wnd.console.log(error.message);
                     break;
                  }
                  case 'auth/weak-password':
                  {
                                       // createUserWithEmailAndPassword()    //
                     $wnd.console.log(error.message);
                     break;
                  }
                  case 'auth/invalid-email':
                  {
                                       // createUserWithEmailAndPassword()    //
                                       // signInWithEmailAndPassword()        //
                     $wnd.console.log(error.message);
                     break;
                  }
                  case 'auth/user-disabled':
                  {
                                       // signInWithEmailAndPassword()        //
                     $wnd.console.log(error.message);
                     break;
                  }
                  case 'auth/user-not-found':
                  {
                                       // signInWithEmailAndPassword()        //
                     $wnd.console.log(error.message);
                     break;
                  }
                  case 'auth/wrong-password':
                  {
                                       // signInWithEmailAndPassword()        //
                     $wnd.console.log(error.message);
                     break;
                  }
                  default:
                  {
                     $wnd.console.log(error.message);
                  }
               }
            });
      }
      catch (err)
      {
         $wnd.console.log("Could not get the Auth service for the default app");
         $wnd.console.log(err.message);
      }
}-*/;
/*------------------------------------------------------------------------------

@name       signInWithEmailAndPassword - sign in with email and password
                                                                              */
                                                                             /**
            Asynchronously signs in using an email and password.

            Fails with an error if the email address and password do not match.

@return     void

@param      email       email
@param      password    password

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Observable<String> signInWithEmailAndPassword(
   String email,
   String password)
{
   Observable<String> observable = Observable.create(
      (Subscriber<String> subscriber) ->
      {
         Promise promise = getAuth().signInWithEmailAndPassword(email, password);
         promise.then(
            user ->
            {
               subscriber.next(user.toString());
               subscriber.complete();
               return(promise);
            },
            error ->
            {
               subscriber.error(FirebaseCore.getErrorMessage(error));
               return(null);
            });

         return(subscriber);
      });
   return(observable);
}
/*------------------------------------------------------------------------------

@name       signOut - sign out
                                                                              */
                                                                             /**
            Signs out the current user.

@return     non-null firebase.Promise containing void

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Observable<String> signOut()
{
   Observable<String> observable = Observable.create(
      (Subscriber<String> subscriber) ->
      {
         Promise promise = getAuth().signOut();
         promise.then(
            result ->
            {
               subscriber.next(result.toString());
               subscriber.complete();
               return(promise);
            },
            error ->
            {
               subscriber.error(FirebaseCore.getErrorMessage(error));
               return(null);
            });

         return(subscriber);
      });
   return(observable);
}
/*------------------------------------------------------------------------------

@name       unitTest - unit test
                                                                              */
                                                                             /**
            Unit test for either the callback or promise interface.

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void unitTest()
{
   String  email    = "giavaneersdeveloper@gmail.com";
   String  password = "1545 1/2 Pacific Avenue";

   createUserWithEmailAndPassword(email, password)
      .subscribe(
         user ->
         {
            signOut().subscribe(
               result ->
               {
                  signInWithEmailAndPassword(email, password)
                  .subscribe(
                     userNew ->
                     {
                        signOut().subscribe(
                           resultNew ->
                           {
                              kLOGGER.logInfo("Success!");
                           });
                     });
               });
         },
         error ->
         {
            kLOGGER.logError((Throwable)error);
         });
}
/*==============================================================================

name:       Auth - core compatible Firebase Auth service

purpose:    GWT compatible Firebase Auth service

history:    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
@jsinterop.annotations.JsType(
   isNative = true, namespace = "firebase.auth", name = "Auth")
public static class Auth
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       createUserWithEmailAndPassword  - create user with email and pswd
                                                                              */
                                                                             /**
            Creates a new user account associated with the specified email
            address and password.

            On successful creation of the user account, this user will also be
            signed in to the application.

            User account creation can fail if the account already exists or the
            password is invalid.

            Note: The email address acts as a unique identifier for the user and
            enables an email-based password reset. This function will create a
            new user account and set the initial user password.

            Error Codes:
               auth/email-already-in-use
               Thrown if there already exists an account with the given email
               address.

               auth/invalid-email
               Thrown if the email address is not valid.

               auth/operation-not-allowed
               Thrown if email/password accounts are not enabled. Enable
               email/password accounts in the Firebase Console, under the Auth
               tab.

               auth/weak-password
               Thrown if the password is not strong enough.

@return     firebase.Promise containing non-null firebase.User

@param      email       email
@param      password    password

@example
            firebase.auth().createUserWithEmailAndPassword(email, password)
                .catch(function(error) {
              // Handle Errors here.
              var errorCode = error.code;
              var errorMessage = error.message;
              if (errorCode == 'auth/weak-password') {
                alert('The password is too weak.');
              } else {
                alert(errorMessage);
              }
              console.log(error);
            });

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native Promise createUserWithEmailAndPassword(
   String email,
   String password);

/*------------------------------------------------------------------------------

@name       signInWithEmailAndPassword - sign in with email and password
                                                                              */
                                                                             /**
            Asynchronously signs in using an email and password.

            Fails with an error if the email address and password do not match.

            Note: The user's password is NOT the password used to access the
            user's email account. The email address serves as a unique
            identifier for the user, and the password is used to access the
            user's account in your Firebase project.

            Error Codes:
               auth/invalid-email
               Thrown if the email address is not valid.

               auth/user-disabled
               Thrown if the user corresponding to the given email has been
               disabled.

               auth/user-not-found
               Thrown if there is no user corresponding to the given email.

               auth/wrong-password
               Thrown if the password is invalid for the given email, or the
               account corresponding to the email does not have a password set.

@return     firebase.Promise containing non-null firebase.User

@param      email       email
@param      password    password

@example
            firebase.auth().signInWithEmailAndPassword(email, password)
                .catch(function(error) {
              // Handle Errors here.
              var errorCode = error.code;
              var errorMessage = error.message;
              if (errorCode === 'auth/wrong-password') {
                alert('Wrong password.');
              } else {
                alert(errorMessage);
              }
              console.log(error);
            });

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native Promise signInWithEmailAndPassword(
   String email,
   String password);

/*------------------------------------------------------------------------------

@name       signOut - sign out
                                                                              */
                                                                             /**
            Signs out the current user.

@return     non-null firebase.Promise containing void

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@JsMethod
public native Promise signOut();

}//====================================// Auth ===============================//
}//====================================// end FirebaseAuthenticationService ==//
