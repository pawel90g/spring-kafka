package eu.garbacik.common.settings;

import lombok.Data;

@Data
public class TopicWithReplySettings extends TopicSettings {
    private String replyName;
    private String groupId;
}
