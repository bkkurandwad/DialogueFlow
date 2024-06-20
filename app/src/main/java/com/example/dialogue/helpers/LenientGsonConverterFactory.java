package com.example.dialogue.helpers;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class LenientGsonConverterFactory extends Converter.Factory {
    private final Gson gson;

    private LenientGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    public static LenientGsonConverterFactory create() {
        return create(new Gson());
    }

    public static LenientGsonConverterFactory create(Gson gson) {
        return new LenientGsonConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new LenientGsonResponseBodyConverter<>(gson, adapter);
    }

    private static class LenientGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        LenientGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            JsonReader jsonReader = gson.newJsonReader(value.charStream());
            jsonReader.setLenient(true); // Enable lenient mode
            try {
                return adapter.read(jsonReader);
            } finally {
                value.close();
            }
        }
    }
}
