package cn.nero.lang.core.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;
import java.util.List;

import static cn.nero.lang.core.generator.AllGenerator.*;

public class BaseGenerator {


    public static void main(String[] args) {
        String tableString = "all";
        tableString = "ai_model";
        List<String> tables = AllGenerator.getTables(tableString);
        BaseGenerator.generate(tables);
    }

    public static void generate(List<String> tables) {
        //将entity和mapper生成在core工程目录下

        FastAutoGenerator.create(DB_URL, DB_USERNAME, DB_PASSWORD)
                //全局配置
                .globalConfig(builder -> builder.author("nero")
                        .disableOpenDir()
                        .outputDir(OUTPUT_DIR_JAVA))
                //包配置
                .packageConfig(builder -> builder.parent(PACKAGE_NAME)
                        .mapper("mapper")
                        .entity("model.entity")
                        .pathInfo(Collections.singletonMap(OutputFile.xml, OUTPUT_DIR_RESOURCE + "/" + PACKAGE_NAME + ".mapper")))
                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(tables).build();
                    builder.entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .enableChainModel()
                            .disableSerialVersionUID()
                            .enableFileOverride()
                            .formatFileName("%sEntity")
                            .build();
                    builder.mapperBuilder()
                            .enableFileOverride()
//                            .disableMapperXml()//不生成xml文件
                            .build();
                    builder.serviceBuilder()
                            .enableFileOverride()
                            .formatServiceFileName("%sService")
                            .build();
                    //禁止controller代码生成
                    builder.controllerBuilder().disable().build();
                })
                .execute();

    }
}
