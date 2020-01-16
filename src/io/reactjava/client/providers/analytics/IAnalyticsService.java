/*==============================================================================

name:       IAnalyticsService.java

purpose:    Analytics Service Interface.

history:    Tue Dec 31, 2019 10:30:00 (Giavaneers - LBM) created

note:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.providers.analytics;
                                       // imports --------------------------- //
import io.reactjava.client.core.react.IProvider;

                                       // IAnalyticsService ==================//
public interface IAnalyticsService extends IProvider
{
                                       // class constants --------------------//
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       exception - report an exception
                                                                              */
                                                                             /**
            Report an exception.

@param      description    description
@param      fatal          whether is a fatal error

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void exception(
   String  description,
   boolean fatal);

/*------------------------------------------------------------------------------

@name       ga - original ga function
                                                                              */
                                                                             /**
            The original ga function can be accessed via this method.

@param      args     args

@history    Wed Mar 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void ga(
   Object... args);

/*------------------------------------------------------------------------------

@name       logEvent  - send analytics event with any specified event params
                                                                              */
                                                                             /**
            Send analytics event with any specified event params.

@param      eventName      event name

@history    Tue Dec 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void logEvent(
   EventNames eventName);

/*------------------------------------------------------------------------------

@name       logEvent  - send analytics event with any specified event params
                                                                              */
                                                                             /**
            Send analytics event with any specified event params.

@param      category             a top level category (required)
@param      action               description of the behaviour (required)

@history    Tue Dec 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void logEvent(
   String  category,
   String  action);

/*------------------------------------------------------------------------------

@name       logEvent  - send analytics event with any specified event params
                                                                              */
                                                                             /**
            Send analytics event with any specified event params.

@param      category             a top level category (required)
@param      action               description of the behaviour (required)
@param      label                more precise labelling of the action (optional)
@param      value                numerical value (optional)
@param      bNonInteraction      if an event is not triggered by a user
                                 interaction, but instead by the code
                                 (e.g. on page load), it should be flagged as a
                                 nonInteraction event to avoid skewing bounce
                                 rate data  (optional)
@param      transport            transport mechanism with which hits will be
                                 sent, one of 'beacon', 'xhr', or 'image'
                                 (optional)

@history    Tue Dec 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
void logEvent(
   String  category,
   String  action,
   String  label,
   Integer value,
   Boolean bNonInteraction,
   String  transport);

/*==============================================================================

name:       EventNames - enumeration of supported event names

purpose:    enumeration of supported event names

history :   Tue Dec 31, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static enum EventNames
{
   ADD_PAYMENT_INFO("add_payment_info"),
   ADD_TO_CART("add_to_cart"),
   ADD_TO_WISHLIST("add_to_wishlist"),
   BEGIN_CHECKOUT("begin_checkout"),
   CHECKOUT_PROGRESS("checkout_progress"),
   EXCEPTION("exception"),
   GENERATE_LEAD("generate_lead"),
   LOGIN("login"),
   PAGE_VIEW("page_view"),
   PURCHASE("purchase"),
   REFUND("refund"),
   REMOVE_FROM_CART("remove_from_cart"),
   SCREEN_VIEW("screen_view"),
   SEARCH("search"),
   SELECT_CONTENT("select_content"),
   SET_CHECKOUT_OPTION("set_checkout_option"),
   SHARE("share"),
   SIGN_UP("sign_up"),
   TIMING_COMPLETE("timing_complete"),
   VIEW_ITEM("view_item"),
   VIEW_ITEM_LIST("view_item_list"),
   VIEW_PROMOTION("view_promotion"),
   VIEW_SEARCH_RESULTS("view_search_results");

                                       // class constants --------------------//
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
protected String value;                // value                               //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       constructor - constructor for specified value
                                                                              */
                                                                             /**
            Constructor for specified value.

@param      value    value

@history    Tue Dec 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
EventNames(
   String value)
{
   this.value = value;
}
/*------------------------------------------------------------------------------

@name       value - get value
                                                                              */
                                                                             /**
            Get value.

@return     value

@history    Tue Dec 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public String value()
{
   return(value);
}
}//====================================// EventNames =========================//
}//====================================// end IAnalyticsService ==============//
