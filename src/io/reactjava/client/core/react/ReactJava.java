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
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.user.client.Timer;
import io.reactjava.client.core.providers.platform.web.PlatformWeb;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import jsinterop.base.Js;
import jsinterop.base.JsForEachCallbackFn;
import jsinterop.base.JsPropertyMap;
import jsinterop.core.html.Window;
                                       // ReactJava ==========================//
public class ReactJava
{
                                       // class constants --------------------//
private static final Logger kLOGGER  = Logger.newInstance();
public static final  String kFACTORY = "factory";
public static final  String kMARKUP  = "markup";
public static final  String kSTYLES  = "styles";

                                       // class variables ------------------- //
                                       // whether initialized                 //
protected static boolean             bInitialized;
                                       // core compiler preprocessor          //
protected static IReactCodeGenerator generator;

                                       // map of stylesheet by component class//
protected static Map<String,String>  injectedStylesheets;

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

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected static void addBaseTag()
{
   if (Window.getDocument().getElementsByTagName("base").getLength() == 0)
   {
                                       // there is no base tag, so add one    //
      String  path = Window.getLocation().getPathname();
      jsinterop.core.dom.Element base = Window.getDocument().createElement("base");
      base.setAttribute("href", path.substring(0, path.lastIndexOf('/') + 1));

      jsinterop.core.dom.Element head = Window.getDocument().getHead();
      head.insertBefore(base, head.getFirstChild());
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
                                       // add base tag to document if required//
   addBaseTag();

   final ReactElement[] renderElement = new ReactElement[1];
   IConfiguration  configuration = Configuration.sharedInstance();
   initialize(configuration, null, (Object response, Object reqToken) ->
   {
      if (response instanceof Throwable)
      {
         kLOGGER.logError((Throwable)response);
      }
      else
      {
         Map<String,Class> routes = configuration.getNavRoutes();
         if (routes == null)
         {
            configuration.setNavRoutes(
               new HashMap<String,Class>()
               {{
                  put("", app.getClass());
               }});
         }
         if (getIsWebPlatform())
         {
            new Timer()
            {
                                       // currently in the last inject http   //
                                       // request completion handler, so to   //
                                       // make debugging easier, do the       //
                                       // initial render on a new task...     //
               public void run()
               {
                  try
                  {
                     Router.render(configuration);
                  }
                  catch(Throwable t)
                  {
                     kLOGGER.logError(t);
                  }
               }
            }.schedule(0);
         }
         else
         {
            renderElement[0] = Router.render(configuration);
         }
      }
   });

   return(renderElement[0]);
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
      Document.get().getElementById(styleId).removeFromParent();
   }

   stylesheets.clear();

   return(stylesheets);
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
   ReactElement element;
   Properties props     = Properties.with("configuration", configuration);
   String     classname = type.getName();
   Component  component = getComponentFactory(classname).apply(props);

   element =
      createElement(
         getRenderableComponent(component), component.props());

   return(element);
}

public static <P extends Properties> ReactElement createElement(
   Class type, IConfiguration configuration, ReactElement...children)
{
   ReactElement element;
   Properties props     = Properties.with("configuration", configuration);
   String     classname = type.getName();
   Component  component = getComponentFactory(classname).apply(props);

   element =
      createElement(
         getRenderableComponent(component), component.props(),
         children);

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
   Properties props   = component.props();
   ReactElement element = createElement(getRenderableComponent(component), props);

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
         : React.createElement(
            ReactJavaNative.getType(type), props, children);

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
   INativeRenderableComponent<P> type, P props)
{
   return(React.createElement(type, props));
}

public static <P extends Properties> ReactElement createElement(
   INativeRenderableComponent<P> type, P props, String value)
{
   return(React.createElement(type, props, value));
}

public static <P extends Properties> ReactElement createElement(
   INativeRenderableComponent<P> type, P props, ReactElement...children)
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

@name       ensureComponentStyles - get native component
                                                                              */
                                                                             /**
            Get native component.

@param      component      component
@param      bUpdate        iff true, update

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void ensureComponentStyles(
   Component component,
   boolean   bUpdate)
{
   String classname = component.getClass().getName();
   if (bUpdate || getInjectedStylesheets().get(classname) == null)
   {
                                       // inject the stylesheet at the start  //
                                       // of the body (OK as of a later HTML5 //
                                       // version) so that it is more specific//
                                       // than those of material-ui, for      //
                                       // example, which are placed at the    //
                                       // bottom of the head
      String styles = null;
      component.renderCSS();
      if (component.css != null && component.css.length() > 0)
      {
         styles = component.css;
      }
      if (styles != null)
      {
         String styleId = styleIdFromClassname(classname);
         if (bUpdate)
         {
            Document.get().getElementById(styleId).removeFromParent();
         }

         StyleElement style = Document.get().createStyleElement();
         style.setId(styleId);
         style.setInnerText(styles);
         Document.get().getBody().insertFirst(style);

         getInjectedStylesheets().put(classname, styleId);
      }
   }
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
public static IReactCodeGenerator getCodeGenerator()
{
   if (generator == null)
   {
                                       // generate preprocessor classes       //
      //generator = GWT.create(IReactCodeGenerator.class);
      generator = new ReactCodeGeneratorImplementation();
   }
   return(generator);
}
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
      (Function<Properties,Component>)getCodeGenerator().getFactory(classname);

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
   return(getCodeGenerator().getFactoryMap());
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
   Function<P, ReactElement> fcn = component.componentFcn;
   if (fcn == null)
   {
      component.render();
      fcn = component.componentFcn;
   }

   return(fcn);
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
      injectedStylesheets = new HashMap<String,String>();
   }
   return(injectedStylesheets);
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
public static String getNativeComponentPropertiesFieldname(
   JsPropertyMap<Object> componentMap)
{
   if (nativeComponentPropertiesFieldname == null)
   {
      JsForEachCallbackFn callback = new JsForEachCallbackFn()
      {
         public void onKey(String key)
         {
            if (nativeComponentPropertiesFieldname == null)
            {
                                       // since the instance variable is last //
                                       // in the source, it is first here...  //
               nativeComponentPropertiesFieldname = key;

               kLOGGER.logInfo(
                  "ReactJava.getNativeComponentPropertiesFieldname(): =" + key);
            }
         }
      };
      componentMap.forEach(callback);
   }
   return(nativeComponentPropertiesFieldname);
}
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
   return(getCodeGenerator().getPlatformProvider());
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
      (Function<Properties,IProvider>)getCodeGenerator().getFactory(classname);

   return(factory);
}
/*------------------------------------------------------------------------------

@name       getRenderableComponent - get native component for specified component
                                                                              */
                                                                             /**
            Get native component.

@return     native component

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static <P extends Properties> INativeRenderableComponent getRenderableComponent(
   String classname)
{
   Component component = getComponentFactory(classname).apply(new Properties());
   return(getRenderableComponent(component));
}
/*------------------------------------------------------------------------------

@name       getRenderableComponent - get native component for specified component
                                                                              */
                                                                             /**
            Get native component.

@return     native component

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static <P extends Properties> INativeRenderableComponent getRenderableComponent(
   Component component)
{
   return((props) ->
   {
                                       // assign the private instance variable//
      JsPropertyMap<Object> map = Js.asPropertyMap(component);
      map.set(getNativeComponentPropertiesFieldname(map), props);

      ReactElement element = null;

      Function<P, ReactElement> fcn = getComponentFcn(component);
      if (fcn != null)
      {
         element = fcn.apply((P)props);
      }
      return(element);
   });
}
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
            bInitialized = true;
            if (requestor != null)
            {
               requestor.apiResponse(response1, requestToken);
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

@name       styleIdFromClassname - style element id from component classname
                                                                              */
                                                                             /**
            Get style element id from component classname.

@return     style element id from component classname

@param      classname      component classname

@history    Sat Dec 15, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static String styleIdFromClassname(
   String classname)
{
   return("style_" + classname.replace(".","_"));
}
}//====================================// end ReactJava ======================//
