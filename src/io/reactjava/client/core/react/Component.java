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
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.Node;
import io.reactjava.client.core.react.IConfiguration.ICloudServices;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jsinterop.base.Js;
import jsinterop.base.JsArrayLike;
import jsinterop.base.JsPropertyMap;
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
protected RefMgr       refMgr;         // component ref manager               //
protected StateMgr     stateMgr;       // component state manager             //
protected ReactElement reactElement;   // react element                       //
protected String       css;            // css                                 //
                                       // css injected styleId                //
protected String       cssInjectedStyleId;
                                       // list of injected styleIds           //
protected List<String> injectedStyleIds;
                                       // private instance variables -------- //
                                       // component function                  //
                                       // private to force access through     //
                                       // accessors to support JSXTransform   //
private java.util.function.Function<Properties, ReactElement>
                       componentFcn;
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

@name       componentDidMount - componentDidMount event handler
                                                                              */
                                                                             /**
            Invoked by React on DOM being initially ready. This implementation
            is null.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void componentDidMount()
{
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
            JSXTransform.

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
      htmlElement.id != null ? getComponentByIdMap().get(htmlElement.id) : null;

   return(component);
}
/*------------------------------------------------------------------------------

@name       forId - get component by specified id
                                                                              */
                                                                             /**
            Get component by specified id.

@return     component by specified id or null if not found

@history    Fri Nov 08, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static Component forId(
   String id)
{
   return(getComponentByIdMap().get(id));
}
/*------------------------------------------------------------------------------

@name       getComponentByIdMap - get map of component by id
                                                                              */
                                                                             /**
            Get map of component by id.

@return     map of component by id

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static Map<String,Component> getComponentByIdMap()
{
   if (componentById == null)
   {
      componentById = new HashMap<>();
   }
   return(componentById);
}
/*------------------------------------------------------------------------------

@name       getComponentFcn - get component function
                                                                              */
                                                                             /**
            Get component function.

@return     component function

@history    Sat Dec 21, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected java.util.function.Function<Properties, ReactElement> getComponentFcn()
{
   return(componentFcn);
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

@name       getCloudServicesConfig - get cloud services configuration
                                                                              */
                                                                             /**
            Get cloud services configuration. This impementation is to be
            overridden.

@return     cloud services configuration.

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected ICloudServices getCloudServicesConfig()
{
   return(null);
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

@name       getRef - get ref element value
                                                                              */
                                                                             /**
            Get ref element value.

            Note, the useRef hook is supported for research purposes only, since
            its functionality can be readily replaced in ReactJava by use of a
            declared component instance variable.

@return     ref element value

@history    Thu Jul 25, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Object getRef(
   String key)
{
   return(getRefMgr().getRef(key));
}
/*------------------------------------------------------------------------------

@name       getRefBoolean - get ref boolean value
                                                                              */
                                                                             /**
            Get ref boolean value.

            Note, the useRef hook is supported for research purposes only, since
            its functionality can be readily replaced in ReactJava by use of a
            declared component instance variable.

@return     ref boolean value

@history    Thu Jul 25, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected boolean getRefBoolean(
   String key)
{
   Object value = getRef(key);
   return(value != null ? (Boolean)value : false);
}
/*------------------------------------------------------------------------------

@name       getRefInt - get ref int value
                                                                              */
                                                                             /**
            Get ref int value.

            Note, the useRef hook is supported for research purposes only, since
            its functionality can be readily replaced in ReactJava by use of a
            declared component instance variable.

@return     ref int value

@history    Thu Jul 25, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected int getRefInt(
   String key)
{
   Object value = getRef(key);
   return(value != null ? (Integer)value : 0);
}
/*------------------------------------------------------------------------------

@name       getRefMgr - get ref manager
                                                                              */
                                                                             /**
            Get ref manager.

            Note, the useRef hook is supported for research purposes only, since
            its functionality can be readily replaced in ReactJava by use of a
            declared component instance variable.

@return     ref manager

@history    Thu Jul 25, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected RefMgr getRefMgr()
{
   if (refMgr == null)
   {
      refMgr = new RefMgr();
   }
   return(refMgr);
}
/*------------------------------------------------------------------------------

@name       getRefString - get ref string value
                                                                              */
                                                                             /**
            Get ref string value.

            Note, the useRef hook is supported for research purposes only, since
            its functionality can be readily replaced in ReactJava by use of a
            declared component instance variable.

@return     ref string value

@history    Thu Jul 25, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected String getRefString(
   String key)
{
   return((String)getRef(key));
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
      getComponentByIdMap().put(id, this);
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

@history    Thu Jun 20, 2019 10:30:00 (Giavaneers - LBM) created

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

@name       setComponentFcn - set component function
                                                                              */
                                                                             /**
            Set component function.

@param      componentFcn      component function

@history    Sat Dec 21, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void setComponentFcn(
   java.util.function.Function<Properties, ReactElement> componentFcn)
{
   this.componentFcn = componentFcn;
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
         getComponentByIdMap().remove(current);

      }
                                       // don't know why set() won't work here//
      componentProperties = (P)Properties.with(componentProperties, "id", id);
      getComponentByIdMap().put(id, this);
   }
}
/*------------------------------------------------------------------------------

@name       setRef - set ref value
                                                                              */
                                                                             /**
            Set ref value.

            Note, the useRef hook is supported for research purposes only, since
            its functionality can be readily replaced in ReactJava by use of a
            declared component instance variable.

@param      key      key
@param      value    value

@history    Thu Jul 25, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void setRef(
   String key,
   Object value)
{
   getRefMgr().setRef(key, value);
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

@name       useEffect - useEffect hook
                                                                              */
                                                                             /**
            useEffect hook.

@param      effectHandler     effect function to be invoked when component
                              mounted, unmounted, and updated.

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

@name       useEffect - useEffect hook
                                                                              */
                                                                             /**
            useEffect hook.

@param      effectHandler     effect function.

@param      dependencies      array of property and state values when changed
                              will also cause the effect handler to be
                              invoked; passing an empty array will cause the
                              effect handler to be invoked only on component
                              mounted and unmounted (not on update).

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void useEffect(
   INativeEffectHandler effectHandler,
   Object[]             dependencies)
{
   React.useEffect(effectHandler, dependencies);
}
/*------------------------------------------------------------------------------

@name       useRef - initialize ref variables
                                                                              */
                                                                             /**
            Initialize ref variables. Each attribute of the specified
            ComponentRef contains a name (key) and an associated initial
            value. This ReactJava implementation splits each attribute into a
            separate React useRef hook.

            Note, the useRef hook is supported for research purposes only, since
            its functionality can be readily replaced in ReactJava by use of a
            declared component instance variable.

@param      key      key
@param      value    value

@history    Thu Jul 25, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void useRef(
   String key,
   Object value)
{
   getRefMgr().useRef(key, value);
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

name:       RefMgr - ref manager

purpose:    React useRef hook support.

            Note, the useRef hook is supported for research purposes only, since
            its functionality can be readily replaced in ReactJava by use of a
            declared component instance variable.

history:    Thu Jul 25, 2019 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public static class RefMgr
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // map of ref variables                //
protected Map<String,JsArrayLike> ref;

/*------------------------------------------------------------------------------

@name       RefMgr - constructor
                                                                              */
                                                                             /**
            Constructor.

@history    Thu Jul 25, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public RefMgr()
{
}
/*------------------------------------------------------------------------------

@name       getRef - get ref element value
                                                                              */
                                                                             /**
            Get ref element value.

@return     ref element value

@history    Thu Jul 25, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected Object getRef(
   String key)
{
   return(ref != null ? Js.asPropertyMap(ref.get(key)).get("current") : null);
}
/*------------------------------------------------------------------------------

@name       setRef - set ref
                                                                              */
                                                                             /**
            Set ref.

@param      key      ref variable name
@param      value    new ref variable value

@history    Thu Jul 25, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void setRef(
   String key,
   Object value)
{
   JsPropertyMap<Object> refVariable = Js.asPropertyMap(ref.get(key));
   if (refVariable == null)
   {
      throw new IllegalStateException(
         "useRef() with key=" + key + " must be invoked before setRef()");
   }

   refVariable.set("current", value);
}
/*------------------------------------------------------------------------------

@name       useRef - initialize ref variable
                                                                              */
                                                                             /**
            Initialize ref variable. Each attribute of the specified
            ComponentRef contains a name (key) and an associated initial
            value. This ReactJava implementation splits each attribute into a
            separate React ref hook.

@param      key      ref variable name
@param      value    new ref variable value

@history    Thu Jul 25, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void useRef(
   String key,
   Object value)
{
   if (ref == null)
   {
      ref = new HashMap<>();
   }
                                       // React.useRef() invocation may not   //
                                       // be in a conditional                 //
   ref.put(key, React.useRef(value));
}
}//====================================// end RefMgr =========================//
/*==============================================================================

name:       StateMgr - state manager

purpose:    React useState hook support.

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
   Object copy = state != null ? state.get(key).getAt(0) : null;
   if (copy != null)
   {
      copy = makeStateValueCopy(copy);
   }

   return(copy);
}
/*------------------------------------------------------------------------------

@name       makeStateValueCopy - make copy of state element value
                                                                              */
                                                                             /**
            Make copy of state element value.

@return     copy of state element value

@history    Wed Dec 04, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static Object makeStateValueCopy(
   Object value)
{
   Object copy = value;
   Class  clas = value.getClass();
   try
   {
      if (clas.isArray())
      {
         int length;

         if (value instanceof String[])
         {
            length = ((String[])value).length;
            copy = new String[length];
         }
         else if (value instanceof int[])
         {
            length = ((int[])value).length;
            copy = new int[length];
         }
         else if (value instanceof boolean[])
         {
            length = ((boolean[])value).length;
            copy = new boolean[length];
         }
         else if (value instanceof float[])
         {
            length = ((float[])value).length;
            copy = new float[length];
         }
         else if (value instanceof double[])
         {
            length = ((double[])value).length;
            copy = new double[length];
         }
         else if (value instanceof char[])
         {
            length = ((char[])value).length;
            copy = new char[length];
         }
         else if (value instanceof short[])
         {
            length = ((short[])value).length;
            copy = new short[length];
         }
         else if (value instanceof Object[])
         {
            length = ((Object[])value).length;
            copy = new Object[length];
         }
         else
         {
            throw new UnsupportedOperationException(clas.toString());
         }
         System.arraycopy(value, 0, copy, 0, length);
      }
      else if (value instanceof Map)
      {
         copy = new HashMap((Map)value);
      }
      else if (value instanceof List)
      {
         copy = new ArrayList((List)value);
      }
      else if (value instanceof Set)
      {
         copy = new HashSet((Set)value);
      }
   }
   catch(Exception e)
   {
      kLOGGER.logError(e);
   }

   return(copy);
}
/*------------------------------------------------------------------------------

@name       setState - set state
                                                                              */
                                                                             /**
            Set state. React determines equivalence of new and old Object values
            generally by whether both refer to the same instance, rather than
            whether they are equivalent. As a consequence, for example, if the
            current state variable is an int array whose first element has
            value a, and the first element is changed to value b before being
            set as the new state variable, React determines there has been no
            state change since the value is the same array despite its contents
            have changed. Similarly, for example, if the new state value is a
            different int array of the same length and containing the same
            content, React determines there has been a state change because
            reference is to two different arrays, even though the arrays are
            equivalent.

            ReactJava uses a test of equivalence instead. Accordingly, unlike
            React, ReactJava treats the first case as a state change and the
            second case as no state change.


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
   if (state != null)
   {
      JsArrayLike stateVariable = state.get(key);
      if (stateVariable == null)
      {
         throw new IllegalStateException(
            "useState() with key=" + key + " must be invoked before setState()");
      }
      Object current = getState(key);
      if (current == null || !current.equals(value))
      {
         setStateNative(stateVariable, makeStateValueCopy(value));
      }
   }
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
                                       // the second element of the           //
                                       // stateVariable array is a fcn that   //
                                       // enqueues the state variable change  //
                                       // with the new value being passed to  //
                                       // the fcn as an argument              //
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
