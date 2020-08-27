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
import com.google.gwt.core.client.GWT;
import elemental2.core.JsObject;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLBodyElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLStyleElement;
import elemental2.dom.Node;
import io.reactjava.client.core.react.IConfiguration.ICloudServices;
import io.reactjava.client.core.react.SEOInfo.SEOPageInfo;
import io.reactjava.client.core.rxjs.subscription.Subscription;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import jsinterop.base.Js;
import jsinterop.base.JsArrayLike;
import jsinterop.base.JsPropertyMap;
                                       // Component ==========================//
public abstract class Component<P extends Properties>
{
                                       // class constants --------------------//
public static final boolean   kSRCCFG_USE_STATE_HOOK = true;
public static final String    kFORCE_UPDATE_KEY      = "builtinForceUpdate";
public static final String    kPROMISE_CANCELLED     = "promise cancelled";

protected static final Logger kLOGGER = Logger.newInstance();

                                       // class variables                     //
protected static int   nextId;         // next elementId to be autoassigned   //
private   static Map<String,Component> // map of component by id              //
                       componentById;
                                       // map of component by id  subscribers //
private   static Map<String,List<Subscriber<Component>>>
                       componentByIdSubscribers;
                                       // map of stylesheet by component class//
protected static Map<String,String>
                       injectedStylesheets;

                                       // static field to hold any new        //
                                       // instance properties (see            //
                                       // newInstance()                       //
protected static Properties
                       newInstanceProperties;

