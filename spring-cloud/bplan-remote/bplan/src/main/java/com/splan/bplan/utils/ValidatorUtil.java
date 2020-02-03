package com.splan.bplan.utils;

import com.splan.bplan.http.CommonResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public static <T> List<String> validate(T t) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);

        List<String> messageList = new ArrayList<>();
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            messageList.add(constraintViolation.getMessage());
        }
        return messageList;
    }

    public static <T> CommonResult validateCommonResult(List<T> t) {
        Validator validator = factory.getValidator();
        for (int i = 0; i < t.size(); i++) {
            Set<ConstraintViolation<T>> constraintViolations = validator.validate(t.get(i));

            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                return ResultUtil.returnError("1002",constraintViolation.getMessage());
            }

        }
        return ResultUtil.returnSuccess("");

    }


    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches() ){
            return false;
        }
        return true;
    }
}
