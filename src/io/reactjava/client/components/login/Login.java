/*==============================================================================

name:       Login.java

purpose:    ReactJava component handling account creation and login

history:    Sat Oct 27, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.login;
                                       // imports --------------------------- //
import com.giavaneers.util.gwt.Logger;
import elemental2.dom.DomGlobal;
import elemental2.dom.Event;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.KeyboardEvent;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.INativeEventHandler;
import io.reactjava.client.core.react.NativeObject;
import io.reactjava.client.core.rxjs.observable.Observer;
import io.reactjava.client.providers.auth.IAuthenticationService;
import io.reactjava.client.providers.auth.IAuthenticationService.IUserCredential;
import jsinterop.base.Js;
                                       // Login ==============================//
public class Login extends Component
{
                                       // class constants ------------------- //
                                       // logger                              //
public static final Logger kLOGGER = Logger.newInstance();

                                       // property names                      //
public static final String kPROPERTY_AUTHENTICATION_SVC  = "authenticationSvc";
public static final String kPROPERTY_CREATE_ACCT_ENABLED = "createAcctEnabled";
public static final String kPROPERTY_LABEL_SIGN_IN       = "labelSignIn";
public static final String kPROPERTY_LABEL_SIGN_UP       = "labelSignUp";
public static final String kPROPERTY_OBSERVER            = "observer";

                                       // elementIds                          //
public static final String kELEMENT_ID_DISPLAY_NAME      = "displayname";
public static final String kELEMENT_ID_EMAIL             = "email";
public static final String kELEMENT_ID_ERROR             = "error";
public static final String kELEMENT_ID_PASSWORD          = "password";

                                       // another state variable              //
public static final String kSTATE_CREDENTIALS_ENTERED    = "credentialsEntered";
public static final String kSTATE_ERROR                  = "error";
public static final String kSTATE_SIGN_UP                = "signUp";

                                       // default property values             //
public static final String  kLABEL_SIGN_IN_DEFAULT       = "SIGN IN";
public static final String  kLABEL_SIGN_UP_DEFAULT       = "SIGN UP";

                                       // read-only input attribute           //
public static final NativeObject kATTRIB_READ_ONLY =
   NativeObject.with("readOnly", true);
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
protected IAuthenticationService auth; // authentication service              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       createAccount - signup button-click handler
                                                                              */
                                                                             /**
            Signup button-click handler.

@history    Fri Jan 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void createAccount()
{
   String email    = getInputElementText(kELEMENT_ID_EMAIL);
   String password = getInputElementText(kELEMENT_ID_PASSWORD);

   getAuth().createUserWithEmailAndPassword(email, password).subscribe(
      (IUserCredential userCredential) ->
      {
         Observer<IUserCredential> observer =
            (Observer<IUserCredential>)props().get(kPROPERTY_OBSERVER);

         if (observer != null)
         {
            observer.next(userCredential);
            observer.complete();
         }
      },
      (String error) ->
      {
         errorPut(error);
      });
}
/*------------------------------------------------------------------------------

@name       errorPut - handle the specified error
                                                                              */
                                                                             /**
            Handle the specified error.

@param      error    error string

@history    Thu Dec 12, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void errorPut(
   String error)
{
   getInputElement(kELEMENT_ID_ERROR).value = error;
   if (error.length() > 0)
   {
      kLOGGER.logError(error);
   }
   setState(kSTATE_ERROR, error);
}
/*------------------------------------------------------------------------------

@name       errorRemove - remove any error condition
                                                                              */
                                                                             /**
            remove any error condition.

@history    Thu Dec 12, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void errorRemove()
{
   errorPut("");
}
/*------------------------------------------------------------------------------

@name       getAuth - get authentication service provider
                                                                              */
                                                                             /**
            Get authentication service provider.

@return     authentication service provider.

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected IAuthenticationService getAuth()
{
   return((IAuthenticationService)props().get(kPROPERTY_AUTHENTICATION_SVC));
}
/*------------------------------------------------------------------------------

@name       getCreateAccountEnabled - get whether can create a new account
                                                                              */
                                                                             /**
            Get whether can create a new account.

@return     true iff can create a new account

@history    Thu Dec 12, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public boolean getCreateAccountEnabled()
{
   return(props().getBoolean(kPROPERTY_CREATE_ACCT_ENABLED));
}
/*------------------------------------------------------------------------------

@name       getCredentialsEntered - get whether credentials have been entered
                                                                              */
                                                                             /**
            Get whether credentials have been entered.

@return     true iff credentials have been entered

@history    Thu Dec 12, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public boolean getCredentialsEntered()
{
   boolean getCredentialsEntered =
      getInputElementText(kELEMENT_ID_EMAIL).length() > 0
         && getInputElementText(kELEMENT_ID_PASSWORD).length() > 0;

   return(getCredentialsEntered);
}
/*------------------------------------------------------------------------------

@name       onChangeHandler - input change event handler
                                                                              */
                                                                             /**
            onChange event handler as a public instance variable.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler onChangeHandler = (Event e) ->
{
   HTMLInputElement element = (HTMLInputElement)e.target;
   switch(element.id)
   {
      case kELEMENT_ID_EMAIL:
      case kELEMENT_ID_PASSWORD:
      {
         errorRemove();
         setState(kSTATE_CREDENTIALS_ENTERED, getCredentialsEntered());
         break;
      }
   }
};
/*------------------------------------------------------------------------------

@name       getInputElement - get specified input element
                                                                              */
                                                                             /**
            Get specified input element.

@return     specified input element

@param      elementId      specified input elementId

@history    Thu Dec 12, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public HTMLInputElement getInputElement(
   String elementId)
{
   HTMLInputElement inputElement =
      (HTMLInputElement)DomGlobal.document.getElementById(elementId);

   return(inputElement);
}
/*------------------------------------------------------------------------------

@name       getInputElementText - get specified input element text
                                                                              */
                                                                             /**
            Get specified input element text.

@return     specified input element text, or the empty string if not found

@param      elementId      specified input elementId

@history    Thu Dec 12, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public String getInputElementText(
   String elementId)
{
   HTMLInputElement inputElement = getInputElement(elementId);
   return(inputElement != null ? inputElement.value : "");
}
/*------------------------------------------------------------------------------

@name       getLabelSignIn - get sign-in button title
                                                                              */
                                                                             /**
            Get sign-in button title.

@return     sign-in button title

@history    Thu Dec 12, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public String getLabelSignIn()
{
   String label = props().getString(kPROPERTY_LABEL_SIGN_IN);
   if (label == null)
   {
      label = kLABEL_SIGN_IN_DEFAULT;
   }
   return(label);
}
/*------------------------------------------------------------------------------

@name       getLabelSignUp - get sign-up button title
                                                                              */
                                                                             /**
            Get sign-up button title.

@return     sign-up button title

@history    Thu Dec 12, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public String getLabelSignUp()
{
   String label = props().getString(kPROPERTY_LABEL_SIGN_UP);
   if (label == null)
   {
      label = kLABEL_SIGN_UP_DEFAULT;
   }
   return(label);
}
/*------------------------------------------------------------------------------

@name       keyUpHandler - keyUp event handler
                                                                              */
                                                                             /**
            keyUp event handler as a public instance variable. A TextField
            does not intrinsicly support input of the RETURN character, so we
            add this keyboard event handler.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler keyUpHandler = (Event e) ->
{
   KeyboardEvent keyEvent = Js.uncheckedCast(e);
   switch(keyEvent.key)
   {
      case "Enter":
      {
         if (getCredentialsEntered())
         {
            if (getStateBoolean(kSTATE_SIGN_UP))
            {
                                       // attempt to create account           //
               createAccount();
            }
            else
            {
                                        // attempt to login                   //
               login();
            }
         }
         break;
      }
   }
};
/*------------------------------------------------------------------------------

@name       login - signin button-click handler
                                                                              */
                                                                             /**
            Signin button-click handler.

@history    Fri Jan 26, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void login()
{
   String email    = getInputElementText(kELEMENT_ID_EMAIL);
   String password = getInputElementText(kELEMENT_ID_PASSWORD);

   getAuth().signInWithEmailAndPassword(email, password).subscribe(
      (IUserCredential userCredential) ->
      {
         Observer<IUserCredential> observer =
            (Observer<IUserCredential>)props().get(kPROPERTY_OBSERVER);

         if (observer != null)
         {
            observer.next(userCredential);
            observer.complete();
         }
      },
      (String error) ->
      {
         errorPut(error);
      });
}
/*------------------------------------------------------------------------------

@name       render - render component
                                                                              */
                                                                             /**
            Render component.

@history    Sat Oct 27, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public final void render()
{
   useState(kSTATE_CREDENTIALS_ENTERED, false);
   useState(kSTATE_ERROR, "");
   useState(kSTATE_SIGN_UP, false);
/*--
   <@material-ui.core.Grid container justify="center">
      <@material-ui.core.Grid item class='contentWidth'>
         <div class='padding'>
            <@material-ui.core.TextField
               id={kELEMENT_ID_ERROR}
               margin="normal"
               error={getStateString(kSTATE_ERROR).length() > 0}
               fullWidth
               InputProps={kATTRIB_READ_ONLY}/>
--*/
            if (getStateBoolean(kSTATE_SIGN_UP))
            {
/*--
            <@material-ui.core.TextField
               id={kELEMENT_ID_DISPLAY_NAME}
               label="Display Name"
               placeholder="Enter Display Name"
               margin="normal"
               variant="outlined"
               fullWidth
               onChange={onChangeHandler} />
--*/
            }