                                       // protected instance variables ------ //
protected boolean      bDismounted;    // whether is dismounted               //
protected RefMgr       refMgr;         // component ref manager               //
protected StateMgr     stateMgr;       // component state manager             //
protected ReactElement reactElement;   // react element                       //
protected String       css;            // css                                 //
                                       // css injected styleId                //
protected String       cssInjectedStyleId;
                                       // list of injected styleIds           //
protected List<String> injectedStyleIds;
                                       // render editors                      //
protected Map<String,RenderEditFunction>
                       renderEditors;
                                       // subscriptions list                  //
protected List<Subscription>
                       subscriptions;
                                       // private instance variables -------- //
                                       // component function                  //
                                       // private to force access through     //
                                       // accessors to support JSXTransform   //
private java.util.function.Function<Properties, ReactElement>
                       componentFcn;
                                       // 200810 - made final package-private //
                                       // to make immutable and renamed from  //
                                       // 'props' to avoid  minified          //
                                       // confusion with react 'props'        //
final P                componentProperties;

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
   this.componentProperties = initializeProperties(null);
   initializeInternal();
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
   this.componentProperties = initializeProperties(initialProps);
   initializeInternal();
}
/*------------------------------------------------------------------------------

@name       addRenderEditor - add render editor
                                                                              */
                                                                             /**
            Add render editor.

@return     true iff render editor was not already registered.

@param      editorId    editor identifier (typically component classname)
@param      editFcn     edit function

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public boolean addRenderEditor(
   String             editorId,
   RenderEditFunction editFcn)
{
   boolean bAdded = getRenderEditors().put(editorId, editFcn) == null;
   if (bAdded)
   {
                                       // force update so render editor can   //
                                       // be invoked                          //
      forceUpdate();
   }
   return(bAdded);
}
/*------------------------------------------------------------------------------

@name       addSubscription - add subscription
                                                                              */
                                                                             /**
            Add subscription.

@param      subscription      subscription

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Subscription addSubscription(
   Subscription subscription)
{
   getSubscriptions().add(subscription);
   return(subscription);
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
   List<Subscription> subscriptions = getSubscriptions();
   while(subscriptions.size() > 0)
   {
      Subscription subscription = subscriptions.remove(0);
      {
         subscription.unsubscribe();
      }
   }
   removeComponentByIdSubscribers(props().getString("id"));
   removeComponentByIdSubscribers(getClass().getName());
}
/*------------------------------------------------------------------------------

@name       clearInjectedStylesheets - clear injected stylesheets
                                                                              */
                                                                             /**
            Remove all injected stylesheets and clear the list.

@return     empty list of injected stylesheets

@history    Sat Dec 15, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static Map<String,String> clearInjectedStylesheets()
{
   Map<String,String> stylesheets = getInjectedStylesheets();
   for (String styleId : stylesheets.values())
   {
      Element stylesheet = DomGlobal.document.getElementById(styleId);
      if (stylesheet != null)
      {
         stylesheet.remove();
      }
   }

   stylesheets.clear();

   return(stylesheets);
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

@name       componentDoSEOInfo - do any seoInfo processing for component
                                                                              */
                                                                             /**
            Do any seoInfo processing for specified component.

@history    Fri Dec 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void doSEOInfo(
   Properties props)
{
   SEOInfo seoInfo = props.getConfiguration().getSEOInfo();
   if (seoInfo != null && seoInfo.pageInfos != null)
   {
      String pageHash = Router.getPath();
      for (SEOPageInfo pageInfo : seoInfo.pageInfos)
      {
         if (pageHash != null && pageHash.equals(pageInfo.pageHash))
         {
            if (pageInfo.title != null && pageInfo.title.length() > 0)
            {
                                       // assign the page title               //
               ReactJava.setHead(
                  NativeObject.with(
                     "type", ReactJava.kHEAD_ELEM_TYPE_TITLE,
                     "id",   "seo" + ReactJava.kHEAD_ELEM_TYPE_TITLE,
                     "text", pageInfo.title));
            }
            if (pageInfo.description != null
                  && pageInfo.description.length() > 0)
            {
                                       // assign the page description         //
               ReactJava.setHead(
                  NativeObject.with(
                     "type",   ReactJava.kHEAD_ELEM_TYPE_META,
                     "id",     "seo" + ReactJava.kHEAD_ELEM_TYPE_META,
                     "name",   "description",
                     "content", pageInfo.description));
            }
            if (pageInfo.structuredDataType != null
                  && pageInfo.structuredDataType.length() > 0
                  && pageInfo.structuredData != null
                  && pageInfo.structuredData.length() > 0)
            {
                                       // assign the structured data          //
               ReactJava.setHead(
                  NativeObject.with(
                     "type",               ReactJava.kHEAD_ELEM_TYPE_STRUCTURED,
                     "id",                 "seo" + ReactJava.kHEAD_ELEM_TYPE_STRUCTURED,
                     "structuredDataType", pageInfo.structuredDataType,
                     "structuredData",     pageInfo.structuredData));
            }

            break;
         }
      }
   }
}
/*------------------------------------------------------------------------------

@name       defaultElementId - provide a default elementId
                                                                              */
                                                                             /**
            Provide a default elementId. This implemnetation is null;

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

@name       ensureComponentStyles - get native component
                                                                              */
                                                                             /**
            Get native component.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void ensureStyles()
{
   String styleId = getStyleId();
   if (styleId != null && ReactJava.getIsWebPlatform())
   {
                                       // inject the stylesheet at the start  //
                                       // of the body (OK as of a later HTML5 //
                                       // version) so that it is more specific//
                                       // than those of material-ui, for      //
                                       // example, which are placed at the    //
                                       // bottom of the head
      String cssSave = css;
      renderCSS();
                                       // check if the css has changed        //
      boolean bCSSChanged =
         (css != null && cssSave == null)
            || (css == null && cssSave != null)
            || (css != null && !css.equals(cssSave));

      if (bCSSChanged)
      {
         if (cssInjectedStyleId != null)
         {
            Element style =
               DomGlobal.document.getElementById(cssInjectedStyleId);

            if (style != null)
            {
               kLOGGER.logInfo(
                  "Component.ensureComponentStyles(): to update css, "
                + "removing injected styleId=" + styleId);
            }
            style.remove();
            cssInjectedStyleId = null;
         }
         if (css != null)
         {
                                       // inject current                      //
            HTMLStyleElement style =
               (HTMLStyleElement)DomGlobal.document.createElement("style");

            kLOGGER.logInfo(
               "Component.ensureComponentStyles(): injecting styleId="
                  + styleId);

            style.id          = styleId;
            style.textContent = css;

            HTMLBodyElement body = DomGlobal.document.body;
            body.insertBefore(style, body.firstElementChild);

            cssInjectedStyleId = styleId;
            getInjectedStyles().add(styleId);
            getInjectedStylesheets().put(styleId, styleId);
         }
      }
   }
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

@name       forClass - get component by specified class
                                                                              */
                                                                             /**
            Get component by specified class.

@return     component by specified class or null if not found

@history    Fri Nov 08, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static Observable<Component> forClass(
   Class clas)
{
                                       // the id map is multipurpose          //
   return(forId(clas.getName()));
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
   String      componentId = htmlElement.getAttribute("rjcomponentid");

   Component component   =
      componentId != null ? getComponentByIdMap().get(componentId) : null;

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
public static Observable<Component> forId(
   String id)
{
   Observable<Component> observable = Observable.create(
      (Subscriber<Component> subscriber) ->
      {
         Component component = getComponentByIdMap().get(id);
         if (component != null)
         {
            subscriber.next(component);
            subscriber.complete();
         }
         else
         {
            putComponentByIdSubscriber(id, subscriber);
         }
         return(subscriber);
      });

   return(observable);
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

@name       getComponentByIdSubscribers - get map of component by id subscribers
                                                                              */
                                                                             /**
            Get map of component by id subscribers.

@return     map of component by id subscribers

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected static Map<String,List<Subscriber<Component>>> getComponentByIdSubscribers()
{
   if (componentByIdSubscribers == null)
   {
      componentByIdSubscribers = new HashMap<>();
   }
   return(componentByIdSubscribers);
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

@name       getExportedResourceURL - get exported resource url
                                                                              */
                                                                             /**
            Get exported resource url from module resource path relative to
            specified 'public path' in nmodule gwt.xml file.

@return     exported resource url

@param      rsrcPublicRelativePath     path to resource realative to module
                                       specified public path

@history    Sat Aug 22, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected String getExportedResourceURL(
   String rsrcPublicRelativePath)
{
   return(GWT.getModuleBaseURL() + rsrcPublicRelativePath);
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

@name       getInjectedStylesheets - get stylesheets
                                                                              */
                                                                             /**
            Get stylesheets.

@return     stylesheets

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static Map<String,String> getInjectedStylesheets()
{
   if (injectedStylesheets == null)
   {
      injectedStylesheets = new HashMap<>();
   }
   return(injectedStylesheets);
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
   return(!bDismounted);
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
   return(reactElement);
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
   if (renderEditors == null)
   {
      renderEditors = new HashMap<>();
   }
   return(renderEditors);
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
   if (subscriptions == null)
   {
      subscriptions = new ArrayList<>();
   }
   return(subscriptions);
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

@name       initializeInternal - internal initialize method
                                                                              */
                                                                             /**
            Internal initialize method. This method is invoked in the
            constructor, so be careful that any referenced instance variables
            have been initialized. Specifically, in Java, the order for
            initialization statements is as follows:

               1. static variables and static initializers in order of
                  appearance in the source.

               2. instance variables and instance initializers in order of
                  appearance in the source.

               3. constructors.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
void initializeInternal()
{
   initConfiguration();
   initTheme();

   if (newInstanceProperties != null)
   {
                                       // ensure ReactJava window variable    //
                                       // for debugging purposes              //

      JsObject reactJava = ReactJava.getReactJavaWindowVariable();

                                       // allow a subclass to override        //
      initialize();
   }
}
/*------------------------------------------------------------------------------

@name       initializeProperties - initialize properties
                                                                              */
                                                                             /**
            Initialize properties.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
P initializeProperties(
   P initialProps)
{
   P properties =
      initialProps != null
         ? initialProps
         : newInstanceProperties != null
            ? (P)newInstanceProperties : (P)new Properties();

   properties.setComponent(this);
   return(properties);
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

@name       initStatics - initialize static variables
                                                                              */
                                                                             /**
            Initialize static variables.

@history    Thu Mar 12, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static void initStatics()
{
//   nextId        = 0;
   componentById = null;
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
   ElementDsc edited = root;
   for (RenderEditFunction editFcn : getRenderEditors().values())
   {
      edited = editFcn.apply(this, edited);
   }
   return(edited);
}
/*------------------------------------------------------------------------------

@name       newInstance - factory method
                                                                              */
                                                                             /**
            Package private factory method allowing componentProperties field
            to be final.

@return     new component instance, or null if factory not found

@param      classname      classname
@param      props          any props

@history    Tue Aug 11, 2020 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
static Component newInstance(
   String     classname,
   Properties props)
{
   synchronized(Component.class)
   {
                                       // 'synchronized' has no real effect   //
                                       // in ReactJava, but it's included here//
                                       // for readability                     //
      Component newInstance = null;

      Function<Properties,Component> fcn =
         ReactJava.getComponentFactory(classname);

      if (fcn != null)
      {
         newInstanceProperties = props;
         newInstance = fcn.apply(null);
         newInstanceProperties = null;
      }

      return(newInstance);
   }
}
/*------------------------------------------------------------------------------
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
   registerId(props);
   doSEOInfo(props);
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
protected P props()
{
   return(componentProperties);
}
/*------------------------------------------------------------------------------

@name       putComponentById - put map of component by id
                                                                              */
                                                                             /**
            Put map of component by id.

@param      id             id
@param      component      component

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void putComponentById(
   String    id,
   Component component)
{
   String    componentClass = component.getClass().getName();
   Component previousOwner  = getComponentByIdMap().put(id, component);
   String    previousClass  =
      previousOwner != null ? previousOwner.getClass().getName() : null;

   if (previousClass != null && !previousClass.equals(componentClass))
   {
      throw new IllegalStateException(
         "id=" + id + " of " + previousClass
        + " being assigned to " + componentClass
        + "\nDoes your render() method contain more than one root component?");
   }

   String previousDsc =
      previousClass == null ? "initial assignment": "existing id update";

   kLOGGER.logInfo(
      "Component.putComponentById(): "
    + "component=" + componentClass + ", id=" + id + ", " + previousDsc);

                                       // forId() subscribers                 //
   List<Subscriber<Component>> subscribers =
      getComponentByIdSubscribers().get(id);

   if (subscribers != null)
   {
      while (subscribers.size() > 0)
      {
         Subscriber<Component> subscriber = subscribers.remove(0);
         subscriber.next(component);
         subscriber.complete();
      }
   }

   getComponentByIdMap().put(componentClass, component);

                                       // forClass() subscribers              //
   subscribers = getComponentByIdSubscribers().get(componentClass);
   if (subscribers != null)
   {
      while (subscribers.size() > 0)
      {
         Subscriber<Component> subscriber = subscribers.remove(0);
         subscriber.next(component);
         subscriber.complete();
      }
   }
}
/*------------------------------------------------------------------------------

@name       putComponentByIdSubscriber - put map of component by id subscriber
                                                                              */
                                                                             /**
            Put map of component by id subscriber.

@param      id             id
@param      subscriber     subscriber

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void putComponentByIdSubscriber(
   String     id,
   Subscriber subscriber)
{
   List<Subscriber<Component>> subscribers = getComponentByIdSubscribers().get(id);
   if (subscribers == null)
   {
      subscribers = new ArrayList<>();
      getComponentByIdSubscribers().put(id, subscribers);
   }
   if (!subscribers.contains(subscriber))
   {
      subscribers.add(subscriber);
   }
}
/*------------------------------------------------------------------------------

@name       removeComponentByIdSubscribers - remove subscribers for id
                                                                              */
                                                                             /**
            Remove subscribers for id.

@param      id    id

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected static void removeComponentByIdSubscribers(
   String id)
{
   getComponentByIdSubscribers().remove(id);
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

@name       registerId - register any id
                                                                              */
                                                                             /**
            Register any id.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void registerId(
   Properties props)
{
   String id = props().getString("id");
   if (id == null)
   {
      throw new IllegalStateException("id may not be null");
   }
   if (id.startsWith("rjAuto"))
   {
      String defaultId = defaultElementId();
      if (defaultId != null)
      {
         props().set("id", defaultId);
         id = defaultId;
      }
   }

   putComponentById(id, this);
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

@name       removeStyles - remove any injected styles for the component
                                                                              */
                                                                             /**
            Remove any injected styles for the specified component.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void removeStyles()
{
   if (ReactJava.getIsWebPlatform())
   {
      List<String> componentStyles = getInjectedStyles();
      for (String styleId : componentStyles)
      {
         Element style = DomGlobal.document.getElementById(styleId);
         if (style != null)
         {
            if (true)
            {
               kLOGGER.logInfo(
                  "ReactJava.removeComponentStyles(): removing styleId="
                     + styleId);
            }
            style.remove();
         }
         else
         {
            kLOGGER.logWarning(
               "ReactJava.removeComponentStyles(): "
             + "cannot find styleId=" + styleId);
         }
      }
      componentStyles.clear();
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

@name       setMounted - set whether component is mounted
                                                                              */
                                                                             /**
            Set whether the component is mounted. This is typically set
            in a useEffect cleanup function.

@param      bMounted    whether component has been dismounted.

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void setMounted(
   boolean bMounted)
{
   this.bDismounted = !bMounted;
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
   if (kSRCCFG_USE_STATE_HOOK)
   {
      if (getMounted())
      {
         // see issue #37
         // getStateMgr().setState(key, value) does not currently propogate the
         // render to the children, which has taken a while to debug
         // unsuccessfully, so for now force an update instead...
         getStateMgr().setState(key, value);
      }
      else
      {
         kLOGGER.logWarning(
            "Component.setState(): ignored since component is not mounted, "
               + getClass().getName());
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
   useEffect(effectHandler, null);
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
   React.useEffect(
      effectHandler,
      dependencies != null ? dependencies : Js.uncheckedCast(Js.undefined()));
}
/*------------------------------------------------------------------------------

@name       useEffectTrackDismounted - built-in useEffect hook
                                                                              */
                                                                             /**
            Built-in useEffect hook to track when the component is dismounted.

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void useEffectTrackDismounted()
{
   useEffect(() ->
   {
      setMounted(true);
      return(() ->
      {
                                       // cleanup function                    //
         setMounted(false);
         cancelSubscriptions();
      });
   });
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
   Object copy = null;
   if (state != null)
   {
      JsArrayLike stateMembers = state.get(key);
      if (stateMembers != null)
      {
         copy = stateMembers.getAt(0);
      }
   }
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
   if (value == null)
   {
      throw new IllegalArgumentException("valuye may not be null");
   }
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
                                       // be in a conditional;                //
                                       // the stateVariable as a two element  //
                                       // JsArray is returned by React and    //
                                       // stored in the map for the key       //
   state.put(key, React.useState(value));
}
}//====================================// end StateMgr =======================//
}//====================================// end Component ----------------------//
