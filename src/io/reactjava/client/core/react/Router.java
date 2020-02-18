/*==============================================================================

name:       Router - router

purpose:    ReactJava router

            ReactJava determines the routes for a component by invocation of
            its getNavRoutes() method whose results can change from one
            invocation to the next. The default implementation of the
            getNavRoutes() method returns no routes.

            A route is a tuple of a path specification with an associated
            component class. The routes declared by a component are organized
            as a Map, where the key is the path and the value is the
            associated target component class.  For example,

               Map<String,Class> navRoutes = new HashMap<>()
               {{
                  put("",                           Component1.class);
                  put("http://myApp.html#animals",  Component1.class);
                  put("animals",                    Component1.class);
                  put("animals]heading",            Component1.class);
                  put(".]heading",                  Component1.class);
                  put("flowers/:color/:leafcount?", Component2.class);
                  put("trees/:height(ten|twenty)",  Component3.class);
               }};

            declares seven different routes, each with a different format.

            The first is known as the 'default' route; namely, the route
            specified when only the webApp url is used. For example, if the
            webApp is accessible at 'http://myApp.html', Component1 will be
            rendered.

            The second specifically specifies the complete url including the
            path, 'animals' which maps to Component1 as before. Note this is the
            format to be used in an anchor tag as well. For example,

               <a href="http://myApp.html#animals"></a>

            will cause Component1 to be rendered. This format will work as a
            navRoute but is discouraged in favor of the format of the third
            route.

            The third is the simplest way to specify a specific component, in
            this case, Component1 again.

            The fourth names not only the target component to be rendered, but
            also the elementId of a specific element that should be scrolled
            to the top. In this case, Component1 will be rendered and the
            element with id 'heading' will be scrolled to the top. An anchor
            tag can accomplish the same thing, such as

               <a href="http://myApp.html#animals]heading"></a>

            The fifth names only a target elementId of the current component
            rendered. Like the previous, the element with the specified id will
            be scrolled to the top.

            The sixth matches with the relative url 'flowers', and also assigns
            the target component 'color' property with the path value element
            following the 'flowers' value and assigns the 'leafcount' property
            with the path value element following the 'color' value if it
            exists. Note the first parameter is required while the second is
            optional since its path specification includes a trailing
            question mark. For example, the relative url '/flowers/yellow/eight'
            will render the Component2 class, assigning its 'color' property to
            'yellow' and its 'leafcount' property to 'eight', while
            '/flowers/yellow/' will also render the Component2 class, assigning
            its 'color' property to 'yellow' and leave its 'leafcount' property
            value unassigned.

            The seventh matches with the relative url 'trees' only if the path
            value element following the 'trees' value satisfies the regular
            expression 'ten|twenty', in which case the target component's height
            property value will be assigned whichever value was specified in the
            url: either 'ten' or 'twenty'.

history:    Mon Aug 28, 2018 10:30:00 (Giavaneers - LBM) created
            Wed Dec 12, 2018 10:30:00 (Giavaneers - LBM) added support for path
               parameters.

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import com.giavaneers.util.gwt.Logger;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
                                       // Router =============================//
public class Router implements IRouter
{
                                       // constants ------------------------- //
private static final Logger kLOGGER = Logger.newInstance();
                                       // default path                        //
public static final String  kPATH_DEFAULT   = "";
                                       // class variables ------------------- //
                                       // configuration                       //
protected static IConfiguration     configuration;
                                       // url parameters map                  //
protected static Map<String,String> urlParameters;
                                       // parameters map url                  //
protected static String             parametersURL;

                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
static
{
   com.google.gwt.user.client.History.addValueChangeHandler(
      new ValueChangeHandler<String>()
      {
         public void onValueChange(ValueChangeEvent event)
         {
            ReactJava.clearInjectedStylesheets();
            render(configuration);
         }
      });
}
/*------------------------------------------------------------------------------

@name       componentForHash - find a matching route for specified hash
                                                                              */
                                                                             /**
            Find a matching route for specified hash.

@return     void

@param      descriptor     descriptor

@history    Thu Dec 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Component componentForHash(
   Map<String,Object> descriptor)
{
   Component component = null;
   if (descriptor != null)
   {
      String     classname = ((Class)descriptor.get(kKEY_CLASS)).getCanonicalName();
      Properties props     = new Properties();

      Map<String,String> properties =
         (Map<String,String>)descriptor.get(kKEY_PROPS);

      if (properties != null)
      {
         for (String key : properties.keySet())
         {
            props.set(key, properties.get(key));
         }
      }
                                       // for debugging...                    //
      //Map<String,Function> factoryMap = ReactJava.getComponentFactoryMap();
      //kLOGGER.logInfo(
      //   "Router.componentForHash(): factoryMap size=" + factoryMap.size());
      //for (String clasname : factoryMap.keySet())
      //{
      //   kLOGGER.logInfo(
      //      "Router.componentForHash(): factoryMap entry for classname="
      //    + clasname
      //    + " -> "
      //    + ReactJava.getComponentFactory(clasname));
      //}

      Function<Properties,Component> factory =
         ReactJava.getComponentFactory(classname);

      if (factory != null)
      {
         component = factory.apply(props);

                                       // assign any nested routes            //
         configuration.setNavRoutesNested(component.getNavRoutes());

                                       // assign any cloud services config    //
         configuration.setCloudServicesConfig(component.getCloudServicesConfig());
      }
   }

   return(component);
}
/*------------------------------------------------------------------------------

@name       elementForPath - find a matching route for specified path
                                                                              */
                                                                             /**
            Find a matching route for specified path.

@return     void

@param      path     path

@history    Thu Dec 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static ReactElement elementForPath(
   String path)
{
   ReactElement element = null;

   if (configuration == null)
   {
      throw new IllegalStateException("Configuration must not be null");
   }

   Map<String,Class> routeMap = configuration.getNavRoutesNested();
   if (routeMap == null)
   {
      routeMap = configuration.getNavRoutes();
   }

   Map<String,Object> descriptor = IRouter.descriptorForHash(routeMap, path);
   if (descriptor != null)
   {
      Component component = componentForHash(descriptor);
      if (component != null)
      {
         element = ReactJava.createElement(component);
      }
   }

   return(element);
}
/*------------------------------------------------------------------------------

@name       getConfiguration - get configuration
                                                                              */
                                                                             /**
            Get configuration.

@return     configuration

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static IConfiguration getConfiguration()
{
   return(configuration);
}
/*------------------------------------------------------------------------------

@name       getHash - get current location hash
                                                                              */
                                                                             /**
            Get current location hash.

@return     current location hash

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static String getHash()
{
                                       // path is of the form                 //
                                       // urlPath#routePath]anchorElementId   //
                                       // for example:                        //
                                       // "HelloWorld.html#HelloPage]top"     //
   String hash = Window.Location.getHash();

                                       // entry via Router.push() may present //
                                       // encoding by the browser, for example//
                                       // '#RoutingReactJava.html#A]top'      //
                                       // ->'#RoutingReactJava.html%23A%5Btop'//
   if (hash.startsWith("#"))
   {
      hash = hash.substring(1);
   }
   hash = hash.replace("%23","#").replace("%5D","]").replace("%7D","}");

   String[] hashSplits = hash.split("#");
   if (hashSplits.length > 1)
   {
      hash = hashSplits[1];
   }
   return(hash);
}
/*------------------------------------------------------------------------------

@name       getHashAnchorElementId - get anchor element id from current hash
                                                                              */
                                                                             /**
            Get anchor element id from current hash.

@return     anchor element id from current hash or null if none.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static String getHashAnchorElementId()
{
   String   anchorElementId = null;
   String[] bracketSplits   = getHash().split("\\]");
   if (bracketSplits.length > 1)
   {
      anchorElementId = bracketSplits[1];
   }
   return(anchorElementId);
}
/*------------------------------------------------------------------------------

@name       getPath - get current location path
                                                                              */
                                                                             /**
            Get current location path.

@return     current location path

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static String getPath()
{
   String[] bracketSplits = getHash().split("\\]");
   String   path          = bracketSplits[0];
   return(path);
}
/*------------------------------------------------------------------------------

@name       getURLParameters - get current location url parameters
                                                                              */
                                                                             /**
            Get current location url parameters.

@return     current location url parameters

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static Map<String,String> getURLParameters()
{
   String url = DomGlobal.document.URL;
   if (!url.equals(parametersURL))
   {
      urlParameters   = new HashMap<>();
      String[] splits = url.split("\\?");
      if (splits.length > 1)
      {
         String query = splits[1].split("#")[0];
         for (String param : query.split("&"))
         {
                     splits = param.split("=");
            String   key    = splits[0].trim();
            String   value  = splits.length > 1 ? splits[1].trim() : "true";

            urlParameters.put(key, value);
         }
      }
      parametersURL = url;
   }

   return(urlParameters);
}
/*------------------------------------------------------------------------------

@name       getURLParameter - get specified url parameter
                                                                              */
                                                                             /**
            Get specified url parameter.

@return     specified url parameter or null if not found

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static String getURLParameter(
   String key)
{
   return(getURLParameters().get(key));
}
/*------------------------------------------------------------------------------

@name       goBack - go back one location
                                                                              */
                                                                             /**
            Go back one location.

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void goBack()
{
   com.google.gwt.user.client.History.back();
}
/*------------------------------------------------------------------------------

@name       goForward - go forward one location
                                                                              */
                                                                             /**
            Go forward one location.

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void goForward()
{
   com.google.gwt.user.client.History.forward();
}
/*------------------------------------------------------------------------------

@name       push - push specified path
                                                                              */
                                                                             /**
            Push specified path.

@param      path     path

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void push(String path)
{
   com.google.gwt.user.client.History.newItem(path);
}
/*------------------------------------------------------------------------------

@name       render - parse markup
                                                                              */
                                                                             /**
            Parse markup.

@return     void

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static ReactElement render(
   IConfiguration configurationNew)
{
   if (configurationNew != null)
   {
      configuration = configurationNew;
   }
   return(render());
}
/*------------------------------------------------------------------------------

@name       render - parse markup
                                                                              */
                                                                             /**
            Parse markup.

@return     void

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static ReactElement render()
{
   String  path        = getPath();
   boolean bNewElement = !".".equals(path);

   ReactElement element = bNewElement ? elementForPath(path) : null;
   if (bNewElement && element == null)
   {
      kLOGGER.logWarning(
         "Router.render(): no element to render for hash value "
       + "\"" + getHash() + "\""
       + ", using default path");

      element = elementForPath("");
   }
   if (bNewElement && element == null)
   {
      kLOGGER.logError("Router.render(): no element to render for default path");
   }
   else if (ReactJava.getIsWebPlatform())
   {
      try
      {
         if (element != null)
         {
            ReactDOM.render(element, DomGlobal.document.getElementById("root"));
         }

         String anchorElementId = getHashAnchorElementId();
         if (anchorElementId != null)
         {
                                    // scroll to specified anchor element  //
            scrollIntoView(anchorElementId, 0);
         }
         else
         {
                                       // some browsers will leave the scroll //
                                       // bar unchanged when switching to a   //
                                       // new path, so if an anchor element   //
                                       // is not specified along with the     //
                                       // path, scroll to the top             //
            DomGlobal.window.scrollTo(0, 0);
         }
      }
      catch(Throwable t)
      {
         kLOGGER.logError("Router.render(): " + t);
         if (!path.equals(kPATH_DEFAULT))
         {
                                       // error recovery: push default path   //
            new Timer()
            {
               public void run()
               {
                  Router.push(kPATH_DEFAULT);
               }
            }.schedule(0);
         }
      }
   }

   return(element);
}
/*------------------------------------------------------------------------------

@name       replace - replace with specified path
                                                                              */
                                                                             /**
            Replace with specified path.

@param      path     path

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void replace(String path)
{
   com.google.gwt.user.client.History.replaceItem(path);
}
/*------------------------------------------------------------------------------

@name       scrollIntoView - scroll to specified elementId
                                                                              */
                                                                             /**
            Implements the functionality of going to a specified anchor on the
            page by scrolling to the specified elementId. This implementation
            waits up to 1 second for the specified element to be found in the
            DOM. If it finds it, it scrolls to make the specified element
            visible.

@param      elementId      element id
@param      vOffset        vertical offset, for example: '10px'

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void scrollIntoView(
   String elementId,
   int    vOffset)
{
   final double[] timerDsc = new double[2];

   timerDsc[0] = elemental2.dom.DomGlobal.setInterval(e ->
   {
      timerDsc[1]++;

      Element element = DomGlobal.document.getElementById(elementId);
      if (element != null)
      {
         element.scrollIntoView();
         if (vOffset != 0)
         {
            DomGlobal.window.scrollBy(0, vOffset);
         }
      }
      if (element != null || timerDsc[1] > 10)
      {
         elemental2.dom.DomGlobal.clearInterval(timerDsc[0]);
      }
   }, 100);
}
}//====================================// end Router -------------------------//
