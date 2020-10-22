/*==============================================================================

name:       ReactJava.java

purpose:    ReactJava Java Interface.

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import com.giavaneers.util.gwt.APIRequestor;
import com.giavaneers.util.gwt.Logger;
import elemental2.core.JsObject;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLLinkElement;
import elemental2.dom.HTMLMetaElement;
import elemental2.dom.HTMLScriptElement;
import elemental2.dom.HTMLTitleElement;
import elemental2.dom.NodeList;
import elemental2.dom.StyleSheet;
import io.reactjava.client.providers.platform.web.PlatformWeb;
import java.util.Map;
import java.util.function.Function;
import jsinterop.base.Js;
                                       // ReactJava ==========================//
public class ReactJava
{
                                       // class constants --------------------//
private static final Logger kLOGGER  = Logger.newInstance();
public static final  String kFACTORY = "factory";
public static final  String kMARKUP  = "markup";
public static final  String kSTYLES  = "styles";

                                       // head element types                  //
public static final String kHEAD_ELEM_TYPE_LINK       = "link";
public static final String kHEAD_ELEM_TYPE_META       = "meta";
public static final String kHEAD_ELEM_TYPE_TITLE      = "title";
public static final String kHEAD_ELEM_TYPE_SCRIPT     = "script";
public static final String kHEAD_ELEM_TYPE_STRUCTURED = "structured";

                                       // class variables ------------------- //
                                       // whether booted                      //
protected static boolean             bBooted;
                                       // whether initialized                 //
protected static boolean             bInitialized;

                                       // core compiler preprocessor          //
//protected static IReactCodeGenerator generator;

                                       // component properties native         //
                                       // fieldname which changes with degree //
                                       // of minification                     //
protected static String              nativeComponentPropertiesFieldname;

                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       addBaseTag - add base tag as a function of the url
                                                                              */
                                                                             /**
            The React router and perhaps other functionality depends upon the
            base hRef assignment to accommodate a base url offset from the
            hostname for the calculation of relative urls. This typically needs
            to be assigned in the head section of the main html file and often
            causes errors since it must be modified for each deployment to a
            different server. This function make the assignment dynamic,
            alleviating the need to supply a base tag in the main html.

            Is null for react native.

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected static void addBaseTag()
{
   if (getIsWebPlatform()
                                       // not for react native                //
         && DomGlobal.document.getElementsByTagName("base").getLength() == 0)
   {
                                       // there is no base tag, so add one    //
      String  path = DomGlobal.window.location.getPathname();
      Element base = DomGlobal.document.createElement("base");
      base.setAttribute("href", path.substring(0, path.lastIndexOf('/') + 1));

      Element head = DomGlobal.document.head;
      head.insertBefore(base, head.firstElementChild);
   }
}
/*------------------------------------------------------------------------------

@name       boot - boot react
                                                                              */
                                                                             /**
            Boot react.

@return     Observable for boot completion

@param     app             app

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static ReactElement boot(
   AppComponentTemplate app)
{
   bBooted = true;
                                       // add base tag to document if required//
   addBaseTag();
                                       // ensure original launch url          //
   Router.getLaunchURL();

   final ReactElement[] renderElement = new ReactElement[1];

   IConfiguration configuration = Configuration.sharedInstance();
   configuration.setApp(app);
   initialize(configuration, null, (Object response, Object reqToken) ->
   {
      if (response instanceof Throwable)
      {
         kLOGGER.logError((Throwable)response);
      }
      else if (getIsWebPlatform())
      {
         DomGlobal.setTimeout(
            (e) ->
            {
                                       // currently in the last inject http   //
                                       // request completion handler, so to   //
                                       // make debugging easier, do the       //
                                       // initial render on a new task...     //
               try
               {
                  Router.render(configuration);
               }
               catch(Throwable t)
               {
                  kLOGGER.logError(t);
               }
            }, 0, configuration);
      }
      else
      {
         renderElement[0] = Router.render(configuration);
      }
   });

   return(renderElement[0]);
}
/*------------------------------------------------------------------------------

@name       componentRender - component render process
                                                                              */
                                                                             /**
            Component render process.

@return     native component for specified ReactJava component

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static <P extends Properties> INativeFunctionalComponent componentRender(
   Component component)
{
   return((props) ->
   {
      Function<P,ReactElement> fcn = getComponentFcn(component);
      if (fcn != null)
      {
                                       // pre-render processing               //
         component.preRender(props);
                                       // built-in useEffect()                //
         component.useEffectTrackDismounted();
                                       // invoke the component function       //
         component.reactElement = fcn.apply((P)props);

                                       // update any styles                   //
         component.ensureStyles();
                                       // post-render processing              //
         component.postRender();
      }
      return(component.reactElement);
   });
}
 /*------------------------------------------------------------------------------

@name       createElement - create react element of the component class
                                                                              */
                                                                             /**
            Convenience method to create react element of the specified
            component class.

            The old render sequence is as follows:

            1. The router finds the appropriate component class for the current
               route.

            2. The current configuration is added as an element of a new
               Properties instance.

            3. A function to construct an instance of the componentClass is
               found from the global resources map. This function is invoked
               passing the new Properties instance as an argument.

            4. The properties of the new component instance are retrieved.

            5. A function to generate a renderable component for the
               componentClass is found from the global resources map. This
               function takes a properties instance as   an argument.

            6. This function is invoked, passing the new component instance
               properties as an argument. The result is the target element to be
               rendered.

            7. The Router finally invokes the ReactDOM.render() method, passing
               the target element to be rendered as an argument.

            The new render sequence is as follows:

            1. The router finds the appropriate component class for the current
               route.

            2. The current configuration is added as an element of a new
               Properties instance.

            3. A function to construct an instance of the componentClass is
               found from the global resources map. This function is invoked
               passing the new Properties instance as an argument.

            4. The render() method of the instance is invoked, which assigns the
               target element to be rendered as its 'renderElement' instance
               variable.

            5. The Router finally invokes the ReactDOM.render() method, passing
               the target element to be rendered as an argument.


@return     react element of the specified component class

@param      type        component class

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static <P extends Properties> ReactElement createElement(
   Class          type,
   IConfiguration configuration)
{
   return(createElement(type, configuration, new ReactElement[0]));
}

public static <P extends Properties> ReactElement createElement(
   Class type, IConfiguration configuration, ReactElement...children)
{
   ReactElement element;
   Properties props     = Properties.with("configuration", configuration);
   String     classname = type.getName();
   Component  component = Component.newInstance(classname, props);
   if (component == null)
   {
      throw new IllegalStateException("No factory method found for "+classname);
   }

   element =
      createElement( componentRender(component), component.props(), children);

   return(element);
}
/*------------------------------------------------------------------------------

@name       createElement - create react element of the specified component
                                                                              */
                                                                             /**
            Convenience method to create react element of the specified
            component.

@return     react element of the specified class

@param      component      target component

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static <P extends Properties> ReactElement createElement(
   Component  component)
{
   Properties   props   = component.props();
   ReactElement element = createElement(componentRender(component), props);

   return(element);
}
/*------------------------------------------------------------------------------

@name       createElement - create react element of the specified class
                                                                              */
                                                                             /**
            Convenience method to create react element of the specified class.

@return     react element of the specified class

@param      type        standard html tag
@param      props       properties

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static <P extends Properties> ReactElement createElement(
   String type, P props)
{
   ReactElement element =
      Character.isLowerCase(type.charAt(0))
         ? React.createElement(type, props)
         : React.createElement(ReactJavaNative.getType(type), props);

   return(element);
}
public static <P extends Properties> ReactElement createElement(
   String type, P props, String value)
{
   ReactElement element =
      Character.isLowerCase(type.charAt(0))
         ? React.createElement(type, props, value)
         : React.createElement(
            ReactJavaNative.getType(type), props, value);

   return(element);
}
public static <P extends Properties> ReactElement createElement(
   String type, P props, ReactElement...children)
{
   ReactElement element =
      Character.isLowerCase(type.charAt(0))
         ? React.createElement(type, props, children)
         : React.createElement(ReactJavaNative.getType(type), props, children);

   return(element);
}
/*------------------------------------------------------------------------------

@name       createElement - create react element of the specified class
                                                                              */
                                                                             /**
            Convenience method to create react element of the specified class.

@return     react element of the specified class

@param      type        standard html tag
@param      props       properties

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static <P extends Properties> ReactElement createElement(
   INativeFunctionalComponent<P> type, P props)
{
   return(React.createElement(type, props));
}

public static <P extends Properties> ReactElement createElement(
   INativeFunctionalComponent<P> type, P props, String value)
{
   return(React.createElement(type, props, value));
}

public static <P extends Properties> ReactElement createElement(
   INativeFunctionalComponent<P> type, P props, ReactElement...children)
{
   return(React.createElement(type, props, children));
}
/*------------------------------------------------------------------------------

@name       createElement - create react element of the specified class
                                                                              */
                                                                             /**
            Convenience method to create react element of the specified class.

@return     react element of the specified class

@param      type        standard html tag
@param      props       properties

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static <P extends Properties> ReactElement createElement(
   INativeComponentConstructor type, P props)
   throws Exception
{
   return(React.createElement(type, props));
}
public static <P extends Properties> ReactElement createElement(
   INativeComponentConstructor type, P props, ReactElement...children)
   throws Exception
{
   return(React.createElement(type, props, children));
}
/*------------------------------------------------------------------------------

@name       getCodeGenerator - get ReactJava code generator
                                                                              */
                                                                             /**
            Get ReactJava code generator.

@return     ReactJava code generator

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
//public static IReactCodeGenerator getCodeGenerator()
//{
//   if (generator == null)
//   {
//                                       // generate preprocessor classes       //
//      //generator = GWT.create(IReactCodeGenerator.class);
//      generator = new ReactCodeGeneratorImplementation();
//   }
//   return(generator);
//}
/*------------------------------------------------------------------------------

@name       getComponentFactory - get factory supplier for specified classname
                                                                              */
                                                                             /**
            Get factory supplier for specified classname.

@return     factory supplier for specified classname

@param      classname      classname

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Function<Properties,Component> getComponentFactory(
   String classname)
{
   Function<Properties,Component> factory =
      (Function<Properties,Component>)ReactGeneratedCode.getFactory(classname);

   return(factory);
}
/*------------------------------------------------------------------------------

@name       getComponentFactoryMap - get component factory map
                                                                              */
                                                                             /**
            Get component factory map.

@return     component factory map

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Map<String,Function> getComponentFactoryMap()
{
   return(ReactGeneratedCode.getFactoryMap());
}
/*------------------------------------------------------------------------------

@name       getComponentFcn - get component function for specified component
                                                                              */
                                                                             /**
            Get component function for specified component.

@return     component function for specified component

@param      component      target component

@history    Mon Dec 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static <P extends Properties> Function<P, ReactElement> getComponentFcn(
   Component component)
{
   Function<P, ReactElement> fcn = component.getComponentFcn();
   if (fcn == null)
   {
      component.render();
      fcn = component.getComponentFcn();
   }

   return(fcn);
}
/*------------------------------------------------------------------------------

@name       getIsWebPlatform - convenience method to get whether is web platform
                                                                              */
                                                                             /**
            Convenience method to get whether is web platform.

@return     true iff is web platform

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean getIsWebPlatform()
{
   boolean bWebPlatform =
      getPlatformProvider().equals(PlatformWeb.class.getName());

   return(bWebPlatform);
}
/*------------------------------------------------------------------------------

@name       getNativeComponentPropertiesFieldname - get native fieldname
                                                                              */
                                                                             /**
            Get native componentProperties fieldname.

            By investigation:

               1. is the first item for which the callback is invoked.
               2. detailed   -> "io_reactjava_client_core_react_Component"
                                "_componentProperties"
               3. pretty     -> "componentProperties"
               4. obfuscated -> "g"

@return     native componentProperties fieldname

@param      componentMap      component as JsPropertyMap

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
//public static String getNativeComponentPropertiesFieldname(
//   JsPropertyMap<Object> componentMap)
//{
//   if (nativeComponentPropertiesFieldname == null)
//   {
//      JsForEachCallbackFn callback = new JsForEachCallbackFn()
//      {
//         public void onKey(String key)
//         {
//            if (nativeComponentPropertiesFieldname == null)
//            {
//                                       // since the instance variable is last //
//                                       // in the source, it is first here...  //
//               nativeComponentPropertiesFieldname = key;
//
//               //kLOGGER.logInfo(
//               //   "ReactJava.getNativeComponentPropertiesFieldname(): ="
//               //      + key);
//            }
//         }
//      };
//      componentMap.forEach(callback);
//   }
//   return(nativeComponentPropertiesFieldname);
//}
/*------------------------------------------------------------------------------

@name       getPlatformProvider - get platform provider
                                                                              */
                                                                             /**
            Get platform provider.

@return     platform provider

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getPlatformProvider()
{
   return(ReactGeneratedCode.getPlatformProvider());
}
/*------------------------------------------------------------------------------

@name       getProvider - get provider
                                                                              */
                                                                             /**
            Get provider for specified interface.

@return     provider for specified interface

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static <T> T getProvider(
   Class interfaceType)
{
   IProvider      provider  = null;
   IConfiguration config    = Router.getConfiguration();
   String providerClassname = config.getProviders().get(interfaceType.getName());

   if (providerClassname != null)
   {
      provider =
         getProvider(providerClassname).apply(
            Properties.with("configuration", config));
   }

   return(Js.uncheckedCast(provider));
}
/*------------------------------------------------------------------------------

@name       getProvider- get provider factory for specified classname
                                                                              */
                                                                             /**
            Get factory for specified classname.

@return     factory for specified classname

@param      classname      classname

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Function<Properties,IProvider> getProvider(
   String classname)
{
   Function<Properties,IProvider> factory =
      (Function<Properties,IProvider>)ReactGeneratedCode.getFactory(classname);

   return(factory);
}
/*------------------------------------------------------------------------------

@name       getReactJavaWindowVariable - get ReactJava widow variable
                                                                              */
                                                                             /**
            Get ReactJava widow variable. Primarily for debugging purposes
            to ensure the ReactJava window variable has been initialized and
            its contents can be verified.

@history    Fri May 15, 2020 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native JsObject getReactJavaWindowVariable()
/*-{
    var reactJava = $wnd.ReactJava;
    return(reactJava);
}-*/;
/*------------------------------------------------------------------------------

@name       initialize - initialize
                                                                              */
                                                                             /**
            Initialize.

@param      configuration     configuration
@param      requestToken      request token
@param      requestor         requestor

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static void initialize(
   IConfiguration configuration,
   Object         requestToken,
   APIRequestor   requestor)
{
   if (!getIsWebPlatform() || Utilities.scriptByPath != null)
   {
                                       // either already initialized          //
                                       // or a mobile platform                //
      if (requestor != null)
      {
         requestor.apiResponse("OK", requestToken);
      }
   }
   else
   {
      Utilities.injectScriptsAndCSS(
         configuration, null, requestToken,
         (Object response1, Object reqToken1) ->
         {
            if (response1 instanceof Throwable)
            {
               if (requestor != null)
               {
                  requestor.apiResponse((Throwable)response1, requestToken);
               }
            }
            else
            {
                                       // ensure ReactJava window variable    //
                                       // for debugging purposes              //
               JsObject reactJava = ReactJava.getReactJavaWindowVariable();

               configuration.initialize().subscribe(
                  (Object rsp) ->
                  {
                     bInitialized = true;
                     if (requestor != null)
                     {
                        requestor.apiResponse(response1, requestToken);
                     }
                  },
                  (Object error) ->
                  {
                     if (requestor != null)
                     {
                        requestor.apiResponse(error, requestToken);
                     }
                  });
            }
         });
   }
}
/*------------------------------------------------------------------------------

@name       initialized - test whether initialized
                                                                              */
                                                                             /**
            Test whether initialized.

@return     true iff initialized

@history    Thu Sep 7, 2017 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean initialized()
{
   return(bInitialized);
}
/*------------------------------------------------------------------------------

@name       setHead - set a head element
                                                                              */
                                                                             /**
            Set a head element.

@return     element descriptor

@param      descriptor     element descriptor, where each tuple is an element
                           attribute

@history    Wed Jun 12, 2019 08:46:23 (LBM) created.

@notes      see https://developers.google.com/search/docs/guides/intro-structured-data
                                                                              */
