package cn.nero.lang.core.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;

import static cn.nero.lang.core.generator.AllGenerator.*;

import java.util.List;

public class DtoGenerator {

    public static void main(String[] args) {
        String tableString = "all";
        tableString = "ai_model";
        List<String> tables = AllGenerator.getTables(tableString);
        DtoGenerator.generate(tables);
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
                        .entity("model.dto"))
                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(tables).build();
                    builder.entityBuilder()
                            .enableLombok()
                            .enableChainModel()
                            .disableSerialVersionUID()
                            .javaTemplate("/templates/dto.java")//dto.java.vm
                            .formatFileName("%sDto")
                            .enableFileOverride()
                            .build();
                    //禁止mapper代码生成
                    builder.mapperBuilder().disable().build();
                    //禁止service代码生成
                    builder.serviceBuilder().disable().build();
                    //禁止controller代码生成
                    builder.controllerBuilder().disable().build();
                })
                .injectionConfig(builder -> builder.beforeOutputFile((tableInfo, object) ->
                        tableInfo.getFields().forEach(field ->
                                field.setComment(field.getComment().replaceAll("\n", " ")
                                        .replaceAll("\r", " ")
                                        .replaceAll("\t", " ")
                                        .replaceAll("\"", "'")
                                        .replaceAll("\\\\", ",")))))
                .execute();
    }
}
