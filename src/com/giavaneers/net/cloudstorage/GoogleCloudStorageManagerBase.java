/*==============================================================================

name:       GoogleCloudStorageManagerBase.java

purpose:    Google Cloud Storage Manager, providing access to Google Cloud 
            Storage from within a desktop or mobile client.
            
            For authorization in this scenario we use a service account, which 
            is an account that belongs to our application instead of to an 
            individual end user. Our application calls Google APIs on behalf 
            of the service account, and user consent is not required.

            Note that Google Cloud Storage DOES NOT SUPPORT API KEYS for
            authentication.

            A service account's credentials include a generated email address
            that is unique, a client ID, and at least one public/private key 
            pair. We obtain these credentials in the Google Developers Console, 
            or if our application uses Google App Engine, a service account is 
            set up automatically. We use the client ID and one private key to 
            create a signed JWT (pronounced 'jot') and construct an access-token
            request in the appropriate format. 
            
            Authentication proceeds in the following three steps:
            
            1. Create a JWT, which includes a header, a claim set, and a 
               signature.
            
            2. Request an access token from the Google OAuth 2.0 Authorization 
               Server.
            
            3. Handle the JSON response that the Authorization Server returns.
               If the response includes an access token, use the access token 
               to call a Google API. (If the response does not include an 
               access token, your JWT and token request might not be properly 
               formed, or the service account might not have permission to 
               access the requested scopes.)
               

history:    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

notes:

                     This program was created by Giavaneers
            and is the confidential and proprietary product of Giavaneers.
         Any unauthorized use, reproduction or transfer is strictly prohibited.

                        COPYRIGHT 2014 BY GIAVANEERS, INC.
         (Subject to limited distribution and restricted disclosure only).
                              All rights reserved.

==============================================================================*/
                                       // package ----------------------------//
package com.giavaneers.net.cloudstorage;
                                       // imports ----------------------------//
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Lists;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.model.ObjectAccessControl;
import com.google.api.services.storage.model.Objects;
import com.google.api.services.storage.model.StorageObject;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Logger;
                                       // GoogleCloudStorageManagerBase ======//
public class GoogleCloudStorageManagerBase
{
                                       // class constants --------------------//
private static final Logger            kLOGGER =
   Logger.getLogger(GoogleCloudStorageManagerBase.class.getName());

protected static final String          kCLOUD_STORAGE_SCOPE =
   "https://www.googleapis.com/auth/devstorage.read_write";

