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
import elemental2.dom.Element;
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
protected static int nextId;           // next elementId to be autoassigned   //
                                       // protected instance variables ------ //
protected StateMgr   stateMgr;         // component state manager             //
protected java.util.function.Function<Properties, ReactElement>
                     componentFcn;     // component function                  //
protected String     css;              // css                                 //
                                       // private to approximate immutable    //
                                       // renamed from 'props' to avoid       //
                                       // minified confusion with react       //
                                       // 'props'; note, this instance        //
                                       // variable should appear last - see   //
                                       // ReactJava.                          //
                                       // getNativeComponentPropertiesFieldname()//
private   P          componentProperties;

/*------------------------------------------------------------------------------

@name       Component - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Component()
{
   initialize(null);
}
/*------------------------------------------------------------------------------

@name       Component - constructor for specified properties
                                                                              */
                                                                             /**
            Constructor for specified properties

@param      initialProps      initial properties

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Component(P initialProps)
{
   initialize(initialProps);
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
public Element getDOMElement()
{
   String  elementId = props().getString("id");
   Element element   = DomGlobal.document.getElementById(elementId);
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
public Element getDOMParentElement()
{
   Element parent  = null;
   Element element = getDOMElement();
   if (element != null)
   {
      Node parentNode = getDOMElement().parentNode;
      if (parentNode instanceof Element)
      {
         parent = (Element)parentNode;
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
   IUITheme theme = (IUITheme)props().get("theme");
   if (theme == null)
   {
      theme = props().getConfiguration().getTheme();
      setTheme(theme);
   }
   return(theme);
}
/*------------------------------------------------------------------------------

@name       initialize - initialize
                                                                              */
                                                                             /**
            Initialize. This method is invoked in the constructor(P componentProperties),
            so be careful that any referenced instance variables have been
            initialized. Specifically, in Java, the order for initialization
            statements is as follows:

               1. static variables and static initializers in order of
                  appearance in the source.

               2. instance variables and instance initializers in order of
                  appearance in the source.

               3. constructors.

@return     void

@param      initialProps      initial properties

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public P initialize(
   P initialProps)
{
   this.componentProperties =
      initialProps != null ? initialProps : (P)new Properties();

   this.componentProperties.setComponent(this);

   initConfiguration();
   initTheme();

   if (ReactJava.getIsWebPlatform())
   {
      ReactJava.ensureComponentStyles(this, false);
   }
   return(this.componentProperties);
}
/*------------------------------------------------------------------------------

@name       initConfiguration - initialize configuration
                                                                              */
                                                                             /**
            Initialize configuration.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void initConfiguration()
{
   if (props().getConfiguration() == null)
   {
      props().setConfiguration(Configuration.sharedInstance());
   }
}
/*------------------------------------------------------------------------------

@name       initTheme - initialize theme
                                                                              */
                                                                             /**
            Initialize theme.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void initTheme()
{
   getTheme();
}
/*------------------------------------------------------------------------------

@name       props - get properties
                                                                              */
                                                                             /**
            Get properties.

@return     properties

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public P props()
{
   return(componentProperties);
}
/*------------------------------------------------------------------------------

@name       render - render markup
                                                                              */
                                                                             /**
            Render markup.

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

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
};
/*------------------------------------------------------------------------------

@name       setId - set id
                                                                              */
                                                                             /**
            Set id.

@param      id    new id value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void setId(
   String id)
{
   if (!id.equals(componentProperties.get("id")))
   {
                                       // don't know why set() won't work here//
      componentProperties = (P)Properties.with(componentProperties, "id", id);
   }
}
/*------------------------------------------------------------------------------

@name       setState - set theme
                                                                              */
                                                                             /**
            Set theme.

@param      key      key
@param      value    value

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

@param      theme     theme

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public IUITheme setTheme(
   IUITheme theme)
{
   props().set("theme", (theme));
   return(theme);
}
/*------------------------------------------------------------------------------

@name       update - update component render
                                                                              */
                                                                             /**
            Update component render.

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void update()
{
   try
   {
      ReactElement element = ReactJava.createElement(this);
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

@name       useEffect - component side effect handler
                                                                              */
                                                                             /**
            Effect hook handler similar to componentDidMount,
            componentDidUpdate, and componentWillUnmount combined.

@param      effectHandler     function to be invoked on effect

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void useEffect(
   INativeEffectHandler effectHandler)
{
   React.useEffect(effectHandler);
}
/*------------------------------------------------------------------------------

@name       useState - initialize state variables
                                                                              */
                                                                             /**
            Initialize state variables. Each attribute of the specified
            ComponentState contains a name (key) and an associated initial
            value. This ReactJava implementation splits each attribute into a
            separate React state hook.

@param      key      key
@param      value    value

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

@param      key      state variable name
@param      value    new state variable value

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
