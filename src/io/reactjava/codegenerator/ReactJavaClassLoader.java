/*=============================================================================

name:       ReactJavaClassLoader.java

purpose:    React Java class loader.

history:    Mon Jan 08, 2019 10:30:00 (Giavaneers - LBM) created

notes:
                     This program was created by Giavaneers
            and is the confidential and proprietary product of Giavaneers.
         Any unauthorized use, reproduction or transfer is strictly prohibited.

                        COPYRIGHT 2019 BY GIAVANEERS, INC.
         (Subject to limited distribution and restricted disclosure only).
                              All rights reserved.

============================================================================= */
                                       // package --------------------------- //
package io.reactjava.codegenerator;
                                       // imports --------------------------- //
import java.net.URL;
import java.net.URLClassLoader;
                                       // ReactJavaClassLoader ===============//
public class ReactJavaClassLoader extends URLClassLoader
{
                                       // class constants ------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // context to be used when loading     //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       ReactJavaClassLoader - default constructor 
                                                                              */
                                                                             /**
            Create a new ReactJavaClassLoader instance. 

@return     An instance of ReactJavaClassLoader if successful. 

@history    Mon Jan 08, 2019 10:30:00 (Giavaneers - LBM) created.

@notes      
                                                                              */
//------------------------------------------------------------------------------
public ReactJavaClassLoader(
   URL[]       urls,
   ClassLoader parent)
{
   super(urls, parent);
}
/*------------------------------------------------------------------------------

@name       loadClass - load class with name
                                                                              */
                                                                             /**
            Find and load the class with the specified name.

@return     resulting class.

@param      name        name of class to load

@history    Mon Jan 08, 2019 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected Class<?> loadClass(
   String  name,
   boolean resolve)
   throws  ClassNotFoundException
{
   synchronized (getClassLoadingLock(name))
   {
                                       // first check if the class has        //
                                       // already been loaded                 //
      Class<?> c = findLoadedClass(name);
      if (c == null)
      {
         try
         {
            c = super.findClass(name);
            if (resolve)
            {
               resolveClass(c);
            }
         }
         catch (ClassNotFoundException e)
         {
                                       // have the parent try                 //
            c = super.loadClass(name, resolve);
         }
      }

      return c;
   }
}
}//====================================// end class ReactJavaClassLoader -----//
