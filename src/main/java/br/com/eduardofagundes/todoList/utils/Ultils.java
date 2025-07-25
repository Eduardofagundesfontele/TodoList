package br.com.eduardofagundes.todoList.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.xml.transform.Source;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Target;
import java.util.HashSet;
import java.util.Set;

public class Ultils {

    public  static void copyNonNullProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertNames(source));
    }

    public static String[] getNullPropertNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);

       PropertyDescriptor [] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for(PropertyDescriptor pd: pds){
            Object srcValue = src.getPropertyValue(pd.getName());
            if(srcValue == null) {
             emptyNames.add(pd.getName());

            }
        }
        String[] result = new String[emptyNames.size()];
        return  emptyNames.toArray(result);
    }
}