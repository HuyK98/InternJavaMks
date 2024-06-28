package org.sakaiproject.tool.imagedetector.model;

import org.springframework.web.multipart.MultipartFile;

public class SearchMosModel {
    private MultipartFile attachment;

    public MultipartFile getAttachment() {
        return attachment;
    }

    public void setAttachment(MultipartFile attachment) {
        this.attachment = attachment;
    }
}
