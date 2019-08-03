package com.upday.News.service.implementation;

import com.upday.News.entity.*;
import com.upday.News.repository.IArticleRepository;
import com.upday.News.repository.IAuthorRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleService implements IArticleService {

    private final IArticleRepository articleRepository;
    private final IKeywordRepository keywordRepository;
    private final IAuthorRepository authorRepository;

    @Autowired
    public ArticleService(IArticleRepository articleRepository, IKeywordRepository keywordRepository, IAuthorRepository authorRepository) {
        this.articleRepository = articleRepository;
        this.keywordRepository = keywordRepository;
        this.authorRepository = authorRepository;
    }


    @Override
    @Transactional
    public Single<Long> add(Article article) {
        return Single.create(emitter -> {


            Set<ArticleKeyword> keywords = article.getKeywords().stream()
                    .map(k -> {
                        Optional<Keyword> keywordFound = keywordRepository.findById(
                                Optional.ofNullable(k.getKeyword().getId())
                                        .orElse(0L)
                        );
                        keywordFound.orElseThrow();
                        k.setKeyword(keywordFound.get());
                        return k;
                    })
                    .collect(Collectors.toSet());

            Set<ArticleAuthor> authors = article.getAuthors().stream()
                    .map(author -> {
                        Optional<Author> authorFound = authorRepository.findById(
                                Optional.ofNullable(author.getAuthor().getId())
                                        .orElse(0L)
                        );
                        authorFound.orElseThrow();
                        author.setAuthor(authorFound.get());
                        return author;
                    })
                    .collect(Collectors.toSet());

            article.setKeywords(keywords);
            article.setAuthors(authors);

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
