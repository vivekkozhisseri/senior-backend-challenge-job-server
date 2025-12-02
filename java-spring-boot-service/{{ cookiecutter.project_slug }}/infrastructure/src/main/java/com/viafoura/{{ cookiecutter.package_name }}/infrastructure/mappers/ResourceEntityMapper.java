package com.viafoura.{{ cookiecutter.package_name }}.infrastructure.mappers;

import com.viafoura.{{ cookiecutter.package_name }}.core.domains.resource.Resource;
import com.viafoura.{{ cookiecutter.package_name }}.infrastructure.entities.ResourceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceEntityMapper {
  Resource resourceEntityToResource(ResourceEntity resourceEntity);

  ResourceEntity resourceToResourceEntity(Resource resource);
}