/*--
            <@material-ui.core.TextField
               id={kELEMENT_ID_EMAIL}
               label="EMail"
               placeholder="Enter EMail"
               margin="normal"
               variant="outlined"
               fullWidth
               onChange={onChangeHandler}
               onKeyUp={keyUpHandler} />
            <@material-ui.core.TextField
               id={kELEMENT_ID_PASSWORD}
               label="Password"
               placeholder="Enter Password"
               margin="normal"
               variant="outlined"
               fullWidth
               onChange={onChangeHandler}
               onKeyUp={keyUpHandler} />
--*/
            if (!getStateBoolean(kSTATE_SIGN_UP))
            {
/*--
            <@material-ui.core.Button
               class='padtop'
               variant='contained'
               fullWidth={true}
               disabled={!getStateBoolean(kSTATE_CREDENTIALS_ENTERED)}
               onClick={signInHandler} >
               {getLabelSignIn()}
            </@material-ui.core.Button>
--*/
            }
/*--
--*/
            if (getCreateAccountEnabled())
            {
/*--
            <@material-ui.core.Button
               class='padtop'
               variant='contained'
               fullWidth={true}
               onClick={signUpHandler} >
               {getLabelSignUp()}
            </@material-ui.core.Button>
--*/
            }
/*--
         </div>
      </@material-ui.core.Grid>
   </@material-ui.core.Grid>
--*/
};
/*------------------------------------------------------------------------------

@name       renderCSS - get component css
                                                                              */
                                                                             /**
            Get component css.

@history    Sat Oct 27, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
/*--
   .padding
   {
      padding:          16px;
      display:          block;
      box-sizing:       border-box;
   }
   .padtop
   {
      margin-top:       20px;
   }
   .signup
   {
      margin-top:       20px;
   }
   .contentWidth
   {
   }
   @media (min-width: 320px)
   {
      .contentWidth {width: 300px;}
   }
   @media (min-width: 576px)
   {
      .contentWidth {width: 540px;}
   }
   @media (min-width: 768px)
   {
      .contentWidth {width: 720px;}
   }
   @media (min-width: 992px)
   {
      .contentWidth {width: 960px;}
   }
   @media (min-width: 1200px)
   {
      .contentWidth {width: 1140px;}
   }
--*/
}
/*------------------------------------------------------------------------------

@name       signInHandler - signIn onClick event handler
                                                                              */
                                                                             /**
            SignIn onClick event handler as a public instance variable.

@history    Sat Oct 27, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler signInHandler = (Event e) ->
{
   login();
};
/*------------------------------------------------------------------------------

@name       signUpHandler - signUp onClick event handler
                                                                              */
                                                                             /**
            SignUp onClick event handler as a public instance variable.

@history    Sat Oct 27, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler signUpHandler = (Event e) ->
{
   if (getStateBoolean(kSTATE_SIGN_UP))
   {
      createAccount();
   }
   else
   {
      setState(kSTATE_SIGN_UP, true);
   }
};
}//====================================// end Login ==========================//
