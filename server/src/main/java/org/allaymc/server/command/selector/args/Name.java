package org.allaymc.server.command.selector.args;

import org.allaymc.api.command.CommandSender;
import org.allaymc.api.command.selector.SelectorType;
import org.allaymc.api.command.selector.args.CachedSimpleSelectorArgument;
import org.allaymc.api.entity.Entity;
import org.allaymc.api.math.location.Location3dc;
import org.allaymc.server.command.selector.ParseUtils;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * @author daoge_cmd
 */
public class Name extends CachedSimpleSelectorArgument {
    @Override
    protected Predicate<Entity> cache(SelectorType selectorType, CommandSender sender, Location3dc basePos, String... arguments) {
        var have = new ArrayList<String>();
        var dontHave = new ArrayList<String>();
        for (var name : arguments) {
            var reversed = ParseUtils.checkReversed(name);
            if (reversed) {
                name = name.substring(1);
                dontHave.add(name);
            } else have.add(name);
        }
        return entity -> have.stream().allMatch(name -> entity.getCommandSenderName().equals(name)) &&
                         dontHave.stream().noneMatch(name -> entity.getCommandSenderName().equals(name));
    }

    @Override
    public String getKeyName() {
        return "name";
    }

    @Override
    public int getPriority() {
        return 4;
    }
}
