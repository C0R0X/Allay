package org.allaymc.api.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.allaymc.api.server.Server;
import org.allaymc.api.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author MagicDroidX (Nukkit) | daoge_cmd
 */
// TODO: refactor
// TODO: add javadoc
@Slf4j
public class Config {

    public static final int DETECT = -1; //Detect by file extension
    public static final int PROPERTIES = 0; // .properties
    public static final int CNF = Config.PROPERTIES; // .cnf
    public static final int JSON = 1; // .js, .json
    public static final int YAML = 2; // .yml, .yaml
    // public static final int EXPORT = 3; // .export, .xport
    // public static final int SERIALIZED = 4; // .sl
    public static final int ENUM = 5; // .txt, .list, .enum
    public static final int ENUMERATION = Config.ENUM;
    public static final Map<String, Integer> format = new TreeMap<>();

    static {
        format.put("properties", Config.PROPERTIES);
        format.put("json", Config.JSON);
        format.put("yml", Config.YAML);
        format.put("txt", Config.ENUM);
        format.put("list", Config.ENUM);
        format.put("enum", Config.ENUM);
    }

    //private LinkedHashMap<String, Object> config = new LinkedHashMap<>();
    private ConfigSection config = new ConfigSection();
    private File file;
    @Getter
    private boolean correct = false;
    private int type = Config.DETECT;

    /**
     * Constructor for Config instance with undefined file object
     *
     * @param type - Config type
     */
    public Config(int type) {
        this.type = type;
        this.correct = true;
        this.config = new ConfigSection();
    }

    /**
     * Constructor for Config (YAML) instance with undefined file object
     */
    public Config() {
        this(Config.YAML);
    }

    public Config(String file) {
        this(file, Config.DETECT);
    }

    public Config(File file) {
        this(file.toString(), Config.DETECT);
    }

    public Config(String file, int type) {
        this(file, type, new ConfigSection());
    }

    public Config(File file, int type) {
        this(file.toString(), type, new ConfigSection());
    }

    public Config(String file, int type, ConfigSection defaultMap) {
        this.load(file, type, defaultMap);
    }

    public Config(File file, int type, ConfigSection defaultMap) {
        this.load(file.toString(), type, defaultMap);
    }

    public void reload() {
        this.config.clear();
        this.correct = false;
        if (this.file == null) throw new IllegalStateException("Failed to reload Config. File object is undefined.");
        this.load(this.file.toString(), this.type);

    }

    public boolean load(String file) {
        return this.load(file, Config.DETECT);
    }

