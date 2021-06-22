package br.com.zup.edu.ecommerce.product.images;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class ImagesRequest {

    @NotNull
    private List<MultipartFile> images;

    public ImagesRequest(List<MultipartFile> images) {
        this.images = images;
    }

    public List<MultipartFile> getImages() {
        return images;
    }
}
