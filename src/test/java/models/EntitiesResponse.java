package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class EntitiesResponse {
    public List<EntityResponse> entity;

    public List<EntityResponse> getEntity() {
        return entity;
    }

    public EntityResponse getEntity(int index) {
        return entity.get(index);
    }
}