    public boolean load(String file, int type) {
        return this.load(file, type, new ConfigSection());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public boolean load(String file, int type, ConfigSection defaultMap) {
        this.correct = true;
        this.type = type;
        this.file = new File(file);
        if (!this.file.exists()) {
            try {
                this.file.getParentFile().mkdirs();
                this.file.createNewFile();
            } catch (IOException e) {
                log.error("Could not create Config {}", this.file.toString(), e);
            }
            this.config = defaultMap;
            this.save();
        } else {
            if (this.type == Config.DETECT) {
                String extension = "";
                if (this.file.getName().lastIndexOf(".") != -1 && this.file.getName().lastIndexOf(".") != 0) {
                    extension = this.file.getName().substring(this.file.getName().lastIndexOf(".") + 1);
                }
                if (format.containsKey(extension)) {
                    this.type = format.get(extension);
                } else {
                    this.correct = false;
                }
            }
            if (this.correct) {
                String content = "";
                try {
                    content = Files.readString(this.file.toPath());
                } catch (IOException e) {
                    log.error("An error occurred while loading the file {}", file, e);
                }
                this.parseContent(content);
                if (!this.correct) return false;
                if (this.setDefault(defaultMap) > 0) {
                    this.save();
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean load(InputStream inputStream) {
        if (inputStream == null) return false;
        if (this.correct) {
            String content;
            try {
                content = Utils.readString(inputStream);
            } catch (IOException e) {
                log.error("An error occurred while loading a config from an input stream, input: {}", inputStream, e);
                return false;
            }
            this.parseContent(content);
        }
        return correct;
    }

    public boolean loadAsJson(InputStream inputStream, Gson gson) {
        if (inputStream == null) return false;
        if (this.correct) {
            String content;
            try {
                content = Utils.readString(inputStream);
            } catch (IOException e) {
                log.error("An error occurred while loading a config from an input stream, input: {}", inputStream, e);
                return false;
            }
            this.parseContentAsJson(content, gson);
        }
        return correct;
    }

    public boolean save(File file, boolean async) {
        this.file = file;
        return save(async);
    }

    public boolean save(File file) {
        this.file = file;
        return save();
    }

    public boolean saveAsJson(@NotNull File file, boolean async, @NotNull Gson gson) {
        this.file = file;
        return saveAsJson(async, gson);
    }

    public boolean save() {
        return this.save(false);
    }

    public boolean saveAsJson(boolean async, @NotNull Gson gson) {
        if (!this.correct) {
            return false;
        }
        save0(async, new StringBuilder(gson.toJson(this.config)).append('\n'));
        return true;
    }

    public boolean save(Boolean async) {
        if (this.file == null) throw new IllegalStateException("Failed to save Config. File object is undefined.");
        if (this.correct) {
            StringBuilder content = new StringBuilder();
            switch (this.type) {
                case Config.PROPERTIES:
                    content = new StringBuilder(this.writeProperties());
                    break;
                case Config.JSON:
                    content = new StringBuilder(new GsonBuilder().setPrettyPrinting().create().toJson(this.config));
                    break;
                case Config.YAML:
                    DumperOptions dumperOptions = new DumperOptions();
                    dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                    Yaml yaml = new Yaml(dumperOptions);
                    content = new StringBuilder(yaml.dump(this.config));
                    break;
                case Config.ENUM:
                    for (Map.Entry<?, ?> entry : this.config.entrySet()) {
                        content.append(entry.getKey()).append("\r\n");
                    }
                    break;
            }
            save0(async, content);
            return true;
        } else {
            return false;
        }
    }

    private void save0(boolean async, StringBuilder content) {
        if (async) {
            Server.getInstance().getVirtualThreadPool().submit(() -> {
                try {
                    Files.write(this.file.toPath(), content.toString().getBytes());
                } catch (IOException e) {
                    log.error("Failed to save the config file {}", file, e);
                    throw new ConfigException(e);
                }
            });
        } else {
            try {
                Files.write(this.file.toPath(), content.toString().getBytes());
            } catch (IOException e) {
                log.error("Failed to save the config file {}", file, e);
                throw new ConfigException(e);
            }
        }
    }

    public void set(final String key, Object value) {
        this.config.set(key, value);
    }

    public Object get(String key) {
        return this.get(key, null);
    }

    public <T> T get(String key, T defaultValue) {
        return this.correct ? this.config.get(key, defaultValue) : defaultValue;
    }

    public ConfigSection getSection(String key) {
        return this.correct ? this.config.getSection(key) : new ConfigSection();
    }

    public boolean isSection(String key) {
        return config.isSection(key);
    }

    public ConfigSection getSections(String key) {
        return this.correct ? this.config.getSections(key) : new ConfigSection();
    }

    public ConfigSection getSections() {
        return this.correct ? this.config.getSections() : new ConfigSection();
    }

    public int getInt(String key) {
        return this.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return this.correct ? this.config.getInt(key, defaultValue) : defaultValue;
    }

    public boolean isInt(String key) {
        return config.isInt(key);
    }

    public long getLong(String key) {
        return this.getLong(key, 0);
    }

    public long getLong(String key, long defaultValue) {
        return this.correct ? this.config.getLong(key, defaultValue) : defaultValue;
    }

    public boolean isLong(String key) {
        return config.isLong(key);
    }

    public double getDouble(String key) {
        return this.getDouble(key, 0);
    }

    public double getDouble(String key, double defaultValue) {
        return this.correct ? this.config.getDouble(key, defaultValue) : defaultValue;
    }

    public boolean isDouble(String key) {
        return config.isDouble(key);
    }

    public String getString(String key) {
        return this.getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        return this.correct ? this.config.getString(key, defaultValue) : defaultValue;
    }

    public boolean isString(String key) {
        return config.isString(key);
    }

    public boolean getBoolean(String key) {
        return this.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return this.correct ? this.config.getBoolean(key, defaultValue) : defaultValue;
    }

    public boolean isBoolean(String key) {
        return config.isBoolean(key);
    }

    public List<?> getList(String key) {
        return this.getList(key, null);
    }

    public List<?> getList(String key, List<?> defaultList) {
        return this.correct ? this.config.getList(key, defaultList) : defaultList;
    }

    public boolean isList(String key) {
        return config.isList(key);
    }

    public List<String> getStringList(String key) {
        return config.getStringList(key);
    }

    public List<Integer> getIntegerList(String key) {
        return config.getIntegerList(key);
    }

    public List<Boolean> getBooleanList(String key) {
        return config.getBooleanList(key);
    }

    public List<Double> getDoubleList(String key) {
        return config.getDoubleList(key);
    }

    public List<Float> getFloatList(String key) {
        return config.getFloatList(key);
    }

    public List<Long> getLongList(String key) {
        return config.getLongList(key);
    }

    public List<Byte> getByteList(String key) {
        return config.getByteList(key);
    }

    public List<Character> getCharacterList(String key) {
        return config.getCharacterList(key);
    }

    public List<Short> getShortList(String key) {
        return config.getShortList(key);
    }

    public List<Map<?, ?>> getMapList(String key) {
        return config.getMapList(key);
    }

    public boolean exists(String key) {
        return config.exists(key);
    }

    public boolean exists(String key, boolean ignoreCase) {
        return config.exists(key, ignoreCase);
    }

    public void remove(String key) {
        config.remove(key);
    }

    public Map<String, Object> getAll() {
        return this.config.getAllMap();
    }

    public void setAll(LinkedHashMap<String, Object> map) {
        this.config = new ConfigSection(map);
    }

    public void setAll(ConfigSection section) {
        this.config = section;
    }

    /**
     * Get root (main) config section of the Config
     *
     * @return root config section
     */
    public ConfigSection getRootSection() {
        return config;
    }

    public int setDefault(LinkedHashMap<String, Object> map) {
        return setDefault(new ConfigSection(map));
    }

    public int setDefault(ConfigSection map) {
        int size = this.config.size();
        this.fillDefaults(map, this.config);
        return this.config.size() - size;
    }

    private void fillDefaults(ConfigSection defaultMap, ConfigSection data) {
        for (String key : defaultMap.keySet()) {
            if (!data.containsKey(key)) {
                data.put(key, defaultMap.get(key));
            }
        }
    }

    private void parseList(String content) {
        content = content.replace("\r\n", "\n");
        for (String v : content.split("\n")) {
            if (v.trim().isEmpty()) {
                continue;
            }
            config.put(v, true);
        }
    }

    private String writeProperties() {
        StringBuilder content = new StringBuilder("#Properties Config file\r\n#" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "\r\n");
        for (Map.Entry<?, ?> entry : this.config.entrySet()) {
            Object v = entry.getValue();
            Object k = entry.getKey();
            if (v instanceof Boolean) {
                v = (Boolean) v ? "on" : "off";
            }
            content.append(k).append("=").append(v).append("\r\n");
        }
        return content.toString();
    }

    private void parseProperties(String content) {
        for (final String line : content.split("\n")) {
            if (Pattern.compile("[a-zA-Z0-9\\-_.]*+=+[^\\r\\n]*").matcher(line).matches()) {
                final int splitIndex = line.indexOf('=');
                if (splitIndex == -1) {
                    continue;
                }
                final String key = line.substring(0, splitIndex);
                final String value = line.substring(splitIndex + 1);
                final String valueLower = value.toLowerCase();
                if (this.config.containsKey(key)) {
                    log.debug("[Config] Repeated property {} on file {}", key, this.file.toString());
                }
                switch (valueLower) {
                    case "on":
                    case "true":
                    case "yes":
                        this.config.put(key, true);
                        break;
                    case "off":
                    case "false":
                    case "no":
                        this.config.put(key, false);
                        break;
                    default:
                        this.config.put(key, value);
                        break;
                }
            }
        }
    }

    private void parseContentAsJson(String content, Gson gson) {
        try {
            this.config = new ConfigSection(gson.fromJson(content, new TypeToken<LinkedHashMap<String, Object>>() {
            }.getType()));
        } catch (RuntimeException e) {
            log.warn("Failed to parse the config file {}", file, e);
            throw new ConfigException(e);
        }
    }

    private void parseContent(String content) {
        try {
            switch (this.type) {
                case Config.PROPERTIES:
                    this.parseProperties(content);
                    break;
                case Config.JSON:
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    this.config = new ConfigSection(gson.fromJson(content, new TypeToken<LinkedHashMap<String, Object>>() {
                    }.getType()));
                    break;
                case Config.YAML:
                    DumperOptions dumperOptions = new DumperOptions();
                    dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                    Yaml yaml = new Yaml(dumperOptions);
                    this.config = new ConfigSection(yaml.loadAs(content, LinkedHashMap.class));
                    break;
                case Config.ENUM:
                    this.parseList(content);
                    break;
                default:
                    this.correct = false;
            }
        } catch (RuntimeException e) {
            log.warn("Failed to parse the config file {}", file, e);
            throw new ConfigException(e);
        }
    }

    public Set<String> getKeys() {
        if (this.correct) return config.getKeys();
        return new HashSet<>();
    }

    public Set<String> getKeys(boolean child) {
        if (this.correct) return config.getKeys(child);
        return new HashSet<>();
    }
}
