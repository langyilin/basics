package cn.nero.lang.core.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;

import java.util.List;

import static cn.nero.lang.core.generator.AllGenerator.*;

public class BizServiceGenerator {

    public static void main(String[] args) {
        String tableString = "all";
        tableString = "user_container_info";
        List<String> tables = AllGenerator.getTables(tableString);
        BizServiceGenerator.generate(tables);
    }

    public static void generate(List<String> tables) {
        //将entity和mapper生成在core工程目录下
        FastAutoGenerator.create(DB_URL, DB_USERNAME, DB_PASSWORD)
                //全局配置
                .globalConfig(builder -> builder.author("nero")
                        .disableOpenDir()
                        .enableSwagger()
                        .outputDir(OUTPUT_DIR_JAVA))
                //包配置
                .packageConfig(builder -> builder.parent(PACKAGE_NAME)
                        .entity("service.biz"))
                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(tables).build();
                    builder.entityBuilder()
                            .javaTemplate("/templates/biz-service.java")//dto.java.vm
                            .formatFileName("%sBizService")
                            .enableFileOverride()
                            .build();
                    //禁止mapper代码生成
                    builder.mapperBuilder().disable().build();
                    //禁止service代码生成
                    builder.serviceBuilder().disable().build();
                    //禁止controller代码生成
                    builder.controllerBuilder().disable().build();
                })
                .injectionConfig(builder -> builder.beforeOutputFile((tableInfo, object) -> {
                    String simple = tableInfo.getEntityName().replace("BizService", "");
                    object.put("entitySimple", simple);
                    object.put("entitySimpleName", simple.substring(0, 1).toLowerCase() + simple.substring(1));
                    tableInfo.getFields().forEach(field -> {
                        if (field.isKeyFlag()) {
                            object.put("pkPropertyType", field.getPropertyType());
                        }
                    });
                }))
                .execute();
    }

}