                                       // json factory                        //
protected static final JsonFactory     kJSON_FACTORY =
   JacksonFactory.getDefaultInstance();
                                       // max retry attempts                  //
protected static final int             kWRITE_MAX_RETRIES = 3;

public static final Map<String,String> kMIME_TYPE_MAP =
   new HashMap<String,String>()
   {{
      put("bin",  "application/octet-stream");
      put("jar",  "application/octet-stream");
      put("epub", "application/epub+zip");
      put("pdf",  "application/pdf");
      put("mobi", "application/x-mobipocket-ebook");
      put("txt",  "text/plain");
      put("htm",  "text/html");
      put("html", "text/html");
      put("css",  "text/css");
      put("csv",  "text/csv");
      put("js",   "text/javascript");
      put("gif",  "image/gif");
      put("jpeg", "image/jpg");
      put("jpg",  "image/jpg");
      put("png",  "image/png");
      put("mov",  "video/quicktime");
      put("mp3",  "audio/mpeg");
      put("ogg",  "audio/ogg");
      put("avi",  "video/avi");
      put("f4v",  "video/f4v");
      put("flv",  "video/flv");
      put("m4v",  "video/x-m4v");
      put("mp4",  "video/mp4");
      put("ogv",  "video/ogg");
      put("txt",  "text/html");
      put("webm", "video/webm");
      put("wmv",  "video/wmv");
      put("zip",  "application/zip");
      put("vtt",  "text/vtt");
   }};
                                       // class variables --------------------//
                                       // shared instance                     //
protected static GoogleCloudStorageManagerBase
                                  sharedInstance;
                                       // http transport                      //
protected static HttpTransport    httpTransport;
                                       // service account id                  //
protected static String           serviceAccountId;
                                       // svc acct private key input stream   //
protected static InputStream      privateKeyInputStream;
                                       // applicationName                     //
protected static String           applicationName;
                                       // storage client                      //
protected static Storage          storage;
                                       // public instance variables ----------//
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables ---------//
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       GoogleCloudStorageManagerBase - default constructor
                                                                              */
                                                                             /**
            Constructor

@return     An instance of GoogleCloudStorageManagerBase if successful.

@history    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public GoogleCloudStorageManagerBase()
{
}
/*------------------------------------------------------------------------------

@name       GoogleCloudStorageManagerBase - default constructor
                                                                              */
                                                                             /**
            Constructor

@return     An instance of GoogleCloudStorageManagerBase if successful.

@history    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public GoogleCloudStorageManagerBase(
   String      serviceAccountId,
   InputStream privateKeyInputStream,
   String      applicationName)
{
   this.serviceAccountId      = serviceAccountId;
   this.privateKeyInputStream = privateKeyInputStream;
   this.applicationName       = applicationName;
}
/*------------------------------------------------------------------------------

@name       deleteFile - delete file from google cloud storage
                                                                              */
                                                                             /**
            Delete file from google cloud storage.

@return     void

@param      targetURL      destination url

@history    Tue Dec 16, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void deleteFile(
   String targetURL)
   throws Exception
{
   deleteFile(targetURL, null, null);
}
/*------------------------------------------------------------------------------

@name       deleteFile - delete file from google cloud storage
                                                                              */
                                                                             /**
            Delete file from google cloud storage.
               
@return     void

@param      targetURL      destination url

@history    Tue Dec 16, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void deleteFile(
   String                    targetURL,
   String                    requestToken,
   BiConsumer<Object,Object> callback)
   throws                    Exception
{
   kLOGGER.info(
      "GoogleCloudStorageManagerBase.deleteFile(): "
    + "begin for targetURL=" + targetURL);
    
   String[] tokens     = targetURL.split("/", 4);
   String   bucketName = tokens[2];
   String   objectName = tokens[3];
   Storage  storage    = getStorage();

   Storage.Objects.Delete delete = 
      storage.objects().delete(bucketName, objectName);
   
   delete.execute();
   
   if (callback != null)
   {
      callback.accept("OK", requestToken);
   }
}
/*------------------------------------------------------------------------------

@name       downloadObject - download object from google cloud storage
                                                                              */
                                                                             /**
            Download object from google cloud storage.

@return     void

@param      src      source object
@param      dst      destination file

@history    Tue Dec 16, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void downloadObject(
   StorageObject src,
   File          dst)
   throws        Exception
{
   String   bucketName = src.getBucket();
   String   objectName = src.getName();
   Storage  storage    = getStorage();

   kLOGGER.info(
      "GoogleCloudStorageManagerBase.downloadObject(): "
    + "begin for src=" + objectName);


   Storage.Objects.Get get = storage.objects().get(bucketName, objectName);

                                       // downloading data                    //
   FileOutputStream out = new FileOutputStream(dst);

                                       // since not in AppEngine, download    //
                                       // the whole thing in one request, if  //
                                       // possible                            //
   get.getMediaHttpDownloader().setDirectDownloadEnabled(true);
   get.executeMediaAndDownloadTo(out);
}
/*------------------------------------------------------------------------------

@name       downloadPublicFile - download public file from google cloud storage
                                                                              */
                                                                             /**
            Download public file from google cloud storage, using
            non-credentialed http request

@return     void

@param      src      source url
@param      dst      destination file

@history    Tue Dec 16, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void downloadPublicFile(
   String  src,
   File    dst)
   throws  IOException
{
   kLOGGER.info(
      "GoogleCloudStorageManagerBase.downloadPublicFile(): "
    + "begin for src=" + src);

   FileOutputStream   out      = new FileOutputStream(dst);
   HttpRequestFactory factory  = getHttpTransport().createRequestFactory();
   HttpRequest        request  = factory.buildGetRequest(new GenericUrl(src));

   request.execute().download(out);
   kLOGGER.info("Done");
}
/*------------------------------------------------------------------------------

@name       fileExists - check whether target file exists
                                                                              */
                                                                             /**
            Check whether target file exists. If length >= 0, test includes 
            whether target file length is as specified.
               
@return     true iff target file exists

@param      targetURL         target url
@param      oemName           oem
@param      length            target file length

@history    Sun Nov 22, 2015 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public boolean fileExists(
   String  targetURL,
   String  oemName,
   long    length)
   throws  Exception
{
   kLOGGER.info(
      "GoogleCloudStorageManagerBase.fileExists(): "
    + "begin for targetURL=" + targetURL);
   
   boolean  bExists    = false;
   String[] tokens     = targetURL.split("/", 4);
   String   bucketName = tokens[2];
   String   objectName = tokens[3];
   
   try
   {
      long actualLength = fileLength(bucketName, objectName);
      if (length < 0)
      {
         bExists = actualLength >= 0;
      }
      else
      {
         bExists = actualLength == length;
      }
   } 
   catch(Exception e)
   {
      if (e.getMessage().indexOf("404 Not Found") < 0)
      {
         throw e;
      }
   }

   return(bExists);
}
/*------------------------------------------------------------------------------

@name       fileLength - check whether target file exists
                                                                              */
                                                                             /**
            Check whether target file exists. If length >= 0, test includes 
            whether target file length is as specified.
               
@return     true iff target file exists

@param      targetURL         target url
@param      oemName           oem
@param      length            target file length

@history    Sun Nov 22, 2015 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public long fileLength(
   String  bucketName,
   String  objectName)
   throws  Exception
{
   kLOGGER.info(
      "GoogleCloudStorageManagerBase.fileExists(): "
    + "begin for bucket=" + bucketName + ", object=" + objectName);
   
   long    fileLength = -1;
   Storage storage    = getStorage();

   Storage.Objects.Get get = storage.objects().get(bucketName, objectName);
   try
   {
      StorageObject object = get.execute();
      if (object != null)
      {
         fileLength = object.getSize().longValue();
      }
   } 
   catch(Exception e)
   {
      if (e.getMessage().indexOf("404 Not Found") < 0 
            && e.getMessage().indexOf("403 Forbidden") < 0)
      {
         throw e;
      }
   }

   return(fileLength);
}
/*------------------------------------------------------------------------------

@name       getApplicationName - get application name
                                                                              */
                                                                             /**
            Get application name.

@return     application name

@history    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static String getApplicationName()
{
   return(applicationName);
}
/*------------------------------------------------------------------------------

@name       getCredentialJSONPrivateKeyInputStream - get credential private key
                                                                              */
                                                                             /**
            Get credential JSON private key input stream

@return     service account credential

@history    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static InputStream getCredentialJSONPrivateKeyInputStream()
{
   return(privateKeyInputStream);
}
/*------------------------------------------------------------------------------

@name       getHttpTransport - get http transport
                                                                              */
                                                                             /**
            Get http transport.

@return     http transport.

@history    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static HttpTransport getHttpTransport()
{
   if (httpTransport == null)
   {
         httpTransport = new NetHttpTransport();
   }
   return(httpTransport);
}
/*------------------------------------------------------------------------------

@name       getServiceAccountId - get service account email
                                                                              */
                                                                             /**
            Get service account email.

@return     service account email

@history    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static String getServiceAccountId()
{
   return(serviceAccountId);
}
/*------------------------------------------------------------------------------

@name       getStorage - get storage client
                                                                              */
                                                                             /**
            Get storage client.

@return     storage client.

@history    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static Storage getStorage()
   throws Exception
{
   if (storage == null)
   {
      try
      {
         kLOGGER.info("GoogleCloudStorageManagerBase.getStorage(): begin.");
      
                                       // initialize the transport            //
         HttpTransport httpTransport = getHttpTransport();
         kLOGGER.info("GoogleCloudStorageManagerBase.getStorage(): builder.");
      
                                       // build service account credential    //
         GoogleCredential credential =
            GoogleCredential
               .fromStream(getCredentialJSONPrivateKeyInputStream())
               .createScoped(Collections.singleton(kCLOUD_STORAGE_SCOPE));

                                       // set up global Storage instance      //
         storage = 
            new Storage.Builder(
               httpTransport, kJSON_FACTORY, credential)
               .setApplicationName(getApplicationName()).build();
      }
      catch(Exception e)
      {
         kLOGGER.severe(e.toString());
         throw e;
      }
   }
   
   return(storage);
}
/*------------------------------------------------------------------------------

@name       getReadableByteChannelFromGoogleCloudStorage - channel from cloud api
                                                                              */
                                                                             /**
            Create a new readable byte channel from the specified source url in 
            google cloud storage.

            Interpret the url which may contain an iWonder API range request 
            query parameter, create a url connection, and return the connection 
            input stream.
            
            A range request query parameter is of the form:
            
               range={rangeRequestValue},
               
               where {rangeRequestValue} conforms to the standard http range 
               header specification.
            
            For example, to fetch bytes 2039 through 4123 inclusive from an
            iWonder Bundle,
            
               "http://bundles.iwonderbundle.com/Bundle.html/?range=2039-4123"
               
@return     if asynchronous, null; otherwise HttpConnectionWrapper or Exception

@param      url                     source url without any range request param
@param      rsrcName                resource name
@param      connectTimeoutMsec      connection timeout value
@param      readTimeoutMsec         read timeout value
@param      rangeRequest            range request

@history    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public ReadableByteChannel getReadableByteChannelFromGoogleCloudStorage(
   String   targetURL,
   String   oemName,
   long[]   rangeReq)
   throws   Exception
{
   String[] tokens         = targetURL.split("/", 4);
   String   bucketName     = tokens[2];
   String   objectName     = tokens[3];
   Storage  storage        = getStorage();
   Storage.Objects.Get get = storage.objects().get(bucketName, objectName);
   
                                       // downloading data                    //
   java.io.FileOutputStream out = 
      new java.io.FileOutputStream(
         new File(objectName.substring(objectName.lastIndexOf('/') + 1)));
   
                                       // since not in AppEngine, download    //
                                       // the whole thing in one request, if  //
                                       // possible                            //
   get.getMediaHttpDownloader().setDirectDownloadEnabled(true);
   get.executeMediaAndDownloadTo(out);
   
   out = out;
   /*
   String[]    tokens      = targetURL.split("/", 4);
   String      bucketName  = tokens[2];
   String      objectName  = tokens[3];
   GcsFilename gcsFilename = new GcsFilename(bucketName, objectName);
   long        offset      = rangeReq[0];
   
   if (kLOGGER.willLog(kLOGGER.kLOG_LEVEL_DEBUG))
   {
      kLOGGER.logInfo(
         "GoogleCloudStorage.getInputStreamFromGoogleCloudStorage(): "
       + "getting read channel for bucket=" + bucketName + ", object=" + objectName);
   }

   GcsInputChannel readChannel = 
      newGCSServiceInstance().openPrefetchingReadChannel(
         gcsFilename, offset, kBUFFER_SIZE);
      
   if (kLOGGER.willLog(kLOGGER.kLOG_LEVEL_DEBUG))
   {
      kLOGGER.logInfo(
         "GoogleCloudStorage.getInputStreamFromGoogleCloudStorage(): "
       + "got read channel for bucket=" + bucketName + ", object=" + objectName);
   }
   */
   return(null);
}
/*------------------------------------------------------------------------------

@name       fileLength - check whether target file exists
                                                                              */
                                                                             /**
            Check whether target file exists. If length >= 0, test includes
            whether target file length is as specified.

@return     true iff target file exists

@param      targetURL         target url
@param      oemName           oem
@param      length            target file length

@history    Sun Nov 22, 2015 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public List<StorageObject> listBucket(
   String  bucketName)
   throws  Exception
{
   kLOGGER.info(
      "GoogleCloudStorageManagerBase.listBucket(): "
    + "begin for bucket=" + bucketName);

    Storage.Objects.List listRequest = getStorage().objects().list(bucketName);
    List<StorageObject>  results     = new ArrayList<>();
    Objects              objects;

    do
    {
                                       // iterate through each page of        //
                                       // results and add them to list        //
      objects = listRequest.execute();
                                       // add the items in this page of       //
                                       // results to the list                 //
      results.addAll(objects.getItems());

                                       // get any next page, in the next      //
                                       // iteration of this loop              //
      listRequest.setPageToken(objects.getNextPageToken());
    } while (null != objects.getNextPageToken());

    return results;
}
/*------------------------------------------------------------------------------

@name       sharedInstance - get shared instance
                                                                              */
                                                                             /**
            Get shared instance.

@return     shared instance.

@history    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static GoogleCloudStorageManagerBase sharedInstance()
{
   if (sharedInstance == null)
   {
      sharedInstance = new GoogleCloudStorageManagerBase();
   }
   
   return(sharedInstance);
}
/*------------------------------------------------------------------------------

@name       writeFile - write file to google cloud storage
                                                                              */
                                                                             /**
            Write file to google cloud storage
               
            Note that the upload sometimes fails with a SocketTimeoutException
            which has been reported numerous times across a number of forums
            without any good explanation or workaround found. As a consequence,
            until a better solution is found, this implementation retries a 
            number of times in the event this error is encountered.
               
@return     void

@param      srcFilePath    source filepath
@param      targetURL      destination url

@history    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void writeFile(
   String                    srcFilePath,
   String                    targetURL,
   String                    requestToken,
   BiConsumer<Object,Object> callback)
   throws                    Exception
{
   kLOGGER.info(
      "GoogleCloudStorageManagerBase.writeFile(): begin for srcFilePath="
         + srcFilePath);
    
   File srcFile = new File(srcFilePath);
   if (!srcFile.exists())
   {
      throw new FileNotFoundException(srcFilePath);
   }
   
   writeFile(
      new FileInputStream(srcFile), srcFile.length(), targetURL, 0,
      requestToken, callback);
}
/*------------------------------------------------------------------------------

@name       writeFile - write bytes to google cloud storage
                                                                              */
                                                                             /**
            Write bytes to google cloud storage

            Note that the upload sometimes fails with a SocketTimeoutException
            which has been reported numerous times across a number of forums
            without any good explanation or workaround found. As a consequence,
            until a better solution is found, this implementation retries a
            number of times in the event this error is encountered.

@return     void

@param      srcFilePath    source filepath
@param      targetURL      destination url

@history    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void writeFile(
   byte[] bytes,
   String targetURL)
   throws Exception
{
   kLOGGER.info(
      "GoogleCloudStorageManagerBase.writeFile(): begin for bytes.length="
         + bytes.length);

   writeFile(
      new ByteArrayInputStream(bytes), bytes.length, targetURL, 0, null, null);
}
/*------------------------------------------------------------------------------

@name       writeFile - write file to google cloud storage
                                                                              */
                                                                             /**
            Write file to google cloud storage
               
            Note that the upload sometimes fails with a SocketTimeoutException
            which has been reported numerous times across a number of forums
            without any good explanation or workaround found. As a consequence,
            until a better solution is found, this implementation retries a 
            number of times in the event this error is encountered.
               
@return     void

@param      srcFilePath    source filepath
@param      targetURL      destination url

@history    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void writeFile(
   InputStream               srcIn,
   long                      srcLength,
   String                    targetURL,
   int                       attempt,
   Object                    requestToken,
   BiConsumer<Object,Object> callback)
   throws                    Exception
{
   kLOGGER.info("GoogleCloudStorageManagerBase.writeFile(): attempt " + attempt);
    
   try
   {
      writeFile(srcIn, srcLength, targetURL, requestToken, callback);
   }
   catch(SocketTimeoutException e)
   {
      kLOGGER.warning(
         "GoogleCloudStorageManagerBase.writeFile(): timeout encountered");
      
      if (attempt >= kWRITE_MAX_RETRIES)
      {
         throw e;
      }
      
      writeFile(srcIn, srcLength, targetURL, ++attempt, requestToken, callback);
   }
}
/*------------------------------------------------------------------------------

@name       writeFile - write file to google cloud storage
                                                                              */
                                                                             /**
            Write file to google cloud storage. If input stream length is < 0,
            upload is streamed using chunked encoding and no total upload length
            is sent to the server.
            
            Note that the upload sometimes fails with a SocketTimeoutException
            which has been reported numerous times across a number of forums
            without any good explanation or workaround found. As a consequence,
            until a better solution is found, this implementation retries a 
            number of times in the event this error is encountered.
               
@return     void

@param      srcFilePath    source filepath
@param      targetURL      destination url of the form,
                           'gs://bucketName/objectPathname'

@history    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void writeFile(
   InputStream               inStream,
   long                      inLength,
   String                    targetURL,
   Object                    requestToken,
   BiConsumer<Object,Object> callback)
   throws                    Exception
{
   String[] tokens     = targetURL.split("/", 4);
   String   bucketName = tokens[2];
   String   objectPath = tokens[3];
   Storage  storage    = getStorage();
   
                                       // indicate uploading content          //
   String mimeType =
      kMIME_TYPE_MAP.get(objectPath.substring(objectPath.lastIndexOf(".") + 1));

   InputStreamContent mediaContent = new InputStreamContent(mimeType, inStream);

   if (inLength > 0)
   {
                                       // knowing the stream length allows    //
                                       // server-side optimization, and       //
                                       // client-side progress reporting with //
                                       // a MediaHttpUploaderProgressListener //
      mediaContent.setLength(inLength);
   }
                                       // permission is public read           //
   List<ObjectAccessControl> acl = Lists.newArrayList();
   acl.add(new ObjectAccessControl().setEntity("allUsers").setRole("READER"));

   StorageObject objectMetadata = 
      new StorageObject().setName(objectPath).setAcl(acl);
   
   if (false && targetURL.toLowerCase().endsWith(".html"))
   {
                                       // if we don't want inline javascript  //
                                       // being cached, we need to no-cache   //
                                       // the entire file                     //
      String cacheControl = "private, max-age=0, no-cache";
      objectMetadata = objectMetadata.setCacheControl(cacheControl);
   }

   Storage.Objects.Insert insert = 
      storage.objects().insert(bucketName, objectMetadata, mediaContent);
   
   MediaHttpUploader mediaHttpUploader = insert.getMediaHttpUploader();
   
                                       // for small files, directUploadEnabled//
                                       // true to reduce the number of HTTP   //
                                       // requests made to the server         //
                                       // (150708 LBM -assigned false to see  //
                                       // whether that avoids response        //
                                       // timeout observed sometimes)         //
   if (false/*mediaContent.getLength() > 0 
         && mediaContent.getLength() <= MediaHttpUploader.MINIMUM_CHUNK_SIZE * 3*/) 
   {
      mediaHttpUploader.setDirectUploadEnabled(true);
   }
   else
   {
      mediaHttpUploader.setChunkSize(MediaHttpUploader.MINIMUM_CHUNK_SIZE);
   }
   if (callback != null)
   {
      mediaHttpUploader.setProgressListener(
         new MediaHttpUploaderProgressHandler(
            inLength >= 0, requestToken, callback));
   }
                                       // upload the file                     //
   insert.execute();
}
/*==============================================================================

name:       MediaHttpUploaderProgressHandler - upload progress handler

purpose:    Upload progress handler.

history:    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

notes:

==============================================================================*/
protected class MediaHttpUploaderProgressHandler 
   implements MediaHttpUploaderProgressListener 
{
                                       // class constants ------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // whether upload length is known      //
protected boolean                   bLengthKnown;
                                       // request token                       //
protected Object                    requestToken;
                                       // requestor                           //
protected BiConsumer<Object,Object> callback;
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       MediaHttpUploaderProgressHandler - default constructor
                                                                              */
                                                                             /**
            Default constructor

@return     instance of CredentialBuilder iff successful

@history    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public MediaHttpUploaderProgressHandler(
   boolean                   bLengthKnown,
   Object                    requestToken,
   BiConsumer<Object,Object> callback)
{
   this.bLengthKnown = bLengthKnown;
   this.requestToken = requestToken;
   this.callback     = callback;
}
/*------------------------------------------------------------------------------

@name       progressChanged - progressChanged event handler
                                                                              */
                                                                             /**
            progressChanged event handler.

@return     void

@param      uploader    uploader instance

@history    Tue Apr 08, 2014 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void progressChanged(
   MediaHttpUploader uploader) 
   throws            IOException 
{
   double            progress = 0D;
   long              uploaded = 0;
   String            status   = "";
   
   switch (uploader.getUploadState()) 
   {
      case NOT_STARTED:
      {
          status = "Initializing";
          break;
      }
      case INITIATION_STARTED:
      {
          status = "Begin upload";
          break;
      }
      case INITIATION_COMPLETE:
      {
         status   = "Upload started";
         break;
      }
      case MEDIA_IN_PROGRESS:
      {
         uploaded = uploader.getNumBytesUploaded();
         status   = "Uploaded " + uploaded + " bytes";
         progress = 
            bLengthKnown 
               ? Math.min(uploader.getProgress(), 0.98D) : 0.50D;
         break;
      }
      case MEDIA_COMPLETE:
      {
         status   = "Upload finished";
         progress = 1.0D;
         break;
      }
   }
   
   Map<String,Object> report = new HashMap<String,Object>();
   report.put("status",   status);
   report.put("progress", (int)(progress * 100));
   
   callback.accept(report, requestToken);
}
}//====================================// MediaHttpUploaderProgressHandler ---//
}//====================================// end GoogleCloudStorageManagerBase --//

