/*
package com.lc.web.controller;

import lombok.val;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class VideoController {
     private long ChunkSize = 1000000L;
    @GetMapping("/videos/{name}")
    public ResponseEntity playVideo(@PathVariable String name,@RequestHeader HttpHeaders headers) throws Exception {
        val video = new UrlResource("file:/Users/luochao/Downloads/VID_20141229_113601.mp4");
        val region = resourceRegion(video, headers);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(region);
    }
    private ResourceRegion resourceRegion(UrlResource video, HttpHeaders headers) throws Exception {
        val contentLength = video.contentLength();
        if (headers.getRange() != null && headers.getRange().size() > 0) {
            val range = headers.getRange().get(0);
            val start = range.getRangeStart(contentLength);
            val end = range.getRangeEnd(contentLength);
            val rangeLength = Long.min(ChunkSize, end - start + 1);
            return new ResourceRegion(video, start, rangeLength);
        } else {
            val rangeLength = Long.min(ChunkSize, contentLength);
            return new ResourceRegion(video, 0, rangeLength);
        }
    }
}
*/
