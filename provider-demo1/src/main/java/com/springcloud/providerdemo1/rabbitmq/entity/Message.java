package com.springcloud.providerdemo1.rabbitmq.entity;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String queueName;
    private Timestamp createTime;
    private String exchange = "topic.exchange";
    private Map<String, Object> context = new HashMap();
    private List<T> messages;

    public void addMessage(T message) {
        if (this.getMessages() == null) {
            this.setMessages(new ArrayList());
        }

        this.getMessages().add(message);
    }

    public void addContext(String key, Object value) {
        this.context.put(key, value);
    }

    public Object getContextByKey(String key) {
        return this.context.get(key);
    }

    public Message() {
    }

    public String getQueueName() {
        return this.queueName;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public String getExchange() {
        return this.exchange;
    }

    public Map<String, Object> getContext() {
        return this.context;
    }

    public List<T> getMessages() {
        return this.messages;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public void setMessages(List<T> messages) {
        this.messages = messages;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Message)) {
            return false;
        } else {
            Message<?> other = (Message)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label71: {
                    Object this$queueName = this.getQueueName();
                    Object other$queueName = other.getQueueName();
                    if (this$queueName == null) {
                        if (other$queueName == null) {
                            break label71;
                        }
                    } else if (this$queueName.equals(other$queueName)) {
                        break label71;
                    }

                    return false;
                }

                Object this$createTime = this.getCreateTime();
                Object other$createTime = other.getCreateTime();
                if (this$createTime == null) {
                    if (other$createTime != null) {
                        return false;
                    }
                } else if (!this$createTime.equals(other$createTime)) {
                    return false;
                }

                label57: {
                    Object this$exchange = this.getExchange();
                    Object other$exchange = other.getExchange();
                    if (this$exchange == null) {
                        if (other$exchange == null) {
                            break label57;
                        }
                    } else if (this$exchange.equals(other$exchange)) {
                        break label57;
                    }

                    return false;
                }

                Object this$context = this.getContext();
                Object other$context = other.getContext();
                if (this$context == null) {
                    if (other$context != null) {
                        return false;
                    }
                } else if (!this$context.equals(other$context)) {
                    return false;
                }

                Object this$messages = this.getMessages();
                Object other$messages = other.getMessages();
                if (this$messages == null) {
                    if (other$messages == null) {
                        return true;
                    }
                } else if (this$messages.equals(other$messages)) {
                    return true;
                }

                return false;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Message;
    }

    public int hashCode() {
        int result = 1;
        Object $queueName = this.getQueueName();
        result = result * 59 + ($queueName == null ? 43 : $queueName.hashCode());
        Object $createTime = this.getCreateTime();
        result = result * 59 + ($createTime == null ? 43 : $createTime.hashCode());
        Object $exchange = this.getExchange();
        result = result * 59 + ($exchange == null ? 43 : $exchange.hashCode());
        Object $context = this.getContext();
        result = result * 59 + ($context == null ? 43 : $context.hashCode());
        Object $messages = this.getMessages();
        result = result * 59 + ($messages == null ? 43 : $messages.hashCode());
        return result;
    }

    public String toString() {
        return "Message(queueName=" + this.getQueueName() + ", createTime=" + this.getCreateTime() + ", exchange=" + this.getExchange() + ", context=" + this.getContext() + ", messages=" + this.getMessages() + ")";
    }
}
