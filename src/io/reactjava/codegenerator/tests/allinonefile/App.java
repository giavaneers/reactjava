/*==============================================================================

name:       App.java

purpose:    ReactJava TwoSquares State Variable Example.

            Contains a number of components with the following hierarchies:

            ...react.Component
            |___ ...allinonefile.A                extends ..react.Component
            |___ ...allinonefile.Component        extends ..react.Component
            |    |___ ...allinonefile.B           extends Component
            |    |___ ...allinonefile.B.Component extends ..react.Component
            |         |___ ...allinonefile.B.F    extends Component
            |         |___ ...allinonefile.B.F.G  extends Component
            |___ ...allinonefile.D                extends Component
            |___ ...allinonefile.D.E              extends Component

            So 'extends Component' refers to a class whose name is relative to
            the current classname, working its way step by step back to each
            outerclass until a classname that ends with ...Component is found.

history:    Sat Oct 27, 2018 10:30:00 (Giavaneers - LBM) created

notes:

                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
               LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.codegenerator.tests.allinonefile;
                                       // imports --------------------------- //
import elemental2.dom.Event;
import io.reactjava.client.core.react.INativeEventHandler;
import io.reactjava.client.core.react.IProvider;
import java.util.Vector;
import java.util.function.Consumer;
                                       // App ================================//
public class App extends io.reactjava.client.core.react.AppComponentTemplate
{
                                       // class constants ------------------- //
public static final String kSTATE_ON = "on";

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       onhandler - initialize
                                                                              */
                                                                             /**
            Initialize.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Consumer onHandler = (onValue) ->
{
   setState(kSTATE_ON, onValue);
};
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
                                       // react complains when a state        //
                                       // variable is boolean                 //
   useState(kSTATE_ON, "false");
   String onValue = getStateString(kSTATE_ON);

                                       // react complains if an attribute     //
                                       // is not all lower case; so           //
                                       // 'stateChangeHandler' ->             //
                                       //    'statechangehandler'             //
/*--
   <div id={"App"}>
      <A on={onValue} statechangehandler={onHandler} id="A"></A>
      <B on={onValue.equals("true") ? "false" : "true"}
         statechangehandler={onHandler} id="B">
      </B>
   </div>
--*/
};
/*==============================================================================

name:       A - component A

purpose:    Component A

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class A extends io.reactjava.client.core.react.Component
{
                                       // constants ------------------------- //
public static final String kPROPERTY_ON                   = "on";
public static final String kPROPERTY_STATE_CHANGE_HANDLER = "statechangehandler";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       clickHandler - onClick event handler
                                                                              */
                                                                             /**
            onClick event handler as a public instance variable, accessible in
            markup.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler clickHandler = (Event e) ->
{
   ((Consumer)props().get(kPROPERTY_STATE_CHANGE_HANDLER)).accept("true");
};
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
   String clas = "true".equals(props().getString(kPROPERTY_ON)) ? "on" : "off";
/*--
   <div class={clas} onClick={clickHandler} id="Adiv"></div>
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
   .on
   {
      height:           300px;
      width:            300px;
      background-color: green;
   }
   .off
   {
      height:           300px;
      width:            300px;
      background-color: red;
   }
--*/
}
/*==============================================================================

name:       E - component E

purpose:    Component E

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class E extends Component
{
                                       // constants ------------------------- //
public static final String kPROPERTY_ON                   = "on";
public static final String kPROPERTY_STATE_CHANGE_HANDLER = "statechangehandler";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       clickHandler - onClick event handler
                                                                              */
                                                                             /**
            onClick event handler as a public instance variable, accessible in
            markup.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler clickHandler = (Event e) ->
{
   ((Consumer)props().get(kPROPERTY_STATE_CHANGE_HANDLER)).accept("false");
};
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
   String clas = "true".equals(props().getString(kPROPERTY_ON)) ? "on" : "off";
/*--
   <div class={clas} onClick={clickHandler} id="Bdiv"></div>
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
   .on
   {
      height:           300px;
      width:            300px;
      background-color: green;
   }
   .off
   {
      height:           300px;
      width:            300px;
      background-color: red;
   }
--*/
}
}//====================================// end E ==============================//
}//====================================// end A ==============================//
/*==============================================================================

name:       B - component B

purpose:    Component B

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class B extends Component
{
                                       // constants ------------------------- //
public static final String kPROPERTY_ON                   = "on";
public static final String kPROPERTY_STATE_CHANGE_HANDLER = "statechangehandler";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       clickHandler - onClick event handler
                                                                              */
                                                                             /**
            onClick event handler as a public instance variable, accessible in
            markup.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler clickHandler = (Event e) ->
{
   ((Consumer)props().get(kPROPERTY_STATE_CHANGE_HANDLER)).accept("false");
};
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
   String clas = "true".equals(props().getString(kPROPERTY_ON)) ? "on" : "off";
/*--
   <div class={clas} onClick={clickHandler} id="Bdiv"></div>
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
   .on
   {
      height:           300px;
      width:            300px;
      background-color: green;
   }
   .off
   {
      height:           300px;
      width:            300px;
      background-color: red;
   }
--*/
}
/*==============================================================================

name:       Component - component Component

purpose:    Component Component

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Component extends io.reactjava.client.core.react.Component
{
                                       // constants ------------------------- //
public static final String kPROPERTY_ON                   = "on";
public static final String kPROPERTY_STATE_CHANGE_HANDLER = "statechangehandler";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       clickHandler - onClick event handler
                                                                              */
                                                                             /**
            onClick event handler as a public instance variable, accessible in
            markup.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler clickHandler = (Event e) ->
{
   ((Consumer)props().get(kPROPERTY_STATE_CHANGE_HANDLER)).accept("true");
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
   .on
   {
      height:           300px;
      width:            300px;
      background-color: green;
   }
   .off
   {
      height:           300px;
      width:            300px;
      background-color: red;
   }
--*/
}
}//====================================// end Component ======================//
/*==============================================================================

name:       F - component F

purpose:    Component F

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class F extends Component
{
                                       // constants ------------------------- //
public static final String kPROPERTY_ON                   = "on";
public static final String kPROPERTY_STATE_CHANGE_HANDLER = "statechangehandler";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       clickHandler - onClick event handler
                                                                              */
                                                                             /**
            onClick event handler as a public instance variable, accessible in
            markup.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler clickHandler = (Event e) ->
{
   ((Consumer)props().get(kPROPERTY_STATE_CHANGE_HANDLER)).accept("false");
};
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
   String clas = "true".equals(props().getString(kPROPERTY_ON)) ? "on" : "off";
/*--
   <div class={clas} onClick={clickHandler} id="Bdiv"></div>
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
   .on
   {
      height:           300px;
      width:            300px;
      background-color: green;
   }
   .off
   {
      height:           300px;
      width:            300px;
      background-color: red;
   }
--*/
}
/*==============================================================================

name:       G - component G

purpose:    Component G

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class G extends Component
{
                                       // constants ------------------------- //
public static final String kPROPERTY_ON                   = "on";
public static final String kPROPERTY_STATE_CHANGE_HANDLER = "statechangehandler";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       clickHandler - onClick event handler
                                                                              */
                                                                             /**
            onClick event handler as a public instance variable, accessible in
            markup.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler clickHandler = (Event e) ->
{
   ((Consumer)props().get(kPROPERTY_STATE_CHANGE_HANDLER)).accept("false");
};
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
   String clas = "true".equals(props().getString(kPROPERTY_ON)) ? "on" : "off";
/*--
   <div class={clas} onClick={clickHandler} id="Bdiv"></div>
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
   .on
   {
      height:           300px;
      width:            300px;
      background-color: green;
   }
   .off
   {
      height:           300px;
      width:            300px;
      background-color: red;
   }
--*/
}
}//====================================// end G ==============================//
}//====================================// end F ==============================//
}//====================================// end B ==============================//
/*==============================================================================

name:       Component - component Component

purpose:    Component Component which is also a provider

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class Component
   extends io.reactjava.client.core.react.Component
   implements IProvider
{
                                       // constants ------------------------- //
public static final String kPROPERTY_ON                   = "on";
public static final String kPROPERTY_STATE_CHANGE_HANDLER = "statechangehandler";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       clickHandler - onClick event handler
                                                                              */
                                                                             /**
            onClick event handler as a public instance variable, accessible in
            markup.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler clickHandler = (Event e) ->
{
   ((Consumer)props().get(kPROPERTY_STATE_CHANGE_HANDLER)).accept("true");
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
   .on
   {
      height:           300px;
      width:            300px;
      background-color: green;
   }
   .off
   {
      height:           300px;
      width:            300px;
      background-color: red;
   }
--*/
}
}//====================================// end Component ======================//
/*==============================================================================

name:       D - component D

purpose:    Component D

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class D extends Component
{
                                       // constants ------------------------- //
public static final String kPROPERTY_ON                   = "on";
public static final String kPROPERTY_STATE_CHANGE_HANDLER = "statechangehandler";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       clickHandler - onClick event handler
                                                                              */
                                                                             /**
            onClick event handler as a public instance variable, accessible in
            markup.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler clickHandler = (Event e) ->
{
   ((Consumer)props().get(kPROPERTY_STATE_CHANGE_HANDLER)).accept("false");
};
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
   String clas = "true".equals(props().getString(kPROPERTY_ON)) ? "on" : "off";
/*--
   <div class={clas} onClick={clickHandler} id="Bdiv"></div>
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
   .on
   {
      height:           300px;
      width:            300px;
      background-color: green;
   }
   .off
   {
      height:           300px;
      width:            300px;
      background-color: red;
   }
--*/
}
/*==============================================================================

name:       E - component E

purpose:    Component E

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class E extends Component
{
                                       // constants ------------------------- //
public static final String kPROPERTY_ON                   = "on";
public static final String kPROPERTY_STATE_CHANGE_HANDLER = "statechangehandler";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       clickHandler - onClick event handler
                                                                              */
                                                                             /**
            onClick event handler as a public instance variable, accessible in
            markup.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler clickHandler = (Event e) ->
{
   ((Consumer)props().get(kPROPERTY_STATE_CHANGE_HANDLER)).accept("false");
};
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
   String clas = "true".equals(props().getString(kPROPERTY_ON)) ? "on" : "off";
/*--
   <div class={clas} onClick={clickHandler} id="Bdiv"></div>
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
   .on
   {
      height:           300px;
      width:            300px;
      background-color: green;
   }
   .off
   {
      height:           300px;
      width:            300px;
      background-color: red;
   }
--*/
}
}//====================================// end E ==============================//
}//====================================// end D ==============================//
/*==============================================================================

name:       NonComponentA - non component inner class

purpose:    non component inner class

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class NonComponentA
{
                                       // constants ------------------------- //
public static final String kPROPERTY_ON                   = "on";
public static final String kPROPERTY_STATE_CHANGE_HANDLER = "statechangehandler";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

}//====================================// end NonComponentA ==================//
/*==============================================================================

name:       NonComponentB - non component inner class

purpose:    non component inner class

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class NonComponentB extends Vector
{
                                       // constants ------------------------- //
public static final String kPROPERTY_ON                   = "on";
public static final String kPROPERTY_STATE_CHANGE_HANDLER = "statechangehandler";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

}//====================================// end NonComponentB ==================//
}//====================================// end TopLevelComponent ==============//
/*==============================================================================

name:       TopLevelComponent - a second top level class in the same file

purpose:    A second top level class in the same file

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
class TopLevelComponent extends io.reactjava.client.core.react.Component
{
                                       // class constants ------------------- //
public static final String kSTATE_ON = "on";

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
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
                                       // react complains when a state        //
                                       // variable is boolean                 //
   useState(kSTATE_ON, "false");
   String onValue = getStateString(kSTATE_ON);

                                       // react complains if an attribute     //
                                       // is not all lower case; so           //
                                       // 'stateChangeHandler' ->             //
                                       //    'statechangehandler'             //
/*--
   <div id={"App"}>
      <A on={onValue} statechangehandler={onHandler} id="A"></A>
      <B on={onValue.equals("true") ? "false" : "true"}
         statechangehandler={onHandler} id="B">
      </B>
   </div>
--*/
};
/*==============================================================================

name:       TopLevelComponentA - component A

purpose:    Component A

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class TopLevelComponentA extends App.Component
{
                                       // constants ------------------------- //
public static final String kPROPERTY_ON                   = "on";
public static final String kPROPERTY_STATE_CHANGE_HANDLER = "statechangehandler";
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       clickHandler - onClick event handler
                                                                              */
                                                                             /**
            onClick event handler as a public instance variable, accessible in
            markup.

@history    Thu Feb 14, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEventHandler clickHandler = (Event e) ->
{
   ((Consumer)props().get(kPROPERTY_STATE_CHANGE_HANDLER)).accept("false");
};
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
   String clas = "true".equals(props().getString(kPROPERTY_ON)) ? "on" : "off";
/*--
   <div class={clas} onClick={clickHandler} id="Bdiv"></div>
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
   .on
   {
      height:           300px;
      width:            300px;
      background-color: green;
   }
   .off
   {
      height:           300px;
      width:            300px;
      background-color: red;
   }
--*/
}
}//====================================// end TopLevelComponentA =============//
}//====================================// end TopLevelComponent ==============//
