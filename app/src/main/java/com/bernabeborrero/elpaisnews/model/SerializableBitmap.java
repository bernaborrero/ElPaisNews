package com.bernabeborrero.elpaisnews.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by berna on 14/02/15.
 */
public class SerializableBitmap implements Serializable {

    transient Bitmap bitmap;

    public SerializableBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        if(bitmap != null){
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            boolean success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
            if(success){
                oos.writeObject(byteStream.toByteArray());
            }
        }
    }

    private void readObject(ObjectInputStream ois) {
        try {
            ois.defaultReadObject();
            byte[] image = (byte[]) ois.readObject();
            if(image != null && image.length > 0){
                bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            }
        } catch (IOException | ClassNotFoundException e) {
            Log.e("SerializableBitmap", "Error decoding image");
            bitmap = null;
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

}
