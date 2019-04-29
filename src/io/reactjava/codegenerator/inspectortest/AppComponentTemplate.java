/*==============================================================================

name:       AppComponentTemplate.java

purpose:    AppComponentTemplate proxy for compilation by AppComponentInspector

history:    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.codegenerator.inspectortest;
                                       // imports --------------------------- //
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
                                       // AppComponentTemplate ============== //
public class AppComponentTemplate<P extends Properties> extends Component<P>
{
                                       // constants ------------------------- //
public static Collection<String> importedNodeModules = new ArrayList();

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

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public AppComponentTemplate()
{
   importedNodeModules = getImportedNodeModules();
                                       // stop any subsequent operation       //
   throw new UnsupportedOperationException();
}
/*------------------------------------------------------------------------------

@name       AppComponentTemplate - constructor for specified properties
                                                                              */
                                                                             /**
            AppComponentTemplate for specified properties

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public AppComponentTemplate(P props)
{
   this();
}
/*------------------------------------------------------------------------------

@name       getImportedNodeModules - get imported node modules
                                                                              */
                                                                             /**
            Get imported node modules.

@return     collection of node module names

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected Collection<String> getImportedNodeModules()
{
   return(importedNodeModules);
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

@name       main - standard main routine
                                                                              */
                                                                             /**
            Standard main routine.

@param      args     args[0] - platform specification

@history    Sat Dec 08, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void main(
   String[] args)
{
   String appClassname = args[0];
   try
   {
      Class appClass = Class.forName(appClassname);
      try
      {
                                       // check for the nullary constructor,  //
                                       // either provided explicitly or       //
                                       // by the compiler if no other exists  //
         Constructor ctor = appClass.getConstructor();
         try
         {
                                       // invoke the nullary constructor      //
            ctor.newInstance();
         }
         catch(Exception uoe)
         {
                                       // constructor should generate an error//
                                       // return result to invoker            //
            System.out.println(importedNodeModules.toString());
         }
      }
      catch(Exception e)
      {
         try
         {
                                       // check for props constructor         //
            Constructor ctor = appClass.getConstructor(Properties.class);
            try
            {
                                       // invoke the props constructor        //
               ctor.newInstance((Properties)null);
            }
            catch(Exception uoe)
            {
                                       // constructor should generate an error//
                                       // return result to invoker            //
               System.out.println(importedNodeModules.toString());
            }
         }
         catch(Exception ee)
         {
            System.err.println(
               "If you supply any custom constructor for " + appClassname
             + ", you must also supply at least a nullary constructor as well.");
         }
      }
   }
   catch(Throwable t)
   {
      System.err.println(t);
   }
}
}//====================================// end AppComponentTemplate -----------//
