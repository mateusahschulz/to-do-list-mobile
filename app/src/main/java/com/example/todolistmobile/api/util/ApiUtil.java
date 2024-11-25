package com.example.todolistmobile.api.util;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiUtil {

    public static <T> void executeCall(Call<T> call, ApiCallback<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                // Verifica se a resposta foi bem-sucedida
                if (response.isSuccessful()) {
                    // Se o corpo não for nulo, chama onSuccess
                    if (response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        // Para casos de sucesso sem corpo, como DELETE com sucesso (204)
                        callback.onSuccess(null); // Pode adaptar para uma resposta específica, se necessário
                    }
                } else {
                    // Trata casos de falha, como erro de validação (400), servidor (500), etc.
                    handleError(response, callback);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                // Se a requisição falhar por qualquer motivo (como falta de conexão), loga e notifica erro
                Log.e("API Failure", "Erro na requisição: " + t.getMessage());
                callback.onError(t.getMessage());
            }
        });
    }

    // Método para tratar erros de resposta da API
    private static <T> void handleError(Response<T> response, ApiCallback<T> callback) {
        int statusCode = response.code();

        if (statusCode == 401) {
            // Tratar caso de Unauthorized (login falhou, mas sem falha de rede)
            Log.e("API Error", "Unauthorized - Código 401");
            callback.onError("Unauthorized - Acesso negado");
        } else if (statusCode == 404) {
            // Caso o recurso não seja encontrado
            Log.e("API Error", "Recurso não encontrado - Código 404");
            callback.onError("Recurso não encontrado");
        } else if (statusCode == 500) {
            // Caso de erro no servidor
            Log.e("API Error", "Erro no servidor - Código 500");
            callback.onError("Erro no servidor");
        } else {
            // Para qualquer outro erro (400, 403, etc.)
            String errorMessage = response.message();
            Log.e("API Error", "Erro na resposta: " + errorMessage);
            callback.onError(errorMessage);
        }
    }
}


/*public class ApiUtil {

    public static <T> void executeCall(Call<T> call, ApiCallback<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMessage = response.message();
                    Log.e("API Error", "Erro na resposta: " + errorMessage);
                    callback.onError(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                String errorMessage = t.getMessage();
                Log.e("API Failure", "Erro na requisição: " + errorMessage);
                callback.onError(errorMessage);
            }
        });
    }
}*/
