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
import elemental2.core.JsArray;
import elemental2.core.JsObject;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jsinterop.base.JsArrayLike;

                                       // Component ==========================//
public abstract class Component<P extends Properties>
{
                                       // class constants --------------------//
public static final boolean kSRCCFG_USE_STATE_HOOK = true;
public static final String  kFORCE_UPDATE_KEY      = "builtinForceUpdate";

private static final Logger kLOGGER = Logger.newInstance();

                                       // class variables                     //
protected static int   nextId;         // next elementId to be autoassigned   //
private   static Map<String,Component> // map of component by id              //
                       componentById;
                                       // protected instance variables ------ //
protected StateMgr     stateMgr;       // component state manager             //
protected java.util.function.Function<Properties, ReactElement>
                       componentFcn;   // component function                  //
protected ReactElement reactElement;   // react element                       //
protected String       css;            // css                                 //
                                       // list of injected styleIds           //
protected List<String> injectedStyleIds;
                                       // private to approximate immutable    //
                                       // renamed from 'props' to avoid       //
                                       // minified confusion with react       //
                                       // 'props'; note, this instance        //
                                       // variable should appear last - see   //
                                       // ReactJava.                          //
                                       // getNativeComponentPropertiesFieldname()//
private   P            componentProperties;

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

@name       cssSelectorForId - get css selector for specified elementId
                                                                              */
                                                                             /**
            Get css selector for specified elementId.

@return     css selector for specified elementId

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected String cssSelectorForId()
{
   return("#" + props().get("id"));
}
/*------------------------------------------------------------------------------

@name       forceUpdate - force component update
                                                                              */
                                                                             /**
            Force component update. Since ReactJava components are stateless,
            this implementation utilizes a transition on a built-in state
            variable and whose 'useState()' invocation is generated by the
            JSXTransorm.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void forceUpdate()
{
   int value = getStateInt(kFORCE_UPDATE_KEY);
   setState(kFORCE_UPDATE_KEY, ++value);
}
/*------------------------------------------------------------------------------

@name       forElement - get component for specified element
                                                                              */
                                                                             /**
            Get component for specified element.

@return     component for specified element

@param      element     specified element

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static Component forElement(
   Element element)
{
   if (!(element instanceof HTMLElement))
   {
      throw new IllegalStateException("Expected instance of HTMLElement");
   }

   HTMLElement htmlElement = (HTMLElement)element;

   Component component =
      htmlElement.id != null ? getComponentById().get(htmlElement.id) : null;

   return(component);
}
/*------------------------------------------------------------------------------

@name       getComponentById - get map of component by id
                                                                              */
                                                                             /**
            Get map of component by id.

@return     map of component by id

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
private static Map<String,Component> getComponentById()
{
   if (componentById == null)
   {
      componentById = new HashMap<>();
   }
   return(componentById);
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

@name       getInjectedStyles - get list of injected styleIds
                                                                              */
                                                                             /**
            Set list of injected styleIds.

@return     list of injected styleIds

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected List<String> getInjectedStyles()
{
   if (injectedStyleIds == null)
   {
      injectedStyleIds = new ArrayList<>();
   }
   return(injectedStyleIds);
}
/*------------------------------------------------------------------------------

@name       getNavRoutes - get routes for component
                                                                              */
                                                                             /**
            Get component map of component classname by route path. This method
            is invoked at compile time.

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
   return("rjAuto" + ++nextId);
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

@name       getStyleId - get styleId
                                                                              */
                                                                             /**
            Get styleId

@return     styleId, or null if props id is null.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected String getStyleId()
{
   String styleId = null;
   String id      = props().getString("id");
   if (id != null)
   {
      styleId = id + "_" + getClass().getSimpleName();
   }

   return(styleId);
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
            Initialize. This method is invoked in the constructor prior to
            initial rendering. This is the initialize method typically
            overridden by subclasses.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void initialize()
{
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

            This method is public so it can be invoked by the JSX component
            function.

@return     component properties

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

   String id = this.componentProperties.getString("id");
   if (id != null)
   {
      getComponentById().put(id, this);
   }

   initConfiguration();
   initTheme();

   if (initialProps != null)
   {
                                       // allow a subclass to override        //
      initialize();
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
protected P props()
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
}
/*------------------------------------------------------------------------------

@name       renderCSS - get component css
                                                                              */
                                                                             /**
            Get component css. Invoked before render().

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
}
/*------------------------------------------------------------------------------

@name       removeAnyStylesheet - remove any injected stylesheet
                                                                              */
                                                                             /**
            Remove any injected stylesheet.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void removeAnyStylesheet()
{
   Element previous = DomGlobal.document.getElementById(getStyleId());
   if (previous != null)
   {
      previous.remove();
   }
}
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
   String current = componentProperties.getString("id");
   if (!id.equals(current))
   {
      if (current != null)
      {
         getComponentById().remove(current);

      }
                                       // don't know why set() won't work here//
      componentProperties = (P)Properties.with(componentProperties, "id", id);
      getComponentById().put(id, this);
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
protected IUITheme setTheme(
   IUITheme theme)
{
   props().set("theme", (theme));
   return(theme);
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
protected void useEffect(
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
protected void useState(
   String key,
   Object value)
{
   getStateMgr().useState(key, value);
}
/*==============================================================================

name:       CompileTime - compile time utilities

purpose:    Compile time utilities

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class CompileTime
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       resolve - resolve a string value at compile time
                                                                              */
                                                                             /**
            Resolve a string value at compile time. This implementation is null.

@return     string value resolved at compile time

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static String resolve(
   String key)
{
   return(key);
}
}//====================================// end CompileTime ====================//
/*==============================================================================

name:       StateMgr - properties

purpose:    JsInterop definition for access to javascript onLoad handler

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class StateMgr
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // map of state variables              //
protected Map<String,JsArrayLike> state;

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
   return(state != null ? state.get(key).getAt(0) : null);
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
   JsArrayLike stateVariable = state.get(key);
   if (stateVariable == null)
   {
      throw new IllegalStateException(
         "useState() with key=" + key + " must be invoked before setState()");
   }

   setStateNative(stateVariable, value);
}
/*------------------------------------------------------------------------------

@name       setStateNative - set state native
                                                                              */
                                                                             /**
            Set state native. Invokes the setter with the specified next
            state value.

@param      stateVariable     state variable
@param      value             new state variable value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public native void setStateNative(JsArrayLike stateVariable, Object value)
/*-{
   stateVariable[1](value);
}-*/;
/*------------------------------------------------------------------------------

@name       useState - initialize state variable
                                                                              */
                                                                             /**
            Initialize state variable. Each attribute of the specified
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
      state = new HashMap<>();
   }
                                       // React.useState() invocation may not //
                                       // be in a conditional                 //
   state.put(key, React.useState(value));
}
}//====================================// end StateMgr =======================//
}//====================================// end Component ----------------------//
