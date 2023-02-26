package com.vladimir.vocabularyservice.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer<T> {
    public byte[] serialize(T obj) {
        try(
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                ObjectOutputStream o = new ObjectOutputStream(b)
        ) {
            o.writeObject(obj);
            return b.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public T deserialize(byte[] bytes) {
        try(
                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                ObjectInputStream o = new ObjectInputStream(inputStream)
        ) {
            return (T) o.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
