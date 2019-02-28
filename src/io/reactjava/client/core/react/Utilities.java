/*==============================================================================

name:       Utilities.java

purpose:    Utilities.

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import com.giavaneers.util.gwt.APIRequestor;
import com.giavaneers.util.gwt.Logger;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.typedarrays.client.Uint8ArrayNative;
import com.google.gwt.typedarrays.shared.Uint8Array;
import elemental2.core.JsObject;
import io.reactjava.client.core.providers.http.HttpClientBase;
import io.reactjava.client.core.providers.http.IHttpResponse;
import io.reactjava.client.core.providers.http.IHttpResponse.ResponseType;
import io.reactjava.client.core.resources.javascript.IJavascriptResources;
import io.reactjava.client.core.resources.javascript.JavascriptResources;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jsinterop.core.dom.Document;
import jsinterop.core.dom.Element;
import jsinterop.core.html.Window;
                                       // Utilities ==========================//
public class Utilities
{
                                       // class constants --------------------//
private static final Logger kLOGGER = Logger.newInstance();

                                       // class variables ------------------- //
                                       // map of script by path               //
protected static Map<String,String> scriptByPath;

                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       Utilities - default constructor
                                                                              */
                                                                             /**
            Default constructor

@return     An instance of Utilities if successful.

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected Utilities()
{
}
/*------------------------------------------------------------------------------

@name       bytesToUint8Array - get Uint8Array from byte array
                                                                              */
                                                                             /**
            Get Uint8Array from byte array.

@return     Uint8Array from byte array

@param      bytes    byte array

@history    Mon May 09, 2016 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Uint8ArrayNative bytesToUint8Array(
   byte[] bytes)
{
   return(bytesToUint8Array(bytes, 0, bytes.length));
}
/*------------------------------------------------------------------------------

@name       bytesToUint8Array - get Uint8Array from byte array
                                                                              */
                                                                             /**
            Get Uint8Array from byte array.

@return     Uint8Array from byte array

@param      bytes    byte array

@history    Mon May 09, 2016 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Uint8ArrayNative bytesToUint8Array(
   byte[] bytes,
   int    offset,
   int    length)
{
   Uint8ArrayNative aBytes = Uint8ArrayNative.create(length);
   for (int i = 0, iMax = length; i < iMax; i++, offset++)
   {
      aBytes.set(i, bytes[offset]);
   }

   return(aBytes);
}
/*------------------------------------------------------------------------------

@name       createScriptByPath - get shared instance
                                                                              */
                                                                             /**
            Get shared instance.

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void createScriptByPath(
   IConfiguration           configuration,
   Callback<Void,Exception> callback)
{
   long   startTime  = System.currentTimeMillis();
   String configName = configuration.getName();

   String path =
      getArtifactReactJavaDirectoryName()
         + "/javascript/"
         + (configName != null ? configName : "") + "Archive.js";

   String url = GWT.getModuleBaseForStaticFiles() + path;

   APIRequestor requestor = (Object rsp, Object token) ->
   {
      IHttpResponse response = (IHttpResponse)rsp;
      Throwable error = response.getError();
      if (error != null)
      {
         callback.onFailure(new Exception(error));
      }
      else
      {
         try
         {
            long loadedTime = System.currentTimeMillis();

            kLOGGER.logInfo(
               "Utilities.createScriptByPath(): archive download time="
                  + (loadedTime - startTime));

            byte[]   bytes        = response.getBytes();
            byte[]   decompressed = fromInflaterFiltered(bytes, 0, bytes.length);
            String   header       = new String(decompressed, 0, 5000, "UTF-8");
            String   marker       = "headerEnd;";
                     header       = header.substring(0, header.indexOf(marker));
            int      offsetBase   = header.length() + marker.length();
            String[] descs        = header.split(";");
            String   pathLast     = null;
            int      offsetLast   = -1;
            int      length       = -1;
            String   scriptLast   = null;
            scriptByPath          = new HashMap<String,String>();

            for (int i = 0; i < descs.length; i++)
            {
               String   desc   = descs[i];
               String[] splits = desc.split("=");
               int      offset = offsetBase + Integer.parseInt(splits[1]);

               if (pathLast != null)
               {
                  length     = offset - offsetLast;
                  scriptLast = new String(decompressed, offsetLast, length);
                  scriptByPath.put(pathLast, scriptLast);
               }
               pathLast   = splits[0];
               offsetLast = offset;
            }

            length     = decompressed.length - offsetLast;
            scriptLast = new String(decompressed, offsetLast, length);
            scriptByPath.put(pathLast, scriptLast);

            kLOGGER.logInfo(
               "Utilities.createScriptByPath(): decompress and map build time="
                  + (System.currentTimeMillis() - loadedTime));

            callback.onSuccess(null);
         }
         catch(Exception e)
         {
            callback.onFailure(e);
         }
      }
   };
                                       // read the remote archive             //
   new HttpClientBase(url)
      .setResponseType(ResponseType.kARRAYBUFFER)
      .send(requestor);
}
/*------------------------------------------------------------------------------

@name       fromInflaterFiltered - byte array to composite device data record
                                                                              */
                                                                             /**
            Convert compressed byte array to device data record using filtered
            Inflator.


@return     compressed device data record from byte array representation .

@param      bytes     compressed byte array

@history    Mon May 09, 2016 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String fromInflaterFiltered(
   String compressed)
   throws Exception
{
   byte[] inflatedBytes =
      fromInflaterFiltered(compressed.getBytes("UTF-8"), 0, compressed.length());

   String uncompressed = new String(inflatedBytes, "UTF-8");
   return(uncompressed);
}
/*------------------------------------------------------------------------------

@name       fromInflaterFiltered - byte array to composite device data record
                                                                              */
                                                                             /**
            Convert compressed byte array to device data record using filtered
            Inflator.


@return     compressed device data record from byte array representation .

@param      bytes     compressed byte array

@history    Mon May 09, 2016 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static byte[] fromInflaterFiltered(
   byte[]           bytes,
   int              offset,
   int              length)
   throws           Exception
{
   Uint8ArrayNative aBytes       = bytesToUint8Array(bytes, offset, length);
   Uint8Array       inflated     = fromInflaterFilteredNative(aBytes);
   byte[]           decompressed = uint8ArrayToBytes(inflated);

   return(decompressed);
}
/*------------------------------------------------------------------------------

@name       fromInflaterFilteredNative - byte array to composite device data record
                                                                              */
                                                                             /**
            Convert compressed byte array to device data record using filtered
            Inflator.


@return     compressed device data record from byte array representation .

@param      bytes     compressed byte array

@history    Mon May 09, 2016 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public final static native Uint8Array fromInflaterFilteredNative(
   Uint8Array compressed)
   throws Exception
/*-{
   try
   {
      var pako         = $wnd.pako;
      var decompressed = pako.inflate(compressed);
      return(decompressed);
   }
   catch (err)
   {
      console.log(err);
   }
}-*/;
/*------------------------------------------------------------------------------

@name       getHrefParams - get href params
                                                                              */
                                                                             /**
            Get href params.

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static Map<String,String> getHrefParams()
{
   Map<String,String> params = new HashMap<String,String>();
   String             hRef   = Window.getLocation().getHref();
   int                idx    = hRef.indexOf('?');
   if (idx > 0)
   {
      for (String elem : hRef.substring(idx + 1).split("&"))
      {
         String[] tokens = elem.split("=");
         params.put(tokens[0], tokens[1]);
      }
   }
   return(params);
}
/*------------------------------------------------------------------------------

@name       getArtifactReactJavaDirectoryName - get artifact java directory name
                                                                              */
                                                                             /**
            Get artifact reactjava directory name.

@return     artifact reactjava directory name.

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getArtifactReactJavaDirectoryName()
{
   return(IReactCodeGenerator.kREACT_JAVA_DIR_NAME);
}
/*------------------------------------------------------------------------------

@name       getObjectBooleanValueNative - get primitive boolean property value
                                                                              */
                                                                             /**
            Get primitive boolean property value

@return     void

@param      properties     properties instance
@param      key            property name
@param      value          primitive int property value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native boolean getObjectBooleanValueNative(
   JsObject properties,
   String   key)
/*-{
   return(properties[key]);
}-*/;
/*------------------------------------------------------------------------------

@name       getObjectIntValueNative - get primitive int property value
                                                                              */
                                                                             /**
            Get primitive int property value

@return     void

@param      properties     properties instance
@param      key            property name
@param      value          primitive int property value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native int getObjectIntValueNative(
   JsObject properties,
   String   key)
/*-{
   return(properties[key]);
}-*/;
/*------------------------------------------------------------------------------

@name       injectCSS - inject the specified script into the top window
                                                                              */
                                                                             /**
            Standard core entry point method.

@return     void

@param      requestToken      client request token
@param      requestor         client requestor

@history    Sun Jan 7, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void injectCSS(
   String url)
{
   injectLink(url, "stylesheet", "text/css");
}
/*------------------------------------------------------------------------------

@name       injectLink - inject the specified script into the top window
                                                                              */
                                                                             /**
            Standard core entry point method.

@return     void

@param      requestToken      client request token
@param      requestor         client requestor

@history    Sun Jan 7, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void injectLink(
   String linkURL,
   String rel,
   String type)
{
   if (!linkURL.startsWith("http"))
   {
      String rsrcDir =
         "/" + (linkURL.endsWith(".css") ? "css" : "images") + "/";

      linkURL =
         (getArtifactReactJavaDirectoryName() + rsrcDir + linkURL).replace("@","");

      linkURL = GWT.getModuleBaseForStaticFiles() + linkURL;
   }

   Document document    = Window.getDocument();
   Element  linkElement = document.createElement("link");
            linkElement.setAttribute("href", linkURL);

   if (rel != null && rel.length() > 0)
   {
      linkElement.setAttribute("rel",  rel);
   }
   if (type != null && type.length() > 0)
   {
      linkElement.setAttribute("type", type);
   }
   document.getHead().appendChild(linkElement);
}
/*------------------------------------------------------------------------------

@name       injectResourceLoadLazyCompressed - inject resource into the top window
                                                                              */
                                                                             /**
            Inject lazily loaded compressed resource into the top window.

@return     void

@param      rsrcURL        resource url
@param      callback       callback

@history    Sun Jan 7, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void injectResourceLoadLazyCompressed(
   String                         rsrcURL,
   final Callback<Void,Exception> callback)
{
                                       // remove any '@' characters in the    //
                                       // script path since at least the GWT  //
                                       // code server throws CORS objections  //
                                       // with URLs containing a '@' character//

   rsrcURL = (getArtifactReactJavaDirectoryName() + rsrcURL).replace("@","");

                                       // no cache                            //
   rsrcURL =
      GWT.getModuleBaseForStaticFiles()
         + rsrcURL + "?" + System.currentTimeMillis();

   APIRequestor requestor = (Object rsp, Object token) ->
   {
      IHttpResponse response = (IHttpResponse)rsp;
      Throwable error = response.getError();
      if (error != null)
      {
         callback.onFailure(new Exception(error));
      }
      else
      {
         try
         {
            byte[] bytes = response.getBytes();

                                       // decompress the script               //
            byte[] decompressed =
               fromInflaterFiltered(bytes, 0, bytes.length);

            String script = new String(decompressed, "UTF-8");

            //kLOGGER.logInfo(
            //   "injectResourceLoadLazyCompressed(): injecting snippet=\n"
            //      + script.substring(0, 300));

                                       // inject into the top window          //
            ScriptInjector.fromString(script)
               .setWindow(ScriptInjector.TOP_WINDOW)
               .inject();

            callback.onSuccess(null);
         }
         catch(Exception e)
         {
            callback.onFailure(e);
         }
      }
   };
                                       // read the script using a requestor   //
                                       // instead of an Observable since Rx   //
                                       // is in the process of being loaded   //
   new HttpClientBase(rsrcURL)
      .setResponseType(ResponseType.kARRAYBUFFER)
      .setRequestToken(rsrcURL)
      .send(requestor);
}
/*------------------------------------------------------------------------------

@name       injectScriptAsResource - inject script into the top window
                                                                              */
                                                                             /**
            Inject script packaged as a resource into the top window.

@return     void

@param      scriptURL      script url
@param      callback       callback

@history    Sun Jan 7, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void injectScriptAsResource(
   String                   scriptURL,
   boolean                  bCompress,
   Callback<Void,Exception> callback)
{
   JavascriptResources.getScriptTextByPath(
      scriptURL, null, (Object response, final Object reqToken) ->
      {
         if (response instanceof Throwable)
         {
            callback.onFailure(new Exception((Throwable)response));
         }
         else
         {
            if (bCompress)
            {
               try
               {
                  response = fromInflaterFiltered((String)response);
               }
               catch(Exception e)
               {
                  callback.onFailure(e);
               }
            }

            ScriptInjector.fromString(
               (String)response).setWindow(
                  ScriptInjector.TOP_WINDOW).inject();

            callback.onSuccess(null);
         }
      });
}
/*------------------------------------------------------------------------------

@name       injectScriptLoadLazyCompressed - inject script into the top window
                                                                              */
                                                                             /**
            Inject lazily loaded compressed script into the top window.

@return     void

@param      scriptURL      script url
@param      callback       callback

@history    Sun Jan 7, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void injectScriptLoadLazyCompressed(
   String                   scriptURL,
   Callback<Void,Exception> callback)
{
   injectResourceLoadLazyCompressed("/javascript/" + scriptURL, callback);
}
/*------------------------------------------------------------------------------

@name       injectScriptLoadLazyUncompressed - inject script into the top window
                                                                              */
                                                                             /**
            Inject lazily loaded uncompressed script into the top window.

@return     void

@param      scriptURL      script url
@param      callback       callback

@history    Sun Jan 7, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void injectScriptLoadLazyUncompressed(
   String                   scriptURL,
   Callback<Void,Exception> callback)
{
                                       // pako will always be handled here... //
                                       // remove any '@' characters in the    //
                                       // script path since at least the GWT  //
                                       // code server throws CORS objections  //
                                       // with URLs containing a '@' character//
   scriptURL =
      (getArtifactReactJavaDirectoryName()
         + "/javascript/" + scriptURL).replace("@","");

   scriptURL = GWT.getModuleBaseForStaticFiles() + scriptURL;

                        // inject into the top window          //
   ScriptInjector.fromUrl(
      scriptURL).setWindow(
         ScriptInjector.TOP_WINDOW).setCallback(callback).inject();
}
/*------------------------------------------------------------------------------

@name       injectScriptOrCSS - inject the specified script into the top window
                                                                              */
                                                                             /**
            Standard core entry point method.

@return     void

@param      requestToken      client request token
@param      requestor         client requestor

@history    Sun Jan 7, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void injectScriptOrCSS(
   IConfiguration     configuration,
   String             scriptURL,
   final Object       requestToken,
   final APIRequestor requestor)
{
   injectScriptsAndCSS(
      configuration,
      new ArrayList<String>(
         Arrays.asList(new String[]{scriptURL})), requestToken, requestor);
}
/*------------------------------------------------------------------------------

@name       injectScriptPreloaded - inject script into the top window
                                                                              */
                                                                             /**
            Inject preloaded script into the top window.

@return     void

@param      scriptURL      script url
@param      callback       callback

@history    Sun Jan 7, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void injectScriptPreloaded(
   String                   scriptURL,
   Callback<Void,Exception> callback)
{
   String script = scriptByPath.get(scriptURL);
   if (script == null)
   {
      kLOGGER.logWarning("injectScriptPreloaded(): script not found="+ scriptURL);
   }
   else
   {
      ScriptInjector.fromString(
         script + "?" + System.currentTimeMillis()).setWindow(
            ScriptInjector.TOP_WINDOW).inject();
   }
   callback.onSuccess(null);
}
/*------------------------------------------------------------------------------

@name       injectScriptsAndCSS - inject the specified scripts and css
                                                                              */
                                                                             /**
            Inject the specified scripts and css into the top window.

@return     void

@params     configuration     dependency injection and other configuration
                              parameters
@param      subscriber        subscriber on completion

@history    Sun Jan 7, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void injectScriptsAndCSS(
   IConfiguration configuration,
   List<String>   inject,
   Object         requestToken,
   APIRequestor   requestor)
{
   boolean bScriptsLoadLazy = configuration.getScriptsLoadLazy();

   if (inject == null)
   {
      inject = new ArrayList<String>(configuration.getGlobalCSS());
      inject.addAll(configuration.getGlobalImages());
      inject.addAll(configuration.getRequiredPlatformScripts());
      inject.addAll(configuration.getBundleScripts());
   }
   final List<String> injectRsrcURLs = inject;

   Callback<Void,Exception> callback =
      new Callback<Void,Exception>()
      {
         final Callback<Void,Exception> thisCallback = this;
         long  timeOnEntry = System.currentTimeMillis();
         int   counter     = -1;

         public void onFailure(Exception reason)
         {
            kLOGGER.logError("Failed to load " + injectRsrcURLs.get(counter));
            if (requestor != null)
            {
               requestor.apiResponse(reason, requestToken);
            }
         }
         public void onSuccess(Void result)
         {
            if (counter >= 0)
            {
               kLOGGER.logInfo(
                  "Successfully loaded " + injectRsrcURLs.get(counter));
            }
            if (++counter >= injectRsrcURLs.size())
            {
               kLOGGER.logInfo(
                  "Successfully loaded all resources, ms="
                     + (System.currentTimeMillis() - timeOnEntry));

               if (requestor != null)
               {
                  requestor.apiResponse(injectRsrcURLs, requestToken);
               }
            }
            else
            {
               String injectURL = injectRsrcURLs.get(counter);
               if (!injectURL.startsWith("http"))
               {
                                       // each injectURL has zero or more     //
                                       // alternatives, separated by commas,  //
                                       // where the last entry is the one to  //
                                       // be injected                         //

                  String[] choices   = injectURL.split(",");
                           injectURL = choices[choices.length - 1];
               }

               boolean  bCompress =
                  configuration.getScriptsCompressed()
                     && !injectURL.contains(IJavascriptResources.kSCRIPT_PLAT_PAKO);

               kLOGGER.logInfo("Loading " + injectURL);

               if (injectURL.endsWith(".ico"))
               {
                  injectLink(injectURL, "icon", "image/x-icon");
                  this.onSuccess(null);
               }
               else if (injectURL.endsWith(".css")/* || injectURL.endsWith(".scss")*/)
               {
                  injectCSS(injectURL);
                  this.onSuccess(null);
               }
               else if (!injectURL.endsWith(".js"))
               {
                  injectLink(injectURL, "stylesheet", null);
                  this.onSuccess(null);
               }
               else if (injectURL.startsWith("http"))
               {
                                       // inject into the top window          //
                  ScriptInjector.fromUrl(
                     injectURL + "?" + System.currentTimeMillis()).setWindow(
                        ScriptInjector.TOP_WINDOW).setCallback(this).inject();
               }
               else if (IJavascriptResources.kSRCCFG_SCRIPTS_AS_RESOURCES)
               {
                  injectScriptAsResource(injectURL, bCompress, thisCallback);
               }
               else if (!bScriptsLoadLazy)
               {
                  injectScriptPreloaded(injectURL, thisCallback);
               }
               else if (!bCompress)
               {
                                       // pako will always be handled here... //
                  injectScriptLoadLazyUncompressed(injectURL, thisCallback);
               }
               else
               {
                  injectScriptLoadLazyCompressed(injectURL, thisCallback);
               }
            }
         }
      };

   if (!bScriptsLoadLazy && scriptByPath == null)
   {
                                       // inject pako for decompression       //
      injectRsrcURLs.remove(IJavascriptResources.kSCRIPT_PLAT_PAKO);
      injectScriptOrCSS(configuration, IJavascriptResources.kSCRIPT_PLAT_PAKO, null,
         (Object response, Object reqToken) ->
         {
            if (response instanceof Throwable)
            {
               callback.onFailure(new Exception((Throwable)response));
            }
            else
            {
               createScriptByPath(configuration, callback);
            }
         });
   }
   else
   {
      callback.onSuccess(null);
   }
}
/*------------------------------------------------------------------------------

@name       scriptByPath - get map of script by path
                                                                              */
                                                                             /**
            Get map of script by path.

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static Map<String,String> scriptByPath()
{
   if (scriptByPath == null)
   {
      scriptByPath = new HashMap<String,String>();
   }
   return(scriptByPath);
}
/*------------------------------------------------------------------------------

@name       setObjectBooleanValueNative - assign primitive boolean value
                                                                              */
                                                                             /**
            Assign primitive boolean property value

@return     void

@param      properties     properties instance
@param      key            property name
@param      value          primitive float property value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void setObjectBooleanValueNative(
   JsObject properties,
   String   key,
   boolean  value)
/*-{
   properties[key] = value;
}-*/;
/*------------------------------------------------------------------------------

@name       setObjectDoubleValueNative - assign primitive double property value
                                                                              */
                                                                             /**
            Assign primitive double property value

@return     void

@param      properties     properties instance
@param      key            property name
@param      value          primitive double property value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void setObjectDoubleValueNative(
   JsObject properties,
   String   key,
   double   value)
/*-{
   properties[key] = value;
}-*/;
/*------------------------------------------------------------------------------

@name       setObjectFloatValueNative - assign primitive float property value
                                                                              */
                                                                             /**
            Assign primitive float property value

@return     void

@param      properties     properties instance
@param      key            property name
@param      value          primitive float property value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void setObjectFloatValueNative(
   JsObject properties,
   String   key,
   float    value)
/*-{
   properties[key] = value;
}-*/;
/*------------------------------------------------------------------------------

@name       setObjectIntValueNative - assign primitive int property value
                                                                              */
                                                                             /**
            Assign primitive int property value

@return     void

@param      properties     properties instance
@param      key            property name
@param      value          primitive int property value

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native void setObjectIntValueNative(
   JsObject properties,
   String   key,
   int      value)
/*-{
   properties[key] = value;
}-*/;
/*------------------------------------------------------------------------------

@name       uint8ArrayToBytes - get byte array from Uint8Array
                                                                              */
                                                                             /**
            Get byte array from Uint8Array.

@return     byte array from Uint8Array

@param      aBytes      Uint8Array

@history    Mon May 09, 2016 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static byte[] uint8ArrayToBytes(
   Uint8Array aBytes)
{
   byte[] bytes = new byte[0];
   try
   {
      bytes = new byte[aBytes != null ? aBytes.length() : 0];
   }
   catch(StringIndexOutOfBoundsException e)
   {
      e = e;
      throw e;
   }

   for (int i = 0, iMax = bytes.length; i < iMax; i++)
   {
      try
      {
         bytes[i] = (byte)aBytes.get(i);
      }
      catch(StringIndexOutOfBoundsException e)
      {
         e = e;
         throw e;
      }
   }
   return(bytes);
}
}//====================================// end Utilities ======================//
