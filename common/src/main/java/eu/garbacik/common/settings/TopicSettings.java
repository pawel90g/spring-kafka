package eu.garbacik.common.settings;

import lombok.Data;

@Data
public class TopicSettings {
    private String name;
    private Integer partitionsCount;
    private Integer replicasCount;
}
