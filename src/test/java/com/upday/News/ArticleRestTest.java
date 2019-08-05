package com.upday.News;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.upday.News.entity.*;
import com.upday.News.repository.IArticleAuthorRepository;
import com.upday.News.repository.IArticleKeywordRepository;
import com.upday.News.repository.IArticleRepository;
import com.upday.News.utility.DateUtil;
import com.upday.News.web.dto.Response;
import com.upday.News.web.dto.request.AddArticleRequest;
import com.upday.News.web.dto.response.ArticleDto;
import com.upday.News.web.dto.response.ArticleSingleDto;
import com.upday.News.web.dto.response.AuthorDto;
import com.upday.News.web.dto.response.KeywordDto;
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
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ArticleRestTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private IArticleRepository articleRepository;
    @MockBean
    private IArticleAuthorRepository articleAuthorRepository;
    @MockBean
    private IArticleKeywordRepository articleKeywordRepository;


    @Test
    public void display_one_article_OK() throws Exception {

        // given
        Article article = new Article(1L, "news", new Date(), "short description", "text",
                Collections.singleton(new ArticleAuthor(new Article(1L), new Author(1L))),
                Collections.singleton(new ArticleKeyword(new Article(1L), new Keyword(1L))));

        ArticleAuthor articleAuthors = (ArticleAuthor) article.getAuthors().toArray()[0];
        ArticleKeyword articleKeyword = (ArticleKeyword) article.getKeywords().toArray()[0];

        ArticleSingleDto articleSingleDto = new ArticleSingleDto();
        articleSingleDto.setId(article.getId());
        articleSingleDto.setHeader(article.getHeader());
        articleSingleDto.setShortDescription(article.getShortDescription());
        articleSingleDto.setText(article.getText());
        articleSingleDto.setPublishDate(DateUtil.dateToString(article.getPublishDate()));
        articleSingleDto.setAuthors(Collections.singleton(new AuthorDto(articleAuthors.getAuthor().getId(), articleAuthors.getAuthor().getName())));
        articleSingleDto.setKeywords(Collections.singleton(new KeywordDto(articleKeyword.getKeyword().getId(), articleKeyword.getKeyword().getKeyword())));

        // when
        String expected = om.writeValueAsString(Response.successWithData(articleSingleDto));

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        // then
        ResponseEntity<String> response = restTemplate.getForEntity("/articles/1", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());

        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(articleRepository, times(1)).findById(1L);

    }


    @Test
    public void list_all_articles_OK() throws Exception {

        // given
        List<Article> articles = Arrays.asList(
                new Article(1L, "news", new Date(), "short description", "text",
                        Collections.singleton(new ArticleAuthor(new Article(1L), new Author(1L))),
                        Collections.singleton(new ArticleKeyword(new Article(1L), new Keyword(1L)))),
                new Article(2L, "news", new Date(), "short description", "text",
                        Collections.singleton(new ArticleAuthor(new Article(1L), new Author(1L))),
                        Collections.singleton(new ArticleKeyword(new Article(1L), new Keyword(1L)))));

        List<ArticleDto> collect = articles.stream().map(toArticleDto).collect(Collectors.toList());

        PageImpl<ArticleDto> articlePage = new PageImpl<>(collect, PageRequest.of(0, 10), 2);

        PageRequest pageRequest = PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));

        // when
        when(articleRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(articles, PageRequest.of(0, 10), 2));


        // then
        String expected = om.writeValueAsString(Response.successWithData(articlePage));

        ResponseEntity<String> response = restTemplate.getForEntity("/articles", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(articleRepository, times(1)).findAll(pageRequest);
    }

    @Test
    public void list_all_articles_for_a_given_author_OK() throws Exception {

        // given
        List<Article> articles = Arrays.asList(
                new Article(1L, "news", new Date(), "short description", "text",
                        Collections.singleton(new ArticleAuthor(new Article(1L), new Author(1L))),
                        Collections.singleton(new ArticleKeyword(new Article(1L), new Keyword(1L)))),
                new Article(2L, "news", new Date(), "short description", "text",
                        Collections.singleton(new ArticleAuthor(new Article(1L), new Author(2L))),
                        Collections.singleton(new ArticleKeyword(new Article(1L), new Keyword(1L)))));

        List<ArticleDto> collect = articles.stream().map(toArticleDto).collect(Collectors.toList());

        PageImpl<ArticleDto> articlePage = new PageImpl<>(collect, PageRequest.of(0, 10), 2);

        PageRequest pageRequest = PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));

        // when
        when(articleRepository.findAllByAuthors(pageRequest, new Long[]{1L, 2L}))
                .thenReturn(new PageImpl<>(articles, PageRequest.of(0, 10), 2));

        // then
        String expected = om.writeValueAsString(Response.successWithData(articlePage));

        ResponseEntity<String> response = restTemplate.getForEntity("/articles?authorsId=1,2", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());

        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(articleRepository, times(1)).findAllByAuthors(pageRequest, new Long[]{1L, 2L});

    }


    @Test
    public void save_article_OK() throws Exception {

        // given
        Article newArticle = new Article(1L, "news", new Date(), "short description", "text",
                Collections.singleton(new ArticleAuthor(new Article(1L), new Author(1L))),
                Collections.singleton(new ArticleKeyword(new Article(1L), new Keyword(1L))));

        ArticleAuthor articleAuthor = (ArticleAuthor) newArticle.getAuthors().toArray()[0];
        ArticleKeyword articleKeyword = (ArticleKeyword) newArticle.getKeywords().toArray()[0];

        AddArticleRequest addArticleRequest = new AddArticleRequest();
        addArticleRequest.setHeader(newArticle.getHeader());
        addArticleRequest.setShortDescription(newArticle.getShortDescription());
        addArticleRequest.setText(newArticle.getText());
        addArticleRequest.setPublishDate(DateUtil.dateToString(newArticle.getPublishDate()));
        addArticleRequest.setAuthors(Collections.singleton(articleAuthor.getAuthor().getId()));
        addArticleRequest.setKeywords(Collections.singleton(articleKeyword.getKeyword().getId()));

        Set<ArticleKeyword> articleKeywords = Collections.singleton(new ArticleKeyword(new ArticleKeywordPK(newArticle.getId(), 1L)));
        Set<ArticleAuthor> articleAuthors = Collections.singleton(new ArticleAuthor(new ArticleAuthorPK(newArticle.getId(), 1L)));
        // when
        when(articleRepository.save(any(Article.class))).thenReturn(newArticle);

        when(articleKeywordRepository.saveAll(any(Collection.class)))
                .thenReturn(articleKeywords);

        when(articleAuthorRepository.saveAll(any(Collection.class)))
                .thenReturn(articleAuthors);

        // then
        ResponseEntity<String> response = restTemplate.postForEntity("/articles", addArticleRequest, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(articleRepository, times(1)).save(any(Article.class));
        verify(articleKeywordRepository, times(1)).saveAll(any(Collection.class));
        verify(articleAuthorRepository, times(1)).saveAll(any(Collection.class));

    }

    @Test
    public void patch_Article_OK() {

        // given
        String patchInJson = "{\"header\": \"updated header\"}";

        // when
        when(articleRepository.findById(1L)).thenReturn(Optional.of(new Article(1L)));
        when(articleRepository.save(any(Article.class))).thenReturn(new Article());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(patchInJson, headers);

        // then
        ResponseEntity<String> response = restTemplate.exchange("/articles/1", HttpMethod.PUT, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(articleRepository, times(1)).findById(1L);
        verify(articleRepository, times(1)).save(any(Article.class));

    }

    @Test
    public void delete_article_OK() {

        Article article = new Article(1L);

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        doNothing().when(articleRepository).delete(article);

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange("/articles/1", HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(articleRepository, times(1)).findById(1L);
        verify(articleRepository, times(1)).delete(article);
    }

    @Test
    public void find_all_articles_for_a_specific_keyword_OK() throws Exception {

        // given
        List<Article> articles = Arrays.asList(
                new Article(1L, "news", new Date(), "short description", "text",
                        Collections.singleton(new ArticleAuthor(new Article(1L), new Author(1L))),
                        Collections.singleton(new ArticleKeyword(new Article(1L), new Keyword(1L)))),
                new Article(2L, "news", new Date(), "short description", "text",
                        Collections.singleton(new ArticleAuthor(new Article(1L), new Author(2L))),
                        Collections.singleton(new ArticleKeyword(new Article(1L), new Keyword(2L)))));

        List<ArticleDto> collect = articles.stream().map(toArticleDto).collect(Collectors.toList());

        PageImpl<ArticleDto> articlePage = new PageImpl<>(collect, PageRequest.of(0, 10), 2);

        PageRequest pageRequest = PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));

        // when
        when(articleRepository.findAllByKeywords(pageRequest, new Long[]{1L, 2L}))
                .thenReturn(new PageImpl<>(articles, PageRequest.of(0, 10), 2));

        // then
        String expected = om.writeValueAsString(Response.successWithData(articlePage));

        ResponseEntity<String> response = restTemplate.getForEntity("/articles?keywordsId=1,2", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());

        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(articleRepository, times(1)).findAllByKeywords(pageRequest, new Long[]{1L, 2L});

    }

    @Test
    public void list_all_articles_for_a_given_period_OK() throws Exception {

        // given
        Optional<Date> dateFrom = Optional.of(LocalDate.of(2018, 1, 1)).map(DateUtil.localDateToDate);
        Optional<Date> dateto = Optional.of(LocalDate.of(2019, 1, 1)).map(DateUtil.localDateToDate);


        Optional<Date> dateArticle1 = Optional.of(LocalDate.of(2018, 4, 1)).map(DateUtil.localDateToDate);
        Optional<Date> dateArticle2 = Optional.of(LocalDate.of(2018, 12, 1)).map(DateUtil.localDateToDate);


        List<Article> articles = Arrays.asList(
                new Article(1L, "news", dateArticle1.get(), "short description", "text",
                        Collections.singleton(new ArticleAuthor(new Article(1L), new Author(1L))),
                        Collections.singleton(new ArticleKeyword(new Article(1L), new Keyword(1L)))),
                new Article(2L, "news", dateArticle2.get(), "short description", "text",
                        Collections.singleton(new ArticleAuthor(new Article(1L), new Author(2L))),
                        Collections.singleton(new ArticleKeyword(new Article(1L), new Keyword(2L)))));

        List<ArticleDto> collect = articles.stream().map(toArticleDto).collect(Collectors.toList());

        PageImpl<ArticleDto> articlePage = new PageImpl<>(collect, PageRequest.of(0, 10), 2);

        PageRequest pageRequest = PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));

        // when
        when(articleRepository.findAllByPublishDateBetween(dateFrom.get(), dateto.get(), pageRequest))
                .thenReturn(new PageImpl<>(articles, PageRequest.of(0, 10), 2));

        // then
        String expected = om.writeValueAsString(Response.successWithData(articlePage));

        ResponseEntity<String> response = restTemplate.getForEntity("/articles?dateFrom=" + DateUtil.dateToString(dateFrom.get()) + "&dateTo=" + DateUtil.dateToString(dateto.get()), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());

        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(articleRepository, times(1)).findAllByPublishDateBetween(dateFrom.get(), dateto.get(), pageRequest);

    }


    private Function<Article, ArticleDto> toArticleDto = article -> {
        ArticleAuthor articleAuthors = (ArticleAuthor) article.getAuthors().toArray()[0];
        ArticleKeyword articleKeyword = (ArticleKeyword) article.getKeywords().toArray()[0];

        ArticleDto articleSingleDto = new ArticleDto();
        articleSingleDto.setId(article.getId());
        articleSingleDto.setHeader(article.getHeader());
        articleSingleDto.setShortDescription(article.getShortDescription());
        articleSingleDto.setPublishDate(DateUtil.dateToString(article.getPublishDate()));
        articleSingleDto.setAuthors(Collections.singleton(new AuthorDto(articleAuthors.getAuthor().getId(), articleAuthors.getAuthor().getName())));
        articleSingleDto.setKeywords(Collections.singleton(new KeywordDto(articleKeyword.getKeyword().getId(), articleKeyword.getKeyword().getKeyword())));

        return articleSingleDto;
    };
}
