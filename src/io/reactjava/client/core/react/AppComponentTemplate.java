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
import java.util.List;
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

@name       getImportedNodeModules - get imported node modules
                                                                              */
                                                                             /**
            Get imported node modules.

@return     ordered list of node module names of the folowing forms:

               nodeModuleName[:javascript:css]

               where,

                  nodeModuleName is the name of the node module in the
                  project node_modules directory

                  :javascript is an optional qualifier indicating the 'main'
                  module javascript is to be imported

                  :css  is an optional qualifier indicating the 'style'
                  module css is to be imported

                  and if either ':css' or ':javascript' is included but the
                  other is not, the other is not imported

                  and if neither ':css' nor ':javascript' is included, the
                  'main' module javascript is to be imported (the default case)

               or nodeModuleName.js for a specific module script

               or nodeModuleName.css for a specific module stylesheet

            For examples,

               'prismjs'
                  specifies the default script for module 'prismjs' without
                  any module css

               'prismjs:javascript:css'
                  specifies the default script for module 'prismjs' along with
                  the default module css

               'prismjs.components.prism-core',
               'prismjs.components.prism-clike',
               'prismjs.components.prism-java',
               'prismjs.themes.prism-okaidia.css'
                  specifies
                     components.prism-core.js  of module 'prismjs', followed by
                     components.prism-clike.js of module 'prismjs', followed by
                     components.prism-java.js  of module 'prismjs'
                  and
                     themes.prism-okaidia.css  of module 'prismjs'

@history    Sun Nov 02, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected List<String> getImportedNodeModules()
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
