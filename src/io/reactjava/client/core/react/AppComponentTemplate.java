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

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import com.google.gwt.core.client.EntryPoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
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

@return     An instance of AppComponentTemplate if successful.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public AppComponentTemplate()
{
   this(null);
}
/*------------------------------------------------------------------------------

@name       AppComponentTemplate - constructor for specified properties
                                                                              */
                                                                             /**
            AppComponentTemplate for specified properties

@return     An instance of AppComponentTemplate if successful.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public AppComponentTemplate(P props)
{
   super(props);
}
/*------------------------------------------------------------------------------

@name       getImportedNodeModules - get imported node modules
                                                                              */
                                                                             /**
            Get imported node modules.

@return     collection of node module names

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected Collection<String> getImportedNodeModules()
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

@name       getNavRoutes - get routes for application
                                                                              */
                                                                             /**
            Get map of component classname by route path.

@return     void

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected Map<String,Class> getNavRoutes()
{
   return(super.getNavRoutes());
}
/*------------------------------------------------------------------------------

@name       initConfiguration - initialize configuration
                                                                              */
                                                                             /**
            Initialize configuration. This implementation is null.

@return     void

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
   super.initConfiguration();
}
/*------------------------------------------------------------------------------

@name       onModuleLoad - standard core entry point method
                                                                              */
                                                                             /**
            Standard core entry point method.

@return     void

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
}//====================================// end AppComponentTemplate ---------- //