//------------------------------------------------------------------------------
public static Element setHead(
   NativeObject descriptor)
{
   String type = descriptor.getString("type");
   if (type  == null)
   {
      throw new IllegalArgumentException("type may not be null");
   }

   Element element = DomGlobal.document.createElement(type);
   element.setAttribute("id", descriptor.getString("id"));

   switch(type)
   {
      case kHEAD_ELEM_TYPE_LINK:
      {
         HTMLLinkElement link = (HTMLLinkElement)element;
         link.as              = descriptor.getString("as");
         link.charset         = descriptor.getString("charset");
         link.disabled        = descriptor.getBoolean("disabled");
         link.href            = descriptor.getString("href");
         link.hreflang        = descriptor.getString("hreflang");
         link.media           = descriptor.getString("media");
         link.rel             = descriptor.getString("rel");
         link.rev             = descriptor.getString("rev");
         link.sheet           = (StyleSheet)descriptor.get("sheet");
         link.target          = descriptor.getString("target");
         link.type            = descriptor.getString("type");
         break;
      }
      case kHEAD_ELEM_TYPE_META:
      {
         HTMLMetaElement meta = (HTMLMetaElement)element;
         meta.name            = descriptor.getString("name");
         meta.content         = descriptor.getString("content");
         break;
      }
      case kHEAD_ELEM_TYPE_SCRIPT:
      {
         HTMLScriptElement script = (HTMLScriptElement)element;
         script.charset           = descriptor.getString("charset");
         script.defer             = descriptor.getBoolean("defer");
         script.event             = descriptor.getString("event");
         script.htmlFor           = descriptor.getString("htmlFor");
         script.src               = descriptor.getString("src");
         script.text              = descriptor.getString("text");
         script.type              = descriptor.getString("type");
         break;
      }
      case kHEAD_ELEM_TYPE_STRUCTURED:
      {
         String structuredDataType = descriptor.getString("structuredDataType");
         String structuredData     = descriptor.getString("structuredData");

         HTMLScriptElement script = (HTMLScriptElement)element;
         script.type  = "application/ld+json";
         script.text  = "'@context': 'https://schema.org',";
         script.text += "'@type': '" + structuredDataType + "',";
         script.text += structuredData;
         break;
      }
      case kHEAD_ELEM_TYPE_TITLE:
      {
         HTMLTitleElement title = (HTMLTitleElement)element;
         title.text             = descriptor.getString("text");
         break;
      }
      default:
      {
         throw new UnsupportedOperationException(type);
      }
   }

   Element  head     = DomGlobal.document.head;
   Element  remove   = null;
   NodeList existing = DomGlobal.document.getElementsByTagName(type);

   removeAnyExisting:
   for (int idx = 0, idxMax = existing.getLength(); idx < idxMax; idx++)
   {
      switch(type)
      {
                                       // identify any existing equivalent    //
         case kHEAD_ELEM_TYPE_META:
         {
            HTMLMetaElement elem  = (HTMLMetaElement)element;
            HTMLMetaElement chase = (HTMLMetaElement)existing.item(idx);
            if (elem.name.equals(chase.name))
            {
               remove = chase;
               break removeAnyExisting;
            }
            break;
         }
         case kHEAD_ELEM_TYPE_STRUCTURED:
         {
            HTMLScriptElement elem  = (HTMLScriptElement)element;
            HTMLScriptElement chase = (HTMLScriptElement)existing.item(idx);
            if (elem.type.equals(chase.type))
            {
               remove = chase;
               break removeAnyExisting;
            }
            break;
         }
         case kHEAD_ELEM_TYPE_TITLE:
         {
            remove = (Element)existing.item(idx);
            break removeAnyExisting;
         }
      }
   }
   if (remove != null)
   {
                                       // remove any existing equivalent      //
      head.removeChild(remove);
   }
                                       // insert the new element              //
   head.insertBefore(element, head.firstChild);

   return(element);
}
}//====================================// end ReactJava ======================//
