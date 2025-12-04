package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.domain.Image;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final BookRepository bookRepository;
    private final ImageRepository imageRepository;

    @Override
    public Image saveImage(Long bookId , String imageUrl) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("책을 찾을 수 없습니다 ."));

        Image image = new Image();
        image.setImgUrl(imageUrl);
        image.setBook(book);

        return imageRepository.save(image);
    }

    @Override
    public List<Image> findByBook(Long bookId) {
        return imageRepository.findByBookBookId(bookId);
    }

    @Override
    public void delete(Long imageId) {
        imageRepository.deleteById(imageId);
    }
}
