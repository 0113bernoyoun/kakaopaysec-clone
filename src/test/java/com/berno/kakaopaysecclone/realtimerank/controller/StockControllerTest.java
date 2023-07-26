package com.berno.kakaopaysecclone.realtimerank.controller;

import com.berno.kakaopaysecclone.common.BaseSpringBootTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("컨트롤러 테스트")
class StockControllerTest extends BaseSpringBootTest {


    private final static String DEFAULT_PATH = "/sec/realtime";

    @Test
    public void 전체_주식_조회_테스트() throws Exception {
        // given
        final String path = "/list";

        //then
        mockMvc.perform(
                        get(DEFAULT_PATH + path)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-all-list",
                        responseFields(
                                fieldWithPath("statusCode").type(NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(STRING).description("상태"),
                                fieldWithPath("data[].name").type(STRING).description("주식 이름"),
                                fieldWithPath("data[].code").type(STRING).description("주식 코드"),
                                fieldWithPath("data[].price").type(NUMBER).description("주식 가격")
                        )
                ));

    }

    @Test
    public void 증가한_주식_조회_테스트() throws Exception {
        // given
        final String path = "/up";
        final String startIdxValue = "0";
        final String offsetValue = "20";

        //then
        mockMvc.perform(
                        get(DEFAULT_PATH + path)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("startIdx", startIdxValue)
                                .param("offset", offsetValue)
                ).andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-up-list",
                        responseFields(
                                fieldWithPath("statusCode").type(NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(STRING).description("상태"),
                                fieldWithPath("data[].name").type(STRING).description("주식 이름"),
                                fieldWithPath("data[].code").type(STRING).description("주식 코드"),
                                fieldWithPath("data[].price").type(NUMBER).description("주식 가격"),
                                fieldWithPath("data[].rateOfChange").type(NUMBER).description("변화율")
                        )
                ));
    }

    @Test
    public void 하락한_주식_조회_테스트() throws Exception {
        // given
        final String path = "/down";
        final String startIdxValue = "0";
        final String offsetValue = "20";

        //then
        mockMvc.perform(
                        get(DEFAULT_PATH + path)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("startIdx", startIdxValue)
                                .param("offset", offsetValue)
                ).andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-down-list",
                        responseFields(
                                fieldWithPath("statusCode").type(NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(STRING).description("상태"),
                                fieldWithPath("data[].name").type(STRING).description("주식 이름"),
                                fieldWithPath("data[].code").type(STRING).description("주식 코드"),
                                fieldWithPath("data[].price").type(NUMBER).description("주식 가격"),
                                fieldWithPath("data[].rateOfChange").type(NUMBER).description("변화율")
                        )
                ));
    }

    @Test
    public void 데이터_저장_테스트() throws Exception {
        // given
        final String path = "/stocks";

        // then
        mockMvc.perform(
                        put(DEFAULT_PATH + path)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(print())
                .andDo(document("random-data-save",
                        responseFields(
                                fieldWithPath("statusCode").type(NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(STRING).description("상태"),
                                fieldWithPath("data[].name").type(STRING).description("주식 이름"),
                                fieldWithPath("data[].code").type(STRING).description("주식 코드"),
                                fieldWithPath("data[].price").type(NUMBER).description("주식 가격")
                        )
                ));
    }

}