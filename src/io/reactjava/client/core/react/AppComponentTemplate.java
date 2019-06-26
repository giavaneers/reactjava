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
import io.reactjava.client.core.react.SEOInfo.SEOPageInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.reactjava.client.core.react.ReactJava.kHEAD_ELEM_TYPE_META;
import static io.reactjava.client.core.react.ReactJava.kHEAD_ELEM_TYPE_STRUCTURED;
import static io.reactjava.client.core.react.ReactJava.kHEAD_ELEM_TYPE_TITLE;

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
            Get imported node modules. This method is typically invoked at
            compile time.

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

@name       initialize - initialize
                                                                              */
                                                                             /**
            Initialize. This override of the default assigns an id which isn't
            otherwise done for an app.

@return     component properties

@param      initialProps      initial properties

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public P initialize(
   P initialProps)
{
   P props = super.initialize(initialProps);
   if (props.getString("id") == null)
   {
      setId(getNextId());
   }
   return(props);
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

@name       render - render markup
                                                                              */
                                                                             /**
            Render markup. This implementation adds SEO support in the form of
            specific title, description and structured data head entries as
            specified by any app SEOInfo instance.

@history    Thu Jun 20, 2019 10:30:00 (Giavaneers - LBM) created

@notes      see https://developers.google.com/search/docs/guides/intro-structured-data

                                                                              */
//------------------------------------------------------------------------------
public void render()
{
   SEOInfo seoInfo = getSEOInfo();
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
                     "type", kHEAD_ELEM_TYPE_TITLE, "text", pageInfo.title));
            }
            if (pageInfo.description != null
                  && pageInfo.description.length() > 0)
            {
                                       // assign the page description         //
               ReactJava.setHead(
                  NativeObject.with(
                     "type",   kHEAD_ELEM_TYPE_META,
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
                     "type",               kHEAD_ELEM_TYPE_STRUCTURED,
                     "structuredDataType", pageInfo.structuredDataType,
                     "structuredData",     pageInfo.structuredData));
            }

            break;
         }
      }
   }
}
}//====================================// end AppComponentTemplate ---------- //
