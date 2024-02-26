package org.allaymc.server.command.selector.args;

import org.allaymc.api.command.CommandSender;
import org.allaymc.server.command.selector.ParseUtils;
import org.allaymc.api.command.selector.SelectorSyntaxException;
import org.allaymc.api.command.selector.SelectorType;
import org.allaymc.api.command.selector.args.CachedSimpleSelectorArgument;
import org.allaymc.api.entity.Entity;
import org.allaymc.api.math.location.Location3fc;

import java.util.function.Predicate;


public class RX extends CachedSimpleSelectorArgument {
    @Override
    protected Predicate<Entity> cache(SelectorType selectorType, CommandSender sender, Location3fc basePos, String... arguments) throws SelectorSyntaxException {
        ParseUtils.singleArgument(arguments, getKeyName());
        ParseUtils.cannotReversed(arguments[0]);
        final var rx = Double.parseDouble(arguments[0]);
        if (!ParseUtils.checkBetween(-90d, 90d, rx))
            throw new SelectorSyntaxException("RX out of bound (-90 - 90): " + rx);
        return entity -> entity.getLocation().pitch() <= rx;
    }

    @Override
    public String getKeyName() {
        return "rx";
    }

    @Override
    public int getPriority() {
        return 3;
    }
}