package com.upday.News;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.upday.News.entity.Keyword;
import com.upday.News.repository.IKeywordRepository;
import com.upday.News.web.dto.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class KeywordRestTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private IKeywordRepository keywordRepository;



    @Test
    public void find_keywordId_OK() throws Exception {

        // given
        Keyword keyword = new Keyword(1L, "cars");

        // when
        String expected = om.writeValueAsString(Response.successWithData(keyword));

        when(keywordRepository.findById(1L)).thenReturn(Optional.of(keyword));

        // then
        ResponseEntity<String> response = restTemplate.getForEntity("/keywords/1", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());

        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(keywordRepository, times(1)).findById(1L);

    }


    @Test
    public void find_allKeyword_OK() throws Exception {

        // given
        List<Keyword> keywords = Arrays.asList(
                new Keyword(1L, "cars"),
                new Keyword(2L, "computers"));

        PageImpl<Keyword> keywordPage = new PageImpl<>(keywords, PageRequest.of(0, 10), 6);

        PageRequest pageRequest = PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));

        // when
        when(keywordRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(keywords, PageRequest.of(0, 10), 6));


        // then
        String expected = om.writeValueAsString(Response.successWithData(keywordPage));

        ResponseEntity<String> response = restTemplate.getForEntity("/keywords", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(keywordRepository, times(1)).findAll(pageRequest);
    }

    @Test
    public void find_keywordName_OK() throws Exception {

        // given
        List<Keyword> keywords = Collections.singletonList(new Keyword(1L, "cars"));

        // when
        when(keywordRepository.findByKeywordIgnoreCaseContaining("%cars%")).thenReturn(Optional.of(keywords));

        // then
        String expected = om.writeValueAsString(Response.successWithData(keywords));

        ResponseEntity<String> response = restTemplate.getForEntity("/keywords?keyword=cars", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());

        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(keywordRepository, times(1)).findByKeywordIgnoreCaseContaining("%cars%");

    }


    @Test
    public void save_keyword_OK() throws Exception {

        // given
        Keyword newKeyword = new Keyword(1L, "cars");

        // when
        when(keywordRepository.save(any(Keyword.class))).thenReturn(newKeyword);

        // then
        ResponseEntity<String> response = restTemplate.postForEntity("/keywords", newKeyword, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(keywordRepository, times(1)).save(any(Keyword.class));

    }


}
