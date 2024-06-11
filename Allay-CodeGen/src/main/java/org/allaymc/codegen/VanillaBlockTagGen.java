package org.allaymc.codegen;

import com.google.gson.JsonParser;
import com.squareup.javapoet.*;
import lombok.SneakyThrows;
import org.allaymc.dependence.StringUtils;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Allay Project 2023/11/18
 *
 * @author daoge_cmd
 */
public class VanillaBlockTagGen {
    static final Path BLOCK_TAGS_FILE_PATH = Path.of(CodeGen.DATA_PATH + "block_tags.json");
    static final ClassName BLOCK_TAG_CLASS = ClassName.get("org.allaymc.api.block.tag", "BlockTag");
    static final Set<String> KEYS = new HashSet<>();
    static final String JAVA_DOC = """
            Automatically generated by {@code org.allaymc.codegen.VanillaBlockTagGen} <br>
            Allay Project <p>
            @author daoge_cmd
            """;

    static {
        try {
            JsonParser
                    .parseReader(Files.newBufferedReader(BLOCK_TAGS_FILE_PATH))
                    .getAsJsonObject()
                    .entrySet()
                    .forEach(entry -> KEYS.add(entry.getKey()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        generate();
    }

    @SneakyThrows
    public static void generate() {
        TypeSpec.Builder codeBuilder = TypeSpec.interfaceBuilder("VanillaBlockTags")
                .addJavadoc(JAVA_DOC)
                .addModifiers(Modifier.PUBLIC)
                .addField(
                        FieldSpec
                                .builder(ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class), BLOCK_TAG_CLASS), "NAME_TO_TAG", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                                .initializer("new $T<>()", HashMap.class)
                                .build()
                )
                .addMethod(
                        MethodSpec
                                .methodBuilder("create")
                                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .returns(BLOCK_TAG_CLASS)
                                .addParameter(String.class, "name")
                                .addStatement("var tag = new $T(name)", BLOCK_TAG_CLASS)
                                .addStatement("NAME_TO_TAG.put(name, tag)")
                                .addStatement("return tag")
                                .build()
                )
                .addMethod(
                        MethodSpec
                                .methodBuilder("getTagByName")
                                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .returns(BLOCK_TAG_CLASS)
                                .addParameter(String.class, "name")
                                .addStatement("return NAME_TO_TAG.get(name)")
                                .build()
                );
        var fieldNames = new HashSet<String>();
        for (var key : KEYS) {
            var fieldName = StringUtils.fastTwoPartSplit(key, ":", "")[1].toUpperCase();
            fieldNames.add(fieldName);
            codeBuilder.addField(
                    FieldSpec
                            .builder(BLOCK_TAG_CLASS, fieldName)
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                            .initializer("create($S)", key)
                            .build()
            );
        }

        var javaFile = JavaFile.builder("org.allaymc.api.data", codeBuilder.build()).build();
        Files.writeString(Path.of("Allay-API/src/main/java/org/allaymc/api/data/VanillaBlockTags.java"), javaFile.toString());
    }
}