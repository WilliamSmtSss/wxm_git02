package com.splan.bplan.netty.manager;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelMatcher;

public class ChannelMatchers implements ChannelMatcher {

    @Override
    public boolean matches(Channel channel) {
        return true;
    }
}
