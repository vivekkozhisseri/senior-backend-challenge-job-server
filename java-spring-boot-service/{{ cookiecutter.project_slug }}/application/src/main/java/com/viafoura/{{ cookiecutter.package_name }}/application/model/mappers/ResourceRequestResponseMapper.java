package com.viafoura.{{ cookiecutter.package_name }}.application.model.mappers;

import com.viafoura.{{ cookiecutter.package_name }}.application.model.requests.CreateResourceRequest;
import com.viafoura.{{ cookiecutter.package_name }}.application.model.responses.resource.ResourceResponse;
import com.viafoura.{{ cookiecutter.package_name }}.core.domains.resource.Resource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceRequestResponseMapper {
  ResourceResponse resourceToResourceResponse(Resource resource);

  Resource resourceResponseToResource(ResourceResponse resourceResponse);

  Resource createResourceRequestToResource(CreateResourceRequest createResourceRequest);
}

