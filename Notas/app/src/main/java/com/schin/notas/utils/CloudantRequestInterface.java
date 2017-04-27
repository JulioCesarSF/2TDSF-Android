package com.schin.notas.utils;

import com.schin.notas.entity.CloudantResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by logonrm on 27/04/2017.
 *
 * Como organização o retrofit organiza todas as rotas
 * da sua API dentro de uma interface
 *
 *
 */

public interface CloudantRequestInterface {

    @GET("_all_docs?include_docs=true")
    Call<CloudantResponse> getAllDocs();


}
