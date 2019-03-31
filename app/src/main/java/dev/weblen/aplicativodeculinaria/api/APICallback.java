package dev.weblen.aplicativodeculinaria.api;

public interface APICallback<T> {
    void onResponse(T result);

    void onCancel();
}
