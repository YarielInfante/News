package com.upday.News.service.implementation;

import com.upday.News.entity.Keyword;
import com.upday.News.repository.IKeywordRepository;
import com.upday.News.service.IKeywordService;
import io.reactivex.Single;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class KeywordService implements IKeywordService {

    private final IKeywordRepository keywordRepository;

    public KeywordService(IKeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    @Override
    public Single<Optional<Page<Keyword>>> getAll(Pageable pageable) {

        return Single.create(emitter -> {
            Page<Keyword> all = keywordRepository.findAll(pageable);

            emitter.onSuccess(Optional.of(all));
        });
    }

    @Override
    public Single<Long> addAKeyword(Keyword keyword) {

        return Single.create(singleSubscriber -> {

            Keyword save = keywordRepository.save(keyword);

            singleSubscriber.onSuccess(save.getId());

        });
    }

    @Override
    public Single<Optional<List<Keyword>>> getByKeyword(String keyword) {

        return Single.create(emitter -> {

            Optional<List<Keyword>> keywords = keywordRepository.findByKeywordIgnoreCaseContaining("%" + keyword + "%");

            emitter.onSuccess(keywords);
        });
    }

    @Override
    public Single<Keyword> getOneId(long id) {
        return Single.create(emitter -> {
            Optional<Keyword> keyword = keywordRepository.findById(id);

            keyword.ifPresentOrElse(
                    emitter::onSuccess,
                    () -> emitter.onError(new EntityNotFoundException()));
        });
    }
}
