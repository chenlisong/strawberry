package com.example.demo.convert;

import org.mockito.internal.util.reflection.Fields;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvertRun {

    public static void main(String[] args) throws Exception{

        HttpStudent httpStudent = new HttpStudent();
        httpStudent.setId(1001L);
        httpStudent.setName("zhangsan");
        httpStudent.setSex(1);
        httpStudent.setContent("hello world");

        System.out.println(convert(httpStudent, Student.class).getName());

        System.out.println(convert(httpStudent, Student.class, new String[]{"content2"}, new Object[]{"hello world"}).getContent2());
    }

    public static <T> T convert(Object src, Class<T> targetClass, String[] fieldAppend, Object[] values) throws Exception{
        T t = convert(src, targetClass);

        Field[] fields = t.getClass().getDeclaredFields();
        if(isNull(fields, fieldAppend, values)) {
            return t;
        }

        List<String> fieldNames = entity(Arrays.asList(fields), "name", String.class);

        for(int i=0; i<fieldAppend.length; i++) {
            if(fieldNames.contains(fieldAppend[i])) {
                String setMethodName = "set"+fieldAppend[i].substring(0,1).toUpperCase()+fieldAppend[i].substring(1);
                targetClass.getDeclaredMethod(setMethodName, values[i].getClass()).invoke(t, values[i]);
            }
        }
        return t;
    }

    public static <T> T convert(Object src, Class<T> targetClass) throws Exception{
        T t = (T) Class.forName(targetClass.getName()).newInstance();

        Field[] fields = targetClass.getDeclaredFields();
        Field[] srcFields = src.getClass().getDeclaredFields();
        if(isNull(fields, srcFields)) {
            return t;
        }
        List<String> srcFieldNames = entity(Arrays.asList(srcFields), "name", String.class);

        for(Field field : fields) {
            if(field.isAnnotationPresent(Convert.class)) {
                String fieldName = field.getName();
                String getMethodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);

                if(srcFieldNames.contains(fieldName)) {
                    String setMethodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                    Object fieldValue = src.getClass().getDeclaredMethod(getMethodName).invoke(src);
                    targetClass.getDeclaredMethod(setMethodName, fieldValue.getClass()).invoke(t, fieldValue);
                }
            }
        }
        return t;
    }

    public static boolean isNull (Object[] ... list) {
        if(list.length > 0) {
            for (Object[] unit : list) {
                if (unit == null || unit.length <= 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <T> List<T> entity(List<? extends Object> list,String fieldName,Class<? extends Object> t){
        List<T> result = new ArrayList<T>();
        if(list == null || list.size() <= 0){
            return result;
        }
        for(Object o : list){

            String getMethod = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
            try {
                result.add((T) o.getClass().getDeclaredMethod(getMethod).invoke(o));
            } catch (Exception e) {
                throw new RuntimeException("CommonUtil entity error ."+e);
            }
        }
        return result;
    }
}
