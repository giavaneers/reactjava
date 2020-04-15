/*==============================================================================

name:       Component.java

purpose:    Component proxy for compilation by AppComponentInspector

history:    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.codegenerator.inspectortest;
                                       // imports --------------------------- //
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.Node;
import elemental2.promise.Promise;
import io.reactjava.client.core.react.ElementDsc;
import io.reactjava.client.core.react.IConfiguration.ICloudServices;
import io.reactjava.client.core.react.INativeEffectHandler;
import io.reactjava.client.core.react.IUITheme;
import io.reactjava.client.core.react.ReactElement;
import io.reactjava.client.core.rxjs.functions.Action0;
import io.reactjava.client.core.rxjs.functions.Action1;
import io.reactjava.client.core.rxjs.observable.Observable;
import io.reactjava.client.core.rxjs.observable.Observer;
import io.reactjava.client.core.rxjs.subscription.Subscription;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
                                       // Component ==========================//
public abstract class Component<P extends Properties>
{
                                       // class constants --------------------//
public static final String  kPROMISE_CANCELLED = "promise cancelled";

                                       // class variables                     //
protected static int        nextId;    // next elementId to be autoassigned   //
                                       // protected instance variables ------ //
protected String            css;       // css                                 //
                                       // component properties                //
protected P                 componentProperties;
protected IUITheme theme;              // theme                               //
                                       // private instance variables -------- //
                                       // component function                  //
private   Function<Properties, ReactElement>
                            componentFcn;

/*------------------------------------------------------------------------------

@name       Component - default constructor
                                                                              */
                                                                             /**
            Default constructor

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Component()
{
}
/*------------------------------------------------------------------------------

@name       Component - constructor for specified properties
                                                                              */
                                                                             /**
            Constructor for specified properties

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Component(P initialProps)
{
}
/*------------------------------------------------------------------------------

@name       addRenderEditor - add render editor
                                                                              */
                                                                             /**
            Add render editor.

@param      editor      render editor

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public boolean addRenderEditor(
   String             editorId,
   RenderEditFunction editFcn)
{
   return(true);
}
/*------------------------------------------------------------------------------

@name       addSubscription - add subscription
                                                                              */
                                                                             /**
            Add subscription.

@param      subscription      subscription
@param      promise           any promise property of the associated Observable

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Subscription addSubscription(
   Subscription subscription)
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       cancelSubscriptions - cancel subscriptions
                                                                              */
                                                                             /**
            Cancel any subscriptions

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void cancelSubscriptions()
{
}
/*------------------------------------------------------------------------------

@name       defaultElementId - provide a default elementId
                                                                              */
                                                                             /**
            Provide a default elementId.

@return     default elementId, or null if none.

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected String defaultElementId()
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       forceUpdate - force component update
                                                                              */
                                                                             /**
            Force component update.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void forceUpdate()
{
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
   return(null);
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
   return(null);
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
protected Function<Properties, ReactElement> getComponentFcn()
{
   return(componentFcn);
}
/*------------------------------------------------------------------------------

@name       getDOMElement - get dom element
                                                                              */
                                                                             /**
            Get dom element.

@return     dom element

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

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

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

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

@name       getMounted - get whether component is mounted
                                                                              */
                                                                             /**
            Get whether whether component is mounted as indicated explicitly
            by the component previously.

@return     whether component is mounted.

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean getMounted()
{
   return(true);
}
/*------------------------------------------------------------------------------

@name       getNavRoutes - get routes for component
                                                                              */
                                                                             /**
            Get component map of component classname by route path.

            Note, in general these routes are nested relative to those of the
            Application Template or its subclasses.

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

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected static String getNextId()
{
   return("@" + ++nextId);
}
/*------------------------------------------------------------------------------

@name       getReactElement - get result of render
                                                                              */
                                                                             /**
            Get result of component function invocation

@return     result of component function invocation

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public ReactElement getReactElement()
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       getRef - get ref element value
                                                                              */
                                                                             /**
            Get ref element value.

@return     ref element value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Object getRef(
   String key)
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       getRefBoolean - get ref boolean value
                                                                              */
                                                                             /**
            Get ref boolean value.

@return     ref boolean value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected boolean getRefBoolean(
   String key)
{
   return(false);
}
/*------------------------------------------------------------------------------

@name       getRefInt - get ref int value
                                                                              */
                                                                             /**
            Get ref int value.

@return     ref int value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected int getRefInt(
   String key)
{
   return(0);
}
/*------------------------------------------------------------------------------

@name       getRefMgr - get ref manager
                                                                              */
                                                                             /**
            Get ref manager.

@return     ref manager

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected RefMgr getRefMgr()
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       getRefString - get ref string value
                                                                              */
                                                                             /**
            Get ref string value.

@return     ref string value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected String getRefString(
   String key)
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       getRenderEditors - get render editors
                                                                              */
                                                                             /**
            Get render editors.

@return     render editors

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Map<String,RenderEditFunction> getRenderEditors()
{
   return(null);
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
   return(null);
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
   return(false);
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
   return(0);
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
   return(null);
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
   return(null);
}
/*------------------------------------------------------------------------------

@name       getSubscriptions - get subscriptions
                                                                              */
                                                                             /**
            Get subscriptions

@return     subscriptions

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected List<Subscription> getSubscriptions()
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       getTheme - get theme
                                                                              */
                                                                             /**
            Get theme. This implementation simply returns default theme.

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
      theme = IUITheme.defaultInstance();
      props().set("theme", theme);
   }
   return(theme);
}
/*------------------------------------------------------------------------------

@name       initialize - set properties
                                                                              */
                                                                             /**
            Set properties.

@return     initialProps     properties

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public P initialize(
   P initialProps)
{
   return(initialProps);
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
}
/*------------------------------------------------------------------------------

@name       invokeRenderEditors - invoke any render editors
                                                                              */
                                                                             /**
            Invoke any render editors.

@return     Edited root element descriptor

@param      root           element descriptor root

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected ElementDsc invokeRenderEditors(
   ElementDsc root)
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       preRender - component pre-render processing
                                                                              */
                                                                             /**
            Component pre-render processing.


@history    Fri Dec 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void preRender(
   P props)
{
}
/*------------------------------------------------------------------------------

@name       postRender - component post-render processing
                                                                              */
                                                                             /**
            Component post-render processing. This implementation is null.


@history    Fri Dec 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void postRender()
{
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

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

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

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
};
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
}
/*------------------------------------------------------------------------------

@name       setMounted - set whether component is mounted
                                                                              */
                                                                             /**
            Set whether the component is mounted. This is typically set
            in a useEffect cleanup function.

@param      bDismounted    whether component has been dismounted.

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void setMounted(
   boolean bMounted)
{
}
/*------------------------------------------------------------------------------

@name       setRef - set ref value
                                                                              */
                                                                             /**
            Set ref value.

@param      key      key
@param      value    value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void setRef(
   String key,
   Object value)
{
}
/*------------------------------------------------------------------------------

@name       setState - set state
                                                                              */
                                                                             /**
            Set state.

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
}
/*------------------------------------------------------------------------------

@name       setTheme - set theme
                                                                              */
                                                                             /**
            Set theme.

@return     theme

@param      theme    new theme

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public IUITheme setTheme(
   IUITheme theme)
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       subscribe - subscribe to specified Observable
                                                                              */
                                                                             /**
            Subscribe to specified Observable. Using this method instead of the
            direct invocation of rxjx.Observable allows built-in cancellation
            of all subscriptions when the subscriber is dismounted.

@return     theme

@param      observable     target observable
@param      onNext         onNext handler

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public <T> Subscription subscribe(
   Observable<T>      observable,
   Action1<? super T> onNext)
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       subscribe - subscribe to specified Observable
                                                                              */
                                                                             /**
            Subscribe to specified Observable. Using this method instead of the
            direct invocation of rxjx.Observable allows built-in cancellation
            of all subscriptions when the subscriber is dismounted.

@return     theme

@param      observable     target observable
@param      onNext         onNext handler
@param      onError        onError handler

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public <T> Subscription subscribe(
   Observable<T>      observable,
   Action1<? super T> onNext,
   Action1<?>         onError)
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       subscribe - subscribe to specified Observable
                                                                              */
                                                                             /**
            Subscribe to specified Observable. Using this method instead of the
            direct invocation of rxjx.Observable allows built-in cancellation
            of all subscriptions when the subscriber is dismounted.

@return     theme

@param      observable     target observable
@param      observer       observer

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public <T> Subscription subscribe(
   Observable<T> observable,
   Observer<? super T> observer)
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       subscribe - subscribe to specified Observable
                                                                              */
                                                                             /**
            Subscribe to specified Observable. Using this method instead of the
            direct invocation of rxjx.Observable allows built-in cancellation
            of all subscriptions when the subscriber is dismounted.

@return     theme

@param      observable     target observable
@param      onNext         onNext handler
@param      onError        onError handler
@param      onCompleted    onCompleted handler

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public <T> Subscription subscribe(
   Observable<T>      observable,
   Action1<? super T> onNext,
   Action1<?>         onError,
   Action0            onCompleted)
{
   return(null);
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
}
/*------------------------------------------------------------------------------

@name       useRef - initialize ref variables
                                                                              */
                                                                             /**
            Initialize ref variables. Each attribute of the specified
            ComponentRef contains a name (key) and an associated initial
            value. This ReactJava implementation splits each attribute into a
            separate React useRef hook.

@param      key      key
@param      value    value

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void useRef(
   String key,
   Object value)
{
}
/*------------------------------------------------------------------------------

@name       useState - initialize state variables
                                                                              */
                                                                             /**
            Initialize state variables. Each attribute of the specified
            state variable contains a name (key) and an associated initial
            value. This ReactJava implementation splits each attribute into a
            separate React state hook.

@param      key       key
@param      value     initial value

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void useState(
   String key,
   Object value)
{
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

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

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
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       RefMgr - constructor
                                                                              */
                                                                             /**
            Constructor

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

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

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected Object getRef(
   String key)
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       setRef - set ref
                                                                              */
                                                                             /**
            Set ref.

@param      key      ref variable name
@param      value    new ref variable value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void setRef(
   String key,
   Object value)
{
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

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void useRef(
   String key,
   Object value)
{
}
}//====================================// end RefMgr =========================//
/*==============================================================================

name:       RenderEditFunction - a generic BiFunction wrapper

purpose:    A generic BiFunction wrapper

history:    Mon Jun 26, 2017 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
public interface RenderEditFunction
   extends BiFunction<Component,ElementDsc,ElementDsc>
{
}//====================================// end RenderEditFunction =============//
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
                                       // (none)                              //

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
   return(null);
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
}
}//====================================// end StateMgr =======================//
}//====================================// end Component ----------------------//
