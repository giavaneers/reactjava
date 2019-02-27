/*==============================================================================

name:       Component.java

purpose:    ReactJava Component.

history:    Mon June 4, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                  This program was created by Giavaneers
         and is the confidential and proprietary product of Giavaneers Inc.
       Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2018 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.


==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import com.giavaneers.util.gwt.Logger;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.Node;
import java.util.Map;
import java.util.function.Function;
                                       // Component ==========================//
public abstract class Component<P extends Properties>
{
                                       // class constants --------------------//
private static final Logger kLOGGER  = Logger.newInstance();

                                       // class variables                     //
protected static int nextId;
                                       // protected instance variables ------ //
protected io.reactjava.client.core.react.Element
                     renderElement;    // element to be rendered              //
protected Function<Properties,io.reactjava.client.core.react.Element>
                     componentFcn;     // component function                  //
protected String     css;              // css                                 //
protected P          props;            // component properties                //
protected IUITheme   theme;            // theme                               //

/*------------------------------------------------------------------------------

@name       Component - default constructor
                                                                              */
                                                                             /**
            Default constructor

@return     An instance of Component if successful.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Component()
{
   this(null);
}
/*------------------------------------------------------------------------------

@name       Component - constructor for specified properties
                                                                              */
                                                                             /**
            Constructor for specified properties

@return     An instance of Component if successful.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Component(P props)
{
   this.props = props != null ? props : (P)new Properties();
   this.props.setComponent(this);

   initConfiguration();

   if (ReactJava.getIsWebPlatform())
   {
      ReactJava.ensureComponentStyles(this, false);
   }
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
   String  elementId = getProperties().getString("id");
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
   Element parent = null;
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

@name       getNavRoutes - get routes for component
                                                                              */
                                                                             /**
            Get component map of component classname by route path.

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
   return("@" + ++nextId);
}
/*------------------------------------------------------------------------------

@name       getProperties - get props
                                                                              */
                                                                             /**
            Get properties.

@return     properties

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public P getProperties()
{
   return(props);
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
   if (theme == null)
   {
      theme = props.getConfiguration().getTheme();
   }
   return(theme);
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
   if (getProperties().getConfiguration() == null)
   {
      getProperties().setConfiguration(Configuration.sharedInstance());
   }
}
/*------------------------------------------------------------------------------

@name       render - render markup
                                                                              */
                                                                             /**
            Render markup.

@return     void

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created
            Wed Oct 17, 2018 10:30:00 (Giavaneers - LBM) renamed per suggestion
               by Ethan Elshyeb.

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

@return     void

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
};
/*------------------------------------------------------------------------------

@name       setProperties - set properties
                                                                              */
                                                                             /**
            Set properties.

@return     void

@return     props     properties

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void setProperties(
   P props)
{
   this.props = props;
}
/*------------------------------------------------------------------------------

@name       setTheme - set theme
                                                                              */
                                                                             /**
            Set theme.

@return     theme

@param      new theme

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public IUITheme setTheme(
   IUITheme theme)
{
   this.theme = theme;
   return(theme);
}
/*------------------------------------------------------------------------------

@name       update - update component render
                                                                              */
                                                                             /**
            Update component render.

@return     void

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void update()
{
   try
   {
      io.reactjava.client.core.react.Element element =
         ReactJava.createElement(this);

      if (ReactJava.getIsWebPlatform())
      {
                                       // update any styles                   //
         ReactJava.ensureComponentStyles(this, true);
         ReactDOM.render(element, getDOMParentElement());
      }
   }
   catch(Exception e)
   {
      kLOGGER.logError(e);
   }
}
}//====================================// end Component ----------------------//
