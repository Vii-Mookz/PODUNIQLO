package com.hitachi_tstv.mist.it.pod_uniqlo;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Created by tunyaporn on 10/24/2017.
 */

public class UploadImageUtils {




    private void checkExternalMedia(){
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
    }

    public static String uploadFile(String fileNameInServer, String urlServer, Bitmap bitmap) {
        try {

            // configurable parameters
            // 1. upload url
            // 2. file name
            // 3. uploaded file path
            // 4. compress
            // 5. result

            HttpURLConnection connection = null;
            DataOutputStream outputStream = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            URL url = new URL(urlServer);

            Log.d("VAL-Tag-UIU", "URL ===> " + url);
            connection = (HttpURLConnection) url.openConnection();

            // Allow Inputs & Outputs
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            // Enable POST method
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream
                    .writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
                            + fileNameInServer + "\"" + lineEnd);
            outputStream.writeBytes(lineEnd);

            Log.d("VAL-Tag-UIU", "Name ==> " + fileNameInServer + "\"" + lineEnd);


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (bitmap.getWidth() > bitmap.getHeight()) {
                bitmap = Bitmap.createScaledBitmap(bitmap, 1600, 1200, false);
            } else {
                bitmap = Bitmap.createScaledBitmap(bitmap, 1200, 1600, false);

            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
            byte[] data = baos.toByteArray();

            data[13] = 00000001;
            data[14] = 00000001;
            data[15] = (byte) 244;
            data[16] =  00000001;




//            byte[] slice = Arrays.copyOf(data,  10000);
//            String encodedImage = Base64.encodeToString(data, Base64.DEFAULT);
//            StringBuilder stringBuilder1 = new StringBuilder();
//            stringBuilder1 = stringBuilder1.append(Base64.encodeToString(data, Base64.DEFAULT));
//
//
//
//
//
//            Log.d("TAG:", "Data ==> " + stringBuilder1);


//            stringBuilder.append("[");

//            int count = 0;
//            int cnt = 0;
//            String result = "[";
            StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[");
            for (byte b : data){
//                Log.d("TAG:", "Count ==> " + count%1000);
//                Log.d("TAG:", "Count ==> " + ((count%1000) == 0));
//                String encodedImage = Base64.encodeToString(data, Base64.DEFAULT);
//                result += encodedImage.toString();

//                if ((count % 5000) == 0) {


//                    cnt++;
//                    stringBuilder.append(";");

//                    Log.d("TAG:", "Data ==> " + stringBuilder.toString());
//                    stringBuilder = new StringBuilder();
//                }
//                if (count % 200 == 0) {
//                    stringBuilder.append(":");
//                    Log.d("TAG:", "Data ==> " + stringBuilder.toString());
//                    stringBuilder = new StringBuilder();
//                }


                stringBuilder.append(b);
                    stringBuilder.append(" ");
//                count++;

            }
//            result += "]";
            stringBuilder.append("]");



//            Log.d("TAG:", "Result ==> " + result);

//            Log.d("TAG:", "Data ==> " + stringBuilder.charAt(stringBuilder.length()-1));

//            Log.d("TAG:", "Data ==> " + stringBuilder.toString());
//            int max = 1000;
//            for (int i = 0;i < stringBuilder.length() / max;i++) {
//                int start = i * max;
//                int end = (i + 1) * max;
//                end = end > stringBuilder.length() ? stringBuilder.length() : end;
//                Log.d("TAG:", "Data ==> " + stringBuilder.substring(start,end) + ";");
//            }



//            Log.d("TAG:", "Data ==> " + stringBuilder.toString());

            outputStream.write(data);

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens
                    + lineEnd);


            // Convert response message in inputstream to string.
            StringBuilder sb = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            outputStream.flush();
            outputStream.close();

            Log.d("VAL-Tag-UIU", "Return ==> " + sb.toString());

            return sb.toString();

        } catch (Exception e) {
            return null;
        }
    }

    public static String getRandomFileName() {
        String _df = android.text.format.DateFormat.format("MMddyyyyhhmmss",
                new java.util.Date()).toString();
        Random r = new Random();
        int random = Math.abs(r.nextInt() % 100);
        return String.format("%d%s.jpg", random, _df);
    }
}