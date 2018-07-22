package api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import okhttp3.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.List;
import java.util.UUID;

/**
 * Created by user on 7/20/18.
 */
public class HttpClient {
//    private static String ACCESS_TOKEN = "ce83c7853d73344ab6af0e6872fba1badfe2b6dd";
    private static  String ACCESS_TOKEN = "359c3b123ffc16f406e2cba1913565b570fea178";
    private static final String BASE_URL = "https://todoist.com/api/v7/sync";
    private static final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

    public static String createProjct(String projectName) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String temp_id = UUID.randomUUID().toString();
        RequestBody body = RequestBody.create(mediaType,String.format( "token=" + ACCESS_TOKEN  +
                "&sync_token=aLGJg_2qwBE_kE3j9_Gn6uoKQtvQeyjm7UEz_aVwF8KdriDxw7e_InFZK61h"  + "&resource_types=[\"projects\"]" +"&commands=[\n" +
                "        { \"type\": \"project_add\",\n" +
                "          \"temp_id\": \"%s\",\n" +
                "          \"uuid\": \"%s\",\n" +
                "          \"args\": { \"name\": \"%s\"} }\n" +
                "      ]",uuid,temp_id,projectName));
       return getID(postRequest(body));
    }

    public static boolean checkProject(String projectName) throws Exception {

        RequestBody body = RequestBody.create(mediaType, "token=" + ACCESS_TOKEN  +
                "&sync_token=*"  + "&resource_types=[\"projects\"]");

        return projectName.contains(postRequest(body));
    }

    public static void deleteProject(String id) throws Exception {
        RequestBody body = RequestBody.create(mediaType,String.format( "token=" + ACCESS_TOKEN  +
                "&sync_token=aLGJg_2qwBE_kE3j9_Gn6uoKQtvQeyjm7UEz_aVwF8KdriDxw7e_InFZK61h"  + "&resource_types=[\"projects\"]" +"&commands=[\n" +
                "        { \"type\": \"project_delete\",\n" +
                "          \"temp_id\": \"%s\",\n" +
                "          \"uuid\": \"%s\",\n" +
                "          \"args\": { \"ids\":[%s]}}\n" +
                "      ]",UUID.randomUUID(),UUID.randomUUID(),id));
        postRequest(body);
    }

    public static String addTask(String taskName) throws Exception {
        RequestBody body = RequestBody.create(mediaType,String.format( "token=" + ACCESS_TOKEN  +
                "&sync_token=aLGJg_2qwBE_kE3j9_Gn6uoKQtvQeyjm7UEz_aVwF8KdriDxw7e_InFZK61h"  + "&resource_types=[\"items\"]" +"&commands=[\n" +
                "        { \"type\": \"item_add\",\n" +
                "          \"temp_id\": \"%s\",\n" +
                "          \"uuid\": \"%s\",\n" +
                "          \"args\": { \"content\": \"%s\" } }\n" +
                "      ]",UUID.randomUUID(),UUID.randomUUID(),taskName));
       return getID(postRequest(body));
    }

    public static String addTask(String taskName,String projectID) throws Exception {
        RequestBody body = RequestBody.create(mediaType,String.format( "token=" + ACCESS_TOKEN  +
                "&sync_token=aLGJg_2qwBE_kE3j9_Gn6uoKQtvQeyjm7UEz_aVwF8KdriDxw7e_InFZK61h"  + "&resource_types=[\"items\"]" +"&commands=[\n" +
                "        { \"type\": \"item_add\",\n" +
                "          \"temp_id\": \"%s\",\n" +
                "          \"uuid\": \"%s\",\n" +
                "          \"args\": { \"content\": \"%s\", \"project_id\": %s } }\n" +
                "      ]",UUID.randomUUID(),UUID.randomUUID(),taskName,projectID));
        return getID(postRequest(body));
    }

    public static void deleteTask(String taskID) throws Exception {
        RequestBody body = RequestBody.create(mediaType,String.format( "token=" + ACCESS_TOKEN  +
                "&sync_token=aLGJg_2qwBE_kE3j9_Gn6uoKQtvQeyjm7UEz_aVwF8KdriDxw7e_InFZK61h"  + "&resource_types=[\"items\"]" +"&commands=[\n" +
                "        { \"type\": \"item_delete\",\n" +
                "          \"temp_id\": \"%s\",\n" +
                "          \"uuid\": \"%s\",\n" +
                "          \"args\": { \"ids\":[%s] } }\n" +
                "      ]",UUID.randomUUID(),UUID.randomUUID(),taskID));
        postRequest(body);
    }

    public static boolean checkTask(String taskName) throws Exception {
        RequestBody body = RequestBody.create(mediaType, "token=" + ACCESS_TOKEN +
                 "&resource_types=[\"items\"]");

        return postRequest(body).contains(taskName);
    }

    public static void uncompleteTask(String taskID) throws Exception {
        RequestBody body = RequestBody.create(mediaType,String.format("token=" + ACCESS_TOKEN  +
                "&commands=[{\"type\": \"item_uncomplete\", \"uuid\": \"%s\", \"args\": {\"ids\": [%s]}}]",UUID.randomUUID(),taskID));

        postRequest(body);
    }

    public static String getID(String json){
        Object obj = new JsonParser().parse(json);

        JsonObject jsonObject = (JsonObject) obj;
        return jsonObject.get("temp_id_mapping").toString().replaceAll("[{}]","").substring(39);
    }

    public static String postRequest(RequestBody requestBody) throws Exception {
        Response response;
        String responseJson;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(requestBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try {
            response = client.newCall(request).execute();
            responseJson = response.body().string();


        } catch (IOException e) {
            throw new ApiException(e.getMessage());
        }
        if(responseJson.contains("error")){
            throw new Exception("PostRequest failied:" + responseJson);
        }
        return responseJson;
    }
}
