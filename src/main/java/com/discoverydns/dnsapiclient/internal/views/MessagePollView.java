package com.discoverydns.dnsapiclient.internal.views;

import com.discoverydns.dnsapiclient.command.message.Message;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("messagePoll")
public class MessagePollView {
    @JsonProperty("message")
    private Message message;
    @JsonProperty("outstandingMessagesCount")
    private Integer outstandingMessagesCount;

    public Message getMessage() {
        return message;
    }

    public Integer getOutstandingMessagesCount() {
        return outstandingMessagesCount;
    }
}
