package com.o2o.nm.sys.service;

import com.o2o.nm.sys.entity.Permission;

import java.util.List;
import java.util.Map;

public interface ResService {
    List<Map<String, Object>> getResources();

    Permission getResource(String id);

    ServiceResponse move(String id, String to);

    void save(Permission res);

    void delete(String id);

    void emptyResources();

    void savePermissions(List<Permission> permissions);
}
