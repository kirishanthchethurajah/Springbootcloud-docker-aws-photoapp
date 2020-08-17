package com.kira.micro.userservice.repository;

import com.kira.micro.userservice.model.AlbumCollection;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

//@FeignClient(name = "albums-service", fallback = AlbumFallBack.class)
@FeignClient(name = "albums-service", fallbackFactory = AlbumFallBackFactory.class)
public interface AlbumFeignServiceClient {
    @GetMapping("/users/{id}/albums")
    public List<AlbumCollection> getAlbums(@PathVariable  String id);
}
@Component
class AlbumFallBackFactory implements FallbackFactory<AlbumFeignServiceClient>{

    @Override
    public AlbumFeignServiceClient create(Throwable throwable) {
        return new AlbumFeignServiceClientFallback(throwable);
    }
}

class AlbumFeignServiceClientFallback implements AlbumFeignServiceClient{
    private final Throwable throwable;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public AlbumFeignServiceClientFallback(Throwable throwable) {
        this.throwable= throwable;
    }

    @Override
    public List<AlbumCollection> getAlbums(String id) {
        if(throwable instanceof FeignException && ((FeignException)throwable).status() == 404){
            logger.error("404 error album service is not working for the user :"+id+"with error message" + throwable.getLocalizedMessage());
        }
        else{
            logger.error("different error occurred"+throwable.getLocalizedMessage());
        }
        return new ArrayList<>();
    }
}
