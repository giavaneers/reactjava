/*==============================================================================

name:       IRouter.java

purpose:    ReactJava router interface.

history:    Thu Dec 13, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import java.util.HashMap;
import java.util.Map;
                                       // IRouter ============================//
public interface IRouter
{
                                       // class constants --------------------//
static final String kKEY_CLASS = "class";
static final String kKEY_PROPS = "props";

                                       // class variables ------------------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       descriptorForHash - find a matching component for specified hash
                                                                              */
                                                                             /**
            Find a matching component for specified hash.

@return     matching component for specified hash

@param      hash     hash

@history    Thu Dec 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static Map<String,Object> descriptorForHash(
   Map<String,Class>  routeMap,
   String             hash)
{
   Map<String,String> properties = new HashMap<>();
   String[]           hashTokens = hash.split("/");
   Map<String,Object> descriptor = null;

   for (String path : routeMap.keySet())
   {
      String[] pathTokens = path.split("/");
      int      iMax       = Math.min(hashTokens.length, pathTokens.length);
      boolean  bMatch     = true;
      for (int i = 0; i < iMax; i++)
      {
         String hashToken = hashTokens[i];
         String pathToken = pathTokens[i];

         if (!hashToken.equals(pathToken))
         {
            if (pathToken.startsWith(":"))
            {
               String key = pathToken.substring(1).trim();
               if (key.endsWith("?"))
               {
                  key = key.substring(0, key.length() - 1);
               }
               else if (key.endsWith(")"))
               {
                  String regex =
                     key.substring(key.indexOf('(') + 1, key.length() - 1);

                  if (!hashToken.matches(regex))
                  {
                     bMatch = false;
                     break;
                  }
                  key = key.substring(0, key.length() - regex.length() - 2);
               }
               properties.put(key, hashToken);
            }
            else
            {
               bMatch = false;
               break;
            }
         }
      }
      if (bMatch)
      {
         Class clas = routeMap.get(path);
         if (clas != null)
         {
            descriptor = new HashMap<String,Object>()
            {{
               put(kKEY_CLASS, clas);
               put(kKEY_PROPS, properties);
            }};
         }
         break;
      }
   }

   return(descriptor);
}
/*------------------------------------------------------------------------------

@name       main - standard main routine
                                                                              */
                                                                             /**
            Standard main routine.

@return     void

@param      args     args[0] - platform specification

@history    Sat Aug 11, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static void main(
   String[] args)
{
   try
   {
      class Component0 extends AppComponentTemplate{}
      class Component1 extends AppComponentTemplate{}
      class Component2 extends AppComponentTemplate{}
      class Component3 extends AppComponentTemplate
      {
         protected Map<String,Class> getNavRoutes()
         {
                                       // nested routes                       //
            Map<String,Class> routeMap = new HashMap<String,Class>()
            {{
               put("",                           Component1.class);
               put("animals",                    Component2.class);
               put("flowers/:color/:leafcount?", Component3.class);
               put("trees/:height(ten|twenty)",  Component0.class);
            }};
            return(routeMap);
         }
      }

      Map<String,Class> routeMap = new HashMap<String,Class>()
      {{
         put("",                           Component0.class);
         put("animals",                    Component1.class);
         put("flowers/:color/:leafcount?", Component2.class);
         put("trees/:height(ten|twenty)",  Component3.class);
      }};

      String hash = "";
      Map<String,Object> descriptor = descriptorForHash(routeMap, hash);
      Map<String,String> properties = (Map)descriptor.get(kKEY_PROPS);
      if (descriptor == null
            || descriptor.get(kKEY_CLASS) != Component0.class
            || properties.size() != 0)
      {
         throw new IllegalStateException(
            "For hash='" + hash
          + "', expected class was Component0.class without properties "
          + "but actual class was " + descriptor.get(kKEY_CLASS)
          + " and properties were "
          + (properties != null ? "not " : "") + "zero length");
      }

      hash       = "animals";
      descriptor = descriptorForHash(routeMap, hash);
      properties = (Map)descriptor.get(kKEY_PROPS);
      if (descriptor == null
            || descriptor.get(kKEY_CLASS) != Component1.class
            || properties.size() != 0)
      {
         throw new IllegalStateException(
            "For hash='" + hash
          + "', expected class was Component1.class without properties "
          + "but actual class was " + descriptor.get(kKEY_CLASS)
          + " and properties were "
          + (properties != null ? "not " : "") + "zero length");
      }

      hash       = "flowers/red/10";
      descriptor = descriptorForHash(routeMap, hash);
      properties = (Map)descriptor.get(kKEY_PROPS);
      String color     = properties.get("color");
      String leafcount = properties.get("leafcount");
      if (descriptor == null
            || descriptor.get(kKEY_CLASS) != Component2.class
            || !"red".equals(color)
            || !"10".equals(leafcount))
      {
         throw new IllegalStateException(
            "For hash='" + hash
          + "', expected class was Component1.class "
          + "with property 'color' 'red' "
          + "and property 'leafcount' '10' "
          + "but actual class was " + descriptor.get(kKEY_CLASS)
          + "with property 'color' " + color
          + "and property 'leafcount' " + leafcount);
      }

      hash       = "trees/twenty";
      descriptor = descriptorForHash(routeMap, hash);
      properties = (Map)descriptor.get(kKEY_PROPS);
      String height = properties.get("height");
      if (descriptor == null
            || descriptor.get(kKEY_CLASS) != Component3.class
            || descriptor.get(kKEY_PROPS) == null
            || !"twenty".equals(height))
      {
         throw new IllegalStateException(
            "For hash='" + hash
          + "', expected class was Component3.class "
          + "with property 'height' '20' "
          + "but actual class was " + descriptor.get(kKEY_CLASS)
          + "with property 'height' " + height);
      }
                                       // nested routes ----------------------//
      hash       = "animals";
      descriptor = descriptorForHash(routeMap, hash);
      properties = (Map)descriptor.get(kKEY_PROPS);
      if (descriptor == null
            || descriptor.get(kKEY_CLASS) != Component2.class
            || properties.size() != 0)
      {
         throw new IllegalStateException(
            "For hash='" + hash
          + "', expected class was Component2.class without properties "
          + "but actual class was " + descriptor.get(kKEY_CLASS)
          + " and properties were "
          + (properties != null ? "not " : "") + "zero size");
      }
   }
   catch(Exception e)
   {
      e.printStackTrace();
   }
}
}//====================================// end IRouter ========================//
