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
                  put("animals",                    Component1.class);
                  put("flowers/:color/:leafcount?", Component2.class);
                  put("trees/:height(ten|twenty)",  Component3.class);
               }};

            declares three different routes.

            The first matches explictly with the relative url value 'animals',

            The second matches with the relative url 'flowers', and also assigns
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

            The third matches with the relative url 'trees' only if the path
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
import com.google.gwt.user.client.Window;
import elemental2.dom.DomGlobal;
import java.util.Map;
import java.util.function.Function;

// Router =============================//
public class Router implements IRouter
{
                                       // constants ------------------------- //
private static final Logger kLOGGER = Logger.newInstance();
                                       // class variables ------------------- //
                                       // configuration                       //
protected static IConfiguration configuration;

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

@param      hash     hash

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
      String     classname = ((Class)descriptor.get(kKEY_CLASS)).getName();
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

      Function<Properties,Component> factory =
         ReactJava.getComponentFactory(classname);

      if (factory != null)
      {
         component = factory.apply(props);

                                       // assign any nested routes            //
         configuration.setNavRoutesNested(component.getNavRoutes());
      }
   }

   return(component);
}
/*------------------------------------------------------------------------------

@name       elementForHash - find a matching route for specified hash
                                                                              */
                                                                             /**
            Find a matching route for specified hash.

@return     void

@param      hash     hash

@history    Thu Dec 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Element elementForHash(
   String hash)
{
   Element element = null;

   if (configuration == null)
   {
      throw new IllegalStateException("Configuration must not be null");
   }

   Map<String,Class> routeMap = configuration.getNavRoutesNested();
   if (routeMap == null)
   {
      routeMap = configuration.getNavRoutes();
   }

   Map<String,Object> descriptor = IRouter.descriptorForHash(routeMap, hash);
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

@name       goBack - go back one location
                                                                              */
                                                                             /**
            Go back one location.

@return     void

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

@return     void

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

@return     void

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
public static Element render(
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
public static Element render()
{
   String hash = Window.Location.getHash();
   if (hash.startsWith("#"))
   {
      hash = hash.substring(1);
   }

   Element element = elementForHash(hash);
   if (element != null)
   {
      if (ReactJava.getIsWebPlatform())
      {
         ReactDOM.render(element, DomGlobal.document.getElementById("root"));
      }
   }
   else
   {
      kLOGGER.logError(
         "Router.render(): no element to render."
       + " Did you forget to specify a route for hash value \"" + hash + "\"?");
   }
   return(element);
}
/*------------------------------------------------------------------------------

@name       replace - replace with specified path
                                                                              */
                                                                             /**
            Replace with specified path.

@return     void

@param      path     path

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void replace(String path)
{
   com.google.gwt.user.client.History.replaceItem(path);
}
}//====================================// end Router -------------------------//
