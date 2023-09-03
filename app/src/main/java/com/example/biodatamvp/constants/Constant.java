package com.example.biodatamvp.constants;


import android.graphics.Bitmap;
import android.util.Base64;


import java.io.ByteArrayOutputStream;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Constant {
    private static final String BASE_URL = "http://192.168.43.219:8080";
    public static final String CONNECTION = BASE_URL + "/api/v1/biodata/";

    public static final String IMAGES_BIODATA = CONNECTION + "images/";

    public static int permission_camera_code = 786;
    public static byte[] calcHmacSha256(byte[] secretKey, byte[] message) {
        byte[] hmacSha256 = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "HmacSHA256");
            mac.init(secretKeySpec);
            hmacSha256 = mac.doFinal(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate hmac-sha256", e);
        }
        return hmacSha256;
    }







}
