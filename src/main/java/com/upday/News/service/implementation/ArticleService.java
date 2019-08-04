package com.upday.News.service.implementation;

import com.upday.News.entity.*;
import com.upday.News.repository.*;
import com.upday.News.service.IArticleService;
import io.reactivex.Completable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ArticleService implements IArticleService {

    private final IArticleRepository articleRepository;
    private final IKeywordRepository keywordRepository;
    private final IAuthorRepository authorRepository;
    private final IArticleKeywordRepository articleKeywordRepository;
    private final IArticleAuthorRepository articleAuthorRepository;

    @Autowired
    public ArticleService(IArticleRepository articleRepository, IKeywordRepository keywordRepository, IAuthorRepository authorRepository, IArticleKeywordRepository articleKeywordRepository, IArticleAuthorRepository articleAuthorRepository) {
        this.articleRepository = articleRepository;
        this.keywordRepository = keywordRepository;
        this.authorRepository = authorRepository;
        this.articleKeywordRepository = articleKeywordRepository;
        this.articleAuthorRepository = articleAuthorRepository;
    }


    @Override
    @Transactional
    public Single<Long> add(Article article) {
        return Single.create(emitter -> {

            Article save = articleRepository.save(article);

            Set<ArticleKeyword> keywords = article.getKeywords().stream()
                    .map(k -> {
                        Optional<Keyword> keywordFound = keywordRepository.findById(
                                Optional.ofNullable(k.getKeyword().getId())
                                        .orElse(0L)
                        );
                        keywordFound.orElseThrow();
                        k.setArticleKeywordPK(new ArticleKeywordPK(save.getId(), keywordFound.get().getId()));
                        return k;
                    })
                    .collect(Collectors.toSet());

            articleKeywordRepository.saveAll(keywords);

            Set<ArticleAuthor> authors = article.getAuthors().stream()
                    .map(author -> {
                        Optional<Author> authorFound = authorRepository.findById(
                                Optional.ofNullable(author.getAuthor().getId())
                                        .orElse(0L)
                        );
                        authorFound.orElseThrow();
                        author.setArticleAuthorPK(new ArticleAuthorPK(save.getId(), authorFound.get().getId()));
                        return author;
                    })
                    .collect(Collectors.toSet());

            articleAuthorRepository.saveAll(authors);

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
    public Single<Optional<Page<Article>>> getAll(Pageable pageable) {
        return Single.create(emitter -> {
            Page<Article> articles = articleRepository.findAll(pageable);

            emitter.onSuccess(Optional.ofNullable(articles));
        });
    }


    @Override
    @Transactional
    public Completable update(Article article) {
        return Completable.create(emitter -> {

            Optional<Article> articleFound = articleRepository.findById(article.getId());

            if (articleFound.isPresent()) {

                articleFound.get().setHeader(article.getHeader() != null ? article.getHeader() : articleFound.get().getHeader());
                articleFound.get().setShortDescription(article.getShortDescription() != null ? article.getShortDescription() : articleFound.get().getShortDescription());
                articleFound.get().setPublishDate(article.getPublishDate() != null ? article.getPublishDate() : articleFound.get().getPublishDate());
                articleFound.get().setText(article.getText() != null ? article.getText() : articleFound.get().getText());

                articleRepository.save(articleFound.get());

                if (article.getKeywords() != null && article.getKeywords().size() > 0) {
                    article.getKeywords().forEach(k -> articleKeywordRepository.save(new ArticleKeyword(new ArticleKeywordPK(article.getId(), k.getArticleKeywordPK().getKeywordId()))));
                }

                if (article.getAuthors() != null && article.getAuthors().size() > 0) {
                    article.getAuthors().forEach(author -> articleAuthorRepository.save(new ArticleAuthor(new ArticleAuthorPK(article.getId(), author.getArticleAuthorPK().getAuthorId()))));
                }


                emitter.onComplete();

            } else {
                emitter.onError(new EntityNotFoundException());
            }
        });
    }

    @Override
    public Completable delete(long id) {
        return Completable.create(completableSubscriber -> {

            Optional<Article> articleFound = articleRepository.findById(id);

            if (articleFound.isPresent()) {

                articleRepository.delete(articleFound.get());

                completableSubscriber.onComplete();


            } else {

                completableSubscriber.onError(new EntityNotFoundException());

            }
        });
    }

    @Override
    public Single<Optional<Page<Article>>> getAllByAuthorsId(Pageable pageable, Long[] authorsId) {
        return Single.create(emitter -> {

            Page<Article> articles = articleRepository.findAllByAuthors(pageable, authorsId);

            emitter.onSuccess(Optional.ofNullable(articles));
        });
    }
}
