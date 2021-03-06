/*==============================================================================

name:       ElementDsc - element descriptor descriptor

purpose:    ReactJava element descriptor

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import com.giavaneers.util.gwt.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
                                       // ElementDsc ========================//
public class ElementDsc<P extends Properties>
{
                                       // constants ------------------------- //
private static final Logger kLOGGER  = Logger.newInstance();

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public Object           type;          // type                                //
public IConfiguration   configuration; // configuration                       //
public P                props;         // properties                          //
public String           value;         // value                               //
public ElementDsc       parent;        // parent                              //
public List<ElementDsc> children;      // children                            //
public ReactElement     childrenElem;  // children element                    //
                                       // protected instance variables -------//
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       create - create element descriptor of the component class
                                                                              */
                                                                             /**
            Convenience method to create element descriptor of the specified
            component class.

@return     element descriptor of the specified component class

@param      type        component class

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static <P extends Properties> ElementDsc create(
   ElementDsc     parent,
   Object         type,
   IConfiguration configuration)
{
   return(create(parent, type, configuration, (ElementDsc[])null));
}

public static <P extends Properties> ElementDsc create(
   ElementDsc     parent,
   Object         type,
   IConfiguration configuration,
   ElementDsc...  children)
{
   ElementDsc dsc = create(parent, type, (P)null, children);
   dsc.configuration = configuration;
   return(dsc);
}

public static <P extends Properties> ElementDsc create(
   ElementDsc parent,
   Object     type,
   P          props)
{
   if (type instanceof String)
   {
                                       // creating a component                //
      Component component = Component.newInstance((String)type, props);
      if (component != null)
      {
          type = ReactJava.componentRender(component);

                                       // properties to use should be the     //
                                       // properties of an instance of the    //
                                       // specified classname after being     //
                                       // initialized                         //
          props = (P)component.componentProperties;
      }
   }
   return(create(parent, type, props, (ElementDsc[])null));
}
public static <P extends Properties> ElementDsc create(
   ElementDsc parent,
   Object     type,
   P          props,
   String     value)
{
   ElementDsc dsc = create(parent, type, props, (ElementDsc[])null);
   dsc.value = value;
   return(dsc);
}

public static <P extends Properties> ElementDsc create(
   ElementDsc    parent,
   Object        type,
   P             props,
   ReactElement childrenElem)
{
   ElementDsc dsc = create(parent, type, props, (ElementDsc[])null);
   dsc.childrenElem = childrenElem;
   return(dsc);
}

public static <P extends Properties> ElementDsc create(
   ElementDsc    parent,
   Object        type,
   P             props,
   ElementDsc... children)
{
   ElementDsc dsc = new ElementDsc();
   dsc.type       = type;
   dsc.props      = props;
   dsc.parent     = parent;
   dsc.children   = new ArrayList<ElementDsc>();
   if (children != null)
   {
      dsc.children.addAll(Arrays.asList(children));
   }
   if (parent != null)
   {
      parent.children.add(dsc);
   }

   return(dsc);
}
/*------------------------------------------------------------------------------

@name       createElement - create element from the specified root descriptor
                                                                              */
                                                                             /**
            Create element from the specified root descriptor.

@return     element from the specified root descriptor

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static ReactElement createElement(
   ElementDsc root)
{
   return(createElement(root, null));
}
/*------------------------------------------------------------------------------

@name       createElement - create element from the specified root descriptor
                                                                              */
                                                                             /**
            Create element from the specified root descriptor.

@return     element from the specified root descriptor

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static ReactElement createElement(
   ElementDsc root,
   Component  component)
{
                                       // invoke any render editors           //
   root = component != null ? component.invokeRenderEditors(root) : root;

   List<ReactElement> childList = new ArrayList<>();
   for (ElementDsc childDsc : (List<ElementDsc>)root.children)
   {
      childList.add(createElement(childDsc));
   }
   ReactElement[] children = childList.toArray(new ReactElement[childList.size()]);
   ReactElement   element  = null;
   try
   {
      if (root.type instanceof String)
      {
         String type = (String)root.type;
         if (root.value != null)
         {
            element = ReactJava.createElement(type, root.props, root.value);
         }
         else
         {
            if (root.childrenElem != null)
            {
               children = new ReactElement[]{root.childrenElem};
            }
            element = ReactJava.createElement(type, root.props, children);
         }
      }
      else if (root.type instanceof Class)
      {
         Class type = (Class)root.type;
         kLOGGER.logInfo("ElementDsc.createElement(): " + type);
         element = ReactJava.createElement(type, root.configuration, children);
      }
      else if (root.type instanceof INativeFunctionalComponent)
      {
         INativeFunctionalComponent type = (INativeFunctionalComponent)root.type;
         if (root.value != null)
         {
            element = ReactJava.createElement(type, root.props, root.value);
         }
         else
         {
            element = ReactJava.createElement(type, root.props, children);
         }
      }
      else if (root.type instanceof INativeComponentConstructor)
      {
         INativeComponentConstructor type = (INativeComponentConstructor)root.type;
         element = ReactJava.createElement(type, root.props, children);
      }
                                       // ensure an element id                //
      if (element != null && element.props.getString("id") == null)
      {
         throw new IllegalStateException("ReactElement must have an id");
      }
   }
   catch(Exception e)
   {
      kLOGGER.logError(e);
   }

   return(element);
}
/*------------------------------------------------------------------------------

@name       getElementDscById - get element descriptor with specified id
                                                                              */
                                                                             /**
            Get element descriptor with specified id from branch rooted at this.

@return     Element descriptor with specified id from branch rooted at this.

@param      id    target id

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ElementDsc getElementDscById(
   String id)
{
   ElementDsc elementDsc = null;
   if (id != null)
   {
      if (id.equals(props.getString("id")))
      {
         elementDsc = this;
      }
      else if (children != null)
      {
         for (ElementDsc child : children)
         {
            elementDsc = child.getElementDscById(id);
            if (elementDsc != null)
            {
               break;
            }
         }
      }
   }

   return(elementDsc);
}
}//====================================// end ElementDsc ---------------------//
