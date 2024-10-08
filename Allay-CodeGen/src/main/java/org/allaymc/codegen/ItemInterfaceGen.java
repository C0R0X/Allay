package org.allaymc.codegen;

import com.squareup.javapoet.*;
import lombok.SneakyThrows;
import org.allaymc.dependence.ItemId;

import javax.lang.model.element.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Allay Project 2023/5/20
 *
 * @author daoge_cmd
 */
public class ItemInterfaceGen extends BaseInterfaceGen {

    public static final ClassName ITEM_STACK_CLASS_NAME = ClassName.get("org.allaymc.api.item", "ItemStack");
    public static final ClassName ITEM_ID_CLASS_NAME = ClassName.get("org.allaymc.api.item.data", "ItemId");
    public static final ClassName ITEM_TYPE_CLASS_NAME = ClassName.get("org.allaymc.api.item.type", "ItemType");
    public static final ClassName ITEM_TYPES_CLASS_NAME = ClassName.get("org.allaymc.api.item.type", "ItemTypes");
    public static final ClassName ITEM_TYPE_BUILDER_CLASS_NAME = ClassName.get("org.allaymc.server.item.type", "AllayItemType");
    public static final ClassName ITEM_TYPE_DEFAULT_INITIALIZER_CLASS_NAME = ClassName.get("org.allaymc.server.item.type", "ItemTypeDefaultInitializer");
    public static final TypeSpec.Builder ITEM_TYPE_DEFAULT_INITIALIZER_CLASS_BUILDER =
            TypeSpec.classBuilder(ITEM_TYPE_DEFAULT_INITIALIZER_CLASS_NAME)
                    .addJavadoc(
                            "@author daoge_cmd <br>\n" +
                            "Allay Project <br>\n")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    public static Map<Pattern, String> SUB_PACKAGE_GROUPERS = new LinkedHashMap<>();

    public static void main(String[] args) {
        // NOTICE: Please run ItemIdEnumGen.generate() first before running this method
        generate();
    }

    @SneakyThrows
    public static void generate() {
        registerSubPackages();
        var interfaceDir = Path.of("Allay-API/src/main/java/org/allaymc/api/item/interfaces");
        if (!Files.exists(interfaceDir)) Files.createDirectories(interfaceDir);
        var typesClass = TypeSpec.classBuilder(ITEM_TYPES_CLASS_NAME).addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        for (var id : ItemId.values()) {
            typesClass.addField(
                    FieldSpec.builder(ParameterizedTypeName.get(ITEM_TYPE_CLASS_NAME, generateClassFullName(id)), id.name())
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                            .build()
            );
            var itemClassSimpleName = generateClassSimpleName(id);
            var itemClassFullName = generateClassFullName(id);
            var folderName = tryFindSpecifiedFolderName(itemClassSimpleName);
            var folderPath = folderName != null ? interfaceDir.resolve(folderName) : interfaceDir;
            var path = folderPath.resolve(itemClassSimpleName + ".java");
            if (!Files.exists(path)) {
                System.out.println("Generating " + itemClassSimpleName + "...");
                if (!Files.exists(folderPath))
                    Files.createDirectories(folderPath);
                generateClass(ITEM_STACK_CLASS_NAME, itemClassFullName, path);
            }
            addDefaultItemTypeInitializer(id, itemClassFullName);
        }
        generateDefaultItemTypeInitializer();
        var javaFile = JavaFile.builder(ITEM_TYPES_CLASS_NAME.packageName(), typesClass.build())
                .indent(Utils.INDENT)
                .skipJavaLangImports(true)
                .build();
        System.out.println("Generating " + ITEM_TYPES_CLASS_NAME.simpleName() + ".java ...");
        Files.writeString(Path.of("Allay-API/src/main/java/org/allaymc/api/item/type/" + ITEM_TYPES_CLASS_NAME.simpleName() + ".java"), javaFile.toString());
    }

