/*==============================================================================

name:       Component.java

purpose:    ReactJava Component.

history:    Mon June 4, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import com.giavaneers.util.gwt.Logger;
import elemental2.core.Function;
import elemental2.dom.DomGlobal;
import elemental2.dom.Node;
import java.util.HashMap;
import java.util.Map;
                                       // Component ==========================//
public abstract class Component<P extends Properties>
{
                                       // class constants --------------------//
public static final boolean kSRCCFG_USE_STATE_HOOK = false;

private static final Logger kLOGGER = Logger.newInstance();

                                       // class variables                     //
protected static int        nextId;    // next elementId to be autoassigned   //
                                       // protected instance variables ------ //
protected StateMgr          stateMgr;  // component state manager             //
protected java.util.function.Function<Properties,Element>
                                       // component function                  //
                            componentFcn;
protected String            css;       // css                                 //
protected P                 props;     // component properties                //
protected IUITheme          theme;     // theme                               //

/*------------------------------------------------------------------------------

@name       Component - default constructor
                                                                              */
                                                                             /**
            Default constructor

@return     An instance of Component if successful.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Component()
{
   this(null);
}
/*------------------------------------------------------------------------------

@name       Component - constructor for specified properties
                                                                              */
                                                                             /**
            Constructor for specified properties

@return     An instance of Component if successful.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Component(P props)
{
   this.props = props != null ? props : (P)new Properties();
   this.props.setComponent(this);

   initConfiguration();

   if (ReactJava.getIsWebPlatform())
   {
      ReactJava.ensureComponentStyles(this, false);
   }
}
/*------------------------------------------------------------------------------

@name       componentDidMount - componentDidMount indication
                                                                              */
                                                                             /**
            componentDidMount indication. This implementation is null.

@return     void

@history    Fri Mar 8, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void componentDidMount()
{
}
/*------------------------------------------------------------------------------

@name       componentDidUpdate - componentDidUpdate indication
                                                                              */
                                                                             /**
            componentDidUpdate indication. This implementation is null.

@return     void

@history    Fri Mar 8, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void componentDidUpdate()
{
}

/*------------------------------------------------------------------------------

@name       componentWillUnmount - componentWillUnmount indication
                                                                              */
                                                                             /**
            componentWillUnmount indication. This implementation is null.

@return     void

@history    Fri Mar 8, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void componentWillUnmount()
{
}
/*------------------------------------------------------------------------------

@name       getDOMElement - get dom element
                                                                              */
                                                                             /**
            Get dom element.

@return     dom element

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public elemental2.dom.Element getDOMElement()
{
   String                 elementId = getProperties().getString("id");
   elemental2.dom.Element element   = DomGlobal.document.getElementById(elementId);
   return(element);
}
/*------------------------------------------------------------------------------

@name       getDOMParentElement - get dom parent element
                                                                              */
                                                                             /**
            Get dom parent element.

@return     dom parent element

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public elemental2.dom.Element getDOMParentElement()
{
   elemental2.dom.Element parent = null;
   elemental2.dom.Element element = getDOMElement();
   if (element != null)
   {
      Node parentNode = getDOMElement().parentNode;
      if (parentNode instanceof elemental2.dom.Element)
      {
         parent = (elemental2.dom.Element)parentNode;
      }
   }
   else
   {
      parent = DomGlobal.document.getElementById("root");
   }

   return(parent);
}
/*------------------------------------------------------------------------------

@name       getNavRoutes - get routes for component
                                                                              */
                                                                             /**
            Get component map of component classname by route path.

@return     void

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected Map<String,Class> getNavRoutes()
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       getNextId - get next component id
                                                                              */
                                                                             /**
            Get next component id.

@return     next component id

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected static String getNextId()
{
   return("@" + ++nextId);
}
/*------------------------------------------------------------------------------

@name       getProperties - get props
                                                                              */
                                                                             /**
            Get properties.

@return     properties

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public P getProperties()
{
   return(props);
}
/*------------------------------------------------------------------------------

@name       getState - get state element value
                                                                              */
                                                                             /**
            Get state element value.

@return     state element value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Object getState(
   String key)
{
   return(getStateMgr().getState(key));
}
/*------------------------------------------------------------------------------

@name       getStateBoolean - get state boolean value
                                                                              */
                                                                             /**
            Get state boolean value.

@return     state boolean value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected boolean getStateBoolean(
   String key)
{
   Object value = getState(key);
   return(value != null ? (Boolean)value : false);
}
/*------------------------------------------------------------------------------

@name       getStateInt - get state int value
                                                                              */
                                                                             /**
            Get state int value.

@return     state int value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected int getStateInt(
   String key)
{
   Object value = getState(key);
   return(value != null ? (Integer)value : 0);
}
/*------------------------------------------------------------------------------

@name       getStateString - get state string value
                                                                              */
                                                                             /**
            Get state string value.

@return     state string value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected String getStateString(
   String key)
{
   return((String)getState(key));
}
/*------------------------------------------------------------------------------

@name       getStateMgr - get state manager
                                                                              */
                                                                             /**
            Get state manager.

@return     state manager

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected StateMgr getStateMgr()
{
   if (stateMgr == null)
   {
      stateMgr = new StateMgr();
   }
   return(stateMgr);
}
/*------------------------------------------------------------------------------

@name       getTheme - get theme
                                                                              */
                                                                             /**
            Get theme.

@return     theme

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public IUITheme getTheme()
{
   if (theme == null)
   {
      theme = props.getConfiguration().getTheme();
   }
   return(theme);
}
/*------------------------------------------------------------------------------

@name       initialize - set properties
                                                                              */
                                                                             /**
            Set properties.

@return     void

@return     props     properties

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public P initialize(
   P props)
{
   this.props = props != null ? props : (P)new Properties();
   this.props.setComponent(this);

   return(props);
}
/*------------------------------------------------------------------------------

@name       initConfiguration - initialize configuration
                                                                              */
                                                                             /**
            Initialize configuration. This implementation is null.

@return     void

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void initConfiguration()
{
   if (getProperties().getConfiguration() == null)
   {
      getProperties().setConfiguration(Configuration.sharedInstance());
   }
}
/*------------------------------------------------------------------------------

@name       render - render markup
                                                                              */
                                                                             /**
            Render markup.

@return     void

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created
            Wed Oct 17, 2018 10:30:00 (Giavaneers - LBM) renamed per suggestion
               by Ethan Elshyeb.

@notes

                                                                              */
