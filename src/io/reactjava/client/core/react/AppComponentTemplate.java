/*==============================================================================

name:       AppComponentTemplate - app component template

purpose:    App component template.

            The application configuration executed by default is the 'default'
            configuration. An alternative choice can be specified at runtime by
            specifying the 'configurationName' url parameter. For example,
            to choose a configuration defined at compile time named 'test',
            the app can be launched with a url of the form:

               http://[applicationPath]?configurationName=test

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import com.google.gwt.core.client.EntryPoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
                                       // AppComponentTemplate ============== //
public class AppComponentTemplate<P extends Properties>
   extends Component<P> implements EntryPoint
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       AppComponentTemplate - constructor for specified properties
                                                                              */
                                                                             /**
            AppComponentTemplate for specified properties

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public AppComponentTemplate()
{
   super();
}
/*------------------------------------------------------------------------------

@name       AppComponentTemplate - constructor for specified properties
                                                                              */
                                                                             /**
            AppComponentTemplate for specified properties

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public AppComponentTemplate(P props)
{
   super(props);
}
/*------------------------------------------------------------------------------

@name       getCustomJavascripts - get custom javascripts
                                                                              */
                                                                             /**
            Get custom javascripts. This method is typically invoked at
            boot time.

@return     ordered list of javascript urls

@history    Fri May 22, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected List<String> getCustomJavascripts()
{
   return(new ArrayList<>());
}
/*------------------------------------------------------------------------------

@name       getConfigurations - get array of configurations
                                                                              */
                                                                             /**
            Get array of configurations. If null, the default configuration
            is used.

@return     array of configurations

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected IConfiguration[] getConfigurations()
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       getConfigurationName - get configuration name
                                                                              */
                                                                             /**
            Get configuration name. If null, the default configuration is used.

@return     configuration name

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected String getConfigurationName()
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       getSEOInfo - get seo information
                                                                              */
                                                                             /**
            Get SEO info. This method is typically invoked at compile time.

            The intention is to provide a title, description, and base url for
            the app deployment in order to create a redirect target for each
            hash, along with an associated sitemap.

@return     SEOInfo string

@history    Sun Jun 16, 2019 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected SEOInfo getSEOInfo()
{
   return(null);
}
/*------------------------------------------------------------------------------

@name       initConfiguration - initialize configuration
                                                                              */
                                                                             /**
            Initialize configuration. This implementation is null.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void initConfiguration()
{
   if (Configuration.sharedInstance() == null)
   {
      IConfiguration.assignSharedInstance(this);
   }

   Collection<String> bundleScripts =
      Configuration.sharedInstance.getBundleScripts();

   for (String custom : getCustomJavascripts())
   {
      bundleScripts.add(Utilities.toAbsoluteURL(custom));
   }
   super.initConfiguration();
}
/*------------------------------------------------------------------------------

@name       initializeProperties - initialize properties
                                                                              */
                                                                             /**
            Initialize properties. This override of the default assigns an id
            which isn't otherwise done for an app as well as assigning any url
            parameters to the app as properties.

            This method will be invoked two times on startup: the first time
            before ReactJava has been booted when GWT constructs an instance of
            the target App class in preparation for invoking onModuleLoad(),

               com_google_gwt_lang_Cast_castTo__
               Ljava_lang_Object_2L
               com_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(
                  new io_reactjava_client_examples_hellowworld_App_App__V, 41)
                  .onModuleLoad__V();

            and the second time in the thread of invocation of onModuleLoad()
            in which invocation of ReactJava.boot() ultimately results in
            ReactRouter.render(), invoking ReactRouter.componentForHash(),
            invoking the component factory default constructor found from
            ReactJava.getComponentFactory(classname).

@return     component properties

@param      initialProps      initial properties

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
@Override
P initializeProperties(
   P initialProps)
{
   if (ReactJava.bBooted)
   {
                                       // assign the app id                   //
      newInstanceProperties.set("id", getClass().getName());

                                       // assign any url parameters as props  //
      for (String propertyName : Router.getURLParameters().keySet())
      {
         newInstanceProperties.set(
            propertyName, Router.getURLParameter(propertyName));
      }
   }

   return(super.initializeProperties(initialProps));
}
/*------------------------------------------------------------------------------

@name       onModuleLoad - standard core entry point method
                                                                              */
                                                                             /**
            Standard core entry point method.

@history    Sun Jan 7, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void onModuleLoad()
{
   ReactJava.boot(this);
                                       // do it the way it could be done from //
                                       // javascript, although there seems to //
                                       // be some problems sometimes in that  //
                                       // on incremental compile and restart  //
                                       // the code generator class cannot be  //
                                       // found as a javascript global...     //
   //ReactJava.bootNative(getClass().getName());
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
//@Override
//protected void preRender(
//   Properties props)
//{
//                                     // initialize Component statics        //
//   initStatics();
//   super.preRender(props);
//}
}//====================================// end AppComponentTemplate ---------- //
