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
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.EventTarget;
import elemental2.dom.HTMLDocument;
import elemental2.dom.HTMLElement;
import io.reactjava.client.providers.http.HttpClient;
import io.reactjava.client.providers.http.HttpClientBase;
import io.reactjava.client.providers.http.HttpResponse;
import io.reactjava.client.providers.http.IHttpResponse;
import io.reactjava.client.providers.http.IHttpResponse.ResponseType;
import io.reactjava.client.core.resources.javascript.IJavascriptResources;
import io.reactjava.client.core.resources.javascript.JavascriptResources;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import jsinterop.base.Js;
                                       // Utilities ==========================//
public class Utilities
{
                                       // class constants --------------------//
private static final Logger kLOGGER = Logger.newInstance();

public static final String  kREGEX_ZERO_OR_MORE_CHARACTERS = ".*";

public static final String  kREGEX_ONE_OR_MORE_WHITESPACE  = "\\s+";
public static final String  kREGEX_ZERO_OR_MORE_WHITESPACE = "\\s*";

public static final String  kREGEX_ONE_OR_MORE_NON_WHITESPACE  = "\\S+";
public static final String  kREGEX_ZERO_OR_MORE_NON_WHITESPACE = "\\S*";

public static final String  kREGEX_AS_DELIMETER =
   kREGEX_ONE_OR_MORE_WHITESPACE + "as" + kREGEX_ONE_OR_MORE_WHITESPACE;

public static final List<String> kCSS_COLORS =
   new ArrayList<>(
      Arrays.asList(
         "aliceblue",
         "antiquewhite",
         "aqua",
         "aquamarine",
         "azure",
         "beige",
         "bisque",
         "black",
         "blanchedalmond",
         "blue",
         "blueviolet",
         "brown",
         "burlywood",
         "cadetblue",
         "chartreuse",
         "chocolate",
         "coral",
         "cornflowerblue",
         "cornsilk",
         "crimson",
         "cyan",
         "darkblue",
         "darkcyan",
         "darkgoldenrod",
         "darkgray",
         "darkgreen",
         "darkkhaki",
         "darkmagenta",
         "darkolivegreen",
         "darkorange",
         "darkorchid",
         "darkred",
         "darksalmon",
         "darkseagreen",
         "darkslateblue",
         "darkslategray",
         "darkturquoise",
         "darkviolet",
         "deeppink",
         "deepskyblue",
         "dimgray",
         "dodgerblue",
         "firebrick",
         "floralwhite",
         "forestgreen",
         "fuchsia",
         "gainsboro",
         "ghostwhite",
         "gold",
         "goldenrod",
         "gray",
         "green",
         "greenyellow",
         "honeydew",
         "hotpink",
         "indianred",
         "indigo",
         "ivory",
         "khaki",
         "lavender",
         "lavenderblush",
         "lawngreen",
         "lemonchiffon",
         "lightblue",
         "lightcoral",
         "lightcyan",
         "lightgoldenrodyellow",
         "lightgreen",
         "lightgrey",
         "lightpink",
         "lightsalmon",
         "lightseagreen",
         "lightskyblue",
         "lightslategray",
         "lightsteelblue",
         "lightyellow",
         "lime",
         "limegreen",
         "linen",
         "magenta",
         "maroon",
         "mediumaquamarine",
         "mediumblue",
         "mediumorchid",
         "mediumpurple",
         "mediumseagreen",
         "mediumslateblue",
         "mediumspringgreen",
         "mediumturquoise",
         "mediumvioletred",
         "midnightblue",
         "mintcream",
         "mistyrose",
         "moccasin",
         "navajowhite",
         "navy",
         "navyblue",
         "oldlace",
         "olive",
         "olivedrab",
         "orange",
         "orangered",
         "orchid",
         "palegoldenrod",
         "palegreen",
         "paleturquoise",
         "palevioletred",
         "papayawhip",
         "peachpuff",
         "peru",
         "pink",
         "plum",
         "powderblue",
         "purple",
         "red",
         "rosybrown",
         "royalblue",
         "saddlebrown",
         "salmon",
         "sandybrown",
         "seagreen",
         "seashell",
         "sienna",
         "silver",
         "skyblue",
         "slateblue",
         "slategray",
         "snow",
         "springgreen",
         "steelblue",
         "tan",
         "teal",
         "thistle",
         "tomato",
         "transparent",
         "turquoise",
         "violet",
         "wheat",
         "white",
         "whitesmoke",
         "yellow",
         "yellowgreen"));
                                       // class variables ------------------- //
                                       // map of script by path               //
protected static Map<String,String> scriptByPath;
                                       // list of imported stylesheets        //
protected static List<String>       importedStylesheets;
                                       // list of js and css to inject        //
protected static List<String>       injectRsrcURLs;
                                       // list of js and css injected         //
protected static List<String>       injectedRsrcURLs;

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

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected Utilities()
{
}
/*------------------------------------------------------------------------------

@name       addEventListener - add event listener
                                                                              */
                                                                             /**
            Add event listener with any specified debounce interval and
            and specified value filter.

@param      eventName            event name
@param      eventTarget          target element; if null -> window
@param      debounceInterval     debounce msec
@param      filter               any function producing filter that must change
@param      listener             event listener

@history    Mon Feb 24, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void addEventListener(
   final String        eventName,
   EventTarget eventTarget,
   final int           debounceInterval,
   final Supplier filter,
   final EventListener listener)
{
   if (eventTarget == null)
   {
      eventTarget = DomGlobal.window;
   }
   eventTarget.addEventListener(eventName, new EventListener()
   {
      long   last;
      long   now;
      Object lastValue;
      Object val;

      public void handleEvent(Event e)
      {
         now = System.currentTimeMillis();
         val = filter != null ? filter.get() : null;

         if ((last == 0 || (now - last) > debounceInterval)
               && (lastValue == null || !lastValue.equals(val)))
         {
            lastValue = val;
            last      = now;
            try
            {
               listener.handleEvent(e);
            }
            catch(Throwable t)
            {
               kLOGGER.logError(
                  "Utilities.addEventListener(): handleEvent threw " + t);
            }
         }
      }
   });
};
/*------------------------------------------------------------------------------

@name       assignReactJavaWindowVariable - assign ReactJava window variable
                                                                              */
                                                                             /**
            Assign ReactJava window variable.

@param      windowVarName     existing window variable name which is to be the
                              value of the new ReactJava window variable.

@history    Sat Aug 15, 2020 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static  void assignReactJavaWindowVariable(
   String windowVarName)
{
   NativeObject windowObject = Js.uncheckedCast(DomGlobal.window);
   Object       windowVarVal = windowObject.get(windowVarName);
   NativeObject ReactJava    = Js.uncheckedCast(windowObject.get("ReactJava"));
   ReactJava.set(windowVarName, windowVarVal);
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

@param      compressed     compressed byte array

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

@param      compressed     compressed byte array

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

@name       getElementHeight - get element height
                                                                              */
                                                                             /**
            Get element height in pixels including padding and border.

@return     Element height in pixels, or null if not found.

@param      elementId      target elementId

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static Integer getElementHeight(
   String elementId)
{
   Integer height  = null;
   Element element = DomGlobal.document.getElementById(elementId);
   if (element instanceof HTMLElement)
   {
      height = getElementHeight((HTMLElement)element);
   }

   return(height);
}
/*------------------------------------------------------------------------------

@name       getElementHeight - get element height
                                                                              */
                                                                             /**
            Get element height in pixels including padding and border.

@return     Element height in pixels, or null if not found.

@param      element     target element

@history    Tue Aug 29, 2017 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static Integer getElementHeight(
   HTMLElement element)
{
   return(element != null ? element.offsetHeight : null);
}
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
   String             hRef   = DomGlobal.window.location.getHref();
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
   return(ReactGeneratedCode.kREACT_JAVA_DIR_NAME);
}
/*------------------------------------------------------------------------------

@name       getImportedStylesheets - get imported stylesheets
                                                                              */
                                                                             /**
            Get imported stylesheets. Cannot use RxJs here since it has not
            been loaded yet.

@return     list of imported stylesheets.

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static List<String> getImportedStylesheets(
   Callback<Void,Exception> callback)
{
   if (importedStylesheets == null)
   {
      APIRequestor requestorLocal = (Object rsp, Object token) ->
      {
         IHttpResponse response = (IHttpResponse)rsp;
         Throwable     error    = response.getError();
         try
         {
            importedStylesheets = new ArrayList<>();
            if (error == null)
            {
               String stylesheets = null;
               byte[] rspBytes    = response.getBytes();
               if (rspBytes != null && rspBytes.length > 0)
               {
                  stylesheets = new String(rspBytes, "UTF-8").trim();
               }
               if (stylesheets != null && stylesheets.length() > 0)
               {
                  importedStylesheets.addAll(
                     Arrays.asList(stylesheets.split(",")));
               }
               else
               {
                  kLOGGER.logInfo(
                     "Utilities.getImportedStylesheets(): "
                   + "no list of external stylesheets found");
               }
            }

            callback.onSuccess(null);
         }
         catch(Exception e)
         {
            callback.onFailure(e);
         }
      };

      String cssListURL =
         GWT.getModuleBaseForStaticFiles()
            + getArtifactReactJavaDirectoryName()
            + "/css/" + ReactGeneratedCode.kIMPORTED_STYLESHEETS_LIST;

                                       // read any remote list of stylesheets //
      kLOGGER.logInfo(
         "Utilities.getImportedStylesheets(): "
       + "checking for any list of external stylesheets");

      new HttpClientBase(cssListURL)
         .setResponseType(ResponseType.kARRAYBUFFER)
         .send(requestorLocal);
   }

   return(importedStylesheets);
}
/*------------------------------------------------------------------------------

@name       getInjectedRsrcURLs - get list of injected resources
                                                                              */
                                                                             /**
            Get list of injected resources.

@return     list list of injected resources.

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static List<String> getInjectedRsrcURLs()
{
   if (injectedRsrcURLs == null)
   {
      injectedRsrcURLs = new ArrayList<>();
   }
   return(injectedRsrcURLs);
}
/*------------------------------------------------------------------------------

@name       getObjectBooleanValueNative - get primitive boolean property value
                                                                              */
                                                                             /**
            Get primitive boolean property value

@return     boolean value

@param      properties     properties instance
@param      key            property name

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

@name       getObjectDoubleValueNative - get primitive double property value
                                                                              */
                                                                             /**
            Get primitive double property value

@return     primitive double property value

@param      properties     properties instance
@param      key            property name

@history    Mon May 21, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native double getObjectDoubleValueNative(
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

@return     primitive int property value

@param      properties     properties instance
@param      key            property name

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

@name       getURLAsString - get specified url as string
                                                                              */
                                                                             /**
            Get specified url as string.

@return     and observable for the string

@param      url         specified url

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Observable<String> getURLAsString(
   String url)
{
   Observable<String> observable = Observable.create(
      (Subscriber<String> subscriber) ->
      {
         HttpClient.get(url).subscribe(
            (HttpResponse rsp) ->
            {
               String content = rsp.getText();
               subscriber.next(content);
               subscriber.complete();
            },
            (Throwable error) ->
            {
               kLOGGER.logError(error);
            });
         return(subscriber);
      });

   return(observable);
}
/*------------------------------------------------------------------------------

@name       getURLAsString - get specified url as string
                                                                              */
                                                                             /**
            Get specified url as string.

@param      url         specified url
@param      callback    completion callback

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void getURLAsString(
   String                     url,
   Callback<String,Exception> callback)
{
   APIRequestor requestor = (Object rsp, Object token) ->
   {
      IHttpResponse response = (IHttpResponse)rsp;
      Throwable     error    = response.getError();
      if (error != null)
      {
         callback.onFailure(new Exception(error));
      }
      else
      {
         try
         {
            byte[] bytes = response.getBytes();
            callback.onSuccess(new String(bytes, "UTF-8"));
         }
         catch(Exception e)
         {
            callback.onFailure(e);
         }
      }
   };
                                       // read the url using a requestor      //
                                       // instead of an Observable since Rx   //
                                       // may not yet have been loaded        //
   new HttpClientBase(url)
      .setResponseType(ResponseType.kARRAYBUFFER)
      .setRequestToken(url)
      .send(requestor);
}
/*------------------------------------------------------------------------------

@name       injectCSS - inject the specified script into the top window
                                                                              */
                                                                             /**
            Standard core entry point method.

@param      url      url

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

@param      linkURL      linkURL
@param      rel            rel
@param      type         type

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

   HTMLDocument document = DomGlobal.document;
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
   document.head.appendChild(linkElement);
}
/*------------------------------------------------------------------------------

@name       injectResourceLoadLazyCompressed - inject resource into the top window
                                                                              */
                                                                             /**
            Inject lazily loaded compressed resource into the top window.

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

@name       injectScript- inject script into the top window
                                                                              */
                                                                             /**
            Inject script into the top window.

@param      scriptURL      script url

@history    Sun Jan 7, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void injectScript(
   String                   scriptURL,
   Callback<Void,Exception> callback)
{
   ScriptInjector.fromUrl(
      scriptURL + "?" + System.currentTimeMillis()).setWindow(
         ScriptInjector.TOP_WINDOW).setCallback(callback).inject();
}
/*------------------------------------------------------------------------------

@name       injectScriptAsResource - inject script into the top window
                                                                              */
                                                                             /**
            Inject script packaged as a resource into the top window.

@param      scriptURL      script url
@param      bCompress      true iff should compress
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

@param      configuration     configuration
@param      scriptURL         script url
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

@param      configuration     dependency injection and other configuration
                              parameters
@param      injectCustom      custom inject
@param      requestToken      client request token
@param      requestor         client requestor

@history    Sun Jan 7, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void injectScriptsAndCSS(
   IConfiguration configuration,
   List<String>   injectCustom,
   Object         requestToken,
   APIRequestor   requestor)
{
   boolean bScriptsLoadLazy = configuration.getScriptsLoadLazy();

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
                                       // will be null on initial entry...    //
            List importedStylesheets = getImportedStylesheets(this);
            if (importedStylesheets == null)
            {
            }
            else if (counter < 0)
            {
               if (injectCustom == null)
               {
                  injectRsrcURLs = new ArrayList<>(importedStylesheets);
                  injectRsrcURLs.addAll(configuration.getGlobalCSS());
                  injectRsrcURLs.addAll(configuration.getGlobalImages());
                  injectRsrcURLs.addAll(configuration.getRequiredPlatformScripts());
                  injectRsrcURLs.addAll(configuration.getBundleScripts());
               }
               else
               {
                  injectRsrcURLs = new ArrayList<>(injectCustom);
               }
            }
            else
            {
               String injected = injectRsrcURLs.get(counter);

                                       // check for ReactJava var assignment  //
               String[] splits = injected.split(kREGEX_AS_DELIMETER);
               if (splits.length > 1)
               {
                  String rjVarName = splits[1];
                  int    idx       = rjVarName.indexOf('.');
                  if (idx < 0)
                  {
                     throw new IllegalStateException(
                        "Window variable assignment " + rjVarName
                      + "should be of the form 'ReactJava.[varName]'");
                  }
                  String windowVar = rjVarName.substring(idx + 1);
                  assignReactJavaWindowVariable(windowVar);
               }

               getInjectedRsrcURLs().add(injected);
               kLOGGER.logInfo("Successfully loaded " + injected);
            }

            if (importedStylesheets == null)
            {
            }
            else if (++counter >= injectRsrcURLs.size())
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
                     && !injectURL.contains(
                           IJavascriptResources.kSCRIPT_PLAT_PAKO);

               kLOGGER.logInfo("Loading " + injectURL);

                                       // check for ReactJava var assignment  //
               String[] splits = injectURL.split(kREGEX_AS_DELIMETER);
               if (splits.length > 1)
               {
                  injectURL = splits[0];
               }

               if (getInjectedRsrcURLs().contains(injectURL))
               {
                                       // don't inject it twice...            //
               }
               else if (injectURL.endsWith(".ico"))
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
                                       // ensure ReactJava window variable    //
                                       // for debugging purposes              //

               JsObject reactJava = ReactJava.getReactJavaWindowVariable();
               int      dummy     = 0;
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

@name       isURL - test whether specified candidate is URL
                                                                              */
                                                                             /**
            Test whether specified candidate is URL.

            The specified candidate is considered a URL if it starts with
            "http://", "https://", or it contains a "/" and contains no
            whitespace characters,

@return     true iff specified candidate is URL

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static boolean isURL(
   String candidate)
{
   boolean bURL =
      candidate.startsWith("http://")
         || candidate.startsWith("https://")
                                       // relative url                        //
         || (candidate.indexOf('/') >= 0
               && candidate.indexOf('\n') < 0
               && candidate.indexOf('\t') < 0
               && candidate.indexOf('\r') < 0
               && candidate.indexOf(' ') < 0);

   return(bURL);
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

@name       toAbsoluteURL - generate an absolute url from specified url
                                                                              */
                                                                             /**
            Generate absolute url from specified url.

            If the specified url is a relative url, generates an absolute url
            using the current location; otherwise, if the specified url is an
            absolute url, returns it unchanged.

@return     an absolute url from specified url, or null iff url is null

@history    Sun Mar 31, 2019 10:30:00 (Giavaneers - LBM) created
            Fri May 22, 2020 10:30:00 (Giavaneers - LBM) modified to assume
               the specified url is always a url, so never returns null as it
               use to do if the specified url was not already absolute and
               did not contain a '/' character.

@notes

                                                                              */
//------------------------------------------------------------------------------
public static String toAbsoluteURL(
   String url)
{
   String absolute = url;
   String search;
   if (url != null)
   {
      if (url.indexOf('\n') >= 0
            || url.indexOf('\t') >= 0
            && url.indexOf('\r') >= 0
            && url.indexOf(' ') >= 0)
      {
         throw new IllegalArgumentException("URL may not contain whitespace");
      }
                                       // translate some urls from url params //
      url = url.replace(']','/');

      if (url.startsWith("http://")
            || url.startsWith("https://")
            || url.startsWith("jar:http://")
            || url.startsWith("jar:https://"))
      {
         absolute = url;
      }
      else
      {
                                       // relative url                        //
         absolute = DomGlobal.window.location.getHref();
         search   = DomGlobal.window.location.getSearch();
         if (search.length() > 0)
         {
                                       // remove any query string             //
            absolute = absolute.substring(0, absolute.indexOf(search));
            if (absolute.endsWith("/"))
            {
               absolute = absolute.substring(0, absolute.length() - 1);
            }
         }

         absolute = absolute.substring(0, absolute.lastIndexOf('/') + 1) + url;
      }
   }

   return(absolute);
}
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