    private static void addDefaultItemTypeInitializer(ItemId id, ClassName itemClassName) {
        var initializer = CodeBlock.builder();
        initializer
                .add("$T.$N = $T\n", ITEM_TYPES_CLASS_NAME, id.name(), ITEM_TYPE_BUILDER_CLASS_NAME)
                .add("        .builder($T.class)\n", itemClassName)
                .add("        .vanillaItem($T.$N)\n", ITEM_ID_CLASS_NAME, id.name())
                .add("        .build();");
        ITEM_TYPE_DEFAULT_INITIALIZER_CLASS_BUILDER
                .addMethod(
                        MethodSpec.methodBuilder(generateInitializerMethodName(id))
                                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .addStatement("if ($T.$N != null) return", ITEM_TYPES_CLASS_NAME, id.name())
                                .addCode(initializer.build())
                                .build()
                );
    }

    @SneakyThrows
    private static void generateDefaultItemTypeInitializer() {
        var filePath = Path.of("Allay-Server/src/main/java/org/allaymc/server/item/type/ItemTypeDefaultInitializer.java");
        Files.deleteIfExists(filePath);
        var folderPath = filePath.getParent();
        if (!Files.exists(folderPath))
            Files.createDirectories(folderPath);
        var javaFile = JavaFile.builder(ITEM_TYPE_DEFAULT_INITIALIZER_CLASS_NAME.packageName(), ITEM_TYPE_DEFAULT_INITIALIZER_CLASS_BUILDER.build())
                .indent(Utils.INDENT)
                .skipJavaLangImports(true)
                .build();
        System.out.println("Generating " + ITEM_TYPE_DEFAULT_INITIALIZER_CLASS_NAME.simpleName() + ".java ...");
        Files.writeString(filePath, javaFile.toString());
    }

    private static ClassName generateClassFullName(ItemId id) {
        var simpleName = generateClassSimpleName(id);
        var folderName = tryFindSpecifiedFolderName(simpleName);
        return ClassName.get("org.allaymc.api.item.interfaces" + (folderName != null ? "." + folderName : ""), simpleName);
    }

    private static String generateClassSimpleName(ItemId id) {
        // Windows环境对大小写不敏感，所以需要特殊处理一部分物品id
        // netherbrick和nether_brick需要特殊处理
        if (id == ItemId.NETHERBRICK) return "ItemNetherbrick0Stack";
        // tallgrass和tall_grass需要特殊处理
        if (id == ItemId.TALLGRASS) return "ItemTallgrass0Stack";
        return "Item" + Utils.convertToPascalCase(id.getIdentifier().path().replace(".", "_")) + "Stack";
    }

    private static String generateInitializerMethodName(ItemId id) {
        // 同上
        if (id == ItemId.NETHERBRICK) return "initNetherbrick0";
        if (id == ItemId.TALLGRASS) return "initTallgrass0";
        return "init" + Utils.convertToPascalCase(id.getIdentifier().path().replace(".", "_"));
    }

    private static String tryFindSpecifiedFolderName(String blockClassSimpleName) {
        for (var entry : SUB_PACKAGE_GROUPERS.entrySet()) {
            var pattern = entry.getKey();
            if (pattern.matcher(blockClassSimpleName).find()) {
                return entry.getValue();
            }
        }
        return null;
    }

    private static void registerSubPackage(Pattern regex, String packageName) {
        SUB_PACKAGE_GROUPERS.put(regex, packageName);
    }

