package br.com.zup.edu.ecommerce.product.images;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUpload implements Uploader {

    @Override
    public Set<String> upload(Collection<MultipartFile> pictures) throws IOException {
        Set<String> links = new HashSet<>();

        return pictures.stream()
                .map(i -> {
                    String link = "http://bucket.io/" + StringUtils.cleanPath(i.getOriginalFilename()).replace(" ", "");
                    links.add(link);
                    return link;
                })
                .collect(Collectors.toSet());
    }

/*
    @Override
    public Set<String> upload(Collection<MultipartFile> multipartFiles) throws IOException {
        Set<String> links = new HashSet<>();

        for (MultipartFile file : multipartFiles) {
            Path uploadPath = Paths.get("uploads");
            String fileName = UUID.randomUUID() + StringUtils.cleanPath(file.getOriginalFilename());

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                String link = Paths.get(System.getProperty("user.dir"), filePath.toString())
                                                        .toString().replace(" ", "");
                links.add(link);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }

        return links;
    }
*/
}
