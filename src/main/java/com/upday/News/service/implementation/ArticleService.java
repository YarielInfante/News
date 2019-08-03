package com.upday.News.service.implementation;

import com.upday.News.entity.Article;
import com.upday.News.entity.ArticleKeyword;
import com.upday.News.entity.Keyword;
import com.upday.News.repository.IArticleRepository;
import com.upday.News.repository.IKeywordRepository;
import com.upday.News.service.IArticleService;
import io.reactivex.Completable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService implements IArticleService {

    private final IArticleRepository articleRepository;
    private final IKeywordRepository keywordRepository;

    @Autowired
    public ArticleService(IArticleRepository articleRepository, IKeywordRepository keywordRepository) {
        this.articleRepository = articleRepository;
        this.keywordRepository = keywordRepository;
    }


    @Override
    @Transactional
    public Single<Long> add(Article article) {
        return Single.create(emitter -> {


            List<ArticleKeyword> keywords = article.getKeywords().stream()
                    .map(k -> {
                        Optional.ofNullable(k.getKeyword()).orElseThrow();
                        Optional<Keyword> keywordFound = keywordRepository.findByKeyword(
                                Optional.ofNullable(k.getKeyword().getKeyword())
                                        .map(String::trim)
                                        .map(String::toLowerCase)
                                        .orElse("")
                        );
                        keywordFound.orElseThrow();
                        k.setKeyword(keywordFound.get());
                        return k;
                    })
                    .collect(Collectors.toList());

            article.setKeywords(keywords);

            Article save = articleRepository.save(article);

            emitter.onSuccess(save.getId());
        });
    }

    @Override
    public Single<Article> getOneId(long id) {
        return Single.create(emitter -> {
            Optional<Article> articleFound = articleRepository.findById(id);

            articleFound.ifPresentOrElse(
                    emitter::onSuccess,
                    () -> emitter.onError(new EntityNotFoundException()));
        });
    }

    @Override
    public Single<List<Article>> getAll() {
        return null;
    }

    @Override
    public Completable update(Article article) {
        return null;
    }

    @Override
    public Completable delete(long id) {
        return null;
    }
}
