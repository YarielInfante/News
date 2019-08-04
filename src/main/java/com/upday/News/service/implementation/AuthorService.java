package com.upday.News.service.implementation;

import com.upday.News.entity.Author;
import com.upday.News.repository.IAuthorRepository;
import com.upday.News.service.IAuthorService;
import io.reactivex.Single;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements IAuthorService {

    private final IAuthorRepository authorRepository;

    public AuthorService(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Single<Optional<Page<Author>>> getAll(Pageable pageable) {

        return Single.create(emitter -> {
            Page<Author> all = authorRepository.findAll(pageable);

            emitter.onSuccess(Optional.of(all));
        });
    }

    @Override
    public Single<Long> addAuthor(Author author) {

        return Single.create(singleSubscriber -> {

            Author save = authorRepository.save(author);

            singleSubscriber.onSuccess(save.getId());

        });
    }

    @Override
    public Single<Optional<List<Author>>> getByName(String name) {

        return Single.create(emitter -> {

            Optional<List<Author>> author = authorRepository.findByNameIgnoreCaseContaining("%" + name + "%");
//            Optional<Author> author = authorRepository.findByNameIgnoreCaseContaining(name );

            emitter.onSuccess(author);
        });
    }


}
