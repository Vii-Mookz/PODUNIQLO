package com.hitachi_tstv.mist.it.pod_uniqlo;

import android.graphics.Bitmap;
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

public class UploadImageUtils1 {
    public static String uploadFile(String fileNameInServer, String urlServer, Bitmap bitmap,String truck_id) {
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

            URL url = new URL(urlServer+"?Truck_Id="+ truck_id);

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
            data[17] = (byte) 244;

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
//            return encodedImage;
        } catch (Exception e) {
            Log.d("TAG:", "EXXXXXX" + e + "Line: " + e.getStackTrace()[0].getLineNumber());
            e.printStackTrace();
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