    private static void registerSubPackages() {
        registerSubPackage(Pattern.compile(".*StairsStack"), "stairs");
        registerSubPackage(Pattern.compile(".*DoorStack"), "door");
        registerSubPackage(Pattern.compile(".*Slab\\d?Stack"), "slab");
        registerSubPackage(Pattern.compile(".*StandingSignStack"), "standingsign");
        registerSubPackage(Pattern.compile(".*HangingSignStack"), "hangingsign");
        registerSubPackage(Pattern.compile(".*WallSignStack"), "wallsign");
        registerSubPackage(Pattern.compile(".*SignStack"), "sign");
        registerSubPackage(Pattern.compile(".*WallStack"), "wall");
        registerSubPackage(Pattern.compile("ItemElement.*"), "element");
        registerSubPackage(Pattern.compile(".*CoralStack"), "coral");
        registerSubPackage(Pattern.compile(".*CoralBlockStack"), "coralblock");
        registerSubPackage(Pattern.compile(".*CoralFan.*"), "coralfan");
        registerSubPackage(Pattern.compile(".*BricksStack"), "bricks");
        registerSubPackage(Pattern.compile(".*WoolStack"), "wool");
        registerSubPackage(Pattern.compile(".*ButtonStack"), "button");
        registerSubPackage(Pattern.compile(".*PlanksStack"), "planks");
        registerSubPackage(Pattern.compile(".*TrapdoorStack"), "trapdoor");
        registerSubPackage(Pattern.compile(".*CandleStack"), "candle");
        registerSubPackage(Pattern.compile(".*CandleCakeStack"), "candlecake");
        registerSubPackage(Pattern.compile(".*ConcreteStack"), "concrete");
        registerSubPackage(Pattern.compile(".*ConcretePowderStack"), "concretepowder");
        registerSubPackage(Pattern.compile(".*TerracottaStack"), "terracotta");
        registerSubPackage(Pattern.compile(".*ShulkerBoxStack"), "shulkerbox");
        registerSubPackage(Pattern.compile(".*CarpetStack"), "carpet");
        registerSubPackage(Pattern.compile(".*WoodStack"), "wood");
        registerSubPackage(Pattern.compile(".*(Leaves\\d?|LeavesFlowered)Stack"), "leaves");
        registerSubPackage(Pattern.compile(".*FenceStack"), "fence");
        registerSubPackage(Pattern.compile(".*FenceGateStack"), "fencegate");
        registerSubPackage(Pattern.compile(".*Log\\d?Stack"), "log");
        registerSubPackage(Pattern.compile(".*CopperStack"), "copper");
        registerSubPackage(Pattern.compile(".*SaplingStack"), "sapling");
        registerSubPackage(Pattern.compile(".*(?:Water|Lava)Stack"), "liquid");
        registerSubPackage(Pattern.compile(".*BoatStack"), "boat");
        registerSubPackage(Pattern.compile(".*MinecartStack"), "minecart");
        registerSubPackage(Pattern.compile(".*BucketStack"), "bucket");
        registerSubPackage(Pattern.compile(".*EggStack"), "egg");
        registerSubPackage(Pattern.compile("ItemMusicDisc.*"), "musicdisc");
        registerSubPackage(Pattern.compile("ItemPiston.*"), "piston");
        registerSubPackage(Pattern.compile("ItemStickyPiston.*"), "piston");
        registerSubPackage(Pattern.compile(".*StainedGlassStack"), "stainedglass");
        registerSubPackage(Pattern.compile(".*StainedGlassPaneStack"), "stainedglasspane");
        registerSubPackage(Pattern.compile(".*GlassStack"), "glass");
        registerSubPackage(Pattern.compile(".*GlassPaneStack"), "glasspane");
        registerSubPackage(Pattern.compile(".*HelmetStack"), "helmet");
        registerSubPackage(Pattern.compile(".*ChestplateStack"), "chestplate");
        registerSubPackage(Pattern.compile(".*LeggingsStack"), "leggings");
        registerSubPackage(Pattern.compile(".*BootsStack"), "boots");
        registerSubPackage(Pattern.compile(".*SwordStack"), "sword");
        registerSubPackage(Pattern.compile(".*PickaxeStack"), "pickaxe");
        registerSubPackage(Pattern.compile(".*ShovelStack"), "shovel");
        registerSubPackage(Pattern.compile(".*HoeStack"), "hoe");
        registerSubPackage(Pattern.compile(".*AxeStack"), "axe");
        registerSubPackage(Pattern.compile(".*SandstoneStack"), "sandstone");
        registerSubPackage(Pattern.compile(".*SandStack"), "sand");
        registerSubPackage(Pattern.compile(".*LightBlock.*Stack"), "lightblock");
        registerSubPackage(Pattern.compile(".*DirtStack"), "dirt");
        registerSubPackage(Pattern.compile(".*AnvilStack"), "anvil");
        registerSubPackage(Pattern.compile(".*CoralWallFanStack"), "coralwallfan");
    }
}
