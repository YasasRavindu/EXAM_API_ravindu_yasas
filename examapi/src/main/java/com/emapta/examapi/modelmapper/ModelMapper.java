package com.emapta.examapi.modelmapper;



import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Component
public class ModelMapper {

    @SuppressWarnings("rawtypes")
    @Autowired
    private List<Converter> converters;

    public <T> T map(Object source, Class<T> targetType) {
        org.modelmapper.ModelMapper mapper = createModelMapperInstance();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        return mapper.map(source, targetType);
    }

    public <T, S> List<T> map(List<S> sourceList, Class<T> targetType) {
        org.modelmapper.ModelMapper mapper = createModelMapperInstance();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        List<T> result = new ArrayList<T>();

        if (!CollectionUtils.isEmpty(sourceList)) {
            for (S item : sourceList) {
                result.add(mapper.map(item, targetType));
            }
        }
        return result;
    }

    public void map(Object source, Object target) {
        org.modelmapper.ModelMapper mapper = createModelMapperInstance();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.map(source, target);
    }

    @SuppressWarnings("unchecked")
    private org.modelmapper.ModelMapper createModelMapperInstance() {
        org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();
        converters.forEach(converter -> mapper.addConverter(converter));
        return mapper;
    }
}
