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
import io.reactjava.client.core.react.INativeEffectHandler;
import io.reactjava.client.core.react.IUITheme;
import io.reactjava.client.core.react.React;
import io.reactjava.client.core.react.ReactElement;
import io.reactjava.client.core.react.ReactJava;
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
protected ReactElement renderElement;  // element to be rendered              //
protected Function<Properties, ReactElement>
                       componentFcn;   // component function                  //
protected String       css;            // css                                 //
protected P            props;          // component properties                //
protected IUITheme     theme;          // theme                               //

/*------------------------------------------------------------------------------

@name       Component - default constructor
                                                                              */
                                                                             /**
            Default constructor

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

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Component(P props)
{
}
/*------------------------------------------------------------------------------

@name       forceUpdate - force component update
                                                                              */
                                                                             /**
            Force component update.

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void forceUpdate()
{
}
/*------------------------------------------------------------------------------

@name       forElement - get state element value
                                                                              */
                                                                             /**
            Get state element value.

@return     state element value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static io.reactjava.client.core.react.Component forElement(
   Element element)
{
   return(null);
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
public elemental2.dom.Element getDOMElement()
{
   String                 elementId = props().getString("id");
   elemental2.dom.Element element   = DomGlobal.document.getElementById(elementId);
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
public elemental2.dom.Element getDOMParentElement()
{
   elemental2.dom.Element parent  = null;
   elemental2.dom.Element element = getDOMElement();
   if (element != null)
   {
      Node parentNode = getDOMElement().parentNode;
      if (parentNode instanceof elemental2.dom.Element)
      {
         parent = (elemental2.dom.Element)parentNode;
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
   IUITheme theme = (IUITheme)props().get("theme");
   if (theme == null)
   {
      theme = IUITheme.defaultInstance();
      props().set("theme", theme);
   }
   return(theme);
}
/*------------------------------------------------------------------------------

@name       props - get props
                                                                              */
                                                                             /**
            Get properties.

@return     properties

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public P props()
{
   return(props);
}
/*------------------------------------------------------------------------------

@name       render - render markup
                                                                              */
                                                                             /**
            Render markup.

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

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
};
/*------------------------------------------------------------------------------

@name       setId - set id
                                                                              */
                                                                             /**
            Set id.

@param      id    new id value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void setId(
   String id)
{
   props().set("id", id);
}
/*------------------------------------------------------------------------------

@name       initialize - set properties
                                                                              */
                                                                             /**
            Set properties.

@param      props     properties

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

@name       useEffect - component side effect handler
                                                                              */
                                                                             /**
            Effect hook handler similar to componentDidMount,
            componentDidUpdate, and componentWillUnmount combined.

@param      effectHandler     function to be invoked on effect

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public void useEffect(
   INativeEffectHandler effectHandler)
{
   React.useEffect(effectHandler);
}
}//====================================// end Component ----------------------//
