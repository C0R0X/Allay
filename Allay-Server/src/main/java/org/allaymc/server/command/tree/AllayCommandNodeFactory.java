package org.allaymc.server.command.tree;

import org.allaymc.api.command.tree.CommandNode;
import org.allaymc.api.command.tree.CommandNodeFactory;
import org.allaymc.api.entity.Entity;
import org.allaymc.server.command.tree.node.*;

import java.util.List;

/**
 * Allay Project 2024/1/5
 *
 * @author daoge_cmd
 */
public class AllayCommandNodeFactory implements CommandNodeFactory {
    @Override
    public CommandNode key(String key, CommandNode parent, String defaultValue) {
        return new KeyNode(key, parent, defaultValue);
    }

    @Override
    public CommandNode str(String name, CommandNode parent, String defaultValue) {
        return new StringNode(name, parent, defaultValue);
    }

    @Override
    public CommandNode intNum(String name, CommandNode parent, int defaultValue) {
        return new IntNode(name, parent, defaultValue);
    }

    @Override
    public CommandNode floatNum(String name, CommandNode parent, float defaultValue) {
        return new FloatNode(name, parent, defaultValue);
    }

    @Override
    public CommandNode doubleNum(String name, CommandNode parent, double defaultValue) {
        return new DoubleNode(name, parent, defaultValue);
    }

    @Override
    public CommandNode bool(String name, CommandNode parent, boolean defaultValue) {
        return new BooleanNode(name, parent, defaultValue);
    }

    @Override
    public CommandNode enums(String name, CommandNode parent, String defaultValue, String[] enums) {
        return new EnumNode(name, parent, defaultValue, enums);
    }

    @Override
    public CommandNode msg(String name, CommandNode parent, String defaultValue) {
        return new MessageNode(name, parent, defaultValue);
    }

    @Override
    public CommandNode remain(String name, CommandNode parent, Object defaultValue) {
        return new RemainArgNode(name, parent, defaultValue);
    }

    @Override
    public CommandNode target(String name, CommandNode parent, List<Entity> defaultValue) {
        return new TargetNode(name, parent, defaultValue);
    }
}