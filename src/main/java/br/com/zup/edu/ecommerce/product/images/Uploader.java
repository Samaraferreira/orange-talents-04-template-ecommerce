package br.com.zup.edu.ecommerce.product.images;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

public interface Uploader {
    Set<String> upload(Collection<MultipartFile> pictures) throws IOException;
}
