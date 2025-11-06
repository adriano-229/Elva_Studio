package com.example.mycar.controller;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/mercado")
public class MercadoPagoController {

    @GetMapping
    public Preference mercado() throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken("APP_USR-6951650072980469-110520-e2eb4e76ddbde2368b413b1f0676555d-2968361325");

        PreferenceItemRequest itemRequest =
                PreferenceItemRequest.builder()
                        .id("12345")
                        .title("Games")
                        .description("PS5")
                        .pictureUrl("https://picture.com/PS5")
                        .categoryId("games")
                        .quantity(2)
                        .currencyId("BRL")
                        .unitPrice(new BigDecimal("4000"))
                        .build();
        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

        //urls de retorno
        PreferenceBackUrlsRequest backUrls =
                PreferenceBackUrlsRequest.builder()
                        .success("https://www.tu-sitio/success")
                        .pending("https://www.tu-sitio/pending")
                        .failure("https://www.tu-sitio/failure")
                        .build();
        // TODO colocar url reales

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .autoReturn("approved")
                .build();

        PreferenceClient client = new PreferenceClient();
        return client.create(preferenceRequest);
    }


}
