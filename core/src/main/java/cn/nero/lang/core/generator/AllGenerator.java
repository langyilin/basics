package cn.nero.lang.core.generator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AllGenerator {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "root";
    public static final String PACKAGE_NAME = "cn.nero.lang.core";
    public static final String OUTPUT_DIR_BASE = System.getProperty("user.dir") + "/core/src/main";
    public static final String OUTPUT_DIR_JAVA = OUTPUT_DIR_BASE + "/java";
    public static final String OUTPUT_DIR_RESOURCE = OUTPUT_DIR_BASE + "/resources";

    public static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

    public static void main(String[] args) {
        String tableString = "all";
        tableString = "ai_model";
        List<String> tables = AllGenerator.getTables(tableString);
        //生成基础的entity,service,mapper等
        BaseGenerator.generate(tables);
        //生成dto
        DtoGenerator.generate(tables);
        //生成converter
        ConverterGenerator.generate(tables);
        //生成BizService
        BizServiceGenerator.generate(tables);
    }
}
