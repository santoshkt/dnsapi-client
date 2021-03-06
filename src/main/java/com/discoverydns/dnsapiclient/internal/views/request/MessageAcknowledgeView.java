package com.discoverydns.dnsapiclient.internal.views.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("message")
public class MessageAcknowledgeView {
    @JsonProperty("acknowledged")
    private boolean isAcknowledged() {
        return true;
    }
}
