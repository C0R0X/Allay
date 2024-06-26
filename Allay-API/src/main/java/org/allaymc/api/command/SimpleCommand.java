package org.allaymc.api.command;

import org.allaymc.api.command.tree.CommandTree;
import org.allaymc.api.i18n.MayContainTrKey;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamData;

/**
 * Allay Project 2023/12/29
 *
 * @author daoge_cmd
 */
public abstract class SimpleCommand extends BaseCommand {

    protected CommandTree commandTree;

    public SimpleCommand(String name, @MayContainTrKey String description) {
        super(name, description);
        this.commandTree = CommandTree.create(this);
        prepareCommandTree(this.commandTree);
        buildOverloadsFromCommandTree();
    }

    public abstract void prepareCommandTree(CommandTree tree);

    protected void buildOverloadsFromCommandTree() {
        var leaves = commandTree.getLeaves();
        for (var leaf : leaves) {
            var paramArray = new CommandParamData[leaf.depth()];
            var node = leaf;
            var index = leaf.depth() - 1;
            while (!node.isRoot()) {
                paramArray[index] = node.toNetworkData();
                node = node.parent();
                index--;
            }
            overloads.add(paramArray);
        }
    }

    @Override
    public CommandResult execute(CommandSender sender, String[] args) {
        return commandTree.parse(sender, args);
    }
}
