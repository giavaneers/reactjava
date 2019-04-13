/*==============================================================================

name:       Component.java

purpose:    Component proxy for compilation by AppComponentInspector

history:    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.codegenerator.inspectortest;
                                       // imports --------------------------- //
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.Node;
import io.reactjava.client.core.react.IUITheme;
import java.util.Map;
import java.util.function.Function;
                                       // Component ==========================//
public abstract class Component<P extends Properties>
{
                                       // class constants --------------------//
                                       // (none)                              //
                                       // class variables                     //
protected static int nextId;
                                       // protected instance variables ------ //
protected io.reactjava.client.core.react.Element
                     renderElement;    // element to be rendered              //
protected Function<Properties,io.reactjava.client.core.react.Element>
                     componentFcn;     // component function                  //
protected String     css;              // css                                 //
protected P          props;            // component properties                //
protected IUITheme theme;            // theme                               //

/*------------------------------------------------------------------------------

@name       Component - default constructor
                                                                              */
                                                                             /**
            Default constructor

@return     An instance of Component if successful.

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Component()
{
}
/*------------------------------------------------------------------------------

@name       Component - constructor for specified properties
                                                                              */
                                                                             /**
            Constructor for specified properties

@return     An instance of Component if successful.

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Component(P props)
{
}
/*------------------------------------------------------------------------------

@name       getDOMElement - get dom element
                                                                              */
                                                                             /**
            Get dom element.

@return     dom element

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

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

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

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

            Note, in general these routes are nested relative to those of the
            Application Template or its subclasses.

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

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

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

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

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
            Get theme. This implementation simply returns default theme.

@return     theme

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public IUITheme getTheme()
{
   IUITheme theme = (IUITheme)getProperties().get("theme");
   if (theme == null)
   {
      theme = IUITheme.defaultInstance();
      getProperties().set("theme", theme);
   }
   return(theme);
}
/*------------------------------------------------------------------------------

@name       render - render markup
                                                                              */
                                                                             /**
            Render markup.

@return     void

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

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

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
};
/*------------------------------------------------------------------------------

@name       initialize - set properties
                                                                              */
                                                                             /**
            Set properties.

@return     void

@return     props     properties

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void setProperties(
   P props)
{
   this.props = props;
}
/*------------------------------------------------------------------------------

@name       update - update component render
                                                                              */
                                                                             /**
            Update component render.

@return     void

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void update()
{
}
}//====================================// end Component ----------------------//
