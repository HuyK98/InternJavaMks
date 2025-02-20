package org.sakaiproject.tool.imagedetector.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import org.sakaiproject.tool.imagedetector.model.SearchMosModel;

@Controller
@Slf4j
public class SearchMosController {

    @Value("${detection.api}")
    String detectionApiUrl;

    @GetMapping(value = "/searchmos/upload")
    public ModelAndView uploadImageRefresh() {
        ModelAndView mav = new ModelAndView("search-result");
        return mav;
    }

    @PostMapping(value = "/searchmos/upload")
    public ModelAndView uploadImage(@ModelAttribute("model") SearchMosModel model, BindingResult bindingResult) throws IOException {
        ModelAndView mav = new ModelAndView("search-result");

        MultipartFile file = model.getAttachment();
        String imageBase64 = Base64Utils.encodeToString(file.getBytes());
        mav.addObject("image", imageBase64);

        log.debug(String.format("Connecting to %s ...", detectionApiUrl));

        try {
            WebClient webClient = WebClient.builder()
                    .baseUrl(detectionApiUrl)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                    .build();

            // Build multipart request
            MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
            bodyBuilder.part("file", file.getResource());

            // Send the API request and process the response
            String responseJson = webClient.post()
                    .uri("/")
                    .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            mav.addObject("responseJson", responseJson);
        } catch (Exception e) {
            log.error("Error connecting to detection API", e);
        }
        return mav;
    }
}
