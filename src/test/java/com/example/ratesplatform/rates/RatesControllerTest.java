package com.example.ratesplatform.rates;

import com.example.ratesplatform.rates.dto.request.CreateRateRequestDto;
import com.example.ratesplatform.rates.dto.response.ApplicableRateResponseDto;
import com.example.ratesplatform.rates.dto.response.CreateRateResponseDto;
import com.example.ratesplatform.shared.common.dtos.SuccessfulResponseDto;
import com.example.ratesplatform.shared.externalservices.currencyservice.ICurrencyService;
import com.example.ratesplatform.shared.externalservices.currencyservice.dto.Currency;
import com.example.ratesplatform.shared.externalservices.h2db.model.Rate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static com.example.ratesplatform.shared.common.utils.Constants.RATE_BASE_CONTROLLER_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class RatesControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    ICurrencyService currencyService;

    // Test CREATE rate
    @Test
    void givenRateData_whenCreateRate_thenCreateNewRate() {
        final CreateRateRequestDto request = CreateRateRequestDto.builder()
                .brandId(1)
                .productId(1)
                .startDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 12, 31))
                .price(2000)
                .currencyCode("USD")
                .build();

        webTestClient
                .post()
                .uri(RATE_BASE_CONTROLLER_PATH)
                .body(Mono.just(request), CreateRateRequestDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<SuccessfulResponseDto<CreateRateResponseDto>>() {
                })
                .consumeWith(response -> {
                    final SuccessfulResponseDto<CreateRateResponseDto> responseBody = response.getResponseBody();
                    assertNotNull(responseBody);

                    final CreateRateResponseDto createdRate = responseBody.data();
                    assertNotNull(createdRate);
                    assertEquals(request.brandId(), createdRate.brandId());
                    assertEquals(request.productId(), createdRate.productId());
                    assertEquals(request.startDate(), createdRate.startDate());
                    assertEquals(request.endDate(), createdRate.endDate());
                    assertEquals(request.price(), createdRate.price());
                    assertEquals(request.currencyCode(), createdRate.currencyCode());
                });
    }

    // Test GET rate BY ID
    @Test
    void givenRateId_whenGetRateById_thenRetrieveRateData() {

        webTestClient
                .get()
                .uri(RATE_BASE_CONTROLLER_PATH + "/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<SuccessfulResponseDto<Rate>>() {
                })
                .consumeWith(response -> {
                    final SuccessfulResponseDto<Rate> responseBody = response.getResponseBody();
                    assertNotNull(responseBody);

                    final Rate rateData = responseBody.data();
                    assertNotNull(rateData);
                    assertEquals(1, rateData.getProductId());
                    assertEquals(1, rateData.getBrandId());
                    assertEquals(LocalDate.of(2022, 5, 31), rateData.getEndDate());
                    assertEquals(LocalDate.of(2022, 1, 1), rateData.getStartDate());
                    assertEquals(1550, rateData.getPrice());
                    assertEquals("EUR", rateData.getCurrencyCode());
                });
    }

    // Test GET APPLICABLE rate (GET rate by date, productId and brandId)
    @Test
    void givenDateAndProductIdAndBrandId_whenGetRateApplicable_thenRetrieveRateData() {

        // Mock currency service
        doReturn(
                Mono.just(Currency.builder()
                        .decimals(2)
                        .symbol("$")
                        .code("USD")
                        .build()))
                .when(currencyService)
                .findCurrencyByCurrencyCode(any());

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(RATE_BASE_CONTROLLER_PATH + "/applicable")
                        .queryParam("brandId", 1)
                        .queryParam("productId", 1)
                        .queryParam("date", LocalDate.of(2022, 7, 1))
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<SuccessfulResponseDto<ApplicableRateResponseDto>>() {
                })
                .consumeWith(response -> {
                    final SuccessfulResponseDto<ApplicableRateResponseDto> responseBody = response.getResponseBody();
                    assertNotNull(responseBody);

                    final ApplicableRateResponseDto rateData = responseBody.data();
                    assertNotNull(rateData);
                    assertEquals(1, rateData.productId());
                    assertEquals(1, rateData.brandId());
                    assertEquals(LocalDate.of(2022, 12, 31), rateData.endDate());
                    assertEquals(LocalDate.of(2022, 6, 1), rateData.startDate());
                    assertEquals(18.5, rateData.price());    // formatted price
                    assertEquals("$", rateData.symbol());    // symbol
                    assertEquals("USD", rateData.currencyCode());
                });
    }

    // Test MODIFY rate PRICE
    @Test
    void givenRateIdAndNewPrice_whenModifyRatePrice_thenPatchRatePrice() {

        webTestClient
                .patch()
                .uri(uriBuilder -> uriBuilder
                        .path(RATE_BASE_CONTROLLER_PATH + "/3/price")
                        .queryParam("price", 3050)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<SuccessfulResponseDto<Integer>>() {
                })
                .consumeWith(response -> {
                    final SuccessfulResponseDto<Integer> responseBody = response.getResponseBody();
                    assertNotNull(responseBody);

                    final Integer rowsUpdated = responseBody.data();
                    assertNotNull(rowsUpdated);
                    assertEquals(1, rowsUpdated);
                });
    }

    // Test DELETE rate
    @Test
    void givenRateId_whenDeleteRate_thenDeleteRate() {

        webTestClient
                .delete()
                .uri(RATE_BASE_CONTROLLER_PATH + "/4")
                .exchange()
                .expectStatus().isNoContent();
    }
}
