package com.tian.config.factory;

import com.tian.config.annotation.CurrencyFormat;
import org.springframework.context.support.EmbeddedValueResolutionSupport;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.number.CurrencyStyleFormatter;

import java.math.RoundingMode;
import java.util.*;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/13
 * 说明：
 */
public class CurrencyFormatFactory extends EmbeddedValueResolutionSupport implements AnnotationFormatterFactory<CurrencyFormat> {

    private static final Set<Class<?>> FIELD_TYPES;

    @Override
    public Set<Class<?>> getFieldTypes() {
        return FIELD_TYPES;
    }

    @Override
    public Printer<?> getPrinter(CurrencyFormat annotation, Class<?> aClass) {
        return this.configureFormatterFrom(annotation);
    }

    @Override
    public Parser<?> getParser(CurrencyFormat annotation, Class<?> aClass) {
        return this.configureFormatterFrom(annotation);
    }

    private Formatter<Number> configureFormatterFrom(CurrencyFormat annotation) {
        String currencyField = this.resolveEmbeddedValue(annotation.currency());
        //annotation.getClass()
        Currency instance = Currency.getInstance(currencyField);
        CurrencyStyleFormatter currencyStyleFormatter = new CurrencyStyleFormatter();
        currencyStyleFormatter.setCurrency(instance);
        return currencyStyleFormatter;
    }

    static {
        Set<Class<?>> fieldTypes = new HashSet(6);
        fieldTypes.add(Double.class);
        fieldTypes.add(Double.TYPE);
        fieldTypes.add(Long.class);
        fieldTypes.add(Long.TYPE);
        fieldTypes.add(Integer.class);
        fieldTypes.add(Integer.TYPE);
        FIELD_TYPES = Collections.unmodifiableSet(fieldTypes);
    }

    public static void main(String[] args) {
        CurrencyStyleFormatter currencyStyleFormatter = new CurrencyStyleFormatter();
        currencyStyleFormatter.setCurrency(Currency.getInstance("JPY"));
        currencyStyleFormatter.setFractionDigits(Currency.getInstance("JPY").getDefaultFractionDigits());
        currencyStyleFormatter.setRoundingMode(RoundingMode.HALF_EVEN);
        currencyStyleFormatter.setLenient(false);
        System.out.println(Currency.getInstance("JPY").getDefaultFractionDigits());

        System.out.println(currencyStyleFormatter.print(77600.1, Locale.CHINESE));
        System.out.println(currencyStyleFormatter.print(77600.5, Locale.CHINESE));
        System.out.println(currencyStyleFormatter.print(7600.6, Locale.CHINESE));
        System.out.println(currencyStyleFormatter.print(600, Locale.CHINESE));
        System.out.println(currencyStyleFormatter.print(-600.1, Locale.CHINESE));
        System.out.println(currencyStyleFormatter.print(-600.5, Locale.CHINESE));
        System.out.println(currencyStyleFormatter.print(-600.6, Locale.CHINESE));

    }
}
