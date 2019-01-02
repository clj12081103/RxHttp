package com.cai.rxhttplib;

import android.util.Log;
import android.webkit.MimeTypeMap;
import com.cai.rxhttplib.config.ConfigInfo;
import com.cai.rxhttplib.interfaces.HttpMethod;
import com.cai.rxhttplib.retrofit.ApiService;
import com.cai.rxhttplib.retrofit.RetrofitClient;
import com.cai.rxhttplib.retrofit.UploadFileRequestBody;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class RetrofitHelper {

    public static final String UPLOAD_BINARY_KEY = "uploadBinary666";


    public static <T> Observable<ResponseBody> getResponseObservable(ConfigInfo<T> info) {
        ApiService service = RetrofitClient.getInstance().getApiService();
        Observable<ResponseBody> observable = null;
        switch (info.method) {
            case HttpMethod.GET:
                if (info.download) {
                    observable = service.download(info.url, info.params, info.headerParams);
                } else {
                    observable = service.get(info.url, info.params, info.headerParams);
                }
                break;
            case HttpMethod.POST:
                if (info.uploadMultipart) {
                    Map<String, RequestBody> params = RetrofitHelper.buildMultipartParams(info);
                    Map<String, RequestBody> files = RetrofitHelper.buildMyltipartFiles(info);
                    observable = service.uploadMultipart(info.url, params, files, info.headerParams);
                } else if (info.isParamsAsJson) {
//                    RequestBody body = RetrofitHelper.buidlJsonRequestBody(info);
//                    info.headerParams.put(com.cai.rxhttplib.utils.HttpHeaders.HEAD_KEY_CONTENT_TYPE, "application/json");
//                    observable = service.jsonPost(info.url, body, info.headerParams);
                } else if (info.uploadBinary) {
                    RequestBody body = RetrofitHelper.buildBinaryRequestBody(info);
                    observable = service.uploadRawByPost(info.url, body, info.headerParams);
                } else {
                    observable = service.post(info.url, info.params);
                }

                break;
        }
        return observable;
    }

    private static <T> RequestBody buildBinaryRequestBody(ConfigInfo<T> info) {
        String value = info.files.get(UPLOAD_BINARY_KEY);
        File file = new File(value);
        String type = getMimeType(value);
        Log.d("type", "mimetype:" + type);
        UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(file, type, info, 0);
        return fileRequestBody;
    }


    public static <T> Map<String, RequestBody> buildMultipartParams(ConfigInfo<T> info) {
        //将key-value放进body中:
        Map<String, RequestBody> paramsMap = new HashMap<>();
        if (info.params != null && info.params.size() > 0) {
            Map<String, String> params = info.params;
            int count = params.size();
            if (count > 0) {
                Set<Map.Entry<String, String>> set = params.entrySet();
                for (Map.Entry<String, String> entry : set) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    String type = "text/plain";
                    RequestBody fileRequestBody = RequestBody.create(MediaType.parse(type), value);
                    paramsMap.put(key, fileRequestBody);
                }
            }
        }
        return paramsMap;
    }

    public static <T> Map<String, RequestBody> buildMyltipartFiles(ConfigInfo<T> info) {
        Map<String, RequestBody> filesMap = new HashMap<>();
//
//        //将文件放进map中
//        if (info.getFiles() != null && info.getFiles().size() > 0) {
//            Map<String, String> files = info.getFiles();
//            int count = files.size();
//            if (count > 0) {
//                Set<Map.Entry<String, String>> set = files.entrySet();
//                int i = 0;
//                for (Map.Entry<String, String> entry : set) {
//                    String key = entry.getKey();
//                    String value = entry.getValue();
//                    File file = new File(value);
//                    String type = getMimeType(value);
//                    Log.d("type", "mimetype:" + type);
//                    UploadFileRequestBody fileBody = new UploadFileRequestBody(file, type, info, i);
//                    //RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
//                    filesMap.put(key + "\"; filename=\"" + file.getName(), fileBody);
//                    i++;
//                }
//            }
//        }
        return filesMap;
    }

//    static <T> RequestBody buidlJsonRequestBody(ConfigInfo<T> info)  {
//        String jsonStr = ParamsProcessor.getFinalJsonStr(info);
//        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), jsonStr);

//    }

    private static String getMimeType(String fileUrl) {


        String suffix = getSuffix(new File(fileUrl));
        if (suffix == null) {
            return "file";
        }
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
        if (type != null && !type.isEmpty()) {
            return type;
        }
        return "file";
    }

    private static String getSuffix(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return null;
        }
        String fileName = file.getName();
        if (fileName.equals("") || fileName.endsWith(".")) {
            return null;
        }
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            return fileName.substring(index + 1).toLowerCase(Locale.US);
        } else {
            return null;
        }
    }
}
