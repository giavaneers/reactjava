/*==============================================================================

name:       IAuthenticationService.java

purpose:    Authentication Service Interface.

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

note:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.providers.auth;
                                       // imports --------------------------- //
import io.reactjava.client.core.rxjs.observable.Observable;
import io.reactjava.client.core.react.IProvider;

                                       // IAuthenticationService =============//
public interface IAuthenticationService extends IProvider
{
                                       // class constants --------------------//
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
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
Observable configure(
   Object configurationData);

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
Observable<String> createUserWithEmailAndPassword(
   String email,
   String password);

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
Observable<String> signInWithEmailAndPassword(
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
Observable<String> signOut();

/*------------------------------------------------------------------------------

@name       unitTest - unit test
                                                                              */
                                                                             /**
            Unit test for either the callback or promise interface.

@history    Sat Oct 21, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void unitTest();

}//====================================// end IAuthenticationService =========//
