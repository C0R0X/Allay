package org.allaymc.server.component.impl;

import org.allaymc.api.component.annotation.ComponentEventListener;
import org.allaymc.api.component.annotation.ComponentIdentifier;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.api.identifier.Identifier;
import org.allaymc.server.component.event.TestEvent;
import org.allaymc.server.component.interfaces.TestEventListenerComponent;

/**
 * Allay Project 2023/5/6
 *
 * @author daoge_cmd
 */
public class SimpleTestEventListenerComponent implements TestEventListenerComponent {


    @ComponentIdentifier
    public static final Identifier IDENTIFIER = new Identifier("minecraft:test_event_listener_component");

    @ComponentEventListener
    protected void testListener(TestEvent event) {
        event.setMessage("testListener() accepted to the event!");
    }
}