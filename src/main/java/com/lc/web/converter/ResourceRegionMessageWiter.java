/*
package com.lc.web.converter;

import com.google.common.collect.Maps;
import lombok.val;
import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.ResourceRegionEncoder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ResourceRegionMessageWiter implements HttpMessageWriter<ResourceRegion> {
    private ResourceRegionEncoder regionEncoder = new ResourceRegionEncoder();
    List<MediaType> mediaTypes = MediaType.asMediaTypes(regionEncoder.getEncodableMimeTypes());
    ResolvableType REGION_TYPE = ResolvableType.forClass(ResourceRegion.class);

    @Override
    public List<MediaType> getWritableMediaTypes() {
        return MediaType.asMediaTypes(regionEncoder.getEncodableMimeTypes());
    }

    @Override
    public boolean canWrite(ResolvableType resolvableType, MediaType mediaType) {
        return regionEncoder.canEncode(resolvableType, mediaType);
    }

    @Override
    public Mono<Void> write(Publisher<? extends ResourceRegion> publisher,
                            ResolvableType resolvableType,
                            MediaType mediaType,
                            ReactiveHttpOutputMessage reactiveHttpOutputMessage,
                            Map<String, Object> map) {
        return null;
    }

    @Override
    public Mono<Void> write(Publisher<? extends ResourceRegion> inputStream,
                            ResolvableType actualType,
                            ResolvableType elementType,
                            MediaType mediaType,
                            ServerHttpRequest request,
                            ServerHttpResponse response,
                            Map<String, Object> hints) {
        val headers = response.getHeaders();
        headers.set(HttpHeaders.ACCEPT_RANGES, "bytes");
        return Mono.from(inputStream).flatMap(resourceRegion -> {
            try {
                response.setStatusCode(HttpStatus.PARTIAL_CONTENT);
                val resourceMediaType = getResourceMediaType(mediaType, resourceRegion.getResource());
                headers.setContentType(resourceMediaType);
                headers.setContentType(resourceMediaType);
                val contentLength = resourceRegion.getResource().contentLength();
                val start = resourceRegion.getPosition();
                val end = Math.min(start + resourceRegion.getCount() - 1, contentLength - 1);
                headers.add("Content-Range", "bytes " + start + "-" + end + "/" + contentLength);
                headers.setContentLength(end - start + 1);
                return zeroCopy(resourceRegion.getResource(), resourceRegion, response)
                        .orElseGet(() -> {
                            Mono<? extends ResourceRegion> input = Mono.just(resourceRegion);
                            val body = this.regionEncoder.encode(input, response.bufferFactory(), REGION_TYPE, resourceMediaType, Maps.newHashMap());
                            return response.writeWith(body);
                        });
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    private Mono<Void> writeSingleRegion(ResourceRegion region, ReactiveHttpOutputMessage message) {
        return zeroCopy(region.getResource(), region, message)
                .orElseGet(() -> {
                    val input = Mono.just(region);
                    val mediaType = message.getHeaders().getContentType();
                    val body = this.regionEncoder.encode(input, message.bufferFactory(), REGION_TYPE, mediaType, Maps.newHashMap());
                    return message.writeWith(body);
                });
    }

    private MediaType getResourceMediaType(MediaType mediaType, Resource resource) {
        if (mediaType != null && mediaType.isConcrete() && mediaType != MediaType.APPLICATION_OCTET_STREAM) {
            return mediaType;
        } else {
            return MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM);
        }
    }

    private Optional<Mono<Void>> zeroCopy(Resource resource, ResourceRegion region,
                                          ReactiveHttpOutputMessage message) {
        if (message instanceof ZeroCopyHttpOutputMessage && resource.isFile()) {
            try {
                val file = resource.getFile();
                val pos = region.getPosition();
                val count = region.getCount();
                return Optional.of(((ZeroCopyHttpOutputMessage) message).writeWith(file, pos, count));
            } catch (IOException ex) {
                // should not happen
            }
        }
        return Optional.empty();
    }


}*/