//------------------------------------------------------------------------------
public void render()
{
};
/*------------------------------------------------------------------------------

@name       renderCSS - get component css
                                                                              */
                                                                             /**
            Get component css.

@return     void

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
};
/*------------------------------------------------------------------------------

@name       setState - set theme
                                                                              */
                                                                             /**
            Set theme.

@return     theme

@param      new theme

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void setState(
   String key,
   Object value)
{
   if (kSRCCFG_USE_STATE_HOOK)
   {
      // see issue #37
      // getStateMgr().setState(key, value) does not currently propogate the
      // render to the children, which has taken a while to debug
      // unsuccessfully, so for now force an update instead...
      getStateMgr().setState(key, value);
   }
   else
   {
      if (!value.equals(getState(key)))
      {
         getStateMgr().state.put(key, value);
         update();
      }
   }
}
/*------------------------------------------------------------------------------

@name       setTheme - set theme
                                                                              */
                                                                             /**
            Set theme.

@return     theme

@param      new theme

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public IUITheme setTheme(
   IUITheme theme)
{
   this.theme = theme;
   return(theme);
}
/*------------------------------------------------------------------------------

@name       update - update component render
                                                                              */
                                                                             /**
            Update component render.

@return     void

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void update()
{
   try
   {
      Element element = ReactJava.createElement(this);
      if (ReactJava.getIsWebPlatform())
      {
                                       // update any styles                   //
         ReactJava.ensureComponentStyles(this, true);
         ReactDOM.render(element, getDOMParentElement());
      }
   }
   catch(Exception e)
   {
      kLOGGER.logError(e);
   }
}
/*------------------------------------------------------------------------------

@name       useState - initialize state variables
                                                                              */
                                                                             /**
            Initialize state variables. Each attribute of the specified
            ComponentState contains a name (key) and an associated initial
            value. This ReactJava implementation splits each attribute into a
            separate React state hook.

@return     The specified initial state


@param      state       initial state

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void useState(
   String key,
   Object value)
{
   getStateMgr().useState(key, value);
}
/*==============================================================================

name:       StateMgr - properties

purpose:    JsInterop definition for access to javascript onLoad handler

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public class StateMgr
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
protected Map<String,Object>   state;  // map of state variable setters       //
protected Map<String,Function> setters;// map of state variable setters       //

/*------------------------------------------------------------------------------

@name       StateMgr - constructor
                                                                              */
                                                                             /**
            Constructor

@return     An instance of StateMgr if successful.

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public StateMgr()
{
}
/*------------------------------------------------------------------------------

@name       getState - get state element value
                                                                              */
                                                                             /**
            Get state element value.

@return     state element value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected Object getState(
   String key)
{
   Object value = null;
   if (state != null)
   {
      value = state.get(key);
   }
   return(value);
}
/*------------------------------------------------------------------------------

@name       setState - set state
                                                                              */
                                                                             /**
            Set state.

@return     void

@param      key      state variable name
@param      value    new state variable value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void setState(
   String key,
   Object value)
{
   if (!value.equals(getState(key)))
   {
      Function setter = setters.get(key);
      if (setter == null)
      {
         throw new IllegalStateException(
            "useState() with key=" + key + " must be invoked before setState()");
      }

      if (kSRCCFG_USE_STATE_HOOK)
      {
         // see issue #37
         // setter.apply(value) does not currently propogate the render
         // to the children, which has taken a while to debug unsuccessfully, so
         // for now, in the calling Component setState() method,
         // force an update instead...

         state.put(key, value);
         setter.apply(value);
      }
   }
}
/*------------------------------------------------------------------------------

@name       useState - initialize state variables
                                                                              */
                                                                             /**
            Initialize state variables. Each attribute of the specified
            ComponentState contains a name (key) and an associated initial
            value. This ReactJava implementation splits each attribute into a
            separate React state hook.

@return     The specified initial state


@param      state       initial state

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void useState(
   String key,
   Object value)
{
   if (state == null)
   {
                                       // lazy instantiation                  //
      state   = new HashMap<>();
      setters = new HashMap<>();
   }
   if (state.get(key) == null)
   {
      state.put(key, value);
      setters.put(key, (Function)React.useState(value).slice()[1]);
   }
}
}//====================================// end StateMgr =======================//
}//====================================// end Component ----------------------//
