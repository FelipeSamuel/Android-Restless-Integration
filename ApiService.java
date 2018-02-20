package br.com.awaketecnologia.dipaolo.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.awaketecnologia.dipaolo.data.model.Condicao;
import br.com.awaketecnologia.dipaolo.data.model.Model;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Criado por Felipe Samuel em 09/02/2018.
 */

public class ApiService<T> {

    private String urlBase;
    private String endPoint;

    private List<Condicao> condition;

    private Exception exception;

    private OkHttpClient clienteHttp;
    private Request request;
    private Response response;

    private final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();

    private int codigo;
    private String retorno;

    private Class classe;

    public ApiService(Class classe){
        this.classe = classe;
    }


    /**
     * Endpoints
     */

    public T add(T t) {
        try {
            RequestBody body = RequestBody.create(TYPE_JSON, gson.toJson(t));
            Request request = new Request.Builder()
                    .url(urlBase + endPoint)
                    .post(body)
                    .build();

            Response response = clienteHttp.newCall(request).execute();
            retorno = response.body().string().toString();
            return (T) gson.fromJson(retorno, this.classe);
        } catch (IOException e) {
            Logger.e(e.getCause(), e.getMessage());
            this.exception = e;
        }
        return null;
    }

    public T update(T t) {
        try {
            RequestBody body = RequestBody.create(TYPE_JSON, gson.toJson(t));
            Request request = new Request.Builder()
                    .url(urlBase + endPoint)
                    .put(body)
                    .build();

            Response response = clienteHttp.newCall(request).execute();
            retorno = response.body().string();
            return  (T) gson.fromJson(retorno, this.classe);
        } catch (IOException e) {
            Logger.e(e.getCause(), e.getMessage());
            this.exception = e;
        }
        return null;
    }

    public Model<T> getAll() {
        try {
            String url;
            if (this.condition == null || this.condition.size() == 0) {
                url = this.urlBase + this.endPoint;
            } else {
                url = this.urlBase + endPoint + filtro();
            }

            request = new Request.Builder()
                    .url(url)
                    .build();

            response = clienteHttp.newCall(request).execute();

            retorno = response.body().string().toString();
            codigo = response.code();

            Model<T> model = (Model<T>) gson.fromJson(retorno, new Model<T>().getClasse());

            return model;

        } catch (Exception ex) {
            this.exception = ex;
            Logger.e(ex.getCause(), ex.getMessage());
        }

        return null;
    }

    public T get(int id) {
        try {
            request = new Request.Builder()
                    .url(this.urlBase + endPoint + "/" + id)
                    .build();

            response = clienteHttp.newCall(request).execute();

            retorno = response.body().string();
            codigo = response.code();

            if (codigo == 200) {
                return (T) gson.fromJson(retorno,this.classe);
            }

        } catch (Exception ex) {
            this.exception = ex;
            Logger.e(ex.getCause(), ex.getMessage());
        }
        return null;
    }

    public boolean delete(int id) {
        try {
            request = new Request.Builder()
                    .url(this.urlBase + endPoint + "/" + id)
                    .delete()
                    .build();

            response = clienteHttp.newCall(request).execute();
            codigo = response.code();

            if (codigo == 204) {
                return true;
            }

        } catch (Exception ex) {
            this.exception = ex;
            Logger.e(ex.getCause(), ex.getMessage());
        }
        return false;
    }

    /**
     * Construtores
     */

    public ApiService<T> condition(List<Condicao> condition) {
        this.condition = condition;
        return this;
    }

    public ApiService<T> build() {
        clienteHttp = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        return this;
    }

    public ApiService<T> baseUrl(String baseUrl) {
        this.urlBase = baseUrl;
        return this;
    }

    public ApiService<T> endPoint(String endpoint) {
        this.endPoint = endpoint;
        return this;
    }

    /**
     * Util
     */

    private String filtro() {
        return "?q={\"filters\":" + this.gson.toJson(this.condition) + "}";
    }

    public Exception getErro() {
        Logger.e(this.exception.getCause(), this.exception.getMessage());
        return this.exception;
    }

}
