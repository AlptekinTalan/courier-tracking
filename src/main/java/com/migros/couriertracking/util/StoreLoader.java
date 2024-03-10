package com.migros.couriertracking.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.migros.couriertracking.model.Store;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class StoreLoader {

    private final ResourceLoader resourceLoader;

    private List<Store> migrosStores;

    // Initialize Migros stores from stores.json
    public StoreLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<Store> getStores() {
        return migrosStores;
    }

    @PostConstruct
    public void initialize() {
        try {
            Resource resource = resourceLoader.getResource("classpath:stores.json");
            InputStream inputStream = resource.getInputStream();

            ObjectMapper objectMapper = new ObjectMapper();
            migrosStores = objectMapper.readValue(inputStream, new TypeReference<List<Store>>() {});
            for (Store migrosStore : migrosStores) {
                System.out.println("migrosStore: "+migrosStore.getName() + " "+ migrosStore.getLat() + " "+ migrosStore.getLng() );
            }
        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
        }
    }
}

