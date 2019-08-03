package com.upday.News.service.implementation;

import com.upday.News.entity.Keyword;
import com.upday.News.repository.IKeywordRepository;
import com.upday.News.service.IKeywordService;
import io.reactivex.Single;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
